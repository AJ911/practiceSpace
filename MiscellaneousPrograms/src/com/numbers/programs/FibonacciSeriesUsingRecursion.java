/**
 * 
 */
package com.numbers.programs;

import java.util.Scanner;

/**
 * @author M1041417
 *
 */
public class FibonacciSeriesUsingRecursion {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		int number = scan.nextInt();

		System.out.println("fibonacci series: ");
		fibonacciSeries(number);

		System.out.println("Number " + fibonacciNumber(number));

	}

	public static void fibonacciSeries(int number) {

		int[] fibArr = new int[number];

		fibArr[0] = 0;
		fibArr[1] = 1;
		for (int i = 2; i < number; i++)
			fibArr[i] = fibArr[i - 1] + fibArr[i - 2];

		for (int i = 0; i < number; i++)
			System.out.println(fibArr[i]);

	}

	public static int fibonacciNumber(int number) {

		if (number == 0)
			return 0;
		else if (number == 1 || number == 2)
			return 1;
		else
			return fibonacciNumber(number - 1) + fibonacciNumber(number - 2);

	}

}
