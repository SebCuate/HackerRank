package javaStuff.javaBasics.herencia;

public class Herencias_Instancias {

	public static void main(String[] args) {
		I1 i1 = new C2();
		I2 i2 = new C2();
		C1 c1 = new C2();
		C2 c2 = (C2) new C1();
	}
	
	
}

interface I1{}
interface I2 extends I1{}
class C1 implements I1{}
class C2 extends C1 implements I2{}
