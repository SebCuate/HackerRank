package cursoLambdasStreams.lambdas;

import cursoLambdasStreams.fundamentals.Employee;
import cursoLambdasStreams.fundamentals.Product;

public class EjemplosPrinter {

	public static void ejerciciosLambda(Double a, Double b) {
		
		Printer<String> pS = string -> System.out.println(string);
		Printer<Product> pP = product ->System.out.println(product);
		Printer<Employee> pE = empl -> System.out.println(empl);
		
		pS.print("HOLA");
		pP.print(new Product());
		pE.print(new Employee());
		
	}
	
}
