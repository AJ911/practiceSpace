/**
 * 
 */
package com.numbers.programs;

/**
 * @author Avanindra
 *
 */
public class FindTopTwoMax {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		new FindTopTwoMax().findTwoMax(new int[] { 23, 22, 67, 0, 1, 56 });

	}

	public void findTwoMax(int[] arr) {

		int maxNum = Integer.MIN_VALUE;
		int secMaxNum = Integer.MIN_VALUE;

		for (int i = 0; i < arr.length; i++) {

			if (arr[i] > maxNum)
				maxNum = arr[i];

			else if (arr[i] > secMaxNum && arr[i] < maxNum)
				secMaxNum = arr[i];

		}

		System.out.println("Maximum number is: " + maxNum + " and second maximum number is: " + secMaxNum);

	}

}
