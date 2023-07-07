package hackerRank.problemSolving.easy;

/**
 * 
 * @author SebasCU
 *
 *
 */
public class FizzBuzz {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		fizzBuzz(15);
	}
	
	private static void fizzBuzz(int n) {
        for(int i = 1; i <= n; i++){
            
        	if(i%5 != 0 && i%3 != 0)
                System.out.println(i);
        	else {
        		if(i%3 == 0)
	                System.out.print("Fizz");
	            if(i%5 == 0)
	                System.out.print("Buzz");
	            System.out.println();
        	}
            
        }
    }

}
