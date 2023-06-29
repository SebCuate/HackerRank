package hackerRank.easy;

import java.util.ArrayList;
import java.util.List;

public class DiagonalDifference {

	public static void main(String[] args) {

		int n = 3;
		List<List<Integer>> matrix = new ArrayList<>();
		
		int sum1 = 0, sum2 = 0;
		
		List<Integer> array = new ArrayList<Integer>();
		array.add(11);
		array.add(2);
		array.add(4);
		matrix.add(array);
		
		array = new ArrayList<Integer>();
		array.add(4);
		array.add(5);
		array.add(6);
		matrix.add(array);
		
		array = new ArrayList<Integer>();
		array.add(10);
		array.add(8);
		array.add(-12);
		matrix.add(array);
		
		n = matrix.size();
		
		for (int i = 0; i < n; i++) {
			
			sum1 += matrix.get(i).get(i);
			sum2 += matrix.get(i).get(n - (i + 1));
		}
		
		if((sum1 - sum2) <= 0 )
			System.out.println((sum2 - sum1));
		else
			System.out.println((sum1 - sum2));

	}

}
