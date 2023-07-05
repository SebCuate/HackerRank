package hackerRank.java.easy;

import java.util.Scanner;

public class JavaStdinStdout_II {

	public static void main(String[] args) {
	
		Scanner scan = new Scanner(System.in);
	    int i = scan.nextInt();
	    double d = scan.nextDouble();
	    scan.nextLine(); //para poder avanzar a la siguiente linea debemos hacer un nextLine()
		String s = scan.nextLine();
	    
/*
42
3.1415
Welcome to HackerRank's Java tutorials!
 */

	    System.out.println("String: " + s);
	    System.out.println("Double: " + d);
	    System.out.println("Int: " + i);
	    
	}	
	
}
