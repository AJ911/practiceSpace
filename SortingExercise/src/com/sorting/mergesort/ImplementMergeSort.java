package com.sorting.mergesort;

public class ImplementMergeSort {

	public static void main(String[] args) {

		int[] arrResult = ImplementMergeSort.doMergeSort(new int[] { 10, 12, 1, 12, 20, 5 });
		for (int i : arrResult) {
			System.out.println(i);
		}

	}

	public static int[] doMergeSort(int[] arr) {

		int n = arr.length;
		if (n < 2)
			return arr;

		int lLeft = n / 2;
		int lRight = n - lLeft;

		int[] lArr = new int[lLeft];
		int[] rArr = new int[lRight];

		int i = 0;
		while (i < lLeft) {

			lArr[i] = arr[i];
			i++;

		}

		int j = 0;
		int k = lLeft;
		while (k < n) {
			rArr[j] = arr[k];
			j++;
			k++;

		}

		doMergeSort(lArr);
		doMergeSort(rArr);

		mergeArrays(lArr, rArr, arr);

		return arr;

	}

	public static int[] mergeArrays(int[] lArr, int[] rArr, int[] arr) {

		int i = 0;
		int j = 0;

		int k = 0;

		while (i < lArr.length && j < rArr.length) {

			if (lArr[i] < rArr[j]) {
				arr[k] = lArr[i];
				i++;
				k++;
			} else {
				arr[k] = rArr[j];
				j++;
				k++;
			}

		}

		if (i >= lArr.length) {
			while (j < rArr.length)
				arr[k++] = rArr[j++];
		} else {
			while (i < lArr.length)
				arr[k++] = lArr[i++];
		}

		return arr;
	}

}
