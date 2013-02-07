package DemoClassify;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Corpus.Corpus;
import Corpus.TBODCorpusLoader;
import Corpus.TBODCorpusLoader.LabelSet;

public class ClassifyTrainerTest {
	
	private TBODCorpusLoader loader;
	private Corpus corpus;	
	private CorpusTestGenerator myCTG;
	
	
	@Before 
	public void initialize()  throws Exception {
		this.loader = new TBODCorpusLoader(LabelSet.TWO_LABEL);
		this.corpus = this.loader.load();
		
		this.myCTG = new CorpusTestGenerator();
		this.myCTG.generate(this.corpus);
	}

	
	@Test
	public void testGetClassify() throws Exception {		
		
		final ClassifyTrainer ct = new ClassifyTrainer(ClassifyTrainer.PROP_FILE_PATH);
		ct.setTrainingExamples(CorpusTestGenerator.TRAIN_FILE_PATH);
		ct.setTest(CorpusTestGenerator.TEST_FILE_PATH);
		
		String sentence = this.myCTG.generateSentence("Small and dirty beds");
		assertEquals(ClassifyTrainer.NEGATIVE, ct.getClassify(sentence));
		
		sentence = this.myCTG.generateSentence("The bed is very big and clean");
		assertEquals(ClassifyTrainer.POSITIVE, ct.getClassify(sentence));

	}
	
	/*
	@Test
	public void testSetTest() throws Exception {
		final ClassifyTrainer ct = new ClassifyTrainer(ClassifyTrainer.PROP_FILE_PATH);
		ct.setTrainingExamples(CorpusTestGenerator.TRAIN_FILE_PATH);
		List<MyResult> results = ct.setTest(CorpusTestGenerator.TEST_FILE_PATH);
		System.out.println("size " + results.size());
		for (MyResult myResult : results) {
			System.out.println("calres " + myResult.getCalcRes());
			System.out.println("orires " + myResult.getOriRes());
			System.out.println("res " + myResult.getRes());
		}
		
	}
	*/

}
