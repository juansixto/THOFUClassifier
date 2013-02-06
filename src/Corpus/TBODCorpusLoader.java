package Corpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TBODCorpusLoader implements CorpusLoader {
	private final static String CORPUS_PATH = "corpus/TBOD/hotels";
	
	private final static String TEXT_NODE = "text";
	private final static String TITLE_NODE = "title";
	private final static String SENTENCE_NODE = "sentence";

	private final static String RATING_ATTRIBUTE = "rating";
	private final static String ID_ATTRIBUTE = "id";
	
	private HashMap<String, String> labelMappings = new HashMap<String, String>();
	private HashMap<String, String> ratingMappings = new HashMap<String, String>();
	
	private final static String VERY_POOR_LABEL = "VP";
	private final static String POOR_LABEL = "PR";
	private final static String FAIR_LABEL = "FR";
	private final static String GOOD_LABEL = "GD";
	private final static String VERY_GOOD_LABEL = "VG";
	
	private final static String DISCARD_LABEL = "DISCARD";
	
	public enum LabelSet {
		TWO_LABEL,
		THREE_LABEL,
		FIVE_LABEL;
	}
	
	public TBODCorpusLoader(final LabelSet labelSet) {
		if(labelSet == LabelSet.TWO_LABEL) {
			this.labelMappings.put("1", POOR_LABEL);
			this.labelMappings.put("2", POOR_LABEL);
			this.labelMappings.put("3", GOOD_LABEL);
			this.labelMappings.put("4", GOOD_LABEL);
			this.labelMappings.put("5", GOOD_LABEL);
			this.ratingMappings.put("1", VERY_POOR_LABEL);
			this.ratingMappings.put("2", POOR_LABEL);
			this.ratingMappings.put("3", FAIR_LABEL);
			this.ratingMappings.put("4", GOOD_LABEL);
			this.ratingMappings.put("5", VERY_GOOD_LABEL);
		} else if(labelSet == LabelSet.THREE_LABEL) {
			this.labelMappings.put("1", POOR_LABEL);
			this.labelMappings.put("2", POOR_LABEL);
			this.labelMappings.put("3", FAIR_LABEL);
			this.labelMappings.put("4", GOOD_LABEL);
			this.labelMappings.put("5", GOOD_LABEL);
		} else {
			this.labelMappings.put("1", VERY_POOR_LABEL);
			this.labelMappings.put("2", POOR_LABEL);
			this.labelMappings.put("3", FAIR_LABEL);
			this.labelMappings.put("4", GOOD_LABEL);
			this.labelMappings.put("5", VERY_GOOD_LABEL);
		}
	}
	
	public Corpus load() throws CorpusLoaderException {
		final Vector<Document> documents = new Vector<Document>();

		final File corpusPath = new File(CORPUS_PATH);
		
		if(corpusPath.isDirectory()) {
			final File[] documentFiles = corpusPath.listFiles(new RegexFilenameFilter("[0-9]+\\.xml"));
			
			try {
				
				final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
				for(final File documentFile: documentFiles) {
					try {
						org.w3c.dom.Document xmlDocument = builder.parse(new InputSource(new BufferedReader ( new InputStreamReader( new FileInputStream(documentFile), "UTF-8"))));
						final String rating = this.labelMappings.get(xmlDocument.getDocumentElement().getAttribute(RATING_ATTRIBUTE));
						final String fiverat = this.ratingMappings.get(xmlDocument.getDocumentElement().getAttribute(RATING_ATTRIBUTE));
						
						if(!rating.equals(DISCARD_LABEL)) {
							final NodeList titlesNodes = ((Element)xmlDocument.getDocumentElement().getElementsByTagName(TITLE_NODE).item(0)).getElementsByTagName(SENTENCE_NODE);
							final NodeList sentenceNodes =((Element)xmlDocument.getDocumentElement().getElementsByTagName(TEXT_NODE).item(0)).getElementsByTagName(SENTENCE_NODE);
							final Hashtable<Integer, String> tempDocument = new Hashtable<Integer, String>();
							final Element titleNode = (Element) titlesNodes.item(0);
							
							final int idt = Integer.parseInt(titleNode.getAttribute(ID_ATTRIBUTE));
							String sentencet = titleNode.getTextContent().trim();
							sentencet = sentencet.replaceAll("_", " ")
									.replaceAll("\\([0-9]+\\)", "")
									.replaceAll(" (?=[\\.\\,\\;\\:\\!\\?\\)\\'])", "")
									.replaceAll("(?<=\\() ", "")
									.replaceAll("\" ([^\\\"]+) \"", "\"$1\"")
									.replaceAll("-- ([^\\\"]+) --", "--$1--")
									.replaceAll(" / ", "/");
							tempDocument.put(idt, sentencet.trim());
							
							for(int i = 0; i < sentenceNodes.getLength(); i++) {
								final Element sentenceNode = (Element) sentenceNodes.item(i);
								final int id = Integer.parseInt(sentenceNode.getAttribute(ID_ATTRIBUTE));
								String sentence = sentenceNode.getTextContent().trim();
								sentence = sentence.replaceAll("_", " ")
										.replaceAll("\\([0-9]+\\)", "")
										.replaceAll(" (?=[\\.\\,\\;\\:\\!\\?\\)\\'])", "")
										.replaceAll("(?<=\\() ", "")
										.replaceAll("\" ([^\\\"]+) \"", "\"$1\"")
										.replaceAll("-- ([^\\\"]+) --", "--$1--")
										.replaceAll(" / ", "/");
								tempDocument.put(id, sentence.trim());
							}
							
							final StringBuilder textBuilder = new StringBuilder(); 
							
							for(int i = 1; i <= tempDocument.size(); i++) {
								textBuilder.append(tempDocument.get(i));
								textBuilder.append(' ');
							}
							
							documents.add(new Document(rating, textBuilder.toString().trim(),fiverat));
						}
					} catch (SAXException e) {
						System.err.println("Document '" + documentFile.getName() + "' is not well formed!");
					} catch (IOException e) {
						//This message should not be printed
						System.err.println("Document '" + documentFile.getName() + "' does not exist!");
					}
				}
			} catch (final ParserConfigurationException e) {
				throw new CorpusLoaderException("Cannot load the corpus document files", e);
			}
		} else {
			throw new CorpusLoaderException("The corpus directory does not exist");
		}
		
		return new Corpus(documents); 
	}
	
	private class RegexFilenameFilter implements FilenameFilter {
		private final Pattern pattern;

		public RegexFilenameFilter(String pattern) {
			 this.pattern = Pattern.compile(pattern);
		}
		
		@Override
		public boolean accept(File dir, String name) {
			return this.pattern.matcher(name).matches();
		}	
	}
}
