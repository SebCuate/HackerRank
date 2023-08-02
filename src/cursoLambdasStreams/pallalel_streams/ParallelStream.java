package cursoLambdasStreams.pallalel_streams;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import cursoLambdasStreams.util.Database;
import cursoLambdasStreams.util.Videogame;

public class ParallelStream {

    //-D java.util.concurrent.ForkJoinPool.common.parallelism=5
    public static void main(String[] args) {
    	Stream<Videogame> util = Database.videogames.stream();
//    	ejemplo(util);
    	comparacionParallelStream();
    }
    
    private static void ejemplo(Stream<Videogame> stream) {
    	stream.parallel().forEach(
    			n -> System.out.println(n.getName() + " - " + Thread.currentThread().getName())
    			);
	}
    
    private static void comparacionParallelStream() {
    	int limit = 200000000;
    	
    	long startTime = System.currentTimeMillis();
    	IntStream.rangeClosed(1, limit).reduce(0, Integer::sum);
    	long endTime = System.currentTimeMillis();
    	System.out.println("total time stream: " + (endTime-startTime) + " ms");
    	
    	long startTime2 = System.currentTimeMillis();
    	IntStream.rangeClosed(1, limit).parallel().reduce(0, Integer::sum);
    	long endTime2 = System.currentTimeMillis();
    	System.out.println("total time parallel: " + (endTime2-startTime2) + " ms");
    }
}
