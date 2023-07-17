package javaStuff.javaExams.exam_and_curse_java_se_;
public class Question_12_ex_Guia {
	public static void main(String[] args) {
		AnotherClass ac = new AnotherClass();
		SomeClass sc = new AnotherClass();
//		ac = sc; //Requiere un casteo o cambiar el tipo de ac
		sc.methodA();
		ac.methodA();
	}
}

class SomeClass {
	public void methodA() {
		System.out.println("SomeClass#methodA");
	}
}

class AnotherClass extends SomeClass {
	public void methodA() {
		System.out.println("AnotherClass#methodA()");
	}
}