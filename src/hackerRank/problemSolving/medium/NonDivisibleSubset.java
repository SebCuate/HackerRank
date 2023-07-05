package hackerRank.problemSolving.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author SebasCU
 *
 * Given a set of distinct integers, print the size of a maximal subset of S where the sum of any 2 numbers in S' is not evenly divisible by k.
 * Given a set of distinct integers S of size n, we have to print the size of a maximal subset of S where the sum of any 2 numbers in S` is not evenly divisible by k.
 * https://medium.com/@mrunankmistry52/non-divisible-subset-problem-comprehensive-explanation-c878a752f057
 */
public class NonDivisibleSubset {

	public static void main(String[] args) {
		
//		int k = 4;
//		List<Integer> s = Arrays.asList(19, 10, 12, 10, 24, 25, 22);
//		Expected 3
		
//		int k = 6;
//		List<Integer> s = Arrays.asList( 12 ,6 ,1, 9, 13, 15, 10, 21, 14, 32, 5, 8, 23, 19);
//		int[] s = {12 ,6 ,1, 9, 13, 15, 10, 21, 14, 32, 5, 8, 23, 19};
//		Expected 8
		
		int k = 7;
		List<Integer> s = Arrays.asList(278, 576, 496, 727, 410, 124, 338, 149, 209, 702, 282, 718, 771, 575, 436);
//		Expected 11
		
		nonDivisibleSubset(k, s);

	}

	 /*
     * Complete the 'nonDivisibleSubset' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER k
     *  2. INTEGER_ARRAY s
     */
    private static int nonDivisibleSubset(int k, List<Integer> s) {
		
    	List<Integer> count = new ArrayList<>();
    	int result = 0;
    	
    	for (int i = 0; i < k; i++) {
			count.add(0);
		}
    	//Obtenemos los residuos de los numeros al ser divididos por k
    	for(int i=0; i<s.size(); i++){
    		count.set(s.get(i)%k, count.get(s.get(i)%k) + 1);
        }
    	
//    	System.out.println(count);
    	if (count.get(0)>0)
            result++;
    	
    	for(int i=1; i<=k/2; i++){
    		
            if(i == (k-i) %k)  
            	result += Math.min(count.get(i),1);
            else 
            	result += Math.max(count.get(i),count.get(k-i));
            
        }
//    	System.out.println(result);
    	return result;
    }
    
    static int nonDivisibleSubset(int k, int[] arr) {
        int[] remains=new int[k];
        int result=0;
        
        for (int i=0;i<arr.length;i++){
            remains[arr[i]%k]++;
        }
        
        for (int i=0;i<remains.length;i++){
            System.out.print(remains[i] + " ");
        }
        
        if (remains[0]>0)
            result++;
        
        for (int i=1;i<remains.length;i++){
            if(i==(k-i)){
                result++;
            }else {
                if (remains[i]>=remains[k-i]){
                    result+=remains[i];
                }else {
                    result+=remains[k-i];
                }
                remains[i]=0;
                remains[k-i]=0;
            }
        }
        System.out.println(result);
        return result;
    }
	
}
