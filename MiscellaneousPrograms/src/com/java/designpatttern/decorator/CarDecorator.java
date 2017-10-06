/**
 * 
 */
package com.java.designpatttern.decorator;

/**
 * @author Avanindra
 *
 */
public class CarDecorator implements Car {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.java.designpatttern.decorator.Car#assemble()
	 */

	protected Car car;

	/**
	 * @param car
	 */
	public CarDecorator(Car car) {
		super();
		this.car = car;
	}

	@Override
	public void assemble() {

		this.car.assemble();

	}

}
