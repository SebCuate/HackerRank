package util;

import java.math.BigInteger;

public class Fibonacci {

	public static void main(String[] args) {

		int limite = 11;
//		for (int i = 1; i < limite; i++) {
//			System.out.print(fibonacciRecursivo(i) + " ");
//			System.out.print(evenFib(BigInteger.valueOf(i)) + " ");
//		}
		fibonacciIterativo(limite);
	}

	private static int fibonacciIterativo(int noIteraciones) {

		int num1 = 0, num2 = 1, suma = 1;

		for (int i = 1; i < noIteraciones; i++) {

			// muestro la suma
			System.out.print(suma + " ");

			// primero sumamos
			suma = num1 + num2;
			// Despues, cambiamos la segunda variable por la primera
			num1 = num2;
			// Por ultimo, cambiamos la suma por la segunda variable
			num2 = suma;
		}
		
		return suma;
	}

	private static int fibonacciRecursivo(int n) {

		// CASO BASE, si es cero devuelve un cero
		// Puedes poner n<=0 tamvien para incluir negativos
		if (n == 0) {
			return 0;
			// CASO BASE, si es 1 devuelve un 1
		} else if (n == 1) {
			return 1;
		} else {
			// Hago la suma
			return fibonacciRecursivo(n - 1) + fibonacciRecursivo(n - 2);
		}
	}

	private static BigInteger evenFib(BigInteger n) {

//		// CASO BASE, si es cero devuelve un cero
//		// Puedes poner n<=0 tambien para incluir negativos
//		if (n < 1)
//			return n;
//
//		if (n == 1)
//			return 2;
//
//		// calculation of
//		// Fn = 4*(Fn-1) + Fn-2
//		return ((4 * evenFib(n - 1)) + evenFib(n - 2));

		// CASO BASE, si es cero devuelve un cero
		// Puedes poner n<=0 tambien para incluir negativos
		if (n.compareTo(BigInteger.ONE) < 0)
			return n;

		if (n.compareTo(BigInteger.ONE) == 0)
			return BigInteger.valueOf(2);

		// calculation of
		// Fn = 4*(Fn-1) + Fn-2
		return (
				(evenFib(
						n.subtract(BigInteger.ONE))
						.multiply(BigInteger.valueOf(4)
					)
				)
				.add(evenFib(n.subtract(BigInteger.valueOf(2)))));
	}

}
