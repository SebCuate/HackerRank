package JavaStuff.javaExams.exam_and_curse_java_se_;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Question_6_extan_1ZO {

	public static void main(String[] args) {
		try {
			doA();
			doB();
		} catch (IOException e) {
			System.out.print("c");
			return;
		} catch (RuntimeException e) {
			System.out.print("g");
		} 
		finally {
			System.out.print("d");
		}
		System.out.print("f");
	}

	public static void doA() throws IndexOutOfBoundsException{
		System.out.print("a");
		if (true) {
			throw new IndexOutOfBoundsException();
		}
	}

	private static void doB() throws FileNotFoundException {
		System.out.print("b");
		if (true) {
			throw new FileNotFoundException();
		}
	}

}
