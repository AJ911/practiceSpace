/**
 * 
 */
package com.java.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Avanindra
 *
 */
public class SortMapByValue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Map<String, Integer> myMap = new HashMap<>();
		myMap.put("Avi", 80);
		myMap.put("Bidur", 90);
		myMap.put("Shubham", 70);
		myMap.put("Hemant", 60);

		Set<Entry<String, Integer>> mapSet = myMap.entrySet();
		List<Entry<String, Integer>> mapList = new ArrayList<>(mapSet);

		Collections.sort(mapList, );

	}

	Comparator<Integer> valueComparator = new Comparator<Integer>() {

		@Override
		public int compare(Integer arg0, Integer arg1) {

			return 0;
		}
	};

}
