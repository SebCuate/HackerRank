package cursoLambdasStreams.streams;

import java.util.function.IntBinaryOperator;
import java.util.stream.Stream;

import cursoLambdasStreams.util.Database;
import cursoLambdasStreams.util.Videogame;

public class ConstructorStreams {

	public static void main(String[] args) {
		
		IntBinaryOperator suma = (a,b) -> a+b;
		
		Stream<Videogame> myStream = Database.videogames.stream();
		
//		System.out.println(myStream.count());
//		myStream.forEach(n -> System.out.println(n.getName()));
		myStream
			.peek(n -> n.setName("Hola"))
			.forEach(n -> System.out.println(n.getName()))
		;
	}
	
	@FunctionalInterface
	interface operadora{
		Double excecute(Double a, Double b);
	    default Double sum(Double a, Double b) { return  a + b; }
	}
	
	static operadora metodo1(int n) {
		return (a, b) -> Double.valueOf(0);
	}

}
