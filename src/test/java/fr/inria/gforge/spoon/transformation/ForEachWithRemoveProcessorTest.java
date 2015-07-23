package fr.inria.gforge.spoon.transformation;

import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.junit.Test;

import spoon.Launcher;
import spoon.processing.ProcessingManager;
import spoon.reflect.factory.Factory;
import spoon.support.QueueProcessingManager;

public class ForEachWithRemoveProcessorTest {
	@Test
	public void customTest() throws Exception {
		final String[] args = { "-i", "src/test/resources/src/", "-o",
				"target/foreach/", "-p",
				"fr.inria.gforge.spoon.transformation.ForEachWithRemoveProcessor" };

		final Launcher launcher = new Launcher();
		launcher.setArgs(args);
		launcher.run();
		
		/*final Factory factory = launcher.getFactory();
		final ProcessingManager processingManager = new QueueProcessingManager(
				factory);
		final ForEachWithRemoveProcessor processor = new ForEachWithRemoveProcessor();
		processingManager.addProcessor(processor);
		processingManager.process(factory.Class().getAll());*/

		//assertTrue(Main.compile(Main.tokenize("-1.6 target/foreach/"),
		//		new PrintWriter(System.out), new PrintWriter(System.err), null));
	}
}
