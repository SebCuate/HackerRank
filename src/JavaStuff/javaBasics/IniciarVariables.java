package JavaStuff.javaBasics;

public class IniciarVariables {
	
	public static void main(String[] args) {
		byte b = Byte.MAX_VALUE;
		short s = b;
		char c = (char) b;
		s = (short) c;
		int i = c;
		Integer I = i;
		Double D = I.doubleValue();
		i = I;
		long l = Long.MAX_VALUE; 
		float f = l;
		double d = f;
		
		double d2 = Double.MAX_VALUE;
		float f2 = (float) d2;
		long l2 = (long) f2;
		int i2 = (int) l2;
		short s2 = (short) i2;
		byte b2 = (byte) s2;
		char c2 = (char) b2;
//		
	}

}

