package fr.inria.gforge.spoon.analysis;


/** 
 * Reports warnings when empty catch blocks are found.
 */
public class CatchProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.code.CtCatch> {
    public java.util.List<spoon.reflect.code.CtCatch> emptyCatchs = new java.util.ArrayList<spoon.reflect.code.CtCatch>();

    public void process(spoon.reflect.code.CtCatch element) {
        if ((element.getBody().getStatements().size()) == 0) {
            emptyCatchs.add(element);
            getFactory().getEnvironment().report(this, spoon.processing.Severity.WARNING, element, "empty catch clause");
        } 
    }
}

