package javaStuff.javaExams.exam_whizlabs_test_1;

public class Question_01 {

	public static void main(String[] args) {
		
		int i = 0, j = 5, k;
		
		do{
			k=i;
			System.out.println(i + " - " + j + " - " + k);
		}while(i++ < --j);
		System.out.println(i + " - " + j + " - " + k);
		System.out.println("k = " + k);
			
	}
}
