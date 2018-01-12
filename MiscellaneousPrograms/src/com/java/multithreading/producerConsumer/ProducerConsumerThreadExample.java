/**
 * 
 */
package com.java.multithreading.producerConsumer;

/**
 * @author Avanindra
 *
 */
public class ProducerConsumerThreadExample {

	/**
	 * @param args
	 */

	public static void main(String[] args) throws InterruptedException {

		ProducerConsumer pc = new ProducerConsumer();

		Thread threadOne = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					pc.produce();
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			}

		});

		Thread threadTwo = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					pc.consume();
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			}

		});

		threadOne.start();
		threadTwo.start();

		threadOne.join();
		threadTwo.join();

	}

}
