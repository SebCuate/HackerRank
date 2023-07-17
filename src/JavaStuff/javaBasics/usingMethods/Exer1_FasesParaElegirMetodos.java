package javaStuff.javaBasics.usingMethods;

public class Exer1_FasesParaElegirMetodos {

	public static void main(String[] args) {
		var var = 1;
//			= Integer.valueOf(1);
//			= new Short[] {1,2};
		name(Short.valueOf("3"));//array
//		name(var, var);//2 variables
//		name(var, var, var);//array
	}
	private static void name(double i1) {System.out.println("double");}
	private static void name(Integer i2) {System.out.println("Integer");}
	private static void name(Number i2) {System.out.println("Number");}
	
//private static void name(int i1, Integer i2) {System.out.println("Primitivo: int 2 variables");}

//private static void name(int... params) {System.out.println("Primitivo: int array");}
//private static void name(long l1, long l2) {System.out.println("Primitivo: long 2 variables");}	
//private static void name(float l1, float l2) {System.out.println("Primitivo: double 2 variables");}
//private static void name(double l1, double l2) {System.out.println("Primitivo: double 2 variables");}
	
//	private static void name(long... longs1) {
//		System.out.println("Primitivo: long array");	
//	}
	
//private static void name(short N1, short N2) {System.out.println("short primitivo");}
//	
//private static void name(Short N1, Short N2) {System.out.println("Short N1 y N2");}
//	
//private static void name(Double N1, Double N2) {System.out.println("Double N1 y N2");}
//	
//	private static void name(Number N1, Number N2) {
//		System.out.println("NUMBER N1 y N2");
//	}
	
//private static void name(Object N1, Object N2) {System.out.println("Object N1 y N2");}
//	
//	private static void name(Number... N1) {
//		System.out.println("NUMBER Array");
//	}
	
}
