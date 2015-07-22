package fr.inria.gforge.spoon.transformation;

import org.junit.Test;

import spoon.Launcher;

public class StupidmPrefixProcessorTest {
	@Test
	public void customTest() throws Exception {
		final String[] args = { "-i", "src/test/resources/src/", "-o",
				"target/spooned/", "-p",
				"fr.inria.gforge.spoon.transformation.StupidmPrefixProcessor" };

		final Launcher launcher = new Launcher();
		launcher.setArgs(args);
		launcher.run();

		/*File file = new File("/");
		JavaOutputProcessor output = launcher.createOutputWriter(file,
				launcher.createEnvironment());
		output.createJavaFile(launcher.getFactory().Class().getAll().get(0));

		assertTrue(true);*/
	}
}
