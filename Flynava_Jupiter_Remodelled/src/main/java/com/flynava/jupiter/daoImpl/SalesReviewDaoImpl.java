package com.flynava.jupiter.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.SalesReviewDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Utility;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class SalesReviewDaoImpl implements SalesReviewDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	DBCursor cursor = null;
	
	private static final Logger logger = Logger.getLogger(SalesReviewDaoImpl.class);

	@Override
	public ArrayList<DBObject> getTopTenOd(RequestModel pRequestModel) {
		// This function for Top ten od in sales review.

		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate().toString();
		else

			lstartDate = Utility.getCurrentDate();
		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate().toString();
		else
			lendDate = Utility.getCurrentDate();

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {

			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();

		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				od.add(pRequestModel.getOdArray()[i]);
			}
		} else {

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
		ArrayList<String> rbdList = new ArrayList<String>();
		if (pRequestModel.getRbdArray() != null && pRequestModel.getRbdArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRbdArray().length; i++)
				rbdList.add(pRequestModel.getRbdArray()[i]);
		} else {
			rbdList = null;
		}
		ArrayList<String> fareBasislist = new ArrayList<String>();
		if (pRequestModel.getFarebasisArray() != null && pRequestModel.getFarebasisArray().length > 0) {
			for (int i = 0; i < pRequestModel.getFarebasisArray().length; i++)
				fareBasislist.add(pRequestModel.getFarebasisArray()[i]);
		} else {
			fareBasislist = null;
		}
		ArrayList<String> currencylist = new ArrayList<String>();
		if (pRequestModel.getCurrencyArray() != null && pRequestModel.getCurrencyArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCurrencyArray().length; i++)
				currencylist.add(pRequestModel.getCurrencyArray()[i]);
		} else {
			currencylist = null;
		}
		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}
		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() ||  "[]".equals(odJson)) {
			odJson = null;
		}

		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() ||  "[]".equals(countryJson)) {
			countryJson = null;
		}
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}
		String rbdJSON = Utility.create_json(rbdList);
		if (rbdJSON.isEmpty() ||  "[]".equals(rbdJSON)) {
			rbdJSON = null;
		}
		String farebasisJSON = Utility.create_json(fareBasislist);
		if (farebasisJSON.isEmpty() ||  "[]".equals(farebasisJSON)) {
			farebasisJSON = null;
		}
		String currencyJSON = Utility.create_json(currencylist);
		if (currencyJSON.isEmpty() ||  "[]".equals(currencyJSON)) {
			currencyJSON = null;
		}
		String query = "JUP_FN_Sales_Review(" + startDateJson + "," + endDateJson + "," + regionJson + "," + countryJson
				+ "," + posJson + "," + odJson + "," + compartmentJson + "," + currencyJSON + "," + rbdJSON + ","
				+ farebasisJSON + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			resultObj = db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			} else {
				lCollection=null;
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
			logger.error("getTopTenOd-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDatalist;

	}

}
