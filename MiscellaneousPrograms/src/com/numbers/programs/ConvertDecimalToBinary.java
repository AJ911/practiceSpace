/**
 * 
 */
package com.numbers.programs;

/**
 * @author Avanindra
 *
 */
public class ConvertDecimalToBinary {

	/**
	 * @param args
	 */

	int[] binaryVal = new int[25];
	int index = 0;

	public static void main(String[] args) {
		ConvertDecimalToBinary conversion = new ConvertDecimalToBinary();

		int[] binArr = conversion.toBinary(254);

		System.out.println("Binary converson is: ");
		for (int i = conversion.index; i >= 0; i--)
			System.out.print(binArr[i]);

	}

	public int[] toBinary(int decNum) {

		int rem = decNum % 2;
		binaryVal[index++] = rem;
		decNum = decNum / 2;

		if (decNum != 0)
			toBinary(decNum);

		return binaryVal;
	}

}
