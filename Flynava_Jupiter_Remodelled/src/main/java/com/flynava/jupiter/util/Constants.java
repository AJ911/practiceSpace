package com.flynava.jupiter.util;

public class Constants {

	/* public static String DB_NAME = "testDB"; */
	public static String DB_NAME = "fzDB";
	public static String hostName = "FZ";
	public static String COLUMN_NAME = "_id";
	public static String COLLECTION_NAME = "collection";

	// ERROR CODES
	public static final String ERROR_O1 = "An Exception has occured. Please contact your administrator";

	public static final int TYPE_OF_CHANNELS = 6;
	public static final int TYPE_OF_TRIPS = 2;
	public static final int TYPE_OF_FARES = 2; // Promotional or Non-promotional
												// Fares

	// Compartment Y
	public static final int TYPE_OF_RBD_FOR_Y = 8;
	public static final int TYPE_OF_FARE_BRANDS_FOR_Y = 6;

	// Compartment J
	public static final int TYPE_OF_RBD_FOR_J = 3;
	public static final int TYPE_OF_FARE_BRANDS_FOR_J = 2;

	// Compartment F or A
	public static final int TYPE_OF_RBD_FOR_F_A = 3;
	public static final int TYPE_OF_FARE_BRANDS_FOR_F_A = 1;

	// Compartment NA
	public static final int TYPE_OF_RBD_FOR_NA = 8;
	public static final int TYPE_OF_FARE_BRANDS_FOR_NA = 6;

	public static final float DEFAULT_COMPETITOR_RATING = (float) 5.1;

	public static final String INSERT_SUCCESS_STATUS = "Success!";
	public static final String INSERT_SUCCESS_MESSAGE = "Record Successfully saved.";

	public static final String INSERT_FAILED_STATUS = "Failed!";
	public static final String INSERT_FAILED_MESSAGE = "Something went wrong! Record could not be saved";

	public static final int NO_OF_COMPETITORS = 5;

	public static final String FARE_FILING_EXCEL_SHEET_NAME = "FiledFare_Jupiter";

	public static final String excelFileName() {

		// return "C:\\Users\\avani\\filingFares.csv";

		return "//var//www//html//jupiter//java//filingFares.csv";

	}

}
