import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.crypto.KeySelector.Purpose;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import spoon.Launcher;
import spoon.reflect.code.CtAssert;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtTry;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtSimpleType;
import spoon.reflect.visitor.Filter;
import spoon.support.compiler.VirtualFile;

public class MainTest {

	Launcher launcher = new Launcher();
	List<CtSimpleType<?>> classes = new ArrayList<CtSimpleType<?>>();
	CtSimpleType<?> buggyClass;
	List<CtStatement> statements = new ArrayList<CtStatement>();
	List<CtStatement> buggyStatements = new ArrayList<CtStatement>();
	
	List<CtSimpleType<?>> population = new ArrayList<CtSimpleType<?>>();
	int pop_size = 10;

	public void test() {
		String[] spoon_args = { "-i", "src/main/java/", "-o", "src/main/java/" };
		launcher.setArgs(spoon_args);
		try {
			launcher.run();
		} catch (Exception e) {
			return;
		}

		initClasses();
		initStatements();
		initPopulation();

		while (true) {
			JUnitCore junit = new JUnitCore();
			Result result = junit.run(BuggyClassTest.class);
			if (!result.wasSuccessful()) {
				//mutateBuggyClass();
				writeBuggyClass();
			} else {
				break;
			}
		}
	}

	private void initClasses() {
		for (CtSimpleType<?> c : launcher.getFactory().Class().getAll()) {
			classes.add(c);
			if (c.getActualClass() == BuggyClass.class) {
				buggyClass = c;
			}
		}
	}

	private void initStatements() {
		for (CtSimpleType<?> c : classes) {
			statements.addAll(c.getElements(statementFilter));
			if (c.getActualClass() == BuggyClass.class) {
				buggyStatements.addAll(c.getElements(statementFilter));
			}
		}
	}
	
	private void initPopulation() {
		Random rnd = new Random();
		while (population.size() < pop_size) {
			// pick a random class
			CtSimpleType<?> candidate = buggyClass;
			// mutate it
			
			// add it
		}
	}

	private void mutateClass(CtSimpleType<?> candidate) {
		Random rnd = new Random();
		// randomly choose a line to insert
		CtStatement newStatement = statements
				.get(rnd.nextInt(statements.size()));

		// randomly choose a place to insert it
		CtStatement oldStatement = buggyStatements.get(rnd
				.nextInt(buggyStatements.size()));
		if (rnd.nextGaussian() < 0.5) {
			oldStatement.insertAfter(newStatement);
		} else {
			oldStatement.insertBefore(newStatement);
		}
	}

	private void writeBuggyClass() {
		String[] spoon_args = { "-o", "src/main/java/" };
		launcher.setArgs(spoon_args);
		VirtualFile virtual = new VirtualFile(buggyClass.toString());
		launcher.addInputResource(virtual);
		try {
			launcher.run();
		} catch (Exception e) {
			return;
		}

		/*org.eclipse.jdt.internal.compiler.batch.Main.compile(
				org.eclipse.jdt.internal.compiler.batch.Main
						.tokenize("-1.6 src/main/java/"), new PrintWriter(
						System.out), new PrintWriter(System.err), null);*/
	}

	private Filter<CtStatement> statementFilter = new Filter<CtStatement>() {

		@Override
		public boolean matches(CtStatement element) {
			if (element instanceof CtBlock<?>) {
				return false;
			} else if (element instanceof CtAssert<?>) {
				return false;
			} else if (element instanceof CtTry) {
				return false;
			} else if (element instanceof CtClass<?>) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public Class<?> getType() {
			return CtStatement.class;
		}
	};

	/*		CtSimpleType<?> classA = classes.get(0);
		CtSimpleType<?> classB = classes.get(1);

		List<CtStatement> statementsA = classA.getElements(statementFilter);
		List<CtStatement> statementsB = classB.getElements(statementFilter);
		System.out.println(String.valueOf(statementsA.size()));
		System.out.println(String.valueOf(statementsB.size()));

		System.out.println(String.valueOf("-------------- A statements: "));
		for (CtStatement ctStatement : statementsA) {
			System.out.println(String.valueOf("---" + ctStatement.toString()));
		}
		System.out.println(String.valueOf("-------------- B statements: "));
		for (CtStatement ctStatement : statementsB) {
			System.out.println(String.valueOf("---" + ctStatement.toString()));
		}

		// generate childrens
		CtClass<?> classP = (CtClass<?>) launcher.getFactory().Code().createClassAccess(classA.getReference());
		CtClass<?> classQ = (CtClass<?>) launcher.getFactory().Code().createClassAccess(classB.getReference());
		
		int cutoff = Math.min(statementsA.size(), statementsB.size());*/
}
