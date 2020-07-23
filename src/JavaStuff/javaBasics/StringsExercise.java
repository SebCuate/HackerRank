package JavaStuff.javaBasics;

public class StringsExercise {

	public static void main(String[] args) {
		
		String s1 = "hello"; //se crea en el String pool
		var v1 = s1.toCharArray();
		
		
	}
	
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