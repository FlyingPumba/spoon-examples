package fr.inria.gforge.spoon.transformation;


/** 
 * Removes m prefix to variables
 * 
 * @author Ivan Arcuschin
 */
public class StupidmPrefixProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtVariable<?>> {
    public void process(spoon.reflect.declaration.CtVariable<?> element) {
        java.lang.String name = element.getSimpleName();
        if (((name.startsWith("m")) && ((name.length()) > 1)) && (java.lang.Character.isUpperCase(name.charAt(1)))) {
            getFactory().getEnvironment().report(this, spoon.processing.Severity.MESSAGE, element, ("Found variable name: " + name));
            java.lang.String new_name = (java.lang.Character.toLowerCase(name.charAt(1))) + (name.substring(2));
            element.setSimpleName(new_name);
            getFactory().getEnvironment().report(this, spoon.processing.Severity.MESSAGE, element, ("Change that name to : " + new_name));
        } 
    }
}

