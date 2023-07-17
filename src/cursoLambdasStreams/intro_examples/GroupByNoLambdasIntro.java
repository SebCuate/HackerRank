package cursoLambdasStreams.intro_examples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cursoLambdasStreams.util.Console;
import cursoLambdasStreams.util.Database;
import cursoLambdasStreams.util.Videogame;

public class GroupByNoLambdasIntro {

    public static void main(String[] args) {
        List<Videogame> videogames = Database.videogames;
        Map<Console, Double> r = new HashMap<>(7);

        for (Console c : Console.values()) {
            double countPrice = 0.0;
            int countTotal = 0;
            for (Videogame v : videogames) {
                if (v.getConsole().equals(c)) {
                    countTotal ++;
                    countPrice += v.getPrice();
                }
            }
            double avg = countPrice / countTotal;
            r.put(c, avg);
        }

        System.out.println(r);
    }
}
