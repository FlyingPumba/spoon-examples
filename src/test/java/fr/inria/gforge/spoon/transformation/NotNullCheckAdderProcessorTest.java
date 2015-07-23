package fr.inria.gforge.spoon.transformation;

import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;

import org.eclipse.jdt.internal.compiler.batch.Main;

import spoon.Launcher;

public class NotNullCheckAdderProcessorTest {

	public void testCompileSourceCodeAfterProcessSourceCodeWithNotNullCheckAdderProcessor()
			throws Exception {
		final String[] args = { "-i", "src/test/resources/src/", "-o",
				"target/spooned/", "-p",
				"fr.inria.gforge.spoon.transformation.NotNullCheckAdderProcessor" };

		final Launcher launcher = new Launcher();
		launcher.setArgs(args);
		launcher.run();

		assertTrue(Main.compile(Main.tokenize("-1.6 target/spooned/"),
				new PrintWriter(System.out), new PrintWriter(System.err), null));
	}
}
