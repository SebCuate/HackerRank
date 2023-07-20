package cursoLambdasStreams.functionalInterfaces;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class EjemFunction {
	
	public static void main(String[] args) {
		
//		EjemFunctionBiFunction.func();
//		EjemFunctionBiFunction.biFunc();
//		EjemFunction.func2();
		EjemFunction.biFunc2(
				Arrays.asList("a","b","c","d"), 
				Arrays.asList(1, 2, 3, 4),
				(l1, l2) -> l1 + l2
			);
	}
	
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
	
	/*
	 * Metodo para ver serializacion y deserialización de objetos
	 */
	public static void func2() {
		
		Function<Person, ByteArrayOutputStream> serializer = p -> {
			ByteArrayOutputStream inMemoryBytes = new ByteArrayOutputStream();
			try(ObjectOutputStream output = new ObjectOutputStream(inMemoryBytes)) {
				output.writeObject(p);
				output.flush();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			return inMemoryBytes;
		};
		
		Function<ByteArrayInputStream, Person> deserializer = b -> {
			
			try(ObjectInputStream input = new ObjectInputStream(b)) {
				return (Person) input.readObject();
			} catch (IOException | ClassNotFoundException e) {
				System.err.println(e.getMessage());
			}
			return null;
		};
		
		ByteArrayOutputStream objSer = serializer.apply(new Person("Sebas", 32));
		System.out.println(Arrays.toString(objSer.toByteArray()));
		Person objDeser = deserializer.apply(new ByteArrayInputStream(objSer.toByteArray()));
		System.out.println(objDeser.toString());
	}
	
	public static <T,U,R> void biFunc2(
		List<T> list1,
		List<U> list2,
		BiFunction<T, U, R> combiner
	) {
		List<R> result = new ArrayList<R>();
		
		for (int i = 0; i < list1.size(); i++) {
			result.add(combiner.apply(list1.get(i), list2.get(i)));
		}
		System.out.println(result);
	}

}
