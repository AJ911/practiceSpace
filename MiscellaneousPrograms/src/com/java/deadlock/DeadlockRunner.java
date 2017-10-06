/**
 * 
 */
package com.java.deadlock;

/**
 * @author Avanindra
 *
 */
public class DeadlockRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		MyDeadlockClass mdcOne = new MyDeadlockClass();
		mdcOne.trd1.setName("thread-One");
		mdcOne.trd1.start();

		mdcOne.trd2.setName("thread-Two");
		mdcOne.trd2.start();

	}

}
