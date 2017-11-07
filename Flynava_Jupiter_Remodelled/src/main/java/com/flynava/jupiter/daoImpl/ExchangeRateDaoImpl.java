package com.flynava.jupiter.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.ExchangeRateDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.UserDetails;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Utility;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * @author Surya This Class ExchangeRateDaoImpl contains all the Dao level
 *         functions required in Exchange Rate Dashboard
 */
@Repository
public class ExchangeRateDaoImpl implements ExchangeRateDao {

	@Autowired
	MongoTemplate mMongoTemplateDemoDB;

	DBCursor cursor = null;

	private static final Logger logger = Logger.getLogger(ExchangeRateDaoImpl.class);

	@Override
	public ArrayList<DBObject> getCurrencyTable(RequestModel pRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

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

		ArrayList<String> currencyList = new ArrayList<String>();
		if (pRequestModel.getCurrencyArray() != null && pRequestModel.getCurrencyArray().length > 0) {

			for (int i = 0; i < pRequestModel.getCurrencyArray().length; i++)

				currencyList.add(pRequestModel.getCurrencyArray()[i]);
		} else {

			currencyList = null;
		}

		ArrayList<String> rbdList = new ArrayList<String>();
		if (pRequestModel.getRbdArray() != null && pRequestModel.getRbdArray().length > 0) {

			for (int i = 0; i < pRequestModel.getRbdArray().length; i++)

				rbdList.add(pRequestModel.getRbdArray()[i]);
		} else {

			rbdList = null;
		}

		ArrayList<String> farebasisList = new ArrayList<String>();
		if (pRequestModel.getFarebasisArray() != null && pRequestModel.getFarebasisArray().length > 0) {

			for (int i = 0; i < pRequestModel.getFarebasisArray().length; i++)

				farebasisList.add(pRequestModel.getFarebasisArray()[i]);
		} else {

			farebasisList = null;
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

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);

			}
		}

		else {
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
		String currencyJson = Utility.create_json(currencyList);
		if (currencyJson.isEmpty() || "[]".equals(currencyJson)) {
			currencyJson = null;
		}

		String farebasisJson = Utility.create_json(farebasisList);
		if (farebasisJson.isEmpty() || "[]".equals(farebasisJson)) {
			farebasisJson = null;
		}

		String rbdJson = Utility.create_json(rbdList);
		if (rbdJson.isEmpty() || "[]".equals(rbdJson)) {
			rbdJson = null;
		}

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Exchange_dhb_Currency_table(" + startDateJson + "," + endDateJson + "," + regionJson
				+ "," + countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + currencyJson + ","
				+ farebasisJson + "," + rbdJson + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		System.out.println("query :" + query);

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

			System.out.println(lDataList);

		} catch (Exception e) {
			logger.error("getCurrencyTable-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getYQTable(RequestModel pRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

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

		ArrayList<String> currencyList = new ArrayList<String>();
		if (pRequestModel.getCurrencyArray() != null && pRequestModel.getCurrencyArray().length > 0) {

			for (int i = 0; i < pRequestModel.getCurrencyArray().length; i++)

				currencyList.add(pRequestModel.getCurrencyArray()[i]);
		} else {

			currencyList = null;
		}

		ArrayList<String> rbdList = new ArrayList<String>();
		if (pRequestModel.getRbdArray() != null && pRequestModel.getRbdArray().length > 0) {

			for (int i = 0; i < pRequestModel.getRbdArray().length; i++)

				rbdList.add(pRequestModel.getRbdArray()[i]);
		} else {

			rbdList = null;
		}

		ArrayList<String> farebasisList = new ArrayList<String>();
		if (pRequestModel.getFarebasisArray() != null && pRequestModel.getFarebasisArray().length > 0) {

			for (int i = 0; i < pRequestModel.getFarebasisArray().length; i++)

				farebasisList.add(pRequestModel.getFarebasisArray()[i]);
		} else {

			farebasisList = null;
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

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);

			}
		}

		else {
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

		String currencyJson = Utility.create_json(currencyList);
		if (currencyJson.isEmpty() || "[]".equals(currencyJson)) {
			currencyJson = null;
		}

		String farebasisJson = Utility.create_json(farebasisList);
		if (farebasisJson.isEmpty() || "[]".equals(farebasisJson)) {
			farebasisJson = null;
		}

		String rbdJson = Utility.create_json(rbdList);
		if (rbdJson.isEmpty() || "[]".equals(rbdJson)) {
			rbdJson = null;
		}

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}

		String query = "JUP_FN_Exchange_dhb_YQ_Table(" + startDateJson + "," + endDateJson + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + currencyJson + ","
				+ farebasisJson + "," + rbdJson + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

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
			logger.error("getYQTable-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		System.out.println("data :" + lDataList.toString());

		return lDataList;

	}

	@Override
	public UserDetails getUserDetails(RequestModel requestModel) {
		DB db = mMongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection collection = db.getCollection("JUP_DB_User_Profile");

		BasicDBObject whereQuery = new BasicDBObject();
		String user;

		if (requestModel.getUser() != null && !requestModel.getUser().isEmpty()) {
			user = requestModel.getUser();
		} else {
			user = "mahesh";

		}
		requestModel.setUser(user);
		whereQuery.put("user", requestModel.getUser());
		DBCursor cursor = collection.find(whereQuery);
		JSONArray Od = null;
		JSONArray Pos = null;
		JSONArray Country = null;
		JSONArray Region = null;
		JSONArray Compartment = null;
		UserDetails userDetails = null;
		while (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			userDetails = new UserDetails();
			userDetails.setUser(dbObj.get("user").toString());
			userDetails.setSig_od(dbObj.get("sig_od").toString());
			JSONArray country = new JSONArray(dbObj.get("country").toString());
			for (int i = 0; i < country.length(); i++) {
				if (country != null && !"null".equalsIgnoreCase(country.get(i).toString())) {
					Country = new JSONArray(dbObj.get("country").toString());
				}
			}
			userDetails.setCountry(Country);
			// userDetails.setOd(od);(dbObj.get("od").toString());
			JSONArray od = new JSONArray(dbObj.get("od").toString());
			for (int i = 0; i < od.length(); i++) {
				if (od != null && !"null".equalsIgnoreCase(od.get(i).toString())) {
					Od = new JSONArray(dbObj.get("od").toString());
				}
			}

			userDetails.setOd(Od);
			// userDetails.setPos(dbObj.get("pos").toString());
			JSONArray pos = new JSONArray(dbObj.get("pos").toString());
			for (int i = 0; i < pos.length(); i++) {
				if (pos != null && !"null".equalsIgnoreCase(pos.get(i).toString())) {
					Pos = new JSONArray(dbObj.get("pos").toString());
				}
			}
			userDetails.setPos(Pos);
			userDetails.setLevel(dbObj.get("level").toString());
			JSONArray region = new JSONArray(dbObj.get("region").toString());
			for (int i = 0; i < region.length(); i++) {
				if (region != null && !"null".equalsIgnoreCase(region.get(i).toString())) {
					Region = new JSONArray(dbObj.get("region").toString());
				}
			}

			userDetails.setRegion(Region);
			JSONArray compartment = new JSONArray(dbObj.get("compartment").toString());
			for (int i = 0; i < compartment.length(); i++) {
				if (compartment != null && !"null".equalsIgnoreCase(compartment.get(i).toString())) {
					Compartment = new JSONArray(dbObj.get("compartment").toString());
				}
			}
			userDetails.setCompartment(Compartment);

		}
		return userDetails;
	}

}
