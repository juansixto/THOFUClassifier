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
		this.posMappings.put("JJ", "a");
		this.posMappings.put("JJR", "a");
		this.posMappings.put("JJS", "a");
		this.posMappings.put("NN", "n");
		this.posMappings.put("NNS", "n");
		this.posMappings.put("NP", "n");
		this.posMappings.put("NPS", "n");
		this.posMappings.put("RB", "r");
		this.posMappings.put("RBR", "r");
		this.posMappings.put("RBS", "r");
		this.posMappings.put("VB", "v");
		this.posMappings.put("VBD", "v");
		this.posMappings.put("VBG", "v");
		this.posMappings.put("VBN", "v");
		this.posMappings.put("VBP", "v");
		this.posMappings.put("VBZ", "v");
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
			this.posCount++;
		}
		
		private void incrementNegative() {
			this.negCount++;
		}
		
		private int getPolarity() {
			if(this.posCount < this.negCount){
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
				this.polarity = attributes.getValue(SENSE_POLARITY_ATTRIBUTE);
				this.pos = attributes.getValue(SENSE_PART_OF_SPEECH_ATTRIBUTE);
				if(this.pos.equals("s")) {
					this.pos = "a";
				}
			} else if(qName.equals(LEMMA_TAG)) {
				this.text = new StringBuilder();
			}
		}
		
		@Override
		public void endElement(String uri, String lName, String qName) throws SAXException {
			if(qName.equals(LEMMA_TAG)) {
				final String index = this.text.append(".").append(this.pos).toString();
				if(!QWordNetDB.this.polarities.containsKey(index)) {
					QWordNetDB.this.polarities.put(index, new QWordNetPolarityCounter());
				}
				
				if(this.polarity.equals(SENSE_POSITIVE_POLARITY_VALUE)) {
					QWordNetDB.this.polarities.get(index).incrementPositive();
				} else {
					QWordNetDB.this.polarities.get(index).incrementNegative();
				}
			}
		}
		
		@Override
		public void characters(char[] chars, int start, int length) throws SAXException {
			this.text.append(chars, start, length);
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
		if(this.posMappings.containsKey(pos)) {
			final String index = lemma + "." + this.posMappings.get(pos);
			if(this.polarities.containsKey(index)) {
				return this.polarities.get(index).getPolarity();
			} else return 0;
		} else return 0;
	}
	
	public static void main(String[] args) {
		QWordNetDB qwordnet = new QWordNetDB();
		qwordnet.load();

	}
}
