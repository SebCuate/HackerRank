package easy;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class CompareTriplets {

    // Complete the compareTriplets function below.
    static List<Integer> compareTriplets(List<Integer> a, List<Integer> b) {
    	
    	a = new ArrayList<>();
    	a.add(2);
    	a.add(3);
    	a.add(4);
    	
    	b = new ArrayList<>();
    	b.add(4);
    	b.add(3);
    	b.add(2);
    	
    	List<Integer> scores = new ArrayList<>();
    	scores.add(0);
    	scores.add(0);
    	
    	for (int i = 0; i < a.size(); i++) {
			if (a.get(i) > b.get(i))
				scores.set(0, scores.get(0) + 1 );
			else if (a.get(i) < b.get(i))
				scores.set(1, scores.get(1) + 1);
		}
    	
    	System.out.println(scores);
    	return scores;
    }

    public static void main(String[] args) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//
//        List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
//            .map(Integer::parseInt)
//            .collect(toList());
//
//        List<Integer> b = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
//            .map(Integer::parseInt)
//            .collect(toList());
    	
        List<Integer> result = compareTriplets(null, null);

//        bufferedWriter.write(
//            result.stream()
//                .map(Object::toString)
//                .collect(joining(" "))
//            + "\n"
//        );
//
//        bufferedReader.close();
//        bufferedWriter.close();
    }
}
