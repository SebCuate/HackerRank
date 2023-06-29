package hackerRank.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Happyladybugs {

	static String happyLadybugs(String b) {
		
		b= b.toUpperCase();
		
		boolean flag = false;
		if (b.length() == 1 && b.charAt(0) != '_') {
			return "NO";
		}
		int count = 0;
		
		//DOES IT HAVE A/SOME UNDERSCORE(S)?
		for (int i = 0; i < b.length(); i++) {
			if (b.charAt(i) == '_') {
				flag = true;
				count++;
			}
		}
		
		//DOES IT HAVE ONLY UNDERSCORE(S)?
		if (count == b.length()) {
			return "YES";
			//THERE'S NO A UNHAPPY LADYBUG 
		}
		
		//IF IT HAS DIFERENT CHARS, SO
		//DOES IT HAS AN UNDERSCORE?
		
		//NO
		//CHECK IF THE WORD IS "ORDERED" 
		//AND THERE'S NOT A UNIQUE LADYBUG  
		if (flag == false) {
			for (int i = 1; i < b.length(); i++) {
				if (i == b.length() - 1) {
					if (b.charAt(i) != b.charAt(i - 1)) {
						return "NO";
					}
					return "YES";
				}
				if (b.charAt(i) != b.charAt(i - 1) && b.charAt(i) != b.charAt(i + 1)) {
					return "NO";
				}

			}
			
			//ONLY REQUIERED TO AVOID WARNINGS
			return "YES";
		}
		
		//YES
		//CHECK IF THE WORD THERE'S A UNIQUE LADYBUG  
		else {
			char[] arr = b.toCharArray();
			Arrays.sort(arr);
			if (arr[0] != arr[1]) {
				return "NO";
			}
			for (int i = 1; i < arr.length; i++) {
				//THE UNDERSCORES ARE LOCATE AT THE BUTTOM OF THE WORD
				if (arr[i] == '_') {
					if (arr[i - 1] != arr[i - 2]) {
						return "NO";
					}
					return "YES";
				}
				if (arr[i] != arr[i - 1] && arr[i] != arr[i + 1]) {
					return "NO";
				}

			}
		}
		
		
		//ONLY REQUIERED TO AVOID WARNINGS
		return "YES";

	}

	public static void main(String[] args) {

		String s1 = "RBY_YBR";
		String s2 = "XXYYXXXYYXX";
		String s3 = "::";
		String s4 = "B";
//		String s5 = "";
//		String s6 = "";
//		String s7 = "";
//		String s8 = "";
//		String s9 = "";
//		String s10 = "";

		Map<String, Integer> map = new HashMap<>();
		
		System.out.println(happyLadybugs(s2));

	}

}
