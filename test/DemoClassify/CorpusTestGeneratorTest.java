package DemoClassify;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import Corpus.Corpus;
import Corpus.TBODCorpusLoader;
import Corpus.TBODCorpusLoader.LabelSet;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class CorpusTestGeneratorTest {

	@Test
	public void testGenerateSentence() throws Exception {
		CorpusTestGenerator ctg = new CorpusTestGenerator();
		
		ctg.generateSentence("The room is big and good, the staff is bad");
		assertEquals(1, ctg.roomSentences.size());
		assertEquals(0, ctg.serviceSentences.size());
		assertEquals(1, ctg.staffSentences.size());
		assertEquals(0, ctg.facilitiesSentences.size());
	}
	@Test
	public void testGenerate() throws Exception {
		final TBODCorpusLoader loader = new TBODCorpusLoader(LabelSet.TWO_LABEL);
		final Corpus corpus = loader.load();
				
		String TEST_FILE_PATH = "data/tests/THOFUDemo.test";
		String TRAIN_FILE_PATH = "data/tests/THOFUDemo.train";
		
		File fileTest = new File(TEST_FILE_PATH);
		if (fileTest.exists()){
			fileTest.delete();
		}		
		File fileTrain = new File(TRAIN_FILE_PATH);
		if (fileTrain.exists()){
			fileTrain.delete();
		}
				
		CorpusTestGenerator ctg = new CorpusTestGenerator(TEST_FILE_PATH, TRAIN_FILE_PATH);
		ctg.generate(corpus);
		
		assertTrue(fileTest.exists());
		assertTrue(fileTrain.exists());
	}
	@Test
	public void testClearText() {
		CorpusTestGenerator ctg = new CorpusTestGenerator();

		if(CorpusTestGenerator.CLEAR_TEXT){
			Annotation document = new Annotation("Today, is the day.");
			ctg.pipeline.annotate(document);
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			for (int h = 0; h < sentences.size(); h++) {
			CoreMap sentence = sentences.get(h);		
			List<CoreLabel> tokens = ctg.clearText(sentence);
			assertEquals(4, tokens.size());
			}
		}
	}
	
	@Test
	public void testaddPolarity() {
		CorpusTestGenerator ctg = new CorpusTestGenerator();
		if(CorpusTestGenerator.POLARITY_TAGGING){
			String lemma = "good";
			String prevLemma = "hotel";
			String polarityFeature = "lemma_" + prevLemma + "_" + lemma ;
			ctg.addPolarity(lemma, "JJ", polarityFeature);
			assertEquals("{lemma_hotel_good=1.0}", ctg.featureCounter.toString());
			lemma = "bad";
			polarityFeature = "lemma_" + prevLemma + "_" + lemma ;
			ctg.addPolarity(lemma, "JJ", polarityFeature);
			assertEquals("{lemma_hotel_good=1.0, lemma_hotel_bad=-1.0}", ctg.featureCounter.toString());
			}
	}
	
	@Test
	public void testposTagging() {
		CorpusTestGenerator ctg = new CorpusTestGenerator();
		if(CorpusTestGenerator.PART_OF_SPEECH){
			String lemma = "good";
			ctg.posTagging(lemma, "JJ");
			assertEquals("{lemma_good_POS_JJ=1.0}", ctg.featureCounter.toString());
		}				
	}
	@Test
	public void testcheckNegate() {
		CorpusTestGenerator ctg = new CorpusTestGenerator();
		if(CorpusTestGenerator.NEGATIVE_TAGGING){
			ctg.checkNegate("hello");
			assertFalse(ctg.negate);
			ctg.checkNegate("nor");
			assertTrue(ctg.negate);
			
		}
		
	}

}
