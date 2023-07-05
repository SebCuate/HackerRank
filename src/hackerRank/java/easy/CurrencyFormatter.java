package hackerRank.java.easy;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {

	public static void main(String[] args) {
		
		double payment = 12324.134;
		Locale INDIA = new Locale("en","IN");
		Locale MEXICO = new Locale("es","MX");
	    String us = NumberFormat.getCurrencyInstance(Locale.US).format(payment);
	    String india = NumberFormat.getCurrencyInstance(INDIA).format(payment);
	    String china = NumberFormat.getCurrencyInstance(Locale.CHINA).format(payment);
	    String france = NumberFormat.getCurrencyInstance(Locale.FRANCE).format(payment);
	    String mexico = NumberFormat.getCurrencyInstance(MEXICO).format(payment);
	    
	    System.out.println("US: " + us);

	    System.out.println("India: " + india);

	    System.out.println("China: " + china);

	    System.out.println("France: " + france);
	    
	    System.out.println("México: " + mexico);

	}

}
