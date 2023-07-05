package hackerRank.java.easy;

import java.util.Scanner;

public class StringFormatting {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
        System.out.println("================================");
        
        /*
java 100
cpp 65
python 50

hello 0
hackerrank 65
java 10
         */
        for(int i=0;i<3;i++){
            
        	String s1=sc.next();
            int x=sc.nextInt();
            
            //%-15s indica que el string s1 tendrá 15 caracteres de longitud y 
            //	el menos indica que se empezara a escribir de izquierda a derecha
            //%03d indica que el entero debera escribirse con 3 digitos, 
            //	el 0 indica que se siendo los digitos de la izquiera 0's 
            //%n indica un salto de linea
            System.out.printf("%-15s%03d%n",s1,x);
        }
        System.out.println("================================");

	}

}
