package com.flynava.jupiter.daoImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.WorkFlowDao;
import com.flynava.jupiter.model.AggTrgrTypes;
import com.flynava.jupiter.model.Diffuser;
import com.flynava.jupiter.model.InfareData;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.TriggerType;
import com.flynava.jupiter.model.Upsert;
import com.flynava.jupiter.model.WorkPackage;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Utility;
import com.mongodb.AggregationOptions;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;

@Repository
public class WorkFlowDaoImpl implements WorkFlowDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	private static final Logger logger = Logger.getLogger(WorkFlowDaoImpl.class);

	BasicDBObject allQuery = new BasicDBObject();
	BasicDBObject fields = new BasicDBObject();
	DBCursor cursor = null;

	@Override
	public List<DBObject> getTriggersRecommendation(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String fromDate = mRequestModel.getFromDate();
		String toDate = mRequestModel.getToDate();
		String genDateFrom = mRequestModel.getGetDateFrom();
		String genDateTo = mRequestModel.getGenDateTo();

		ArrayList<String> regionList = null;
		if (mRequestModel.getRegionArray() != null) {
			regionList = new ArrayList<String>();
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				regionList.add(mRequestModel.getRegionArray()[i]);
			}
		}

		ArrayList<String> countryList = null;
		if (mRequestModel.getCountryArray() != null) {
			countryList = new ArrayList<String>();
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				countryList.add(mRequestModel.getCountryArray()[i]);
			}
		}

		ArrayList<String> posList = null;
		if (mRequestModel.getPosArray() != null) {
			posList = new ArrayList<String>();
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				posList.add(mRequestModel.getPosArray()[i]);
			}
		}

		ArrayList<String> originList = null;
		if (mRequestModel.getOriginArray() != null) {
			originList = new ArrayList<String>();
			for (int i = 0; i < mRequestModel.getOriginArray().length; i++) {
				originList.add(mRequestModel.getOriginArray()[i]);
			}
		}

		ArrayList<String> destinationList = null;
		if (mRequestModel.getDestinationArray() != null) {
			destinationList = new ArrayList<String>();
			for (int i = 0; i < mRequestModel.getDestinationArray().length; i++) {
				destinationList.add(mRequestModel.getDestinationArray()[i]);
			}
		}

		ArrayList<String> compartmentList = null;
		if (mRequestModel.getCompartmentArray() != null) {
			compartmentList = new ArrayList<String>();
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++)
				compartmentList.add(mRequestModel.getCompartmentArray()[i]);
		}

		ArrayList<String> triggerList = null;
		if (mRequestModel.getTriggerArray() != null && mRequestModel.getTriggerArray().length > 0) {
			triggerList = new ArrayList<String>();
			for (int i = 0; i < mRequestModel.getTriggerArray().length; i++) {
				triggerList.add(mRequestModel.getTriggerArray()[i]);
			}
		}

		ArrayList<String> statusList = null;
		if (mRequestModel.getStatusArray() != null && mRequestModel.getStatusArray().length > 0) {
			statusList = new ArrayList<String>();
			for (int i = 0; i < mRequestModel.getStatusArray().length; i++) {
				statusList.add(mRequestModel.getStatusArray()[i]);
			}
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String fromDateJson = Utility.create_jsonString(fromDate);
		String toDateJson = Utility.create_jsonString(toDate);
		String genDateFromJson = Utility.create_jsonString(genDateFrom);
		String genDateToJson = Utility.create_jsonString(genDateTo);
		String regionJson = Utility.create_json(regionList);
		String countryJson = Utility.create_json(countryList);
		String posJson = Utility.create_json(posList);
		String originJson = Utility.create_json(originList);
		String destinationJson = Utility.create_json(destinationList);
		String compartmentJson = Utility.create_json(compartmentList);
		String triggerJson = Utility.create_json(triggerList);
		String statusJson = Utility.create_json(statusList);

		String query = "JUP_FN_Triggers_Recommendation" + "(" + regionJson + "," + countryJson + "," + posJson + ","
				+ originJson + "," + destinationJson + "," + "" + compartmentJson + "," + fromDateJson + ","
				+ toDateJson + "," + triggerJson + "," + statusJson + "," + lTempCollectionName + "," + genDateFromJson
				+ "," + genDateTo + ")";

		DBCollection lCollection = null;
		cursor = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		List<DBObject> array = null;
		try {
			db.eval(query);
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

			// System.out.println(lDataList);

		} catch (Exception e) {
			logger.error("getTriggersRecommendation-Exception", e);
		} finally {
			if (lCollection != null && db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDataList;
	}

	@Override
	public Double findTriggerLength(String str) {

		String collectionName = "JUP_DB_Cum_Pricing_Actions_New";
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("trigger_type", str);
		Double length = 0D;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			length = (double) db.getCollection(collectionName).find(whereQuery).length();

		} catch (Exception e) {
			logger.error("findTriggerLength-Exception", e);
		}
		return length;
	}

	@Override
	public ArrayList<DBObject> getForcastAvailability(RequestModel mRequestModel) {
		// TODO Auto-generated method stub

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		String startDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			startDate = mRequestModel.getFromDate().toString();
		else {
			startDate = "2017-02-14";
		}
		String endDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			endDate = mRequestModel.getToDate().toString();
		else {
			endDate = "2017-02-14";
		}
		String od = null;
		if (mRequestModel.getSingleOD() != null && !mRequestModel.getSingleOD().isEmpty())
			od = mRequestModel.getSingleOD().toString();

		String startDateJson = new flexjson.JSONSerializer().serialize(startDate);

		String endDateJson = new flexjson.JSONSerializer().serialize(endDate);

		String odJson = null;
		if (od != null)
			odJson = new flexjson.JSONSerializer().serialize(od);

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_workflow_Flight_Forecast(" + odJson + "," + startDateJson + "," + endDateJson + ","
				+ lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {

			db.eval(query);
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

			System.out.println(lDataList);

		} catch (Exception e) {
			logger.error("getForcastAvailability-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		return lDataList;
	}

	@Override
	public ArrayList<DBObject> getWorkflowCompetitiorSummary(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		String startDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			startDate = mRequestModel.getFromDate().toString();
		else {

		}
		String endDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			endDate = mRequestModel.getToDate().toString();
		else {

		}
		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {

			for (int i = 0; i < mRequestModel.getOdArray().length; i++)

				odList.add(mRequestModel.getOdArray()[i]);
		} else {
			odList = null;
		}
		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < mRequestModel.getPosArray().length; i++)

				posList.add(mRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(mRequestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		String startDateJson = new flexjson.JSONSerializer().serialize(startDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(endDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}
		String odJson = Utility.create_json(odList);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
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

		String query = "JUP_FN_workflow_Competitor_Summary(" + posJson + "," + odJson + "," + compartmentJson + ","
				+ startDateJson + "," + endDateJson + "," + lTempCollectionName + ")";
		System.out.println("query :" + query);

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

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

			System.out.println(lDataList);
		} catch (Exception e) {
			logger.error("getWorkflowCompetitiorSummary-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		return lDataList;

	}

	@Override
	public Boolean addUser(RequestModel mRequestModel) {
		String user = null;
		if (mRequestModel.getUser() != null && !mRequestModel.getUser().isEmpty())
			user = mRequestModel.getUser().toString();
		else {

		}
		String name = null;
		if (mRequestModel.getName() != null && !mRequestModel.getName().isEmpty())
			name = mRequestModel.getName().toString();
		else {

		}
		String level = null;
		if (mRequestModel.getLevel() != null && !mRequestModel.getLevel().isEmpty())
			level = mRequestModel.getLevel().toString();
		else {

		}
		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				countryList.add(mRequestModel.getCountryArray()[i]);
			}
		} else {
			// TODO: add the default values from user profile.
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				regionList.add(mRequestModel.getRegionArray()[i]);
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
			posList = null;
		}

		ArrayList<String> odList = new ArrayList<String>();
		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				odList.add(mRequestModel.getOdArray()[i]);
			}
		} else {
			odList = null;
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(mRequestModel.getCompartmentArray()[i]);
			}
		} else {
			compartmentList = null;
		}

		ArrayList<String> competitorList = new ArrayList<String>();
		if (mRequestModel.getCompetitorArray() != null && mRequestModel.getCompetitorArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompetitorArray().length; i++) {
				competitorList.add(mRequestModel.getCompetitorArray()[i]);
			}
		} else {
			competitorList = null;
		}

		ArrayList<String> dashboardList = new ArrayList<String>();
		if (mRequestModel.getDashboardArray() != null && mRequestModel.getDashboardArray().length > 0) {
			for (int i = 0; i < mRequestModel.getDashboardArray().length; i++) {
				dashboardList.add(mRequestModel.getDashboardArray()[i]);
			}
		} else {
			dashboardList = null;
		}

		String sigOD = null;
		if (mRequestModel.getSigOD() != null && !mRequestModel.getSigOD().isEmpty())
			sigOD = mRequestModel.getSigOD().toString();
		else {

		}

		BasicDBObject document = new BasicDBObject();

		document.put("user", user);
		document.put("name", name);
		document.put("level", level);
		document.put("region", regionList);
		document.put("country", countryList);
		document.put("pos", posList);
		document.put("od", odList);
		document.put("compartment", compartmentList);
		document.put("competitors", competitorList);
		document.put("dashboards", dashboardList);
		document.put("sig_od", sigOD);

		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		String collectionName = "JUP_DB_User_Profile";
		try {
			if (db.collectionExists(collectionName)) {
				lCollection = db.getCollection(collectionName);
			}
			if (lCollection != null && db.collectionExists(collectionName))
				lCollection.insert(document);

		} catch (Exception e) {
			logger.error("addUser-Exception", e);
			return false;
		}
		return true;
	}

	@Override
	public int saveWorkPackage(WorkPackage pWorkPackage) {

		int lRow = 1;
		try {

			DB lDb = null;
			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			DBCollection lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_WorkPackage");

			BasicDBObject lDocumentDetail = new BasicDBObject();

			List<String> triggerIdList = new ArrayList<String>();
			for (int i = 0; i < pWorkPackage.getTriggerId().length; i++) {
				triggerIdList.add(pWorkPackage.getTriggerId()[i]);
			}

			lDocumentDetail.put("WorkPackageID", CalculationUtil.generateRandomString());
			lDocumentDetail.put("TriggerID", triggerIdList);
			lDocumentDetail.put("WorkPackageName", pWorkPackage.getPackageName());
			lDocumentDetail.put("BusinessArea", pWorkPackage.getBusinessRegion());
			lDocumentDetail.put("ProductType", pWorkPackage.getProductType());
			lDocumentDetail.put("ProductSubtype", pWorkPackage.getSubType());
			lDocumentDetail.put("Priority", pWorkPackage.getPriorityNumber());
			lDocumentDetail.put("CreatedBy", pWorkPackage.getCreatedBy());
			lDocumentDetail.put("Review_level", pWorkPackage.getReviewLevel());
			lDocumentDetail.put("Target_Filing_date", pWorkPackage.getTargetFilingDate());
			lDocumentDetail.put("Filing_status", pWorkPackage.getFilingStatus());
			lDocumentDetail.put("QA_status", pWorkPackage.getQAStatus());
			lDocumentDetail.put("Creation_date", pWorkPackage.getCreationDate());

			lCollection.insert(lDocumentDetail);

		} catch (MongoException e) {

			lRow = 0;
		}

		return lRow;
	}

	@Override
	public ArrayList<DBObject> getWorkPackage() {

		DB lDb = null;
		DBCursor cursor = null;
		DBCollection lCollection = null;
		ArrayList<DBObject> workPackageList = new ArrayList<DBObject>();
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_WorkPackage");

			cursor = lCollection.find();

			if (cursor != null) {
				while (cursor.hasNext()) {

					workPackageList.add(cursor.next());

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return workPackageList;
	}

	@Override
	public int saveAction(List<Diffuser> pDiffuserList) {

		int lRow = 1;

		DB lDb = null;
		BasicDBObject lNewDocument = null;
		BasicDBObject searchQuery = null;
		BasicDBObject updateField = null;
		DBObject dbObject = null;

		/*
		 * updating status,accepted_Recommended_Fare and accepted_Base_Fare in
		 * JUP_DB_Workflow_1 collection
		 */

		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			DBCollection lWorkflowCollection = null;
			if (lDb != null)
				lWorkflowCollection = lDb.getCollection("JUP_DB_Workflow_1_dummy");

			List<String> fareBasisList = new ArrayList<String>();

			for (Diffuser pDiffuser : pDiffuserList) {

				float acceptedRecommendedFare = 0;
				float surcharge = 0;
				float taxes = 0;
				float yq_q = 0;
				float acceptedBaseFare = 0;
				float yr = 0;

				if (pDiffuser.getRecommendedFare() != null)
					acceptedRecommendedFare = Float.parseFloat(pDiffuser.getRecommendedFare());

				if (pDiffuser.getSurcharges() != null)
					surcharge = Float.parseFloat(pDiffuser.getSurcharges());

				if (pDiffuser.getTaxes() != null)
					taxes = Float.parseFloat(pDiffuser.getTaxes());

				if (pDiffuser.getYQ_Q() != null)
					yq_q = Float.parseFloat(pDiffuser.getYQ_Q());

				if (pDiffuser.getYR() != null)
					yr = Float.parseFloat(pDiffuser.getYR());

				acceptedBaseFare = acceptedRecommendedFare - (surcharge + taxes + yq_q + yr);

				searchQuery = new BasicDBObject();
				updateField = new BasicDBObject();

				searchQuery.append("trigger_id", pDiffuser.getTriggerId());
				searchQuery.append("farebasis", pDiffuser.getFareBasisCode());
				searchQuery.append("origin", pDiffuser.getOrigin());
				searchQuery.append("destination", pDiffuser.getDestination());

				fareBasisList.add(pDiffuser.getFareBasisCode());

				lNewDocument = new BasicDBObject();

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.now();

				updateField.append("action_time", sdf.format(cal.getTime()).substring(0, 5));
				updateField.append("action_date", dtf.format(localDate));
				updateField.append("status", pDiffuser.getStatus());
				updateField.append("accepted_Base_Fare", acceptedBaseFare);
				updateField.append("accepted_Recommended_Fare", acceptedRecommendedFare);
				updateField.append("accepted_sale_date_from", pDiffuser.getSaleDateFrom());
				updateField.append("accepted_sale_date_to", pDiffuser.getSaleDateTo());
				updateField.append("accepted_travel_date_from", pDiffuser.getTravelDateFrom());
				updateField.append("accepted_travel_date_to", pDiffuser.getTravelDateTo());
				updateField.append("accepted_flight_number", pDiffuser.getFlightNumber());
				updateField.append("accepted_dow", pDiffuser.getDow());

				lNewDocument.append("$set", updateField);

				lWorkflowCollection.update(searchQuery, lNewDocument, false, true);

			}

			for (Diffuser pDiffuser : pDiffuserList) {
				DBObject searchQueryAutoAcpt = new BasicDBObject();
				searchQueryAutoAcpt.put("farebasis", new BasicDBObject("$nin", fareBasisList));
				searchQueryAutoAcpt.put("origin", pDiffuser.getOrigin());
				searchQueryAutoAcpt.put("destination", pDiffuser.getDestination());
				searchQueryAutoAcpt.put("trigger_id", pDiffuser.getTriggerId());
				searchQueryAutoAcpt.put("triggering_data.dep_date_end",
						new BasicDBObject("$gte", pDiffuser.getDepDateFrom()));
				searchQueryAutoAcpt.put("triggering_data.dep_date_start",
						new BasicDBObject("$lte", pDiffuser.getDepDateTo()));

				DBObject lNewDocumentTwo = new BasicDBObject();
				DBObject updateFieldTwo = new BasicDBObject();

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.now();

				updateFieldTwo.put("action_time", sdf.format(cal.getTime()).substring(0, 5));
				updateFieldTwo.put("action_date", dtf.format(localDate));
				updateFieldTwo.put("status", "Auto" + pDiffuser.getStatus());
				lNewDocumentTwo.put("$set", updateFieldTwo);

				lWorkflowCollection.update(searchQueryAutoAcpt, lNewDocumentTwo, false, true);

			}

		} catch (MongoException e) {
			lRow = 0; // if something goes wrong this variable will be set to 0
			logger.error("saveAction-Exception", e);
		}

		/*
		 * fetching the recently updated row by trigger Id, this row will next
		 * be inserted into JUP_DB_ATPCO_Diffuser collection
		 */
		try {

			DBCollection lWorkflowCollection = lDb.getCollection("JUP_DB_Workflow_1_dummy");

			for (Diffuser pDiffuser : pDiffuserList) {

				searchQuery = new BasicDBObject();
				searchQuery.append("trigger_id", pDiffuser.getTriggerId());
				searchQuery.append("origin", pDiffuser.getOrigin());
				searchQuery.append("destination", pDiffuser.getDestination());
				searchQuery.append("farebasis", pDiffuser.getFareBasisCode());

				dbObject = lWorkflowCollection.findOne(searchQuery);

				/*
				 * inserting dbObject into JUP_DB_ATPCO_Diffuser collection
				 */

				DBCollection lATPCODiffuserCollection = lDb.getCollection("JUP_DB_ATPCO_Diffuser");

				BasicDBObject searchQueryDiffuser = new BasicDBObject();
				searchQueryDiffuser.append("trigger_id", pDiffuser.getTriggerId());
				// searchQueryDiffuser.append("origin", pDiffuser.getOrigin());
				// searchQueryDiffuser.append("destination",
				// pDiffuser.getDestination());
				searchQueryDiffuser.append("farebasis", pDiffuser.getFareBasisCode());

				DBCursor cursor = null;
				cursor = lATPCODiffuserCollection.find(searchQueryDiffuser);

				/*
				 * checking if the trigger id already exists in
				 * JUP_DB_ATPCO_Diffuser collection
				 */

				if (cursor != null) {
					/*
					 * if a record for that trigger Id already exists, it will
					 * be deleted
					 */
					while (cursor.hasNext()) {
						lATPCODiffuserCollection.remove(cursor.next());

					}
				}

				lNewDocument = new BasicDBObject();

				DBObject dbObjHostpricingdData = null;
				if (dbObject != null && dbObject.containsKey("host_pricing_data"))
					dbObjHostpricingdData = (BasicDBObject) dbObject.get("host_pricing_data");

				if (dbObject.containsKey("trigger_id") && dbObject.get("trigger_id") != null)
					lNewDocument.put("trigger_id", dbObject.get("trigger_id").toString());
				else
					lNewDocument.put("trigger_id", null);

				if (dbObject.containsKey("origin") && dbObject.get("origin") != null)
					lNewDocument.put("origin", dbObject.get("origin").toString());
				else
					lNewDocument.put("origin", null);

				if (dbObject.containsKey("destination") && dbObject.get("destination") != null)
					lNewDocument.put("destination", dbObject.get("destination").toString());
				else
					lNewDocument.put("destination", null);

				if (dbObject.containsKey("compartment") && dbObject.get("compartment") != null)
					lNewDocument.put("compartment", dbObject.get("compartment").toString());
				else
					lNewDocument.put("compartment", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("tariff_code")
						&& dbObjHostpricingdData.get("tariff_code") != null)
					lNewDocument.put("tariff_code", dbObjHostpricingdData.get("tariff_code").toString());
				else
					lNewDocument.put("tariff_code", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("rtg")
						&& dbObjHostpricingdData.get("rtg") != null)
					lNewDocument.put("routing_number", dbObjHostpricingdData.get("rtg").toString());
				else
					lNewDocument.put("routing_number", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("oneway_return")
						&& dbObjHostpricingdData.get("oneway_return") != null)
					lNewDocument.put("oneway_return", dbObjHostpricingdData.get("oneway_return").toString());
				else
					lNewDocument.put("oneway_return", null);

				if (dbObject.containsKey("farebasis") && dbObject.get("farebasis") != null)
					lNewDocument.put("farebasis", dbObject.get("farebasis").toString());
				else
					lNewDocument.put("farebasis", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("footnote")
						&& dbObjHostpricingdData.get("footnote") != null)
					lNewDocument.put("footnote", dbObjHostpricingdData.get("footnote").toString());
				else
					lNewDocument.put("footnote", null);

				if (dbObject.containsKey("currency") && dbObject.get("currency") != null)
					lNewDocument.put("currency", dbObject.get("currency").toString());
				else
					lNewDocument.put("currency", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("RBD")
						&& dbObjHostpricingdData.get("RBD") != null)
					lNewDocument.put("RBD", dbObjHostpricingdData.get("RBD").toString());
				else
					lNewDocument.put("RBD", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("RBD_type")
						&& dbObjHostpricingdData.get("RBD_type") != null)
					lNewDocument.put("RBD_type", dbObjHostpricingdData.get("RBD_type").toString());
				else
					lNewDocument.put("RBD_type", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("Parent_RBD")
						&& dbObjHostpricingdData.get("Parent_RBD") != null)
					lNewDocument.put("Parent_RBD", dbObjHostpricingdData.get("Parent_RBD").toString());
				else
					lNewDocument.put("Parent_RBD", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("fare")
						&& dbObjHostpricingdData.get("fare") != null)
					lNewDocument.put("current_fare", dbObjHostpricingdData.get("fare").toString());
				else
					lNewDocument.put("current_fare", null);

				if (dbObject.containsKey("accepted_Recommended_Fare")
						&& dbObject.get("accepted_Recommended_Fare") != null)
					lNewDocument.put("Fare", dbObject.get("accepted_Recommended_Fare").toString());
				else
					lNewDocument.put("Fare", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("derived_fare")
						&& dbObjHostpricingdData.get("derived_fare") != null)
					lNewDocument.put("derived_fare", dbObjHostpricingdData.get("derived_fare").toString());
				else
					lNewDocument.put("derived_fare", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("Surcharges")
						&& dbObjHostpricingdData.get("Surcharges") != null) {
					lNewDocument.put("current_surcharge", dbObjHostpricingdData.get("Surcharges").toString());
					lNewDocument.put("accepted_surcharge", dbObjHostpricingdData.get("Surcharges").toString());
				} else {

					lNewDocument.put("current_surcharge", null);
					lNewDocument.put("accepted_surcharge", null);
				}

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("YQ")
						&& dbObjHostpricingdData.get("YQ") != null) {
					lNewDocument.put("current_YQ", dbObjHostpricingdData.get("YQ").toString());
					lNewDocument.put("accepted_YQ", dbObjHostpricingdData.get("YQ").toString());
					lNewDocument.put("YQ", dbObjHostpricingdData.get("YQ").toString());
				} else {

					lNewDocument.put("current_YQ", null);
					lNewDocument.put("accepted_YQ", null);
					lNewDocument.put("YQ", null);
				}

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("taxes")
						&& dbObjHostpricingdData.get("taxes") != null) {
					lNewDocument.put("current_taxes", dbObjHostpricingdData.get("taxes").toString());
					lNewDocument.put("accepted_taxes", dbObjHostpricingdData.get("taxes").toString());
					lNewDocument.put("taxes", dbObjHostpricingdData.get("taxes").toString());
				} else {

					lNewDocument.put("current_taxes", null);
					lNewDocument.put("accepted_taxes", null);
					lNewDocument.put("taxes", null);
				}

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("Rule_id")
						&& dbObjHostpricingdData.get("Rule_id") != null)
					lNewDocument.put("Rule_id", dbObjHostpricingdData.get("Rule_id").toString());
				else
					lNewDocument.put("Rule_id", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("exchange_rate")
						&& dbObjHostpricingdData.get("exchange_rate") != null)
					lNewDocument.put("exchange_rate", dbObjHostpricingdData.get("exchange_rate").toString());
				else
					lNewDocument.put("exchange_rate", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("channel")
						&& dbObjHostpricingdData.get("channel") != null)
					lNewDocument.put("channel", dbObjHostpricingdData.get("channel").toString());
				else
					lNewDocument.put("channel", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("derived_filed_fare")
						&& dbObjHostpricingdData.get("derived_filed_fare") != null)
					lNewDocument.put("derived_filed_fare", dbObjHostpricingdData.get("derived_filed_fare").toString());
				else
					lNewDocument.put("derived_filed_fare", null);

				if (dbObject.containsKey("accepted_travel_date_from")
						&& dbObject.get("accepted_travel_date_from") != null)
					lNewDocument.put("dep_date_from", dbObject.get("accepted_travel_date_from").toString());
				else
					lNewDocument.put("dep_date_from", null);

				if (dbObject.containsKey("accepted_travel_date_to") && dbObject.get("accepted_travel_date_to") != null)
					lNewDocument.put("dep_date_to", dbObject.get("accepted_travel_date_to").toString());
				else
					lNewDocument.put("dep_date_to", null);

				if (dbObject.containsKey("accepted_sale_date_from") && dbObject.get("accepted_sale_date_from") != null)
					lNewDocument.put("sale_date_from", dbObject.get("accepted_sale_date_from").toString());
				else
					lNewDocument.put("sale_date_from", null);

				if (dbObject.containsKey("accepted_sale_date_to") && dbObject.get("accepted_sale_date_to") != null)
					lNewDocument.put("sale_date_to", dbObject.get("accepted_sale_date_to").toString());
				else
					lNewDocument.put("sale_date_to", null);

				if (dbObject.containsKey("accepted_dow") && dbObject.get("accepted_dow") != null)
					lNewDocument.put("accepted_dow", dbObject.get("accepted_dow").toString());
				else
					lNewDocument.put("accepted_dow", null);

				if (dbObject.containsKey("accepted_flight_number") && dbObject.get("accepted_flight_number") != null)
					lNewDocument.put("flight_number", dbObject.get("accepted_flight_number").toString());
				else
					lNewDocument.put("flight_number", null);

				if (dbObject.containsKey("effective_from") && dbObject.get("effective_from") != null)
					lNewDocument.put("effective_from", dbObject.get("effective_from").toString());
				else
					lNewDocument.put("effective_from", null);

				if (dbObject.containsKey("effective_to") && dbObject.get("effective_to") != null)
					lNewDocument.put("effective_to", dbObject.get("effective_to").toString());
				else
					lNewDocument.put("effective_to", null);

				if (dbObject.containsKey("accepted_Base_Fare") && dbObject.get("accepted_Base_Fare") != null)
					lNewDocument.put("accepted_base_fare", dbObject.get("accepted_Base_Fare").toString());
				else
					lNewDocument.put("accepted_base_fare", null);

				if (dbObject.containsKey("accepted_Recommended_Fare")
						&& dbObject.get("accepted_Recommended_Fare") != null)
					lNewDocument.put("accepted_fare", dbObject.get("accepted_Recommended_Fare").toString());
				else
					lNewDocument.put("accepted_fare", null);

				lATPCODiffuserCollection.insert(lNewDocument);

			}

		} catch (

		Exception e) {

			lRow = 0;
			logger.error("saveAction-Exception", e);
		}

		return lRow;
	}

	@Override
	public ArrayList<DBObject> getWorkFlowFareAnalysis(RequestModel pRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		String startDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			startDate = pRequestModel.getFromDate().toString();
		else {

			startDate = "2017-02-14";
		}
		String endDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			endDate = pRequestModel.getToDate().toString();
		else {

			endDate = "2017-02-14";
		}
		ArrayList<String> odList = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {

			for (int i = 0; i < pRequestModel.getOdArray().length; i++)

				odList.add(pRequestModel.getOdArray()[i]);
		} else {
			odList = null;
		}
		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> compartmentList = null;
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			compartmentList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		}

		String startDateJson = new flexjson.JSONSerializer().serialize(startDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(endDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}
		String odJson = Utility.create_json(odList);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
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

		String query = "JUP_FN_Workflow_Fare_analysis(" + posJson + "," + odJson + "," + compartmentJson + ","
				+ startDateJson + "," + endDateJson + "," + lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			db.eval(query);
			if (db.collectionExists(lCollectionName)) {
				lCollection = db.getCollection(lCollectionName);
				cursor = lCollection.find();
			}
			List<DBObject> array = new ArrayList<DBObject>();
			if (cursor != null)
				array = cursor.toArray();
			for (int i = 0; i < array.size(); i++) {

				DBObject faresDBObject = array.get(i);

				if (faresDBObject != null && faresDBObject.containsKey("onewayReturn")) {
					if (Integer.parseInt(faresDBObject.get("onewayReturn").toString()) == 1)
						faresDBObject.put("onewayReturn", "O");
					else
						faresDBObject.put("onewayReturn", "R");

				}

				lDataList.add(faresDBObject);
			}

			System.out.println(lDataList);

		} catch (Exception e) {
			logger.error("getBookingsMarketShare-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		return lDataList;

	}

	@Override
	public ArrayList<DBObject> getRBDDiffuser() {

		DB lDb = null;
		DBCursor cursor = null;
		DBCollection lCollection = null;
		ArrayList<DBObject> rbdDiffuserList = new ArrayList<DBObject>();
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_ATPCO_RBD_Diffuser");

			cursor = lCollection.find();

			if (cursor != null) {
				while (cursor.hasNext()) {

					rbdDiffuserList.add(cursor.next());

				}
			}

		} catch (Exception e) {

		}

		return rbdDiffuserList;
	}

	@Override
	public ArrayList<DBObject> getFareBrandDiffuser() {

		DB lDb = null;
		DBCursor cursor = null;
		DBCollection lCollection = null;
		ArrayList<DBObject> rbdFareBrandList = new ArrayList<DBObject>();
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_ATPCO_Fare_Brand_Diffuser");

			cursor = lCollection.find();

			if (cursor != null) {
				while (cursor.hasNext()) {

					rbdFareBrandList.add(cursor.next());

				}
			}

		} catch (Exception e) {

		}

		return rbdFareBrandList;

	}

	@Override
	public ArrayList<DBObject> getChannelDiffuser() {

		DB lDb = null;
		DBCursor cursor = null;
		DBCollection lCollection = null;
		ArrayList<DBObject> channelDiffuserList = new ArrayList<DBObject>();
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_ATPCO_Channel_Diffuser");

			cursor = lCollection.find();

			if (cursor != null) {
				while (cursor.hasNext()) {

					channelDiffuserList.add(cursor.next());

				}
			}

		} catch (Exception e) {

		}

		return channelDiffuserList;

	}

	@Override
	public ArrayList<DBObject> getSavedTriggerAction() {

		DB lDb = null;
		DBCursor cursor = null;
		DBCollection lCollection = null;
		ArrayList<DBObject> triggerActionList = new ArrayList<DBObject>();
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_ATPCO_Diffuser");

			cursor = lCollection.find();

			if (cursor != null) {
				while (cursor.hasNext()) {

					triggerActionList.add(cursor.next());

				}
			}

		} catch (Exception e) {

		}

		return triggerActionList;

	}

	@Override
	public int fileFare(String workPackageId) {

		int lRow = 1;
		DBCollection lWorkPackageCollection = null;
		DBCollection lATPCODiffuserCollection = null;
		DBCollection lATPCOFaresCollection = null;
		DB lDb = null;
		DBObject searchQuery = null;

		DBCursor workPackageCursor = null;
		DBCursor atpcoDiffuserCursor = null;

		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			if (lDb != null) {
				lWorkPackageCollection = lDb.getCollection("JUP_DB_WorkPackage");
				lATPCODiffuserCollection = lDb.getCollection("JUP_DB_ATPCO_Diffuser");
				lATPCOFaresCollection = lDb.getCollection("JUP_DB_ATPCO_Fares_Java");

			}

			searchQuery = new BasicDBObject();
			searchQuery.put("WorkPackageID", workPackageId);

			workPackageCursor = lWorkPackageCollection.find(searchQuery);

			List<String> triggerIdList = new ArrayList<String>();

			while (workPackageCursor.hasNext()) {

				DBObject workPakageObject = workPackageCursor.next();
				if (workPakageObject != null && workPakageObject.containsKey("TriggerID"))
					triggerIdList.add(workPakageObject.get("TriggerID").toString());

			}

			BasicDBObject queryAtpcoDiffuser = new BasicDBObject();
			queryAtpcoDiffuser.put("trigger_id", new BasicDBObject("$in", triggerIdList));
			atpcoDiffuserCursor = lATPCODiffuserCollection.find(queryAtpcoDiffuser);

			while (atpcoDiffuserCursor.hasNext()) {

				DBObject atpcoDiffuserObject = atpcoDiffuserCursor.next();
				DBObject insertDBobj = new BasicDBObject();
				insertDBobj.put("Tx_record_type", "F");
				insertDBobj.put("data_owning_carrier_code", "FZ");

				if (atpcoDiffuserObject.containsKey("tariff_number")
						&& atpcoDiffuserObject.get("tariff_number") != null)
					insertDBobj.put("tariff_number", atpcoDiffuserObject.get("tariff_number").toString());
				else
					insertDBobj.put("tariff_number", "");
				insertDBobj.put("action_code", "N");

				if (atpcoDiffuserObject.containsKey("origin") && atpcoDiffuserObject.get("origin") != null)
					insertDBobj.put("origin", atpcoDiffuserObject.get("origin").toString());
				else
					insertDBobj.put("origin", "");

				if (atpcoDiffuserObject.containsKey("destination") && atpcoDiffuserObject.get("destination") != null)
					insertDBobj.put("destination", atpcoDiffuserObject.get("destination").toString());
				else
					insertDBobj.put("destination", "");

				if (atpcoDiffuserObject.containsKey("farebasis") && atpcoDiffuserObject.get("farebasis") != null)
					insertDBobj.put("fare_class", atpcoDiffuserObject.get("farebasis").toString());
				else
					insertDBobj.put("fare_class", "");

				if (atpcoDiffuserObject.containsKey("oneway_return")
						&& atpcoDiffuserObject.get("oneway_return") != null)
					insertDBobj.put("trip_indicator", atpcoDiffuserObject.get("oneway_return").toString());
				else
					insertDBobj.put("trip_indicator", "");

				if (atpcoDiffuserObject.containsKey("routing_number")
						&& atpcoDiffuserObject.get("routing_number") != null)
					insertDBobj.put("routing_number", atpcoDiffuserObject.get("routing_number").toString());
				else
					insertDBobj.put("routing_number", "");

				if (atpcoDiffuserObject.containsKey("footnote") && atpcoDiffuserObject.get("footnote") != null)
					insertDBobj.put("footnote", atpcoDiffuserObject.get("footnote").toString());
				else
					insertDBobj.put("footnote", "");

				if (atpcoDiffuserObject.containsKey("currency") && atpcoDiffuserObject.get("currency") != null)
					insertDBobj.put("currency_code", atpcoDiffuserObject.get("currency").toString());
				else
					insertDBobj.put("currency_code", "");

				if (atpcoDiffuserObject.containsKey("accepted_fare")
						&& atpcoDiffuserObject.get("accepted_fare") != null)
					insertDBobj.put("fare_amount", atpcoDiffuserObject.get("accepted_fare").toString());
				else
					insertDBobj.put("fare_amount", "");

				insertDBobj.put("change_routing_number", "");
				insertDBobj.put("change_footnote", "");
				insertDBobj.put("change_currency", "");

				if (atpcoDiffuserObject.containsKey("effective_from")
						&& atpcoDiffuserObject.get("effective_from") != null)
					insertDBobj.put("effective_date", atpcoDiffuserObject.get("effective_from").toString());
				else
					insertDBobj.put("effective_date", "");

				insertDBobj.put("last_filed_date", "");

				lATPCOFaresCollection.insert(insertDBobj);

			}

			DBCursor cursor = lATPCOFaresCollection.find();

			JSONArray atpcoFaresArray = new JSONArray();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet spreadsheet = workbook.createSheet(Constants.FARE_FILING_EXCEL_SHEET_NAME);

			File file = new File(Constants.excelFileName());
			if (file.exists() && !file.isDirectory()) {
				file.delete();
				file.createNewFile();
			} else
				file.createNewFile();

			int rowCount = 0;
			if (cursor != null) {
				while (cursor.hasNext()) {
					DBObject faresDBObject = cursor.next();

					Map<String, String> faresMap = new LinkedHashMap<String, String>();

					if (faresDBObject.containsKey("Tx_record_type"))
						faresMap.put("Tx_record_type", faresDBObject.get("Tx_record_type").toString());

					faresMap.put("data_owning_carrier_code", faresDBObject.get("data_owning_carrier_code").toString());

					if (faresDBObject.containsKey("tariff_number") && faresDBObject.get("tariff_number") != null)
						faresMap.put("tariff_number", faresDBObject.get("tariff_number").toString());
					else
						faresMap.put("tariff_number", "NA");

					if (faresDBObject.containsKey("action_code") && faresDBObject.get("action_code") != null)
						faresMap.put("action_code", faresDBObject.get("action_code").toString());

					if (faresDBObject.containsKey("origin") && faresDBObject.get("origin") != null)
						faresMap.put("origin", faresDBObject.get("origin").toString());
					else
						faresMap.put("origin", "NA");

					if (faresDBObject.containsKey("destination") && faresDBObject.get("destination") != null)
						faresMap.put("destination", faresDBObject.get("destination").toString());
					else
						faresMap.put("destination", "NA");

					if (faresDBObject.containsKey("farebasis") && faresDBObject.get("farebasis") != null)
						faresMap.put("fare_class", faresDBObject.get("farebasis").toString());
					else
						faresMap.put("fare_class", "NA");

					if (faresDBObject.containsKey("oneway_return") && faresDBObject.get("oneway_return") != null)
						faresMap.put("trip_indicator", faresDBObject.get("oneway_return").toString());
					else
						faresMap.put("trip_indicator", "NA");

					if (faresDBObject.containsKey("routing_number") && faresDBObject.get("routing_number") != null)
						faresMap.put("routing_number", faresDBObject.get("routing_number").toString());
					else
						faresMap.put("routing_number", "NA");

					if (faresDBObject.containsKey("footnote") && faresDBObject.get("footnote") != null)
						faresMap.put("footnote", faresDBObject.get("footnote").toString());
					else
						faresMap.put("footnote", "NA");

					if (faresDBObject.containsKey("currency_code") && faresDBObject.get("currency_code") != null)
						faresMap.put("currency_code", faresDBObject.get("currency_code").toString());
					else
						faresMap.put("currency_code", "NA");

					if (faresDBObject.containsKey("fare_amount") && faresDBObject.get("fare_amount") != null)
						faresMap.put("fare_amount", faresDBObject.get("fare_amount").toString());
					else
						faresMap.put("fare_amount", "NA");

					if (faresDBObject.containsKey("change_routing_number"))
						faresMap.put("change_routing_number", faresDBObject.get("change_routing_number").toString());
					else
						faresMap.put("change_routing_number", "NA");

					if (faresDBObject.containsKey("change_footnote"))
						faresMap.put("change_footnote", faresDBObject.get("change_footnote").toString());
					else
						faresMap.put("change_footnote", "NA");

					if (faresDBObject.containsKey("change_currency"))
						faresMap.put("change_currency", faresDBObject.get("change_currency").toString());
					else
						faresMap.put("change_currency", "NA");

					if (faresDBObject.containsKey("effective_date"))
						faresMap.put("effective_date", faresDBObject.get("effective_date").toString());
					else
						faresMap.put("effective_date", "NA");

					if (faresDBObject.containsKey("last_filed_date"))
						faresMap.put("last_filed_date", faresDBObject.get("last_filed_date").toString());
					else
						faresMap.put("last_filed_date", "NA");

					Row row = spreadsheet.createRow(rowCount);
					Cell cell = null;
					int column = 0;
					for (String str : faresMap.keySet()) {
						cell = row.createCell(column);

						cell.setCellType(Cell.CELL_TYPE_STRING);

						FileOutputStream fos = new FileOutputStream(file);
						if (rowCount == 0)
							cell.setCellValue(str);
						else
							cell.setCellValue(faresMap.get(str));
						workbook.write(fos);

						column++;
					}

					JSONObject faresJsonObj = new JSONObject(faresMap);
					atpcoFaresArray.put(faresJsonObj);

					rowCount++;
				}
			}

		} catch (MongoException e) {
			lRow = 0;
		} catch (IOException e) {

			e.printStackTrace();
			lRow = 0;
		}

		return lRow;
	}

	@Override
	public int insert(Upsert upsert) {
		BasicDBObject lNewDocument = null;
		BasicDBObject updateField = null;
		DBCollection lCollection = null;
		DB lDb = null;
		int lRow = 1;
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_Afsheen");

			BasicDBObject lsearch = new BasicDBObject();
			DBObject searchValue = null;
			updateField = new BasicDBObject();
			String triggerID = null;

			if (upsert.getTriggerId() != null && !upsert.getTriggerId().isEmpty()) {
				triggerID = upsert.getTriggerId();
				upsert.setTriggerId(triggerID);
			} else {

				upsert.setTriggerId(triggerID);
			}

			ArrayList<String> flightNumberList = new ArrayList<String>();
			if (upsert.getFlightnumberArray() != null && upsert.getFlightnumberArray().length > 0) {
				for (int i = 0; i < upsert.getFlightnumberArray().length; i++)
					flightNumberList.add(upsert.getFlightnumberArray()[i]);
			} else {
				flightNumberList = null;
			}
			ArrayList<String> daysofweekList = new ArrayList<String>();
			if (upsert.getDaysofweekArray() != null && upsert.getDaysofweekArray().length > 0) {
				for (int i = 0; i < upsert.getDaysofweekArray().length; i++)
					daysofweekList.add(upsert.getDaysofweekArray()[i]);
			} else {
				daysofweekList = null;
			}

			lsearch = new BasicDBObject();
			lsearch.append("trigger_id", triggerID).append("origin", upsert.getOrigin()).append("pos", upsert.getPos());
			searchValue = lCollection.findOne(lsearch);
			if (searchValue != null) {
				lNewDocument = new BasicDBObject();
				updateField.put("Flight number", upsert.getFlightnumberArray());
				updateField.put("Days of week", upsert.getDaysofweekArray());
				lNewDocument.put("$set", updateField);
				lCollection.update(lsearch, lNewDocument);
			}
		} catch (Exception e) {
			lRow = 0;
			e.printStackTrace();
		}

		return lRow;
	}

	@Override
	public List<DBObject> getODTrgrData(RequestModel pRequestModel) {

		List<DBObject> dbObjList = new ArrayList<DBObject>();

		DBCursor cursor = null;
		DB lDb = null;
		DBCollection lCollection = null;

		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_Workflow_OD_User");

			if (lCollection != null)
				cursor = lCollection.find();

			if (cursor != null) {

				while (cursor.hasNext()) {

					dbObjList.add(cursor.next());

				}

			}

			System.out.println(dbObjList);

		} catch (Exception e) {

			logger.error("getODTrgrData-Exception ", e);

		}

		return dbObjList;
	}

	@Override
	public ArrayList<DBObject> getFlightAnalysis(RequestModel pRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();
		DBCursor cursor = null;

		String startDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			startDate = pRequestModel.getFromDate().toString();
		else {
			startDate = "2017-02-14";
		}
		String endDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			endDate = pRequestModel.getToDate().toString();
		else {
			endDate = "2017-02-14";
		}
		String od = null;
		if (pRequestModel.getSingleOD() != null && !pRequestModel.getSingleOD().isEmpty())
			od = pRequestModel.getSingleOD().toString();

		String startDateJson = new flexjson.JSONSerializer().serialize(startDate);

		String endDateJson = new flexjson.JSONSerializer().serialize(endDate);

		String odJson = null;
		if (od != null)
			odJson = new flexjson.JSONSerializer().serialize(od);

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_workflow_Flight_Analysis(" + odJson + "," + startDateJson + "," + endDateJson + ","
				+ lTempCollectionName + ")";

		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {

			db.eval(query);
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

			System.out.println(lDataList);

		} catch (Exception e) {
			logger.error("getForcastAvailability-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName)) {
				lCollection.drop();
			}
		}
		return lDataList;

	}

	@Override
	public List<DBObject> getRegion(RequestModel pRequestModel) {

		ArrayList<String> posList = null;
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {
			posList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getPosArray().length; i++) {
				posList.add(pRequestModel.getPosArray()[i]);
			}
		}

		DBObject query = null;
		if (posList != null) {
			query = new QueryBuilder().start().or(new QueryBuilder().start().put("POS_CD").in(posList).get()).get();
		} else {
			query = new QueryBuilder().start().and(new QueryBuilder().start().get()).get();
		}

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection coll = db.getCollection("JUP_DB_Region_Master");
		BasicDBObject document = new BasicDBObject();
		document.put("$and", query);
		DBObject match = new BasicDBObject("$match", query);
		DBObject project = new BasicDBObject("$project", new BasicDBObject("COUNTRY_CD", 1));
		List<DBObject> pipeline = Arrays.asList(match, project);
		AggregationOutput output = (coll).aggregate(match, project);

		coll.aggregate(pipeline);

		ArrayList<DBObject> resultList = new ArrayList<DBObject>();
		for (DBObject result : output.results()) {
			resultList.add(result);
		}

		return resultList;

	}

	@Override
	public ArrayList<DBObject> getWorkflowInfareData(RequestModel pRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String startDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty()) {
			startDate = pRequestModel.getFromDate().toString();
		}
		String endDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty()) {
			endDate = pRequestModel.getToDate().toString();
		}

		ArrayList<String> odList = null;
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {
			odList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				odList.add(pRequestModel.getOdArray()[i]);
			}
		}

		/*
		 * ArrayList<String> posList = new ArrayList<String>(); if
		 * (pRequestModel.getPosArray() != null &&
		 * pRequestModel.getPosArray().length > 0) {
		 * 
		 * for (int i = 0; i < pRequestModel.getPosArray().length; i++)
		 * 
		 * posList.add(pRequestModel.getPosArray()[i]); } else { posList = null;
		 * }
		 */
		List<DBObject> posObj = getRegion(pRequestModel);

		JSONArray data = new JSONArray(posObj);
		ArrayList<String> posList = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);
			String pos = null;
			if (jsonObj.has("COUNTRY_CD") && jsonObj.get("COUNTRY_CD") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("COUNTRY_CD").toString())) {
				pos = (jsonObj.get("COUNTRY_CD").toString());

			}

			posList.add(pos);

		}

		String compartment = "-";
		ArrayList<String> compartmentList = null;
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			compartmentList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				if (pRequestModel.getCompartmentArray()[i].equals("Y")) {
					compartment = "E";
				}
				if (pRequestModel.getCompartmentArray()[i].equals("J")) {
					compartment = "B";
				}
				compartmentList.add(compartment);
			}
		}

		DBObject query = new QueryBuilder().start().and(new QueryBuilder().start().put("outbound_departure_date")
				.greaterThanEquals(startDate).lessThanEquals(endDate).get()).get();
		DBObject query1 = null;

		if (posList != null) {

			query1 = new QueryBuilder().start().and(query, new QueryBuilder().start().put("pos").in(posList).get())
					.get();

		} else {
			query1 = new QueryBuilder().start().and(query, new QueryBuilder().start().get()).get();

		}
		DBObject query2 = null;

		if (compartmentList != null) {

			query2 = new QueryBuilder().start()
					.and(query1, new QueryBuilder().start().put("compartment").in(compartmentList).get()).get();

		} else {
			query2 = new QueryBuilder().start().and(query1, new QueryBuilder().start().get()).get();

		}
		ArrayList<DBObject> resultList = new ArrayList<DBObject>();
		if (odList != null) {
			DBObject query3 = new QueryBuilder().start()
					.and(query2, new QueryBuilder().start().put("od").in(odList).get()).get();

			DBObject Lookup_LocalField = new BasicDBObject("from", "JUP_DB_Exchange_Rate")
					.append("localField", "currency").append("foreignField", "code").append("as", "Exchange");
			DBObject lookup = new BasicDBObject("$lookup", Lookup_LocalField);
			BasicDBObject document = new BasicDBObject();
			document.put("$and", query3);
			DBObject match = new BasicDBObject("$match", query3);
			DBObject unwindPath = new BasicDBObject("path", "$Exchange");
			DBObject unwindCombine = new BasicDBObject("$unwind", unwindPath);
			DBObject sorting = new BasicDBObject("$sort", new BasicDBObject("observation_date", -1)
					.append("observation_time", -1).append("price_outbound", 1).append("price_inbound", 1));
			Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
			dbObjIdMap.put("origin", "$origin");
			dbObjIdMap.put("destination", "$destination");
			dbObjIdMap.put("compartment", "$compartment");
			dbObjIdMap.put("carrier", "$carrier");
			dbObjIdMap.put("dep_date", "$outbound_departure_date");

			DBObject project = new BasicDBObject("$project", new BasicDBObject("carrier", "$doc.carrier")
					.append("pos", "$doc.pos").append("origin", "$doc.origin").append("destination", "$doc.destination")
					.append("observation_date", "$doc.observation_date")
					.append("observation_time", "$doc.observation_time")
					.append("outbound_flight_no", "$doc.outbound_flight_no")
					.append("outbound_departure_date", "$doc.outbound_departure_date")
					.append("outbound_departure_time", "$doc.outbound_departure_time")
					.append("outbound_fare_basis", "$doc.outbound_fare_basis")
					.append("outbound_booking_class", "$doc.outbound_booking_class")
					.append("price_outbound", "$doc.price_outbound")
					.append("inbound_flight_no", "$doc.inbound_flight_no")
					.append("inbound_departure_date", "$doc.inbound_departure_date")
					.append("inbound_arrival_date", "$doc.inbound_arrival_date")
					.append("inbound_fare_basis", "$doc.inbound_fare_basis").append("tax", "$doc.tax")
					.append("price_outbound", "$doc.price_outbound")
					.append("inbound_booking_class", "$doc.inbound_booking_class")
					.append("price_inbound", "$doc.price_inbound").append("tax", "$doc.tax")
					.append("price_outbound", "$doc.price_outbound").append("price_inbound", "$doc.price_inbound")
					.append("currency", "$doc.currency").append("Reference_Rate", "$doc.Exchange.Reference_Rate")
					.append("taxj",
							new BasicDBObject("$multiply", Arrays.asList("$doc.tax", "$doc.Exchange.Reference_Rate")))
					.append("price_incj",
							new BasicDBObject("$multiply",
									Arrays.asList("$doc.price_inc", "$doc.Exchange.Reference_Rate")))
					.append("price_outboundj",
							new BasicDBObject("$multiply",
									Arrays.asList("$doc.price_outbound", "$doc.Exchange.Reference_Rate")))
					.append("price_inboundj", new BasicDBObject("$multiply",
							Arrays.asList("$doc.price_inbound", "$doc.Exchange.Reference_Rate"))));
			DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			DBCollection coll = db.getCollection("JUP_DB_Infare");

			List<DBObject> pipeline = Arrays.asList(match, project);
			DBObject unwidiskusageObject = new BasicDBObject("allowDiskUse", true);
			List<DBObject> diskusage = Arrays.asList(unwidiskusageObject);
			List<DBObject> aggregateList = Arrays.asList(match, lookup, unwindCombine, sorting,
					new BasicDBObject("$group",
							new BasicDBObject("_id", dbObjIdMap).append("doc", new BasicDBObject("$first", "$$ROOT"))),
					project);

			Cursor aggregateOutput = coll.aggregate(aggregateList,
					AggregationOptions.builder().allowDiskUse(true).build());

			while (aggregateOutput.hasNext()) {
				DBObject docu = aggregateOutput.next();
				System.out.println(docu);
				resultList.add(docu);
			}
		} else {
			DBObject query3 = new QueryBuilder().start().and(query2, new QueryBuilder().start().get()).get();
			DBObject Lookup_LocalField = new BasicDBObject("from", "JUP_DB_Exchange_Rate")
					.append("localField", "currency").append("foreignField", "code").append("as", "Exchange");
			DBObject lookup = new BasicDBObject("$lookup", Lookup_LocalField);
			BasicDBObject document = new BasicDBObject();
			document.put("$and", query3);
			DBObject match = new BasicDBObject("$match", query3);
			DBObject unwindPath = new BasicDBObject("path", "$Exchange");
			DBObject unwindCombine = new BasicDBObject("$unwind", unwindPath);
			DBObject project = new BasicDBObject("$project", new BasicDBObject("carrier", 1).append("pos", 1)
					.append("origin", 1).append("destination", 1).append("observation_date", 1)
					.append("observation_time", 1).append("outbound_flight_no", 1).append("outbound_departure_date", 1)
					.append("outbound_departure_time", 1).append("outbound_fare_basis", 1)
					.append("outbound_booking_class", 1).append("price_outbound", 1).append("inbound_flight_no", 1)
					.append("inbound_departure_date", 1).append("inbound_arrival_date", 1)
					.append("inbound_fare_basis", 1).append("inbound_booking_class", 1).append("price_inbound", 1)
					.append("tax", 1).append("price_inc", 1).append("currency", 1)
					.append("Reference_Rate", "$Exchange.Reference_Rate").append("price_inc",
							new BasicDBObject("$multiply", Arrays.asList("$price_incj", "$Exchange.Reference_Rate"))));
			DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			DBCollection coll = db.getCollection("JUP_DB_Infare");

			List<DBObject> pipeline = Arrays.asList(match, project);
			DBObject unwidiskusageObject = new BasicDBObject("allowDiskUse", true);
			List<DBObject> diskusage = Arrays.asList(unwidiskusageObject);
			List<DBObject> aggregateList = Arrays.asList(match, lookup, unwindCombine, project);

			Cursor aggregateOutput = coll.aggregate(aggregateList,
					AggregationOptions.builder().allowDiskUse(true).build());

			while (aggregateOutput.hasNext()) {
				DBObject doc = aggregateOutput.next();
				System.out.println(doc);
				resultList.add(doc);
			}

		}

		return resultList;
	}

	@Override
	public int withdrawWorkPackage(Diffuser pDiffuser) {

		DB lDb = null;
		DBCollection lWorkPackageCollection = null;
		int row = 1;
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			if (lDb != null)
				lWorkPackageCollection = lDb.getCollection("JUP_DB_WorkPackage");

			BasicDBObject searchWorkPackage = new BasicDBObject();
			searchWorkPackage.put("WorkPackageName", pDiffuser.getWorkPackageName());

			BasicDBObject updateWorkPackage = new BasicDBObject();
			BasicDBObject newWorkPackage = new BasicDBObject();
			updateWorkPackage.append("Filing_status", "Withdrawn");
			updateWorkPackage.append("reason", pDiffuser.getReason());
			newWorkPackage.append("$set", updateWorkPackage);

			if (lWorkPackageCollection != null)
				lWorkPackageCollection.update(searchWorkPackage, newWorkPackage);

		} catch (Exception e) {

			row = 0;
			logger.error("withdrawWorkPackage-Exception", e);

		}

		return row;
	}

	@Override
	public int getConfigdate(RequestModel pRequestModel) {

		BasicDBObject lNewDocument = null;
		BasicDBObject updateField = null;
		DBCollection lCollection = null;
		DB lDb = null;
		int lRow = 1;
		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			if (lDb != null)
				lCollection = lDb.getCollection("JUP_DB_Config_Date");

			BasicDBObject lsearch = new BasicDBObject();
			DBObject searchValue = null;
			updateField = new BasicDBObject();
			String user = null;

			if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()) {
				user = pRequestModel.getUser();
				pRequestModel.setUser(user);
			} else {

				pRequestModel.setUser(user);
			}

			ArrayList<String> configDateList = new ArrayList<String>();
			if (pRequestModel.getConfigdateArray() != null && pRequestModel.getConfigdateArray().length > 0) {
				for (int i = 0; i < pRequestModel.getConfigdateArray().length; i++)
					configDateList.add(pRequestModel.getConfigdateArray()[i]);
			} else {
				configDateList = null;
			}

			lsearch = new BasicDBObject();
			lsearch.append("user", user);
			searchValue = lCollection.findOne(lsearch);
			if (searchValue != null) {
				lNewDocument = new BasicDBObject();
				updateField.put("configured_days", pRequestModel.getConfigdateArray());

				lNewDocument.put("$set", updateField);
				lCollection.update(lsearch, lNewDocument);
			}
		} catch (Exception e) {
			lRow = 0;
			e.printStackTrace();
		}

		return lRow;
	}

	@Override
	public ArrayList<DBObject> getConfiguredDates(RequestModel pRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String user = null;
		if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty())
			user = pRequestModel.getUser().toString();

		DBObject query = new QueryBuilder().start().and(new QueryBuilder().start().put("user").is(user).get()).get();

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection coll = db.getCollection("JUP_DB_Config_Date");
		BasicDBObject document = new BasicDBObject();
		document.put("$and", query);
		DBObject match = new BasicDBObject("$match", query);
		DBObject project = new BasicDBObject("$project", new BasicDBObject("configured_days", 1));
		List<DBObject> pipeline = Arrays.asList(match, project);
		AggregationOutput output = (coll).aggregate(match, project); // AggregationOutput
																		// output
																		// =
		coll.aggregate(pipeline);

		ArrayList<DBObject> resultList = new ArrayList<DBObject>();
		for (DBObject result : output.results()) {
			System.out.println(result);

			resultList.add(result);
			//
		}

		return resultList;
	}

	@Override
	public int getFlightDuration(String pOrigin, String pDestination) {

		String origin = null;
		if (pOrigin != null)
			origin = pOrigin;

		String destination = null;
		if (pDestination != null)
			destination = pDestination;

		int fltDuration = 0;

		DB lDb = null;
		if (mongoTemplateDemoDB != null)
			lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection lCollection = null;
		if (lDb != null)
			lCollection = lDb.getCollection("JUP_DB_OD_Duration");

		BasicDBObject searchODQuery = null;

		List<String> originList = null;
		List<String> destinationList = null;

		if (origin != null && destination != null) {
			if (!origin.contains("DXB") && !destination.contains("DXB")) {

				originList = new ArrayList<String>();
				originList.add(origin);
				originList.add("DXB");

				destinationList = new ArrayList<String>();
				destinationList.add("DXB");
				destinationList.add(destination);

			} else {

				originList = new ArrayList<String>();
				originList.add(origin);

				destinationList = new ArrayList<String>();
				destinationList.add(destination);

			}

			searchODQuery = new BasicDBObject();

			searchODQuery.put("Dept Sta", new BasicDBObject("$in", originList));
			searchODQuery.put("Arvl Sta", new BasicDBObject("$in", destinationList));

			DBCursor cursor = null;
			if (lCollection != null)
				cursor = lCollection.find(searchODQuery);

			int[] durationArray = new int[cursor.size()];
			int count = 0;
			if (cursor != null) {
				while (cursor.hasNext()) {
					DBObject dbObj = cursor.next();
					int duration = 0;
					if (dbObj.containsKey("flight_duration_minutes") && dbObj.get("flight_duration_minutes") != null)
						duration = Integer.parseInt(dbObj.get("flight_duration_minutes").toString());

					durationArray[count++] = duration;

				}

			}

			Arrays.sort(durationArray);

			fltDuration = durationArray[durationArray.length - 1];

		}

		return fltDuration;
	}

	public Map<String, TriggerType> getTriggerTypes(RequestModel mRequestModel) {

		Map<String, TriggerType> trgrTypeMap = new HashMap<String, TriggerType>();

		DB lDb = null;
		if (mongoTemplateDemoDB != null)
			lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection lCollection = null;
		if (lDb != null)
			lCollection = lDb.getCollection("JUP_DB_Trigger_Types");

		DBCursor cursor = null;
		if (lCollection != null)
			cursor = lCollection.find();

		if (cursor != null) {
			while (cursor.hasNext()) {
				DBObject dbObj = cursor.next();

				if (valueExists(dbObj, "desc")) {

					TriggerType trgrType = new TriggerType();

					if (valueExists(dbObj, "triggering_event"))
						trgrType.setTrgrEvent(dbObj.get("triggering_event").toString());

					if (valueExists(dbObj, "type_of_trigger"))
						trgrType.setTrgrType(dbObj.get("type_of_trigger").toString());

					if (valueExists(dbObj, "dashboard"))
						trgrType.setDshbrd(dbObj.get("dashboard").toString());

					if (valueExists(dbObj, "triggering_event_type"))
						trgrType.setTrgrEventType(dbObj.get("triggering_event_type").toString());

					trgrTypeMap.put(dbObj.get("desc").toString(), trgrType);

				}

			}
		}

		return trgrTypeMap;
	}

	@Override
	public AggTrgrTypes getAllTriggerTypes(RequestModel mRequestModel) {

		DB lDb = null;
		if (mongoTemplateDemoDB != null)
			lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection lCollection = null;
		if (lDb != null)
			lCollection = lDb.getCollection("JUP_DB_Workflow_1_dummy");

		DBCursor cursor = null;
		if (lCollection != null)
			cursor = lCollection.find();

		Map<String, TriggerType> trgrMap = getTriggerTypes(mRequestModel);
		Map<String, Integer> dashbCountMap = new HashMap<String, Integer>();
		Map<String, Integer> trgrEventCountMap = new HashMap<String, Integer>();
		Map<String, Integer> trgrEventTypeCountMap = new HashMap<String, Integer>();
		Map<String, Integer> trgrTypeCountMap = new HashMap<String, Integer>();

		if (cursor != null) {
			while (cursor.hasNext()) {
				DBObject dbObj = cursor.next();

				if (dbObj != null && dbObj.containsKey("trigger_type")) {

					String trgrDesc = dbObj.get("trigger_type").toString();

					if (trgrMap != null && trgrMap.containsKey(trgrDesc)) {

						TriggerType trgrType = trgrMap.get(trgrDesc);

						String dashbName = trgrType.getDshbrd();
						if (dashbCountMap.containsKey(dashbName)) {
							int dshbCount = dashbCountMap.get(dashbName) + 1;
							dashbCountMap.put(dashbName, dshbCount);
						} else
							dashbCountMap.put(dashbName, 1);

						String trgrEvent = trgrType.getTrgrEvent();
						if (trgrEventCountMap.containsKey(trgrEvent)) {
							int trgrEventCount = trgrEventCountMap.get(trgrEvent) + 1;
							trgrEventCountMap.put(trgrEvent, trgrEventCount);
						} else
							trgrEventCountMap.put(trgrEvent, 1);

						String trgrEventType = trgrType.getTrgrEventType();
						if (trgrEventTypeCountMap.containsKey(trgrEventType)) {
							int trgrEventTypeCount = trgrEventTypeCountMap.get(trgrEventType) + 1;
							trgrEventTypeCountMap.put(trgrEventType, trgrEventTypeCount);
						} else
							trgrEventTypeCountMap.put(trgrEventType, 1);

						String typeOfTrgr = trgrType.getTrgrType();
						if (trgrTypeCountMap.containsKey(typeOfTrgr)) {
							int trgrTypeCount = trgrTypeCountMap.get(typeOfTrgr) + 1;
							trgrTypeCountMap.put(typeOfTrgr, trgrTypeCount);
						} else
							trgrTypeCountMap.put(typeOfTrgr, 1);

					}

				}
			}
		}

		AggTrgrTypes aggTrgrTypes = new AggTrgrTypes();

		List<Map.Entry<String, Integer>> dshbTrgrList = new ArrayList<Map.Entry<String, Integer>>();
		for (Map.Entry<String, Integer> mapEle : dashbCountMap.entrySet())
			dshbTrgrList.add(mapEle);
		aggTrgrTypes.setDshbTrgrList(dshbTrgrList);

		List<Map.Entry<String, Integer>> eventTrgrList = new ArrayList<Map.Entry<String, Integer>>();
		for (Map.Entry<String, Integer> mapEle : trgrEventCountMap.entrySet())
			eventTrgrList.add(mapEle);
		aggTrgrTypes.setEventTrgrList(eventTrgrList);

		List<Map.Entry<String, Integer>> eventTypeTrgrList = new ArrayList<Map.Entry<String, Integer>>();
		for (Map.Entry<String, Integer> mapEle : trgrEventTypeCountMap.entrySet())
			eventTypeTrgrList.add(mapEle);
		aggTrgrTypes.setEventTypeTrgrList(eventTypeTrgrList);

		List<Map.Entry<String, Integer>> trgrTypeList = new ArrayList<Map.Entry<String, Integer>>();
		for (Map.Entry<String, Integer> mapEle : trgrTypeCountMap.entrySet())
			trgrTypeList.add(mapEle);
		aggTrgrTypes.setTrgrTypeList(trgrTypeList);

		return aggTrgrTypes;
	}

	public boolean valueExists(Object obj, String key) {

		boolean valExists = false;

		if (obj != null) {

			if (obj instanceof DBObject) {

				DBObject dbObj = (DBObject) obj;

				if (dbObj.containsKey(key) && dbObj.get(key) != null
						&& !dbObj.get(key).toString().equalsIgnoreCase("null")
						&& !dbObj.get(key).toString().equalsIgnoreCase("NA")) {

					valExists = true;

				}

			} else if (obj instanceof JSONObject) {

				JSONObject jsonObj = (JSONObject) obj;

				if (jsonObj.has(key) && jsonObj.get(key) != null
						&& !jsonObj.get(key).toString().equalsIgnoreCase("null")
						&& !jsonObj.get(key).toString().equalsIgnoreCase("NA")) {

					valExists = true;

				}

			}

		}

		return valExists;

	}

	@Override
	public int savemodel(WorkPackage pWorkPackage, InfareData infare) {

		int lRow = 1;
		try {

			DB lDb = null;
			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			DBCollection lCollection = null;
			if (lDb != null)
				lCollection = lDb.getCollection("temp_Afsheen_");

			BasicDBObject lDocumentDetail = new BasicDBObject();

			Map<String, WorkPackage> map = new HashMap<String, WorkPackage>();
			String work = "work";
			List<DBObject> documents = new ArrayList<DBObject>();
			Map<String, List<WorkPackage>> map1 = pWorkPackage.getWorkpackagemapMap();
			List<WorkPackage> infareList = null;
			for (Map.Entry<String, List<WorkPackage>> mapElem : map1.entrySet()) {
				for (WorkPackage wp : mapElem.getValue()) {

					lDocumentDetail.put("WorkPackageID", CalculationUtil.generateRandomString());
					lDocumentDetail.put("workPackagename", wp.getWorkpackagename());
					lDocumentDetail.put("createdBy", wp.getCreatedBy());
					lDocumentDetail.put("BussinessArea", wp.getBussinessArea());
					lDocumentDetail.put("ProductType", wp.getProductType());
					lDocumentDetail.put("ProductSubType", wp.getProductsubType());
					lDocumentDetail.put("reviewlevel", wp.getReviewLevel());
					lDocumentDetail.put("createdon", wp.getCreatedOn());
					lDocumentDetail.put("targetFilingdate", wp.getTargetFilingDate());
					lDocumentDetail.put("QAStatus", wp.getQAStatus());
					lDocumentDetail.put("filingDate", wp.getFilingDate());
					lDocumentDetail.put("filingstatus", wp.getFilingStatus());
					lDocumentDetail.put("workunitname", wp.getWorkUnitName());
					lDocumentDetail.put("workunitID", wp.getWorkunitId());
					lDocumentDetail.put("GFS", wp.getGfs());
					lDocumentDetail.put("origin", wp.getOrigin());
					lDocumentDetail.put("destination", wp.getDestination());
					lDocumentDetail.put("tariff", wp.getTariff());
					lDocumentDetail.put("route", wp.getRoute());
					lDocumentDetail.put("ruleID", wp.getRuleID());
					lDocumentDetail.put("footnote", wp.getFootnote());
					lDocumentDetail.put("farebasisCode", wp.getfBC());
					lDocumentDetail.put("RBD", wp.getRBD());
					lDocumentDetail.put("ow_rt", wp.getoW_RT());
					lDocumentDetail.put("baseFare", wp.getBasefare());
					lDocumentDetail.put("yq", wp.getYQ());
					lDocumentDetail.put("yr", wp.getYR());
					lDocumentDetail.put("surcharge", wp.getSurcharge());
					lDocumentDetail.put("taxes", wp.getTaxes());
					lDocumentDetail.put("totalfare", wp.getTotalfare());
					lDocumentDetail.put("salesfrom", wp.getSalesfrom());
					lDocumentDetail.put("salesto", wp.getSalesTo());
					lDocumentDetail.put("travelfrom", wp.getTravelfrom());
					lDocumentDetail.put("travelto", wp.getTravelto());
					lDocumentDetail.put("flightno", wp.getFlightno());
					lDocumentDetail.put("dow", wp.getDow());
					// DBObject[]map2=new HashMap<String, DBObject>();
					// map2.put("workpackage", lDocumentDetail);

					documents.add(lDocumentDetail);
					System.out.println(documents);
				}
			}
			lCollection.insert(documents);
			/*
			 * for (WorkPackage linfare : infareList) {
			 * 
			 * }
			 */

		} catch (MongoException e) {

			lRow = 0;
		}

		return lRow;
	}

	@Override
	public int savedublicate(List<Diffuser> pDiffuserList) {

		int lRow = 1;

		DB lDb = null;
		BasicDBObject lNewDocument = null;
		BasicDBObject searchQuery = null;
		BasicDBObject updateField = null;
		DBObject dbObject = null;

		/*
		 * updating status,accepted_Recommended_Fare and accepted_Base_Fare in
		 * JUP_DB_Workflow_1 collection
		 */

		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			DBCollection lWorkflowCollection = null;
			if (lDb != null)
				lWorkflowCollection = lDb.getCollection("JUP_DB_Workflow_1_dummy");

			List<String> fareBasisList = new ArrayList<String>();

			for (Diffuser pDiffuser : pDiffuserList) {

				float acceptedRecommendedFare = 0;
				float surcharge = 0;
				float taxes = 0;
				float yq_q = 0;
				float acceptedBaseFare = 0;
				float yr = 0;

				if (pDiffuser.getRecommendedFare() != null)
					acceptedRecommendedFare = Float.parseFloat(pDiffuser.getRecommendedFare());

				if (pDiffuser.getSurcharges() != null)
					surcharge = Float.parseFloat(pDiffuser.getSurcharges());

				if (pDiffuser.getTaxes() != null)
					taxes = Float.parseFloat(pDiffuser.getTaxes());

				if (pDiffuser.getYQ_Q() != null)
					yq_q = Float.parseFloat(pDiffuser.getYQ_Q());

				if (pDiffuser.getYR() != null)
					yr = Float.parseFloat(pDiffuser.getYR());

				acceptedBaseFare = acceptedRecommendedFare - (surcharge + taxes + yq_q + yr);

				searchQuery = new BasicDBObject();
				updateField = new BasicDBObject();

				searchQuery.append("pos", pDiffuser.getPos());
				searchQuery.append("origin", pDiffuser.getOrigin());
				searchQuery.append("destination", pDiffuser.getDestination());
				searchQuery.put("accepted_travel_date_from", new BasicDBObject("$gte", pDiffuser.getDepDateFrom()));// pos
																													// comp
																													// travel
																													// from
																													// travel
																													// to
				searchQuery.put("accepted_travel_date_to", new BasicDBObject("$lte", pDiffuser.getDepDateTo()));
				searchQuery.append("farebasis", pDiffuser.getFareBasisCode());
				searchQuery.append("origin", pDiffuser.getOrigin());
				searchQuery.append("destination", pDiffuser.getDestination());

				fareBasisList.add(pDiffuser.getFareBasisCode());

				lNewDocument = new BasicDBObject();

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.now();

				updateField.append("action_time", sdf.format(cal.getTime()).substring(0, 5));
				updateField.append("action_date", dtf.format(localDate));
				updateField.append("status", pDiffuser.getStatus());
				updateField.append("accepted_Base_Fare", acceptedBaseFare);
				updateField.append("accepted_Recommended_Fare", acceptedRecommendedFare);
				updateField.append("accepted_sale_date_from", pDiffuser.getSaleDateFrom());
				updateField.append("accepted_sale_date_to", pDiffuser.getSaleDateTo());
				updateField.append("accepted_travel_date_from", pDiffuser.getTravelDateFrom());
				updateField.append("accepted_travel_date_to", pDiffuser.getTravelDateTo());
				updateField.append("accepted_flight_number", pDiffuser.getFlightNumber());
				updateField.append("accepted_dow", pDiffuser.getDow());

				lNewDocument.append("$set", updateField);

				lWorkflowCollection.update(searchQuery, lNewDocument, false, true);

			}

			for (Diffuser pDiffuser : pDiffuserList) {
				DBObject searchQueryAutoAcpt = new BasicDBObject();
				searchQueryAutoAcpt.put("farebasis", new BasicDBObject("$nin", fareBasisList));
				searchQueryAutoAcpt.put("origin", pDiffuser.getOrigin());
				searchQueryAutoAcpt.put("destination", pDiffuser.getDestination());
				searchQueryAutoAcpt.put("pos", pDiffuser.getPos());// pos comp
				searchQueryAutoAcpt.put("compartment", pDiffuser.getCompartment());
				searchQueryAutoAcpt.put("triggering_data.dep_date_end",
						new BasicDBObject("$gt", pDiffuser.getDepDateFrom()));
				searchQueryAutoAcpt.put("triggering_data.dep_date_start",
						new BasicDBObject("$lt", pDiffuser.getDepDateTo()));

				DBObject lNewDocumentTwo = new BasicDBObject();
				DBObject updateFieldTwo = new BasicDBObject();

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.now();

				updateFieldTwo.put("action_time", sdf.format(cal.getTime()).substring(0, 5));
				updateFieldTwo.put("action_date", dtf.format(localDate));
				updateFieldTwo.put("status", "Auto" + pDiffuser.getStatus());
				lNewDocumentTwo.put("$set", updateFieldTwo);

				lWorkflowCollection.update(searchQueryAutoAcpt, lNewDocumentTwo, false, true);

			}

		} catch (MongoException e) {
			lRow = 0; // if something goes wrong this variable will be set to 0
			logger.error("saveAction-Exception", e);
		}

		/*
		 * fetching the recently updated row by trigger Id, this row will next
		 * be inserted into JUP_DB_ATPCO_Diffuser collection
		 */
		try {

			DBCollection lWorkflowCollection = lDb.getCollection("JUP_DB_Workflow_1_dummy");

			for (Diffuser pDiffuser : pDiffuserList) {

				searchQuery = new BasicDBObject();
				searchQuery.append("trigger_id", pDiffuser.getTriggerId());
				searchQuery.append("origin", pDiffuser.getOrigin());
				searchQuery.append("destination", pDiffuser.getDestination());
				searchQuery.append("farebasis", pDiffuser.getFareBasisCode());

				dbObject = lWorkflowCollection.findOne(searchQuery);

				/*
				 * inserting dbObject into JUP_DB_ATPCO_Diffuser collection
				 */

				DBCollection lATPCODiffuserCollection = lDb.getCollection("JUP_DB_ATPCO_Diffuser");

				BasicDBObject searchQueryDiffuser = new BasicDBObject();
				searchQueryDiffuser.append("trigger_id", pDiffuser.getTriggerId());
				// searchQueryDiffuser.append("origin", pDiffuser.getOrigin());
				// searchQueryDiffuser.append("destination",
				// pDiffuser.getDestination());
				searchQueryDiffuser.append("farebasis", pDiffuser.getFareBasisCode());

				DBCursor cursor = null;
				cursor = lATPCODiffuserCollection.find(searchQueryDiffuser);

				/*
				 * checking if the trigger id already exists in
				 * JUP_DB_ATPCO_Diffuser collection
				 */

				if (cursor != null) {
					/*
					 * if a record for that trigger Id already exists, it will
					 * be deleted
					 */
					while (cursor.hasNext()) {
						lATPCODiffuserCollection.remove(cursor.next());

					}
				}

				lNewDocument = new BasicDBObject();

				DBObject dbObjHostpricingdData = null;
				if (dbObject != null && dbObject.containsKey("host_pricing_data"))
					dbObjHostpricingdData = (BasicDBObject) dbObject.get("host_pricing_data");

				if (dbObject.containsKey("trigger_id") && dbObject.get("trigger_id") != null)
					lNewDocument.put("trigger_id", dbObject.get("trigger_id").toString());
				else
					lNewDocument.put("trigger_id", null);

				if (dbObject.containsKey("origin") && dbObject.get("origin") != null)
					lNewDocument.put("origin", dbObject.get("origin").toString());
				else
					lNewDocument.put("origin", null);

				if (dbObject.containsKey("destination") && dbObject.get("destination") != null)
					lNewDocument.put("destination", dbObject.get("destination").toString());
				else
					lNewDocument.put("destination", null);

				if (dbObject.containsKey("compartment") && dbObject.get("compartment") != null)
					lNewDocument.put("compartment", dbObject.get("compartment").toString());
				else
					lNewDocument.put("compartment", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("tariff_code")
						&& dbObjHostpricingdData.get("tariff_code") != null)
					lNewDocument.put("tariff_code", dbObjHostpricingdData.get("tariff_code").toString());
				else
					lNewDocument.put("tariff_code", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("rtg")
						&& dbObjHostpricingdData.get("rtg") != null)
					lNewDocument.put("routing_number", dbObjHostpricingdData.get("rtg").toString());
				else
					lNewDocument.put("routing_number", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("oneway_return")
						&& dbObjHostpricingdData.get("oneway_return") != null)
					lNewDocument.put("oneway_return", dbObjHostpricingdData.get("oneway_return").toString());
				else
					lNewDocument.put("oneway_return", null);

				if (dbObject.containsKey("farebasis") && dbObject.get("farebasis") != null)
					lNewDocument.put("farebasis", dbObject.get("farebasis").toString());
				else
					lNewDocument.put("farebasis", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("footnote")
						&& dbObjHostpricingdData.get("footnote") != null)
					lNewDocument.put("footnote", dbObjHostpricingdData.get("footnote").toString());
				else
					lNewDocument.put("footnote", null);

				if (dbObject.containsKey("currency") && dbObject.get("currency") != null)
					lNewDocument.put("currency", dbObject.get("currency").toString());
				else
					lNewDocument.put("currency", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("RBD")
						&& dbObjHostpricingdData.get("RBD") != null)
					lNewDocument.put("RBD", dbObjHostpricingdData.get("RBD").toString());
				else
					lNewDocument.put("RBD", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("RBD_type")
						&& dbObjHostpricingdData.get("RBD_type") != null)
					lNewDocument.put("RBD_type", dbObjHostpricingdData.get("RBD_type").toString());
				else
					lNewDocument.put("RBD_type", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("Parent_RBD")
						&& dbObjHostpricingdData.get("Parent_RBD") != null)
					lNewDocument.put("Parent_RBD", dbObjHostpricingdData.get("Parent_RBD").toString());
				else
					lNewDocument.put("Parent_RBD", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("fare")
						&& dbObjHostpricingdData.get("fare") != null)
					lNewDocument.put("current_fare", dbObjHostpricingdData.get("fare").toString());
				else
					lNewDocument.put("current_fare", null);

				if (dbObject.containsKey("accepted_Recommended_Fare")
						&& dbObject.get("accepted_Recommended_Fare") != null)
					lNewDocument.put("Fare", dbObject.get("accepted_Recommended_Fare").toString());
				else
					lNewDocument.put("Fare", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("derived_fare")
						&& dbObjHostpricingdData.get("derived_fare") != null)
					lNewDocument.put("derived_fare", dbObjHostpricingdData.get("derived_fare").toString());
				else
					lNewDocument.put("derived_fare", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("Surcharges")
						&& dbObjHostpricingdData.get("Surcharges") != null) {
					lNewDocument.put("current_surcharge", dbObjHostpricingdData.get("Surcharges").toString());
					lNewDocument.put("accepted_surcharge", dbObjHostpricingdData.get("Surcharges").toString());
				} else {

					lNewDocument.put("current_surcharge", null);
					lNewDocument.put("accepted_surcharge", null);
				}

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("YQ")
						&& dbObjHostpricingdData.get("YQ") != null) {
					lNewDocument.put("current_YQ", dbObjHostpricingdData.get("YQ").toString());
					lNewDocument.put("accepted_YQ", dbObjHostpricingdData.get("YQ").toString());
					lNewDocument.put("YQ", dbObjHostpricingdData.get("YQ").toString());
				} else {

					lNewDocument.put("current_YQ", null);
					lNewDocument.put("accepted_YQ", null);
					lNewDocument.put("YQ", null);
				}

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("taxes")
						&& dbObjHostpricingdData.get("taxes") != null) {
					lNewDocument.put("current_taxes", dbObjHostpricingdData.get("taxes").toString());
					lNewDocument.put("accepted_taxes", dbObjHostpricingdData.get("taxes").toString());
					lNewDocument.put("taxes", dbObjHostpricingdData.get("taxes").toString());
				} else {

					lNewDocument.put("current_taxes", null);
					lNewDocument.put("accepted_taxes", null);
					lNewDocument.put("taxes", null);
				}

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("Rule_id")
						&& dbObjHostpricingdData.get("Rule_id") != null)
					lNewDocument.put("Rule_id", dbObjHostpricingdData.get("Rule_id").toString());
				else
					lNewDocument.put("Rule_id", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("exchange_rate")
						&& dbObjHostpricingdData.get("exchange_rate") != null)
					lNewDocument.put("exchange_rate", dbObjHostpricingdData.get("exchange_rate").toString());
				else
					lNewDocument.put("exchange_rate", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("channel")
						&& dbObjHostpricingdData.get("channel") != null)
					lNewDocument.put("channel", dbObjHostpricingdData.get("channel").toString());
				else
					lNewDocument.put("channel", null);

				if (dbObjHostpricingdData != null && dbObjHostpricingdData.containsKey("derived_filed_fare")
						&& dbObjHostpricingdData.get("derived_filed_fare") != null)
					lNewDocument.put("derived_filed_fare", dbObjHostpricingdData.get("derived_filed_fare").toString());
				else
					lNewDocument.put("derived_filed_fare", null);

				if (dbObject.containsKey("accepted_travel_date_from")
						&& dbObject.get("accepted_travel_date_from") != null)
					lNewDocument.put("dep_date_from", dbObject.get("accepted_travel_date_from").toString());
				else
					lNewDocument.put("dep_date_from", null);

				if (dbObject.containsKey("accepted_travel_date_to") && dbObject.get("accepted_travel_date_to") != null)
					lNewDocument.put("dep_date_to", dbObject.get("accepted_travel_date_to").toString());
				else
					lNewDocument.put("dep_date_to", null);

				if (dbObject.containsKey("accepted_sale_date_from") && dbObject.get("accepted_sale_date_from") != null)
					lNewDocument.put("sale_date_from", dbObject.get("accepted_sale_date_from").toString());
				else
					lNewDocument.put("sale_date_from", null);

				if (dbObject.containsKey("accepted_sale_date_to") && dbObject.get("accepted_sale_date_to") != null)
					lNewDocument.put("sale_date_to", dbObject.get("accepted_sale_date_to").toString());
				else
					lNewDocument.put("sale_date_to", null);

				if (dbObject.containsKey("accepted_dow") && dbObject.get("accepted_dow") != null)
					lNewDocument.put("accepted_dow", dbObject.get("accepted_dow").toString());
				else
					lNewDocument.put("accepted_dow", null);

				if (dbObject.containsKey("accepted_flight_number") && dbObject.get("accepted_flight_number") != null)
					lNewDocument.put("flight_number", dbObject.get("accepted_flight_number").toString());
				else
					lNewDocument.put("flight_number", null);

				if (dbObject.containsKey("effective_from") && dbObject.get("effective_from") != null)
					lNewDocument.put("effective_from", dbObject.get("effective_from").toString());
				else
					lNewDocument.put("effective_from", null);

				if (dbObject.containsKey("effective_to") && dbObject.get("effective_to") != null)
					lNewDocument.put("effective_to", dbObject.get("effective_to").toString());
				else
					lNewDocument.put("effective_to", null);

				if (dbObject.containsKey("accepted_Base_Fare") && dbObject.get("accepted_Base_Fare") != null)
					lNewDocument.put("accepted_base_fare", dbObject.get("accepted_Base_Fare").toString());
				else
					lNewDocument.put("accepted_base_fare", null);

				if (dbObject.containsKey("accepted_Recommended_Fare")
						&& dbObject.get("accepted_Recommended_Fare") != null)
					lNewDocument.put("accepted_fare", dbObject.get("accepted_Recommended_Fare").toString());
				else
					lNewDocument.put("accepted_fare", null);

				lATPCODiffuserCollection.insert(lNewDocument);

			}

		} catch (

		Exception e) {

			lRow = 0;
			logger.error("saveAction-Exception", e);
		}

		return lRow;
	}
}
