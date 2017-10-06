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

			synchronized (strOne) {

				try {
					System.out.println(trd1.getName() + " holding " + strOne);
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println(trd1.getName() + " waiting for " + strTwo);

			}

			synchronized (strTwo) {

				System.out.println(trd1.getName() + " holding " + strOne + " and " + strTwo);

			}

		}
	};
	Thread trd2 = new Thread() {
		public void run() {

			synchronized (strTwo) {

				try {
					System.out.println(trd2.getName() + " holding " + strOne);
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println(trd2.getName() + " waiting for " + strOne);

			}

			synchronized (strOne) {

				System.out.println(trd2.getName() + " holding " + strOne + " and " + strTwo);

			}

		}

	};

}
