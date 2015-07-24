package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BuggyClass {
	public void method() {
		// the following foreach block is buggy, and thus should be patched
		List<Integer> ints = new ArrayList<>();
		ints.add(0);
		ints.add(1);
		ints.add(2);
		for (Integer i : ints) {
		    if (i <= 1) {
		        ints.remove(i);
		    }
		}
		
		// the following foreachs block is OK, and thus should be left as it is
		List<String> strings = new ArrayList<>();
		strings.add("Foo");
		strings.add("Bar");
		strings.add("Foo2");
		List<String> toRemove = new ArrayList<>();
		for (String s : strings) {
		    if (s.contains("Foo")) {
		    	toRemove.add(s);
		    }
		}
		for (String s : toRemove) {
			strings.remove(s);
		}
		
		// the following while block is OK, and thus should be left as it is
		List<Integer> otherInts = new ArrayList<>();
		otherInts.add(3);
		otherInts.add(4);
		otherInts.add(5);
		Iterator<Integer> it = otherInts.iterator();
		while (it.hasNext()) {
			it.remove();
		}
		
		// the following foreach block is buggy, and thus should be patched (but should not conflict with the previous patch)
		List<Integer> ints2 = new LinkedList<>();
		ints2.add(0);
		ints2.add(1);
		ints2.add(2);
		for (Integer i : ints2) {
		    if (i <= 1) {
		        ints2.remove(i);
		    }
		}
	}
}
