package hackerRank.problemSolving.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SuperReducedString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String util = "ajjjjaaiqowlllsissislajasdknassanccnncc";
		System.out.println(util);
		System.out.println(superReducedString(util));
		System.out.println(superReducedString2(util));
	}
	
	private static String superReducedString(String s) {
	    // Write your code here
//		String result = new String(s);
		ArrayList<String> sUtil = new ArrayList<>(Arrays.asList(s.split("")));
		
		for (int i = 0; i < sUtil.size() - 1; i++) {
//			System.out.println(i + " - " +sUtil.size() + " - " + sUtil.get(i) + " - " + sUtil.get(i + 1));
			if(sUtil.get(i).contentEquals(sUtil.get(i + 1))) {
//				System.out.println(i + " - " +sUtil.size() + " - " + sUtil.get(i) + " - " + sUtil.get(i+1));
				sUtil.remove(i+1);
				sUtil.remove(i);
				i = -1 ;
				System.out.println(sUtil);
			}
		}

		String result = sUtil.stream().map(Object::toString)
                .collect(Collectors.joining(""));
		if(result.length() == 0)
			return "Empty String";
		else
			return result;
	}
	
	private static String superReducedString2(String s) {
		String regex = "([a-z])\\1";
	    String result;
	    System.out.println(s);
	    while(true){
             result = s.replaceFirst(regex, "");
             s = result.replaceFirst(regex, "");
             System.out.println(result);
             if (result.equals(s)){
                 break;
             }
        }
        if (result.isEmpty()){
            result = "Empty String";
        }
        return result;
	}

}
