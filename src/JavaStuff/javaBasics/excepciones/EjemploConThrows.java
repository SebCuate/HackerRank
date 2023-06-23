package JavaStuff.javaBasics.excepciones;

public class EjemploConThrows {
	public static void main(String[] args)
//			throws Exception //COn este throws el programa compila
	{
		TestClass tc = new TestClass();
		try {
			tc.m1();
		} catch (MyException e) {
			tc.m1();
		} finally {
			tc.m2();
		}
		
		try {
			 if(true);
		}
		finally {
			System.out.println("algo");
		}
	}
}

class MyException extends Exception {
}

class TestClass {
	

	public void m1() throws MyException {
		throw new MyException();
	}

	public void m2() throws RuntimeException {
		throw new NullPointerException();
	}
}