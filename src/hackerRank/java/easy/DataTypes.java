package hackerRank.java.easy;

public class DataTypes {

	public static void main(String[] args) {
		
		try{
            long x=1;
            System.out.println(x+" can be fitted in:");
            if(x>=-128 && x<=127)System.out.println("* byte");
            //Complete the code
            if(x < Short.MAX_VALUE && x > Short.MIN_VALUE)
    			System.out.println("* short");
    		if(x < Integer.MAX_VALUE && x > Integer.MIN_VALUE)
    			System.out.println("* int");
    		if(x < Long.MAX_VALUE && x > Long.MIN_VALUE)
    			System.out.println("* long");
        }
        catch(Exception e)
        {
            System.out.println(" can't be fitted anywhere.");
        }
	}
	
}
