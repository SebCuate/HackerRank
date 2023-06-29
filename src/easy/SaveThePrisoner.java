package easy;

public class SaveThePrisoner {
	
	static int saveThePrisoner(int n, int m, int s) {

		int _util = s + m - 1;
		
		if (_util > n) {
			if (_util % n == 0) {
				return n;
			}
			return _util % n;
		}

		return _util;

	}

	public static void main(String[] args) {

//		5 2 1
//		5 2 2
//		7 19 2
//		3 7 3
		int n = 7; // prisioneros
		int m = 19; // dulces
		int s = 2; // inicio

		int result = saveThePrisoner(n, m, s);

		System.out.println(result);

	}

}
