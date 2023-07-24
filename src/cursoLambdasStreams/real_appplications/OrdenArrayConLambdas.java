package cursoLambdasStreams.real_appplications;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

public class OrdenArrayConLambdas {

	public static void main(String[] args) {

		List<Integer> numbers = Arrays.asList(4,1,34,5,6,32,54,6,9,81);
		numbers.sort((a,b) -> a-b); //si a - b es positivo cambia la posicion de a por la de b
		System.out.println(numbers);
		
		List<String> names = Arrays.asList("asd","sdf","dfg","xcv","xbn","xsw","aaa","aa","a");
		names.sort(String::compareTo);
		System.out.println(names);
		names.sort(Comparator.reverseOrder());
		System.out.println(names);
		
		List<PersonEjem> personas = Arrays.asList(
			new PersonEjem("a",31),
			new PersonEjem("b",30),
			new PersonEjem("c",32),
			new PersonEjem("aa",31),
			new PersonEjem("ab",28)
			);
		personas.sort(Comparator.comparingInt(PersonEjem::getAge).thenComparing(PersonEjem::getName));
		System.out.println(personas);
	}

}

@Data
@AllArgsConstructor
class PersonEjem{
	private String name;
	private Integer age;
}