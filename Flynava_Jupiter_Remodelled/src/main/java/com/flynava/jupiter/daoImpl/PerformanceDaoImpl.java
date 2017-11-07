package com.flynava.jupiter.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.PerformanceDao;
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
public class PerformanceDaoImpl implements PerformanceDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	private static final Logger logger = Logger.getLogger(PerformanceDaoImpl.class);

	@Override
	public ArrayList<DBObject> getChannelSegmentRevenueSpread(RequestModel requestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String startDate = null;
		if (requestModel.getFromDate() != null) {
			startDate = requestModel.getFromDate();
		} else {
			startDate = Utility.getFirstDateOfCurrentYear();
		}

		String endDate = null;
		if (requestModel.getToDate() != null) {
			endDate = requestModel.getToDate();

		} else {
			endDate = Utility.getCurrentDate();

		}

		ArrayList<String> region = null;
		if (requestModel.getRegionArray() != null) {
			region = new ArrayList<String>();
			for (int i = 0; i < requestModel.getRegionArray().length; i++)
				region.add(requestModel.getRegionArray()[i]);
		}

		ArrayList<String> country = null;
		if (requestModel.getCountryArray() != null) {
			country = new ArrayList<String>();
			for (int i = 0; i < requestModel.getCountryArray().length; i++)
				country.add(requestModel.getCountryArray()[i]);
		}

		ArrayList<String> pos = null;
		if (requestModel.getPosArray() != null) {
			pos = new ArrayList<String>();
			for (int i = 0; i < requestModel.getPosArray().length; i++)
				pos.add(requestModel.getPosArray()[i]);
		}

		ArrayList<String> compartment = new ArrayList<String>();
		if (requestModel.getCompartmentArray() != null) {
			for (int i = 0; i < requestModel.getCompartmentArray().length; i++)
				compartment.add(requestModel.getCompartmentArray()[i]);
		} else {
			compartment.add("Y");
			compartment.add("J");
			compartment.add("F");
		}

		String startDateJson = new flexjson.JSONSerializer().serialize(startDate);
		String endDateJson = new flexjson.JSONSerializer().serialize(endDate);
		String regionJson = Utility.create_json(region);
		String countryJson = Utility.create_json(country);
		String posJson = Utility.create_json(pos);
		String compartmentJson = Utility.create_json(compartment);

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Cust_Seg_dhb_Channel_summary(" + startDateJson + "," + endDateJson + "," + regionJson
				+ "," + countryJson + "," + posJson + "," + compartmentJson + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			resultObj = db.eval(query);

			lCollection = db.getCollection(lCollectionName);
			DBCursor cursor = lCollection.find();

			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {

			logger.error("getChannelSegmentRevenueSpread-Exception", e);
		} finally {
			if (lCollection != null && db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		System.out.println("Data fethed: " + lDataList);

		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getDistributorSegmentRevenueSpread(RequestModel requestModel) {
		// TODO Auto-generated method stub
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String startDate = null;
		if (requestModel.getFromDate() != null) {
			startDate = requestModel.getFromDate();
		} else {
			startDate = Utility.getFirstDateOfCurrentYear();
		}

		String endDate = null;
		if (requestModel.getToDate() != null) {
			endDate = requestModel.getToDate();

		} else {
			endDate = Utility.getCurrentDate();

		}

		ArrayList<String> region = null;
		if (requestModel.getRegionArray() != null) {
			region = new ArrayList<String>();
			for (int i = 0; i < requestModel.getRegionArray().length; i++)
				region.add(requestModel.getRegionArray()[i]);
		}

		ArrayList<String> country = null;
		if (requestModel.getCountryArray() != null) {
			country = new ArrayList<String>();
			for (int i = 0; i < requestModel.getCountryArray().length; i++)
				country.add(requestModel.getCountryArray()[i]);
		}

		ArrayList<String> pos = null;
		if (requestModel.getPosArray() != null) {
			pos = new ArrayList<String>();
			for (int i = 0; i < requestModel.getPosArray().length; i++)
				pos.add(requestModel.getPosArray()[i]);
		}

		ArrayList<String> od = null;
		if (requestModel.getOdArray() != null) {
			od = new ArrayList<String>();
			for (int i = 0; i < requestModel.getOdArray().length; i++)
				od.add(requestModel.getOdArray()[i]);
		}

		ArrayList<String> compartment = new ArrayList<String>();
		if (requestModel.getCompartmentArray() != null) {
			for (int i = 0; i < requestModel.getCompartmentArray().length; i++)
				compartment.add(requestModel.getCompartmentArray()[i]);
		} else {
			compartment.add("Y");
			compartment.add("J");
			compartment.add("F");
		}

		String startDateJson = new flexjson.JSONSerializer().serialize(startDate);
		String endDateJson = new flexjson.JSONSerializer().serialize(endDate);
		String regionJson = Utility.create_json(region);
		String countryJson = Utility.create_json(country);
		String posJson = Utility.create_json(pos);
		String odJson = Utility.create_json(od);
		String compartmentJson = Utility.create_json(compartment);

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_Cust_Seg_dhb_Distributors_summary(" + startDateJson + "," + endDateJson + ","
				+ regionJson + "," + countryJson + "," + posJson + "," + odJson + "," + compartmentJson + ","
				+ lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			resultObj = db.eval(query);

			lCollection = db.getCollection(lCollectionName);
			DBCursor cursor = lCollection.find();

			List<DBObject> array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {
				lDataList.add(array.get(i));
			}

		} catch (Exception e) {

			logger.error("getDistributorSegmentRevenueSpread-Exception", e);
		} finally {
			if (lCollection != null && db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		System.out.println("Data fethed: " + lDataList);

		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getCustomerSegmentRevenueSpread(RequestModel requestModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<DBObject> getAnalystPerformance() {

		DB lDb = null;
		DBCursor cursor = null;
		DBCollection lCollection = null;
		ArrayList<DBObject> DBObjectList = new ArrayList<DBObject>();
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_Performance_Analyst");

			cursor = lCollection.find();

			if (cursor != null) {
				while (cursor.hasNext()) {
					DBObject dbObject = (DBObject) cursor.next();
					DBObjectList.add(dbObject);

				}
			}

		} catch (Exception e) {

		}

		return DBObjectList;
	}

	@Override
	public ArrayList<DBObject> getAnalystPerformanceSQL() {

		DB lDb = null;
		DBCursor cursor = null;
		DBCollection lCollection = null;
		ArrayList<DBObject> DBObjectList = new ArrayList<DBObject>();
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_Performance_Graph");

			cursor = lCollection.find();

			if (cursor != null) {
				while (cursor.hasNext()) {
					DBObject dbObject = (DBObject) cursor.next();
					DBObjectList.add(dbObject);

				}
			}

		} catch (Exception e) {

		}

		return DBObjectList;
	}

	@Override
	public int saveSimulation() {

		DB lDb = null;
		DBCollection lCollection = null;
		DBObject searchQuery = new BasicDBObject();

		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_Performance_Analyst_copy");

			DBObject newSimulation = new BasicDBObject();
			newSimulation.put("field_1", 100);
			newSimulation.put("field_2", 500);
			newSimulation.put("field_3", 700);

			searchQuery.put("analyst_name", "Analyst_1");

			BasicDBObject update = new BasicDBObject();
			update.put("$addToSet", new BasicDBObject("simulation", newSimulation));
			lCollection.update(searchQuery, update, true, true);

		} catch (Exception e) {

		}

		return 0;
	}

	public ArrayList<DBObject> getJupiterPerformance() {

		DB lDb = null;
		DBCursor cursor = null;
		DBCollection lCollection = null;
		ArrayList<DBObject> DBObjectList = new ArrayList<DBObject>();
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_Performance_Jupiter");

			cursor = lCollection.find();

			if (cursor != null) {
				while (cursor.hasNext()) {
					DBObject dbObject = (DBObject) cursor.next();
					DBObjectList.add(dbObject);

				}
			}

		} catch (Exception e) {

		}

		return DBObjectList;

	}

}
