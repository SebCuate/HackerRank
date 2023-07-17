package cursoLambdasStreams.intro_examples;

import java.util.ArrayList;
import java.util.List;

import cursoLambdasStreams.util.Database;
import cursoLambdasStreams.util.Videogame;

public class PipelineNoLambdasIntro {

    public static void main(String[] args) {
        List<Videogame> videogames = Database.videogames;

        List<String> r = new ArrayList<>(20);

        for (Videogame v: videogames) {
            if (!r.contains(v.getName())) {
                if (v.getPrice() > 10) {
                    r.add(v.getName().toUpperCase());
                }
            }
        }
        for (String videogameTitle: r) {
            System.out.println(videogameTitle);
        }
    }
}
