package com.flynava.jupiter.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.KpiDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.UserProfile;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Utility;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class KpiDaoImpl implements KpiDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;
	DBCursor cursor = null;

	private static final Logger logger = Logger.getLogger(KpiDaoImpl.class);

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_KPI_dhb_Breakeven_Seat_Factor" which provides the data for
	 * Breakeven Seat Factor in Kpi Dashboard.
	 */
	@Override
	public ArrayList<DBObject> getBreakevenSeatFactor(RequestModel pRequestModel) {
		// This function is used for Break even Seat factor under KPIcockpit

		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

		ArrayList<String> od = new ArrayList<String>();

		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				od.add(pRequestModel.getOdArray()[i]);
			}
		} else if (pRequestModel.getSigod() != null) {

			od.add(pRequestModel.getSigod());
		} else {
			od = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String odJson = new flexjson.JSONSerializer().serialize(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
		}

		String query = "JUP_FN_KPI_dhb_Breakeven_Seat_Factor(" + dateJsonObj + "," + odJson + "," + lTempCollectionName
				+ ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getBreakevenSeatFactor-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();

		}
		return lDatalist;

	}
	/*
	 * This method calls the Mongo function
	 * "JUP_FN_KPI_Significant_NonSignificant_OD" which provides the data for
	 * Signification OD/Non-Significant OD in Kpi Dashboard.
	 */

	@Override
	public ArrayList<DBObject> getSignificantOd(RequestModel pRequestModel) {
		// This function for Signification OD/Non-Significant OD under
		// KPIcockpit

		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);
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
		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
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
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}
		String query = "JUP_FN_KPI_Significant_NonSignificant_OD(" + dateJsonObj + "," + regionJson + "," + countryJson
				+ "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getSignificantOd-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDatalist;

	}
	/*
	 * This method calls the Mongo function
	 * "JUP_FN_KPI_dhb_Price_Intelligence_Quotient" which provides the data for
	 * Price Availability Index in Kpi Dashboard.
	 */

	@Override
	public ArrayList<DBObject> getPriceAvailabilityIndex(RequestModel pRequestModel) {

		// This function for PriceAvailabilityIndex under
		// KPIcockpit
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();
		JSONObject dateJsonObj = new JSONObject();

		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

		ArrayList<String> rbdList = new ArrayList<String>();
		if (pRequestModel.getRbdArray() != null && pRequestModel.getRbdArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRbdArray().length; i++)
				rbdList.add(pRequestModel.getRbdArray()[i]);
		} else {
			rbdList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < pRequestModel.getPosArray().length; i++)
				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {

			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
		} else {
			od = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {

			for (int i = 0; i < pRequestModel.getRegionArray().length; i++)
				regionList.add(pRequestModel.getRegionArray()[i]);
		} else {
			regionList = null;
		}
		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {

			for (int i = 0; i < pRequestModel.getCountryArray().length; i++)
				countryList.add(pRequestModel.getCountryArray()[i]);
		} else {
			countryList = null;
		}

		ArrayList<String> compartment = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {

			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				compartment.add(pRequestModel.getCompartmentArray()[i]);
		} else {
			compartment = null;
		}
		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String rbdJson = Utility.create_json(rbdList);
		if (rbdJson.isEmpty() || "[]".equals(rbdJson)) {
			rbdJson = null;
		}
		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
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
		String compartmentJson = Utility.create_json(compartment);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String query = "JUP_FN_KPI_dhb_Price_Intelligence_Quotient(" + dateJsonObj + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + rbdJson + ","
				+ lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getPriceAvailabilityIndex-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDatalist;

	}

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_KPI_dhb_Price_Agility_Index_1" which provides the data for Price
	 * Stability Index in Kpi Dashboard.
	 */
	@Override
	public ArrayList<DBObject> getPriceStabilityIndex(RequestModel pRequestModel) {
		// This function for Price Stability under
		// KPIcockpit
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();

		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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

			for (int i = 0; i < pRequestModel.getPosArray().length; i++) {

				posList.add(pRequestModel.getPosArray()[i]);
			}
		} else {
			posList = null;
		}
		ArrayList<String> od = new ArrayList<String>();

		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				od.add(pRequestModel.getOdArray()[i]);
			}
		} else if (pRequestModel.getSigod() != null) {

			od.add(pRequestModel.getSigod());
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
		} else {
			compartmentList = null;

		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);
		String rbdJson = Utility.create_json(rbdList);
		if (rbdJson.isEmpty() || "[]".equals(rbdJson)) {
			rbdJson = null;
		}
		String farebasisJson = Utility.create_json(farebasisList);
		if (farebasisJson.isEmpty() || "[]".equals(farebasisJson)) {
			farebasisJson = null;
		}
		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}
		String odJson = new flexjson.JSONSerializer().serialize(od);
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
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}
		String query = "JUP_FN_KPI_dhb_Price_Agility_Index_1(" + dateJsonObj + "," + regionJson + "," + countryJson
				+ "," + posJson + "," + odJson + "," + compartmentJson + "," + farebasisJson + "," + rbdJson + ","
				+ lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getPriceStabilityIndex-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDatalist;

	}

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_KPI_dhb_Effective_Ineffective_fares" which provides the data for
	 * total Effective Ineffective in Kpi Dashboard.
	 */
	// TODO This can be deleted if it is not been ask to used by product team.
	@Override
	public ArrayList<DBObject> getTotalEffective_IneffectiveFares(RequestModel pRequestModel) {
		// This function for Total Effective Ineffective Fares under
		// KPIcockpit
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();

		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

		ArrayList<String> rbdList = new ArrayList<String>();
		if (pRequestModel.getRbdArray() != null && pRequestModel.getRbdArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRbdArray().length; i++)
				rbdList.add(pRequestModel.getRbdArray()[i]);
		} else {
			rbdList = null;
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < pRequestModel.getPosArray().length; i++)
				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++)
				regionList.add(pRequestModel.getRegionArray()[i]);
		} else {
			regionList = null;

		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {

			for (int i = 0; i < pRequestModel.getCountryArray().length; i++)
				countryList.add(pRequestModel.getCountryArray()[i]);
		} else {
			countryList = null;
		}
		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {

			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
		} else {
			od = null;
		}

		ArrayList<String> compartment = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)

				compartment.add(pRequestModel.getCompartmentArray()[i]);
		} else
			compartment = null;
		ArrayList<String> farebasis = new ArrayList<String>();
		if (pRequestModel.getFarebasisArray() != null && pRequestModel.getFarebasisArray().length > 0) {
			for (int i = 0; i < pRequestModel.getFarebasisArray().length; i++)

				farebasis.add(pRequestModel.getFarebasisArray()[i]);
		} else
			farebasis = null;

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);
		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);

		String rbdJson = Utility.create_json(rbdList);
		if (rbdJson.isEmpty() || "[]".equals(rbdJson)) {
			rbdJson = null;
		}
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}
		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}
		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}
		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
		}
		String compartmentJson = Utility.create_json(compartment);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String farebaisJson = Utility.create_json(farebasis);
		if (farebaisJson.isEmpty() || farebaisJson.equals("[]")) {
			farebaisJson = null;
		}

		String query = "JUP_FN_KPI_dhb_Effective_Ineffective_fares(" + dateJsonObj + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + farebaisJson + ","
				+ rbdJson + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getTotalEffective_IneffectiveFares-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDatalist;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_KPI_dhb_Revenue_Split" which
	 * provides the data for Revenue Split in Kpi Dashboard.
	 */
	@Override
	public ArrayList<DBObject> getRevenueSplit(RequestModel pRequestModel) {
		// This function for Revenue Split under
		// KPIcockpit
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();
		JSONObject dateJsonObj = new JSONObject();

		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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

		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
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
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String query = "JUP_FN_KPI_dhb_Revenue_Split(" + dateJsonObj + "," + regionJson + "," + countryJson + ","
				+ posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getRevenueSplit-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDatalist;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_KPI_dhb_New_Products" which
	 * provides the data for New Product in Kpi Dashboard.
	 */
	@Override
	public ArrayList getNewProducts(RequestModel pRequestModel) {
		// This function for New Product under
		// KPIcockpit
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();
		JSONObject dateJsonObj = new JSONObject();

		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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

		ArrayList<String> class1List = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)
				class1List.add(pRequestModel.getCompartmentArray()[i]);
		} else
			class1List = null;
		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}
		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
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
		String class1Json = Utility.create_json(class1List);
		if (class1Json.isEmpty() || class1Json.equals("[]")) {
			class1Json = null;
		}

		String query = "JUP_FN_KPI_dhb_New_Products(" + dateJsonObj + "," + regionJson + "," + countryJson + ","
				+ posJson + "," + odJson + "," + class1Json + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getNewProducts-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDatalist;
	}

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_KPI_dhb_Yield_RASM_Seat_Factor" which provides the data for Yield
	 * Rasm in Kpi Dashboard.
	 */
	@Override
	public ArrayList<DBObject> getYield_RASM_Seat(RequestModel pRequestModel, UserProfile userProfile) {
		// This function for Yield Rasm seatfactor under
		// KPIcockpit
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();
		JSONObject dateJsonObj = new JSONObject();

		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

		ArrayList<String> od = new ArrayList<String>();

		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				od.add(pRequestModel.getOdArray()[i]);
			}
		} else if (pRequestModel.getSigod() != null) {

			od.add(pRequestModel.getSigod());
		} else {
			od = null;
		}
		String odJson = new flexjson.JSONSerializer().serialize(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
		}

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String query = "JUP_FN_KPI_dhb_Yield_RASM_Seat_Factor(" + dateJsonObj + "," + odJson + "," + lTempCollectionName
				+ ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getYield_RASM_Seat-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDatalist;
	}

	/*
	 * This method calls the Mongo function "JUP_FN_KPI_dhb_PE_Signal" which
	 * provides the data for Price Elasticity in Kpi Dashboard.
	 */
	@Override
	public ArrayList<DBObject> getPriceElasticity(RequestModel pRequestModel) {
		// This function for Price Elasticity under
		// KPIcockpit
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();
		JSONObject dateJsonObj = new JSONObject();

		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < pRequestModel.getPosArray().length; i++)
				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
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
		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}
		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
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
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String query = "JUP_FN_KPI_dhb_PE_Signal(" + startDateJson + "," + endDateJson + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			// TODO: handle exception
			logger.error("getPriceElasticity-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDatalist;
	}

	// TODO This might be deleted if it is not used by UI.
	/*
	 * This method calls the Mongo function "JUP_FN_PE_Range" which provides the
	 * data for Price Elasticity Range in Kpi Dashboard.
	 */
	@Override
	public ArrayList<DBObject> getPriceElasticityRange(RequestModel pRequestModel) {
		// This function for Price Elasticity Range under
		// KPIcockpit
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();
		JSONObject dateJsonObj = new JSONObject();
		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

		ArrayList<String> posList = new ArrayList<String>();
		if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {

			for (int i = 0; i < pRequestModel.getPosArray().length; i++)

				posList.add(pRequestModel.getPosArray()[i]);
		} else {
			posList = null;
		}

		ArrayList<String> od = new ArrayList<String>();
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {

			for (int i = 0; i < pRequestModel.getOdArray().length; i++)
				od.add(pRequestModel.getOdArray()[i]);
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
		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);

		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}
		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
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
		String compartmentJson = Utility.create_json(compartmentList);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String query = "JUP_FN_PE_Range(" + startDateJson + "," + endDateJson + "," + regionJson + "," + countryJson
				+ "," + posJson + "," + odJson + "," + compartmentJson + "," + lTempCollectionName + ")";
		System.out.println("query" + query);

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
				lDatalist.add(array.get(i));
			}

		} catch (Exception e) {
			logger.error("getPriceElasticityRange-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}
		return lDatalist;

	}

	/*
	 * This method calls the Mongo function
	 * "JUP_FN_KPI_dhb_Effective_Ineffective_fares_1" which provides the data
	 * for Total Effective in Kpi Dashboard.
	 */
	@Override
	public ArrayList<DBObject> getTotalEffectiveIneffectiveFares(RequestModel pRequestModel) {
		ArrayList<DBObject> lDatalist = new ArrayList<DBObject>();

		JSONObject dateJsonObj = new JSONObject();

		String lstartDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			lstartDate = pRequestModel.getFromDate();

		else {
			lstartDate = Utility.getCurrentDate();
			pRequestModel.setFromDate(lstartDate);
		}

		String lendDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			lendDate = pRequestModel.getToDate();
		else {
			lendDate = Utility.getCurrentDate();
			pRequestModel.setToDate(lendDate);
		}

		String lastYrStartDate = pRequestModel.getLastYrFromDate();
		String lastYrEndDate = pRequestModel.getLastYrToDate();

		dateJsonObj.put("cur_start_date", lstartDate);
		dateJsonObj.put("cur_end_date", lendDate);

		if (lastYrStartDate == null)
			dateJsonObj.put("last_start_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_start_date", lastYrStartDate);

		if (lastYrEndDate == null)
			dateJsonObj.put("last_end_date", JSONObject.NULL);
		else
			dateJsonObj.put("last_end_date", lastYrEndDate);

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

		ArrayList<String> regionList = new ArrayList<String>();
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++)
				regionList.add(pRequestModel.getRegionArray()[i]);
		} else {
			regionList = null;

		}

		ArrayList<String> countryList = new ArrayList<String>();
		if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {

			for (int i = 0; i < pRequestModel.getCountryArray().length; i++)
				countryList.add(pRequestModel.getCountryArray()[i]);
		} else {
			countryList = null;
		}
		ArrayList<String> od = new ArrayList<String>();

		if (pRequestModel.getOdArray() != null) {
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				od.add(pRequestModel.getOdArray()[i]);
			}
		} else if (pRequestModel.getSigod() != null) {

			od.add(pRequestModel.getSigod());
		} else {
			od = null;
		}

		ArrayList<String> compartment = new ArrayList<String>();
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++)

				compartment.add(pRequestModel.getCompartmentArray()[i]);
		} else
			compartment = null;
		ArrayList<String> farebasis = new ArrayList<String>();
		if (pRequestModel.getFarebasisArray() != null && pRequestModel.getFarebasisArray().length > 0) {
			for (int i = 0; i < pRequestModel.getFarebasisArray().length; i++)

				farebasis.add(pRequestModel.getFarebasisArray()[i]);
		} else
			farebasis = null;

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();
		String lCollectionName = "JUP_DB_" + lTime + "_" + lRandomString;
		String lTempCollectionName = new flexjson.JSONSerializer().serialize(lCollectionName);
		String startDateJson = new flexjson.JSONSerializer().serialize(lstartDate);

		String rbdJson = Utility.create_json(rbdList);
		if (rbdJson.isEmpty() || "[]".equals(rbdJson)) {
			rbdJson = null;
		}
		String farebasisJson = Utility.create_json(farebasisList);
		if (farebasisJson.isEmpty() || "[]".equals(farebasisJson)) {
			farebasisJson = null;
		}
		if (startDateJson.isEmpty() || startDateJson.equals("[]")) {
			startDateJson = null;
		}
		String endDateJson = new flexjson.JSONSerializer().serialize(lendDate);
		if (endDateJson.isEmpty() || endDateJson.equals("[]")) {
			endDateJson = null;
		}
		String posJson = Utility.create_json(posList);
		if (posJson.isEmpty() || "[]".equals(posJson)) {
			posJson = null;
		}
		String regionJson = Utility.create_json(regionList);
		if (regionJson.isEmpty() || regionJson.equals("[]")) {
			regionJson = null;
		}
		String countryJson = Utility.create_json(countryList);
		if (countryJson.isEmpty() || "[]".equals(countryJson)) {
			countryJson = null;
		}
		String odJson = Utility.create_json(od);
		if (odJson.isEmpty() || "[]".equals(odJson)) {
			odJson = null;
		}
		String compartmentJson = Utility.create_json(compartment);
		if (compartmentJson.isEmpty() || "[]".equals(compartmentJson)) {
			compartmentJson = null;
		}

		String farebaisJson = Utility.create_json(farebasis);
		if (farebaisJson.isEmpty() || farebaisJson.equals("[]")) {
			farebaisJson = null;
		}

		String query = "JUP_FN_KPI_dhb_Effective_Ineffective_fares_1(" + dateJsonObj + "," + regionJson + ","
				+ countryJson + "," + posJson + "," + odJson + "," + compartmentJson + "," + farebaisJson + ","
				+ rbdJson + "," + lTempCollectionName + ")";

		Object resultObj = null;
		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
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
			logger.error("getTotalEffectiveIneffectiveFares-Exception", e);
		} finally {
			if (db.collectionExists(lCollectionName))
				lCollection.drop();
		}

		return lDatalist;
	}
}
