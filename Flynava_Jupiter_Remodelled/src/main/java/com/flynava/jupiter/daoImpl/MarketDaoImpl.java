package com.flynava.jupiter.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.MarketDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Utility;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class MarketDaoImpl implements MarketDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	BasicDBObject allQuery = new BasicDBObject();
	BasicDBObject fields = new BasicDBObject();
	DBCursor cursor = null;
	List lRegionList = null;

	private static final Logger logger = Logger.getLogger(MarketDaoImpl.class);

	@Override
	public ArrayList<DBObject> getMarketSummary(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lYearList = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lYearList = Utility.getYearsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			for (int i = 0; i <= 3; i++) {
				Calendar now = Calendar.getInstance(); // Gets the current date
														// and time
				int year = now.get(Calendar.YEAR);
				int prevYear = year - i;
				lYearList.add(Integer.toString(prevYear));
			}
			mRequestModel.setFromDate(Utility.getFirstDateOfCurrentYear());
			try {
				mRequestModel.setToDate(Utility.getLastDateCurrentMonth(new Date()));
			} catch (Exception e) {
				logger.error("getMarketSummary-Exception", e);
			}

		}

		ArrayList<String> lMonth = new ArrayList<String>();
		// removed dates as per Sidharth-filter dates will not apply at any
		// time, as the grid is yearly
		try {
			String firstDate = Utility.getFirstDateOfCurrentYear();
			String lastDate = Utility.getLastDateCurrentMonth(new Date());
			lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);
		} catch (Exception e) {
			logger.error("getMarketSummary-Exception", e);
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					regionList.clear();
				} else {
					regionList.add(mRequestModel.getRegionArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCountryArray()[i])) {
					countryList.clear();
				} else {
					countryList.add(mRequestModel.getCountryArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getPosArray()[i])) {
					posList.clear();
				} else {
					posList.add(mRequestModel.getPosArray()[i]);
				}
			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getOdArray()[i])) {
					odList.clear();
				} else {
					odList.add(mRequestModel.getOdArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCompartmentArray()[i])) {
					compartmentList.clear();
				} else {
					compartmentList.add(mRequestModel.getCompartmentArray()[i]);
				}

			}
		} else {
			compartmentList.clear();
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Market_dhb_Market_Summary(" + lYearList + "," + lMonth + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getMarketSummary-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getMonthlyNetworkPassengerGrowth(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lYearList = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lYearList = Utility.getYearsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			for (int i = 0; i <= 3; i++) {
				Calendar now = Calendar.getInstance(); // Gets the current date
														// and time
				int year = now.get(Calendar.YEAR);
				int prevYear = year - i;
				lYearList.add(Integer.toString(prevYear));
			}

		}

		ArrayList<String> lMonth = new ArrayList<String>();
		// Same as Market summary above
		/*
		 * if (mRequestModel.getFromDate() != null && mRequestModel.getToDate()
		 * != null) { lMonth =
		 * Utility.getMonthsFromDateRange(mRequestModel.getFromDate(),
		 * mRequestModel.getToDate()); } else {
		 */
		try {
			String firstDate = Utility.getFirstDateOfCurrentYear();
			String lastDate = Utility.getLastDateOfCurrentYear();

			mRequestModel.setFromDate(firstDate);
			mRequestModel.setToDate(lastDate);

			lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);
		} catch (Exception e) {
			logger.error("getMonthlyNetworkPassengerGrowth-Exception", e);
		}

		/* } */

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					regionList.clear();
				} else {
					regionList.add(mRequestModel.getRegionArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCountryArray()[i])) {
					countryList.clear();
				} else {
					countryList.add(mRequestModel.getCountryArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getPosArray()[i])) {
					posList.clear();
				} else {
					posList.add(mRequestModel.getPosArray()[i]);
				}
			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getOdArray()[i])) {
					odList.clear();
				} else {
					odList.add(mRequestModel.getOdArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCompartmentArray()[i])) {
					compartmentList.clear();
				} else {
					compartmentList.add(mRequestModel.getCompartmentArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Market_dhb_Monthly_network_passengers_growth(" + lYearList + "," + lMonth + ","
				+ regionJson + "," + countryJson + "," + posJson + "," + compartmentJson + "," + lTempCollectionName
				+ ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getMonthlyNetworkPassengerGrowth-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getTopTenAgentByVolume(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lMonth = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lMonth = Utility.getMonthsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			try {
				/*
				 * String firstDate = Utility.getFirstDateCurrentMonth(new
				 * Date()); String lastDate =
				 * Utility.getLastDateCurrentMonth(new Date()); lMonth =
				 * Utility.getMonthsFromDateRange(firstDate, lastDate);
				 */

				// TODO Remove the below code once hard coding of current date
				// is removed and uncomment the code block above.
				String firstDate = Utility
						.getFirstDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				String lastDate = Utility
						.getLastDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);

			} catch (Exception e) {
				logger.error("getTopTenAgentByVolume-Exception", e);
			}

		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					regionList.clear();
				} else {
					regionList.add(mRequestModel.getRegionArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCountryArray()[i])) {
					countryList.clear();
				} else {
					countryList.add(mRequestModel.getCountryArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getPosArray()[i])) {
					posList.clear();
				} else {
					posList.add(mRequestModel.getPosArray()[i]);
				}
			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getOdArray()[i])) {
					odList.clear();
				} else {
					odList.add(mRequestModel.getOdArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCompartmentArray()[i])) {
					compartmentList.clear();
				} else {
					compartmentList.add(mRequestModel.getCompartmentArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Market_dhb_Top_Ten_Agent(" + lMonth + "," + regionJson + "," + countryJson + ","
				+ posJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getTopTenAgentByVolume-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getTopTenOdSpikes(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lMonth = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lMonth = Utility.getMonthsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			try {
				/*
				 * String firstDate = Utility.getFirstDateCurrentMonth(new
				 * Date()); String lastDate =
				 * Utility.getLastDateCurrentMonth(new Date()); lMonth =
				 * Utility.getMonthsFromDateRange(firstDate, lastDate);
				 */

				// TODO Remove the below code once hardcoding of current date is
				// removed and uncomment the code block above.
				String firstDate = Utility
						.getFirstDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				String lastDate = Utility
						.getLastDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);
			} catch (Exception e) {
				logger.error("getTopTenOdSpikes-Exception", e);
			}

		}
		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					regionList.clear();
				} else {
					regionList.add(mRequestModel.getRegionArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCountryArray()[i])) {
					countryList.clear();
				} else {
					countryList.add(mRequestModel.getCountryArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getPosArray()[i])) {
					posList.clear();
				} else {
					posList.add(mRequestModel.getPosArray()[i]);
				}
			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getOdArray()[i])) {
					odList.clear();
				} else {
					odList.add(mRequestModel.getOdArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCompartmentArray()[i])) {
					compartmentList.clear();
				} else {
					compartmentList.add(mRequestModel.getCompartmentArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}

		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Market_dhb_Top_Ten_OD_Spikes(" + lMonth + "," + regionJson + "," + countryJson + ","
				+ posJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}
		} catch (Exception e) {
			logger.error("getTopTenOdSpikes-Exception", e);
		} finally {
			if (lCollection != null && db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getTopTenCountryMarket(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lMonth = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lMonth = Utility.getMonthsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			// lMonth.add("7");

			try {
				/*
				 * String firstDate = Utility.getFirstDateCurrentMonth(new
				 * Date()); String lastDate =
				 * Utility.getLastDateCurrentMonth(new Date()); lMonth =
				 * Utility.getMonthsFromDateRange(firstDate, lastDate);
				 */

				// TODO Remove the below code once hard coding of current date
				// is removed and uncomment the code block above.
				String firstDate = Utility
						.getFirstDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				String lastDate = Utility
						.getLastDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);
			} catch (Exception e) {
				logger.error("getTopTenCountryMarket-Exception", e);
			}

		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					String lColl = "JUP_DB_Region_Master";
					DBCollection lDbCollection = null;
					DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
					if (db.collectionExists(lColl)) {
						lDbCollection = db.getCollection(lColl);
						lRegionList = lDbCollection.distinct("Region");
						for (int j = 0; j < lRegionList.size(); j++) {
							regionList.add(lRegionList.get(j).toString());
						}
					}
				} else {
					regionList.add(mRequestModel.getRegionArray()[i]);
				}
			}
		} else {
			String lColl = "JUP_DB_Region_Master";
			DBCollection lDbCollection = null;
			DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			if (db.collectionExists(lColl)) {
				lDbCollection = db.getCollection(lColl);
				lRegionList = lDbCollection.distinct("Region");
				for (int i = 0; i < lRegionList.size(); i++) {
					regionList.add(lRegionList.get(i).toString());
				}
			}
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCountryArray()[i])) {
					countryList.clear();
				} else {
					countryList.add(mRequestModel.getCountryArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getPosArray()[i])) {
					posList.clear();
				} else {
					posList.add(mRequestModel.getPosArray()[i]);
				}
			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getOdArray()[i])) {
					odList.clear();
				} else {
					odList.add(mRequestModel.getOdArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCompartmentArray()[i])) {
					compartmentList.clear();
				} else {
					compartmentList.add(mRequestModel.getCompartmentArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}

		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}

		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Market_dhb_Top_10_country_markets(" + lMonth + "," + regionJson + "," + countryJson + ","
				+ compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getTopTenCountryMarket-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getCallCenterSalesGrowth(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lMonth = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty()
				&& mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty()) {
			lMonth = Utility.getMonthsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			try {
				/*
				 * String firstDate = Utility.getFirstDateCurrentMonth(new
				 * Date()); String lastDate =
				 * Utility.getLastDateCurrentMonth(new Date()); lMonth =
				 * Utility.getMonthsFromDateRange(firstDate, lastDate);
				 */

				// TODO Remove the below code once hardcoding of current date is
				// removed and uncomment the code block above.
				String firstDate = Utility
						.getFirstDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				String lastDate = Utility
						.getLastDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);
			} catch (Exception e) {
				logger.error("getCallCenterSalesGrowth-Exception", e);
			}

		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					regionList.clear();
				} else {
					regionList.add(mRequestModel.getRegionArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCountryArray()[i])) {
					countryList.clear();
				} else {
					countryList.add(mRequestModel.getCountryArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getPosArray()[i])) {
					posList.clear();
				} else {
					posList.add(mRequestModel.getPosArray()[i]);
				}
			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getOdArray()[i])) {
					odList.clear();
				} else {
					odList.add(mRequestModel.getOdArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCompartmentArray()[i])) {
					compartmentList.clear();
				} else {
					compartmentList.add(mRequestModel.getCompartmentArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}

		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String lchannel = null;
		if (mRequestModel.getChannel() != null && !mRequestModel.getChannel().isEmpty())
			lchannel = mRequestModel.getChannel().toString();
		else
			lchannel = "GDS";

		String channelJson = new flexjson.JSONSerializer().serialize(lchannel);

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Market_dhb_Call_center_sale_growth(" + lMonth + "," + regionJson + "," + countryJson
				+ "," + posJson + "," + compartmentJson + "," + channelJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getCallCenterSalesGrowth-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;

	}

	@Override
	public BasicDBObject getBspsalesSummaryhost(RequestModel mRequestModel) {
		ArrayList<String> lYearList = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lYearList = Utility.getYearsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			lYearList.add(Integer.toString(Utility.getCurrentYear()));
		}

		ArrayList<String> lMonth = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lMonth = Utility.getMonthsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			try {
				String firstDate = Utility.getFirstDateOfCurrentYear();
				String lastDate = Utility.getLastDateCurrentMonth(new Date());
				lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);
			} catch (Exception e) {
				logger.error("getBspsalesSummaryhost-Exception", e);
			}

		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				regionList.add(mRequestModel.getRegionArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				countryList.add(mRequestModel.getCountryArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		String pos = null;
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				posList.add(mRequestModel.getPosArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0)
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(mRequestModel.getCompartmentArray()[i]);
			}
		else {
			// TODO: add the default values from user profile.
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}

		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String query = "JUP_FN_Market_dhb_BSP_Summary_host(" + lYearList + "," + lMonth + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + compartmentJson + ")";
		Object resultObj = null;
		try {
			DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);
			if (resultObj != null) {
				return (BasicDBObject) resultObj;
			} else {
				return null;
			}

		} catch (Exception e) {
			logger.error("getBspsalesSummaryhost-Exception", e);
		}

		return (BasicDBObject) resultObj;

	}

	@Override
	public BasicDBObject getBspsalesSummaryAirline(RequestModel mRequestModel) {
		ArrayList<String> lYearList = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lYearList = Utility.getYearsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			lYearList.add(Integer.toString(Utility.getCurrentYear()));
		}

		ArrayList<String> lMonth = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lMonth = Utility.getMonthsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			try {
				String firstDate = Utility.getFirstDateOfCurrentYear();
				String lastDate = Utility.getLastDateCurrentMonth(new Date());
				lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);
			} catch (Exception e) {
				logger.error("getBspsalesSummaryAirline-Exception", e);
			}

		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				regionList.add(mRequestModel.getRegionArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				countryList.add(mRequestModel.getCountryArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		String pos = null;
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				posList.add(mRequestModel.getPosArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0)
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(mRequestModel.getCompartmentArray()[i]);
			}
		else {
			// TODO: add the default values from user profile.
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}

		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String query = "JUP_FN_Market_dhb_BSP_Summary_host(" + lYearList + "," + lMonth + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + compartmentJson + ")";
		Object resultObj = null;
		try {
			DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);
			if (resultObj == null) {
				resultObj = "";
			}

		} catch (Exception e) {
			logger.error("getBspsalesSummaryAirline-Exception", e);
		}

		return (BasicDBObject) resultObj;

	}

	@Override
	public ArrayList<DBObject> getMarketBarometer(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<Integer> lYearList = new ArrayList<Integer>();
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty()
				&& mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty()) {
			lYearList = Utility.getYearsFromDateRange1(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			Calendar now = Calendar.getInstance(); // Gets the current date
			// and time
			int year = now.get(Calendar.YEAR);
			lYearList.add(year);

		}

		ArrayList<String> lMonth = new ArrayList<String>();
		ArrayList<Integer> lMonthList = new ArrayList<Integer>();
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty()
				&& mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty()) {
			lMonth = Utility.getMonthsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
			lMonthList = Utility.convertStringListToIntegerList(lMonth);
		} else {
			try {
				/*
				 * String firstDate = Utility.getFirstDateCurrentMonth(new
				 * Date()); String lastDate =
				 * Utility.getLastDateCurrentMonth(new Date()); lMonth =
				 * Utility.getMonthsFromDateRange(firstDate, lastDate);
				 */

				// TODO Remove the below code once hardcoding of current date is
				// removed and uncomment the code block above.
				String firstDate = Utility
						.getFirstDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				String lastDate = Utility
						.getLastDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);

				lMonthList = Utility.convertStringListToIntegerList(lMonth);
			} catch (Exception e) {
				logger.error("getMarketBarometer-Exception", e);
			}

		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					regionList.clear();
				} else {
					regionList.add(mRequestModel.getRegionArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCountryArray()[i])) {
					countryList.clear();
				} else {
					countryList.add(mRequestModel.getCountryArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getPosArray()[i])) {
					posList.clear();
				} else {
					posList.add(mRequestModel.getPosArray()[i]);
				}
			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getOdArray()[i])) {
					odList.clear();
				} else {
					odList.add(mRequestModel.getOdArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCompartmentArray()[i])) {
					compartmentList.clear();
				} else {
					compartmentList.add(mRequestModel.getCompartmentArray()[i]);
				}

			}
		} else {
			compartmentList.clear();
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}
		// As OD Filter is not present in dashboard screen
		String odJson = null;

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Market_dhb_Market_Barometer_detail(" + lYearList + "," + lMonth + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getMarketBarometer-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getMarketoutlook(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lYearList = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lYearList = Utility.getYearsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			lYearList.add(Integer.toString(Utility.getCurrentYear()));
		}

		ArrayList<String> lMonth = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lMonth = Utility.getMonthsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			try {
				/*
				 * String firstDate = Utility.getFirstDateOfCurrentYear();
				 * String lastDate = Utility.getLastDateCurrentMonth(new
				 * Date()); lMonth = Utility.getMonthsFromDateRange(firstDate,
				 * lastDate);
				 */

				// TODO Remove the below code once hardcoding of current date is
				// removed and uncomment the code block above.
				String firstDate = Utility
						.getFirstDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				String lastDate = Utility
						.getLastDateCurrentMonth(Utility.convertStringToDateFromat(Utility.getCurrentDate()));
				lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);
			} catch (Exception e) {
				logger.error("getMarketoutlook-Exception", e);
			}

		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					regionList.clear();
				} else {
					regionList.add(mRequestModel.getRegionArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCountryArray()[i])) {
					countryList.clear();
				} else {
					countryList.add(mRequestModel.getCountryArray()[i]);
				}
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getPosArray()[i])) {
					posList.clear();
				} else {
					posList.add(mRequestModel.getPosArray()[i]);
				}
			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getOdArray()[i])) {
					odList.clear();
				} else {
					odList.add(mRequestModel.getOdArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCompartmentArray()[i])) {
					compartmentList.clear();
				} else {
					compartmentList.add(mRequestModel.getCompartmentArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}

		String odJson = Utility.create_json(odList);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
		}

		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Market_outlook_all(" + lYearList + "," + lMonth + "," + regionJson + "," + countryJson
				+ "," + posJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getMarketoutlook-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}

		return lDataList;

	}

	//
	@Override
	public BasicDBObject getCompetitorBooking(RequestModel mRequestModel) {

		ArrayList<String> lMonth = new ArrayList<String>();
		if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
			lMonth = Utility.getMonthsFromDateRange(mRequestModel.getFromDate(), mRequestModel.getToDate());
		} else {
			try {
				String firstDate = Utility.getFirstDateCurrentMonth(new Date());
				String lastDate = Utility.getLastDateCurrentMonth(new Date());
				lMonth = Utility.getMonthsFromDateRange(firstDate, lastDate);
			} catch (Exception e) {
				logger.error("getCompetitorBooking-Exception", e);
			}

		}

		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				posList.add(mRequestModel.getPosArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> originList = new ArrayList<String>();
		if (mRequestModel.getOriginArray() != null && mRequestModel.getOriginArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOriginArray().length; i++) {
				originList.add(mRequestModel.getOriginArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> destinationList = new ArrayList<String>();
		if (mRequestModel.getDestinationArray() != null && mRequestModel.getDestinationArray().length > 0) {
			for (int i = 0; i < mRequestModel.getDestinationArray().length; i++) {
				destinationList.add(mRequestModel.getDestinationArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartment() != null && !mRequestModel.getCompartment().isEmpty())
			compartmentList.add(mRequestModel.getCompartment());
		else {
			compartmentList.add("Y");
			compartmentList.add("J");
			compartmentList.add("F");
		}

		String originJson = Utility.create_json(originList);
		if (originJson.isEmpty() || "[]".equals(originJson)) {
			originJson = null;
		}

		String destinationJson = Utility.create_json(destinationList);
		if (destinationJson.isEmpty() || "[]".equals(destinationJson)) {
			destinationJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}
		String compartmentJson = Utility.create_json(compartmentList);

		String query = "JUP_FN_Market_dhb_Comp_Booking(" + lMonth + "," + posJson + "," + originJson + ","
				+ destinationJson + "," + compartmentJson + ")";
		Object resultObj = null;
		try {
			DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);
			System.out.println("resultObj: " + ((BasicDBObject) resultObj).size());

		} catch (Exception e) {

			logger.error("getCompetitorBooking-Exception", e);
		}

		return (BasicDBObject) resultObj;

	}

	@Override
	public BasicDBObject getOppurtunities(RequestModel requestModel) {

		// This function is used for Oppurtunities under Market Dashboard.
		// function which i used for Oppurtunities is
		// JUP_FN_Market_dhb_Opportunities from demoDB
		// TODO change function name once demoDB is up

		ArrayList<String> lMonth = new ArrayList<String>();
		if (requestModel.getFromDate() != null && requestModel.getToDate() != null) {
			lMonth = Utility.getMonthsFromDateRange(requestModel.getFromDate(), requestModel.getToDate());
		}

		ArrayList<String> pos = new ArrayList<String>();
		if (requestModel.getPos() != null && !requestModel.getPos().isEmpty())
			pos.add(requestModel.getPos());
		else
			pos.add("RUH");

		ArrayList<String> origin = new ArrayList<String>();
		if (requestModel.getOrigin() != null && !requestModel.getOrigin().isEmpty())
			origin.add(requestModel.getOrigin());
		else
			origin.add("JED");

		ArrayList<String> destination1 = new ArrayList<String>();
		if (requestModel.getDestination() != null && !requestModel.getDestination().isEmpty())
			destination1.add(requestModel.getDestination());
		else
			destination1.add("CMN");

		ArrayList<String> class1 = new ArrayList<String>();
		if (requestModel.getCompartment() != null && !requestModel.getCompartment().isEmpty())
			class1.add(requestModel.getCompartment());
		else
			class1.add("Y");
		class1.add("J");

		String posJson = Utility.create_json(pos);
		String originJson = Utility.create_json(origin);
		String destination1Json = Utility.create_json(destination1);
		String compartmentJson = Utility.create_json(class1);

		String query = "JUP_FN_Market_dhb_Opportunities(" + lMonth + "," + posJson + "," + originJson + ","
				+ destination1Json + "," + compartmentJson + ")";
		Object resultObj = null;
		try {
			DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);
			System.out.println("resultObj: " + ((BasicDBObject) resultObj).size());

		} catch (Exception e) {

			logger.error("getOppurtunities-Exception", e);
		}

		return (BasicDBObject) resultObj;

	}

	@Override
	public BasicDBObject getHostBooking(RequestModel requestModel) {

		// This function is used for Host booking under Market Dashboard.
		// function which i used for host booking is
		// JUP_FN_Market_dhb_Host_Booking from demoDB

		ArrayList<String> lMonth = new ArrayList<String>();
		if (requestModel.getFromDate() != null && requestModel.getToDate() != null) {
			lMonth = Utility.getMonthsFromDateRange(requestModel.getFromDate(), requestModel.getToDate());
		}

		ArrayList<String> pos = new ArrayList<String>();
		if (requestModel.getPos() != null && !requestModel.getPos().isEmpty())
			pos.add(requestModel.getPos());
		else
			pos.add("RUH");
		pos.add("DXB");

		ArrayList<String> origin = new ArrayList<String>();
		if (requestModel.getOrigin() != null && !requestModel.getOrigin().isEmpty())
			origin.add(requestModel.getOrigin());
		else
			origin.add("JED");
		origin.add("DXB");

		ArrayList<String> destination = new ArrayList<String>();
		if (requestModel.getDestination() != null && !requestModel.getDestination().isEmpty())
			destination.add(requestModel.getDestination());
		else
			destination.add("CMN");
		destination.add("DOH");

		ArrayList<String> class1 = new ArrayList<String>();
		if (requestModel.getCompartment() != null && !requestModel.getCompartment().isEmpty())
			class1.add(requestModel.getCompartment());
		else
			class1.add("Y");
		class1.add("J");

		String posJson = Utility.create_json(pos);
		String originJson = Utility.create_json(origin);
		String destinationJson = Utility.create_json(destination);
		String compartmentJson = Utility.create_json(class1);

		String query = "JUP_FN_Market_dhb_Host_Booking(" + lMonth + "," + posJson + "," + originJson + ","
				+ destinationJson + "," + compartmentJson + ")";
		Object resultObj = null;
		try {
			DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);
			System.out.println("resultObj: " + ((BasicDBObject) resultObj).size());

		} catch (Exception e) {
			logger.error("getHostBooking-Exception", e);
		}

		return (BasicDBObject) resultObj;

	}

	@Override
	public BasicDBObject getBSPOverviewDetails(RequestModel requestModel) {

		// This function is used for Host booking under Market Dashboard.
		// function which i used for bsp overview details is
		// JUP_FN_Market_BSP_Overview from demoDB

		ArrayList<Double> yearList = new ArrayList<Double>();
		if (requestModel.getYearArray() != null && requestModel.getYearArray().length > 0) {
			for (int i = 0; i < requestModel.getYearArray().length; i++) {
				yearList.add(Double.parseDouble(requestModel.getYearArray()[i].toString()));
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<Double> monthList = new ArrayList<Double>();
		if (requestModel.getMonthArray() != null && requestModel.getMonthArray().length > 0) {
			for (int i = 0; i < requestModel.getMonthArray().length; i++) {
				monthList.add(Double.parseDouble(requestModel.getMonthArray()[i].toString()));
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (requestModel.getRegionArray() != null && requestModel.getRegionArray().length > 0) {
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				regionList.add(requestModel.getRegionArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (requestModel.getCountryArray() != null && requestModel.getCountryArray().length > 0) {
			for (int i = 0; i < requestModel.getCountryArray().length; i++) {
				countryList.add(requestModel.getCountryArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (requestModel.getPosArray() != null && requestModel.getPosArray().length > 0) {
			for (int i = 0; i < requestModel.getPosArray().length; i++) {
				posList.add(requestModel.getPosArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (requestModel.getOdArray() != null && requestModel.getPosArray().length > 0) {
			for (int i = 0; i < requestModel.getOdArray().length; i++) {
				odList.add(requestModel.getOdArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (requestModel.getCompartmentArray() != null && requestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < requestModel.getCompartmentArray().length; i++) {
				compartmentList.add(requestModel.getCompartmentArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}
		String yearJson = new flexjson.JSONSerializer().serialize(yearList);

		String monthJson = new flexjson.JSONSerializer().serialize(monthList);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}

		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}

		String odJson = Utility.create_json(odList);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
		}

		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String query = "JUP_FN_Market_BSP_Overview(" + yearJson + "," + monthJson + "," + regionJson + "," + countryJson
				+ "," + posJson + "," + odJson + "," + compartmentJson + ")";

		System.out.println("query : " + query);
		Object resultObj = null;
		try {
			DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);
			System.out.println("resultObj: " + ((BasicDBObject) resultObj).size());

		} catch (Exception e) {
			logger.error("getBSPOverviewDetails-Exception", e);
		}

		return (BasicDBObject) resultObj;

	}

	@Override
	public BasicDBObject getMarketbarometerDetails(RequestModel requestModel) {

		// This function is used for Host booking under Market Dashboard.
		// function which i used for market barometer details is
		// JUP_FN_Market_dhb_Market_Barometer_Details from demoDB
		ArrayList<Double> yearList = new ArrayList<Double>();
		if (requestModel.getYearArray() != null && requestModel.getYearArray().length > 0) {
			for (int i = 0; i < requestModel.getYearArray().length; i++) {
				yearList.add(Double.parseDouble(requestModel.getYearArray()[i].toString()));
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<Double> monthList = new ArrayList<Double>();
		if (requestModel.getMonthArray() != null && requestModel.getMonthArray().length > 0) {
			for (int i = 0; i < requestModel.getMonthArray().length; i++) {
				monthList.add(Double.parseDouble(requestModel.getMonthArray()[i].toString()));
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (requestModel.getRegionArray() != null && requestModel.getRegionArray().length > 0) {
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				regionList.add(requestModel.getRegionArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (requestModel.getCountryArray() != null && requestModel.getCountryArray().length > 0) {
			for (int i = 0; i < requestModel.getCountryArray().length; i++) {
				countryList.add(requestModel.getCountryArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (requestModel.getPosArray() != null && requestModel.getPosArray().length > 0) {
			for (int i = 0; i < requestModel.getPosArray().length; i++) {
				posList.add(requestModel.getPosArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (requestModel.getOdArray() != null && requestModel.getPosArray().length > 0) {
			for (int i = 0; i < requestModel.getOdArray().length; i++) {
				odList.add(requestModel.getOdArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (requestModel.getCompartmentArray() != null && requestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < requestModel.getCompartmentArray().length; i++) {
				compartmentList.add(requestModel.getCompartmentArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}
		String cond = null;
		if (requestModel.getCond() != null && !"null".equalsIgnoreCase(requestModel.getCond()))
			cond = requestModel.getCond();
		else
			cond = "friends";
		String yearJson = new flexjson.JSONSerializer().serialize(yearList);

		String monthJson = new flexjson.JSONSerializer().serialize(monthList);

		String condJson = new flexjson.JSONSerializer().serialize(cond);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}

		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}

		String odJson = Utility.create_json(odList);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
		}

		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}
		String query = "JUP_FN_Market_Barometer_Overview(" + yearJson + "," + monthJson + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + condJson + ")";
		System.out.println("query : " + query);
		Object resultObj = null;
		try {
			DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);
			System.out.println("resultObj: " + ((BasicDBObject) resultObj).size());

		} catch (Exception e) {
			logger.error("getMarketbarometerDetails-Exception", e);
		}

		return (BasicDBObject) resultObj;

	}

	public void querybuilder() {
		DBCollection lcollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		lcollection = db.getCollection("JUP_DB_Market_Share");

		List<BasicDBObject> andQuery = new ArrayList<BasicDBObject>();
		andQuery.add(new BasicDBObject("region", "GCC"));
		andQuery.add(new BasicDBObject("country", ""));
		andQuery.add(new BasicDBObject("pos", ""));
		andQuery.add(new BasicDBObject("compartment", ""));

		Iterable<DBObject> output = lcollection
				.aggregate(Arrays.asList((DBObject) new BasicDBObject("$match", andQuery), (DBObject) new BasicDBObject(
						"$group",
						new BasicDBObject("_id", 0).append("region", "$region").append("country", "$country")
								.append("pos", "$pos").append("od", "$od").append("compartment", "$compartment")),
						(DBObject) new BasicDBObject("$limit", 200),
						(DBObject) new BasicDBObject("$project",
								new BasicDBObject("_id", 0).append("directKey", "$views.directKey")
										.append("url", "$views.url").append("date", "$views.date"))))
				.results();

		for (DBObject dbObject : output) {
			System.out.println(dbObject);
		}
	}

	@Override
	public ArrayList<DBObject> getMarketOutlookReport(RequestModel requestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> region = null;

		if (requestModel.getRegionArray() != null && !requestModel.getRegionArray().equals("[]")) {
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				region = new ArrayList<String>();
				region.add(requestModel.getRegionArray()[i]);
			}
		}

		ArrayList<String> country = null;
		if (requestModel.getCountryArray() != null && !requestModel.getCountryArray().equals("[]")) {
			for (int i = 0; i < requestModel.getCountryArray().length; i++) {
				country = new ArrayList<String>();
				country.add(requestModel.getCountryArray()[i]);
			}
		}

		ArrayList<String> pos = null;
		if (requestModel.getPosArray() != null && !requestModel.getPosArray().equals("[]")) {
			for (int i = 0; i < requestModel.getPosArray().length; i++) {
				pos = new ArrayList<String>();
				pos.add(requestModel.getPosArray()[i]);
			}
		}

		ArrayList<String> od = null;
		if (requestModel.getOdArray() != null && !requestModel.getOdArray().equals("[]")) {
			for (int i = 0; i < requestModel.getOdArray().length; i++) {
				od = new ArrayList<String>();
				od.add(requestModel.getOdArray()[i]);
			}
		}

		ArrayList<String> compartment = null;
		if (requestModel.getCompartmentArray() != null && !requestModel.getCompartmentArray().equals("[]")) {
			for (int i = 0; i < requestModel.getCompartmentArray().length; i++) {
				compartment = new ArrayList<String>();
				compartment.add(requestModel.getCompartmentArray()[i]);
			}
		}

		ArrayList<Integer> year = new ArrayList<Integer>();
		if (requestModel.getYear() != null)
			year.add(Integer.parseInt(requestModel.getYear()));
		else
			year.add(Utility.getCurrentYear());

		ArrayList<Integer> month = new ArrayList<Integer>();
		if (requestModel.getMonth() != null)
			month.add(Integer.parseInt(requestModel.getMonth()));
		else
			month.add(Utility.getCurrentMonth());

		String regionJson = Utility.create_json(region);
		String countryJson = Utility.create_json(country);
		String posJson = Utility.create_json(pos);
		String odJson = Utility.create_json(od);
		String compartmentJson = Utility.create_json(compartment);
		String yearJson = new flexjson.JSONSerializer().serialize(year);
		String monthJson = new flexjson.JSONSerializer().serialize(month);

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Market_dhb_Market_Outllook_1(" + yearJson + "," + monthJson + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			db.eval(query);
			lCollection = db.getCollection(lCollectionName);
			DBCursor cursor = lCollection.find();

			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

			System.out.println(lDataList);
		} catch (Exception e) {
			logger.error("getMarketOutlookReport-Exception", e);
		} finally {
			if (lCollection != null && db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;

	}

	@Override
	public List<Object> getAirPortPaxTraffic(RequestModel requestModel) {
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection collection = db.getCollection("JUP_DB_Data_Airport_pax_traffic");

		BasicDBObject whereQuery = new BasicDBObject();

		DBCursor dbCursor = null;
		List<Object> PaxtrafficList = new ArrayList<Object>();
		if (requestModel.getRegionArray() != null && requestModel.getCountryArray() == null
				&& requestModel.getPosArray() == null) {
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				whereQuery.put("Region", requestModel.getRegionArray()[i]);
			}

		} else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() == null
				|| requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
						&& requestModel.getPosArray() != null) {
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				whereQuery.put("Region", requestModel.getRegionArray()[i]);
			}
			for (int i = 0; i < requestModel.getCountryArray().length; i++) {
				whereQuery.put("Country", requestModel.getCountryArray()[i]);
			}

		} else if (requestModel.getRegionArray() == null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() == null
				|| requestModel.getRegionArray() != null && requestModel.getCountryArray() == null
						&& requestModel.getPosArray() != null
				|| requestModel.getRegionArray() == null && requestModel.getCountryArray() != null
						&& requestModel.getPosArray() != null) {
			for (int i = 0; i < requestModel.getCountryArray().length; i++) {
				whereQuery.put("Country", requestModel.getCountryArray()[i]);
			}

		}

		dbCursor = collection.find(whereQuery);

		while (dbCursor.hasNext()) {

			PaxtrafficList.add(dbCursor.next());

		}

		return PaxtrafficList;
	}

	@Override
	public List<Object> getOAGUnreservedRoutes() {
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection collection = db.getCollection("JUP_DB_Data_OAG_Underserved_Routes");

		DBCursor cursor = collection.find();

		List<Object> routesList = new ArrayList<Object>();

		while (cursor.hasNext()) {

			if (Integer.parseInt(((cursor.next()).get("Rank___Route_")).toString()) <= 10) {

				routesList.add(cursor.next());
			}

		}

		return routesList;
	}

	@Override
	public List<Object> getIATAGlobalOutlook(RequestModel requestModel) {

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection collection = db.getCollection("JUP_DB_Data_IATA_Global_Outlook");

		BasicDBObject whereQuery = new BasicDBObject();

		DBCursor dbCursor = null;
		List<Object> outlookList = new ArrayList<Object>();
		if (requestModel.getRegionArray() != null) {
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				whereQuery.put("Region", requestModel.getRegionArray()[i]);
			}
		} else {

			whereQuery.put("Region", "Europe");
		}

		dbCursor = collection.find(whereQuery);

		while (dbCursor.hasNext()) {

			outlookList.add(dbCursor.next());

		}

		return outlookList;
	}

	@Override
	public List<Object> getAirlinePax(RequestModel requestModel) {

		List<Object> paxList = new ArrayList<Object>();
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection collection = db.getCollection("JUP_DB_Data_Airline_Pax");
		BasicDBObject whereQuery = new BasicDBObject();

		ArrayList<Integer> lYearList = new ArrayList<Integer>();
		ArrayList<Integer> lMonth = new ArrayList<Integer>();
		if (requestModel.getFromDate() != null && requestModel.getToDate() != null) {
			lYearList = Utility.getIntYearsFromDateRange(requestModel.getFromDate(), requestModel.getToDate());
			lMonth = Utility.getIntMonthsFromDateRange(requestModel.getFromDate(), requestModel.getToDate());
		} else {
			lYearList.add(Utility.getCurrentYear());
			lMonth.add(Utility.getCurrentMonth());
		}

		whereQuery.put("year", new BasicDBObject("$in", lYearList));
		whereQuery.put("month", new BasicDBObject("$in", lMonth));

		DBCursor cursor = collection.find(whereQuery);

		while (cursor.hasNext()) {

			paxList.add(cursor.next());

		}
		return paxList;
	}

	@Override
	public List<Object> getMarketOutlookDescription(RequestModel requestModel) {

		List<Object> marketOtlookDescriptionList = new ArrayList<Object>();

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection collection = db.getCollection("JUP_DB_Market_Outlook");

		BasicDBObject inQuery = new BasicDBObject();

		if (requestModel.getRegionArray() != null && requestModel.getCountryArray() == null
				&& requestModel.getPosArray() == null && requestModel.getOdArray() == null) {

			List<String> regionList = new ArrayList<String>();
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				regionList.add(requestModel.getRegionArray()[i]);
			}

			inQuery.put("region", new BasicDBObject("$in", regionList));

		} else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() == null && requestModel.getOdArray() == null) {

			List<String> regionList = new ArrayList<String>();
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				regionList.add(requestModel.getRegionArray()[i]);
			}

			List<String> countryList = new ArrayList<String>();
			for (int i = 0; i < requestModel.getCountryArray().length; i++) {
				countryList.add(requestModel.getCountryArray()[i]);
			}

			inQuery.put("region", new BasicDBObject("$in", regionList));
			inQuery.put("country", new BasicDBObject("$in", countryList));

		} else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() != null && requestModel.getOdArray() == null) {

			List<String> regionList = new ArrayList<String>();
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				regionList.add(requestModel.getRegionArray()[i]);
			}

			List<String> countryList = new ArrayList<String>();
			for (int i = 0; i < requestModel.getCountryArray().length; i++) {
				countryList.add(requestModel.getCountryArray()[i]);
			}

			List<String> posList = new ArrayList<String>();
			for (int i = 0; i < requestModel.getPosArray().length; i++) {
				posList.add(requestModel.getPosArray()[i]);
			}

			inQuery.put("region", new BasicDBObject("$in", regionList));
			inQuery.put("country", new BasicDBObject("$in", countryList));
			inQuery.put("pos", new BasicDBObject("$in", posList));

		} else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() != null && requestModel.getOdArray() != null) {

			List<String> regionList = new ArrayList<String>();
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				regionList.add(requestModel.getRegionArray()[i]);
			}

			List<String> countryList = new ArrayList<String>();
			for (int i = 0; i < requestModel.getCountryArray().length; i++) {
				countryList.add(requestModel.getCountryArray()[i]);
			}

			List<String> posList = new ArrayList<String>();
			for (int i = 0; i < requestModel.getPosArray().length; i++) {
				posList.add(requestModel.getPosArray()[i]);
			}

			List<String> odList = new ArrayList<String>();
			for (int i = 0; i < requestModel.getOdArray().length; i++) {
				odList.add(requestModel.getOdArray()[i]);
			}

			inQuery.put("region", new BasicDBObject("$in", regionList));
			inQuery.put("country", new BasicDBObject("$in", countryList));
			inQuery.put("pos", new BasicDBObject("$in", posList));
			inQuery.put("od", new BasicDBObject("$in", odList));

		}

		DBCursor cursor = collection.find(inQuery);

		if (cursor != null) {
			while (cursor.hasNext()) {
				marketOtlookDescriptionList.add(cursor.next());
			}
		}
		return marketOtlookDescriptionList;
	}

}
