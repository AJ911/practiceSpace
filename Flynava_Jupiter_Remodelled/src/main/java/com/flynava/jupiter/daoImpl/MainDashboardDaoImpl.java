package com.flynava.jupiter.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.MainDashboardDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.UserProfile;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Utility;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class MainDashboardDaoImpl implements MainDashboardDao {

	@Autowired
	MongoTemplate mMongoTemplateDB;

	private static final Logger logger = Logger.getLogger(MainDashboardDaoImpl.class);

	@Override
	public ArrayList<DBObject> getBookings(RequestModel pRequestModel) {

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

		ArrayList<String> lRegionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length != 0)
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++)
				lRegionList.add(pRequestModel.getRegionArray()[i]);
		else {

			if (pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() == null
					&& pRequestModel.getCountryArray() == null && pRequestModel.getCompartmentArray() != null
					|| pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() == null
							&& pRequestModel.getCountryArray() == null && pRequestModel.getCompartmentArray() == null) {

				String[] lRegionArray = getRegion(pRequestModel, "AllRegions", pRequestModel.getRegionArray());
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

		String lFlag = "true";
		if (pRequestModel.getFlag() != null && !pRequestModel.getFlag().isEmpty())
			lFlag = pRequestModel.getFlag();

		String lRegionJson = Utility.create_json(lRegionList);
		String lCountryJson = Utility.create_json(lCountryList);
		String lPosJson = Utility.create_json(lPosList);
		String lOdJson = Utility.create_json(lOdList);
		String lCompartmentJson = Utility.create_json(lCompartmentList);
		String lFlagJson = new flexjson.JSONSerializer().serialize(lFlag);

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String lQuery = "JUP_FN_Bookings(" + lDateJsonObj + "," + lRegionJson + "," + lCountryJson + "," + lPosJson
				+ "," + lOdJson + "," + lCompartmentJson + "," + lFlagJson + "," + lTempCollectionName + ")";

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

	@Override
	public Object getEvents(RequestModel pRequestModel) {

		String lEventName = new String();
		if (pRequestModel.getEventName() != null && !pRequestModel.getEventName().isEmpty())
			lEventName = pRequestModel.getEventName();

		String lEventNameJson = new flexjson.JSONSerializer().serialize(lEventName);

		int lYear = Utility.getCurrentYear();

		Object lResultObj = null;

		String lQuery = "JUP_FN_Event_dropdown(" + lYear + "," + lEventNameJson + ")";
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		lResultObj = lDb.eval(lQuery);

		return lResultObj;

	}

	@Override
	public ArrayList<DBObject> getSales(RequestModel pRequestModel) {

		JSONObject lDateJsonObj = new JSONObject();
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String lStartDate = null;
		String lEndDate = null;

		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lStartDate = pRequestModel.getFromDate();

		else {
			lStartDate = Utility.getNthDate(Utility.getCurrentDate(), -7);
			pRequestModel.setFromDate(lStartDate);
		}

		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lEndDate = pRequestModel.getToDate();
		else {
			lEndDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lEndDate);
		}

		String lLastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

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

		pRequestModel.setFromDate(lEndDate);
		pRequestModel.setToDate(lStartDate);

		ArrayList<String> lRegionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length != 0)
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++)
				lRegionList.add(pRequestModel.getRegionArray()[i]);
		else {

			if (pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() == null
					&& pRequestModel.getCountryArray() == null && pRequestModel.getCompartmentArray() != null
					|| pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() == null
							&& pRequestModel.getCountryArray() == null && pRequestModel.getCompartmentArray() == null) {

				String[] lRegionArray = getRegion(pRequestModel, "AllRegions", pRequestModel.getRegionArray());
				Set<String> lRegionSet = new HashSet<String>();
				for (String s : lRegionArray) {
					lRegionSet.add(s);
				}

				String[] lAllRegionsArray = new String[lRegionSet.size()];

				int i = 0;

				Iterator<String> it = lRegionSet.iterator();
				while (it.hasNext()) {
					String lRegion = it.next();

					lAllRegionsArray[i++] = lRegion;
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

		String lFlag = "true";
		if (pRequestModel.getFlag() != null && !pRequestModel.getFlag().isEmpty())
			lFlag = pRequestModel.getFlag();

		String lRegionJson = Utility.create_json(lRegionList);
		String lCountryJson = Utility.create_json(lCountryList);
		String lPosJson = Utility.create_json(lPosList);
		String lOdJson = Utility.create_json(lOdList);
		String lCompartmentJson = Utility.create_json(lCompartmentList);
		String lFlagJson = new flexjson.JSONSerializer().serialize(lFlag);

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String lQuery = "JUP_FN_Sales_All(" + lDateJsonObj + "," + lRegionJson + "," + lCountryJson + "," + lPosJson
				+ "," + lOdJson + "," + lCompartmentJson + "," + lFlagJson + "," + lTempCollectionName + ")";

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
			logger.error("getSales-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;

	}

	@Override
	public Object getIndustryBenchmark(RequestModel pRequestModel) {

		ArrayList<String> arg = new ArrayList<String>();
		arg.add("EK");
		arg.add("EY");
		arg.add("LH");

		String argJson = Utility.create_json(arg);

		String lQuery = "JUP_FN_Industry_Benchmark(" + argJson + ")";
		Object lResultObj = null;
		try {
			DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
			lResultObj = lDb.eval(lQuery);

		} catch (Exception e) {

			logger.error("getIndustryBenchmark-Exception", e);
		}

		return lResultObj;
	}

	@Override
	public ArrayList getKpiIndex(RequestModel pRequestModel) {
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection lCollection = lDb.getCollection("JUP_DB_KPI_Summary");
		DBCursor lCursor = lCollection.find();
		List<Object> kpiList = new ArrayList<Object>();
		while (lCursor.hasNext()) {

			kpiList.add(lCursor.next());

		}

		return (ArrayList) kpiList;
	}

	public UserProfile getUserProfile(RequestModel pRequestModel) {
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection lCollection = lDb.getCollection("JUP_DB_User_Profile");

		BasicDBObject lWhereQuery = new BasicDBObject();
		lWhereQuery.put("user", pRequestModel.getUser());

		DBCursor lCursor = lCollection.find(lWhereQuery);

		UserProfile lUserProfile = null;
		while (lCursor.hasNext()) {
			DBObject lDbObj = lCursor.next();
			lUserProfile = new UserProfile();
			lUserProfile.setUser(lDbObj.get("user").toString());

			String[] lTopThreeCompArray = null;
			if (lDbObj.containsKey("competitors"))
				lTopThreeCompArray = new JSONArray(lDbObj.get("competitors").toString()).toString().replace("},{", " ,")
						.split(" ");
			if (lTopThreeCompArray.length != 0)
				lUserProfile.setCompetitors(
						lTopThreeCompArray[0].substring(1, lTopThreeCompArray[0].length() - 1).split(","));

			if (lDbObj.containsKey("sig_od"))
				lUserProfile.setSig_od(lDbObj.get("sig_od").toString());

			String[] lCountryArray = null;
			if (lDbObj.containsKey("country"))
				lCountryArray = new JSONArray(lDbObj.get("country").toString()).toString().replace("},{", " ,")
						.split(" ");

			if (lCountryArray != null && lCountryArray.length != 0)
				lUserProfile.setCountry(lCountryArray[0].substring(1, lCountryArray[0].length() - 1).split(","));

			String[] lPosArray = new JSONArray(lDbObj.get("pos").toString()).toString().replace("},{", " ,").split(" ");
			if (lPosArray != null && lPosArray.length != 0)
				lUserProfile.setPos(lPosArray[0].substring(1, lPosArray[0].length() - 1).split(","));

			String[] lOdArray = null;
			if (lDbObj.containsKey("od"))
				lOdArray = new JSONArray(lDbObj.get("od").toString()).toString().replace("},{", " ,").split(" ");
			if (lOdArray != null && lOdArray.length != 0)
				lUserProfile.setOd(lOdArray[0].substring(1, lOdArray[0].length() - 1).split(","));

			if (lDbObj.containsKey("level"))
				lUserProfile.setLevel(lDbObj.get("level").toString());

			String[] lRegionArray = new JSONArray(lDbObj.get("region").toString()).toString().replace("},{", " ,")
					.split(" ");
			if (lRegionArray.length != 0)
				lUserProfile.setRegion(lRegionArray[0].substring(1, lRegionArray[0].length() - 1).split(","));

			String[] lCompartmentArray = new JSONArray(lDbObj.get("compartment").toString()).toString()
					.replace("},{", " ,").split(" ");
			if (lCompartmentArray.length != 0)
				lUserProfile.setCompartment(
						lCompartmentArray[0].substring(1, lCompartmentArray[0].length() - 1).split(","));

		}
		return lUserProfile;
	}

	public String[] getRegion(RequestModel pRequestModel, String pFilterParam, String[] pParamVal) {
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection lCollection = lDb.getCollection("JUP_DB_Region_Master");

		BasicDBObject lWhereQuery = new BasicDBObject();

		String[] lRegionArray = null;

		if ("AllRegions".equalsIgnoreCase(pFilterParam)) {

			ArrayList<String> lRegionList = new ArrayList<String>();

			BasicDBObject lAllRegionsQuery = new BasicDBObject();
			BasicDBObject lFields = new BasicDBObject();
			lFields.put("Region", 1);
			DBCursor lCursor = lCollection.find(lAllRegionsQuery, lFields);

			while (lCursor.hasNext()) {

				DBObject lDbObj = lCursor.next();
				if (lDbObj != null && lDbObj.containsField("Region"))
					lRegionList.add(lDbObj.get("Region").toString());

			}

			lRegionArray = new String[lRegionList.size()];

			int lCount = 0;
			for (int i = 0; i < lRegionList.size(); i++) {
				lRegionArray[lCount++] = lRegionList.get(i);
			}

		} else {
			lRegionArray = new String[pParamVal.length];
			int lCount = 0;
			for (int i = 0; i < pParamVal.length; i++) {
				lWhereQuery.put(pFilterParam, pParamVal[i]);
				DBObject lDbObj = lCollection.findOne(lWhereQuery);

				lRegionArray[lCount++] = lDbObj.get("Region").toString();

			}

		}

		return lRegionArray;

	}

	public String[] getCountry(RequestModel pRequestModel, String pFilterParam, String[] pParamVal) {
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection lCollection = lDb.getCollection("JUP_DB_Region_Master");

		BasicDBObject lWhereQuery = new BasicDBObject();

		String[] lCountryArray = null;

		lCountryArray = new String[pParamVal.length];
		int lCount = 0;
		for (int i = 0; i < pParamVal.length; i++) {
			lWhereQuery.put(pFilterParam, pParamVal[i]);
			DBObject lDbObj = lCollection.findOne(lWhereQuery);

			if (lDbObj != null)
				lCountryArray[lCount++] = lDbObj.get("COUNTRY_CD").toString();

		}

		return lCountryArray;

	}

	public String[] getCountryName(RequestModel pRequestModel, String pFilterParam, String[] pParamVal) {
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection lCollection = lDb.getCollection("JUP_DB_Region_Master");

		BasicDBObject lWhereQuery = new BasicDBObject();

		String[] lCountryArray = null;

		lCountryArray = new String[pParamVal.length];
		int lCount = 0;
		for (int i = 0; i < pParamVal.length; i++) {
			lWhereQuery.put(pFilterParam, pParamVal[i]);
			DBObject lDbObj = lCollection.findOne(lWhereQuery);

			if (lDbObj != null)
				lCountryArray[lCount++] = lDbObj.get("COUNTRY_NAME_TX").toString();

		}

		return lCountryArray;

	}

}
