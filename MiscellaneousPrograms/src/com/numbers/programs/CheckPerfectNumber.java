/**
 * 
 */
package com.numbers.programs;

/**
 * @author Avanindra
 *
 */
public class CheckPerfectNumber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Is your number a perfect number? " + CheckPerfectNumber.checkPerfectNumber(28));

	}

	public static boolean checkPerfectNumber(int num) {

		int factors = 0;

		for (int i = 1; i <= num / 2; i++) {
			if (num % i == 0)
				factors += i;
		}

		return factors == num;

	}

}
