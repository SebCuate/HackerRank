package util;

public class LcmGfm {

	public static void main(String[] args) {

		int a = 57;
		int b = 38;

		System.out.println("The GCF of " + a + " and " + b + " is: " + GCF(a, b));
		System.out.println("The LCM of " + a + " and " + b + " is: " + LCM(a, b));

	}

	/**
	 * Greatest Common Factor, must a>b the biggest number than can divide both
	 * number without have a remainder
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static int GCF(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return (GCF(b, a % b));
		}
	}

	/**
	 * Lowest Common Factor, must a>b the Lowest number than can divide both number
	 * without have a remainder
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int LCM(int a, int b) {
		return (a * b) / GCF(a, b);
	}

}
