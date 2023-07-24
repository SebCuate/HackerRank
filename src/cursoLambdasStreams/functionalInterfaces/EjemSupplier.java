package cursoLambdasStreams.functionalInterfaces;

import java.util.Random;
import java.util.function.Supplier;

public class EjemSupplier {

	public static void main(String[] args) {
		supplier();
	}
	
	private static void supplier() {
		Supplier<Integer> rInt = () -> new Random().nextInt(100);
		
		System.out.println(rInt.get());
		System.out.println(rInt.get());
		System.out.println(rInt.get());
	}

}
