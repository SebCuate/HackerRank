package hackerRank.java.easy;

import java.util.Scanner;

public class JavaBasicsIfElse {

	public static void main(String[] args) {
		
		int N = 18;

		if (N % 2 != 0)
			System.out.println("Weird");
		else if (N <= 5 && N >= 2)
			System.out.println("Not Weird");
		else if (N <= 20 && N >= 6)
			System.out.println("Weird");
		else if (N > 20)
			System.out.println("Not Weird");

	}

}
