package cursoLambdasStreams.functionalInterfaces;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EjemConsumer {

	public static void main(String[] args) {
		
		consumer();
		
	}
	
	private static void consumer() {
		Set<Integer> nums = Set.of(1,2,3,4,5,6,7,8,9);
		List<Integer> squares = new LinkedList<>();
		
		nums.forEach(n -> squares.add(n*n));
		System.out.println(squares);
	}
	
	private static void biConsumer() {
		Set<Integer> nums = Set.of(1,2,3,4,5,6,7,8,9);
		List<Integer> squares = new LinkedList<>();
		Map<String, String> map = Map.of("1","1","2","2","3","3");
		
		nums.forEach(n -> squares.add(n*n));
		System.out.println(squares);
	}


}
