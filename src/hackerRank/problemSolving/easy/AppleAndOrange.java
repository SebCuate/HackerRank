package hackerRank.problemSolving.easy;

import java.util.List;

/**
 * https://www.hackerrank.com/challenges/apple-and-orange/problem?isFullScreen=true
 * @author SebasCU
 *
 *
 * Sam's house has an apple tree and an orange tree that yield an abundance of fruit. 
 * Using the information given below, determine the number of apples and oranges that land on Sam's house.

 * In the diagram below:

 * The red region denotes the house, where s is the start point, and t is the endpoint. 
   The apple tree is to the left of the house, and the orange tree is to its right.
 * 
 * Assume the trees are located on a single point, where the apple tree is at point a, and the orange tree is at point b.
 * 
 * When a fruit falls from its tree, it lands d units of distance from its tree of origin along the -axis. 
 * *A negative value of d means the fruit fell  units to the tree's left, 
 * *A positive value of d means it falls d units to the tree's right. *
 *
 *
 * Given the value of d for m apples and n oranges, determine how many apples and oranges will fall on Sam's house (i.e., in the inclusive range [s,t])?
 */
public class AppleAndOrange {

	public static void main(String[] args) {
		
		
		//Where's the house is
		int s = 7;
        int t = 11;
        //Apple tree location
        int a = 5;
        //Orange tree location
        int b = 15;
        
        List<Integer> apples = List.of(-2, 2, 1);
        List<Integer> oranges = List.of(5, -6);

		countApplesAndOranges(s, t, a, b, apples, oranges);
	}

	/*
     * Complete the 'countApplesAndOranges' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER s
     *  2. INTEGER t
     *  3. INTEGER a
     *  4. INTEGER b
     *  5. INTEGER_ARRAY apples
     *  6. INTEGER_ARRAY oranges
     */

    private static void countApplesAndOranges(int s, int t, int a, int b, List<Integer> apples, List<Integer> oranges) {
    // Write your code here
    	
    	int noApples = 0;
    	int noOranges = 0;
    	
    	for (Integer integer : apples)
			if((a + integer) >= s && (a + integer) <= t )
				noApples++;
		
    	for (Integer integer : oranges)
    		if((b + integer) >= s && (b + integer) <= t )
				noOranges++;
		

    	System.out.println(noApples);
    	System.out.println(noOranges);
    }
}
