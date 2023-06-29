package hackerRank.euler;

import java.math.BigInteger;

/**
 * @author SebasCU
 * If we list all the natural numbers below  that are multiples of  or , we get  and . The sum of these multiples is .
 * Find the sum of all the multiples of 3 or 5 below N.
 */
public class No1_MultiplesOf_3_and_5 {

	public static void main(String[] args) {

		BigInteger n = BigInteger.valueOf(Integer.MAX_VALUE);
		solution(n);
		
	}
	
	private static void solution(BigInteger limit) {
        BigInteger result = BigInteger.ZERO;
        limit = limit.subtract(BigInteger.ONE);
        
        result = result.add(
        		(limit.divide(BigInteger.valueOf(3)))
        		.multiply(
        			(limit.divide(BigInteger.valueOf(3)))
        			.add(BigInteger.valueOf(1))
        		)
        		.divide(BigInteger.valueOf(2))
        		.multiply(BigInteger.valueOf(3))
        );
        result = result.add(
        		(limit.divide(BigInteger.valueOf(5)))
        		.multiply(
        			(limit.divide(BigInteger.valueOf(5)))
        			.add(BigInteger.valueOf(1))
        		)
        		.divide(BigInteger.valueOf(2))
        		.multiply(BigInteger.valueOf(5))
        );
        result = result.subtract(
        		(limit.divide(BigInteger.valueOf(15)))
        		.multiply(
        			(limit.divide(BigInteger.valueOf(15)))
        			.add(BigInteger.valueOf(1))
        		)
        		.divide(BigInteger.valueOf(2))
        		.multiply(BigInteger.valueOf(15))
        );
//        result += 3*((limit/3)*(limit/3 + 1))/2;
//        result += 5*((limit/5)*(limit/5 + 1))/2;
//        result -= 15*((limit/15)*(limit/15 + 1))/2;
        
        System.out.println(result);
    }

}
