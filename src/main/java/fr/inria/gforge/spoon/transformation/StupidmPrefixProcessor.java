package fr.inria.gforge.spoon.transformation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtVariable;

/**
 * Removes m prefix to variables
 *
 * @author Ivan Arcuschin
 */
public class StupidmPrefixProcessor extends AbstractProcessor<CtVariable<?>> {

	public void process(CtVariable<?> element) {
		// check if variable name starts with 'm' and then upper case
		String name = element.getSimpleName();
		if (name.startsWith("m") && name.length() > 1
				&& Character.isUpperCase(name.charAt(1))) {
			// if so, we have a problem, cuz I don't like those 'm' thingies
			String new_name = Character.toLowerCase(name.charAt(1))
					+ name.substring(2);
			// rename it !
			element.setSimpleName(new_name);
		}
	}
}
