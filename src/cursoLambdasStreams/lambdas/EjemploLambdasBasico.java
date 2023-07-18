package cursoLambdasStreams.lambdas;

import java.util.List;

public class EjemploLambdasBasico {

	public static void EjemploLambdas() {
		List<String> countries = List.of("Mexico", "Colombia", "Argentina", "Uruguay");
		
		for(String c: countries)
			System.out.println(c);
		
		countries.forEach(c -> System.out.println(c));
	}
}
