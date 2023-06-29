package hackerRank.euler;

import java.math.BigInteger;

/**
 * 
 * @author SebasCU
 *
 *         By considering the terms in the Fibonacci sequence whose values do
 *         not exceed N, find the sum of the even-valued terms.
 */
public class No2_EvenFibonacciNumbers {

	public static void main(String[] args) {

		BigInteger suma = BigInteger.ZERO;
		BigInteger limit = BigInteger.valueOf(196419);
        BigInteger num = BigInteger.ZERO;
		for (int i = 1;; i++) {
			num = evenFib(BigInteger.valueOf(i));
			if(num.compareTo(limit) > -1)
				break;
			suma = suma.add(num);
		}
	    System.out.println(suma);
	}

	public static BigInteger evenFib(BigInteger n) {

        // CASO BASE, si es cero devuelve un cero
        // Puedes poner n<=0 tambien para incluir negativos
        if (n.compareTo(BigInteger.ONE) < 0)
            return n;

        if (n.compareTo(BigInteger.ONE) == 0)
            return BigInteger.valueOf(2);

        // calculation of
        // Fn = 4*(Fn-1) + Fn-2
        return ((evenFib(n.subtract(BigInteger.ONE)).multiply(BigInteger.valueOf(4))).add(evenFib(n.subtract(BigInteger.valueOf(2)))));
    }

}
