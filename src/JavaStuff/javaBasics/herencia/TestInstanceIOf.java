package JavaStuff.javaBasics.herencia;

public class TestInstanceIOf {
	public static void main(String args[]) {
		Dog1 d = new Dog1();
		System.out.println(d instanceof Animal);// true
	}
}

class Animal {
}

class Dog1 extends Animal {// Dog inherits Animal
}