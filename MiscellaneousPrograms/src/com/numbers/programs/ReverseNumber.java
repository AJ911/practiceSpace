/**
 * 
 */
package com.numbers.programs;

/**
 * @author Avanindra
 *
 */
public class ReverseNumber {

	/**
	 * @param args
	 */

	int revNumber = 0;

	public static void main(String[] args) {

		System.out.println("Reverse of the number is: " + new ReverseNumber().doReverse(17809300));

	}

	public int doReverse(int num) {

		revNumber = revNumber * 10 + num % 10;
		num = num / 10;
		if (num != 0)
			doReverse(num);

		return revNumber;

	}

}
