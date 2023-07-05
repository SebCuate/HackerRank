package hackerRank.java.easy;

/**
 * 
 * @author SebasCU
 *
 * We use the integers a, b and n to create the following series:
 *
 *	(a + 2^(n-1) * b)
 *
 * For each query, print the series corresponding to the given a, b and n values as a single line of n space-separated integers.
 */

public class LoopsII {

	public static void main(String[] args) {
		
		solucion(0, 2, 10);
		solucion(5, 3, 5);
		solucion(3, 3, 3);
		solucion(0, 0, 5);
		solucion(6, 6, 10);
	}
	
	private static void solucion(int a, int b, int n){
        
        long lUtil = a + b; 
        
        System.out.print(lUtil);
        for (int i = 1; i < n; i++) {
            lUtil += Math.pow(2, i) * b;
            System.out.print(" " + lUtil); 
        }
        System.out.println();
        
    }

}
