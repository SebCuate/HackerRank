package JavaStuff.javaBasics.strings;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class StringsExercise {
	
	public static void main(String[] args) {
		
		String s = "";
		StringBuilder sb = new StringBuilder();
//		System.out.println(s instanceof );
		
		String s1 = "Hello World";
		String s2 = "Hello";
		String s3 = "hELLO wORLD";
		String s4 = "Hello World";
		String s5 = "Hello World mom";
		
		System.out.println(s1.compareTo(s2));
		System.out.println(s1.compareTo(s3));
		System.out.println(s1.compareTo(s4));
		System.out.println(s1.compareTo(s5));

		System.out.println("...................");
		System.out.println(Arrays.compare(s1.toCharArray(), s2.toCharArray()));
		System.out.println(Arrays.compare(s1.toCharArray(), s3.toCharArray()));
		
		String[] sa1 = {"Holaasdasdasdasdasd", "Adios", "l", "s", "34"};
		String[] sa2 = {"H", "Adi"};
		String[] sa3 = {"o", "a", "A", "H"};
		System.out.println("...................");
		System.out.println(Arrays.compare(sa1, sa2));
		System.out.println(Arrays.compare(sa1, sa3));
		System.out.println(Arrays.compare(sa2, sa3));
		
		
	}
	

//	public static void main(String[] args) {
//		
//		String s1, s2, s3, s4, s5;
//		
//		s1 = "java";
//		s2 = s1.replace('A', 'a'); //Cuando se remplaza un char que no esta en el String regresa el mismo objeto
//		s3 = s1.replace('a', 'a'); //Cuando se remplaza un char por si mismo regresa el mismo objeto, esto aplica solo con el metodo de char
//		s4 = s1.replace("A", "a"); //Cuando se remplaza un String que no esta en el String regresa el mismo objeto, esto porque no hubo cambios en el
//		s5 = s1.replace("a", "a"); //Pese a que cambia el mismo caracter, este método regresa un nuevo String, pues hubo modificaciones en el objeto, aunque sea el mismo contenido
//		
//		System.out.println(s1 == s2);
//		System.out.println(s1 == s3);
//		System.out.println(s1 == s4);
//		System.out.println(s1 == s5);
//		System.out.println("++++++++++++++++++++++");
//		System.out.println(s2 == s1);
//		System.out.println(s2 == s3);
//		System.out.println(s2 == s4);
//		System.out.println(s2 == s5);
//		System.out.println("++++++++++++++++++++++");
//		System.out.println(s3 == s1);
//		System.out.println(s3 == s2);
//		System.out.println(s3 == s4);
//		System.out.println(s3 == s5);
//		System.out.println("++++++++++++++++++++++");
//		System.out.println(s4 == s1);
//		System.out.println(s4 == s2);
//		System.out.println(s4 == s3);
//		System.out.println(s4 == s5);
//		System.out.println("++++++++++++++++++++++");
//		System.out.println(s5 == s1);
//		System.out.println(s5 == s2);
//		System.out.println(s5 == s3);
//		System.out.println(s5 == s4);
//		System.out.println("++++++++++++++++++++++");
//		
//	}
	
//	public static void main(String[] args) {
//		
//		String s1 = "hello"; //se crea en el String pool
//		var v1 = s1.toCharArray();
//		StringBuilder sb1 = new StringBuilder();
//		for (var c : v1) sb1.append(c);
//		String s2 = sb1.toString(); //Regresa un objeto tipo string que esta en el Stack
//		
//		System.out.println(s1 == s2); //No son el mismo objeto
//		System.out.println(s1.equals(s2));  //Son del mismo tipo de instancia y tienen el mismo contenido
//		System.out.println(s1.equals(sb1)); //Tienen el mismo contenido, pero no son del mismo tipo de instancia
//		System.out.println(s1.equals(v1)); //Tienen el mismo contenido, pero no son del mismo tipo de instancia
//		
//		s1 = s1.intern();
//		s2 = s2.intern();
//		System.out.println(s1 == s2); //No son el mismo objeto
//		System.out.println(s1.equals(s2));  //Son del mismo tipo de instancia y tienen el mismo contenido
//	}
	
//	public static void main(String[] args) {		
//
//		String s1 = new String("string");
//		String s2 = new String("string");
//		String s3 = s1;
//		
//		System.out.println(s1.equals(s2) + "    " + (s1 == s2));
//		System.out.println(s1.equals(s3) + "    " + (s1 == s3));
//		System.out.println(s3.equals(s2) + "    " + (s3 == s2));
//		System.out.println("-------------------");
//		
//		s1 = s1.intern();
//		s2 = "string";		
//		s3 = "String".toLowerCase(); //crea un nuevo objeto en el Stack, no en el String pool
//		
//		System.out.println(s1.equals(s2) + "    " + (s1 == s2));
//		System.out.println(s1.equals(s3) + "    " + (s1 == s3));
//		System.out.println(s3.equals(s2) + "    " + (s3 == s2));
//		System.out.println("-------------------");
//		
//		s3 = "String".toLowerCase().intern(); //manda el objeto al String pool en el mismo paso
//		
//		System.out.println(s1.equals(s2) + "    " + (s1 == s2));
//		System.out.println(s1.equals(s3) + "    " + (s1 == s3));
//		System.out.println(s3.equals(s2) + "    " + (s3 == s2));
//		
//		
//		System.out.println("-------------------");
//		s1 = "\t \n";
//		System.out.println("s1: " + s1.isBlank() + "  " + s1.isEmpty());
//		
//		System.out.println("-------------------");
//		s1 = "    Hola \t c a r a\t d e \t b o l a \n";
//		System.out.println("s1:" + s1.strip() + "-" + s1.trim() + "-");
//	}	
}