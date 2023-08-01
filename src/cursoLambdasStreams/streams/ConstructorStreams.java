package cursoLambdasStreams.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cursoLambdasStreams.util.BasicVideogame;
import cursoLambdasStreams.util.Database;
import cursoLambdasStreams.util.Videogame;

public class ConstructorStreams {

	public static void main(String[] args) {

		
		Stream<Videogame> myStream = Database.videogames.stream();
//		streamUtil(myStream);
//		mapEjemplo(myStream);
//		flatMapEjemplo(myStream);
//		flatMapEjemplo2(myStream);
//		peekForEach(myStream);
//		ordenado(myStream);
		dropTake(myStream);
	}

	private static void streamUtil(Stream<Videogame> myStream) {

		List<Videogame> util = myStream
//			.peek(n -> n.setName("Hola"))
//			.forEach(n -> System.out.println(n.getName()))

//			.anyMatch(v -> v.getTotalSold() > 10000)
//			.allMatch(v -> v.getTotalSold() > 10000)
//			.noneMatch(v -> v.getTotalSold() > 100000)

//			.findAny() //regresa un objeto tipo videojuego
//			.findFirst() // regresa el primer objeto

//			.filter(Videogame::getIsDiscount)
				.filter(v -> !v.getIsDiscount()).filter(v -> v.getPrice() > 25.0)
				.filter(v -> v.getOfficialWebsite().contains("forza"))
//			.map(Videogame::getTotalSold)
//			.reduce(Integer::sum)

//			.max((a,b) -> a.getName().compareTo(b.getName()))

//			.distinct()
				.collect(Collectors.toList())
//			.forEach(n -> System.out.println(n))
		;

//		util.forEach(n -> n.setName("Hola"));
		util.forEach(n -> System.out.println(n));
	}

	private static void mapEjemplo(Stream<Videogame> myStream) {
		
		List<BasicVideogame> util = myStream.map(v -> {
			return BasicVideogame.builder().name(v.getName()).price(v.getPrice()).console(v.getConsole()).build();
		}).collect(Collectors.toList());
		List<String> utilNombres = util.stream().map(v -> {
			return v.getName();
		}).collect(Collectors.toList());
		util.forEach(n -> System.out.println(n));
		utilNombres.forEach(n -> System.out.println(n));
	}

	private static void flatMapEjemplo(Stream<Videogame> myStream) {

		// Nos entrega una lista de listas de Reviews (tipo string)
		// agrupados por los reviews de cada juego
//		var r = myStream.map(v -> v.getReviews()).collect(Collectors.toList());

		// Nos entrega una lista de Reviews (tipo string)
		// sería como iterar las listas y entregarlas en una sola
		// Seria equivalente a usar map y usar el .getName()
		var r = myStream.flatMap(v -> v.getReviews().stream()).collect(Collectors.toList());

		r.forEach(n -> System.out.println(n));
	}

	private static void flatMapEjemplo2(Stream<Videogame> myStream) {

		var r = myStream.flatMap(v -> v.getReviews().stream()).count();

		System.out.println(r);
//		r.forEach(n -> System.out.println(n));
	}

	private static void peekForEach(Stream<Videogame> myStream) {

//		List<Videogame> 
//			util = 
		myStream
			.peek(n -> n.setName("Hola")) //regresa un stream
			.forEach(n -> System.out.println(n.getName())) //no regresa nada
			;  
	}
	
	private static void ordenado(Stream<Videogame> myStream) {
		
		List<Videogame> util = 
			myStream
				.sorted(Comparator.comparingInt(v -> v.getReviews().size()))
				.collect(Collectors.toList())
			;
		util.forEach(n -> System.out.println(n.getReviews().size()));
	}
	
	private static void dropTake(Stream<Videogame> myStream) {
		
		myStream = Database.videogames.stream();
		
		//takeWhile - toma todo
		List<Videogame> util =
			myStream
//				.sorted(Comparator.comparing(v -> v.getName()))
				.sorted(Comparator.comparing(Videogame::getConsole).reversed())
//				.sorted(Comparator.comparing(Videogame::getName))
				.takeWhile(v -> !v.getConsole().toString().equalsIgnoreCase("xbox"))
//				.dropWhile(v -> !v.getConsole().toString().equalsIgnoreCase("xbox"))
//				.takeWhile(v -> v.getName().toLowerCase().charAt(0) != 'm')
//				.dropWhile(v -> v.getName().toLowerCase().charAt(0) != 'h')
//				.takeWhile(v -> !v.getName().toLowerCase().startsWith("g"))
//				.dropWhile(v -> !v.getName().toLowerCase().startsWith("f"))
				.collect(Collectors.toList())
			;
		util.forEach(n -> System.out.println(n));
	}

}
