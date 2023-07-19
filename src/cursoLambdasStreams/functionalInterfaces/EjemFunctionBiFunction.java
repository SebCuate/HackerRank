package cursoLambdasStreams.functionalInterfaces;

import java.util.function.BiFunction;
import java.util.function.Function;

public class EjemFunctionBiFunction {
	
	public static void func() {
		Function<Integer, Integer> mult = n -> n*10;
		
		System.out.println(mult.apply(10));
		System.out.println(mult.andThen(n -> n+3).apply(10));
		System.out.println(mult.compose((Integer n) -> n+3).apply(10));
		System.out.println(mult
				.compose((Integer n) -> n+3)//Se ejecuta antes de apply
				.andThen(n -> n+3) //se ejecuta despues de apply
				.apply(10)
			);
	}
	
	public static void biFunc() {
		BiFunction<Integer, Integer, Integer> mult = (n,s) -> n*s;
		
		System.out.println(mult.apply(10, 5));
		System.out.println(
			mult
//				.compose((Integer n) -> n+3)//No existe funcion compose
				.andThen(n -> n+3) //se ejecuta despues de apply
				.apply(10, 5)
		);
	}

}
