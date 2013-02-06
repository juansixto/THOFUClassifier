package DemoClassify;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaxonomyClassTest {

	@Test
	public void testSearchFeature() throws Exception {
		TaxonomyClass tc = new TaxonomyClass();
		assertTrue(tc.searchFeature("rooms", 0));
		assertFalse(tc.searchFeature("rooms", 2));
		assertTrue(tc.searchFeature("service", 1));
		assertTrue(tc.searchFeature("staff", 2));
		assertTrue(tc.searchFeature("casino", 3));
	}

}
