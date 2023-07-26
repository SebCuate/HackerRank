package hackerRank.java.hard;

import java.math.BigInteger;
import java.util.function.Predicate;

/**
 * This Java 8 challenge tests your knowledge of Lambda expressions!

Write the following methods that return a lambda expression performing a specified action:

PerformOperation isOdd(): The lambda expression must return true if a number is odd or false if it is even.
PerformOperation isPrime(): The lambda expression must return true if a number is prime or false if it is composite.
PerformOperation isPalindrome(): The lambda expression must return true if a number is a palindrome or false if it is not.
Input Format

Input is handled for you by the locked stub code in your editor.

Output Format

The locked stub code in your editor will print  lines of output.

Sample Input

The first line contains an integer,  (the number of test cases).

The  subsequent lines each describe a test case in the form of  space-separated integers:
The first integer specifies the condition to check for ( for Odd/Even,  for Prime, or  for Palindrome). The second integer denotes the number to be checked.
 * @author SebasCU
 *
 */
public class JavaLambdaExpressions {

	public static void main(String[] args) {
		
		int [][] conditions = {
			{1,4},
			{2,5},
			{3,898},
			{1,3},
			{2,12},
			{3,789987},
			{1,1}
		};
		for (int i = 0; i < conditions.length; i++) {
	        switch (conditions[i][0]) {
	        //Forma 1
	            case 1 -> System.out.println(isOdd.test(conditions[i][1]) ? "ODD" : "EVEN");
	            case 2 -> System.out.println(isPrime.test(conditions[i][1]) ? "PRIME" : "COMPOSITE");
	            case 3 -> System.out.println(isPalindrome.test(conditions[i][1]) ? "PALINDROME" : "NOT PALINDROME");
	        //Forma 2
//            case 1 -> System.out.println(isOdd2().check(conditions[i][1]) ? "ODD" : "EVEN");
//            case 2 -> System.out.println(isPrime2().check(conditions[i][1]) ? "PRIME" : "COMPOSITE");
//            case 3 -> System.out.println(isPalindrome2().check(conditions[i][1]) ? "PALINDROME" : "NOT PALINDROME");
            //Forma 3
//            case 1 -> System.out.println(isOdd3().test(conditions[i][1]) ? "ODD" : "EVEN");
//            case 2 -> System.out.println(isPrime3().test(conditions[i][1]) ? "PRIME" : "COMPOSITE");
//            case 3 -> System.out.println(isPalindrome3().test(conditions[i][1]) ? "PALINDROME" : "NOT PALINDROME");
	        }
	    }

	}
	//Forma 1 Creando solo las lambdas
	static Predicate<Integer> isOdd = (n) -> n % 2 != 0;
	static Predicate<Integer> isPrime = (n) -> {
		for(int i = 3 ; i < n ; i++) {
            if(n % i == 0) return false;
        }
        return true;
	};
	static Predicate<Integer> isPalindrome = (n) -> {
		String nS = n.toString();
		for(int i =0 ; i <= (nS.length()/2); i++) {
            if(nS.charAt(i) != nS.charAt(nS.length()-(i+1))) return false;
        }
        return true;
	};
	
	//Forma 2: Creando mi propia functionalInterface
	@FunctionalInterface
	interface PerformOperation {
	    boolean check(int number);
	}

	static PerformOperation isOdd2() {
	    return number -> number % 2 != 0;
	}

	static PerformOperation isPrime2() {
	    return number -> BigInteger.valueOf(number).isProbablePrime(1);
	}

	static PerformOperation isPalindrome2() {
	    return number -> String.valueOf(number).equals(new StringBuilder(String.valueOf(number)).reverse().toString());
	}
	
	//Forma 3 Usando predicate
	static Predicate<Integer> isOdd3() {
	    return number -> number % 2 != 0;
	}

	static Predicate<Integer> isPrime3() {
	    return number -> BigInteger.valueOf(number).isProbablePrime(1);
	}

	static Predicate<Integer> isPalindrome3() {
	    return number -> String.valueOf(number).equals(new StringBuilder(String.valueOf(number)).reverse().toString());
	}
}
