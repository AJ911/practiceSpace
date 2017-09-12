package com.ds.stack;

public class ArrayStackRunner {

	public static void main(String[] args) {

		ArrayStack stack = new ArrayStack(10);
		stack.push(12);
		stack.push(15);
		stack.push(9);
		stack.push(11);
		stack.push(17);

		stack.pop();
		stack.display();

	}

}
