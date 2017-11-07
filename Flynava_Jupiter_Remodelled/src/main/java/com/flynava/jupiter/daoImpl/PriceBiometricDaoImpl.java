package com.flynava.jupiter.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.PriceBiometricDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Utility;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * @author Anu Merin
 *
 */
@Repository
public class PriceBiometricDaoImpl implements PriceBiometricDao {

	@Autowired
	MongoTemplate mMongoTemplateDB;

	BasicDBObject allQuery = new BasicDBObject();
	BasicDBObject fields = new BasicDBObject();
	DBCursor cursor = null;

	private static final Logger logger = Logger.getLogger(PriceBiometricDaoImpl.class);

	/*
	 * This method calls the Mongo function "JUP_FN_Revenue_Split" which
	 * provides the data for Revenue Split grid in Price Biometric Dashboard.
	 */
	@Override
	public ArrayList<DBObject> getRevenueSplit(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();
		String lStartDate = null;
		String lEndDate = null;

		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty()) {
			lStartDate = mRequestModel.getFromDate().toString();
		} else {
			lStartDate = Utility.getCurrentDate();
		}

		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty()) {
			lEndDate = mRequestModel.getToDate().toString();
		} else {
			lEndDate = Utility.getCurrentDate();
		}

		String lastYrStartDate = mRequestModel.getLastYrFromDate();
		String lastYrEndDate = mRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lStartDate);
		dateJsonObj.put("cur_end_date", lEndDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

		/*
		 * ArrayList<String> regionList = new ArrayList<String>(); if
		 * (mRequestModel.getRegionArray() != null &&
		 * mRequestModel.getRegionArray().length > 0) { for (int i = 0; i <
		 * mRequestModel.getRegionArray().length; i++) { if
		 * ("all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
		 * regionList.clear(); } else {
		 * regionList.add(mRequestModel.getRegionArray()[i]); } } } else { //
		 * TODO: add the default values from user profile. }
		 */
		List lRegionList = null;
		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					String lColl = "JUP_DB_Region_Master";
					DBCollection lDbCollection = null;
					DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
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

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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

		String query = "JUP_FN_Price_dhb_Revenue_Split(" + dateJsonObj + "," + regionJson + "," + countryJson + ","
				+ posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		cursor = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		List<DBObject> array = null;
		try {
			Object resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			if (cursor != null)
				array = cursor.toArray();
			if (array != null) {
				for (int i = 0; i < array.size(); i++) {
					lDataList.add(array.get(i));
				}
			}

		} catch (Exception e) {

		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_SQL_graph" which provides
	 * the data for SQL graph in Price Biometric Dashboard.
	 */
	@Override
	public BasicDBObject getSQLGraph(RequestModel mRequestModel) {
		BasicDBObject lSQLGraph = null;
		String lstartDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			lstartDate = mRequestModel.getFromDate().toString();
		else

			lstartDate = Utility.getFirstDateOfCurrentYear();
		String lendDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			lendDate = mRequestModel.getToDate().toString();
		else
			lendDate = Utility.getCurrentDate();

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
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				posList.add(mRequestModel.getPosArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				odList.add(mRequestModel.getOdArray()[i]);
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

		}
		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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

		String query = "JUP_FN_SQL_graph(" + startDateJson + "," + endDateJson + "," + regionJson + "," + countryJson
				+ "," + posJson + "," + odJson + "," + compartmentJson + ")";

		logger.debug("Query" + query);
		Object resultObj = null;
		try {
			DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);

		} catch (Exception e) {
			logger.error("getSQLGraph-Exception", e);
		}
		lSQLGraph = (BasicDBObject) resultObj;

		return (BasicDBObject) resultObj;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_Strategy_performance" which
	 * provides the data for Strategy Performance Grid in Price Biometric
	 * Dashboard.
	 */
	@Override
	public ArrayList<DBObject> getStrategyPerformance(RequestModel mRequestModel) {
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();

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
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				posList.add(mRequestModel.getPosArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> originList = new ArrayList<String>();
		if (mRequestModel.getOriginArray() != null && mRequestModel.getOriginArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOriginArray().length; i++)
				originList.add(mRequestModel.getOriginArray()[i]);
		} else {
			originList = null;
		}
		ArrayList<String> destinationList = new ArrayList<String>();
		if (mRequestModel.getDestinationArray() != null && mRequestModel.getDestinationArray().length > 0) {
			for (int i = 0; i < mRequestModel.getDestinationArray().length; i++)
				destinationList.add(mRequestModel.getDestinationArray()[i]);
		} else {
			destinationList = null;
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0)
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(mRequestModel.getCompartmentArray()[i]);
			}
		else {

		}
		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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
		String originJson = Utility.create_json(originList);
		if (originJson.isEmpty() || "[]".equals(originJson)) {
			originJson = null;
		}
		String destinationJson = Utility.create_json(destinationList);
		if (destinationJson.isEmpty() || "[]".equals(destinationJson)) {
			destinationJson = null;
		}
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String query = "JUP_FN_Model_and_strategy_performance(" + regionJson + "," + countryJson + "," + posJson + ","
				+ originJson + "," + destinationJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			} else {
				cursor = null;
			}
			if (cursor != null) {
				List<DBObject> array = cursor.toArray();
				for (int i = 0; i < array.size(); i++) {
					lDatalist.add(array.get(i));
				}

			} else {
				lDatalist = new ArrayList<DBObject>();
			}

		} catch (Exception e) {
			logger.error("getStrategyPerformance-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDatalist;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_Model_performance" which
	 * provides the data for Model Performance grid in Price Biometric
	 * Dashboard->Price Performance Screen .
	 */
	@Override
	public ArrayList<DBObject> getModelPerformance(RequestModel mRequestModel) {
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();

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
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				posList.add(mRequestModel.getPosArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> originList = new ArrayList<String>();
		if (mRequestModel.getOriginArray() != null && mRequestModel.getOriginArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOriginArray().length; i++)
				originList.add(mRequestModel.getOriginArray()[i]);
		} else {
			originList = null;
		}
		ArrayList<String> destinationList = new ArrayList<String>();
		if (mRequestModel.getDestinationArray() != null && mRequestModel.getDestinationArray().length > 0) {
			for (int i = 0; i < mRequestModel.getDestinationArray().length; i++)
				destinationList.add(mRequestModel.getDestinationArray()[i]);
		} else {
			destinationList = null;
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0)
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(mRequestModel.getCompartmentArray()[i]);
			}
		else {

		}
		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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
		String originJson = Utility.create_json(originList);
		if (originJson.isEmpty() || "[]".equals(originJson)) {
			originJson = null;
		}

		String destinationJson = Utility.create_json(destinationList);
		if (destinationJson.isEmpty() || "[]".equals(destinationJson)) {
			destinationJson = null;
		}
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String query = "JUP_FN_Model_and_strategy_performance(" + regionJson + "," + countryJson + "," + posJson + ","
				+ originJson + "," + destinationJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			} else {
				cursor = null;
			}
			if (cursor != null) {
				lDatalist = (ArrayList<DBObject>) cursor.toArray();

			} else {
				lDatalist = new ArrayList<DBObject>();
			}

		} catch (Exception e) {
			logger.error("getModelPerformance-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDatalist;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_Price_Curve" which provides
	 * the data for Price curve in Price Biometric Dashboard->Price Curve Screen
	 * .
	 */
	@Override
	public ArrayList<DBObject> getPriceCurve(RequestModel mRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();
		String lStartDate = null;
		String lEndDate = null;

		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty()) {
			lStartDate = mRequestModel.getFromDate().toString();
		} else {
			lStartDate = Utility.getCurrentDate();
		}

		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty()) {
			lEndDate = mRequestModel.getToDate().toString();
		} else {
			lEndDate = Utility.getNthDate(Utility.getCurrentDate(), 90);
		}

		String lastYrStartDate = mRequestModel.getLastYrFromDate();
		String lastYrEndDate = mRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lStartDate);
		dateJsonObj.put("cur_end_date", lEndDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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
			/* odList.add("DXBDOH"); */
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

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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

		String query = "JUP_FN_Price_Curve(" + dateJsonObj + "," + regionJson + "," + countryJson + "," + posJson + ","
				+ odJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		logger.debug("Query :" + query);
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getPriceCurve-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDataList;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_Price_Performance" which
	 * provides the data for Price Performance grid in Price Biometric
	 * Dashboard->Price Performance Screen .
	 */
	@Override
	public ArrayList<DBObject> getPricePerformance(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();

		String lStartDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			lStartDate = mRequestModel.getFromDate().toString();
		else
			lStartDate = Utility.getCurrentDate();

		String lEndDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			lEndDate = mRequestModel.getToDate().toString();
		else
			lEndDate = Utility.getCurrentDate();

		String lastYrStartDate = mRequestModel.getLastYrFromDate();
		String lastYrEndDate = mRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lStartDate);
		dateJsonObj.put("cur_end_date", lEndDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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

		// CurrencyList
		ArrayList<String> currencyList = new ArrayList<String>();
		if (mRequestModel.getCurrencyArray() != null && mRequestModel.getCurrencyArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCurrencyArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCurrencyArray()[i])) {
					currencyList.clear();
				} else {
					currencyList.add(mRequestModel.getCurrencyArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		// Farebasis
		String lFareBasisCode = "";
		if (mRequestModel.getFarebasis() != null && !mRequestModel.getFarebasis().isEmpty()) {
			lFareBasisCode = new flexjson.JSONSerializer().serialize(mRequestModel.getFarebasis());
		} else {
			lFareBasisCode = null;
		}

		// RBD
		ArrayList<String> rbdList = new ArrayList<String>();
		if (mRequestModel.getRbdArray() != null && mRequestModel.getRbdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRbdArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRbdArray()[i])) {
					rbdList.clear();
				} else {
					rbdList.add(mRequestModel.getRbdArray()[i]);
				}

			}
		} else {
			// TODO User Profile Data
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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

		String currencyJson = Utility.create_json(currencyList);
		if (currencyJson.isEmpty() || "[]".equals(currencyJson)) {
			currencyJson = null;
		}

		String rbdJson = Utility.create_json(rbdList);
		if (rbdJson.isEmpty() || "[]".equals(rbdJson)) {
			rbdJson = null;
		}

		String query = "JUP_FN_Price_Performance(" + dateJsonObj + "," + regionJson + "," + countryJson + "," + posJson
				+ "," + odJson + "," + compartmentJson + "," + currencyJson + "," + lFareBasisCode + "," + rbdJson + ","
				+ lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		cursor = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		List<DBObject> array = null;
		try {

			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			if (cursor != null)
				array = cursor.toArray();
			if (array != null) {
				for (int i = 0; i < array.size(); i++) {
					lDataList.add(array.get(i));
				}
			}

			System.out.println(lDataList);

		} catch (Exception e) {
			logger.error("getPricePerformance-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_Price_Characteristics_Major_Agencies" which provides the data for
	 * Price Characteristics Major Agencies grid in Price Biometric
	 * Dashboard->Price Characteristics Screen. .
	 */
	@Override
	public ArrayList<DBObject> getPriceCharac_MajorAgencies(RequestModel mRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();

		String lStartDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			lStartDate = mRequestModel.getFromDate().toString();
		else
			lStartDate = Utility.getCurrentDate();
		String lEndDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			lEndDate = mRequestModel.getToDate().toString();
		else
			lEndDate = Utility.getCurrentDate();

		String lastYrStartDate = mRequestModel.getLastYrFromDate();
		String lastYrEndDate = mRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lStartDate);
		dateJsonObj.put("cur_end_date", lEndDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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

		String query = "JUP_FN_Price_Characteristics_Major_Agencies(" + dateJsonObj + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		cursor = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		List<DBObject> array = null;
		try {

			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			if (cursor != null)
				array = cursor.toArray();
			if (cursor != null) {
				for (int i = 0; i < array.size(); i++) {
					lDataList.add(array.get(i));
				}
			}

		} catch (Exception e) {
			logger.error("getPriceCharac_MajorAgencies-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_Price_Characteristics_price_history" which provides the data for
	 * Price Characteristics Price History grid in Price Biometric
	 * Dashboard->Price Characteristics Screen.
	 */
	@Override
	public ArrayList<DBObject> getPriceCharac_PriceHistory(RequestModel mRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		BasicDBObject lPriceChar_PriceHistory = null;
		JSONObject dateJsonObj = new JSONObject();

		String startDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			startDate = mRequestModel.getFromDate();

		else {
			startDate = Utility.getCurrentDate();

		}

		String endDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			endDate = mRequestModel.getToDate();
		else {
			endDate = Utility.getCurrentDate();

		}

		String lastYrStartDate = mRequestModel.getLastYrFromDate();
		String lastYrEndDate = mRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", startDate);
		dateJsonObj.put("cur_end_date", endDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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

		String query = "JUP_FN_Price_Characteristics_price_history(" + dateJsonObj + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		cursor = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		List<DBObject> array = null;
		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			if (cursor != null)
				array = cursor.toArray();
			if (array != null) {
				for (int i = 0; i < array.size(); i++) {
					lDataList.add(array.get(i));
				}
			}

		} catch (Exception e) {
			logger.error("getPriceCharac_PriceHistory-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_Price_Characteristics_customer_segment" which provides the data
	 * for Price Characteristics Customer Segment grid in Price Biometric
	 * Dashboard->Price Characteristics Screen.
	 */
	@Override
	public ArrayList<DBObject> getPriceCharac_CustomerSegment(RequestModel mRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();

		String startDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			startDate = mRequestModel.getFromDate();

		else {
			startDate = Utility.getCurrentDate();
			mRequestModel.setFromDate(startDate);
		}

		String endDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			endDate = mRequestModel.getToDate();
		else {
			endDate = Utility.getCurrentDate();
			mRequestModel.setToDate(endDate);
		}

		String lastYrStartDate = mRequestModel.getLastYrFromDate();
		String lastYrEndDate = mRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", startDate);
		dateJsonObj.put("cur_end_date", endDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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

		String query = "JUP_FN_Price_Characteristics_customersegment(" + dateJsonObj + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		cursor = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		List<DBObject> array = null;
		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			if (cursor != null)
				array = cursor.toArray();
			if (cursor != null) {
				for (int i = 0; i < array.size(); i++) {
					lDataList.add(array.get(i));
				}
			}

		} catch (Exception e) {
			logger.error("getPriceCharac_CustomerSegment-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_Price_Characteristics_fare_brand" which provides the data for
	 * Price Characteristics Fare Brand grid in Price Biometric Dashboard->Price
	 * Characteristics Screen.
	 */
	@Override
	public ArrayList<DBObject> getPriceCharac_FareBrand(RequestModel mRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();

		String startDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			startDate = mRequestModel.getFromDate();

		else {
			startDate = Utility.getCurrentDate();
			mRequestModel.setFromDate(startDate);
		}

		String endDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			endDate = mRequestModel.getToDate();
		else {
			endDate = Utility.getCurrentDate();
			mRequestModel.setToDate(endDate);
		}

		String lastYrStartDate = mRequestModel.getLastYrFromDate();
		String lastYrEndDate = mRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", startDate);
		dateJsonObj.put("cur_end_date", endDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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

		String query = "JUP_FN_Price_Characteristics_fare_brand(" + dateJsonObj + "," + regionJson + "," + countryJson
				+ "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getPriceCharac_FareBrand-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_Price_Characteristics_Channels" which provides the data for Price
	 * Characteristics Channels grid in Price Biometric Dashboard->Price
	 * Characteristics Screen.
	 */
	@Override
	public ArrayList<DBObject> getPriceCharac_Channels(RequestModel mRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		JSONObject dateJsonObj = new JSONObject();

		String startDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			startDate = mRequestModel.getFromDate();

		else {
			startDate = Utility.getCurrentDate();
			mRequestModel.setFromDate(startDate);
		}

		String endDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			endDate = mRequestModel.getToDate();
		else {
			endDate = Utility.getCurrentDate();
			mRequestModel.setToDate(endDate);
		}

		String lastYrStartDate = mRequestModel.getLastYrFromDate();
		String lastYrEndDate = mRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", startDate);
		dateJsonObj.put("cur_end_date", endDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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

		String query = "JUP_FN_Price_Characteristics_Channels(" + dateJsonObj + "," + regionJson + "," + countryJson
				+ "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		cursor = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		List<DBObject> array = null;
		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			if (cursor != null) {
				array = cursor.toArray();
			}
			if (array != null) {
				for (int i = 0; i < array.size(); i++) {
					lDataList.add(array.get(i));
				}
			}
		} catch (Exception e) {
			logger.error("getPriceCharac_Channels-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_Price_quote" which provides
	 * the data for Price Performance grid in Price Biometric Dashboard->Price
	 * Quote Screen .
	 */
	@Override
	public ArrayList<DBObject> getPriceQuote(RequestModel mRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		JSONObject dateJsonObj = new JSONObject();

		String startDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			startDate = mRequestModel.getFromDate();

		else {
			startDate = Utility.getCurrentDate();
			mRequestModel.setFromDate(startDate);
		}

		String endDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			endDate = mRequestModel.getToDate();
		else {
			endDate = Utility.getCurrentDate();
			mRequestModel.setToDate(endDate);
		}

		String lastYrStartDate = mRequestModel.getLastYrFromDate();
		String lastYrEndDate = mRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", startDate);
		dateJsonObj.put("cur_end_date", endDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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
		ArrayList<String> currencyList = new ArrayList<String>();
		if (mRequestModel.getCurrencyArray() != null && mRequestModel.getCurrencyArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCurrencyArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getCurrencyArray()[i])) {
					currencyList.clear();
				} else {
					currencyList.add(mRequestModel.getCurrencyArray()[i]);
				}
			}
		} else {
			// TODO User Profile Data
		}
		ArrayList<String> farebasisList = new ArrayList<String>();
		if (mRequestModel.getFarebasisArray() != null && mRequestModel.getFarebasisArray().length > 0) {
			for (int i = 0; i < mRequestModel.getFarebasisArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getFarebasisArray()[0])) {
					farebasisList.clear();
				} else {
					farebasisList.add(mRequestModel.getFarebasisArray()[0]);
				}
			}
		} else {
			// TODO User Profile Data
		}
		ArrayList<String> rbdList = new ArrayList<String>();
		if (mRequestModel.getRbdArray() != null && mRequestModel.getRbdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRbdArray().length; i++) {
				if ("all".equalsIgnoreCase(mRequestModel.getRbdArray()[0])) {
					rbdList.clear();
				} else {
					rbdList.add(mRequestModel.getRbdArray()[0]);
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

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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
		String farebasisJson = Utility.create_json(farebasisList);
		if (farebasisJson.isEmpty() || "[]".equals(farebasisJson)) {
			farebasisJson = null;
		}
		String rbdJson = Utility.create_json(rbdList);
		if (rbdJson.isEmpty() || "[]".equals(rbdJson)) {
			rbdJson = null;
		}
		String currencyJson = Utility.create_json(currencyList);
		if (currencyJson.isEmpty() || "[]".equals(currencyJson)) {
			currencyJson = null;
		}

		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String query = "JUP_FN_Price_quote(" + dateJsonObj + "," + regionJson + "," + countryJson + "," + posJson + ","
				+ odJson + "," + compartmentJson + "," + currencyJson + "," + farebasisJson + "," + rbdJson + ","
				+ lTempCollectionName + ")";
		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getPriceQuote-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_Price_Heat_Map_OD_Wise"
	 * which provides the data for Price Heat Map grid in Price Biometric
	 * Dashboard->Price Heat Map OD Grid .
	 */
	@Override
	public ArrayList<DBObject> getPriceHeatMapODChannel(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();
		String lStartDate = null;
		String lEndDate = null;

		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty()) {
			lStartDate = mRequestModel.getFromDate().toString();
		} else {
			lStartDate = Utility.getCurrentDate();
		}

		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty()) {
			lEndDate = mRequestModel.getToDate().toString();
		} else {
			lEndDate = Utility.getCurrentDate();
		}

		String lastYrStartDate = mRequestModel.getLastYrFromDate();
		String lastYrEndDate = mRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lStartDate);
		dateJsonObj.put("cur_end_date", lEndDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				posList.add(mRequestModel.getPosArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				odList.add(mRequestModel.getOdArray()[i]);
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

		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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

		String query = "JUP_FN_Price_Heat_Map(" + dateJsonObj + "," + regionJson + "," + countryJson + "," + posJson
				+ "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getPriceHeatMapODChannel-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDataList;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_Price_Heat_Map_Segment_Wise"
	 * which provides the data for Price Heat Map grid in Price Biometric
	 * Dashboard->Price Heat Map Customer Segment Grid .
	 */
	@Override
	public BasicDBObject getPriceHeatMap_CustomerSegment(RequestModel mRequestModel) {
		BasicDBObject lPriceHeatMap_CustomerSegment = null;
		BasicDBObject allQuery = new BasicDBObject();
		BasicDBObject fields = new BasicDBObject();
		DBCursor cursor = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection lODCollection = db.getCollection("JUP_DB_OD_Master");
		DBCollection lPOSCollection = db.getCollection("JUP_DB_Region_Master");

		String lstartDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			lstartDate = mRequestModel.getFromDate().toString();
		else
			lstartDate = Utility.getFirstDateOfCurrentYear();
		String lendDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			lendDate = mRequestModel.getToDate().toString();
		else
			lendDate = Utility.getCurrentDate();

		ArrayList<String> pos = new ArrayList<String>();
		if (mRequestModel.getPos() != null && !mRequestModel.getPos().isEmpty())
			pos.add(mRequestModel.getPos());
		else
			fields.put("POS_CD", 1);
		cursor = lPOSCollection.find(allQuery, fields);
		while (cursor.hasNext()) {
			String lPos = cursor.next().get("POS_CD").toString();
			if (lPos != null || !lPos.equals("null")) {
				pos.add(lPos);
			}
		}

		ArrayList<String> origin = new ArrayList<String>();
		if (mRequestModel.getOrigin() != null && !mRequestModel.getOrigin().isEmpty())
			origin.add(mRequestModel.getOrigin());
		else
			fields.put("origin", 1);
		cursor = lODCollection.find(allQuery, fields);
		while (cursor.hasNext()) {
			origin.add(cursor.next().get("origin").toString());
		}

		ArrayList<String> destination = new ArrayList<String>();
		if (mRequestModel.getDestination() != null && !mRequestModel.getDestination().isEmpty())
			destination.add(mRequestModel.getDestination());
		else
			fields.put("destination", 1);
		cursor = lODCollection.find(allQuery, fields);
		while (cursor.hasNext()) {

			destination.add(cursor.next().get("destination").toString());
		}

		ArrayList<String> lCompartment = new ArrayList<String>();
		if (mRequestModel.getCompartment() != null && !mRequestModel.getCompartment().isEmpty())
			lCompartment.add(mRequestModel.getCompartment());
		else
			lCompartment.add("Y");
		lCompartment.add("J");
		lCompartment.add("F");

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		String posJson = Utility.create_json(pos);
		String originJson = Utility.create_json(origin);
		String destinationJson = Utility.create_json(destination);
		String compartmentJson = Utility.create_json(lCompartment);

		String query = "JUP_FN_Price_Heat_Map_Segment_Wise(" + startDateJson + "," + endDateJson + "," + posJson + ","
				+ originJson + "," + destinationJson + "," + compartmentJson + ")";
		Object resultObj = null;
		try {

			resultObj = db.eval(query);

		} catch (Exception e) {
			logger.error("getPriceHeatMap_CustomerSegment-Exception", e);
		}
		lPriceHeatMap_CustomerSegment = (BasicDBObject) resultObj;

		return (BasicDBObject) resultObj;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_Price_Heat_Map_Channel_Wise"
	 * which provides the data for Price Heat Map grid in Price Biometric
	 * Dashboard->Price Heat Map Channel Grid .
	 */
	@Override
	public BasicDBObject getPriceHeatMapChannels(RequestModel mRequestModel) {
		BasicDBObject lPriceHeatMap_Channels = null;
		BasicDBObject allQuery = new BasicDBObject();
		BasicDBObject fields = new BasicDBObject();
		DBCursor cursor = null;
		DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection lODCollection = db.getCollection("JUP_DB_OD_Master");
		DBCollection lPOSCollection = db.getCollection("JUP_DB_Region_Master");

		String lstartDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			lstartDate = mRequestModel.getFromDate().toString();
		else
			lstartDate = Utility.getFirstDateOfCurrentYear();
		String lendDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			lendDate = mRequestModel.getToDate().toString();
		else
			lendDate = Utility.getCurrentDate();

		ArrayList<String> pos = new ArrayList<String>();
		if (mRequestModel.getPos() != null && !mRequestModel.getPos().isEmpty())
			pos.add(mRequestModel.getPos());
		else
			fields.put("POS_CD", 1);
		cursor = lPOSCollection.find(allQuery, fields);
		while (cursor.hasNext()) {
			String lPos = cursor.next().get("POS_CD").toString();
			if (lPos != null || !lPos.equals("null")) {
				pos.add(lPos);
			}
		}

		ArrayList<String> origin = new ArrayList<String>();
		if (mRequestModel.getOrigin() != null && !mRequestModel.getOrigin().isEmpty())
			origin.add(mRequestModel.getOrigin());
		else
			fields.put("origin", 1);
		cursor = lODCollection.find(allQuery, fields);
		while (cursor.hasNext()) {
			origin.add(cursor.next().get("origin").toString());
		}

		ArrayList<String> destination = new ArrayList<String>();
		if (mRequestModel.getDestination() != null && !mRequestModel.getDestination().isEmpty())
			destination.add(mRequestModel.getDestination());
		else
			fields.put("destination", 1);
		cursor = lODCollection.find(allQuery, fields);
		while (cursor.hasNext()) {

			destination.add(cursor.next().get("destination").toString());
		}

		ArrayList<String> lCompartment = new ArrayList<String>();
		if (mRequestModel.getCompartment() != null && !mRequestModel.getCompartment().isEmpty())
			lCompartment.add(mRequestModel.getCompartment());
		else
			lCompartment.add("Y");
		lCompartment.add("J");
		lCompartment.add("F");

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		String posJson = Utility.create_json(pos);
		String originJson = Utility.create_json(origin);
		String destinationJson = Utility.create_json(destination);
		String compartmentJson = Utility.create_json(lCompartment);

		String query = "JUP_FN_Price_Heat_Map_Channel_Wise(" + startDateJson + "," + endDateJson + "," + posJson + ","
				+ originJson + "," + destinationJson + "," + compartmentJson + ")";
		Object resultObj = null;
		try {

			resultObj = db.eval(query);

		} catch (Exception e) {
			logger.error("getPriceHeatMapChannels-Exception", e);
		}
		lPriceHeatMap_Channels = (BasicDBObject) resultObj;

		return (BasicDBObject) resultObj;
	}

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_Heat_Map_Effective_Ineffective_fares" which provides the data for
	 * Price Heat Map grid in Price Biometric Dashboard->Price Heat Map Fares
	 * Grid .
	 */
	@Override
	public BasicDBObject getPriceHeatMapFares(RequestModel mRequestModel) {
		BasicDBObject lPriceHeatMap_Fares = null;
		String lstartDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			lstartDate = mRequestModel.getFromDate().toString();
		else
			lstartDate = "2016-09-20";
		String lendDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			lendDate = mRequestModel.getToDate().toString();
		else
			lendDate = "2016-10-13";

		ArrayList<String> pos = new ArrayList<String>();
		if (mRequestModel.getPos() != null && !mRequestModel.getPos().isEmpty())
			pos.add(mRequestModel.getPos());
		else
			pos.add("KBL");

		ArrayList<String> origin = new ArrayList<String>();
		if (mRequestModel.getOrigin() != null && !mRequestModel.getOrigin().isEmpty())
			origin.add(mRequestModel.getOrigin());
		else
			origin.add("KBL");

		ArrayList<String> destination = new ArrayList<String>();
		if (mRequestModel.getDestination() != null && !mRequestModel.getDestination().isEmpty())
			destination.add(mRequestModel.getDestination());
		else
			destination.add("KWI");

		ArrayList<String> compartment = new ArrayList<String>();
		if (mRequestModel.getCompartment() != null && !mRequestModel.getCompartment().isEmpty())
			compartment.add(mRequestModel.getCompartment());
		else
			compartment.add("Y");

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		String posJson = Utility.create_json(pos);
		String originJson = Utility.create_json(origin);
		String destinationJson = Utility.create_json(destination);
		String compartmentJson = Utility.create_json(compartment);

		String query = "JUP_FN_Heat_Map_Effective_Ineffective_fares(" + startDateJson + "," + endDateJson + ","
				+ posJson + "," + originJson + "," + destinationJson + "," + compartmentJson + ")";
		Object resultObj = null;
		try {
			DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);

		} catch (Exception e) {
			logger.error("getPriceHeatMapFares-Exception", e);
		}
		lPriceHeatMap_Fares = (BasicDBObject) resultObj;
		return (BasicDBObject) resultObj;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_Analyst_performance" which
	 * provides the data for Analyst Performance grid in Price Biometric
	 * Dashboard->Analyst Performance Grid .
	 */
	@Override
	public BasicDBObject getAnalystPerformance(RequestModel mRequestModel) {
		BasicDBObject lAnalystPerformance = null;
		String lstartDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			lstartDate = mRequestModel.getFromDate().toString();
		else

			lstartDate = Utility.getFirstDateOfCurrentYear();
		String lendDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			lendDate = mRequestModel.getToDate().toString();
		else
			lendDate = Utility.getCurrentDate();

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
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				posList.add(mRequestModel.getPosArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				odList.add(mRequestModel.getOdArray()[i]);
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

		}
		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || "[]".equals(regionJson)) {
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
		String query = "JUP_FN_Analyst_performance(" + startDateJson + "," + endDateJson + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + ")";
		Object resultObj = null;
		logger.debug("Query" + query);
		try {
			DB db = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);

		} catch (Exception e) {
			logger.error("getAnalystPerformance-Exception", e);
		}
		lAnalystPerformance = (BasicDBObject) resultObj;

		return (BasicDBObject) resultObj;
	}

	@Override
	public ArrayList<DBObject> getVLYRPoc(RequestModel pRequestModel) {
		JSONObject lDateJsonObj = new JSONObject();
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String lStartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lStartDate = pRequestModel.getFromDate();

		else {
			lStartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lStartDate);
		}

		String lEndDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lEndDate = pRequestModel.getToDate();
		else {
			lEndDate = Utility.getNthDate(lStartDate, 7);
			pRequestModel.setToDate(lEndDate);
		}

		String lLastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();
		
		String lFromSnapDate = pRequestModel.getFromSnapDate();
		String lToSnapDate = pRequestModel.getToSnapDate();

		lDateJsonObj.put("cur_start_date", lStartDate);
		lDateJsonObj.put("cur_end_date", lEndDate);

		if (lLastYrStartDate == null)
			lDateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			lDateJsonObj.put("last_start_date", lLastYrStartDate);

		if (lastYrEndDate == null)
			lDateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			lDateJsonObj.put("last_end_date", lastYrEndDate);
		
		if (lFromSnapDate == null)
			lDateJsonObj.put("cur_snap_date", JSONObject.NULL);
		else
			lDateJsonObj.put("cur_snap_date", lFromSnapDate);

		if (lToSnapDate == null)
			lDateJsonObj.put("last_snap_date", JSONObject.NULL);
		else
			lDateJsonObj.put("last_snap_date", lToSnapDate);

		ArrayList<String> lRegionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length != 0)
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++)
				lRegionList.add(pRequestModel.getRegionArray()[i]);
		else {

			if (pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() == null
					&& pRequestModel.getCountryArray() == null && pRequestModel.getCompartmentArray() != null
					|| pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() == null
							&& pRequestModel.getCountryArray() == null && pRequestModel.getCompartmentArray() == null) {
				
				MainDashboardDaoImpl lObj=new MainDashboardDaoImpl();
				String[] lRegionArray =lObj.getRegion(pRequestModel, "AllRegions", pRequestModel.getRegionArray());
				Set<String> lRegionSet = new HashSet<String>();
				for (String s : lRegionArray) {
					lRegionSet.add(s);
				}

				String[] lAllRegionsArray = new String[lRegionSet.size()];

				int i = 0;

				Iterator<String> it = lRegionSet.iterator();
				while (it.hasNext()) {
					String s = it.next();

					lAllRegionsArray[i++] = s;
				}

				for (int r = 0; r < lAllRegionsArray.length; r++)
					lRegionList.add(lAllRegionsArray[r]);

			} else {
				lRegionList = null;
			}

		}

		ArrayList<String> lCountryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length != 0)
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++)
				lCountryList.add(pRequestModel.getCountryArray()[i]);
		else
			lCountryList = null;

		ArrayList<String> lPosList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length != 0) {
			for (int i = 0; i < pRequestModel.getPosArray().length; i++)
				lPosList.add(pRequestModel.getPosArray()[i]);
		} else
			lPosList = null;

		ArrayList<String> lOdList = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length != 0)
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOdList.add(pRequestModel.getOdArray()[i]);
		else
			lOdList = null;

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length != 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();

		}

		String lRegionJson = Utility.create_json(lRegionList);
		String lCountryJson = Utility.create_json(lCountryList);
		String lPosJson = Utility.create_json(lPosList);
		String lOdJson = Utility.create_json(lOdList);
		String lCompartmentJson = Utility.create_json(lCompartmentList);

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String lQuery = "JUP_FN_VLYR_TestingFM_Booking(" + lDateJsonObj + "," + lRegionJson + "," + lCountryJson + "," + lPosJson
				+ "," + lOdJson + "," + lCompartmentJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			lDb.eval(lQuery);
			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> cursorArray = lCursor.toArray();
			for (int i = 0; i < cursorArray.size(); i++) {
				lDataList.add(cursorArray.get(i));
			}

			System.out.println(lDataList);

		} catch (Exception e) {
			logger.error("getBookings-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

}
