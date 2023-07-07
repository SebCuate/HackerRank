package hackerRank.problemSolving.easy;

/**
 * 
 * @author SebasCU
 *
 *There is a string, s, of lowercase English letters that is repeated infinitely many times. 
 *Given an integer, n, find and print the number of letter a's in the first n letters of the infinite string.
 *
 */
public class RepeatedString {

	public static void main(String[] args) {
		
		System.out.println(
			repeatedString("aba", 10l)
		);

	}

	private static long repeatedString(String s, long n) {
	    // Write your code here
		long result = 0;
		
		for (int i = 0; i < s.length(); i++) {
			if(s.split("")[i].equalsIgnoreCase("a")) {
//				System.out.println(s.split("")[i]);
				result++;
			}
		}		
		result = result * (n / s.length());
		
		for (int i = 0; i < (n % s.length()); i++) {
			if(s.split("")[i].equalsIgnoreCase("a")) {
//				System.out.println(s.split("")[i]);
				result++;
			}
		}
		
		return result;
    }
	
}
