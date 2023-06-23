package dataStructures;

import java.util.ArrayList;
import java.util.List;

public class ReverseArray {

	public static void main(String[] args) {
		
		List<Integer> reverse = new ArrayList<>();
		reverse.add(3);
		reverse.add(2);
		reverse.add(5);
		reverse.add(1);
		reverse.add(6);
//		reverse.add(6);
		
		System.out.println(reverseArray(reverse));

	}
	
	public static List<Integer> reverseArray(List<Integer> a) {
        Integer pivot = 0;
        for(int i = 0; i < (a.size()/2)-0.5; i++){
            pivot = a.get(i);
            a.set(i, a.get(a.size()-1-i));
            a.set(a.size()-1-i, pivot);
        }

        return a;
    }

}
