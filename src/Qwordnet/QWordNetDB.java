package Qwordnet;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class QWordNetDB {
	private final static String QWORDNET_FILE_LOCATION = "lexicons/qwordnet/qwordnet-30-0.3.xml";
	
	private HashMap<String, QWordNetPolarityCounter> polarities = new HashMap<String, QWordNetDB.QWordNetPolarityCounter>();
	private HashMap<String, String> posMappings = new HashMap<String, String>();
	
	private QWordNetDB() {
		posMappings.put("JJ", "a");
		posMappings.put("JJR", "a");
		posMappings.put("JJS", "a");
		posMappings.put("NN", "n");
		posMappings.put("NNS", "n");
		posMappings.put("NP", "n");
		posMappings.put("NPS", "n");
		posMappings.put("RB", "r");
		posMappings.put("RBR", "r");
		posMappings.put("RBS", "r");
		posMappings.put("VB", "v");
		posMappings.put("VBD", "v");
		posMappings.put("VBG", "v");
		posMappings.put("VBN", "v");
		posMappings.put("VBP", "v");
		posMappings.put("VBZ", "v");
	}
	
	public static QWordNetDB createInstance() {
		final QWordNetDB qWordNetDB = new QWordNetDB();
		qWordNetDB.load();
		return qWordNetDB;
	}
	
	private class QWordNetPolarityCounter {
		private int posCount = 0;
		private int negCount = 0;
		
		private void incrementPositive() {
			posCount++;
		}
		
		private void incrementNegative() {
			negCount++;
		}
		
		private int getPolarity() {
			if(posCount < negCount){
				return -1;
			} else return 1; 

		}
	}
	
	private class QWordNetXMLSAXParserHandler extends DefaultHandler {
		
		private final static String SENSE_TAG = "sense";
		private final static String SENSE_POLARITY_ATTRIBUTE = "polarity";
		private final static String SENSE_POSITIVE_POLARITY_VALUE = "1";
		private final static String SENSE_PART_OF_SPEECH_ATTRIBUTE = "pos";
		
		private final static String LEMMA_TAG = "lemma";
		
		private StringBuilder text = new StringBuilder();
		private String polarity;
		private String pos;
		
		@Override
		public void startElement(String uri, String lName, String qName, Attributes attributes) throws SAXException {
			if(qName.equals(SENSE_TAG)) {
				polarity = attributes.getValue(SENSE_POLARITY_ATTRIBUTE);
				pos = attributes.getValue(SENSE_PART_OF_SPEECH_ATTRIBUTE);
				if(pos.equals("s")) {
					pos = "a";
				}
			} else if(qName.equals(LEMMA_TAG)) {
				text = new StringBuilder();
			}
		}
		
		@Override
		public void endElement(String uri, String lName, String qName) throws SAXException {
			if(qName.equals(LEMMA_TAG)) {
				String index = text.append(".").append(pos).toString();
				if(!polarities.containsKey(index)) {
					polarities.put(index, new QWordNetPolarityCounter());
				}
				
				if(polarity.equals(SENSE_POSITIVE_POLARITY_VALUE)) {
					polarities.get(index).incrementPositive();
				} else {
					polarities.get(index).incrementNegative();
				}
			}
		}
		
		@Override
		public void characters(char[] chars, int start, int length) throws SAXException {
			text.append(chars, start, length);
		}
	}
	
	private void load() {
		try {
			final SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			saxParser.parse(QWORDNET_FILE_LOCATION, new QWordNetXMLSAXParserHandler());
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public int getPolarity(final String lemma, String pos) {
		if(posMappings.containsKey(pos)) {
			String index = lemma + "." + posMappings.get(pos);
			if(polarities.containsKey(index)) {
				return polarities.get(index).getPolarity();
			} else return 0;
		} else return 0;
	}
	
	public static void main(String[] args) {
		QWordNetDB qwordnet = new QWordNetDB();
		qwordnet.load();

	}
}
