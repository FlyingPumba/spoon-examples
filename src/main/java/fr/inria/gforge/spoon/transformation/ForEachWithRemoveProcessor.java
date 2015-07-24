package fr.inria.gforge.spoon.transformation;

import java.util.List;
import java.util.Random;

import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.visitor.Filter;

/**
 * Fixes ForEach blocks which try to remove the iterator variable from the
 * collection while iterating.
 * If let like that, the code would cause a Concurrent Modification Exception.
 * 
 * In this processor, we path/fix those buggy lines by collecting all the items
 * to be removed in another list, and deleting them after we have finished the
 * original ForEach block.
 * 
 * @author Ivan Arcuschin
 */
public class ForEachWithRemoveProcessor extends
		spoon.processing.AbstractProcessor<CtForEach> {

	@java.lang.Override
	public boolean isToBeProcessed(CtForEach element) {
		return true;
	}

	public void process(CtForEach element) {
		List<CtInvocation<?>> removeMethodCalls = element
				.getElements(invocationsFilter);
		if (!removeMethodCalls.isEmpty()) {
			CtVariableAccess<?> collectionIterated = (CtVariableAccess<?>) element
					.getExpression();

			// are we removing the current element in the foreach loop ?
			boolean removing = false;
			CtInvocation<?> removeCall = null;
			for (CtInvocation<?> call : removeMethodCalls) {
				CtVariableAccess<?> access = (CtVariableAccess<?>) call
						.getTarget();
				if (call.getArguments().contains(element.getVariable())
						&& access
								.getVariable()
								.getSimpleName()
								.equals(collectionIterated.getVariable()
										.getSimpleName())) {
					// we found a remove method being called on the
					// collectionIterated using as a parameter the iterator
					// variable: pretty much a bug
					removing = true;
					removeCall = call;
					System.out.println("Found a bug!");
					break;
				}
			}
			if (removing && removeCall != null) {
				// we need to patch this buggy removeCall

				// generate the collector name for the lines to be removed
				// we add a random number to avoid variables with same name
				Random rnd = new Random();
				String removeCollectorName = "toRemove"
						+ String.valueOf(rnd.nextInt(100000));

				// insert a list before the foreach
				CtCodeSnippetStatement snippet = getFactory().Core()
						.createCodeSnippetStatement();
				final java.lang.String value = java.lang.String
						.format(("java.util.List<%s> %s = new java.util.LinkedList<%s>()"),
								element.getVariable().getType().getSimpleName(),
								removeCollectorName, element.getVariable()
										.getType().getSimpleName());
				snippet.setValue(value);
				element.insertBefore(snippet);

				// replace the remove with the add
				CtCodeSnippetStatement addSnippet = getFactory().Core()
						.createCodeSnippetStatement();
				final java.lang.String value3 = java.lang.String.format(
						("%s.add(%s)"), removeCollectorName, element
								.getVariable().getSimpleName());
				addSnippet.setValue(value3);
				removeCall.replace(addSnippet);

				// insert a foreach block after current to remove the
				// hastToRemove elements
				CtCodeSnippetStatement newForEach = getFactory().Core()
						.createCodeSnippetStatement();
				final java.lang.String value2 = java.lang.String.format(
						("for (%s r : %s) {%s.remove(r);}"), element
								.getVariable().getType().getSimpleName(),
						removeCollectorName, element.getExpression()
								.getSignature());
				newForEach.setValue(value2);
				element.insertAfter(newForEach);
			}
		}
	}

	private Filter<CtInvocation<?>> invocationsFilter = new Filter<CtInvocation<?>>() {

		@Override
		public boolean matches(CtInvocation<?> element) {
			return element.getExecutable().getSimpleName().contains("remove");
		}

		@Override
		public Class<?> getType() {
			return CtInvocation.class;
		}
	};

}
