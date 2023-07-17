package javaStuff.javaExams.exam_whizlabs_test_1;

//Domain :Working with Java Arrays
public class Question_03 {

	public static void main(String[] args) {
		String[][] strings = { { "a", "d" }, { "b", "e" }, { "c", "f" } };

		for (int i = 0; i < strings.length; i++) {
			for (int j = 0; j < strings[i].length; j++) {
				System.out.print(strings[i][j] + " ");
			}
			System.out.println();
		}
	}
	
}
//
//What is the output when executing the given code?
//
//a b c
//d e f
//
//a b
//c d
//e f
//
//a d
//b e
//c f
//
//An ArrayIndexOutOfBoundsException is thrown
//
//Compilation fails
//
//Explanation:
//Answer: C
//
//The strings variable references a two-dimensional array. The outer array contains three one-dimensional arrays - {"a", "d"}, {"b", "e"} and {"c", "f"}. 
//The elements of these arrays are printed one by one by the for statements.
