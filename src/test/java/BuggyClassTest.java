import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BuggyClassTest {
	@Test
	public void passingTest() throws Exception {
		BuggyClass c = new BuggyClass();

		assertEquals(c.m1(null), 0);
		assertEquals(c.m2(null), 0);

		assertEquals(c.m1("Hi"), 2);
		assertEquals(c.m2("Hi-Hi"), 5);
	}

	@Test
	public void buggyTest() throws Exception {
		BuggyClass c = new BuggyClass();

		assertEquals(c.m3(null), 0);
		assertEquals(c.m3("Hi"), 2);
	}
}
