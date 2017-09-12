package com.sorting.quicksort;

public class MyQuickSort {

	public static void main(String[] args) {

		int[] arrResult = MyQuickSort.doQuickSort(new int[] { 10, 12, 1, 12, 20, 5 });
		for (int i : arrResult) {
			System.out.println(i);
		}

	}

	public static int[] doQuickSort(int[] arr) {

		int lowerIndex = 0;
		int higherIndex = arr.length - 1;

		int pivot = (arr[(lowerIndex + higherIndex) / 2]);

		return arr;
	}

	public static void swapNumbers(int i, int j) {

	}

}
