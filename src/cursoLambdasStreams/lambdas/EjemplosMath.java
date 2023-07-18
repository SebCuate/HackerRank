package cursoLambdasStreams.lambdas;

import java.math.MathContext;

public class EjemplosMath {

	public static void ejerciciosLambda(Double a, Double b) {
		
		Math substract = new Math() {
			@Override
			public Double excecute(Double a, Double b) {
				return a - b;
			}
		};
		Math multuply = (x, y) -> x+y;
		Math divide = (x, y) -> {
//			System.out.println(x/y);
			return x/y;
		};
		//ejemplo de sobreescritura de metodo default
		Math pwr = new Math() {
			@Override
			public Double excecute(Double a, Double b) {
				return a - b;
			}
			@Override
			public Double sum(Double a, Double b) {
				return a%b;
			}

		};

		
		System.out.println(substract.sum(a, b));
		System.out.println(substract.excecute(a, b));
		System.out.println(multuply.excecute(a, b));
		System.out.println(divide.excecute(a, b));
		System.out.println(pwr.sum(a, b));
	}
	
}
