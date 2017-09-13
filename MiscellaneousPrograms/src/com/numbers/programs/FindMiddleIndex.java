package com.numbers.programs;

/*find out the middle index where sum of both the ends are equal*/
public class FindMiddleIndex {

	public static void main(String[] args) {

		System.out.println(
				"adding elements from index 0 to " + FindMiddleIndex.findMiddleIndex(new int[] { 10, 12, 6, 28, 0 })
						+ " gives you same sum as the other half");

	}

	public static int findMiddleIndex(int[] arr) {

		int sumLeft = 0;
		int sumRight = 0;

		int startIndex = 0;
		int endIndex = arr.length - 1;

		while (true) {

			if (sumLeft > sumRight)
				sumRight += arr[endIndex--];
			else
				sumLeft += arr[startIndex++];

			if (startIndex > endIndex) {

				if (sumLeft == sumRight) {
					break;
				} else {
					try {
						throw new Exception("pass a proper array");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		}

		return endIndex;

	}

}
