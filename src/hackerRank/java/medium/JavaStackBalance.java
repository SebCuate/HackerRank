package hackerRank.java.medium;

import java.util.Stack;

/**
 * A string containing only parentheses is balanced if the following is true: 1. if it is an empty string 2. if A and B are correct, AB is correct, 3. if A is correct, (A) and {A} and [A] are also correct.
 * 
 * Examples of some correctly balanced strings are: "{}()", "[{()}]", "({()})"
 * 
 * Examples of some unbalanced strings are: "{}(", "({)}", "[[", "}{" etc.
 * 
 * Given a string, determine if it is balanced or not.
 * 
 * @author SebasCU
 *
 */
public class JavaStackBalance {

	public static void main(String[] args) {

		String[] s = {
				"{}()",
				"({()})",
				"{}(",
				"[]",
				"[][][][[]][[]]",
				"})({"
				};
		for (int i = 0; i < s.length; i++)
			System.out.println(valid(s[i]));
	}

	public static boolean valid(String s){
	    Stack <Character> st = new Stack<>();
	    
	    int i=0;
	    while(i<s.length()){
	        if(s.charAt(i)=='{'|| s.charAt(i)=='('|| s.charAt(i)=='['){
	            st.push(s.charAt(i));
	        }
	        else{
	            if(st.isEmpty()){
	                return false;
	            }
	            else{
	                if((st.peek()=='{'&& s.charAt(i)=='}')||(st.peek()=='['&& s.charAt(i)==']')|| (st.peek()=='('&& s.charAt(i)==')')){
	                    st.pop();
	                }
	                else{
	                    return false;
	                }
	            }
	        }
	        i++;
	    }
	    return st.isEmpty();
	}

}
