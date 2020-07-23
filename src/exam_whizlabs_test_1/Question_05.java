package exam_whizlabs_test_1;


//Incorrect
//Domain :Programming Abstractly Through Interfaces
public class Question_05 {

}

interface Foo {

	void methodA() { }  //Las llaves hacen que se declare un cuerpo en el método
	static void methodB(); //En una interface, un metodo estatico debe tener cuerpo
	protected void methodC(); //no puede haber metodos protegidos en una interface
	public void methodD();

}

//Which method is valid?
//A. methodA
//
//B. methodB
//
//C. methodC
//
//D. methodD
//
//E. None of the above
//
//Explanation:
//Answer: D
//
//All methods in an interface are public and abstract by default, and an abstract method doesn't have a body. Option A is incorrect, then.
//methodB is static, but a static method must have a body. This means option B is incorrect as well.
//The protected modifier isn't allowed in an interface; thus, option C is also incorrect.
//The public modifier on an interface method is redundant, but adding it doesn't do any harm; hence, option D is the correct answer.