package fr.inria.gforge.spoon.transformation;

import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.junit.Test;

import spoon.Launcher;

public class StupidmPrefixProcessorTest {
	@Test
	public void customTest() throws Exception {
		final String[] args = { "-i", "src/test/resources/src/", "-o",
				"target/dropthosems/", "-p",
				"fr.inria.gforge.spoon.transformation.StupidmPrefixProcessor" };

		final Launcher launcher = new Launcher();
		launcher.setArgs(args);
		launcher.run();

		assertTrue(Main.compile(Main.tokenize("-1.6 target/dropthosems/"),
				new PrintWriter(System.out), new PrintWriter(System.err), null));
	}
}
