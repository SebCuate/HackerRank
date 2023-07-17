package javaStuff.lambdas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Excersice2_Car_Predicate {

	String company;
	int year;
	double price;
	String type;

	public Excersice2_Car_Predicate(String c, int y, double p, String t) {
		this.company = c;
		this.year = y;
		this.price = p;
		this.type = t;
	}

	public String toString() {
		return "(" + company + " " + year + ")";
	}

	public static void main(String[] args) {
//		CarMall2 cm = new CarMall2();
//        List<Excersice2_Car_Predicate> carsByCompany = cm.showCars2((Excersice2_Car_Predicate c) -> {
//        	return c.company.equals("Honda") && c.price > 10000;
//        } );
//        System.out.println(carsByCompany);
		
//		CarMall2 cm = new CarMall2();
//		CarFilter2 cf = new CompanyFilter2("Honda");
//		List<Excersice2_Car_Predicate> carsByCompany = cm.showCars2(cf);
//		System.out.println(carsByCompany);
	}
	
}

//interface CarFilter2 {
//	boolean showCar(Excersice2_Car_Predicate c);
//}

interface CarFilter2<T> {
	boolean showCar2(T t);
}

class CarMall2 {
	List<Excersice2_Car_Predicate> cars = new ArrayList<>();

	CarMall2() {
		cars.add(new Excersice2_Car_Predicate("Honda", 2012, 9000.0, "HATCH"));
		cars.add(new Excersice2_Car_Predicate("Honda", 2018, 17000.0, "SEDAN"));
		cars.add(new Excersice2_Car_Predicate("Toyota", 2014, 19000.0, "SUV"));
		cars.add(new Excersice2_Car_Predicate("Ford", 2014, 13000.0, "SPORTS"));
		cars.add(new Excersice2_Car_Predicate("Nissan", 2017, 8000.0, "SUV"));
	}

	List<Excersice2_Car_Predicate> showCars2(CarFilter2<Excersice2_Car_Predicate> cp){
        ArrayList<Excersice2_Car_Predicate> carsToShow = new ArrayList<>();
        for(Excersice2_Car_Predicate c : cars){
//            if(cp.test(c)) carsToShow.add(c);
        }
        return carsToShow;
    }
}

//class CompanyFilter2 implements CarFilter2 {
//	private String company;
//
//	public CompanyFilter2(String c) {
//		this.company = c;
//	}
//
//	public boolean showCar2(Excersice2_Car_Predicate c) {
//		return company.equals(c.company);
//	}
//}