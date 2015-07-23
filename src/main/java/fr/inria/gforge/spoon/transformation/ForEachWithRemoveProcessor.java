package fr.inria.gforge.spoon.transformation;

import java.util.List;

import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.visitor.Filter;

/** 
 * Adds a not-null check for all method parameters which are objects
 * 
 * @author Martin Monperrus
 */
public class ForEachWithRemoveProcessor extends spoon.processing.AbstractProcessor<CtForEach> {
    
	@java.lang.Override
    public boolean isToBeProcessed(CtForEach element) {
        return true;
    }

    public void process(CtForEach element) {
    	List<CtInvocation<?>> removeMethodCalls = element.getElements(invocationsFilter);
    	if (!removeMethodCalls.isEmpty()) {
    		// are we removing the current element in the foreach loop ?
    		boolean removing = false;
    		CtInvocation<?> removeCall = null;
    		for (CtInvocation<?> call: removeMethodCalls) {
    			if (call.getArguments().contains(element.getVariable())) {
    				removing = true;
    				removeCall = call;
    				System.out.println("Found a bug!");
    				break;
    			}
    		} 
    		if (removing && removeCall != null) {
	    		// we have a remove method call inside a foreach method
	    		//element.getBody().replace(element);
	    		
	    		// insert a list before the foreach
	    		CtCodeSnippetStatement snippet = getFactory().Core().createCodeSnippetStatement();
	            final java.lang.String value = java.lang.String.format(("List<%s> toRemove = new LinkedList<%s>()"), element.getVariable().getType().getSimpleName(), element.getVariable().getType().getSimpleName());
	            snippet.setValue(value);
	            element.insertBefore(snippet);
	            
	            // replace the remove with the add
	            CtCodeSnippetStatement addSnippet = getFactory().Core().createCodeSnippetStatement();
	            final java.lang.String value3 = java.lang.String.format(("toRemove.add(%s)"), element.getVariable().getType().getSimpleName());
	            addSnippet.setValue(value3);
	            removeCall.replace(addSnippet);
	            
	    		
	    		// insert a foreach block after current to remove the hastToRemove elements
	    		CtCodeSnippetStatement newForEach = getFactory().Core().createCodeSnippetStatement();
	    		final java.lang.String value2 = java.lang.String.format(("for (%s r : toRemove) {%s.remove(r);}"), element.getVariable().getType().getSimpleName(), element.getExpression().getSignature());
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

