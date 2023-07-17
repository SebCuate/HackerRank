package javaStuff.javaBasics.herencia;

public class HerenciaMetodos {

	public static void main(String[] args) {
//		Super s = new Super(); //Good Night, Harry
//		Sub s = new Sub();//Good Morning, Potter
		SubSub s = new SubSub();//Good Morning, Harry Potter
//		SubSubSub s = new SubSubSub();
		System.out.println(s.greeting() + ", " + ((Super)s).name() +  " " + ((Super)s).edad);
	}
}
	
class Super {
	int edad = 28;
	Super(){System.out.println("Soy el constructor del papa");}
	static String greeting() {return "Good Night";}
	String name() {return "Harry";}
}

class Sub extends Super {
	public Sub() {System.out.println("Soy el constructor de Sub");}
	Sub(int edad){
		System.out.println("Soy el constructor de Sub");
		this.edad = edad;
	}	
	static String greeting() {return "Good Morning";}
	String name() {return "Potter";}
}
class SubSub extends Sub {
	SubSub(){super(35);}//como no existe el contructor sin parametros en el padre se debe especificar el llamado de forma explicita, además, debe ser la primera linea en el constructor 
	String name() {return "Harry Potter";}//Sobreescribo el método, las clases superiores implementaran este método
	Number name(String name) {return edad;} 
}
class SubSubSub extends SubSub {
	static int edad = 29; //Las variables se ocultan, no se sobreescriben 
//	String greeting() {return "Good Afternoon";}// Un metodo estatico no puede sobreescribirse como no estatico y viseversa
	protected static String greeting() {return "Good Afternoon";}
	@Override
	protected Integer name(String name) {return this.name().length() + edad;} //Sobreescribo el método name(String), Cambie tipo de retorno a uno más especifico y visibilidad a uno mayor
	@Override
	String name() {return "Harry Potter Long";}//Sobreescribo el método name()
	char[] name(int edad) {return "Harry Potter".toCharArray();} //Sobrecargo el método name
}
