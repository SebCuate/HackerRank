package cursoLambdasStreams.intro_examples;

import java.util.List;

import cursoLambdasStreams.util.Database;
import cursoLambdasStreams.util.Videogame;

public class PipelineIntro {

    public static void main(String[] args) {
        List<Videogame> videogames = Database.videogames;
        videogames.stream()
                .distinct()
                .filter(v -> v.getTotalSold() > 10)
                .map(v -> v.getName().toUpperCase())
                .forEach(System.out::println);
    }
}
