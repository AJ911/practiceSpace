/**
 * 
 */
package com.string.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Avanindra
 *
 */
public class ReverseWords {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Reversed String is: " + new ReverseWords().doReverse("I drink and I know Things"));

	}

	public String doReverse(String str) {

		String revString = "";
		List<String> wordsList = new ArrayList<>();

		if (str != null) {
			String[] strArr = str.split(" ");
			wordsList = Arrays.asList(strArr);
		}

		for (int i = wordsList.size() - 1; i >= 0; i--) {
			revString = revString + " " + wordsList.get(i);
		}

		return revString;

	}

}
