package cursoLambdasStreams.intro_examples;

import java.util.stream.Collectors;

import cursoLambdasStreams.util.Database;
import cursoLambdasStreams.util.Videogame;

public class GroupByIntro {

    public static void main(String[] args) {
        System.out.println(
                Database.videogames
                        .stream()
                        .collect(
                                Collectors.groupingBy(Videogame::getConsole,
                                Collectors.summarizingDouble(Videogame::getPrice)))
        );
    }
}
