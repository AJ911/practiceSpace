/**
 * 
 */
package com.java.designpatttern.decorator;

/**
 * @author Avanindra
 *
 */
public class LuxuryCar extends CarDecorator {

	/**
	 * @param car
	 */
	public LuxuryCar(Car car) {
		super(car);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.java.designpatttern.decorator.CarDecorator#assemble()
	 */
	@Override
	public void assemble() {
		super.assemble();
		System.out.println("Adding features to luxury car");
	}

}
