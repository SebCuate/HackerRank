package javaStuff.lambdas;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Excersice1_Car {

	String company;
	int year;
	double price;
	String type;

	public Excersice1_Car(String c, int y, double p, String t) {
		this.company = c;
		this.year = y;
		this.price = p;
		this.type = t;
	}

	public String toString() {
		return "(" + company + " " + year + ")";
	}

	public static void main(String[] args) {
		CarMall cm = new CarMall();
        List<Excersice1_Car> carsByCompany = cm.showCars((c) -> {
        	return c.company.equals("Honda") && c.price > 10000;
        } );
        System.out.println(carsByCompany);
		
//		CarMall cm = new CarMall();
//		CarFilter cf = new CompanyFilter("Honda");
//		List<Car> carsByCompany = cm.showCars(cf);
//		System.out.println(carsByCompany);
	}
	
}

interface CarFilter {
	boolean showCar(Excersice1_Car c);
}

class CarMall {
	List<Excersice1_Car> cars = new ArrayList<>();

	CarMall() {
		cars.add(new Excersice1_Car("Honda", 2012, 9000.0, "HATCH"));
		cars.add(new Excersice1_Car("Honda", 2018, 17000.0, "SEDAN"));
		cars.add(new Excersice1_Car("Toyota", 2014, 19000.0, "SUV"));
		cars.add(new Excersice1_Car("Ford", 2014, 13000.0, "SPORTS"));
		cars.add(new Excersice1_Car("Nissan", 2017, 8000.0, "SUV"));
	}

	List<Excersice1_Car> showCars(CarFilter cf) {
		ArrayList<Excersice1_Car> carsToShow = new ArrayList<>();
		for (Excersice1_Car c : cars) {
			if (cf.showCar(c))
				carsToShow.add(c);
		}
		return carsToShow;
	}
}

class CompanyFilter implements CarFilter {
	private String company;

	public CompanyFilter(String c) {
		this.company = c;
	}

	public boolean showCar(Excersice1_Car c) {
		return company.equals(c.company);
	}
}