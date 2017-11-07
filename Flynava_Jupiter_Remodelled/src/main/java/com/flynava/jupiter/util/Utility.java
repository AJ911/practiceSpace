package com.flynava.jupiter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.flynava.jupiter.model.SplitOD;

public class Utility {

	private static final Logger logger = Logger.getLogger(Utility.class);

	public static final boolean verifyHashedPassword(String passwordDBToHash, String passwordUItoHash, byte[] salt) {

		String generatedDBPassword = null;
		String generateUIPassword = null;
		boolean message = false;
		StringBuilder sb = new StringBuilder();

		try {

			MessageDigest mdUI = MessageDigest.getInstance("SHA1");
			MessageDigest mdDB = MessageDigest.getInstance("SHA1");

			mdDB.update(salt);

			if (passwordDBToHash != null) {
				byte[] dbBytes = mdDB.digest(passwordDBToHash.getBytes());

				sb = new StringBuilder();
				for (int i = 0; i < dbBytes.length; i++) {
					sb.append(Integer.toString((dbBytes[i] & 0xff) + 0x100, 16).substring(1));
				}
				generatedDBPassword = sb.toString();
				System.out.println("generatedDBPassword: " + generatedDBPassword);
			}

			mdUI.update(salt);

			if (passwordUItoHash != null) {
				byte[] uiBytes = mdUI.digest(passwordUItoHash.getBytes());

				StringBuilder sb1 = new StringBuilder();
				for (int i = 0; i < uiBytes.length; i++) {
					sb1.append(Integer.toString((uiBytes[i] & 0xff) + 0x100, 16).substring(1));
				}
				generateUIPassword = sb1.toString();
				System.out.println("generated UI Password: " + generateUIPassword);
			}
			if (generateUIPassword != null) {
				if (generateUIPassword.equals(generatedDBPassword)) {
					message = true;
					return (message);
				} else {
					message = false;
					return (message);

				}
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error("verifyHashedPassword-Exception", e);
		}

		System.out.println(message);
		return message;

	}

	public static final String getToken(String userName, String password) {

		return userName + password;

	}

	public static final String create_json(ArrayList<String> array) {

		String json = new flexjson.JSONSerializer().serialize(array);
		return json;
	}

	public static final String create_jsonString(String str) {

		String json = new flexjson.JSONSerializer().serialize(str);
		return json;
	}

	public static final String readAll(BufferedReader reader) {

		StringBuilder sb = new StringBuilder();
		int cp;
		try {
			while ((cp = reader.read()) != -1) {
				sb.append((char) cp);
			}
		} catch (IOException e) {
			logger.error("readAll-Exception", e);
		}
		return sb.toString();

	}

	/**
	 * This method gets a list og months from a range of dates.
	 * 
	 * @return ArrayList of months
	 * @author Anu Merin
	 */
	public static final ArrayList<String> getMonthsFromDateRange(String firstdate, String seconddate) {
		String date1 = firstdate;
		String date2 = seconddate;
		ArrayList<String> monthList = new ArrayList<String>();

		DateFormat lFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();

		try {
			beginCalendar.setTime(lFormat.parse(date1));
			finishCalendar.setTime(lFormat.parse(date2));
		} catch (ParseException e) {
			logger.error("getMonthsFromDateRange-Exception", e);
		}

		while (beginCalendar.before(finishCalendar) || beginCalendar.equals(finishCalendar)) {
			// add one month to date per loop
			String lMonthName = beginCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			int monthInt = Month.valueOf(lMonthName.toUpperCase()).getValue();
			monthList.add(String.valueOf(monthInt));
			beginCalendar.add(Calendar.MONTH, 1);
		}

		return monthList;
	}

	/**
	 * This method gets the current date of the current year from a Calendar
	 * Instance. Format output would be in "yyyy-MM-dd"
	 * 
	 * @return String
	 * @author Anu Merin
	 */
	public static final String getCurrentDate() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());

	}

	public static String getNthDate(String fromDate, int days) {

		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(fromDate);
		} catch (ParseException e) {

			logger.error("getNthDate-Exception", e);
		}
		final Calendar calendar = Calendar.getInstance();
		if (date != null)
			calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);

		return format.format(calendar.getTime());

	}

	public static String getPrevYrDate(String currentDate) {

		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(currentDate);
		} catch (ParseException e) {

			logger.error("getNthDate-Exception", e);
		}
		final Calendar calendar = Calendar.getInstance();
		if (date != null)
			calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);

		return format.format(calendar.getTime());

	}

	public static Double findSum(Object object) {
		if (object == null)
			return 0D;

		JSONArray arr = new JSONArray(object.toString());
		Double sum = 0D;
		for (int i = 0; i < arr.length(); i++)
			sum = sum + Double.parseDouble(arr.get(i).toString());

		return sum;
	}

	/**
	 * This method converts a Date to a Sting Format. Format output would be in
	 * "yyyy-MM-dd"
	 * 
	 * @return String
	 * @author Anu Merin
	 */
	public static final String convertDateToStringFromat(Date date) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(date.getTime());
		// Output "Wed Sep 26 14:23:28 EST 2012"
		String formatted = format1.format(date.getTime());
		System.out.println(formatted);
		// Output "2012-09-26"
		return formatted;
	}

	/**
	 * This method gets the first date of the current year i.e 2016-01-01 from a
	 * Calendar Instance. Format output would be in "yyyy-MM-dd"
	 * 
	 * @return String
	 * @author Anu Merin
	 */
	public static final String getFirstDateOfCurrentYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.set(Calendar.MONTH, 0);
		calendarStart.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = calendarStart.getTime();
		return dateFormat.format(startDate);

	}

	public static int findMonth(String date) {

		String str[] = date.split("-");
		int month = Integer.parseInt(str[1]);

		return month;

	}

	public static int findYear(String date) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date utilDate = null;
		try {
			utilDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("findYear-Exception", e);
		}

		Calendar cal = Calendar.getInstance();
		if (utilDate != null)
			cal.setTime(utilDate);
		int year = cal.get(Calendar.YEAR);

		return year;

	}

	public static int findDay(String date) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date utilDate = null;
		try {
			utilDate = sdf.parse(date);
		} catch (ParseException e) {
			logger.error("findDay-Exception", e);
		}

		Calendar cal = Calendar.getInstance();
		if (utilDate != null)
			cal.setTime(utilDate);
		int year = cal.get(Calendar.DAY_OF_MONTH);

		return year;

	}

	public static int getCurrentMonth() {

		// Calendar now = Calendar.getInstance();

		return /* now.get(Calendar.MONTH) + 1 */2;

	}

	/**
	 * This method gets a list of years from a range of dates.
	 * 
	 * @return ArrayList of months
	 * @author Anu Merin
	 */
	public static final ArrayList<String> getYearsFromDateRange(String firstdate, String seconddate) {
		String date1 = firstdate;
		String date2 = seconddate;
		ArrayList<String> yearList = new ArrayList<String>();

		DateFormat lFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();

		try {
			beginCalendar.setTime(lFormat.parse(date1));
			finishCalendar.setTime(lFormat.parse(date2));
		} catch (ParseException e) {
			logger.error("getYearsFromDateRange-Exception", e);
		}

		while (beginCalendar.before(finishCalendar) || beginCalendar.equals(finishCalendar)) {
			// add one month to date per loop
			// String lYearName =
			// beginCalendar.getDisplayName(Calendar.YEAR,beginCalendar.get(Calendar.YEAR),
			// Locale.getDefault());
			String lYearName = Integer.toString(beginCalendar.get(Calendar.YEAR));
			// int yearInt = Year.valueOf(lYearName.toUpperCase()).getValue();
			yearList.add(lYearName);
			beginCalendar.add(Calendar.YEAR, 1);
		}

		return yearList;
	}

	public static final ArrayList<Integer> getYearsFromDateRange1(String firstdate, String seconddate) {
		String date1 = firstdate;
		String date2 = seconddate;
		ArrayList<Integer> yearList = new ArrayList<Integer>(3);

		DateFormat lFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();

		try {
			beginCalendar.setTime(lFormat.parse(date1));
			finishCalendar.setTime(lFormat.parse(date2));
		} catch (ParseException e) {
			logger.error("getYearsFromDateRange1-Exception", e);
		}

		while (beginCalendar.before(finishCalendar) || beginCalendar.equals(finishCalendar)) {
			// add one month to date per loop
			Integer lYearName = beginCalendar.get(Calendar.YEAR);
			yearList.add(lYearName);
			beginCalendar.add(Calendar.YEAR, 1);
		}

		return yearList;
	}

	/**
	 * This method gets the first date of the current year i.e 2016-12-31 from a
	 * Calendar Instance. Format output would be in "yyyy-MM-dd"
	 * 
	 * @return String
	 * @author Anu Merin
	 */
	public static final String getLastDateOfCurrentYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.set(Calendar.MONTH, 11);
		calendarStart.set(Calendar.DAY_OF_MONTH, 31);
		Date startDate = calendarStart.getTime();
		return dateFormat.format(startDate);

	}

	/**
	 * This method gets the first date of the current month from a Date. Format
	 * output would be in "yyyy-MM-dd"
	 * 
	 * @return String
	 * @author Anu Merin
	 */
	public static final String getFirstDateCurrentMonth(Date date) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date lDate = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(lDate);
	}

	/**
	 * This method gets last date of the current month from a Date Instance.
	 * Format output would be in "yyyy-MM-dd"
	 * 
	 * @return String
	 * @author Anu Merin
	 */
	public static final String getLastDateCurrentMonth(Date date) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lDate = calendar.getTime();
		SimpleDateFormat lsdf = new SimpleDateFormat("yyyy-MM-dd");
		return lsdf.format(lDate);
	}

	public static final int getPreviousYears() {
		Calendar prevYear = Calendar.getInstance();
		prevYear.add(Calendar.YEAR, -1);
		return prevYear.get(Calendar.YEAR);
	}

	static final int MILLIS_IN_SECOND = 1000;
	static final int SECONDS_IN_MINUTE = 60;
	static final int MINUTES_IN_HOUR = 60;
	static final int HOURS_IN_DAY = 24;
	static final int DAYS_IN_YEAR = 365;
	static final long MILLISECONDS_IN_YEAR = (long) MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR
			* HOURS_IN_DAY * DAYS_IN_YEAR;

	public static int getCurrentYear() {
		Calendar now = Calendar.getInstance(); // Gets the current date and time
		int year = now.get(Calendar.YEAR);
		return year;
	}

	/**
	 * This method converts a Date to a Sting Format. Format output would be in
	 * "yyyy-MM-dd"
	 * 
	 * @return String
	 * @author Anu Merin
	 */
	public static final Date convertStringToDateFromat(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date lDate = null;
		try {
			lDate = df.parse(date);
		} catch (ParseException e) {
			logger.error("convertStringToDateFromat-Exception", e);
		}
		return lDate;
	}

	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
	}

	public static boolean datePassed(String date) {

		if (convertStringToDateFromat(date).before(convertStringToDateFromat(Utility.getCurrentDate())))
			return true;
		else
			return false;

	}

	public static Authentication getAuthenticationName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth;
	}

	// Get Authentication Type (Like Login User Type)
	public static Collection<? extends GrantedAuthority> getAuthenticationType() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> value = auth.getAuthorities();
		return value;
	}

	public static ArrayList<Integer> convertStringListToIntegerList(ArrayList<String> lMonth) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (String mon : lMonth)
			list.add(Integer.valueOf(mon));
		return list;
	}

	public static int numberOfDaysInMonth(int month, int year) {

		/*
		 * pass month and year to this function and the function will return
		 * number of days this month has
		 */
		// months start from 0 and goes upto 11. eg. 0 for january and 11 for
		// december
		Calendar monthStart = new GregorianCalendar(year, month, 1);
		return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static long getDaysOfYear(int year) {
		int days = 365;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		if (cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365) {
			days = 366;
		} else {
			days = 365;
		}
		return days;
	}

	public static SplitOD getOrignDestFromOD(String od) {
		SplitOD originDest = new SplitOD();

		if (od.length() == 6) {
			/*
			 * this logic needs to be remodelled if the length of the od is not
			 * exactly equal to 6
			 */
			originDest.setOrigin(od.substring(0, 3));
			originDest.setDestination(od.substring(3, 6));

		}

		/*
		 * if the length of od is not equal to 6 origin and destination member
		 * variables of the SplitOD class will be set to null
		 */

		return originDest;

	}

	/**
	 * This method gets a list of years from a range of dates.
	 * 
	 * @return ArrayList of months
	 * @author Anu Merin
	 */
	public static final ArrayList<Integer> getIntYearsFromDateRange(String firstdate, String seconddate) {
		String date1 = firstdate;
		String date2 = seconddate;
		ArrayList<Integer> yearList = new ArrayList<Integer>();

		DateFormat lFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();

		try {
			beginCalendar.setTime(lFormat.parse(date1));
			finishCalendar.setTime(lFormat.parse(date2));
		} catch (ParseException e) {
			logger.error("getYearsFromDateRange-Exception", e);
		}

		while (beginCalendar.before(finishCalendar) || beginCalendar.equals(finishCalendar)) {
			// add one month to date per loop
			// String lYearName =
			// beginCalendar.getDisplayName(Calendar.YEAR,beginCalendar.get(Calendar.YEAR),
			// Locale.getDefault());
			int lYearName = beginCalendar.get(Calendar.YEAR);
			// int yearInt = Year.valueOf(lYearName.toUpperCase()).getValue();
			yearList.add(lYearName);
			beginCalendar.add(Calendar.YEAR, 1);
		}

		return yearList;
	}

	/**
	 * This method gets a list og months from a range of dates.
	 * 
	 * @return ArrayList of months
	 * @author Anu Merin
	 */
	public static final ArrayList<Integer> getIntMonthsFromDateRange(String firstdate, String seconddate) {
		String date1 = firstdate;
		String date2 = seconddate;
		ArrayList<Integer> monthList = new ArrayList<Integer>();

		DateFormat lFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();

		try {
			beginCalendar.setTime(lFormat.parse(date1));
			finishCalendar.setTime(lFormat.parse(date2));
		} catch (ParseException e) {
			logger.error("getMonthsFromDateRange-Exception", e);
		}

		while (beginCalendar.before(finishCalendar) || beginCalendar.equals(finishCalendar)) {
			// add one month to date per loop
			String lMonthName = beginCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			int monthInt = Month.valueOf(lMonthName.toUpperCase()).getValue();
			monthList.add(monthInt);
			beginCalendar.add(Calendar.MONTH, 1);
		}

		return monthList;
	}

	// To get System Time
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println(sdf.format(cal.getTime()));
	}

	public static final int getPreviousMonths() {
		Calendar prevYear = Calendar.getInstance();
		// prevYear.add(Calendar.MONTH);
		return prevYear.get(Calendar.MONTH);
	}

}
