package javaBasics;

public class IniciarVariables {
	String base = "a";
	
	public IniciarVariables(){
		base = appendS(base, "b");
	}
	{
		base = appendS(base, "c");
	}
	
	private String appendS(String base, String append) {
		return base + append;
	}
	
	
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
//		
//		System.out.println("");
////		
//		Hijo h = new Hijo();
//		Test t = h;
//		h = (Hijo) t;
//		System.out.println("ADIOS");
//		String a;
//		StringBuffer b;
//		StringBuilder c;
//		
//		char algo = (char) 0;
////		UtilEnum algo = UtilEnum.BLACK;
//		
//		
//		
//		switch (algo) {
//		case 'a' | 'b' | (char) 100:
//			System.out.println(1);
////		case PINK : case GREEN:
////			System.out.println("ROSA");
////			break;
////		case BLACK:
////			System.out.println("NEGRO");
//			break;
//		default:
//			break;
//		}
//		
//		"".intern();
		
//		Integer.valueOf(12.0);
//		System.out.println(algo());
		
	}
	
	static Object algo() {
		return Integer.valueOf(1);
	}
	
	String algo(char algo){
		switch (algo) {
		case 'a' | 'b' | (char) 100:
			return "OTRA COSA";
//		case PINK : case GREEN:
//			System.out.println("ROSA");
//			break;
//		case BLACK:
//			System.out.println("NEGRO");
		default:
			break;
		}
		return "";
	}
}

class Hijo extends IniciarVariables{
	void hola () {
		
	}
	void adios() {
		
	}
}
