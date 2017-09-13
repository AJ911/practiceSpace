package com.string.programs;

public class ReverseString {

	String revString = "";

	public static void main(String[] args) {

		System.out.println("Reversed String is: " + new ReverseString().doReverse("I drink and I know Things"));

	}

	public String doReverse(String str) {

		if (str.length() == 1)
			return str;
		else {
			revString = revString + str.charAt(str.length() - 1) + doReverse(str.substring(0, str.length() - 1));

			return revString;
		}

	}

}
