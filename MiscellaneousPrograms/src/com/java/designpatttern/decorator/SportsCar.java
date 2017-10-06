/**
 * 
 */
package com.java.designpatttern.decorator;

/**
 * @author Avanindra
 *
 */
public class SportsCar extends CarDecorator {

	/**
	 * @param car
	 */
	public SportsCar(Car car) {
		super(car);

	}

	@Override
	public void assemble() {

		super.assemble();

		System.out.println("Adding features for sports car");
	}

}
