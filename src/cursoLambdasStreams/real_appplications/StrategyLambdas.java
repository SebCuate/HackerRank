package cursoLambdasStreams.real_appplications;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class StrategyLambdas {

	public static void main(String[] args) {

		var product1 = ProductStrategy.builder()
				.id(1l).name("Bear").price(150.0)
				.userType("Basic")
				.build();
		var product2 = ProductStrategy.builder()
				.id(1l).name("Bear").price(150.0)
				.userType("Plus")
				.build();
		var product3 = ProductStrategy.builder()
				.id(1l).name("Bear").price(150.0)
				.userType("Prime")
				.build();
		
		var products = List.of(product1, product2, product3);
		
		products.forEach(p-> {
			switch(p.getUserType()) {
			case "Basic": p.setDiscountStrategy(Strategies.basic);break;
			case "Plus": p.setDiscountStrategy(Strategies.plus);break;
			case "Prime": p.setDiscountStrategy(Strategies.prime);break;
			}
		});
		
		products.forEach(p-> {
			System.out.println("Plan: " + p.getUserType());
			System.out.println("Price: " + p.getPrice());
			System.out.println("After Discount: " + p.getDiscountStrategy().get(p.getPrice()));	
		});
	}

}

@FunctionalInterface
interface ApplyDiscountStrategy {
	Double get(Double price);
}

@Setter @Getter
@ToString
@Builder
class ProductStrategy{
	private Long id;
	private String userType;
	private String name;
	private Double price;
	private Double discount;
	private ApplyDiscountStrategy discountStrategy;
}

class Strategies{
	static ApplyDiscountStrategy basic = p-> p*0.98;
	static ApplyDiscountStrategy plus = p-> p*0.90;
	static ApplyDiscountStrategy prime = p-> p*0.80;
}