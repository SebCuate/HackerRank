package cursoLambdasStreams.intro_examples;

import cursoLambdasStreams.util.Database;

public class ParallelStreamIntro {

    public static void main(String[] args) {
        Database.videogames.parallelStream()
                .forEach(v -> System.out.println(Thread.currentThread().getName() + " - " + v));
    }
}
