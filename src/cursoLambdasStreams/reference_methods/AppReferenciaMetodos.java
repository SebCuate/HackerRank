package cursoLambdasStreams.reference_methods;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class AppReferenciaMetodos {

	public static void main(String[] args) {
		
//		porObjeto();
//		estatico();
//		porConstructor();
		arbitrario();
	}
	
	private static void porObjeto() {
		List<Integer> numbers = new ArrayList<>();
		IntStream repeat = IntStream.range(1, 15);
		
//		Método referenciado: por objeto
//		repeat.forEach(i -> numbers.add(i));
		repeat.forEach(numbers::add);
		
		System.out.println(numbers);
	}
	
	private static void estatico() {
		
//		Supplier<UUID> getToken = () -> UUID.randomUUID();
		Supplier<UUID> getToken = UUID::randomUUID;
		
		System.out.println(getToken.get());
	}
		
	private static void porConstructor() {
		
//		Supplier<MyObjectRefMet> mO = () -> new MyObjectRefMet();
		Supplier<MyObjectRefMet> mO = MyObjectRefMet::new;
		
		System.out.println(mO.toString());
	}
	
	private static void arbitrario() {
//		BiPredicate<String, String> equals = (s1, s2) -> s1.equals(s2);
		BiPredicate<String, String> equals = String::equals;
		
		System.out.println(equals.test("Hola", "Holi"));
		
	}

}
