package Qwordnet;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QWordNetDBTest {

	@Test
	public void TestGetPolarity() throws Exception {
		QWordNetDB qwdb = QWordNetDB.createInstance();
		assertEquals(0, qwdb.getPolarity("aged", "JJ"));
		assertEquals(-1, qwdb.getPolarity("cheap", "JJ"));
		assertEquals(1, qwdb.getPolarity("happy", "JJ"));
		assertEquals(1, qwdb.getPolarity("old", "JJ"));
		assertEquals(1, qwdb.getPolarity("easy", "JJ"));
		
		//non-existent word
		assertEquals(0, qwdb.getPolarity("jumar", "JJ"));

	}
}
