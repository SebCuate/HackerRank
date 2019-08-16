package medium;

import java.util.HashSet;
import java.util.Set;

public class ClimbingLeaderboard {

	// Complete the climbingLeaderboard function below.
    static int[] climbingLeaderboard(int[] scores, int[] alice) {
    	
    	Set<Integer> utilSet = new HashSet<>();
    	
    	for (int util : scores) {
			utilSet.add(util);
		}
    	
    	int[] scores2 = new int[utilSet.size()];
    	
    	
    	
    	return null;
    }
	
	public static void main(String[] args) {
		
		int r = 7;
		int n = 5;
		
		int[] scores = new int[r];
		int[] alice = new int[n];
		
		int[] positions = new int[n];
		
		positions = climbingLeaderboard(scores, alice);
		
		for (int i : positions) {
			System.out.print(i + " ");
		}
		
	}

}
