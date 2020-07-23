package exam_whizlabs_test_1;

public class Question_04 {
	public static void main(String[] args) {
	}
}

class SuperTest {
	public Object myMethod(Object... args) {
		return args;
		// A valid body
	}
}

class Test extends SuperTest {
	// Method 1
	public Object myMethod(String... args) {
		return args;
		// A valid body
	}
	// Method 2
	public Object myMethod(Integer[] args) {
		return args;
		// A valid body
	}
	// Method 3
	public Object myMethod(Object arg) {
		return arg;
		// A valid body
	}
	// Method 4
	public String myMethod(Object[] args) {
		return null;
		// A valid body
	}
}

//Which method in the Test class doesn't overload the only method in the SuperTest?
//Method 1
//Method 2
//Method 3
//Method 4
//None of the above
//
//Answer: D
//The first three methods of the <em>Test</em> class don't have the same parameters as the method in the <em>SuperTest</em> class, hence they don't override.
//Notice varargs is just syntactic sugar for arrays, hence method 4 has the same signature as the method in the super-class. In fact, it overrides the super-class's method.