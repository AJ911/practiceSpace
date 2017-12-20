package com.sorting.quicksort;

public class MyQuickSort {

	public static void main(String[] args) {

		int arr[] = new int[] { 10, 12, 1, 12, 20, 5 };

		int[] arrResult = MyQuickSort.doQuickSort(arr, 0, arr.length - 1);

		for (int i : arrResult) {
			System.out.println(i);
		}

	}

	public static int[] doQuickSort(int[] arr, int lowerIndex, int higherIndex) {

		int i = lowerIndex;
		int j = higherIndex;

		int pivot = (arr[lowerIndex + (higherIndex - lowerIndex) / 2]);

		while (i <= j) {

			while (arr[i] < pivot)
				i++;

			while (arr[j] > pivot)
				j--;

			if (i <= j)
				swapNumbers(i, j);

		}
		return arr;

	}

	public static void swapNumbers(int i, int j) {

	}

}
