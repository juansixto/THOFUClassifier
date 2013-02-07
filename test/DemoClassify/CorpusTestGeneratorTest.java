package DemoClassify;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import Corpus.Corpus;
import Corpus.TBODCorpusLoader;
import Corpus.TBODCorpusLoader.LabelSet;

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
		final TBODCorpusLoader loader = new TBODCorpusLoader(LabelSet.THREE_LABEL);
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

}
