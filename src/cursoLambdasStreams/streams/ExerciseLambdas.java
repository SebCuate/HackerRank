package cursoLambdasStreams.streams;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cursoLambdasStreams.util.Console;
import cursoLambdasStreams.util.Database;
import cursoLambdasStreams.util.Videogame;

public class ExerciseLambdas {

    public static void main(String[] args) {
        Stream<Videogame> videogames = Database.videogames.stream();
//        System.out.println(exercise1(videogames));
//        exercise2(videogames).count();
//        System.out.println(exercise3(videogames).getTotalSold());
        exercise4(videogames);
    }

    /*
    *Regresar true si el stream contiene al menos un videojuego con numero de ventas mayor a 10
    *y no este en descuento o su precio sea mayor a 9.99 de lo contrario regresar false.
    */
    static Boolean exercise1(Stream<Videogame> stream) {
    	return 
    		stream.filter(v -> v.getTotalSold() > 10)
    			.filter(v -> !v.getIsDiscount() || v.getPrice() < 10)
    			.count() > 0
    			;
    }

    /*
     *Regresar un Stream unicamente con los titulos de todas las consolas que sean de XBOX
     * durante el proceso imprimir los resultados ignorando los repetidos.
     */
    static Stream<String> exercise2(Stream<Videogame> stream) {
        return stream
        		.filter(v -> v.getConsole().toString().equalsIgnoreCase("xbox") 
//        				|| v.getConsole().toString().equalsIgnoreCase("all")
        				)
        		.map(v -> v.getName())
        		.distinct()
        		.peek(System.out::println)
        		;
    }

    /*
     *Regresar el videojuego con el mayor número de ventas
     * unicamente contemplando los primers 10 elementos del stream.
     */
    static Videogame exercise3(Stream<Videogame> stream) {
        return stream.limit(10)
        	.sorted(Comparator.comparingInt(v -> ((Videogame) v).getTotalSold()).reversed())
        	.findFirst()
        	.get()
        	;
    }

    /*
     *Regresar un stream con todos los comentarios de cada review de todos los videojuegos del stream.
     */
    static Stream<String> exercise4(Stream<Videogame> stream) {
        
    	var r = stream.flatMap(v -> v.getReviews().stream()).collect(Collectors.toList());
    	r.forEach(System.out::println);
    	return null;
    }

    /*
     *Regresar un stream con los todos los videojuegos con precio menores a 20.0
     * sin utilizar el operador filter().
     */
    static Stream<Double> exercise5(Stream<Videogame> stream) {
        return null;
    }

}
