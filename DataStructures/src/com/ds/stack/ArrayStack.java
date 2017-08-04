package com.ds.stack;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class ArrayStack {

	private int arr[];
	private int top, size, len;

	public ArrayStack(int size) {
		super();

		this.size = size;
		arr = new int[size];
		top = -1;
		len = 0;

	}

	public boolean isEmpty() {

		return top == -1;
	}

	public boolean isFull() {

		return top == size - 1;

	}

	public int getSize() {

		return len;
	}

	public int peek() {

		if (isEmpty())
			throw new NoSuchElementException("stack is empty");

		return arr[top];

	}

	public void push(int element) {

		if (isFull())
			throw new StackOverflowError();

		arr[++top] = element;

		len++;

	}

	public int pop() {

		if (isEmpty())
			throw new EmptyStackException();

		len--;

		return arr[top--];

	}

	public void display() {

		if (len == 0)
			System.out.println("stack is empty");
		else {
			for (int i = top; i >= 0; i--)
				System.out.println(arr[i]);

		}

	}

}
