package com.ds.queue;

public class QueueRunner {

	public static void main(String[] args) {

		ArrayQueue queue = new ArrayQueue(10);
		if (!queue.isFull())
			queue.insert(2);

		if (!queue.isFull())
			queue.insert(23);

		if (!queue.isFull())
			queue.insert(9);

		if (!queue.isFull())
			queue.insert(6);

		if (!queue.isFull())
			queue.insert(28);

		if (!queue.isFull())
			queue.insert(90);

		System.out.println("queue elements:");
		queue.display();

		if (!queue.isEmpty())
			queue.remove();

		System.out.println("after removing element: ");
		queue.display();

	}

}
