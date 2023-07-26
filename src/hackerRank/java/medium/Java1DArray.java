package hackerRank.java.medium;

import java.util.Arrays;
import java.util.List;

/**
 * https://www.hackerrank.com/challenges/java-1d-array/problem?isFullScreen=true
 * 
 * Let's play a game on an array! 
 * You're standing at index 0 of an n-element array named game. 
 *
 * From some index i (where 0<=i<=n), you can perform one of the following moves:
 * 
 * Move Backward: If cell i-1 exists and contains a zero, you can walk back to cell i-1.
 * Move Forward:
 * If cell i+1 contains a zero, you can walk to cell i+1.
 * If cell i+leap contains a zero, you can jump to cell i+leap.
 * If you're standing in cell n-1 or the value of i + leap >= n, you can walk or jump off the end of the array and win the game.
 * 
 * @author SebasCU
 *
 */
public class Java1DArray {

    public static void main(String[] args) {
    	
    	int[] leap = {
    			3,
    			5,
    			3,
    			1
    			,41,8,5
    			,65,95
    		};
    	int[][] game = {
    			{0,0,0,0,0} 
    			,{0,0,0,1,1,1} 
    			,{0,0,1,1,1,1,0} 
    			,{0,0,1,0}
    			,{0,0,0,0,0,1,0,1,0}
    			,{0,1,1,0,0,1,0,0,0,1,0,1,1}
    			,{0,0,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,1,0,0,1,0,1,1,0,0,1,0,0,0,1,1,0,1,1,0,1,0,1,1,0,0,1,0,0,0,1,0,0,1,0,1,0,1,1,0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,1}
    			,{0,0,1,0,0,1,0,0,1,1,0,1,1,1,1,0,0}
    			,{0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,1,0,1,1,0,1,0,0,0,0,0,0,1,0,1,1,0,0,1,0,0,1,0,0,1,1,0,1,0,0,1,1,0,1,0,1,0,1,1,0,1,1,0,0,1,0,0,1,0,1,1,0,0,1,1,1,0,1,1,0,0,0,0,0,1,0,1}
    		};   
//    	int[] leap = new int[game.length];
        
    	for(int i = 0; i < leap.length; i++) {
    		System.out.println( (canWin(leap[i], game[i], 0)) ? "YES" : "NO" );
    	}
    }
    
//    public static boolean canWin(int leap, int[] game) {
//
//		for(int i = 0; i < game.length; i++) {	
//			
//			if( (leap+i) >= game.length)
//				return true;
//			else if ((leap + i)<game.length && game[leap + i] == 0) { 
//				game[i] = 1;
//				i += leap-1;
//			}
//			else if((i+1)<game.length && game[i+1] == 0) {
//				game[i] = 1;
//			}
//			else if ((i-1) >= 0 && game[i-1] == 0) { 
//				game[i] = 1;
//				i -= 1;
//			}
//			else
//				return false;
//		}
//		return true;
//    }
	
	private static boolean canWin(int leap, int[] game, int i) {
	    if (i >= game.length) {
	        return true;
	    } else if (i < 0 || game[i] == 1) {
	        return false;
	    }

	    game[i] = 1;

	    return canWin(leap, game, i + leap) ||
	           canWin(leap, game, i + 1) ||
	           canWin(leap, game, i - 1);
	}

}
