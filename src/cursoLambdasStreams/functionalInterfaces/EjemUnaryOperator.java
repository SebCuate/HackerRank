package cursoLambdasStreams.functionalInterfaces;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

public class EjemUnaryOperator {

	public static void main(String[] args) {
		unaryOperator();
		binaryOperator();
	}
	
	private static void unaryOperator() {
		String util = "holi holi";
		
		Function<String, String> toUpper = String::toUpperCase;
		System.out.println(toUpper.apply(util));
		
		UnaryOperator<String> uToUpper = String::toUpperCase;
		System.out.println(uToUpper.apply(util));
		LongUnaryOperator lUO = n -> n+10;
		System.out.println(lUO.applyAsLong(10l));
		
	}
	
	private static void binaryOperator() {
		String util = "holi holi";
		
		BiFunction<String, String, String> toUpper = String::concat;
		System.out.println(toUpper.apply(util,util));
		
		BinaryOperator<String> uToUpper = String::concat;
		System.out.println(uToUpper.apply(util, util));

	}

}
