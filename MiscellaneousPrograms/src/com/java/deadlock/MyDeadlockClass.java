/**
 * 
 */
package com.java.deadlock;

/**
 * @author Avanindra
 *
 */
public class MyDeadlockClass {

	String strOne = "String-One";
	String strTwo = "String-Two";

	Thread trd1 = new Thread() {
		public void run() {
			while (true) {
				synchronized (strOne) {
					synchronized (strTwo) {

						System.out.println(strOne + " " + strTwo);

					}

				}
			}

		}
	};
	Thread trd2 = new Thread() {
		public void run() {
			while (true) {
				synchronized (strTwo) {
					synchronized (strOne) {

						System.out.println(strOne + " " + strTwo);

					}

				}
			}

		}

	};

}
