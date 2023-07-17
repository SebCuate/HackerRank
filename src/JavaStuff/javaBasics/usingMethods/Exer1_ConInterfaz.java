package javaStuff.javaBasics.usingMethods;

public class Exer1_ConInterfaz {
	
	public static void main(String[] args) {
		Exer1_ConInterfaz exe = new Exer1_ConInterfaz();
//		System.out.println( exe.check(0, 0, null );		
	}
	
//	boolean check(int i1, int i2, I1  func) {
//	boolean check(int i1, int i2, I2  func) {
//	boolean check(int i1, int i2, I3  func) {
//		return I2.m(i1, i2);
//	}
	
}

interface I1{
	boolean m(Integer I1, Integer I2); //modifcador de acceso default
	boolean m(long I1, long I2);//modifcador de acceso default
}

interface I2{
	 static boolean m(int i1, int i2){//modifcador de acceso privado: los métodos definidos así deben serlo
		return i1 == i2;
	}
}

interface I3{
	boolean m(Integer I1, Integer I2); //modifcador de acceso default
	default boolean m(long i1, long i2){//modifcador de acceso privado por default
		return i1 == i2;
	}
	static void m() {};//modifcador de acceso privado: los métodos definidos así deben serlo
}
