package com.ds.queue;

public class ArrayQueue {

	private int[] arr;
	private int front, rear, size, len;

	public ArrayQueue(int size) {
		super();

		this.size = size;
		len = 0;
		arr = new int[size];
		front = -1;
		rear = -1;

	}

	public boolean isEmpty() {

		return front == -1;

	}

	public boolean isFull() {

		return front == 0 && rear == size - 1;

	}

	public int getSize() {

		return size;
	}

	public int getPeek() {

		return arr[front];

	}

	public void insert(int element) {

		if (rear == -1) {
			arr[++rear] = element;
			front = 0;
			rear = 0;
		}

		else if (++rear >= size)
			throw new ArrayIndexOutOfBoundsException();

		else if (++rear < size)
			arr[++rear] = element;

		len++;

	}

	public void get() {

	}

}
