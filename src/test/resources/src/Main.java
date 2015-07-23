package src;

import src.p3.C;

import java.util.Vector;

public class Main {
	@SuppressWarnings("unused")
	private Integer mField = 0;
	@SuppressWarnings("unused")
	public String mField2 = "";
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Integer mVariable = 0;
		
		@SuppressWarnings("unused")
		String mVariable2 = "";
		
		try {
			@SuppressWarnings("unused")
			Vector<?> v = null;
			m1();
		} catch (Exception ignored) {
		}
	}

	public static void m1() throws Exception {
		m2();
	}

	public static void m2() throws Exception {
		throw new RuntimeException();
	}

	public void m(C c) throws Exception {
	}
}
