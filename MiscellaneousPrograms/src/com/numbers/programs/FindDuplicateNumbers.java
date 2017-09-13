package com.numbers.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindDuplicateNumbers {

	public static void main(String[] args) {

		Integer intArr[] = { 10, 10, 12, 12, 34, 56, 34 };
		List<Integer> dupList = FindDuplicateNumbers.findDuplicate(Arrays.asList(intArr));

		System.out.println("following numbers are repeating in your array");
		for (int i : dupList) {
			System.out.println(i);
		}

	}

	public static List<Integer> findDuplicate(List<Integer> intList) {

		List<Integer> dupList = new ArrayList<>();

		Map<Integer, Integer> intMap = new HashMap<>();

		for (int i : intList) {

			if (intMap.containsKey(i))
				intMap.put(i, intMap.get(i) + 1);
			else
				intMap.put(i, 1);

		}

		for (Map.Entry<Integer, Integer> i : intMap.entrySet())
			if (i.getValue() > 1)
				dupList.add(i.getKey());

		return dupList;

	}

}
