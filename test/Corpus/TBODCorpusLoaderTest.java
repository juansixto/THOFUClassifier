package Corpus;

import static org.junit.Assert.*;


import org.junit.Test;

import Corpus.TBODCorpusLoader.LabelSet;

public class TBODCorpusLoaderTest {

	@Test
	public void testLoad() throws Exception {
		final TBODCorpusLoader loader = new TBODCorpusLoader(LabelSet.THREE_LABEL);
		final Corpus corpus = loader.load();
		
		//corpus is loaded
		assertFalse(corpus.isEmpty());
		assertEquals(988, corpus.size());
		
		//documents are loaded correctly
		Document[] documents = new Document[988];
		documents = corpus.toArray(documents);
		
		assertNull(documents[0].getRating());
		assertTrue(documents[0].getText().startsWith("I decided to stay there during my trip"));
		assertEquals("PR", documents[0].getClassification());
		
		assertNull(documents[987].getRating());
		assertTrue(documents[987].getText().startsWith("Since the last review, no real improvement."));
		assertEquals("PR", documents[987].getClassification());
	}

}
