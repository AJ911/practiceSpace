
package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.PriceBiometricDao;
import com.flynava.jupiter.model.CustomerSegmentModel;
import com.flynava.jupiter.model.FareBrandModel;
import com.flynava.jupiter.model.FilterModel;
import com.flynava.jupiter.model.MajorAgenciesModel;
import com.flynava.jupiter.model.MajorAgenciesResponse;
import com.flynava.jupiter.model.MajorAgenciesTotalsResponse;
import com.flynava.jupiter.model.ModelPerformance;
import com.flynava.jupiter.model.PriceBiometricRevenueSplitModel;
import com.flynava.jupiter.model.PriceBiometricRevenueSplitTotalsModel;
import com.flynava.jupiter.model.PriceChannelsModel;
import com.flynava.jupiter.model.PriceChannelsResponse;
import com.flynava.jupiter.model.PriceCurve;
import com.flynava.jupiter.model.PriceCustomerSegmentResponse;
import com.flynava.jupiter.model.PriceElasticityModel;
import com.flynava.jupiter.model.PriceFareBrandResponse;
import com.flynava.jupiter.model.PriceHeatMapChannel;
import com.flynava.jupiter.model.PriceHeatMapODChannel;
import com.flynava.jupiter.model.PriceHeatMapSegment;
import com.flynava.jupiter.model.PriceHistoryModel;
import com.flynava.jupiter.model.PriceHistoryResponse;
import com.flynava.jupiter.model.PriceHistoryTotalsResponse;
import com.flynava.jupiter.model.PricePerformanceModel;
import com.flynava.jupiter.model.PricePerformanceResponse;
import com.flynava.jupiter.model.PriceQuote;
import com.flynava.jupiter.model.PriceQuoteResponse;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.RevenueSplitModel;
import com.flynava.jupiter.model.StrategyPerformance;
import com.flynava.jupiter.model.VlyrPoc;
import com.flynava.jupiter.serviceInterface.PriceBiometricService;
import com.flynava.jupiter.util.ApplicationConstants;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Utility;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class PriceBiometricServiceImpl implements PriceBiometricService {

	@Autowired
	PriceBiometricDao priceBiometricDao;

	private static final Logger logger = Logger.getLogger(PriceBiometricServiceImpl.class);

	@Override
	public Map<String, Object> getRevenueSplit(RequestModel mRequestModel) {
		float lFlownRevenueYTD = 0;
		float lSalesRevenueYTD = 0;
		float lRevenueVLYR = 0;
		float lRevenueVTGT = 0;
		JSONArray lTargetRevenueArray = null;
		JSONArray lRevenueForecastArray = null;
		JSONArray lSalesRevenueArray = null;
		JSONArray lFlownRevenueArray = null;
		JSONArray lFlown_1RevenueArray = null;
		JSONArray lSalesRevenue_LastYr_Array = null;
		float lTotalFlownRevenueYTD = 0;
		float lTotalSalesRevenueYTD = 0;
		float lTotalSalesRevenue_LY = 0;
		float lTotalRevenueForecast = 0;
		float lTotalRevenue_target = 0;
		float lCapacitylastyr = 0;
		float ltotalhostcapcity = 0;
		float ltotalhostcapacitylastyr = 0;
		float ltotalflownrevenue = 0;
		float ltotalflownrevenuelastyr = 0;

		JSONArray lCapacityCarrierArray = null;
		JSONArray lCapacityArray = null;
		JSONArray lCapacitylastyrArray = null;

		Map<String, Float> carrierPaxMap = new HashMap<String, Float>();
		Map<String, Float> capacitylastyrMap = new HashMap<String, Float>();
		List<FilterModel> lDataList = new ArrayList<FilterModel>();
		List<PriceBiometricRevenueSplitModel> lRevenueSplitModelList = new ArrayList<PriceBiometricRevenueSplitModel>();
		List<PriceBiometricRevenueSplitTotalsModel> lTotalsList = new ArrayList<PriceBiometricRevenueSplitTotalsModel>();

		Map<String, Object> lResponseRevenueSplitMap = new HashMap<String, Object>();

		FilterModel lFilterModel = new FilterModel();

		ArrayList<DBObject> lRevenueSplitObjList = priceBiometricDao.getRevenueSplit(mRequestModel);

		JSONArray lRevenueSplitData = new JSONArray(lRevenueSplitObjList);

		try {
			if (lRevenueSplitData != null) {

				JSONObject lRevenueSplitJsonObj = null;

				for (int i = 0; i < lRevenueSplitData.length(); i++) {
					lRevenueSplitJsonObj = lRevenueSplitData.getJSONObject(i);
					lFilterModel = new FilterModel();

					if (lRevenueSplitJsonObj.has(ApplicationConstants.DEPARTURE_DATE)
							&& lRevenueSplitJsonObj.get(ApplicationConstants.DEPARTURE_DATE) != null
							&& !lRevenueSplitJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setDepartureDate(
								lRevenueSplitJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString());
					}
					if (lRevenueSplitJsonObj.has(ApplicationConstants.REGION)
							&& lRevenueSplitJsonObj.get(ApplicationConstants.REGION) != null
							&& !"null".equalsIgnoreCase(
									lRevenueSplitJsonObj.get(ApplicationConstants.REGION).toString())) {
						lFilterModel.setRegion(lRevenueSplitJsonObj.get(ApplicationConstants.REGION).toString());
					}
					if (lRevenueSplitJsonObj.has(ApplicationConstants.COUNTRY)
							&& lRevenueSplitJsonObj.get(ApplicationConstants.COUNTRY) != null
							&& !"null".equalsIgnoreCase(
									lRevenueSplitJsonObj.get(ApplicationConstants.COUNTRY).toString())) {
						lFilterModel.setCountry(lRevenueSplitJsonObj.get(ApplicationConstants.COUNTRY).toString());
					}
					if (lRevenueSplitJsonObj.has(ApplicationConstants.POS)
							&& lRevenueSplitJsonObj.get(ApplicationConstants.POS) != null && !"null"
									.equalsIgnoreCase(lRevenueSplitJsonObj.get(ApplicationConstants.POS).toString())) {
						lFilterModel.setPos(lRevenueSplitJsonObj.get(ApplicationConstants.POS).toString());
					}

					if (lRevenueSplitJsonObj.has(ApplicationConstants.COMPARTMENT)
							&& lRevenueSplitJsonObj.get(ApplicationConstants.COMPARTMENT) != null
							&& !"null".equalsIgnoreCase(
									lRevenueSplitJsonObj.get(ApplicationConstants.COMPARTMENT).toString())) {
						lFilterModel
								.setCompartment(lRevenueSplitJsonObj.get(ApplicationConstants.COMPARTMENT).toString());
					}

					double flownRevenue = 0;
					if (lRevenueSplitJsonObj.has(ApplicationConstants.HOST_FLOWN_REVENUE)
							&& lRevenueSplitJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE) != null
							&& !"null".equalsIgnoreCase(
									lRevenueSplitJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE).toString())
							&& !"[]".equalsIgnoreCase(
									lRevenueSplitJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE).toString())) {
						lFlownRevenueArray = new JSONArray(
								lRevenueSplitJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE).toString());
						if (lFlownRevenueArray != null && lFlownRevenueArray.length() > 0) {
							flownRevenue = Utility.findSum(lFlownRevenueArray);
							lFilterModel.setFlownrevenue(((float) flownRevenue));
						}
					} else {
						lFilterModel.setFlownrevenue(((float) flownRevenue));
					}

					double flownRevenue_lastyr = 0;
					if (lRevenueSplitJsonObj.has(ApplicationConstants.HOST_FLOWN_REVENUE_LY)
							&& lRevenueSplitJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE_LY) != null
							&& !"null".equalsIgnoreCase(
									lRevenueSplitJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE_LY).toString())
							&& !"[]".equalsIgnoreCase(
									lRevenueSplitJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE_LY).toString())) {
						lFlown_1RevenueArray = new JSONArray(
								lRevenueSplitJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE_LY).toString());
						if (lFlown_1RevenueArray != null && lFlown_1RevenueArray.length() > 0) {
							flownRevenue_lastyr = Utility.findSum(lFlown_1RevenueArray);
							lFilterModel.setFlownrevenue_lastyr(((float) flownRevenue_lastyr));
						}
					} else {
						lFilterModel.setFlownRevenue_lastyr(Float.toString((float) flownRevenue_lastyr));
					}

					if (lRevenueSplitJsonObj.has(ApplicationConstants.TARGET_REVENUE)
							&& lRevenueSplitJsonObj.get(ApplicationConstants.TARGET_REVENUE) != null
							&& !"null".equalsIgnoreCase(
									lRevenueSplitJsonObj.get(ApplicationConstants.TARGET_REVENUE).toString())
							&& !"[]".equalsIgnoreCase(
									lRevenueSplitJsonObj.get(ApplicationConstants.TARGET_REVENUE).toString())) {
						lTargetRevenueArray = new JSONArray(
								lRevenueSplitJsonObj.get(ApplicationConstants.TARGET_REVENUE).toString());

					}

					if (lRevenueSplitJsonObj.has("forcast_revenue")
							&& lRevenueSplitJsonObj.get("forcast_revenue") != null
							&& !"null".equalsIgnoreCase(lRevenueSplitJsonObj.get("forcast_revenue").toString())
							&& !"[]".equalsIgnoreCase(lRevenueSplitJsonObj.get("forcast_revenue").toString())) {
						lRevenueForecastArray = new JSONArray(lRevenueSplitJsonObj.get("forcast_revenue").toString());

					}

					double lSalesRevenue = 0;
					if (lRevenueSplitJsonObj.has("revenue") && lRevenueSplitJsonObj.get("revenue") != null
							&& !"null".equalsIgnoreCase(lRevenueSplitJsonObj.get("revenue").toString())
							&& !"[]".equalsIgnoreCase(lRevenueSplitJsonObj.get("revenue").toString())) {
						lSalesRevenueArray = new JSONArray(lRevenueSplitJsonObj.get("revenue").toString());
						if (lSalesRevenueArray != null) {
							if (lSalesRevenueArray.length() > 0) {
								lSalesRevenue = Utility.findSum(lSalesRevenueArray);
								lFilterModel.setSalesRevenue(Float.toString((float) lSalesRevenue));
							}
						}
					} else {
						lFilterModel.setSalesRevenue(Float.toString((float) lSalesRevenue));
					}

					double lSalesRevenue_lastyr = 0;
					if (lRevenueSplitJsonObj.has("revenue_1") && lRevenueSplitJsonObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lRevenueSplitJsonObj.get("revenue_1").toString())
							&& !"[]".equalsIgnoreCase(lRevenueSplitJsonObj.get("revenue_1").toString())) {
						lSalesRevenue_LastYr_Array = new JSONArray(lRevenueSplitJsonObj.get("revenue_1").toString());
						if (lSalesRevenue_LastYr_Array != null && lSalesRevenue_LastYr_Array.length() > 0) {
							for (int j = 0; j < lSalesRevenue_LastYr_Array.length(); j++) {
								if (lSalesRevenue_LastYr_Array.get(j) != null
										|| "null".equals(lSalesRevenue_LastYr_Array.get(j))) {
									lSalesRevenue_lastyr = Utility.findSum(lSalesRevenue_LastYr_Array);
								} else {
									lSalesRevenue_lastyr = 0;
								}

								lFilterModel.setSalesRevenue_lastyr(Float.toString((float) lSalesRevenue_lastyr));
							}
						}
					} else {
						lFilterModel.setSalesRevenue_lastyr(Float.toString((float) lSalesRevenue_lastyr));
					}

					// Capacity Carrier
					double lCapacityCarrier = 0;
					if (lRevenueSplitJsonObj.has("capacityCarrier")
							&& lRevenueSplitJsonObj.get("capacityCarrier") != null
							&& !lRevenueSplitJsonObj.get("capacityCarrier").toString().equalsIgnoreCase("null")
							&& !lRevenueSplitJsonObj.get("capacityCarrier").toString().equalsIgnoreCase("[]")) {
						lCapacityCarrierArray = new JSONArray(lRevenueSplitJsonObj.get("capacityCarrier").toString());
					} else {
						lCapacityCarrierArray = null;
					}
					if (lRevenueSplitJsonObj.has("capacity_LY") && lRevenueSplitJsonObj.get("capacity_LY") != null
							&& !"null".equalsIgnoreCase(lRevenueSplitJsonObj.get("capacity_LY").toString())
							&& !"[]".equalsIgnoreCase(lRevenueSplitJsonObj.get("capacity_LY").toString())) {
						lCapacitylastyrArray = new JSONArray(lRevenueSplitJsonObj.get("capacity_LY").toString());
					}
					if (lCapacitylastyrArray != null) {
						for (int m = 0; m < lCapacitylastyrArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lCapacitylastyrArray.get(m).toString()))
								lCapacitylastyr += Float.parseFloat(lCapacitylastyrArray.get(m).toString());
						}
					}

					// Capacity
					double lCapacity = 0;
					if (lRevenueSplitJsonObj.has("capacity") && lRevenueSplitJsonObj.get("capacity") != null
							&& !lRevenueSplitJsonObj.get("capacity").toString().equalsIgnoreCase("null")
							&& !lRevenueSplitJsonObj.get("capacity").toString().equalsIgnoreCase("[]")) {
						lCapacityArray = new JSONArray(lRevenueSplitJsonObj.get("capacity").toString());
					} else {
						lCapacityArray = null;
					}
					if (lCapacityCarrierArray != null) {
						for (int k = 0; k < lCapacityCarrierArray.length(); k++) {
							carrierPaxMap.put(lCapacityCarrierArray.getString(k), (float) lCapacityArray.getDouble(k));
						}
						for (int k = 0; k < lCapacityCarrierArray.length(); k++) {
							capacitylastyrMap.put(lCapacityCarrierArray.getString(k),
									(float) lCapacitylastyrArray.getDouble(k));
						}
					}

					StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lFilterModel);
					String keyBuilder = lStr.toString();
					if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
							|| "nullnullnull".equalsIgnoreCase(keyBuilder)
							|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
						keyBuilder = "null";
					}
					if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {

						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}

					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.REGION)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.COUNTRY)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.POS)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getPos() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}
					}

					lFilterModel.setFilterKey(lStr.toString());

					lDataList.add(lFilterModel);

				}

				Map<String, RevenueSplitModel> lTempMap = new HashMap<String, RevenueSplitModel>();
				RevenueSplitModel lRevenueSplitModel = null;
				for (FilterModel lModel : lDataList) {

					if (!lTempMap.containsKey(lModel.getFilterKey())) {
						lRevenueSplitModel = new RevenueSplitModel();
						lRevenueSplitModel.setCombinationkey(lModel.getFilterKey());
						lRevenueSplitModel.setRegion(lModel.getRegion());
						lRevenueSplitModel.setCountry(lModel.getCountry());
						lRevenueSplitModel.setPos(lModel.getPos());
						lRevenueSplitModel.setCompartment(lModel.getCompartment());
						lRevenueSplitModel.setDepartureDate(lModel.getDepartureDate());
						/*
						 * lRevenueSplitModel.setTotalFlownRevenue(Float.
						 * parseFloat(lModel.getFlownRevenue()));
						 * lRevenueSplitModel
						 * .setTotalFlownRevenue_lastyr(Float.parseFloat(lModel.
						 * getFlownRevenue_lastyr()));
						 */
						lRevenueSplitModel.setTotalSalesRevenue(Float.parseFloat(lModel.getSalesRevenue()));
						lRevenueSplitModel
								.setTotalSalesRevenue_lastyr(Float.parseFloat(lModel.getSalesRevenue_lastyr()));

						// flown revenue
						float flownrevenue = lModel.getFlownrevenue();
						lRevenueSplitModel.setTotalFlownRevenue(flownrevenue);

						float lflownrevenuelastyr = (lModel.getFlownrevenue_lastyr());
						lRevenueSplitModel.setTotalFlownRevenue_lastyr(lflownrevenuelastyr);

						lTempMap.put(lModel.getFilterKey(), lRevenueSplitModel);
					} else {
						for (String lKey : lTempMap.keySet()) {
							if (lModel.getFilterKey().equals(lKey)) {
								lRevenueSplitModel = lTempMap.get(lKey);
							}
						}
						lRevenueSplitModel.setRegion(lModel.getRegion());
						lRevenueSplitModel.setCountry(lModel.getCountry());
						lRevenueSplitModel.setPos(lModel.getPos());
						lRevenueSplitModel.setCompartment(lModel.getCompartment());
						// flown revenue & lastyr
						float totalFlownRevenue = (lModel.getFlownrevenue())
								+ lRevenueSplitModel.getTotalFlownRevenue();
						float totalFlownRevenue_lastyr = (lModel.getFlownrevenue_lastyr())
								+ lRevenueSplitModel.getTotalFlownRevenue_lastyr();
						lRevenueSplitModel.setTotalFlownRevenue(totalFlownRevenue);
						lRevenueSplitModel.setTotalFlownRevenue_lastyr(totalFlownRevenue_lastyr);
						// sales revenue & lastyr

						float totalSales_Revenue = 0;
						totalSales_Revenue = Float.parseFloat(lModel.getSalesRevenue())
								+ lRevenueSplitModel.getTotalSalesRevenue();
						lRevenueSplitModel.setTotalSalesRevenue(totalSales_Revenue);

						float totalSales_RevenueLastyr = 0;
						totalSales_RevenueLastyr = Float.parseFloat(lModel.getSalesRevenue_lastyr())
								+ lRevenueSplitModel.getTotalSalesRevenue_lastyr();
						lRevenueSplitModel.setTotalSalesRevenue_lastyr(totalSales_RevenueLastyr);

					}
				}

				PriceBiometricRevenueSplitModel lModel = null;
				for (String key : lTempMap.keySet()) {
					lModel = new PriceBiometricRevenueSplitModel();

					if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {
						lModel.setRegion(lTempMap.get(key).getRegion());
						lModel.setCompartment(lTempMap.get(key).getCompartment());
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.REGION)) {
						lModel.setRegion(lTempMap.get(key).getRegion());
						lModel.setCountry(lTempMap.get(key).getCountry());
						lModel.setCompartment(lTempMap.get(key).getCompartment());
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.COUNTRY)) {
						lModel.setRegion(lTempMap.get(key).getRegion());
						lModel.setCountry(lTempMap.get(key).getCountry());
						lModel.setPos(lTempMap.get(key).getPos());
						lModel.setCompartment(lTempMap.get(key).getCompartment());
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.POS)) {
						lModel.setRegion(lTempMap.get(key).getRegion());
						lModel.setCountry(lTempMap.get(key).getCountry());
						lModel.setPos(lTempMap.get(key).getPos());
						lModel.setOrigin(lTempMap.get(key).getOrigin());
						lModel.setDestination(lTempMap.get(key).getDestination());
						lModel.setCompartment(lTempMap.get(key).getCompartment());
					}

					// Sales Revenue
					float lTotalSalesRevenue = lTempMap.get(key).getTotalSalesRevenue();
					lSalesRevenueYTD = lTotalSalesRevenue;
					lModel.setRevenueSalesYTD(Math.round(lSalesRevenueYTD));

					// Grand Total Sales Revenue YTD
					lTotalSalesRevenueYTD += lSalesRevenueYTD;

					// Flown Revenue
					float totalFlownRevenue = lTempMap.get(key).getTotalFlownRevenue();
					lFlownRevenueYTD = totalFlownRevenue;
					lModel.setRevenueFlownYTD(Math.round(lFlownRevenueYTD));

					// Grand Total Flown Revenue YTD
					lTotalFlownRevenueYTD += lFlownRevenueYTD;

					String host = "FZ";
					float hostcapacity = 0;

					if (carrierPaxMap.containsKey(host)) {
						hostcapacity = (carrierPaxMap.get(host));
						lModel.setHostcapacity(hostcapacity);
					}
					float hostcapacitylastyr = 0;
					if (carrierPaxMap.containsKey(host)) {
						hostcapacitylastyr = (capacitylastyrMap.get(host));
						lModel.setHostcapacitylastyr(hostcapacitylastyr);
					}
					float lFlownrevenuelastyr = lTempMap.get(key).getTotalFlownRevenue_lastyr();
					lModel.setTotalflownrevenuelastyr(lFlownrevenuelastyr);
					float pflownrevenue = lTempMap.get(key).getTotalFlownRevenue();
					lModel.setTotalflownrevenue(pflownrevenue);
					if (lFlownrevenuelastyr != 0) {
						lRevenueVLYR = CalculationUtil.calculateVLYR(pflownrevenue, lFlownrevenuelastyr, hostcapacity,
								hostcapacitylastyr);
						lModel.setRevenueVLYR(Float.toString(lRevenueVLYR));

					} else {
						lModel.setRevenueVLYR("_");
					}
					// totals for vlyr
					ltotalhostcapcity += hostcapacity;
					ltotalhostcapacitylastyr += hostcapacitylastyr;
					ltotalflownrevenue += pflownrevenue;
					ltotalflownrevenuelastyr += lFlownrevenuelastyr;

					// Sales Revenue Last Year
					float totalSalesRevenueLastyr = lTempMap.get(key).getTotalSalesRevenue_lastyr();

					/*
					 * // Revenue VLYR if (totalSalesRevenueLastyr != 0) {
					 * lRevenueVLYR =
					 * CalculationUtil.calculateVLYR(lTotalSalesRevenue,
					 * totalSalesRevenueLastyr);
					 * lModel.setRevenueVLYR(Float.toString(Math.round(
					 * lRevenueVLYR))); } else { lModel.setRevenueVLYR("NA"); }
					 */
					lTotalSalesRevenue_LY += totalSalesRevenueLastyr;

					// Total Target Revenue
					float targetRevenue = 0.0f;
					if (lTargetRevenueArray.length() > 0) {
						for (int i = 0; i < lTargetRevenueArray.length(); i++) {
							targetRevenue += Float.parseFloat(lTargetRevenueArray.get(i).toString());
						}
					}

					// total revenue forecast
					float lRevenueForecast = 0.0f;
					if (lRevenueForecastArray != null) {
						if (lRevenueForecastArray.length() > 0
								&& /*
									 * checking if the dep_date has passed or
									 * not
									 */!Utility.datePassed(lTempMap.get(key).getDepartureDate().toString())) {
							for (int i = 0; i < lRevenueForecastArray.length(); i++) {
								lRevenueForecast += Float.parseFloat(lRevenueForecastArray.get(i).toString());
							}
						}
					}

					// calculating no of days in the departure month
					int noOfDays = Utility.numberOfDaysInMonth(
							Utility.findMonth(lTempMap.get(key).getDepartureDate().toString()) - 1,
							Utility.findYear(lTempMap.get(key).getDepartureDate().toString()));

					// calculating prorated target and forecast for departure
					// date
					float lProratedTargetRevenue = targetRevenue / (float) noOfDays;
					float lProratedRevenueForecast = lRevenueForecast / (float) noOfDays;

					lTotalRevenue_target += lProratedTargetRevenue;
					lTotalRevenueForecast += lProratedRevenueForecast;

					// Revenue VTGT
					if (lProratedTargetRevenue != 0)
						lRevenueVTGT = CalculationUtil.calculateVTGTRemodelled(totalFlownRevenue,
								lProratedRevenueForecast, lProratedTargetRevenue);
					lModel.setRevenueVTGT(Float.toString((lRevenueVTGT)));
					lRevenueSplitModelList.add(lModel);
				}

				lResponseRevenueSplitMap.put("RevenueSplit", lRevenueSplitModelList);

				/* Calculation of Totals and setting in TotalList - Start */
				PriceBiometricRevenueSplitTotalsModel lTotalsModel = new PriceBiometricRevenueSplitTotalsModel();
				lTotalsModel.setTotalSalesRevenueYTD(CalculationUtil.round(lTotalSalesRevenueYTD, 1));
				lTotalsModel.setTotalFlownRevenueYTD(CalculationUtil.round(lTotalFlownRevenueYTD, 1));
				float lTotalRevenueVLYR = 0;
				if (ltotalflownrevenuelastyr != 0 && ltotalflownrevenuelastyr > 0) {
					lTotalRevenueVLYR = CalculationUtil.calculateVLYR(ltotalflownrevenue, ltotalflownrevenuelastyr,
							ltotalhostcapcity, ltotalhostcapacitylastyr);
					lTotalsModel.setTotalRevenueVLYR(Float.toString(lTotalRevenueVLYR));
				} else {
					lTotalsModel.setTotalRevenueVLYR("_");
				}

				float lTotalRevenueVTGT = 0;
				if (lTotalRevenue_target != 0 && lTotalRevenue_target > 0) {
					lTotalRevenueVTGT = CalculationUtil.calculateVTGTRemodelled(lTotalSalesRevenueYTD,
							lTotalRevenueForecast, lTotalRevenue_target);
					lTotalsModel.setTotalRevenueVTGT((lTotalRevenueVTGT));
				} else {
					lTotalsModel.setTotalRevenueVTGT(lTotalRevenueVTGT);
				}

				lTotalsList.add(lTotalsModel);

				/* Calculation of Totals and setting in TotalList - End */

				lResponseRevenueSplitMap.put("RevenueSplitTotals", lTotalsList);

			}
		} catch (Exception e) {
			logger.error("getRevenueSplit-Exception", e);
		}

		return lResponseRevenueSplitMap;
	}

	/**
	 * SQL Graph is moved to Performance Dashboard as per confluence on
	 * 08-12-2016
	 */
	@Override
	public BasicDBObject getSQLGraph(RequestModel mRequestModel) {
		BasicDBObject lSQLGraphObj = priceBiometricDao.getSQLGraph(mRequestModel);
		return lSQLGraphObj;
	}

	@Override
	public Map<String, Object> getStrategyPerformance(RequestModel mRequestModel) {
		ArrayList<DBObject> lStrategyPerformanceObjList = priceBiometricDao.getStrategyPerformance(mRequestModel);
		Map<String, Object> strategyPerformanceMap = new HashMap<String, Object>();
		Map<String, StrategyPerformance> spMap = new HashMap<String, StrategyPerformance>();
		JSONArray lStrategyPerformanceObjArray = new JSONArray(lStrategyPerformanceObjList);
		if (lStrategyPerformanceObjArray != null) {
			for (int i = 0; i < lStrategyPerformanceObjArray.length(); i++) {
				JSONObject jsonObj = lStrategyPerformanceObjArray.getJSONObject(i);
				/**/
				JSONArray pricingArray = null;
				String Strategy = null;
				String pricingStrategy = null;
				if (jsonObj.has(ApplicationConstants.STRATEGY_ID)) {
					if (jsonObj.get(ApplicationConstants.STRATEGY_ID) != null
							&& !jsonObj.get(ApplicationConstants.STRATEGY_ID).toString().equalsIgnoreCase("null")
							&& !jsonObj.get(ApplicationConstants.STRATEGY_ID).toString().equalsIgnoreCase("[]")) {
						pricingArray = new JSONArray(jsonObj.get(ApplicationConstants.STRATEGY_ID).toString());
						if (pricingArray.length() > 0) {
							for (int j = 0; j < pricingArray.length(); j++) {
								if (pricingArray != null && !pricingArray.get(j).toString().equalsIgnoreCase("null")
										&& !pricingArray.get(j).toString().equalsIgnoreCase("[]")) {
									JSONObject strategyname = new JSONObject(pricingArray.getJSONObject(j).toString());
									if (strategyname.length() > 0)
										if (strategyname != null
												&& !strategyname.get(ApplicationConstants.PRICING_STRATEGY).toString()
														.equalsIgnoreCase("null")) {
											pricingStrategy = (strategyname.get(ApplicationConstants.PRICING_STRATEGY)
													.toString());
											if (pricingStrategy.length() > 0
													&& !pricingStrategy.equalsIgnoreCase("[]")) {
												Strategy = pricingStrategy.substring(2, 6);
											}

										}
								}
							}
						}
					}
				}

				String origin = "-";
				if (jsonObj.has(ApplicationConstants.ORIGIN) && jsonObj.get(ApplicationConstants.ORIGIN) != null
						&& !jsonObj.get(ApplicationConstants.ORIGIN).toString().equalsIgnoreCase("null"))
					origin = jsonObj.getString(ApplicationConstants.ORIGIN);
				String destination = "-";
				if (jsonObj.has(ApplicationConstants.DESTINATION)
						&& jsonObj.get(ApplicationConstants.DESTINATION) != null
						&& !jsonObj.get(ApplicationConstants.DESTINATION).toString().equalsIgnoreCase("null"))
					destination = jsonObj.getString(ApplicationConstants.DESTINATION);
				String od = origin + destination;
				if (Strategy != null) {
					if (!spMap.containsKey(Strategy)) {
						StrategyPerformance sp = new StrategyPerformance();
						Set<String> odSet = new HashSet<String>();
						odSet.add(od);
						sp.setStrategyName(Strategy);
						sp.setOdSet(odSet);
						spMap.put(Strategy, sp);
					} else {
						StrategyPerformance sp = spMap.get(Strategy);
						sp.getOdSet().add(od);
					}
				}
			}
			Double totelOdCount = 0D;
			for (String key : spMap.keySet()) {
				StrategyPerformance sp = spMap.get(key);
				Double odCount = (double) sp.getOdSet().size();
				sp.setOdCount(odCount);
				totelOdCount += odCount;
			}
			for (String key : spMap.keySet()) {
				StrategyPerformance sp = spMap.get(key);
				sp.setUsage((sp.getOdCount() / totelOdCount) * 100);
				// for now performance is not present
				sp.setPerformance(0D);
			}

			List<StrategyPerformance> spList = new ArrayList<StrategyPerformance>(spMap.values());
			strategyPerformanceMap.put("StrategyPerformance", spList);

		}

		return strategyPerformanceMap;

	}

	@Override
	public Map<String, Object> getModelPerformance(RequestModel mRequestModel) {

		ArrayList<DBObject> lModelPerformanceObjList = priceBiometricDao.getModelPerformance(mRequestModel);
		Map<String, Object> modelPerformanceMap = new HashMap<String, Object>();
		Map<String, ModelPerformance> mMap = new HashMap<String, ModelPerformance>();
		JSONArray lStrategyPerformanceObjArray = new JSONArray(lModelPerformanceObjList);
		String modelName = null;
		if (lStrategyPerformanceObjArray != null) {
			for (int i = 0; i < lStrategyPerformanceObjArray.length(); i++) {
				JSONObject jsonObj = lStrategyPerformanceObjArray.getJSONObject(i);

				if (jsonObj.has(ApplicationConstants.MODEL)) {
					if (jsonObj.get(ApplicationConstants.MODEL) != null
							&& !jsonObj.get(ApplicationConstants.MODEL).toString().equalsIgnoreCase("null")) {
						modelName = (jsonObj.get(ApplicationConstants.MODEL).toString());

					}
				}
				String origin = "-";
				if (jsonObj.has(ApplicationConstants.ORIGIN) && jsonObj.get(ApplicationConstants.ORIGIN) != null
						&& !jsonObj.get(ApplicationConstants.ORIGIN).toString().equalsIgnoreCase("null"))
					origin = jsonObj.getString(ApplicationConstants.ORIGIN);
				String destination = "-";
				if (jsonObj.has(ApplicationConstants.DESTINATION)
						&& jsonObj.get(ApplicationConstants.DESTINATION) != null
						&& !jsonObj.get(ApplicationConstants.DESTINATION).toString().equalsIgnoreCase("null"))
					destination = jsonObj.getString(ApplicationConstants.DESTINATION);
				String od = origin + destination;
				if (!mMap.containsKey(modelName)) {
					ModelPerformance mp = new ModelPerformance();
					Set<String> odSet = new HashSet<String>();
					odSet.add(od);
					mp.setModelName(modelName);
					mp.setOdSet(odSet);
					mMap.put(modelName, mp);
				} else {
					ModelPerformance mp = mMap.get(modelName);
					mp.getOdSet().add(od);
				}
			}
			Double totelOdCount = 0D;
			for (String key : mMap.keySet()) {
				ModelPerformance mp = mMap.get(key);
				Double odCount = (double) mp.getOdSet().size();
				mp.setOdCount(odCount);
				totelOdCount += odCount;
			}
			for (String key : mMap.keySet()) {
				ModelPerformance mp = mMap.get(key);
				mp.setUsage((mp.getOdCount() / totelOdCount) * 100);
				// for now performance is not present
				mp.setPerformance(0D);
			}

			List<ModelPerformance> mpList = new ArrayList<ModelPerformance>(mMap.values());
			modelPerformanceMap.put("StrategyPerformance", mpList);

		}

		return modelPerformanceMap;

	}

	@Override
	public Map<String, Object> getPriceCurve(RequestModel mRequestModel) {
		ArrayList<DBObject> priceCurve = priceBiometricDao.getPriceCurve(mRequestModel);
		JSONArray priceCurveData = new JSONArray(priceCurve);

		List<PriceCurve> pcList = new ArrayList<PriceCurve>();
		Map<String, Double> rbdPaxMap = new HashMap<String, Double>();
		Map<String, Object> priceCurveMap = new HashMap<String, Object>();

		for (int i = 0; i < priceCurveData.length(); i++) {
			JSONObject jsonObj = priceCurveData.getJSONObject(i);
			String depDate = "-";
			if (jsonObj.get(ApplicationConstants.DEPARTURE_DATE) != null
					&& !jsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString().equalsIgnoreCase("null"))
				depDate = jsonObj.getString(ApplicationConstants.DEPARTURE_DATE);
			String origin = "-";
			if (jsonObj.get(ApplicationConstants.ORIGIN) != null
					&& !jsonObj.get(ApplicationConstants.ORIGIN).toString().equalsIgnoreCase("null"))
				origin = jsonObj.getString(ApplicationConstants.ORIGIN);
			String destination = "-";
			if (jsonObj.get(ApplicationConstants.DESTINATION) != null
					&& !jsonObj.get(ApplicationConstants.DESTINATION).toString().equalsIgnoreCase("null"))
				destination = jsonObj.getString(ApplicationConstants.DESTINATION);
			String rbd = "-";
			if (jsonObj.get(ApplicationConstants.RBD) != null
					&& !jsonObj.get(ApplicationConstants.RBD).toString().equalsIgnoreCase("null"))
				rbd = jsonObj.getString(ApplicationConstants.RBD);

			double baseFare = 0D;
			if (jsonObj.has(ApplicationConstants.BASEFARE) && jsonObj.get(ApplicationConstants.BASEFARE) != null
					&& !jsonObj.get(ApplicationConstants.BASEFARE).toString().equalsIgnoreCase("null"))
				baseFare = jsonObj.getDouble(ApplicationConstants.BASEFARE);

			double hostRevenue = 0D;
			if (jsonObj.has("Host_revenue") && jsonObj.get("Host_revenue") != null
					&& !jsonObj.get("Host_revenue").toString().equalsIgnoreCase("null"))
				hostRevenue = jsonObj.getDouble("Host_revenue");

			double hostFlownRevenue = 0D;
			if (jsonObj.has(ApplicationConstants.HOST_FLOWN_REVENUE)
					&& jsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE) != null
					&& !jsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE).toString().equalsIgnoreCase("null"))
				hostFlownRevenue = Utility.findSum(jsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE));

			double hostRevenueLastYear = 0D;
			if (jsonObj.get("Host_revenue_last_year") != null
					&& !jsonObj.get("Host_revenue_last_year").toString().equalsIgnoreCase("null"))
				hostRevenueLastYear = jsonObj.getDouble("Host_revenue_last_year");

			double hostRevenueTarget = 0D;
			if (jsonObj.get("revenue_target") != null
					&& !jsonObj.get("revenue_target").toString().equalsIgnoreCase("null"))
				hostRevenueTarget = Utility.findSum(jsonObj.get("revenue_target"));

			double hostRevenueForecast = 0.0D;
			if (!Utility.datePassed(jsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString())
					&& checkIfValueExist(ApplicationConstants.FORECAST_REVENUE, jsonObj)) {
				hostRevenueForecast = Utility.findSum(jsonObj.get(ApplicationConstants.FORECAST_REVENUE));
			}

			double hostPax = 0D;
			if (jsonObj.has("Host_pax") && jsonObj.get("Host_pax") != null
					&& !jsonObj.get("Host_pax").toString().equalsIgnoreCase("null"))
				hostPax = jsonObj.getDouble("Host_pax");

			double hostFlownPax = 0D;
			if (jsonObj.has(ApplicationConstants.HOST_FLOWN_PAX)
					&& jsonObj.get(ApplicationConstants.HOST_FLOWN_PAX) != null
					&& !jsonObj.get(ApplicationConstants.HOST_FLOWN_PAX).toString().equalsIgnoreCase("null"))
				hostFlownPax = Utility.findSum(jsonObj.get(ApplicationConstants.HOST_FLOWN_PAX));

			double hostPaxLastYear = 0D;
			if (jsonObj.has(ApplicationConstants.HOST_SALES_PAX_LY)
					&& jsonObj.get(ApplicationConstants.HOST_SALES_PAX_LY) != null
					&& !jsonObj.get(ApplicationConstants.HOST_SALES_PAX_LY).toString().equalsIgnoreCase("null"))
				hostPaxLastYear = jsonObj.getDouble(ApplicationConstants.HOST_SALES_PAX_LY);

			double hostPaxTarget = 0D;
			if (jsonObj.has(ApplicationConstants.TARGET_PAX) && jsonObj.get(ApplicationConstants.TARGET_PAX) != null
					&& !jsonObj.get(ApplicationConstants.TARGET_PAX).toString().equalsIgnoreCase("null"))
				hostPaxTarget = Utility.findSum(jsonObj.get(ApplicationConstants.TARGET_PAX));

			double hostPaxForecast = 0D;
			if (jsonObj.has(ApplicationConstants.FORECAST_PAX) && jsonObj.get(ApplicationConstants.FORECAST_PAX) != null
					&& !jsonObj.get(ApplicationConstants.FORECAST_PAX).toString().equalsIgnoreCase("null"))
				hostPaxTarget = Utility.findSum(jsonObj.get(ApplicationConstants.FORECAST_PAX));

			int noOfdays = Utility.numberOfDaysInMonth(
					Utility.findMonth(jsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString()) - 1,
					Utility.findYear(jsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString()));

			double proratedHostPaxTarget = hostPaxTarget / (double) noOfdays;
			double proratedHostPaxForecast = hostPaxForecast / (double) noOfdays;
			double proratedHostRevenueTarget = hostRevenueTarget / (double) noOfdays;
			double proratedHostRevenueForecast = hostRevenueForecast / (double) noOfdays;

			double marketPaxTotel = 0D;
			if (jsonObj.get("market_pax") != null && !jsonObj.get("market_pax").toString().equalsIgnoreCase("null"))
				marketPaxTotel = Utility.findSum(jsonObj.get("market_pax"));
			double marketPaxTotelLastYear = 0D;
			if (jsonObj.get("market_pax_1") != null && !jsonObj.get("market_pax_1").toString().equalsIgnoreCase("null"))
				marketPaxTotelLastYear = Utility.findSum(jsonObj.get("market_pax_1"));

			// creating rbd and pax map
			if (!rbdPaxMap.containsKey(rbd)) {
				rbdPaxMap.put(rbd, hostPax);
			} else {
				rbdPaxMap.put(rbd, rbdPaxMap.get(rbd) + hostPax);
			}

			PriceCurve pc = new PriceCurve();
			pc.setDepDate(depDate);
			pc.setOrigin(origin);
			pc.setDestination(destination);
			pc.setRbd(rbd);
			pc.setBaseFare(baseFare);
			pc.setHostRevenue(hostRevenue);
			pc.setHostPax(hostPax);

			Double hostRevenueVLYR = 0D;
			if (hostRevenueLastYear != 0) {
				hostRevenueVLYR = ((hostRevenue - hostRevenueLastYear) / hostRevenueLastYear) * 100;
			}
			Double hostRevenueVTGT = 0D;
			if (hostRevenueTarget != 0) {
				hostRevenueVTGT = (double) CalculationUtil.calculateVTGTRemodelled((float) hostFlownRevenue,
						(float) proratedHostRevenueForecast, (float) proratedHostRevenueTarget);
			}
			Double hostPaxVLYR = 0D;
			if (hostPaxLastYear != 0) {
				hostPaxVLYR = ((hostPax - hostPaxLastYear) / hostPaxLastYear) * 100;
			}
			Double hostPaxVTGT = 0D;
			if (hostPaxTarget != 0) {
				hostPaxVTGT = (double) CalculationUtil.calculateVTGTRemodelled((float) hostFlownPax,
						(float) proratedHostPaxForecast, (float) proratedHostPaxTarget);
			}
			Double marketShare = 0D;
			if (marketPaxTotel != 0)
				marketShare = (hostPax / marketPaxTotel) * 100;
			Double marketShareLastYear = 0D;
			if (marketPaxTotelLastYear != 0)
				marketShareLastYear = (hostPaxLastYear / marketPaxTotelLastYear) * 100;
			Double marketShareVLYR = 0D;
			if (marketShareLastYear != 0)
				marketShareVLYR = ((marketShare - marketShareLastYear) / marketShareLastYear) * 100;

			pc.setHostRevenueVLYR(hostRevenueVLYR);
			pc.setHostRevenueVTGT(hostRevenueVTGT);
			pc.setHostPaxVLYR(hostPaxVLYR);
			pc.setHostPaxVTGT(hostPaxVTGT);
			pc.setMarketShare(marketShare);
			pc.setMarketShareVLYR(marketShareVLYR);

			// setting the below values as zero as data is not available
			pc.setFairMarketShare(0D);
			pc.setAvgMarketShare(0D);
			pcList.add(pc);

		}
		// finding top 4 RBD's based on pax
		int size = 4;
		if (rbdPaxMap.size() < 4) {
			size = rbdPaxMap.size();
		}
		Map<String, Double> sortedMap = CalculationUtil.getTopNValuesOfMap(rbdPaxMap, size);

		priceCurveMap.put("top4rbds", sortedMap);

		priceCurveMap.put("priceCurve", pcList);
		return priceCurveMap;
	}

	@Override
	public Map<String, Object> getPricePerformance(RequestModel mRequestModel) {

		float lSalesRevenueYTD = 0;
		float lRevenueVLYR = 0;
		float lRevenueVTGT = 0;

		float lPaxYTD = 0;
		float lPaxVLYR = 0;
		float lPaxVTGT = 0;

		float marketShareYTD = 0;
		float marketShare_lastyr = 0;
		float marketShareVLYR = 0.0f;

		JSONArray lHostFlownRevenueArray = null;
		JSONArray lTargetRevenueArray = null;
		JSONArray lHostRevenueForecastArray = null;
		JSONArray lHostMSPaxArray = null;
		JSONArray lHostPaxFlownArray = null;
		JSONArray lHostPaxForecastArray = null;
		JSONArray lTargetPaxArray = null;
		JSONArray lHostMSPax_LastYr_Array = null;
		JSONArray lCarrierArray = null;
		JSONArray lHostBookingsArray = null;
		JSONArray lHostBookingsLastYearArray = null;
		JSONArray lHostBookingsTargetArray = null;
		JSONArray lCapacityArray = null;
		JSONArray lCapacityCarrierArray = null;

		JSONArray lCapacityFrequencyArray = null;
		JSONArray lRatingCarrierArray = null;
		JSONArray lCompetitiorRatingArray = null;
		float lTotaltargetPax = 0;
		float totalRevenueYTD = 0;
		float totalRevenue_LY = 0;
		float totalPaxYTD = 0;
		float totalPax_LY = 0;
		float totalRevenue_VLYR = 0;
		float totalPax_VLYR = 0;
		float lTotalforcastRevenue = 0;
		float lTotalforcastPax = 0;
		float lTotalTargetRevenue = 0;
		double forcastpax = 0;
		double forcastrevenue = 0;
		JSONArray flownrevenuelastyrarray = null;
		double flownrevenuelastyr = 0;
		float ltotalflownpaxlastyr = 0;
		float ltotalflownrevenuelastyr = 0;
		float ltotalhostcapcity = 0;
		float ltotlahostcapacitylastyr = 0;
		float lTotalsForcastRevenue = 0;
		float lTotalflownpax = 0;
		float ltotalflownrevenue = 0;
		float lTotalsForcastPax = 0;

		float ltotalflowntrevenue = 0;
		float ltotalflownpax = 0;
		float ltotalrevenuevtgt = 0;
		float ltotalmarketsharevtgt = 0;
		float ltotalodDistance = 0;
		float ltotalmarketsize = 0;
		float ltotalmarketsharepax = 0;
		float ltotalmarketsizelastyr = 0;
		float ltotalmarketsharepaxlastyr = 0;
		float ltotalhostbooking = 0;
		float ltotalcapcityFZ = 0;
		float ltotalhostbookinglastyr = 0;
		float ltotalhostbookingtarget = 0;
		float lFlownpax = 0;
		float ltotalFms = 0;
		float lFlownRevenue = 0;
		float lFlownPaxlastyr = 0;
		float lFlownrevenuelastyr = 0;
		JSONArray lCapacitylastyrArray = null;
		JSONArray flownpaxlastyrarray = null;
		double flownpaxlastyr = 0;
		List<FilterModel> lPricePerfModelList = new ArrayList<FilterModel>();
		List<PricePerformanceResponse> lPricePerformanceList = new ArrayList<PricePerformanceResponse>();
		List<PriceHistoryTotalsResponse> lTotalsList = new ArrayList<PriceHistoryTotalsResponse>();

		FilterModel lFilterModel = new FilterModel();
		Map<String, Float> carrierPaxMap = new HashMap<String, Float>();
		Map<String, Float> capacitylastyrMap = new HashMap<String, Float>();
		Map<String, Object> responsePricePerfMap = new HashMap<String, Object>();

		ArrayList<DBObject> lPricePerformanceObjList = priceBiometricDao.getPricePerformance(mRequestModel);
		JSONArray lPricePerfData = new JSONArray(lPricePerformanceObjList);
		try {
			if (lPricePerfData != null) {
				for (int i = 0; i < lPricePerfData.length(); i++) {
					JSONObject lPricePerfJsonObj = lPricePerfData.getJSONObject(i);
					lFilterModel = new FilterModel();
					System.out.println("lPricePerfJsonObj" + lPricePerfJsonObj);

					if (lPricePerfJsonObj.has(ApplicationConstants.DEPARTURE_DATE)
							&& lPricePerfJsonObj.get(ApplicationConstants.DEPARTURE_DATE) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.DEPARTURE_DATE).toString().equalsIgnoreCase("null")) {
						lFilterModel.setDepartureDate(
								lPricePerfJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.REGION)
							&& lPricePerfJsonObj.get(ApplicationConstants.REGION) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.REGION).toString().equalsIgnoreCase("null")) {
						lFilterModel.setRegion(lPricePerfJsonObj.get(ApplicationConstants.REGION).toString());
					}
					if (lPricePerfJsonObj.has(ApplicationConstants.COUNTRY)
							&& lPricePerfJsonObj.get(ApplicationConstants.COUNTRY) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.COUNTRY).toString().equalsIgnoreCase("null")) {
						lFilterModel.setCountry(lPricePerfJsonObj.get(ApplicationConstants.COUNTRY).toString());
					}
					if (lPricePerfJsonObj.has(ApplicationConstants.POS)
							&& lPricePerfJsonObj.get(ApplicationConstants.POS) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.POS).toString().equalsIgnoreCase("null")) {
						lFilterModel.setPos(lPricePerfJsonObj.get(ApplicationConstants.POS).toString());
					}
					if (lPricePerfJsonObj.has(ApplicationConstants.ORIGIN)
							&& lPricePerfJsonObj.get(ApplicationConstants.ORIGIN) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.ORIGIN).toString().equalsIgnoreCase("null")) {
						lFilterModel.setOrigin(lPricePerfJsonObj.get(ApplicationConstants.ORIGIN).toString());
					}
					if (lPricePerfJsonObj.has(ApplicationConstants.DESTINATION)
							&& lPricePerfJsonObj.get(ApplicationConstants.DESTINATION) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.DESTINATION).toString().equalsIgnoreCase("null")) {
						lFilterModel.setDestination(lPricePerfJsonObj.get(ApplicationConstants.DESTINATION).toString());
					}

					lFilterModel.setOd(lFilterModel.getOrigin() + lFilterModel.getDestination());

					if (lPricePerfJsonObj.has(ApplicationConstants.COMPARTMENT)
							&& lPricePerfJsonObj.get(ApplicationConstants.COMPARTMENT) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.COMPARTMENT).toString().equalsIgnoreCase("null")) {
						lFilterModel.setCompartment(lPricePerfJsonObj.get(ApplicationConstants.COMPARTMENT).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.CHANNEL)
							&& lPricePerfJsonObj.get(ApplicationConstants.CHANNEL) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.CHANNEL).toString().equalsIgnoreCase("null")) {
						lFilterModel.setChannel(lPricePerfJsonObj.get(ApplicationConstants.CHANNEL).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.FAREBASIS)
							&& lPricePerfJsonObj.get(ApplicationConstants.FAREBASIS) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.FAREBASIS).toString().equalsIgnoreCase("null")) {
						lFilterModel.setFareBasis(lPricePerfJsonObj.get(ApplicationConstants.FAREBASIS).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.RBD)
							&& lPricePerfJsonObj.get(ApplicationConstants.RBD) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.RBD).toString().equalsIgnoreCase("null")) {
						lFilterModel.setRbd(lPricePerfJsonObj.get(ApplicationConstants.RBD).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.RULEID)
							&& lPricePerfJsonObj.get(ApplicationConstants.RULEID) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.RULEID).toString().equalsIgnoreCase("null")) {
						lFilterModel.setRuleId(lPricePerfJsonObj.get(ApplicationConstants.RULEID).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.FOOTNOTE)
							&& lPricePerfJsonObj.get(ApplicationConstants.FOOTNOTE) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.FOOTNOTE).toString().equalsIgnoreCase("null")) {
						lFilterModel.setFootNote(lPricePerfJsonObj.get(ApplicationConstants.FOOTNOTE).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.BASEFARE)
							&& lPricePerfJsonObj.get(ApplicationConstants.BASEFARE) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.BASEFARE).toString().equalsIgnoreCase("null")) {
						lFilterModel.setBaseFare(lPricePerfJsonObj.get(ApplicationConstants.BASEFARE).toString());
					} else {
						lFilterModel.setBaseFare("0");
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.SURCHARGE)
							&& lPricePerfJsonObj.get(ApplicationConstants.SURCHARGE) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.SURCHARGE).toString().equalsIgnoreCase("null")) {
						lFilterModel.setSurCharge(lPricePerfJsonObj.get(ApplicationConstants.SURCHARGE).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.YQ)
							&& lPricePerfJsonObj.get(ApplicationConstants.YQ) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.YQ).toString().equalsIgnoreCase("null")) {
						lFilterModel.setYqCharge(lPricePerfJsonObj.get(ApplicationConstants.YQ).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.TAXES)
							&& lPricePerfJsonObj.get(ApplicationConstants.TAXES) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.TAXES).toString().equalsIgnoreCase("null")) {
						lFilterModel.setTaxes(lPricePerfJsonObj.get(ApplicationConstants.TAXES).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.TOTALFARE)
							&& lPricePerfJsonObj.get(ApplicationConstants.TOTALFARE) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.TOTALFARE).toString().equalsIgnoreCase("null")) {
						lFilterModel.setTotalFare(lPricePerfJsonObj.get(ApplicationConstants.TOTALFARE).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.HOST_SALES_REVENUE)
							&& lPricePerfJsonObj.get(ApplicationConstants.HOST_SALES_REVENUE) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.HOST_SALES_REVENUE).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setSalesRevenue(
								lPricePerfJsonObj.get(ApplicationConstants.HOST_SALES_REVENUE).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.HOST_SALES_REVENUE_LY)
							&& lPricePerfJsonObj.get(ApplicationConstants.HOST_SALES_REVENUE_LY) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.HOST_SALES_REVENUE_LY).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setSalesRevenue_lastyr(
								lPricePerfJsonObj.get(ApplicationConstants.HOST_SALES_REVENUE_LY).toString());
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.TARGET_REVENUE)
							&& lPricePerfJsonObj.get(ApplicationConstants.TARGET_REVENUE) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.TARGET_REVENUE).toString().equalsIgnoreCase("null")) {
						lTargetRevenueArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.TARGET_REVENUE).toString());
					}
					if (lTargetRevenueArray != null) {
						for (int m = 0; m < lTargetRevenueArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lTargetRevenueArray.get(m).toString()))
								lTotalTargetRevenue += Float.parseFloat(lTargetRevenueArray.get(m).toString());
						}
					}
					double targetpax = 0;
					if (lPricePerfJsonObj.has("target_pax") && lPricePerfJsonObj.get("target_pax") != null
							&& !"null".equalsIgnoreCase(lPricePerfJsonObj.get("target_pax").toString())
							&& !"[]".equalsIgnoreCase(lPricePerfJsonObj.get("target_pax").toString())) {
						lTargetPaxArray = new JSONArray(lPricePerfJsonObj.get("target_pax").toString());

					}
					if (lTargetPaxArray != null) {
						for (int m = 0; m < lTargetPaxArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lTargetPaxArray.get(m).toString()))
								lTotaltargetPax += Float.parseFloat(lTargetPaxArray.get(m).toString());
						}
					}
					if (lPricePerfJsonObj.has(ApplicationConstants.CURRENCY)
							&& lPricePerfJsonObj.get(ApplicationConstants.CURRENCY) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.CURRENCY).toString().equalsIgnoreCase("null")) {
						lFilterModel.setCurrency(lPricePerfJsonObj.get(ApplicationConstants.CURRENCY).toString());
					}

					if (checkIfValueExist(ApplicationConstants.MARKET_TYPE, lPricePerfJsonObj)) {
						lFilterModel.setMarketType(lPricePerfJsonObj.get(ApplicationConstants.MARKET_TYPE).toString());
					} else {
						lFilterModel.setMarketType("-");
					}

					if (checkIfValueExist(ApplicationConstants.HOST_SALES_PAX, lPricePerfJsonObj)) {
						lFilterModel.setHostPax(lPricePerfJsonObj.get(ApplicationConstants.HOST_SALES_PAX).toString());
					}

					if (checkIfValueExist(ApplicationConstants.HOST_SALES_PAX_LY, lPricePerfJsonObj)) {
						lFilterModel.setHostPax_lastyr(
								lPricePerfJsonObj.get(ApplicationConstants.HOST_SALES_PAX_LY).toString());
					}

					if (checkIfValueExist(ApplicationConstants.TARGET_PAX, lPricePerfJsonObj)) {
						lTargetPaxArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.TARGET_PAX).toString());
					}
					if (lPricePerfJsonObj.has("flown_pax_1") && lPricePerfJsonObj.get("flown_pax_1") != null
							&& !"null".equalsIgnoreCase(lPricePerfJsonObj.get("flown_pax_1").toString())) {
						flownpaxlastyrarray = new JSONArray(lPricePerfJsonObj.get("flown_pax_1").toString());
						if (flownpaxlastyrarray != null) {
							if (flownpaxlastyrarray.length() > 0) {
								flownpaxlastyr = Utility.findSum(flownpaxlastyrarray);
								lFilterModel.setFlownpaxlastyr((float) flownpaxlastyr);
							}
						}
					}
					if (lPricePerfJsonObj.has("flown_revenue_1") && lPricePerfJsonObj.get("flown_revenue_1") != null
							&& !"null".equalsIgnoreCase(lPricePerfJsonObj.get("flown_revenue_1").toString())) {
						flownrevenuelastyrarray = new JSONArray(lPricePerfJsonObj.get("flown_revenue_1").toString());
						if (flownrevenuelastyrarray != null) {
							if (flownrevenuelastyrarray.length() > 0) {
								flownrevenuelastyr = Utility.findSum(flownrevenuelastyrarray);
								lFilterModel.setFlownrevenue_lastyr((float) flownrevenuelastyr);
							}
						}
					}

					if (checkIfValueExist(ApplicationConstants.CARRIER, lPricePerfJsonObj)
							&& !"[]".equalsIgnoreCase(lPricePerfJsonObj.get(ApplicationConstants.CARRIER).toString())) {
						lCarrierArray = new JSONArray(lPricePerfJsonObj.get(ApplicationConstants.CARRIER).toString());
					} else {
						lCarrierArray = null;
					}

					if (checkIfValueExist(ApplicationConstants.MARKETSHARE, lPricePerfJsonObj) && !"[]"
							.equalsIgnoreCase(lPricePerfJsonObj.get(ApplicationConstants.MARKETSHARE).toString())) {
						lHostMSPaxArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.MARKETSHARE).toString());
					} else {
						lHostMSPaxArray = null;
					}

					if (checkIfValueExist(ApplicationConstants.MARKETSHARE_LY, lPricePerfJsonObj) && !"[]"
							.equalsIgnoreCase(lPricePerfJsonObj.get(ApplicationConstants.MARKETSHARE_LY).toString())) {
						lHostMSPax_LastYr_Array = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.MARKETSHARE_LY).toString());
					} else {
						lHostMSPax_LastYr_Array = null;
					}

					// Capacity Carrier
					double lCapacityCarrier = 0;
					if (lPricePerfJsonObj.has("capacity_airline") && lPricePerfJsonObj.get("capacity_airline") != null
							&& !lPricePerfJsonObj.get("capacity_airline").toString().equalsIgnoreCase("null")
							&& !lPricePerfJsonObj.get("capacity_airline").toString().equalsIgnoreCase("[]")) {
						lCapacityCarrierArray = new JSONArray(lPricePerfJsonObj.get("capacity_airline").toString());
					} else {
						lCapacityCarrierArray = null;
					}

					// Capacity Frequency
					double lCapacityFrequency = 0;
					if (lPricePerfJsonObj.has(ApplicationConstants.CAPACITY_FREQUENCY)
							&& lPricePerfJsonObj.get(ApplicationConstants.CAPACITY_FREQUENCY) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.CAPACITY_FREQUENCY).toString()
									.equalsIgnoreCase("null")
							&& !lPricePerfJsonObj.get(ApplicationConstants.CAPACITY_FREQUENCY).toString()
									.equalsIgnoreCase("[]")) {
						lCapacityFrequencyArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.CAPACITY_FREQUENCY).toString());
					} else {
						lCapacityFrequencyArray = null;
					}

					// Capacity
					double lCapacity = 0;
					if (lPricePerfJsonObj.has(ApplicationConstants.CAPACITY)
							&& lPricePerfJsonObj.get(ApplicationConstants.CAPACITY) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.CAPACITY).toString().equalsIgnoreCase("null")
							&& !lPricePerfJsonObj.get(ApplicationConstants.CAPACITY).toString()
									.equalsIgnoreCase("[]")) {
						lCapacityArray = new JSONArray(lPricePerfJsonObj.get(ApplicationConstants.CAPACITY).toString());
					} else {
						lCapacityArray = null;
					}
					if (lPricePerfJsonObj.has("capacity_1") && lPricePerfJsonObj.get("capacity_1") != null
							&& !"null".equalsIgnoreCase(lPricePerfJsonObj.get("capacity_1").toString())
							&& !"[]".equalsIgnoreCase(lPricePerfJsonObj.get("capacity_1").toString())) {
						lCapacitylastyrArray = new JSONArray(lPricePerfJsonObj.get("capacity_1").toString());
					}
					if (lCapacityCarrierArray != null) {
						for (int k = 0; k < lCapacityCarrierArray.length(); k++) {
							carrierPaxMap.put(lCapacityCarrierArray.getString(k), (float) lCapacityArray.getDouble(k));
						}
					}
					if (lCapacitylastyrArray != null && lCapacityCarrierArray != null) {
						for (int k = 0; k < lCapacityCarrierArray.length(); k++) {
							capacitylastyrMap.put(lCapacityCarrierArray.getString(k),
									(float) lCapacitylastyrArray.getDouble(k));
						}
					}

					double lHostBookings = 0;
					if (lPricePerfJsonObj.has(ApplicationConstants.HOST_BOOKINGS)
							&& lPricePerfJsonObj.get(ApplicationConstants.HOST_BOOKINGS) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.HOST_BOOKINGS).toString()
									.equalsIgnoreCase("null")
							&& !lPricePerfJsonObj.get(ApplicationConstants.HOST_BOOKINGS).toString()
									.equalsIgnoreCase("[]")) {
						lHostBookingsArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.HOST_BOOKINGS).toString());
						if (lHostBookingsArray != null) {
							if (lHostBookingsArray.length() > 0) {
								lHostBookings = Utility.findSum(lHostBookingsArray);
								lFilterModel.setHostBookings(Float.toString((float) lHostBookings));
							} else {
								lFilterModel.setHostBookings(Float.toString((float) lHostBookings));
							}
						}
					} else {
						lFilterModel.setHostBookings(Float.toString((float) lHostBookings));
					}

					double lHostBookings_lastyr = 0;
					if (lPricePerfJsonObj.has(ApplicationConstants.HOST_BOOKINGS_LY)
							&& lPricePerfJsonObj.get(ApplicationConstants.HOST_BOOKINGS_LY) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.HOST_BOOKINGS_LY).toString()
									.equalsIgnoreCase("null")
							&& !lPricePerfJsonObj.get(ApplicationConstants.HOST_BOOKINGS_LY).toString()
									.equalsIgnoreCase("[]")) {
						lHostBookingsLastYearArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.HOST_BOOKINGS_LY).toString());
						if (lHostBookingsLastYearArray != null) {
							if (lHostBookingsLastYearArray.length() > 0) {
								lHostBookings_lastyr = Utility.findSum(lHostBookingsLastYearArray);
								lFilterModel.setHostBookings_lastyr(Float.toString((float) lHostBookings_lastyr));
							} else {
								lFilterModel.setHostBookings_lastyr(Float.toString((float) lHostBookings_lastyr));
							}
						}
					} else {
						lFilterModel.setHostBookings_lastyr(Float.toString((float) lHostBookings_lastyr));
					}

					double lHostBookingsTgt = 0;
					if (lPricePerfJsonObj.has(ApplicationConstants.TARGET_BOOKINGS)
							&& lPricePerfJsonObj.get(ApplicationConstants.TARGET_BOOKINGS) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.TARGET_BOOKINGS).toString()
									.equalsIgnoreCase("null")
							&& !lPricePerfJsonObj.get(ApplicationConstants.TARGET_BOOKINGS).toString()
									.equalsIgnoreCase("[]")) {
						lHostBookingsTargetArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.TARGET_BOOKINGS).toString());
						if (lHostBookingsTargetArray != null) {
							if (lHostBookingsTargetArray.length() > 0) {
								lHostBookingsTgt = Utility.findSum(lHostBookingsTargetArray);
								lFilterModel.setHostBookings_tgt(Float.toString((float) lHostBookingsTgt));
							} else {
								lFilterModel.setHostBookings_tgt(Float.toString((float) lHostBookingsTgt));
							}
						}
					} else {
						lFilterModel.setHostBookings_tgt(Float.toString((float) lHostBookingsTgt));
					}

					JSONArray lOdDistanceArray = null;
					double lODDistance = 0;
					if (lPricePerfJsonObj.has(ApplicationConstants.OD_DISTANCE)
							&& lPricePerfJsonObj.get(ApplicationConstants.OD_DISTANCE) != null && !lPricePerfJsonObj
									.get(ApplicationConstants.OD_DISTANCE).toString().equalsIgnoreCase("null")) {
						lOdDistanceArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.OD_DISTANCE).toString());
						if (lOdDistanceArray != null) {
							if (lOdDistanceArray.length() > 0) {
								lODDistance = Utility.findSum(lOdDistanceArray);
								lFilterModel.setOdDistance(Float.toString((float) lODDistance));
							} else {
								lFilterModel.setOdDistance(Float.toString((float) lODDistance));
							}

						}
					}

					if (checkIfValueExist(ApplicationConstants.FORECAST_PAX, lPricePerfJsonObj))
						lHostPaxForecastArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.FORECAST_PAX).toString());
					if (lHostPaxForecastArray != null) {
						if (lHostPaxForecastArray.length() > 0) {
							forcastpax = Utility.findSum(lHostPaxForecastArray);
							lFilterModel.setPaxForcast((float) (forcastpax));
						}
					}
					if (lHostPaxForecastArray != null) {
						for (int m = 0; m < lHostPaxForecastArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lHostPaxForecastArray.get(m).toString()))
								lTotalforcastPax += Float.parseFloat(lHostPaxForecastArray.get(m).toString());
						}
					}

					if (lPricePerfJsonObj.has(ApplicationConstants.FORECAST_REVENUE)
							&& lPricePerfJsonObj.get(ApplicationConstants.FORECAST_REVENUE) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.FORECAST_REVENUE).toString()
									.equalsIgnoreCase("null")) {
						lHostRevenueForecastArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.FORECAST_REVENUE).toString());
						if (lHostRevenueForecastArray != null) {
							if (lHostRevenueForecastArray.length() > 0) {
								forcastrevenue = Utility.findSum(lHostRevenueForecastArray);
								lFilterModel.setRevenueForcast((float) (forcastrevenue));
							}
						}
					}
					if (lHostRevenueForecastArray != null) {
						for (int m = 0; m < lHostRevenueForecastArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lHostRevenueForecastArray.get(m).toString()))
								lTotalforcastRevenue += Float.parseFloat(lHostRevenueForecastArray.get(m).toString());
						}
					}
					double flown = 0;
					if (checkIfValueExist(ApplicationConstants.HOST_FLOWN_PAX, lPricePerfJsonObj)) {
						lHostPaxFlownArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX).toString());

						if (lHostPaxFlownArray != null) {
							if (lHostPaxFlownArray.length() > 0) {
								for (int j = 0; j < lHostPaxFlownArray.length(); j++) {
									if (lHostPaxFlownArray != null
											&& !lHostPaxFlownArray.get(j).toString().equalsIgnoreCase("null")
											&& !lHostPaxFlownArray.get(j).toString().equalsIgnoreCase("[]")) {

										flown = Utility.findSum(lHostPaxFlownArray);
										lFilterModel.setFlownpax((float) flown);
									}
								}
							}
						}
					}
					double flownrevenue = 0;
					if (lPricePerfJsonObj.has(ApplicationConstants.HOST_FLOWN_REVENUE)
							&& lPricePerfJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE) != null
							&& !lPricePerfJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE).toString()
									.equalsIgnoreCase("null")) {
						lHostFlownRevenueArray = new JSONArray(
								lPricePerfJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE).toString());
						if (lHostFlownRevenueArray != null) {
							if (lHostFlownRevenueArray.length() > 0) {
								for (int j = 0; j < lHostFlownRevenueArray.length(); j++) {
									if (lHostFlownRevenueArray != null
											&& !lHostFlownRevenueArray.get(j).toString().equalsIgnoreCase("null")
											&& !lHostFlownRevenueArray.get(j).toString().equalsIgnoreCase("[]")) {
										flownrevenue = Utility.findSum(lHostFlownRevenueArray);
										lFilterModel.setFlownrevenue((float) flownrevenue);
									}
								}
							}
						}
					}
					// months and days
					int lMonth = 0;
					int lDays = 0;
					if (!"null"
							.equalsIgnoreCase(lPricePerfJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString())) {
						lMonth = Utility
								.findMonth(lPricePerfJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString());
						lDays = Utility.findDay(lPricePerfJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString());
						lFilterModel.setMonths(lMonth);
						lFilterModel.setDays(lDays);
					}
					// Rating Carrier
					if (lPricePerfJsonObj.has("rating_carrier") && lPricePerfJsonObj.get("rating_carrier") != null
							&& !lPricePerfJsonObj.get("rating_carrier").toString().equalsIgnoreCase("null")
							&& !lPricePerfJsonObj.get("rating_carrier").toString().equalsIgnoreCase("[]")) {
						lRatingCarrierArray = new JSONArray(lPricePerfJsonObj.get("rating_carrier").toString());
					} else {
						lRatingCarrierArray = null;
					}

					if (lPricePerfJsonObj.has("rating") && lPricePerfJsonObj.get("rating") != null
							&& !lPricePerfJsonObj.get("rating").toString().equalsIgnoreCase("null")
							&& !lPricePerfJsonObj.get("rating").toString().equalsIgnoreCase("[]")) {
						lCompetitiorRatingArray = new JSONArray(lPricePerfJsonObj.get("rating").toString());
					} else {
						lCompetitiorRatingArray = null;
					}

					double marketSharePax = 0;
					double marketSharePax_lastYr = 0;

					if (lCarrierArray != null) {
						if (lCarrierArray.length() > 0) {
							for (int j = 0; j < lCarrierArray.length(); j++) {
								if (lCarrierArray.get(j).toString().equalsIgnoreCase("FZ")) {
									if (lHostMSPaxArray.length() > 0) {
										marketSharePax = Double.parseDouble(lHostMSPaxArray.get(j).toString());
										lFilterModel.setMarketSharePax(Double.toString(marketSharePax));
									}
									if (lHostMSPax_LastYr_Array.length() > 0) {
										marketSharePax_lastYr = Double
												.parseDouble(lHostMSPax_LastYr_Array.get(j).toString());
										lFilterModel.setMarketSharePax_lastyr(Double.toString(marketSharePax_lastYr));
									}

								}
							}
						}
					} else {
						lFilterModel.setMarketSharePax("0");
						lFilterModel.setMarketSharePax_lastyr("0");
					}

					double marketSize = 0;
					double marketSize_lastyr = 0;

					if (lCarrierArray != null) {
						if (lCarrierArray.length() > 0) {
							for (int j = 0; j < lCarrierArray.length(); j++) {
								if (lHostMSPaxArray.length() > 0) {
									marketSize += Double.parseDouble(lHostMSPaxArray.get(j).toString());
									lFilterModel.setMarketSize(Double.toString(marketSize));
								}
								if (lHostMSPax_LastYr_Array.length() > 0) {
									marketSize_lastyr += Double.parseDouble(lHostMSPax_LastYr_Array.get(j).toString());
									lFilterModel.setMarketSize_lastyr(Double.toString(marketSize_lastyr));
								}

							}
						}
					} else {
						lFilterModel.setMarketSize("0");
						lFilterModel.setMarketSize_lastyr("0");
					}

					float capacityFZ = 0;
					float capacityComp1 = 0;
					float capacityComp2 = 0;
					float capacityComp3 = 0;
					float capacityComp4 = 0;

					if (lCapacityCarrierArray != null) {
						if (lCapacityCarrierArray.length() > 0) {
							for (int j = 0; j < lCapacityCarrierArray.length(); j++) {
								if (lCapacityCarrierArray.length() > 0) {
									if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("FZ")) {
										capacityFZ += Float.parseFloat(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("EK")) {
										capacityComp1 += Float.parseFloat(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("EY")) {
										capacityComp2 += Float.parseFloat(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("G9")) {
										capacityComp3 = Float.parseFloat(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("QR")) {
										capacityComp4 = Float.parseFloat(lCapacityArray.get(j).toString());
									}
								}
							}
							lFilterModel.setCapacityFZ(capacityFZ);
							lFilterModel.setCapacityComp1(capacityComp1);
							lFilterModel.setCapacityComp2(capacityComp2);
							lFilterModel.setCapacityComp3(capacityComp3);
							lFilterModel.setCapacityComp4(capacityComp4);
						}
					} else {
						lFilterModel.setCapacityFZ(capacityFZ);
						lFilterModel.setCapacityComp1(capacityComp1);
						lFilterModel.setCapacityComp2(capacityComp2);
						lFilterModel.setCapacityComp3(capacityComp3);
						lFilterModel.setCapacityComp4(capacityComp4);
					}

					float compRatingFZ = 0;
					float compRatingComp1 = 0;
					float compRatingComp2 = 0;
					float compRatingComp3 = 0;
					float compRatingComp4 = 0;
					if (lCompetitiorRatingArray != null && lCompetitiorRatingArray.length() > 0) {
						for (int j = 0; j < lCompetitiorRatingArray.length(); j++) {
							if (lRatingCarrierArray.length() > 0) {
								if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("FZ")) {
									compRatingFZ = Float.parseFloat(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("EK")) {
									compRatingComp1 = Float.parseFloat(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("EY")) {
									compRatingComp2 = Float.parseFloat(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("G9")) {
									compRatingComp3 = Float.parseFloat(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("QR")) {
									compRatingComp4 = Float.parseFloat(lCompetitiorRatingArray.get(j).toString());
								}

							}
						}
						lFilterModel.setCompRatingFZ(compRatingFZ);
						lFilterModel.setCompRatingComp1(compRatingComp1);
						lFilterModel.setCompRatingComp2(compRatingComp2);
						lFilterModel.setCompRatingComp3(compRatingComp3);
						lFilterModel.setCompRatingComp4(compRatingComp4);
					} else {
						lFilterModel.setCompRatingFZ(compRatingFZ);
						lFilterModel.setCompRatingComp1(compRatingComp1);
						lFilterModel.setCompRatingComp2(compRatingComp2);
						lFilterModel.setCompRatingComp3(compRatingComp3);
						lFilterModel.setCompRatingComp4(compRatingComp4);
					}

					lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
							+ lFilterModel.getPos() + lFilterModel.getOrigin() + lFilterModel.getDestination()
							+ lFilterModel.getFareBasis() + lFilterModel.getCustomerSegment()
							+ lFilterModel.getChannel() + lFilterModel.getCompartment());

					lPricePerfModelList.add(lFilterModel);

				}

				Map<String, PricePerformanceModel> map = new HashMap<String, PricePerformanceModel>();
				PricePerformanceModel lPricePerf = null;
				if (lPricePerfModelList.size() > 0) {

					for (FilterModel lObj : lPricePerfModelList) {

						if (!map.containsKey(lObj.getFilterKey())) {
							lPricePerf = new PricePerformanceModel();
							lPricePerf.setDepartureDate(lObj.getDepartureDate());
							lPricePerf.setRegion(lObj.getRegion());
							lPricePerf.setCountry(lObj.getCountry());
							lPricePerf.setPos(lObj.getPos());
							lPricePerf.setOrigin(lObj.getOrigin());
							lPricePerf.setDestination(lObj.getDestination());
							/* lPricePerf.setOd(lObj.getOd()); */
							lPricePerf.setCompartment(lObj.getCompartment());
							lPricePerf.setPriceElasticitySignal(lObj.getPriceElasticitySignal());
							lPricePerf.setFareBasis(lObj.getFareBasis());
							lPricePerf.setRbd(lObj.getRbd());
							lPricePerf.setFootNote(lObj.getFootNote());
							lPricePerf.setRuleID(lObj.getRuleId());
							lPricePerf.setTotalFare(lObj.getTotalFare());
							lPricePerf.setBaseFare(Float.parseFloat(lObj.getBaseFare()));
							lPricePerf.setYqCharge(Float.parseFloat(lObj.getYqCharge()));
							lPricePerf.setTaxes(Float.parseFloat(lObj.getTaxes()));
							lPricePerf.setSurCharge(Float.parseFloat(lObj.getSurCharge()));

							lPricePerf.setChannel(lObj.getChannel());

							lPricePerf.setCurrency(lObj.getCurrency());
							lPricePerf.setMarketType(lObj.getMarketType());

							lPricePerf.setOdDistance(lObj.getOdDistance());
							lPricePerf.setCapacityFZ((lObj.getCapacityFZ()));
							lPricePerf.setCapacityComp1((lObj.getCapacityComp1()));
							lPricePerf.setCapacityComp2((lObj.getCapacityComp2()));
							lPricePerf.setCapacityComp3((lObj.getCapacityComp3()));
							lPricePerf.setCapacityComp4((lObj.getCapacityComp4()));

							lPricePerf.setCompRatingFZ((lObj.getCompRatingFZ()));
							lPricePerf.setCompRatingComp1((lObj.getCompRatingComp1()));
							lPricePerf.setCompRatingComp2((lObj.getCompRatingComp2()));
							lPricePerf.setCompRatingComp3((lObj.getCompRatingComp3()));
							lPricePerf.setCompRatingComp4((lObj.getCompRatingComp4()));

							// Sales Revenue
							float totalRevenueSales = Float.parseFloat(lObj.getSalesRevenue());
							lPricePerf.setTotalSalesRevenue(totalRevenueSales);

							// flown revenue
							float totalFlownRevenue = (lObj.getFlownrevenue());
							lPricePerf.setFlownRevenue(totalFlownRevenue);
							float totalflownrevenuelastyr = lObj.getFlownrevenue_lastyr();
							lPricePerf.setFlownrevenuelastyr(totalflownrevenuelastyr);
							float totalRevenueSales_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr());
							lPricePerf.setTotalSalesRevenue_lastyr(totalRevenueSales_lastyr);

							/*
							 * // host revenue forecast float revenueForecast =
							 * (lObj.getRevenueForecast());
							 * lPricePerf.setRevenueForecast(revenueForecast);
							 */

							/*
							 * // Host revenue_Target
							 * lPricePerf.setHostRevenue_tgt((lObj.
							 * getTargetRevenue()));
							 */

							// Pax
							float totalPax = Float.parseFloat(lObj.getHostPax());
							lPricePerf.setTotalPax(Math.round(totalPax));

							/*
							 * // paxForecast float paxForecast =
							 * Float.parseFloat(lObj.getHostPaxForecast());
							 * lPricePerf.setHostPaxForecast(Math.round(
							 * paxForecast));
							 */

							// paxFlown

							float paxFlown = (lObj.getFlownpax());
							lPricePerf.setFlownPax((paxFlown));
							float paxflownlastyr = lObj.getFlownpaxlastyr();
							lPricePerf.setFlownpaxlastyr(paxflownlastyr);

							int totalPax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
							lPricePerf.setTotalPax_lastyr(totalPax_lastyr);

							/*
							 * // Host Pax_Target
							 * lPricePerf.setHostPax_tgt((int)
							 * Float.parseFloat(lObj.getHostPax_tgt()));
							 */

							// Market Share Pax
							int marketShareHostPax = 0;

							if (lObj.getMarketSharePax() != null) {
								marketShareHostPax = Math.round(Float.parseFloat(lObj.getMarketSharePax()));
							} else {
								marketShareHostPax = 0;
							}
							lPricePerf.setMarketSharePax(marketShareHostPax);

							float marketShareHostPax_lastyr = 0;

							if (lObj.getMarketSharePax_lastyr() != null) {
								marketShareHostPax_lastyr = (Float.parseFloat(lObj.getMarketSharePax_lastyr()));
							} else {
								marketShareHostPax_lastyr = 0;
							}
							lPricePerf.setMarketSharePax_lastyr(marketShareHostPax_lastyr);

							// Market Size
							float marketSize = (Float.parseFloat(lObj.getMarketSize()));
							lPricePerf.setMarketSize(marketSize);

							int marketSize_lastyr = Math.round(Float.parseFloat(lObj.getMarketSize_lastyr()));
							lPricePerf.setMarketSize_lastyr(marketSize_lastyr);

							// Host Bookings
							lPricePerf.setHostBookings((int) Float.parseFloat(lObj.getHostBookings()));

							// Host Bookings_Lastyr
							lPricePerf.setHostBookings_lastyr((int) Float.parseFloat(lObj.getHostBookings_lastyr()));

							// Host Bookings_Target
							lPricePerf.setHostbookings_target(Float.parseFloat(lObj.getHostBookings_tgt()));
							// months and days
							int lMonth = 0;
							int lDays = 0;
							if (!"null".equalsIgnoreCase(lObj.getDepartureDate().toString())) {
								lMonth = Utility.findMonth(lObj.getDepartureDate());
								lDays = Utility.findDay(lObj.getDepartureDate());
								lPricePerf.setMonths(lMonth);
								lPricePerf.setDays(lDays);
							}
							map.put(lObj.getFilterKey(), lPricePerf);
						} else {
							for (String lKey : map.keySet()) {
								if (lObj.getFilterKey().equals(lKey)) {
									lPricePerf = map.get(lKey);
								}
							}
							// Sales Revenue
							float totalRevenueSales = Float.parseFloat(lObj.getSalesRevenue())
									+ lPricePerf.getTotalSalesRevenue();
							float totalSalesRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr())
									+ lPricePerf.getTotalSalesRevenue_lastyr();
							lPricePerf.setTotalSalesRevenue(totalRevenueSales);
							lPricePerf.setTotalSalesRevenue_lastyr(totalSalesRevenue_lastyr);

							// flown revenue
							float totalFlownRevenue = (lObj.getFlownrevenue()) + lPricePerf.getFlownRevenue();
							lPricePerf.setFlownRevenue(totalFlownRevenue);
							float totalflownrevenuelastyr = lObj.getFlownrevenue_lastyr()
									+ lPricePerf.getFlownrevenuelastyr();
							lPricePerf.setFlownrevenuelastyr(totalflownrevenuelastyr);

							// Revenue_Target
							float totalRevenue_tgt = Float.parseFloat(lObj.getTargetRevenue())
									+ lPricePerf.getHostRevenue_tgt();
							lPricePerf.setHostRevenue_tgt(totalRevenue_tgt);

							// Revenue Forecast
							float totalRevenueForecast = Float.parseFloat(lObj.getRevenueForecast())
									+ lPricePerf.getRevenueForecast();
							lPricePerf.setRevenueForecast(Math.round(totalRevenueForecast));

							// Pax
							float lTotalPax = Float.parseFloat(lObj.getHostPax()) + lPricePerf.getTotalPax();
							lPricePerf.setTotalPax(lTotalPax);

							// flown Pax
							float lTotalFlownPax = (lObj.getFlownpax()) + lPricePerf.getFlownPax();
							lPricePerf.setFlownPax(Math.round(lTotalFlownPax));
							float lflownpaxlastyr = lObj.getFlownpaxlastyr() + lPricePerf.getFlownpaxlastyr();
							lPricePerf.setFlownpaxlastyr(lflownpaxlastyr);

							// pax forecast
							float paxForecast = Float.parseFloat(lObj.getHostPaxForecast()) + lPricePerf.getFlownPax();
							lPricePerf.setHostPaxForecast(Math.round(paxForecast));

							// Pax Last year
							float totalPax_lastyr = Float.parseFloat(lObj.getHostPax_lastyr())
									+ lPricePerf.getTotalPax_lastyr();
							lPricePerf.setTotalPax_lastyr((int) totalPax_lastyr);

							// Host Pax_Target
							float totalPax_tgt = Float.parseFloat(lObj.getHostPax_tgt()) + lPricePerf.getHostPax_tgt();
							lPricePerf.setHostPax_tgt(totalPax_tgt);

							// Market Share

							int m1 = Utility.findMonth(lObj.getDepartureDate());
							int m2 = Utility.findMonth(lPricePerf.getDepartureDate());

							if (m1 != m2) {

								float marketShareHostPax = (Float.parseFloat(lObj.getMarketSharePax()))
										+ lPricePerf.getMarketSharePax();
								lPricePerf.setMarketSharePax(marketShareHostPax);

								float marketShareHostPax_lastyr = (Float.parseFloat(lObj.getMarketSharePax_lastyr()))
										+ lPricePerf.getMarketSharePax_lastyr();
								lPricePerf.setMarketSharePax_lastyr(marketShareHostPax_lastyr);

								float marketSize = (Float.parseFloat(lObj.getMarketSize()))
										+ lPricePerf.getMarketSize();
								lPricePerf.setMarketSize(marketSize);

								float marketSize_lastyr = (Float.parseFloat(lObj.getMarketSize_lastyr()))
										+ lPricePerf.getMarketSize_lastyr();
								lPricePerf.setMarketSize_lastyr(marketSize_lastyr);

							} else {
								float marketShareHostPax = lPricePerf.getMarketSharePax();
								lPricePerf.setMarketSharePax(marketShareHostPax);

								float marketShareHostPax_lastyr = lPricePerf.getMarketSharePax_lastyr();
								lPricePerf.setMarketSharePax_lastyr(marketShareHostPax_lastyr);

								float marketSize = lPricePerf.getMarketSize();
								lPricePerf.setMarketSize(marketSize);

								float marketSize_lastyr = lPricePerf.getMarketSize_lastyr();
								lPricePerf.setMarketSize_lastyr(marketSize_lastyr);
							}

							// Host Bookings_Target
							float totalBookings_tgt = Float.parseFloat(lObj.getHostBookings_tgt())
									+ lPricePerf.getHostbookings_target();
							lPricePerf.setHostbookings_target(totalBookings_tgt);

							// Host Bookings
							float totalBookings = Float.parseFloat(lObj.getHostBookings())
									+ lPricePerf.getHostBookings();
							lPricePerf.setHostBookings(totalBookings);

							// Host Bookings_Lastyr
							float totalBookings_lastyr = Float.parseFloat(lObj.getHostBookings_lastyr())
									+ lPricePerf.getHostBookings_lastyr();
							lPricePerf.setHostBookings_lastyr(totalBookings_lastyr);

							// Market share
							double totalMarketSharePax = Double
									.parseDouble(Float.toString(lPricePerf.getMarketSharePax()))
									+ lPricePerf.getTotalMarketSharePax();
							lPricePerf.setTotalMarketSharePax(totalMarketSharePax);

							// Market Share Last Year
							double totalMarketSharePax_lastYr = Double
									.parseDouble(Float.toString(lPricePerf.getMarketSharePax_lastyr()))
									+ lPricePerf.getTotalMarketSharePax_lastyr();
							lPricePerf.setTotalMarketSharePax_lastyr(totalMarketSharePax_lastYr);
							// months and days
							int lMonth = 0;
							int lDays = 0;
							if (!"null".equalsIgnoreCase(lObj.getDepartureDate().toString())) {
								lMonth = Utility.findMonth(lObj.getDepartureDate());
								lDays = Utility.findDay(lObj.getDepartureDate());
								lPricePerf.setMonths(lMonth);
								lPricePerf.setDays(lDays);
							}
						}

					}
				}

				PricePerformanceResponse lResponse = null;
				for (String key : map.keySet()) {
					lResponse = new PricePerformanceResponse();
					// Region
					lResponse.setRegion(map.get(key).getRegion());
					// Country
					lResponse.setCountry(map.get(key).getCountry());
					// Pos
					lResponse.setPos(map.get(key).getPos());
					// Origin
					lResponse.setOrigin(map.get(key).getOrigin());
					// Destination
					lResponse.setDestination(map.get(key).getDestination());
					// Compartment
					lResponse.setCompartment(map.get(key).getCompartment());
					// RBD
					lResponse.setRbd(map.get(key).getRbd());
					// Fare Basis
					lResponse.setFareBasisCode(map.get(key).getFareBasis());
					// FootNote
					if (!map.get(key).getFootNote().isEmpty()) {
						lResponse.setFootNote(map.get(key).getFootNote());
					} else {
						lResponse.setFootNote("-");
					}
					// RuleID
					lResponse.setRuleID(map.get(key).getRuleID());
					// Currency
					lResponse.setCurrency(map.get(key).getCurrency());
					// TotalFare
					lResponse.setTotalFare(map.get(key).getTotalFare());
					// Base Fare
					lResponse.setBaseFare(map.get(key).getBaseFare());
					// YQ/Q
					lResponse.setYqCharge(Float.toString(map.get(key).getYqCharge()));
					// Tax
					lResponse.setTaxes(Float.toString(map.get(key).getTaxes()));
					// Surcharge
					lResponse.setSurCharge(Float.toString(map.get(key).getSurCharge()));
					// PE Signal
					if (map.get(key).getPriceElasticitySignal() != null) {
						lResponse.setPriceElasticitySignal(map.get(key).getPriceElasticitySignal());
					} else {
						lResponse.setPriceElasticitySignal("-");
					}
					float pForcastPax = 0;
					long totaldaysFromdate = 0;
					Date date1 = null;
					Date date2 = null;
					if (!mRequestModel.getFromDate().isEmpty() && mRequestModel.getFromDate() != null
							&& !mRequestModel.getToDate().isEmpty() && mRequestModel.getToDate() != null) {
						date1 = Utility.convertStringToDateFromat(mRequestModel.getFromDate());
						date2 = Utility.convertStringToDateFromat(mRequestModel.getToDate());
					} else {
						date1 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
						date2 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
					}
					// int result = str2.compareTo(str1);
					long diff = Utility.getDifferenceDays(date1, date2);
					long result = diff + 1;
					if (result == 0) {
						totaldaysFromdate = result + 1;
					}
					// no. of days
					int noOfDays = Utility.numberOfDaysInMonth(
							Utility.findMonth(map.get(key).getDepartureDate().toString()) - 1,
							Utility.findYear(map.get(key).getDepartureDate().toString()));
					float targetProratedPax = ((lTotaltargetPax / (float) noOfDays) * result);
					float proratedForcastPax = (((float) forcastpax / (float) noOfDays) * result);
					float targetProratedRevenue = ((lTotalTargetRevenue / noOfDays) * result);
					float proratedForcastRevenue = (((float) forcastrevenue / (float) noOfDays) * result);

					// months and days
					int lMonth = 0;
					int lDays = 0;
					if (!"null".equalsIgnoreCase(map.get(key).getDepartureDate().toString())) {
						lMonth = Utility.findMonth(map.get(key).getDepartureDate());
						lDays = Utility.findDay(map.get(key).getDepartureDate());
						lResponse.setMonths(lMonth);
						lResponse.setDays(lDays);
					}
					if (lResponse.getMonths() == map.get(key).getMonths()) {
						float lTotaltargetProratedPax = targetProratedPax;
						float lTotalproratedForcastPax = proratedForcastPax;
						lResponse.setTotalforcastpax(lTotalproratedForcastPax);
						lResponse.setTotaltargetproratedpax(lTotaltargetProratedPax);
						float lTotaltargetProratedRevenue = targetProratedRevenue;
						float lTotalproratedForcastRevenue = proratedForcastRevenue;
						lResponse.setTotalforcastrevenue(lTotalproratedForcastRevenue);
						lResponse.setTotaltargetproratedrevnue(lTotaltargetProratedRevenue);

					} else {
						float lTotaltargetProratedPax = targetProratedPax + lTotaltargetPax;
						float lTotalproratedForcastPax = proratedForcastPax + lTotalforcastPax;
						lResponse.setTotalforcastpax(lTotalproratedForcastPax);
						lResponse.setTotaltargetproratedpax(lTotaltargetProratedPax);
						float lTotaltargetProratedRevenue = targetProratedRevenue + lTotalTargetRevenue;
						float lTotalproratedForcastRevenue = proratedForcastRevenue + lTotalforcastRevenue;
						lResponse.setTotalforcastrevenue(lTotalproratedForcastRevenue);
						lResponse.setTotaltargetproratedrevnue(lTotaltargetProratedRevenue);

					}
					// pax vtgt
					float pForcastRevenue = 0;
					if (result == 0) {
						pForcastPax = 0;
						proratedForcastRevenue = 0;
					} else {
						pForcastPax = lResponse.getTotalforcastpax();
						pForcastRevenue = lResponse.getTotalforcastrevenue();
					}
					lTotalsForcastRevenue += pForcastRevenue;
					lTotalsForcastPax += pForcastPax;
					float paxvtgt = 0;
					float pflownpax = map.get(key).getFlownPax();
					lTotalflownpax += pflownpax;
					lResponse.setFlownpax(pflownpax);
					if (targetProratedPax != 0) {
						paxvtgt = CalculationUtil.calculateVTGTRemodelled(pflownpax, pForcastPax, targetProratedPax);
					}
					lResponse.setPaxVTGT((paxvtgt));
					// revenue vtgt
					float revenuevtgt = 0;
					float pflownrevenue = map.get(key).getFlownRevenue();
					ltotalflownrevenue += pflownrevenue;
					lResponse.setFlownrevenue(pflownrevenue);
					if (targetProratedRevenue != 0) {
						revenuevtgt = CalculationUtil.calculateVTGTRemodelled(pflownrevenue, pForcastRevenue,
								targetProratedRevenue);
						lResponse.setRevenueVTGT((revenuevtgt));
					}

					// Channel
					lResponse.setChannel(map.get(key).getChannel());

					// Capacity
					lResponse.setCapacityFZ(Float.toString(map.get(key).getCapacityFZ()));
					lResponse.setCapacityComp1(Float.toString(map.get(key).getCapacityComp1()));
					lResponse.setCapacityComp2(Float.toString(map.get(key).getCapacityComp2()));

					// CompRating
					lResponse.setCompRatingFZ(Float.toString(map.get(key).getCompRatingFZ()));
					lResponse.setCompRatingComp1(Float.toString(map.get(key).getCompRatingComp1()));
					lResponse.setCompRatingComp2(Float.toString(map.get(key).getCompRatingComp2()));

					ArrayList<Integer> lCapacityList = new ArrayList<Integer>();
					int lCapacityFZ = 1;
					// if (!map.get(key).getCapacityFZ().equalsIgnoreCase("-"))
					// {
					lCapacityFZ = (int) (map.get(key).getCapacityFZ());
					lCapacityList.add(lCapacityFZ);
					// }

					// if
					// (!map.get(key).getCapacityComp1().equalsIgnoreCase("-"))
					// {
					lCapacityList.add((int) (map.get(key).getCapacityComp1()));
					// }
					// if
					// (!map.get(key).getCapacityComp2().equalsIgnoreCase("-"))
					// {
					lCapacityList.add((int) (map.get(key).getCapacityComp2()));
					// }
					// if
					// (!map.get(key).getCapacityComp3().equalsIgnoreCase("-"))
					// {
					lCapacityList.add((int) (map.get(key).getCapacityComp3()));
					// }
					// if
					// (!map.get(key).getCapacityComp4().equalsIgnoreCase("-"))
					// {
					lCapacityList.add((int) (map.get(key).getCapacityComp4()));
					// }

					ArrayList<Float> lCompRatingList = new ArrayList<Float>();
					// if
					// (!map.get(key).getCompRatingFZ().equalsIgnoreCase("-")) {
					lCompRatingList.add((map.get(key).getCompRatingFZ()));
					/*
					 * } else { lCompRatingList.add((float) 1); }
					 */
					// if
					// (!map.get(key).getCompRatingComp1().equalsIgnoreCase("-"))
					// {
					lCompRatingList.add((map.get(key).getCompRatingComp1()));
					/*
					 * } else { lCompRatingList.add((float) 1); }
					 */
					// if
					// (!map.get(key).getCompRatingComp2().equalsIgnoreCase("-"))
					// {
					lCompRatingList.add((map.get(key).getCompRatingComp2()));
					/*
					 * } else { lCompRatingList.add((float) 1); }
					 */
					// if
					// (!map.get(key).getCompRatingComp3().equalsIgnoreCase("-"))
					// {
					lCompRatingList.add((map.get(key).getCompRatingComp3()));
					/*
					 * } else { lCompRatingList.add((float) 1); }
					 */
					// if
					// (!map.get(key).getCompRatingComp4().equalsIgnoreCase("-"))
					// {
					lCompRatingList.add((map.get(key).getCompRatingComp4()));
					/*
					 * } else { lCompRatingList.add((float) 1); }
					 */

					// Sales Revenue YTD
					float lTotalSalesRevenue = map.get(key).getTotalSalesRevenue();
					lSalesRevenueYTD = lTotalSalesRevenue;
					lResponse.setSalesRevenueYTD(lSalesRevenueYTD);

					String host = "FZ";
					float hostcapacity = 0;

					if (carrierPaxMap.containsKey(host)) {
						hostcapacity = (carrierPaxMap.get(host));
						lResponse.setHostcapacity(hostcapacity);
					}
					float hostcapacitylastyr = 0;
					if (capacitylastyrMap.size() > 0) {
						if (carrierPaxMap.containsKey(host)) {
							hostcapacitylastyr = (capacitylastyrMap.get(host));
							lResponse.setHostcapacitylastyr(hostcapacitylastyr);
						}
					}

					float flownpax = map.get(key).getFlownPax();
					lFlownpax = flownpax;
					lResponse.setTotalflownpax(flownpax);
					float flownrevenue = map.get(key).getFlownRevenue();
					lFlownRevenue = flownrevenue;
					lResponse.setTotalflownrevenue(flownrevenue);
					float lFlownpaxlastyr = map.get(key).getFlownpaxlastyr();
					lFlownPaxlastyr = lFlownpaxlastyr;
					lResponse.setTotalflownpaxlastyr(lFlownpaxlastyr);
					float lflownrevenuelastyr = map.get(key).getFlownrevenuelastyr();
					lFlownrevenuelastyr = lflownrevenuelastyr;
					lResponse.setTotalflownrevenuelastyr(lflownrevenuelastyr);
					// Sales Revenue VLYR
					float totalSalesRevenue_lastyr = map.get(key).getTotalSalesRevenue_lastyr();
					if (lflownrevenuelastyr != 0) {
						lRevenueVLYR = CalculationUtil.calculateVLYR(flownrevenue, lflownrevenuelastyr, hostcapacity,
								hostcapacitylastyr);
						lResponse.setRevenueVLYR(lRevenueVLYR);
					} else {
						lResponse.setRevenueVLYR(lRevenueVLYR);
					}
					// grand totalfor vlyr
					ltotalflownpax += lFlownpax;
					ltotalflowntrevenue += lFlownRevenue;
					ltotalflownpaxlastyr += lFlownPaxlastyr;
					ltotalflownrevenuelastyr += lFlownrevenuelastyr;
					ltotalhostcapcity += hostcapacity;
					ltotlahostcapacitylastyr += hostcapacitylastyr;

					// Sales Revenue VTGT
					float targetRevenue = map.get(key).getHostRevenue_tgt();
					float revenueForecast = map.get(key).getRevenueForecast();
					float flownRevenue = map.get(key).getFlownRevenue();

					/*
					 * if (targetRevenue > 0) { lRevenueVTGT =
					 * CalculationUtil.calculateVTGTRemodelled(flownRevenue,
					 * revenueForecast, targetRevenue);
					 * lResponse.setRevenueVTGT(lRevenueVTGT); } else {
					 * lResponse.setRevenueVTGT(lRevenueVTGT); }
					 */

					// Grand Total Sales Revenue
					totalRevenueYTD += lSalesRevenueYTD;
					totalRevenue_LY += totalSalesRevenue_lastyr;

					// Currency
					lResponse.setCurrency(map.get(key).getCurrency());

					// Setting Pax YTD
					float totalPax = map.get(key).getTotalPax();
					lPaxYTD = totalPax;
					lResponse.setPaxYTD((int) lPaxYTD);

					// Setting Pax VLYR
					float totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					if (lFlownpaxlastyr > 0) {
						lPaxVLYR = CalculationUtil.calculateVLYR(flownpax, lFlownpaxlastyr, hostcapacity,
								hostcapacitylastyr);
					} else {
						lPaxVLYR = 0;
					}
					lResponse.setPaxVLYR(Math.round(lPaxVLYR));

					float targetPax = map.get(key).getHostPax_tgt();
					float flownPax = map.get(key).getFlownPax();
					float paxForecast = map.get(key).getHostPaxForecast();
					/*
					 * if (targetPax != 0) { lPaxVTGT =
					 * CalculationUtil.calculateVTGTRemodelled(flownPax,
					 * paxForecast, targetPax); lResponse.setPaxVTGT(lPaxVTGT);
					 * } else { lResponse.setPaxVTGT(lPaxVTGT); }
					 */

					// Grand Total Pax
					totalPaxYTD += lPaxYTD;
					totalPax_LY += totalPax_lastyr;

					// Yield Calculations
					float lTotalPax = Float.parseFloat(Float.toString(totalPax));
					float lOD_Distance = Float.parseFloat(map.get(key).getOdDistance());
					float lYield = CalculationUtil.calculateYield(lTotalSalesRevenue, lTotalPax, lOD_Distance);
					lResponse.setYieldYTD(CalculationUtil.roundToTwoDecimal(lYield, 2));

					float lRPKM_lastyr = Float.parseFloat(Float.toString(map.get(key).getTotalPax_lastyr()))
							* lOD_Distance;
					float yield_lastyr = 0;
					if (lRPKM_lastyr > 0) {
						yield_lastyr = map.get(key).getTotalSalesRevenue_lastyr() / lRPKM_lastyr;
					} else {

					}
					yield_lastyr = map.get(key).getTotalSalesRevenue_lastyr() / lRPKM_lastyr;

					String lYieldVLYR = "";
					if (yield_lastyr > 0) {
						lYieldVLYR = Float.toString(CalculationUtil.calculateVLYR(lYield, yield_lastyr));
					} else {
						lYieldVLYR = "0.0";
					}
					lResponse.setYieldVLYR(Float.parseFloat(lYieldVLYR));

					// Market Type
					lResponse.setMarketType(map.get(key).getMarketType());

					// Market size
					float marketsize = map.get(key).getMarketSize();
					lResponse.setMarketSize(map.get(key).getMarketSize());

					// Setting MarketShare YTD
					float marketsharepax = map.get(key).getMarketSharePax();
					if (map.get(key).getMarketSize() > 0) {
						marketShareYTD = ((map.get(key).getMarketSharePax() * 100) / map.get(key).getMarketSize());
						lResponse.setMarketShareYTD(marketShareYTD);
					} else {
						lResponse.setMarketShareYTD(marketShareYTD);
					}

					float marketsharepax_lastyr = map.get(key).getMarketSharePax_lastyr();
					float marketsizelastyr = map.get(key).getMarketSize_lastyr();
					if (map.get(key).getMarketSize_lastyr() > 0) {
						marketShare_lastyr = ((map.get(key).getMarketSharePax_lastyr() * 100)
								/ map.get(key).getMarketSize_lastyr());
					} else {
						marketShare_lastyr = 0;
					}

					// Market Share VLYR

					if (map.get(key).getMarketSharePax_lastyr() > 0) {
						marketShareVLYR = CalculationUtil.calculateVLYR((float) marketShareYTD, marketShare_lastyr);
						lResponse.setMarketShareVLYR(marketShareVLYR);
					} else {
						lResponse.setMarketShareVLYR(marketShareVLYR);
					}

					// FMS
					float lCompRatingFZ = 1;
					// if
					// (!map.get(key).getCompRatingFZ().equalsIgnoreCase("-")) {
					lCompRatingFZ = (map.get(key).getCompRatingFZ());
					// }

					float lFMS = CalculationUtil.calculateFMS(lCapacityFZ, lCompRatingFZ, lCapacityList,
							lCompRatingList);
					if (lFMS != 0) {
						lResponse.setFms(Float.toString(lFMS));
					} else {
						lResponse.setFms("-");
					}

					// Market VTGT
					float lMarketShareVTGT = 0;
					lMarketShareVTGT = ((marketShareYTD - lFMS) / lFMS) * 100;
					lResponse.setMarketShareVTGT(Float.toString(lMarketShareVTGT));

					// Delta FMS
					float lDeltaFMS = lFMS - marketShareYTD;
					lResponse.setDeltaFMS(Float.toString(lDeltaFMS));

					// total of marketshare vtgt and revenue vtgt
					ltotalrevenuevtgt += revenuevtgt;
					ltotalmarketsharevtgt += lMarketShareVTGT;
					ltotalodDistance += lOD_Distance;
					ltotalmarketsize += marketsize;
					ltotalmarketsharepax += marketsharepax;
					ltotalmarketsizelastyr += marketsizelastyr;
					ltotalmarketsharepaxlastyr += marketsharepax_lastyr;
					ltotalFms += lFMS;

					// Price Performance Calculation
					float lPPScore = CalculationUtil.calculatePricePerformance(revenuevtgt, lMarketShareVTGT);
					lResponse.setPricePerformanceScore(Float.toString(lPPScore));

					// Host bookings
					float hostbooking = map.get(key).getHostBookings();
					float hostbookinglastyr = map.get(key).getHostBookings_lastyr();
					float hostbookingtarget = map.get(key).getHostbookings_target();
					ltotalhostbooking += hostbooking;
					ltotalhostbookinglastyr += hostbookinglastyr;
					ltotalhostbookingtarget += hostbookingtarget;

					lResponse.setBookings((int) map.get(key).getHostBookings());
					float lBookingsVLYR = 0;
					if (map.get(key).getHostBookings_lastyr() > 0) {
						lBookingsVLYR = CalculationUtil.calculateVLYR(map.get(key).getHostBookings(),
								map.get(key).getHostBookings_lastyr());
					}
					lResponse.setBookingsVLYR((int) lBookingsVLYR);

					float lBookingsVTGT = 0;
					if (map.get(key).getHostbookings_target() > 0) {
						lBookingsVTGT = CalculationUtil.calculateVTGT(map.get(key).getHostBookings(),
								map.get(key).getHostbookings_target());
					}
					lResponse.setBookingsVTGT((int) lBookingsVTGT);

					// if (!map.get(key).getCapacityFZ().equalsIgnoreCase("-"))
					// {
					lCapacityFZ = (int) (map.get(key).getCapacityFZ());
					ltotalcapcityFZ += lCapacityFZ;
					/*
					 * } else { lCapacityFZ = 1; }
					 */
					float availability = lCapacityFZ - map.get(key).getHostBookings();
					lResponse.setAvailability(Float.toString(availability));

					lPricePerformanceList.add(lResponse);
				}

				// Calculation of TotalRevenueVLYR,TotalPaxVLYR

				if (totalRevenue_LY > 0) {
					totalRevenue_VLYR = CalculationUtil.calculateVLYR(ltotalflowntrevenue, ltotalflownrevenuelastyr,
							ltotalhostcapcity, ltotlahostcapacitylastyr);
				} else {
					totalRevenue_VLYR = 0;
				}

				if (totalPax_LY > 0) {
					totalPax_VLYR = CalculationUtil.calculateVLYR(ltotalflownpax, ltotalflownpaxlastyr,
							ltotalhostcapcity, ltotlahostcapacitylastyr);
				} else {
					totalPax_VLYR = 0;
				}

				float yield = CalculationUtil.calculateYield(totalRevenueYTD, totalPaxYTD, ltotalodDistance);
				float yieldlastyr = CalculationUtil.calculateYield(totalRevenue_LY, totalPax_LY, ltotalodDistance);
				float yieldvlyr = CalculationUtil.calculateVLYR(yield, yieldlastyr);
				float marketshareytd = ((ltotalmarketsharepax * 100) / ltotalmarketsize);
				float marketsharelastyr = ((ltotalmarketsharepaxlastyr * 100) / ltotalmarketsizelastyr);
				float marketsharevlyr = CalculationUtil.calculateVLYR(marketshareytd, marketsharelastyr);
				float marketsharevtgt = CalculationUtil.calculateVTGT(marketshareytd, ltotalFms);
				float totaldeltafms = ltotalFms - marketshareytd;
				float totalhostbookingvlyr = CalculationUtil.calculateVLYR(ltotalhostbooking, ltotalhostbookinglastyr);
				float totalhostbookingvtgt = CalculationUtil.calculateVTGT(ltotalhostbooking, ltotalhostbookingtarget);
				float totalavailability = ltotalcapcityFZ - ltotalhostbooking;
				// float lPPScore =
				// CalculationUtil.calculatePricePerformance(ltotalrevenuevtgt,
				// marketsharevtgt);

				// Totals
				PriceHistoryTotalsResponse lTotals = new PriceHistoryTotalsResponse();
				lTotals.setTotalPaxYTD(Float.toString(totalPaxYTD));
				lTotals.setTotalPaxVLYR(Float.toString(totalPax_VLYR));
				lTotals.setTotalRevenueYTD(Float.toString(totalRevenueYTD));
				lTotals.setTotalRevenueVLYR(Float.toString(totalRevenue_VLYR));
				// lTotals.setTotalpriceperformanceScore(Float.toString(lPPScore));
				lTotals.setTotalYield(Float.toString(yield));
				lTotals.setTotlaYieldVLYR(Float.toString(yieldvlyr));
				lTotals.setTotalMarketSize(Float.toString(ltotalmarketsize));
				lTotals.setTotalmarketshareytd(Float.toString(marketshareytd));
				lTotals.setTotalmarketsharevlyr(Float.toString(marketsharevlyr));
				lTotals.setTotalmarketsharevtgt(Float.toString(marketsharevtgt));
				lTotals.setTotaldeltafms(Float.toString(totaldeltafms));
				lTotals.setTotalFms(Float.toString(ltotalFms));
				lTotals.setTotalhostbooking(Float.toString(ltotalhostbooking));
				lTotals.setTotalhostbookingvlyr(Float.toString(totalhostbookingvlyr));
				lTotals.setTotalhostbookingvtgt(Float.toString(totalhostbookingvtgt));
				lTotals.setTotalavailability(Float.toString(totalavailability));

				lTotalsList.add(lTotals);

			}
			responsePricePerfMap.put("PricePerformanceTotals", lTotalsList);
			responsePricePerfMap.put("PricePerformance", lPricePerformanceList);

		} catch (Exception e) {
			logger.error("getPricePerformance-Exception", e);
		}

		return responsePricePerfMap;

	}

	@Override
	public Map<String, Object> getPriceCharac_MajorAgencies(RequestModel mRequestModel) {

		float lRevenueYTD = 0;
		float lRevenueVLYR = 0;
		float lRevenueVTGT = 0;

		JSONArray lFlownRevenueArray = null;
		JSONArray lFlown_1RevenueArray = null;

		float totalSalesRevenueYTD = 0;
		float totalSalesRevenue_LY = 0;

		int totalPaxYTD = 0;
		int totalPax_LY = 0;

		List<FilterModel> lMajorAgenciesModelList = new ArrayList<FilterModel>();
		List<MajorAgenciesResponse> lMajorAgenciesList = new ArrayList<MajorAgenciesResponse>();
		List<MajorAgenciesTotalsResponse> lTotalsList = new ArrayList<MajorAgenciesTotalsResponse>();
		List<PriceElasticityModel> lAirchargePaxList = new ArrayList<PriceElasticityModel>();

		FilterModel lFilterModel = new FilterModel();

		Map<String, Object> responseMajorAgenciesMap = new HashMap<String, Object>();

		List<DBObject> lPriceChar_MajorAgenciesObjList = priceBiometricDao.getPriceCharac_MajorAgencies(mRequestModel);
		JSONArray lPriceChar_MajorAgenciesData = new JSONArray(lPriceChar_MajorAgenciesObjList);
		try {
			if (lPriceChar_MajorAgenciesData != null) {
				for (int i = 0; i < lPriceChar_MajorAgenciesData.length(); i++) {
					JSONObject lMajorAgenciesJsonObj = lPriceChar_MajorAgenciesData.getJSONObject(i);
					lFilterModel = new FilterModel();

					if (lMajorAgenciesJsonObj.has(ApplicationConstants.REGION)
							&& lMajorAgenciesJsonObj.get(ApplicationConstants.REGION) != null && !lMajorAgenciesJsonObj
									.get(ApplicationConstants.REGION).toString().equalsIgnoreCase("null")) {
						lFilterModel.setRegion(lMajorAgenciesJsonObj.get(ApplicationConstants.REGION).toString());
					}
					if (lMajorAgenciesJsonObj.has(ApplicationConstants.COUNTRY)
							&& lMajorAgenciesJsonObj.get(ApplicationConstants.COUNTRY) != null && !lMajorAgenciesJsonObj
									.get(ApplicationConstants.COUNTRY).toString().equalsIgnoreCase("null")) {
						lFilterModel.setCountry(lMajorAgenciesJsonObj.get(ApplicationConstants.COUNTRY).toString());
					}
					if (lMajorAgenciesJsonObj.has(ApplicationConstants.POS)
							&& lMajorAgenciesJsonObj.get(ApplicationConstants.POS) != null && !lMajorAgenciesJsonObj
									.get(ApplicationConstants.POS).toString().equalsIgnoreCase("null")) {
						lFilterModel.setPos(lMajorAgenciesJsonObj.get(ApplicationConstants.POS).toString());
					}
					if (lMajorAgenciesJsonObj.has(ApplicationConstants.DESTINATION)
							&& lMajorAgenciesJsonObj.get(ApplicationConstants.DESTINATION) != null
							&& !lMajorAgenciesJsonObj.get(ApplicationConstants.DESTINATION).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel
								.setDestination(lMajorAgenciesJsonObj.get(ApplicationConstants.DESTINATION).toString());
					}
					if (lMajorAgenciesJsonObj.has(ApplicationConstants.ORIGIN)
							&& lMajorAgenciesJsonObj.get(ApplicationConstants.ORIGIN) != null && !lMajorAgenciesJsonObj
									.get(ApplicationConstants.ORIGIN).toString().equalsIgnoreCase("null")) {
						lFilterModel.setOrigin(lMajorAgenciesJsonObj.get(ApplicationConstants.ORIGIN).toString());
					}

					lFilterModel.setOd(lFilterModel.getOrigin() + lFilterModel.getDestination());
					if (lMajorAgenciesJsonObj.has(ApplicationConstants.COMPARTMENT)
							&& lMajorAgenciesJsonObj.get(ApplicationConstants.COMPARTMENT) != null
							&& !lMajorAgenciesJsonObj.get(ApplicationConstants.COMPARTMENT).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel
								.setCompartment(lMajorAgenciesJsonObj.get(ApplicationConstants.COMPARTMENT).toString());
					}
					if (lMajorAgenciesJsonObj.has("price elasticity")
							&& lMajorAgenciesJsonObj.get("price elasticity") != null
							&& !lMajorAgenciesJsonObj.get("price elasticity").toString().equalsIgnoreCase("null")) {
						lFilterModel.setPriceElasticitySignal(lMajorAgenciesJsonObj.get("price elasticity").toString());
					}

					if (lMajorAgenciesJsonObj.has("Agency_name") && lMajorAgenciesJsonObj.get("Agency_name") != null
							&& !lMajorAgenciesJsonObj.get("Agency_name").toString().equalsIgnoreCase("null")) {
						lFilterModel.setAgencyName(lMajorAgenciesJsonObj.get("Agency_name").toString().trim());
					}

					if (lMajorAgenciesJsonObj.has("Min_price") && lMajorAgenciesJsonObj.get("Min_price") != null
							&& !lMajorAgenciesJsonObj.get("Min_price").toString().equalsIgnoreCase("null")) {
						lFilterModel.setMinPrice(lMajorAgenciesJsonObj.get("Min_price").toString());
					}

					if (lMajorAgenciesJsonObj.has("Max_price") && lMajorAgenciesJsonObj.get("Max_price") != null
							&& !lMajorAgenciesJsonObj.get("Max_price").toString().equalsIgnoreCase("null")) {
						lFilterModel.setMaxPrice(lMajorAgenciesJsonObj.get("Max_price").toString());
					}

					if (lMajorAgenciesJsonObj.has("Host_revenue") && lMajorAgenciesJsonObj.get("Host_revenue") != null
							&& !lMajorAgenciesJsonObj.get("Host_revenue").toString().equalsIgnoreCase("null")) {
						lFilterModel.setSalesRevenue(lMajorAgenciesJsonObj.get("Host_revenue").toString());
					} else {
						lFilterModel.setSalesRevenue("0");
					}

					if (lMajorAgenciesJsonObj.has("Host_revenue_last_year")
							&& lMajorAgenciesJsonObj.get("Host_revenue_last_year") != null && !lMajorAgenciesJsonObj
									.get("Host_revenue_last_year").toString().equalsIgnoreCase("null")) {
						lFilterModel
								.setSalesRevenue_lastyr(lMajorAgenciesJsonObj.get("Host_revenue_last_year").toString());
					} else {
						lFilterModel.setSalesRevenue_lastyr("0");
					}

					double flownRevenue = 0;
					if (lMajorAgenciesJsonObj.has(ApplicationConstants.HOST_FLOWN_REVENUE)
							&& lMajorAgenciesJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE) != null
							&& !lMajorAgenciesJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE).toString()
									.equalsIgnoreCase("null")) {
						lFlownRevenueArray = new JSONArray(
								lMajorAgenciesJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE).toString());
						if (lFlownRevenueArray != null) {
							if (lFlownRevenueArray.length() > 0) {
								flownRevenue = Utility.findSum(lFlownRevenueArray);
								lFilterModel.setFlownRevenue(Float.toString((float) flownRevenue));
							}
						}
					} else {
						lFilterModel.setFlownRevenue(Float.toString((float) flownRevenue));
					}

					double flownRevenue_lastyr = 0;
					if (lMajorAgenciesJsonObj.has(ApplicationConstants.HOST_FLOWN_REVENUE_LY)
							&& lMajorAgenciesJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE_LY) != null
							&& !lMajorAgenciesJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE_LY).toString()
									.equalsIgnoreCase("null")) {
						lFlown_1RevenueArray = new JSONArray(
								lMajorAgenciesJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE_LY).toString());
						if (lFlown_1RevenueArray != null) {
							if (lFlown_1RevenueArray.length() > 0) {
								flownRevenue_lastyr = Utility.findSum(lFlown_1RevenueArray);
								lFilterModel.setFlownRevenue_lastyr(Float.toString((float) flownRevenue_lastyr));
							}
						}
					} else {
						lFilterModel.setFlownRevenue_lastyr(Float.toString((float) flownRevenue_lastyr));
					}

					if (lMajorAgenciesJsonObj.has("AIR_CHARGE") && lMajorAgenciesJsonObj.get("AIR_CHARGE") != null
							&& !lMajorAgenciesJsonObj.get("AIR_CHARGE").toString().equalsIgnoreCase("null")) {
						lFilterModel.setAirCharge(lMajorAgenciesJsonObj.get("AIR_CHARGE").toString());
					}

					if (lMajorAgenciesJsonObj.has("Host_pax") && lMajorAgenciesJsonObj.get("Host_pax") != null
							&& !lMajorAgenciesJsonObj.get("Host_pax").toString().equalsIgnoreCase("null")) {
						lFilterModel.setHostPax(lMajorAgenciesJsonObj.get("Host_pax").toString());
					} else {
						lFilterModel.setHostPax("0");
					}

					if (lMajorAgenciesJsonObj.has(ApplicationConstants.HOST_SALES_PAX_LY)
							&& lMajorAgenciesJsonObj.get(ApplicationConstants.HOST_SALES_PAX_LY) != null
							&& !lMajorAgenciesJsonObj.get(ApplicationConstants.HOST_SALES_PAX_LY).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setHostPax_lastyr(
								lMajorAgenciesJsonObj.get(ApplicationConstants.HOST_SALES_PAX_LY).toString());
					} else {
						lFilterModel.setHostPax_lastyr("0");
					}

					if (lMajorAgenciesJsonObj.has("Proximity_indicator")
							&& lMajorAgenciesJsonObj.get("Proximity_indicator") != null
							&& !lMajorAgenciesJsonObj.get("Proximity_indicator").toString().equalsIgnoreCase("null")) {
						lFilterModel.setProximityIndicator(lMajorAgenciesJsonObj.get("Proximity_indicator").toString());
					}

					StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lFilterModel);
					String keyBuilder = lStr.toString();
					if ("null".equalsIgnoreCase(keyBuilder) || keyBuilder.equalsIgnoreCase("nullnull")
							|| keyBuilder.equalsIgnoreCase("nullnullnull")
							|| keyBuilder.equalsIgnoreCase("nullnullnullnull")) {
						keyBuilder = "null";
					}
					if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {

						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCompartment()
									+ lFilterModel.getAgencyName());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCompartment() + lFilterModel.getAgencyName());
						}

					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.REGION)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getAgencyName() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCompartment() + lFilterModel.getAgencyName());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.COUNTRY)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(
									lFilterModel.getRegion() + lFilterModel.getCountry() + lFilterModel.getPos()
											+ lFilterModel.getAgencyName() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCompartment() + lFilterModel.getAgencyName());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.POS)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getPos() + lFilterModel.getOrigin() + lFilterModel.getDestination()
									+ lFilterModel.getAgencyName() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCompartment() + lFilterModel.getAgencyName());
						}
					}
					lFilterModel.setFilterKey(lStr.toString());

					// Creating co-ordinates for price Elasticity slope

					if ((int) Float.parseFloat(lFilterModel.getAirCharge()) > 0
							&& Integer.parseInt(lFilterModel.getHostPax()) > 0) {
						PriceElasticityModel lPEModel = new PriceElasticityModel();
						lPEModel.setRegion(lFilterModel.getRegion());
						lPEModel.setCountry(lFilterModel.getCountry());
						lPEModel.setOrigin(lFilterModel.getOrigin());
						lPEModel.setDestination(lFilterModel.getDestination());
						lPEModel.setCompartment(lFilterModel.getCompartment());
						lPEModel.setCustomerSegment(lFilterModel.getCustomerSegment());
						double logAirCharge = CalculationUtil
								.logOfBase((int) Float.parseFloat(lFilterModel.getAirCharge()));
						lPEModel.setAirCharge(Double.toString(logAirCharge));
						double logPax = CalculationUtil.logOfBase(Integer.parseInt(lFilterModel.getHostPax()));
						lPEModel.setHostPax(Double.toString(logPax));
						lPEModel.setFilterKey(lFilterModel.getFilterKey());
						lAirchargePaxList.add(lPEModel);
					}

					lMajorAgenciesModelList.add(lFilterModel);

				}

				Map<String, MajorAgenciesModel> map = new HashMap<String, MajorAgenciesModel>();
				MajorAgenciesModel lMajorAgencies = null;
				if (lMajorAgenciesModelList.size() > 0) {

					for (FilterModel lObj : lMajorAgenciesModelList) {

						if (!map.containsKey(lObj.getFilterKey())) {
							lMajorAgencies = new MajorAgenciesModel();
							lMajorAgencies.setCombinationKey(lObj.getFilterKey());
							lMajorAgencies.setRegion(lObj.getRegion());
							lMajorAgencies.setCountry(lObj.getCountry());
							lMajorAgencies.setPos(lObj.getPos());
							lMajorAgencies.setOrigin(lObj.getOrigin());
							lMajorAgencies.setDestination(lObj.getDestination());
							lMajorAgencies.setCompartment(lObj.getCompartment());
							lMajorAgencies.setAgencyName(lObj.getAgencyName());
							float totalSalesRevenue = Float.parseFloat(lObj.getSalesRevenue());
							float totalSalesRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr());
							lMajorAgencies.setTotalSalesRevenue(totalSalesRevenue);
							lMajorAgencies.setTotalSalesRevenue_lastyr(totalSalesRevenue_lastyr);
							int totalPax = (int) Float.parseFloat(lObj.getHostPax());
							lMajorAgencies.setTotalPax(totalPax);
							int totalPax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
							lMajorAgencies.setTotalPax_lastyr(totalPax_lastyr);
							map.put(lObj.getFilterKey(), lMajorAgencies);
						} else {
							for (String lKey : map.keySet()) {
								if (lObj.getFilterKey().equals(lKey)) {
									lMajorAgencies = map.get(lKey);
								}
							}
							lMajorAgencies.setRegion(lObj.getRegion());
							lMajorAgencies.setCountry(lObj.getCountry());
							lMajorAgencies.setPos(lObj.getPos());
							lMajorAgencies.setOrigin(lObj.getOrigin());
							lMajorAgencies.setDestination(lObj.getDestination());
							lMajorAgencies.setCompartment(lObj.getCompartment());
							float totalSalesRevenue = Float.parseFloat(lObj.getSalesRevenue())
									+ lMajorAgencies.getTotalSalesRevenue();
							float totalSalesRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr())
									+ lMajorAgencies.getTotalSalesRevenue_lastyr();
							lMajorAgencies.setTotalSalesRevenue(totalSalesRevenue);
							lMajorAgencies.setTotalSalesRevenue_lastyr(totalSalesRevenue_lastyr);
							int totalPax = (int) Float.parseFloat(lObj.getHostPax()) + lMajorAgencies.getTotalPax();
							lMajorAgencies.setTotalPax(totalPax);
							int totalpax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr())
									+ lMajorAgencies.getTotalPax_lastyr();
							lMajorAgencies.setTotalPax_lastyr(totalpax_lastyr);
						}

					}
				}

				MajorAgenciesResponse lResponse = null;
				for (String key : map.keySet()) {
					lResponse = new MajorAgenciesResponse();
					lResponse.setRegion(map.get(key).getRegion());
					lResponse.setCountry(map.get(key).getCountry());
					lResponse.setPos(map.get(key).getPos());
					lResponse.setOrigin(map.get(key).getOrigin());
					lResponse.setDestination(map.get(key).getDestination());
					lResponse.setCompartment(map.get(key).getCompartment());

					float lPE = CalculationUtil.roundToTwoDecimal(CalculationUtil
							.calculatePriceElasticity(lAirchargePaxList, map.get(key).getCombinationKey()), 2);
					lResponse.setPriceElasticity(Float.toString(lPE));

					lResponse.setAgencyName(map.get(key).getAgencyName());
					lResponse.setPriceMin("-");
					lResponse.setPriceMax("-");

					// Revenue YTD, Revenue VLYR
					float totalSalesRevenue = map.get(key).getTotalSalesRevenue();
					lResponse.setRevenueYTD(Float.toString(totalSalesRevenue));

					// Grand Total Sales Revenue YTD
					totalSalesRevenueYTD += totalSalesRevenue;

					float totalSalesRevenue_lastyr = map.get(key).getTotalSalesRevenue_lastyr();

					// Grand Total Sales Revenue Last yr
					totalSalesRevenue_LY += totalSalesRevenue_lastyr;

					if (totalSalesRevenue_lastyr != 0) {
						lRevenueVLYR = CalculationUtil.calculateVLYR(totalSalesRevenue, totalSalesRevenue_lastyr);
						lResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
					} else {
						lResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
					}

					// Pax YTD, Pax VLYR
					int totalPax = map.get(key).getTotalPax();
					int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					lResponse.setPaxYTD(Integer.toString(totalPax));

					// Grand Total Sales Revenue YTD
					totalPaxYTD += totalPax;
					totalPax_LY += totalPax_lastyr;

					float lpaxVLYR = 0;
					if (totalPax_lastyr > 0) {
						lpaxVLYR = CalculationUtil.calculateVLYR(totalPax, totalPax_lastyr);
					} else {
						lpaxVLYR = 0;
					}
					lResponse.setPaxVLYR(Float.toString(lpaxVLYR));
					lResponse.setProximityIndicator("-");

					lMajorAgenciesList.add(lResponse);
				}

				/* Calculation of Totals and setting in TotalList - Start */
				MajorAgenciesTotalsResponse lTotalsModel = new MajorAgenciesTotalsResponse();

				// Revenue Totals
				lTotalsModel.setTotalSalesRevenueYTD(Float.toString(CalculationUtil.round(totalSalesRevenueYTD, 2)));
				// lTotalsModel.setTotalFlownRevenueYTD(CalculationUtil.round(totalFlownRevenueYTD,
				// 1));
				float lTotalRevenueVLYR = 0;
				if (totalSalesRevenue_LY != 0 && totalSalesRevenue_LY > 0) {
					lTotalRevenueVLYR = CalculationUtil.calculateVLYR(totalSalesRevenueYTD, totalSalesRevenue_LY);
					lTotalsModel.setTotalRevenueVLYR(Float.toString(lTotalRevenueVLYR));
				} else {
					lTotalsModel.setTotalRevenueVLYR(Float.toString(lTotalRevenueVLYR));
				}

				// Pax Totals
				lTotalsModel.setTotalPaxYTD(Integer.toString(totalPaxYTD));
				// lTotalsModel.setTotalFlownRevenueYTD(CalculationUtil.round(totalFlownRevenueYTD,
				// 1));
				float lTotalPaxVLYR = 0;
				if (totalPax_LY != 0 && totalPax_LY > 0) {
					lTotalPaxVLYR = CalculationUtil.calculateVLYR(totalPaxYTD, totalPax_LY);
					lTotalsModel.setTotalPaxVLYR(Float.toString(lTotalPaxVLYR));
				} else {
					lTotalsModel.setTotalRevenueVLYR(Float.toString(lTotalPaxVLYR));
				}

				lTotalsList.add(lTotalsModel);

				/* Calculation of Totals and setting in TotalList - End */

			}

			responseMajorAgenciesMap.put("MajorAgencies", lMajorAgenciesList);
			responseMajorAgenciesMap.put("MajorAgenciesTotals", lTotalsList);

		} catch (Exception e) {
			logger.error("getPriceCharac_MajorAgencies-Exception", e);
		}

		return responseMajorAgenciesMap;
	}

	@Override
	public Map<String, Object> getPriceCharac_PriceHistory(RequestModel mRequestModel) {
		float lRevenueYTD = 0;
		float lRevenueVLYR = 0;

		float totalRevenueYTD = 0;
		float totalRevenue_LY = 0;
		float totalRevenue_VLYR = 0;
		float totalPax_YTD = 0;
		float totalPax_LY = 0;
		float totalPaxVLYR = 0;
		float totalYield_YTD = 0;
		float totalYield_LY = 0;

		List<FilterModel> lPriceHistoryDataList = new ArrayList<FilterModel>();
		List<PriceHistoryResponse> lPriceHistoryResponseList = new ArrayList<PriceHistoryResponse>();
		List<PriceHistoryTotalsResponse> lTotalsList = new ArrayList<PriceHistoryTotalsResponse>();

		List<PriceElasticityModel> lAirchargePaxList = new ArrayList<PriceElasticityModel>();

		Map<String, Object> responsePriceHistoryMap = new HashMap<String, Object>();

		FilterModel lFilterModel = new FilterModel();

		ArrayList<DBObject> lPriceCharac_PriceHistoryObjList = priceBiometricDao
				.getPriceCharac_PriceHistory(mRequestModel);
		JSONArray lPriceChar_PriceHistoryData = new JSONArray(lPriceCharac_PriceHistoryObjList);
		try {
			if (lPriceChar_PriceHistoryData != null) {
				for (int i = 0; i < lPriceChar_PriceHistoryData.length(); i++) {
					JSONObject lPriceHistoryJsonObj = lPriceChar_PriceHistoryData.getJSONObject(i);
					lFilterModel = new FilterModel();
					System.out.println("lPriceHistoryJsonObj" + lPriceHistoryJsonObj);

					if (lPriceHistoryJsonObj.has("AIR_CHARGE") && lPriceHistoryJsonObj.get("AIR_CHARGE") != null
							&& !lPriceHistoryJsonObj.get("AIR_CHARGE").toString().equalsIgnoreCase("null")) {
						lFilterModel.setAirCharge(lPriceHistoryJsonObj.get("AIR_CHARGE").toString());
					}

					if (lPriceHistoryJsonObj.has(ApplicationConstants.REGION)
							&& lPriceHistoryJsonObj.get(ApplicationConstants.REGION) != null && !lPriceHistoryJsonObj
									.get(ApplicationConstants.REGION).toString().equalsIgnoreCase("null")) {
						lFilterModel.setRegion(lPriceHistoryJsonObj.get(ApplicationConstants.REGION).toString());
					}
					if (lPriceHistoryJsonObj.has(ApplicationConstants.COUNTRY)
							&& lPriceHistoryJsonObj.get(ApplicationConstants.COUNTRY) != null && !lPriceHistoryJsonObj
									.get(ApplicationConstants.COUNTRY).toString().equalsIgnoreCase("null")) {
						lFilterModel.setCountry(lPriceHistoryJsonObj.get(ApplicationConstants.COUNTRY).toString());
					}
					if (lPriceHistoryJsonObj.has(ApplicationConstants.POS)
							&& lPriceHistoryJsonObj.get(ApplicationConstants.POS) != null && !lPriceHistoryJsonObj
									.get(ApplicationConstants.POS).toString().equalsIgnoreCase("null")) {
						lFilterModel.setPos(lPriceHistoryJsonObj.get(ApplicationConstants.POS).toString());
					}
					if (lPriceHistoryJsonObj.has(ApplicationConstants.DESTINATION)
							&& lPriceHistoryJsonObj.get(ApplicationConstants.DESTINATION) != null
							&& !lPriceHistoryJsonObj.get(ApplicationConstants.DESTINATION).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel
								.setDestination(lPriceHistoryJsonObj.get(ApplicationConstants.DESTINATION).toString());
					}
					if (lPriceHistoryJsonObj.has(ApplicationConstants.ORIGIN)
							&& lPriceHistoryJsonObj.get(ApplicationConstants.ORIGIN) != null && !lPriceHistoryJsonObj
									.get(ApplicationConstants.ORIGIN).toString().equalsIgnoreCase("null")) {
						lFilterModel.setOrigin(lPriceHistoryJsonObj.get(ApplicationConstants.ORIGIN).toString());
					}

					// Setting of OD
					lFilterModel.setOd(lFilterModel.getOrigin() + lFilterModel.getDestination());

					if (lPriceHistoryJsonObj.has(ApplicationConstants.COMPARTMENT)
							&& lPriceHistoryJsonObj.get(ApplicationConstants.COMPARTMENT) != null
							&& !lPriceHistoryJsonObj.get(ApplicationConstants.COMPARTMENT).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel
								.setCompartment(lPriceHistoryJsonObj.get(ApplicationConstants.COMPARTMENT).toString());
					}
					if (lPriceHistoryJsonObj.has("PE_signal") && lPriceHistoryJsonObj.get("PE_signal") != null
							&& !lPriceHistoryJsonObj.get("PE_signal").toString().equalsIgnoreCase("null")) {
						lFilterModel.setPriceElasticitySignal(lPriceHistoryJsonObj.get("PE_signal").toString());
					}

					if (lPriceHistoryJsonObj.has("Host_revenue") && lPriceHistoryJsonObj.get("Host_revenue") != null
							&& !lPriceHistoryJsonObj.get("Host_revenue").toString().equalsIgnoreCase("null")) {
						lFilterModel.setSalesRevenue(lPriceHistoryJsonObj.get("Host_revenue").toString());
					}

					if (lPriceHistoryJsonObj.has("Host_revenue_last_year")
							&& lPriceHistoryJsonObj.get("Host_revenue_last_year") != null && !lPriceHistoryJsonObj
									.get("Host_revenue_last_year").toString().equalsIgnoreCase("null")) {
						lFilterModel
								.setSalesRevenue_lastyr(lPriceHistoryJsonObj.get("Host_revenue_last_year").toString());
					} else {
						lFilterModel.setSalesRevenue_lastyr("0");
					}

					if (lPriceHistoryJsonObj.has("Host_revenue_flown")
							&& lPriceHistoryJsonObj.get("Host_revenue_flown") != null
							&& !lPriceHistoryJsonObj.get("Host_revenue_flown").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFlownRevenue(lPriceHistoryJsonObj.get("Host_revenue_flown").toString());
					}

					if (lPriceHistoryJsonObj.has("Host_revenue_flown_1")
							&& lPriceHistoryJsonObj.get("Host_revenue_flown_1") != null
							&& !lPriceHistoryJsonObj.get("Host_revenue_flown_1").toString().equalsIgnoreCase("null")) {
						lFilterModel
								.setFlownRevenue_lastyr(lPriceHistoryJsonObj.get("Host_revenue_flown_1").toString());
					}

					JSONArray lFlownPaxArray = null;
					double lFlownPax = 0;
					if (lPriceHistoryJsonObj.has(ApplicationConstants.HOST_FLOWN_PAX)
							&& lPriceHistoryJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX) != null
							&& !lPriceHistoryJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX).toString()
									.equalsIgnoreCase("null")) {
						lFlownPaxArray = new JSONArray(
								lPriceHistoryJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX).toString());
						if (lFlownPaxArray != null) {
							if (lFlownPaxArray.length() > 0) {
								lFlownPax = Utility.findSum(lFlownPaxArray);
								lFilterModel.setFlownPax(Float.toString((float) lFlownPax));
							} else {
								lFilterModel.setFlownPax(Float.toString((float) lFlownPax));
							}
						}
					}

					if (lPriceHistoryJsonObj.has("sales_pax") && lPriceHistoryJsonObj.get("sales_pax") != null
							&& !lPriceHistoryJsonObj.get("sales_pax").toString().equalsIgnoreCase("null")) {
						lFilterModel.setSalesPax(lPriceHistoryJsonObj.get("sales_pax").toString());
						lFilterModel.setHostPax(lPriceHistoryJsonObj.get("sales_pax").toString());
					}

					JSONArray lBookedPaxArray = null;
					double lBookedPax = 0;
					if (lPriceHistoryJsonObj.has("booked_pax") && lPriceHistoryJsonObj.get("booked_pax") != null
							&& !lPriceHistoryJsonObj.get("booked_pax").toString().equalsIgnoreCase("null")) {
						lBookedPaxArray = new JSONArray(lPriceHistoryJsonObj.get("booked_pax").toString());
						if (lBookedPaxArray != null) {
							if (lBookedPaxArray.length() > 0) {
								lBookedPax = Utility.findSum(lBookedPaxArray);
								lFilterModel.setBookedPax(Float.toString((float) lBookedPax));
							} else {
								lFilterModel.setBookedPax(Float.toString((float) lBookedPax));
							}
						}
					}

					if (lPriceHistoryJsonObj.has("sales_pax_last_year")
							&& lPriceHistoryJsonObj.get("sales_pax_last_year") != null
							&& !lPriceHistoryJsonObj.get("sales_pax_last_year").toString().equalsIgnoreCase("null")) {
						lFilterModel.setHostPax_lastyr(lPriceHistoryJsonObj.get("sales_pax_last_year").toString());
					}

					JSONArray lOdDistanceArray = null;
					double lODDistance = 0;
					if (lPriceHistoryJsonObj.has("od_distance") && lPriceHistoryJsonObj.get("od_distance") != null
							&& !lPriceHistoryJsonObj.get("od_distance").toString().equalsIgnoreCase("null")) {
						lOdDistanceArray = new JSONArray(lPriceHistoryJsonObj.get("od_distance").toString());
						if (lOdDistanceArray != null) {
							if (lOdDistanceArray.length() > 0) {
								lODDistance = Utility.findSum(lOdDistanceArray);
								lFilterModel.setOdDistance(Float.toString((float) lODDistance));
							} else {
								lFilterModel.setOdDistance(Float.toString((float) lODDistance));
							}

						}
					}
					StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lFilterModel);
					String keyBuilder = lStr.toString();
					if ("null".equalsIgnoreCase(keyBuilder) || keyBuilder.equalsIgnoreCase("nullnull")
							|| keyBuilder.equalsIgnoreCase("nullnullnull")
							|| keyBuilder.equalsIgnoreCase("nullnullnullnull")) {
						keyBuilder = "null";
					}
					if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {

						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}

					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.REGION)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.COUNTRY)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.POS)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getPos() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}
					}

					lFilterModel.setFilterKey(lStr.toString());

					// Creating co-ordinates for price Elasticity slope

					if ((int) Float.parseFloat(lFilterModel.getAirCharge()) > 0
							&& Integer.parseInt(lFilterModel.getHostPax()) > 0) {
						PriceElasticityModel lPEModel = new PriceElasticityModel();
						lPEModel.setRegion(lFilterModel.getRegion());
						lPEModel.setCountry(lFilterModel.getCountry());
						lPEModel.setOrigin(lFilterModel.getOrigin());
						lPEModel.setDestination(lFilterModel.getDestination());
						lPEModel.setCompartment(lFilterModel.getCompartment());
						lPEModel.setCustomerSegment(lFilterModel.getCustomerSegment());
						double logAirCharge = CalculationUtil
								.logOfBase((int) Float.parseFloat(lFilterModel.getAirCharge()));
						lPEModel.setAirCharge(Double.toString(logAirCharge));
						double logPax = CalculationUtil.logOfBase(Integer.parseInt(lFilterModel.getHostPax()));
						lPEModel.setHostPax(Double.toString(logPax));
						lPEModel.setFilterKey(lFilterModel.getFilterKey());
						lAirchargePaxList.add(lPEModel);
					}

					lPriceHistoryDataList.add(lFilterModel);

				}

				Map<String, PriceHistoryModel> map = new HashMap<String, PriceHistoryModel>();
				PriceHistoryModel lPriceHistory = null;
				if (lPriceHistoryDataList.size() > 0) {

					for (FilterModel lObj : lPriceHistoryDataList) {

						if (!map.containsKey(lObj.getFilterKey())) {
							lPriceHistory = new PriceHistoryModel();
							lPriceHistory.setCombinationKey(lObj.getFilterKey());
							lPriceHistory.setRegion(lObj.getRegion());
							lPriceHistory.setCountry(lObj.getCountry());
							lPriceHistory.setPos(lObj.getPos());
							lPriceHistory.setOd(lObj.getOd());
							lPriceHistory.setOrigin(lObj.getOrigin());
							lPriceHistory.setDestination(lObj.getDestination());
							lPriceHistory.setCompartment(lObj.getCompartment());
							float totalRevenue = Float.parseFloat(lObj.getSalesRevenue());
							float totalRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr());
							lPriceHistory.setTotalRevenue(totalRevenue);
							if (totalRevenue_lastyr > 0) {
								lPriceHistory.setTotalRevenue_lastyr(totalRevenue_lastyr);
							} else {
								lPriceHistory.setTotalRevenue_lastyr(totalRevenue_lastyr);
							}
							int totalPax = Integer.parseInt(lObj.getHostPax());
							lPriceHistory.setTotalPax(totalPax);
							int totalPax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
							if (totalPax_lastyr > 0) {
								lPriceHistory.setTotalPax_lastyr(totalPax_lastyr);
							} else {
								lPriceHistory.setTotalPax_lastyr(totalPax_lastyr);
							}

							float bookedPax = Float.parseFloat(lObj.getBookedPax());
							lPriceHistory.setBookedPax((int) bookedPax);
							float flownPax = Float.parseFloat(lObj.getFlownPax());
							lPriceHistory.setFlownPax((int) flownPax);
							int salesPax = Integer.parseInt(lObj.getSalesPax());
							lPriceHistory.setSalesPax(salesPax);
							if (Float.parseFloat(lObj.getOdDistance()) > 0) {
								lPriceHistory.setOdDistance(Float.parseFloat(lObj.getOdDistance()));
							} else {
								lPriceHistory.setOdDistance(0);
							}

							map.put(lObj.getFilterKey(), lPriceHistory);
						} else {
							for (String lKey : map.keySet()) {
								if (lObj.getFilterKey().equals(lKey)) {
									lPriceHistory = map.get(lKey);
								}
							}
							float totalRevenue = Float.parseFloat(lObj.getSalesRevenue())
									+ lPriceHistory.getTotalRevenue();
							float totalRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr())
									+ lPriceHistory.getTotalRevenue_lastyr();
							lPriceHistory.setTotalRevenue(totalRevenue);
							lPriceHistory.setTotalRevenue_lastyr(totalRevenue_lastyr);
							int totalPax = Integer.parseInt(lObj.getHostPax()) + lPriceHistory.getTotalPax();
							lPriceHistory.setTotalPax(totalPax);
							int totalpax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
							lPriceHistory.setTotalPax_lastyr(totalpax_lastyr + lPriceHistory.getTotalPax_lastyr());

							int bookedPax = (int) Float.parseFloat(lObj.getBookedPax()) + lPriceHistory.getBookedPax();
							lPriceHistory.setTotalBookedPax(bookedPax);

							int salesPax = Integer.parseInt(lObj.getSalesPax()) + lPriceHistory.getSalesPax();
							lPriceHistory.setTotalSalesPax(salesPax);

							int flownPax = (int) Float.parseFloat(lObj.getFlownPax()) + lPriceHistory.getFlownPax();
							lPriceHistory.setTotalFlownPax(flownPax);

							if (Float.parseFloat(lObj.getOdDistance()) > 0) {
								lPriceHistory.setOdDistance(Float.parseFloat(lObj.getOdDistance()));
							} else {
								lPriceHistory.setOdDistance(0);
							}

						}

					}
				}

				PriceHistoryResponse lResponse = null;
				for (String key : map.keySet()) {
					lResponse = new PriceHistoryResponse();
					lResponse.setRegion(map.get(key).getRegion());
					lResponse.setCountry(map.get(key).getCountry());
					lResponse.setPos(map.get(key).getPos());
					lResponse.setOrigin(map.get(key).getOrigin());
					lResponse.setDestination(map.get(key).getDestination());
					lResponse.setCompartment(map.get(key).getCompartment());
					float lPE = CalculationUtil.roundToTwoDecimal(CalculationUtil
							.calculatePriceElasticity(lAirchargePaxList, map.get(key).getCombinationKey()), 2);
					lResponse.setPriceElasticity(Float.toString(lPE));

					// Revenue
					float totalRevenue = map.get(key).getTotalRevenue();
					lRevenueYTD = totalRevenue;
					lResponse.setRevenueYTD(Float.toString(lRevenueYTD));
					float totalRevenue_lastyr = map.get(key).getTotalRevenue_lastyr();
					if (totalRevenue_lastyr != 0) {
						lRevenueVLYR = ((totalRevenue - totalRevenue_lastyr) / totalRevenue_lastyr) * 100;
						lResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
					} else {
						lResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
					}

					// Grand Total Revenue
					totalRevenueYTD += totalRevenue;
					totalRevenue_LY += totalRevenue_lastyr;

					int totalPax = map.get(key).getTotalPax();
					int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					lResponse.setPaxYTD(Integer.toString(totalPax));
					int lpaxVLYR = 0;
					if (totalPax_lastyr > 0) {
						lpaxVLYR = ((totalPax - totalPax_lastyr) / totalPax_lastyr) * 100;
					} else {
						lpaxVLYR = 0;
					}
					lResponse.setPaxVLYR(Integer.toString(lpaxVLYR));

					// Grand total Pax
					totalPax_YTD += totalPax;
					totalPax_LY += totalPax_lastyr;

					// Yield to be calculated
					float lRPKM = totalPax * map.get(key).getOdDistance();
					float lYieldYTD = 0;
					if (lRPKM > 0) {
						lYieldYTD = totalRevenue / lRPKM;
					} else {
						lYieldYTD = 0;
					}

					float lYieldVLYR = 0;
					float lRPKM_1 = totalPax_lastyr * map.get(key).getOdDistance();
					if (lRPKM > 0) {
						lYieldVLYR = totalRevenue_lastyr / lRPKM_1;
					} else {
						lYieldVLYR = 0;
					}
					lResponse.setYieldYTD(Float.toString(lYieldYTD));
					lResponse.setYieldVLYR(Float.toString(lYieldVLYR));

					// Grand total Yield
					// totalYield_YTD+=lYieldYTD;
					// totalYield_LY+=totalPax_lastyr;

					// Average Fare
					float avgFare = totalRevenue / totalPax;
					lResponse.setAvgFare(Float.toString(avgFare));

					// B/T/F
					String lB_T_F = Integer.toString(map.get(key).getTotalBookedPax()) + "/"
							+ Integer.toString(map.get(key).getTotalSalesPax()) + "/"
							+ Integer.toString(map.get(key).getTotalFlownPax());
					lResponse.setB_T_F(lB_T_F);

					lPriceHistoryResponseList.add(lResponse);

				}

				if (totalRevenue_LY > 0) {
					totalRevenue_VLYR = CalculationUtil.calculateVLYR(totalRevenueYTD, totalRevenue_LY);
				} else {
					totalRevenue_VLYR = 0;
				}

				if (totalPax_LY > 0) {
					totalPaxVLYR = CalculationUtil.calculateVLYR(totalPax_YTD, totalPax_LY);
				} else {
					totalPaxVLYR = 0;
				}

				PriceHistoryTotalsResponse lTotals = new PriceHistoryTotalsResponse();
				lTotals.setTotalPaxYTD(Float.toString(totalPax_YTD));
				lTotals.setTotalPaxVLYR(Float.toString(totalPaxVLYR));
				lTotals.setTotalRevenueYTD(Float.toString(totalRevenueYTD));
				lTotals.setTotalRevenueVLYR(Float.toString(totalRevenue_VLYR));
				lTotalsList.add(lTotals);

			}
			responsePriceHistoryMap.put("PriceHistoryTotals", lTotalsList);
			responsePriceHistoryMap.put("PriceHistory", lPriceHistoryResponseList);

		} catch (Exception e) {
			logger.error("getPriceCharac_PriceHistory-Exception", e);
		}

		return responsePriceHistoryMap;

	}

	@Override
	public Map<String, Object> getPriceCharac_CustomerSegment(RequestModel mRequestModel) {

		float lRevenueYTD = 0;
		float lRevenueVLYR = 0;

		float totalRevenueYTD = 0;
		float totalRevenue_LY = 0;
		float totalRevenue_VLYR = 0;
		float totalPax_YTD = 0;
		float totalPax_LY = 0;
		float totalPaxVLYR = 0;

		List<FilterModel> lCustomerSegmentDataList = new ArrayList<FilterModel>();
		List<PriceCustomerSegmentResponse> lCustomerSegmentList = new ArrayList<PriceCustomerSegmentResponse>();
		List<PriceHistoryTotalsResponse> lTotalsList = new ArrayList<PriceHistoryTotalsResponse>();

		List<PriceElasticityModel> lAirchargePaxList = new ArrayList<PriceElasticityModel>();

		FilterModel lFilterModel = new FilterModel();

		Map<String, Object> responseCustomerSegmentMap = new HashMap<String, Object>();

		ArrayList<DBObject> lPriceCharac_CustomerSegmentObjList = priceBiometricDao
				.getPriceCharac_CustomerSegment(mRequestModel);
		JSONArray lCustomerSegmentData = new JSONArray(lPriceCharac_CustomerSegmentObjList);
		try {
			if (lCustomerSegmentData != null) {
				for (int i = 0; i < lCustomerSegmentData.length(); i++) {
					JSONObject lCustomerSegmentJsonObj = lCustomerSegmentData.getJSONObject(i);
					lFilterModel = new FilterModel();

					if (lCustomerSegmentJsonObj.has(ApplicationConstants.REGION)
							&& lCustomerSegmentJsonObj.get(ApplicationConstants.REGION) != null
							&& !lCustomerSegmentJsonObj.get(ApplicationConstants.REGION).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setRegion(lCustomerSegmentJsonObj.get(ApplicationConstants.REGION).toString());
					}
					if (lCustomerSegmentJsonObj.has(ApplicationConstants.COUNTRY)
							&& lCustomerSegmentJsonObj.get(ApplicationConstants.COUNTRY) != null
							&& !lCustomerSegmentJsonObj.get(ApplicationConstants.COUNTRY).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setCountry(lCustomerSegmentJsonObj.get(ApplicationConstants.COUNTRY).toString());
					}
					if (lCustomerSegmentJsonObj.has(ApplicationConstants.POS)
							&& lCustomerSegmentJsonObj.get(ApplicationConstants.POS) != null && !lCustomerSegmentJsonObj
									.get(ApplicationConstants.POS).toString().equalsIgnoreCase("null")) {
						lFilterModel.setPos(lCustomerSegmentJsonObj.get(ApplicationConstants.POS).toString());
					}
					if (lCustomerSegmentJsonObj.has(ApplicationConstants.DESTINATION)
							&& lCustomerSegmentJsonObj.get(ApplicationConstants.DESTINATION) != null
							&& !lCustomerSegmentJsonObj.get(ApplicationConstants.DESTINATION).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setDestination(
								lCustomerSegmentJsonObj.get(ApplicationConstants.DESTINATION).toString());
					}
					if (lCustomerSegmentJsonObj.has(ApplicationConstants.ORIGIN)
							&& lCustomerSegmentJsonObj.get(ApplicationConstants.ORIGIN) != null
							&& !lCustomerSegmentJsonObj.get(ApplicationConstants.ORIGIN).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setOrigin(lCustomerSegmentJsonObj.get(ApplicationConstants.ORIGIN).toString());
					}

					// Setting of OD
					lFilterModel.setOd(lFilterModel.getOrigin() + lFilterModel.getDestination());

					if (lCustomerSegmentJsonObj.has(ApplicationConstants.COMPARTMENT)
							&& lCustomerSegmentJsonObj.get(ApplicationConstants.COMPARTMENT) != null
							&& !lCustomerSegmentJsonObj.get(ApplicationConstants.COMPARTMENT).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setCompartment(
								lCustomerSegmentJsonObj.get(ApplicationConstants.COMPARTMENT).toString());
					}
					if (lCustomerSegmentJsonObj.has("PE_Signal") && lCustomerSegmentJsonObj.get("PE_Signal") != null
							&& !lCustomerSegmentJsonObj.get("PE_Signal").toString().equalsIgnoreCase("null")) {
						lFilterModel.setPriceElasticitySignal(lCustomerSegmentJsonObj.get("PE_signal").toString());
					} else {
						lFilterModel.setPriceElasticitySignal("0");
					}

					if (lCustomerSegmentJsonObj.has("Customer_segment")
							&& lCustomerSegmentJsonObj.get("Customer_segment") != null
							&& !lCustomerSegmentJsonObj.get("Customer_segment").toString().equalsIgnoreCase("null")) {
						lFilterModel.setCustomerSegment(lCustomerSegmentJsonObj.get("Customer_segment").toString());
					} else {
						lFilterModel.setCustomerSegment("NA");
					}

					if (lCustomerSegmentJsonObj.has("Host_revenue")
							&& lCustomerSegmentJsonObj.get("Host_revenue") != null
							&& !lCustomerSegmentJsonObj.get("Host_revenue").toString().equalsIgnoreCase("null")) {
						lFilterModel.setSalesRevenue(lCustomerSegmentJsonObj.get("Host_revenue").toString());
					}

					if (lCustomerSegmentJsonObj.has("Host_revenue_last_year")
							&& lCustomerSegmentJsonObj.get("Host_revenue_last_year") != null && !lCustomerSegmentJsonObj
									.get("Host_revenue_last_year").toString().equalsIgnoreCase("null")) {
						lFilterModel.setSalesRevenue_lastyr(
								lCustomerSegmentJsonObj.get("Host_revenue_last_year").toString());
					}

					if (lCustomerSegmentJsonObj.has("Host_revenue_flown")
							&& lCustomerSegmentJsonObj.get("Host_revenue_flown") != null
							&& !lCustomerSegmentJsonObj.get("Host_revenue_flown").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFlownRevenue(lCustomerSegmentJsonObj.get("Host_revenue_flown").toString());
					}

					if (lCustomerSegmentJsonObj.has("Host_revenue_flown_1")
							&& lCustomerSegmentJsonObj.get("Host_revenue_flown_1") != null && !lCustomerSegmentJsonObj
									.get("Host_revenue_flown_1").toString().equalsIgnoreCase("null")) {
						lFilterModel
								.setFlownRevenue_lastyr(lCustomerSegmentJsonObj.get("Host_revenue_flown_1").toString());
					}

					if (lCustomerSegmentJsonObj.has("Host_pax") && lCustomerSegmentJsonObj.get("Host_pax") != null
							&& !lCustomerSegmentJsonObj.get("Host_pax").toString().equalsIgnoreCase("null")) {
						lFilterModel.setHostPax(lCustomerSegmentJsonObj.get("Host_pax").toString());
					}
					if (lCustomerSegmentJsonObj.has("Host_pax_last_year")
							&& lCustomerSegmentJsonObj.get("Host_pax_last_year") != null
							&& !lCustomerSegmentJsonObj.get("Host_pax_last_year").toString().equalsIgnoreCase("null")) {
						lFilterModel.setHostPax_lastyr(lCustomerSegmentJsonObj.get("Host_pax_last_year").toString());
					}

					if (lCustomerSegmentJsonObj.has(ApplicationConstants.HOST_FLOWN_PAX)
							&& lCustomerSegmentJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX) != null
							&& !lCustomerSegmentJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setFlownPax(
								lCustomerSegmentJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX).toString());
					}
					if (lCustomerSegmentJsonObj.has("flown_pax_lyr")
							&& lCustomerSegmentJsonObj.get("flown_pax_lyr") != null
							&& !lCustomerSegmentJsonObj.get("flown_pax_lyr").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFlownPax_lastyr(lCustomerSegmentJsonObj.get("flown_pax_lyr").toString());
					}

					if (lCustomerSegmentJsonObj.has("AIR_CHARGE") && lCustomerSegmentJsonObj.get("AIR_CHARGE") != null
							&& !lCustomerSegmentJsonObj.get("AIR_CHARGE").toString().equalsIgnoreCase("null")) {
						lFilterModel.setAirCharge(lCustomerSegmentJsonObj.get("AIR_CHARGE").toString());
					}

					StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lFilterModel);
					String keyBuilder = lStr.toString();
					if ("null".equalsIgnoreCase(keyBuilder) || keyBuilder.equalsIgnoreCase("nullnull")
							|| keyBuilder.equalsIgnoreCase("nullnullnull")
							|| keyBuilder.equalsIgnoreCase("nullnullnullnull")) {
						keyBuilder = "null";
					}
					if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {

						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCustomerSegment());
						}

					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.REGION)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCustomerSegment());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.COUNTRY)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCustomerSegment());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.POS)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getPos() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCustomerSegment());
						}
					}

					lFilterModel.setFilterKey(lStr.toString());

					// Creating co-ordinates for price Elasticity slope

					if ((int) Float.parseFloat(lFilterModel.getAirCharge()) > 0
							&& Integer.parseInt(lFilterModel.getHostPax()) > 0) {
						PriceElasticityModel lPEModel = new PriceElasticityModel();
						lPEModel.setRegion(lFilterModel.getRegion());
						lPEModel.setCountry(lFilterModel.getCountry());
						lPEModel.setOrigin(lFilterModel.getOrigin());
						lPEModel.setDestination(lFilterModel.getDestination());
						lPEModel.setCompartment(lFilterModel.getCompartment());
						lPEModel.setCustomerSegment(lFilterModel.getCustomerSegment());
						double logAirCharge = CalculationUtil
								.logOfBase((int) Float.parseFloat(lFilterModel.getAirCharge()));
						lPEModel.setAirCharge(Double.toString(logAirCharge));
						double logPax = CalculationUtil.logOfBase(Integer.parseInt(lFilterModel.getHostPax()));
						lPEModel.setHostPax(Double.toString(logPax));
						lPEModel.setFilterKey(lFilterModel.getFilterKey());
						lAirchargePaxList.add(lPEModel);
					}

					lCustomerSegmentDataList.add(lFilterModel);

				}

				Map<String, CustomerSegmentModel> map = new HashMap<String, CustomerSegmentModel>();
				CustomerSegmentModel lCustomerSegment = null;
				if (lCustomerSegmentDataList.size() > 0) {

					for (FilterModel lObj : lCustomerSegmentDataList) {

						if (!map.containsKey(lObj.getFilterKey())) {
							lCustomerSegment = new CustomerSegmentModel();
							lCustomerSegment.setCombinationKey(lObj.getFilterKey());
							lCustomerSegment.setRegion(lObj.getRegion());
							lCustomerSegment.setCountry(lObj.getCountry());
							lCustomerSegment.setPos(lObj.getPos());
							lCustomerSegment.setOrigin(lObj.getOrigin());
							lCustomerSegment.setDestination(lObj.getDestination());
							lCustomerSegment.setOd(lObj.getOd());
							lCustomerSegment.setCustomerSegment(lObj.getCustomerSegment());
							lCustomerSegment.setCompartment(lObj.getCompartment());
							float totalRevenue = Float.parseFloat(lObj.getSalesRevenue());
							float totalRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr());
							lCustomerSegment.setTotalRevenue(totalRevenue);
							if (totalRevenue_lastyr > 0) {
								lCustomerSegment.setTotalRevenue_lastyr(totalRevenue_lastyr);
							} else {
								lCustomerSegment.setTotalRevenue_lastyr(totalRevenue_lastyr);
							}
							int totalPax = Integer.parseInt(lObj.getHostPax());
							lCustomerSegment.setTotalPax(totalPax);
							int totalPax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
							if (totalPax_lastyr > 0) {
								lCustomerSegment.setTotalPax_lastyr(totalPax_lastyr);
							} else {
								lCustomerSegment.setTotalPax_lastyr(totalPax_lastyr);
							}

							map.put(lObj.getFilterKey(), lCustomerSegment);
						} else {
							for (String lKey : map.keySet()) {
								if (lObj.getFilterKey().equals(lKey)) {
									lCustomerSegment = map.get(lKey);
								}
							}

							float totalRevenue = Float.parseFloat(lObj.getSalesRevenue())
									+ lCustomerSegment.getTotalRevenue();
							float totalRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr())
									+ lCustomerSegment.getTotalRevenue_lastyr();
							lCustomerSegment.setTotalRevenue(totalRevenue);
							lCustomerSegment.setTotalRevenue_lastyr(totalRevenue_lastyr);
							int totalPax = Integer.parseInt(lObj.getHostPax()) + lCustomerSegment.getTotalPax();
							lCustomerSegment.setTotalPax(totalPax);
							int totalpax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr())
									+ lCustomerSegment.getTotalPax_lastyr();
							lCustomerSegment.setTotalPax_lastyr(totalpax_lastyr);
						}

					}
				}

				PriceCustomerSegmentResponse lResponse = null;
				for (String key : map.keySet()) {
					lResponse = new PriceCustomerSegmentResponse();
					lResponse.setRegion(map.get(key).getRegion());
					lResponse.setCountry(map.get(key).getCountry());
					lResponse.setPos(map.get(key).getPos());
					lResponse.setOrigin(map.get(key).getOrigin());
					lResponse.setDestination(map.get(key).getDestination());
					lResponse.setCompartment(map.get(key).getCompartment());

					lResponse.setCustomerSegment(map.get(key).getCustomerSegment());
					float lPE = CalculationUtil.roundToTwoDecimal(CalculationUtil
							.calculatePriceElasticity(lAirchargePaxList, map.get(key).getCombinationKey()), 2);
					lResponse.setPriceElasticity(Float.toString(lPE));

					// Revenue
					float totalRevenue = map.get(key).getTotalRevenue();
					lRevenueYTD = totalRevenue;
					lResponse.setRevenueYTD(Float.toString(lRevenueYTD));
					float totalRevenue_lastyr = map.get(key).getTotalRevenue_lastyr();
					if (totalRevenue_lastyr != 0) {
						lRevenueVLYR = ((totalRevenue - totalRevenue_lastyr) / totalRevenue_lastyr) * 100;
						lResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
					} else {
						lResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
					}

					// Grand Total Revenue
					totalRevenueYTD += totalRevenue;
					totalRevenue_LY += totalRevenue_lastyr;

					// Pax
					int totalPax = map.get(key).getTotalPax();
					int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					lResponse.setPaxYTD(Integer.toString(totalPax));
					int lpaxVLYR = 0;
					if (totalPax_lastyr > 0) {
						lpaxVLYR = ((totalPax - totalPax_lastyr) / totalPax_lastyr) * 100;
					} else {
						lpaxVLYR = 0;
					}
					lResponse.setPaxVLYR(Integer.toString(lpaxVLYR));

					// Grand total Pax
					totalPax_YTD += totalPax;
					totalPax_LY += totalPax_lastyr;

					lCustomerSegmentList.add(lResponse);

				}

				// Calculation of Revenue percentage and Pax Percentage
				for (PriceCustomerSegmentResponse lObject : lCustomerSegmentList) {
					float lRevenue_percentage = CalculationUtil
							.roundToTwoDecimal(Float.parseFloat(lObject.getRevenueYTD()) / totalRevenueYTD, 2);
					lObject.setRevenuePercentage(Float.toString(lRevenue_percentage));

					float lPax_percentage = CalculationUtil
							.roundToTwoDecimal(Float.parseFloat(lObject.getPaxYTD()) / totalPax_YTD, 2);
					lObject.setPaxPercentage(Float.toString(lPax_percentage));
				}

				// Calculation of TotalRevenueVLYR,TotalPaxVLYR
				if (totalRevenue_LY > 0) {
					totalRevenue_VLYR = CalculationUtil.calculateVLYR(totalRevenueYTD, totalRevenue_LY);
				} else {
					totalRevenue_VLYR = 0;
				}

				if (totalPax_LY > 0) {
					totalPaxVLYR = CalculationUtil.calculateVLYR(totalPax_YTD, totalPax_LY);
				} else {
					totalPaxVLYR = 0;
				}

				// Totals
				PriceHistoryTotalsResponse lTotals = new PriceHistoryTotalsResponse();
				lTotals.setTotalPaxYTD(Float.toString(totalPax_YTD));
				lTotals.setTotalPaxVLYR(Float.toString(totalPaxVLYR));
				lTotals.setTotalRevenueYTD(Float.toString(totalRevenueYTD));
				lTotals.setTotalRevenueVLYR(Float.toString(totalRevenue_VLYR));
				lTotalsList.add(lTotals);

			}

			responseCustomerSegmentMap.put("CustomerSegmentTotals", lTotalsList);
			responseCustomerSegmentMap.put("CustomerSegment", lCustomerSegmentList);

		} catch (Exception e) {
			logger.error("getPriceCharac_CustomerSegments-Exception", e);
		}

		return responseCustomerSegmentMap;
	}

	@Override
	public Map<String, Object> getPriceCharac_FareBrand(RequestModel mRequestModel) {
		float lRevenueYTD = 0;
		float lRevenueVLYR = 0;

		float totalRevenueYTD = 0;
		float totalRevenue_LY = 0;
		float totalRevenue_VLYR = 0;
		float totalPax_YTD = 0;
		float totalPax_LY = 0;
		float totalPaxVLYR = 0;

		List<FilterModel> lFareBrandDataList = new ArrayList<FilterModel>();
		List<PriceFareBrandResponse> lFareBrandList = new ArrayList<PriceFareBrandResponse>();
		List<PriceHistoryTotalsResponse> lTotalsList = new ArrayList<PriceHistoryTotalsResponse>();

		List<PriceElasticityModel> lAirchargePaxList = new ArrayList<PriceElasticityModel>();

		FilterModel lFilterModel = new FilterModel();

		Map<String, Object> responseFareBrandsMap = new HashMap<String, Object>();

		ArrayList<DBObject> lPriceCharac_FareBrandObjList = priceBiometricDao.getPriceCharac_FareBrand(mRequestModel);
		JSONArray lFareBrandsData = new JSONArray(lPriceCharac_FareBrandObjList);
		try {
			if (lFareBrandsData != null) {
				for (int i = 0; i < lFareBrandsData.length(); i++) {
					JSONObject lFareBrandsJsonObj = lFareBrandsData.getJSONObject(i);
					lFilterModel = new FilterModel();

					if (lFareBrandsJsonObj.has(ApplicationConstants.REGION)
							&& lFareBrandsJsonObj.get(ApplicationConstants.REGION) != null && !lFareBrandsJsonObj
									.get(ApplicationConstants.REGION).toString().equalsIgnoreCase("null")) {
						lFilterModel.setRegion(lFareBrandsJsonObj.get(ApplicationConstants.REGION).toString());
					}
					if (lFareBrandsJsonObj.has(ApplicationConstants.COUNTRY)
							&& lFareBrandsJsonObj.get(ApplicationConstants.COUNTRY) != null && !lFareBrandsJsonObj
									.get(ApplicationConstants.COUNTRY).toString().equalsIgnoreCase("null")) {
						lFilterModel.setCountry(lFareBrandsJsonObj.get(ApplicationConstants.COUNTRY).toString());
					}
					if (lFareBrandsJsonObj.has(ApplicationConstants.POS)
							&& lFareBrandsJsonObj.get(ApplicationConstants.POS) != null
							&& !lFareBrandsJsonObj.get(ApplicationConstants.POS).toString().equalsIgnoreCase("null")) {
						lFilterModel.setPos(lFareBrandsJsonObj.get(ApplicationConstants.POS).toString());
					}
					if (lFareBrandsJsonObj.has(ApplicationConstants.DESTINATION)
							&& lFareBrandsJsonObj.get(ApplicationConstants.DESTINATION) != null && !lFareBrandsJsonObj
									.get(ApplicationConstants.DESTINATION).toString().equalsIgnoreCase("null")) {
						lFilterModel
								.setDestination(lFareBrandsJsonObj.get(ApplicationConstants.DESTINATION).toString());
					}
					if (lFareBrandsJsonObj.has(ApplicationConstants.ORIGIN)
							&& lFareBrandsJsonObj.get(ApplicationConstants.ORIGIN) != null && !lFareBrandsJsonObj
									.get(ApplicationConstants.ORIGIN).toString().equalsIgnoreCase("null")) {
						lFilterModel.setOrigin(lFareBrandsJsonObj.get(ApplicationConstants.ORIGIN).toString());
					}
					// Setting of OD
					lFilterModel.setOd(lFilterModel.getOrigin() + lFilterModel.getDestination());

					if (lFareBrandsJsonObj.has(ApplicationConstants.COMPARTMENT)
							&& lFareBrandsJsonObj.get(ApplicationConstants.COMPARTMENT) != null && !lFareBrandsJsonObj
									.get(ApplicationConstants.COMPARTMENT).toString().equalsIgnoreCase("null")) {
						lFilterModel
								.setCompartment(lFareBrandsJsonObj.get(ApplicationConstants.COMPARTMENT).toString());
					}
					if (lFareBrandsJsonObj.has("PE_signal") && lFareBrandsJsonObj.get("PE_signal") != null
							&& !lFareBrandsJsonObj.get("PE_signal").toString().equalsIgnoreCase("null")) {
						lFilterModel.setPriceElasticitySignal(lFareBrandsJsonObj.get("PE_signal").toString());
					}

					if (lFareBrandsJsonObj.has("farebasis") && lFareBrandsJsonObj.get("farebasis") != null
							&& !lFareBrandsJsonObj.get("farebasis").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFareBasis(lFareBrandsJsonObj.get("farebasis").toString());
					}

					if (lFareBrandsJsonObj.has("Fare_brand") && lFareBrandsJsonObj.get("Fare_brand") != null
							&& !lFareBrandsJsonObj.get("Fare_brand").toString().equalsIgnoreCase("null")
							&& !lFareBrandsJsonObj.get("Fare_brand").toString().equalsIgnoreCase("[]")) {
						lFilterModel.setFareBrand(lFareBrandsJsonObj.get("Fare_brand").toString());
					} else {
						lFilterModel.setFareBrand("-");
					}

					if (lFareBrandsJsonObj.has("Fare_rule") && lFareBrandsJsonObj.get("Fare_rule") != null
							&& !lFareBrandsJsonObj.get("Fare_rule").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFareRule(lFareBrandsJsonObj.get("Fare_rule").toString());
					} else {
						lFilterModel.setFareRule("-");
					}

					if (lFareBrandsJsonObj.has("Host_revenue") && lFareBrandsJsonObj.get("Host_revenue") != null
							&& !lFareBrandsJsonObj.get("Host_revenue").toString().equalsIgnoreCase("null")) {
						lFilterModel.setSalesRevenue(lFareBrandsJsonObj.get("Host_revenue").toString());
					}

					if (lFareBrandsJsonObj.has("Host_revenue_last_year")
							&& lFareBrandsJsonObj.get("Host_revenue_last_year") != null
							&& !lFareBrandsJsonObj.get("Host_revenue_last_year").toString().equalsIgnoreCase("null")) {
						lFilterModel
								.setSalesRevenue_lastyr(lFareBrandsJsonObj.get("Host_revenue_last_year").toString());
					}

					if (lFareBrandsJsonObj.has("Host_revenue_flown")
							&& lFareBrandsJsonObj.get("Host_revenue_flown") != null
							&& !lFareBrandsJsonObj.get("Host_revenue_flown").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFlownRevenue(lFareBrandsJsonObj.get("Host_revenue_flown").toString());
					}

					if (lFareBrandsJsonObj.has("Host_revenue_flown_1")
							&& lFareBrandsJsonObj.get("Host_revenue_flown_1") != null
							&& !lFareBrandsJsonObj.get("Host_revenue_flown_1").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFlownRevenue_lastyr(lFareBrandsJsonObj.get("Host_revenue_flown_1").toString());
					}

					if (lFareBrandsJsonObj.has("Host_pax") && lFareBrandsJsonObj.get("Host_pax") != null
							&& !lFareBrandsJsonObj.get("Host_pax").toString().equalsIgnoreCase("null")) {
						lFilterModel.setHostPax(lFareBrandsJsonObj.get("Host_pax").toString());
					}
					if (lFareBrandsJsonObj.has("Host_pax_last_year")
							&& lFareBrandsJsonObj.get("Host_pax_last_year") != null
							&& !lFareBrandsJsonObj.get("Host_pax_last_year").toString().equalsIgnoreCase("null")) {
						lFilterModel.setHostPax_lastyr(lFareBrandsJsonObj.get("Host_pax_last_year").toString());
					}

					if (lFareBrandsJsonObj.has(ApplicationConstants.HOST_FLOWN_PAX)
							&& lFareBrandsJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX) != null
							&& !lFareBrandsJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel
								.setFlownPax(lFareBrandsJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX).toString());
					}
					if (lFareBrandsJsonObj.has("flown_pax_lyr") && lFareBrandsJsonObj.get("flown_pax_lyr") != null
							&& !lFareBrandsJsonObj.get("flown_pax_lyr").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFlownPax_lastyr(lFareBrandsJsonObj.get("flown_pax_lyr").toString());
					}

					if (lFareBrandsJsonObj.has("AIR_CHARGE") && lFareBrandsJsonObj.get("AIR_CHARGE") != null
							&& !lFareBrandsJsonObj.get("AIR_CHARGE").toString().equalsIgnoreCase("null")) {
						lFilterModel.setAirCharge(lFareBrandsJsonObj.get("AIR_CHARGE").toString());
					}

					StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lFilterModel);
					String keyBuilder = lStr.toString();
					if ("null".equalsIgnoreCase(keyBuilder) || keyBuilder.equalsIgnoreCase("nullnull")
							|| keyBuilder.equalsIgnoreCase("nullnullnull")
							|| keyBuilder.equalsIgnoreCase("nullnullnullnull")) {
						keyBuilder = "null";
					}
					if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {

						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCompartment()
									+ lFilterModel.getFareBrand() + lFilterModel.getFareRule());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getFareBrand());
							lStr.append(lFilterModel.getFareRule());
						}

					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.REGION)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(
									lFilterModel.getRegion() + lFilterModel.getCountry() + lFilterModel.getCompartment()
											+ lFilterModel.getFareBrand() + lFilterModel.getFareRule());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getFareBrand());
							lStr.append(lFilterModel.getFareRule());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.COUNTRY)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(
									lFilterModel.getRegion() + lFilterModel.getCountry() + lFilterModel.getCompartment()
											+ lFilterModel.getFareBrand() + lFilterModel.getFareRule());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getFareBrand());
							lStr.append(lFilterModel.getFareRule());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.POS)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getPos() + lFilterModel.getCompartment()
									+ lFilterModel.getFareBrand() + lFilterModel.getFareRule());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getFareBrand());
							lStr.append(lFilterModel.getFareRule());
						}
					}

					lFilterModel.setFilterKey(lStr.toString());

					// Creating co-ordinates for price Elasticity slope

					if ((int) Float.parseFloat(lFilterModel.getAirCharge()) > 0
							&& Integer.parseInt(lFilterModel.getHostPax()) > 0) {
						PriceElasticityModel lPEModel = new PriceElasticityModel();
						lPEModel.setRegion(lFilterModel.getRegion());
						lPEModel.setCountry(lFilterModel.getCountry());
						lPEModel.setOrigin(lFilterModel.getOrigin());
						lPEModel.setDestination(lFilterModel.getDestination());
						lPEModel.setCompartment(lFilterModel.getCompartment());
						lPEModel.setCustomerSegment(lFilterModel.getCustomerSegment());
						double logAirCharge = CalculationUtil
								.logOfBase((int) Float.parseFloat(lFilterModel.getAirCharge()));
						lPEModel.setAirCharge(Double.toString(logAirCharge));
						double logPax = CalculationUtil.logOfBase(Integer.parseInt(lFilterModel.getHostPax()));
						lPEModel.setHostPax(Double.toString(logPax));
						lPEModel.setFilterKey(lFilterModel.getFilterKey());
						lAirchargePaxList.add(lPEModel);
					}

					lFareBrandDataList.add(lFilterModel);

				}

				Map<String, FareBrandModel> map = new HashMap<String, FareBrandModel>();
				FareBrandModel lFareBrand = null;
				if (lFareBrandDataList.size() > 0) {

					for (FilterModel lObj : lFareBrandDataList) {

						if (!map.containsKey(lObj.getFilterKey())) {
							lFareBrand = new FareBrandModel();
							lFareBrand.setCombinationKey(lObj.getFilterKey());
							lFareBrand.setRegion(lObj.getRegion());
							lFareBrand.setCountry(lObj.getCountry());
							lFareBrand.setPos(lObj.getPos());
							lFareBrand.setOrigin(lObj.getOrigin());
							lFareBrand.setDestination(lObj.getDestination());
							lFareBrand.setCompartment(lObj.getCompartment());
							lFareBrand.setFareBrand(lObj.getFareBrand());
							lFareBrand.setFareRule(lObj.getFareRule());
							lFareBrand.setPriceElasticitySignal(lObj.getPriceElasticitySignal());
							float totalSalesRevenue = Float.parseFloat(lObj.getSalesRevenue());
							float totalSalesRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr());

							lFareBrand.setTotalRevenue(totalSalesRevenue);
							if (totalSalesRevenue_lastyr > 0) {
								lFareBrand.setTotalRevenue_lastyr(totalSalesRevenue_lastyr);
							} else {
								lFareBrand.setTotalRevenue_lastyr(totalSalesRevenue_lastyr);
							}
							int totalPax = Integer.parseInt(lObj.getHostPax());
							lFareBrand.setTotalPax(totalPax);
							int totalPax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
							if (totalPax_lastyr > 0) {
								lFareBrand.setTotalPax_lastyr(totalPax_lastyr);
							} else {
								lFareBrand.setTotalPax_lastyr(totalPax_lastyr);
							}

							map.put(lObj.getFilterKey(), lFareBrand);
						} else {
							for (String lKey : map.keySet()) {
								if (lObj.getFilterKey().equals(lKey)) {
									lFareBrand = map.get(lKey);
								}
							}

							float totalRevenue = Float.parseFloat(lObj.getSalesRevenue())
									+ lFareBrand.getTotalRevenue();
							float totalRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr())
									+ lFareBrand.getTotalRevenue_lastyr();
							lFareBrand.setTotalRevenue(totalRevenue);
							lFareBrand.setTotalRevenue_lastyr(totalRevenue_lastyr);
							int totalPax = Integer.parseInt(lObj.getHostPax()) + lFareBrand.getTotalPax();
							lFareBrand.setTotalPax(totalPax);
							int totalpax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr())
									+ lFareBrand.getTotalPax_lastyr();
							lFareBrand.setTotalPax_lastyr(totalpax_lastyr);
						}

					}
				}

				PriceFareBrandResponse lResponse = null;
				for (String key : map.keySet()) {
					lResponse = new PriceFareBrandResponse();
					if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {
						lResponse.setRegion(map.get(key).getRegion());
						lResponse.setCompartment(map.get(key).getCompartment());
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.REGION)) {
						lResponse.setRegion(map.get(key).getRegion());
						lResponse.setCountry(map.get(key).getCountry());
						lResponse.setCompartment(map.get(key).getCompartment());
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.COUNTRY)) {
						lResponse.setRegion(map.get(key).getRegion());
						lResponse.setCountry(map.get(key).getCountry());
						lResponse.setPos(map.get(key).getPos());
						lResponse.setCompartment(map.get(key).getCompartment());
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.POS)) {
						lResponse.setRegion(map.get(key).getRegion());
						lResponse.setCountry(map.get(key).getCountry());
						lResponse.setPos(map.get(key).getPos());
						lResponse.setOrigin(map.get(key).getOrigin());
						lResponse.setDestination(map.get(key).getDestination());
						lResponse.setCompartment(map.get(key).getCompartment());
					}
					if (map.get(key).getFareBrand() != null) {
						lResponse.setFareBrand(map.get(key).getFareBrand());
					} else {
						lResponse.setFareBrand("-");
					}

					if (map.get(key).getFareRule() != null) {
						lResponse.setFareRules(map.get(key).getFareRule());
					} else {
						lResponse.setFareRules("-");
					}

					float lPE = CalculationUtil.roundToTwoDecimal(CalculationUtil
							.calculatePriceElasticity(lAirchargePaxList, map.get(key).getCombinationKey()), 2);
					lResponse.setPriceElasticity(Float.toString(lPE));

					// Revenue
					float totalRevenue = map.get(key).getTotalRevenue();
					lRevenueYTD = totalRevenue;
					lResponse.setRevenueYTD(Float.toString(lRevenueYTD));
					float totalRevenue_lastyr = map.get(key).getTotalRevenue_lastyr();
					if (totalRevenue_lastyr != 0) {
						lRevenueVLYR = ((totalRevenue - totalRevenue_lastyr) / totalRevenue_lastyr) * 100;
						lResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
					} else {
						lResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
					}

					// Grand Total Revenue
					totalRevenueYTD += totalRevenue;
					totalRevenue_LY += totalRevenue_lastyr;

					// Pax
					int totalPax = map.get(key).getTotalPax();
					int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					lResponse.setPaxYTD(Integer.toString(totalPax));
					int lpaxVLYR = 0;
					if (totalPax_lastyr > 0) {
						lpaxVLYR = ((totalPax - totalPax_lastyr) / totalPax_lastyr) * 100;
					} else {
						lpaxVLYR = 0;
					}
					lResponse.setPaxVLYR(Integer.toString(lpaxVLYR));

					// Grand total Pax
					totalPax_YTD += totalPax;
					totalPax_LY += totalPax_lastyr;

					lFareBrandList.add(lResponse);

				}

				// Calculation of Revenue percentage and Pax Percentage
				for (PriceFareBrandResponse lObject : lFareBrandList) {
					float lRevenue_percentage = (Float.parseFloat(lObject.getRevenueYTD()) / totalRevenueYTD) * 100;
					lObject.setRevenuePercentage(Float.toString(lRevenue_percentage));

					float lPax_percentage = (Float.parseFloat(lObject.getPaxYTD()) / totalPax_YTD) * 100;
					lObject.setPaxPercentage(Float.toString(lPax_percentage));
				}

				// Calculation of TotalRevenueVLYR,TotalPaxVLYR
				if (totalRevenue_LY > 0) {
					totalRevenue_VLYR = CalculationUtil.calculateVLYR(totalRevenueYTD, totalRevenue_LY);
				} else {
					totalRevenue_VLYR = 0;
				}

				if (totalPax_LY > 0) {
					totalPaxVLYR = CalculationUtil.calculateVLYR(totalPax_YTD, totalPax_LY);
				} else {
					totalPaxVLYR = 0;
				}

				// Totals
				PriceHistoryTotalsResponse lTotals = new PriceHistoryTotalsResponse();
				lTotals.setTotalPaxYTD(Float.toString(totalPax_YTD));
				lTotals.setTotalPaxVLYR(Float.toString(totalPaxVLYR));
				lTotals.setTotalRevenueYTD(Float.toString(totalRevenueYTD));
				lTotals.setTotalRevenueVLYR(Float.toString(totalRevenue_VLYR));
				lTotalsList.add(lTotals);

			}
			responseFareBrandsMap.put("FareBrandsTotals", lTotalsList);
			responseFareBrandsMap.put("FareBrands", lFareBrandList);

		} catch (Exception e) {
			logger.error("getPriceCharac_FareBrand-Exception", e);
		}

		return responseFareBrandsMap;

	}

	@Override
	public Map<String, Object> getPriceCharac_Channels(RequestModel mRequestModel) {
		float lRevenueYTD = 0;
		float lRevenueVLYR = 0;
		float lRevenueVTGT = 0;

		float lTargetRevenue = 0;
		float lRevenueForecast = 0;

		int lTargetPax = 0;
		int lPaxForecast = 0;

		JSONArray lTargetRevenueArray = null;
		JSONArray lRevenueForecastArray = null;
		JSONArray lPaxForecastArray = null;
		JSONArray lTargetPaxArray = null;

		float totalRevenueYTD = 0;
		float totalRevenue_LY = 0;
		float totalRevenue_VLYR = 0;
		float totalPax_YTD = 0;
		float totalPax_LY = 0;
		float totalPaxVLYR = 0;

		List<FilterModel> lPriceChannelList = new ArrayList<FilterModel>();
		List<PriceChannelsResponse> lChannelList = new ArrayList<PriceChannelsResponse>();
		List<PriceHistoryTotalsResponse> lTotalsList = new ArrayList<PriceHistoryTotalsResponse>();

		List<PriceElasticityModel> lAirchargePaxList = new ArrayList<PriceElasticityModel>();

		FilterModel lFilterModel = new FilterModel();

		Map<String, Object> responsePriceChannelsMap = new HashMap<String, Object>();

		ArrayList<DBObject> lPriceChar_ChannelsObjList = priceBiometricDao.getPriceCharac_Channels(mRequestModel);
		JSONArray lPriceChannelsData = new JSONArray(lPriceChar_ChannelsObjList);
		try {
			if (lPriceChannelsData != null) {
				for (int i = 0; i < lPriceChannelsData.length(); i++) {
					JSONObject lPriceChannelsJsonObj = lPriceChannelsData.getJSONObject(i);
					lFilterModel = new FilterModel();

					if (lPriceChannelsJsonObj.has(ApplicationConstants.DEPARTURE_DATE)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.DEPARTURE_DATE) != null
							&& !lPriceChannelsJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setDepartureDate(
								lPriceChannelsJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString());
					}

					if (lPriceChannelsJsonObj.has(ApplicationConstants.REGION)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.REGION) != null && !lPriceChannelsJsonObj
									.get(ApplicationConstants.REGION).toString().equalsIgnoreCase("null")) {
						lFilterModel.setRegion(lPriceChannelsJsonObj.get(ApplicationConstants.REGION).toString());
					}
					if (lPriceChannelsJsonObj.has(ApplicationConstants.COUNTRY)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.COUNTRY) != null && !lPriceChannelsJsonObj
									.get(ApplicationConstants.COUNTRY).toString().equalsIgnoreCase("null")) {
						lFilterModel.setCountry(lPriceChannelsJsonObj.get(ApplicationConstants.COUNTRY).toString());
					}
					if (lPriceChannelsJsonObj.has(ApplicationConstants.POS)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.POS) != null && !lPriceChannelsJsonObj
									.get(ApplicationConstants.POS).toString().equalsIgnoreCase("null")) {
						lFilterModel.setPos(lPriceChannelsJsonObj.get(ApplicationConstants.POS).toString());
					}
					if (lPriceChannelsJsonObj.has(ApplicationConstants.DESTINATION)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.DESTINATION) != null
							&& !lPriceChannelsJsonObj.get(ApplicationConstants.DESTINATION).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel
								.setDestination(lPriceChannelsJsonObj.get(ApplicationConstants.DESTINATION).toString());
					}
					if (lPriceChannelsJsonObj.has(ApplicationConstants.ORIGIN)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.ORIGIN) != null && !lPriceChannelsJsonObj
									.get(ApplicationConstants.ORIGIN).toString().equalsIgnoreCase("null")) {
						lFilterModel.setOrigin(lPriceChannelsJsonObj.get(ApplicationConstants.ORIGIN).toString());
					}
					// Setting of OD
					lFilterModel.setOd(lFilterModel.getOrigin() + lFilterModel.getDestination());

					if (lPriceChannelsJsonObj.has(ApplicationConstants.COMPARTMENT)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.COMPARTMENT) != null
							&& !lPriceChannelsJsonObj.get(ApplicationConstants.COMPARTMENT).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel
								.setCompartment(lPriceChannelsJsonObj.get(ApplicationConstants.COMPARTMENT).toString());
					}
					if (lPriceChannelsJsonObj.has(ApplicationConstants.CHANNEL)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.CHANNEL) != null && !lPriceChannelsJsonObj
									.get(ApplicationConstants.CHANNEL).toString().equalsIgnoreCase("null")) {
						if (lPriceChannelsJsonObj.get(ApplicationConstants.CHANNEL).toString()
								.equalsIgnoreCase("GDS")) {
							lFilterModel.setChannel("Indirect");
						} else {
							lFilterModel.setChannel("Direct");
						}

					}

					if (lPriceChannelsJsonObj.has("PE_signal") && lPriceChannelsJsonObj.get("PE_signal") != null
							&& !lPriceChannelsJsonObj.get("PE_signal").toString().equalsIgnoreCase("null")) {
						lFilterModel.setPriceElasticitySignal(lPriceChannelsJsonObj.get("PE_signal").toString());
					}

					if (lPriceChannelsJsonObj.has("Host_revenue") && lPriceChannelsJsonObj.get("Host_revenue") != null
							&& !lPriceChannelsJsonObj.get("Host_revenue").toString().equalsIgnoreCase("null")) {
						lFilterModel.setSalesRevenue(lPriceChannelsJsonObj.get("Host_revenue").toString());
					}

					if (lPriceChannelsJsonObj.has("Host_revenue_last_year")
							&& lPriceChannelsJsonObj.get("Host_revenue_last_year") != null && !lPriceChannelsJsonObj
									.get("Host_revenue_last_year").toString().equalsIgnoreCase("null")) {
						lFilterModel
								.setSalesRevenue_lastyr(lPriceChannelsJsonObj.get("Host_revenue_last_year").toString());
					}

					if (lPriceChannelsJsonObj.has("Host_flown_revenue")
							&& lPriceChannelsJsonObj.get("Host_flown_revenue") != null
							&& !lPriceChannelsJsonObj.get("Host_flown_revenue").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFlownRevenue(
								Double.toString(Utility.findSum(lPriceChannelsJsonObj.get("Host_flown_revenue"))));
					}

					if (lPriceChannelsJsonObj.has("Host_flown_revenue_last_year")
							&& lPriceChannelsJsonObj.get("Host_flown_revenue_last_year") != null
							&& !lPriceChannelsJsonObj.get("Host_flown_revenue_last_year").toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setFlownRevenue_lastyr(Double
								.toString(Utility.findSum(lPriceChannelsJsonObj.get("Host_flown_revenue_last_year"))));
					}

					if (lPriceChannelsJsonObj.has(ApplicationConstants.TARGET_REVENUE)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.TARGET_REVENUE) != null
							&& !lPriceChannelsJsonObj.get(ApplicationConstants.TARGET_REVENUE).toString()
									.equalsIgnoreCase("null"))
						lTargetRevenueArray = new JSONArray(
								lPriceChannelsJsonObj.get(ApplicationConstants.TARGET_REVENUE).toString());

					if (lTargetRevenueArray != null) {
						for (int j = 0; j < lTargetRevenueArray.length(); j++) {
							lTargetRevenue += Float.parseFloat(lTargetRevenueArray.get(j).toString());
						}
					}
					lFilterModel.setTargetRevenue(Float.toString(lTargetRevenue));

					if (lPriceChannelsJsonObj.has(ApplicationConstants.FORECAST_REVENUE)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.FORECAST_REVENUE) != null
							&& !lPriceChannelsJsonObj.get(ApplicationConstants.FORECAST_REVENUE).toString()
									.equalsIgnoreCase("null"))
						lRevenueForecastArray = new JSONArray(
								lPriceChannelsJsonObj.get(ApplicationConstants.FORECAST_REVENUE).toString());

					if (lRevenueForecastArray != null) {
						for (int j = 0; j < lRevenueForecastArray.length(); j++) {
							lRevenueForecast += Float.parseFloat(lRevenueForecastArray.get(j).toString());
						}

					}

					if (lPriceChannelsJsonObj.has(ApplicationConstants.DEPARTURE_DATE)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.DEPARTURE_DATE) != null
							&& !Utility.datePassed(
									lPriceChannelsJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString()))
						lFilterModel.setRevenueForecast(Float.toString(lRevenueForecast));

					if (lPriceChannelsJsonObj.has("Host_pax") && lPriceChannelsJsonObj.get("Host_pax") != null
							&& !lPriceChannelsJsonObj.get("Host_pax").toString().equalsIgnoreCase("null")) {
						lFilterModel.setHostPax(lPriceChannelsJsonObj.get("Host_pax").toString());
					}

					if (lPriceChannelsJsonObj.has("Host_flown_pax")
							&& lPriceChannelsJsonObj.get("Host_flown_pax") != null
							&& !lPriceChannelsJsonObj.get("Host_flown_pax").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFlownPax(
								Double.toString(Utility.findSum(lPriceChannelsJsonObj.get("Host_flown_pax"))));
					}

					if (lPriceChannelsJsonObj.has("Host_pax_last_year")
							&& lPriceChannelsJsonObj.get("Host_pax_last_year") != null
							&& !lPriceChannelsJsonObj.get("Host_pax_last_year").toString().equalsIgnoreCase("null")) {
						lFilterModel.setHostPax_lastyr(lPriceChannelsJsonObj.get("Host_pax_last_year").toString());
					}

					if (lPriceChannelsJsonObj.has("Host_pax_target")
							&& lPriceChannelsJsonObj.get("Host_pax_target") != null
							&& !lPriceChannelsJsonObj.get("Host_pax_target").toString().equalsIgnoreCase("null")) {
						lTargetPaxArray = new JSONArray(lPriceChannelsJsonObj.get("Host_pax_target").toString());
					}

					if (lPriceChannelsJsonObj.has(ApplicationConstants.FORECAST_PAX)
							&& lPriceChannelsJsonObj.get(ApplicationConstants.FORECAST_PAX) != null
							&& !lPriceChannelsJsonObj.get(ApplicationConstants.FORECAST_PAX).toString()
									.equalsIgnoreCase("null")) {
						lPaxForecastArray = new JSONArray(
								lPriceChannelsJsonObj.get(ApplicationConstants.FORECAST_PAX).toString());
					}

					if (lPriceChannelsJsonObj.has("AIR_CHARGE") && lPriceChannelsJsonObj.get("AIR_CHARGE") != null
							&& !lPriceChannelsJsonObj.get("AIR_CHARGE").toString().equalsIgnoreCase("null")) {
						lFilterModel.setAirCharge(lPriceChannelsJsonObj.get("AIR_CHARGE").toString());
					}

					StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lFilterModel);
					String keyBuilder = lStr.toString();
					if ("null".equalsIgnoreCase(keyBuilder) || keyBuilder.equalsIgnoreCase("nullnull")
							|| keyBuilder.equalsIgnoreCase("nullnullnull")
							|| keyBuilder.equalsIgnoreCase("nullnullnullnull")) {
						keyBuilder = "null";
					}
					if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {

						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCompartment()
									+ lFilterModel.getChannel());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getChannel());
						}

					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.REGION)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getCompartment() + lFilterModel.getChannel());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getChannel());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.COUNTRY)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getCompartment() + lFilterModel.getChannel());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getChannel());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.POS)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(
									lFilterModel.getRegion() + lFilterModel.getCountry() + lFilterModel.getPos()
											+ lFilterModel.getCompartment() + lFilterModel.getChannel());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getChannel());
						}
					}

					lFilterModel.setFilterKey(lStr.toString());
					// System.out.println("KEY======"+lFilterModel.getFilterKey());

					// Creating co-ordinates for price Elasticity slope

					if ((int) Float.parseFloat(lFilterModel.getAirCharge()) > 0
							&& Integer.parseInt(lFilterModel.getHostPax()) > 0) {
						PriceElasticityModel lPEModel = new PriceElasticityModel();
						lPEModel.setRegion(lFilterModel.getRegion());
						lPEModel.setCountry(lFilterModel.getCountry());
						lPEModel.setOrigin(lFilterModel.getOrigin());
						lPEModel.setDestination(lFilterModel.getDestination());
						lPEModel.setCompartment(lFilterModel.getCompartment());
						lPEModel.setCustomerSegment(lFilterModel.getCustomerSegment());
						double logAirCharge = CalculationUtil
								.logOfBase((int) Float.parseFloat(lFilterModel.getAirCharge()));
						lPEModel.setAirCharge(Double.toString(logAirCharge));
						double logPax = CalculationUtil.logOfBase(Integer.parseInt(lFilterModel.getHostPax()));
						lPEModel.setHostPax(Double.toString(logPax));
						lPEModel.setFilterKey(lFilterModel.getFilterKey());
						lAirchargePaxList.add(lPEModel);
					}

					lPriceChannelList.add(lFilterModel);

				}

				Map<String, PriceChannelsModel> map = new HashMap<String, PriceChannelsModel>();
				PriceChannelsModel lPriceChannel = null;
				if (lPriceChannelList.size() > 0) {

					for (FilterModel lObj : lPriceChannelList) {

						if (!map.containsKey(lObj.getFilterKey())) {
							lPriceChannel = new PriceChannelsModel();
							lPriceChannel.setCombinationKey(lObj.getFilterKey());
							lPriceChannel.setRegion(lObj.getRegion());
							lPriceChannel.setCountry(lObj.getCountry());
							lPriceChannel.setPos(lObj.getPos());
							lPriceChannel.setOd(lObj.getOd());
							lPriceChannel.setOrigin(lObj.getOrigin());
							lPriceChannel.setDestination(lObj.getDestination());
							lPriceChannel.setCompartment(lObj.getCompartment());
							lPriceChannel.setChannel(lObj.getChannel());
							lPriceChannel.setPriceElasticitySignal(lObj.getPriceElasticitySignal());

							float totalSalesRevenue = 0;
							if (lObj.getSalesRevenue() != null)
								totalSalesRevenue = Float.parseFloat(lObj.getSalesRevenue());
							float totalSalesRevenue_lastyr = 0;
							if (lObj.getSalesRevenue_lastyr() != null)
								Float.parseFloat(lObj.getSalesRevenue_lastyr());

							// no of days in the departure month
							int noOfDays = Utility.numberOfDaysInMonth(Utility.findMonth(lObj.getDepartureDate()) - 1,
									Utility.findMonth(lObj.getDepartureDate()));

							// prorated target Revenue
							float lProratedTgtRevenue = 0;
							if (lObj.getTargetRevenue() != null)
								lProratedTgtRevenue = Float.parseFloat(lObj.getTargetRevenue()) / (float) noOfDays;
							lPriceChannel.setTargetRevenue(lProratedTgtRevenue);

							// prorated forecast Revenue
							float lProratedForecastRevenue = 0;
							if (lObj.getRevenueForecast() != null)
								lProratedForecastRevenue = Float.parseFloat(lObj.getRevenueForecast())
										/ (float) noOfDays;
							lPriceChannel.setRevenueForecast(lProratedForecastRevenue);

							lPriceChannel.setTotalRevenue(totalSalesRevenue);

							if (lObj.getFlownRevenue() != null)
								lPriceChannel.setFlownRevenue(Float.parseFloat(lObj.getFlownRevenue()));

							lPriceChannel.setTotalRevenue_lastyr(totalSalesRevenue_lastyr);

							lPriceChannel.setTotalRevenue_lastyr(totalSalesRevenue_lastyr);

							int totalPax = 0;
							if (lObj.getHostPax() != null)
								totalPax = Integer.parseInt(lObj.getHostPax());
							lPriceChannel.setTotalPax(totalPax);

							int flownPax = 0;
							if (lObj.getFlownPax() != null)
								flownPax = Math.round(Float.parseFloat(lObj.getFlownPax()));
							lPriceChannel.setFlownPax(flownPax);

							int totalPax_lastyr = 0;
							if (lObj.getHostPax_lastyr() != null)
								totalPax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
							lPriceChannel.setTotalPax_lastyr(totalPax_lastyr);

							if (lTargetPaxArray != null) {
								for (int i = 0; i < lTargetPaxArray.length(); i++) {
									lTargetPax += lTargetPaxArray.getInt(i);
								}
							}

							// prorated target pax
							float proratedTargetPax = (float) lTargetPax / (float) noOfDays;
							lPriceChannel.setTargetPax((int) proratedTargetPax);

							if (lPaxForecastArray != null && lObj.getDepartureDate() != null
									&& !Utility.datePassed(lObj.getDepartureDate())) {
								if (lPaxForecastArray.length() > 0
										&& (lTargetPaxArray.length() > 0 && lTargetPaxArray != null)) {
									for (int i = 0; i < lTargetPaxArray.length(); i++) {
										lPaxForecast += lTargetPaxArray.getInt(i);
									}
								}

							}

							// prorated pax forecast
							float proratedForecastPax = (float) lPaxForecast / (float) noOfDays;
							lPriceChannel.setForecastPax((int) proratedForecastPax);

							map.put(lObj.getFilterKey(), lPriceChannel);
						} else {
							for (String lKey : map.keySet()) {
								if (lObj.getFilterKey().equals(lKey)) {
									lPriceChannel = map.get(lKey);
								}
							}
							lPriceChannel.setRegion(lObj.getRegion());
							lPriceChannel.setCountry(lObj.getCountry());
							lPriceChannel.setPos(lObj.getPos());
							lPriceChannel.setOrigin(lObj.getOrigin());
							lPriceChannel.setDestination(lObj.getDestination());
							lPriceChannel.setCompartment(lObj.getCompartment());
							if (lObj.getPriceElasticitySignal() != null)
								lPriceChannel.setPriceElasticitySignal(lObj.getPriceElasticitySignal());

							float totalRevenue = lPriceChannel.getTotalRevenue();
							if (lObj.getSalesRevenue() != null)
								totalRevenue += Float.parseFloat(lObj.getSalesRevenue());

							float totalFlownRevenue = lPriceChannel.getFlownRevenue();
							if (lObj.getFlownRevenue() != null)
								totalFlownRevenue += Float.parseFloat(lObj.getFlownRevenue());

							float totalTargetRevenue = lPriceChannel.getTargetRevenue();
							if (lObj.getTargetRevenue() != null)
								totalTargetRevenue += Float.parseFloat(lObj.getTargetRevenue());

							float totalRevenueForecast = lPriceChannel.getRevenueForecast();
							if (lObj.getRevenueForecast() != null)
								totalRevenueForecast += Float.parseFloat(lObj.getRevenueForecast());

							float totalRevenue_lastyr = lPriceChannel.getTotalRevenue_lastyr();
							if (lObj.getSalesRevenue_lastyr() != null)
								totalRevenue_lastyr += Float.parseFloat(lObj.getSalesRevenue_lastyr());

							lPriceChannel.setTotalRevenue(totalRevenue);
							lPriceChannel.setFlownRevenue(totalFlownRevenue);
							lPriceChannel.setRevenueForecast(totalRevenueForecast);
							lPriceChannel.setTargetRevenue(totalTargetRevenue);
							lPriceChannel.setTotalRevenue_lastyr(totalRevenue_lastyr);

							int totalPax = lPriceChannel.getTotalPax();
							if (lObj.getHostPax() != null)
								totalPax += Math.round(Float.parseFloat(lObj.getHostPax()));

							int totalFlownPax = lPriceChannel.getFlownPax();
							if (lObj.getFlownPax() != null)
								totalFlownPax += Math.round(Float.parseFloat(lObj.getFlownPax()));

							int totalPaxForecast = lPriceChannel.getForecastPax();
							if (lObj.getHostPaxForecast() != null)
								totalPaxForecast += Math.round(Float.parseFloat(lObj.getHostPaxForecast()));

							int totalPaxTarget = lPriceChannel.getTargetPax();
							if (lObj.getTargetPax() != null)
								totalPaxTarget += Math.round(Float.parseFloat(lObj.getTargetPax()));

							lPriceChannel.setFlownPax(totalFlownPax);
							lPriceChannel.setTotalPax(totalPax);
							lPriceChannel.setFlownPax(totalFlownPax);
							lPriceChannel.setTargetPax(totalPaxTarget);
							lPriceChannel.setForecastPax(totalPaxForecast);

							int totalpax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
							lPriceChannel.setTotalPax_lastyr(totalpax_lastyr + lPriceChannel.getTotalPax_lastyr());
						}

					}
				}

				PriceChannelsResponse lResponse = null;

				for (String key : map.keySet()) {
					lResponse = new PriceChannelsResponse();
					/*
					 * if ("network".equalsIgnoreCase(mRequestModel.getLevel()))
					 * { lResponse.setRegion(map.get(key).getRegion());
					 * lResponse.setCompartment(map.get(key).getCompartment());
					 * } else if (mRequestModel.getLevel().equalsIgnoreCase(
					 * ApplicationConstants.REGION)) {
					 * lResponse.setRegion(map.get(key).getRegion());
					 * lResponse.setCountry(map.get(key).getCountry());
					 * lResponse.setCompartment(map.get(key).getCompartment());
					 * } else if (mRequestModel.getLevel().equalsIgnoreCase(
					 * ApplicationConstants.COUNTRY)) {
					 * lResponse.setRegion(map.get(key).getRegion());
					 * lResponse.setCountry(map.get(key).getCountry());
					 * lResponse.setPos(map.get(key).getPos());
					 * lResponse.setCompartment(map.get(key).getCompartment());
					 * } else if (mRequestModel.getLevel().equalsIgnoreCase(
					 * ApplicationConstants.POS)) {
					 */
					lResponse.setRegion(map.get(key).getRegion());
					lResponse.setCountry(map.get(key).getCountry());
					lResponse.setPos(map.get(key).getPos());
					lResponse.setOd(map.get(key).getOd());
					lResponse.setCompartment(map.get(key).getCompartment());
					/* } */
					lResponse.setChannel(map.get(key).getChannel());

					float lPE = CalculationUtil.roundToTwoDecimal(CalculationUtil
							.calculatePriceElasticity(lAirchargePaxList, map.get(key).getCombinationKey()), 2);
					lResponse.setPriceElasticitySignal(Float.toString(lPE));

					// Revenue
					float totalRevenue = map.get(key).getTotalRevenue();
					float totalFlownRevenue = map.get(key).getFlownRevenue();
					float totalRevenueForecast = map.get(key).getRevenueForecast();
					float totalTargetRevenue = map.get(key).getTargetRevenue();
					lRevenueYTD = totalRevenue;
					lResponse.setRevenueYTD(lRevenueYTD);
					float totalRevenue_lastyr = map.get(key).getTotalRevenue_lastyr();
					if (totalRevenue_lastyr != 0) {
						lRevenueVLYR = ((totalRevenue - totalRevenue_lastyr) / totalRevenue_lastyr) * 100;
						lResponse.setRevenueVLYR(lRevenueVLYR);
					} else {
						lResponse.setRevenueVLYR(lRevenueVLYR);
					}

					if (totalTargetRevenue > 0) {
						// lRevenueVTGT = ((totalRevenue - lTargetRevenue) /
						// lTargetRevenue) * 100;
						lRevenueVTGT = CalculationUtil.calculateVTGTRemodelled(totalFlownRevenue, totalRevenueForecast,
								totalTargetRevenue);
						lResponse.setRevenueVTGT(lRevenueVTGT);
					} else {
						lResponse.setRevenueVTGT(0);
					}

					// Grand Total Revenue
					totalRevenueYTD += totalRevenue;
					totalRevenue_LY += totalRevenue_lastyr;

					// Pax
					int totalPax = map.get(key).getTotalPax();
					int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					lResponse.setPaxYTD(totalPax);
					int lpaxVLYR = 0;
					if (totalPax_lastyr > 0) {
						lpaxVLYR = ((totalPax - totalPax_lastyr) / totalPax_lastyr) * 100;
					} else {
						lpaxVLYR = 0;
					}
					lResponse.setPaxVLYR(lpaxVLYR);

					// Grand total Pax
					totalPax_YTD += totalPax;
					totalPax_LY += totalPax_lastyr;

					lChannelList.add(lResponse);

				}

				// Calculation of Revenue percentage and Pax Percentage
				for (PriceChannelsResponse lObject : lChannelList) {
					float lRevenue_percentage = (lObject.getRevenueYTD() / totalRevenueYTD) * 100;
					lObject.setRevenuePercentage(Float.toString(lRevenue_percentage));

					float lPax_percentage = (lObject.getPaxYTD() / totalPax_YTD) * 100;
					lObject.setPaxPercentage(Float.toString(lPax_percentage));
				}

				// Calculation of TotalRevenueVLYR,TotalPaxVLYR
				if (totalRevenue_LY > 0) {
					totalRevenue_VLYR = CalculationUtil.calculateVLYR(totalRevenueYTD, totalRevenue_LY);
				} else {
					totalRevenue_VLYR = 0;
				}

				if (totalPax_LY > 0) {
					totalPaxVLYR = CalculationUtil.calculateVLYR(totalPax_YTD, totalPax_LY);
				} else {
					totalPaxVLYR = 0;
				}

				// Totals
				PriceHistoryTotalsResponse lTotals = new PriceHistoryTotalsResponse();
				lTotals.setTotalPaxYTD(Float.toString(totalPax_YTD));
				lTotals.setTotalPaxVLYR(Float.toString(totalPaxVLYR));
				lTotals.setTotalRevenueYTD(Float.toString(totalRevenueYTD));
				lTotals.setTotalRevenueVLYR(Float.toString(totalRevenue_VLYR));
				lTotalsList.add(lTotals);

			}
			responsePriceChannelsMap.put("PriceChannelsTotals", lTotalsList);
			responsePriceChannelsMap.put("PriceChannels", lChannelList);

		} catch (Exception e) {
			logger.error("getPriceCharac_Channels-Exception", e);
		}

		return responsePriceChannelsMap;
	}

	@Override
	public Map<String, Object> getPriceQuote(RequestModel mRequestModel) {

		float totalRevenueYTD = 0;
		float totalRevenue_LY = 0;
		float totalRevenue_VLYR = 0;
		float totalPax_YTD = 0;
		float totalPax_LY = 0;
		float totalPaxVLYR = 0;
		float lTotaltargetPax = 0;
		float lTotalforcastRevenue = 0;
		float lTotalforcastPax = 0;
		JSONArray lHostFlownRevenueArray = null;
		double flownrevenue = 0;
		double forcastpax = 0;
		double forcastrevenue = 0;
		JSONArray lHostPaxFlownArray = null;
		JSONArray lTargetRevenueArray = null;
		float lTotalTargetRevenue = 0;
		double flown = 0;
		JSONArray lCompetitiorRatingArray = null;
		JSONArray lCapacityCarrierArray = null;
		FilterModel lFilterModel = new FilterModel();
		List<FilterModel> lDataList = new ArrayList<FilterModel>();
		JSONArray lRatingCarrierArray = null;
		Map<String, Object> responsePriceQuoteMap = new HashMap<String, Object>();
		List<PriceQuoteResponse> lPriceQuoteResponseList = new ArrayList<PriceQuoteResponse>();
		List<PriceHistoryTotalsResponse> lTotalsList = new ArrayList<PriceHistoryTotalsResponse>();

		ArrayList<DBObject> lPriceQuoteObjList = priceBiometricDao.getPriceQuote(mRequestModel);

		JSONArray lPriceQuotedata = new JSONArray(lPriceQuoteObjList);
		try {
			if (lPriceQuotedata != null) {
				for (int i = 0; i < lPriceQuotedata.length(); i++) {
					JSONObject lPriceQuoteJsonObj = lPriceQuotedata.getJSONObject(i);
					lFilterModel = new FilterModel();
					if (lPriceQuoteJsonObj.has(ApplicationConstants.DEPARTURE_DATE)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.DEPARTURE_DATE) != null
							&& !lPriceQuoteJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString()
									.equalsIgnoreCase("null")) {
						lFilterModel.setDepartureDate(
								lPriceQuoteJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString());
					}
					if (lPriceQuoteJsonObj.has(ApplicationConstants.REGION)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.REGION) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.REGION).toString().equalsIgnoreCase("null")) {
						lFilterModel.setRegion(lPriceQuoteJsonObj.get(ApplicationConstants.REGION).toString());
					}
					if (lPriceQuoteJsonObj.has(ApplicationConstants.COUNTRY)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.COUNTRY) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.COUNTRY).toString().equalsIgnoreCase("null")) {
						lFilterModel.setCountry(lPriceQuoteJsonObj.get(ApplicationConstants.COUNTRY).toString());
					}
					if (lPriceQuoteJsonObj.has(ApplicationConstants.POS)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.POS) != null
							&& !lPriceQuoteJsonObj.get(ApplicationConstants.POS).toString().equalsIgnoreCase("null")) {
						lFilterModel.setPos(lPriceQuoteJsonObj.get(ApplicationConstants.POS).toString());
					}
					if (lPriceQuoteJsonObj.has(ApplicationConstants.ORIGIN)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.ORIGIN) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.ORIGIN).toString().equalsIgnoreCase("null")) {
						lFilterModel.setOrigin(lPriceQuoteJsonObj.get(ApplicationConstants.ORIGIN).toString());
					}
					if (lPriceQuoteJsonObj.has(ApplicationConstants.DESTINATION)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.DESTINATION) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.DESTINATION).toString().equalsIgnoreCase("null")) {
						lFilterModel
								.setDestination(lPriceQuoteJsonObj.get(ApplicationConstants.DESTINATION).toString());
					}

					// Setting of OD
					lFilterModel.setOd(lFilterModel.getOrigin() + lFilterModel.getDestination());

					if (lPriceQuoteJsonObj.has(ApplicationConstants.COMPARTMENT)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.COMPARTMENT) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.COMPARTMENT).toString().equalsIgnoreCase("null")) {
						lFilterModel
								.setCompartment(lPriceQuoteJsonObj.get(ApplicationConstants.COMPARTMENT).toString());
					}

					if (lPriceQuoteJsonObj.has("RBD") && lPriceQuoteJsonObj.get("RBD") != null
							&& !lPriceQuoteJsonObj.get("RBD").toString().equalsIgnoreCase("null")) {
						lFilterModel.setRbd(lPriceQuoteJsonObj.get("RBD").toString());
					}
					if (lPriceQuoteJsonObj.has("fare_basis") && lPriceQuoteJsonObj.get("fare_basis") != null
							&& !lPriceQuoteJsonObj.get("fare_basis").toString().equalsIgnoreCase("null")) {
						lFilterModel.setFareBasis(lPriceQuoteJsonObj.get("fare_basis").toString());
					}
					if (lPriceQuoteJsonObj.has("currency") && lPriceQuoteJsonObj.get("currency") != null
							&& !lPriceQuoteJsonObj.get("currency").toString().equalsIgnoreCase("null")) {
						lFilterModel.setCurrency(lPriceQuoteJsonObj.get("currency").toString());
					}
					if (lPriceQuoteJsonObj.has(ApplicationConstants.CHANNEL)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.CHANNEL) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.CHANNEL).toString().equalsIgnoreCase("null")) {
						lFilterModel.setChannel(lPriceQuoteJsonObj.get(ApplicationConstants.CHANNEL).toString());
					}
					if (lPriceQuoteJsonObj.has("RBD") && lPriceQuoteJsonObj.get("RBD") != null
							&& !lPriceQuoteJsonObj.get("RBD").toString().equalsIgnoreCase("null")) {
						lFilterModel.setRbd((lPriceQuoteJsonObj.get("RBD").toString()));
					}

					if (lPriceQuoteJsonObj.has("revenue") && lPriceQuoteJsonObj.get("revenue") != null
							&& !lPriceQuoteJsonObj.get("revenue").toString().equalsIgnoreCase("null")) {
						lFilterModel.setRevenue(Float.parseFloat(lPriceQuoteJsonObj.get("revenue").toString()));
					}

					if (lPriceQuoteJsonObj.has("revenue_1") && lPriceQuoteJsonObj.get("revenue_1") != null
							&& !lPriceQuoteJsonObj.get("revenue_1").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("revenue_1").toString().equalsIgnoreCase("[]")) {
						lFilterModel.setRevenuelastyr(Float.parseFloat(lPriceQuoteJsonObj.get("revenue_1").toString()));
					} else {
						lFilterModel.setRevenuelastyr(0);
					}
					if (lPriceQuoteJsonObj.has(ApplicationConstants.RULEID)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.RULEID) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.RULEID).toString().equalsIgnoreCase("null")) {
						lFilterModel.setRuleId(lPriceQuoteJsonObj.get(ApplicationConstants.RULEID).toString());
					}

					if (lPriceQuoteJsonObj.has(ApplicationConstants.FOOTNOTE)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.FOOTNOTE) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.FOOTNOTE).toString().equalsIgnoreCase("null")) {
						lFilterModel.setFootNote(lPriceQuoteJsonObj.get(ApplicationConstants.FOOTNOTE).toString());
					}

					if (lPriceQuoteJsonObj.has(ApplicationConstants.BASEFARE)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.BASEFARE) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.BASEFARE).toString().equalsIgnoreCase("null")) {
						lFilterModel.setBaseFare(lPriceQuoteJsonObj.get(ApplicationConstants.BASEFARE).toString());
					} else {
						lFilterModel.setBaseFare("0");
					}

					if (lPriceQuoteJsonObj.has(ApplicationConstants.SURCHARGE)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.SURCHARGE) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.SURCHARGE).toString().equalsIgnoreCase("null")) {
						lFilterModel.setSurCharge(lPriceQuoteJsonObj.get(ApplicationConstants.SURCHARGE).toString());
					}

					if (lPriceQuoteJsonObj.has(ApplicationConstants.YQ)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.YQ) != null
							&& !lPriceQuoteJsonObj.get(ApplicationConstants.YQ).toString().equalsIgnoreCase("null")) {
						lFilterModel.setYqCharge(lPriceQuoteJsonObj.get(ApplicationConstants.YQ).toString());
					}

					if (lPriceQuoteJsonObj.has(ApplicationConstants.TAXES)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.TAXES) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.TAXES).toString().equalsIgnoreCase("null")) {
						lFilterModel.setTaxes(lPriceQuoteJsonObj.get(ApplicationConstants.TAXES).toString());
					}

					if (lPriceQuoteJsonObj.has(ApplicationConstants.TOTALFARE)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.TOTALFARE) != null && !lPriceQuoteJsonObj
									.get(ApplicationConstants.TOTALFARE).toString().equalsIgnoreCase("null")) {
						lFilterModel.setTotalFare(lPriceQuoteJsonObj.get(ApplicationConstants.TOTALFARE).toString());
					}

					if (lPriceQuoteJsonObj.has("base_fare") && lPriceQuoteJsonObj.get("base_fare") != null
							&& !lPriceQuoteJsonObj.get("base_fare").toString().equalsIgnoreCase("null")) {
						lFilterModel.setBaseFare(lPriceQuoteJsonObj.get("base_fare").toString());
					}
					if (lPriceQuoteJsonObj.has("Customer_segment") && lPriceQuoteJsonObj.get("Customer_segment") != null
							&& !lPriceQuoteJsonObj.get("Customer_segment").toString().equalsIgnoreCase("null")) {
						lFilterModel.setCustomerSegment(lPriceQuoteJsonObj.get("Customer_segment").toString());
					}

					JSONArray lOdDistanceArray = null;
					double lODDistance = 0;
					if (lPriceQuoteJsonObj.has("od_distance") && lPriceQuoteJsonObj.get("od_distance") != null
							&& !lPriceQuoteJsonObj.get("od_distance").toString().equalsIgnoreCase("null")) {
						lOdDistanceArray = new JSONArray(lPriceQuoteJsonObj.get("od_distance").toString());
						if (lOdDistanceArray != null) {
							if (lOdDistanceArray.length() > 0) {
								lODDistance = Utility.findSum(lOdDistanceArray);
								lFilterModel.setOdDistance(Float.toString((float) lODDistance));
							}
						}
					}

					if (lPriceQuoteJsonObj.has("Host_Pax") && lPriceQuoteJsonObj.get("Host_Pax") != null
							&& !lPriceQuoteJsonObj.get("Host_Pax").toString().equalsIgnoreCase("null")) {
						lFilterModel.setHostPax(lPriceQuoteJsonObj.get("Host_Pax").toString());
					}

					if (lPriceQuoteJsonObj.has("Host_Pax_last_year")
							&& lPriceQuoteJsonObj.get("Host_Pax_last_year") != null
							&& !lPriceQuoteJsonObj.get("Host_Pax_last_year").toString().equalsIgnoreCase("null")) {
						lFilterModel.setHostPax_lastyr(lPriceQuoteJsonObj.get("Host_Pax_last_year").toString());
					}

					int noOfDays = Utility.numberOfDaysInMonth(
							Utility.findMonth(lPriceQuoteJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString()),
							Utility.findYear(lPriceQuoteJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString()));

					JSONArray lHostPaxTgtArray = null;
					double lHostPaxTgt = 0;
					if (lPriceQuoteJsonObj.has("Host_Pax_target") && lPriceQuoteJsonObj.get("Host_Pax_target") != null
							&& !lPriceQuoteJsonObj.get("Host_Pax_target").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("Host_Pax_target").toString().equalsIgnoreCase("[]")) {
						lHostPaxTgtArray = new JSONArray(lPriceQuoteJsonObj.get("Host_Pax_target").toString());
						if (lHostPaxTgtArray != null) {
							if (lHostPaxTgtArray.length() > 0) {
								lHostPaxTgt = Utility.findSum(lHostPaxTgtArray);
								float proratedPaxTgt = (float) lHostPaxTgt / (float) noOfDays;
								lFilterModel.setHostPax_tgt(Float.toString(proratedPaxTgt));
							} else {
								lFilterModel.setHostPax_tgt(Float.toString(0));
							}
						}
					} else {
						lFilterModel.setHostPax_tgt(Float.toString(0));
					}
					if (lHostPaxTgtArray != null) {
						for (int m = 0; m < lHostPaxTgtArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lHostPaxTgtArray.get(m).toString()))
								lTotaltargetPax += Float.parseFloat(lHostPaxTgtArray.get(m).toString());
						}
					}

					if (lPriceQuoteJsonObj.has(ApplicationConstants.TARGET_REVENUE)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.TARGET_REVENUE) != null
							&& !lPriceQuoteJsonObj.get(ApplicationConstants.TARGET_REVENUE).toString()
									.equalsIgnoreCase("null")) {
						lTargetRevenueArray = new JSONArray(
								lPriceQuoteJsonObj.get(ApplicationConstants.TARGET_REVENUE).toString());
					}
					if (lTargetRevenueArray != null) {
						for (int m = 0; m < lTargetRevenueArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lTargetRevenueArray.get(m).toString()))
								lTotalTargetRevenue += Float.parseFloat(lTargetRevenueArray.get(m).toString());
						}
					}

					JSONArray lMarketSharePaxArray = null;
					if (lPriceQuoteJsonObj.has("market_share_pax") && lPriceQuoteJsonObj.get("market_share_pax") != null
							&& !lPriceQuoteJsonObj.get("market_share_pax").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("market_share_pax").toString().equalsIgnoreCase("[]")) {
						lMarketSharePaxArray = new JSONArray(lPriceQuoteJsonObj.get("market_share_pax").toString());
					}

					JSONArray lMarketSharePax_1Array = null;
					if (lPriceQuoteJsonObj.has("market_share_pax_1")
							&& lPriceQuoteJsonObj.get("market_share_pax_1") != null
							&& !lPriceQuoteJsonObj.get("market_share_pax_1").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("market_share_pax_1").toString().equalsIgnoreCase("[]")) {
						lMarketSharePax_1Array = new JSONArray(lPriceQuoteJsonObj.get("market_share_pax_1").toString());
					}

					/*
					 * if (lPriceQuoteJsonObj.has(ApplicationConstants.
					 * HOST_FLOWN_PAX) &&
					 * lPriceQuoteJsonObj.get(ApplicationConstants.
					 * HOST_FLOWN_PAX) != null &&
					 * !lPriceQuoteJsonObj.get(ApplicationConstants.
					 * HOST_FLOWN_PAX).toString() .equalsIgnoreCase("null") &&
					 * !lPriceQuoteJsonObj.get(ApplicationConstants.
					 * HOST_FLOWN_PAX).toString() .equalsIgnoreCase("[]")) {
					 * lHostPaxFlownArray = new JSONArray(
					 * lPriceQuoteJsonObj.get(ApplicationConstants.
					 * HOST_FLOWN_PAX).toString()); }
					 * 
					 * lFilterModel.setFlownPax(Double
					 * .toString(Utility.findSum(lPriceQuoteJsonObj.get(
					 * ApplicationConstants.HOST_FLOWN_PAX))));
					 */

					if (checkIfValueExist(ApplicationConstants.HOST_FLOWN_PAX, lPriceQuoteJsonObj)) {
						lHostPaxFlownArray = new JSONArray(
								lPriceQuoteJsonObj.get(ApplicationConstants.HOST_FLOWN_PAX).toString());

						if (lHostPaxFlownArray != null) {
							if (lHostPaxFlownArray.length() > 0) {
								for (int j = 0; j < lHostPaxFlownArray.length(); j++) {
									if (lHostPaxFlownArray != null
											&& !lHostPaxFlownArray.get(j).toString().equalsIgnoreCase("null")
											&& !lHostPaxFlownArray.get(j).toString().equalsIgnoreCase("[]")) {

										flown = Utility.findSum(lHostPaxFlownArray);
										lFilterModel.setFlownpax((float) flown);
									}
								}
							}
						}
					}
					if (lPriceQuoteJsonObj.has(ApplicationConstants.HOST_FLOWN_REVENUE)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE) != null
							&& !lPriceQuoteJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE).toString()
									.equalsIgnoreCase("null")) {
						lHostFlownRevenueArray = new JSONArray(
								lPriceQuoteJsonObj.get(ApplicationConstants.HOST_FLOWN_REVENUE).toString());
						if (lHostFlownRevenueArray != null) {
							if (lHostFlownRevenueArray.length() > 0) {
								for (int j = 0; j < lHostFlownRevenueArray.length(); j++) {
									if (lHostFlownRevenueArray != null
											&& !lHostFlownRevenueArray.get(j).toString().equalsIgnoreCase("null")
											&& !lHostFlownRevenueArray.get(j).toString().equalsIgnoreCase("[]")) {
										flownrevenue = Utility.findSum(lHostFlownRevenueArray);
										lFilterModel.setFlownrevenue((float) flownrevenue);
									}
								}
							}
						}
					}

					JSONArray lHostPaxForecastArray = null;
					if (lPriceQuoteJsonObj.has(ApplicationConstants.FORECAST_PAX)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.FORECAST_PAX) != null
							&& !lPriceQuoteJsonObj.get(ApplicationConstants.FORECAST_PAX).toString()
									.equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get(ApplicationConstants.FORECAST_PAX).toString()
									.equalsIgnoreCase("[]")) {
						lHostPaxForecastArray = new JSONArray(
								lPriceQuoteJsonObj.get(ApplicationConstants.FORECAST_PAX).toString());
						if (lHostPaxForecastArray != null) {
							if (lHostPaxForecastArray.length() > 0) {
								forcastpax = Utility.findSum(lHostPaxForecastArray);
								lFilterModel.setPaxForcast((float) (forcastpax));
							}
						}
					}
					if (lHostPaxForecastArray != null) {
						for (int m = 0; m < lHostPaxForecastArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lHostPaxForecastArray.get(m).toString()))
								lTotalforcastPax += Float.parseFloat(lHostPaxForecastArray.get(m).toString());
						}
					} else {
						lTotalforcastPax = 0;
					}

					JSONArray lHostRevenueForecastArray = null;
					if (lPriceQuoteJsonObj.has(ApplicationConstants.FORECAST_REVENUE)
							&& lPriceQuoteJsonObj.get(ApplicationConstants.FORECAST_REVENUE) != null
							&& !lPriceQuoteJsonObj.get(ApplicationConstants.FORECAST_REVENUE).toString()
									.equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get(ApplicationConstants.FORECAST_REVENUE).toString()
									.equalsIgnoreCase("[]")) {
						lHostRevenueForecastArray = new JSONArray(
								lPriceQuoteJsonObj.get(ApplicationConstants.FORECAST_REVENUE).toString());
						if (lHostRevenueForecastArray != null) {
							if (lHostRevenueForecastArray.length() > 0) {
								forcastrevenue = Utility.findSum(lHostRevenueForecastArray);
								lFilterModel.setRevenueForcast((float) (forcastrevenue));
							}
						}

					}
					if (lHostRevenueForecastArray != null) {
						for (int m = 0; m < lHostRevenueForecastArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lHostRevenueForecastArray.get(m).toString()))
								lTotalforcastRevenue += Float.parseFloat(lHostRevenueForecastArray.get(m).toString());
						}
					} else {
						lTotalforcastRevenue = 0;
					}

					JSONArray lCapacityArray = null;
					double lCapacity = 0;
					if (lPriceQuoteJsonObj.has("capacity") && lPriceQuoteJsonObj.get("capacity") != null
							&& !lPriceQuoteJsonObj.get("capacity").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("capacity").toString().equalsIgnoreCase("[]")) {
						lCapacityArray = new JSONArray(lPriceQuoteJsonObj.get("capacity").toString());
						if (lCapacityArray != null) {
							if (lCapacityArray.length() > 0) {
								lCapacity = Utility.findSum(lCapacityArray);
								lFilterModel.setCapacity(Float.toString((float) lCapacity));
							}
						}
					} else {
						lFilterModel.setCapacity(Float.toString((float) lCapacity));
					}

					// Capacity Carrier
					double lCapacityCarrier = 0;
					if (lPriceQuoteJsonObj.has("capacity_carrier") && lPriceQuoteJsonObj.get("capacity_carrier") != null
							&& !lPriceQuoteJsonObj.get("capacity_carrier").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("capacity_carrier").toString().equalsIgnoreCase("[]")) {
						lCapacityCarrierArray = new JSONArray(lPriceQuoteJsonObj.get("capacity_carrier").toString());
					} else {
						lCapacityCarrierArray = null;
					}
					float capacityFZ = 0;
					float capacityComp1 = 0;
					float capacityComp2 = 0;
					float capacityComp3 = 0;
					float capacityComp4 = 0;

					if (lCapacityCarrierArray != null) {
						if (lCapacityCarrierArray.length() > 0) {
							for (int j = 0; j < lCapacityCarrierArray.length(); j++) {
								if (lCapacityCarrierArray.length() > 0) {
									if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("FZ")) {
										capacityFZ += Float.parseFloat(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("EK")) {
										capacityComp1 += Float.parseFloat(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("EY")) {
										capacityComp2 += Float.parseFloat(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("G9")) {
										capacityComp3 = Float.parseFloat(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("QR")) {
										capacityComp4 = Float.parseFloat(lCapacityArray.get(j).toString());
									}
								}
							}
							lFilterModel.setCapacityFZ(capacityFZ);
							lFilterModel.setCapacityComp1(capacityComp1);
							lFilterModel.setCapacityComp2(capacityComp2);
							lFilterModel.setCapacityComp3(capacityComp3);
							lFilterModel.setCapacityComp4(capacityComp4);
						}
					} else {
						lFilterModel.setCapacityFZ(capacityFZ);
						lFilterModel.setCapacityComp1(capacityComp1);
						lFilterModel.setCapacityComp2(capacityComp2);
						lFilterModel.setCapacityComp3(capacityComp3);
						lFilterModel.setCapacityComp4(capacityComp4);
					}

					JSONArray lCapacityFrequencyArray = null;
					if (lPriceQuoteJsonObj.has("capacity_frequency")
							&& lPriceQuoteJsonObj.get("capacity_frequency") != null
							&& !lPriceQuoteJsonObj.get("capacity_frequency").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("capacity_frequency").toString().equalsIgnoreCase("[]")) {
						lCapacityFrequencyArray = new JSONArray(
								lPriceQuoteJsonObj.get("capacity_frequency").toString());
					}

					// Rating Carrier
					if (lPriceQuoteJsonObj.has("rating_carrier") && lPriceQuoteJsonObj.get("rating_carrier") != null
							&& !lPriceQuoteJsonObj.get("rating_carrier").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("rating_carrier").toString().equalsIgnoreCase("[]")) {
						lRatingCarrierArray = new JSONArray(lPriceQuoteJsonObj.get("rating_carrier").toString());
					} else {
						lRatingCarrierArray = null;
					}

					if (lPriceQuoteJsonObj.has("rating") && lPriceQuoteJsonObj.get("rating") != null
							&& !lPriceQuoteJsonObj.get("rating").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("rating").toString().equalsIgnoreCase("[]")) {
						lCompetitiorRatingArray = new JSONArray(lPriceQuoteJsonObj.get("rating").toString());
					} else {
						lCompetitiorRatingArray = null;
					}

					float compRatingFZ = 0;
					float compRatingComp1 = 0;
					float compRatingComp2 = 0;
					float compRatingComp3 = 0;
					float compRatingComp4 = 0;
					if (lCompetitiorRatingArray != null && lCompetitiorRatingArray.length() > 0) {
						for (int j = 0; j < lCompetitiorRatingArray.length(); j++) {
							if (lRatingCarrierArray != null && lRatingCarrierArray.length() > 0) {
								if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("FZ")) {
									compRatingFZ = Float.parseFloat(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("EK")) {
									compRatingComp1 = Float.parseFloat(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("EY")) {
									compRatingComp2 = Float.parseFloat(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("G9")) {
									compRatingComp3 = Float.parseFloat(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("QR")) {
									compRatingComp4 = Float.parseFloat(lCompetitiorRatingArray.get(j).toString());
								}

							}
						}
						lFilterModel.setCompRatingFZ(compRatingFZ);
						lFilterModel.setCompRatingComp1(compRatingComp1);
						lFilterModel.setCompRatingComp2(compRatingComp2);
						lFilterModel.setCompRatingComp3(compRatingComp3);
						lFilterModel.setCompRatingComp4(compRatingComp4);
					} else {
						lFilterModel.setCompRatingFZ(compRatingFZ);
						lFilterModel.setCompRatingComp1(compRatingComp1);
						lFilterModel.setCompRatingComp2(compRatingComp2);
						lFilterModel.setCompRatingComp3(compRatingComp3);
						lFilterModel.setCompRatingComp4(compRatingComp4);
					}

					// months and days
					int lMonth = 0;
					int lDays = 0;
					if (!"null"
							.equalsIgnoreCase(lPriceQuoteJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString())) {
						lMonth = Utility
								.findMonth(lPriceQuoteJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString());
						lDays = Utility.findDay(lPriceQuoteJsonObj.get(ApplicationConstants.DEPARTURE_DATE).toString());
						lFilterModel.setMonths(lMonth);
						lFilterModel.setDays(lDays);
					}
					JSONArray lCarrierArray = null;
					if (lPriceQuoteJsonObj.has("carrier") && lPriceQuoteJsonObj.get("carrier") != null
							&& !lPriceQuoteJsonObj.get("carrier").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("carrier").toString().equalsIgnoreCase("[]")) {
						lCarrierArray = new JSONArray(lPriceQuoteJsonObj.get("carrier").toString());
					}

					JSONArray lMarketSizeArray = null;
					double lMarketSize = 0;
					if (lPriceQuoteJsonObj.has("market_size") && lPriceQuoteJsonObj.get("market_size") != null
							&& !lPriceQuoteJsonObj.get("market_size").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("market_size").toString().equalsIgnoreCase("[]")) {
						lMarketSizeArray = new JSONArray(lPriceQuoteJsonObj.get("market_size").toString());
						if (lMarketSizeArray != null) {
							if (lMarketSizeArray.length() > 0) {
								lMarketSize = Utility.findSum(lMarketSizeArray);
								lFilterModel.setMarketSize(Float.toString((float) lMarketSize));
							}
						}
					} else {
						lFilterModel.setMarketSize(Float.toString((float) lMarketSize));
					}

					JSONArray lMarketSize_1Array = null;
					double lMarketSize_lastyr = 0;
					if (lPriceQuoteJsonObj.has("market_size_1") && lPriceQuoteJsonObj.get("market_size_1") != null
							&& !lPriceQuoteJsonObj.get("market_size_1").toString().equalsIgnoreCase("null")
							&& !lPriceQuoteJsonObj.get("market_size_1").toString().equalsIgnoreCase("[]")) {
						lMarketSize_1Array = new JSONArray(lPriceQuoteJsonObj.get("market_size_1").toString());
						if (lMarketSize_1Array != null) {
							if (lMarketSize_1Array.length() > 0) {
								lMarketSize_lastyr = Utility.findSum(lMarketSize_1Array);
								lFilterModel.setMarketSize_lastyr(Float.toString((float) lMarketSize_lastyr));
							}
						}
					} else {
						lFilterModel.setMarketSize_lastyr(Float.toString((float) lMarketSize_lastyr));
					}

					StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lFilterModel);
					String keyBuilder = lStr.toString();
					if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {

						if (keyBuilder.equals("null") || "null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(
									lFilterModel.getRegion() + lFilterModel.getFareBasis() + lFilterModel.getChannel()
											+ lFilterModel.getCustomerSegment() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getFareBasis() + lFilterModel.getChannel()
									+ lFilterModel.getCustomerSegment() + lFilterModel.getCompartment());
						}

					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.REGION)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getFareBasis() + lFilterModel.getChannel()
									+ lFilterModel.getCustomerSegment() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getFareBasis() + lFilterModel.getChannel()
									+ lFilterModel.getCustomerSegment() + lFilterModel.getCompartment());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.COUNTRY)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getFareBasis() + lFilterModel.getChannel()
									+ lFilterModel.getCustomerSegment() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getFareBasis() + lFilterModel.getChannel()
									+ lFilterModel.getCustomerSegment() + lFilterModel.getCompartment());
						}
					} else if (mRequestModel.getLevel().equalsIgnoreCase(ApplicationConstants.POS)) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(
									lFilterModel.getRegion() + lFilterModel.getCountry() + lFilterModel.getFareBasis()
											+ lFilterModel.getChannel() + lFilterModel.getCustomerSegment()
											+ lFilterModel.getPos() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getFareBasis() + lFilterModel.getChannel()
									+ lFilterModel.getCustomerSegment() + lFilterModel.getCompartment());
						}

					}

					lFilterModel.setFilterKey(lStr.toString());

					System.out.println("lPriceQuote     " + lFilterModel.getFilterKey());
					lDataList.add(lFilterModel);

				}

				Map<String, PriceQuote> map = new HashMap<String, PriceQuote>();
				PriceQuote lPriceQuote = null;
				for (FilterModel filterModel : lDataList) {
					if (!map.containsKey(filterModel.getFilterKey())) {
						lPriceQuote = new PriceQuote();
						lPriceQuote.setCombinationKey(filterModel.getFilterKey());
						lPriceQuote.setRegion(filterModel.getRegion());
						lPriceQuote.setCountry(filterModel.getCountry());
						lPriceQuote.setPos(filterModel.getPos());
						lPriceQuote.setOrigin(filterModel.getOrigin());
						lPriceQuote.setDestination(filterModel.getDestination());
						lPriceQuote.setOd(filterModel.getOd());
						lPriceQuote.setCompartment(filterModel.getCompartment());
						lPriceQuote.setFareBasis(filterModel.getFareBasis());
						lPriceQuote.setBaseFare(filterModel.getBaseFare());
						lPriceQuote.setChannel(filterModel.getChannel());
						lPriceQuote.setCustomerSegment(filterModel.getCustomerSegment());
						lPriceQuote.setDepartureDate(filterModel.getDepartureDate());
						lPriceQuote.setRbd(filterModel.getRbd());

						lPriceQuote.setFootNote(filterModel.getFootNote());
						lPriceQuote.setRuleID(filterModel.getRuleId());
						lPriceQuote.setTotalFare(filterModel.getTotalFare());
						lPriceQuote.setYqCharge(Float.parseFloat(filterModel.getYqCharge()));
						lPriceQuote.setTaxes(Float.parseFloat(filterModel.getTaxes()));
						lPriceQuote.setSurCharge(Float.parseFloat(filterModel.getSurCharge()));
						lPriceQuote.setCurrency(filterModel.getCurrency());

						if (filterModel.getOdDistance() != null) {
							// od distance
							float odDistance = Float.parseFloat(filterModel.getOdDistance());
							lPriceQuote.setOdDistance(odDistance);
						}

						lPriceQuote.setCapacityFZ(Float.toString(filterModel.getCapacityFZ()));
						lPriceQuote.setCapacityComp1(Float.toString(filterModel.getCapacityComp1()));
						lPriceQuote.setCapacityComp2(Float.toString(filterModel.getCapacityComp2()));
						lPriceQuote.setCapacityComp3(Float.toString(filterModel.getCapacityComp3()));
						lPriceQuote.setCapacityComp4(Float.toString(filterModel.getCapacityComp4()));

						lPriceQuote.setCompRatingFZ(Float.toString(filterModel.getCompRatingFZ()));
						lPriceQuote.setCompRatingComp1(Float.toString(filterModel.getCompRatingComp1()));
						lPriceQuote.setCompRatingComp2(Float.toString(filterModel.getCompRatingComp2()));
						lPriceQuote.setCompRatingComp3(Float.toString(filterModel.getCompRatingComp3()));
						lPriceQuote.setCompRatingComp4(Float.toString(filterModel.getCompRatingComp4()));
						// Pax
						Double totalHostPax = Double.parseDouble(filterModel.getHostPax());
						lPriceQuote.setTotalHostPax(Double.toString(totalHostPax));
						Double totalHostPax_lastyr = Double.parseDouble(filterModel.getHostPax_lastyr());
						if (totalHostPax_lastyr > 0) {
							lPriceQuote.setTotalHostPax_lastyr(Double.toString(totalHostPax_lastyr));
						} else {
							lPriceQuote.setTotalHostPax_lastyr(Double.toString(0));
						}
						if (filterModel.getHostPax_tgt() != null) {
							lPriceQuote.setHostPaxTarget(Double.parseDouble(filterModel.getHostPax_tgt()));
						} else {
							lPriceQuote.setHostPaxTarget(0D);
						}

						// MarketSize
						lPriceQuote.setMarketSize(Float.parseFloat(filterModel.getMarketSize()));
						lPriceQuote.setMarketSize_lastyr(Float.parseFloat(filterModel.getMarketSize_lastyr()));

						// Revenue
						float revenue = filterModel.getRevenue();
						lPriceQuote.setTotalRevenue(revenue);
						float revenuelastyr = filterModel.getRevenuelastyr();
						lPriceQuote.setTotalRevenue_lastyr(revenuelastyr);

						// FlownRevenue
						float totalflownrevenue = filterModel.getFlownrevenue();
						lPriceQuote.setTotalFlownRevenue(totalflownrevenue);
						// float
						// totalflownrevenuelastyr=filterModel.getFlownRevenue_lastyr();

						// lPriceQuote.setTotalFlownRevenue_lastyr(Float.parseFloat(filterModel.getFlownRevenue_lastyr()));

						// Flown Pax
						lPriceQuote.setHostFlownPax((filterModel.getFlownpax()));

						// months and days
						int lMonth = 0;
						int lDays = 0;
						if (!"null".equalsIgnoreCase(filterModel.getDepartureDate().toString())) {
							lMonth = Utility.findMonth(filterModel.getDepartureDate());
							lDays = Utility.findDay(filterModel.getDepartureDate());
							lPriceQuote.setMonths(lMonth);
							lPriceQuote.setDays(lDays);
						}
						map.put(filterModel.getFilterKey(), lPriceQuote);
					} else {
						for (String lKey : map.keySet()) {
							if (filterModel.getFilterKey().equals(lKey)) {
								lPriceQuote = map.get(lKey);
							}
						}
						// od distance
						float odDistance = Float.parseFloat(filterModel.getOdDistance()) + lPriceQuote.getOdDistance();
						lPriceQuote.setOdDistance(odDistance);
						// Pax
						Double totalHostPax = Double.parseDouble(filterModel.getHostPax())
								+ Double.parseDouble(lPriceQuote.getTotalHostPax());
						lPriceQuote.setTotalHostPax(Double.toString(totalHostPax));

						float totalFlownPax = 0;
						if (lPriceQuote.getHostFlownPax() > 0)
							totalFlownPax = lPriceQuote.getHostFlownPax();
						if (filterModel.getFlownPax() != null)
							totalFlownPax += Double.parseDouble(filterModel.getFlownPax());
						lPriceQuote.setHostFlownPax(totalFlownPax);

						Double totalHostPax_lastyr = Double.parseDouble(filterModel.getHostPax_lastyr())
								+ Double.parseDouble(lPriceQuote.getTotalHostPax_lastyr());
						lPriceQuote.setTotalHostPax_lastyr(Double.toString(totalHostPax_lastyr));

						/*
						 * Double totalHostTarget =
						 * Double.parseDouble(filterModel.getHostPax_tgt()) +
						 * lPriceQuote.getHostPaxTarget();
						 * lPriceQuote.setHostPaxTarget(totalHostTarget);
						 */

						/*
						 * Double totalHostPaxForecast =
						 * lPriceQuote.getHostPaxForecast(); if
						 * (filterModel.getHostPaxForecast() != null)
						 * totalHostPaxForecast +=
						 * Double.parseDouble(filterModel.getHostPaxForecast());
						 * lPriceQuote.setHostPaxForecast(totalHostPaxForecast);
						 */

						// Market Size
						float hostMarketSize = Float.parseFloat(filterModel.getMarketSize())
								+ lPriceQuote.getMarketSize();
						lPriceQuote.setTotalMarketSize(hostMarketSize);
						float hostMarketSize_lastyr = Float.parseFloat(filterModel.getMarketSize_lastyr())
								+ lPriceQuote.getMarketSize_lastyr();
						lPriceQuote.setTotalMarketSize_lastyr(hostMarketSize_lastyr);

						// Flown Revenue
						float totalFlownRevenue = (filterModel.getFlownrevenue()) + lPriceQuote.getTotalFlownRevenue();
						/*
						 * float totalFlownRevenue_lastyr =
						 * Float.parseFloat(filterModel.getFlownRevenue_lastyr()
						 * ) + lPriceQuote.getTotalFlownRevenue_lastyr();
						 */
						lPriceQuote.setTotalFlownRevenue(totalFlownRevenue);
						// lPriceQuote.setTotalFlownRevenue_lastyr(totalFlownRevenue_lastyr);

						// Revenue
						float revenue = filterModel.getRevenue() + lPriceQuote.getTotalRevenue();
						lPriceQuote.setTotalRevenue(revenue);
						float revenuelastyr = filterModel.getRevenuelastyr() + lPriceQuote.getTotalRevenue_lastyr();
						lPriceQuote.setTotalRevenue_lastyr(revenuelastyr);
						// months and days
						int lMonth = 0;
						int lDays = 0;
						if (!"null".equalsIgnoreCase(filterModel.getDepartureDate().toString())) {
							lMonth = Utility.findMonth(filterModel.getDepartureDate());
							lDays = Utility.findDay(filterModel.getDepartureDate());
							lPriceQuote.setMonths(lMonth);
							lPriceQuote.setDays(lDays);
						}
					}
				}

				PriceQuoteResponse lModel = null;
				for (String key : map.keySet()) {
					lModel = new PriceQuoteResponse();
					lModel.setRegion(map.get(key).getRegion());
					lModel.setCountry(map.get(key).getCountry());
					lModel.setPos(map.get(key).getPos());

					lModel.setOrigin(map.get(key).getOrigin());
					lModel.setDestination(map.get(key).getDestination());

					lModel.setOd(map.get(key).getOd());
					lModel.setCompartment(map.get(key).getCompartment());
					lModel.setFareBasis(map.get(key).getFareBasis());
					lModel.setBaseFare(map.get(key).getBaseFare());
					lModel.setChannel(map.get(key).getChannel());
					lModel.setCustomerSegment(map.get(key).getCustomerSegment());
					lModel.setRbd(map.get(key).getRbd());
					// FootNote
					lModel.setFootNote(map.get(key).getFootNote());
					// RuleID
					lModel.setRuleID(map.get(key).getRuleID());
					// Currency
					lModel.setCurrency(map.get(key).getCurrency());
					// TotalFare
					lModel.setTotalFare(map.get(key).getTotalFare());
					// Base Fare
					lModel.setBaseFare(map.get(key).getBaseFare());
					// YQ/Q
					lModel.setYqCharge(Float.toString(map.get(key).getYqCharge()));
					// Tax
					lModel.setTaxes(Float.toString(map.get(key).getTaxes()));
					// Surcharge
					lModel.setSurCharge(Float.toString(map.get(key).getSurCharge()));
					float pForcastPax = 0;
					int totaldaysFromdate = 0;
					Date date1 = null;
					Date date2 = null;
					if (!mRequestModel.getFromDate().isEmpty() && mRequestModel.getFromDate() != null
							&& !mRequestModel.getToDate().isEmpty() && mRequestModel.getToDate() != null) {
						date1 = Utility.convertStringToDateFromat(mRequestModel.getFromDate());
						date2 = Utility.convertStringToDateFromat(mRequestModel.getToDate());
					} else {
						date1 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
						date2 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
					}
					// int result = str2.compareTo(str1);
					int diff = (int) Utility.getDifferenceDays(date1, date2);
					int result = diff + 1;
					if (result == 0) {
						totaldaysFromdate = result + 1;
					}
					// no. of days
					int noOfDays = Utility.numberOfDaysInMonth(
							Utility.findMonth(map.get(key).getDepartureDate().toString()) - 1,
							Utility.findYear(map.get(key).getDepartureDate().toString()));
					float targetProratedPax = ((lTotaltargetPax / (float) noOfDays) * result);
					float proratedForcastPax = (((float) forcastpax / (float) noOfDays) * result);
					float targetProratedRevenue = ((lTotalTargetRevenue / noOfDays) * result);
					float proratedForcastRevenue = (((float) forcastrevenue / (float) noOfDays) * result);

					// months and days
					int lMonth = 0;
					int lDays = 0;
					if (!"null".equalsIgnoreCase(map.get(key).getDepartureDate().toString())) {
						lMonth = Utility.findMonth(map.get(key).getDepartureDate());
						lDays = Utility.findDay(map.get(key).getDepartureDate());
						lModel.setMonth(lMonth);
						lModel.setDays(lDays);
					}
					if (lModel.getMonth() == map.get(key).getMonths()) {
						float lTotaltargetProratedPax = targetProratedPax;
						float lTotalproratedForcastPax = proratedForcastPax;
						lModel.setTotalforcastpax(lTotalproratedForcastPax);
						lModel.setTotaltargetproratedpax(lTotaltargetProratedPax);
						float lTotaltargetProratedRevenue = targetProratedRevenue;
						float lTotalproratedForcastRevenue = proratedForcastRevenue;
						lModel.setTotalforcastrevenue(lTotalproratedForcastRevenue);
						lModel.setTotaltargetproratedrevnue(lTotaltargetProratedRevenue);

					} else {
						float lTotaltargetProratedPax = targetProratedPax + lTotaltargetPax;
						float lTotalproratedForcastPax = proratedForcastPax + lTotalforcastPax;
						lModel.setTotalforcastpax(lTotalproratedForcastPax);
						lModel.setTotaltargetproratedpax(lTotaltargetProratedPax);
						float lTotaltargetProratedRevenue = targetProratedRevenue + lTotalTargetRevenue;
						float lTotalproratedForcastRevenue = proratedForcastRevenue + lTotalforcastRevenue;
						lModel.setTotalforcastrevenue(lTotalproratedForcastRevenue);
						lModel.setTotaltargetproratedrevnue(lTotaltargetProratedRevenue);

					}
					// pax vtgt
					float pForcastRevenue = 0;
					if (result == 0) {
						pForcastPax = 0;
						proratedForcastRevenue = 0;
					} else {
						pForcastPax = lModel.getTotalforcastpax();
						pForcastRevenue = lModel.getTotalforcastrevenue();
					}
					float paxvtgt = 0;
					float pflownpax = map.get(key).getHostFlownPax();
					lModel.setFlownpax(pflownpax);
					if (targetProratedPax != 0) {
						paxvtgt = CalculationUtil.calculateVTGTRemodelled(pflownpax, pForcastPax, targetProratedPax);
					}
					lModel.setHostPaxVTGT((paxvtgt));
					// revenue vtgt
					float revenuevtgt = 0;
					float pflownrevenue = map.get(key).getTotalFlownRevenue();
					lModel.setFlownrevenue(pflownrevenue);
					if (targetProratedRevenue != 0) {
						revenuevtgt = CalculationUtil.calculateVTGTRemodelled(pflownrevenue, pForcastRevenue,
								targetProratedRevenue);
						lModel.setRevenuevtgt((revenuevtgt));
					}

					// PAX Calculations
					float totalHostPax = Float.parseFloat(map.get(key).getTotalHostPax());
					double totalPaxFlown = map.get(key).getHostFlownPax();
					float totalHostPax_lastyr = Float.parseFloat(map.get(key).getTotalHostPax_lastyr());
					float totalHostTgt = Float.parseFloat(map.get(key).getHostPaxTarget().toString());

					// Grand total Pax
					totalPax_YTD += totalHostPax;
					totalPax_LY += totalHostPax_lastyr;

					lModel.setHostPaxYTD(Float.toString(totalHostPax));
					int lpaxVLYR = 0;
					if (totalHostPax_lastyr > 0) {
						lpaxVLYR = (int) CalculationUtil.calculateVLYR(totalHostPax, totalHostPax_lastyr);
					} else {
						lpaxVLYR = 0;
					}
					if (lpaxVLYR > 0) {
						lModel.setHostPaxVLYR(Integer.toString(lpaxVLYR));
					} else {
						lModel.setHostPaxVLYR("NA");
					}

					// Market Share Calculations
					float totalHostMarketSize = map.get(key).getTotalMarketSize();
					float totalHostMarketSize_lastyr = map.get(key).getTotalMarketSize_lastyr();

					float hostMarketShare = 0;
					if (totalHostMarketSize > 0) {
						hostMarketShare = totalHostPax / totalHostMarketSize;
					}
					float hostMarketShare_lastyr = 0;
					if (totalHostMarketSize_lastyr > 0) {
						hostMarketShare_lastyr = totalHostPax_lastyr / totalHostMarketSize_lastyr;
					}

					lModel.setMarketShareYTD(Float.toString(hostMarketShare));
					int lMarketShareVLYR = 0;
					if (hostMarketShare_lastyr > 0) {
						lMarketShareVLYR = (int) CalculationUtil.calculateVLYR(hostMarketShare, hostMarketShare_lastyr);
					} else {
						lMarketShareVLYR = 0;
					}
					if (lMarketShareVLYR > 0) {
						lModel.setMarketShareVLYR(Float.toString(lMarketShareVLYR));
					} else {
						lModel.setMarketShareVLYR("NA");
					}

					// Capacity
					lModel.setCapacityFZ(map.get(key).getCapacityFZ());
					lModel.setCapacityComp1(map.get(key).getCapacityComp1());
					lModel.setCapacityComp2(map.get(key).getCapacityComp2());

					// CompRating
					lModel.setCompRatingFZ(map.get(key).getCompRatingFZ());
					lModel.setCompRatingComp1(map.get(key).getCompRatingComp1());
					lModel.setCompRatingComp2(map.get(key).getCompRatingComp2());

					ArrayList<Integer> lCapacityList = new ArrayList<Integer>();
					int lCapacityFZ = 1;
					if (!map.get(key).getCapacityFZ().equalsIgnoreCase("-")) {
						lCapacityFZ = (int) Float.parseFloat(map.get(key).getCapacityFZ());
						lCapacityList.add(lCapacityFZ);
					}

					if (!map.get(key).getCapacityComp1().equalsIgnoreCase("-")) {
						lCapacityList.add((int) Float.parseFloat(map.get(key).getCapacityComp1()));
					}
					if (!map.get(key).getCapacityComp2().equalsIgnoreCase("-")) {
						lCapacityList.add((int) Float.parseFloat(map.get(key).getCapacityComp2()));
					}
					if (!map.get(key).getCapacityComp3().equalsIgnoreCase("-")) {
						lCapacityList.add((int) Float.parseFloat(map.get(key).getCapacityComp3()));
					}
					if (!map.get(key).getCapacityComp4().equalsIgnoreCase("-")) {
						lCapacityList.add((int) Float.parseFloat(map.get(key).getCapacityComp4()));
					}

					ArrayList<Float> lCompRatingList = new ArrayList<Float>();
					if (!map.get(key).getCompRatingFZ().equalsIgnoreCase("-")) {
						lCompRatingList.add(Float.parseFloat(map.get(key).getCompRatingFZ()));
					} else {
						lCompRatingList.add((float) 1);
					}
					if (!map.get(key).getCompRatingComp1().equalsIgnoreCase("-")) {
						lCompRatingList.add(Float.parseFloat(map.get(key).getCompRatingComp1()));
					} else {
						lCompRatingList.add((float) 1);
					}
					if (!map.get(key).getCompRatingComp2().equalsIgnoreCase("-")) {
						lCompRatingList.add(Float.parseFloat(map.get(key).getCompRatingComp2()));
					} else {
						lCompRatingList.add((float) 1);
					}
					if (!map.get(key).getCompRatingComp3().equalsIgnoreCase("-")) {
						lCompRatingList.add(Float.parseFloat(map.get(key).getCompRatingComp3()));
					} else {
						lCompRatingList.add((float) 1);
					}
					if (!map.get(key).getCompRatingComp4().equalsIgnoreCase("-")) {
						lCompRatingList.add(Float.parseFloat(map.get(key).getCompRatingComp4()));
					} else {
						lCompRatingList.add((float) 1);
					}
					// FMS
					float lCompRatingFZ = 1;
					if (!map.get(key).getCompRatingFZ().equalsIgnoreCase("-")) {
						lCompRatingFZ = Float.parseFloat(map.get(key).getCompRatingFZ());
					}

					float lFMS = CalculationUtil.calculateFMS(lCapacityFZ, lCompRatingFZ, lCapacityList,
							lCompRatingList);
					if (lFMS != 0) {
						lModel.setFms((lFMS));
					} else {
						lModel.setFms(0);
					}
					float lMarketShareVTGT = 0;
					lMarketShareVTGT = ((hostMarketShare - lFMS) / lFMS) * 100;

					// Average Fare Calculations
					float avgFare = map.get(key).getTotalRevenue() / totalHostPax;
					lModel.setAvgFare(Float.toString(avgFare));

					// Yield Calculations
					float yield = 0;
					float lRPKM = totalHostPax * map.get(key).getOdDistance();
					if (lRPKM > 0) {
						yield = ((map.get(key).getTotalRevenue() * 100) / lRPKM);
					}
					if (yield > 0) {
						lModel.setYield(Float.toString(yield));
					} else {
						lModel.setYield(Float.toString(0));
					}
					float yield_lastyr = 0;
					float lRPKM_lastyr = totalHostPax_lastyr * map.get(key).getOdDistance();
					if (lRPKM_lastyr > 0) {
						yield_lastyr = ((map.get(key).getTotalRevenue_lastyr() * 100) / lRPKM_lastyr);
					}
					int lYieldVLYR = 0;
					if (yield_lastyr > 0) {
						lYieldVLYR = (int) CalculationUtil.calculateVLYR(yield, yield_lastyr);
					} else {
						lYieldVLYR = 0;
					}
					if (lYieldVLYR > 0) {
						lModel.setYieldVLYR(Float.toString(lYieldVLYR));
					} else {
						lModel.setYieldVLYR("NA");
					}

					lPriceQuoteResponseList.add(lModel);
				}

				// Calculation of TotalPaxVLYR

				if (totalPax_LY > 0) {
					totalPaxVLYR = CalculationUtil.calculateVLYR(totalPax_YTD, totalPax_LY);
				} else {
					totalPaxVLYR = 0;
				}

				// Totals
				PriceHistoryTotalsResponse lTotals = new PriceHistoryTotalsResponse();
				lTotals.setTotalPaxYTD(Float.toString(totalPax_YTD));
				lTotals.setTotalPaxVLYR(Float.toString(totalPaxVLYR));
				lTotalsList.add(lTotals);

			}
			responsePriceQuoteMap.put("PriceQuoteGridTotals", lTotalsList);
			responsePriceQuoteMap.put("PriceQuoteGridResponse", lPriceQuoteResponseList);
		} catch (Exception e) {
			logger.error("getPriceQuote-Exception", e);
		}

		return responsePriceQuoteMap;
	}

	@Override
	public Map<String, Object> getPriceHeatMapODChannel(RequestModel mRequestModel) {

		ArrayList<DBObject> lPriceHeatMapODChannelObj = priceBiometricDao.getPriceHeatMapODChannel(mRequestModel);
		JSONArray lPriceHeatMapData = new JSONArray(lPriceHeatMapODChannelObj);
		JSONArray data = null;
		Map<String, PriceHeatMapODChannel> phMapOd = new HashMap<String, PriceHeatMapODChannel>();
		Map<String, PriceHeatMapODChannel> phMapChannel = new HashMap<String, PriceHeatMapODChannel>();
		Map<String, PriceHeatMapODChannel> phMapSegment = new HashMap<String, PriceHeatMapODChannel>();
		Map<String, Object> priceHeatOdChannelMap = new HashMap<String, Object>();

		for (int i = 0; i < lPriceHeatMapData.length(); i++) {
			JSONObject jsonObj = lPriceHeatMapData.getJSONObject(i);
			String origin = "-";
			if (jsonObj.get(ApplicationConstants.ORIGIN) != null
					&& !jsonObj.get(ApplicationConstants.ORIGIN).toString().equalsIgnoreCase("null"))
				origin = jsonObj.getString(ApplicationConstants.ORIGIN);
			String destination = "-";
			if (jsonObj.get(ApplicationConstants.DESTINATION) != null
					&& !jsonObj.get(ApplicationConstants.DESTINATION).toString().equalsIgnoreCase("null"))
				destination = jsonObj.getString(ApplicationConstants.DESTINATION);
			String channel = "-";
			if (jsonObj.get(ApplicationConstants.CHANNEL) != null
					&& !jsonObj.get(ApplicationConstants.CHANNEL).toString().equalsIgnoreCase("null"))
				channel = jsonObj.getString(ApplicationConstants.CHANNEL);
			String segment = "-";
			if (jsonObj.has("segment") && jsonObj.get("segment") != null
					&& !jsonObj.get("segment").toString().equalsIgnoreCase("null"))
				segment = jsonObj.getString("segment");
			Double revenue = 0D;
			if (jsonObj.get("Host_revenue") != null && !jsonObj.get("Host_revenue").toString().equalsIgnoreCase("null"))
				revenue = jsonObj.getDouble("Host_revenue");
			Double revenueLastYear = 0D;
			if (jsonObj.get("Host_revenue_last_year") != null
					&& !jsonObj.get("Host_revenue_last_year").toString().equalsIgnoreCase("null"))
				revenueLastYear = jsonObj.getDouble("Host_revenue_last_year");
			Double revenueTarget = 0D;
			if (jsonObj.get("Host_revenue_target") != null
					&& !jsonObj.get("Host_revenue_target").toString().equalsIgnoreCase("null"))
				revenueTarget = Utility.findSum(jsonObj.get("Host_revenue_target"));
			Double marketShare = 0D;
			Double hostMarketShare = 0D;

			String combinationKeyOd = origin + destination;
			String combinationKeyChannel = channel;
			String combinationKeySegment = segment;
			if (!phMapOd.containsKey(combinationKeyOd)) {
				PriceHeatMapODChannel ph = new PriceHeatMapODChannel();
				ph.setOrigin(origin);
				ph.setDestination(destination);
				ph.setRevenue(revenue);
				ph.setRevenueLastYear(revenueLastYear);
				ph.setRevenueTarget(revenueTarget);
				ph.setCombinationKey(combinationKeyOd);
				ph.setMarketShare(marketShare);
				ph.setHostMarketShare(hostMarketShare);
				// ph.setHostEffectiveFares(hostEffectiveFares);
				// ph.setHostIneffectiveFares(hostIneffectiveFares);
				// Double ppi = Double.valueOf((double)
				// ThreadLocalRandom.current().nextInt(0, 151));
				// ph.setPpi(ppi);
				phMapOd.put(combinationKeyOd, ph);
			} else {
				PriceHeatMapODChannel ph = phMapOd.get(combinationKeyOd);
				ph.setRevenue(ph.getRevenue() + revenue);
				ph.setRevenueLastYear(ph.getRevenueLastYear() + revenueLastYear);
				ph.setRevenueTarget(revenueTarget + ph.getRevenueTarget());
				ph.setMarketShare(marketShare + ph.getMarketShare());
				ph.setHostMarketShare(hostMarketShare + ph.getHostMarketShare());
				// ph.setHostEffectiveFares(hostEffectiveFares +
				// ph.getHostEffectiveFares());
				// ph.setHostIneffectiveFares(hostIneffectiveFares +
				// ph.getHostIneffectiveFares());
			}

			if (!phMapChannel.containsKey(combinationKeyChannel)) {
				PriceHeatMapODChannel ph = new PriceHeatMapODChannel();
				ph.setChannel(channel);
				ph.setRevenue(revenue);
				ph.setRevenueLastYear(revenueLastYear);
				ph.setRevenueTarget(revenueTarget);
				ph.setCombinationKeyChannel(channel);
				ph.setMarketShare(marketShare);
				ph.setHostMarketShare(hostMarketShare);
				// ph.setHostEffectiveFares(hostEffectiveFares);
				// ph.setHostIneffectiveFares(hostIneffectiveFares);
				// Double ppi = Double.valueOf((double)
				// ThreadLocalRandom.current().nextInt(0, 151));
				// ph.setPpi(ppi);
				phMapChannel.put(channel, ph);
			} else {
				PriceHeatMapODChannel ph = phMapChannel.get(channel);
				ph.setRevenue(ph.getRevenue() + revenue);
				ph.setRevenueLastYear(ph.getRevenueLastYear() + revenueLastYear);
				ph.setRevenueTarget(revenueTarget + ph.getRevenueTarget());
				ph.setMarketShare(marketShare + ph.getMarketShare());
				ph.setHostMarketShare(hostMarketShare + ph.getHostMarketShare());
				// ph.setHostEffectiveFares(hostEffectiveFares +
				// ph.getHostEffectiveFares());
				// ph.setHostIneffectiveFares(hostIneffectiveFares +
				// ph.getHostIneffectiveFares());
			}

			if (!phMapSegment.containsKey(combinationKeySegment)) {
				PriceHeatMapODChannel ph = new PriceHeatMapODChannel();
				ph.setSegment(segment);
				ph.setRevenue(revenue);
				ph.setRevenueLastYear(revenueLastYear);
				ph.setRevenueTarget(revenueTarget);
				ph.setMarketShare(marketShare);
				ph.setHostMarketShare(hostMarketShare);
				// ph.setHostEffectiveFares(hostEffectiveFares);
				// ph.setHostIneffectiveFares(hostIneffectiveFares);
				// Double ppi = Double.valueOf((double)
				// ThreadLocalRandom.current().nextInt(0, 151));
				// ph.setPpi(ppi);
				phMapSegment.put(segment, ph);
			} else {
				PriceHeatMapODChannel ph = phMapSegment.get(segment);
				ph.setRevenue(ph.getRevenue() + revenue);
				ph.setRevenueLastYear(ph.getRevenueLastYear() + revenueLastYear);
				ph.setRevenueTarget(revenueTarget + ph.getRevenueTarget());
				ph.setMarketShare(marketShare + ph.getMarketShare());
				ph.setHostMarketShare(hostMarketShare + ph.getHostMarketShare());
				// ph.setHostEffectiveFares(hostEffectiveFares +
				// ph.getHostEffectiveFares());
				// ph.setHostIneffectiveFares(hostIneffectiveFares +
				// ph.getHostIneffectiveFares());
			}
		}
		for (String key : phMapOd.keySet()) {
			PriceHeatMapODChannel ph = phMapOd.get(key);
			Double revenueVLYR = 0D;
			if (ph.getRevenueLastYear() != 0) {
				revenueVLYR = ((ph.getRevenue() - ph.getRevenueLastYear()) / ph.getRevenueLastYear()) * 100;
			}
			Double revenueVTGT = 0D;
			if (ph.getRevenueTarget() != 0) {
				revenueVTGT = ((ph.getRevenue() - ph.getRevenueTarget()) / ph.getRevenueTarget()) * 100;
			}
			Double realMarketShare = 0D;
			if (ph.getMarketShare() != 0)
				realMarketShare = (ph.getHostMarketShare() / ph.getMarketShare()) * 100;
			ph.setRevenueVLYR(revenueVLYR);
			ph.setRevenueVTGT(revenueVTGT);
			ph.setMarketShare(realMarketShare);
			ph.setPpi((double) (CalculationUtil.calculatePricePerformance(revenueVTGT.floatValue(), 0)));
			// data unavailable in DB
			ph.setFMS(0D);
		}

		for (String key : phMapChannel.keySet()) {
			PriceHeatMapODChannel ph = phMapChannel.get(key);
			Double revenueVLYR = 0D;
			if (ph.getRevenueLastYear() != 0) {
				revenueVLYR = ((ph.getRevenue() - ph.getRevenueLastYear()) / ph.getRevenueLastYear()) * 100;
			}
			Double revenueVTGT = 0D;
			if (ph.getRevenueTarget() != 0) {
				revenueVTGT = ((ph.getRevenue() - ph.getRevenueTarget()) / ph.getRevenueTarget()) * 100;
			}
			Double realMarketShare = 0D;
			if (ph.getMarketShare() != 0)
				realMarketShare = (ph.getHostMarketShare() / ph.getMarketShare()) * 100;
			ph.setRevenueVLYR(revenueVLYR);
			ph.setRevenueVTGT(revenueVTGT);
			ph.setPpi((double) (CalculationUtil.calculatePricePerformance(revenueVTGT.floatValue(), 0)));
			ph.setMarketShare(realMarketShare);
			// data unavailable in DB
			ph.setFMS(0D);
		}

		for (String key : phMapSegment.keySet()) {
			PriceHeatMapODChannel ph = phMapSegment.get(key);
			Double revenueVLYR = 0D;
			if (ph.getRevenueLastYear() != 0) {
				revenueVLYR = ((ph.getRevenue() - ph.getRevenueLastYear()) / ph.getRevenueLastYear()) * 100;
			}
			Double revenueVTGT = 0D;
			if (ph.getRevenueTarget() != 0) {
				revenueVTGT = ((ph.getRevenue() - ph.getRevenueTarget()) / ph.getRevenueTarget()) * 100;
			}
			Double realMarketShare = 0D;
			if (ph.getMarketShare() != 0)
				realMarketShare = (ph.getHostMarketShare() / ph.getMarketShare()) * 100;
			ph.setRevenueVLYR(revenueVLYR);
			ph.setRevenueVTGT(revenueVTGT);
			ph.setPpi((double) (CalculationUtil.calculatePricePerformance(revenueVTGT.floatValue(), 0)));
			ph.setMarketShare(realMarketShare);
			// data unavailable in DB
			ph.setFMS(0D);
		}

		List<PriceHeatMapODChannel> phOdList = new ArrayList<PriceHeatMapODChannel>(phMapOd.values());
		List<PriceHeatMapODChannel> phChannelList = new ArrayList<PriceHeatMapODChannel>(phMapChannel.values());
		List<PriceHeatMapODChannel> phSegmentList = new ArrayList<PriceHeatMapODChannel>(phMapSegment.values());

		priceHeatOdChannelMap.put("priceHeatODMap", phOdList);
		priceHeatOdChannelMap.put("priceHeatChannelMap", phChannelList);
		priceHeatOdChannelMap.put("priceHeatSegmentMap", phSegmentList);
		return priceHeatOdChannelMap;
	}

	@Override
	public Map<String, Object> getPriceHeatMap_CustomerSegment(RequestModel mRequestModel) {
		BasicDBObject lPriceHeatMap_CustomerSegmentObj = priceBiometricDao
				.getPriceHeatMap_CustomerSegment(mRequestModel);

		JSONArray data = null;
		if (lPriceHeatMap_CustomerSegmentObj.containsKey("_batch")) {
			data = new JSONArray(lPriceHeatMap_CustomerSegmentObj.get("_batch").toString());
		}
		Map<String, PriceHeatMapSegment> phMap = new HashMap<String, PriceHeatMapSegment>();
		Map<String, Object> PriceHeatMap = new HashMap<String, Object>();
		if (data != null) {
			for (int i = 0; i < data.length(); i++) {
				JSONObject jsonObj = data.getJSONObject(i);
				String segment = "-";
				if (jsonObj.get("segment") != null && !jsonObj.get("segment").toString().equalsIgnoreCase("null"))
					segment = jsonObj.getString("segment");
				Double revenue = 0D;
				if (jsonObj.get("Host_revenue") != null
						&& !jsonObj.get("Host_revenue").toString().equalsIgnoreCase("null"))
					revenue = jsonObj.getDouble("Host_revenue");
				Double revenueLastYear = 0D;
				if (jsonObj.get("Host_revenue_last_year") != null
						&& !jsonObj.get("Host_revenue_last_year").toString().equalsIgnoreCase("null"))
					revenueLastYear = jsonObj.getDouble("Host_revenue_last_year");
				Double revenueTarget = 0D;
				if (jsonObj.get("Host_revenue_target") != null
						&& !jsonObj.get("Host_revenue_target").toString().equalsIgnoreCase("null"))
					revenueTarget = Utility.findSum(jsonObj.get("Host_revenue_target"));
				int hostEffectiveFares = 0;
				if (jsonObj.get("Host_effective_fares") != null
						&& !jsonObj.get("Host_effective_fares").toString().equalsIgnoreCase("null"))
					hostEffectiveFares = jsonObj.getInt("Host_effective_fares");
				int hostIneffectiveFares = 0;
				if (jsonObj.get("Host_ineffective_fares") != null
						&& !jsonObj.get("Host_ineffective_fares").toString().equalsIgnoreCase("null"))
					hostIneffectiveFares = jsonObj.getInt("Host_ineffective_fares");
				String combinationKey = segment;
				if (!phMap.containsKey(combinationKey)) {
					PriceHeatMapSegment ph = new PriceHeatMapSegment();
					ph.setSegment(segment);
					;
					ph.setRevenue(revenue);
					ph.setRevenueLastYear(revenueLastYear);
					ph.setRevenueTarget(revenueTarget);
					ph.setCombinationKey(combinationKey);
					ph.setHostEffectiveFares(hostEffectiveFares);
					ph.setHostIneffectiveFares(hostIneffectiveFares);
					Double ppi = Double.valueOf((double) ThreadLocalRandom.current().nextInt(0, 151));
					ph.setPpi(ppi);
					phMap.put(combinationKey, ph);
				} else {
					PriceHeatMapSegment ph = phMap.get(combinationKey);
					ph.setRevenue(ph.getRevenue() + revenue);
					ph.setRevenueLastYear(ph.getRevenueLastYear() + revenueLastYear);
					ph.setRevenueTarget(revenueTarget + ph.getRevenueTarget());
					ph.setHostEffectiveFares(hostEffectiveFares + ph.getHostEffectiveFares());
					ph.setHostIneffectiveFares(hostIneffectiveFares + ph.getHostIneffectiveFares());
				}
			}
		}
		for (String key : phMap.keySet()) {
			PriceHeatMapSegment ph = phMap.get(key);
			Double revenueVLYR = 0D;
			if (ph.getRevenueLastYear() != 0) {
				revenueVLYR = ((ph.getRevenue() - ph.getRevenueLastYear()) / ph.getRevenueLastYear()) * 100;
			}
			Double revenueVTGT = 0D;
			if (ph.getRevenueTarget() != 0) {
				revenueVTGT = ((ph.getRevenue() - ph.getRevenueTarget()) / ph.getRevenueTarget()) * 100;
			}
			ph.setRevenueVLYR(revenueVLYR);
			ph.setRevenueVTGT(revenueVTGT);
			// need clarification so setting it to zero
			ph.setMarketShare(ph.getMarketShare());
			// data unavailable in DB
			ph.setFMS(0D);
		}
		List<PriceHeatMapSegment> phList = new ArrayList<PriceHeatMapSegment>(phMap.values());
		PriceHeatMap.put("priceHeatMapSegment", phList);
		return PriceHeatMap;
	}

	@Override
	public Map<String, Object> getPriceHeatMapChannels(RequestModel mRequestModel) {
		BasicDBObject lPriceHeatMap_ChannelsObj = priceBiometricDao.getPriceHeatMapChannels(mRequestModel);
		JSONArray data = null;
		if (lPriceHeatMap_ChannelsObj.containsKey("_batch")) {
			data = new JSONArray(lPriceHeatMap_ChannelsObj.get("_batch").toString());
		}
		Map<String, PriceHeatMapChannel> phMap = new HashMap<String, PriceHeatMapChannel>();
		Map<String, Object> PriceHeatMap = new HashMap<String, Object>();
		if (data != null) {
			for (int i = 0; i < data.length(); i++) {
				JSONObject jsonObj = data.getJSONObject(i);
				String channel = "-";
				if (jsonObj.get(ApplicationConstants.CHANNEL) != null
						&& !jsonObj.get(ApplicationConstants.CHANNEL).toString().equalsIgnoreCase("null"))
					channel = jsonObj.getString(ApplicationConstants.CHANNEL);
				Double revenue = 0D;
				if (jsonObj.get("Host_revenue") != null
						&& !jsonObj.get("Host_revenue").toString().equalsIgnoreCase("null"))
					revenue = jsonObj.getDouble("Host_revenue");
				Double revenueLastYear = 0D;
				if (jsonObj.get("Host_revenue_last_year") != null
						&& !jsonObj.get("Host_revenue_last_year").toString().equalsIgnoreCase("null"))
					revenueLastYear = jsonObj.getDouble("Host_revenue_last_year");
				Double revenueTarget = 0D;
				if (jsonObj.get("Host_revenue_target") != null
						&& !jsonObj.get("Host_revenue_target").toString().equalsIgnoreCase("null"))
					revenueTarget = Utility.findSum(jsonObj.get("Host_revenue_target"));
				int hostEffectiveFares = 0;
				if (jsonObj.get("Host_effective_fares") != null
						&& !jsonObj.get("Host_effective_fares").toString().equalsIgnoreCase("null"))
					hostEffectiveFares = jsonObj.getInt("Host_effective_fares");
				int hostIneffectiveFares = 0;
				if (jsonObj.get("Host_ineffective_fares") != null
						&& !jsonObj.get("Host_ineffective_fares").toString().equalsIgnoreCase("null"))
					hostIneffectiveFares = jsonObj.getInt("Host_ineffective_fares");
				String combinationKey = channel;
				if (!phMap.containsKey(combinationKey)) {
					PriceHeatMapChannel ph = new PriceHeatMapChannel();
					ph.setChannel(channel);
					ph.setRevenue(revenue);
					ph.setRevenueLastYear(revenueLastYear);
					ph.setRevenueTarget(revenueTarget);
					ph.setCombinationKey(combinationKey);
					ph.setHostEffectiveFares(hostEffectiveFares);
					ph.setHostIneffectiveFares(hostIneffectiveFares);
					Double ppi = Double.valueOf((double) ThreadLocalRandom.current().nextInt(0, 151));
					ph.setPpi(ppi);
					phMap.put(combinationKey, ph);
				} else {
					PriceHeatMapChannel ph = phMap.get(combinationKey);
					ph.setRevenue(ph.getRevenue() + revenue);
					ph.setRevenueLastYear(ph.getRevenueLastYear() + revenueLastYear);
					ph.setRevenueTarget(revenueTarget + ph.getRevenueTarget());
					ph.setHostEffectiveFares(hostEffectiveFares + ph.getHostEffectiveFares());
					ph.setHostIneffectiveFares(hostIneffectiveFares + ph.getHostIneffectiveFares());
				}
			}
		}
		for (String key : phMap.keySet()) {
			PriceHeatMapChannel ph = phMap.get(key);
			Double revenueVLYR = 0D;
			if (ph.getRevenueLastYear() != 0) {
				revenueVLYR = ((ph.getRevenue() - ph.getRevenueLastYear()) / ph.getRevenueLastYear()) * 100;
			}
			Double revenueVTGT = 0D;
			if (ph.getRevenueTarget() != 0) {
				revenueVTGT = ((ph.getRevenue() - ph.getRevenueTarget()) / ph.getRevenueTarget()) * 100;
			}
			ph.setRevenueVLYR(revenueVLYR);
			ph.setRevenueVTGT(revenueVTGT);
			// need clarification so setting it to zero
			ph.setMarketShare(0D);
			// data unavailable in DB
			ph.setFMS(0D);
		}
		List<PriceHeatMapChannel> phList = new ArrayList<PriceHeatMapChannel>(phMap.values());
		PriceHeatMap.put("priceHeatMapChannel", phList);
		return PriceHeatMap;
	}

	@Override
	public BasicDBObject getPriceHeatMapFares(RequestModel mRequestModel) {
		BasicDBObject lPriceHeatMap_FaresObj = priceBiometricDao.getPriceHeatMapFares(mRequestModel);
		return lPriceHeatMap_FaresObj;
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
	public Map<String, Object> getVLYRPoc(RequestModel mRequestModel) {
		FilterModel lFilterModel = null;
		List<FilterModel> lBookingsVLYRList = new ArrayList<FilterModel>();
		List<VlyrPoc> lFinalList = new ArrayList<VlyrPoc>();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		ArrayList<DBObject> lBookingsList = priceBiometricDao.getVLYRPoc(mRequestModel);
		JSONArray lData = new JSONArray(lBookingsList);
		try {
			if (lData != null) {
				for (int i = 0; i < lData.length(); i++) {
					JSONObject lBookingsJsonObj = lData.getJSONObject(i);
					lFilterModel = new FilterModel();
					if (checkIfValueExist("dep_date", lBookingsJsonObj)) {
						lFilterModel
								.setDepartureDate(lBookingsJsonObj.get("dep_date").toString());
					}
					if (checkIfValueExist(ApplicationConstants.REGION, lBookingsJsonObj)) {
						lFilterModel.setRegion(lBookingsJsonObj.get(ApplicationConstants.REGION).toString());
					}
					if (checkIfValueExist(ApplicationConstants.COUNTRY, lBookingsJsonObj)) {
						lFilterModel.setCountry(lBookingsJsonObj.get(ApplicationConstants.COUNTRY).toString());
					}
					if (checkIfValueExist(ApplicationConstants.POS, lBookingsJsonObj)) {
						lFilterModel.setPos(lBookingsJsonObj.get(ApplicationConstants.POS).toString());
					}
					if (checkIfValueExist(ApplicationConstants.OD, lBookingsJsonObj)) {
						lFilterModel.setOd(lBookingsJsonObj.get(ApplicationConstants.OD).toString());
					}
					if (checkIfValueExist(ApplicationConstants.COMPARTMENT, lBookingsJsonObj)) {
						lFilterModel.setCompartment(lBookingsJsonObj.get(ApplicationConstants.COMPARTMENT).toString());
					}
					if (checkIfValueExist("pax", lBookingsJsonObj)) {
						lFilterModel.setHostBookings(lBookingsJsonObj.get("pax").toString());
					}

					if (checkIfValueExist("capacity", lBookingsJsonObj)) {
						lFilterModel.setCapacityFZ(Float.parseFloat(lBookingsJsonObj.get("capacity").toString()));
					}

					String lPrev_year = Integer.toString(Utility.getCurrentYear() - 1);
					if (lFilterModel.getDepartureDate().contains(lPrev_year)) {
						lFilterModel.setHostBookings_lastyr(lBookingsJsonObj.get("pax").toString());
						lFilterModel.setCapacityLY_FZ(Float.parseFloat(lBookingsJsonObj.get("capacity").toString()));
					} else {
						lFilterModel.setHostBookings_lastyr("0");
						lFilterModel.setCapacityLY_FZ(0);
					}

					lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
							+ lFilterModel.getPos() + lFilterModel.getOd() + lFilterModel.getCompartment());
					lBookingsVLYRList.add(lFilterModel);
				}
			}
			Map<String, VlyrPoc> map = new HashMap<String, VlyrPoc>();
			VlyrPoc lModel = null;
			if (lBookingsVLYRList.size() > 0) {
				for (FilterModel lObj : lBookingsVLYRList) {
					if (!map.containsKey(lObj.getFilterKey())) {
						lModel = new VlyrPoc();
						lModel.setDepartureDate(lObj.getDepartureDate());
						lModel.setRegion(lObj.getRegion());
						lModel.setCountry(lObj.getCountry());
						lModel.setPos(lObj.getPos());
						lModel.setOd(lObj.getOd());
						lModel.setCompartment(lObj.getCompartment());

						lModel.setCapacityFZ((lObj.getCapacityFZ()));
						lModel.setCapacityFZ_LY(lObj.getCapacityLY_FZ());

						// Host Bookings
						lModel.setHostBookings((int) Float.parseFloat(lObj.getHostBookings()));

						// Host Bookings_Lastyr
						lModel.setHostBookings_lastyr((int) Float.parseFloat(lObj.getHostBookings_lastyr()));
						map.put(lObj.getFilterKey(), lModel);
					} else {
						for (String lKey : map.keySet()) {
							if (lObj.getFilterKey().equals(lKey)) {
								lModel = map.get(lKey);
							}
							// Host Bookings
							float totalBookings = Float.parseFloat(lObj.getHostBookings()) + lModel.getHostBookings();
							lModel.setHostBookings(totalBookings);

							// Host Bookings_Lastyr
							float totalBookings_lastyr = Float.parseFloat(lObj.getHostBookings_lastyr())
									+ lModel.getHostBookings_lastyr();
							lModel.setHostBookings_lastyr(totalBookings_lastyr);
							
							float capacity_FZ=lObj.getCapacityFZ() + lModel.getCapacityFZ();
							lModel.setCapacityFZ(capacity_FZ);
							float capacityLY_FZ=lObj.getCapacityLY_FZ() + lModel.getCapacityFZ_LY();
							lModel.setCapacityFZ_LY(capacityLY_FZ);
						}
					}
				}
			}
			
			VlyrPoc lResponse = null;
			for (String key : map.keySet()) {
				lResponse = new VlyrPoc();
				// Region
				lResponse.setRegion(map.get(key).getRegion());
				// Country
				lResponse.setCountry(map.get(key).getCountry());
				// Pos
				lResponse.setPos(map.get(key).getPos());
				// Od
				lResponse.setOd(map.get(key).getOd());
				// Compartment
				lResponse.setCompartment(map.get(key).getCompartment());
				
				float lBookingsVLYR = 0;
				if (map.get(key).getHostBookings_lastyr() > 0) {
					lBookingsVLYR = CalculationUtil.calculateVLYR(map.get(key).getHostBookings(), map.get(key).getHostBookings_lastyr(), map.get(key).getCapacityFZ(),
							map.get(key).getCapacityFZ_LY());
				} else {
					lBookingsVLYR = 0;
				}
				lResponse.setHostBookings_VLYR(Math.round(lBookingsVLYR));
				
				lFinalList.add(lResponse);
				
			}
			responseMap.put("VLYR", lFinalList);

		} catch (Exception e) {
			logger.error("getVLYRPoc-Exception", e);
		}
		return responseMap;
	}

}
