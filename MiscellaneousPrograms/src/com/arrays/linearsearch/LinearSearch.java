/**
 * 
 */
package com.arrays.linearsearch;

import java.util.Scanner;

/**
 * @author Avanindra
 *
 */
public class LinearSearch {

	/* this function returns the index of element x in arr[] */

	static int search(int[] arr, int x) {

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == x)
				return i;
		}

		/* return -1 if the element is not found */
		return -1;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int[] arr = new int[100];

		Scanner scan = new Scanner(System.in);
		int tests = scan.nextInt();

		while (tests > 0) {

			/* input the size of the array */
			int n = scan.nextInt();

			/* input the elements of the array one by one */
			for (int i = 0; i < n; i++)
				arr[i] = scan.nextInt();

			/* input the element to be searched */
			int x = scan.nextInt();

			/* compute and print the result */
			System.out.println(search(arr, x));

			tests--;

		}

		if (scan != null)
			scan.close();

	}

}
