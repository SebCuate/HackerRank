package javaBasics;

public class StringsExercise {

	public static void main(String[] args) {		

		String s1 = new String("string");
		String s2 = new String("string");
		String s3 = s1;
		
		System.out.println(s1.equals(s2) + "    " + (s1 == s2));
		System.out.println(s1.equals(s3) + "    " + (s1 == s3));
		System.out.println(s3.equals(s2) + "    " + (s3 == s2));
		
		System.out.println("-------------------");
		
		s1 = s1.intern();
		s2 = "string";		
		s3 = "String".toLowerCase(); //crea un nuevo objeto en el Stack, no en el String pool
		
		System.out.println(s1.equals(s2) + "    " + (s1 == s2));
		System.out.println(s1.equals(s3) + "    " + (s1 == s3));
		System.out.println(s3.equals(s2) + "    " + (s3 == s2));
		
		System.out.println("-------------------");
		
		s3 = "String".toLowerCase().intern(); //manda el objeto al String pool en el mismo paso
		
		System.out.println(s1.equals(s2) + "    " + (s1 == s2));
		System.out.println(s1.equals(s3) + "    " + (s1 == s3));
		System.out.println(s3.equals(s2) + "    " + (s3 == s2));
		
		System.out.println("-------------------");
		s1 = "\t \n";
		
		System.out.println("s1: " + s1.isBlank() + "  " + s1.isEmpty());
		
		
		
	}
	
}
