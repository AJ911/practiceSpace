/**
 * 
 */
package com.string.programs;

/**
 * @author Avanindra
 *
 */
public class CheckPallindromeUsingRecursion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println(checkPallindrome("NITINJ"));

	}

	public static boolean checkPallindrome(String str) {

		if (str == null || str.length() == 0)
			return false;
		else if (str.length() == 1)
			return true;
		else {
			if (str.charAt(0) == str.charAt(str.length() - 1))
				return checkPallindrome(str.substring(1, str.length() - 1));

			return false;
		}

	}

}
