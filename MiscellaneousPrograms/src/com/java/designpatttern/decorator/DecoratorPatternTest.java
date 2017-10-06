/**
 * 
 */
package com.java.designpatttern.decorator;

/**
 * @author Avanindra
 *
 */
public class DecoratorPatternTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Car sportsCar = new SportsCar(new BasicCar());
		sportsCar.assemble();

		System.out.println("\n");

		Car sportsLuxuryCar = new SportsCar(new LuxuryCar(new BasicCar()));
		sportsLuxuryCar.assemble();

	}

}
