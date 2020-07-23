package exam_and_curse_java_se_;

public class Question_2_Ex_Guia {
	
	public static void main(String[] args) {
		Super s = new Super(); //Imprime Good Night, Harry
//		Sub s = new Sub();//Imprime Good Morning, Potter
//		SubSub s = new SubSub();//Imprime Good Morning, Harry Potter
		System.out.println(s.greeting() + ", " + s.name());
	}
	
}

class Super {
	static String greeting() {
		return "Good Night";
	}

	String name() {
		return "Harry";
	}
}

class Sub extends Super {
	
	static String greeting() {
		return "Good Morning";
	}
	
	@Override
	String name() {
		return "Potter";
	}
}

class SubSub extends Sub {
	
	@Override
	String name() {
		return "Harry Potter";
	}
}