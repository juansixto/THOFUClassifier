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
	static int TestSplit = 10; //%
	int Num_Test = 0;
	int Num_Train = 0;
	TaxonomyClass myTC = new TaxonomyClass();
	List<String> RoomSentences = new ArrayList<String>();
	List<String> ServiceSentences = new ArrayList<String>();
	List<String> StaffSentences = new ArrayList<String>();
	List<String> FacilitiesSentences = new ArrayList<String>();


	public String Generate(Corpus corpus) throws IOException{
		//int max = 0; NOT USED, DELETE?
		//int featC = 0; NOT USED, DELETE?
		String generatorData = "";

		int CorpusMax= (corpus.size())*TestSplit/100;
		QWordNetDB qwordnet = QWordNetDB.createInstance();
		int Num_Test = 0;
		int Num_Train = 0;
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		String  tFichero = "data/THOFUDemo.test";
		String  trFichero = "data/THOFUDemo.train";
		File  TrainFile = new File (trFichero);
		File  TestFile = new File (tFichero);


		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		RVFDataset<String, String> dataset = new RVFDataset<String, String>();
		// int nDoc = 1;  NOT USED, DELETE?

		for (Document doc : corpus) {

			final Annotation document = new Annotation(doc.getText());
			pipeline.annotate(document);

			final List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			ClassicCounter<String> featureCounter = new ClassicCounter<String>();
			for (int h = 0; h < sentences.size(); h++) {
				final CoreMap sentence = sentences.get(h);
				final List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
				final Iterator<CoreLabel> it = tokens.iterator();

				while(it.hasNext()) {
					final String pos = it.next().get(PartOfSpeechAnnotation.class);
					if(pos.equals(".") || pos.equals(",") || pos.equals("``")) {
						it.remove();
					}
				}

				boolean negate = false;

				for (int i = 0; i < tokens.size(); i++) {		

					final CoreLabel token = tokens.get(i);

					String word = token.get(TextAnnotation.class).toLowerCase();
					String wordPos = token.get(PartOfSpeechAnnotation.class);
					String lemma = word;
					boolean located = false;

					if(wordPos.startsWith("NN") || wordPos.startsWith("JJ") || wordPos.startsWith("RB")) {
						featureCounter.incrementCount("lemma_" + lemma);


						if(negate) {
							featureCounter.incrementCount("not_lemma_" + lemma);
						}
					}


					if(wordPos.startsWith("JJ") | wordPos.startsWith("JJR")| wordPos.startsWith("JJS")) {
						//String RelWord = ""; NOT USED, DELETE?
						for(int j = 1; j < 3; j++) {
							if(i+j<tokens.size()){
								if(tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NN")|tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NNS")| tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NNP")| tokens.get(i+j).get(PartOfSpeechAnnotation.class).startsWith("NNPS")) {
									//RelWord = tokens.get(i+j).get(TextAnnotation.class).toLowerCase(); NOT USED, DELETE?
									String prevLemma = tokens.get(i+j).get(LemmaAnnotation.class).toLowerCase();
									//String prevLemmaPos = tokens.get(i+j).get(PartOfSpeechAnnotation.class); NOT USED, DELETE?
									final String polarityFeature = "lemma_" + prevLemma + "_" + lemma ;
									final boolean negative = negate != (( qwordnet.getPolarity(lemma, wordPos)) < 0);
									if(negative) {
										featureCounter.decrementCount(polarityFeature);
									} else {
										featureCounter.incrementCount(polarityFeature);
									}
									located = true;
								}
							}
							if ((i-j)>0){
								if(tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NN") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNP") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNS") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNPS")) {
									//RelWord = tokens.get(i-j).get(TextAnnotation.class).toLowerCase(); NOT USED, DELETE?
									String prevLemma = tokens.get(i-j).get(LemmaAnnotation.class).toLowerCase();
									//String prevLemmaPos = tokens.get(i-j).get(PartOfSpeechAnnotation.class); NOT USED, DELETE?
									final String polarityFeature = "lemma_" + prevLemma + "_" + lemma ;
									final boolean negative = negate != (( qwordnet.getPolarity(lemma, wordPos)) < 0);
									if(negative) {
										featureCounter.decrementCount(polarityFeature);
									} else {
										featureCounter.incrementCount(polarityFeature);
									}
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

					if(!negate) {
						for(String negationToken: NEGATION_TOKENS) {
							if(negationToken.equals(word)) {
								negate = true;
								break;
							}
						}
					}
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





			if ((rnd > TestSplit) &&( Num_Test <=CorpusMax)){
				BufferedWriter  trainf = new BufferedWriter (new FileWriter (TrainFile));
				trainf.write(doc.getClassification() +"	" +temp+"\n");
				trainf.close();	
				Num_Train++;
			} else{
				BufferedWriter  testf = new BufferedWriter (new FileWriter (TestFile));
				testf.write(doc.getClassification() +"	" +temp+"\n");
				testf.close();
				Num_Test++;}


		}

		String resp = "Total items: " + (Num_Test+Num_Train) + "Total test items: " + Num_Test + "Total train items: " + Num_Train ;
		System.out.println(CorpusMax);
		System.out.println("Data files created");
		System.out.println("Total items: " + (Num_Test+Num_Train));
		System.out.println("Total test items: " + Num_Test);
		System.out.println("Total train items: " + Num_Train);


		return resp;
	}

	public void PrintResults(){

		System.out.println("Data files created");
		System.out.println("Total items: " + (this.Num_Test + this.Num_Train));
		System.out.println("Total test items: " + this.Num_Test);
		System.out.println("Total train items: " + this.Num_Train);
	}



	public String GenerateSentence(String INsentence) throws IOException
	{


		this.RoomSentences = new ArrayList<String>();
		this.ServiceSentences = new ArrayList<String>();
		this.StaffSentences = new ArrayList<String>();
		this.FacilitiesSentences = new ArrayList<String>();

		QWordNetDB qwordnet = QWordNetDB.createInstance();

		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");


		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		//RVFDataset<String, String> dataset = new RVFDataset<String, String>(); NOT USED, DELETE?


		final Annotation document = new Annotation(INsentence);
		pipeline.annotate(document);

		final List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		ClassicCounter<String> featureCounter = new ClassicCounter<String>();
		for (int h = 0; h < sentences.size(); h++) {
			final CoreMap sentence = sentences.get(h);
			final List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);

			final Iterator<CoreLabel> it = tokens.iterator();

			while(it.hasNext()) {
				final String pos = it.next().get(PartOfSpeechAnnotation.class);
				if(pos.equals(".") || pos.equals(",") || pos.equals("``")) {
					it.remove();
				}
			}

			boolean negate = false;
			boolean roomFeat = false;
			boolean serviceFeat = false;
			boolean staffFeat = false;
			boolean facilityFeat = false;

			for (int i = 0; i < tokens.size(); i++) {

				final CoreLabel token = tokens.get(i);
				String lemma = token.get(LemmaAnnotation.class).toLowerCase();

				String word = token.get(TextAnnotation.class).toLowerCase();
				String wordPos = token.get(PartOfSpeechAnnotation.class);

				if(this.myTC.searchFeature(word,0)){ roomFeat = true;}
				if(this.myTC.searchFeature(word,1)){ serviceFeat = true;}
				if(this.myTC.searchFeature(word,2)){ staffFeat = true;}
				if(this.myTC.searchFeature(word,3)){ facilityFeat = true;}

				boolean located = false;
				if(wordPos.startsWith("NN") || wordPos.startsWith("JJ") || wordPos.startsWith("RB")) {
					featureCounter.incrementCount("lemma_" + lemma);


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
								final boolean negative = negate != (( qwordnet.getPolarity(lemma, wordPos)) < 0);
								if(negative) {
									featureCounter.decrementCount(polarityFeature);
								} else {
									featureCounter.incrementCount(polarityFeature);
								}
								located = true;
							}
						}
						if ((i-j)>0){
							if(tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NN") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNP") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNS") |tokens.get(i-j).get(PartOfSpeechAnnotation.class).startsWith("NNPS")) {
								//RelWord = tokens.get(i-j).get(TextAnnotation.class).toLowerCase(); NOT USED, DELETE?
								String prevLemma = tokens.get(i-j).get(LemmaAnnotation.class).toLowerCase();
								//String prevLemmaPos = tokens.get(i-j).get(PartOfSpeechAnnotation.class);  NOT USED, DELETE?
								final String polarityFeature = "lemma_" + prevLemma + "_" + lemma ;
								final boolean negative = negate != (( qwordnet.getPolarity(lemma, wordPos)) < 0);
								if(negative) {
									featureCounter.decrementCount(polarityFeature);
								} else {
									featureCounter.incrementCount(polarityFeature);
								}
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

				if(!negate) {
					for(String negationToken: NEGATION_TOKENS) {
						if(negationToken.equals(word)) {
							negate = true;
							break;
						}
					}
				}

			}
			if(roomFeat){
				this.RoomSentences.add(sentence.toString());
			}
			if(serviceFeat){
				this.ServiceSentences.add(sentence.toString());
			}
			if(staffFeat){
				this.StaffSentences.add(sentence.toString());
			}
			if(facilityFeat){
				this.FacilitiesSentences.add(sentence.toString());
			}
		}

		String temp = featureCounter.toString();


		String resp = "AA" + "	" +temp;





		return resp;
	}
}


