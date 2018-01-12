/**
 * 
 */
package com.java.multithreading.producerConsumer;

import java.util.LinkedList;

/**
 * @author Avanindra
 *
 */
public class ProducerConsumer {

	LinkedList<Integer> numbersList = new LinkedList<Integer>();
	int capacity = 4;

	public void produce() throws InterruptedException {

		int value = 0;

		while (true) {

			synchronized (this) {

				while (numbersList.size() == capacity)
					wait();

				System.out.println("producer produced: " + value);
				numbersList.add(value++);

			}

		}

	}

	public void consume() throws InterruptedException {
		while (true) {

			synchronized (this) {

				while (numbersList.size() == 0)
					wait();

				int value = numbersList.removeFirst();
				System.out.println("consumer consumed: " + value);
				notify();

			}

		}

	}

}
