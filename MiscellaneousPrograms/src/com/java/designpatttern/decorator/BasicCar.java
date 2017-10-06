/**
 * 
 */
package com.java.designpatttern.decorator;

/**
 * @author Avanindra
 *
 */
public class BasicCar implements Car {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.java.designpatttern.decorator.Car#assemble()
	 */
	@Override
	public void assemble() {

		System.out.println("Basic car");

	}

}
