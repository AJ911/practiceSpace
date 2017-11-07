
package com.flynava.jupiter.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.CustSegDashboardDao;
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
 * @author Surya This Class CustSegDashboardDaoImpl contains all the Dao level
 *         functions required in Distribution Customer Dashboard
 */
@Repository
public class CustSegDashboardDaoImpl implements CustSegDashboardDao {

	@Autowired
	MongoTemplate mMongoTemplateDemoDB;
	
	private static final Logger logger = Logger.getLogger(CustSegDashboardDaoImpl.class);

	@Override
	public ArrayList<DBObject> getChannelSummary(RequestModel pRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		// getting input from the request
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate().toString();
		else {
			lstartDate = Utility.getCurrentDate();
		}
		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate().toString();
		else {
			lendDate = Utility.getCurrentDate();
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++) {
				countryList.add(pRequestModel.getCountryArray()[i]);
			}
		} else {
			countryList = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++) {
				regionList.add(pRequestModel.getRegionArray()[i]);
			}
		} else {
			regionList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

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

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Cust_Seg_dhb_Channel_summary(" + startDateJson + "," + endDateJson + "," + regionJson
				+ "," + countryJson + "," + posJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		System.out.println("query :" + query);

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = new ArrayList<DBObject>();
			if (cursor != null)
				array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getChannelSummary-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		System.out.println(lDataList.toString());
		System.out.println(lDataList.size());
		return lDataList;

	}

	@Override
	public BasicDBObject getFrequentFlyers(RequestModel pRequestModel) {

		BasicDBObject FrequentFlyers = null;

		// getting input from the request
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate().toString();
		else {
			lstartDate = Utility.getFirstDateOfCurrentYear();
		}
		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate().toString();
		else {
			lendDate = Utility.getCurrentDate();
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++) {
				countryList.add(pRequestModel.getCountryArray()[i]);
			}
		} else {
			countryList = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++) {
				regionList.add(pRequestModel.getRegionArray()[i]);
			}
		} else {
			regionList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length != 0)
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
		else {
			od = null;

		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
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

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Dist_cust_seg_dhb_Frequent_Flyer(" + startDateJson + "," + endDateJson + "," + regionJson
				+ "," + countryJson + "," + posJson + "," + odJson + "," + compartmentJson + ")";
		Object resultObj = null;
		try {
			DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);
			if (resultObj == null) {
				resultObj = "";
			}
		} catch (Exception e) {
			logger.error("getFrequentFlyers-Exception", e);
		}

		return (BasicDBObject) resultObj;
	}

	@Override
	public ArrayList<DBObject> getDistributors(RequestModel pRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		// getting input from the request
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate().toString();
		else {
			lstartDate = Utility.getCurrentDate();
		}
		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate().toString();
		else {
			lendDate = Utility.getCurrentDate();
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++) {
				countryList.add(pRequestModel.getCountryArray()[i]);
			}
		} else {
			countryList = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++) {
				regionList.add(pRequestModel.getRegionArray()[i]);
			}
		} else {
			regionList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length != 0)
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
		else {
			od = null;

		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
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

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Cust_Seg_dhb_Distributors_summary(" + startDateJson + "," + endDateJson + ","
				+ regionJson + "," + countryJson + "," + posJson + "," + odJson + "," + compartmentJson + ","
				+ lTempCollectionName + ")";
		System.out.println("query :" + query);

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = new ArrayList<DBObject>();
			if (cursor != null)
				array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getDistributors-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getDistributorDetails(RequestModel pRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		// getting input from the request
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate().toString();
		else {
			lstartDate = Utility.getCurrentDate();
		}
		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate().toString();
		else {
			lendDate = Utility.getCurrentDate();
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++) {
				countryList.add(pRequestModel.getCountryArray()[i]);
			}
		} else {
			countryList = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++) {
				regionList.add(pRequestModel.getRegionArray()[i]);
			}
		} else {
			regionList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length != 0)
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
		else {
			od = null;

		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
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

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Cust_Seg_dhb_Distributors_details(" + startDateJson + "," + endDateJson + ","
				+ regionJson + "," + countryJson + "," + posJson + "," + odJson + "," + compartmentJson + ","
				+ lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = new ArrayList<DBObject>();
			if (cursor != null)
				array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getDistributorDetails-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		System.out.println(lDataList.toString());
		System.out.println(lDataList.size());
		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getChannelSummaryDirect(RequestModel pRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		// getting input from the request
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate().toString();
		else {
			lstartDate = Utility.getCurrentDate();
		}
		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate().toString();
		else {
			lendDate = Utility.getCurrentDate();
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++) {
				countryList.add(pRequestModel.getCountryArray()[i]);
			}
		} else {
			countryList = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++) {
				regionList.add(pRequestModel.getRegionArray()[i]);
			}
		} else {
			regionList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length != 0)
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
		else {
			od = null;

		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
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

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Cust_Seg_dhb_Channel_Direct(" + startDateJson + "," + endDateJson + "," + regionJson
				+ "," + countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName
				+ ")";
		System.out.println("query :" + query);

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = new ArrayList<DBObject>();
			if (cursor != null)
				array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getChannelSummaryDirect-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getChannelSummaryIndirect(RequestModel pRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		// getting input from the request
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate().toString();
		else {
			lstartDate = Utility.getCurrentDate();
		}
		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate().toString();
		else {
			lendDate = Utility.getCurrentDate();
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++) {
				countryList.add(pRequestModel.getCountryArray()[i]);
			}
		} else {
			countryList = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++) {
				regionList.add(pRequestModel.getRegionArray()[i]);
			}
		} else {
			regionList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length != 0)
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
		else {
			od = null;

		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
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

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Cust_Seg_dhb_Channel_InDirect(" + startDateJson + "," + endDateJson + "," + regionJson
				+ "," + countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName
				+ ")";
		System.out.println("query :" + query);

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = new ArrayList<DBObject>();
			if (cursor != null)
				array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getChannelSummaryIndirect-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		System.out.println(lDataList.toString());
		System.out.println(lDataList.size());
		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getCustomerSegment(RequestModel pRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		// getting input from the request
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate().toString();
		else {
			lstartDate = Utility.getCurrentDate();
		}
		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate().toString();
		else {
			lendDate = Utility.getCurrentDate();
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++) {
				countryList.add(pRequestModel.getCountryArray()[i]);
			}
		} else {
			countryList = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++) {
				regionList.add(pRequestModel.getRegionArray()[i]);
			}
		} else {
			regionList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length != 0)
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
		else {
			od = null;

		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
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

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Cust_Seg_dhb_Customer_Segment(" + startDateJson + "," + endDateJson + "," + regionJson
				+ "," + countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName
				+ ")";
		System.out.println("query :" + query);

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {

			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = new ArrayList<DBObject>();
			if (cursor != null)
				array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getCustomerSegment-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getCustomerSegmentDetails(RequestModel pRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		// getting input from the request
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate().toString();
		else {
			lstartDate = Utility.getCurrentDate();
		}
		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate().toString();
		else {
			lendDate = Utility.getCurrentDate();
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++) {
				countryList.add(pRequestModel.getCountryArray()[i]);
			}
		} else {
			countryList = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++) {
				regionList.add(pRequestModel.getRegionArray()[i]);
			}
		} else {
			regionList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length != 0)
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
		else {
			od = null;

		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
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

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Cust_Seg_dhb_Customer_Segment_details(" + startDateJson + "," + endDateJson + ","
				+ regionJson + "," + countryJson + "," + posJson + "," + odJson + "," + compartmentJson + ","
				+ lTempCollectionName + ")";
		System.out.println("query :" + query);

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = new ArrayList<DBObject>();
			if (cursor != null)
				array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getCustomerSegmentDetails-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		System.out.println(lDataList.toString());
		System.out.println(lDataList.size());
		return lDataList;
	}

	@Override
	public BasicDBObject getPriceEffectiveness(RequestModel requestModel) {
		BasicDBObject customersegmentdetails = null;

		// getting input from the request
		String lstartDate = null;
		if (requestModel.getFromDate() != null && !requestModel.getFromDate().isEmpty())
			lstartDate = requestModel.getFromDate().toString();
		else {
			lstartDate = Utility.getCurrentDate();
		}
		String lendDate = null;
		if (requestModel.getToDate() != null && !requestModel.getToDate().isEmpty())
			lendDate = requestModel.getToDate().toString();
		else {
			lendDate = Utility.getCurrentDate();
		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (requestModel.getCountryArray() != null && requestModel.getCountryArray().length > 0) {
			for (int i = 0; i < requestModel.getCountryArray().length; i++) {
				countryList.add(requestModel.getCountryArray()[i]);
			}
		} else {
			countryList = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (requestModel.getRegionArray() != null && requestModel.getRegionArray().length > 0) {
			for (int i = 0; i < requestModel.getRegionArray().length; i++) {
				regionList.add(requestModel.getRegionArray()[i]);
			}
		} else {
			regionList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (requestModel.getPosArray() != null && requestModel.getPosArray().length > 0) {

			for (int i = 0; i < requestModel.getPosArray().length; i++)

				posList.add(requestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (requestModel.getOdArray() != null && requestModel.getOdArray().length != 0)
			for (int i = 0; i < requestModel.getOdArray().length; i++)
				od.add(requestModel.getOdArray()[i]);
		else {
			od = null;

		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (requestModel.getCompartmentArray() != null && requestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < requestModel.getCompartmentArray().length; i++) {
				compartmentList.add(requestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
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

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Cust_Seg_dhb_price_effectiveness" + "(" + startDateJson + "," + endDateJson + ","
				+ regionJson + "," + countryJson + "," + posJson + "," + "" + odJson + "," + compartmentJson + ")";

		Object resultObj = null;
		try {
			DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			resultObj = db.eval(query);
			if (resultObj == null) {
				resultObj = "";
			}
		} catch (Exception e) {
			logger.error("getPriceEffectiveness-Exception", e);
		}

		return (BasicDBObject) resultObj;
	}

}
