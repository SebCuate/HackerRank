package cursoLambdasStreams.functionalInterfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class EjemPredicate {

	public static void main(String[] args) {
//		predicate();
		biPredicate();
	}
	
	private static void predicate() {
		List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 10, 48, 49, 50, 100));
		
		int limInf = 3;
		int limSup = 49;
		Predicate<Integer> greaterThan = n -> n >limSup;
		Predicate<Integer> lessThan = n -> n <limInf;
		Predicate<Integer> between = greaterThan.or(lessThan);
		
//		numbers.removeIf(greaterThan);
//		numbers.removeIf(lessThan)
		numbers.removeIf(between.negate());
		System.out.println(numbers);
	}
	
	private static void biPredicate() {
		
		String util = "Hello World";
		String util1 = "Hello ";
		String util2 = "World";
		BiPredicate<String, String> myEquals = (s1, s2) -> s1.concat(s2).equals(util);
		
		System.out.println(myEquals.test(util1, util2));
	}
	
}
