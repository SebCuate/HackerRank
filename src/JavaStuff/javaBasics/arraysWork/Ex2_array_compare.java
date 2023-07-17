package javaStuff.javaBasics.arraysWork;

import java.util.Arrays;
import java.util.List;

public class Ex2_array_compare {

	public static void main(String[] args) {

		int[] ia1 = new int[]{1,2,3,4,5,6,7,8,9,10};
		int[] ia2 = {1,2,3,4,5,6};
		int[] ia3 =  new int[7];
		for (int i = 0; i < ia3.length; i++) {
			ia3[i] = i+1; 
		}
		ia3[6] = 10;
		
		System.out.println(Arrays.compare(ia1, ia2));
		System.out.println(Arrays.mismatch(ia1, ia2));
		System.out.println(Arrays.compare(ia1, ia3));
		System.out.println(Arrays.compare(ia2, ia3));
		
		List li1 = List.of(ia1);
		List li2 = List.of(ia2);
		List li3 = List.of(ia3);
		
		li1.set(0, 3);
		
		System.out.println("------------------------");
		System.out.println(Arrays.compare(ia1, ia2));
		System.out.println(Arrays.compare(ia1, ia3));
		System.out.println(Arrays.compare(ia2, ia3));
		
	}

}
