package com.sorting.bubblesort;

public class MyBubbleSort {

	public static void main(String[] args) {

		int[] arrResult = MyBubbleSort.doBubbleSort(new int[] { 10, 12, 1, 12, 20, 5 });
		for (int i : arrResult) {
			System.err.println(i);
		}

	}

	public static int[] doBubbleSort(int[] arr) {

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - 1; j++) {

				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}

			}
		}

		return arr;

	}

}
