package JavaStuff.javaBasics.strings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Exer_LocalDate {

	public static void main(String[] args) {
		
		LocalDate l1 = LocalDate.of(2019, 1, 1);
		LocalDate l2 = LocalDate.parse("2019-01-01", DateTimeFormatter.ISO_DATE);
		LocalDate l3 = LocalDate.now().withYear(2019).withMonth(1).withDayOfMonth(1);
		
		System.out.println(l1.equals(l2));
		System.out.println(l1.equals(l3));
		System.out.println(l3.equals(l2));
	}
	
}
