/**
 * 
 */
package com.java.programs;

/**
 * @author Avanindra
 *
 */
public class SingletonClass {

	private static volatile SingletonClass singletonInstance;

	private SingletonClass() {

	}

	public static SingletonClass getSingletonInstance() {

		if (singletonInstance == null)
			singletonInstance = new SingletonClass();

		return singletonInstance;

	}

	public void display() {

		System.out.println("I am a singleton instance function");
	}

}
