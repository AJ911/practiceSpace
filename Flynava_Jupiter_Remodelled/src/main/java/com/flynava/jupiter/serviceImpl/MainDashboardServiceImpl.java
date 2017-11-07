package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.MainDashboardDao;
import com.flynava.jupiter.model.Bookings;
import com.flynava.jupiter.model.Events;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.Sales;
import com.flynava.jupiter.model.UserProfile;
import com.flynava.jupiter.serviceInterface.MainDashboardService;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.FilterUtil;
import com.flynava.jupiter.util.Utility;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class MainDashboardServiceImpl implements MainDashboardService {

	@Autowired
	MainDashboardDao mMainDashboardDao;

	private static final Logger logger = Logger.getLogger(MainDashboardServiceImpl.class);

	@Override
	public Map<String, Object> getBookings(RequestModel pRequestModel) {

		UserProfile lUserProfile = mMainDashboardDao.getUserProfile(pRequestModel);

		if (lUserProfile != null && "network".equalsIgnoreCase(lUserProfile.getLevel())) {
			if (pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() == null
					&& pRequestModel.getCountryArray() != null) {

				pRequestModel.setRegionArray(
						mMainDashboardDao.getRegion(pRequestModel, "COUNTRY_CD", pRequestModel.getCountryArray()));

			} else if (pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() != null
					&& pRequestModel.getCountryArray() != null) {

				pRequestModel.setRegionArray(
						mMainDashboardDao.getRegion(pRequestModel, "COUNTRY_CD", pRequestModel.getCountryArray()));

			} else if (pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() != null
					&& pRequestModel.getCountryArray() == null) {

				pRequestModel.setRegionArray(
						mMainDashboardDao.getRegion(pRequestModel, "POS_CD", pRequestModel.getPosArray()));

				pRequestModel.setCountryArray(
						mMainDashboardDao.getCountry(pRequestModel, "POS_CD", pRequestModel.getPosArray()));

			} else if (pRequestModel.getRegionArray() != null && pRequestModel.getPosArray() != null
					&& pRequestModel.getCountryArray() == null) {

				pRequestModel.setCountryArray(
						mMainDashboardDao.getCountry(pRequestModel, "POS_CD", pRequestModel.getPosArray()));

			}

		}

		ArrayList<DBObject> lResultObjList = mMainDashboardDao.getBookings(pRequestModel);
		Map<String, Object> lResponseBookingsMap = new HashMap<String, Object>();
		JSONArray lDataJSONArray = null;
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);

		}

		try {

			if (lDataJSONArray != null) {

				Map<String, Object> lResponseGridMap = new HashMap<String, Object>();
				Map<String, Object> lResponseDepDateMap = new HashMap<String, Object>();

				lResponseGridMap = getCalculatedBookings(lDataJSONArray, FilterUtil.getFilter(pRequestModel),
						pRequestModel, lUserProfile);
				lResponseDepDateMap = getCalculatedBookings(lDataJSONArray, "depDate", pRequestModel, lUserProfile);

				lResponseBookingsMap.put("Graph", lResponseDepDateMap);
				lResponseBookingsMap.put("Grid", lResponseGridMap);

			} else {
				lResponseBookingsMap.put("Graph", null);
				lResponseBookingsMap.put("Grid", null);
			}
		} catch (Exception e) {
			logger.error("getBookings-Exception", e);
		}

		return lResponseBookingsMap;

	}

	public Map<String, Object> getCalculatedBookings(JSONArray lDataJSONArray, String lKeyType,
			RequestModel pRequestModel, UserProfile lUserProfile) {

		Map<String, Object> lResultMap = new HashMap<String, Object>();
		Bookings lBookingModel = null;
		// Map<String, Object> lResponseBookingsMap = null;
		Map<String, Object> lCompMap = null;
		Map<String, Object> lUserDataMap = new HashMap<String, Object>();
		List<Object> lKeyList = new ArrayList<Object>();

		try {

			if (lDataJSONArray != null) {

				for (int i = 0; i < lDataJSONArray.length(); i++) {

					JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);

					String lKey = null;
					if ("depDate".equalsIgnoreCase(lKeyType) && lJsonObj.has("dep_date"))
						lKey = lJsonObj.get("dep_date").toString();

					else if ("POS+OD+compartment".equalsIgnoreCase(lKeyType) && lJsonObj.has("pos")
							&& lJsonObj.has("origin") && lJsonObj.has("destination") && lJsonObj.has("compartment"))
						lKey = lJsonObj.get("pos").toString() + lJsonObj.get("origin").toString()
								+ lJsonObj.get("destination").toString() + lJsonObj.get("compartment");

					else if ("country+POS+OD+compartment".equalsIgnoreCase(lKeyType) && lJsonObj.has("country")
							&& lJsonObj.has("pos") && lJsonObj.has("origin") && lJsonObj.has("destination")
							&& lJsonObj.has("compartment"))
						lKey = lJsonObj.get("country").toString() + lJsonObj.get("pos").toString()
								+ lJsonObj.get("origin").toString() + lJsonObj.get("destination").toString()
								+ lJsonObj.get("compartment");

					else if ("region".equalsIgnoreCase(lKeyType) && lJsonObj.has("region")
							&& lJsonObj.has("compartment"))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("compartment").toString();

					else if ("regionFilter".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("compartment").toString();

					else if ("county".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("country").toString() + lJsonObj.get("pos").toString()
								+ lJsonObj.get("compartment").toString();

					else if ("pos".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("pos").toString() + lJsonObj.get("compartment").toString();

					else if ("od".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("origin").toString() + lJsonObj.get("destination").toString()
								+ lJsonObj.get("compartment").toString();

					else if ("od+compartment".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("origin").toString() + lJsonObj.get("destination").toString()
								+ lJsonObj.get("compartment").toString();

					else if ("region+country".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("pos").toString() + lJsonObj.get("compartment").toString();

					else if ("region+country+OD".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("origin").toString() + lJsonObj.get("destination").toString()
								+ lJsonObj.get("compartment").toString();

					else if (lKeyType.equalsIgnoreCase("region+country+POS"))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("pos").toString() + lJsonObj.get("compartment").toString();

					else if ("region+country+POS+OD".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("pos").toString() + lJsonObj.get("origin").toString()
								+ lJsonObj.get("destination").toString() + lJsonObj.get("compartment").toString();

					else if ("region+OD".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("origin").toString()
								+ lJsonObj.get("destination").toString() + lJsonObj.get("compartment").toString();

					else if ("region+country+POS+OD+compartment".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("pos").toString() + lJsonObj.get("origin").toString()
								+ lJsonObj.get("destination").toString() + lJsonObj.get("compartment").toString();
					else
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString();

					if (lResultMap.containsKey(lKey)
							&& ((Bookings) lResultMap.get(lKey)).getKey().equalsIgnoreCase(lKey)) {

						/*
						 * getting lBookingModel object corresponding to the
						 * lKey which is already existing in result map
						 */
						lBookingModel = (Bookings) lResultMap.get(lKey);

						float lPax = 0;
						float lPaxlastyr = 0;
						float lFlownPax = 0;
						float lTargetPax = 0;
						float lForecastPax = 0;
						float lMarketSharePax = 0;
						float lMarketSharePaxlastyr = 0;
						float lTargetMarketSharePax = 0;
						float lCurrentObjPax = 0;
						float lCurrentObjPaxlastyr = 0;
						float lCurrentObjTicket = 0;
						float lTicket = 0;

						JSONArray lCarrierJSONArray = null;
						if (lJsonObj.has("carrier"))
							lCarrierJSONArray = new JSONArray(lJsonObj.get("carrier").toString());

						JSONArray lMarketSharePaxJSONArray = null;
						if (lJsonObj.has("market_share_pax"))
							lMarketSharePaxJSONArray = new JSONArray(lJsonObj.get("market_share_pax").toString());

						JSONArray lMarketSharePax_1JSONArray = null;
						if (lJsonObj.has("market_share_pax_1"))
							lMarketSharePax_1JSONArray = new JSONArray(lJsonObj.get("market_share_pax_1").toString());

						JSONArray lHostTargetPaxJSONArray = null;
						if (lJsonObj.has("target_pax"))
							lHostTargetPaxJSONArray = new JSONArray(lJsonObj.get("target_pax").toString());

						JSONArray lCapacityAirline = null;
						if (lJsonObj.has("capacity_airline"))
							lCapacityAirline = new JSONArray(lJsonObj.get("capacity_airline").toString());

						JSONArray lCapacityJSONArray = null;
						if (lJsonObj.has("capacity"))
							lCapacityJSONArray = new JSONArray(lJsonObj.get("capacity").toString());

						JSONArray lCapacity_1JSONArray = null;
						if (lJsonObj.has("capacity_1"))
							lCapacity_1JSONArray = new JSONArray(lJsonObj.get("capacity_1").toString());

						JSONArray lCarrierRatingArray = null;
						if (lJsonObj.has("rating_carrier"))
							lCarrierRatingArray = new JSONArray(lJsonObj.get("rating_carrier").toString());

						JSONArray lRatingArray = null;
						if (lJsonObj.has("rating"))
							lRatingArray = new JSONArray(lJsonObj.get("rating").toString());

						JSONArray lPaxJSONArray = null;
						if (lJsonObj.has("pax"))
							lPaxJSONArray = new JSONArray(lJsonObj.get("pax").toString());

						JSONArray lPax_1JSONArray = null;
						if (lJsonObj.has("pax_1"))
							lPax_1JSONArray = new JSONArray(lJsonObj.get("pax_1").toString());

						JSONArray lForecastPaxJSONArray = null;
						if (lJsonObj.has("forecast_pax"))
							lForecastPaxJSONArray = new JSONArray(lJsonObj.get("forecast_pax").toString());

						JSONArray lFlownPaxJSONArray = null;
						if (lJsonObj.has("flown_pax"))
							lFlownPaxJSONArray = new JSONArray(lJsonObj.get("flown_pax").toString());

						JSONArray lTicketJSONArray = null;
						if (lJsonObj.has("ticket"))
							lTicketJSONArray = new JSONArray(lJsonObj.get("ticket").toString());

						JSONArray lChannelJSONArray = null;
						if (lJsonObj.has("channel") && lJsonObj.get("channel").toString().length() != 0)
							lChannelJSONArray = new JSONArray(lJsonObj.get("channel").toString());

						for (int p = 0; p < lPaxJSONArray.length(); p++)
							lCurrentObjPax += Long.parseLong(lPaxJSONArray.get(p).toString());

						for (int p = 0; p < lPax_1JSONArray.length(); p++)
							lCurrentObjPaxlastyr += Long.parseLong(lPax_1JSONArray.get(p).toString());

						if (lTicketJSONArray != null) {
							for (int t = 0; t < lTicketJSONArray.length(); t++)
								lCurrentObjTicket += Long.parseLong(lTicketJSONArray.get(t).toString());
						}

						/*
						 * calculating market size by adding all the market
						 * share lCurrentObjPax
						 */
						float lMarketSize = 0;
						if (lMarketSharePaxJSONArray != null)
							for (int m = 0; m < lMarketSharePaxJSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lMarketSharePaxJSONArray.get(m).toString()))
									lMarketSize += Float.parseFloat(lMarketSharePaxJSONArray.get(m).toString());
							}

						/*
						 * calculating market size_1 by adding all the market
						 * share lCurrentObjPax
						 */
						float lMarketSizelastyr = 0;
						if (lMarketSharePax_1JSONArray != null)
							for (int m = 0; m < lMarketSharePax_1JSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lMarketSharePax_1JSONArray.get(m).toString()))
									lMarketSizelastyr += Float.parseFloat(lMarketSharePax_1JSONArray.get(m).toString());
							}

						/* getting target pax from the array */
						float lTotalTargetPax = 0;
						if (lHostTargetPaxJSONArray != null) {
							for (int m = 0; m < lHostTargetPaxJSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lHostTargetPaxJSONArray.get(m).toString()))
									lTotalTargetPax += Float.parseFloat(lHostTargetPaxJSONArray.get(m).toString());
							}
						}

						/*
						 * checking if the departure date has passed. If the
						 * departure date has passed then the value of forecast
						 * pax will be set to 0
						 */
						float lTotalForecastPax = 0;
						if (!Utility.datePassed(lJsonObj.get("dep_date").toString())) {
							if (lForecastPaxJSONArray != null) {
								for (int m = 0; m < lForecastPaxJSONArray.length(); m++) {
									if (!"null".equalsIgnoreCase(lForecastPaxJSONArray.get(m).toString()))
										lTotalForecastPax += Float.parseFloat(lForecastPaxJSONArray.get(m).toString());
								}
							}
						}

						/* getting flown pax from the flown pax array */
						float lCurrentObjFlownPax = 0;
						if (lFlownPaxJSONArray != null) {
							for (int m = 0; m < lFlownPaxJSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lFlownPaxJSONArray.get(m).toString()))
									lCurrentObjFlownPax += Float.parseFloat(lFlownPaxJSONArray.get(m).toString());

							}
						}

						/*
						 * calculating number of days in the current object's
						 * departure date month
						 */
						int lNoOfDaysInMonth = Utility.numberOfDaysInMonth(
								Utility.findMonth(lJsonObj.get("dep_date").toString()) - 1,
								Utility.findYear(lJsonObj.get("dep_date").toString()));

						/* prorating the monthly target pax and forecast pax */
						float lCurrentObjProratedTargetPax = lTotalTargetPax / (float) lNoOfDaysInMonth;
						float lCurrentObjProratedForecastPax = lTotalForecastPax / (float) lNoOfDaysInMonth;

						/*
						 * adding pax, flown pax, ticket and pax last year of
						 * every record
						 */
						lPax = (float) lBookingModel.getPax() + lCurrentObjPax;
						lPaxlastyr = (float) lBookingModel.getPax_1() + lCurrentObjPaxlastyr;
						lFlownPax = (float) lBookingModel.getFlownPax() + lCurrentObjFlownPax;
						lTicket = (float) lBookingModel.getTicket() + lCurrentObjTicket;

						/*
						 * getting every carrier's capacity and multiplying it
						 * with that carrier's rating
						 */
						float lCarriersCapacity = 0;
						if (lCapacityAirline != null && lCarrierRatingArray != null) {
							for (int r = 0; r < lCapacityAirline.length(); r++) {
								for (int k = 0; k < lCarrierRatingArray.length(); k++) {
									if (lCarrierRatingArray.length() >= lCapacityAirline.length()
											&& lCapacityAirline.get(r).toString()
													.equalsIgnoreCase(lCarrierRatingArray.get(k).toString())
											&& lCarrierRatingArray.length() == lRatingArray.length()) {

										if (!lCapacityJSONArray.get(r).toString().equalsIgnoreCase("null")
												&& !lCapacityJSONArray.get(r).toString().equalsIgnoreCase("[]")
												&& !lRatingArray.get(k).toString().equalsIgnoreCase("null")
												&& !lRatingArray.get(k).toString().equalsIgnoreCase("[]"))
											lCarriersCapacity += Float.parseFloat(lCapacityJSONArray.get(r).toString())
													* (Float.parseFloat(lRatingArray.get(k).toString()));

									}
								}
							}
						}

						/* getting host capacity from the capacity array */
						float hostCapacity = 0;
						if (lCapacityAirline != null && lCapacityJSONArray != null
								&& lCapacityAirline.length() == lCapacityJSONArray.length()) {
							for (int r = 0; r < lCapacityAirline.length(); r++) {
								if (lCapacityAirline.get(r).toString().equalsIgnoreCase(Constants.hostName)) {
									hostCapacity += Float.parseFloat(lCapacityJSONArray.get(r).toString());
								}
							}

						}

						/*
						 * getting last year capacity of host from the capacity
						 * array
						 */
						float hostCapacity_1 = 0;
						if (lCapacityAirline != null && lCapacityJSONArray != null
								&& lCapacityAirline.length() == lCapacity_1JSONArray.length()) {
							for (int r = 0; r < lCapacityAirline.length(); r++) {
								if (lCapacityAirline.get(r).toString().equalsIgnoreCase(Constants.hostName)) {
									hostCapacity_1 += Float.parseFloat(lCapacity_1JSONArray.get(r).toString());
								}
							}

						}

						int lMonth = 0;
						int lDay = 0;
						if (lJsonObj.has("dep_date") && !"null".equalsIgnoreCase(lJsonObj.get("dep_date").toString())) {

							lMonth = Utility.findMonth(lJsonObj.get("dep_date").toString());
							lDay = Utility.findDay(lJsonObj.get("dep_date").toString());

						}

						if ("depDate".equalsIgnoreCase(lKeyType)) {
							if (lBookingModel.getMonth() != lMonth && lBookingModel.getDay() != lDay
									|| lBookingModel.getMonth() == lMonth && lBookingModel.getDay() == lDay) {

								lMarketSize += lBookingModel.getMarketSize();
								lMarketSizelastyr += lBookingModel.getMarketSize_1();
								lTargetPax = (float) lBookingModel.getTargetPax() + lCurrentObjProratedTargetPax;
								lForecastPax = (float) lBookingModel.getForecastPax() + lCurrentObjProratedForecastPax;
								lCarriersCapacity += (float) lBookingModel.getCarrierCapacity();
								hostCapacity += (float) lBookingModel.getHostCapacity();
								hostCapacity_1 += (float) lBookingModel.getHostCapacity_1();

							}
						} else {

							if (lBookingModel.getMonth() != lMonth) {

								lMarketSize += lBookingModel.getMarketSize();
								lMarketSizelastyr += lBookingModel.getMarketSize_1();
								lTargetPax = (float) lBookingModel.getTargetPax() + lCurrentObjProratedTargetPax;
								lForecastPax = (float) lBookingModel.getForecastPax() + lCurrentObjProratedForecastPax;
								lCarriersCapacity += (float) lBookingModel.getCarrierCapacity();
								hostCapacity += (float) lBookingModel.getHostCapacity();
								hostCapacity_1 += (float) lBookingModel.getHostCapacity_1();

							} else {

								lTargetPax = lCurrentObjProratedTargetPax;
								lForecastPax = lCurrentObjProratedForecastPax;
							}

						}

						if (lCarrierJSONArray != null && lUserProfile != null) {

							for (int c = 0; c < lCarrierJSONArray.length(); c++) {

								String lCarrier = lCarrierJSONArray.get(c).toString();

								Map<String, Object> lCompetitors = new HashMap<String, Object>();

								if (lMarketSharePax_1JSONArray.length() != 0)
									lMarketSharePaxlastyr = Float
											.parseFloat(lMarketSharePax_1JSONArray.get(c).toString());

								if (lMarketSharePaxJSONArray.length() != 0)
									lMarketSharePax = Float.parseFloat(lMarketSharePaxJSONArray.get(c).toString());

								/* getting carrier's rating */
								float lRating = Constants.DEFAULT_COMPETITOR_RATING;
								if (lCarrierRatingArray != null
										&& lCarrierRatingArray.length() >= lRatingArray.length()) {
									for (int r = 0; r < lCarrierRatingArray.length(); r++) {
										if (lCarrierRatingArray.get(r).toString().equalsIgnoreCase(lCarrier)) {
											if (!lRatingArray.get(r).toString().equalsIgnoreCase("null")
													&& !lRatingArray.get(r).toString().equalsIgnoreCase("[]"))

												lRating = Float.parseFloat(lRatingArray.get(r).toString());
										}
									}
								}

								/* getting carrier's capacity */
								float lAirlineCapcity = 0;
								if (lCapacityAirline != null && lCapacityJSONArray != null
										&& lCapacityAirline.length() == lCapacityJSONArray.length()) {
									for (int r = 0; r < lCapacityAirline.length(); r++) {
										if (lCapacityAirline.get(r).toString().equalsIgnoreCase(lCarrier)) {
											lAirlineCapcity += Float.parseFloat(lCapacityJSONArray.get(r).toString());
										}
									}

								}

								if ("depDate".equalsIgnoreCase(lKeyType)) {
									if (lBookingModel.getMonth() != lMonth && lBookingModel.getDay() != lDay
											|| lBookingModel.getMonth() == lMonth && lBookingModel.getDay() == lDay) {

										if (lBookingModel.getCompetitorsMap() != null
												&& lBookingModel.getCompetitorsMap().containsKey(lCarrier)
												&& lBookingModel.getCompetitorsMap().get(lCarrier) != null) {

											Map lCarrierMap = (Map) lBookingModel.getCompetitorsMap().get(lCarrier);

											lMarketSharePax += Float
													.parseFloat(lCarrierMap.get("marketSharePax").toString());

											lMarketSharePaxlastyr += Float
													.parseFloat(lCarrierMap.get("marketSharePax_1").toString());

											lAirlineCapcity += Float.parseFloat(lCarrierMap.get("capacity").toString());
										}

									}

								} else {

									if (lBookingModel.getMonth() != lMonth) {

										if (lBookingModel.getCompetitorsMap() != null
												&& lBookingModel.getCompetitorsMap().containsKey(lCarrier)
												&& lBookingModel.getCompetitorsMap().get(lCarrier) != null) {

											Map lCarrierMap = (Map) lBookingModel.getCompetitorsMap().get(lCarrier);

											lMarketSharePax += Float
													.parseFloat(lCarrierMap.get("marketSharePax").toString());

											lMarketSharePaxlastyr += Float
													.parseFloat(lCarrierMap.get("marketSharePax_1").toString());

											lAirlineCapcity += Float.parseFloat(lCarrierMap.get("capacity").toString());

										}

									}

								}

								/*
								 * competitors and host's FMS Calculation
								 */
								float lCarrierFMS = CalculationUtil.calculateFMS(lAirlineCapcity, lRating,
										lCarriersCapacity);

								/*
								 * competitors and host's bookings ytd
								 * 
								 */
								float lCarrierBookingsYTD = lMarketSharePax;

								/*
								 * competitors and host's bookings vlyr
								 * calculation
								 * 
								 */
								float lCarrierBookingsVLYR = 0;
								if (lMarketSharePaxlastyr != 0)
									lCarrierBookingsVLYR = CalculationUtil.calculateVLYR(lMarketSharePax,
											lMarketSharePaxlastyr);

								/*
								 * Competitors' and host's MarketShare ytd
								 * calculation
								 */
								float lCarrierMarketShareYTD = 0;
								if (lMarketSize != 0)
									lCarrierMarketShareYTD = CalculationUtil.doDivision(lMarketSharePax, lMarketSize)
											* 100;

								/*
								 * Competitors' and host's MarketShare
								 * calculation
								 * 
								 */
								float lCarrierMarketShare = 0.0f;
								if (lMarketSize != 0)
									lCarrierMarketShare = CalculationUtil.doDivision(lMarketSharePax, lMarketSize)
											* 100;

								/*
								 * Competitors' and host's MarketShare last yr
								 * calculation
								 */
								float lCarrierMarketSharelastyr = 0.0f;
								if (lMarketSizelastyr != 0)
									lCarrierMarketSharelastyr = CalculationUtil.doDivision(lMarketSharePaxlastyr,
											lMarketSizelastyr) * 100;

								/*
								 * Competitors' and host's MarketShare vlyr
								 * calculation
								 */
								float lCarrierMarketShareVLYR = 0;
								if (lCarrierMarketSharelastyr != 0)
									lCarrierMarketShareVLYR = CalculationUtil.calculateVLYR(lCarrierMarketShare,
											lCarrierMarketSharelastyr);

								/*
								 * Competitors' and host's MarketShare vtgt
								 * Calculation
								 */
								float lCarrierMarketShareVTGT = 0;
								if (lCarrierFMS != 0)
									lCarrierMarketShareVTGT = CalculationUtil.calculateVTGT(lCarrierMarketShare,
											lCarrierFMS);

								lCompetitors.put("name", lCarrier);
								lCompetitors.put("marketSharePax", lMarketSharePax);
								lCompetitors.put("marketSharePax_1", lMarketSharePaxlastyr);
								lCompetitors.put("targetMarketSharePax", lTargetMarketSharePax);
								lCompetitors.put("rating", lRating);
								lCompetitors.put("capacity", Math.round(lAirlineCapcity));
								lCompetitors.put("FMS", Math.round(lCarrierFMS));
								lCompetitors.put("BookingsYTD", Math.round(lCarrierBookingsYTD));
								lCompetitors.put("BookingsVLYR", Math.round(lCarrierBookingsVLYR));
								lCompetitors.put("MarketShareYTD", Math.round(lCarrierMarketShareYTD));
								lCompetitors.put("MarketShareVLYR", Math.round(lCarrierMarketShareVLYR));
								lCompetitors.put("MarketShareVTGT", Math.round(lCarrierMarketShareVTGT));

								lCompMap = (Map<String, Object>) (lBookingModel).getCompetitorsMap();

								lCompMap.put(lCarrier, lCompetitors);

							}

						}

						/* Host's Bookings YTD Calculation */

						float lHostBookingsYTD = lPax;

						/* Host's Bookings VLYR Calculation */

						float lHostBookingsVlYR = 0.0f;
						if (lPaxlastyr != 0)
							lHostBookingsVlYR = CalculationUtil.calculateVLYR(lPax, lPaxlastyr, hostCapacity,
									hostCapacity_1);

						/* Host's Bookings VTGT Calculation */
						float lHostBookingsVTGT = 0.0f;
						if (lTargetPax != 0)
							lHostBookingsVTGT = CalculationUtil.calculateVTGTRemodelled(lFlownPax, lForecastPax,
									lTargetPax);

						/* host's ticketed percentage calculation */

						float lTicketPercentage = 0.0f;
						if (lPax != 0)
							lTicketPercentage = CalculationUtil.doDivision(lTicket, lPax) * 100;

						/* Channel-wise Calculation */

						float lDirectChannelPax = 0;
						float lIndirectChannelPax = 0;

						float lDirectChannelNetBookings = 0;
						float lIndirectChannelNetBookings = 0;

						if (lChannelJSONArray != null && lChannelJSONArray.length() != 0 && lTicketJSONArray != null
								&& lTicketJSONArray.length() != 0
								&& lChannelJSONArray.length() == lTicketJSONArray.length())
							for (int c = 0; c < lChannelJSONArray.length(); c++) {

								if (!"null".equalsIgnoreCase(lChannelJSONArray.get(c).toString())
										&& !lChannelJSONArray.get(c).toString().isEmpty()) {

									if ("GDS".equalsIgnoreCase(lChannelJSONArray.get(c).toString())) {

										lIndirectChannelPax = lIndirectChannelPax
												+ Float.parseFloat(lPaxJSONArray.get(c).toString());
									}

									if (!"GDS".equalsIgnoreCase(lChannelJSONArray.get(c).toString())) {

										lDirectChannelPax = lDirectChannelPax
												+ Float.parseFloat(lPaxJSONArray.get(c).toString());
									}

								}

							}

						lIndirectChannelNetBookings += lIndirectChannelPax;
						lIndirectChannelNetBookings += ((Bookings) lResultMap.get(lKey))
								.getNetBooking_IndirectChannel();
						lDirectChannelNetBookings += lDirectChannelPax;
						lDirectChannelNetBookings += ((Bookings) lResultMap.get(lKey)).getNetBooking_DirectChannel();

						lBookingModel = (Bookings) lResultMap.get(lKey);

						if (lJsonObj.has("dep_date"))
							lBookingModel.setDep_date(lJsonObj.get("dep_date").toString());

						if (lJsonObj.has("book_date"))
							lBookingModel.setBook_date(lJsonObj.get("book_date").toString());

						if (lJsonObj.has("region"))
							lBookingModel.setRegion(lJsonObj.get("region").toString());

						if (lJsonObj.has("country"))
							lBookingModel.setCountry(lJsonObj.get("country").toString());

						if (lJsonObj.has("pos"))
							lBookingModel.setPos(((Bookings) lResultMap.get(lKey)).getPos());

						if (lJsonObj.has("origin"))
							lBookingModel.setOrigin(((Bookings) lResultMap.get(lKey)).getOrigin());

						if (lJsonObj.has("destination"))
							lBookingModel.setDestination(((Bookings) lResultMap.get(lKey)).getDestination());

						if (lJsonObj.has("compartment"))
							lBookingModel.setCompartment(((Bookings) lResultMap.get(lKey)).getCompartment());

						lBookingModel.setPax(Math.round(lPax));
						lBookingModel.setPax_1(Math.round(lPaxlastyr));
						lBookingModel.setFlownPax(Math.round(lFlownPax));
						lBookingModel.setNetBooking_IndirectChannel(Math.round(lIndirectChannelNetBookings));
						lBookingModel.setNetBooking_DirectChannel(Math.round(lDirectChannelNetBookings));
						lBookingModel.setMarketSize(lMarketSize);
						lBookingModel.setMarketSize_1(lMarketSizelastyr);
						lBookingModel.setTargetPax(lTargetPax);
						lBookingModel.setForecastPax(lForecastPax);
						lBookingModel.setHostBookingsYTD(Math.round(lHostBookingsYTD));
						lBookingModel.setHostBookingsVLYR(Math.round(lHostBookingsVlYR));
						lBookingModel.setHostBookingsVTGT(Math.round(lHostBookingsVTGT));
						lBookingModel.setTicket(Math.round(lTicket));
						lBookingModel.setHostTicketedPercentage(Math.round(lTicketPercentage));
						lBookingModel.setMonth(lMonth);
						lBookingModel.setDay(lDay);
						lBookingModel.setCarrierCapacity(Math.round(lCarriersCapacity));
						lBookingModel.setHostCapacity(hostCapacity);
						lBookingModel.setHostCapacity_1(hostCapacity_1);
						lBookingModel.setKey(lKey);

						lResultMap.put(lKey, lBookingModel);
					}

					else if (!lResultMap.containsKey(lKey)) {

						lBookingModel = new Bookings();
						lCompMap = new HashMap<String, Object>();

						float lPax = 0;
						float lPaxlastyr = 0;
						float lMarketSharePax = 0;
						float lMarketSharePaxlastyr = 0;
						float lTotalTargetPax = 0;
						float lCurrentObjPax = 0;
						float lCurrentObjPaxlastyr = 0;
						float lCurrentObjTicket = 0;

						JSONArray lCarrierJSONArray = null;
						if (lJsonObj.has("carrier"))
							lCarrierJSONArray = new JSONArray(lJsonObj.get("carrier").toString());

						JSONArray lMarketSharePaxJSONArray = null;
						if (lJsonObj.has("market_share_pax"))
							lMarketSharePaxJSONArray = new JSONArray(lJsonObj.get("market_share_pax").toString());

						JSONArray lMarketSharePax_1JSONArray = null;
						if (lJsonObj.has("market_share_pax_1"))
							lMarketSharePax_1JSONArray = new JSONArray(lJsonObj.get("market_share_pax_1").toString());

						JSONArray lHostTargetPaxJSONArray = null;
						if (lJsonObj.has("target_pax"))
							lHostTargetPaxJSONArray = new JSONArray(lJsonObj.get("target_pax").toString());

						JSONArray lCapacityJSONArray = null;
						if (lJsonObj.has("capacity"))
							lCapacityJSONArray = new JSONArray(lJsonObj.get("capacity").toString());

						JSONArray lCapacityAirline = null;
						if (lJsonObj.has("capacity_airline"))
							lCapacityAirline = new JSONArray(lJsonObj.get("capacity_airline").toString());

						JSONArray lCapacity_1JSONArray = null;
						if (lJsonObj.has("capacity_1"))
							lCapacity_1JSONArray = new JSONArray(lJsonObj.get("capacity_1").toString());

						JSONArray lCarrierRatingArray = null;
						if (lJsonObj.has("rating_carrier"))
							lCarrierRatingArray = new JSONArray(lJsonObj.get("rating_carrier").toString());

						JSONArray lRatingArray = null;
						if (lJsonObj.has("rating"))
							lRatingArray = new JSONArray(lJsonObj.get("rating").toString());

						JSONArray lPaxJSONArray = null;
						if (lJsonObj.has("pax"))
							lPaxJSONArray = new JSONArray(lJsonObj.get("pax").toString());

						JSONArray lPax_1JSONArray = null;
						if (lJsonObj.has("pax_1"))
							lPax_1JSONArray = new JSONArray(lJsonObj.get("pax_1").toString());

						JSONArray lForecastPaxJSONArray = null;
						if (lJsonObj.has("forecast_pax"))
							lForecastPaxJSONArray = new JSONArray(lJsonObj.get("forecast_pax").toString());

						JSONArray lFlownPaxJSONArray = null;
						if (lJsonObj.has("flown_pax"))
							lFlownPaxJSONArray = new JSONArray(lJsonObj.get("flown_pax").toString());

						JSONArray lTicketJSONArray = null;
						if (lJsonObj.has("ticket"))
							lTicketJSONArray = new JSONArray(lJsonObj.get("ticket").toString());

						JSONArray lChannelJSONArray = null;
						if (lJsonObj.has("channel") && lJsonObj.get("channel").toString().length() != 0)
							lChannelJSONArray = new JSONArray(lJsonObj.get("channel").toString());

						if (lPax_1JSONArray != null)
							for (int p = 0; p < lPaxJSONArray.length(); p++)
								lCurrentObjPax += Float.parseFloat(lPaxJSONArray.get(p).toString());

						if (lPax_1JSONArray != null)
							for (int p = 0; p < lPax_1JSONArray.length(); p++)
								lCurrentObjPaxlastyr += Float.parseFloat(lPax_1JSONArray.get(p).toString());

						if (lTicketJSONArray != null)
							for (int t = 0; t < lTicketJSONArray.length(); t++)
								lCurrentObjTicket += Float.parseFloat(lTicketJSONArray.get(t).toString());

						float lMarketSize = 0;
						if (lMarketSharePaxJSONArray != null)
							for (int m = 0; m < lMarketSharePaxJSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lMarketSharePaxJSONArray.get(m).toString()))
									lMarketSize += Float.parseFloat(lMarketSharePaxJSONArray.get(m).toString());
							}

						float lMarketSizelastyr = 0;
						if (lMarketSharePax_1JSONArray != null)
							for (int m = 0; m < lMarketSharePax_1JSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lMarketSharePax_1JSONArray.get(m).toString()))
									lMarketSizelastyr += Float.parseFloat(lMarketSharePax_1JSONArray.get(m).toString());
							}

						if (lHostTargetPaxJSONArray != null)
							for (int m = 0; m < lHostTargetPaxJSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lHostTargetPaxJSONArray.get(m).toString()))
									lTotalTargetPax += Long.parseLong(lHostTargetPaxJSONArray.get(m).toString());
							}

						float lTotalForecastPax = 0;
						if (lForecastPaxJSONArray != null && !Utility.datePassed(lJsonObj.get("dep_date").toString())) {
							for (int m = 0; m < lForecastPaxJSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lForecastPaxJSONArray.get(m).toString()))
									lTotalForecastPax += Float.parseFloat(lForecastPaxJSONArray.get(m).toString());
							}
						}

						float lCurrentObjFlownPax = 0;
						if (lFlownPaxJSONArray != null) {
							for (int m = 0; m < lFlownPaxJSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lFlownPaxJSONArray.get(m).toString()))
									lCurrentObjFlownPax += Float.parseFloat(lFlownPaxJSONArray.get(m).toString());

							}
						}

						float lCarriersCapacity = 0;
						if (lCapacityAirline != null && lCarrierRatingArray != null) {
							for (int r = 0; r < lCapacityAirline.length(); r++) {
								for (int k = 0; k < lCarrierRatingArray.length(); k++) {
									if (lCarrierRatingArray.length() >= lCapacityAirline.length()
											&& lCapacityAirline.get(r).toString()
													.equalsIgnoreCase(lCarrierRatingArray.get(k).toString())
											&& lCarrierRatingArray.length() == lRatingArray.length()) {

										if (!lCapacityJSONArray.get(r).toString().equalsIgnoreCase("null")
												&& !lCapacityJSONArray.get(r).toString().equalsIgnoreCase("[]")
												&& !lRatingArray.get(k).toString().equalsIgnoreCase("null")
												&& !lRatingArray.get(k).toString().equalsIgnoreCase("[]"))
											lCarriersCapacity += Float.parseFloat(lCapacityJSONArray.get(r).toString())
													* (Float.parseFloat(lRatingArray.get(k).toString()));

									}
								}
							}
						}

						/* getting host capacity from the capacity array */
						float hostCapacity = 0;
						if (lCapacityAirline != null && lCapacityJSONArray != null
								&& lCapacityAirline.length() == lCapacityJSONArray.length()) {
							for (int r = 0; r < lCapacityAirline.length(); r++) {
								if (lCapacityAirline.get(r).toString().equalsIgnoreCase(Constants.hostName)) {
									hostCapacity += Float.parseFloat(lCapacityJSONArray.get(r).toString());
								}
							}

						}

						/*
						 * getting last year capacity of host from the capacity
						 * array
						 */
						float hostCapacity_1 = 0;
						if (lCapacityAirline != null && lCapacityJSONArray != null
								&& lCapacityAirline.length() == lCapacity_1JSONArray.length()) {
							for (int r = 0; r < lCapacityAirline.length(); r++) {
								if (lCapacityAirline.get(r).toString().equalsIgnoreCase(Constants.hostName)) {
									hostCapacity_1 += Float.parseFloat(lCapacity_1JSONArray.get(r).toString());
								}
							}

						}

						int lNoOfDaysInMonth = Utility.numberOfDaysInMonth(
								Utility.findMonth(lJsonObj.get("dep_date").toString()) - 1,
								Utility.findYear(lJsonObj.get("dep_date").toString()));

						float lCurrentObjProratedTargetPax = lTotalTargetPax / (float) lNoOfDaysInMonth;
						float lCurrentObjProratedForecastPax = lTotalForecastPax / (float) lNoOfDaysInMonth;

						lPax = lCurrentObjPax;
						lPaxlastyr = lCurrentObjPaxlastyr;

						int lMonth = 0;
						int lDay = 0;

						if (lJsonObj.has("dep_date") && !"null".equalsIgnoreCase(lJsonObj.get("dep_date").toString())) {

							lMonth = Utility.findMonth(lJsonObj.get("dep_date").toString());
							lDay = Utility.findDay(lJsonObj.get("dep_date").toString());

						}

						if (lCarrierJSONArray != null) {
							for (int c = 0; c < lCarrierJSONArray.length(); c++) {

								String lCarrier = lCarrierJSONArray.get(c).toString();

								Map<String, Object> lCompetitors = new HashMap<String, Object>();

								if (lMarketSharePax_1JSONArray.length() != 0)
									lMarketSharePaxlastyr = Float
											.parseFloat(lMarketSharePax_1JSONArray.get(c).toString());

								if (lMarketSharePaxJSONArray.length() != 0)
									lMarketSharePax = Float.parseFloat(lMarketSharePaxJSONArray.get(c).toString());

								float lRating = Constants.DEFAULT_COMPETITOR_RATING;
								if (lCarrierRatingArray != null
										&& lCarrierRatingArray.length() >= lRatingArray.length()) {
									for (int r = 0; r < lCarrierRatingArray.length(); r++) {
										if (lCarrierRatingArray.get(r).toString().equalsIgnoreCase(lCarrier)) {

											if (!lRatingArray.get(r).toString().equalsIgnoreCase("null")
													&& !lRatingArray.get(r).toString().equalsIgnoreCase("[]"))

												lRating = Float.parseFloat(lRatingArray.get(r).toString());

										}
									}
								}
								float lAirlineCapcity = 0;
								if (lCapacityAirline != null && lCapacityJSONArray != null
										&& lCapacityAirline.length() == lCapacityJSONArray.length()) {
									for (int r = 0; r < lCapacityAirline.length(); r++) {
										if (lCapacityAirline.get(r).toString().equalsIgnoreCase(lCarrier)) {
											lAirlineCapcity += Float.parseFloat(lCapacityJSONArray.get(r).toString());
										}
									}

								}

								float lCarrierFMS = CalculationUtil.calculateFMS(lAirlineCapcity, lRating,
										lCarriersCapacity);

								float lCarrierBookingsYTD = lMarketSharePax;

								float lCarrierBookingsVLYR = 0.0f;
								if (lMarketSharePaxlastyr != 0)
									lCarrierBookingsVLYR = CalculationUtil.calculateVLYR(lMarketSharePax,
											lMarketSharePaxlastyr);

								float lCarrierMarketShareYTD = 0;
								if (lMarketSize != 0)
									lCarrierMarketShareYTD = CalculationUtil.doDivision(lMarketSharePax, lMarketSize)
											* 100;

								float lCarrierMarketShare = 0.0f;
								if (lMarketSize != 0)
									lCarrierMarketShare = CalculationUtil.doDivision(lMarketSharePax, lMarketSize)
											* 100;

								float lCarrierMarketSharelastyr = 0.0f;
								if (lMarketSizelastyr != 0)
									lCarrierMarketSharelastyr = CalculationUtil.doDivision(lMarketSharePaxlastyr,
											lMarketSizelastyr) * 100;

								float lCarrierMarketShareVLYR = 0;
								if (lCarrierMarketSharelastyr != 0)
									lCarrierMarketShareVLYR = CalculationUtil.calculateVLYR(lCarrierMarketShare,
											lCarrierMarketSharelastyr);

								float lCarrierMarketShareVTGT = 0;
								if (lCarrierFMS != 0)
									lCarrierMarketShareVTGT = CalculationUtil.calculateVTGT(lCarrierMarketShare,
											lCarrierFMS);

								lCompetitors.put("name", lCarrier);
								lCompetitors.put("marketSharePax", lMarketSharePax);
								lCompetitors.put("marketSharePax_1", lMarketSharePaxlastyr);
								lCompetitors.put("rating", lRating);
								lCompetitors.put("capacity", Math.round(lAirlineCapcity));
								lCompetitors.put("FMS", Math.round(lCarrierFMS));
								lCompetitors.put("BookingsYTD", Math.round(lCarrierBookingsYTD));
								lCompetitors.put("BookingsVLYR", Math.round(lCarrierBookingsVLYR));
								lCompetitors.put("MarketShareYTD", Math.round(lCarrierMarketShareYTD));
								lCompetitors.put("MarketShareVLYR", Math.round(lCarrierMarketShareVLYR));
								lCompetitors.put("MarketShareVTGT", Math.round(lCarrierMarketShareVTGT));

								lCompMap.put(lCarrier, lCompetitors);

								lBookingModel.setCompetitorsMap(lCompMap);

							}

						}

						float lHostBookingsYTD = lPax;

						float lHostBookingsVlYR = 0.0f;
						if (lPaxlastyr != 0)
							lHostBookingsVlYR = CalculationUtil.calculateVLYR(lPax, lPaxlastyr, hostCapacity,
									hostCapacity_1);

						float lHostBookingsVTGT = 0.0f;
						if (lCurrentObjProratedTargetPax != 0)
							lHostBookingsVTGT = CalculationUtil.calculateVTGTRemodelled(lCurrentObjFlownPax,
									lCurrentObjProratedForecastPax, lCurrentObjProratedTargetPax);

						float lTicketPercentage = 0.0f;
						if (lPax != 0)
							lTicketPercentage = CalculationUtil.doDivision(lCurrentObjTicket, lPax) * 100;

						/* Calculating Channel's lDataJSONArray */

						long lDirectChannelPax = 0;
						long lIndirectChannelPax = 0;

						long lDirectChannelNetBookings = 0;
						long lIndirectChannelNetBookings = 0;

						if (lChannelJSONArray != null && lChannelJSONArray.length() != 0 && lTicketJSONArray != null
								&& lTicketJSONArray.length() != 0
								&& lChannelJSONArray.length() == lTicketJSONArray.length())
							for (int c = 0; c < lChannelJSONArray.length(); c++) {

								if (!"null".equalsIgnoreCase(lChannelJSONArray.get(c).toString())
										&& !lChannelJSONArray.get(c).toString().isEmpty()) {

									if ("GDS".equalsIgnoreCase(lChannelJSONArray.get(c).toString())) {

										lIndirectChannelPax = lIndirectChannelPax
												+ Long.parseLong(lPaxJSONArray.get(c).toString());
									}

									if (!"GDS".equalsIgnoreCase(lChannelJSONArray.get(c).toString())) {

										lDirectChannelPax = lDirectChannelPax
												+ Long.parseLong(lPaxJSONArray.get(c).toString());
									}

								}

							}

						lIndirectChannelNetBookings += lIndirectChannelPax /*- indirectChannelCancel*/;
						lDirectChannelNetBookings += lDirectChannelPax /*- directChannelCancel*/;

						if (lJsonObj.has("dep_date"))
							lBookingModel.setDep_date(lJsonObj.get("dep_date").toString());

						if (lJsonObj.has("book_date"))
							lBookingModel.setBook_date(lJsonObj.get("book_date").toString());

						if (lJsonObj.has("region"))
							lBookingModel.setRegion(lJsonObj.get("region").toString());

						if (lJsonObj.has("country"))
							lBookingModel.setCountry(lJsonObj.get("country").toString());

						if (lJsonObj.has("pos"))
							lBookingModel.setPos(lJsonObj.get("pos").toString());

						if (lJsonObj.has("origin"))
							lBookingModel.setOrigin(lJsonObj.get("origin").toString());

						if (lJsonObj.has("destination"))
							lBookingModel.setDestination(lJsonObj.get("destination").toString());

						if (lJsonObj.has("compartment"))
							lBookingModel.setCompartment(lJsonObj.get("compartment").toString());

						// lBookingModel.setCompetitorsMap(lCompMap);

						lBookingModel.setPax(Math.round(lPax));
						lBookingModel.setPax_1(Math.round(lPaxlastyr));
						lBookingModel.setFlownPax(Math.round(lCurrentObjFlownPax));
						lBookingModel.setNetBooking_IndirectChannel(Math.round(lIndirectChannelNetBookings));
						lBookingModel.setNetBooking_DirectChannel(Math.round(lDirectChannelNetBookings));
						lBookingModel.setMarketSize(lMarketSize);
						lBookingModel.setMarketSize_1(lMarketSizelastyr);
						lBookingModel.setTargetPax(lCurrentObjProratedTargetPax);
						lBookingModel.setForecastPax(lCurrentObjProratedForecastPax);
						lBookingModel.setHostBookingsYTD(Math.round(lHostBookingsYTD));
						lBookingModel.setHostBookingsVLYR(Math.round(lHostBookingsVlYR));
						lBookingModel.setHostBookingsVTGT(Math.round(lHostBookingsVTGT));
						lBookingModel.setTicket(Math.round(lCurrentObjTicket));
						lBookingModel.setHostTicketedPercentage(Math.round(lTicketPercentage));
						lBookingModel.setMonth(lMonth);
						lBookingModel.setDay(lDay);
						lBookingModel.setCarrierCapacity(Math.round(lCarriersCapacity));
						lBookingModel.setHostCapacity(hostCapacity);
						lBookingModel.setHostCapacity_1(hostCapacity_1);
						lBookingModel.setKey(lKey);

						lResultMap.put(lKey, lBookingModel);
					}
				}

				Set<String> lOdKeySet = new HashSet<String>();
				lOdKeySet = lResultMap.keySet();
				for (String lKeys : lOdKeySet) {
					lKeyList.add(lResultMap.get(lKeys));
				}

				Map<String, Object> lAggMap = new HashMap<String, Object>();
				if (!"depDate".equalsIgnoreCase(lKeyType)) {

					float lAggHostPax = 0;
					float lAggHostFlownPax = 0;
					float lAggHostForecastPax = 0;
					float lAggHostPaxlastyr = 0;
					float lAggHostTargetPax = 0;
					float lAggHostMarketSize = 0;
					float lAggHostTicketed = 0;
					float lAggHostCapacity = 0;
					float lAggHostCapacity_1 = 0;

					float lAggHostBookingsYTD = 0.0f;
					float lAggHostBookingsVLYR = 0.0f;
					float lAggHostBookingsVTGT = 0.0f;
					float lAggHostTicketedPercentage = 0.0f;
					float lAggHostTicketedBookings = 0.0f;
					float lAggCapacity = 0;
					float lAggCapacitylastyr = 0;
					float lRunRate = 0.0f;
					float lStrength = 0.0f;
					float lNetBookings = 0;

					float lDays = 0;
					Date lDateFrom = Utility.convertStringToDateFromat(pRequestModel.getFromDate());
					Date lDdateTo = Utility.convertStringToDateFromat(pRequestModel.getToDate());

					lDays = Utility.getDifferenceDays(lDateFrom, lDdateTo) + 1;

					for (int i = 0; i < lKeyList.size(); i++) {

						Bookings bookingIterator = (Bookings) lKeyList.get(i);

						long lFlownPax = bookingIterator.getFlownPax();
						float lTargetPax = bookingIterator.getTargetPax() * lDays;
						float lForecastPax = bookingIterator.getForecastPax() * lDays;

						float hostPaxVTGT = CalculationUtil.calculateVTGTRemodelled(lFlownPax, lForecastPax,
								lTargetPax);
						((Bookings) lKeyList.get(i)).setHostBookingsVTGT(Math.round(hostPaxVTGT));

						/* calculating aggregates */
						lAggHostPax += bookingIterator.getPax();
						lAggHostFlownPax += (float) bookingIterator.getFlownPax();
						lAggHostPaxlastyr += bookingIterator.getPax_1();
						lAggHostForecastPax += bookingIterator.getForecastPax();
						lAggHostTargetPax += bookingIterator.getTargetPax();
						lAggHostCapacity += bookingIterator.getHostCapacity();
						lAggHostCapacity_1 += bookingIterator.getHostCapacity_1();

						lAggHostMarketSize += bookingIterator.getMarketSize();
						lAggHostTicketed += bookingIterator.getTicket();
						lAggCapacity += bookingIterator.getCarrierCapacity();
						lAggCapacitylastyr += bookingIterator.getCarrierCapacity_1();

					}

					lAggHostBookingsYTD = lAggHostPax;

					if (lAggHostPaxlastyr != 0)
						lAggHostBookingsVLYR = CalculationUtil.calculateVLYR(lAggHostPax, lAggHostPaxlastyr,
								lAggHostCapacity, lAggHostCapacity_1);

					if (lAggHostTargetPax != 0)
						lAggHostBookingsVTGT = CalculationUtil.calculateVTGTRemodelled(lAggHostFlownPax,
								lAggHostForecastPax * lDays, lAggHostTargetPax * lDays);

					if (lAggHostPax != 0)
						lAggHostTicketedPercentage = CalculationUtil.doDivision(lAggHostTicketed, lAggHostPax) * 100;

					if (lDays != 0)
						lRunRate = CalculationUtil.doDivision(lAggHostPax, lDays);

					if (lAggCapacity != 0)
						lStrength = ((lAggHostPax - lAggHostPaxlastyr) * lAggCapacitylastyr) / lAggCapacity;

					if (lAggHostPax != 0)
						lAggHostTicketedBookings = CalculationUtil.doDivision(lAggHostTicketed, lAggHostPax) * 100;

					lNetBookings = lAggHostTicketed;

					lAggMap.put("aggregate_host_bookings_YTD", Math.round(lAggHostBookingsYTD));
					lAggMap.put("aggregate_host_bookings_VLYR", Math.round(lAggHostBookingsVLYR));
					lAggMap.put("aggregate_host_bookings_VTGT", Math.round(lAggHostBookingsVTGT));
					lAggMap.put("aggregate_host_marketSize", Math.round(lAggHostMarketSize));
					lAggMap.put("agg_host_ticketed_percentage", Math.round(lAggHostTicketedPercentage));

					lAggMap.put("tiles_runRate_currentValue", Math.round(lRunRate));
					lAggMap.put("tiles_runRate_targetValue", "NA");
					if (lStrength != 0)
						lAggMap.put("tiles_strength", Math.round(lStrength));
					else
						lAggMap.put("tiles_strength", "NA");

					lAggMap.put("tiles_netBookings", Math.round(lNetBookings));
					lAggMap.put("tiles_Ticketed_Bookings", Math.round(lAggHostTicketedBookings));

				}

				String[] competitorsArray = lUserProfile.getCompetitors();
				for (int i = 0; i < lKeyList.size(); i++) {

					Bookings bookingIterator = (Bookings) lKeyList.get(i);

					Map<String, Object> carriersMap = null;
					if (bookingIterator.getCompetitorsMap() != null)
						carriersMap = bookingIterator.getCompetitorsMap();

					Map<String, Object> compResultMap = new HashMap<String, Object>();

					for (int c = 0; c < competitorsArray.length; c++) {

						String lComp = competitorsArray[c].replace("\"", "");
						if (carriersMap != null && carriersMap.containsKey(lComp)) {
							compResultMap.put(lComp, carriersMap.get(lComp));
							carriersMap.remove(lComp);
						}
					}

					if (carriersMap.containsKey(Constants.hostName)) {
						compResultMap.put(Constants.hostName, carriersMap.get(Constants.hostName));
						carriersMap.remove(Constants.hostName);
					}

					Set<String> carriersSet = carriersMap.keySet();
					Map<String, Float> compMarketShareMap = new HashMap<String, Float>();

					for (String carrier : carriersSet) {

						Map<String, Object> carrierDetailsMap = null;
						if (carriersMap != null && carriersMap.containsKey(carrier))
							carrierDetailsMap = (Map) carriersMap.get(carrier);
						compMarketShareMap.put(carrier,
								Float.parseFloat(carrierDetailsMap.get("marketSharePax").toString()));
					}

					Map<String, Float> sortedCompMap = new TreeMap<String, Float>(compMarketShareMap);
					List<String> sortedCompList = new ArrayList<String>();

					for (String compKey : sortedCompMap.keySet()) {
						sortedCompList.add(compKey);
					}

					int noOfCarriers = 0;
					if (Constants.NO_OF_COMPETITORS > competitorsArray.length)
						noOfCarriers = Constants.NO_OF_COMPETITORS - competitorsArray.length;

					int carriersCount = 1;
					int sortedCompListLimit = sortedCompList.size() - 1;
					if (noOfCarriers <= sortedCompList.size()) {
						while (carriersCount <= noOfCarriers && sortedCompListLimit >= 0) {

							compResultMap.put(sortedCompList.get(sortedCompListLimit--),
									carriersMap.get(sortedCompList.get(sortedCompListLimit--)));

							carriersCount++;

						}
					} else {
						for (int c = 0; c < sortedCompList.size(); c++)
							compResultMap.put(sortedCompList.get(c), carriersMap.get(sortedCompList.get(c)));

					}

					((Bookings) lKeyList.get(i)).setCompetitorsMap(compResultMap);

				}

				lUserDataMap.put("Data", lKeyList);
				lUserDataMap.put("aggregateMap", lAggMap);

			} else

			{
				lUserDataMap.put("Data", null);
			}

		} catch (Exception e) {
			logger.error("getCalculatedBookings-Exception", e);
		}

		return lUserDataMap;

	}

	@Override
	public Map<String, Object> getEvents(RequestModel pRequestModel) {
		Map<String, Object> lEventsMap = new HashMap<String, Object>();
		List<Object> lEventsList = new ArrayList<Object>();

		BasicDBObject lResultObj = (BasicDBObject) mMainDashboardDao.getEvents(pRequestModel);
		JSONArray lDataJSONArray = null;
		if (lResultObj != null && lResultObj.containsKey("_batch")) {
			lDataJSONArray = new JSONArray(lResultObj.get("_batch").toString());

		}

		if (lDataJSONArray != null) {

			Events lEventModel = null;
			for (int i = 0; i < lDataJSONArray.length(); i++) {

				JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);
				lEventModel = new Events();

				if (lJsonObj.has("name") && lJsonObj.get("name") != null
						&& !"null".equalsIgnoreCase(lJsonObj.get("name").toString()))
					lEventModel.setName(lJsonObj.get("name").toString());

				if (lJsonObj.has("origin") && lJsonObj.get("origin") != null
						&& !"null".equalsIgnoreCase(lJsonObj.get("origin").toString()))
					lEventModel.setOrigin(lJsonObj.get("origin").toString());

				if (lJsonObj.has("destination") && lJsonObj.get("destination") != null
						&& !"null".equalsIgnoreCase(lJsonObj.get("destination").toString()))
					lEventModel.setDestination(lJsonObj.get("destination").toString());

				if (lJsonObj.has("lYear") && lJsonObj.get("lYear") != null
						&& !"null".equalsIgnoreCase(lJsonObj.get("lYear").toString()))
					lEventModel.setYear(lJsonObj.get("lYear").toString());

				if (lJsonObj.has("start_date") && lJsonObj.get("start_date") != null
						&& !"null".equalsIgnoreCase(lJsonObj.get("start_date").toString()))
					lEventModel.setStart_date(lJsonObj.get("start_date").toString());

				if (lJsonObj.has("end_date") && lJsonObj.get("end_date") != null
						&& !"null".equalsIgnoreCase(lJsonObj.get("end_date").toString()))
					lEventModel.setEnd_date(lJsonObj.get("end_date").toString());

				if (lJsonObj.has("season_flag") && lJsonObj.get("season_flag") != null
						&& !"null".equalsIgnoreCase(lJsonObj.get("season_flag").toString()))
					lEventModel.setSeason_flag(lJsonObj.get("season_flag").toString());

				if (lEventModel.getName() != null && lEventModel.getOrigin() != null
						&& lEventModel.getDestination() != null)
					lEventModel.setValue(
							lEventModel.getName() + "-" + lEventModel.getOrigin() + "-" + lEventModel.getDestination());

				lEventsList.add(lEventModel);

			}

			lEventsMap.put("events", lEventsList);
		}

		return lEventsMap;
	}

	@Override
	public Map<String, Object> getSales(RequestModel pRequestModel) {

		UserProfile lUserProfile = mMainDashboardDao.getUserProfile(pRequestModel);

		if (lUserProfile != null && "network".equalsIgnoreCase(lUserProfile.getLevel())) {
			if (pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() == null
					&& pRequestModel.getCountryArray() != null) {

				pRequestModel.setRegionArray(
						mMainDashboardDao.getRegion(pRequestModel, "COUNTRY_CD", pRequestModel.getCountryArray()));

			} else if (pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() != null
					&& pRequestModel.getCountryArray() != null) {

				pRequestModel.setRegionArray(
						mMainDashboardDao.getRegion(pRequestModel, "COUNTRY_CD", pRequestModel.getCountryArray()));

			} else if (pRequestModel.getRegionArray() == null && pRequestModel.getPosArray() != null
					&& pRequestModel.getCountryArray() == null) {

				pRequestModel.setRegionArray(
						mMainDashboardDao.getRegion(pRequestModel, "POS_CD", pRequestModel.getPosArray()));

				pRequestModel.setCountryArray(
						mMainDashboardDao.getCountry(pRequestModel, "POS_CD", pRequestModel.getPosArray()));

			} else if (pRequestModel.getRegionArray() != null && pRequestModel.getPosArray() != null
					&& pRequestModel.getCountryArray() == null) {

				pRequestModel.setCountryArray(
						mMainDashboardDao.getCountry(pRequestModel, "POS_CD", pRequestModel.getPosArray()));

			}

		}

		ArrayList<DBObject> lResultObjList = mMainDashboardDao.getSales(pRequestModel);
		Map<String, Object> lResponseSalesMap = new HashMap<String, Object>();
		JSONArray lDataJSONArray = null;
		if (lResultObjList != null) {
			lDataJSONArray = new JSONArray(lResultObjList);

		}

		try {

			if (lDataJSONArray != null) {

				Map<String, Object> lResponseGridMap = new HashMap<String, Object>();
				Map<String, Object> lResponseDepDateMap = new HashMap<String, Object>();

				lResponseGridMap = getCalculatedSales(lDataJSONArray, FilterUtil.getFilter(pRequestModel),
						pRequestModel, lUserProfile);

				lResponseDepDateMap = getCalculatedSales(lDataJSONArray, "depDate", pRequestModel, lUserProfile);

				lResponseSalesMap.put("Graph", lResponseDepDateMap);
				lResponseSalesMap.put("Grid", lResponseGridMap);

			} else {
				lResponseSalesMap.put("Graph", null);
				lResponseSalesMap.put("Grid", null);
			}
		} catch (

		Exception e) {
			logger.error("getSales-Exception", e);
		}

		return lResponseSalesMap;

	}

	public static Map<String, Object> getCalculatedSales(JSONArray lDataJSONArray, String lKeyType,
			RequestModel pRequestModel, UserProfile lUserProfile) {

		Map<String, Object> lResultMap = new HashMap<String, Object>();
		Sales lSalesModel = null;

		Map<String, Object> lCompMap = null;
		Map<String, Object> lUserDataMap = new HashMap<String, Object>();
		List<Object> lKeyList = new ArrayList<Object>();

		try {

			if (lDataJSONArray != null) {

				for (int i = 0; i < lDataJSONArray.length(); i++) {

					JSONObject lJsonObj = lDataJSONArray.getJSONObject(i);

					String lKey = null;
					if ("depDate".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("dep_date").toString();

					else if ("POS+OD+compartment".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("pos").toString() + lJsonObj.get("origin").toString()
								+ lJsonObj.get("destination").toString() + lJsonObj.get("compartment");

					else if ("country+POS+OD+compartment".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("country").toString() + lJsonObj.get("pos").toString()
								+ lJsonObj.get("origin").toString() + lJsonObj.get("destination").toString()
								+ lJsonObj.get("compartment");

					else if ("region".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("compartment").toString();

					else if ("regionFilter".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("compartment").toString();

					else if ("county".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("country").toString() + lJsonObj.get("pos").toString()
								+ lJsonObj.get("compartment").toString();

					else if ("pos".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("pos").toString() + lJsonObj.get("compartment").toString();

					else if ("od".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("origin").toString() + lJsonObj.get("destination").toString()
								+ lJsonObj.get("compartment").toString();

					else if ("od+compartment".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("origin").toString() + lJsonObj.get("destination").toString()
								+ lJsonObj.get("compartment").toString();

					else if ("region+country".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("pos").toString() + lJsonObj.get("compartment").toString();

					else if ("region+country+OD".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("origin").toString() + lJsonObj.get("destination").toString()
								+ lJsonObj.get("compartment").toString();

					else if ("region+country+POS".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("pos").toString() + lJsonObj.get("compartment").toString();

					else if ("region+country+POS+OD".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("pos").toString() + lJsonObj.get("origin").toString()
								+ lJsonObj.get("destination").toString() + lJsonObj.get("compartment").toString();

					else if ("region+OD".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("origin").toString()
								+ lJsonObj.get("destination").toString() + lJsonObj.get("compartment").toString();

					else if ("region+country+POS+OD+compartment".equalsIgnoreCase(lKeyType))
						lKey = lJsonObj.get("region").toString() + lJsonObj.get("country").toString()
								+ lJsonObj.get("pos").toString() + lJsonObj.get("origin").toString()
								+ lJsonObj.get("destination").toString() + lJsonObj.get("compartment").toString();

					if (lResultMap.containsKey(lKey)
							&& ((Sales) lResultMap.get(lKey)).getKey().equalsIgnoreCase(lKey)) {

						lSalesModel = (Sales) lResultMap.get(lKey);

						JSONArray lCarrierJSONArray = null;
						if (lJsonObj.has("carrier"))
							lCarrierJSONArray = new JSONArray(lJsonObj.get("carrier").toString());

						JSONArray lMarketSharePaxJSONArray = null;
						if (lJsonObj.has("market_share_pax"))
							lMarketSharePaxJSONArray = new JSONArray(lJsonObj.get("market_share_pax").toString());

						JSONArray lMarketSharePax_1JSONArray = null;
						if (lJsonObj.has("market_share_pax_1"))
							lMarketSharePax_1JSONArray = new JSONArray(lJsonObj.get("market_share_pax_1").toString());

						JSONArray lHostTargetPaxJSONArray = null;
						if (lJsonObj.has("target_pax"))
							lHostTargetPaxJSONArray = new JSONArray(lJsonObj.get("target_pax").toString());

						JSONArray lCapacityJSONArray = null;
						if (lJsonObj.has("capacity"))
							lCapacityJSONArray = new JSONArray(lJsonObj.get("capacity").toString());

						JSONArray lCapacityAirline = null;
						if (lJsonObj.has("capacity_airline"))
							lCapacityAirline = new JSONArray(lJsonObj.get("capacity_airline").toString());

						JSONArray lCarrierRatingArray = null;
						if (lJsonObj.has("rating_carrier"))
							lCarrierRatingArray = new JSONArray(lJsonObj.get("rating_carrier").toString());

						JSONArray lRatingArray = null;
						if (lJsonObj.has("rating"))
							lRatingArray = new JSONArray(lJsonObj.get("rating").toString());

						JSONArray lPaxJSONArray = null;
						if (lJsonObj.has("pax"))
							lPaxJSONArray = new JSONArray(lJsonObj.get("pax").toString());

						JSONArray lPax_1JSONArray = null;
						if (lJsonObj.has("pax_1"))
							lPax_1JSONArray = new JSONArray(lJsonObj.get("pax_1").toString());

						JSONArray lForecastPaxJSONArray = null;
						if (lJsonObj.has("forecast_pax"))
							lForecastPaxJSONArray = new JSONArray(lJsonObj.get("forecast_pax").toString());

						JSONArray lForecastRevenueJSONArray = null;
						if (lJsonObj.has("forecast_revenue"))
							lForecastRevenueJSONArray = new JSONArray(lJsonObj.get("forecast_revenue").toString());

						JSONArray lFlownPaxJSONArray = null;
						if (lJsonObj.has("flown_pax"))
							lFlownPaxJSONArray = new JSONArray(lJsonObj.get("flown_pax").toString());

						JSONArray lRevenueJSONArray = null;
						if (lJsonObj.has("revenue"))
							lRevenueJSONArray = new JSONArray(lJsonObj.get("revenue").toString());

						JSONArray lRevenue_1JSONArray = null;
						if (lJsonObj.has("revenue_1"))
							lRevenue_1JSONArray = new JSONArray(lJsonObj.get("revenue_1").toString());

						JSONArray lFlownRevenueJSONArray = null;
						if (lJsonObj.has("flown_revenue"))
							lFlownRevenueJSONArray = new JSONArray(lJsonObj.get("flown_revenue").toString());

						JSONArray lFlownRevenue_1JSONArray = null;
						if (lJsonObj.has("flown_revenue_1"))
							lFlownRevenue_1JSONArray = new JSONArray(lJsonObj.get("flown_revenue_1").toString());

						JSONArray lTargetRevenueJSONArray = null;
						if (lJsonObj.has("target_revenue"))
							lTargetRevenueJSONArray = new JSONArray(lJsonObj.get("target_revenue").toString());

						JSONArray lMarketRevenueJSONArray = null;
						if (lJsonObj.has("market_revenue"))
							lMarketRevenueJSONArray = new JSONArray(lJsonObj.get("market_revenue").toString());

						JSONArray lMarketRevenue_1JSONArray = null;
						if (lJsonObj.has("market_revenue_1"))
							lMarketRevenue_1JSONArray = new JSONArray(lJsonObj.get("market_revenue_1").toString());

						float lMarketSize = 0;
						for (int p = 0; p < lMarketSharePaxJSONArray.length(); p++)
							if (!"[]".equalsIgnoreCase(lMarketSharePaxJSONArray.get(p).toString())
									&& !"null".equalsIgnoreCase(lMarketSharePaxJSONArray.get(p).toString()))
								lMarketSize += Float.parseFloat(lMarketSharePaxJSONArray.get(p).toString());

						long lMarketSizelastyr = 0;
						for (int p = 0; p < lMarketSharePax_1JSONArray.length(); p++)
							if (!"[]".equalsIgnoreCase(lMarketSharePax_1JSONArray.get(p).toString())
									&& !"null".equalsIgnoreCase(lMarketSharePax_1JSONArray.get(p).toString()))
								lMarketSizelastyr += Float.parseFloat(lMarketSharePax_1JSONArray.get(p).toString());

						float lCurrentObjPax = 0;
						if (lPaxJSONArray != null && lPaxJSONArray.length() != 0) {
							for (int p = 0; p < lPaxJSONArray.length(); p++)
								if (!"[]".equalsIgnoreCase(lPaxJSONArray.get(p).toString())
										&& !"null".equalsIgnoreCase(lPaxJSONArray.get(p).toString()))
									lCurrentObjPax += Float.parseFloat(lPaxJSONArray.get(p).toString());
						}

						float lCurrentObjPaxlastyr = 0;
						if (lPax_1JSONArray != null && lPax_1JSONArray.length() != 0) {
							for (int p = 0; p < lPax_1JSONArray.length(); p++)
								if (!"[]".equalsIgnoreCase(lPax_1JSONArray.get(p).toString())
										&& !"null".equalsIgnoreCase(lPax_1JSONArray.get(p).toString()))
									lCurrentObjPaxlastyr += Float.parseFloat(lPax_1JSONArray.get(p).toString());
						}

						float lTotalTargetPax = 0;
						if (lHostTargetPaxJSONArray != null && lHostTargetPaxJSONArray.length() != 0) {
							for (int p = 0; p < lHostTargetPaxJSONArray.length(); p++)
								if (!"[]".equalsIgnoreCase(lHostTargetPaxJSONArray.get(p).toString())
										&& !"null".equalsIgnoreCase(lHostTargetPaxJSONArray.get(p).toString()))
									lTotalTargetPax += Float.parseFloat(lHostTargetPaxJSONArray.get(p).toString());
						}

						float lTotalForecastPax = 0;
						if (!Utility.datePassed(lJsonObj.get("dep_date").toString())) {
							if (lForecastPaxJSONArray != null) {
								for (int m = 0; m < lForecastPaxJSONArray.length(); m++) {
									if (!"null".equalsIgnoreCase(lForecastPaxJSONArray.get(m).toString()))
										lTotalForecastPax += Float.parseFloat(lForecastPaxJSONArray.get(m).toString());
								}
							}
						}

						float lTotalForecastRevenue = 0;
						if (!Utility.datePassed(lJsonObj.get("dep_date").toString())) {
							if (lForecastRevenueJSONArray != null) {
								for (int m = 0; m < lForecastRevenueJSONArray.length(); m++) {
									if (!"null".equalsIgnoreCase(lForecastRevenueJSONArray.get(m).toString()))
										lTotalForecastRevenue += Float
												.parseFloat(lForecastRevenueJSONArray.get(m).toString());
								}
							}
						}

						float lCurrentObjFlownPax = 0;
						if (lFlownPaxJSONArray != null) {
							for (int m = 0; m < lFlownPaxJSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lFlownPaxJSONArray.get(m).toString()))
									lCurrentObjFlownPax += Float.parseFloat(lFlownPaxJSONArray.get(m).toString());

							}
						}

						float lCurrentObjRevenue = 0;
						if (lRevenueJSONArray != null && lRevenueJSONArray.length() != 0) {
							for (int p = 0; p < lRevenueJSONArray.length(); p++)
								if (!"[]".equalsIgnoreCase(lRevenueJSONArray.get(p).toString())
										&& !"null".equalsIgnoreCase(lRevenueJSONArray.get(p).toString()))
									lCurrentObjRevenue += Float.parseFloat(lRevenueJSONArray.get(p).toString());
						}

						float lCurrentObjRevenuelastyr = 0;
						if (lRevenueJSONArray != null && lRevenueJSONArray.length() != 0) {
							for (int p = 0; p < lRevenue_1JSONArray.length(); p++)
								if (!"[]".equalsIgnoreCase(lRevenue_1JSONArray.get(p).toString())
										&& !"null".equalsIgnoreCase(lRevenue_1JSONArray.get(p).toString()))
									lCurrentObjRevenuelastyr += Float.parseFloat(lRevenue_1JSONArray.get(p).toString());
						}

						float lCurrentObjFlownRevenue = 0.0f;
						for (int p = 0; p < lFlownRevenueJSONArray.length(); p++)
							if (!"[]".equalsIgnoreCase(lFlownRevenueJSONArray.get(p).toString())
									&& !"null".equalsIgnoreCase(lFlownRevenueJSONArray.get(p).toString()))
								lCurrentObjFlownRevenue += Float.parseFloat(lFlownRevenueJSONArray.get(p).toString());

						float lCurrentObjFlownRevenuelastyr = 0.0f;
						for (int p = 0; p < lFlownRevenue_1JSONArray.length(); p++)
							if (!"[]".equalsIgnoreCase(lFlownRevenue_1JSONArray.get(p).toString())
									&& !"null".equalsIgnoreCase(lFlownRevenue_1JSONArray.get(p).toString()))
								lCurrentObjFlownRevenuelastyr += Float
										.parseFloat(lFlownRevenue_1JSONArray.get(p).toString());

						float lTotalTargetRevenue = 0.0f;
						for (int p = 0; p < lTargetRevenueJSONArray.length(); p++)
							if (!"[]".equalsIgnoreCase(lTargetRevenueJSONArray.get(p).toString())
									&& !"null".equalsIgnoreCase(lTargetRevenueJSONArray.get(p).toString()))
								lTotalTargetRevenue += Float.parseFloat(lTargetRevenueJSONArray.get(p).toString());

						float lCarriersCapacity = 0;
						if (lCapacityAirline != null && lCarrierRatingArray != null) {
							for (int r = 0; r < lCapacityAirline.length(); r++) {
								for (int k = 0; k < lCarrierRatingArray.length(); k++) {
									if (lCarrierRatingArray.length() >= lCapacityAirline.length()
											&& lCapacityAirline.get(r).toString()
													.equalsIgnoreCase(lCarrierRatingArray.get(k).toString())
											&& lCarrierRatingArray.length() == lRatingArray.length()) {

										if (!lCapacityJSONArray.get(r).toString().equalsIgnoreCase("null")
												&& !lCapacityJSONArray.get(r).toString().equalsIgnoreCase("[]")
												&& !lRatingArray.get(k).toString().equalsIgnoreCase("null")
												&& !lRatingArray.get(k).toString().equalsIgnoreCase("[]"))
											lCarriersCapacity += Float.parseFloat(lCapacityJSONArray.get(r).toString())
													* (Float.parseFloat(lRatingArray.get(k).toString()));

									}
								}
							}
						}

						int lNoOfDaysInMonth = Utility.numberOfDaysInMonth(
								Utility.findMonth(lJsonObj.get("dep_date").toString()) - 1,
								Utility.findYear(lJsonObj.get("dep_date").toString()));

						float lCurrentObjProratedTargetPax = lTotalTargetPax / (float) lNoOfDaysInMonth;
						float lCurrentObjProratedForecastPax = lTotalForecastPax / (float) lNoOfDaysInMonth;
						float lCurrentObjProratedForecastRevenue = lTotalForecastRevenue / (float) lNoOfDaysInMonth;
						float lCurrentObjProratedRevenueTarget = lTotalTargetRevenue / (float) lNoOfDaysInMonth;

						float lPax = (float) lSalesModel.getPax() + lCurrentObjPax;
						float lPaxlastyr = (float) lSalesModel.getPax_1() + lCurrentObjPaxlastyr;
						float lFlownPax = (float) lSalesModel.getFlownPax() + lCurrentObjFlownPax;
						float lTargetPax = lCurrentObjProratedTargetPax;
						float lForecastPax = lCurrentObjProratedForecastPax;
						float lForecastRevenue = lCurrentObjProratedForecastRevenue;
						float lTargetRevenue = lCurrentObjProratedRevenueTarget;
						float lRevenue = (float) lSalesModel.getRevenue() + lCurrentObjRevenue;
						float lRevenuelastyr = (float) lSalesModel.getRevenue_1() + lCurrentObjRevenuelastyr;
						float lFlownRevenue = (float) lSalesModel.getFlownRevenue() + lCurrentObjFlownRevenue;
						float lFlownRevenuelastyr = (float) lSalesModel.getFlownRevenue_1()
								+ lCurrentObjFlownRevenuelastyr;

						int lMonth = 0;
						int lYear = 0;
						int lDay = 0;

						if (lJsonObj.has("dep_date") && !"null".equalsIgnoreCase(lJsonObj.get("dep_date").toString())) {

							lMonth = Utility.findMonth(lJsonObj.get("dep_date").toString());
							lYear = Utility.findYear(lJsonObj.get("dep_date").toString());
							lDay = Utility.findDay(lJsonObj.get("dep_date").toString());

						}

						if ("depDate".equalsIgnoreCase(lKeyType)) {
							if (lSalesModel.getMonth() != lMonth && lSalesModel.getDay() != lDay
									|| lSalesModel.getMonth() == lMonth && lSalesModel.getDay() == lDay) {

								lMarketSize += (float) lSalesModel.getMarketSize();
								lTargetPax += (float) lSalesModel.getTargetPax();
								lForecastPax += (float) lSalesModel.getForecastPax();
								lForecastRevenue += (float) lSalesModel.getRevenueForecast();
								lTargetRevenue = (float) lSalesModel.getTargetRevenue();
								lCarriersCapacity += (float) lSalesModel.getCarrierCapacity();

							}

						} else {

							if (lSalesModel.getMonth() != lMonth) {

								lMarketSize += (float) lSalesModel.getMarketSize();
								lTargetPax += (float) lSalesModel.getTargetPax();
								lForecastPax += (float) lSalesModel.getForecastPax();
								lForecastRevenue += (float) lSalesModel.getRevenueForecast();
								lTargetRevenue = (float) lSalesModel.getTargetRevenue();
								lCarriersCapacity += (float) lSalesModel.getCarrierCapacity();

							}

						}

						/* host lRevenue calculations */

						float lHostRevenueSalesYTD = lRevenue;
						float lHostRevenueFlownYTD = lFlownRevenue;

						float lHostRevenueVLYR = 0.0F;
						if (lFlownRevenuelastyr != 0)
							lHostRevenueVLYR = CalculationUtil.calculateVLYR(lRevenue, lRevenuelastyr);

						float lHostRevenueVTGT = 0.0F;
						if (lTargetRevenue != 0)
							lHostRevenueVTGT = CalculationUtil.calculateVTGTRemodelled(lRevenue, lForecastRevenue,
									lTargetRevenue);

						/* host lCurrentObjPax calculation */

						float lHostPaxYTD = lCurrentObjPax;

						float lHostPaxVLYR = 0.0f;
						if (lCurrentObjPaxlastyr != 0)
							lHostPaxVLYR = CalculationUtil.calculateVLYR(lPax, lPaxlastyr);

						float lHostPaxVTGT = 0.0f;
						if (lTargetPax != 0)
							lHostPaxVTGT = CalculationUtil.calculateVTGTRemodelled(lFlownPax, lForecastPax, lTargetPax);

						if (lCarrierJSONArray != null && lUserProfile != null)
							for (int c = 0; c < lCarrierJSONArray.length(); c++) {

								String lCarrier = lCarrierJSONArray.get(c).toString();
								Map<String, Object> lCompetitors = new HashMap<String, Object>();

								float lMarketSharePaxlastyr = 0.0f;
								if (lMarketSharePax_1JSONArray != null
										&& lMarketSharePax_1JSONArray.length() == lCarrierJSONArray.length()
										&& !"[]".equalsIgnoreCase(lMarketSharePax_1JSONArray.get(c).toString()))
									lMarketSharePaxlastyr = Float
											.parseFloat(lMarketSharePax_1JSONArray.get(c).toString());

								float lMarketSharePax = 0.0f;
								if (lMarketSharePaxJSONArray.length() == lCarrierJSONArray.length()
										&& !"[]".equalsIgnoreCase(lMarketSharePaxJSONArray.get(c).toString()))
									lMarketSharePax = Float.parseFloat(lMarketSharePaxJSONArray.get(c).toString());

								float lMarketRevenue = 0.0f;
								if (lMarketRevenueJSONArray.length() == lCarrierJSONArray.length()
										&& !"[]".equalsIgnoreCase(lMarketRevenueJSONArray.get(c).toString()))
									lMarketRevenue = Float.parseFloat(lMarketRevenueJSONArray.get(c).toString());

								float lMarketRevenuelastyr = 0.0f;
								if (lMarketRevenue_1JSONArray.length() == lCarrierJSONArray.length()
										&& "[]".equalsIgnoreCase(lMarketRevenue_1JSONArray.get(c).toString()))
									lMarketRevenuelastyr = Float
											.parseFloat(lMarketRevenue_1JSONArray.get(c).toString());

								float lRating = Constants.DEFAULT_COMPETITOR_RATING;
								if (lCarrierRatingArray != null
										&& lCarrierRatingArray.length() >= lRatingArray.length()) {
									for (int r = 0; r < lCarrierRatingArray.length(); r++) {
										if (lCarrierRatingArray.get(r).toString().equalsIgnoreCase(lCarrier)) {

											if (!lRatingArray.get(r).toString().equalsIgnoreCase("null")
													&& !lRatingArray.get(r).toString().equalsIgnoreCase("[]"))

												lRating = Float.parseFloat(lRatingArray.get(r).toString());

										}
									}
								}
								float lAirlineCapcity = 0;
								if (lCapacityAirline != null && lCapacityJSONArray != null
										&& lCapacityAirline.length() == lCapacityJSONArray.length()) {
									for (int r = 0; r < lCapacityAirline.length(); r++) {
										if (lCapacityAirline.get(r).toString().equalsIgnoreCase(lCarrier)) {
											lAirlineCapcity += Float.parseFloat(lCapacityJSONArray.get(r).toString());
										}
									}

								}

								if ("depDate".equalsIgnoreCase(lKeyType)) {
									if (lSalesModel.getMonth() != lMonth && lSalesModel.getDay() != lDay
											|| lSalesModel.getMonth() == lMonth && lSalesModel.getDay() == lDay) {

										if (((Map) (lSalesModel).getCompetitors()) != null
												&& ((Map) (lSalesModel).getCompetitors()).containsKey(lCarrier)
												&& ((Map) ((Sales) lResultMap.get(lKey)).getCompetitors())
														.get(lCarrier) != null) {

											Map lCarrierMap = (Map) ((Map) ((Map) (lSalesModel).getCompetitors())
													.get(lCarrier));

											lMarketSharePax += Float
													.parseFloat(lCarrierMap.get("marketSharePax").toString());

											lMarketSharePaxlastyr += Float
													.parseFloat(lCarrierMap.get("marketSharePax_1").toString());

											lMarketRevenue += Float
													.parseFloat(lCarrierMap.get("marketRevenue").toString());

											lMarketRevenuelastyr += Float
													.parseFloat(lCarrierMap.get("marketRevenue_1").toString());

											lAirlineCapcity += Float.parseFloat(lCarrierMap.get("capacity").toString());

										}

									}

								} else {

									if ((lSalesModel).getMonth() != lMonth) {

										if (((Map) (lSalesModel).getCompetitors()) != null
												&& ((Map) (lSalesModel).getCompetitors()).containsKey(lCarrier)
												&& ((Map) (lSalesModel).getCompetitors()).get(lCarrier) != null) {

											Map lCarrierMap = (Map) ((Map) ((Map) (lSalesModel).getCompetitors())
													.get(lCarrier));

											lMarketSharePax += Float
													.parseFloat(lCarrierMap.get("marketSharePax").toString());

											lMarketSharePaxlastyr += Float
													.parseFloat(lCarrierMap.get("marketSharePax_1").toString());

											lMarketRevenue += Float
													.parseFloat(lCarrierMap.get("marketRevenue").toString());

											lMarketRevenuelastyr += Float
													.parseFloat(lCarrierMap.get("marketRevenue_1").toString());

											lAirlineCapcity += Float.parseFloat(lCarrierMap.get("capacity").toString());

										}
									}

								}

								float lCarriermarketShare = 0.0f;
								if (lMarketSize != 0)
									lCarriermarketShare = CalculationUtil.doDivision(lMarketSharePax, lMarketSize)
											* 100;

								float lCarrierMarketSharelastyr = 0.0f;
								if (lMarketSizelastyr != 0)
									lCarrierMarketSharelastyr = CalculationUtil.doDivision(lMarketSharePaxlastyr,
											lMarketSizelastyr) * 100;

								float lCarrierMarketShareVLYR = 0.0f;
								if (lCarrierMarketSharelastyr != 0)
									lCarrierMarketShareVLYR = CalculationUtil.calculateVLYR(lCarriermarketShare,
											lCarrierMarketSharelastyr);

								float lCarrierMarketShareYTD = 0;
								if (lMarketSize != 0)
									lCarrierMarketShareYTD = CalculationUtil.doDivision(lMarketSharePax, lMarketSize)
											* 100;

								float lCarrierFMS = CalculationUtil.calculateFMS(lAirlineCapcity, lRating,
										lCarriersCapacity);

								float lCarrierMarketShareVTGT = 0.0f;
								if (lCarrierFMS != 0)
									lCarrierMarketShareVTGT = CalculationUtil.calculateVTGT(lCarriermarketShare,
											lCarrierFMS);

								float carrierRevenueYTD = 0.0f;
								carrierRevenueYTD = lMarketRevenue;

								float carrierRevenueVLYR = 0.0F;
								if (lMarketRevenuelastyr != 0)
									carrierRevenueVLYR = CalculationUtil.calculateVLYR(lMarketRevenue,
											lMarketRevenuelastyr);

								lCompetitors.put("name", lCarrier);
								lCompetitors.put("marketSharePax", Math.round(lMarketSharePax));
								lCompetitors.put("marketSharePax_1", Math.round(lMarketSharePaxlastyr));
								lCompetitors.put("marketRevenue", Math.round(lMarketRevenue));
								lCompetitors.put("marketRevenue_1", Math.round(lMarketRevenuelastyr));
								lCompetitors.put("rating", lRating);
								lCompetitors.put("capacity", Math.round(lAirlineCapcity));
								lCompetitors.put("FMS", Math.round(lCarrierFMS));
								lCompetitors.put("MarketShareYTD", Math.round(lCarrierMarketShareYTD));
								lCompetitors.put("MarketShareVLYR", Math.round(lCarrierMarketShareVLYR));
								lCompetitors.put("MarketShareVTGT", Math.round(lCarrierMarketShareVTGT));
								lCompetitors.put("marketRevenueYTD", Math.round(carrierRevenueYTD));
								lCompetitors.put("marketRevenueVLYR", Math.round(carrierRevenueVLYR));

								lCompMap = (Map) ((Sales) lResultMap.get(lKey)).getCompetitors();

								lCompMap.put(lCarrier, lCompetitors);

							}

						lSalesModel = (Sales) lResultMap.get(lKey);

						lSalesModel.setDep_date((lSalesModel).getDep_date());
						lSalesModel.setRegion((lSalesModel).getRegion());
						lSalesModel.setCountry((lSalesModel).getCountry());
						lSalesModel.setPos((lSalesModel).getPos());
						lSalesModel.setOrigin((lSalesModel).getOrigin());
						lSalesModel.setDestination((lSalesModel).getDestination());
						lSalesModel.setCompartment((lSalesModel).getCompartment());
						lSalesModel.setPax(Math.round(lCurrentObjPax));
						lSalesModel.setFlownPax(Math.round(lFlownPax));
						lSalesModel.setPax_1(Math.round(lCurrentObjPaxlastyr));
						lSalesModel.setTargetPax(lTargetPax);
						lSalesModel.setForecastPax(lForecastPax);
						lSalesModel.setRevenue(Math.round(lRevenue));
						lSalesModel.setRevenue_1(Math.round(lRevenuelastyr));
						lSalesModel.setFlownRevenue(Math.round(lFlownRevenue));
						lSalesModel.setFlownRevenue_1(Math.round(lFlownRevenuelastyr));
						lSalesModel.setTargetRevenue(lTargetRevenue);
						lSalesModel.setRevenueForecast(lForecastRevenue);
						lSalesModel.setMarketSize(Math.round(lMarketSize));
						lSalesModel.setMarketSize_1(lMarketSizelastyr);
						lSalesModel.setHostRevenueSales(Math.round(lHostRevenueSalesYTD));
						lSalesModel.setHostRevenueFlown(Math.round(lHostRevenueFlownYTD));
						lSalesModel.setHostRevenueVLYR(Math.round(lHostRevenueVLYR));
						lSalesModel.setHostRevenueVTGT(Math.round(lHostRevenueVTGT));
						lSalesModel.setHostPaxYTD(Math.round(lHostPaxYTD));
						lSalesModel.setHostPaxVLYR(Math.round(lHostPaxVLYR));
						lSalesModel.setHostPaxVTGT(Math.round(lHostPaxVTGT));
						lSalesModel.setMonth(lMonth);
						lSalesModel.setDay(lDay);
						lSalesModel.setYear(lYear);
						lSalesModel.setCarrierCapacity(lCarriersCapacity);
						lSalesModel.setKey(lKey);

						lResultMap.put(lKey, lSalesModel);

					} else if (!lResultMap.containsKey(lKey)) {

						lSalesModel = new Sales();
						lCompMap = new HashMap<String, Object>();

						JSONArray lCarrierJSONArray = null;
						if (lJsonObj.has("carrier"))
							lCarrierJSONArray = new JSONArray(lJsonObj.get("carrier").toString());

						JSONArray lMarketSharePaxJSONArray = null;
						if (lJsonObj.has("market_share_pax"))
							lMarketSharePaxJSONArray = new JSONArray(lJsonObj.get("market_share_pax").toString());

						JSONArray lMarketSharePax_1JSONArray = null;
						if (lJsonObj.has("market_share_pax_1"))
							lMarketSharePax_1JSONArray = new JSONArray(lJsonObj.get("market_share_pax_1").toString());

						JSONArray lHostTargetPaxJSONArray = null;
						if (lJsonObj.has("target_pax"))
							lHostTargetPaxJSONArray = new JSONArray(lJsonObj.get("target_pax").toString());

						JSONArray lCapacityJSONArray = null;
						if (lJsonObj.has("capacity"))
							lCapacityJSONArray = new JSONArray(lJsonObj.get("capacity").toString());

						JSONArray lRatingArray = null;
						if (lJsonObj.has("rating"))
							lRatingArray = new JSONArray(lJsonObj.get("rating").toString());

						JSONArray lCapacityAirline = null;
						if (lJsonObj.has("capacity_airline"))
							lCapacityAirline = new JSONArray(lJsonObj.get("capacity_airline").toString());

						JSONArray lCarrierRatingArray = null;
						if (lJsonObj.has("rating_carrier"))
							lCarrierRatingArray = new JSONArray(lJsonObj.get("rating_carrier").toString());

						JSONArray lPaxJSONArray = null;
						if (lJsonObj.has("pax"))
							lPaxJSONArray = new JSONArray(lJsonObj.get("pax").toString());

						JSONArray lPax_1JSONArray = null;
						if (lJsonObj.has("pax_1"))
							lPax_1JSONArray = new JSONArray(lJsonObj.get("pax_1").toString());

						JSONArray lForecastRevenueJSONArray = null;
						if (lJsonObj.has("forecast_revenue"))
							lForecastRevenueJSONArray = new JSONArray(lJsonObj.get("forecast_revenue").toString());

						JSONArray lFlownPaxJSONArray = null;
						if (lJsonObj.has("flown_pax"))
							lFlownPaxJSONArray = new JSONArray(lJsonObj.get("flown_pax").toString());

						JSONArray lForecastPaxJSONArray = null;
						if (lJsonObj.has("forecast_pax"))
							lForecastPaxJSONArray = new JSONArray(lJsonObj.get("forecast_pax").toString());

						JSONArray lRevenueJSONArray = null;
						if (lJsonObj.has("revenue"))
							lRevenueJSONArray = new JSONArray(lJsonObj.get("revenue").toString());

						JSONArray lRevenue_1JSONArray = null;
						if (lJsonObj.has("revenue_1"))
							lRevenue_1JSONArray = new JSONArray(lJsonObj.get("revenue_1").toString());

						JSONArray lFlownRevenueJSONArray = null;
						if (lJsonObj.has("flown_revenue"))
							lFlownRevenueJSONArray = new JSONArray(lJsonObj.get("flown_revenue").toString());

						JSONArray lFlownRevenue_1JSONArray = null;
						if (lJsonObj.has("flown_revenue_1"))
							lFlownRevenue_1JSONArray = new JSONArray(lJsonObj.get("flown_revenue_1").toString());

						JSONArray lMarketRevenueJSONArray = null;
						if (lJsonObj.has("market_revenue"))
							lMarketRevenueJSONArray = new JSONArray(lJsonObj.get("market_revenue").toString());

						JSONArray lMarketRevenue_1JSONArray = null;
						if (lJsonObj.has("market_revenue_1"))
							lMarketRevenue_1JSONArray = new JSONArray(lJsonObj.get("market_revenue_1").toString());

						JSONArray lTargetRevenueJSONArray = null;
						if (lJsonObj.has("target_revenue"))
							lTargetRevenueJSONArray = new JSONArray(lJsonObj.get("target_revenue").toString());

						float lMarketSize = 0;
						for (int p = 0; p < lMarketSharePaxJSONArray.length(); p++)
							if (!"[]".equalsIgnoreCase(lMarketSharePaxJSONArray.get(p).toString())
									&& !"null".equalsIgnoreCase(lMarketSharePaxJSONArray.get(p).toString()))
								lMarketSize += Float.parseFloat(lMarketSharePaxJSONArray.get(p).toString());

						long lMarketSizelastyr = 0;
						for (int p = 0; p < lMarketSharePax_1JSONArray.length(); p++)
							if (!"[]".equalsIgnoreCase(lMarketSharePax_1JSONArray.get(p).toString())
									&& !"null".equalsIgnoreCase(lMarketSharePax_1JSONArray.get(p).toString()))
								lMarketSizelastyr += Float.parseFloat(lMarketSharePax_1JSONArray.get(p).toString());

						float lCurrentObjPax = 0;
						if (lPaxJSONArray != null && lPaxJSONArray.length() != 0) {
							for (int p = 0; p < lPaxJSONArray.length(); p++)
								if (!"[]".equalsIgnoreCase(lPaxJSONArray.get(p).toString())
										&& !"null".equalsIgnoreCase(lPaxJSONArray.get(p).toString()))
									lCurrentObjPax += Float.parseFloat(lPaxJSONArray.get(p).toString());
						}

						float lCurrentObjPaxlastyr = 0;
						if (lPax_1JSONArray != null && lPax_1JSONArray.length() != 0) {
							for (int p = 0; p < lPax_1JSONArray.length(); p++)
								if (!"[]".equalsIgnoreCase(lPax_1JSONArray.get(p).toString())
										&& !"null".equalsIgnoreCase(lPax_1JSONArray.get(p).toString()))
									lCurrentObjPaxlastyr += Float.parseFloat(lPax_1JSONArray.get(p).toString());
						}

						float lTotalTargetPax = 0;
						if (lHostTargetPaxJSONArray != null && lHostTargetPaxJSONArray.length() != 0) {
							for (int p = 0; p < lHostTargetPaxJSONArray.length(); p++)
								if (!"[]".equalsIgnoreCase(lHostTargetPaxJSONArray.get(p).toString())
										&& !"null".equalsIgnoreCase(lHostTargetPaxJSONArray.get(p).toString()))
									lTotalTargetPax += Float.parseFloat(lHostTargetPaxJSONArray.get(p).toString());
						}

						float lTotalForecastPax = 0;
						if (!Utility.datePassed(lJsonObj.get("dep_date").toString())) {
							if (lForecastPaxJSONArray != null) {
								for (int m = 0; m < lForecastPaxJSONArray.length(); m++) {
									if (!"null".equalsIgnoreCase(lForecastPaxJSONArray.get(m).toString()))
										lTotalForecastPax += Float.parseFloat(lForecastPaxJSONArray.get(m).toString());
								}
							}
						}

						float lTotalForecastRevenue = 0;
						if (!Utility.datePassed(lJsonObj.get("dep_date").toString())) {
							if (lForecastRevenueJSONArray != null) {
								for (int m = 0; m < lForecastRevenueJSONArray.length(); m++) {
									if (!"null".equalsIgnoreCase(lForecastRevenueJSONArray.get(m).toString()))
										lTotalForecastRevenue += Float
												.parseFloat(lForecastRevenueJSONArray.get(m).toString());
								}
							}
						}

						float lCurrentObjFlownPax = 0;
						if (lFlownPaxJSONArray != null) {
							for (int m = 0; m < lFlownPaxJSONArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lFlownPaxJSONArray.get(m).toString()))
									lCurrentObjFlownPax += Float.parseFloat(lFlownPaxJSONArray.get(m).toString());

							}
						}

						float lCurrentObjRevenue = 0;
						if (lRevenueJSONArray != null && lRevenueJSONArray.length() != 0) {
							for (int p = 0; p < lRevenueJSONArray.length(); p++)
								if (!"[]".equalsIgnoreCase(lRevenueJSONArray.get(p).toString())
										&& !"null".equalsIgnoreCase(lRevenueJSONArray.get(p).toString()))
									lCurrentObjRevenue += Float.parseFloat(lRevenueJSONArray.get(p).toString());
						}

						float lCurrentObjRevenuelastyr = 0;
						if (lRevenueJSONArray != null && lRevenueJSONArray.length() != 0) {
							for (int p = 0; p < lRevenue_1JSONArray.length(); p++)
								if (!"[]".equalsIgnoreCase(lRevenue_1JSONArray.get(p).toString())
										&& !"null".equalsIgnoreCase(lRevenue_1JSONArray.get(p).toString()))
									lCurrentObjRevenuelastyr += Float.parseFloat(lRevenue_1JSONArray.get(p).toString());
						}

						float lCurrentObjFlownRevenue = 0.0f;
						for (int p = 0; p < lFlownRevenueJSONArray.length(); p++)
							if (!"[]".equalsIgnoreCase(lFlownRevenueJSONArray.get(p).toString())
									&& !"null".equalsIgnoreCase(lFlownRevenueJSONArray.get(p).toString()))
								lCurrentObjFlownRevenue += Float.parseFloat(lFlownRevenueJSONArray.get(p).toString());

						float lCurrentObjFlownRevenuelastyr = 0.0f;
						for (int p = 0; p < lFlownRevenue_1JSONArray.length(); p++)
							if (!"[]".equalsIgnoreCase(lFlownRevenue_1JSONArray.get(p).toString())
									&& !"null".equalsIgnoreCase(lFlownRevenue_1JSONArray.get(p).toString()))
								lCurrentObjFlownRevenuelastyr += Float
										.parseFloat(lFlownRevenue_1JSONArray.get(p).toString());

						float lTotalTargetRevenue = 0.0f;
						for (int p = 0; p < lTargetRevenueJSONArray.length(); p++)
							if (!"[]".equalsIgnoreCase(lTargetRevenueJSONArray.get(p).toString())
									&& !"null".equalsIgnoreCase(lTargetRevenueJSONArray.get(p).toString()))
								lTotalTargetRevenue += Float.parseFloat(lTargetRevenueJSONArray.get(p).toString());

						float lCarriersCapacity = 0;
						if (lCapacityAirline != null && lCarrierRatingArray != null) {
							for (int r = 0; r < lCapacityAirline.length(); r++) {
								for (int k = 0; k < lCarrierRatingArray.length(); k++) {
									if (lCarrierRatingArray.length() >= lCapacityAirline.length()
											&& lCapacityAirline.get(r).toString()
													.equalsIgnoreCase(lCarrierRatingArray.get(k).toString())
											&& lCarrierRatingArray.length() == lRatingArray.length()) {

										if (!lCapacityJSONArray.get(r).toString().equalsIgnoreCase("null")
												&& !lCapacityJSONArray.get(r).toString().equalsIgnoreCase("[]")
												&& !lRatingArray.get(k).toString().equalsIgnoreCase("null")
												&& !lRatingArray.get(k).toString().equalsIgnoreCase("[]"))
											lCarriersCapacity += Float.parseFloat(lCapacityJSONArray.get(r).toString())
													* (Float.parseFloat(lRatingArray.get(k).toString()));

									}
								}
							}
						}

						int lNoOfDaysInMonth = Utility.numberOfDaysInMonth(
								Utility.findMonth(lJsonObj.get("dep_date").toString()) - 1,
								Utility.findYear(lJsonObj.get("dep_date").toString()));

						float lCurrentObjProratedTargetPax = lTotalTargetPax / (float) lNoOfDaysInMonth;
						float lCurrentObjProratedForecastPax = lTotalForecastPax / (float) lNoOfDaysInMonth;
						float lCurrentObjProratedForecastRevenue = lTotalForecastRevenue / (float) lNoOfDaysInMonth;
						float lCurrentObjProratedRevenueTarget = lTotalTargetRevenue / (float) lNoOfDaysInMonth;

						/* host lRevenue calculations */

						float lHostRevenueSalesYTD = lCurrentObjRevenue;
						float lHostRevenueFlownYTD = lCurrentObjFlownRevenue;

						float lHostRevenueVLYR = 0.0F;
						if (lCurrentObjFlownRevenuelastyr != 0)
							lHostRevenueVLYR = CalculationUtil.calculateVLYR(lCurrentObjRevenue,
									lCurrentObjRevenuelastyr);

						float lHostRevenueVTGT = 0.0F;
						if (lCurrentObjProratedRevenueTarget != 0)
							lHostRevenueVTGT = CalculationUtil.calculateVTGTRemodelled(lCurrentObjFlownRevenue,
									lCurrentObjProratedForecastRevenue, lCurrentObjProratedRevenueTarget);

						/* host lCurrentObjPax calculation */

						float lHostPaxYTD = lCurrentObjPax;

						float lHostPaxVLYR = 0.0f;
						if (lCurrentObjPaxlastyr != 0)
							lHostPaxVLYR = CalculationUtil.calculateVLYR(lCurrentObjPax, lCurrentObjPaxlastyr);
						float lHostPaxVTGT = 0.0f;
						if (lCurrentObjProratedTargetPax != 0)
							lHostPaxVTGT = CalculationUtil.calculateVTGTRemodelled(lCurrentObjFlownPax,
									lCurrentObjProratedForecastPax, lCurrentObjProratedTargetPax);

						int lMonth = 0;
						int lYear = 0;
						int lDay = 0;

						if (lJsonObj.has("dep_date") && !"null".equalsIgnoreCase(lJsonObj.get("dep_date").toString())) {

							lMonth = Utility.findMonth(lJsonObj.get("dep_date").toString());
							lYear = Utility.findYear(lJsonObj.get("dep_date").toString());
							lDay = Utility.findDay(lJsonObj.get("dep_date").toString());

						}

						if (lCarrierJSONArray != null)
							for (int c = 0; c < lCarrierJSONArray.length(); c++) {

								String lCarrier = lCarrierJSONArray.get(c).toString();

								Map<String, Object> lCompetitors = new HashMap<String, Object>();

								float lMarketSharePaxlastyr = 0;
								if (lMarketSharePax_1JSONArray.length() != 0)
									lMarketSharePaxlastyr = Float
											.parseFloat(lMarketSharePax_1JSONArray.get(c).toString());

								float lMarketSharePax = 0;
								if (lMarketSharePaxJSONArray.length() != 0)
									lMarketSharePax = Float.parseFloat(lMarketSharePaxJSONArray.get(c).toString());

								float lMarketRevenue = 0;
								if (lMarketRevenueJSONArray.length() != 0) {

									lMarketRevenue = Float.parseFloat(lMarketRevenueJSONArray.get(c).toString());

								}

								float lMarketRevenuelastyr = 0;
								if (lMarketRevenue_1JSONArray.length() != 0)
									lMarketRevenuelastyr = Float
											.parseFloat(lMarketRevenue_1JSONArray.get(c).toString());

								float lCarriermarketShare = 0.0f;
								if (lMarketSize != 0)
									lCarriermarketShare = CalculationUtil.doDivision(lMarketSharePax, lMarketSize);

								float lCarrierMarketSharelastyr = 0.0f;
								if (lMarketSizelastyr != 0)
									lCarrierMarketSharelastyr = CalculationUtil.doDivision(lMarketSharePaxlastyr,
											lMarketSizelastyr);

								float lCarrierMarketShareVLYR = 0.0f;
								if (lCarrierMarketSharelastyr != 0)
									lCarrierMarketShareVLYR = CalculationUtil.calculateVLYR(lCarriermarketShare,
											lCarrierMarketSharelastyr);

								float lCarrierMarketShareYTD = 0;
								if (lMarketSize != 0)
									lCarrierMarketShareYTD = CalculationUtil.doDivision(lMarketSharePax, lMarketSize)
											* 100;

								float lRating = Constants.DEFAULT_COMPETITOR_RATING;
								if (lCarrierRatingArray != null
										&& lCarrierRatingArray.length() >= lRatingArray.length()) {
									for (int r = 0; r < lCarrierRatingArray.length(); r++) {
										if (lCarrierRatingArray.get(r).toString().equalsIgnoreCase(lCarrier)) {

											if (!lRatingArray.get(r).toString().equalsIgnoreCase("null")
													&& !lRatingArray.get(r).toString().equalsIgnoreCase("[]"))

												lRating = Float.parseFloat(lRatingArray.get(r).toString());

										}
									}
								}
								float lAirlineCapcity = 0;
								if (lCapacityAirline != null && lCapacityJSONArray != null
										&& lCapacityAirline.length() == lCapacityJSONArray.length()) {
									for (int r = 0; r < lCapacityAirline.length(); r++) {
										if (lCapacityAirline.get(r).toString().equalsIgnoreCase(lCarrier)) {
											lAirlineCapcity += Float.parseFloat(lCapacityJSONArray.get(r).toString());
										}
									}

								}

								float lCarrierFMS = CalculationUtil.calculateFMS(lAirlineCapcity, lRating,
										lCarriersCapacity);

								float lCarrierMarketShareVTGT = 0.0f;
								if (lCarrierFMS != 0)
									lCarrierMarketShareVTGT = CalculationUtil.calculateVTGT(lCarriermarketShare,
											lCarrierFMS);

								float carrierRevenueYTD = lMarketRevenue;

								float carrierRevenueVLYR = 0.0f;
								if (lMarketRevenuelastyr != 0)
									carrierRevenueVLYR = CalculationUtil.calculateVLYR(lMarketRevenue,
											lMarketRevenuelastyr);

								lCompetitors.put("name", lCarrier);
								lCompetitors.put("marketSharePax", Math.round(lMarketSharePax));
								lCompetitors.put("marketSharePax_1", Math.round(lMarketSharePaxlastyr));
								lCompetitors.put("marketRevenue", Math.round(lMarketRevenue));
								lCompetitors.put("marketRevenue_1", Math.round(lMarketRevenuelastyr));
								lCompetitors.put("rating", lRating);
								lCompetitors.put("capacity", lAirlineCapcity);
								lCompetitors.put("FMS", Math.round(lCarrierFMS));
								lCompetitors.put("MarketShareYTD", Math.round(lCarrierMarketShareYTD));
								lCompetitors.put("MarketShareVLYR", Math.round(lCarrierMarketShareVLYR));
								lCompetitors.put("MarketShareVTGT", Math.round(lCarrierMarketShareVTGT));
								lCompetitors.put("marketRevenueYTD", Math.round(carrierRevenueYTD));
								lCompetitors.put("marketRevenueVLYR", Math.round(carrierRevenueVLYR));

								lCompMap.put(lCarrier, lCompetitors);

								lSalesModel.setCompetitors(lCompMap);

							}

						if (lJsonObj.has("dep_date"))
							lSalesModel.setDep_date(lJsonObj.get("dep_date").toString());

						if (lJsonObj.has("region"))
							lSalesModel.setRegion(lJsonObj.get("region").toString());

						if (lJsonObj.has("country"))
							lSalesModel.setCountry(lJsonObj.get("country").toString());

						if (lJsonObj.has("pos"))
							lSalesModel.setPos(lJsonObj.get("pos").toString());

						if (lJsonObj.has("origin"))
							lSalesModel.setOrigin(lJsonObj.get("origin").toString());

						if (lJsonObj.has("destination"))
							lSalesModel.setDestination(lJsonObj.get("destination").toString());

						if (lJsonObj.has("compartment"))
							lSalesModel.setCompartment(lJsonObj.get("compartment").toString());

						lSalesModel.setPax(Math.round(lCurrentObjPax));
						lSalesModel.setPax_1(Math.round(lCurrentObjPaxlastyr));
						lSalesModel.setForecastPax(lCurrentObjProratedForecastPax);
						lSalesModel.setFlownPax(Math.round(lCurrentObjFlownPax));
						lSalesModel.setTargetPax(Math.round(lCurrentObjProratedTargetPax));
						lSalesModel.setRevenue(Math.round(lCurrentObjRevenue));
						lSalesModel.setRevenue_1(Math.round(lCurrentObjRevenuelastyr));
						lSalesModel.setFlownRevenue(Math.round(lCurrentObjFlownRevenue));
						lSalesModel.setFlownRevenue_1(Math.round(lCurrentObjFlownRevenuelastyr));
						lSalesModel.setTargetRevenue(Math.round(lCurrentObjProratedRevenueTarget));
						lSalesModel.setRevenueForecast(lCurrentObjProratedForecastRevenue);
						lSalesModel.setMarketSize(Math.round(lMarketSize));
						lSalesModel.setMarketSize_1(Math.round(lMarketSizelastyr));
						lSalesModel.setHostRevenueSales(Math.round(lHostRevenueSalesYTD));
						lSalesModel.setHostRevenueFlown(Math.round(lHostRevenueFlownYTD));
						lSalesModel.setHostRevenueVLYR(Math.round(lHostRevenueVLYR));
						lSalesModel.setHostRevenueVTGT(Math.round(lHostRevenueVTGT));
						lSalesModel.setHostPaxYTD(Math.round(lHostPaxYTD));
						lSalesModel.setHostPaxVLYR(Math.round(lHostPaxVLYR));
						lSalesModel.setHostPaxVTGT(Math.round(lHostPaxVTGT));
						lSalesModel.setDay(lDay);
						lSalesModel.setMonth(lMonth);
						lSalesModel.setYear(lYear);
						lSalesModel.setCarrierCapacity(lCarriersCapacity);
						lSalesModel.setKey(lKey);

						lResultMap.put(lKey, lSalesModel);
					}
				}
				Set<String> lOdKeySet = new HashSet<String>();
				lOdKeySet = lResultMap.keySet();
				for (String lKeys : lOdKeySet) {
					lKeyList.add(lResultMap.get(lKeys));
				}

				Map<String, Object> lAggMap = new HashMap<String, Object>();
				if (!"depDate".equalsIgnoreCase(lKeyType)) {

					float lAggHostPax = 0.0f;
					float lAggHostPaxlastyr = 0.0f;
					float lAggHostFlownPax = 0.0f;
					float lAggHostForecastPax = 0.0f;
					float lAggHostTargetPax = 0.0f;
					float lAggHostMarketSize = 0.0f;
					float lAggHostTicketed = 0.0f;
					float lAggHostRevenueSales = 0.0f;
					float lAggHostRevenueFlown = 0.0f;
					float lAggHostRevenueForecast = 0.0f;
					float lAggHostRevenueFlownlastyr = 0.0f;
					float lAggHostTargetRevenue = 0.0f;

					float lAggHostSalesRevenueYTD = 0.0f;
					float lAggHostFlownRevenueYTD = 0.0f;
					float lAggHostRevenueVLYR = 0.0f;
					float lAggHostRevenueVTGT = 0.0f;

					float lAggHostBookingsYTD = 0.0f;
					float lAggHostBookingsVLYR = 0.0f;
					float lAggHostBookingsVTGT = 0.0f;
					float lAggHostTicketedPercentage = 0.0f;
					float lAggCapacity = 0;
					float lAggCapacitylastyr = 0;
					float lRunRate = 0.0f;
					float lStrength = 0.0f;

					long lDays = 0;
					Date lDateFrom = Utility.convertStringToDateFromat(pRequestModel.getFromDate());
					Date lDateTo = Utility.convertStringToDateFromat(pRequestModel.getToDate());

					lDays = Utility.getDifferenceDays(lDateTo, lDateFrom) + 1;

					for (int i = 0; i < lKeyList.size(); i++) {

						Sales salesIterator = (Sales) lKeyList.get(i);

						float lFlownPax = (float) salesIterator.getFlownPax();
						float lTargetPax = ((float) salesIterator.getTargetPax()) * lDays;
						float lForecastPax = ((float) salesIterator.getForecastPax()) * lDays;

						float hostPaxVTGT = CalculationUtil.calculateVTGTRemodelled(lFlownPax, lForecastPax,
								lTargetPax);
						((Sales) lKeyList.get(i)).setHostPaxVTGT(Math.round(hostPaxVTGT));

						float lFlownRevenue = (float) salesIterator.getFlownRevenue();
						float lRevenueForecast = (float) salesIterator.getRevenueForecast() * lDays;
						float lRevenueTarget = (float) salesIterator.getTargetRevenue() * lDays;

						float hostRevenueVTGT = CalculationUtil.calculateVTGTRemodelled(lFlownRevenue, lRevenueForecast,
								lRevenueTarget);
						((Sales) lKeyList.get(i)).setHostRevenueVTGT(Math.round(hostRevenueVTGT));

						lAggHostPax += salesIterator.getPax();
						lAggHostPaxlastyr += salesIterator.getPax_1();
						lAggHostFlownPax += salesIterator.getFlownPax();
						lAggHostForecastPax += salesIterator.getForecastPax();
						lAggHostTargetPax += salesIterator.getTargetPax();
						lAggHostMarketSize += salesIterator.getMarketSize();

						lAggCapacity += salesIterator.getCarrierCapacity();
						lAggCapacitylastyr += salesIterator.getCarrierCapacity_1();

						lAggHostRevenueFlown += salesIterator.getHostRevenueFlown();
						lAggHostRevenueFlownlastyr += salesIterator.getFlownRevenue_1();
						lAggHostRevenueForecast += salesIterator.getRevenueForecast();
						lAggHostRevenueSales += salesIterator.getHostRevenueSales();
						lAggHostTargetRevenue += salesIterator.getTargetRevenue();

					}

					lAggHostBookingsYTD = lAggHostPax;
					if (lAggHostPaxlastyr != 0)
						lAggHostBookingsVLYR = CalculationUtil.calculateVLYR(lAggHostPax, lAggHostPaxlastyr);

					if (lAggHostTargetPax != 0)
						lAggHostBookingsVTGT = CalculationUtil.calculateVTGTRemodelled(lAggHostFlownPax,
								lAggHostForecastPax, lAggHostTargetPax);

					if (lAggHostTicketed != 0)
						lAggHostTicketedPercentage = CalculationUtil.doDivision(lAggHostPax, lAggHostTicketed) * 100;

					if (lDays != 0)
						lRunRate = CalculationUtil.doDivision(lAggHostPax, lDays);

					if (lAggCapacity != 0)
						lStrength = ((lAggHostPax - lAggHostPaxlastyr) * lAggCapacitylastyr) / lAggCapacity;

					lAggHostFlownRevenueYTD = lAggHostRevenueFlown;
					lAggHostSalesRevenueYTD = lAggHostRevenueSales;

					if (lAggHostRevenueFlownlastyr != 0)
						lAggHostRevenueVLYR = CalculationUtil.calculateVLYR(lAggHostRevenueSales,
								lAggHostRevenueFlownlastyr);

					if (lAggHostTargetRevenue != 0)
						lAggHostRevenueVTGT = CalculationUtil.calculateVTGTRemodelled(lAggHostRevenueFlown,
								lAggHostRevenueForecast, lAggHostTargetRevenue);

					lAggMap.put("aggregate_host_bookings_YTD", Math.round(lAggHostBookingsYTD));
					lAggMap.put("aggregate_host_bookings_VLYR", Math.round(lAggHostBookingsVLYR));
					lAggMap.put("aggregate_host_bookings_VTGT", Math.round(lAggHostBookingsVTGT));
					lAggMap.put("aggregate_host_marketSize", Math.round(lAggHostMarketSize));
					lAggMap.put("agg_host_ticketed_percentage", Math.round(lAggHostTicketedPercentage));
					lAggMap.put("aggregate_host_flown_revenue_YTD", Math.round(lAggHostFlownRevenueYTD));
					lAggMap.put("aggregate_host_sales_revenue_YTD", Math.round(lAggHostSalesRevenueYTD));
					lAggMap.put("aggregate_host_revenue_VLYR", Math.round(lAggHostRevenueVLYR));
					lAggMap.put("aggregate_host_revenue_VTGT", Math.round(lAggHostRevenueVTGT));
					lAggMap.put("tiles_runRate_currentValue", Math.round(lRunRate));
					lAggMap.put("tiles_strength", (lStrength));
					lAggMap.put("tiles_runRate_netRevenue", lAggHostRevenueSales);

				}

				String[] competitorsArray = lUserProfile.getCompetitors();
				for (int i = 0; i < lKeyList.size(); i++) {

					Sales salesIterator = (Sales) lKeyList.get(i);

					Map<String, Object> carriersMap = null;
					if (salesIterator.getCompetitors() != null)
						carriersMap = salesIterator.getCompetitors();

					Map<String, Object> compResultMap = new HashMap<String, Object>();

					for (int c = 0; c < competitorsArray.length; c++) {

						String lComp = competitorsArray[c].replace("\"", "");
						if (carriersMap != null && carriersMap.containsKey(lComp)) {
							compResultMap.put(lComp, carriersMap.get(lComp));
							carriersMap.remove(lComp);
						}
					}

					if (carriersMap.containsKey(Constants.hostName)) {
						compResultMap.put(Constants.hostName, carriersMap.get(Constants.hostName));
						carriersMap.remove(Constants.hostName);
					}

					Set<String> carriersSet = carriersMap.keySet();
					Map<String, Float> compMarketShareMap = new HashMap<String, Float>();

					for (String carrier : carriersSet) {

						Map<String, Object> carrierDetailsMap = null;
						if (carriersMap != null && carriersMap.containsKey(carrier))
							carrierDetailsMap = (Map) carriersMap.get(carrier);
						compMarketShareMap.put(carrier,
								Float.parseFloat(carrierDetailsMap.get("marketSharePax").toString()));
					}

					Map<String, Float> sortedCompMap = new TreeMap<String, Float>(compMarketShareMap);
					List<String> sortedCompList = new ArrayList<String>();

					for (String compKey : sortedCompMap.keySet()) {
						sortedCompList.add(compKey);
					}

					int noOfCarriers = 0;
					if (Constants.NO_OF_COMPETITORS > competitorsArray.length)
						noOfCarriers = Constants.NO_OF_COMPETITORS - competitorsArray.length;

					int carriersCount = 1;
					int sortedCompListLimit = sortedCompList.size() - 1;
					if (noOfCarriers <= sortedCompList.size()) {
						while (carriersCount <= noOfCarriers && sortedCompListLimit >= 0) {

							compResultMap.put(sortedCompList.get(sortedCompListLimit--),
									carriersMap.get(sortedCompList.get(sortedCompListLimit--)));

							carriersCount++;

						}
					} else {
						for (int c = 0; c < sortedCompList.size(); c++)
							compResultMap.put(sortedCompList.get(c), carriersMap.get(sortedCompList.get(c)));

					}

					((Sales) lKeyList.get(i)).setCompetitors(compResultMap);

				}

				lUserDataMap.put("Data", lKeyList);
				lUserDataMap.put("aggregateMap", lAggMap);

			} else {

				lUserDataMap.put("Data", null);
			}
		} catch (

		Exception e) {
			logger.error("getCalculatedSales-Exception", e);
		}

		return lUserDataMap;

	}

	@Override
	public Object getIndustryBenchmark(RequestModel pRequestModel) {

		return null;
	}

	@Override
	public ArrayList getKpiIndex(RequestModel pRequestModel) {
		ArrayList KpiIndexlist = mMainDashboardDao.getKpiIndex(pRequestModel);
		return KpiIndexlist;
	}

}
