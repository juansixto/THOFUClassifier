package DemoClassify;

import static org.junit.Assert.*;

import org.junit.Test;

import Corpus.Corpus;
import Corpus.TBODCorpusLoader;
import Corpus.TBODCorpusLoader.LabelSet;

public class CorpusTestGeneratorTest {

	@Test
	public void testGenerateSentence() throws Exception {
		CorpusTestGenerator ctg = new CorpusTestGenerator();
		
		ctg.generateSentence("The room is big and good very good, the staff is bad");
		assertEquals(1, ctg.RoomSentences.size());
		assertEquals(0, ctg.ServiceSentences.size());
		assertEquals(1, ctg.StaffSentences.size());
		assertEquals(0, ctg.FacilitiesSentences.size());
	}

}
