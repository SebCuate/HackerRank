package cursoLambdasStreams.collectors;

import java.util.Collection;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cursoLambdasStreams.util.Console;
import cursoLambdasStreams.util.Database;
import cursoLambdasStreams.util.Videogame;

public class CollectorsTo {

	public static void main(String[] args) {

		Stream<Videogame> util = Database.videogames.stream();
		
//		streamToPartitions(util);
//		streamToGroupBySum(util);
//		streamReduceFunctions(util);
		streamJoin(util);
	}
	
	private static Collection<Videogame> streamToCollection(Stream<Videogame> stream, String type) {
		return (type.equalsIgnoreCase("list"))? 
				stream.collect(Collectors.toList()):
				stream.collect(Collectors.toSet());
	}
	
	private static Map<String, Double> streamToMap(Stream<Videogame> stream) {
		return stream.distinct().collect(Collectors.toMap(Videogame::getName, Videogame::getPrice));
	}
	
	private static void streamToPartitions(Stream<Videogame> stream) {
		
		Map<Boolean, List<Videogame>> result = stream.collect(Collectors.partitioningBy(
			v->v.getPrice() > 15
		));
		
		result.forEach((k,v) -> System.out.println(k + " - " + v));
	}
	
	private static void streamToGroupBySum(Stream<Videogame> stream) {
		
		Map<Console, Integer> result = stream.collect(
				Collectors.groupingBy(
						Videogame::getConsole, 
						Collectors.summingInt(Videogame::getTotalSold)
						)
				);
		
		result.forEach((k,v) -> System.out.println(k + " - " + v));
	}
	
	private static void streamReduceFunctions(Stream<Videogame> stream) {
//		Double avgPrice = stream.collect(Collectors.averagingDouble(Videogame::getPrice));
//		System.out.println(avgPrice);
//		String cheaper = stream.collect(Collectors.minBy(Comparator.
//				comparingDouble(v -> ((Videogame) v).getPrice()))).get().getPrice().toString();
//		System.out.println(cheaper);
//		String expensier = stream.collect(Collectors.maxBy(Comparator.
//				comparingDouble(v -> ((Videogame) v).getPrice()))).get().getPrice().toString();
//		System.out.println(expensier);
		
		IntSummaryStatistics summary = stream.collect(Collectors.summarizingInt(
				v -> v.getReviews().size() //reduce a solo los valores de los reviews
				));
		
	}
	
	private static void streamJoin(Stream<Videogame> stream) {
		String string = stream
				.map(Videogame::toString)
				.collect(Collectors.joining("\n"))
				;
		System.out.println(string);
	}

}
