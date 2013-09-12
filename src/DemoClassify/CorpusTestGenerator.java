package DemoClassify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import Corpus.Corpus;
import Corpus.Document;
import Qwordnet.QWordNetDB;
import edu.stanford.nlp.classify.RVFDataset;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.RVFDatum;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.stats.ClassicCounter;
import edu.stanford.nlp.util.CoreMap;


public class CorpusTestGenerator {

	private final static String[] NEGATION_TOKENS = {"not", "nt", "neither", "nor"};
	public final static String TEST_FILE_PATH = "data/THOFUDemo.test";
	public final static String TRAIN_FILE_PATH = "data/THOFUDemo.train";
	public final static boolean CLEAR_TEXT = true;
	public final static boolean PART_OF_SPEECH = true;
	public final static boolean POLARITY_TAGGING = true;
	public final static boolean NEGATIVE_TAGGING = true;
	private final static boolean DEBUG = true;
	
	final Properties props;
	final StanfordCoreNLP pipeline;
	final QWordNetDB qwordnet;
	boolean negate;
	ClassicCounter<String> featureCounter;
	final int testSplit = 10; //%
	int numTest = 0;
	int numTrain = 0;
	TaxonomyClass myTC = new TaxonomyClass();
	List<String> roomSentences = new ArrayList<String>();
	List<String> serviceSentences = new ArrayList<String>();
	List<String> staffSentences = new ArrayList<String>();
	List<String> facilitiesSentences = new ArrayList<String>();
	
	String  testFilePath = TEST_FILE_PATH;
	String  trainFilePath = TRAIN_FILE_PATH;
	
	public CorpusTestGenerator(){
		this.testFilePath = TEST_FILE_PATH;
		this.trainFilePath = TRAIN_FILE_PATH;
		this.featureCounter = new ClassicCounter<String>();
		this.qwordnet = QWordNetDB.createInstance();
		this.props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		this.pipeline = new StanfordCoreNLP(props);
	}
	
	public CorpusTestGenerator(String testFilePath, String trainFileParg){
		this.testFilePath = testFilePath;
		this.trainFilePath = trainFileParg;
		this.featureCounter = new ClassicCounter<String>();
		this.qwordnet = QWordNetDB.createInstance();
		this.props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		this.pipeline = new StanfordCoreNLP(props);
	}

	public List<CoreLabel> clearText (CoreMap core){
		List<CoreLabel> tokens = core.get(TokensAnnotation.class);
		Iterator<CoreLabel> it = tokens.iterator();
		if(CLEAR_TEXT){
		while(it.hasNext()) {
			final String pos = it.next().get(PartOfSpeechAnnotation.class);
			if(pos.equals(".") || pos.equals(",") || pos.equals("``")) {
				it.remove();
			}
		}
		}
		return tokens;
		
	}
	
	public void addPolarity(String lemma, String wordPos, String polarityFeature){
		if(POLARITY_TAGGING){
			boolean negative = (negate != (( qwordnet.getPolarity(lemma, wordPos)) < 0));
			if(negative) {
				featureCounter.decrementCount(polarityFeature);
			} else {
				featureCounter.incrementCount(polarityFeature);
			}
		}
	}
	public void posTagging(String lemma, String wordPos){
		if(PART_OF_SPEECH){
			if(!POLARITY_TAGGING){
				if(!negate){
					featureCounter.incrementCount("lemma_" + lemma + "_POS_" + wordPos);	
				} else {
					featureCounter.incrementCount("not_lemma_" + lemma + "_POS_" + wordPos);		
				}		
			}else {
				boolean negative = (negate != (( qwordnet.getPolarity(lemma, wordPos)) < 0));
				if(negative) {
					featureCounter.decrementCount("lemma_" + lemma + "_POS_" + wordPos);
				} else {
					featureCounter.incrementCount("lemma_" + lemma + "_POS_" + wordPos);
				}
				
				
			}
		}
	}
	
	public void checkNegate(String word){
		if(!negate) {
			for(String negationToken: NEGATION_TOKENS) {
				if(negationToken.equals(word)) {
					negate = true;
					break;
				}
			}
		}
	}
		
	
	
	public void generate(Corpus corpus) throws IOException{
		String generatorData = "";

		int corpusMax= (corpus.size())*testSplit/100;
		this.numTest = 0;
		this.numTrain = 0;

		
		File  trainFile = new File (this.trainFilePath);
		File  testFile = new File (this.testFilePath);
		BufferedWriter  trainf = new BufferedWriter (new FileWriter (trainFile));
		BufferedWriter  testf = new BufferedWriter (new FileWriter (testFile));



		RVFDataset<String, String> dataset = new RVFDataset<String, String>();

		for (Document doc : corpus) {

			final Annotation document = new Annotation(doc.getText());
			pipeline.annotate(document);

			final List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			featureCounter = new ClassicCounter<String>();
			for (int h = 0; h < sentences.size(); h++) {
				final CoreMap sentence = sentences.get(h);
				final List<CoreLabel> tokens = clearText(sentence);

				negate = false;

				for (int i = 0; i < tokens.size(); i++) {		

					final CoreLabel token = tokens.get(i);

					String word = token.get(TextAnnotation.class).toLowerCase();
					String wordPos = token.get(PartOfSpeechAnnotation.class);
					String lemma = word;
					boolean located = false;

					if(wordPos.startsWith("NN") || wordPos.startsWith("JJ") || wordPos.startsWith("RB")) {
						featureCounter.incrementCount("lemma_" + lemma);
						posTagging(lemma, wordPos);
						if(negate) {
							featureCounter.incrementCount("not_lemma_" + lemma);
						}
					}


					if(wordPos.startsWith("JJ") | wordPos.startsWith("JJR")| wordPos.startsWith("JJS")) {
						for(int j = 1; j < 3; j++) {
							if(i+j<tokens.size()){
								if(tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NN")|tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NNS")| tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NNP")| tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NNPS")) {
									String prevLemma = tokens.get(i+j).get(LemmaAnnotation.class).toLowerCase();
									final String polarityFeature = "lemma_" + prevLemma + "_" + lemma ;
									addPolarity(lemma,wordPos,polarityFeature);
									located = true;
								}
							}
							if ((i-j)>0){
								if(tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NN") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNP") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNS") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNPS")) {
									String prevLemma = tokens.get(i-j).get(LemmaAnnotation.class).toLowerCase();
									final String polarityFeature = "lemma_" + prevLemma + "_" + lemma ;
									addPolarity(lemma,wordPos,polarityFeature);
									located = true;
								}
							}
							if(located)
							{
								located = false;
								break;
							}
						}
					} 
					checkNegate(word);
				}
			}
			dataset.add(new RVFDatum<String, String>(featureCounter, doc.getClassification()));
			String temp = featureCounter.toString();
			temp = temp.replace(" ", "");
			temp = temp.replace("{", "");
			temp = temp.replace("}", "");
			temp = temp.replace("'", "");

			generatorData = generatorData.replace(" ", "");
			generatorData = generatorData.replace("{", "");
			generatorData = generatorData.replace("}", "");
			generatorData = generatorData.replace("'", "");
			double rnd = Math.random()*100;





			if ((rnd > testSplit) &&( this.numTest <=corpusMax)){
				trainf.write(doc.getClassification() +"	" +temp+"\n");
				this.numTrain++;
			} else{	
				testf.write(doc.getClassification() +"	" +temp+"\n");
				this.numTest++;
			}


		}
		
		trainf.close();	
		testf.close();

		if (DEBUG) {
			System.out.println(corpusMax);
			this.printResults();
		}
	}

	public void printResults(){

		System.out.println("Data files created");
		System.out.println("Total items: " + (this.numTest + this.numTrain));
		System.out.println("Total test items: " + this.numTest);
		System.out.println("Total train items: " + this.numTrain);
	}



	public String generateSentence(String INsentence) throws IOException
	{
		this.roomSentences = new ArrayList<String>();
		this.serviceSentences = new ArrayList<String>();
		this.staffSentences = new ArrayList<String>();
		this.facilitiesSentences = new ArrayList<String>();

		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");


		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		//RVFDataset<String, String> dataset = new RVFDataset<String, String>(); NOT USED, DELETE?


		final Annotation document = new Annotation(INsentence);
		pipeline.annotate(document);

		final List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		featureCounter = new ClassicCounter<String>();
		for (int h = 0; h < sentences.size(); h++) {
			final CoreMap sentence = sentences.get(h);
			final List<CoreLabel> tokens = clearText(sentence);

			negate = false;
			boolean roomFeat = false;
			boolean serviceFeat = false;
			boolean staffFeat = false;
			boolean facilityFeat = false;

			for (int i = 0; i < tokens.size(); i++) {

				final CoreLabel token = tokens.get(i);
				String lemma = token.get(LemmaAnnotation.class).toLowerCase();

				String word = token.get(TextAnnotation.class).toLowerCase();
				String wordPos = token.get(PartOfSpeechAnnotation.class);

				if(this.myTC.searchFeature(word,0)){ 
					roomFeat = true;
				}
				
				if(this.myTC.searchFeature(word,1)){ 
					serviceFeat = true;
				}
				
				if(this.myTC.searchFeature(word,2)){ 
					staffFeat = true;
				}
				
				if(this.myTC.searchFeature(word,3)){ 
					facilityFeat = true;
				}

				boolean located = false;
				if(wordPos.startsWith("NN") || wordPos.startsWith("JJ") || wordPos.startsWith("RB")) {
					featureCounter.incrementCount("lemma_" + lemma);
					posTagging(lemma, wordPos);
					if(negate) {
						featureCounter.incrementCount("not_lemma_" + lemma);
					}
				}


				if(wordPos.startsWith("JJ") | wordPos.startsWith("JJR")| wordPos.startsWith("JJS")) {
					//String RelWord = ""; NOT USED, DELETE?
					for(int j = 1; j < 3; j++) {
						if(i+j<tokens.size()){
							if(tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NN")|tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NNS")| tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NNP")| tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NNPS")) {
								//RelWord = tokens.get(i+j).get(TextAnnotation.class).toLowerCase();  NOT USED, DELETE?
								String prevLemma = tokens.get(i+j).get(LemmaAnnotation.class).toLowerCase();
								//String prevLemmaPos = tokens.get(i+j).get(PartOfSpeechAnnotation.class);  NOT USED, DELETE?
								final String polarityFeature = "lemma_" + prevLemma + "_" + lemma ;
								addPolarity(lemma,wordPos,polarityFeature);
								located = true;
							}
						}
						if ((i-j)>0){
							if(tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NN") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNP") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNS") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNPS")) {
								//RelWord = tokens.get(i-j).get(TextAnnotation.class).toLowerCase(); NOT USED, DELETE?
								String prevLemma = tokens.get(i-j).get(LemmaAnnotation.class).toLowerCase();
								//String prevLemmaPos = tokens.get(i-j).get(PartOfSpeechAnnotation.class);  NOT USED, DELETE?
								final String polarityFeature = "lemma_" + prevLemma + "_" + lemma ;
								addPolarity(lemma,wordPos,polarityFeature);
								located = true;
							}
						}
						if(located)
						{
							located = false;
							break;
						}
					}
				} 


				/////////////////////////////////////////////////

				checkNegate(word);

			}
			if(roomFeat){
				this.roomSentences.add(sentence.toString());
			}
			if(serviceFeat){
				this.serviceSentences.add(sentence.toString());
			}
			if(staffFeat){
				this.staffSentences.add(sentence.toString());
			}
			if(facilityFeat){
				this.facilitiesSentences.add(sentence.toString());
			}
		}

		String temp = featureCounter.toString();
		String resp = "AA" + "	" +temp;

		return resp;
	}
}


