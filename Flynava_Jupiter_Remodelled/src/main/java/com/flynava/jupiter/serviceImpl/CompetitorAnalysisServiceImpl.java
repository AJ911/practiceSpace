package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.CompetitorAnalysisDao;
import com.flynava.jupiter.daoInterface.MainDashboardDao;
import com.flynava.jupiter.model.EntrantsLeavers;
import com.flynava.jupiter.model.FareAnalysis;
import com.flynava.jupiter.model.Fares;
import com.flynava.jupiter.model.FaresSellup;
import com.flynava.jupiter.model.FlightAnalysis;
import com.flynava.jupiter.model.Flight_Agents_Friends;
import com.flynava.jupiter.model.MarketIndicatorSummary;
import com.flynava.jupiter.model.MarketIndicatorTopODs;
import com.flynava.jupiter.model.MonthlyLowFare;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.ScheduleDirect;
import com.flynava.jupiter.model.ScheduleIndirect;
import com.flynava.jupiter.model.UserProfile;
import com.flynava.jupiter.serviceInterface.CompetitorAnalysisService;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Utility;
import com.mongodb.DBObject;

@Service
public class CompetitorAnalysisServiceImpl implements CompetitorAnalysisService {

	@Autowired
	CompetitorAnalysisDao mCompAnalysisDao;

	@Autowired
	MainDashboardDao mMainDashboardDao;

	private static final Logger logger = Logger.getLogger(CompetitorAnalysisServiceImpl.class);

	@Override
	public List<Object> getProductIndicator(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);
		return mCompAnalysisDao.getProductIndicator(lUserProfileModel);
	}

	@Override
	public Map<String, Object> getFares(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);

		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getFares(pRequestModel, lUserProfileModel);
		JSONArray lDataJSONArray = null;
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);
		}

		Map<String, Object> lResponseMap = new HashMap<String, Object>();
		Map<String, Object> lFaresMap = new HashMap<String, Object>();
		List<Fares> lResultList = new ArrayList<Fares>();

		if (lDataJSONArray != null) {

			final String lEffectiveDate = lDataJSONArray.getJSONObject(0).get("effective_date").toString();

			Fares lFaresModel = null;
			ArrayList<Integer> lFaresList = null;
			ArrayList<String> lRbdList = null;

			for (int i = 0; i < lDataJSONArray.length(); i++) {

				JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);
				if (lEffectiveDate.equalsIgnoreCase(lJsonObj.get("effective_date").toString())) {

					String lCarrier = lJsonObj.get("carrier").toString();

					if (lResponseMap.containsKey(lCarrier)
							&& lCarrier.equalsIgnoreCase(((Fares) lResponseMap.get(lCarrier)).getCarrier())) {

						lFaresModel = (Fares) lResponseMap.get(lCarrier);
						lFaresList = lFaresModel.getFares();

						if (lJsonObj.has("carrier"))
							lFaresModel.setCarrier(lJsonObj.get("carrier").toString());

						if (lJsonObj.has("price_outbound"))
							lFaresList.add(Math.round(Float.parseFloat(lJsonObj.get("price_outbound").toString())));

						if (lJsonObj.has("outbound_booking_class"))
							lRbdList.add(lJsonObj.get("outbound_booking_class").toString());

						lFaresModel.setRbd(lRbdList);
						lFaresModel.setFares(lFaresList);

						lResponseMap.put(lFaresModel.getCarrier(), lFaresModel);

					} else {

						lFaresModel = new Fares();
						lFaresList = new ArrayList<Integer>();
						lRbdList = new ArrayList<String>();

						if (lJsonObj.has("carrier"))
							lFaresModel.setCarrier(lJsonObj.get("carrier").toString());

						if (lJsonObj.has("price_outbound"))
							lFaresList.add(Math.round(Float.parseFloat(lJsonObj.get("price_outbound").toString())));

						if (lJsonObj.has("outbound_booking_class"))
							lRbdList.add(lJsonObj.get("outbound_booking_class").toString());

						lFaresModel.setRbd(lRbdList);
						lFaresModel.setFares(lFaresList);

						lResponseMap.put(lJsonObj.get("carrier").toString(), lFaresModel);

					}

				} else {
					break;
				}

			}

			Set<String> lCarriers = lResponseMap.keySet();

			for (String s : lCarriers) {

				Fares lFaresModelResponse = ((Fares) lResponseMap.get(s));
				Fares lFaresModelResult = new Fares();

				ArrayList<Integer> lCarrierFareSList = lFaresModelResponse.getFares();

				Collections.sort(lCarrierFareSList);
				lFaresModelResult.setCarrier(lFaresModelResponse.getCarrier());
				if (lCarrierFareSList.size() != 0) {
					lFaresModelResult.setLowestFare(lCarrierFareSList.get(0));
					lFaresModelResult.setHighestFare(lCarrierFareSList.get(lCarrierFareSList.size() - 1));
					lFaresModelResult.setNoOfFares(lCarrierFareSList.size());
				}

				lResultList.add(lFaresModelResult);

			}

			lFaresMap.put("carrierFares", lResultList);
		}

		return lFaresMap;
	}

	@Override
	public List<MarketIndicatorSummary> getMarketIndicator(RequestModel pRequestModel) {
		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);
		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getMarketIndicator(pRequestModel, lUserProfileModel);
		JSONArray lDataJSONArray = null;
		List<MarketIndicatorSummary> lMarketIndicatorList = new ArrayList<MarketIndicatorSummary>();
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);
		}

		if (lDataJSONArray != null) {
			for (int i = 0; i < lDataJSONArray.length(); i++) {

				JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);
				MarketIndicatorSummary lMarketSummary = new MarketIndicatorSummary();
				Map<String, Object> lPosVsTotalMarketMap = new HashMap<String, Object>();
				Map<String, Object> lOverallPosGrowthMap = new HashMap<String, Object>();
				Map<String, Object> lPassengerPerDayMap = new HashMap<String, Object>();
				Map<String, Object> lMarketGrowthMap = new HashMap<String, Object>();
				Map<String, Object> lMarketSizeMap = new HashMap<String, Object>();

				float lPosVsTotalMarket = 0;
				float lOverallPosGrowth = 0;
				float lPassengerPerDay = 0;
				float lMarketGrowth = 0;
				float lMarketSize = 0;

				float lPosVsTotalMarket1 = 0;
				float lOverallPosGrowth1 = 0;
				float lPassengerPerDay1 = 0;
				float lMarketGrowth1 = 0;
				float lMarketSize1 = 0;

				float lPosVsTotalMarket2 = 0;
				float lOverallPosGrowth2 = 0;
				float lPassengerPerDay2 = 0;
				float lMarketGrowth2 = 0;
				float lMarketSize2 = 0;

				float lPosVsTotalMarket3 = 0;
				float lOverallPosGrowth3 = 0;
				float lPassengerPerDay3 = 0;
				float lMarketGrowth3 = 0;
				float lMarketSize3 = 0;

				if (lJsonObj.has("pos_total") && !"null".equalsIgnoreCase(lJsonObj.get("pos_total").toString())
						&& lJsonObj.has("host_pos") && !"null".equalsIgnoreCase(lJsonObj.get("host_pos").toString())
						&& Float.parseFloat(lJsonObj.get("pos_total").toString()) != 0) {

					lPosVsTotalMarket = CalculationUtil.doDivision(
							Float.parseFloat(lJsonObj.get("host_pos").toString()),
							Float.parseFloat(lJsonObj.get("pos_total").toString()));

				}

				if (lJsonObj.has("pos_total_1") && !"null".equalsIgnoreCase(lJsonObj.get("pos_total_1").toString())
						&& lJsonObj.has("host_pos_1") && !"null".equalsIgnoreCase(lJsonObj.get("host_pos_1").toString())
						&& Float.parseFloat(lJsonObj.get("pos_total_1").toString()) != 0) {

					lPosVsTotalMarket1 = CalculationUtil.doDivision(
							Float.parseFloat(lJsonObj.get("host_pos_1").toString()),
							Float.parseFloat(lJsonObj.get("pos_total_1").toString()));

				}

				if (lJsonObj.has("pos_total_2") && !"null".equalsIgnoreCase(lJsonObj.get("pos_total_2").toString())
						&& lJsonObj.has("host_pos_2") && !"null".equalsIgnoreCase(lJsonObj.get("host_pos_2").toString())
						&& Float.parseFloat(lJsonObj.get("pos_total_2").toString()) != 0) {

					lPosVsTotalMarket2 = CalculationUtil.doDivision(
							Float.parseFloat(lJsonObj.get("host_pos_2").toString()),
							Float.parseFloat(lJsonObj.get("pos_total_2").toString()));

				}

				if (lJsonObj.has("pos_total_3") && !"null".equalsIgnoreCase(lJsonObj.get("pos_total_3").toString())
						&& lJsonObj.has("host_pos_3") && !"null".equalsIgnoreCase(lJsonObj.get("host_pos_3").toString())
						&& Float.parseFloat(lJsonObj.get("pos_total_3").toString()) != 0) {

					lPosVsTotalMarket3 = CalculationUtil.doDivision(
							Float.parseFloat(lJsonObj.get("host_pos_3").toString()),
							Float.parseFloat(lJsonObj.get("pos_total_3").toString()));

				}

				if (lJsonObj.has("pos_total") && !"null".equalsIgnoreCase(lJsonObj.get("pos_total").toString())
						&& lJsonObj.has("pos_total_1")
						&& !"null".equalsIgnoreCase(lJsonObj.get("pos_total_1").toString())
						&& Float.parseFloat(lJsonObj.get("pos_total_1").toString()) != 0) {

					lOverallPosGrowth = CalculationUtil.calculateVLYR(
							Float.parseFloat(lJsonObj.get("pos_total").toString()),
							Float.parseFloat(lJsonObj.get("pos_total_1").toString()));

				}

				if (lJsonObj.has("pos_total_1") && !"null".equalsIgnoreCase(lJsonObj.get("pos_total_1").toString())
						&& lJsonObj.has("pos_total_2")
						&& !"null".equalsIgnoreCase(lJsonObj.get("pos_total_2").toString())
						&& Float.parseFloat(lJsonObj.get("pos_total_2").toString()) != 0) {

					lOverallPosGrowth2 = CalculationUtil.calculateVLYR(
							Float.parseFloat(lJsonObj.get("pos_total_1").toString()),
							Float.parseFloat(lJsonObj.get("pos_total_2").toString()));

				}

				if (lJsonObj.has("pos_total_2") && !"null".equalsIgnoreCase(lJsonObj.get("pos_total_2").toString())
						&& lJsonObj.has("pos_total_3")
						&& !"null".equalsIgnoreCase(lJsonObj.get("pos_total_3").toString())
						&& Float.parseFloat(lJsonObj.get("pos_total_3").toString()) != 0) {

					lOverallPosGrowth3 = CalculationUtil.calculateVLYR(
							Float.parseFloat(lJsonObj.get("pos_total_2").toString()),
							Float.parseFloat(lJsonObj.get("pos_total_3").toString()));

				}

				if (lJsonObj.has("passanger") && !"null".equalsIgnoreCase(lJsonObj.get("passanger").toString())) {

					lPassengerPerDay = Float.parseFloat(lJsonObj.get("passanger").toString());

				}

				if (lJsonObj.has("passanger_1") && !"null".equalsIgnoreCase(lJsonObj.get("passanger_1").toString())) {

					lPassengerPerDay1 = Float.parseFloat(lJsonObj.get("passanger_1").toString());

				}

				if (lJsonObj.has("passanger_2") && !"null".equalsIgnoreCase(lJsonObj.get("passanger_2").toString())) {

					lPassengerPerDay2 = Float.parseFloat(lJsonObj.get("passanger_2").toString());

				}

				if (lJsonObj.has("passanger_3") && !"null".equalsIgnoreCase(lJsonObj.get("passanger_3").toString())) {

					lPassengerPerDay3 = Float.parseFloat(lJsonObj.get("passanger_3").toString());

				}

				if (lJsonObj.has("Market_size") && !"null".equalsIgnoreCase(lJsonObj.get("Market_size").toString())
						&& lJsonObj.has("Market_size_1")
						&& !"null".equalsIgnoreCase(lJsonObj.get("Market_size_1").toString())
						&& Float.parseFloat(lJsonObj.get("Market_size_1").toString()) != 0) {

					lMarketGrowth = CalculationUtil.calculateVLYR(
							Float.parseFloat(lJsonObj.get("Market_size").toString()),
							Float.parseFloat(lJsonObj.get("Market_size_1").toString()));
				}

				if (lJsonObj.has("Market_size_1") && !"null".equalsIgnoreCase(lJsonObj.get("Market_size_1").toString())
						&& lJsonObj.has("Market_size_2")
						&& !"null".equalsIgnoreCase(lJsonObj.get("Market_size_2").toString())
						&& Float.parseFloat(lJsonObj.get("Market_size_2").toString()) != 0) {

					lMarketGrowth1 = CalculationUtil.calculateVLYR(
							Float.parseFloat(lJsonObj.get("Market_size_1").toString()),
							Float.parseFloat(lJsonObj.get("Market_size_2").toString()));
				}

				if (lJsonObj.has("Market_size_2") && !"null".equalsIgnoreCase(lJsonObj.get("Market_size_2").toString())
						&& lJsonObj.has("Market_size_3")
						&& !"null".equalsIgnoreCase(lJsonObj.get("Market_size_3").toString())
						&& Float.parseFloat(lJsonObj.get("Market_size_3").toString()) != 0) {

					lMarketGrowth2 = CalculationUtil.calculateVLYR(
							Float.parseFloat(lJsonObj.get("Market_size_2").toString()),
							Float.parseFloat(lJsonObj.get("Market_size_3").toString()));
				}

				if (lJsonObj.has("Market_size") && !"null".equalsIgnoreCase(lJsonObj.get("Market_size").toString())) {

					lMarketSize = Float.parseFloat(lJsonObj.get("Market_size").toString());

				}

				if (lJsonObj.has("Market_size_1")
						&& !"null".equalsIgnoreCase(lJsonObj.get("Market_size_1").toString())) {

					lMarketSize1 = Float.parseFloat(lJsonObj.get("Market_size_1").toString());

				}

				if (lJsonObj.has("Market_size_2")
						&& !"null".equalsIgnoreCase(lJsonObj.get("Market_size_2").toString())) {

					lMarketSize2 = Float.parseFloat(lJsonObj.get("Market_size_2").toString());

				}

				if (lJsonObj.has("Market_size_3")
						&& !"null".equalsIgnoreCase(lJsonObj.get("Market_size_3").toString())) {

					lMarketSize3 = Float.parseFloat(lJsonObj.get("Market_size_3").toString());

				}

				List<Integer> lPosVsTotalMarketList = new ArrayList<Integer>();
				lPosVsTotalMarketList.add(Math.round(lPosVsTotalMarket));
				lPosVsTotalMarketList.add(Math.round(lPosVsTotalMarket1));
				lPosVsTotalMarketList.add(Math.round(lPosVsTotalMarket2));
				lPosVsTotalMarketList.add(Math.round(lPosVsTotalMarket3));

				List<Integer> lOverallPosGrowthList = new ArrayList<Integer>();
				lOverallPosGrowthList.add(Math.round(lOverallPosGrowth));
				lOverallPosGrowthList.add(Math.round(lOverallPosGrowth1));
				lOverallPosGrowthList.add(Math.round(lOverallPosGrowth2));
				lOverallPosGrowthList.add(Math.round(lOverallPosGrowth3));

				List<Integer> lPassengerPerDayList = new ArrayList<Integer>();
				lPassengerPerDayList.add(Math.round(lPassengerPerDay));
				lPassengerPerDayList.add(Math.round(lPassengerPerDay1));
				lPassengerPerDayList.add(Math.round(lPassengerPerDay2));
				lPassengerPerDayList.add(Math.round(lPassengerPerDay3));

				List<Integer> lMarketGrowthList = new ArrayList<Integer>();
				lMarketGrowthList.add(Math.round(lMarketGrowth));
				lMarketGrowthList.add(Math.round(lMarketGrowth1));
				lMarketGrowthList.add(Math.round(lMarketGrowth2));
				lMarketGrowthList.add(Math.round(lMarketGrowth3));

				List<Integer> lMarketSizeList = new ArrayList<Integer>();
				lMarketSizeList.add(Math.round(lMarketSize));
				lMarketSizeList.add(Math.round(lMarketSize1));
				lMarketSizeList.add(Math.round(lMarketSize2));
				lMarketSizeList.add(Math.round(lMarketSize3));

				lMarketSummary.setPOS_vs_TotalMarket(lPosVsTotalMarketList);
				lMarketSummary.setOverall_POS_Growth(lOverallPosGrowthList);
				lMarketSummary.setMarket_Growth(lMarketGrowthList);
				lMarketSummary.setMarketSize(lMarketSizeList);
				lMarketSummary.setPassenger_Per_Day(lPassengerPerDayList);

				lMarketIndicatorList.add(lMarketSummary);

			}
		}

		return lMarketIndicatorList;

	}

	@Override
	public Map<String, Object> getMarketIndicatorTopODs(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);

		Map<String, Object> lResponseTopODMap = new HashMap<String, Object>();

		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getMarketIndicatorTopODs(pRequestModel,
				lUserProfileModel);
		JSONArray lDataJSONArray = null;
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);
		}

		try {

			if (lDataJSONArray != null) {

				Map<String, Object> lResponseGridMap = new HashMap<String, Object>();

				lResponseGridMap = getCalculatedMarketIndicatorTopODs(lDataJSONArray, pRequestModel, lUserProfileModel);
				lResponseTopODMap.put("Grid", lResponseGridMap);

			} else {

				lResponseTopODMap.put("Grid", null);
			}
		} catch (Exception e) {
			logger.error("getMarketIndicatorTopODs-Exception", e);
		}

		return lResponseTopODMap;
	}

	public Map<String, Object> getCalculatedMarketIndicatorTopODs(JSONArray lDataJSONArray, RequestModel pRequestModel,
			UserProfile lUserProfileModel) {

		Map<String, Object> lUserDataMap = new HashMap<String, Object>();
		Map<String, Object> lResultMap = new HashMap<String, Object>();
		MarketIndicatorTopODs lTopODsModel = null;
		Map<String, Object> lCompMap = null;
		List<MarketIndicatorTopODs> lKeyList = new ArrayList<MarketIndicatorTopODs>();
		try {

			if (lDataJSONArray != null) {

				for (int i = 0; i < lDataJSONArray.length(); i++) {

					JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);

					String lKey = lJsonObj.get("od").toString();

					if (lResultMap.containsKey(lKey)
							&& ((MarketIndicatorTopODs) lResultMap.get(lKey)).getKey().equalsIgnoreCase(lKey)) {

						lTopODsModel = (MarketIndicatorTopODs) lResultMap.get(lKey);
						JSONArray lCarrierJSONArray = null;
						if (lJsonObj.has("carrier"))
							lCarrierJSONArray = new JSONArray(lJsonObj.get("carrier").toString());

						JSONArray lMarketSharePaxJSONArray = null;
						if (lJsonObj.has("market_share_pax"))
							lMarketSharePaxJSONArray = new JSONArray(lJsonObj.get("market_share_pax").toString());

						JSONArray lMarketSharePaxlastyrJSONArray = null;
						if (lJsonObj.has("market_share_pax_1"))
							lMarketSharePaxlastyrJSONArray = new JSONArray(
									lJsonObj.get("market_share_pax_1").toString());

						JSONArray lMarketSizeJSONArray = null;
						if (lJsonObj.has("market_share_pax"))
							lMarketSizeJSONArray = lMarketSharePaxJSONArray;

						float lMarketSize = 0;
						if (lMarketSizeJSONArray != null && lMarketSizeJSONArray.length() != 0) {
							for (int m = 0; m < lMarketSizeJSONArray.length(); m++) {
								if (!"[]".equalsIgnoreCase(lMarketSizeJSONArray.get(m).toString())
										&& !"null".equalsIgnoreCase(lMarketSizeJSONArray.get(m).toString()))
									lMarketSize += Float.parseFloat(lMarketSizeJSONArray.get(m).toString());
							}
						}

						JSONArray lMarketSizelastyrJSONArray = null;
						if (lJsonObj.has("market_share_pax_1"))
							lMarketSizelastyrJSONArray = lMarketSharePaxlastyrJSONArray;

						float lMarketSizelastyr = 0;
						if (lMarketSizelastyrJSONArray != null && lMarketSizelastyrJSONArray.length() != 0) {
							for (int m = 0; m < lMarketSizelastyrJSONArray.length(); m++) {
								if (!"[]".equalsIgnoreCase(lMarketSizelastyrJSONArray.get(m).toString())
										&& !"null".equalsIgnoreCase(lMarketSizelastyrJSONArray.get(m).toString()))
									lMarketSizelastyr += Float.parseFloat(lMarketSizelastyrJSONArray.get(m).toString());
							}
						}

						JSONArray lRevenueFlownJSONArray = null;
						if (lJsonObj.has("host_revenue_flown"))
							lRevenueFlownJSONArray = new JSONArray(lJsonObj.get("host_revenue_flown").toString());

						JSONArray lRevenueFlownlastyrJSONArray = null;
						if (lJsonObj.has("host_revenue_flown_1"))
							lRevenueFlownlastyrJSONArray = new JSONArray(
									lJsonObj.get("host_revenue_flown_1").toString());

						JSONArray lRevenueTargetJSONArray = null;
						if (lJsonObj.has("host_revenue_target"))
							lRevenueTargetJSONArray = new JSONArray(lJsonObj.get("host_revenue_target").toString());

						JSONArray lFlownPaxJSONArray = null;
						if (lJsonObj.has("flown_pax"))
							lFlownPaxJSONArray = new JSONArray(lJsonObj.get("flown_pax").toString());

						JSONArray lFlownPaxlastyrJSONArray = null;
						if (lJsonObj.has("flown_pax_1"))
							lFlownPaxlastyrJSONArray = new JSONArray(lJsonObj.get("flown_pax_1").toString());

						JSONArray lHostPaxTargetJSONArray = null;
						if (lJsonObj.has("host_pax_target"))
							lHostPaxTargetJSONArray = new JSONArray(lJsonObj.get("host_pax_target").toString());

						float lRevenueFlown = 0;
						if (lRevenueFlownJSONArray != null) {
							for (int r = 0; r < lRevenueFlownJSONArray.length(); r++) {
								if (!"[]".equalsIgnoreCase(lRevenueFlownJSONArray.get(r).toString())
										&& !"null".equalsIgnoreCase(lRevenueFlownJSONArray.get(r).toString()))
									lRevenueFlown += Float.parseFloat(lRevenueFlownJSONArray.get(r).toString());
							}
						}

						float lRevenueFlownlastyr = 0;
						if (lRevenueFlownJSONArray != null) {
							for (int r = 0; r < lRevenueFlownlastyrJSONArray.length(); r++) {
								if (!"[]".equalsIgnoreCase(lRevenueFlownlastyrJSONArray.get(r).toString())
										&& !"null".equalsIgnoreCase(lRevenueFlownlastyrJSONArray.get(r).toString()))
									lRevenueFlownlastyr += Float
											.parseFloat(lRevenueFlownlastyrJSONArray.get(r).toString());
							}
						}

						float lRevenueTarget = 0;
						if (lRevenueTargetJSONArray != null) {
							for (int r = 0; r < lRevenueTargetJSONArray.length(); r++) {
								if (!"[]".equalsIgnoreCase(lRevenueTargetJSONArray.get(r).toString())
										&& !"null".equalsIgnoreCase(lRevenueTargetJSONArray.get(r).toString()))
									lRevenueTarget += Float.parseFloat(lRevenueTargetJSONArray.get(r).toString());
							}
						}

						float lSalePax = Float.parseFloat(lJsonObj.get("sale_pax").toString());
						float lSalePaxlastyr = Float.parseFloat(lJsonObj.get("sale_pax_last_year").toString());

						float lFlownPax = 0;
						if (lFlownPaxJSONArray != null) {
							for (int r = 0; r < lFlownPaxJSONArray.length(); r++) {
								if (!"[]".equalsIgnoreCase(lFlownPaxJSONArray.get(r).toString())
										&& !"null".equalsIgnoreCase(lFlownPaxJSONArray.get(r).toString()))
									lFlownPax += Float.parseFloat(lFlownPaxJSONArray.get(r).toString());
							}
						}

						float lFlownPaxlastyr = 0;
						if (lFlownPaxlastyrJSONArray != null) {
							for (int r = 0; r < lFlownPaxlastyrJSONArray.length(); r++) {
								if (!"[]".equalsIgnoreCase(lFlownPaxlastyrJSONArray.get(r).toString())
										&& !"null".equalsIgnoreCase(lFlownPaxlastyrJSONArray.get(r).toString()))
									lFlownPaxlastyr += Float.parseFloat(lFlownPaxlastyrJSONArray.get(r).toString());
							}

						}

						float lPaxTarget = 0;
						if (lHostPaxTargetJSONArray != null) {
							for (int r = 0; r < lHostPaxTargetJSONArray.length(); r++) {
								if (!"[]".equalsIgnoreCase(lHostPaxTargetJSONArray.get(r).toString())
										&& !"null".equalsIgnoreCase(lHostPaxTargetJSONArray.get(r).toString()))
									lPaxTarget += Float.parseFloat(lHostPaxTargetJSONArray.get(r).toString());
							}
						}

						float lSaleRevenue = 0;
						if (lJsonObj.has("sale_revenue"))
							lSaleRevenue = Float.parseFloat(lJsonObj.get("sale_revenue").toString());

						lSalePax += lTopODsModel.getSalePax();
						lSalePaxlastyr += lTopODsModel.getSalePax_1();
						lFlownPax += lTopODsModel.getFlownPax();
						lFlownPaxlastyr += lTopODsModel.getFlownPax_1();
						lRevenueFlown += lTopODsModel.getHostRevenueFlown();
						lRevenueFlownlastyr += lTopODsModel.getHostRevenueflown_1();
						lSaleRevenue += lTopODsModel.getSaleRevenue();

						float lMonth = 0;
						if (lJsonObj.has("dep_date") && !"null".equalsIgnoreCase(lJsonObj.get("dep_date").toString()))
							lMonth = (float) Utility.findMonth(lJsonObj.get("dep_date").toString());

						if (lTopODsModel.getMonth() != lMonth) {

							lMarketSize += lTopODsModel.getMarketSize();
							lMarketSizelastyr += lTopODsModel.getMarketSize_1();
							lPaxTarget += lTopODsModel.getHostTargetPax();
							lRevenueTarget += lTopODsModel.getHostRevenueTarget();

						}

						float lRevenueYTD = lSaleRevenue;

						float lRevnueVLYR = 00.0F;
						if (lRevenueFlownlastyr != 0)
							lRevnueVLYR = CalculationUtil.calculateVLYR(lSaleRevenue, lRevenueFlownlastyr);

						float lRevenueVTGT = 0.0F;
						if (lRevenueTarget != 0)
							lRevenueVTGT = CalculationUtil.calculateVTGT(lSaleRevenue, lRevenueTarget);

						float lPaxYTD = lSalePax;

						float lPaxVLYR = 0.0F;
						if (lSalePaxlastyr != 0)
							lPaxVLYR = CalculationUtil.calculateVLYR(lSalePax, lSalePaxlastyr);

						float lPaxVTGT = 0.0f;
						if (lPaxTarget != 0)
							lPaxVTGT = CalculationUtil.calculateVTGT(lSalePax, lPaxTarget);

						if (lCarrierJSONArray != null && lUserProfileModel != null) {

							for (int t = 0; t < lUserProfileModel.getCompetitors().length; t++) {
								for (int c = 0; c < lCarrierJSONArray.length(); c++) {

									String lCarrier = lCarrierJSONArray.get(c).toString();
									String lComp = lUserProfileModel.getCompetitors()[t].replace("\"", "");

									if (lCarrier.equalsIgnoreCase(lComp)
											|| lCarrier.equalsIgnoreCase(Constants.hostName)) {

										Map<String, Object> lCompetitors = new HashMap<String, Object>();

										float lMarketSharePaxlastyr = 0;
										if (lMarketSharePaxlastyrJSONArray.length() != 0)
											lMarketSharePaxlastyr = Float
													.parseFloat(lMarketSharePaxlastyrJSONArray.get(c).toString());

										float lMarketSharePax = 0;
										if (lMarketSharePaxJSONArray.length() != 0)
											lMarketSharePax = Float
													.parseFloat(lMarketSharePaxJSONArray.get(c).toString());

										if (lTopODsModel.getMonth() != lMonth) {

											if (lTopODsModel.getCompetitorsMap() != null
													&& ((Map) lTopODsModel.getCompetitorsMap()).containsKey(lCarrier)
													&& (Map) ((Map) (lTopODsModel).getCompetitorsMap())
															.get(lCarrier) != null) {
												lMarketSharePax += Float.parseFloat(
														((Map) ((Map) (lTopODsModel).getCompetitorsMap()).get(lCarrier))
																.get("marketSharePax").toString());

												lMarketSharePaxlastyr += Float.parseFloat(
														((Map) ((Map) (lTopODsModel).getCompetitorsMap()).get(lCarrier))
																.get("marketSharePax_1").toString());

											}

										}

										float lCarrierFMS = 0;

										float lCarrierBookingsYTD = lMarketSharePax;

										float lCarrierBookingsVLYR = 0.0f;
										if (lMarketSharePaxlastyr != 0)
											lCarrierBookingsVLYR = CalculationUtil.calculateVLYR(lMarketSharePax,
													lMarketSharePaxlastyr);

										float lCarrierMarketShareYTD = 0;
										if (lMarketSize != 0)
											lCarrierMarketShareYTD = CalculationUtil.doDivision(lMarketSharePax,
													lMarketSize) * 100;

										float lCarrierMarketShare = 0.0f;
										if (lMarketSize != 0)
											lCarrierMarketShare = CalculationUtil.doDivision(lMarketSharePax,
													lMarketSize);

										float lCarrierMarketSharelastyr = 0.0f;
										if (lMarketSizelastyr != 0)
											lCarrierMarketSharelastyr = CalculationUtil
													.doDivision(lMarketSharePaxlastyr, lMarketSizelastyr);

										float lCarrierMarketShareVLYR = 0;
										if (lCarrierMarketSharelastyr != 0)
											lCarrierMarketShareVLYR = CalculationUtil.calculateVLYR(lCarrierMarketShare,
													lCarrierMarketSharelastyr);

										float lCarrierMarketShareVTGT = 0;
										if (lCarrierFMS != 0)
											lCarrierMarketShareVTGT = CalculationUtil.calculateVTGT(lCarrierMarketShare,
													lCarrierFMS);

										lCompetitors.put("name", lCarrier);
										lCompetitors.put("marketSharePax", Math.round(lMarketSharePax));
										lCompetitors.put("marketSharePax_1", Math.round(lMarketSharePaxlastyr));
										lCompetitors.put("FMS", Math.round(lCarrierFMS));
										lCompetitors.put("BookingsYTD", Math.round(lCarrierBookingsYTD));
										lCompetitors.put("BookingsVLYR", Math.round(lCarrierBookingsVLYR));
										lCompetitors.put("MarketShare", Math.round(lCarrierMarketShare));
										lCompetitors.put("MarketShare", Math.round(lCarrierMarketSharelastyr));
										lCompetitors.put("MarketShareYTD", Math.round(lCarrierMarketShareYTD));
										lCompetitors.put("MarketShareVLYR", Math.round(lCarrierMarketShareVLYR));
										lCompetitors.put("MarketShareVTGT", Math.round(lCarrierMarketShareVTGT));

										lCompMap.put(lCarrier, lCompetitors);

									}
								}
							}
						}

						if (lJsonObj.has("origin"))
							lTopODsModel.setOrigin(lJsonObj.get("origin").toString());

						if (lJsonObj.has("destination"))
							lTopODsModel.setDestination(lJsonObj.get("destination").toString());

						if (lJsonObj.has("origin") && lJsonObj.get("origin").toString() != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("origin").toString())
								&& lJsonObj.has("destination") && lJsonObj.get("destination").toString() != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("destination").toString()))
							lTopODsModel
									.setOd(lJsonObj.get("origin").toString() + lJsonObj.get("destination").toString());

						lTopODsModel.setSalePax(Math.round(lSalePax));
						lTopODsModel.setSalePax_1(Math.round(lSalePaxlastyr));
						lTopODsModel.setFlownPax(Math.round(lFlownPax));
						lTopODsModel.setFlownPax_1(Math.round(lFlownPaxlastyr));
						lTopODsModel.setHostTargetPax(Math.round(lPaxTarget));
						lTopODsModel.setSaleRevenue(Math.round(lSaleRevenue));
						lTopODsModel.setHostRevenueFlown(Math.round(lRevenueFlown));
						lTopODsModel.setHostRevenueflown_1(Math.round(lRevenueFlownlastyr));
						lTopODsModel.setHostRevenueTarget(Math.round(lRevenueTarget));
						lTopODsModel.setMonth(Math.round(lMonth));
						lTopODsModel.setMarketSize(Math.round(lMarketSize));
						lTopODsModel.setMarketSize_1(Math.round(lMarketSizelastyr));
						lTopODsModel.setPaxVLYR(Math.round(lPaxVLYR));
						lTopODsModel.setPaxYTD(Math.round(lPaxYTD));
						lTopODsModel.setPaxVTGT(Math.round(lPaxVTGT));
						lTopODsModel.setRevenueYTD(Math.round(lRevenueYTD));
						lTopODsModel.setRevenueVLYR(Math.round(lRevnueVLYR));
						lTopODsModel.setRevenueVTGT(Math.round(lRevenueVTGT));
						lTopODsModel.setKey(lKey);

						lResultMap.put(lKey, lTopODsModel);

					}

					else if (!lResultMap.containsKey(lKey)) {

						lTopODsModel = new MarketIndicatorTopODs();
						lCompMap = new HashMap<String, Object>();

						if (lJsonObj.has("origin") && lJsonObj.get("origin").toString() != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("origin").toString())
								&& lJsonObj.has("destination") && lJsonObj.get("destination").toString() != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("destination").toString()))
							lTopODsModel
									.setOd(lJsonObj.get("origin").toString() + lJsonObj.get("destination").toString());

						if (lJsonObj.has("origin") && lJsonObj.get("origin").toString() != null)
							lTopODsModel.setOrigin(lJsonObj.get("origin").toString());

						if (lJsonObj.has("destination") && lJsonObj.get("destination").toString() != null)
							lTopODsModel.setDestination(lJsonObj.get("destination").toString());

						JSONArray lCarrierJSONArray = null;
						if (lJsonObj.has("carrier"))
							lCarrierJSONArray = new JSONArray(lJsonObj.get("carrier").toString());

						JSONArray lMarketSharePaxJSONArray = null;
						if (lJsonObj.has("market_share_pax"))
							lMarketSharePaxJSONArray = new JSONArray(lJsonObj.get("market_share_pax").toString());

						JSONArray lMarketSharePaxlastyrJSONArray = null;
						if (lJsonObj.has("market_share_pax_1"))
							lMarketSharePaxlastyrJSONArray = new JSONArray(
									lJsonObj.get("market_share_pax_1").toString());

						JSONArray lMarketSizeJSONArray = null;
						if (lJsonObj.has("market_share_pax"))
							lMarketSizeJSONArray = lMarketSharePaxJSONArray;

						JSONArray lMarketSizelastyrJSONArray = null;
						if (lJsonObj.has("market_share_pax_1"))
							lMarketSizelastyrJSONArray = lMarketSharePaxlastyrJSONArray;

						float lMarketSize = 0;
						if (lMarketSizeJSONArray != null && lMarketSizeJSONArray.length() != 0) {
							for (int m = 0; m < lMarketSizeJSONArray.length(); m++)
								if (!"[]".equalsIgnoreCase(lMarketSizeJSONArray.get(m).toString())
										&& !"null".equalsIgnoreCase(lMarketSizeJSONArray.get(m).toString()))
									lMarketSize += Float.parseFloat(lMarketSizeJSONArray.get(m).toString());
						}

						float lMarketSizelastyr = 0;
						if (lMarketSizelastyrJSONArray != null && lMarketSizelastyrJSONArray.length() != 0) {
							for (int m = 0; m < lMarketSizelastyrJSONArray.length(); m++)
								if (!"[]".equalsIgnoreCase(lMarketSizelastyrJSONArray.get(m).toString())
										&& !"null".equalsIgnoreCase(lMarketSizelastyrJSONArray.get(m).toString()))
									lMarketSizelastyr += Float.parseFloat(lMarketSizelastyrJSONArray.get(m).toString());
						}

						JSONArray lRevenueFlownJSONArray = null;
						if (lJsonObj.has("host_revenue_flown"))
							lRevenueFlownJSONArray = new JSONArray(lJsonObj.get("host_revenue_flown").toString());

						JSONArray lRevenueFlown_1JSONArray = null;
						if (lJsonObj.has("host_revenue_flown_1"))
							lRevenueFlown_1JSONArray = new JSONArray(lJsonObj.get("host_revenue_flown_1").toString());

						JSONArray lRevenueTargetJSONArray = null;
						if (lJsonObj.has("host_revenue_target"))
							lRevenueTargetJSONArray = new JSONArray(lJsonObj.get("host_revenue_target").toString());

						JSONArray lFlownPaxJSONArray = null;
						if (lJsonObj.has("flown_pax"))
							lFlownPaxJSONArray = new JSONArray(lJsonObj.get("flown_pax").toString());

						JSONArray lFlownPax_1JSONArray = null;
						if (lJsonObj.has("flown_pax_1"))
							lFlownPax_1JSONArray = new JSONArray(lJsonObj.get("flown_pax_1").toString());

						JSONArray lHostPaxTargetJSONArray = null;
						if (lJsonObj.has("host_pax_target"))
							lHostPaxTargetJSONArray = new JSONArray(lJsonObj.get("host_pax_target").toString());

						float lRevenueFlown = 0;
						if (lRevenueFlownJSONArray != null) {
							for (int r = 0; r < lRevenueFlownJSONArray.length(); r++) {
								if (!"[]".equalsIgnoreCase(lRevenueFlownJSONArray.get(r).toString())
										&& !"null".equalsIgnoreCase(lRevenueFlownJSONArray.get(r).toString()))
									lRevenueFlown += Float.parseFloat(lRevenueFlownJSONArray.get(r).toString());
							}
						}

						float lRevenueFlownlastyr = 0;
						if (lRevenueFlownJSONArray != null) {
							for (int r = 0; r < lRevenueFlown_1JSONArray.length(); r++) {
								if (!"[]".equalsIgnoreCase(lRevenueFlown_1JSONArray.get(r).toString())
										&& !"null".equalsIgnoreCase(lRevenueFlown_1JSONArray.get(r).toString()))
									lRevenueFlownlastyr += Float.parseFloat(lRevenueFlown_1JSONArray.get(r).toString());
							}
						}

						float lRevenueTarget = 0;
						if (lRevenueTargetJSONArray != null) {
							for (int r = 0; r < lRevenueTargetJSONArray.length(); r++) {
								if (!"[]".equalsIgnoreCase(lRevenueTargetJSONArray.get(r).toString())
										&& !"null".equalsIgnoreCase(lRevenueTargetJSONArray.get(r).toString()))
									lRevenueTarget += Float.parseFloat(lRevenueTargetJSONArray.get(r).toString());
							}
						}

						float lSalePax = Float.parseFloat(lJsonObj.get("sale_pax").toString());
						float lSalePaxlastyr = Float.parseFloat(lJsonObj.get("sale_pax_last_year").toString());

						float lFlownPax = 0;
						if (lFlownPaxJSONArray != null) {
							for (int r = 0; r < lFlownPaxJSONArray.length(); r++) {
								if (!"[]".equalsIgnoreCase(lFlownPaxJSONArray.get(r).toString())
										&& !"null".equalsIgnoreCase(lFlownPaxJSONArray.get(r).toString()))
									lFlownPax += Float.parseFloat(lFlownPaxJSONArray.get(r).toString());
							}

							float lFlownPaxlastyr = 0;
							if (lFlownPax_1JSONArray != null) {
								for (int r = 0; r < lFlownPax_1JSONArray.length(); r++) {
									if (!"[]".equalsIgnoreCase(lFlownPax_1JSONArray.get(r).toString())
											&& !"null".equalsIgnoreCase(lFlownPax_1JSONArray.get(r).toString()))
										lFlownPaxlastyr += Float.parseFloat(lFlownPax_1JSONArray.get(r).toString());
								}

								float lPaxTarget = 0;
								if (lHostPaxTargetJSONArray != null) {
									for (int r = 0; r < lHostPaxTargetJSONArray.length(); r++) {
										if (!"[]".equalsIgnoreCase(lHostPaxTargetJSONArray.get(r).toString())
												&& !"null".equalsIgnoreCase(lHostPaxTargetJSONArray.get(r).toString()))
											lPaxTarget += Float.parseFloat(lHostPaxTargetJSONArray.get(r).toString());
									}

									float lSaleRevenue = 0;
									if (lJsonObj.has("sale_revenue"))
										lSaleRevenue = Float.parseFloat(lJsonObj.get("sale_revenue").toString());

									float lRevenueYTD = lSaleRevenue;

									float lRevnueVLYR = 00.0F;
									if (lRevenueFlownlastyr != 0)
										lRevnueVLYR = CalculationUtil.calculateVLYR(lSaleRevenue, lRevenueFlownlastyr);

									float lRevenueVTGT = 0.0F;
									if (lRevenueTarget != 0)
										lRevenueVTGT = CalculationUtil.calculateVTGT(lSaleRevenue, lRevenueTarget);

									float lPaxYTD = lSalePax;

									float lPaxVLYR = 0.0F;
									if (lSalePaxlastyr != 0)
										lPaxVLYR = CalculationUtil.calculateVLYR(lSalePax, lSalePaxlastyr);

									float lPaxVTGT = 0.0f;
									if (lPaxTarget != 0)
										lPaxVTGT = CalculationUtil.calculateVTGT(lSalePax, lPaxTarget);

									float lMonth = 0;
									float day = 0;
									if (lJsonObj.has("dep_date")
											&& !"null".equalsIgnoreCase(lJsonObj.get("dep_date").toString())) {

										lMonth = (float) Utility.findMonth(lJsonObj.get("dep_date").toString());
										day = (float) Utility.findDay(lJsonObj.get("dep_date").toString());

									}

									if (lCarrierJSONArray != null && lUserProfileModel != null) {

										for (int t = 0; t < lUserProfileModel.getCompetitors().length; t++) {
											for (int c = 0; c < lCarrierJSONArray.length(); c++) {

												String lCarrier = lCarrierJSONArray.get(c).toString();
												String lComp = lUserProfileModel.getCompetitors()[t].replace("\"", "");

												if (lCarrier.equalsIgnoreCase(lComp)
														|| lCarrier.equalsIgnoreCase(Constants.hostName)) {

													Map<String, Object> lCompetitors = new HashMap<String, Object>();

													float lMarketSharePaxlastyr = 0;
													if (lMarketSharePaxlastyrJSONArray.length() != 0)
														lMarketSharePaxlastyr = Float.parseFloat(
																lMarketSharePaxlastyrJSONArray.get(c).toString());

													float lMarketSharePax = 0;
													if (lMarketSharePaxJSONArray.length() != 0)
														lMarketSharePax = Float
																.parseFloat(lMarketSharePaxJSONArray.get(c).toString());

													float lCarrierBookingsYTD = lMarketSharePax;

													float lCarrierBookingsVLYR = 0.0f;
													if (lMarketSharePaxlastyr != 0)
														lCarrierBookingsVLYR = CalculationUtil
																.calculateVLYR(lMarketSharePax, lMarketSharePaxlastyr);

													float lCarrierMarketShareYTD = 0;
													if (lMarketSize != 0)
														lCarrierMarketShareYTD = CalculationUtil
																.doDivision(lMarketSharePax, lMarketSize) * 100;

													float lCarrierMarketShare = 0.0f;
													if (lMarketSize != 0)
														lCarrierMarketShare = CalculationUtil
																.doDivision(lMarketSharePax, lMarketSize);

													float lCarrierMarketSharelastyr = 0.0f;
													if (lMarketSizelastyr != 0)
														lCarrierMarketSharelastyr = CalculationUtil
																.doDivision(lMarketSharePaxlastyr, lMarketSizelastyr);

													float lCarrierFMS = 0;

													float lCarrierMarketShareVLYR = 0;
													if (lCarrierMarketSharelastyr != 0)
														lCarrierMarketShareVLYR = CalculationUtil.calculateVLYR(
																lCarrierMarketShare, lCarrierMarketSharelastyr);

													float lCarrierMarketShareVTGT = 0;
													if (lCarrierFMS != 0)
														lCarrierMarketShareVTGT = CalculationUtil
																.calculateVTGT(lCarrierMarketShare, lCarrierFMS);

													lCompetitors.put("name", lCarrier);
													lCompetitors.put("marketSharePax", Math.round(lMarketSharePax));
													lCompetitors.put("marketSharePax_1",
															Math.round(lMarketSharePaxlastyr));
													lCompetitors.put("FMS", Math.round(lCarrierFMS));
													lCompetitors.put("BookingsYTD", Math.round(lCarrierBookingsYTD));
													lCompetitors.put("BookingsVLYR", Math.round(lCarrierBookingsVLYR));
													lCompetitors.put("MarketShare", Math.round(lCarrierMarketShare));
													lCompetitors.put("MarketShare",
															Math.round(lCarrierMarketSharelastyr));
													lCompetitors.put("MarketShareYTD",
															Math.round(lCarrierMarketShareYTD));
													lCompetitors.put("MarketShareVLYR",
															Math.round(lCarrierMarketShareVLYR));
													lCompetitors.put("MarketShareVTGT",
															Math.round(lCarrierMarketShareVTGT));

													lCompMap.put(lCarrier, lCompetitors);

												}

												lTopODsModel.setCompetitorsMap(lCompMap);
											}
										}

									}

									if (lJsonObj.has("origin"))
										lTopODsModel.setOrigin(lJsonObj.get("origin").toString());

									if (lJsonObj.has("destination"))
										lTopODsModel.setDestination(lJsonObj.get("destination").toString());

									if (lJsonObj.has("origin") && lJsonObj.get("origin").toString() != null
											&& !"null".equalsIgnoreCase(lJsonObj.get("origin").toString())
											&& lJsonObj.has("destination")
											&& lJsonObj.get("destination").toString() != null
											&& !"null".equalsIgnoreCase(lJsonObj.get("destination").toString()))
										lTopODsModel.setOd(lJsonObj.get("origin").toString()
												+ lJsonObj.get("destination").toString());

									lTopODsModel.setSalePax(Math.round(lSalePax));
									lTopODsModel.setSalePax_1(Math.round(lSalePaxlastyr));
									lTopODsModel.setFlownPax(Math.round(lFlownPax));
									lTopODsModel.setFlownPax_1(Math.round(lFlownPaxlastyr));
									lTopODsModel.setHostTargetPax(Math.round(lPaxTarget));
									lTopODsModel.setSaleRevenue(Math.round(lSaleRevenue));
									lTopODsModel.setHostRevenueFlown(Math.round(lRevenueFlown));
									lTopODsModel.setHostRevenueflown_1(Math.round(lRevenueFlownlastyr));
									lTopODsModel.setHostRevenueTarget(Math.round(lRevenueTarget));
									lTopODsModel.setMonth(Math.round(lMonth));
									lTopODsModel.setMarketSize(Math.round(lMarketSize));
									lTopODsModel.setMarketSize_1(Math.round(lMarketSizelastyr));
									lTopODsModel.setPaxVLYR(Math.round(lPaxVLYR));
									lTopODsModel.setPaxYTD(Math.round(lPaxYTD));
									lTopODsModel.setPaxVTGT(Math.round(lPaxVTGT));
									lTopODsModel.setRevenueYTD(Math.round(lRevenueYTD));
									lTopODsModel.setRevenueVLYR(Math.round(lRevnueVLYR));
									lTopODsModel.setRevenueVTGT(Math.round(lRevenueVTGT));
									lTopODsModel.setKey(lKey);

									lResultMap.put(lKey, lTopODsModel);

								}

							}

						}

					}

				}

				Set<String> lOdKeySet = new HashSet<String>();
				lOdKeySet = lResultMap.keySet();
				for (String keys : lOdKeySet)
					lKeyList.add((MarketIndicatorTopODs) lResultMap.get(keys));
				Collections.sort(lKeyList, new TopOd());
				lUserDataMap.put("Data", lKeyList.get(0));

				Map<String, Object> lAggMap = new HashMap<String, Object>();

				long lAggHostSaleRevenue = 0;
				long lAggHostRevenueFlownlastyr = 0;
				long lAggHostRevenueTarget = 0;

				long lAggHostPax = 0;
				long lAggHostPaxlastyr = 0;
				long lAggHostPaxTarget = 0;

				float lAggHostPaxYTD = 0.0f;
				float lAggHostPaxVLYR = 0.0f;
				float lAggHostPaxVTGT = 0.0f;

				float lAggHostRevenueYTD = 0.0f;
				float lAggHostRevenueVLYR = 0.0f;
				float lAggHostRevenueVTGT = 0.0f;

				MarketIndicatorTopODs lTopOdIterator = (MarketIndicatorTopODs) lKeyList.get(0);

				lAggHostRevenueYTD += lTopOdIterator.getRevenueYTD();
				lAggHostSaleRevenue += lTopOdIterator.getSaleRevenue();
				lAggHostRevenueFlownlastyr += lTopOdIterator.getHostRevenueflown_1();
				lAggHostRevenueTarget += lTopOdIterator.getHostRevenueTarget();

				lAggHostPaxYTD += lTopOdIterator.getSalePax();
				lAggHostPax += lTopOdIterator.getSalePax();
				lAggHostPaxlastyr += lTopOdIterator.getSalePax_1();
				lAggHostPaxTarget += lTopOdIterator.getHostTargetPax();

				if (lAggHostRevenueFlownlastyr != 0)
					lAggHostRevenueVLYR = CalculationUtil.calculateVLYR(lAggHostSaleRevenue,
							lAggHostRevenueFlownlastyr);

				if (lAggHostRevenueTarget != 0)
					lAggHostRevenueVTGT = CalculationUtil.calculateVTGT(lAggHostSaleRevenue, lAggHostRevenueTarget);

				if (lAggHostPaxlastyr != 0)
					lAggHostPaxVLYR = CalculationUtil.calculateVLYR(lAggHostPax, lAggHostPaxlastyr);

				if (lAggHostPaxTarget != 0)
					lAggHostPaxVTGT = CalculationUtil.calculateVTGT(lAggHostPax, lAggHostPaxTarget);

				lAggMap.put("aggregate_host_revenue_YTD", Math.round(lAggHostRevenueYTD));
				lAggMap.put("aggregate_host_revenue_VLYR", Math.round(lAggHostRevenueVLYR));
				lAggMap.put("aggregate_host_revenue_VTGT", Math.round(lAggHostRevenueVTGT));
				lAggMap.put("aggregate_host_pax_YTD", Math.round(lAggHostPaxYTD));
				lAggMap.put("aggregate_host_pax_VLYR", Math.round(lAggHostPaxVLYR));
				lAggMap.put("aggregate_host_pax_VTGT", Math.round(lAggHostPaxVTGT));

				lUserDataMap.put("aggregateMap", lAggMap);
			} else {
				lUserDataMap.put("Data", null);
			}

		} catch (

		Exception e) {
			logger.error("getCalculatedMarketIndicatorTopODs-Exception", e);
		}

		return lUserDataMap;

	}

	class TopOd implements Comparator<MarketIndicatorTopODs> {
		@Override
		public int compare(MarketIndicatorTopODs pArg0, MarketIndicatorTopODs pArg1) {

			if (pArg0.getSaleRevenue() > 0) {
				if (pArg0.getSaleRevenue() < pArg1.getSaleRevenue()) {
					return 1;
				} else {
					return -1;
				}
			}
			return 0;
		}

	}

	@Override
	public List<Object> getFlightAvailability(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);
		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getFlightAvailability(pRequestModel, lUserProfileModel);
		JSONArray lDataJSONArray = null;
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);
		}

		List<Object> lFlightAvailabilityList = new ArrayList<Object>();
		Map<String, Object> lCompartmentMap = new HashMap<String, Object>();
		Map<String, Long> lJsa = new HashMap<String, Long>();
		Map<String, Long> lCsa = new HashMap<String, Long>();
		Map<String, Long> lZsa = new HashMap<String, Long>();
		Map<String, Long> lDsa = new HashMap<String, Long>();
		Map<String, Long> lIsa = new HashMap<String, Long>();
		Map<String, Long> lYsa = new HashMap<String, Long>();
		Map<String, Long> lWsa = new HashMap<String, Long>();
		Map<String, Long> lTsa = new HashMap<String, Long>();
		Map<String, Long> lMsa = new HashMap<String, Long>();
		Map<String, Long> lNsa = new HashMap<String, Long>();
		Map<String, Long> lBsa = new HashMap<String, Long>();
		Map<String, Long> lVsa = new HashMap<String, Long>();
		Map<String, Long> lKsa = new HashMap<String, Long>();
		Map<String, Long> lQsa = new HashMap<String, Long>();
		Map<String, Long> lLsa = new HashMap<String, Long>();
		Map<String, Long> lSsa = new HashMap<String, Long>();
		Map<String, Long> lXsa = new HashMap<String, Long>();

		if (lDataJSONArray != null) {

			long _30daysJsa = 0;
			long _30daysCsa = 0;
			long _30daysZsa = 0;
			long _30daysDsa = 0;
			long _30daysIsa = 0;
			long _30daysYsa = 0;
			long _30daysWsa = 0;
			long _30daysTsa = 0;
			long _30daysMsa = 0;
			long _30daysNsa = 0;
			long _30daysBsa = 0;
			long _30daysVsa = 0;
			long _30daysKsa = 0;
			long _30daysQsa = 0;
			long _30daysLsa = 0;
			long _30daysSsa = 0;
			long _30daysXsa = 0;

			long _2WeeksJsa = 0;
			long _2WeeksCsa = 0;
			long _2WeeksZsa = 0;
			long _2WeeksDsa = 0;
			long _2WeeksIsa = 0;
			long _2WeeksYsa = 0;
			long _2WeeksWsa = 0;
			long _2WeeksTsa = 0;
			long _2WeeksMsa = 0;
			long _2WeeksNsa = 0;
			long _2WeeksBsa = 0;
			long _2WeeksVsa = 0;
			long _2WeeksKsa = 0;
			long _2WeeksQsa = 0;
			long _2WeeksLsa = 0;
			long _2WeeksSsa = 0;
			long _2WeeksXsa = 0;

			long _90daysJsa = 0;
			long _90daysCsa = 0;
			long _90daysZsa = 0;
			long _90daysDsa = 0;
			long _90daysIsa = 0;
			long _90daysYsa = 0;
			long _90daysWsa = 0;
			long _90daysTsa = 0;
			long _90daysMsa = 0;
			long _90daysNsa = 0;
			long _90daysBsa = 0;
			long _90daysVsa = 0;
			long _90daysKsa = 0;
			long _90daysQsa = 0;
			long _90daysLsa = 0;
			long _90daysSsa = 0;
			long _90daysXsa = 0;

			long _6MonthsJsa = 0;
			long _6MonthsCsa = 0;
			long _6MonthsZsa = 0;
			long _6MonthsDsa = 0;
			long _6MonthsIsa = 0;
			long _6MonthsYsa = 0;
			long _6MonthsWsa = 0;
			long _6MonthsTsa = 0;
			long _6MonthsMsa = 0;
			long _6MonthsNsa = 0;
			long _6MonthsBsa = 0;
			long _6MonthsVsa = 0;
			long _6MonthsKsa = 0;
			long _6MonthsQsa = 0;
			long _6MonthsLsa = 0;
			long _6MonthsSsa = 0;
			long _6MonthsXsa = 0;

			long _1YrJsa = 0;
			long _1YrCsa = 0;
			long _1YrZsa = 0;
			long _1YrDsa = 0;
			long _1YrIsa = 0;
			long _1YrYsa = 0;
			long _1YrWsa = 0;
			long _1YrTsa = 0;
			long _1YrMsa = 0;
			long _1YrNsa = 0;
			long _1YrBsa = 0;
			long _1YrVsa = 0;
			long _1YrKsa = 0;
			long _1YrQsa = 0;
			long _1YrLsa = 0;
			long _1YrSsa = 0;
			long _1YrXsa = 0;

			Date lCurrentDate = null;

			lCurrentDate = Utility.convertStringToDateFromat(Utility.getCurrentDate());

			for (int i = 0; i < lDataJSONArray.length(); i++) {

				JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);

				Date lDepDate = null;
				if (lJsonObj.has("dep_date"))
					lDepDate = Utility.convertStringToDateFromat(lJsonObj.get("dep_date").toString());

				long lDiff = 0;
				if (lCurrentDate != null)
					lDiff = Math.abs(lCurrentDate.getTime() - lDepDate.getTime());
				long lDiffDays = lDiff / (24 * 60 * 60 * 1000);

				if (lDiffDays <= 30) {
					_30daysJsa = _30daysJsa + Long.parseLong(lJsonObj.get("JSa").toString());
					_30daysCsa = _30daysCsa + Long.parseLong(lJsonObj.get("CSa").toString());
					_30daysZsa = _30daysZsa + Long.parseLong(lJsonObj.get("ZSa").toString());
					_30daysDsa = _30daysDsa + Long.parseLong(lJsonObj.get("DSa").toString());
					_30daysIsa = _30daysIsa + Long.parseLong(lJsonObj.get("ISa").toString());
					_30daysYsa = _30daysYsa + Long.parseLong(lJsonObj.get("YSa").toString());
					_30daysWsa = _30daysWsa + Long.parseLong(lJsonObj.get("WSa").toString());
					_30daysTsa = _30daysTsa + Long.parseLong(lJsonObj.get("TSa").toString());
					_30daysMsa = _30daysMsa + Long.parseLong(lJsonObj.get("MSa").toString());
					_30daysNsa = _30daysNsa + Long.parseLong(lJsonObj.get("NSa").toString());
					_30daysBsa = _30daysBsa + Long.parseLong(lJsonObj.get("BSa").toString());
					_30daysVsa = _30daysVsa + Long.parseLong(lJsonObj.get("VSa").toString());
					_30daysKsa = _30daysKsa + Long.parseLong(lJsonObj.get("KSa").toString());
					_30daysQsa = _30daysQsa + Long.parseLong(lJsonObj.get("QSa").toString());
					_30daysLsa = _30daysLsa + Long.parseLong(lJsonObj.get("LSa").toString());
					_30daysSsa = _30daysSsa + Long.parseLong(lJsonObj.get("SSa").toString());
					_30daysXsa = _30daysXsa + Long.parseLong(lJsonObj.get("XSa").toString());

				}

				if (lDiffDays <= 14) {
					_2WeeksJsa = _2WeeksJsa + Long.parseLong(lJsonObj.get("JSa").toString());
					_2WeeksCsa = _2WeeksCsa + Long.parseLong(lJsonObj.get("CSa").toString());
					_2WeeksZsa = _2WeeksZsa + Long.parseLong(lJsonObj.get("ZSa").toString());
					_2WeeksDsa = _2WeeksDsa + Long.parseLong(lJsonObj.get("DSa").toString());
					_2WeeksIsa = _2WeeksIsa + Long.parseLong(lJsonObj.get("ISa").toString());
					_2WeeksYsa = _2WeeksYsa + Long.parseLong(lJsonObj.get("YSa").toString());
					_2WeeksWsa = _2WeeksWsa + Long.parseLong(lJsonObj.get("WSa").toString());
					_2WeeksTsa = _2WeeksTsa + Long.parseLong(lJsonObj.get("TSa").toString());
					_2WeeksMsa = _2WeeksMsa + Long.parseLong(lJsonObj.get("MSa").toString());
					_2WeeksNsa = _2WeeksNsa + Long.parseLong(lJsonObj.get("NSa").toString());
					_2WeeksBsa = _2WeeksBsa + Long.parseLong(lJsonObj.get("BSa").toString());
					_2WeeksVsa = _2WeeksVsa + Long.parseLong(lJsonObj.get("VSa").toString());
					_2WeeksKsa = _2WeeksKsa + Long.parseLong(lJsonObj.get("KSa").toString());
					_2WeeksQsa = _2WeeksQsa + Long.parseLong(lJsonObj.get("QSa").toString());
					_2WeeksLsa = _2WeeksLsa + Long.parseLong(lJsonObj.get("LSa").toString());
					_2WeeksSsa = _2WeeksSsa + Long.parseLong(lJsonObj.get("SSa").toString());
					_2WeeksXsa = _2WeeksXsa + Long.parseLong(lJsonObj.get("XSa").toString());
					_2WeeksYsa = _2WeeksYsa + Long.parseLong(lJsonObj.get("YSa").toString());
					_2WeeksWsa = _2WeeksWsa + Long.parseLong(lJsonObj.get("WSa").toString());
					_2WeeksTsa = _2WeeksTsa + Long.parseLong(lJsonObj.get("TSa").toString());
					_2WeeksMsa = _2WeeksMsa + Long.parseLong(lJsonObj.get("MSa").toString());
					_2WeeksNsa = _2WeeksNsa + Long.parseLong(lJsonObj.get("NSa").toString());
					_2WeeksBsa = _2WeeksBsa + Long.parseLong(lJsonObj.get("BSa").toString());
					_2WeeksVsa = _2WeeksVsa + Long.parseLong(lJsonObj.get("VSa").toString());
					_2WeeksKsa = _2WeeksKsa + Long.parseLong(lJsonObj.get("KSa").toString());
					_2WeeksQsa = _2WeeksQsa + Long.parseLong(lJsonObj.get("QSa").toString());
					_2WeeksLsa = _2WeeksLsa + Long.parseLong(lJsonObj.get("LSa").toString());
					_2WeeksSsa = _2WeeksSsa + Long.parseLong(lJsonObj.get("SSa").toString());
					_2WeeksXsa = _2WeeksXsa + Long.parseLong(lJsonObj.get("XSa").toString());

				}

				if (lDiffDays <= 90) {
					_90daysJsa = _90daysJsa + Long.parseLong(lJsonObj.get("JSa").toString());
					_90daysCsa = _90daysCsa + Long.parseLong(lJsonObj.get("CSa").toString());
					_90daysZsa = _90daysZsa + Long.parseLong(lJsonObj.get("ZSa").toString());
					_90daysDsa = _90daysDsa + Long.parseLong(lJsonObj.get("DSa").toString());
					_90daysIsa = _90daysIsa + Long.parseLong(lJsonObj.get("ISa").toString());
					_90daysYsa = _90daysYsa + Long.parseLong(lJsonObj.get("YSa").toString());
					_90daysWsa = _90daysWsa + Long.parseLong(lJsonObj.get("WSa").toString());
					_90daysTsa = _90daysTsa + Long.parseLong(lJsonObj.get("TSa").toString());
					_90daysMsa = _90daysMsa + Long.parseLong(lJsonObj.get("MSa").toString());
					_90daysNsa = _90daysNsa + Long.parseLong(lJsonObj.get("NSa").toString());
					_90daysBsa = _90daysBsa + Long.parseLong(lJsonObj.get("BSa").toString());
					_90daysVsa = _90daysVsa + Long.parseLong(lJsonObj.get("VSa").toString());
					_90daysKsa = _90daysKsa + Long.parseLong(lJsonObj.get("KSa").toString());
					_90daysQsa = _90daysQsa + Long.parseLong(lJsonObj.get("QSa").toString());
					_90daysLsa = _90daysLsa + Long.parseLong(lJsonObj.get("LSa").toString());
					_90daysSsa = _90daysSsa + Long.parseLong(lJsonObj.get("SSa").toString());
					_90daysXsa = _90daysXsa + Long.parseLong(lJsonObj.get("XSa").toString());
					_90daysYsa = _90daysYsa + Long.parseLong(lJsonObj.get("YSa").toString());
					_90daysWsa = _90daysWsa + Long.parseLong(lJsonObj.get("WSa").toString());
					_90daysTsa = _90daysTsa + Long.parseLong(lJsonObj.get("TSa").toString());
					_90daysMsa = _90daysMsa + Long.parseLong(lJsonObj.get("MSa").toString());
					_90daysNsa = _90daysNsa + Long.parseLong(lJsonObj.get("NSa").toString());
					_90daysBsa = _90daysBsa + Long.parseLong(lJsonObj.get("BSa").toString());
					_90daysVsa = _90daysVsa + Long.parseLong(lJsonObj.get("VSa").toString());
					_90daysKsa = _90daysKsa + Long.parseLong(lJsonObj.get("KSa").toString());
					_90daysQsa = _90daysQsa + Long.parseLong(lJsonObj.get("QSa").toString());
					_90daysLsa = _90daysLsa + Long.parseLong(lJsonObj.get("LSa").toString());
					_90daysSsa = _90daysSsa + Long.parseLong(lJsonObj.get("SSa").toString());
					_90daysXsa = _90daysXsa + Long.parseLong(lJsonObj.get("XSa").toString());

				}

				if (lDiffDays <= 183) {
					_6MonthsCsa = _6MonthsCsa + Long.parseLong(lJsonObj.get("JSa").toString());
					_6MonthsJsa = _6MonthsJsa + Long.parseLong(lJsonObj.get("CSa").toString());
					_6MonthsZsa = _6MonthsZsa + Long.parseLong(lJsonObj.get("ZSa").toString());
					_6MonthsDsa = _6MonthsDsa + Long.parseLong(lJsonObj.get("DSa").toString());
					_6MonthsIsa = _6MonthsIsa + Long.parseLong(lJsonObj.get("ISa").toString());
					_6MonthsYsa = _6MonthsYsa + Long.parseLong(lJsonObj.get("YSa").toString());
					_6MonthsWsa = _6MonthsWsa + Long.parseLong(lJsonObj.get("WSa").toString());
					_6MonthsTsa = _6MonthsTsa + Long.parseLong(lJsonObj.get("TSa").toString());
					_6MonthsMsa = _6MonthsMsa + Long.parseLong(lJsonObj.get("MSa").toString());
					_6MonthsNsa = _6MonthsNsa + Long.parseLong(lJsonObj.get("NSa").toString());
					_6MonthsBsa = _6MonthsBsa + Long.parseLong(lJsonObj.get("BSa").toString());
					_6MonthsVsa = _6MonthsVsa + Long.parseLong(lJsonObj.get("VSa").toString());
					_6MonthsKsa = _6MonthsKsa + Long.parseLong(lJsonObj.get("KSa").toString());
					_6MonthsQsa = _6MonthsQsa + Long.parseLong(lJsonObj.get("QSa").toString());
					_6MonthsLsa = _6MonthsLsa + Long.parseLong(lJsonObj.get("LSa").toString());
					_6MonthsSsa = _6MonthsSsa + Long.parseLong(lJsonObj.get("SSa").toString());
					_6MonthsXsa = _6MonthsXsa + Long.parseLong(lJsonObj.get("XSa").toString());
					_6MonthsYsa = _6MonthsYsa + Long.parseLong(lJsonObj.get("YSa").toString());
					_6MonthsWsa = _6MonthsWsa + Long.parseLong(lJsonObj.get("WSa").toString());
					_6MonthsTsa = _6MonthsTsa + Long.parseLong(lJsonObj.get("TSa").toString());
					_6MonthsMsa = _6MonthsMsa + Long.parseLong(lJsonObj.get("MSa").toString());
					_6MonthsNsa = _6MonthsNsa + Long.parseLong(lJsonObj.get("NSa").toString());
					_6MonthsBsa = _6MonthsBsa + Long.parseLong(lJsonObj.get("BSa").toString());
					_6MonthsVsa = _6MonthsVsa + Long.parseLong(lJsonObj.get("VSa").toString());
					_6MonthsKsa = _6MonthsKsa + Long.parseLong(lJsonObj.get("KSa").toString());
					_6MonthsQsa = _6MonthsQsa + Long.parseLong(lJsonObj.get("QSa").toString());
					_6MonthsLsa = _6MonthsLsa + Long.parseLong(lJsonObj.get("LSa").toString());
					_6MonthsSsa = _6MonthsSsa + Long.parseLong(lJsonObj.get("SSa").toString());
					_6MonthsXsa = _6MonthsXsa + Long.parseLong(lJsonObj.get("XSa").toString());

				}

				if (lDiffDays <= 365) {
					_1YrJsa = _1YrJsa + Long.parseLong(lJsonObj.get("JSa").toString());
					_1YrCsa = _1YrCsa + Long.parseLong(lJsonObj.get("CSa").toString());
					_1YrZsa = _1YrZsa + Long.parseLong(lJsonObj.get("ZSa").toString());
					_1YrDsa = _1YrDsa + Long.parseLong(lJsonObj.get("DSa").toString());
					_1YrIsa = _1YrIsa + Long.parseLong(lJsonObj.get("ISa").toString());
					_1YrYsa = _1YrYsa + Long.parseLong(lJsonObj.get("YSa").toString());
					_1YrWsa = _1YrWsa + Long.parseLong(lJsonObj.get("WSa").toString());
					_1YrTsa = _1YrTsa + Long.parseLong(lJsonObj.get("TSa").toString());
					_1YrMsa = _1YrMsa + Long.parseLong(lJsonObj.get("MSa").toString());
					_1YrNsa = _1YrNsa + Long.parseLong(lJsonObj.get("NSa").toString());
					_1YrBsa = _1YrBsa + Long.parseLong(lJsonObj.get("BSa").toString());
					_1YrVsa = _1YrVsa + Long.parseLong(lJsonObj.get("VSa").toString());
					_1YrKsa = _1YrKsa + Long.parseLong(lJsonObj.get("KSa").toString());
					_1YrQsa = _1YrQsa + Long.parseLong(lJsonObj.get("QSa").toString());
					_1YrLsa = _1YrLsa + Long.parseLong(lJsonObj.get("LSa").toString());
					_1YrSsa = _1YrSsa + Long.parseLong(lJsonObj.get("SSa").toString());
					_1YrXsa = _1YrXsa + Long.parseLong(lJsonObj.get("XSa").toString());
					_1YrYsa = _1YrYsa + Long.parseLong(lJsonObj.get("YSa").toString());
					_1YrWsa = _1YrWsa + Long.parseLong(lJsonObj.get("WSa").toString());
					_1YrTsa = _1YrTsa + Long.parseLong(lJsonObj.get("TSa").toString());
					_1YrMsa = _1YrMsa + Long.parseLong(lJsonObj.get("MSa").toString());
					_1YrNsa = _1YrNsa + Long.parseLong(lJsonObj.get("NSa").toString());
					_1YrBsa = _1YrBsa + Long.parseLong(lJsonObj.get("BSa").toString());
					_1YrVsa = _1YrVsa + Long.parseLong(lJsonObj.get("VSa").toString());
					_1YrKsa = _1YrKsa + Long.parseLong(lJsonObj.get("KSa").toString());
					_1YrQsa = _1YrQsa + Long.parseLong(lJsonObj.get("QSa").toString());
					_1YrLsa = _1YrLsa + Long.parseLong(lJsonObj.get("LSa").toString());
					_1YrSsa = _1YrSsa + Long.parseLong(lJsonObj.get("SSa").toString());
					_1YrXsa = _1YrXsa + Long.parseLong(lJsonObj.get("XSa").toString());

				}

			}

			lJsa.put("30Days", _30daysJsa);
			lJsa.put("2Weeks", _2WeeksJsa);
			lJsa.put("90Days", _90daysJsa);
			lJsa.put("6Months", _6MonthsJsa);
			lJsa.put("1year", _1YrJsa);

			lCsa.put("30Days", _30daysCsa);
			lCsa.put("2Weeks", _2WeeksCsa);
			lCsa.put("90Days", _90daysCsa);
			lCsa.put("6Months", _6MonthsCsa);
			lCsa.put("1year", _1YrCsa);

			lZsa.put("30Days", _30daysZsa);
			lZsa.put("2Weeks", _2WeeksZsa);
			lZsa.put("90Days", _90daysZsa);
			lZsa.put("6Months", _6MonthsZsa);
			lZsa.put("1year", _1YrZsa);

			lDsa.put("30Days", _30daysDsa);
			lDsa.put("2Weeks", _2WeeksDsa);
			lDsa.put("90Days", _90daysDsa);
			lDsa.put("6Months", _6MonthsDsa);
			lDsa.put("1year", _1YrDsa);

			lIsa.put("30Days", _30daysIsa);
			lIsa.put("2Weeks", _2WeeksIsa);
			lIsa.put("90Days", _90daysIsa);
			lIsa.put("6Months", _6MonthsIsa);
			lIsa.put("1year", _1YrIsa);

			lYsa.put("30Days", _30daysYsa);
			lYsa.put("2Weeks", _2WeeksYsa);
			lYsa.put("90Days", _90daysYsa);
			lYsa.put("6Months", _6MonthsYsa);
			lYsa.put("1year", _1YrYsa);

			lWsa.put("30Days", _30daysWsa);
			lWsa.put("2Weeks", _2WeeksWsa);
			lWsa.put("90Days", _90daysWsa);
			lWsa.put("6Months", _6MonthsWsa);
			lWsa.put("1year", _1YrWsa);

			lTsa.put("30Days", _30daysTsa);
			lTsa.put("2Weeks", _2WeeksTsa);
			lTsa.put("90Days", _90daysTsa);
			lTsa.put("6Months", _6MonthsTsa);
			lTsa.put("1year", _1YrTsa);

			lMsa.put("30Days", _30daysMsa);
			lMsa.put("2Weeks", _2WeeksMsa);
			lMsa.put("90Days", _90daysMsa);
			lMsa.put("6Months", _6MonthsMsa);
			lMsa.put("1year", _1YrMsa);

			lNsa.put("30Days", _30daysNsa);
			lNsa.put("2Weeks", _2WeeksNsa);
			lNsa.put("90Days", _90daysNsa);
			lNsa.put("6Months", _6MonthsNsa);
			lNsa.put("1year", _1YrNsa);

			lBsa.put("30Days", _30daysBsa);
			lBsa.put("2Weeks", _2WeeksBsa);
			lBsa.put("90Days", _90daysBsa);
			lBsa.put("6Months", _6MonthsBsa);
			lBsa.put("1year", _1YrBsa);

			lVsa.put("30Days", _30daysVsa);
			lVsa.put("2Weeks", _2WeeksVsa);
			lVsa.put("90Days", _90daysVsa);
			lVsa.put("6Months", _6MonthsVsa);
			lVsa.put("1year", _1YrVsa);

			lKsa.put("30Days", _30daysKsa);
			lKsa.put("2Weeks", _2WeeksKsa);
			lKsa.put("90Days", _90daysKsa);
			lKsa.put("6Months", _6MonthsKsa);
			lKsa.put("1year", _1YrKsa);

			lQsa.put("30Days", _30daysQsa);
			lQsa.put("2Weeks", _2WeeksQsa);
			lQsa.put("90Days", _90daysQsa);
			lQsa.put("6Months", _6MonthsQsa);
			lQsa.put("1year", _1YrQsa);

			lLsa.put("30Days", _30daysLsa);
			lLsa.put("2Weeks", _2WeeksLsa);
			lLsa.put("90Days", _90daysLsa);
			lLsa.put("6Months", _6MonthsLsa);
			lLsa.put("1year", _1YrLsa);

			lSsa.put("30Days", _30daysSsa);
			lSsa.put("2Weeks", _2WeeksSsa);
			lSsa.put("90Days", _90daysSsa);
			lSsa.put("6Months", _6MonthsSsa);
			lSsa.put("1year", _1YrSsa);

			lXsa.put("30Days", _30daysXsa);
			lXsa.put("2Weeks", _2WeeksXsa);
			lXsa.put("90Days", _90daysXsa);
			lXsa.put("6Months", _6MonthsXsa);
			lXsa.put("1year", _1YrXsa);

			lCompartmentMap.put("J", lJsa);
			lCompartmentMap.put("C", lCsa);
			lCompartmentMap.put("Z", lZsa);
			lCompartmentMap.put("D", lDsa);
			lCompartmentMap.put("I", lIsa);
			lCompartmentMap.put("Y", lYsa);
			lCompartmentMap.put("W", lWsa);
			lCompartmentMap.put("T", lTsa);
			lCompartmentMap.put("M", lMsa);
			lCompartmentMap.put("N", lNsa);
			lCompartmentMap.put("B", lBsa);
			lCompartmentMap.put("V", lVsa);
			lCompartmentMap.put("K", lKsa);
			lCompartmentMap.put("Q", lQsa);
			lCompartmentMap.put("L", lLsa);
			lCompartmentMap.put("S", lSsa);
			lCompartmentMap.put("X", lXsa);

			lFlightAvailabilityList.add(lCompartmentMap);

		}
		return lFlightAvailabilityList;
	}

	@Override
	public Map<String, Object> getFlightForecast(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);

		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getFlightForecast(pRequestModel, lUserProfileModel);
		JSONArray lDataJSONArray = null;

		Map<String, Integer> lForecastMap = new HashMap<String, Integer>();
		Map<String, Object> lOdMap = new HashMap<String, Object>();

		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);
		}

		if (lDataJSONArray != null) {

			int lTotalPax30Days = 0;

			int lTotalPax60Days = 0;

			int lTotalPax90Days = 0;

			int lTotalPax6Months = 0;

			int lTotalPax1Year = 0;

			for (int i = 0; i < lDataJSONArray.length(); i++) {

				JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);

				int lMonth = 0;
				int lYear = 0;

				if (lJsonObj.has("month"))
					lMonth = Math.round(Float.parseFloat(lJsonObj.get("month").toString()));

				if (lJsonObj.has("year"))
					lYear = Math.round(Float.parseFloat(lJsonObj.get("year").toString()));

				long lDiffDays = Utility.getDifferenceDays(

						Utility.convertStringToDateFromat(
								Utility.getCurrentYear() + "-" + Utility.getCurrentMonth() + "-" + "01"),
						Utility.convertStringToDateFromat(lYear + "-" + lMonth + "-" + "01"));

				long diffMonth = Math.round(((float) lDiffDays) / 30);

				if (lJsonObj.has("pax") && lJsonObj.get("pax") != null
						&& !lJsonObj.get("pax").toString().equalsIgnoreCase("null")) {

					if (diffMonth >= 0) {
						if (diffMonth <= 1) {

							lTotalPax30Days = lTotalPax30Days + Integer.parseInt(lJsonObj.get("pax").toString());

						}

						if (diffMonth <= 2) {

							lTotalPax60Days = lTotalPax60Days + Integer.parseInt(lJsonObj.get("pax").toString());

						}

						if (diffMonth <= 3) {

							lTotalPax90Days = lTotalPax90Days + Integer.parseInt(lJsonObj.get("pax").toString());

						}

						if (diffMonth <= 6) {

							lTotalPax6Months = lTotalPax6Months + Integer.parseInt(lJsonObj.get("pax").toString());

						}

						if (diffMonth <= 12) {

							lTotalPax1Year = lTotalPax1Year + Integer.parseInt(lJsonObj.get("pax").toString());

						}

					}

				}

			}

			lForecastMap.put("30_days", lTotalPax30Days);
			lForecastMap.put("60_days", lTotalPax60Days);
			lForecastMap.put("90_days", lTotalPax90Days);
			lForecastMap.put("6_months", lTotalPax6Months);
			lForecastMap.put("1_year", lTotalPax1Year);

			if (pRequestModel.getOdArray() != null)
				lOdMap.put(pRequestModel.getOdArray()[0], lForecastMap);
			else
				lOdMap.put(lUserProfileModel.getSig_od(), lForecastMap);

		}

		return lOdMap;
	}

	@Override
	public Object getFareBrands(RequestModel pRequestModel) {

		return mCompAnalysisDao.getFareBrands(pRequestModel);

	}

	@Override
	public List<FaresSellup> getFareGridAllFares(RequestModel pRequestModel) {
		// TODO Auto-generated method stub

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);

		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getFareGridAllFares(pRequestModel, lUserProfileModel);

		JSONArray lDataJSONArray = null;
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);
		}

		List<FaresSellup> lFaresSellupList = new ArrayList<FaresSellup>();
		for (int i = 0; i < lDataJSONArray.length(); i++) {

			JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);

			FaresSellup lFaresSellUpModel = new FaresSellup();

			if (lJsonObj.has("carrier"))
				lFaresSellUpModel.setCarrier(lJsonObj.get("carrier").toString());

			if (lJsonObj.has("od"))
				lFaresSellUpModel.setOd(lJsonObj.get("od").toString());

			JSONArray originArray = null;
			if (lJsonObj.has("origin"))
				originArray = new JSONArray(lJsonObj.get("origin").toString());

			if (originArray != null && originArray.length() != 0)
				lFaresSellUpModel.setOrigin(originArray.get(0).toString());

			JSONArray destinationArray = null;
			if (lJsonObj.has("destination"))
				destinationArray = new JSONArray(lJsonObj.get("destination").toString());
			if (destinationArray != null && destinationArray.length() != 0)
				lFaresSellUpModel.setDestination(destinationArray.get(0).toString());

			JSONArray lFaresModelArray = null;
			if (lJsonObj.has("fare"))
				lFaresModelArray = new JSONArray(lJsonObj.get("fare").toString());

			JSONArray lFareBasisJSONArray = null;
			if (lJsonObj.has("fare_basis"))
				lFareBasisJSONArray = new JSONArray(lJsonObj.get("fare_basis").toString());

			JSONArray lRbdTypeJSONArray = null;
			if (lJsonObj.has("RBD_type"))
				lRbdTypeJSONArray = new JSONArray(lJsonObj.get("RBD_type").toString());

			JSONArray lRbdHierarchyJSONArray = null;
			if (lJsonObj.has("RBD_hierarchy"))
				lRbdHierarchyJSONArray = new JSONArray(lJsonObj.get("RBD_hierarchy").toString());

			int lLowestFare = 0;
			if (lJsonObj.has("lowest_fare"))
				lLowestFare = Math.round(Float.parseFloat(lJsonObj.get("lowest_fare").toString()));
			lFaresSellUpModel.setLowestFrae(lLowestFare);

			Set<String> lFareBasisSet = new HashSet<String>();
			for (int r = 0; r < lFareBasisJSONArray.length(); r++) {
				if (!lFareBasisJSONArray.get(r).toString().equalsIgnoreCase("null")
						&& !lFareBasisJSONArray.get(r).toString().equalsIgnoreCase("[]"))
					lFareBasisSet.add(lFareBasisJSONArray.get(r).toString());
			}

			Set<String> lRbdTypeSet = new HashSet<String>();
			for (int r = 0; r < lRbdTypeJSONArray.length(); r++) {
				if (!lRbdTypeJSONArray.get(r).toString().equalsIgnoreCase("null")
						&& !lRbdTypeJSONArray.get(r).toString().equalsIgnoreCase("[]"))
					lRbdTypeSet.add(lRbdTypeJSONArray.get(r).toString());
			}

			Set<Integer> lRbdHierarchySet = new HashSet<Integer>();
			for (int r = 0; r < lRbdHierarchyJSONArray.length(); r++) {
				if (!lRbdHierarchyJSONArray.get(r).toString().equalsIgnoreCase("null")
						&& !lRbdHierarchyJSONArray.get(r).toString().equalsIgnoreCase("[]"))
					lRbdHierarchySet.add(Integer.parseInt(lRbdHierarchyJSONArray.get(r).toString()));
			}

			List<Integer> lSellUpList = null;
			Map<String, Object> lRbdSellUpMap = new HashMap<String, Object>();
			Map<Integer, Integer> lSellUpResultMap = new HashMap<Integer, Integer>();
			Map<String, List<Integer>> lRbdTypeFaresMap = new HashMap<String, List<Integer>>();
			Map<String, List<Integer>> lRbdTypeHierarchyMap = new HashMap<String, List<Integer>>();
			if (lRbdTypeJSONArray != null && lFaresModelArray != null
					&& lRbdTypeJSONArray.length() == lFaresModelArray.length()) {
				for (String lRbdType : lRbdTypeSet) {

					List<Integer> lRbdTypeFaresList = new ArrayList<Integer>();
					List<Integer> lHierarchyFareList = new ArrayList<Integer>();

					for (int r = 0; r < lRbdTypeJSONArray.length(); r++) {

						if (!lRbdTypeJSONArray.get(r).toString().equalsIgnoreCase("null")
								&& !lRbdTypeJSONArray.get(r).toString().equalsIgnoreCase("[]")
								&& lRbdTypeJSONArray.get(r).toString().equalsIgnoreCase(lRbdType)) {

							float lFare = 0;
							if (!lFaresModelArray.get(r).toString().equalsIgnoreCase("null")
									&& !lFaresModelArray.get(r).toString().equalsIgnoreCase("[]"))
								lFare = Float.parseFloat(lFaresModelArray.get(r).toString());

							float lRank = 0;
							if (!lRbdHierarchyJSONArray.get(r).toString().equalsIgnoreCase("null")
									&& !lRbdHierarchyJSONArray.get(r).toString().equalsIgnoreCase("[]"))
								Float.parseFloat(lRbdHierarchyJSONArray.get(r).toString());

							lRbdTypeFaresList.add(Math.round(lFare));
							lHierarchyFareList.add(Math.round(lRank));

						}

					}

					lRbdTypeFaresMap.put(lRbdType, lRbdTypeFaresList);
					lRbdTypeHierarchyMap.put(lRbdType, lHierarchyFareList);

				}

			}

			Set<String> lRbdSet = lRbdTypeFaresMap.keySet();
			Set<Integer> lFareRankSet = new HashSet<Integer>();

			for (String s : lRbdSet) {

				lSellUpList = new ArrayList<Integer>();
				List<Integer> lRbdFareList = null;
				List<Integer> lFareRankList = lRbdTypeHierarchyMap.get(s);

				for (int k = 0; k < lFareRankList.size(); k++) {
					lFareRankSet.add(lFareRankList.get(k));
				}

				for (Integer k : lFareRankSet) {
					lRbdFareList = new ArrayList<Integer>();
					for (int z = 0; z < lFareRankList.size(); z++) {

						if (k == lFareRankList.get(z)) {

							lRbdFareList.add(lRbdTypeFaresMap.get(s).get(z));

						}

					}

					Collections.sort(lRbdFareList);

					lSellUpResultMap.put(k, lRbdFareList.get(0));

				}

				List<Integer> lSellUpRankList = new ArrayList<Integer>();
				for (Integer t : lSellUpResultMap.keySet()) {

					lSellUpRankList.add(t);
				}

				Collections.sort(lSellUpRankList);

				for (int t = 0; t < lSellUpRankList.size(); t++) {

					if (t == lSellUpRankList.size() - 1)
						lSellUpList.add(lSellUpResultMap.get(lSellUpRankList.get(t)) - lLowestFare);
					else
						lSellUpList.add(lSellUpResultMap.get(lSellUpRankList.get(t))
								- lSellUpResultMap.get(lSellUpRankList.get(t + 1)));

				}

				lRbdSellUpMap.put(s, lSellUpList);

			}

			lFaresSellUpModel.setSellUp(lRbdSellUpMap);

			lFaresSellupList.add(lFaresSellUpModel);

		}

		return lFaresSellupList;
	}

	@Override
	public List<FareAnalysis> getFareAnalysis(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);
		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getFareAnalysis(pRequestModel, lUserProfileModel);
		JSONArray lDataJSONArray = null;
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);
		}

		List<FareAnalysis> lFareAnalysisList = new ArrayList<FareAnalysis>();
		if (lDataJSONArray != null) {
			for (int i = 0; i < lDataJSONArray.length(); i++) {

				JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);
				FareAnalysis lFareAnalysisModel = new FareAnalysis();

				if (lJsonObj.has("Tariff_Num"))
					lFareAnalysisModel.setTariffNum(lJsonObj.get("Tariff_Num").toString());

				if (lJsonObj.has("Tariff_Code"))
					lFareAnalysisModel.setTariffCode(lJsonObj.get("Tariff_Code").toString());

				if (lJsonObj.has("CXR"))
					lFareAnalysisModel.setCxr(lJsonObj.get("CXR").toString());

				if (lJsonObj.has("region"))
					lFareAnalysisModel.setRegion(lJsonObj.get("region").toString());

				if (lJsonObj.has("country"))
					lFareAnalysisModel.setCountry(lJsonObj.get("country").toString());

				if (lJsonObj.has("pos"))
					lFareAnalysisModel.setPos(lJsonObj.get("pos").toString());

				if (lJsonObj.has("origin"))
					lFareAnalysisModel.setOrigin(lJsonObj.get("origin").toString());

				if (lJsonObj.has("destination"))
					lFareAnalysisModel.setDestination(lJsonObj.get("destination").toString());

				if (lJsonObj.has("compartment"))
					lFareAnalysisModel.setCompartment(lJsonObj.get("compartment").toString());

				if (lJsonObj.has("Rule"))
					lFareAnalysisModel.setRule(lJsonObj.get("Rule").toString());

				if (lJsonObj.has("Fare_Class"))
					lFareAnalysisModel.setFareClass(lJsonObj.get("Fare_Class").toString());

				if (lJsonObj.has("OW_RT"))
					lFareAnalysisModel.setOW_RT(lJsonObj.get("OW_RT").toString());

				if (lJsonObj.has("CUR"))
					lFareAnalysisModel.setCUR(lJsonObj.get("CUR").toString());

				if (lJsonObj.has("FN"))
					lFareAnalysisModel.setFN(lJsonObj.get("FN").toString());

				if (lJsonObj.has("RTG"))
					lFareAnalysisModel.setRTG(lJsonObj.get("RTG").toString());

				if (lJsonObj.has("Eff_Date"))
					lFareAnalysisModel.setEff_Date(lJsonObj.get("Eff_Date").toString());

				if (lJsonObj.has("Disc_Date"))
					lFareAnalysisModel.setDisc_Date(lJsonObj.get("Disc_Date").toString());

				if (lJsonObj.has("Last_Tkt_Date"))
					lFareAnalysisModel.setLast_Tkt_Date(lJsonObj.get("Last_Tkt_Date").toString());

				if (lJsonObj.has("Fare_Amount"))
					lFareAnalysisModel.setFare_Amount(lJsonObj.get("Fare_Amount").toString());

				if (lJsonObj.has("Batch"))
					lFareAnalysisModel.setBatch(lJsonObj.get("Batch").toString());

				lFareAnalysisList.add(lFareAnalysisModel);

			}
		}

		return lFareAnalysisList;
	}

	@Override
	public Map<String, Object> getProductIndicatorReport(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);

		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getProductIndicatorReport(lUserProfileModel);
		JSONArray lDataJSONArray = null;
		if (lResultObjList != null)
			lDataJSONArray = new JSONArray(lResultObjList);

		List<Object> lAtAirportList = new ArrayList<Object>();
		List<Object> lPreBookingList = new ArrayList<Object>();
		List<Object> lInFlightList = new ArrayList<Object>();
		List<Object> lPostFlightList = new ArrayList<Object>();

		Map<String, Object> productIndicatorMap = new HashMap<String, Object>();

		for (int i = 0; i < lDataJSONArray.length(); i++) {

			JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);

			if (lJsonObj.has("Category")) {

				if (lJsonObj.get("Category").toString().equalsIgnoreCase("Post Flight")) {
					lPostFlightList.add(lJsonObj.toString());

				}

				if ("In Flight".equalsIgnoreCase(lJsonObj.get("Category").toString())) {
					lInFlightList.add(lJsonObj.toString());

				}

				if ("At Airport".equalsIgnoreCase(lJsonObj.get("Category").toString())) {
					lAtAirportList.add(lJsonObj.toString().toString());

				}

				if ("PRE BOOKING".equalsIgnoreCase(lJsonObj.get("Category").toString())) {
					lPreBookingList.add(lJsonObj.toString());

				}

			}

		}

		productIndicatorMap.put("Pre Booking Tab", lPreBookingList);
		productIndicatorMap.put("At Airport Tab", lAtAirportList);
		productIndicatorMap.put("In Flight Tab", lInFlightList);
		productIndicatorMap.put("Post Flight Tab", lPostFlightList);

		return productIndicatorMap;
	}

	@Override
	public Map<String, Object> getFlightAgentFriendsFoes(RequestModel pRequestModel) {

		Map<String, Object> lFriendsAndFoesMap = new HashMap<String, Object>();

		List<Object> lFriendsList = new ArrayList<Object>();
		List<Object> lFoesList = new ArrayList<Object>();
		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);
		ArrayList<DBObject> resultObjFriendsList = mCompAnalysisDao.getFlightAgentFriends(pRequestModel,
				lUserProfileModel);
		JSONArray dataFriends = null;
		if (resultObjFriendsList != null) {
			dataFriends = new JSONArray(resultObjFriendsList);

		}

		if (dataFriends != null) {

			float totalPax = 0;
			float avgPax = 0;

			for (int i = 0; i < dataFriends.length(); i++) {

				JSONObject lJsonObj = dataFriends.getJSONObject(i);

				if (lJsonObj.has("month1") && lJsonObj.has("month2") && lJsonObj.has("month3")) {

					totalPax += Float.parseFloat(lJsonObj.get("month1").toString())
							+ Float.parseFloat(lJsonObj.get("month2").toString())
							+ Float.parseFloat(lJsonObj.get("month3").toString());

				}

			}

			avgPax = totalPax / (float) dataFriends.length();

			for (int i = 0; i < dataFriends.length(); i++) {

				JSONObject lJsonObj = dataFriends.getJSONObject(i);

				if (lJsonObj.has("month1") && lJsonObj.has("month2") && lJsonObj.has("month3")) {

					float pax = 0;
					pax = Float.parseFloat(lJsonObj.get("month1").toString())
							+ Float.parseFloat(lJsonObj.get("month2").toString())
							+ Float.parseFloat(lJsonObj.get("month3").toString());

					if (pax >= avgPax) {
						Flight_Agents_Friends lFriendsModel = new Flight_Agents_Friends();

						if (lJsonObj.has("agent"))
							lFriendsModel.setName(lJsonObj.get("agent").toString());

						if (lJsonObj.has("IATAAgentNumber"))
							lFriendsModel.setIataAgent(lJsonObj.get("IATAAgentNumber").toString());

						if (lJsonObj.has("month1"))
							lFriendsModel.setMonth1(lJsonObj.get("month1").toString());

						if (lJsonObj.has("month2"))
							lFriendsModel.setMonth2(lJsonObj.get("month2").toString());

						if (lJsonObj.has("month3"))
							lFriendsModel.setMonth3(lJsonObj.get("month3").toString());

						lFriendsList.add(lFriendsModel);

					} else {

						Flight_Agents_Friends lFriendsModel = new Flight_Agents_Friends();

						if (lJsonObj.has("agent"))
							lFriendsModel.setName(lJsonObj.get("agent").toString());

						if (lJsonObj.has("IATAAgentNumber"))
							lFriendsModel.setIataAgent(lJsonObj.get("IATAAgentNumber").toString());

						if (lJsonObj.has("month1"))
							lFriendsModel.setMonth1(lJsonObj.get("month1").toString());

						if (lJsonObj.has("month2"))
							lFriendsModel.setMonth2(lJsonObj.get("month2").toString());

						if (lJsonObj.has("month3"))
							lFriendsModel.setMonth3(lJsonObj.get("month3").toString());

						lFoesList.add(lFriendsModel);

					}

				}

			}

			lFriendsAndFoesMap.put("Flight_Agent_Friends", lFriendsList);
			lFriendsAndFoesMap.put("Flight_Agent_Foes", lFoesList);

		}

		return lFriendsAndFoesMap;

	}

	@Override
	public Map<String, Object> getScheduleInformation(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);
		ArrayList<DBObject> lResultObjScheduleDirectList = mCompAnalysisDao.getScheduleDirect(pRequestModel,
				lUserProfileModel);

		JSONArray lDataScheduleDirectJSONArray = null;
		if (lResultObjScheduleDirectList != null) {
			lDataScheduleDirectJSONArray = new JSONArray(lResultObjScheduleDirectList);

		}

		Map<String, Object> lResultMap = new HashMap<String, Object>();
		List<Object> lDirectScheduleList = new ArrayList<Object>();
		if (lDataScheduleDirectJSONArray != null) {
			for (int i = 0; i < lDataScheduleDirectJSONArray.length(); i++) {

				JSONObject lJsonObj = lDataScheduleDirectJSONArray.getJSONObject(i);
				ScheduleDirect lScheduleDirectModel = new ScheduleDirect();

				if (lJsonObj.has("aircraft"))
					lScheduleDirectModel.setAircraft(lJsonObj.get("aircraft").toString());

				if (lJsonObj.has("route_capacity"))
					lScheduleDirectModel
							.setRoute_capacity(Math.round(Float.parseFloat(lJsonObj.get("route_capacity").toString())));

				if (lJsonObj.has("airline"))
					lScheduleDirectModel.setAirline(lJsonObj.get("airline").toString());

				if (lJsonObj.has("No_of_flight"))
					lScheduleDirectModel
							.setNo_of_flight(Math.round(Float.parseFloat(lJsonObj.get("No_of_flight").toString())));

				if (lJsonObj.has("No_of_aircrafts"))
					lScheduleDirectModel.setNo_of_aircrafts(
							Math.round(Float.parseFloat(lJsonObj.get("No_of_aircrafts").toString())));

				if (lJsonObj.has("average_trip_time"))
					lScheduleDirectModel.setAverage_trip_time(
							Math.round(Float.parseFloat(lJsonObj.get("average_trip_time").toString())));

				if (lJsonObj.has("flights_per_day"))
					lScheduleDirectModel.setFlights_per_day(
							Math.round(Float.parseFloat(lJsonObj.get("flights_per_day").toString())));

				JSONArray lAircraftTypeJSONArray = null;
				if (lJsonObj.has("type_of_aircraft"))
					lAircraftTypeJSONArray = new JSONArray(lJsonObj.get("type_of_aircraft").toString());
				String[] lAircraftTypeArray = new String[lAircraftTypeJSONArray.length()];

				if (lAircraftTypeJSONArray != null) {
					for (int k = 0; k < lAircraftTypeJSONArray.length(); k++) {
						lAircraftTypeArray[k] = lAircraftTypeJSONArray.get(k).toString();
					}

					lScheduleDirectModel.setType_of_aircraft(lAircraftTypeArray);

				}

				if (lJsonObj.has("flight_nos_peak_time"))
					lScheduleDirectModel.setFlight_nos_peak_time(lJsonObj.get("flight_nos_peak_time").toString());

				if (lJsonObj.has("off_peak_time"))
					lScheduleDirectModel.setOff_peak_time(lJsonObj.get("off_peak_time").toString());

				JSONArray lOtpJSONArray = null;
				if (lJsonObj.has("OTP"))
					lOtpJSONArray = new JSONArray(lJsonObj.get("OTP").toString());

				String[] lOtpArray = null;

				if (lOtpJSONArray != null) {
					lOtpArray = new String[lOtpJSONArray.length()];
					for (int k = 0; k < lOtpJSONArray.length(); k++)
						lOtpArray[k] = lOtpJSONArray.get(k).toString();

					lScheduleDirectModel.setOTP(lOtpArray);
				}

				if (lJsonObj.has("frequency_shift"))
					lScheduleDirectModel.setFrequency_shift(lJsonObj.get("frequency_shift").toString());

				if (lJsonObj.has("rating"))
					lScheduleDirectModel.setRating(lJsonObj.get("rating").toString());

				if (lJsonObj.has("daily_capacity"))
					lScheduleDirectModel
							.setDaily_capacity(Math.round(Float.parseFloat(lJsonObj.get("daily_capacity").toString())));

				if (lJsonObj.has("rating"))
					lScheduleDirectModel.setRating(lJsonObj.get("rating").toString());

				if (lJsonObj.has("days_of_Week"))
					lScheduleDirectModel.setDays_of_Week(lJsonObj.get("days_of_Week").toString());

				lDirectScheduleList.add(lScheduleDirectModel);
			}
		}

		lResultMap.put("ScheduleDirect", lDirectScheduleList);
		ArrayList<DBObject> lResultObjScheduleIndirectList = mCompAnalysisDao.getScheduleIndirect(pRequestModel,
				lUserProfileModel);

		JSONArray lDataScheduleIndirect = null;
		if (lResultObjScheduleIndirectList != null) {
			lDataScheduleIndirect = new JSONArray(lResultObjScheduleIndirectList);

		}

		List<Object> lIndirectScheduleList = new ArrayList<Object>();
		if (lDataScheduleDirectJSONArray != null) {
			for (int i = 0; i < lDataScheduleIndirect.length(); i++) {

				JSONObject lJsonObj = lDataScheduleIndirect.getJSONObject(i);
				ScheduleIndirect lScheduleIndirectModel = new ScheduleIndirect();

				if (lJsonObj.has("aircraft"))
					lScheduleIndirectModel.setAircraft(lJsonObj.get("aircraft").toString());

				if (lJsonObj.has("route_capacity"))
					lScheduleIndirectModel
							.setRoute_capacity(Math.round(Float.parseFloat(lJsonObj.get("route_capacity").toString())));

				if (lJsonObj.has("airline"))
					lScheduleIndirectModel.setAirline(lJsonObj.get("airline").toString());

				if (lJsonObj.has("No_of_flight"))
					lScheduleIndirectModel
							.setNo_of_flight(Math.round(Float.parseFloat(lJsonObj.get("No_of_flight").toString())));

				if (lJsonObj.has("average_trip_time"))
					lScheduleIndirectModel.setAverage_trip_time(
							Math.round(Float.parseFloat(lJsonObj.get("average_trip_time").toString())));

				if (lJsonObj.has("flights_per_day"))
					lScheduleIndirectModel.setFlights_per_day(
							Math.round(Float.parseFloat(lJsonObj.get("flights_per_day").toString())));

				JSONArray lAircraftTypeJSONArray = null;
				if (lJsonObj.has("type_of_aircraft"))
					lAircraftTypeJSONArray = new JSONArray(lJsonObj.get("type_of_aircraft").toString());
				String[] lAircraftTypeArray = new String[lAircraftTypeJSONArray.length()];

				if (lAircraftTypeJSONArray != null) {
					for (int k = 0; k < lAircraftTypeJSONArray.length(); k++) {
						lAircraftTypeArray[k] = lAircraftTypeJSONArray.get(k).toString();
					}

					lScheduleIndirectModel.setType_of_aircraft(lAircraftTypeArray);

				}

				if (lJsonObj.has("flight_nos_peak_time"))
					lScheduleIndirectModel
							.setFlight_nos_peak_time(Float.parseFloat(lJsonObj.get("flight_nos_peak_time").toString()));

				if (lJsonObj.has("off_peak_time"))
					lScheduleIndirectModel.setOff_peak_time(Float.parseFloat(lJsonObj.get("off_peak_time").toString()));

				if (lJsonObj.has("OTP"))
					lScheduleIndirectModel.setOTP(Float.parseFloat(lJsonObj.get("OTP").toString()));

				if (lJsonObj.has("frequency_shift"))
					lScheduleIndirectModel
							.setFrequency_shift(Float.parseFloat(lJsonObj.get("frequency_shift").toString()));

				if (lJsonObj.has("rating"))
					lScheduleIndirectModel.setRating(Float.parseFloat(lJsonObj.get("rating").toString()));

				if (lJsonObj.has("daily_capacity"))
					lScheduleIndirectModel
							.setDaily_capacity(Math.round(Float.parseFloat(lJsonObj.get("daily_capacity").toString())));

				if (lJsonObj.has("rating"))
					lScheduleIndirectModel.setRating(Float.parseFloat(lJsonObj.get("rating").toString()));

				if (lJsonObj.has("stops"))
					lScheduleIndirectModel.setStops(Float.parseFloat(lJsonObj.get("stops").toString()));

				if (lJsonObj.has("transit_time"))
					lScheduleIndirectModel.setTransit_time(Float.parseFloat(lJsonObj.get("transit_time").toString()));

				if (lJsonObj.has("days_of_Week"))
					lScheduleIndirectModel.setDays_of_Week(new JSONArray(lJsonObj.get("days_of_Week").toString())
							.toString().replace("},{", " ,").split(" "));

				lIndirectScheduleList.add(lScheduleIndirectModel);

			}
		}

		lResultMap.put("ScheduleIndirect", lIndirectScheduleList);

		return lResultMap;
	}

	@Override
	public ArrayList<DBObject> getScheduleNetwork(RequestModel pRequestModel) {
		return mCompAnalysisDao.getScheduleNetwork();

	}

	@Override
	public ArrayList<DBObject> getScheduleInformationOd(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);

		return mCompAnalysisDao.getScheduleInformationOd(pRequestModel, lUserProfileModel);
	}

	@Override
	public Map<String, Object> getMarketEntrantsLeavers(RequestModel requestmodel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(requestmodel);
		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getMarketEntrantsLeavers(requestmodel, lUserProfileModel);
		Map<String, Object> lEntrantsLeaversMap = new HashMap<String, Object>();
		Map<String, EntrantsLeavers> lEntrantsLeaversModelMap = new HashMap<String, EntrantsLeavers>();

		JSONArray lDataJSONArray = null;
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);
		}
		if (lDataJSONArray != null) {
			for (int i = 0; i < lDataJSONArray.length(); i++) {
				JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);
				String lRegion = "-";

				if (checkIfValueExist("region", lJsonObj)) {
					lRegion = lJsonObj.getString("region");
				}

				String lCountry = "-";
				if (checkIfValueExist("country", lJsonObj)) {
					lCountry = lJsonObj.getString("country");
				}

				String lPos = "-";
				if (checkIfValueExist("pos", lJsonObj)) {
					lPos = lJsonObj.getString("pos");
				}

				String lOrigin = "-";
				if (checkIfValueExist("origin", lJsonObj)) {
					lOrigin = lJsonObj.getString("origin");
				}

				String lDestination = "-";
				if (checkIfValueExist("destination", lJsonObj)) {
					lDestination = lJsonObj.getString("destination");
				}

				String lOption = "-";
				if (checkIfValueExist("option", lJsonObj)) {
					lOption = lJsonObj.getString("option");
				}

				String lOd = lOrigin + lDestination;
				String lKey = lRegion + lCountry + lPos + lOd;

				if (!lEntrantsLeaversModelMap.containsKey(lKey)) {
					EntrantsLeavers lEntrantsLeaversModel = new EntrantsLeavers();
					lEntrantsLeaversModel.setRegion(lRegion);
					lEntrantsLeaversModel.setCountry(lCountry);
					lEntrantsLeaversModel.setOrigin(lOrigin);
					lEntrantsLeaversModel.setDestination(lDestination);
					lEntrantsLeaversModel.setPos(lPos);
					lEntrantsLeaversModel.setOd(lOd);
					if (lOption.equals("Entry"))
						lEntrantsLeaversModel.setEntrants(1D);
					else
						lEntrantsLeaversModel.setLeavers(1D);
					lEntrantsLeaversModel.setCombinationKey(lKey);
					lEntrantsLeaversModelMap.put(lKey, lEntrantsLeaversModel);

				} else {
					EntrantsLeavers lEntrantsLeaversModel = (EntrantsLeavers) lEntrantsLeaversModelMap.get(lKey);
					if (lOption.equals("Entry"))
						lEntrantsLeaversModel.setEntrants(lEntrantsLeaversModel.getEntrants() + 1);
					else
						lEntrantsLeaversModel.setLeavers(lEntrantsLeaversModel.getLeavers() + 1);
				}
			}
		}
		List<EntrantsLeavers> lEntrantsLeaversList = new ArrayList<EntrantsLeavers>(lEntrantsLeaversModelMap.values());
		lEntrantsLeaversMap.put("MarketEntrantsLeavers", lEntrantsLeaversList);
		return lEntrantsLeaversMap;
	}

	public List<FlightAnalysis> getFlightAnalysis(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);

		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getFlightAnalysis(pRequestModel, lUserProfileModel);

		JSONArray lDataJSONArray = null;
		List<FlightAnalysis> lFlightAnalysisList = new ArrayList<FlightAnalysis>();
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);
		}

		for (int i = 0; i < lDataJSONArray.length(); i++) {

			JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);

			FlightAnalysis lFlightAnalysisModel = new FlightAnalysis();

			if (lJsonObj.has("airline"))
				lFlightAnalysisModel.setAirline(lJsonObj.get("airline").toString());

			if (lJsonObj.has("od"))
				lFlightAnalysisModel.setOd(lJsonObj.get("od").toString());

			if (lJsonObj.has("flight_no"))
				lFlightAnalysisModel.setFlight_no(lJsonObj.get("flight_no").toString());

			if (lJsonObj.has("aircraft"))
				lFlightAnalysisModel.setAircraft(lJsonObj.get("aircraft").toString());

			if (lJsonObj.has("frequency"))
				lFlightAnalysisModel.setFrequency(lJsonObj.get("frequency").toString());

			if (lJsonObj.has("effective_from_date"))
				lFlightAnalysisModel.setEffective_from_date(lJsonObj.get("effective_from_date").toString());

			if (lJsonObj.has("effective_to_date"))
				lFlightAnalysisModel.setEffective_to_date(lJsonObj.get("effective_to_date").toString());

			if (lJsonObj.has("start_time"))
				lFlightAnalysisModel.setEffective_to_date(lJsonObj.get("start_time").toString());

			if (lJsonObj.has("end_time"))
				lFlightAnalysisModel.setEnd_time(lJsonObj.get("end_time").toString());

			int lTimeTravel = 0;
			if (lJsonObj.has("time_travel"))
				lTimeTravel = Math.round(Float.parseFloat(lJsonObj.get("time_travel").toString()) / 3600);
			lFlightAnalysisModel.setTime_travel(lTimeTravel);

			int lCcapacity = 0;
			if (lJsonObj.has("C"))
				lCcapacity = Integer.parseInt(lJsonObj.get("C").toString());

			int lYcapacity = 0;
			if (lJsonObj.has("Y"))
				lYcapacity = Integer.parseInt(lJsonObj.get("Y").toString());

			int lJcapacity = 0;
			if (lJsonObj.has("J"))
				lJcapacity = Integer.parseInt(lJsonObj.get("J").toString());

			int lFcapacity = 0;
			if (lJsonObj.has("F"))
				lFcapacity = Integer.parseInt(lJsonObj.get("F").toString());

			lFlightAnalysisModel.setFlightCapacity(lCcapacity + lYcapacity + lJcapacity + lFcapacity);

			if (lJsonObj.has("Total_capacity"))
				lFlightAnalysisModel.setTotal_capacity(Integer.parseInt(lJsonObj.get("Total_capacity").toString()));

			lFlightAnalysisList.add(lFlightAnalysisModel);

		}

		return lFlightAnalysisList;

	}

	@Override
	public List<MonthlyLowFare> getMonthlyLowFare(RequestModel pRequestModel) {

		UserProfile lUserProfileModel = mMainDashboardDao.getUserProfile(pRequestModel);

		ArrayList<DBObject> lResultObjList = mCompAnalysisDao.getMonthlyLowFare(pRequestModel, lUserProfileModel);

		JSONArray lDataJSONArray = null;
		List<MonthlyLowFare> lMonthlyLowFareList = new ArrayList<MonthlyLowFare>();
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);
		}

		for (int i = 0; i < lDataJSONArray.length(); i++) {

			JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);

			MonthlyLowFare lMonthlyLowFareModel = new MonthlyLowFare();

			if (lJsonObj.has("lowest_fare"))
				lMonthlyLowFareModel.setLowFare(Math.round(Float.parseFloat(lJsonObj.get("lowest_fare").toString())));

			if (lJsonObj.has("carrier"))
				lMonthlyLowFareModel.setCarrier(lJsonObj.get("carrier").toString());

			if (lJsonObj.has("od"))
				lMonthlyLowFareModel.setOd(lJsonObj.get("od").toString());

			if (lJsonObj.has("dep_date"))
				lMonthlyLowFareModel.setDepDate(lJsonObj.get("dep_date").toString());

			lMonthlyLowFareList.add(lMonthlyLowFareModel);

		}

		return lMonthlyLowFareList;
	}

	private boolean checkIfValueExist(String pStr, JSONObject pJsonObj) {
		boolean valueExist = false;
		boolean lCondition1 = pJsonObj.has(pStr) && pJsonObj.get(pStr) != null;

		if (lCondition1 && !"null".equalsIgnoreCase(pJsonObj.get(pStr).toString())) {
			valueExist = true;
		}
		return valueExist;
	}
}
