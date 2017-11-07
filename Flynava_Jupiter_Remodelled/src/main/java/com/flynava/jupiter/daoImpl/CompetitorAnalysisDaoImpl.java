package com.flynava.jupiter.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.CompetitorAnalysisDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.UserProfile;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Utility;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class CompetitorAnalysisDaoImpl implements CompetitorAnalysisDao {

	@Autowired
	MongoTemplate mMongoTemplateDB;

	private static final Logger logger = Logger.getLogger(CompetitorAnalysisDaoImpl.class);

	@Override
	public List<Object> getProductIndicator(UserProfile lUserProfileModel) {

		BasicDBObject lDbObjectQuery = new BasicDBObject();
		BasicDBObject lQueryField = new BasicDBObject();
		String competitorsArray[] = null;
		if (lUserProfileModel != null)
			competitorsArray = lUserProfileModel.getCompetitors();

		lQueryField.put("Category", 1);
		lQueryField.put("Feature", 1);
		lQueryField.put("Value_added", 1);
		lQueryField.put("Core elements", 1);

		if (competitorsArray != null) {
			for (int i = 0; i < competitorsArray.length; i++) {
				lQueryField.put(competitorsArray[i].replace("\"", ""), 1);
				lQueryField.put("FZ", 1);
			}
		}
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection lCollection = lDb.getCollection("JUP_DB_Data_Product_Summary");

		DBCursor lCursor = lCollection.find(lDbObjectQuery, lQueryField);
		List<Object> lProductIndicatorList = new ArrayList<Object>();
		while (lCursor.hasNext()) {

			lProductIndicatorList.add(lCursor.next());

		}

		System.out.println(lProductIndicatorList);
		return lProductIndicatorList;
	}

	@Override
	public ArrayList<DBObject> getFares(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection lCollection = lDb.getCollection("JUP_DB_Infare_Fares");

		String lStartDate = null;
		if (pRequestModel.getFromDate() != null) {
			lStartDate = pRequestModel.getFromDate();
		} else {
			lStartDate = Utility.getFirstDateOfCurrentYear();
		}

		String lEndDate = null;
		if (pRequestModel.getToDate() != null) {
			lEndDate = pRequestModel.getToDate();

		} else {
			lEndDate = Utility.getCurrentDate();
		}

		String lOd = null;
		if (pRequestModel.getOdArray() != null) {
			lOd = pRequestModel.getOdArray()[0];
		} else {

			if (lUserProfileModel != null)
				lOd = lUserProfileModel.getSig_od();
		}

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();
		}

		BasicDBObject lDbQuery = new BasicDBObject();

		try {

			lDbQuery.put("outbound_dep_date",
					BasicDBObjectBuilder.start("$gte", lStartDate).add("$lte", lEndDate).get());

			lDbQuery.put("origin", lOd.substring(0, 3));
			lDbQuery.put("destination", lOd.substring(3, 6));
			lDbQuery.put("compartment", new BasicDBObject("$in", lCompartmentList));

			DBCursor lCursor = lCollection.find(lDbQuery);
			if (lCursor != null)
				lCursor.sort(new BasicDBObject("effective_date", -1));

			if (lCursor != null) {
				while (lCursor.hasNext()) {
					lDataList.add(lCursor.next());
				}
			}

		}

		catch (Exception e) {
			logger.error("getFares-Exception", e);
		}

		System.out.println(lDataList);

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getFareAnalysis(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lPosList = null;
		if (pRequestModel.getPosArray() != null) {
			lPosList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getPosArray().length; i++)
				lPosList.add(pRequestModel.getPosArray()[i]);
		}

		ArrayList<String> lOdList = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null) {

			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOdList.add(pRequestModel.getOdArray()[i]);
		} else {
			if (lUserProfileModel != null)
				lOdList.add(lUserProfileModel.getSig_od());
		}

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();
		}

		String lPosJson = Utility.create_json(lPosList);
		String lOdJson = Utility.create_json(lOdList);
		String lCompartmentJson = Utility.create_json(lCompartmentList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String lQuery = "JUP_FN_Competitor_Fare_Analysis(" + lPosJson + "," + lOdJson + "," + lCompartmentJson + ","
				+ lTempCollectionName + ")";

		Object lResultObjList = null;
		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			lResultObjList = lDb.eval(lQuery);
			if (lDb.collectionExists(lCollectionName)) {
				lCollection = lDb.getCollection(lCollectionName);
				DBCursor lCursor = lCollection.find();

				List<DBObject> lCursorArray = lCursor.toArray();
				for (int i = 0; i < lCursorArray.size(); i++) {
					lDataList.add(lCursorArray.get(i));
				}
			}

			System.out.println(lResultObjList);
		} catch (Exception e) {
			logger.error("getFareAnalysis-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getMarketIndicator(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		String lOd = null;
		if (pRequestModel.getOdArray() != null) {
			lOd = pRequestModel.getOdArray()[0];
		} else {

			if (lUserProfileModel != null)
				lOd = lUserProfileModel.getSig_od();
		}

		int lYear = 0;
		if (pRequestModel.getFromDate() != null && pRequestModel.getToDate() != null)
			lYear = Integer.parseInt(
					Utility.getYearsFromDateRange(pRequestModel.getFromDate(), pRequestModel.getToDate()).get(0));
		else
			lYear = Utility.getCurrentYear();

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lOdJson = Utility.create_jsonString(lOd);

		String lQuery = "JUP_FN_Competitor_dhb_Market_Ind_Summary(" + lOdJson + "," + lYear + "," + lTempCollectionName
				+ ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {

			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

			System.out.println(lDataList);
		} catch (Exception e) {
			logger.error("getFareAnalysis-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getMarketIndicatorTopODs(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		String lStartDate = null;
		if (pRequestModel.getFromDate() != null)
			lStartDate = pRequestModel.getFromDate();
		else
			lStartDate = Utility.getFirstDateOfCurrentYear();

		String lEndDate = null;
		if (pRequestModel.getToDate() != null)
			lEndDate = pRequestModel.getToDate();
		else
			lEndDate = Utility.getCurrentDate();

		ArrayList<String> lRegionList = null;
		if (pRequestModel.getRegionArray() != null) {
			lRegionList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++)
				lRegionList.add(pRequestModel.getRegionArray()[i]);
		}

		ArrayList<String> lCountryList = null;
		if (pRequestModel.getCountryArray() != null) {
			lCountryList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++)
				lCountryList.add(pRequestModel.getCountryArray()[i]);
		}

		ArrayList<String> lPosList = null;
		if (pRequestModel.getPosArray() != null) {
			lPosList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getPosArray().length; i++)
				lPosList.add(pRequestModel.getPosArray()[i]);
		}

		ArrayList<String> lOdList = null;
		if (pRequestModel.getOdArray() != null) {
			lOdList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOdList.add(pRequestModel.getOdArray()[i]);
		}
		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();
		}

		String lStartDateJson = Utility.create_jsonString(lStartDate);
		String lEndDateJson = Utility.create_jsonString(lEndDate);
		String lRegionJson = Utility.create_json(lRegionList);
		String lCountryJson = Utility.create_json(lCountryList);
		String lPosJson = Utility.create_json(lPosList);
		String lOdJson = Utility.create_json(lOdList);
		String lCompartmentJson = Utility.create_json(lCompartmentList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_dhb_Market_Ind_topod_new(" + lStartDateJson + "," + lEndDateJson + ","
				+ lRegionJson + "," + lCountryJson + "," + lPosJson + "," + lOdJson + "," + lCompartmentJson + ","
				+ lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

		} catch (Exception e) {
			logger.error("getMarketIndicatorTopODs-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}
		System.out.println("Data fethed: " + lDataList);

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getFlightAvailability(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String lOd = new String();
		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				lOd = pRequestModel.getOdArray()[0];
			}
		} else {
			if (lUserProfileModel != null)
				lOd = lUserProfileModel.getSig_od();
		}

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();
		}

		String lOdJson = Utility.create_jsonString(lOd);
		String lCompartmentJson = Utility.create_json(lCompartmentList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_dhb_Internal_Availability(" + lOdJson + "," + lCompartmentJson + ","
				+ lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {

			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

			System.out.println(lDataList);
		} catch (Exception e) {
			logger.error("getFlightAvailability-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getFlightForecast(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lPosList = null;
		if (pRequestModel.getPosArray() != null) {

			lPosList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getPosArray().length; i++)
				lPosList.add(pRequestModel.getPosArray()[i]);

		}

		ArrayList<String> lOdList = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null) {

			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOdList.add(pRequestModel.getOdArray()[i]);
		} else
			lOdList.add(lUserProfileModel.getSig_od());

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();
		}

		String lPosJson = Utility.create_json(lPosList);
		String lOdJson = Utility.create_json(lOdList);
		String lCompartmentJson = Utility.create_json(lCompartmentList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_dhb_Forecast(" + lPosJson + "," + lOdJson + "," + lCompartmentJson + ","
				+ lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {

			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

			System.out.println(lDataList);
		} catch (Exception e) {
			logger.error("getFlightForecast-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	@Override
	public Object getFareBrands(RequestModel pRequestModel) {

		ArrayList<String> lCountryList = null;
		if (pRequestModel.getCountryArray() != null) {
			lCountryList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++)
				lCountryList.add(pRequestModel.getCountryArray()[i]);
		}

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			/* lCompartmentList = CalculationUtil.getAllCompartments(); */

			lCompartmentList.add("Y");

		}

		String lCompartmentJson = Utility.create_json(lCompartmentList);
		String lCountryJson = Utility.create_json(lCountryList);

		String lQuery = "JUP_FN_Fare_Brands_1(" + lCountryJson + "," + lCompartmentJson + ")";

		Object lResultObj = null;

		try {
			DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
			lResultObj = lDb.eval(lQuery);

		} catch (Exception e) {
			logger.error("getFareBrands-Exception", e);
		}

		System.out.println(lResultObj);

		return lResultObj;

	}

	@Override
	public ArrayList<DBObject> getFareGridAllFares(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		String lStartDate = null;
		if (pRequestModel.getFromDate() != null)
			lStartDate = pRequestModel.getFromDate();
		else
			lStartDate = "2017-02-14";

		String lEndDate = null;
		if (pRequestModel.getToDate() != null)
			lEndDate = pRequestModel.getToDate();
		else
			lEndDate = Utility.getCurrentDate();

		ArrayList<String> lOdList = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null) {

			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOdList.add(pRequestModel.getOdArray()[i]);
		} else {

			if (lUserProfileModel != null)
				lOdList.add(lUserProfileModel.getSig_od());

		}

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();
		}

		String lStartDateJson = Utility.create_jsonString(lStartDate);
		String lEndDateJson = Utility.create_jsonString(lEndDate);
		String lOdJson = Utility.create_json(lOdList);
		String lCompartmentJson = Utility.create_json(lCompartmentList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_Fares_Sellup(" + lStartDateJson + "," + lEndDateJson + "," + lOdJson + ","
				+ lCompartmentJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

		} catch (Exception e) {
			logger.error("getFareBrands-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}
		System.out.println("Data fethed: " + lDataList);

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getProductIndicatorReport(UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		BasicDBObject lDbObjectQuery = new BasicDBObject();
		BasicDBObject lQueryField = new BasicDBObject();

		lQueryField.put("Category", 1);
		lQueryField.put("Sub_Category", 2);
		lQueryField.put("Value_added", 3);
		lQueryField.put("Core elements", 4);
		lQueryField.put("Feature", 5);
		for (int i = 0; i < lUserProfileModel.getCompetitors().length; i++) {
			String comp = lUserProfileModel.getCompetitors()[i].replace("\"", "");
			lQueryField.put(comp, 1);
			lQueryField.put(Constants.hostName, 1);

		}
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection lCollection = lDb.getCollection("JUP_DB_Data_Product");

		DBCursor lCursor = lCollection.find(lDbObjectQuery, lQueryField);

		List<DBObject> lCursorArray = lCursor.toArray();
		for (int i = 0; i < lCursorArray.size(); i++) {
			lDataList.add(lCursorArray.get(i));
		}

		System.out.println(lCursor);

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getFlightAgentFriends(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		int lMonth = 0;
		if (pRequestModel.getFromDate() != null && pRequestModel.getToDate() != null) {

			ArrayList<String> months = Utility.getMonthsFromDateRange(pRequestModel.getFromDate(),
					pRequestModel.getToDate());
			for (int i = 0; i < months.size(); i++)
				lMonth = Integer.parseInt(months.get(i));

		} else
			lMonth = Utility.getCurrentMonth();

		ArrayList<String> lPosList = null;
		if (pRequestModel.getPosArray() != null) {
			lPosList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getPosArray().length; i++) {
				lPosList.add(pRequestModel.getPosArray()[i]);
			}
		}

		ArrayList<String> lOdList = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null) {

			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOdList.add(pRequestModel.getOdArray()[i]);
		} else {
			lOdList.add(lUserProfileModel.getSig_od());
		}

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();
		}

		String lPosJson = Utility.create_json(lPosList);
		String lOdJson = Utility.create_json(lOdList);

		String lCompartmentJson = Utility.create_json(lCompartmentList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_dhb_Friend_Agent(" + lMonth + "," + lPosJson + "," + lOdJson + ","
				+ lCompartmentJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

			System.out.println(lDataList);
		} catch (Exception e) {
			logger.error("getFareBrands-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getScheduleDirect(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		String lOd = new String();
		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOd = pRequestModel.getOdArray()[i];
		} else {
			lOd = lUserProfileModel.getSig_od();
		}

		String lStartDate = null;
		if (pRequestModel.getFromDate() != null)
			lStartDate = pRequestModel.getFromDate();
		else
			lStartDate = Utility.getFirstDateOfCurrentYear();

		String lEndDate = null;
		if (pRequestModel.getToDate() != null)
			lEndDate = pRequestModel.getToDate();
		else
			lEndDate = Utility.getCurrentDate();

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length != 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();
		}

		String lStartDateJson = Utility.create_jsonString(lStartDate);
		String lEndDateJson = Utility.create_jsonString(lEndDate);
		String lOdJson = Utility.create_jsonString(lOd);
		String lCompartmentJson = Utility.create_json(lCompartmentList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_dhb_Schedule_Direct(" + lStartDateJson + "," + lEndDateJson + "," + lOdJson
				+ "," + lCompartmentJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

		} catch (Exception e) {
			logger.error("getFareBrands-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}

		// System.out.println("Data fetched: " + lDataList);

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getScheduleIndirect(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lOd = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOd.add(pRequestModel.getOdArray()[i]);
		} else {
			lOd.add(lUserProfileModel.getSig_od());
		}

		String lOdJson = Utility.create_json(lOd);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_dhb_Schedule_InDirect(" + lOdJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

		} catch (Exception e) {
			logger.error("getScheduleIndirect-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}

		System.out.println("Data fetched: " + lDataList);

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getScheduleNetwork() {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_dhb_Schedule_network_level(" + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

		} catch (Exception e) {
			logger.error("getScheduleNetwork-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}
		System.out.println("Data fetched: " + lDataList);

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getScheduleInformationOd(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		ArrayList<String> lOdList = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOdList.add(pRequestModel.getOdArray()[i]);
		} else {
			lOdList.add(lUserProfileModel.getSig_od());
		}

		String lOdJson = Utility.create_json(lOdList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_dhb_Schedule_od_information(" + lOdJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {
			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

		} catch (Exception e) {
			logger.error("getScheduleInformationOd-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}
		System.out.println("Data fetched: " + lDataList);

		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getMarketEntrantsLeavers(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> lRegionList = null;
		if (pRequestModel.getRegionArray() != null) {
			lRegionList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++)
				lRegionList.add(pRequestModel.getRegionArray()[i]);
		}

		ArrayList<String> lCountryList = null;
		if (pRequestModel.getCountryArray() != null) {
			lCountryList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getCountryArray().length; i++)
				lCountryList.add(pRequestModel.getCountryArray()[i]);
		}

		ArrayList<String> lYearList = new ArrayList<String>();
		if (pRequestModel.getFromDate() != null && pRequestModel.getToDate() != null) {

			lYearList = Utility.getYearsFromDateRange(pRequestModel.getFromDate(), pRequestModel.getToDate());
			lYearList.add(pRequestModel.getYear());
		} else {
			lYearList.add(Integer.toString(Utility.getCurrentYear()));
		}

		ArrayList<String> lMonth = new ArrayList<String>();
		if (pRequestModel.getFromDate() != null && pRequestModel.getToDate() != null)
			lMonth = Utility.getMonthsFromDateRange(pRequestModel.getFromDate(), pRequestModel.getToDate());
		else
			lMonth.add(Integer.toString(Utility.getCurrentMonth()));

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < pRequestModel.getPosArray().length; i++) {
				posList.add(pRequestModel.getPosArray()[i]);
			}
		} else {
			posList = null;
		}

		ArrayList<String> lOdList = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOdList.add(pRequestModel.getOdArray()[i]);
		} else {
			lOdList.add(lUserProfileModel.getSig_od());
		}

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartment() != null && !pRequestModel.getCompartment().isEmpty())
			lCompartmentList.add(pRequestModel.getCompartment());
		else
			lCompartmentList = CalculationUtil.getAllCompartments();

		String lRegionJson = Utility.create_json(lRegionList);
		String lCountryJson = Utility.create_json(lCountryList);
		String lPosJson = Utility.create_json(posList);
		String lOdJson = Utility.create_json(lOdList);
		String lCompartmentJson = Utility.create_json(lCompartmentList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_dhb_Entrants_Leavers(" + lRegionJson + "," + lCountryJson + "," + lYearList
				+ "," + lMonth + "," + lPosJson + "," + lOdJson + "," + lCompartmentJson + "," + lTempCollectionName
				+ ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

		} catch (Exception e) {
			logger.error("getMarketEntrantsLeavers-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}
		System.out.println("Data fetched: " + lDataList);

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getFlightAnalysis(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		String lOd = new String();
		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOd = pRequestModel.getOdArray()[i];
		} else {
			lOd = lUserProfileModel.getSig_od();
		}

		String lStartDate = null;
		if (pRequestModel.getFromDate() != null)
			lStartDate = pRequestModel.getFromDate();
		else
			lStartDate = Utility.getCurrentDate();

		String lEndDate = null;
		if (pRequestModel.getToDate() != null)
			lEndDate = pRequestModel.getToDate();
		else
			lEndDate = Utility.getCurrentDate();

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length != 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();

		}

		String lStartDateJson = Utility.create_jsonString(lStartDate);
		String lEndDateJson = Utility.create_jsonString(lEndDate);
		String lOdJson = Utility.create_jsonString(lOd);
		String lCompartmentJson = Utility.create_json(lCompartmentList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_dhb_Flight_Analysis(" + lOdJson + "," + lCompartmentJson + ","
				+ lStartDateJson + "," + lEndDateJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

		} catch (Exception e) {
			logger.error("getFlightAnalysis-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}

		System.out.println("Data fetched: " + lDataList);

		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getMonthlyLowFare(RequestModel pRequestModel, UserProfile lUserProfileModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String lStartDate = null;
		if (pRequestModel.getFromDate() != null) {
			lStartDate = pRequestModel.getFromDate();
		} else {
			lStartDate = "2017-02-14";
		}

		String lEndDate = null;
		if (pRequestModel.getToDate() != null) {
			lEndDate = pRequestModel.getToDate();

		} else {
			lEndDate = Utility.getNthDate(lStartDate, 7);
		}

		ArrayList<String> lOdList = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				lOdList.add(pRequestModel.getOdArray()[i]);
		} else {
			lOdList.add(lUserProfileModel.getSig_od());
		}

		ArrayList<String> lCompartmentList = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length != 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				lCompartmentList.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			lCompartmentList = CalculationUtil.getAllCompartments();

		}

		String lStartDateJson = Utility.create_jsonString(lStartDate);
		String lEndDateJson = Utility.create_jsonString(lEndDate);
		String lCompartmentJson = Utility.create_json(lCompartmentList);
		String lOdJson = Utility.create_json(lOdList);

		String lCollectionName = CalculationUtil.getTempCollection();
		String lTempCollectionName = Utility.create_jsonString(lCollectionName);

		String lQuery = "JUP_FN_Competitor_Daily_low_fare_graph(" + lStartDateJson + "," + lEndDateJson + "," + lOdJson
				+ "," + lCompartmentJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB lDb = mMongoTemplateDB.getDb().getMongo().getDB(Constants.DB_NAME);
		try {

			lDb.eval(lQuery);

			lCollection = lDb.getCollection(lCollectionName);
			DBCursor lCursor = lCollection.find();

			List<DBObject> lCursorArray = lCursor.toArray();
			for (int i = 0; i < lCursorArray.size(); i++) {
				lDataList.add(lCursorArray.get(i));
			}

			System.out.println(lDataList);

		} catch (Exception e) {
			logger.error("getMonthlyLowFare-Exception", e);
		} finally {
			if (lCollection != null && lDb.collectionExists(lCollectionName))
				lCollection.drop();
		}

		System.out.println("Data fetched: " + lDataList);

		return lDataList;

	}

}
