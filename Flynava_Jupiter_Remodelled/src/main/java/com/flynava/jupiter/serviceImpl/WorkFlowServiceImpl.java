package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.WorkFlowDao;
import com.flynava.jupiter.model.AggTrgrTypes;
import com.flynava.jupiter.model.Diffuser;
import com.flynava.jupiter.model.FlightAnalysisModelWorkflow;
import com.flynava.jupiter.model.FlightAnalysisWorkflow;
import com.flynava.jupiter.model.ForcastAvailability;
import com.flynava.jupiter.model.ODTrigger;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.TriggerFares;
import com.flynava.jupiter.model.Upsert;
import com.flynava.jupiter.model.WorkPackage;
import com.flynava.jupiter.serviceInterface.WorkFlowService;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Response;
import com.flynava.jupiter.util.Utility;
import com.mongodb.DBObject;

@Service
public class WorkFlowServiceImpl implements WorkFlowService {

	@Autowired
	WorkFlowDao mWorkFlowDao;

	@Override
	public Map<String, Object> getForcastAvailability(RequestModel pRequestModel) {

		ArrayList<DBObject> forecastDBList = mWorkFlowDao.getForcastAvailability(pRequestModel);

		JSONArray dataArray = new JSONArray(forecastDBList);

		Map<String, Object> forecastResponseMap = new HashMap<String, Object>();
		List<ForcastAvailability> odLevelList = new ArrayList<ForcastAvailability>();
		List<ForcastAvailability> legLevelList = new ArrayList<ForcastAvailability>();
		List<ForcastAvailability> flightLevelList = new ArrayList<ForcastAvailability>();
		List<ForcastAvailability> depDateLevelList = new ArrayList<ForcastAvailability>();

		Map<String, ForcastAvailability> odLevelMap = calculateForecastAvailability("ODLevel", dataArray);
		for (String str : odLevelMap.keySet())
			odLevelList.add(odLevelMap.get(str));
		forecastResponseMap.put("ODLevel", odLevelList);

		Map<String, ForcastAvailability> legLevelMap = calculateForecastAvailability("LegLevel", dataArray);
		for (String str : legLevelMap.keySet())
			legLevelList.add(legLevelMap.get(str));
		forecastResponseMap.put("LegLevel", legLevelList);

		Map<String, ForcastAvailability> flightLevelMap = calculateForecastAvailability("FlightLevel", dataArray);
		for (String str : flightLevelMap.keySet())
			flightLevelList.add(flightLevelMap.get(str));
		forecastResponseMap.put("FlightLevel", flightLevelList);

		Map<String, ForcastAvailability> depDateLevelMap = calculateForecastAvailability("depDateLevel", dataArray);
		for (String str : depDateLevelMap.keySet())
			depDateLevelList.add(depDateLevelMap.get(str));
		forecastResponseMap.put("depDateLevel", depDateLevelList);

		return forecastResponseMap;
	}

	public Map<String, ForcastAvailability> calculateForecastAvailability(String levelKey, JSONArray dataArray) {

		Map<String, ForcastAvailability> forecastResponse = new HashMap<String, ForcastAvailability>();

		for (int i = 0; i < dataArray.length(); i++) {

			JSONObject jsonObj = dataArray.getJSONObject(i);

			String key = null;
			if (levelKey.equalsIgnoreCase("ODLevel"))
				key = jsonObj.get("OD").toString();
			else if (levelKey.equalsIgnoreCase("LegLevel"))
				key = jsonObj.get("Org").toString() + jsonObj.get("Des").toString();
			else if (levelKey.equalsIgnoreCase("FlightLevel"))
				key = jsonObj.get("Flgt_Numb").toString();
			else
				key = jsonObj.get("Depart_Date").toString();

			ForcastAvailability forecastModel = null;

			if (forecastResponse.containsKey(key)) {

				forecastModel = forecastResponse.get(key);

				String leg = null;
				if (jsonObj.has("Org") && jsonObj.has("Des"))
					leg = jsonObj.get("Org").toString() + jsonObj.get("Des").toString();
				forecastModel.setLeg(leg);

				Integer flightNo = 0;
				if (jsonObj.has("Flgt_Numb") && jsonObj.get("Flgt_Numb") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("Flgt_Numb").toString()))
					flightNo = Integer.parseInt(jsonObj.get("Flgt_Numb").toString());
				forecastModel.setFlightNo(flightNo);

				String travelDate = null;
				if (jsonObj.has("Depart_Date"))
					travelDate = jsonObj.get("Depart_Date").toString();
				forecastModel.setTravelDate(travelDate);

				String origin = null;
				if (jsonObj.has("Org"))
					origin = jsonObj.get("Org").toString();

				String destination = null;
				if (jsonObj.has("Des"))
					destination = jsonObj.get("Des").toString();

				Integer forcastJ = 0;
				if (jsonObj.has("J_forecast") && jsonObj.get("J_forecast") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("J_forecast").toString()))
					forcastJ = Integer.parseInt(jsonObj.get("J_forecast").toString());

				Integer forcastY = 0;
				if (jsonObj.has("Y_forecast") && jsonObj.get("Y_forecast") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("Y_forecast").toString()))
					forcastY = Integer.parseInt(jsonObj.get("Y_forecast").toString());

				Integer forecast_TL = 0;
				if (jsonObj.has("forecast_TL") && jsonObj.get("forecast_TL") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("forecast_TL").toString()))
					forecast_TL = jsonObj.getInt("forecast_TL");

				Integer lJ_booking = 0;
				if (jsonObj.get("J_booking") != null && !"null".equalsIgnoreCase(jsonObj.get("J_booking").toString()))
					lJ_booking = jsonObj.getInt("J_booking");

				Integer lY_booking = 0;
				if (jsonObj.get("Y_booking") != null && !"null".equalsIgnoreCase(jsonObj.get("Y_booking").toString()))
					lY_booking = jsonObj.getInt("Y_booking");

				Integer availabilityY = 0;
				if (jsonObj.get("Cabin_Y_Availability") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("Cabin_Y_Availability").toString()))
					availabilityY = jsonObj.getInt("Cabin_Y_Availability");

				Integer availabilityJ = 0;
				if (jsonObj.get("Cabin_J_Availability") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("Cabin_J_Availability").toString()))
					availabilityJ = jsonObj.getInt("Cabin_J_Availability");

				forecastModel.setCapacity_J_CY(lJ_booking + availabilityJ + forecastModel.getCapacity_J_CY());
				forecastModel.setCapacity_Y_CY(lY_booking + availabilityY + forecastModel.getCapacity_Y_CY());
				forecastModel.setCapacity_TL_CY(
						lJ_booking + availabilityJ + lY_booking + availabilityY + forecastModel.getCapacity_TL_CY());

				forecastModel.setBooking_J(lJ_booking + forecastModel.getBooking_J());
				forecastModel.setBooking_Y(lY_booking + forecastModel.getBooking_Y());
				forecastModel.setBooking_TL(lJ_booking + lY_booking + forecastModel.getBooking_TL());

				forecastModel.setForcast_J_CY(forcastJ + forecastModel.getForcast_J_CY());
				forecastModel.setForcast_Y_CY(forcastY + forecastModel.getForcast_Y_CY());
				forecastModel.setForcast_TL(forecast_TL + forecastModel.getForcast_TL());

				forecastModel.setAvailability(availabilityJ + availabilityY + forecastModel.getAvailability());
				forecastModel.setOrigin(origin);
				forecastModel.setDestination(destination);

				forecastResponse.put(key, forecastModel);

			} else {

				forecastModel = new ForcastAvailability();

				String leg = null;
				if (jsonObj.has("Org") && jsonObj.has("Des"))
					leg = jsonObj.get("Org").toString() + jsonObj.get("Des").toString();
				forecastModel.setLeg(leg);

				Integer flightNo = 0;
				if (jsonObj.has("Flgt_Numb") && jsonObj.get("Flgt_Numb") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("Flgt_Numb").toString()))
					flightNo = Integer.parseInt(jsonObj.get("Flgt_Numb").toString());
				forecastModel.setFlightNo(flightNo);

				String travelDate = null;
				if (jsonObj.has("Depart_Date"))
					travelDate = jsonObj.get("Depart_Date").toString();
				forecastModel.setTravelDate(travelDate);

				String origin = null;
				if (jsonObj.has("Org"))
					origin = jsonObj.get("Org").toString();

				String destination = null;
				if (jsonObj.has("Des"))
					destination = jsonObj.get("Des").toString();

				Integer forcastJ = 0;
				if (jsonObj.has("J_forecast") && jsonObj.get("J_forecast") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("J_forecast").toString()))
					forcastJ = Integer.parseInt(jsonObj.get("J_forecast").toString());

				Integer forcastY = 0;
				if (jsonObj.has("Y_forecast") && jsonObj.get("Y_forecast") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("Y_forecast").toString()))
					forcastY = Integer.parseInt(jsonObj.get("Y_forecast").toString());

				Integer forecast_TL = 0;
				if (jsonObj.has("forecast_TL") && jsonObj.get("forecast_TL") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("forecast_TL").toString()))
					forecast_TL = jsonObj.getInt("forecast_TL");

				Integer lJ_booking = 0;
				if (jsonObj.get("J_booking") != null && !"null".equalsIgnoreCase(jsonObj.get("J_booking").toString()))
					lJ_booking = jsonObj.getInt("J_booking");

				Integer lY_booking = 0;
				if (jsonObj.get("Y_booking") != null && !"null".equalsIgnoreCase(jsonObj.get("Y_booking").toString()))
					lY_booking = jsonObj.getInt("Y_booking");

				Integer availabilityY = 0;
				if (jsonObj.get("Cabin_Y_Availability") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("Cabin_Y_Availability").toString()))
					availabilityY = jsonObj.getInt("Cabin_Y_Availability");

				Integer availabilityJ = 0;
				if (jsonObj.get("Cabin_J_Availability") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("Cabin_J_Availability").toString()))
					availabilityJ = jsonObj.getInt("Cabin_J_Availability");

				forecastModel.setCapacity_J_CY(lJ_booking + availabilityJ);
				forecastModel.setCapacity_Y_CY(lY_booking + availabilityY);
				forecastModel.setCapacity_TL_CY(lJ_booking + availabilityJ + lY_booking + availabilityY);

				forecastModel.setBooking_J(lJ_booking);
				forecastModel.setBooking_Y(lY_booking);
				forecastModel.setBooking_TL(lJ_booking + lY_booking);

				forecastModel.setForcast_J_CY(forcastJ);
				forecastModel.setForcast_Y_CY(forcastY);
				forecastModel.setForcast_TL(forecast_TL);

				forecastModel.setAvailability(availabilityJ + availabilityY);
				forecastModel.setOrigin(origin);
				forecastModel.setDestination(destination);

				forecastResponse.put(key, forecastModel);

			}

		}

		return forecastResponse;
	}

	public Map<String, Object> getForcastAvailability_Trash(RequestModel pRequestModel) {
		// TODO Auto-generated method stub
		ArrayList<DBObject> functionData = mWorkFlowDao.getForcastAvailability(pRequestModel);

		JSONArray data = new JSONArray(functionData);

		Map<String, Object> WorkFlowMap = new HashMap<String, Object>();
		List<ForcastAvailability> wfList = new ArrayList<ForcastAvailability>();

		for (int i = 0; i < data.length(); i++) {

			JSONObject jsonObj = data.getJSONObject(i);

			String leg = "-";
			if (jsonObj.get("OD") != null && !"null".equalsIgnoreCase(jsonObj.get("OD").toString()))
				leg = jsonObj.getString("OD");

			Integer flightNo = 0;
			if (jsonObj.get("Flgt_Numb") != null && !"null".equalsIgnoreCase(jsonObj.get("Flgt_Numb").toString()))
				flightNo = jsonObj.getInt("Flgt_Numb");

			String travelDate = "-";
			if (jsonObj.get("Depart_Date") != null && !"null".equalsIgnoreCase(jsonObj.get("Depart_Date").toString()))
				travelDate = jsonObj.getString("Depart_Date");

			String origin = "-";
			if (jsonObj.get("Org") != null && !"null".equalsIgnoreCase(jsonObj.get("Org").toString()))
				origin = jsonObj.getString("Org");

			String destination = "-";
			if (jsonObj.get("Des") != null && !"null".equalsIgnoreCase(jsonObj.get("Des").toString()))
				destination = jsonObj.getString("Des");

			Integer forcastJ = 0;
			if (jsonObj.get("J_forecast") != null && !"null".equalsIgnoreCase(jsonObj.get("J_forecast").toString()))
				forcastJ = jsonObj.getInt("J_forecast");

			Integer forcastY = 0;
			if (jsonObj.get("Y_forecast") != null && !"null".equalsIgnoreCase(jsonObj.get("Y_forecast").toString()))
				forcastY = jsonObj.getInt("Y_forecast");

			Integer forecast_TL = 0;
			if (jsonObj.get("forecast_TL") != null && !"null".equalsIgnoreCase(jsonObj.get("forecast_TL").toString()))
				forecast_TL = jsonObj.getInt("forecast_TL");

			Integer lJ_booking = 0;
			if (jsonObj.get("J_booking") != null && !"null".equalsIgnoreCase(jsonObj.get("J_booking").toString()))
				lJ_booking = jsonObj.getInt("J_booking");

			Integer lY_booking = 0;
			if (jsonObj.get("Y_booking") != null && !"null".equalsIgnoreCase(jsonObj.get("Y_booking").toString()))
				lY_booking = jsonObj.getInt("Y_booking");

			Integer availabilityY = 0;
			if (jsonObj.get("Cabin_Y_Availability") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("Cabin_Y_Availability").toString()))
				availabilityY = jsonObj.getInt("Cabin_Y_Availability");

			Integer availabilityJ = 0;
			if (jsonObj.get("Cabin_J_Availability") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("Cabin_J_Availability").toString()))
				availabilityJ = jsonObj.getInt("Cabin_J_Availability");

			ForcastAvailability fa = new ForcastAvailability();

			fa.setFlightNo(flightNo);
			fa.setLeg(leg);
			fa.setTravelDate(travelDate);

			fa.setCapacity_J_CY(lJ_booking + availabilityJ);
			fa.setCapacity_Y_CY(lY_booking + availabilityY);
			fa.setCapacity_TL_CY(lJ_booking + availabilityJ + lY_booking + availabilityY);

			fa.setBooking_J(lJ_booking);
			fa.setBooking_Y(lY_booking);
			fa.setBooking_TL(lJ_booking + lY_booking);

			fa.setForcast_J_CY(forcastJ);
			fa.setForcast_Y_CY(forcastY);
			fa.setForcast_TL(forecast_TL);

			fa.setAvailability(availabilityJ + availabilityY);
			fa.setOrigin(origin);
			fa.setDestination(destination);

			// data is not available for last year

			wfList.add(fa);

		}

		WorkFlowMap.put("ForcastAvailabilityLegLevel", wfList);
		return WorkFlowMap;
	}

	@Override
	public List<DBObject> getWorkflowCompetitiorSummary(RequestModel pRequestModel) {

		List<DBObject> dbObjList = mWorkFlowDao.getWorkflowCompetitiorSummary(pRequestModel);

		return dbObjList;
	}

	@Override
	public Map<String, Object> addUser(RequestModel pRequestModel) {
		Boolean functionData = mWorkFlowDao.addUser(pRequestModel);
		Map<String, Object> functionMap = new HashMap<String, Object>();
		if (functionData) {
			functionMap.put("userInserted", functionData);
		} else {
			functionMap.put("userInserted", functionData);
		}
		return functionMap;
	}

	@Override
	public Response saveWorkPackage(WorkPackage pWorkPackage) {

		int row = 0;
		if (mWorkFlowDao != null)
			row = mWorkFlowDao.saveWorkPackage(pWorkPackage);

		Response response = new Response();

		if (row > 0) {
			response.setStatus(Constants.INSERT_SUCCESS_STATUS);
			response.setMessage(Constants.INSERT_SUCCESS_MESSAGE);
		} else {
			response.setStatus(Constants.INSERT_FAILED_STATUS);
			response.setMessage(Constants.INSERT_FAILED_MESSAGE);
		}

		return response;
	}

	@Override
	public ArrayList<DBObject> getWorkPackage() {

		return mWorkFlowDao.getWorkPackage();
	}

	@Override
	public Response saveAction(List<Diffuser> pDiffuserList) {

		int row = 0;
		if (mWorkFlowDao != null)
			row = mWorkFlowDao.saveAction(pDiffuserList);

		Response response = new Response();

		if (row > 0) {
			response.setStatus(Constants.INSERT_SUCCESS_STATUS);
			response.setMessage(Constants.INSERT_SUCCESS_MESSAGE);
		} else {
			response.setStatus(Constants.INSERT_FAILED_STATUS);
			response.setMessage(Constants.INSERT_FAILED_MESSAGE);
		}

		return response;
	}

	@Override
	public ArrayList<DBObject> getWorkFlowFareAnalysis(RequestModel pRequestModel) {

		return mWorkFlowDao.getWorkFlowFareAnalysis(pRequestModel);

	}

	private boolean checkIfValueExist(String pStr, JSONObject pJsonObj) {

		boolean valueExist = false;
		boolean lCondition1 = pJsonObj.has(pStr) && pJsonObj.get(pStr) != null;

		if (lCondition1 && !"null".equalsIgnoreCase(pJsonObj.get(pStr).toString())) {
			valueExist = true;
		}
		return valueExist;
	}

	@Override
	public ArrayList<DBObject> getRBDDiffuser() {

		return mWorkFlowDao.getRBDDiffuser();
	}

	@Override
	public ArrayList<DBObject> getFareBrandDiffuser() {

		return mWorkFlowDao.getFareBrandDiffuser();
	}

	@Override
	public ArrayList<DBObject> getChannelDiffuser() {

		return mWorkFlowDao.getChannelDiffuser();
	}

	@Override
	public ArrayList<DBObject> getSavedTriggerAction() {

		return mWorkFlowDao.getSavedTriggerAction();
	}

	@Override
	public Response fileFare(String workPackageId) {

		int row = 0;
		if (mWorkFlowDao != null)
			row = mWorkFlowDao.fileFare(workPackageId);

		Response response = new Response();

		if (row > 0) {
			response.setStatus(Constants.INSERT_SUCCESS_STATUS);
			response.setMessage(Constants.INSERT_SUCCESS_MESSAGE);
		} else {
			response.setStatus(Constants.INSERT_FAILED_STATUS);
			response.setMessage(Constants.INSERT_FAILED_MESSAGE);
		}

		return response;

	}

	@Override
	public Response checkRcmdFareChange(Diffuser pDiffuser) {

		Response response = new Response();

		int thresholdValLt = 150;
		int thresholdValGt = -150;
		int percentVal = 10;
		int rcmdFare = 0;
		if (pDiffuser.getRecommendedFare() != null)
			rcmdFare = Integer.parseInt(pDiffuser.getRecommendedFare());

		if (pDiffuser.isFlag()) {

			int originalFare = 0;
			if (pDiffuser.getOriginalFare() != null)
				originalFare = Integer.parseInt(pDiffuser.getOriginalFare());

			float rcmdFareMargin = (originalFare * percentVal) / 100;
			float rcmdFareMaxVal = originalFare + rcmdFareMargin;
			float rcmdFareMinVal = originalFare - rcmdFareMargin;

			if (rcmdFare >= rcmdFareMinVal && rcmdFare <= rcmdFareMaxVal) {

				response.setStatus("Success");
				response.setMessage("Recommended Fare in the range");

			} else {

				response.setStatus("Failed");
				response.setMessage("Recommended Fare not in the range");

			}
		} else {

			if (rcmdFare >= thresholdValGt && rcmdFare <= thresholdValLt) {

				response.setStatus("Success");
				response.setMessage("Recommended Fare in the range");

			} else {

				response.setStatus("Failed");
				response.setMessage("Recommended Fare not in the range");

			}

		}

		return response;
	}

	@Override
	public Response insert(Upsert upsert) {
		int row = 0;
		if (mWorkFlowDao != null)
			row = mWorkFlowDao.insert(upsert);

		Response response = new Response();

		if (row > 0) {
			response.setStatus(Constants.INSERT_SUCCESS_STATUS);
			response.setMessage(Constants.INSERT_SUCCESS_MESSAGE);
		} else {
			response.setStatus(Constants.INSERT_FAILED_STATUS);
			response.setMessage(Constants.INSERT_FAILED_MESSAGE);
		}

		return response;

	}

	@Override
	public List<Object> getTriggerRecmnd(RequestModel pRequestModel) {

		List<DBObject> triggerDBObjectList = null;
		List<DBObject> odTrgDBObjectList = null;
		if (mWorkFlowDao != null) {
			triggerDBObjectList = mWorkFlowDao.getTriggersRecommendation(pRequestModel);
			odTrgDBObjectList = mWorkFlowDao.getODTrgrData(pRequestModel);

		}
		return getGroupedTrigger(odTrgDBObjectList, getGroupedOD(triggerDBObjectList));
	}

	public Map<String, List<DBObject>> getGroupedOD(List<DBObject> triggerDBObjectList) {

		Map<String, List<DBObject>> triggersMap = new HashMap<String, List<DBObject>>();

		List<DBObject> triggersList = new ArrayList<DBObject>();

		if (triggerDBObjectList != null) {

			for (int i = 0; i < triggerDBObjectList.size(); i++) {

				DBObject dbObj = triggerDBObjectList.get(i);

				String pos = null;
				if (valueExists(dbObj, "pos"))
					pos = dbObj.get("pos").toString();

				String origin = null;
				if (valueExists(dbObj, "origin"))
					origin = dbObj.get("origin").toString();

				String destination = null;
				if (valueExists(dbObj, "destination"))
					destination = dbObj.get("destination").toString();

				String compartment = null;
				if (valueExists(dbObj, "compartment"))
					compartment = dbObj.get("compartment").toString();

				String key = pos + origin + destination + compartment;

				triggersList.add(dbObj);

				triggersMap.put(key, triggersList);

			}

		}

		return triggersMap;

	}

	public List<Object> getGroupedTrigger(List<DBObject> odTrgDBObjectList, Map<String, List<DBObject>> odGroupMap) {

		Map<String, ODTrigger> odTriggerMap = new HashMap<String, ODTrigger>();

		List<Object> ODTriggerList = new ArrayList<Object>();

		if (odTrgDBObjectList != null) {
			for (int i = 0; i < odTrgDBObjectList.size(); i++) {

				DBObject dbObj = odTrgDBObjectList.get(i);

				ODTrigger odTrigger = new ODTrigger();

				String pos = null;
				if (valueExists(dbObj, "pos"))
					pos = dbObj.get("pos").toString();
				odTrigger.setPos(pos);

				String origin = null;
				if (valueExists(dbObj, "origin"))
					origin = dbObj.get("origin").toString();
				odTrigger.setOrigin(origin);

				String destination = null;
				if (valueExists(dbObj, "destination"))
					destination = dbObj.get("destination").toString();
				odTrigger.setDestination(destination);

				String compartment = null;
				if (valueExists(dbObj, "compartment"))
					compartment = dbObj.get("compartment").toString();
				odTrigger.setCompartment(compartment);

				String key = pos + origin + destination + compartment;

				JSONObject mrktObj = null;
				if (dbObj.containsKey("mrkt_data"))
					mrktObj = new JSONObject(dbObj.get("mrkt_data").toString());

				JSONObject hostObj = null;
				if (mrktObj != null) {
					if (mrktObj.has("host"))
						hostObj = new JSONObject(mrktObj.get("host").toString());

					if (hostObj != null) {
						if (valueExists(hostObj, "market_share"))
							odTrigger.setHostMarketShare(Float.parseFloat(hostObj.get("market_share").toString()));

						if (valueExists(hostObj, "fms"))
							odTrigger.setHostFms(Float.parseFloat(hostObj.get("fms").toString()));
					}

					JSONObject lwstFiledFareObj = null;
					if (hostObj.has("lowest_filed_fare"))
						lwstFiledFareObj = hostObj.getJSONObject("lowest_filed_fare");

					if (valueExists(lwstFiledFareObj, "total_fare"))
						odTrigger.setHostLowestFiledFare(
								Float.parseFloat(lwstFiledFareObj.get("total_fare").toString()));

				}

				JSONObject hostRevenueObj = null;
				if (dbObj.containsKey("revenue_data"))
					hostRevenueObj = new JSONObject(dbObj.get("revenue_data").toString());

				Map<String, Float> hostRevenueMap = new HashMap<String, Float>();
				if (hostRevenueObj != null) {

					if (valueExists(hostRevenueObj, "vlyr"))
						hostRevenueMap.put("vlyr", Float.parseFloat(hostRevenueObj.get("vlyr").toString()));
					else
						hostRevenueMap.put("vlyr", null);

					if (valueExists(hostRevenueObj, "vtgt"))
						hostRevenueMap.put("vtgt", Float.parseFloat(hostRevenueObj.get("vtgt").toString()));
					else
						hostRevenueMap.put("vtgt", null);

					if (valueExists(hostRevenueObj, "revenue"))
						hostRevenueMap.put("revenue", Float.parseFloat(hostRevenueObj.get("revenue").toString()));
					else
						hostRevenueMap.put("revenue", null);

				}
				odTrigger.setHostRevenueMap(hostRevenueMap);

				JSONObject hostPaxObj = null;
				if (dbObj.containsKey("pax_data"))
					hostPaxObj = new JSONObject(dbObj.get("pax_data").toString());

				Map<String, Float> hostPaxMap = new HashMap<String, Float>();
				if (hostPaxObj != null) {

					if (valueExists(hostPaxObj, "vlyr"))
						hostPaxMap.put("vlyr", Float.parseFloat(hostPaxObj.get("vlyr").toString()));
					else
						hostPaxMap.put("vlyr", null);

					if (valueExists(hostPaxObj, "vtgt"))
						hostPaxMap.put("vtgt", Float.parseFloat(hostPaxObj.get("vtgt").toString()));
					else
						hostPaxMap.put("vtgt", null);

					if (valueExists(hostPaxObj, "pax"))
						hostPaxMap.put("pax", Float.parseFloat(hostPaxObj.get("pax").toString()));
					else
						hostPaxMap.put("pax", null);

				}

				odTrigger.setHostPaxMap(hostPaxMap);

				JSONObject avgFareObj = null;
				if (dbObj.containsKey("avg_fare_data"))
					avgFareObj = new JSONObject(dbObj.get("avg_fare_data").toString());

				Map<String, Float> avgFareMap = new HashMap<String, Float>();
				if (avgFareObj != null) {

					if (valueExists(avgFareObj, "vlyr"))
						avgFareMap.put("vlyr", Float.parseFloat(avgFareObj.get("vlyr").toString()));
					else
						avgFareMap.put("vlyr", null);

					if (valueExists(avgFareObj, "vtgt"))
						avgFareMap.put("vtgt", Float.parseFloat(avgFareObj.get("vtgt").toString()));
					else
						avgFareMap.put("vtgt", null);

					if (valueExists(avgFareObj, "avg_fare"))
						avgFareMap.put("avgFare", Float.parseFloat(avgFareObj.get("avg_fare").toString()));
					else
						avgFareMap.put("avgFare", null);

				}

				odTrigger.setHostAvgFareMap(avgFareMap);

				JSONObject yieldObj = null;
				if (dbObj.containsKey("yield_data"))
					yieldObj = new JSONObject(dbObj.get("yield_data").toString());

				Map<String, Float> hostYieldMap = new HashMap<String, Float>();
				if (yieldObj != null) {

					if (valueExists(yieldObj, "vlyr"))
						hostYieldMap.put("vlyr", Float.parseFloat(yieldObj.get("vlyr").toString()));
					else
						hostYieldMap.put("vlyr", null);

					if (valueExists(yieldObj, "vtgt"))
						hostYieldMap.put("vtgt", Float.parseFloat(yieldObj.get("vtgt").toString()));
					else
						hostYieldMap.put("vtgt", null);

					if (valueExists(yieldObj, "yield_"))
						hostYieldMap.put("yield", Float.parseFloat(yieldObj.get("yield_").toString()));
					else
						hostYieldMap.put("yield", null);

				}

				odTrigger.setHostYieldMap(hostYieldMap);

				JSONObject seatFactorObj = null;
				if (dbObj.containsKey("seat_factor_data"))
					seatFactorObj = new JSONObject(dbObj.get("seat_factor_data").toString());

				Map<String, Float> seatFactorMap = new HashMap<String, Float>();
				if (seatFactorObj != null) {

					if (valueExists(seatFactorObj, "leg1"))
						seatFactorMap.put("leg1", Float.parseFloat(seatFactorObj.get("leg1").toString()));
					else
						seatFactorMap.put("leg1", null);

					if (valueExists(seatFactorObj, "leg2"))
						seatFactorMap.put("leg2", Float.parseFloat(seatFactorObj.get("leg2").toString()));
					else
						seatFactorMap.put("leg2", null);

				}

				odTrigger.setSeatFactorMap(seatFactorMap);

				if (mrktObj != null) {

					JSONArray compArray = null;
					if (mrktObj.has("comp") && mrktObj.get("comp") != null)
						compArray = new JSONArray(mrktObj.get("comp").toString());

					if (compArray != null) {

						Map<String, Object> compMap = new LinkedHashMap<String, Object>();

						for (int c = 0; c < compArray.length(); c++) {

							JSONObject compObj = new JSONObject(compArray.get(c).toString());

							Map<String, Object> competitor = new HashMap<String, Object>();

							if (valueExists(compObj, "market_share"))
								competitor.put("marketShare", Float.parseFloat(compObj.get("market_share").toString()));
							else
								competitor.put("marketShare", null);

							if (valueExists(compObj, "market_share_vlyr"))
								competitor.put("marketShareVLYR",
										Float.parseFloat(compObj.get("market_share_vlyr").toString()));
							else
								competitor.put("marketShareVLYR", null);

							if (valueExists(compObj, "market_share_vtgt"))
								competitor.put("marketShareVTGT",
										Float.parseFloat(compObj.get("market_share_vtgt").toString()));
							else
								competitor.put("marketShareVTGT", null);

							if (valueExists(compObj, "fms"))
								competitor.put("fms", Float.parseFloat(compObj.get("fms").toString()));
							else
								competitor.put("fms", null);

							JSONObject compLowestFF = null;
							if (valueExists(compObj, "lowest_filed_fare"))
								compLowestFF = new JSONObject(compObj.get("lowest_filed_fare").toString());

							if (valueExists(compObj, "total_fare"))
								competitor.put("lowestFiledFare",
										Float.parseFloat(compLowestFF.get("total_fare").toString()));

							JSONObject freqAvlFare = null;
							if (compObj.has("most_available_fare") && compObj.get("most_available_fare") != null)
								freqAvlFare = new JSONObject(compObj.get("most_available_fare").toString());

							if (valueExists(compObj, "total_fare"))
								competitor.put("FrequentAvlFare",
										Float.parseFloat(freqAvlFare.get("total_fare").toString()));

							String airLine = null;
							if (compObj.has("airline") && compObj.get("airline") != null)
								airLine = compObj.get("airline").toString();

							competitor.put("airline", airLine);
							compMap.put(airLine, competitor);

						}

						odTrigger.setCompMap(compMap);
					}

				}

				List<TriggerFares> faresList = new ArrayList<TriggerFares>();

				JSONArray faresArray = null;
				if (dbObj != null && dbObj.containsKey("fares_docs") && dbObj.get("fares_docs") != null)
					faresArray = new JSONArray(dbObj.get("fares_docs").toString());

				if (faresArray != null) {

					for (int f = 0; f < faresArray.length(); f++) {

						JSONObject faresObj = new JSONObject(faresArray.get(f).toString());
						TriggerFares fares = new TriggerFares();

						String fareBasis = null;

						if (faresObj.has("fare_basis") && faresObj.get("fare_basis") != null)
							fareBasis = faresObj.get("fare_basis").toString();

						if (faresObj.has("Rule_id") && faresObj.get("Rule_id") != null)
							fares.setRuleId(faresObj.get("Rule_id").toString());

						fares.setFarebasis(fareBasis);

						if (valueExists(faresObj, "fare_pax"))
							fares.setBookings(Float.parseFloat(faresObj.get("fare_pax").toString()));

						if (faresObj.has("currency") && faresObj.get("currency") != null)
							fares.setCurrency(faresObj.get("currency").toString());

						if (valueExists(faresObj, "total_fare"))
							fares.setCurrentFare(Float.parseFloat(faresObj.get("total_fare").toString()));

						if (valueExists(faresObj, "recommended_fare"))
							fares.setRecmndFare(Float.parseFloat(faresObj.get("recommended_fare").toString()));

						if (faresObj.has("OW/RT") && faresObj.get("OW/RT") != null)
							fares.setOwrt(faresObj.get("OW/RT").toString());

						if (faresObj.has("footnote") && faresObj.get("footnote") != null)
							fares.setFootnote(faresObj.get("footnote").toString());

						if (faresObj.has("sale_date_from") && faresObj.get("sale_date_from") != null)
							fares.setSaleFrom(faresObj.get("sale_date_from").toString());

						if (faresObj.has("sale_date_to") && faresObj.get("sale_date_to") != null)
							fares.setSaleTo(faresObj.get("sale_date_to").toString());

						if (faresObj.has("dep_date_from") && faresObj.get("dep_date_from") != null)
							fares.setTravelFrom(faresObj.get("dep_date_from").toString());

						if (faresObj.has("dep_date_to") && faresObj.get("dep_date_to") != null)
							fares.setTravelTo(faresObj.get("dep_date_to").toString());

						if (valueExists(faresObj, "reco_yield"))
							fares.setRecmndYield(Float.parseFloat(faresObj.get("reco_yield").toString()));

						if (valueExists(faresObj, "lastFiledDate"))
							fares.setLastFiled(Float.parseFloat(faresObj.get("lastFiledDate").toString()));

						if (valueExists(faresObj, "total_fare"))
							fares.setCurrentBaseFare(Float.parseFloat(faresObj.get("total_fare").toString()));

						if (valueExists(faresObj, "currentYQ"))
							fares.setCurrentYQ(Float.parseFloat(faresObj.get("currentYQ").toString()));

						if (valueExists(faresObj, "currentSurcharges"))
							fares.setCurrentSurcharges(Float.parseFloat(faresObj.get("currentSurcharges").toString()));

						if (valueExists(faresObj, "currentTax"))
							fares.setCurrentTax(Float.parseFloat(faresObj.get("currentTax").toString()));

						if (valueExists(faresObj, "currentTotalFare"))
							fares.setCurrentTotalFare(Float.parseFloat(faresObj.get("currentTotalFare").toString()));

						if (valueExists(faresObj, "recoBaseFare"))
							fares.setRecoBaseFare(Float.parseFloat(faresObj.get("recoBaseFare").toString()));

						if (valueExists(faresObj, "recoTax"))
							fares.setRecoTax(Float.parseFloat(faresObj.get("recoTax").toString()));

						if (valueExists(faresObj, "recoSurcharges"))
							fares.setRecoSurcharges(Float.parseFloat(faresObj.get("recoSurcharges").toString()));

						if (valueExists(faresObj, "recoTotalFare"))
							fares.setRecoTotalFare(Float.parseFloat(faresObj.get("recoTotalFare").toString()));

						if (valueExists(faresObj, "yield_VLYR"))
							fares.setYield_VLYR(Float.parseFloat(faresObj.get("yield_VLYR").toString()));

						if (valueExists(faresObj, "yield_VTGT"))
							fares.setYield_VTGT(Float.parseFloat(faresObj.get("yield_VTGT").toString()));

						if (valueExists(faresObj, "reco_fare_yield"))
							fares.setReco_fare_yield(Float.parseFloat(faresObj.get("reco_fare_yield").toString()));

						List<Map<String, Object>> flightsList = new ArrayList<Map<String, Object>>();

						JSONArray flightsArray = null;
						if (faresObj.has("flights"))
							flightsArray = new JSONArray(faresObj.get("flights").toString());

						if (flightsArray != null) {
							for (int k = 0; k < flightsArray.length(); k++) {

								JSONObject jsonObj = flightsArray.getJSONObject(k);
								Map<String, Object> flightMap = new HashMap<String, Object>();

								if (jsonObj.has("flight_num"))
									flightMap.put("flight_num", jsonObj.get("flight_num").toString());
								if (jsonObj.has("dow"))
									flightMap.put("dow", jsonObj.get("dow").toString());

								flightsList.add(flightMap);

							}
						}

						fares.setFlightNumber(flightsList);

						faresList.add(fares);

					}

				}

				odTrigger.setFaresList(faresList);

				odTriggerMap.put(key, odTrigger);

			}

			if (odTriggerMap != null) {
				for (Map.Entry<String, ODTrigger> odEle : odTriggerMap.entrySet()) {

					ODTrigger odTrgr = odEle.getValue();

					List<Object> compList = new ArrayList<Object>();
					Map<String, Object> compMap = odTrgr.getCompMap();

					for (Map.Entry<String, Object> compEle : compMap.entrySet()) {

						compList.add(compEle.getValue());

					}

					odTrgr.setCompList(compList);
					odTrgr.setCompMap(null);

					if (odGroupMap != null && odGroupMap.containsKey(odEle.getKey())) {

						odTrgr.setTriggersList(odGroupMap.get(odEle.getKey()));

						ODTriggerList.add(odTrgr);

					}

				}

			}

		}

		return ODTriggerList;

	}

	@Override
	public List<List<DBObject>> getTriggerRecords(RequestModel pRequestModel) {

		List<DBObject> dbObjList = new ArrayList<DBObject>();
		List<List<DBObject>> odTrgrList = new ArrayList<List<DBObject>>();
		if (mWorkFlowDao != null)
			dbObjList = mWorkFlowDao.getTriggersRecommendation(pRequestModel);

		Map<String, List<DBObject>> odTriggerMap = getGroupedOD(dbObjList);

		for (Map.Entry<String, List<DBObject>> trgrEle : odTriggerMap.entrySet()) {

			odTrgrList.add(trgrEle.getValue());

		}

		return odTrgrList;

	}

	@Override
	public List<Map<String, List<FlightAnalysisWorkflow>>> getFlightAnalysis(RequestModel pRequestModel) {

		ArrayList<DBObject> functionData = mWorkFlowDao.getFlightAnalysis(pRequestModel);
		JSONArray data = new JSONArray(functionData);

		Map<String, Object> WorkFlowMap = new HashMap<String, Object>();
		List<FlightAnalysisWorkflow> wfList = new ArrayList<FlightAnalysisWorkflow>();
		List<FlightAnalysisModelWorkflow> lflightDataList = new ArrayList<FlightAnalysisModelWorkflow>();
		Map<String, List<FlightAnalysisWorkflow>> responseMap = new HashMap<String, List<FlightAnalysisWorkflow>>();

		FlightAnalysisWorkflow fl = null;
		for (int i = 0; i < data.length(); i++) {

			JSONObject jsonObj = data.getJSONObject(i);

			String lKey = null;
			if (jsonObj.has("od") && jsonObj.get("od") != null)
				lKey = jsonObj.get("od").toString();

			if (responseMap.containsKey(lKey)) {

				List<FlightAnalysisWorkflow> flights = responseMap.get(lKey);
				fl = new FlightAnalysisWorkflow();

				fl.setKey(lKey);

				String od = "-";
				if (jsonObj.has("od"))
					od = jsonObj.get("od").toString();

				fl.setOd(od);
				int flightnumber = 0;
				if (jsonObj.has("Flight_Number"))
					flightnumber = Integer.parseInt(jsonObj.get("Flight_Number").toString());
				fl.setFlightNo(flightnumber);

				if (flightnumber > 0) {
					String travelDate = "-";
					if (jsonObj.has("dep_date") && jsonObj.get("dep_date") != null
							&& !jsonObj.get("dep_date").toString().equalsIgnoreCase("NA"))
						travelDate = jsonObj.get("dep_date").toString();
					fl.setTravelDate(travelDate);

					float jcap = 0;
					float ycap = 0;
					float Tlcap = 0;
					if (jsonObj.has("j_cap") && jsonObj.get("j_cap") != null
							&& !jsonObj.get("j_cap").toString().equalsIgnoreCase("NA")) {

						jcap = Float.parseFloat(jsonObj.get("j_cap").toString());

						if (jsonObj.has("y_cap") && jsonObj.get("y_cap") != null
								&& !jsonObj.get("y_cap").toString().equalsIgnoreCase("NA")) {

							ycap = Float.parseFloat(jsonObj.get("y_cap").toString());
						}
						Tlcap = ycap + jcap;
						fl.setCapacity_TL_CY(Tlcap);

					}

					float jbooking = 0;
					float ybooking = 0;
					if (jsonObj.has("j_booking") && jsonObj.get("j_booking") != null
							&& !jsonObj.get("j_booking").toString().equalsIgnoreCase("NA")) {

						jbooking = Float.parseFloat(jsonObj.get("j_booking").toString());

						if (jsonObj.has("y_booking") && jsonObj.get("y_booking") != null
								&& !jsonObj.get("y_booking").toString().equalsIgnoreCase("NA")) {

							ybooking = Float.parseFloat(jsonObj.get("y_booking").toString());
						}
						float Tlbooking = ybooking + jbooking;
						fl.setBooking_TL(Tlbooking);
					}

					float javailability = 0;
					float yavailability = 0;
					if (jsonObj.has("j_availability") && jsonObj.get("j_availability") != null
							&& !jsonObj.get("j_availability").toString().equalsIgnoreCase("NA")) {

						javailability = Float.parseFloat(jsonObj.get("j_availability").toString());

						if (jsonObj.has("y_availability") && jsonObj.get("y_availability") != null
								&& !jsonObj.get("y_availability").toString().equalsIgnoreCase("NA")) {

							yavailability = Float.parseFloat(jsonObj.get("y_availability").toString());
						}
						float TLavailability = yavailability + javailability;
						fl.setAvailability_TL(TLavailability);
					}

					JSONArray salesPax = null;
					String salesPax_TL = "-";
					if (jsonObj.get("sales_pax") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("sales_pax").toString())
							&& !"[]".equalsIgnoreCase(jsonObj.get("sales_pax").toString())) {
						salesPax = new JSONArray(jsonObj.get("sales_pax").toString());
						salesPax_TL = Double.toString(Utility.findSum(salesPax));
						fl.setSalespax(Float.parseFloat(salesPax_TL));
					}

					JSONArray salesRevenue = null;
					String salesrevenue_TL = "-";
					if (jsonObj.get("sales_revenue") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("sales_revenue").toString())
							&& !"[]".equalsIgnoreCase(jsonObj.get("sales_revenue").toString())) {
						salesRevenue = new JSONArray(jsonObj.get("sales_revenue").toString());
						salesrevenue_TL = Double.toString(Utility.findSum(salesRevenue));
						fl.setSalesrevenue(Float.parseFloat(salesrevenue_TL));
					}
					JSONArray flownPax = null;
					String flownPax_TL = "-";
					if (jsonObj.get("flown_pax") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("flown_pax").toString())
							&& !jsonObj.get("flown_pax").toString().equalsIgnoreCase("[]")) {
						flownPax = new JSONArray(jsonObj.get("flown_pax").toString());
						flownPax_TL = Double.toString(Utility.findSum(flownPax));
						fl.setFlownPax(Float.parseFloat(flownPax_TL));
					}

					JSONArray flownrevenue = null;
					Double flownrevenue_TL = 0D;
					if (jsonObj.get("flown_revenue") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("flown_revenue").toString())
							&& !jsonObj.get("flown_revenue").toString().equalsIgnoreCase("[]")) {
						flownrevenue = new JSONArray(jsonObj.get("flown_revenue").toString());
						flownrevenue_TL = Utility.findSum(flownrevenue);
					}

					JSONArray legDistance = null;
					float distance = 0;
					if (jsonObj.get("leg_distance") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("leg_distance").toString())
							&& !jsonObj.get("flown_compartment").toString().equalsIgnoreCase("[]")) {
						legDistance = new JSONArray(jsonObj.get("leg_distance").toString());
						distance = Float.parseFloat(legDistance.get(0).toString());
					}

					JSONArray forecastrevenue = null;
					String forecastrevenue_TL = "-";
					if (jsonObj.get("forecast_revenue") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("forecast_revenue").toString())
							&& !jsonObj.get("forecast_revenue").toString().equalsIgnoreCase("[]")) {
						forecastrevenue = new JSONArray(jsonObj.get("forecast_revenue").toString());
						forecastrevenue_TL = Double.toString(Utility.findSum(forecastrevenue));
						fl.setForecastRevenue(Float.parseFloat(forecastrevenue_TL));
					}
					JSONArray forecastpax = null;
					String forecastpax_TL = "-";
					if (jsonObj.get("forecast_pax") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("forecast_pax").toString())
							&& !jsonObj.get("forecast_pax").toString().equalsIgnoreCase("[]")) {
						forecastpax = new JSONArray(jsonObj.get("forecast_pax").toString());
						forecastpax_TL = Double.toString(Utility.findSum(forecastpax));
						fl.setForecastpax(Float.parseFloat(forecastpax_TL));
					}

					if (fl.getSalespax() > 0) {
						float seatfactor = (fl.getSalespax() / fl.getCapacity_TL_CY());
						fl.setSeatfactor_TL(seatfactor);
					}

					float yield = CalculationUtil.calculateYield(fl.getSalesrevenue(), fl.getSalespax(),
							fl.getLegDistance());
					fl.setYield_TL(yield);

					if (fl.getSalesrevenue() > 0 && fl.getSalespax() > 0) {
						float averagefare = CalculationUtil.calculateavgfare(fl.getSalesrevenue(), fl.getSalespax());
						fl.setAveragefare(averagefare);
					}

					float forecastseatfactor = fl.getForecastpax() / Tlcap;
					fl.setForecastseatfactor_TL(forecastseatfactor);

					float flownseatfactor = fl.getFlownPax() / Tlcap;
					fl.setFlownseatfactorTL(flownseatfactor);

					float flownseatfactorvlyr = CalculationUtil.calculateVLYR(forecastseatfactor, flownseatfactor);
					fl.setFlownseatfactorvlyr_TL(flownseatfactorvlyr);

				}

				// fl.setFlightsDetailsMap(FlightDetailsMap);
				flights.add(fl);

				responseMap.put(lKey, flights);

			} else {

				fl = new FlightAnalysisWorkflow();

				if (jsonObj.has("od") && jsonObj.get("od") != null)
					lKey = jsonObj.get("od").toString();

				fl.setKey(lKey);

				String od = "-";

				if (jsonObj.has("od"))
					od = jsonObj.get("od").toString();

				fl.setOd(od);
				int flightnumber = 0;
				if (jsonObj.has("Flight_Number"))
					flightnumber = Integer.parseInt(jsonObj.get("Flight_Number").toString());
				fl.setFlightNo(flightnumber);

				List<FlightAnalysisWorkflow> flights = new ArrayList<FlightAnalysisWorkflow>();

				String depdate = "-";
				if (flightnumber > 0) {

					if (jsonObj.has("dep_date") && jsonObj.get("dep_date") != null
							&& !jsonObj.get("dep_date").toString().equalsIgnoreCase("NA"))
						depdate = (jsonObj.get("dep_date").toString());
					fl.setTravelDate(jsonObj.get("dep_date").toString());

					float jcap = 0;
					float ycap = 0;
					float Tlcap = 0;
					if (jsonObj.has("j_cap") && jsonObj.get("j_cap") != null
							&& !jsonObj.get("j_cap").toString().equalsIgnoreCase("NA")) {

						jcap = Float.parseFloat(jsonObj.get("j_cap").toString());

						if (jsonObj.has("y_cap") && jsonObj.get("y_cap") != null
								&& !jsonObj.get("y_cap").toString().equalsIgnoreCase("NA")) {

							ycap = Float.parseFloat(jsonObj.get("y_cap").toString());
						}
						Tlcap = ycap + jcap;
						fl.setCapacity_TL_CY(Tlcap);

					}

					float jbooking = 0;
					float ybooking = 0;
					if (jsonObj.has("j_booking") && jsonObj.get("j_booking") != null
							&& !jsonObj.get("j_booking").toString().equalsIgnoreCase("NA")) {

						jbooking = Float.parseFloat(jsonObj.get("j_booking").toString());

						if (jsonObj.has("y_booking") && jsonObj.get("y_booking") != null
								&& !jsonObj.get("y_booking").toString().equalsIgnoreCase("NA")) {

							ybooking = Float.parseFloat(jsonObj.get("y_booking").toString());
						}
						float Tlbooking = ybooking + jbooking;
						fl.setBooking_TL(Tlbooking);

					}

					float javailability = 0;
					float yavailability = 0;
					if (jsonObj.has("j_availability") && jsonObj.get("j_availability") != null
							&& !jsonObj.get("j_availability").toString().equalsIgnoreCase("NA")) {

						javailability = Float.parseFloat(jsonObj.get("j_availability").toString());

						if (jsonObj.has("y_availability") && jsonObj.get("y_availability") != null
								&& !jsonObj.get("y_availability").toString().equalsIgnoreCase("NA")) {

							yavailability = Float.parseFloat(jsonObj.get("y_availability").toString());
						}
						float TLavailability = yavailability + javailability;
						fl.setAvailability_TL(TLavailability);

					}

					JSONArray salesPax = null;
					String salesPax_TL = "-";
					if (jsonObj.get("sales_pax") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("sales_pax").toString())
							&& !"[]".equalsIgnoreCase(jsonObj.get("sales_pax").toString())) {
						salesPax = new JSONArray(jsonObj.get("sales_pax").toString());
						salesPax_TL = Double.toString(Utility.findSum(salesPax));
						fl.setSalespax(Float.parseFloat(salesPax_TL));

					}

					JSONArray salesRevenue = null;
					String salesrevenue_TL = "-";
					if (jsonObj.get("sales_revenue") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("sales_revenue").toString())
							&& !"[]".equalsIgnoreCase(jsonObj.get("sales_revenue").toString())) {
						salesRevenue = new JSONArray(jsonObj.get("sales_revenue").toString());
						salesrevenue_TL = Double.toString(Utility.findSum(salesRevenue));
						fl.setSalesrevenue(Float.parseFloat(salesrevenue_TL));
					}
					JSONArray flownPax = null;
					String flownPax_TL = "-";
					if (jsonObj.get("flown_pax") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("flown_pax").toString())
							&& !jsonObj.get("flown_pax").toString().equalsIgnoreCase("[]")) {
						flownPax = new JSONArray(jsonObj.get("flown_pax").toString());
						flownPax_TL = Double.toString(Utility.findSum(flownPax));
						fl.setFlownPax(Float.parseFloat(flownPax_TL));

					}

					JSONArray flownrevenue = null;
					String flownrevenue_TL = "-";
					if (jsonObj.get("flown_revenue") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("flown_revenue").toString())
							&& !jsonObj.get("flown_revenue").toString().equalsIgnoreCase("[]")) {
						flownrevenue = new JSONArray(jsonObj.get("flown_revenue").toString());
						flownrevenue_TL = Double.toString(Utility.findSum(flownrevenue));
						fl.setFlownrevenue(Float.parseFloat(flownrevenue_TL));

					}

					JSONArray legDistance = null;
					float distance = 0;
					if (jsonObj.get("leg_distance") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("leg_distance").toString())
							&& !jsonObj.get("flown_compartment").toString().equalsIgnoreCase("[]")) {
						legDistance = new JSONArray(jsonObj.get("leg_distance").toString());
						distance = Float.parseFloat(legDistance.get(0).toString());
						fl.setLegDistance(distance);
					}

					JSONArray forecastrevenue = null;
					String forecastrevenue_TL = "-";
					if (jsonObj.get("forecast_revenue") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("forecast_revenue").toString())
							&& !jsonObj.get("forecast_revenue").toString().equalsIgnoreCase("[]")) {
						forecastrevenue = new JSONArray(jsonObj.get("forecast_revenue").toString());
						forecastrevenue_TL = Double.toString(Utility.findSum(forecastrevenue));
						fl.setForecastRevenue(Float.parseFloat(forecastrevenue_TL));
					}
					JSONArray forecastpax = null;
					String forecastpax_TL = "-";
					if (jsonObj.get("forecast_pax") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("forecast_pax").toString())
							&& !jsonObj.get("forecast_pax").toString().equalsIgnoreCase("[]")) {
						forecastpax = new JSONArray(jsonObj.get("forecast_pax").toString());
						forecastpax_TL = Double.toString(Utility.findSum(forecastpax));
						fl.setForecastpax(Float.parseFloat(forecastpax_TL));
					}

					if (Tlcap > 0 && fl.getSalespax() > 0) {
						float seatfactor = (fl.getSalespax() / fl.getCapacity_TL_CY());
						fl.setSeatfactor_TL(seatfactor);
					}

					float yield = CalculationUtil.calculateYield(fl.getSalesrevenue(), fl.getSalespax(),
							fl.getLegDistance());
					fl.setYield_TL(yield);

					if (fl.getSalespax() > 0 && fl.getSalesrevenue() > 0) {

						float averagefare = CalculationUtil.calculateavgfare(fl.getSalesrevenue(), fl.getSalespax());
						fl.setAveragefare(averagefare);
					}

					float forecastseatfactor = fl.getForecastpax() / Tlcap;
					fl.setForecastseatfactor_TL(forecastseatfactor);

					float flownseatfactor = fl.getFlownPax() / Tlcap;
					fl.setFlownseatfactorTL(flownseatfactor);

					float flownseatfactorvlyr = CalculationUtil.calculateVLYR(forecastseatfactor, flownseatfactor);
					fl.setFlownseatfactorvlyr_TL(flownseatfactorvlyr);

				}

				flights.add(fl);
				responseMap.put(lKey, flights);

			}

		}
		List<Map<String, List<FlightAnalysisWorkflow>>> responseList = new ArrayList<Map<String, List<FlightAnalysisWorkflow>>>();
		for (Map.Entry<String, List<FlightAnalysisWorkflow>> mapElem : responseMap.entrySet()) {

			Map<String, List<FlightAnalysisWorkflow>> odMap = new HashMap<String, List<FlightAnalysisWorkflow>>();

			odMap.put(mapElem.getKey(), mapElem.getValue());
			responseList.add(odMap);

		}

		return responseList;
	}

	@Override
	public Map<String, List<DBObject>> getWorkflowInfareData(RequestModel pRequestModel) {

		List<DBObject> InfareData = mWorkFlowDao.getWorkflowInfareData(pRequestModel);
		Map<String, List<DBObject>> map = new HashMap<String, List<DBObject>>();
		map.put("Infare_Data", InfareData);
		return map;
	}

	@Override
	public Response withdrawWorkPackage(Diffuser pDiffuser) {
		int row = 0;
		if (mWorkFlowDao != null)
			row = mWorkFlowDao.withdrawWorkPackage(pDiffuser);

		Response response = new Response();

		if (row > 0) {
			response.setStatus(Constants.INSERT_SUCCESS_STATUS);
			response.setMessage(Constants.INSERT_SUCCESS_MESSAGE);
		} else {
			response.setStatus(Constants.INSERT_FAILED_STATUS);
			response.setMessage(Constants.INSERT_FAILED_MESSAGE);
		}

		return response;
	}

	@Override
	public Response getConfigDate(RequestModel pRequestModel) {
		int row = 0;
		if (mWorkFlowDao != null)
			row = mWorkFlowDao.getConfigdate(pRequestModel);

		Response response = new Response();

		if (row > 0) {
			response.setStatus(Constants.INSERT_SUCCESS_STATUS);
			response.setMessage(Constants.INSERT_SUCCESS_MESSAGE);
		} else {
			response.setStatus(Constants.INSERT_FAILED_STATUS);
			response.setMessage(Constants.INSERT_FAILED_MESSAGE);
		}

		return response;
	}

	@Override
	public Map<String, Object> getConfiguredDates(RequestModel pRequestModel) {
		ArrayList<DBObject> ConfiguredDateData = mWorkFlowDao.getConfiguredDates(pRequestModel);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("configured_Data", ConfiguredDateData);
		return map;
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
	public AggTrgrTypes getAllTriggerTypes(RequestModel pRequestModel) {

		return mWorkFlowDao.getAllTriggerTypes(pRequestModel);
	}
}
