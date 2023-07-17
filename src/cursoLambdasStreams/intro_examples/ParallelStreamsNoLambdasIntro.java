package cursoLambdasStreams.intro_examples;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cursoLambdasStreams.util.Database;
import cursoLambdasStreams.util.Videogame;

public class ParallelStreamsNoLambdasIntro {

    public static void main(String[] args) {

        List<Videogame> videogames = Database.videogames;
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        for (Videogame v : videogames) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " - " + v);
                }
            });
        }

        threadPool.shutdown();
    }
}
