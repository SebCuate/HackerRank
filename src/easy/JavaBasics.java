package easy;

import java.util.Scanner;

public class JavaBasics {

	public static void main(String[] args) {
		
		int N = -2;


		if (N % 2 != 0)
			System.out.println("Wierd");
		else if (N <= 5 && N >= 2)
			System.out.println("Not Wierd");
		else if (N <= 20 && N >= 6)
			System.out.println("Wierd");
		else if (N > 20)
			System.out.println("Not Wierd");

	}

}
