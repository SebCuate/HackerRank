package hackerRank.java.easy;

import java.util.Scanner;

public class StaticInitializer {

	static int B;
    static int H;
    static boolean flag;
    
    static{
        Scanner sc = new Scanner(System.in);
        B = sc.nextInt();
        H = sc.nextInt();
        flag = true;
        if(B<=0 || H<=0){
            flag = false;
            System.out.println("java.lang.Exception: Breadth and height must be positive");
        }else
            System.out.println(B*H);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
