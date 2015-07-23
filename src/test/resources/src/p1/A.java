package src.p1;

import java.util.ArrayList;
import java.util.List;

import src.p2.B;

public class A {
	@SuppressWarnings("unused")
	private Integer mField = 0;
	@SuppressWarnings("unused")
	public String mField2 = "";
	
	public B m1() {
		@SuppressWarnings("unused")
		Integer mVariable = 0;
		
		@SuppressWarnings("unused")
		String mVariable2 = "";
		
		List<Integer> ints = new ArrayList<>();
		ints.add(0);
		ints.add(1);
		ints.add(2);
		for (Integer i : ints) {
		    if (i <= 1) {
		        ints.remove(i);
		    }
		}
		
		return null;
	}
}
