package medium;

import java.util.HashSet;
import java.util.Set;
import static java.util.Arrays.binarySearch;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.of;

/**
 * This exercises has 3 cheating rules in its design
 * 1.- The scores are ordered on decrease order
 * 2.- Alice scores are ordered on increase order
 * 3.- "Players who have equal scores receive the same ranking number, and the next player(s) receive the immediately following ranking number."
 * Also
 * the rule of the position is not 
 * @author Sebastian Cuatepotzo
 *
 */
public class ClimbingLeaderboard {

	public static int[] climbingLeaderboard(int[] scores, int[] alice) {
		
		//Get an Array with all uniques values
		int[] array = of(scores).distinct().toArray();
		
		//Create an array to save the results
		int[] results = new int[alice.length];
		
		//The position in the leader board has array.length-1  
		int i = array.length-1;
	    
		//Iterate Alice scores array to get the position
		for (int j = 0; j < alice.length; j++) {
				//get the new position
		        while(i>=0) {
		            if(alice[j]>array[i]) 
		            	i--; //Approaching the cheat in the design of this exercise
		            else {
		            	results[j] = i+2; //This is part of the logic
		            	break; //I got the position, just break the code
		            }
		        }
		        if(i<0) 
		        	results[j] = 1;
		}
		
		return results;
	} 
	
	public static void main(String[] args) {
		
		int r = 7;
		int n = 5;
		
		int[] scores = {100,100,90,80,80,60};
		int[] alice = {20,50,80,100,120};
		int[] results = new int[alice.length];
		
		
		results = climbingLeaderboard(scores, alice);
		
		for (int i = 0; i < results.length; i++) {
			System.out.println(results[i]);
		}
		
	}

}
