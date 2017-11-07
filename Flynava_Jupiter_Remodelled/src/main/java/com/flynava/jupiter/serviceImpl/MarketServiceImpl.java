package com.flynava.jupiter.serviceImpl;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoImpl.CompetitorAnalysisDaoImpl;
import com.flynava.jupiter.daoInterface.MainDashboardDao;
import com.flynava.jupiter.daoInterface.MarketDao;
import com.flynava.jupiter.model.BSPModel;
import com.flynava.jupiter.model.BspOverview;
import com.flynava.jupiter.model.CallCenterModel;
import com.flynava.jupiter.model.CallCenterTotalsResponse;
import com.flynava.jupiter.model.FilterModel;
import com.flynava.jupiter.model.MSTotalsResponse;
import com.flynava.jupiter.model.MarketBarometerDetails;
import com.flynava.jupiter.model.MarketBarometerModel;
import com.flynava.jupiter.model.MarketBarometerTotal;
import com.flynava.jupiter.model.MarketOutlookDetails;
import com.flynava.jupiter.model.MarketOutlookDhb;
import com.flynava.jupiter.model.MarketOutlookGrowingResponse;
import com.flynava.jupiter.model.MarketOutlookModel;
import com.flynava.jupiter.model.MarketOutlookReport;
import com.flynava.jupiter.model.MarketOutlookResponse;
import com.flynava.jupiter.model.MarketSummary;
import com.flynava.jupiter.model.MarketSummaryResponse;
import com.flynava.jupiter.model.MonthlyNetworkResponse;
import com.flynava.jupiter.model.MonthlyNetworkResponseModel;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.TopTenAgentsTotalsResponse;
import com.flynava.jupiter.model.TopTenModel;
import com.flynava.jupiter.model.TopTenODTotalsResponse;
import com.flynava.jupiter.serviceInterface.MarketService;
import com.flynava.jupiter.util.ApplicationConstants;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Utility;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class MarketServiceImpl implements MarketService {
	@Autowired
	MarketDao marketDao;

	@Autowired
	MainDashboardDao mainDashboardDao;

	private static final Logger logger = Logger.getLogger(CompetitorAnalysisDaoImpl.class);

	List<MarketOutlookModel> lDeclineList = new ArrayList<MarketOutlookModel>();
	List<MarketOutlookModel> lGrowingList = new ArrayList<MarketOutlookModel>();
	List<MarketOutlookModel> lMatureList = new ArrayList<MarketOutlookModel>();
	List<MarketOutlookModel> lNicheList = new ArrayList<MarketOutlookModel>();

	@Override
	public Map<String, Object> getMarketSummary(RequestModel mRequestModel) {
		// Current year
		Calendar lCalender = Calendar.getInstance(); // Gets the current date
														// and time
		int current_year = lCalender.get(Calendar.YEAR);
		List<FilterModel> lMarketSummaryList = new ArrayList<FilterModel>();
		List<MarketSummaryResponse> lMarketSummaryModelList = new ArrayList<MarketSummaryResponse>();
		List<MSTotalsResponse> lTotalsList = new ArrayList<MSTotalsResponse>();
		Map<String, Object> responseMarketSummaryMap = new HashMap<String, Object>();
		ArrayList<DBObject> lResultListObj = marketDao.getMarketSummary(mRequestModel);
		try {
			if (lResultListObj != null) {
				JSONArray data = new JSONArray(lResultListObj);
				if (data != null) {
					for (int i = 0; i < data.length(); i++) {
						JSONObject lJsonObj = data.getJSONObject(i);
						FilterModel lFilterModel = new FilterModel();
						if (lJsonObj.has("year") && lJsonObj.get("year") != null)
							lFilterModel.setYear(lJsonObj.get("year").toString());
						if (lJsonObj.has("month") && lJsonObj.get("month") != null)
							lFilterModel.setMonth(lJsonObj.get("month").toString());
						if (lJsonObj.has("region") && lJsonObj.get("region") != null)
							lFilterModel.setRegion(lJsonObj.get("region").toString());
						if (lJsonObj.has("country") && lJsonObj.get("country") != null)
							lFilterModel.setCountry(lJsonObj.get("country").toString());
						if (lJsonObj.has("pos") && lJsonObj.get("pos") != null)
							lFilterModel.setPos(lJsonObj.get("pos").toString());
						if (lJsonObj.has("compartment") && lJsonObj.get("compartment") != null)
							lFilterModel.setCompartment(lJsonObj.get("compartment").toString());
						String market_pax = "";
						if (lJsonObj.has("market_pax") && lJsonObj.get("market_pax") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("market_pax").toString())) {
							market_pax = lJsonObj.get("market_pax").toString();
							lFilterModel.setMarket_pax(market_pax);
						} else {
							lFilterModel.setMarket_pax("0");
						}
						String marketpaxlastyr = "";
						if (lJsonObj.has("market_pax_1") && lJsonObj.get("market_pax_1") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("market_pax_1").toString())) {
							marketpaxlastyr = lJsonObj.get("market_pax_1").toString();
							lFilterModel.setMarket_pax_1(marketpaxlastyr);
						} else {
							lFilterModel.setMarket_pax_1("0");
						}
						String market_pax_2 = "";
						if (lJsonObj.has("market_pax_2") && lJsonObj.get("market_pax_2") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("market_pax_2").toString())) {
							market_pax_2 = lJsonObj.get("market_pax_2").toString();
							lFilterModel.setMarket_pax_2(market_pax_2);
						} else {
							lFilterModel.setMarket_pax_2("0.0");
						}
						String market_pax_3 = "";
						if (lJsonObj.has("market_pax_3") && lJsonObj.get("market_pax_3") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("market_pax_3").toString())) {
							market_pax_3 = lJsonObj.get("market_pax_3").toString();
							lFilterModel.setMarket_pax_3(market_pax_3);
						} else {
							lFilterModel.setMarket_pax_3("0.0");
						}
						String market_pax_4 = "";
						if (lJsonObj.has("market_pax_4") && lJsonObj.get("market_pax_4") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("market_pax_4").toString())) {
							market_pax_4 = lJsonObj.get("market_pax_4").toString();
							lFilterModel.setMarket_pax_4(market_pax_4);
						} else {
							lFilterModel.setMarket_pax_4("0.0");
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
								lFilterModel.setFilterKey(lFilterModel.getYear());
								lStr = new StringBuilder();
								lStr.append(lFilterModel.getFilterKey());
							} else {
								lStr.append(lFilterModel.getYear());
							}
						} else if ("region".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lFilterModel.setFilterKey(lFilterModel.getYear() + lFilterModel.getRegion()
										+ lFilterModel.getCountry() + lFilterModel.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lFilterModel.getFilterKey());
							} else {
								lStr.append(lFilterModel.getYear());
							}
						} else if ("country".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lFilterModel.setFilterKey(lFilterModel.getYear() + lFilterModel.getRegion()
										+ lFilterModel.getCountry() + lFilterModel.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lFilterModel.getFilterKey());
							} else {
								lStr.append(lFilterModel.getYear());
							}
						} else if ("pos".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lFilterModel.setFilterKey(
										lFilterModel.getYear() + lFilterModel.getRegion() + lFilterModel.getCountry()
												+ lFilterModel.getPos() + lFilterModel.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lFilterModel.getFilterKey());
							} else {
								lStr.append(lFilterModel.getYear());
							}
						}
						lFilterModel.setFilterKey(lStr.toString());
						System.out.println("lMS " + lFilterModel.getFilterKey());
						lMarketSummaryList.add(lFilterModel);
					}
					Map<String, MarketSummary> map = new HashMap<String, MarketSummary>();
					MarketSummary lMarketSummaryModel = null;
					for (FilterModel lMsObj : lMarketSummaryList) {
						if (!map.containsKey(lMsObj.getFilterKey())) {
							lMarketSummaryModel = new MarketSummary();
							lMarketSummaryModel.setYear((int) Float.parseFloat(lMsObj.getYear()));
							lMarketSummaryModel.setRegion(lMsObj.getRegion());
							lMarketSummaryModel.setCountry(lMsObj.getCountry());
							lMarketSummaryModel.setPos(lMsObj.getPos());
							lMarketSummaryModel.setCompartment(lMsObj.getCompartment());
							// Current year Pax eg. 2016
							lMarketSummaryModel.setMarket_pax((int) Float.parseFloat(lMsObj.getMarket_pax()));
							lMarketSummaryModel.setTotalMarketPax(lMarketSummaryModel.getMarket_pax());
							// 2015
							lMarketSummaryModel.setMarket_pax_1((int) Float.parseFloat(lMsObj.getMarket_pax_1()));
							lMarketSummaryModel.setTotalMarketPax_lastyr(lMarketSummaryModel.getMarket_pax_1());
							// 2014
							lMarketSummaryModel.setMarket_pax_2((int) Float.parseFloat(lMsObj.getMarket_pax_1()));
							lMarketSummaryModel.setTotalMarketPax_2(lMarketSummaryModel.getMarket_pax_1());
							// 2013
							lMarketSummaryModel.setMarket_pax_3((int) Float.parseFloat(lMsObj.getMarket_pax_3()));
							lMarketSummaryModel.setTotalMarketPax_3(lMarketSummaryModel.getMarket_pax_3());
							// 2012
							lMarketSummaryModel.setMarket_pax_4((int) Float.parseFloat(lMsObj.getMarket_pax_4()));
							lMarketSummaryModel.setTotalMarketPax_4(lMarketSummaryModel.getMarket_pax_4());
							map.put(lMsObj.getFilterKey(), lMarketSummaryModel);
						} else {
							for (String lKey : map.keySet()) {
								if (lMsObj.getFilterKey().equals(lKey)) {
									lMarketSummaryModel = map.get(lKey);
								}
							}
							int totalMarketPax = (int) Float.parseFloat(lMsObj.getMarket_pax())
									+ lMarketSummaryModel.getTotalMarketPax();
							lMarketSummaryModel.setTotalMarketPax(totalMarketPax);
							int totalMarketPax_lastyr = (int) Float.parseFloat(lMsObj.getMarket_pax_1())
									+ lMarketSummaryModel.getTotalMarketPax_lastyr();
							lMarketSummaryModel.setTotalMarketPax_lastyr(totalMarketPax_lastyr);
							int totalMarketPax_2 = (int) Float.parseFloat(lMsObj.getMarket_pax_2())
									+ lMarketSummaryModel.getTotalMarketPax_2();
							lMarketSummaryModel.setTotalMarketPax_2(totalMarketPax_2);
							int totalMarketPax_3 = (int) Float.parseFloat(lMsObj.getMarket_pax_3())
									+ lMarketSummaryModel.getTotalMarketPax_3();
							lMarketSummaryModel.setTotalMarketPax_3(totalMarketPax_3);
							int totalMarketPax_4 = (int) Float.parseFloat(lMsObj.getMarket_pax_4())
									+ lMarketSummaryModel.getTotalMarketPax_4();
							lMarketSummaryModel.setTotalMarketPax_4(totalMarketPax_4);
						}
					}
					int totalMarketSize = 0;
					int totalMarketSizelastyr = 0;
					int totalMarketSize_2 = 0;
					int totalMarketSize_3 = 0;
					int totalMarketSize_4 = 0;

					Long days = null;
					if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null) {
						Date fromDate = Utility.convertStringToDateFromat(mRequestModel.getFromDate());
						Date toDate = Utility.convertStringToDateFromat(mRequestModel.getToDate());
						try {
							String lFirstDate = Utility.getFirstDateCurrentMonth(fromDate);
							String lLastDate = Utility.getLastDateCurrentMonth(toDate);
							days = Utility.getDifferenceDays(Utility.convertStringToDateFromat(lFirstDate),
									Utility.convertStringToDateFromat(lLastDate));

						} catch (Exception e) {
							logger.error("getMarketSummary-Exception", e);
						}
					}
					for (String key : map.keySet()) {
						MarketSummaryResponse lMarketResponseModel = new MarketSummaryResponse();
						MarketSummaryResponse lMarketResponseModellastyr = new MarketSummaryResponse();
						MarketSummaryResponse lMarketResponseModel_2 = new MarketSummaryResponse();
						MarketSummaryResponse lMarketResponseModel_3 = new MarketSummaryResponse();
						MarketSummaryResponse lMarketResponseModel_4 = new MarketSummaryResponse();
						int year = 0;
						year = map.get(key).getYear();
						if (year == current_year) {
							lMarketResponseModel.setYear(Integer.toString(year));
							if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
								if (mRequestModel.getRegionArray()[0].toString() != null
										&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
									lMarketResponseModel.setRegion(map.get(key).getRegion());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
								if (mRequestModel.getCountryArray()[0].toString() != null
										&& !mRequestModel.getCountryArray()[0].toString().equals("all")) {
									lMarketResponseModel.setCountry(map.get(key).getCountry());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
								if (mRequestModel.getPosArray()[0].toString() != null
										&& !mRequestModel.getPosArray()[0].toString().equals("all")) {
									lMarketResponseModel.setPos(map.get(key).getPos());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCompartmentArray() != null
									&& mRequestModel.getCompartmentArray().length > 0) {
								if (mRequestModel.getCompartmentArray()[0].toString() != null
										&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
									lMarketResponseModel.setCompartment(map.get(key).getCompartment());
								}
							} else {
								// Do not set anything
							}
						} else {
							lMarketResponseModel.setYear(Integer.toString(current_year));
							if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
								if (mRequestModel.getRegionArray()[0].toString() != null
										&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
									lMarketResponseModel.setRegion(map.get(key).getRegion());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
								if (mRequestModel.getCountryArray()[0].toString() != null
										&& !mRequestModel.getCountryArray()[0].toString().equals("all")) {
									lMarketResponseModel.setCountry(map.get(key).getCountry());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
								if (mRequestModel.getPosArray()[0].toString() != null
										&& !mRequestModel.getPosArray()[0].toString().equals("all")) {
									lMarketResponseModel.setPos(map.get(key).getPos());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCompartmentArray().length > 0) {
								if (mRequestModel.getCompartmentArray()[0].toString() != null
										&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
									lMarketResponseModel.setCompartment(map.get(key).getCompartment());
								}
							} else {
								// Do not set anything
							}
						}
						if (year == (current_year - 1)) {
							lMarketResponseModellastyr.setYear(Integer.toString(year));
							if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
								if (mRequestModel.getRegionArray()[0].toString() != null
										&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
									lMarketResponseModellastyr.setRegion(map.get(key).getRegion());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
								if (mRequestModel.getCountryArray()[0].toString() != null
										&& !mRequestModel.getCountryArray()[0].toString().equals("all")) {
									lMarketResponseModellastyr.setCountry(map.get(key).getCountry());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
								if (mRequestModel.getPosArray()[0].toString() != null
										&& !mRequestModel.getPosArray()[0].toString().equals("all")) {
									lMarketResponseModellastyr.setPos(map.get(key).getPos());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCompartmentArray().length > 0) {
								if (mRequestModel.getCompartmentArray()[0].toString() != null
										&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
									lMarketResponseModellastyr.setCompartment(map.get(key).getCompartment());
								}
							} else {
								// Do not set anything
							}
						} else {
							lMarketResponseModellastyr.setYear(Integer.toString(current_year - 1));
							if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
								if (mRequestModel.getRegionArray()[0].toString() != null
										&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
									lMarketResponseModellastyr.setRegion(map.get(key).getRegion());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
								if (mRequestModel.getCountryArray()[0].toString() != null
										&& !mRequestModel.getCountryArray()[0].toString().equals("all")) {
									lMarketResponseModellastyr.setCountry(map.get(key).getCountry());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
								if (mRequestModel.getPosArray()[0].toString() != null
										&& !mRequestModel.getPosArray()[0].toString().equals("all")) {
									lMarketResponseModellastyr.setPos(map.get(key).getPos());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCompartmentArray() != null
									&& mRequestModel.getCompartmentArray().length > 0) {
								if (mRequestModel.getCompartmentArray()[0].toString() != null
										&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
									lMarketResponseModellastyr.setCompartment(map.get(key).getCompartment());
								}
							} else {
								// Do not set anything
							}
						}
						if (year == (current_year - 2)) {
							lMarketResponseModel_2.setYear(Integer.toString(year));
							if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
								if (mRequestModel.getRegionArray()[0].toString() != null
										&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
									lMarketResponseModel_2.setRegion(map.get(key).getRegion());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
								if (mRequestModel.getCountryArray()[0].toString() != null
										&& !mRequestModel.getCountryArray()[0].toString().equals("all")) {
									lMarketResponseModel_2.setCountry(map.get(key).getCountry());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
								if (mRequestModel.getPosArray()[0].toString() != null
										&& !mRequestModel.getPosArray()[0].toString().equals("all")) {
									lMarketResponseModel_2.setPos(map.get(key).getPos());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCompartmentArray() != null
									&& mRequestModel.getCompartmentArray().length > 0) {
								if (mRequestModel.getCompartmentArray()[0].toString() != null
										&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
									lMarketResponseModel_2.setCompartment(map.get(key).getCompartment());
								}
							} else {
								// Do not set anything
							}
						} else {
							lMarketResponseModel_2.setYear(Integer.toString(current_year - 2));
							if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
								if (mRequestModel.getRegionArray()[0].toString() != null
										&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
									lMarketResponseModel_2.setRegion(map.get(key).getRegion());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
								if (mRequestModel.getCountryArray()[0].toString() != null
										&& !mRequestModel.getCountryArray()[0].toString().equals("all")) {
									lMarketResponseModel_2.setCountry(map.get(key).getCountry());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
								if (mRequestModel.getPosArray()[0].toString() != null
										&& !mRequestModel.getPosArray()[0].toString().equals("all")) {
									lMarketResponseModel_2.setPos(map.get(key).getPos());
								}
							} else {
								// Do not set anything
							}
							if (mRequestModel.getCompartmentArray() != null
									&& mRequestModel.getCompartmentArray().length > 0) {
								if (mRequestModel.getCompartmentArray()[0].toString() != null
										&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
									lMarketResponseModel_2.setCompartment(map.get(key).getCompartment());
								}
							} else {
								// Do not set anything
							}
						}
						if (year == (current_year - 3)) {
							lMarketResponseModel_3.setYear(Integer.toString(year));
							if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
								if (mRequestModel.getRegionArray()[0].toString() != null
										&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
									lMarketResponseModel_3.setRegion(map.get(key).getRegion());
								}
							}
							if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
								if (mRequestModel.getCountryArray()[0].toString() != null
										&& !mRequestModel.getCountryArray()[0].toString().equals("all")) {
									lMarketResponseModel_3.setCountry(map.get(key).getCountry());
								}
							}
							if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
								if (mRequestModel.getPosArray()[0].toString() != null
										&& !mRequestModel.getPosArray()[0].toString().equals("all")) {
									lMarketResponseModel_3.setPos(map.get(key).getPos());
								}
							}
							if (mRequestModel.getCompartmentArray() != null
									&& mRequestModel.getCompartmentArray().length > 0) {
								if (mRequestModel.getCompartmentArray()[0].toString() != null
										&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
									lMarketResponseModel_3.setCompartment(map.get(key).getCompartment());
								}
							}
						} else {
							lMarketResponseModel_3.setYear(Integer.toString(current_year - 3));
							if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
								if (mRequestModel.getRegionArray()[0].toString() != null
										&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
									lMarketResponseModel_3.setRegion(map.get(key).getRegion());
								}
							}
							if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
								if (mRequestModel.getCountryArray()[0].toString() != null
										&& !mRequestModel.getCountryArray()[0].toString().equals("all")) {
									lMarketResponseModel_3.setCountry(map.get(key).getCountry());
								}
							}
							if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
								if (mRequestModel.getPosArray()[0].toString() != null
										&& !mRequestModel.getPosArray()[0].toString().equals("all")) {
									lMarketResponseModel_3.setPos(map.get(key).getPos());
								}
							}
							if (mRequestModel.getCompartmentArray() != null
									&& mRequestModel.getCompartmentArray().length > 0) {
								if (mRequestModel.getCompartmentArray()[0].toString() != null
										&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
									lMarketResponseModel_3.setCompartment(map.get(key).getCompartment());
								}
							}
						}
						// }
						// Computing Market Size
						lMarketResponseModel.setMarketSize(map.get(key).getTotalMarketPax());
						totalMarketSize += map.get(key).getTotalMarketPax();
						lMarketResponseModellastyr.setMarketSize(map.get(key).getTotalMarketPax_lastyr());
						totalMarketSizelastyr += map.get(key).getTotalMarketPax_lastyr();
						lMarketResponseModel_2.setMarketSize(map.get(key).getTotalMarketPax_2());
						totalMarketSize_2 += map.get(key).getTotalMarketPax_2();
						lMarketResponseModel_3.setMarketSize(map.get(key).getTotalMarketPax_3());
						totalMarketSize_3 += map.get(key).getTotalMarketPax_3();
						lMarketResponseModel_4.setMarketSize(map.get(key).getTotalMarketPax_4());
						totalMarketSize_4 += map.get(key).getTotalMarketPax_4();

						// Computing Market Growth
						float marketGrowth = 0;
						float marketGrowth_lastyr = 0;
						float marketGrowth_2 = 0;
						float marketGrowth_3 = 0;

						// Market Growth (Current year)
						if (lMarketResponseModellastyr.getMarketSize() != 0) {
							marketGrowth = CalculationUtil.calculateVLYR(lMarketResponseModel.getMarketSize(),
									lMarketResponseModellastyr.getMarketSize());
						} else {
							marketGrowth = 0;
						}
						if (marketGrowth > 0 || marketGrowth < 0) {
							lMarketResponseModel.setMarketgrowth(Integer.toString(Math.round(marketGrowth)));
						} else {
							lMarketResponseModel.setMarketgrowth("NA");
						}
						// Market Growth last year
						if (lMarketResponseModel_2.getMarketSize() != 0) {
							marketGrowth_lastyr = CalculationUtil.calculateVLYR(
									lMarketResponseModellastyr.getMarketSize(), lMarketResponseModel_2.getMarketSize());
						} else {
							marketGrowth_lastyr = 0;
						}
						if (marketGrowth_lastyr > 0 || marketGrowth_lastyr < 0) {
							lMarketResponseModellastyr
									.setMarketgrowth(Integer.toString(Math.round(marketGrowth_lastyr)));
						} else {
							lMarketResponseModellastyr.setMarketgrowth("NA");
						}
						// Market Growth eg: 2014
						if (lMarketResponseModel_3.getMarketSize() != 0) {
							marketGrowth_2 = CalculationUtil.calculateVLYR(lMarketResponseModel_2.getMarketSize(),
									lMarketResponseModel_3.getMarketSize());
						} else {
							marketGrowth_2 = 0;
						}
						if (marketGrowth_2 > 0 || marketGrowth_2 < 0) {
							lMarketResponseModel_2.setMarketgrowth(Integer.toString(Math.round(marketGrowth_2)));
						} else {
							lMarketResponseModel_2.setMarketgrowth("NA");
						}
						// Market Growth eg: 2013
						if (lMarketResponseModel_4.getMarketSize() != 0) {
							marketGrowth_3 = CalculationUtil.calculateVLYR(lMarketResponseModel_3.getMarketSize(),
									lMarketResponseModel_4.getMarketSize());
						} else {
							marketGrowth_3 = 0;
						}
						if (marketGrowth_3 > 0 || marketGrowth_3 < 0) {
							lMarketResponseModel_3.setMarketgrowth(Integer.toString(Math.round(marketGrowth_2)));
						} else {
							lMarketResponseModel_3.setMarketgrowth("NA");
						}
						// Computing Passenger Per Day
						if (days != null) {
							int passengerPerDay = (int) ((map.get(key).getTotalMarketPax()) / days);
							lMarketResponseModel.setPaxPerDay(passengerPerDay);

							long daysOfYear_1 = Utility
									.getDaysOfYear(Integer.parseInt(lMarketResponseModellastyr.getYear()));
							int passengerPerDaylastyr = (int) (map.get(key).getTotalMarketPax_lastyr() / daysOfYear_1);
							lMarketResponseModellastyr.setPaxPerDay(passengerPerDaylastyr);

							long daysOfYear_2 = Utility
									.getDaysOfYear(Integer.parseInt(lMarketResponseModel_2.getYear()));
							int passengerPerDay_2 = (int) ((map.get(key).getTotalMarketPax_2()) / daysOfYear_2);
							lMarketResponseModel_2.setPaxPerDay(passengerPerDay_2);

							long daysOfYear_3 = Utility
									.getDaysOfYear(Integer.parseInt(lMarketResponseModel_3.getYear()));
							int passengerPerDay_3 = (int) ((map.get(key).getTotalMarketPax_3()) / daysOfYear_3);
							lMarketResponseModel_3.setPaxPerDay(passengerPerDay_3);
						} else {
							lMarketResponseModel.setPaxPerDay(0);
							lMarketResponseModellastyr.setPaxPerDay(0);
							lMarketResponseModel_2.setPaxPerDay(0);
							lMarketResponseModel_3.setPaxPerDay(0);
						}
						// TODO Calculate Overall Network Growth

						lMarketSummaryModelList.add(lMarketResponseModel);
						lMarketSummaryModelList.add(lMarketResponseModellastyr);
						lMarketSummaryModelList.add(lMarketResponseModel_2);
						lMarketSummaryModelList.add(lMarketResponseModel_3);
					}

					responseMarketSummaryMap.put("MarketSummary", lMarketSummaryModelList);

					// Totals---Start
					MSTotalsResponse lModel = new MSTotalsResponse();
					// Computing Complete Market Size
					lModel.setTotalMarketSize(totalMarketSize);
					lModel.setTotalMarketSize_1(totalMarketSizelastyr);
					lModel.setTotalMarketSize_2(totalMarketSize_2);
					lModel.setTotalMarketSize_3(totalMarketSize_3);

					// Computing Market Growth
					float lMarketGrowthVLYR = CalculationUtil.calculateVLYR(totalMarketSize, totalMarketSizelastyr);
					if (lMarketGrowthVLYR > 0) {
						lModel.setTotalMarketgrowth(Integer.toString(Math.round(lMarketGrowthVLYR)));
					} else {
						lModel.setTotalMarketgrowth("0");
					}
					float lMarketGrowthVLYRlastyr = CalculationUtil.calculateVLYR(totalMarketSizelastyr,
							totalMarketSize_2);
					if (lMarketGrowthVLYRlastyr > 0) {
						lModel.setTotalMarketgrowth_1(Integer.toString(Math.round(lMarketGrowthVLYRlastyr)));
					} else {
						lModel.setTotalMarketgrowth_1("0");
					}
					float lMarketGrowthVLYR_2 = CalculationUtil.calculateVLYR(totalMarketSize_2, totalMarketSize_3);
					if (lMarketGrowthVLYR_2 > 0) {
						lModel.setTotalMarketgrowth_2(Integer.toString(Math.round(lMarketGrowthVLYR_2)));
					} else {
						lModel.setTotalMarketgrowth_2("0");
					}
					float lMarketGrowthVLYR_3 = CalculationUtil.calculateVLYR(totalMarketSize_3, totalMarketSize_4);
					if (lMarketGrowthVLYR_3 > 0) {
						lModel.setTotalMarketgrowth_3(Integer.toString(Math.round(lMarketGrowthVLYR_3)));
					} else {
						lModel.setTotalMarketgrowth_3("0");
					}
					// Computing Passenger Per Day
					// completePaxPerDay = completeMarketSize / days;
					if (days != null && days != 0) {
						lModel.setTotalPaxPerDay(totalMarketSize / days);
						lModel.setTotalPaxPerDay_1(totalMarketSizelastyr / days);
						lModel.setTotalPaxPerDay_2(totalMarketSize_2 / days);
						lModel.setTotalPaxPerDay_3(totalMarketSize_3 / days);
					}
					// TODO Compute Total Network Growth
					lTotalsList.add(lModel);
					responseMarketSummaryMap.put("MarketSummaryGraphResponse", lTotalsList);
				}
			} else {
				lMarketSummaryModelList.add(new MarketSummaryResponse());
				responseMarketSummaryMap.put("MarketSummary", lMarketSummaryModelList);
				lTotalsList.add(new MSTotalsResponse());
				responseMarketSummaryMap.put("MarketSummaryGraphResponse", lTotalsList);
			}
		} catch (Exception e) {
			logger.error("getMarketSummary-Exception", e);
		}
		return responseMarketSummaryMap;
	}

	@Override
	public Map<String, Object> getMonthlyNetworkPassengerGrowth(RequestModel mRequestModel) {
		// Current year
		Calendar lCalender = Calendar.getInstance(); // Gets the current date
														// and time
		int current_year = lCalender.get(Calendar.YEAR);
		List<FilterModel> lMarketNetworkList = new ArrayList<FilterModel>();
		List<MonthlyNetworkResponse> lMonthlyGrowthModelList = new ArrayList<MonthlyNetworkResponse>();
		Map<String, Object> responseMarketMonthlyNetworkMap = new HashMap<String, Object>();
		MonthlyNetworkResponseModel lMNResponse = null;
		List<MonthlyNetworkResponseModel> lMNResponseList = new ArrayList<MonthlyNetworkResponseModel>();
		ArrayList<DBObject> lResultObj = marketDao.getMonthlyNetworkPassengerGrowth(mRequestModel);
		if (lResultObj != null) {
			JSONArray data = new JSONArray(lResultObj);
			if (data != null) {
				for (int i = 0; i < data.length(); i++) {
					JSONObject lJsonObj = data.getJSONObject(i);
					FilterModel lFilterModel = new FilterModel();
					if (lJsonObj.has("year") && lJsonObj.get("year") != null)
						lFilterModel.setYear(lJsonObj.get("year").toString());
					if (lJsonObj.has("month") && lJsonObj.get("month") != null)
						lFilterModel.setMonth(lJsonObj.get("month").toString());
					if (lJsonObj.has("region") && lJsonObj.get("region") != null)
						lFilterModel.setRegion(lJsonObj.get("region").toString());
					if (lJsonObj.has("country") && lJsonObj.get("country") != null)
						lFilterModel.setCountry(lJsonObj.get("country").toString());
					if (lJsonObj.has("pos") && lJsonObj.get("pos") != null)
						lFilterModel.setPos(lJsonObj.get("pos").toString());
					if (lJsonObj.has("compartment") && lJsonObj.get("compartment") != null)
						lFilterModel.setCompartment(lJsonObj.get("compartment").toString());
					String market_pax = "0";
					if (lJsonObj.has("pax") && lJsonObj.get("pax") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("pax").toString())) {
						market_pax = lJsonObj.get("pax").toString();
						lFilterModel.setMarket_pax(market_pax);
					} else {
						lFilterModel.setMarket_pax(market_pax);
					}
					String market_paxlastyr = "0";
					if (lJsonObj.has("pax_1") && lJsonObj.get("pax_1") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("pax_1").toString())) {
						market_paxlastyr = lJsonObj.get("pax_1").toString();
						lFilterModel.setMarket_pax_1(market_paxlastyr);
					} else {
						lFilterModel.setMarket_pax_1(market_paxlastyr);
					}
					String market_pax_2 = "0";
					if (lJsonObj.has("pax_2") && lJsonObj.get("pax_2") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("pax_2").toString())) {
						market_pax_2 = lJsonObj.get("pax_2").toString();
						lFilterModel.setMarket_pax_2(market_pax_2);
					} else {
						lFilterModel.setMarket_pax_2(market_pax_2);
					}
					String market_pax_3 = "0";
					if (lJsonObj.has("pax_3") && lJsonObj.get("pax_3") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("pax_3").toString())) {
						market_pax_3 = lJsonObj.get("pax_3").toString();
						lFilterModel.setMarket_pax_3(market_pax_3);
					} else {
						lFilterModel.setMarket_pax_3(market_pax_3);
					}
					String market_pax_4 = "0";
					if (lJsonObj.has("pax_4") && lJsonObj.get("pax_4") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("pax_4").toString())) {
						market_pax_4 = lJsonObj.get("pax_4").toString();
						lFilterModel.setMarket_pax_4(market_pax_4);
					} else {
						lFilterModel.setMarket_pax_4(market_pax_4);
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
							lFilterModel.setFilterKey(lFilterModel.getYear() + lFilterModel.getMonth());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getYear() + lFilterModel.getMonth());
						}
					} else if ("region".equalsIgnoreCase(mRequestModel.getLevel())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(
									lFilterModel.getYear() + lFilterModel.getMonth() + lFilterModel.getRegion());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getYear() + lFilterModel.getMonth());
						}
					} else if ("country".equalsIgnoreCase(mRequestModel.getLevel())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getYear() + lFilterModel.getMonth()
									+ lFilterModel.getRegion() + lFilterModel.getCountry());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getYear() + lFilterModel.getMonth());
						}
					} else if ("pos".equalsIgnoreCase(mRequestModel.getLevel())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getYear() + lFilterModel.getMonth()
									+ lFilterModel.getRegion() + lFilterModel.getCountry() + lFilterModel.getPos());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getYear() + lFilterModel.getMonth());
						}
					}
					lFilterModel.setFilterKey(lStr.toString());
					System.out.println("lMS " + lFilterModel.getFilterKey());
					lMarketNetworkList.add(lFilterModel);
				}
				Map<String, MarketSummary> map = new HashMap<String, MarketSummary>();
				MarketSummary lMarketNetworkModel = null;
				for (FilterModel lMsObj : lMarketNetworkList) {
					if (!map.containsKey(lMsObj.getFilterKey())) {
						lMarketNetworkModel = new MarketSummary();
						lMarketNetworkModel.setYear((int) Float.parseFloat(lMsObj.getYear()));
						lMarketNetworkModel.setMonth(lMsObj.getMonth());
						lMarketNetworkModel.setRegion(lMsObj.getRegion());
						lMarketNetworkModel.setCountry(lMsObj.getCountry());
						lMarketNetworkModel.setPos(lMsObj.getPos());
						lMarketNetworkModel.setCompartment(lMsObj.getCompartment());
						lMarketNetworkModel.setMarket_pax((int) Float.parseFloat(lMsObj.getMarket_pax()));
						lMarketNetworkModel.setTotalMarketPax(lMarketNetworkModel.getMarket_pax());
						lMarketNetworkModel.setMarket_pax_1((int) Float.parseFloat(lMsObj.getMarket_pax_1()));
						lMarketNetworkModel.setTotalMarketPax_lastyr(lMarketNetworkModel.getMarket_pax_1());
						lMarketNetworkModel.setMarket_pax_2((int) Float.parseFloat(lMsObj.getMarket_pax_2()));
						lMarketNetworkModel.setTotalMarketPax_2(lMarketNetworkModel.getMarket_pax_2());
						lMarketNetworkModel.setMarket_pax_3((int) Float.parseFloat(lMsObj.getMarket_pax_3()));
						lMarketNetworkModel.setTotalMarketPax_3(lMarketNetworkModel.getMarket_pax_3());
						lMarketNetworkModel.setMarket_pax_4((int) Float.parseFloat(lMsObj.getMarket_pax_4()));
						lMarketNetworkModel.setTotalMarketPax_4(lMarketNetworkModel.getMarket_pax_4());
						map.put(lMsObj.getFilterKey(), lMarketNetworkModel);
					} else {
						for (String lKey : map.keySet()) {
							if (lMsObj.getFilterKey().equals(lKey)) {
								lMarketNetworkModel = map.get(lKey);
							}
						}
						int totalMarketPax = (int) Float.parseFloat(lMsObj.getMarket_pax())
								+ lMarketNetworkModel.getTotalMarketPax();
						lMarketNetworkModel.setTotalMarketPax(totalMarketPax);
						int totalMarketPax_lastyr = (int) Float.parseFloat(lMsObj.getMarket_pax_1())
								+ lMarketNetworkModel.getTotalMarketPax_lastyr();
						lMarketNetworkModel.setTotalMarketPax_lastyr(totalMarketPax_lastyr);
						int totalMarketPax_2 = (int) Float.parseFloat(lMsObj.getMarket_pax_2())
								+ lMarketNetworkModel.getTotalMarketPax_2();
						lMarketNetworkModel.setTotalMarketPax_2(totalMarketPax_2);
						int totalMarketPax_3 = (int) Float.parseFloat(lMsObj.getMarket_pax_3())
								+ lMarketNetworkModel.getTotalMarketPax_3();
						lMarketNetworkModel.setTotalMarketPax_3(totalMarketPax_3);
						int totalMarketPax_4 = (int) Float.parseFloat(lMsObj.getMarket_pax_4())
								+ lMarketNetworkModel.getTotalMarketPax_4();
						lMarketNetworkModel.setTotalMarketPax_4(totalMarketPax_4);
					}
				}
				for (String key : map.keySet()) {
					MonthlyNetworkResponse lMarketNetwork = new MonthlyNetworkResponse();
					MonthlyNetworkResponse lMarketNetworklastyr = new MonthlyNetworkResponse();
					MonthlyNetworkResponse lMarketNetwork_2 = new MonthlyNetworkResponse();
					MonthlyNetworkResponse lMarketNetwork_3 = new MonthlyNetworkResponse();
					int year = 0;
					year = (map.get(key).getYear());
					if (year == current_year) {
						lMarketNetwork.setYear(Integer.toString(year));
						lMarketNetwork.setRegion(map.get(key).getRegion());
						lMarketNetwork.setCountry(map.get(key).getCountry());
						lMarketNetwork.setPos(map.get(key).getPos());
						lMarketNetwork.setCompartment(map.get(key).getCompartment());
					} else {
						lMarketNetwork.setYear(Integer.toString(current_year));
						lMarketNetwork.setRegion(map.get(key).getRegion());
						lMarketNetwork.setCountry(map.get(key).getCountry());
						lMarketNetwork.setPos(map.get(key).getPos());
						lMarketNetwork.setCompartment(map.get(key).getCompartment());
					}
					if (year == (current_year - 1)) {
						lMarketNetworklastyr.setYear(Integer.toString(year));
						lMarketNetworklastyr.setRegion(map.get(key).getRegion());
						lMarketNetworklastyr.setCountry(map.get(key).getCountry());
						lMarketNetworklastyr.setPos(map.get(key).getPos());
						lMarketNetworklastyr.setCompartment(map.get(key).getCompartment());
					} else {
						lMarketNetworklastyr.setYear(Integer.toString(current_year - 1));
						lMarketNetworklastyr.setRegion(map.get(key).getRegion());
						lMarketNetworklastyr.setCountry(map.get(key).getCountry());
						lMarketNetworklastyr.setPos(map.get(key).getPos());
						lMarketNetworklastyr.setCompartment(map.get(key).getCompartment());
					}
					if (year == (current_year - 2)) {
						lMarketNetwork_2.setYear(Integer.toString(year));
						lMarketNetwork_2.setRegion(map.get(key).getRegion());
						lMarketNetwork_2.setCountry(map.get(key).getCountry());
						lMarketNetwork_2.setPos(map.get(key).getPos());
						lMarketNetwork_2.setCompartment(map.get(key).getCompartment());
					} else {
						lMarketNetwork_2.setYear(Integer.toString(current_year - 2));
						lMarketNetwork_2.setRegion(map.get(key).getRegion());
						lMarketNetwork_2.setCountry(map.get(key).getCountry());
						lMarketNetwork_2.setPos(map.get(key).getPos());
						lMarketNetwork_2.setCompartment(map.get(key).getCompartment());
					}
					if (year == (current_year - 3)) {
						lMarketNetwork_3.setYear(Integer.toString(year));
						lMarketNetwork_3.setRegion(map.get(key).getRegion());
						lMarketNetwork_3.setCountry(map.get(key).getCountry());
						lMarketNetwork_3.setPos(map.get(key).getPos());
						lMarketNetwork_3.setCompartment(map.get(key).getCompartment());
					} else {
						lMarketNetwork_3.setYear(Integer.toString(current_year - 3));
						lMarketNetwork_3.setRegion(map.get(key).getRegion());
						lMarketNetwork_3.setCountry(map.get(key).getCountry());
						lMarketNetwork_3.setPos(map.get(key).getPos());
						lMarketNetwork_3.setCompartment(map.get(key).getCompartment());
					}
					// Computing Pax Growth
					float paxGrowth = 0;
					float paxGrowth_lastyr = 0;
					float paxGrowth_2 = 0;
					float paxGrowth_3 = 0;
					//
					if (map.get(key).getTotalMarketPax_lastyr() > 0) {
						paxGrowth = CalculationUtil.calculateVLYR(map.get(key).getTotalMarketPax(),
								map.get(key).getTotalMarketPax_lastyr());
					} else {
						paxGrowth = 0;
					}
					if (paxGrowth > 0 || paxGrowth < 0) {
						lMarketNetwork.setMonthVLYR(Float.toString(paxGrowth));
					} else {
						lMarketNetwork.setMonthVLYR("0");
					}
					//
					if (map.get(key).getTotalMarketPax_2() > 0) {
						paxGrowth_lastyr = CalculationUtil.calculateVLYR(map.get(key).getTotalMarketPax_lastyr(),
								map.get(key).getTotalMarketPax_2());
					} else {
						paxGrowth_lastyr = 0;
					}
					if (paxGrowth_lastyr > 0 || paxGrowth_lastyr < 0) {
						lMarketNetworklastyr.setMonthVLYR(Float.toString(paxGrowth));
					} else {
						lMarketNetworklastyr.setMonthVLYR("0");
					}
					if (map.get(key).getTotalMarketPax_3() > 0) {
						paxGrowth_2 = CalculationUtil.calculateVLYR(map.get(key).getTotalMarketPax_2(),
								map.get(key).getTotalMarketPax_3());
					} else {
						paxGrowth_2 = 0;
					}
					if (paxGrowth_2 > 0 || paxGrowth_2 < 0) {
						lMarketNetwork_2.setMonthVLYR(Float.toString(paxGrowth_2));
					} else {
						lMarketNetwork_2.setMonthVLYR("0");
					}
					if (map.get(key).getTotalMarketPax_4() > 0) {
						paxGrowth_3 = CalculationUtil.calculateVLYR(map.get(key).getTotalMarketPax_3(),
								map.get(key).getTotalMarketPax_4());
					} else {
						paxGrowth_3 = 0;
					}
					if (paxGrowth_3 > 0 || paxGrowth_3 < 0) {
						lMarketNetwork_3.setMonthVLYR(Float.toString(paxGrowth_3));
					} else {
						lMarketNetwork_3.setMonthVLYR("0");
					}
					if (map.get(key).getMonth().endsWith(".0")) {
						float month = Float.parseFloat(map.get(key).getMonth());
						int lMonth = (int) month;
						lMarketNetwork.setMonth(Month.of(lMonth).name());
						lMarketNetworklastyr.setMonth(Month.of(lMonth).name());
						lMarketNetwork_2.setMonth(Month.of(lMonth).name());
						lMarketNetwork_3.setMonth(Month.of(lMonth).name());
					} else {
						int lMonth = Integer.parseInt(map.get(key).getMonth());
						lMarketNetwork.setMonth(Month.of(lMonth).name());
						lMarketNetworklastyr.setMonth(Month.of(lMonth).name());
						lMarketNetwork_2.setMonth(Month.of(lMonth).name());
						lMarketNetwork_3.setMonth(Month.of(lMonth).name());
					}
					lMonthlyGrowthModelList.add(lMarketNetwork);
					lMonthlyGrowthModelList.add(lMarketNetworklastyr);
					lMonthlyGrowthModelList.add(lMarketNetwork_2);
					lMonthlyGrowthModelList.add(lMarketNetwork_3);
				}
				Collections.sort(lMonthlyGrowthModelList, new MonthComparable());
				Map<String, MonthlyNetworkResponseModel> map2 = new HashMap<String, MonthlyNetworkResponseModel>();
				for (MonthlyNetworkResponse lMoObj2 : lMonthlyGrowthModelList) {
					if (!map2.containsKey(lMoObj2.getYear())) {
						lMNResponse = new MonthlyNetworkResponseModel();
						lMNResponse.setYear(lMoObj2.getYear());
						if ("January".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setJanuary(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setJanuary("0");
						}
						if ("February".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setFebruary(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setFebruary("0");
						}
						if ("March".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setMarch(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setMarch("0");
						}
						if ("April".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setApril(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setApril("0");
						}
						if ("May".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setMay(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setMay("0");
						}
						if ("June".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setJune(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setJune("0");
						}
						if ("July".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setJuly(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setJuly("0");
						}
						if ("August".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setAugust(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setAugust("0");
						}
						if ("September".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setSeptember(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setSeptember("0");
						}
						if ("October".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setOctober(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setOctober("0");
						}
						if ("November".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setNovember(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setNovember("0");
						}
						if ("December".equalsIgnoreCase(lMoObj2.getMonth())) {
							lMNResponse.setDecember(lMoObj2.getMonthVLYR());
						} else {
							lMNResponse.setDecember("0");
						}
						map2.put(lMoObj2.getYear(), lMNResponse);
					} else {
						for (String lKey : map2.keySet()) {
							if (lMoObj2.getYear().equals(lKey)) {
								lMNResponse = map2.get(lKey);
							}
						}
						// lMNResponse.setYear(lMoObj2.getYear());
						if ("January".equalsIgnoreCase(lMoObj2.getMonth())
								&& Integer.parseInt(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setJanuary(lMoObj2.getMonthVLYR());
						}
						if ("February".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setFebruary(lMoObj2.getMonthVLYR());
						}
						if ("March".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setMarch(lMoObj2.getMonthVLYR());
						}
						if ("April".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setApril(lMoObj2.getMonthVLYR());
						}
						if ("May".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setMay(lMoObj2.getMonthVLYR());
						}
						if ("June".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setJune(lMoObj2.getMonthVLYR());
						}
						if ("July".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setJuly(lMoObj2.getMonthVLYR());
						}
						if ("August".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setAugust(lMoObj2.getMonthVLYR());
						}
						if ("September".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setSeptember(lMoObj2.getMonthVLYR());
						}
						if ("October".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setOctober(lMoObj2.getMonthVLYR());
						}
						if ("November".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setNovember(lMoObj2.getMonthVLYR());
						}
						if ("December".equalsIgnoreCase(lMoObj2.getMonth())
								&& Float.parseFloat(lMoObj2.getMonthVLYR()) > 0) {
							lMNResponse.setDecember(lMoObj2.getMonthVLYR());
						}
					}
				}
				MonthlyNetworkResponseModel lResponse = null;
				for (String key : map2.keySet()) {
					lResponse = new MonthlyNetworkResponseModel();
					lResponse.setYear(map2.get(key).getYear());
					lResponse.setJanuary(map2.get(key).getJanuary());
					lResponse.setFebruary(map2.get(key).getFebruary());
					lResponse.setMarch(map2.get(key).getMarch());
					lResponse.setApril(map2.get(key).getApril());
					lResponse.setMay(map2.get(key).getMay());
					lResponse.setJune(map2.get(key).getJune());
					lResponse.setJuly(map2.get(key).getJuly());
					lResponse.setAugust(map2.get(key).getAugust());
					lResponse.setSeptember(map2.get(key).getSeptember());
					lResponse.setOctober(map2.get(key).getOctober());
					lResponse.setNovember(map2.get(key).getNovember());
					lResponse.setDecember(map2.get(key).getDecember());
					lMNResponseList.add(lResponse);
				}
				responseMarketMonthlyNetworkMap.put("MonthlyNetworkPassengerGrowth", lMNResponseList);
			}
		} else {
			lMonthlyGrowthModelList.add(new MonthlyNetworkResponse());
			responseMarketMonthlyNetworkMap.put("MonthlyNetworkPassengerGrowth", lMNResponseList);
		}
		return responseMarketMonthlyNetworkMap;
	}

	public static class MonthComparable implements Comparator<MonthlyNetworkResponse> {
		@Override
		public int compare(MonthlyNetworkResponse arg0, MonthlyNetworkResponse arg1) {
			Integer leftValue = monthValue(arg0.getMonth());
			Integer rightValue = monthValue(arg1.getMonth());
			return leftValue.compareTo(rightValue);
		}

		private static int monthValue(String month) {
			return Month.valueOf(month.toUpperCase()).getValue();
		}
	}

	@Override
	public Map<String, Object> getTopTenAgentByVolume(RequestModel mRequestModel) {
		// Totals
		int totalPaxYTD = 0;
		int totalPaxLastYr = 0;
		int totalVLYR = 0;
		float totalSalesRevenueYTD = 0;
		float totalFlownRevenueYTD = 0;
		float totalFlownRevenueLastYr = 0;
		float totalSalesRevenueLastYr = 0;
		float totalRevVLYR = 0;
		float capacity_TY = 0;
		float capacity_LY = 0;
		List<TopTenAgentsTotalsResponse> lTotalsList = new ArrayList<TopTenAgentsTotalsResponse>();
		int pax_lastyr = 0;
		List<FilterModel> lTopTenModelList = new ArrayList<FilterModel>();
		List<TopTenModel> lTenAgentsList = new ArrayList<TopTenModel>();
		List<TopTenModel> lTopTenAgentsList = new ArrayList<TopTenModel>();
		FilterModel lTopTenModel = new FilterModel();
		Map<String, Object> responseTopTenAgentsMap = new HashMap<String, Object>();
		ArrayList<DBObject> lTopTenAgentsObj = marketDao.getTopTenAgentByVolume(mRequestModel);
		if (!lTopTenAgentsObj.isEmpty()) {
			JSONArray lTopTenAgentsData = new JSONArray(lTopTenAgentsObj);
			try {
				if (lTopTenAgentsData != null) {
					for (int i = 0; i < lTopTenAgentsData.length(); i++) {
						JSONObject lTopTenAgentsJsonObj = lTopTenAgentsData.getJSONObject(i);
						lTopTenModel = new FilterModel();
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.YEAR)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.YEAR) != null
								&& !"null".equalsIgnoreCase(
										lTopTenAgentsJsonObj.get(ApplicationConstants.YEAR).toString())) {
							lTopTenModel.setYear(lTopTenAgentsJsonObj.get(ApplicationConstants.YEAR).toString());
						} else {
							lTopTenModel.setYear(Integer.toString(Utility.getCurrentYear()));
						}
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.MONTH)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.MONTH) != null
								&& !"null".equalsIgnoreCase(
										lTopTenAgentsJsonObj.get(ApplicationConstants.MONTH).toString())) {
							lTopTenModel.setMonth(lTopTenAgentsJsonObj.get(ApplicationConstants.MONTH).toString());
						}
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.REGION)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.REGION) != null
								&& !"null".equalsIgnoreCase(
										lTopTenAgentsJsonObj.get(ApplicationConstants.REGION).toString())) {
							lTopTenModel.setRegion(lTopTenAgentsJsonObj.get(ApplicationConstants.REGION).toString());
						}
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.COUNTRY)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.COUNTRY) != null
								&& !"null".equalsIgnoreCase(
										lTopTenAgentsJsonObj.get(ApplicationConstants.COUNTRY).toString())) {
							lTopTenModel.setCountry(lTopTenAgentsJsonObj.get(ApplicationConstants.COUNTRY).toString());
						}
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.POS)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.POS) != null
								&& !"null".equalsIgnoreCase(
										lTopTenAgentsJsonObj.get(ApplicationConstants.POS).toString())) {
							lTopTenModel.setPos(lTopTenAgentsJsonObj.get(ApplicationConstants.POS).toString());
						}
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.COMPARTMENT)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.COMPARTMENT) != null
								&& !"null".equalsIgnoreCase(
										lTopTenAgentsJsonObj.get(ApplicationConstants.COMPARTMENT).toString())) {
							lTopTenModel.setCompartment(
									lTopTenAgentsJsonObj.get(ApplicationConstants.COMPARTMENT).toString());
						}
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.AGENT)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.AGENT) != null
								&& !"null".equalsIgnoreCase(
										lTopTenAgentsJsonObj.get(ApplicationConstants.AGENT).toString())) {
							lTopTenModel
									.setAgent(lTopTenAgentsJsonObj.get(ApplicationConstants.AGENT).toString().trim());
						}
						if (lTopTenAgentsJsonObj.has("pax") && lTopTenAgentsJsonObj.get("pax") != null
								&& !"null".equalsIgnoreCase(lTopTenAgentsJsonObj.get("pax").toString())) {
							lTopTenModel.setHostPax(lTopTenAgentsJsonObj.get("pax").toString());
						}
						if (lTopTenAgentsJsonObj.has("pax_1") && lTopTenAgentsJsonObj.get("pax_1") != null
								&& !"null".equalsIgnoreCase(lTopTenAgentsJsonObj.get("pax_1").toString())) {
							JSONArray lPaxLYArray = new JSONArray(lTopTenAgentsJsonObj.get("pax_1").toString());
							Double lPaxLY = Utility.findSum(lPaxLYArray);
							lTopTenModel.setHostPax_lastyr(Double.toString(lPaxLY));
						} else {
							lTopTenModel.setHostPax_lastyr("0");
						}
						if (lTopTenAgentsJsonObj.has("revenue") && lTopTenAgentsJsonObj.get("revenue") != null
								&& !"null".equalsIgnoreCase(lTopTenAgentsJsonObj.get("revenue").toString())) {
							lTopTenModel.setSalesRevenue(lTopTenAgentsJsonObj.get("revenue").toString());
						}
						if (lTopTenAgentsJsonObj.has("revenue_1") && lTopTenAgentsJsonObj.get("revenue_1") != null
								&& !"null".equalsIgnoreCase(lTopTenAgentsJsonObj.get("revenue_1").toString())) {
							JSONArray lRevenueLYArray = new JSONArray(lTopTenAgentsJsonObj.get("revenue_1").toString());
							Double lRevenueLY = Utility.findSum(lRevenueLYArray);
							lTopTenModel.setSalesRevenue_lastyr(Double.toString(lRevenueLY));
						} else {
							lTopTenModel.setSalesRevenue_lastyr("0");
						}

						JSONArray lFlownPaxArray;
						if (lTopTenAgentsJsonObj.has("flown_pax") && lTopTenAgentsJsonObj.get("flown_pax") != null
								&& !"null".equalsIgnoreCase(lTopTenAgentsJsonObj.get("flown_pax").toString())
								&& !"[]".equalsIgnoreCase(lTopTenAgentsJsonObj.get("flown_pax").toString())) {
							lFlownPaxArray = new JSONArray(lTopTenAgentsJsonObj.get("flown_pax").toString());
							Double flownPax = Utility.findSum(lFlownPaxArray);
							lTopTenModel.setFlownPax(flownPax.toString());
						} else {
							lTopTenModel.setFlownPax("0");
						}

						JSONArray lFlownPaxLatsYrArray;
						if (lTopTenAgentsJsonObj.has("flown_pax_1") && lTopTenAgentsJsonObj.get("flown_pax_1") != null
								&& !"null".equalsIgnoreCase(lTopTenAgentsJsonObj.get("flown_pax_1").toString())
								&& !"[]".equalsIgnoreCase(lTopTenAgentsJsonObj.get("flown_pax_1").toString())) {
							lFlownPaxLatsYrArray = new JSONArray(lTopTenAgentsJsonObj.get("flown_pax_1").toString());
							Double flownPaxLastYr = Utility.findSum(lFlownPaxLatsYrArray);
							lTopTenModel.setFlownPax_lastyr(flownPaxLastYr.toString());
						} else {
							lTopTenModel.setFlownPax_lastyr("0");
						}

						JSONArray lFlownRevenueArray;
						if (lTopTenAgentsJsonObj.has("flown_revenue")
								&& lTopTenAgentsJsonObj.get("flown_revenue") != null
								&& !"null".equalsIgnoreCase(lTopTenAgentsJsonObj.get("flown_revenue").toString())
								&& !"[]".equalsIgnoreCase(lTopTenAgentsJsonObj.get("flown_revenue").toString())) {
							lFlownRevenueArray = new JSONArray(lTopTenAgentsJsonObj.get("flown_revenue").toString());
							Double flownRevenue = Utility.findSum(lFlownRevenueArray);
							lTopTenModel.setFlownRevenue(flownRevenue.toString());
						} else {
							lTopTenModel.setFlownRevenue("0");
						}
						JSONArray lFlownRevenuelastyrArray = null;
						if (lTopTenAgentsJsonObj.has("flown_revenue_1")
								&& lTopTenAgentsJsonObj.get("flown_revenue_1") != null
								&& !"null".equalsIgnoreCase(lTopTenAgentsJsonObj.get("flown_revenue_1").toString())
								&& !"[]".equalsIgnoreCase(lTopTenAgentsJsonObj.get("flown_revenue_1").toString())) {
							lFlownRevenueArray = new JSONArray(lTopTenAgentsJsonObj.get("flown_revenue_1").toString());
							Double flownRevenue_lastyr = Utility.findSum(lFlownRevenuelastyrArray);
							lTopTenModel.setFlownRevenue_lastyr(flownRevenue_lastyr.toString());
						} else {
							lTopTenModel.setFlownRevenue_lastyr("0");
						}

						JSONArray lForecastPaxArray = null;
						if (lTopTenAgentsJsonObj.has("forecast_pax") && lTopTenAgentsJsonObj.get("forecast_pax") != null
								&& !"null".equalsIgnoreCase(lTopTenAgentsJsonObj.get("forecast_pax").toString())
								&& !"[]".equalsIgnoreCase(lTopTenAgentsJsonObj.get("forecast_pax").toString())) {
							lForecastPaxArray = new JSONArray(lTopTenAgentsJsonObj.get("forecast_pax").toString());
							Double forecastPax = Utility.findSum(lForecastPaxArray);
							lTopTenModel.setHostPaxForecast(forecastPax.toString());
						} else {
							lTopTenModel.setHostPaxForecast("0");
						}

						JSONArray lForecastRevenueArray = null;
						if (lTopTenAgentsJsonObj.has("forecast_revenue")
								&& lTopTenAgentsJsonObj.get("forecast_revenue") != null
								&& !"null".equalsIgnoreCase(lTopTenAgentsJsonObj.get("forecast_revenue").toString())
								&& !"[]".equalsIgnoreCase(lTopTenAgentsJsonObj.get("forecast_revenue").toString())) {
							lForecastRevenueArray = new JSONArray(
									lTopTenAgentsJsonObj.get("forecast_revenue").toString());
							Double forecastRevenue = Utility.findSum(lForecastRevenueArray);
							lTopTenModel.setRevenueForecast(forecastRevenue.toString());
						} else {
							lTopTenModel.setRevenueForecast("0");
						}

						// Capacity Carrier
						JSONArray lCapacityCarrierArray = null;
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.CAPACITY_CARRIER)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_CARRIER) != null
								&& !lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_CARRIER).toString()
										.equalsIgnoreCase("null")
								&& !lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_CARRIER).toString()
										.equalsIgnoreCase("[]")) {
							lCapacityCarrierArray = new JSONArray(
									lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_CARRIER).toString());
						} else {
							lCapacityCarrierArray = null;
						}

						// Capacity Frequency
						JSONArray lCapacityFrequencyArray = null;
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.CAPACITY_FREQUENCY)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_FREQUENCY) != null
								&& !lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_FREQUENCY).toString()
										.equalsIgnoreCase("null")
								&& !lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_FREQUENCY).toString()
										.equalsIgnoreCase("[]")) {
							lCapacityFrequencyArray = new JSONArray(
									lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_FREQUENCY).toString());
						} else {
							lCapacityFrequencyArray = null;
						}

						// Capacity
						JSONArray lCapacityArray = null;
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.CAPACITY)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY) != null
								&& !lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY).toString()
										.equalsIgnoreCase("null")
								&& !lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY).toString()
										.equalsIgnoreCase("[]")) {
							lCapacityArray = new JSONArray(
									lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY).toString());
						} else {
							lCapacityArray = null;
						}

						JSONArray lCapacityLYArray = null;
						if (lTopTenAgentsJsonObj.has(ApplicationConstants.CAPACITY_LY)
								&& lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_LY) != null
								&& !lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_LY).toString()
										.equalsIgnoreCase("null")
								&& !lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_LY).toString()
										.equalsIgnoreCase("[]")) {
							lCapacityLYArray = new JSONArray(
									lTopTenAgentsJsonObj.get(ApplicationConstants.CAPACITY_LY).toString());
						} else {
							lCapacityLYArray = null;
						}

						float capacityFZ = 0;
						float capacityFZ_LY = 0;

						if (lCapacityCarrierArray != null) {
							if (lCapacityCarrierArray.length() > 0) {
								for (int j = 0; j < lCapacityCarrierArray.length(); j++) {
									if (lCapacityArray.length() > 0 && lCapacityLYArray.length() > 0) {
										if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("FZ")) {
											capacityFZ += Float.parseFloat(lCapacityArray.get(j).toString());
											capacityFZ_LY += Float.parseFloat(lCapacityLYArray.get(j).toString());
										}
									}
								}
								lTopTenModel.setCapacityFZ(capacityFZ);
								lTopTenModel.setCapacityLY_FZ(capacityFZ_LY);

							}
						} else {
							lTopTenModel.setCapacityFZ(capacityFZ);
							lTopTenModel.setCapacityLY_FZ(capacityFZ_LY);

						}

						StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lTopTenModel);
						String keyBuilder = lStr.toString();
						if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
							keyBuilder = "null";
						}
						if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(lTopTenModel.getAgent());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getAgent());
							}
						} else if ("region".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(lTopTenModel.getRegion() + lTopTenModel.getAgent());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getAgent());
							}
						} else if ("country".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(
										lTopTenModel.getRegion() + lTopTenModel.getCountry() + lTopTenModel.getAgent());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getAgent());
							}
						} else if ("pos".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(lTopTenModel.getRegion() + lTopTenModel.getCountry()
										+ lTopTenModel.getPos() + lTopTenModel.getAgent());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getAgent());
							}
						}
						lTopTenModel.setFilterKey(lStr.toString());
						System.out.println("lMS " + lTopTenModel.getFilterKey());
						lTopTenModelList.add(lTopTenModel);
					}
					Map<String, TopTenModel> map = new HashMap<String, TopTenModel>();
					TopTenModel lTopTenAgents = null;
					if (lTopTenModelList.size() > 0) {
						for (FilterModel lObj : lTopTenModelList) {
							if (!map.containsKey(lObj.getFilterKey())) {
								lTopTenAgents = new TopTenModel();
								lTopTenAgents.setYear(lObj.getYear());
								lTopTenAgents.setMonth(lObj.getMonth());
								lTopTenAgents.setCombinationKey(lObj.getFilterKey());
								lTopTenAgents.setRegion(lObj.getRegion());
								lTopTenAgents.setCountry(lObj.getCountry());
								lTopTenAgents.setPos(lObj.getPos());
								lTopTenAgents.setOrigin(lObj.getOrigin());
								lTopTenAgents.setDestination(lObj.getDestination());
								lTopTenAgents.setCompartment(lObj.getCompartment());
								// Agent
								lTopTenAgents.setAgent(lObj.getAgent());
								// Pax
								int totalPax = Integer.parseInt(lObj.getHostPax());
								lTopTenAgents.setTotalPax(totalPax);
								pax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
								if (pax_lastyr > 0) {
									lTopTenAgents.setPax_lastyr(pax_lastyr);
								}
								lTopTenAgents.setTotalPax_lastyr(pax_lastyr);
								// Flown Pax
								float flownpax = Float.parseFloat(lObj.getFlownPax());
								float flownpaxlastyr = Float.parseFloat(lObj.getFlownPax_lastyr());
								lTopTenAgents.setTotalflownpax(flownpax);
								lTopTenAgents.setTotalflownpaxlastyr(flownpaxlastyr);
								// Sales Revenue
								float salesRevenue = Float.parseFloat(lObj.getSalesRevenue());
								float salesRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr());
								lTopTenAgents.setTotalSalesRevenue(salesRevenue);
								lTopTenAgents.setTotalSalesRevenue_lastyr(salesRevenue_lastyr);
								// Sales Revenue
								float flownRevenue = Float.parseFloat(lObj.getFlownRevenue());
								float flownRevenue_lastyr = Float.parseFloat(lObj.getFlownRevenue_lastyr());
								lTopTenAgents.setTotalFlownRevenue(flownRevenue);
								lTopTenAgents.setTotalFlownRevenue_lastyr(flownRevenue_lastyr);
								// Capacity
								lTopTenAgents.setCapacity(lObj.getCapacityFZ());
								lTopTenAgents.setCapacity_LY(lObj.getCapacityLY_FZ());
								map.put(lObj.getFilterKey(), lTopTenAgents);
							} else {
								for (String lKey : map.keySet()) {
									if (lObj.getFilterKey().equals(lKey)) {
										lTopTenAgents = map.get(lKey);
									}
								}
								// Pax
								int totalPax = Integer.parseInt(lObj.getHostPax()) + lTopTenAgents.getTotalPax();
								lTopTenAgents.setTotalPax(totalPax);
								int totalpax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
								lTopTenAgents.setTotalPax_lastyr(totalpax_lastyr + lTopTenAgents.getTotalPax_lastyr());
								// Flown Pax
								float flownpax = Float.parseFloat(lObj.getFlownPax())
										+ lTopTenAgents.getTotalflownpax();
								float flownpaxlastyr = Float.parseFloat(lObj.getFlownPax_lastyr())
										+ lTopTenAgents.getTotalflownpaxlastyr();
								lTopTenAgents.setTotalflownpax(flownpax);
								lTopTenAgents.setTotalflownpaxlastyr(flownpaxlastyr);
								// Sales Revenue
								float totalSalesRevenue = Float.parseFloat(lObj.getSalesRevenue())
										+ lTopTenAgents.getTotalSalesRevenue();
								lTopTenAgents.setTotalSalesRevenue(totalSalesRevenue);
								float totalSalesRevenue_lastyr = Float.parseFloat(lObj.getSalesRevenue_lastyr())
										+ lTopTenAgents.getTotalSalesRevenue_lastyr();
								lTopTenAgents.setTotalSalesRevenue_lastyr(totalSalesRevenue_lastyr);
								// Flown Revenue
								float totalFlownRevenue = Float.parseFloat(lObj.getFlownRevenue())
										+ lTopTenAgents.getTotalFlownRevenue();
								lTopTenAgents.setTotalFlownRevenue(totalFlownRevenue);
								float totalFlownRevenue_lastyr = Float.parseFloat(lObj.getFlownRevenue_lastyr())
										+ lTopTenAgents.getTotalFlownRevenue_lastyr();
								lTopTenAgents.setTotalFlownRevenue_lastyr(totalFlownRevenue_lastyr);

								// Capacity
								float capacityFZ = lObj.getCapacityFZ() + lTopTenAgents.getCapacity();
								lTopTenAgents.setCapacity(capacityFZ);
								float capacityLY_FZ = lObj.getCapacityLY_FZ() + lTopTenAgents.getCapacity_LY();
								lTopTenAgents.setCapacity_LY(capacityLY_FZ);

							}
						}
					}
					for (String key : map.keySet()) {
						lTopTenAgents = new TopTenModel();
						lTopTenAgents.setCombinationKey(map.get(key).getCombinationKey());
						lTopTenAgents.setYear(map.get(key).getYear());
						lTopTenAgents.setMonth(map.get(key).getMonth());
						lTopTenAgents.setRegion(map.get(key).getRegion());
						lTopTenAgents.setCountry(map.get(key).getCountry());
						lTopTenAgents.setPos(map.get(key).getPos());
						lTopTenAgents.setCompartment(map.get(key).getCompartment());
						lTopTenAgents.setAgent(map.get(key).getAgent());
						int totalPax = map.get(key).getTotalPax();
						int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
						float totalflownrevenue = map.get(key).getTotalFlownRevenue();
						float totalflownrevenuelastyr = map.get(key).getTotalFlownRevenue_lastyr();
						float totalflownpax = map.get(key).getTotalflownpax();
						float totalflownpaxlastyr = map.get(key).getTotalflownpaxlastyr();
						// Pax
						lTopTenAgents.setPaxYTD(totalPax);
						lTopTenAgents.setTotalPax(totalPax);
						lTopTenAgents.setTotalPax_lastyr(totalPax_lastyr);
						float lpaxVLYR = 0;
						if (totalflownpax > 0) {
							lpaxVLYR = CalculationUtil.calculateVLYR(totalflownpax, totalflownpaxlastyr,
									map.get(key).getCapacity(), map.get(key).getCapacity_LY());
						}
						if (lpaxVLYR != 0) {
							lTopTenAgents.setPaxVLYR(Float.toString(lpaxVLYR));
						} else {
							lTopTenAgents.setPaxVLYR("NA");
						}
						// Sales Revenue
						float totalSalesRevenue = map.get(key).getTotalSalesRevenue();
						float totalSalesRevenue_lastyr = map.get(key).getTotalSalesRevenue_lastyr();
						lTopTenAgents.setSalesRevenueYTD(totalSalesRevenue);
						lTopTenAgents.setTotalSalesRevenue_lastyr(totalSalesRevenue_lastyr);
						float lRevenueVLYR = 0;
						// Flown Revenue
						float totalFlownRevenue = map.get(key).getTotalFlownRevenue();
						lTopTenAgents.setFlownRevenueYTD(totalFlownRevenue);
						float totalFlownRevenue_lastyr = map.get(key).getTotalFlownRevenue_lastyr();
						lTopTenAgents.setFlownRevenue_lastyr(totalFlownRevenue_lastyr);
						lRevenueVLYR = 0;
						if (totalflownrevenue > 0) {
							lRevenueVLYR = CalculationUtil.calculateVLYR(totalflownrevenue, totalflownrevenuelastyr,
									map.get(key).getCapacity(), map.get(key).getCapacity_LY());
						}
						if (lRevenueVLYR != 0) {
							lTopTenAgents.setRevenueVLYR(Float.toString(lRevenueVLYR));
						} else {
							lTopTenAgents.setRevenueVLYR("NA");
						}
						lTenAgentsList.add(lTopTenAgents);
					}
				}
				Collections.sort(lTenAgentsList, new TopTenComp());
				if (lTenAgentsList.size() > 10) {
					lTopTenAgentsList.addAll(lTenAgentsList.subList(0, 10));
				} else {
					lTopTenAgentsList.addAll(lTenAgentsList.subList(0, lTenAgentsList.size()));
				}

				responseTopTenAgentsMap.put("TopTenAgents", lTopTenAgentsList);

				/* Computing totals ---Start */
				for (TopTenModel lObj : lTopTenAgentsList) {
					totalPaxYTD += lObj.getPaxYTD();
					totalPaxLastYr += lObj.getTotalPax_lastyr();
					totalFlownRevenueYTD += lObj.getFlownRevenueYTD();
					totalSalesRevenueYTD += lObj.getSalesRevenueYTD();
					totalSalesRevenueLastYr += lObj.getTotalSalesRevenue_lastyr();
					totalFlownRevenueLastYr += lObj.getTotalFlownRevenue_lastyr();
					capacity_TY += lObj.getCapacity();
					capacity_LY += lObj.getCapacity_LY();
				}
				if (totalPaxLastYr > 0) {
					totalVLYR = (int) CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxLastYr, capacity_TY,
							capacity_LY);
				} else {
					totalVLYR = 0;
				}
				if (totalFlownRevenueLastYr > 0) {
					totalRevVLYR = (int) CalculationUtil.calculateVLYR(totalFlownRevenueYTD, totalFlownRevenueLastYr,
							capacity_TY, capacity_LY);
				} else if (totalSalesRevenueLastYr > 0) {
					totalRevVLYR = (int) CalculationUtil.calculateVLYR(totalSalesRevenueYTD, totalSalesRevenueLastYr,
							capacity_TY, capacity_LY);
				} else {
					totalRevVLYR = 0;
				}
				TopTenAgentsTotalsResponse lTotals = new TopTenAgentsTotalsResponse();
				lTotals.setTotalPaxYTD(Integer.toString(totalPaxYTD));
				if (totalPaxLastYr > 0) {
					lTotals.setTotalPaxVLYR(Integer.toString(totalVLYR));
				} else {
					lTotals.setTotalPaxVLYR("NA");
				}
				lTotals.setTotalSalesRevenueYTD(Float.toString(totalSalesRevenueYTD));
				lTotals.setTotalFlownRevenueYTD(Float.toString(totalFlownRevenueYTD));
				if (totalRevVLYR > 0) {
					lTotals.setTotalRevenueVLYR(Float.toString(totalRevVLYR));
				} else {
					lTotals.setTotalRevenueVLYR("NA");
				}
				lTotalsList.add(lTotals);
				/* Computing totals ---End */

				responseTopTenAgentsMap.put("TopTenAgentsTotals", lTotalsList);
			} catch (Exception e) {
				logger.error("getTopTenAgentByVolume-Exception", e);
			}
		} else {
			responseTopTenAgentsMap.put("TopTenAgentsTotals", null);
			responseTopTenAgentsMap.put("TopTenAgents", null);
		}
		return responseTopTenAgentsMap;
	}

	class TopTenComp implements Comparator<TopTenModel> {
		@Override
		public int compare(TopTenModel arg0, TopTenModel arg1) {

			if (arg0.getPaxYTD() > 0) {
				if (arg0.getPaxYTD() < arg1.getPaxYTD()) {
					return 1;
				} else {
					return -1;
				}
			}
			return 0;
		}
	}

	@Override
	public Map<String, Object> getTopTenOdSpikes(RequestModel mRequestModel) {
		// Totals
		int totalPaxYTD = 0;
		int totalPaxLastYr = 0;
		int totalPaxTarget = 0;
		int totalVLYR = 0;
		int totalVTGT = 0;
		float lCapacitylastyr = 0;
		List<TopTenODTotalsResponse> lTotalsList = new ArrayList<TopTenODTotalsResponse>();
		List<FilterModel> lTopTenModelList = new ArrayList<FilterModel>();
		List<TopTenModel> lODSpikeList = new ArrayList<TopTenModel>();
		List<TopTenModel> lTopTenODSpikeList = new ArrayList<TopTenModel>();
		FilterModel lTopTenModel = new FilterModel();
		JSONArray paxlastyrarray = null;
		Double paxlastyr = 0D;
		Double flownPax = 0D;
		Double flownPax_lastyr = 0D;
		JSONArray lCapacityArray = null;
		JSONArray lCapacityCarrierArray = null;
		JSONArray lCapacitylastyrArray = null;
		Map<String, Float> carrierPaxMap = new HashMap<String, Float>();
		Map<String, Float> capacitylastyrMap = new HashMap<String, Float>();
		Map<String, Object> responseTopTenODSPikesMap = new HashMap<String, Object>();
		ArrayList<DBObject> lTopTenOdSpikesObj = marketDao.getTopTenOdSpikes(mRequestModel);
		if (!lTopTenOdSpikesObj.isEmpty()) {
			JSONArray lTopTenOdSpikesData = new JSONArray(lTopTenOdSpikesObj);
			try {
				if (lTopTenOdSpikesData != null) {
					for (int i = 0; i < lTopTenOdSpikesData.length(); i++) {
						JSONObject lTopTenODSpikesJsonObj = lTopTenOdSpikesData.getJSONObject(i);
						lTopTenModel = new FilterModel();
						if (lTopTenODSpikesJsonObj.has("month") && lTopTenODSpikesJsonObj.get("month") != null
								&& !"month".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("month").toString())) {
							lTopTenModel.setMonth(lTopTenODSpikesJsonObj.get("month").toString());
						}
						if (lTopTenODSpikesJsonObj.has("region") && lTopTenODSpikesJsonObj.get("region") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("region").toString())) {
							lTopTenModel.setRegion(lTopTenODSpikesJsonObj.get("region").toString());
						}
						if (lTopTenODSpikesJsonObj.has("country") && lTopTenODSpikesJsonObj.get("country") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("country").toString())) {
							lTopTenModel.setCountry(lTopTenODSpikesJsonObj.get("country").toString());
						}
						if (lTopTenODSpikesJsonObj.has("pos") && lTopTenODSpikesJsonObj.get("pos") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("pos").toString())) {
							lTopTenModel.setPos(lTopTenODSpikesJsonObj.get("pos").toString());
						}
						if (lTopTenODSpikesJsonObj.has("od") && lTopTenODSpikesJsonObj.get("od") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("od").toString())) {
							lTopTenModel.setOd(lTopTenODSpikesJsonObj.get("od").toString());
						}
						if (lTopTenODSpikesJsonObj.has("compartment")
								&& lTopTenODSpikesJsonObj.get("compartment") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("compartment").toString())) {
							lTopTenModel.setCompartment(lTopTenODSpikesJsonObj.get("compartment").toString());
						}
						if (lTopTenODSpikesJsonObj.has("pax") && lTopTenODSpikesJsonObj.get("pax") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("pax").toString())) {
							lTopTenModel.setHostPax(lTopTenODSpikesJsonObj.get("pax").toString());
						}
						if (lTopTenODSpikesJsonObj.has("pax_1") && lTopTenODSpikesJsonObj.get("pax_1") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("pax_1").toString())
								&& !"[]".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("pax_1").toString())) {
							paxlastyrarray = new JSONArray(lTopTenODSpikesJsonObj.get("pax_1").toString());
							if (paxlastyrarray != null) {
								if (paxlastyrarray.length() > 0) {
									paxlastyr = Utility.findSum(paxlastyrarray);
									lTopTenModel.setHostPax_lastyr(paxlastyr.toString());
								}
							}
						} else {
							lTopTenModel.setHostPax_lastyr("0");
						}
						JSONArray lFlownPaxArray;
						if (lTopTenODSpikesJsonObj.has("flown_pax") && lTopTenODSpikesJsonObj.get("flown_pax") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("flown_pax").toString())
								&& !"[]".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("flown_pax").toString())) {
							lFlownPaxArray = new JSONArray(lTopTenODSpikesJsonObj.get("flown_pax").toString());
							flownPax = Utility.findSum(lFlownPaxArray);
							lTopTenModel.setFlownPax(flownPax.toString());
						} else {
							lTopTenModel.setFlownPax("0");
						}
						JSONArray lFlownPaxlastyrArray = null;
						if (lTopTenODSpikesJsonObj.has("flown_pax_1")
								&& lTopTenODSpikesJsonObj.get("flown_pax_1") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("flown_pax_1").toString())
								&& !"[]".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("flown_pax_1").toString())) {
							lFlownPaxlastyrArray = new JSONArray(lTopTenODSpikesJsonObj.get("flown_pax_1").toString());
							flownPax_lastyr = Utility.findSum(lFlownPaxlastyrArray);
							lTopTenModel.setFlownPax_lastyr(flownPax_lastyr.toString());
						} else {
							lTopTenModel.setFlownPax_lastyr("0");
						}
						// Capacity Carrier
						double lCapacityCarrier = 0;
						if (lTopTenODSpikesJsonObj.has("capacity_airline")
								&& lTopTenODSpikesJsonObj.get("capacity_airline") != null
								&& !lTopTenODSpikesJsonObj.get("capacity_airline").toString().equalsIgnoreCase("null")
								&& !lTopTenODSpikesJsonObj.get("capacity_airline").toString().equalsIgnoreCase("[]")) {
							lCapacityCarrierArray = new JSONArray(
									lTopTenODSpikesJsonObj.get("capacity_airline").toString());
						} else {
							lCapacityCarrierArray = null;
						}
						if (lTopTenODSpikesJsonObj.has("capacity_1") && lTopTenODSpikesJsonObj.get("capacity_1") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("capacity_1").toString())
								&& !"[]".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("capacity_1").toString())) {
							lCapacitylastyrArray = new JSONArray(lTopTenODSpikesJsonObj.get("capacity_1").toString());
						}
						if (lCapacitylastyrArray != null) {
							for (int m = 0; m < lCapacitylastyrArray.length(); m++) {
								if (!"null".equalsIgnoreCase(lCapacitylastyrArray.get(m).toString()))
									lCapacitylastyr += Float.parseFloat(lCapacitylastyrArray.get(m).toString());
							}
						}

						// Capacity
						double lCapacity = 0;
						if (lTopTenODSpikesJsonObj.has("capacity") && lTopTenODSpikesJsonObj.get("capacity") != null
								&& !lTopTenODSpikesJsonObj.get("capacity").toString().equalsIgnoreCase("null")
								&& !lTopTenODSpikesJsonObj.get("capacity").toString().equalsIgnoreCase("[]")) {
							lCapacityArray = new JSONArray(lTopTenODSpikesJsonObj.get("capacity").toString());
						} else {
							lCapacityArray = null;
						}

						for (int k = 0; k < lCapacityCarrierArray.length(); k++) {
							carrierPaxMap.put(lCapacityCarrierArray.getString(k), (float) lCapacityArray.getDouble(k));
						}
						for (int k = 0; k < lCapacityCarrierArray.length(); k++) {
							capacitylastyrMap.put(lCapacityCarrierArray.getString(k),
									(float) lCapacitylastyrArray.getDouble(k));
						}
						JSONArray lTargetPaxArray;
						if (lTopTenODSpikesJsonObj.has("target_pax") && lTopTenODSpikesJsonObj.get("target_pax") != null
								&& !"null".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("target_pax").toString())
								&& !"[]".equalsIgnoreCase(lTopTenODSpikesJsonObj.get("target_pax").toString())) {
							lTargetPaxArray = new JSONArray(lTopTenODSpikesJsonObj.get("target_pax").toString());
							Double targetPax = Utility.findSum(lTargetPaxArray);
							lTopTenModel.setTargetPax(targetPax.toString());
						} else {
							lTopTenModel.setTargetPax("0");
						}
						StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lTopTenModel);
						String keyBuilder = lStr.toString();
						if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
							keyBuilder = "null";
						}
						if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(lTopTenModel.getOd());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getOd());
							}
						} else if ("region".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(lTopTenModel.getOd() + lTopTenModel.getRegion());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getOd());
							}
						} else if ("country".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(
										lTopTenModel.getOd() + lTopTenModel.getRegion() + lTopTenModel.getCountry());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getOd());
							}
						} else if ("pos".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(lTopTenModel.getOd() + lTopTenModel.getRegion()
										+ lTopTenModel.getCountry() + lTopTenModel.getPos());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getOd());
							}
						}
						lTopTenModel.setFilterKey(lStr.toString());
						System.out.println("lMS " + lTopTenModel.getFilterKey());
						lTopTenModelList.add(lTopTenModel);
					}
					Map<String, TopTenModel> map = new HashMap<String, TopTenModel>();
					TopTenModel lTopTenODSpikes = null;
					if (lTopTenModelList.size() > 0) {
						for (FilterModel lObj : lTopTenModelList) {
							if (!map.containsKey(lObj.getFilterKey())) {
								lTopTenODSpikes = new TopTenModel();
								lTopTenODSpikes.setCombinationKey(lObj.getFilterKey());
								lTopTenODSpikes.setMonth(lObj.getMonth());
								lTopTenODSpikes.setRegion(lObj.getRegion());
								lTopTenODSpikes.setCountry(lObj.getCountry());
								lTopTenODSpikes.setPos(lObj.getPos());
								lTopTenODSpikes.setOd(lObj.getOd());
								lTopTenODSpikes.setCompartment(lObj.getCompartment());
								int totalFlownPax = (int) Float.parseFloat(lObj.getFlownPax());
								int totalSalesPax = Integer.parseInt(lObj.getHostPax());
								if (totalFlownPax > 0) {
									lTopTenODSpikes.setTotalPax(totalFlownPax);
								} else {
									lTopTenODSpikes.setTotalPax(totalSalesPax);
								}
								int flownPaxlastyr = (int) Float.parseFloat(lObj.getFlownPax_lastyr());
								int salesPax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
								if (flownPax_lastyr > 0) {
									lTopTenODSpikes.setTotalPax_lastyr(flownPaxlastyr);
								} else {
									lTopTenODSpikes.setTotalPax_lastyr(salesPax_lastyr);
								}
								lTopTenODSpikes.setTargetPax((int) Float.parseFloat(lObj.getTargetPax()));
								map.put(lObj.getFilterKey(), lTopTenODSpikes);
							} else {
								for (String lKey : map.keySet()) {
									if (lObj.getFilterKey().equals(lKey)) {
										lTopTenODSpikes = map.get(lKey);
									}
								}
								// Pax
								int totalFlownPax = (int) Float.parseFloat(lObj.getFlownPax());
								int totalSalesPax = Integer.parseInt(lObj.getHostPax());
								int pax = 0;
								if (totalFlownPax > 0) {
									pax = totalFlownPax;
								} else {
									pax = totalSalesPax;
								}
								int flownPaxlastyr = (int) Float.parseFloat(lObj.getFlownPax_lastyr());
								int salesPax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
								int pax_lastyr = 0;
								if (flownPax_lastyr > 0) {
									pax_lastyr = flownPaxlastyr;
								} else {
									pax_lastyr = salesPax_lastyr;
								}
								int totalPax = pax + lTopTenODSpikes.getTotalPax();
								lTopTenODSpikes.setTotalPax(totalPax);
								int totalpax_lastyr = pax_lastyr + lTopTenODSpikes.getTotalPax_lastyr();
								lTopTenODSpikes.setTotalPax_lastyr(totalpax_lastyr);
							}
						}
					}
					for (String key : map.keySet()) {
						lTopTenODSpikes = new TopTenModel();
						lTopTenODSpikes.setCombinationKey(map.get(key).getCombinationKey());
						lTopTenODSpikes.setMonth(map.get(key).getMonth());
						if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
							if (mRequestModel.getRegionArray()[0].toString() != null
									&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
								lTopTenODSpikes.setRegion(map.get(key).getRegion());
							}
						}
						if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
							if (mRequestModel.getCountryArray()[0].toString() != null
									&& !mRequestModel.getCountryArray()[0].toString().equals("all")) {
								lTopTenODSpikes.setCountry(map.get(key).getCountry());
							}
						}
						if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
							if (mRequestModel.getPosArray()[0].toString() != null
									&& !mRequestModel.getPosArray()[0].toString().equals("all")) {
								lTopTenODSpikes.setPos(map.get(key).getPos());
							}
						}
						if (mRequestModel.getCompartmentArray() != null
								&& mRequestModel.getCompartmentArray().length > 0) {
							if (mRequestModel.getCompartmentArray()[0].toString() != null
									&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
								lTopTenODSpikes.setCompartment(map.get(key).getCompartment());
							}
						}
						lTopTenODSpikes.setOd(map.get(key).getOd());
						int totalPax = map.get(key).getTotalPax();
						int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
						lTopTenODSpikes.setPaxYTD(totalPax);
						lTopTenODSpikes.setTotalPax_lastyr(totalPax_lastyr);
						lTopTenODSpikes.setTargetPax(map.get(key).getTargetPax());

						int lPaxVTGT = 0;
						if (map.get(key).getTargetPax() > 0) {
							lPaxVTGT = (int) CalculationUtil.calculateVTGT(totalPax, map.get(key).getTargetPax());
						}

						String host = "FZ";
						float hostcapacity = 0;

						if (carrierPaxMap.containsKey(host)) {
							hostcapacity = (carrierPaxMap.get(host));
							lTopTenODSpikes.setHostcapacity(hostcapacity);
						}
						float hostcapacitylastyr = 0;
						if (carrierPaxMap.containsKey(host)) {
							hostcapacitylastyr = (capacitylastyrMap.get(host));
							lTopTenODSpikes.setHostcapacitylastyr(hostcapacitylastyr);
						}
						float lFlownpax = map.get(key).getTotalPax();
						lTopTenODSpikes.setFlownpax(lFlownpax);
						float lFlownpaxlastyr = map.get(key).getTotalPax_lastyr();
						lTopTenODSpikes.setFlownpaxlastyr(lFlownpaxlastyr);
						int lpaxVLYR = 0;
						if (lFlownpax > 0) {
							lpaxVLYR = (int) CalculationUtil.calculateVLYR(lFlownpax, lFlownpaxlastyr, hostcapacity,
									hostcapacitylastyr);
						} else {
							lpaxVLYR = 0;
						}

						if (lpaxVLYR > 0) {
							lTopTenODSpikes.setPaxVLYR(Integer.toString(lpaxVLYR));
						} else {
							lTopTenODSpikes.setPaxVLYR("NA");
						}
						lTopTenODSpikes.setPaxVTGT(lPaxVTGT);
						lODSpikeList.add(lTopTenODSpikes);
					}
				}
				Collections.sort(lODSpikeList, new TopTenComp());
				if (lODSpikeList.size() > 10) {
					lTopTenODSpikeList.addAll(lODSpikeList.subList(0, 10));
				} else {
					lTopTenODSpikeList.addAll(lODSpikeList.subList(0, lODSpikeList.size()));
				}

				/* Computing totals ---Start */
				float totalflown = 0;
				float totalflownlastyr = 0;
				float hostcapacity = 0;
				float hostcapacitylastyr = 0;

				for (TopTenModel lObj : lTopTenODSpikeList) {
					totalPaxYTD += lObj.getPaxYTD();
					totalPaxLastYr += lObj.getTotalPax_lastyr();
					totalPaxTarget += lObj.getTargetPax();
					totalflown += lObj.getFlownpax();
					totalflownlastyr += lObj.getFlownpaxlastyr();
					hostcapacity += lObj.getHostcapacity();
					hostcapacitylastyr += lObj.getHostcapacitylastyr();
				}
				if (totalPaxLastYr > 0) {
					totalVLYR = (int) CalculationUtil.calculateVLYR(totalflown, totalflownlastyr, hostcapacity,
							hostcapacitylastyr);
				} else {
					totalVLYR = 0;
				}
				if (totalPaxTarget > 0) {
					totalVTGT = (int) CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxTarget);
				} else {
					totalVTGT = 0;
				}
				TopTenODTotalsResponse lTotals = new TopTenODTotalsResponse();
				lTotals.setTotalPaxYTD(Integer.toString(totalPaxYTD));
				if (totalVLYR != 0) {
					lTotals.setTotalPaxVLYR(Integer.toString(totalVLYR));
				} else {
					lTotals.setTotalPaxVLYR("0");
				}
				if (totalVTGT != 0) {
					lTotals.setTotalPaxVTGT(Integer.toString(totalVTGT));
				} else {
					lTotals.setTotalPaxVTGT("0");
				}
				lTotalsList.add(lTotals);
				/* Computing totals ---End */
			} catch (Exception e) {
				logger.error("getTopTenOdSpikes-Exception", e);
			}
			responseTopTenODSPikesMap.put("TopTenODSpikesTotals", lTotalsList);
			responseTopTenODSPikesMap.put("TopTenODSpikes", lTopTenODSpikeList);
		} else {
			responseTopTenODSPikesMap.put("TopTenODSpikesTotals", null);
			responseTopTenODSPikesMap.put("TopTenODSpikes", null);
		}
		return responseTopTenODSPikesMap;
	}

	@Override
	public Map<String, Object> getTopTenCountryMarket(RequestModel mRequestModel) {
		// Totals
		int totalPaxYTD = 0;
		int totalPaxLastYr = 0;
		int totalPaxTarget = 0;
		int totalVLYR = 0;
		int totalVTGT = 0;
		List<TopTenODTotalsResponse> lTotalsList = new ArrayList<TopTenODTotalsResponse>();
		List<FilterModel> lTopTenModelList = new ArrayList<FilterModel>();
		List<TopTenModel> lCountryMarketsList = new ArrayList<TopTenModel>();
		List<TopTenModel> lTopTenCountryList = new ArrayList<TopTenModel>();
		FilterModel lTopTenModel = new FilterModel();
		Map<String, Object> responseTopTenCountryMarketMap = new HashMap<String, Object>();
		ArrayList<DBObject> lTopTenCountryMarketObj = marketDao.getTopTenCountryMarket(mRequestModel);
		if (!lTopTenCountryMarketObj.isEmpty()) {
			JSONArray lTopTenCountryMarketData = new JSONArray(lTopTenCountryMarketObj);
			try {
				if (lTopTenCountryMarketData != null) {
					for (int i = 0; i < lTopTenCountryMarketData.length(); i++) {
						JSONObject lTopTenCountryMarketJsonObj = lTopTenCountryMarketData.getJSONObject(i);
						lTopTenModel = new FilterModel();
						if (lTopTenCountryMarketJsonObj.has("month") && lTopTenCountryMarketJsonObj.get("month") != null
								&& !"month".equalsIgnoreCase(lTopTenCountryMarketJsonObj.get("month").toString())) {
							lTopTenModel.setMonth(lTopTenCountryMarketJsonObj.get("month").toString());
						}
						if (lTopTenCountryMarketJsonObj.has("region")
								&& lTopTenCountryMarketJsonObj.get("region") != null
								&& !"null".equalsIgnoreCase(lTopTenCountryMarketJsonObj.get("region").toString())) {
							lTopTenModel.setRegion(lTopTenCountryMarketJsonObj.get("region").toString());
						}
						if (lTopTenCountryMarketJsonObj.has("country")
								&& lTopTenCountryMarketJsonObj.get("country") != null
								&& !"null".equalsIgnoreCase(lTopTenCountryMarketJsonObj.get("country").toString())) {
							lTopTenModel.setCountry(lTopTenCountryMarketJsonObj.get("country").toString());
						}
						if (lTopTenCountryMarketJsonObj.has("compartment")
								&& lTopTenCountryMarketJsonObj.get("compartment") != null && !"null"
										.equalsIgnoreCase(lTopTenCountryMarketJsonObj.get("compartment").toString())) {
							lTopTenModel.setCompartment(lTopTenCountryMarketJsonObj.get("compartment").toString());
						}
						if (lTopTenCountryMarketJsonObj.has("sale_pax")
								&& lTopTenCountryMarketJsonObj.get("sale_pax") != null
								&& !"null".equalsIgnoreCase(lTopTenCountryMarketJsonObj.get("sale_pax").toString())) {
							lTopTenModel.setHostPax(lTopTenCountryMarketJsonObj.get("sale_pax").toString());
						} else {
							lTopTenModel.setHostPax("0");
						}
						if (lTopTenCountryMarketJsonObj.has("sale_pax_1")
								&& lTopTenCountryMarketJsonObj.get("sale_pax_1") != null
								&& !"null".equalsIgnoreCase(lTopTenCountryMarketJsonObj.get("sale_pax_1").toString())) {
							lTopTenModel.setHostPax_lastyr(lTopTenCountryMarketJsonObj.get("sale_pax_1").toString());
						} else {
							lTopTenModel.setHostPax_lastyr("0");
						}
						if (lTopTenCountryMarketJsonObj.has("flown_pax")
								&& lTopTenCountryMarketJsonObj.get("flown_pax") != null
								&& !"null".equalsIgnoreCase(lTopTenCountryMarketJsonObj.get("flown_pax").toString())) {
							lTopTenModel.setFlownPax(lTopTenCountryMarketJsonObj.get("flown_pax").toString());
						} else {
							lTopTenModel.setFlownPax("0");
						}
						if (lTopTenCountryMarketJsonObj.has("flown_pax_1")
								&& lTopTenCountryMarketJsonObj.get("flown_pax_1") != null && !"null"
										.equalsIgnoreCase(lTopTenCountryMarketJsonObj.get("flown_pax_1").toString())) {
							lTopTenModel.setFlownPax_lastyr(lTopTenCountryMarketJsonObj.get("flown_pax_1").toString());
						} else {
							lTopTenModel.setFlownPax_lastyr("0");
						}
						JSONArray lTargetPaxArray;
						if (lTopTenCountryMarketJsonObj.has("target_pax")
								&& lTopTenCountryMarketJsonObj.get("target_pax") != null
								&& !"null".equalsIgnoreCase(lTopTenCountryMarketJsonObj.get("target_pax").toString())
								&& !"[]".equalsIgnoreCase(lTopTenCountryMarketJsonObj.get("target_pax").toString())) {
							lTargetPaxArray = new JSONArray(lTopTenCountryMarketJsonObj.get("target_pax").toString());
							Double targetPax = Utility.findSum(lTargetPaxArray);
							lTopTenModel.setTargetPax(Double.toString(targetPax));
						} else {
							lTopTenModel.setTargetPax("0");
						}
						StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lTopTenModel);
						String keyBuilder = lStr.toString();
						if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
							keyBuilder = "null";
						}
						if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(lTopTenModel.getCountry());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getCountry());
							}
						} else if ("region".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(lTopTenModel.getRegion() + lTopTenModel.getCountry());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getCountry());
							}
						} else if ("country".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(lTopTenModel.getCountry());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getCountry());
							}
						} else if ("pos".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopTenModel.setFilterKey(lTopTenModel.getCountry());
								lStr = new StringBuilder();
								lStr.append(lTopTenModel.getFilterKey());
							} else {
								lStr.append(lTopTenModel.getCountry());
							}
						}
						lTopTenModel.setFilterKey(lStr.toString());
						System.out.println("lMS " + lTopTenModel.getFilterKey());
						lTopTenModelList.add(lTopTenModel);
					}
					Map<String, TopTenModel> map = new HashMap<String, TopTenModel>();
					TopTenModel lTopTenCountryMarket = null;
					if (lTopTenModelList.size() > 0) {
						for (FilterModel lObj : lTopTenModelList) {
							if (!map.containsKey(lObj.getFilterKey())) {
								lTopTenCountryMarket = new TopTenModel();
								lTopTenCountryMarket.setCombinationKey(lObj.getFilterKey());
								lTopTenCountryMarket.setMonth(lObj.getMonth());
								lTopTenCountryMarket.setRegion(lObj.getRegion());
								lTopTenCountryMarket.setCountry(lObj.getCountry());
								lTopTenCountryMarket.setPos(lObj.getPos());
								// Pax
								int totalFlownPax = (int) Float.parseFloat(lObj.getFlownPax());
								int totalSalesPax = (int) Float.parseFloat(lObj.getHostPax());
								if (totalFlownPax > 0) {
									lTopTenCountryMarket.setTotalPax(totalFlownPax);
								} else {
									lTopTenCountryMarket.setTotalPax(totalSalesPax);
								}
								int flownPax_lastyr = (int) Float.parseFloat(lObj.getFlownPax_lastyr());
								int salesPax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
								if (flownPax_lastyr > 0) {
									lTopTenCountryMarket.setTotalPax_lastyr(flownPax_lastyr);
								} else {
									lTopTenCountryMarket.setTotalPax_lastyr(salesPax_lastyr);
								}
								lTopTenCountryMarket.setTargetPax(Math.round(Float.parseFloat(lObj.getTargetPax())));
								map.put(lObj.getFilterKey(), lTopTenCountryMarket);
							} else {
								for (String lKey : map.keySet()) {
									if (lObj.getFilterKey().equals(lKey)) {
										lTopTenCountryMarket = map.get(lKey);
									}
								}
								// Pax
								int totalFlownPax = (int) Float.parseFloat(lObj.getFlownPax());
								int totalSalesPax = (int) Float.parseFloat(lObj.getHostPax());
								int pax = 0;
								if (totalFlownPax > 0) {
									pax = totalFlownPax;
								} else {
									pax = totalSalesPax;
								}
								int flownPax_lastyr = (int) Float.parseFloat(lObj.getFlownPax_lastyr());
								int salesPax_lastyr = (int) Float.parseFloat(lObj.getHostPax_lastyr());
								int pax_lastyr = 0;
								if (flownPax_lastyr > 0) {
									pax_lastyr = flownPax_lastyr;
								} else {
									pax_lastyr = salesPax_lastyr;
								}
								int totalPax = pax + lTopTenCountryMarket.getTotalPax();
								lTopTenCountryMarket.setTotalPax(totalPax);
								int totalpax_lastyr = pax_lastyr + lTopTenCountryMarket.getTotalPax_lastyr();
								lTopTenCountryMarket.setTotalPax_lastyr(totalpax_lastyr);
							}
						}
					}
					for (String key : map.keySet()) {
						lTopTenCountryMarket = new TopTenModel();
						lTopTenCountryMarket.setCombinationKey(map.get(key).getCombinationKey());
						lTopTenCountryMarket.setMonth(map.get(key).getMonth());
						if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
							if (mRequestModel.getRegionArray()[0].toString() != null
									&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
								lTopTenCountryMarket.setRegion(map.get(key).getRegion());
							}
						}
						lTopTenCountryMarket.setCountry(map.get(key).getCountry());
						if (mRequestModel.getCompartmentArray() != null
								&& mRequestModel.getCompartmentArray().length > 0) {
							if (mRequestModel.getCompartmentArray()[0].toString() != null
									&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
								lTopTenCountryMarket.setCompartment(map.get(key).getCompartment());
							}
						}
						int totalPax = map.get(key).getTotalPax();
						int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
						lTopTenCountryMarket.setPaxYTD(totalPax);
						lTopTenCountryMarket.setTotalPax(totalPax);
						lTopTenCountryMarket.setTotalPax_lastyr(totalPax_lastyr);
						int lpaxVLYR = 0;
						if (totalPax_lastyr > 0) {
							lpaxVLYR = (int) CalculationUtil.calculateVLYR(totalPax, totalPax_lastyr);
						} else {
							lpaxVLYR = 0;
						}
						int lPaxVTGT = 0;
						if (map.get(key).getTargetPax() > 0) {
							lPaxVTGT = (int) CalculationUtil.calculateVTGT(totalPax, map.get(key).getTargetPax());
						}
						if (lpaxVLYR != 0) {
							lTopTenCountryMarket.setPaxVLYR(Integer.toString(lpaxVLYR));
						} else {
							lTopTenCountryMarket.setPaxVLYR("NA");
						}
						if (lPaxVTGT != 0) {
							lTopTenCountryMarket.setPaxVTGT(lPaxVTGT);
						} else {
							lTopTenCountryMarket.setPaxVTGT(0);
						}
						lTopTenCountryMarket.setTargetPax(map.get(key).getTargetPax());
						lCountryMarketsList.add(lTopTenCountryMarket);
					}
				}
				Collections.sort(lCountryMarketsList, new TopTenComp());
				if (lCountryMarketsList.size() >= 10) {
					lTopTenCountryList.addAll(lCountryMarketsList.subList(0, 10));
				} else {
					lTopTenCountryList.addAll(lCountryMarketsList.subList(0, lCountryMarketsList.size()));
				}
				/* Computing totals ---Start */
				for (TopTenModel lObj : lTopTenCountryList) {
					totalPaxYTD += lObj.getPaxYTD();
					totalPaxLastYr += lObj.getTotalPax_lastyr();
					totalPaxTarget = lObj.getTargetPax();
				}
				if (totalPaxLastYr > 0) {
					totalVLYR = (int) CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxLastYr);
				} else {
					totalVLYR = 0;
				}
				if (totalPaxTarget > 0) {
					totalVTGT = (int) CalculationUtil.calculateVTGT(totalPaxYTD, totalPaxTarget);
				} else {
					totalVTGT = 0;
				}
				TopTenODTotalsResponse lTotals = new TopTenODTotalsResponse();
				lTotals.setTotalPaxYTD(Integer.toString(totalPaxYTD));
				if (totalVLYR != 0) {
					lTotals.setTotalPaxVLYR(Integer.toString(totalVLYR));
				} else {
					lTotals.setTotalPaxVLYR("NA");
				}
				if (totalPaxTarget != 0) {
					lTotals.setTotalPaxVTGT(Integer.toString(totalVTGT));
				} else {
					lTotals.setTotalPaxVTGT("NA");
				}
				lTotalsList.add(lTotals);
				/* Computing totals ---End */
				responseTopTenCountryMarketMap.put("TopTenCountryMarketsTotals", lTotalsList);
				responseTopTenCountryMarketMap.put("TopTenCountryMarkets", lTopTenCountryList);
			} catch (Exception e) {
				logger.error("getTopTenCountryMarket-Exception", e);
			}
		} else {
			responseTopTenCountryMarketMap.put("TopTenCountryMarketsTotals", null);
			responseTopTenCountryMarketMap.put("TopTenCountryMarkets", null);
		}
		return responseTopTenCountryMarketMap;
	}

	@Override
	public Map<String, Object> getCallCenterSalesGrowth(RequestModel mRequestModel) {
		// Totals
		float totalRevenueYTD = 0;
		float totalRevLastYr = 0;
		float totalRevTarget = 0;
		float totalVLYR = 0;
		float totalVTGT = 0;

		List<FilterModel> lCallCenterGrowthList = new ArrayList<FilterModel>();
		List<CallCenterModel> lCallCenterSalesList = new ArrayList<CallCenterModel>();
		List<CallCenterTotalsResponse> lTotalsList = new ArrayList<CallCenterTotalsResponse>();
		FilterModel lCallCenterModel = new FilterModel();
		Map<String, Object> responseCallCenterMap = new HashMap<String, Object>();
		ArrayList<DBObject> lCallCenterObj = marketDao.getCallCenterSalesGrowth(mRequestModel);
		if (!lCallCenterObj.isEmpty()) {
			JSONArray lCallCenterData = new JSONArray(lCallCenterObj);
			try {
				if (lCallCenterData != null) {
					for (int i = 0; i < lCallCenterData.length(); i++) {
						JSONObject lCallCenterJsonObj = lCallCenterData.getJSONObject(i);
						lCallCenterModel = new FilterModel();
						if (lCallCenterJsonObj.has("region") && lCallCenterJsonObj.get("region") != null
								&& !"null".equalsIgnoreCase(lCallCenterJsonObj.get("region").toString())) {
							lCallCenterModel.setRegion(lCallCenterJsonObj.get("region").toString());
						}
						if (lCallCenterJsonObj.has("country") && lCallCenterJsonObj.get("country") != null
								&& !"null".equalsIgnoreCase(lCallCenterJsonObj.get("country").toString())) {
							lCallCenterModel.setCountry(lCallCenterJsonObj.get("country").toString());
						}
						if (lCallCenterJsonObj.has("pos") && lCallCenterJsonObj.get("pos") != null
								&& !"null".equalsIgnoreCase(lCallCenterJsonObj.get("pos").toString())) {
							lCallCenterModel.setPos(lCallCenterJsonObj.get("pos").toString());
						}
						if (lCallCenterJsonObj.has("compartment") && lCallCenterJsonObj.get("compartment") != null
								&& !"null".equalsIgnoreCase(lCallCenterJsonObj.get("compartment").toString())) {
							lCallCenterModel.setCompartment(lCallCenterJsonObj.get("compartment").toString());
						}
						String saleRevStr = lCallCenterJsonObj.get("sale_revenue").toString();
						if (lCallCenterJsonObj.has("sale_revenue") && lCallCenterJsonObj.get("sale_revenue") != null
								&& !"null".equalsIgnoreCase(saleRevStr)) {
							lCallCenterModel.setSalesRevenue(saleRevStr);
						}
						String saleRevlastyrStr = lCallCenterJsonObj.get("sale_revenue_1").toString();
						if (lCallCenterJsonObj.has("sale_revenue_1") && lCallCenterJsonObj.get("sale_revenue_1") != null
								&& !"null".equalsIgnoreCase(saleRevlastyrStr)) {
							lCallCenterModel.setSalesRevenue_lastyr(saleRevlastyrStr);
						}
						String flownRevStr = lCallCenterJsonObj.get("flown_revenue").toString();
						if (lCallCenterJsonObj.has("flown_revenue") && lCallCenterJsonObj.get("flown_revenue") != null
								&& !"null".equalsIgnoreCase(flownRevStr)) {
							lCallCenterModel.setFlownRevenue(flownRevStr);
						}
						String flownRevLastyrStr = lCallCenterJsonObj.get("flown_revenue_1").toString();
						if (lCallCenterJsonObj.has("flown_revenue_1")
								&& lCallCenterJsonObj.get("flown_revenue_1") != null
								&& !"null".equalsIgnoreCase(flownRevLastyrStr)) {
							lCallCenterModel.setFlownRevenue_lastyr(flownRevLastyrStr);
						}
						JSONArray lTargetRevenueArray;
						if (lCallCenterJsonObj.has("target_revenue") && lCallCenterJsonObj.get("target_revenue") != null
								&& !"null".equalsIgnoreCase(lCallCenterJsonObj.get("target_revenue").toString())
								&& !"[]".equalsIgnoreCase(lCallCenterJsonObj.get("target_revenue").toString())) {
							lTargetRevenueArray = new JSONArray(lCallCenterJsonObj.get("target_revenue").toString());
							Double targetRevenue = Utility.findSum(lTargetRevenueArray);
							lCallCenterModel.setTargetRevenue(targetRevenue.toString());
						} else {
							lCallCenterModel.setTargetRevenue("0");
						}
						StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lCallCenterModel);
						String keyBuilder = lStr.toString();
						if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
							keyBuilder = "null";
						}
						if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lCallCenterModel.setFilterKey(lCallCenterModel.getRegion());
								lStr = new StringBuilder();
								lStr.append(lCallCenterModel.getFilterKey());
							} else {
								lStr.append(lCallCenterModel.getCompartment());
							}
						} else if ("region".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lCallCenterModel
										.setFilterKey(lCallCenterModel.getRegion() + lCallCenterModel.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lCallCenterModel.getFilterKey());
							} else {
								lStr.append(lCallCenterModel.getCompartment());
							}
						} else if ("country".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lCallCenterModel.setFilterKey(lCallCenterModel.getRegion()
										+ lCallCenterModel.getCountry() + lCallCenterModel.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lCallCenterModel.getFilterKey());
							} else {
								lStr.append(lCallCenterModel.getCompartment());
							}
						} else if ("pos".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lCallCenterModel
										.setFilterKey(lCallCenterModel.getRegion() + lCallCenterModel.getCountry()
												+ lCallCenterModel.getPos() + lCallCenterModel.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lCallCenterModel.getFilterKey());
							} else {
								lStr.append(lCallCenterModel.getCompartment());
							}
						}
						lCallCenterModel.setFilterKey(lStr.toString());
						System.out.println("lCallCenterModel-Key====" + lCallCenterModel.getFilterKey());
						lCallCenterGrowthList.add(lCallCenterModel);
					}
					Map<String, CallCenterModel> map = new HashMap<String, CallCenterModel>();
					CallCenterModel lCallCenter = null;
					if (lCallCenterGrowthList.size() > 0) {
						for (FilterModel lObj : lCallCenterGrowthList) {
							if (!map.containsKey(lObj.getFilterKey())) {
								lCallCenter = new CallCenterModel();
								lCallCenter.setCombinationKey(lObj.getFilterKey());
								lCallCenter.setRegion(lObj.getRegion());
								lCallCenter.setCountry(lObj.getCountry());
								lCallCenter.setPos(lObj.getPos());
								lCallCenter.setCompartment(lObj.getCompartment());
								String flownRevenue = lObj.getFlownRevenue();
								String flownRevenue_lastyr = lObj.getFlownRevenue_lastyr();
								String salesRevenue = lObj.getSalesRevenue();
								String salesRevenue_lastyr = lObj.getSalesRevenue_lastyr();
								if (flownRevenue.equals("0")) {
									lCallCenter.setTotalRevenue(Float.parseFloat(salesRevenue));
								} else {
									lCallCenter.setTotalRevenue(Float.parseFloat(flownRevenue));
								}
								if (flownRevenue_lastyr.equals("0")) {
									lCallCenter.setTotalRevenue_lastyr(Float.parseFloat(salesRevenue_lastyr));
								} else {
									lCallCenter.setTotalRevenue_lastyr(Float.parseFloat(flownRevenue_lastyr));
								}
								lCallCenter.setTargetRevenue(Float.parseFloat(lObj.getTargetRevenue()));
								map.put(lObj.getFilterKey(), lCallCenter);
							} else {
								for (String lKey : map.keySet()) {
									if (lKey == null) {
										lKey = "-";
									}
									if (lObj.getFilterKey().equals(lKey)) {
										lCallCenter = map.get(lKey);
									}
								}
								String flownRevenue = lObj.getFlownRevenue();
								String flownRevenue_lastyr = lObj.getFlownRevenue_lastyr();
								String salesRevenue = lObj.getSalesRevenue();
								String salesRevenue_lastyr = lObj.getSalesRevenue_lastyr();
								if (flownRevenue.equals("0")) {
									float totalRevenue = Float.parseFloat(salesRevenue) + lCallCenter.getTotalRevenue();
									lCallCenter.setTotalRevenue(totalRevenue);
								} else {
									float totalRevenue = Float.parseFloat(flownRevenue) + lCallCenter.getTotalRevenue();
									lCallCenter.setTotalRevenue(totalRevenue);
								}
								if (flownRevenue_lastyr.equals("0")) {
									float totalRevenue_lastyr = Float.parseFloat(salesRevenue_lastyr)
											+ lCallCenter.getTotalRevenue_lastyr();
									lCallCenter.setTotalRevenue_lastyr(totalRevenue_lastyr);
								} else {
									float totalRevenue_lastyr = Float.parseFloat(flownRevenue_lastyr)
											+ lCallCenter.getTotalRevenue_lastyr();
									lCallCenter.setTotalRevenue_lastyr(totalRevenue_lastyr);
								}
							}
						}
					}
					for (String key : map.keySet()) {
						lCallCenter = new CallCenterModel();
						lCallCenter.setCombinationKey(map.get(key).getCombinationKey());
						lCallCenter.setMonth(map.get(key).getMonth());
						lCallCenter.setRegion(map.get(key).getRegion());
						lCallCenter.setCountry(map.get(key).getCountry());
						lCallCenter.setPos(map.get(key).getPos());
						lCallCenter.setCompartment(map.get(key).getCompartment());
						float totalRevenue = map.get(key).getTotalRevenue();
						float totalRevenue_lastyr = map.get(key).getTotalRevenue_lastyr();
						lCallCenter.setTotalRevenue_lastyr(totalRevenue_lastyr);
						float lRevenueVLYR = 0;
						if (totalRevenue_lastyr > 0) {
							lRevenueVLYR = CalculationUtil.calculateVLYR(totalRevenue, totalRevenue_lastyr);
						} else {
							lRevenueVLYR = 0;
						}
						lCallCenter.setTotalRevenue(CalculationUtil.round(totalRevenue, 1));
						if (lRevenueVLYR != 0) {
							lCallCenter.setRevenueVLYR(Float.toString(lRevenueVLYR));
						} else {
							lCallCenter.setRevenueVLYR("0");
						}
						float lRevenueVTGT = 0;
						if (map.get(key).getTotalRevenue() > 0) {
							lRevenueVTGT = CalculationUtil.calculateVTGT(map.get(key).getTotalRevenue(),
									map.get(key).getTargetRevenue());
						} else {
							lRevenueVTGT = 0;
						}
						if (lRevenueVTGT != 0) {
							lCallCenter.setRevenueVTGT(Float.toString(lRevenueVTGT));
						} else {
							lCallCenter.setRevenueVTGT("0");
						}
						lCallCenter.setTargetRevenue(map.get(key).getTargetRevenue());
						lCallCenterSalesList.add(lCallCenter);
					}
					/* Computing totals ---Start */
					for (CallCenterModel lObj : lCallCenterSalesList) {
						totalRevenueYTD += lObj.getTotalRevenue();
						totalRevLastYr += lObj.getTotalRevenue_lastyr();
						totalRevTarget += lObj.getTargetRevenue();
					}
					if (totalRevLastYr > 0) {
						totalVLYR = CalculationUtil.calculateVLYR(totalRevenueYTD, totalRevLastYr);
					} else {
						totalVLYR = 0;
					}
					if (totalRevTarget > 0) {
						totalVTGT = CalculationUtil.calculateVTGT(totalRevenueYTD, totalRevTarget);
					} else {
						totalVTGT = 0;
					}
					CallCenterTotalsResponse lTotals = new CallCenterTotalsResponse();
					lTotals.setTotalRevYTD(Integer.toString(Math.round(totalRevenueYTD)));
					if (totalVLYR != 0) {
						lTotals.setTotalRevVLYR(Integer.toString(Math.round(totalVLYR)));
					} else {
						lTotals.setTotalRevVLYR("NA");
					}
					if (totalVTGT != 0) {
						lTotals.setTotalRevVTGT(Integer.toString(Math.round(totalVTGT)));
					} else {
						lTotals.setTotalRevVTGT("NA");
					}
					lTotalsList.add(lTotals);
					/* Computing totals ---End */
				}
				Collections.sort(lCallCenterSalesList, new CallCenterComp());
				responseCallCenterMap.put("CallCenterTotals", lTotalsList);
				responseCallCenterMap.put("CallCenterSalesGrowth", lCallCenterSalesList);
			} catch (Exception e) {
				logger.error("getCallCenterSalesGrowth-Exception", e);
			}
		} else {
			responseCallCenterMap.put("CallCenterSalesGrowth", null);
		}
		return responseCallCenterMap;
	}

	class CallCenterComp implements Comparator<CallCenterModel> {
		@Override
		public int compare(CallCenterModel arg0, CallCenterModel arg1) {
			if (arg0.getRevenueVLYR().equals("NA")) {
				arg0.setRevenueVLYR("0");
			}
			if (arg1.getRevenueVLYR().equals("NA")) {
				arg1.setRevenueVLYR("0");
			}
			if (Float.parseFloat(arg0.getRevenueVLYR()) > 0) {
				if (Float.parseFloat(arg0.getRevenueVLYR()) < Float.parseFloat(arg1.getRevenueVLYR())) {
					return 1;
				} else {
					return -1;
				}
			}
			if (arg0.getRevenueYTD() > 0) {
				if (arg0.getRevenueYTD() < arg1.getRevenueYTD()) {
					return 1;
				} else {
					return -1;
				}
			}
			if (arg0.getRevenueVLYR().equals("0")) {
				arg0.setRevenueVLYR("NA");
			}
			if (arg1.getRevenueVLYR().equals("0")) {
				arg1.setRevenueVLYR("NA");
			}
			return 0;
		}
	}

	@Override
	public Map<String, Object> getBspsalesSummaryhost(RequestModel mRequestModel) {
		int pax_lastyr = 0;
		// Totals
		float totalRevenueYTD = 0;
		float totalRevLastYr = 0;
		float totalRevTarget = 0;
		int totalVLYR = 0;
		int totalVTGT = 0;
		List<BSPModel> lBSPSummaryList = new ArrayList<BSPModel>();
		List<BSPModel> lBSPSummaryHostList = new ArrayList<BSPModel>();
		List<CallCenterTotalsResponse> lTotalsList = new ArrayList<CallCenterTotalsResponse>();
		BSPModel lBSPModel = new BSPModel();
		Map<String, Object> responseBSPSummaryHostMap = new HashMap<String, Object>();
		BasicDBObject lBSPSummaryHostObj = marketDao.getBspsalesSummaryhost(mRequestModel);
		if (lBSPSummaryHostObj != null) {
			JSONArray lBSPSummaryData = null;
			if (lBSPSummaryHostObj.containsKey("_batch")) {
				lBSPSummaryData = new JSONArray(lBSPSummaryHostObj.get("_batch").toString());
				System.out.println(lBSPSummaryData);
			}
			try {
				if (lBSPSummaryData != null) {
					for (int i = 0; i < lBSPSummaryData.length(); i++) {
						JSONObject lBSPJsonObj = lBSPSummaryData.getJSONObject(i);
						lBSPModel = new BSPModel();
						System.out.println("lBSPJsonObj" + lBSPJsonObj);
						if (lBSPJsonObj.has("region") && lBSPJsonObj.get("region") != null
								&& !"null".equalsIgnoreCase(lBSPJsonObj.get("region").toString())) {
							lBSPModel.setRegion(lBSPJsonObj.get("region").toString());
						}
						if (lBSPJsonObj.has("country") && lBSPJsonObj.get("country") != null
								&& !"null".equalsIgnoreCase(lBSPJsonObj.get("country").toString())) {
							lBSPModel.setCountry(lBSPJsonObj.get("country").toString());
						}
						if (lBSPJsonObj.has("pos") && lBSPJsonObj.get("pos") != null
								&& !"null".equalsIgnoreCase(lBSPJsonObj.get("pos").toString())) {
							lBSPModel.setPos(lBSPJsonObj.get("pos").toString());
						}
						if (lBSPJsonObj.has("compartment") && lBSPJsonObj.get("compartment") != null
								&& !"null".equalsIgnoreCase(lBSPJsonObj.get("compartment").toString())) {
							lBSPModel.setCompartment(lBSPJsonObj.get("compartment").toString());
						}
						String saleRevStr = lBSPJsonObj.get("revenue").toString();
						if (lBSPJsonObj.has("revenue") && lBSPJsonObj.get("revenue") != null
								&& !"null".equalsIgnoreCase(saleRevStr)) {
							lBSPModel.setSaleRevenue(saleRevStr);
						}
						String saleRevlastyrStr = lBSPJsonObj.get("revenue_1").toString();
						if (lBSPJsonObj.has("revenue_1") && lBSPJsonObj.get("revenue_1") != null
								&& !"null".equalsIgnoreCase(saleRevlastyrStr)) {
							lBSPModel.setSaleRevenue_lastyr(saleRevlastyrStr);
						}
						if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null
								&& mRequestModel.getRegionArray() == null && mRequestModel.getCountryArray() == null
								&& mRequestModel.getPosArray() == null && mRequestModel.getCompartmentArray() == null) {
							lBSPModel.setCombinationKey(lBSPModel.getRegion());
						} else if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null
								&& mRequestModel.getRegionArray() != null && mRequestModel.getCountryArray() == null
								&& mRequestModel.getPosArray() == null && mRequestModel.getCompartmentArray() == null) {
							lBSPModel.setCombinationKey(lBSPModel.getRegion() + lBSPModel.getCountry());
						} else if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null
								&& mRequestModel.getRegionArray() != null && mRequestModel.getCountryArray() != null
								&& mRequestModel.getPosArray() == null && mRequestModel.getCompartmentArray() == null) {
							lBSPModel.setCombinationKey(
									lBSPModel.getRegion() + lBSPModel.getCountry() + lBSPModel.getPos());
						} else if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null
								&& mRequestModel.getRegionArray() != null && mRequestModel.getCountryArray() != null
								&& mRequestModel.getPosArray() != null && mRequestModel.getCompartmentArray() == null) {
							lBSPModel.setCombinationKey(lBSPModel.getRegion() + lBSPModel.getCountry()
									+ lBSPModel.getPos() + lBSPModel.getCompartment());
						} else if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null
								&& mRequestModel.getRegionArray() != null && mRequestModel.getCountryArray() != null
								&& mRequestModel.getPosArray() != null && mRequestModel.getCompartmentArray() != null) {
							lBSPModel.setCombinationKey(lBSPModel.getRegion() + lBSPModel.getCountry()
									+ lBSPModel.getPos() + lBSPModel.getCompartment());
						} else {
							lBSPModel.setCombinationKey(lBSPModel.getRegion());
						}
						if (lBSPModel.getCombinationKey() == null) {
							lBSPModel.setCombinationKey("-");
						}
						System.out.println("lBSPModel-Key====" + lBSPModel.getCombinationKey());
						lBSPSummaryList.add(lBSPModel);
					}
					Map<String, BSPModel> map = new HashMap<String, BSPModel>();
					BSPModel lBSPSummary = null;
					if (lBSPSummaryList.size() > 0) {
						for (BSPModel lObj : lBSPSummaryList) {
							if (!map.containsKey(lObj.getCombinationKey())) {
								lBSPSummary = new BSPModel();
								lBSPSummary.setCombinationKey(lObj.getCombinationKey());
								lBSPSummary.setRegion(lObj.getRegion());
								lBSPSummary.setCountry(lObj.getCountry());
								lBSPSummary.setPos(lObj.getPos());
								lBSPSummary.setCompartment(lObj.getCompartment());
								String salesRevenue = lObj.getSaleRevenue();
								String salesRevenue_lastyr = lObj.getSaleRevenue_lastyr();
								lBSPSummary.setTotalRevenue(Float.parseFloat(salesRevenue));
								lBSPSummary.setTotalRevenue_lastyr(Float.parseFloat(salesRevenue_lastyr));
								map.put(lObj.getCombinationKey(), lBSPSummary);
							} else {
								for (String lKey : map.keySet()) {
									if (lKey == null) {
										lKey = "-";
									}
									if (lObj.getCombinationKey().equals(lKey)) {
										lBSPSummary = map.get(lKey);
									}
								}
								String salesRevenue = lObj.getSaleRevenue();
								String salesRevenue_lastyr = lObj.getSaleRevenue_lastyr();
								float totalRevenue = Float.parseFloat(salesRevenue) + lBSPSummary.getTotalRevenue();
								lBSPSummary.setTotalRevenue(totalRevenue);
								float totalRevenue_lastyr = Float.parseFloat(salesRevenue_lastyr)
										+ lBSPSummary.getTotalRevenue_lastyr();
								lBSPSummary.setTotalRevenue_lastyr(totalRevenue_lastyr);
							}
						}
					}
					for (String key : map.keySet()) {
						lBSPSummary = new BSPModel();
						lBSPSummary.setCombinationKey(map.get(key).getCombinationKey());
						lBSPSummary.setRegion(map.get(key).getRegion());
						lBSPSummary.setCountry(map.get(key).getCountry());
						lBSPSummary.setCompartment(map.get(key).getCompartment());
						lBSPSummary.setPos(map.get(key).getPos());
						float totalRevenue = map.get(key).getTotalRevenue();
						float totalRevenue_lastyr = map.get(key).getTotalRevenue_lastyr();
						float lRevenueVLYR = 0;
						if (totalRevenue_lastyr > 0) {
							lRevenueVLYR = CalculationUtil.calculateVLYR(totalRevenue, totalRevenue_lastyr);
						} else {
							lRevenueVLYR = 0;
						}
						lBSPSummary.setTotalRevenue(totalRevenue);
						if (lRevenueVLYR > 0) {
							lBSPSummary.setRevenueVLYR(Float.toString(lRevenueVLYR));
						} else {
							lBSPSummary.setRevenueVLYR("NA");
						}
						lBSPSummaryHostList.add(lBSPSummary);
					}
					/* Computing totals ---Start */
					for (BSPModel lObj : lBSPSummaryHostList) {
						totalRevenueYTD += lObj.getTotalRevenue();
						totalRevLastYr += lObj.getTotalRevenue_lastyr();
					}
					if (totalRevLastYr > 0) {
						totalVLYR = (int) CalculationUtil.calculateVLYR(totalRevenueYTD, totalRevLastYr);
					} else {
						totalVLYR = 0;
					}
					if (totalRevTarget > 0) {
						totalVTGT = (int) CalculationUtil.calculateVTGT(totalRevenueYTD, totalRevTarget);
					} else {
						totalVTGT = 0;
					}
					CallCenterTotalsResponse lTotals = new CallCenterTotalsResponse();
					lTotals.setTotalRevYTD(Float.toString(totalRevenueYTD));
					if (totalRevLastYr > 0) {
						lTotals.setTotalRevVLYR(Float.toString(totalVLYR));
					} else {
						lTotals.setTotalRevVLYR("NA");
					}
					if (totalRevTarget > 0) {
						lTotals.setTotalRevVTGT(Integer.toString(totalVTGT));
					} else {
						lTotals.setTotalRevVTGT("NA");
					}
					lTotalsList.add(lTotals);
					/* Computing totals ---End */
				}
				responseBSPSummaryHostMap.put("BSPTotals", lTotalsList);
				responseBSPSummaryHostMap.put("BSPHostSummary", lBSPSummaryHostList);
			} catch (Exception e) {
				logger.error("getBspsalesSummaryhost-Exception", e);
			}
		} else {
			lBSPSummaryHostList.add(new BSPModel());
			responseBSPSummaryHostMap.put("BSPHostSummary", lBSPSummaryHostList);
		}
		return responseBSPSummaryHostMap;
	}

	@Override
	public Map<String, Object> getBspsalesSummaryAirline(RequestModel mRequestModel) {
		int pax_lastyr = 0;
		// Totals
		float totalRevenueYTD = 0;
		float totalRevLastYr = 0;
		float totalRevTarget = 0;
		int totalVLYR = 0;
		int totalVTGT = 0;
		List<BSPModel> lBSPSummaryList = new ArrayList<BSPModel>();
		List<BSPModel> lBSPSummaryAirlineList = new ArrayList<BSPModel>();
		List<CallCenterTotalsResponse> lTotalsList = new ArrayList<CallCenterTotalsResponse>();
		BSPModel lBSPModel = new BSPModel();
		Map<String, Object> responseBSPSummaryAirlineMap = new HashMap<String, Object>();
		BasicDBObject lBSPSummaryHostObj = marketDao.getBspsalesSummaryhost(mRequestModel);
		if (lBSPSummaryHostObj != null) {
			JSONArray lBSPSummaryData = null;
			if (lBSPSummaryHostObj.containsKey("_batch")) {
				lBSPSummaryData = new JSONArray(lBSPSummaryHostObj.get("_batch").toString());
				System.out.println(lBSPSummaryData);
			}
			try {
				if (lBSPSummaryData != null) {
					for (int i = 0; i < lBSPSummaryData.length(); i++) {
						JSONObject lBSPJsonObj = lBSPSummaryData.getJSONObject(i);
						lBSPModel = new BSPModel();
						System.out.println("lBSPJsonObj" + lBSPJsonObj);
						if (lBSPJsonObj.has("region") && lBSPJsonObj.get("region") != null
								&& !"null".equalsIgnoreCase(lBSPJsonObj.get("region").toString())) {
							lBSPModel.setRegion(lBSPJsonObj.get("region").toString());
						}
						if (lBSPJsonObj.has("country") && lBSPJsonObj.get("country") != null
								&& !"null".equalsIgnoreCase(lBSPJsonObj.get("country").toString())) {
							lBSPModel.setCountry(lBSPJsonObj.get("country").toString());
						}
						if (lBSPJsonObj.has("pos") && lBSPJsonObj.get("pos") != null
								&& !"null".equalsIgnoreCase(lBSPJsonObj.get("pos").toString())) {
							lBSPModel.setPos(lBSPJsonObj.get("pos").toString());
						}
						if (lBSPJsonObj.has("compartment") && lBSPJsonObj.get("compartment") != null
								&& !"null".equalsIgnoreCase(lBSPJsonObj.get("compartment").toString())) {
							lBSPModel.setCompartment(lBSPJsonObj.get("compartment").toString());
						}
						String saleRevStr = lBSPJsonObj.get("revenue").toString();
						if (lBSPJsonObj.has("revenue") && lBSPJsonObj.get("revenue") != null
								&& !"null".equalsIgnoreCase(saleRevStr)) {
							lBSPModel.setSaleRevenue(saleRevStr);
						}
						String saleRevlastyrStr = lBSPJsonObj.get("revenue_1").toString();
						if (lBSPJsonObj.has("revenue_1") && lBSPJsonObj.get("revenue_1") != null
								&& !"null".equalsIgnoreCase(saleRevlastyrStr)) {
							lBSPModel.setSaleRevenue_lastyr(saleRevlastyrStr);
						}
						if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null
								&& mRequestModel.getRegionArray() == null && mRequestModel.getCountryArray() == null
								&& mRequestModel.getPosArray() == null && mRequestModel.getCompartmentArray() == null) {
							lBSPModel.setCombinationKey(lBSPModel.getRegion());
						} else if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null
								&& mRequestModel.getRegionArray() != null && mRequestModel.getCountryArray() == null
								&& mRequestModel.getPosArray() == null && mRequestModel.getCompartmentArray() == null) {
							lBSPModel.setCombinationKey(lBSPModel.getRegion() + lBSPModel.getCountry());
						} else if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null
								&& mRequestModel.getRegionArray() != null && mRequestModel.getCountryArray() != null
								&& mRequestModel.getPosArray() == null && mRequestModel.getCompartmentArray() == null) {
							lBSPModel.setCombinationKey(
									lBSPModel.getRegion() + lBSPModel.getCountry() + lBSPModel.getPos());
						} else if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null
								&& mRequestModel.getRegionArray() != null && mRequestModel.getCountryArray() != null
								&& mRequestModel.getPosArray() != null && mRequestModel.getCompartmentArray() == null) {
							lBSPModel.setCombinationKey(lBSPModel.getRegion() + lBSPModel.getCountry()
									+ lBSPModel.getPos() + lBSPModel.getCompartment());
						} else if (mRequestModel.getFromDate() != null && mRequestModel.getToDate() != null
								&& mRequestModel.getRegionArray() != null && mRequestModel.getCountryArray() != null
								&& mRequestModel.getPosArray() != null && mRequestModel.getCompartmentArray() != null) {
							lBSPModel.setCombinationKey(lBSPModel.getRegion() + lBSPModel.getCountry()
									+ lBSPModel.getPos() + lBSPModel.getCompartment());
						} else {
							lBSPModel.setCombinationKey(lBSPModel.getRegion());
						}
						if (lBSPModel.getCombinationKey() == null) {
							lBSPModel.setCombinationKey("-");
						}
						System.out.println("lBSPModel-Key====" + lBSPModel.getCombinationKey());
						lBSPSummaryList.add(lBSPModel);
					}
					Map<String, BSPModel> map = new HashMap<String, BSPModel>();
					BSPModel lBSPSummary = null;
					if (lBSPSummaryList.size() > 0) {
						for (BSPModel lObj : lBSPSummaryList) {
							if (!map.containsKey(lObj.getCombinationKey())) {
								lBSPSummary = new BSPModel();
								lBSPSummary.setCombinationKey(lObj.getCombinationKey());
								lBSPSummary.setRegion(lObj.getRegion());
								lBSPSummary.setCountry(lObj.getCountry());
								lBSPSummary.setPos(lObj.getPos());
								lBSPSummary.setCompartment(lObj.getCompartment());
								String salesRevenue = lObj.getSaleRevenue();
								String salesRevenue_lastyr = lObj.getSaleRevenue_lastyr();
								lBSPSummary.setTotalRevenue(Float.parseFloat(salesRevenue));
								lBSPSummary.setTotalRevenue_lastyr(Float.parseFloat(salesRevenue_lastyr));
								map.put(lObj.getCombinationKey(), lBSPSummary);
							} else {
								for (String lKey : map.keySet()) {
									if (lKey == null) {
										lKey = "-";
									}
									if (lObj.getCombinationKey().equals(lKey)) {
										lBSPSummary = map.get(lKey);
									}
								}
								String salesRevenue = lObj.getSaleRevenue();
								String salesRevenue_lastyr = lObj.getSaleRevenue_lastyr();
								float totalRevenue = Float.parseFloat(salesRevenue) + lBSPSummary.getTotalRevenue();
								lBSPSummary.setTotalRevenue(totalRevenue);
								float totalRevenue_lastyr = Float.parseFloat(salesRevenue_lastyr)
										+ lBSPSummary.getTotalRevenue_lastyr();
								lBSPSummary.setTotalRevenue_lastyr(totalRevenue_lastyr);
							}
						}
					}
					for (String key : map.keySet()) {
						lBSPSummary = new BSPModel();
						lBSPSummary.setCombinationKey(map.get(key).getCombinationKey());
						lBSPSummary.setRegion(map.get(key).getRegion());
						lBSPSummary.setCountry(map.get(key).getCountry());
						lBSPSummary.setCompartment(map.get(key).getCompartment());
						lBSPSummary.setPos(map.get(key).getPos());
						float totalRevenue = map.get(key).getTotalRevenue();
						float totalRevenue_lastyr = map.get(key).getTotalRevenue_lastyr();
						float lRevenueVLYR = 0;
						if (totalRevenue_lastyr > 0) {
							lRevenueVLYR = CalculationUtil.calculateVLYR(totalRevenue, totalRevenue_lastyr);
						} else {
							lRevenueVLYR = 0;
						}
						lBSPSummary.setTotalRevenue(totalRevenue);
						if (lRevenueVLYR > 0) {
							lBSPSummary.setRevenueVLYR(Float.toString(lRevenueVLYR));
						} else {
							lBSPSummary.setRevenueVLYR("NA");
						}
						lBSPSummaryAirlineList.add(lBSPSummary);
					}
					/* Computing totals ---Start */
					for (BSPModel lObj : lBSPSummaryAirlineList) {
						totalRevenueYTD += lObj.getTotalRevenue();
						totalRevLastYr += lObj.getTotalRevenue_lastyr();
					}
					if (totalRevLastYr > 0) {
						totalVLYR = (int) CalculationUtil.calculateVLYR(totalRevenueYTD, totalRevLastYr);
					} else {
						totalVLYR = 0;
					}
					if (totalRevTarget > 0) {
						totalVTGT = (int) CalculationUtil.calculateVTGT(totalRevenueYTD, totalRevTarget);
					} else {
						totalVTGT = 0;
					}
					CallCenterTotalsResponse lTotals = new CallCenterTotalsResponse();
					lTotals.setTotalRevYTD(Float.toString(totalRevenueYTD));
					if (totalRevLastYr > 0) {
						lTotals.setTotalRevVLYR(Float.toString(totalVLYR));
					} else {
						lTotals.setTotalRevVLYR("NA");
					}
					if (totalRevTarget > 0) {
						lTotals.setTotalRevVTGT(Integer.toString(totalVTGT));
					} else {
						lTotals.setTotalRevVTGT("NA");
					}
					lTotalsList.add(lTotals);
					/* Computing totals ---End */
				}
				responseBSPSummaryAirlineMap.put("BSPTotals", lTotalsList);
				responseBSPSummaryAirlineMap.put("BSPAirlineSummary", lBSPSummaryAirlineList);
			} catch (Exception e) {
				logger.error("getBspsalesSummaryAirline-Exception", e);
			}
		} else {
			lBSPSummaryAirlineList.add(new BSPModel());
			responseBSPSummaryAirlineMap.put("BSPAirlineSummary", lBSPSummaryAirlineList);
		}
		return responseBSPSummaryAirlineMap;
	}

	@Override
	public Map<String, Object> getMarketBarometer(RequestModel mRequestModel) {
		Map<String, Object> marketBarometerMap = new HashMap<String, Object>();
		Map<String, MarketBarometerModel> mbMap = new HashMap<String, MarketBarometerModel>();
		ArrayList<DBObject> marketData = marketDao.getMarketBarometer(mRequestModel);
		int totalAgentCounts = 0;
		int totalFriendsCounts = 0;
		int totalFoesCounts = 0;
		try {
			if (marketData != null) {
				JSONArray data = new JSONArray(marketData);
				if (data != null) {
					for (int i = 0; i < data.length(); i++) {
						JSONObject jsonObj = data.getJSONObject(i);
						String carrier = "-";
						if (jsonObj.has("airline") && jsonObj.get("airline") != null
								&& !"null".equalsIgnoreCase(jsonObj.get("airline").toString()))
							carrier = jsonObj.getString("airline");
						if (carrier.equals("FZ")) {
							String combinationKey = getCombinationKeyMarketBarometer(mRequestModel, jsonObj);
							String region = "-";
							if (jsonObj.has("region") && jsonObj.get("region") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("region").toString()))
								region = jsonObj.getString("region");
							String country = "-";
							if (jsonObj.has("country") && jsonObj.get("country") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("country").toString()))
								country = jsonObj.getString("country");
							String pos = "-";
							if (jsonObj.has("pos") && jsonObj.get("pos") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("pos").toString()))
								pos = jsonObj.getString("pos");
							String compartment = "-";
							if (jsonObj.has("compartment") && jsonObj.get("compartment") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("compartment").toString()))
								compartment = jsonObj.getString("compartment");
							String agentType = "-";
							if (jsonObj.get("friend_or_foe_rev") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("friend_or_foe_rev").toString()))
								agentType = jsonObj.getString("friend_or_foe_rev");
							if (!mbMap.containsKey(combinationKey)) {
								MarketBarometerModel mb = new MarketBarometerModel();
								mb.setRegion(region);
								mb.setCountry(country);
								mb.setPos(pos);
								mb.setCompartment(compartment);
								mb.setCombinationKey(combinationKey);
								mb.setAgentCounts(1);
								if (agentType.equals("friend")) {
									mb.setFriendsCounts(1);
									totalFriendsCounts += 1;
								} else {
									mb.setFoesCounts(1);
									totalFoesCounts += 1;
								}
								totalAgentCounts += 1;
								// data not available so setting them to 0
								mb.setCorporateCounts(0);
								mb.setDealCounts(0);
								mb.setConsolidatorCounts(0);
								//
								mbMap.put(combinationKey, mb);
							} else {
								MarketBarometerModel mb = mbMap.get(combinationKey);
								mb.setAgentCounts(mb.getAgentCounts() + 1);
								if (agentType.equals("friend")) {
									mb.setFriendsCounts(mb.getFriendsCounts() + 1);
									totalFriendsCounts += 1;
								} else {
									mb.setFoesCounts(mb.getFoesCounts() + 1);
									totalFoesCounts += 1;
								}
								totalAgentCounts += 1;
							}
						}
					}
					List<MarketBarometerModel> mbList = new ArrayList<MarketBarometerModel>(mbMap.values());
					MarketBarometerTotal tm = new MarketBarometerTotal();
					tm.setTotalAgentCounts(totalAgentCounts);
					tm.setTotalFriendsCounts(totalFriendsCounts);
					tm.setTotalFoesCounts(totalFoesCounts);
					marketBarometerMap.put("marketBarometerMap", mbList);
					marketBarometerMap.put("marketBarometerTotals", tm);
				}
			}
		} catch (Exception e) {
			logger.error("getMarketBarometer-Exception", e);
		}
		return marketBarometerMap;
	}

	private String getCombinationKeyMarketBarometer(RequestModel mRequestModel, JSONObject jsonObj) {
		String region = "-";
		if (jsonObj.has("region") && jsonObj.get("region") != null
				&& !"null".equalsIgnoreCase(jsonObj.get("region").toString()))
			region = jsonObj.getString("region");
		String country = "-";
		if (jsonObj.has("country") && jsonObj.get("country") != null
				&& !"null".equalsIgnoreCase(jsonObj.get("country").toString()))
			country = jsonObj.getString("country");
		String pos = "-";
		if (jsonObj.has("pos") && jsonObj.get("pos") != null && !"null".equalsIgnoreCase(jsonObj.get("pos").toString()))
			pos = jsonObj.getString("pos");
		String compartment = "-";
		if (jsonObj.has("compartment") && jsonObj.get("compartment") != null
				&& !"null".equalsIgnoreCase(jsonObj.get("compartment").toString()))
			compartment = jsonObj.getString("compartment");
		String key = compartment;
		boolean flag = false;
		if (mRequestModel.getRegionArray() == null
				|| "all".equalsIgnoreCase(mRequestModel.getRegionArray()[0].toString())
						&& mRequestModel.getCountryArray() == null
				|| "all".equalsIgnoreCase(mRequestModel.getCountryArray()[0].toString())
						&& mRequestModel.getPosArray() == null
				|| "all".equalsIgnoreCase(mRequestModel.getPosArray()[0].toString()))
			flag = true;
		if (flag) {
			if (mRequestModel.getLevel() != null && !mRequestModel.getLevel().isEmpty()
					&& "network".equalsIgnoreCase(mRequestModel.getLevel())) {
				key += region;
			} else if (mRequestModel.getLevel() != null && !mRequestModel.getLevel().isEmpty()
					&& "region".equalsIgnoreCase(mRequestModel.getLevel())) {
				key += region;
			} else if (mRequestModel.getLevel() != null && !mRequestModel.getLevel().isEmpty()
					&& "country".equalsIgnoreCase(mRequestModel.getLevel())) {
				key += country;
			} else if (mRequestModel.getLevel() != null && !mRequestModel.getLevel().isEmpty()
					&& "pos".equalsIgnoreCase(mRequestModel.getLevel())) {
				key += pos;
			}
		} else {
			if (mRequestModel.getRegionArray() != null
					|| "all".equalsIgnoreCase(mRequestModel.getRegionArray()[0].toString())) {
				key = key + region + country;
			}
			if (mRequestModel.getCountryArray() != null
					|| "all".equalsIgnoreCase(mRequestModel.getCountryArray()[0].toString())) {
				key = key + country + pos;
			}
			if (mRequestModel.getPosArray() != null
					|| "all".equalsIgnoreCase(mRequestModel.getPosArray()[0].toString())) {
				key = key + pos;
			}
		}
		System.out.println("key : " + key);
		return key;
	}

	@Override
	public Map<String, Object> getMarketoutlook(RequestModel mRequestModel) {
		List<FilterModel> lMarketOutlookList = new ArrayList<FilterModel>();
		List<MarketOutlookModel> lMoList = new ArrayList<MarketOutlookModel>();
		Map<String, Object> responseMarketOutlookMap = new HashMap<String, Object>();
		ArrayList<DBObject> lMarketOutlookObj = marketDao.getMarketoutlook(mRequestModel);
		if (lMarketOutlookObj != null) {
			JSONArray lMarketOutlookData = new JSONArray(lMarketOutlookObj);
			try {
				if (lMarketOutlookData != null) {
					for (int i = 0; i < lMarketOutlookData.length(); i++) {
						JSONObject lJsonObj = lMarketOutlookData.getJSONObject(i);
						FilterModel lMarketOutlook = new FilterModel();
						if (lJsonObj.has("year") && lJsonObj.get("year") != null)
							lMarketOutlook.setYear(lJsonObj.get("year").toString());
						if (lJsonObj.has("month") && lJsonObj.get("month") != null)
							lMarketOutlook.setMonth(lJsonObj.get("month").toString());
						if (lJsonObj.has("region") && lJsonObj.get("region") != null)
							lMarketOutlook.setRegion(lJsonObj.get("region").toString());
						if (lJsonObj.has("country") && lJsonObj.get("country") != null)
							lMarketOutlook.setCountry(lJsonObj.get("country").toString());
						if (lJsonObj.has("pos") && lJsonObj.get("pos") != null)
							lMarketOutlook.setPos(lJsonObj.get("pos").toString());
						if (lJsonObj.has("compartment") && lJsonObj.get("compartment") != null)
							lMarketOutlook.setCompartment(lJsonObj.get("compartment").toString());
						if (lJsonObj.has("pax") && lJsonObj.get("pax") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("pax").toString()))
							lMarketOutlook.setHostPax(lJsonObj.get("pax").toString());
						if (lJsonObj.has("pax_1") && lJsonObj.get("pax_1") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("pax_1").toString()))
							lMarketOutlook.setHostPax_lastyr(lJsonObj.get("pax_1").toString());
						if (lJsonObj.has("revenue") && lJsonObj.get("revenue") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("revenue").toString()))
							lMarketOutlook.setFlownRevenue(lJsonObj.get("revenue").toString());
						float revenuelastyr = 0;
						if (lJsonObj.has("revenue_1") && lJsonObj.get("revenue_1") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("revenue_1").toString()))
							revenuelastyr = Float.parseFloat(lJsonObj.get("revenue_1").toString());
						lMarketOutlook.setFlownRevenue_lastyr(lJsonObj.get("revenue_1").toString());
						if (lJsonObj.has("MarketingCarrier1") && lJsonObj.get("MarketingCarrier1") != null)
							lMarketOutlook.setCarrier(lJsonObj.get("MarketingCarrier1").toString());
						StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lMarketOutlook);
						String keyBuilder = lStr.toString();
						if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
							keyBuilder = "null";
						}
						if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lMarketOutlook.setFilterKey(lMarketOutlook.getRegion());
								lStr = new StringBuilder();
								lStr.append(lMarketOutlook.getFilterKey());
							} else {
								lStr.append(lMarketOutlook.getRegion());
							}
						} else if ("region".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lMarketOutlook.setFilterKey(lMarketOutlook.getRegion());
								lStr = new StringBuilder();
								lStr.append(lMarketOutlook.getFilterKey());
							} else {
								lStr.append(lMarketOutlook.getRegion() + lMarketOutlook.getCompartment());
							}
						} else if ("country".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lMarketOutlook.setFilterKey(lMarketOutlook.getRegion() + lMarketOutlook.getCountry()
										+ lMarketOutlook.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lMarketOutlook.getFilterKey());
							} else {
								lStr.append(lMarketOutlook.getRegion() + lMarketOutlook.getCountry()
										+ lMarketOutlook.getCompartment());
							}
						} else if ("pos".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lMarketOutlook.setFilterKey(lMarketOutlook.getRegion() + lMarketOutlook.getCountry()
										+ lMarketOutlook.getPos() + lMarketOutlook.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lMarketOutlook.getFilterKey());
							} else {
								lStr.append(lMarketOutlook.getRegion() + lMarketOutlook.getCountry()
										+ lMarketOutlook.getPos() + lMarketOutlook.getOd()
										+ lMarketOutlook.getCompartment());
							}
						}
						lMarketOutlook.setFilterKey(lStr.toString());
						lMarketOutlookList.add(lMarketOutlook);
					}
				}
				Map<String, MarketOutlookModel> map = new HashMap<String, MarketOutlookModel>();
				MarketOutlookModel lMOModel = null;
				for (FilterModel lMoObj : lMarketOutlookList) {
					if (!map.containsKey(lMoObj.getFilterKey())) {
						lMOModel = new MarketOutlookModel();
						lMOModel.setYear(lMoObj.getYear());
						lMOModel.setRegion(lMoObj.getRegion());
						lMOModel.setCountry(lMoObj.getCountry());
						lMOModel.setPos(lMoObj.getPos());
						lMOModel.setCompartment(lMoObj.getCompartment());
						lMOModel.setPax((int) Float.parseFloat(lMoObj.getHostPax()));
						lMOModel.setTotalPax(lMOModel.getPax());
						lMOModel.setPax_1((int) Float.parseFloat(lMoObj.getHostPax_lastyr()));
						lMOModel.setTotalPax_lastyr(lMOModel.getPax_1());
						lMOModel.setRevenue(Float.parseFloat(lMoObj.getFlownRevenue()));
						lMOModel.setTotalRevenue(lMOModel.getRevenue());
						lMOModel.setRevenue_1(Float.parseFloat(lMoObj.getFlownRevenue_lastyr()));
						lMOModel.setTotalRevenue_lastyr(lMOModel.getRevenue_1());
						lMOModel.setCombinationKey(lMoObj.getFilterKey());
						map.put(lMoObj.getFilterKey(), lMOModel);
					} else {
						for (String lKey : map.keySet()) {
							if (lMoObj.getFilterKey().equals(lKey)) {
								lMOModel = map.get(lKey);
							}
							int totalPax = (int) Float.parseFloat(lMoObj.getHostPax()) + lMOModel.getTotalPax();
							lMOModel.setTotalPax(totalPax);
							int totalPax_lastyr = (int) Float.parseFloat(lMoObj.getHostPax_lastyr())
									+ lMOModel.getTotalPax_lastyr();
							lMOModel.setTotalPax_lastyr(totalPax_lastyr);
							float totalRevenue = Float.parseFloat(lMoObj.getFlownRevenue())
									+ lMOModel.getTotalRevenue();
							lMOModel.setTotalRevenue(totalRevenue);
							float totalRevenue_lastyr = Float.parseFloat(lMoObj.getFlownRevenue_lastyr())
									+ lMOModel.getTotalRevenue_lastyr();
							lMOModel.setTotalRevenue(totalRevenue_lastyr);
						}
					}
				}
				for (String key : map.keySet()) {
					lMOModel = new MarketOutlookModel();
					StringBuilder lStr = CalculationUtil.getFilterKeyForMarketOutlook(mRequestModel, map.get(key));
					String keyBuilder = lStr.toString();
					if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
							|| "nullnullnull".equalsIgnoreCase(keyBuilder)
							|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
						keyBuilder = "null";
					}
					if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lMOModel.setCombinationKey(map.get(key).getRegion());
							lStr = new StringBuilder();
							lStr.append(lMOModel.getCombinationKey());
						} else {
							// Need to see if an else key is required
						}
					} else if ("region".equalsIgnoreCase(mRequestModel.getLevel())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lMOModel.setCombinationKey(map.get(key).getRegion());
							lStr = new StringBuilder();
							lStr.append(lMOModel.getCombinationKey());
						} else {
							// Need to see if an else key is required
						}
					} else if ("country".equalsIgnoreCase(mRequestModel.getLevel())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lMOModel.setCombinationKey(map.get(key).getRegion() + map.get(key).getCountry());
							lStr = new StringBuilder();
							lStr.append(lMOModel.getCombinationKey());
						} else {
							// Need to see if an else key is required
						}
					} else if ("pos".equalsIgnoreCase(mRequestModel.getLevel())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lMOModel.setCombinationKey(
									map.get(key).getRegion() + map.get(key).getCountry() + map.get(key).getPos());
							lStr = new StringBuilder();
							lStr.append(lMOModel.getCombinationKey());
						} else {
						}
					}
					lMOModel.setCombinationKey(lStr.toString());
					lMOModel.setYear(map.get(key).getYear());
					lMOModel.setMonth(map.get(key).getMonth());
					if (map.get(key).getCombinationKey().equals(map.get(key).getRegion())) {
						lMOModel.setRegion(map.get(key).getRegion());
					} else if (map.get(key).getCombinationKey()
							.equals(map.get(key).getRegion() + map.get(key).getCountry())) {
						lMOModel.setRegion(map.get(key).getRegion());
						lMOModel.setCountry(map.get(key).getCountry());
					} else if (map.get(key).getCombinationKey()
							.equals(map.get(key).getRegion() + map.get(key).getCountry() + map.get(key).getPos())) {
						lMOModel.setRegion(map.get(key).getRegion());
						lMOModel.setCountry(map.get(key).getCountry());
						lMOModel.setPos(map.get(key).getPos());
					} else {
						lMOModel.setRegion(map.get(key).getRegion());
						lMOModel.setCountry(map.get(key).getCountry());
						lMOModel.setPos(map.get(key).getPos());
						lMOModel.setCompartment(map.get(key).getCompartment());
					}
					int totalPax = map.get(key).getTotalPax();
					lMOModel.setTotalPax(totalPax);
					int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					lMOModel.setTotalPax_lastyr(totalPax_lastyr);
					float totalRevenue = map.get(key).getTotalRevenue();
					lMOModel.setTotalRevenue(totalRevenue);
					float totalRevenue_lastyr = map.get(key).getTotalRevenue_lastyr();
					lMOModel.setTotalRevenue_lastyr(totalRevenue_lastyr);
					float lpaxVLYR = 0;
					int minThresholdValue = -5;
					int maxThresholdValue = 5;
					int declineCounts = 0;
					int matureCounts = 0;
					int growingCounts = 0;
					int nicheCounts = 0;
					if (totalPax_lastyr > 0) {
						lpaxVLYR = CalculationUtil.calculateVLYR(totalPax, totalPax_lastyr);
					} else {
						lpaxVLYR = 0;
					}
					if (lpaxVLYR > maxThresholdValue) {
						growingCounts += 1;
					} else if (lpaxVLYR < minThresholdValue) {
						declineCounts += 1;
					} else if (-5 >= lpaxVLYR && lpaxVLYR <= 5) {
						matureCounts += 1;
					} else {
						nicheCounts += 1;
					}
					lMOModel.setDeclineCounts(Integer.toString(declineCounts));
					lMOModel.setGrowingCounts(Integer.toString(growingCounts));
					lMOModel.setMatureCounts(Integer.toString(matureCounts));
					lMOModel.setNicheCounts(Integer.toString(nicheCounts));
					lMoList.add(lMOModel);
				}
				MarketOutlookModel lResponse = null;
				List<MarketOutlookResponse> lMoResponseList = new ArrayList<MarketOutlookResponse>();
				Map<String, MarketOutlookModel> map2 = new HashMap<String, MarketOutlookModel>();
				for (MarketOutlookModel lMoObj2 : lMoList) {
					if (!map2.containsKey(lMoObj2.getCombinationKey())) {
						lResponse = new MarketOutlookModel();
						lResponse.setRegion(lMoObj2.getRegion());
						lResponse.setCountry(lMoObj2.getCountry());
						lResponse.setPos(lMoObj2.getPos());
						lResponse.setCompartment(lMoObj2.getCompartment());
						lResponse.setDeclineCounts(lMoObj2.getDeclineCounts());
						lResponse.setGrowingCounts(lMoObj2.getGrowingCounts());
						lResponse.setMatureCounts(lMoObj2.getMatureCounts());
						lResponse.setNicheCounts(lMoObj2.getNicheCounts());
						map2.put(lMoObj2.getCombinationKey(), lResponse);
					} else {
						for (String lKey : map2.keySet()) {
							if (lMoObj2.getCombinationKey().equals(lKey)) {
								lResponse = map2.get(lKey);
							}
							int lDecline = Integer.parseInt(lMoObj2.getDeclineCounts())
									+ Integer.parseInt(lResponse.getDeclineCounts());
							int lGrowing = Integer.parseInt(lMoObj2.getGrowingCounts())
									+ Integer.parseInt(lResponse.getGrowingCounts());
							int lMature = Integer.parseInt(lMoObj2.getMatureCounts())
									+ Integer.parseInt(lResponse.getMatureCounts());
							int lNiche = Integer.parseInt(lMoObj2.getNicheCounts())
									+ Integer.parseInt(lResponse.getNicheCounts());
							lResponse.setDeclineCounts(Integer.toString(lDecline));
							lResponse.setGrowingCounts(Integer.toString(lGrowing));
							lResponse.setMatureCounts(Integer.toString(lMature));
							lResponse.setNicheCounts(Integer.toString(lNiche));
						}
					}
				}
				for (String key : map2.keySet()) {
					MarketOutlookResponse lOutlookResponse = new MarketOutlookResponse();
					if (mRequestModel.getRegionArray()[0].toString() != null
							&& !mRequestModel.getRegionArray()[0].toString().equals("all")) {
						lOutlookResponse.setRegion(map2.get(key).getRegion());
					} else {
						lOutlookResponse.setRegion(map2.get(key).getRegion());
					}
					if (mRequestModel.getCountryArray()[0].toString() != null
							&& !mRequestModel.getCountryArray()[0].toString().equals("all")) {
						lOutlookResponse.setCountry(map2.get(key).getCountry());
					}
					if (mRequestModel.getPosArray()[0].toString() != null
							&& !mRequestModel.getPosArray()[0].toString().equals("all")) {
						lOutlookResponse.setPos(map2.get(key).getPos());
					}
					if (mRequestModel.getCompartmentArray()[0].toString() != null
							&& !mRequestModel.getCompartmentArray()[0].toString().equals("all")) {
						lOutlookResponse.setCompartment(map2.get(key).getCompartment());
					}

					lOutlookResponse.setDeclineCounts(map2.get(key).getDeclineCounts());
					lOutlookResponse.setMatureCounts(map2.get(key).getMatureCounts());
					lOutlookResponse.setGrowingCounts(map2.get(key).getGrowingCounts());
					lOutlookResponse.setNicheCounts(map2.get(key).getNicheCounts());
					lMoResponseList.add(lOutlookResponse);
				}
				responseMarketOutlookMap.put("MarketOutlook", lMoResponseList);
			} catch (Exception e) {
				logger.error("getMarketoutlook-Exception", e);
			}
		}
		return responseMarketOutlookMap;
	}

	@Override
	public BasicDBObject getCompetitorBooking(RequestModel requestModel) {
		// It returns object as a list
		BasicDBObject CompetitorBookinglist = marketDao.getCompetitorBooking(requestModel);
		return CompetitorBookinglist;
	}

	// Need to comfirm with Aravind.
	@Override
	public Map<String, Object> getMarketOutlookDetails(RequestModel mRequestModel) {
		lGrowingList = new ArrayList<MarketOutlookModel>();
		lDeclineList = new ArrayList<MarketOutlookModel>();
		lMatureList = new ArrayList<MarketOutlookModel>();
		lNicheList = new ArrayList<MarketOutlookModel>();
		List<FilterModel> lMarketOutlookList = new ArrayList<FilterModel>();
		List<MarketOutlookModel> lMoList = new ArrayList<MarketOutlookModel>();
		Map<String, Object> responseMarketOutlookMap = new HashMap<String, Object>();
		ArrayList<DBObject> lMarketOutlookObj = marketDao.getMarketoutlook(mRequestModel);
		if (!lMarketOutlookObj.isEmpty()) {
			JSONArray lMarketOutlookData = new JSONArray(lMarketOutlookObj);
			try {
				if (lMarketOutlookData != null) {
					for (int i = 0; i < lMarketOutlookData.length(); i++) {
						JSONObject lJsonObj = lMarketOutlookData.getJSONObject(i);
						FilterModel lMarketOutlook = new FilterModel();
						if (lJsonObj.has("year") && lJsonObj.get("year") != null)
							lMarketOutlook.setYear(lJsonObj.get("year").toString());
						if (lJsonObj.has("month") && lJsonObj.get("month") != null)
							lMarketOutlook.setMonth(lJsonObj.get("month").toString());
						if (lJsonObj.has("region") && lJsonObj.get("region") != null)
							lMarketOutlook.setRegion(lJsonObj.get("region").toString());
						if (lJsonObj.has("country") && lJsonObj.get("country") != null)
							lMarketOutlook.setCountry(lJsonObj.get("country").toString());
						if (lJsonObj.has("pos") && lJsonObj.get("pos") != null)
							lMarketOutlook.setPos(lJsonObj.get("pos").toString());
						if (lJsonObj.has("compartment") && lJsonObj.get("compartment") != null)
							lMarketOutlook.setCompartment(lJsonObj.get("compartment").toString());
						if (lJsonObj.has("pax") && lJsonObj.get("pax") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("pax").toString()))
							lMarketOutlook.setHostPax(lJsonObj.get("pax").toString());
						if (lJsonObj.has("pax_1") && lJsonObj.get("pax_1") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("pax_1").toString()))
							lMarketOutlook.setHostPax_lastyr(lJsonObj.get("pax_1").toString());
						if (lJsonObj.has("revenue") && lJsonObj.get("revenue") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("revenue").toString()))
							lMarketOutlook.setFlownRevenue(lJsonObj.get("revenue").toString());
						float revenuelastyr = 0;
						if (lJsonObj.has("revenue_1") && lJsonObj.get("revenue_1") != null
								&& !"null".equalsIgnoreCase(lJsonObj.get("revenue_1").toString()))
							revenuelastyr = Float.parseFloat(lJsonObj.get("revenue_1").toString());
						lMarketOutlook.setFlownRevenue_lastyr(lJsonObj.get("revenue_1").toString());
						if (lJsonObj.has("MarketingCarrier1") && lJsonObj.get("MarketingCarrier1") != null)
							lMarketOutlook.setCarrier(lJsonObj.get("MarketingCarrier1").toString());
						StringBuilder lStr = CalculationUtil.getFilters(mRequestModel, lMarketOutlook);
						String keyBuilder = lStr.toString();
						if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
							keyBuilder = "null";
						}
						if ("network".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lMarketOutlook.setFilterKey(lMarketOutlook.getRegion());
								lStr = new StringBuilder();
								lStr.append(lMarketOutlook.getFilterKey());
							} else {
								lStr.append(lMarketOutlook.getCompartment());
							}
						} else if ("region".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lMarketOutlook
										.setFilterKey(lMarketOutlook.getRegion() + lMarketOutlook.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lMarketOutlook.getFilterKey());
							} else {
								lStr.append(lMarketOutlook.getCompartment());
							}
						} else if ("country".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lMarketOutlook.setFilterKey(lMarketOutlook.getRegion() + lMarketOutlook.getCountry()
										+ lMarketOutlook.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lMarketOutlook.getFilterKey());
							} else {
								lStr.append(lMarketOutlook.getCompartment());
							}
						} else if ("pos".equalsIgnoreCase(mRequestModel.getLevel())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lMarketOutlook.setFilterKey(lMarketOutlook.getRegion() + lMarketOutlook.getCountry()
										+ lMarketOutlook.getPos() + lMarketOutlook.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lMarketOutlook.getFilterKey());
							} else {
								lStr.append(lMarketOutlook.getCompartment());
							}
						}
						lMarketOutlook.setFilterKey(lStr.toString());
						lMarketOutlookList.add(lMarketOutlook);
					}
				}
				Map<String, MarketOutlookModel> map = new HashMap<String, MarketOutlookModel>();
				MarketOutlookModel lMOModel = null;
				for (FilterModel lMoObj : lMarketOutlookList) {
					if (!map.containsKey(lMoObj.getFilterKey())) {
						lMOModel = new MarketOutlookModel();
						lMOModel.setYear(lMoObj.getYear());
						lMOModel.setRegion(lMoObj.getRegion());
						lMOModel.setCountry(lMoObj.getCountry());
						lMOModel.setPos(lMoObj.getPos());
						lMOModel.setCompartment(lMoObj.getCompartment());
						lMOModel.setMarketingCarrier(lMoObj.getCarrier());
						if (lMoObj.getCarrier().equals("FZ")) {
							lMOModel.setPaxFZ((int) Float.parseFloat(lMoObj.getHostPax()));
							lMOModel.setTotalPaxFZ((int) Float.parseFloat(lMoObj.getHostPax()));
							lMOModel.setPax_1FZ((int) Float.parseFloat(lMoObj.getHostPax_lastyr()));
							lMOModel.setTotalPax_1FZ(lMOModel.getPax_1FZ());
						} else {
							lMOModel.setPaxFZ(0);
							lMOModel.setPax_1FZ(0);
						}
						lMOModel.setPax((int) Float.parseFloat(lMoObj.getHostPax()));
						lMOModel.setTotalPax(lMOModel.getPax());
						lMOModel.setPax_1((int) Float.parseFloat(lMoObj.getHostPax_lastyr()));
						lMOModel.setTotalPax_lastyr(lMOModel.getPax_1());
						lMOModel.setRevenue(Float.parseFloat(lMoObj.getFlownRevenue()));
						lMOModel.setTotalRevenue(lMOModel.getRevenue());
						lMOModel.setRevenue_1(Float.parseFloat(lMoObj.getFlownRevenue_lastyr()));
						lMOModel.setTotalRevenue_lastyr(lMOModel.getRevenue_1());
						lMOModel.setCombinationKey(lMoObj.getFilterKey());
						map.put(lMoObj.getFilterKey(), lMOModel);
					} else {
						for (String lKey : map.keySet()) {
							if (lMoObj.getFilterKey().equals(lKey)) {
								lMOModel = map.get(lKey);
							}
							int totalPax = (int) Float.parseFloat(lMoObj.getHostPax()) + lMOModel.getTotalPax();
							lMOModel.setTotalPax(totalPax);
							int totalPax_lastyr = (int) Float.parseFloat(lMoObj.getHostPax_lastyr())
									+ lMOModel.getTotalPax_lastyr();
							lMOModel.setTotalPax_lastyr(totalPax_lastyr);
							if (lMoObj.getCarrier().equals("FZ")) {
								int totalPaxFZ = (int) Float.parseFloat(lMoObj.getHostPax()) + lMOModel.getTotalPaxFZ();
								lMOModel.setTotalPaxFZ(totalPaxFZ);
								int totalPaxlastyrFZ = (int) Float.parseFloat(lMoObj.getHostPax_lastyr())
										+ lMOModel.getTotalPax_1FZ();
								lMOModel.setTotalPax_1FZ(totalPaxlastyrFZ);
							}
							float totalRevenue = Float.parseFloat(lMoObj.getFlownRevenue())
									+ lMOModel.getTotalRevenue();
							lMOModel.setTotalRevenue(totalRevenue);
							float totalRevenue_lastyr = Float.parseFloat(lMoObj.getFlownRevenue_lastyr())
									+ lMOModel.getTotalRevenue_lastyr();
							lMOModel.setTotalRevenue(totalRevenue_lastyr);
						}
					}
				}
				for (String key : map.keySet()) {
					lMOModel = new MarketOutlookModel();
					lMOModel.setCombinationKey(map.get(key).getCombinationKey());
					lMOModel.setYear(map.get(key).getYear());
					lMOModel.setMonth(map.get(key).getMonth());
					if (map.get(key).getCombinationKey().equals(map.get(key).getRegion())) {
						lMOModel.setRegion(map.get(key).getRegion());
					} else if (map.get(key).getCombinationKey()
							.equals(map.get(key).getRegion() + map.get(key).getCountry())) {
						lMOModel.setRegion(map.get(key).getRegion());
						lMOModel.setCountry(map.get(key).getCountry());
					} else if (map.get(key).getCombinationKey()
							.equals(map.get(key).getRegion() + map.get(key).getCountry() + map.get(key).getPos())) {
						lMOModel.setRegion(map.get(key).getRegion());
						lMOModel.setCountry(map.get(key).getCountry());
						lMOModel.setPos(map.get(key).getPos());
					} else {
						lMOModel.setRegion(map.get(key).getRegion());
						lMOModel.setCountry(map.get(key).getCountry());
						lMOModel.setPos(map.get(key).getPos());
						lMOModel.setCompartment(map.get(key).getCompartment());
					}
					int totalPax = map.get(key).getTotalPax();
					lMOModel.setTotalPax(totalPax);
					int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					lMOModel.setTotalPax_lastyr(totalPax_lastyr);
					lMOModel.setMarketingCarrier(map.get(key).getMarketingCarrier());
					if (lMOModel.getMarketingCarrier().equals("FZ")) {
						lMOModel.setTotalPaxFZ(map.get(key).getTotalPaxFZ());
						lMOModel.setTotalPax_1FZ(map.get(key).getTotalPax_1FZ());
					}
					float totalRevenue = map.get(key).getRevenue();
					lMOModel.setTotalRevenue(totalRevenue);
					float totalRevenue_lastyr = map.get(key).getRevenue_1();
					float lpaxVLYR = 0;
					int minThresholdValue = -5;
					int maxThresholdValue = 5;
					int declineCounts = 0;
					int matureCounts = 0;
					int growingCounts = 0;
					int nicheCounts = 0;
					if (totalPax_lastyr > 0) {
						lpaxVLYR = Math.round(CalculationUtil.calculateVLYR(totalPax, totalPax_lastyr));
					} else {
						lpaxVLYR = 0;
					}
					if (lpaxVLYR > maxThresholdValue) {
						growingCounts += 1;
						lGrowingList.add(lMOModel);
					} else if (lpaxVLYR < minThresholdValue) {
						declineCounts += 1;
						lDeclineList.add(lMOModel);
					} else if (-5 >= lpaxVLYR && lpaxVLYR <= 5) {
						matureCounts += 1;
						lMatureList.add(lMOModel);
					} else {
						growingCounts += 1;
						lGrowingList.add(lMOModel);
					}
					lMoList.add(lMOModel);
				}
				getGrowingMarketList(responseMarketOutlookMap);
				getDecliningMarketList(responseMarketOutlookMap);
				getMatureMarketList(responseMarketOutlookMap);
			} catch (Exception e) {
				logger.error("getMarketOutlookDetails-Exception", e);
			}
		}
		return responseMarketOutlookMap;
	}

	private void getGrowingMarketList(Map<String, Object> responseMarketOutlookMap) {
		MarketOutlookModel lResponse = null;
		List<MarketOutlookGrowingResponse> lGrowingMarketResponseList = new ArrayList<MarketOutlookGrowingResponse>();
		// Totals
		int totalPaxYTD = 0;
		int totalPaxLastYr = 0;
		int totalVLYR = 0;
		int totalMarketSizeYTD = 0;
		int totalMarketSizeLastYr = 0;
		float totalMarketSizeVLYR = 0;
		float totalRevenueYTD = 0;
		float totalRevenueLastYr = 0;
		float totalRevVLYR = 0;
		float totalMarketShareYTD = 0;
		float totalMarketSharelastyr = 0;
		float totalMarketShareVLYR = 0;
		Map<String, MarketOutlookModel> map2 = new HashMap<String, MarketOutlookModel>();
		for (MarketOutlookModel lMoObj2 : lGrowingList) {
			if (!map2.containsKey(lMoObj2.getCombinationKey())) {
				lResponse = new MarketOutlookModel();
				lResponse.setCombinationKey(lMoObj2.getCombinationKey());
				lResponse.setRegion(lMoObj2.getRegion());
				lResponse.setCountry(lMoObj2.getCountry());
				lResponse.setPos(lMoObj2.getPos());
				lResponse.setCompartment(lMoObj2.getCompartment());
				// Pax
				lResponse.setTotalPax(lMoObj2.getTotalPax());
				lResponse.setTotalPax_lastyr(lMoObj2.getTotalPax_lastyr());
				// Revenue
				lResponse.setTotalRevenue(lMoObj2.getTotalRevenue());
				lResponse.setTotalRevenue_lastyr(lMoObj2.getTotalRevenue_lastyr());
				// HostPax
				lResponse.setTotalPaxFZ(lMoObj2.getTotalPaxFZ());
				lResponse.setTotalPax_1FZ(lMoObj2.getTotalPax_1FZ());
				map2.put(lMoObj2.getCombinationKey(), lResponse);
			} else {
				for (String lKey : map2.keySet()) {
					if (lMoObj2.getRegion().equals(lKey)) {
						lResponse = map2.get(lKey);
					}
					// Pax
					int totalPax = lMoObj2.getPax() + lResponse.getTotalPax();
					lResponse.setTotalPax(totalPax);
					int totalPax_lastyr = lMoObj2.getPax_1() + lResponse.getTotalPax_lastyr();
					lResponse.setTotalPax_lastyr(totalPax_lastyr);
					// Host Pax
					int totalFZPax = lMoObj2.getPaxFZ() + lResponse.getTotalPaxFZ();
					lResponse.setTotalPaxFZ(totalFZPax);
					int totalFZPaxlastyr = lMoObj2.getPax_1FZ() + lResponse.getTotalPax_1FZ();
					lResponse.setTotalPax_1FZ(totalFZPaxlastyr);
					// Revenue
					float totalRevenue = lMoObj2.getRevenue() + lResponse.getTotalRevenue();
					lResponse.setTotalRevenue(totalRevenue);
					float totalRevenue_lastyr = lMoObj2.getRevenue_1() + lResponse.getTotalRevenue_lastyr();
					lResponse.setTotalRevenue_lastyr(totalRevenue_lastyr);
				}
			}
		}
		for (String key : map2.keySet()) {
			MarketOutlookGrowingResponse lGrowingResponse = new MarketOutlookGrowingResponse();
			lGrowingResponse.setRegion(map2.get(key).getRegion());
			lGrowingResponse.setCountry(map2.get(key).getCountry());
			lGrowingResponse.setPos(map2.get(key).getPos());
			lGrowingResponse.setCompartment(map2.get(key).getCompartment());
			// Calculating FZ pax
			int totalPaxFZ = map2.get(key).getTotalPaxFZ();
			lGrowingResponse.setPaxYTD(Integer.toString(totalPaxFZ));
			int totalPaxlastyrFZ = map2.get(key).getTotalPax_1FZ();
			lGrowingResponse.setTotalPax_lastyr(Integer.toString(totalPaxlastyrFZ));
			int lpaxFZVLYR = 0;
			if (totalPaxlastyrFZ > 0) {
				lpaxFZVLYR = (int) CalculationUtil.calculateVLYR(totalPaxFZ, totalPaxlastyrFZ);
			} else {
				lpaxFZVLYR = 0;
			}
			if (lpaxFZVLYR > 0) {
				lGrowingResponse.setPaxVLYR(Integer.toString(lpaxFZVLYR));
			} else {
				lGrowingResponse.setPaxVLYR("NA");
			}
			// Calculating Market Size
			int totalMarketSize = map2.get(key).getTotalPax();
			int totalMarketSize_lastyr = map2.get(key).getTotalPax_lastyr();
			lGrowingResponse.setMarketSizeYTD(Integer.toString(totalMarketSize));
			lGrowingResponse.setTotalMarketSize_lastyr(Integer.toString(totalMarketSize_lastyr));
			int lMarketSizeVLYR = 0;
			if (totalMarketSize_lastyr > 0) {
				lMarketSizeVLYR = (int) CalculationUtil.calculateVLYR(totalMarketSize, totalMarketSize_lastyr);
			} else {
				lMarketSizeVLYR = 0;
			}
			if (lMarketSizeVLYR > 0) {
				lGrowingResponse.setMarketSizeVLYR(Integer.toString(lMarketSizeVLYR));
			} else {
				lGrowingResponse.setMarketSizeVLYR("NA");
			}
			// Calculating Revenue
			float totalSalesRevenue = map2.get(key).getTotalRevenue();
			float totalRevenue_lastyr = map2.get(key).getTotalRevenue_lastyr();
			lGrowingResponse.setTotalRevenue_lastyr(Float.toString(totalRevenue_lastyr));
			float lRevenueVLYR = 0;
			if (totalRevenue_lastyr > 0) {
				lRevenueVLYR = CalculationUtil.calculateVLYR(totalSalesRevenue, totalRevenue_lastyr);
			} else {
				lRevenueVLYR = 0;
			}
			lGrowingResponse.setRevenueYTD(Float.toString(totalSalesRevenue));
			if (lRevenueVLYR > 0) {
				lGrowingResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
			} else {
				lGrowingResponse.setRevenueVLYR("NA");
			}
			// Calculating Market Share
			float marketShareFZ = (float) totalPaxFZ / totalMarketSize;
			float marketShareFZ_lastyr = 0;
			if (totalPaxlastyrFZ > 0 && totalMarketSize_lastyr > 0) {
				marketShareFZ_lastyr = (float) totalPaxlastyrFZ / totalMarketSize_lastyr;
			} else {
				marketShareFZ_lastyr = 0;
			}
			float lMarketShareVLYR = 0;
			if (marketShareFZ_lastyr > 0) {
				lMarketShareVLYR = CalculationUtil.calculateVLYR(marketShareFZ, marketShareFZ_lastyr);
			} else {
				lMarketShareVLYR = 0;
			}
			if (lMarketShareVLYR > 0) {
				lGrowingResponse.setMarketShareVLYR(Float.toString(lRevenueVLYR));
			} else {
				lGrowingResponse.setMarketShareVLYR("NA");
			}
			lGrowingResponse.setMarketShareYTD(Float.toString(marketShareFZ * 100));
			lGrowingResponse.setTotalMarketShare_lastyr(Float.toString(marketShareFZ_lastyr * 100));
			lGrowingResponse.setMarketShareVLYR(Float.toString(lMarketShareVLYR));
			lGrowingMarketResponseList.add(lGrowingResponse);
		}
		// Totals
		List<MarketOutlookDetails> lTotalsList = new ArrayList<MarketOutlookDetails>();
		/* Computing totals ---Start */
		for (MarketOutlookGrowingResponse lObj : lGrowingMarketResponseList) {
			totalPaxYTD += Integer.parseInt(lObj.getPaxYTD());
			totalPaxLastYr += Integer.parseInt(lObj.getTotalPax_lastyr());
			totalRevenueYTD += Float.parseFloat(lObj.getRevenueYTD());
			totalRevenueLastYr += Float.parseFloat(lObj.getTotalRevenue_lastyr());
			totalMarketSizeYTD += Integer.parseInt(lObj.getMarketSizeYTD());
			totalMarketSizeLastYr += Integer.parseInt(lObj.getTotalMarketSize_lastyr());
		}
		float marketShare = (float) (totalPaxYTD) / (float) totalMarketSizeYTD;
		totalMarketShareYTD = CalculationUtil.round(marketShare, 4);
		totalMarketSharelastyr = (float) (totalPaxLastYr) / (float) totalMarketSizeLastYr;
		if (totalPaxLastYr > 0) {
			totalVLYR = (int) CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxLastYr);
		} else {
			totalVLYR = 0;
		}
		if (totalMarketSizeLastYr > 0) {
			totalMarketSizeVLYR = (int) CalculationUtil.calculateVLYR(totalMarketSizeYTD, totalMarketSizeLastYr);
		} else {
			totalMarketSizeVLYR = 0;
		}
		if (totalRevenueLastYr > 0) {
			totalRevVLYR = (int) CalculationUtil.calculateVLYR(totalRevenueYTD, totalRevenueLastYr);
		} else {
			totalRevVLYR = 0;
		}
		if (totalMarketSharelastyr > 0) {
			totalMarketShareVLYR = (int) CalculationUtil.calculateVLYR(totalMarketShareYTD, totalMarketSharelastyr);
		} else {
			totalMarketShareVLYR = 0;
		}
		MarketOutlookDetails lTotals = new MarketOutlookDetails();
		lTotals.setTotalPaxYTD(Integer.toString(totalPaxYTD));
		if (totalPaxLastYr > 0) {
			lTotals.setTotalPaxVLYR(Integer.toString(totalVLYR));
		} else {
			lTotals.setTotalPaxVLYR("NA");
		}
		lTotals.setTotalRevenueYTD(Float.toString(totalRevenueYTD));
		if (totalRevVLYR > 0) {
			lTotals.setTotalRevenueVLYR(Float.toString(totalRevVLYR));
		} else {
			lTotals.setTotalRevenueVLYR("NA");
		}
		lTotals.setTotalMarketSizeYTD(Integer.toString(totalMarketSizeYTD));
		if (totalMarketSizeVLYR > 0) {
			lTotals.setTotalMarketSizeVLYR(Float.toString(totalMarketSizeVLYR));
		} else {
			lTotals.setTotalMarketSizeVLYR("NA");
		}
		lTotals.setTotalMarketShareYTD(Float.toString(totalMarketShareYTD));
		if (totalMarketShareVLYR > 0) {
			lTotals.setTotalMarketShareVLYR(Float.toString(totalMarketShareVLYR));
		} else {
			lTotals.setTotalMarketShareVLYR("NA");
		}
		lTotalsList.add(lTotals);
		/* Computing totals ---End */
		responseMarketOutlookMap.put("GrowingMarketGridTotals", lTotalsList);
		responseMarketOutlookMap.put("GrowingMarketGrid", lGrowingMarketResponseList);
	}

	private void getDecliningMarketList(Map<String, Object> responseMarketOutlookMap) {
		MarketOutlookModel lResponse = null;
		List<MarketOutlookGrowingResponse> lDecliningMarketResponseList = new ArrayList<MarketOutlookGrowingResponse>();
		// Totals
		int totalPaxYTD = 0;
		int totalPaxLastYr = 0;
		int totalVLYR = 0;
		int totalMarketSizeYTD = 0;
		int totalMarketSizeLastYr = 0;
		float totalMarketSizeVLYR = 0;
		float totalRevenueYTD = 0;
		float totalRevenueLastYr = 0;
		float totalRevVLYR = 0;
		float totalMarketShareYTD = 0;
		float totalMarketSharelastyr = 0;
		float totalMarketShareVLYR = 0;
		Map<String, MarketOutlookModel> map2 = new HashMap<String, MarketOutlookModel>();
		for (MarketOutlookModel lMoObj2 : lDeclineList) {
			if (!map2.containsKey(lMoObj2.getCombinationKey())) {
				lResponse = new MarketOutlookModel();
				lResponse.setCombinationKey(lMoObj2.getCombinationKey());
				lResponse.setRegion(lMoObj2.getRegion());
				lResponse.setCountry(lMoObj2.getCountry());
				lResponse.setPos(lMoObj2.getPos());
				lResponse.setCompartment(lMoObj2.getCompartment());
				lResponse.setTotalPax(lMoObj2.getTotalPax());
				lResponse.setTotalPax_lastyr(lMoObj2.getTotalPax_lastyr());
				lResponse.setTotalRevenue(lMoObj2.getTotalRevenue());
				lResponse.setTotalRevenue_lastyr(lMoObj2.getTotalRevenue_lastyr());
				lResponse.setTotalPaxFZ(lMoObj2.getTotalPaxFZ());
				lResponse.setTotalPax_1FZ(lMoObj2.getTotalPax_1FZ());
				map2.put(lMoObj2.getCombinationKey(), lResponse);
			} else {
				for (String lKey : map2.keySet()) {
					if (lMoObj2.getRegion().equals(lKey)) {
						lResponse = map2.get(lKey);
					}
					int totalPax = lMoObj2.getPax() + lResponse.getTotalPax();
					lResponse.setTotalPax(totalPax);
					int totalPax_lastyr = lMoObj2.getPax_1() + lResponse.getTotalPax_lastyr();
					lResponse.setTotalPax_lastyr(totalPax_lastyr);
					int totalFZPax = lMoObj2.getPaxFZ() + lResponse.getTotalPaxFZ();
					lResponse.setTotalPaxFZ(totalFZPax);
					int totalFZPaxlastyr = lMoObj2.getPax_1FZ() + lResponse.getTotalPax_1FZ();
					lResponse.setTotalPax_1FZ(totalFZPaxlastyr);
					float totalRevenue = lMoObj2.getRevenue() + lResponse.getTotalRevenue();
					lResponse.setTotalRevenue(totalRevenue);
					float totalRevenue_lastyr = lMoObj2.getRevenue_1() + lResponse.getTotalRevenue_lastyr();
					lResponse.setTotalRevenue_lastyr(totalRevenue_lastyr);
				}
			}
		}
		for (String key : map2.keySet()) {
			MarketOutlookGrowingResponse lDecliningResponse = new MarketOutlookGrowingResponse();
			lDecliningResponse.setRegion(map2.get(key).getRegion());
			lDecliningResponse.setCountry(map2.get(key).getCountry());
			lDecliningResponse.setPos(map2.get(key).getPos());
			lDecliningResponse.setCompartment(map2.get(key).getCompartment());
			// Calculating FZ pax
			int totalPaxFZ = map2.get(key).getTotalPaxFZ();
			lDecliningResponse.setPaxYTD(Integer.toString(totalPaxFZ));
			int totalPaxlastyrFZ = map2.get(key).getTotalPax_1FZ();
			lDecliningResponse.setTotalPax_lastyr(Integer.toString(totalPaxlastyrFZ));
			int lpaxFZVLYR = 0;
			if (totalPaxlastyrFZ > 0) {
				lpaxFZVLYR = (int) CalculationUtil.calculateVLYR(totalPaxFZ, totalPaxlastyrFZ);
			} else {
				lpaxFZVLYR = 0;
			}
			if (lpaxFZVLYR > 0) {
				lDecliningResponse.setPaxVLYR(Integer.toString(lpaxFZVLYR));
			} else {
				lDecliningResponse.setPaxVLYR("NA");
			}
			// Calculating Market Size
			int totalMarketSize = map2.get(key).getTotalPax();
			int totalMarketSize_lastyr = map2.get(key).getTotalPax_lastyr();
			lDecliningResponse.setMarketSizeYTD(Integer.toString(totalMarketSize));
			lDecliningResponse.setTotalMarketSize_lastyr(Integer.toString(totalMarketSize_lastyr));
			int lMarketSizeVLYR = 0;
			if (totalMarketSize_lastyr > 0) {
				lMarketSizeVLYR = (int) CalculationUtil.calculateVLYR(totalMarketSize, totalMarketSize_lastyr);
			} else {
				lMarketSizeVLYR = 0;
			}
			if (lMarketSizeVLYR > 0) {
				lDecliningResponse.setMarketSizeVLYR(Integer.toString(lMarketSizeVLYR));
			} else {
				lDecliningResponse.setMarketSizeVLYR("NA");
			}
			// Calculating Revenue
			float totalSalesRevenue = map2.get(key).getTotalRevenue();
			float totalRevenue_lastyr = map2.get(key).getTotalRevenue_lastyr();
			lDecliningResponse.setTotalRevenue_lastyr(Float.toString(totalRevenue_lastyr));
			float lRevenueVLYR = 0;
			if (totalRevenue_lastyr > 0) {
				lRevenueVLYR = CalculationUtil.calculateVLYR(totalSalesRevenue, totalRevenue_lastyr);
			} else {
				lRevenueVLYR = 0;
			}
			lDecliningResponse.setRevenueYTD(Float.toString(totalSalesRevenue));
			if (lRevenueVLYR > 0) {
				lDecliningResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
			} else {
				lDecliningResponse.setRevenueVLYR("NA");
			}
			// Calculating Market Share
			float marketShareFZ = (float) totalPaxFZ / totalMarketSize;
			float marketShareFZ_lastyr = 0;
			if (totalPaxlastyrFZ > 0 && totalMarketSize_lastyr > 0) {
				marketShareFZ_lastyr = (float) totalPaxlastyrFZ / totalMarketSize_lastyr;
			} else {
				marketShareFZ_lastyr = 0;
			}
			float lMarketShareVLYR = 0;
			if (marketShareFZ_lastyr > 0) {
				lMarketShareVLYR = CalculationUtil.calculateVLYR(marketShareFZ, marketShareFZ_lastyr);
			} else {
				lMarketShareVLYR = 0;
			}
			if (lMarketShareVLYR > 0) {
				lDecliningResponse.setMarketShareVLYR(Float.toString(lRevenueVLYR));
			} else {
				lDecliningResponse.setMarketShareVLYR("NA");
			}
			lDecliningResponse.setMarketShareYTD(Float.toString(marketShareFZ * 100));
			lDecliningResponse.setTotalMarketShare_lastyr(Float.toString(marketShareFZ_lastyr * 100));
			lDecliningResponse.setMarketShareVLYR(Float.toString(lMarketShareVLYR));
			lDecliningMarketResponseList.add(lDecliningResponse);
		}
		// Totals
		List<MarketOutlookDetails> lTotalsList = new ArrayList<MarketOutlookDetails>();
		/* Computing totals ---Start */
		for (MarketOutlookGrowingResponse lObj : lDecliningMarketResponseList) {
			totalPaxYTD += Integer.parseInt(lObj.getPaxYTD());
			totalPaxLastYr += Integer.parseInt(lObj.getTotalPax_lastyr());
			totalRevenueYTD += Float.parseFloat(lObj.getRevenueYTD());
			totalRevenueLastYr += Float.parseFloat(lObj.getTotalRevenue_lastyr());
			totalMarketSizeYTD += Integer.parseInt(lObj.getMarketSizeYTD());
			totalMarketSizeLastYr += Integer.parseInt(lObj.getTotalMarketSize_lastyr());
		}
		float marketShare = (float) (totalPaxYTD) / (float) totalMarketSizeYTD;
		totalMarketShareYTD = CalculationUtil.round(marketShare, 4);
		totalMarketSharelastyr = (float) (totalPaxLastYr) / (float) totalMarketSizeLastYr;
		if (totalPaxLastYr > 0) {
			totalVLYR = (int) CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxLastYr);
		} else {
			totalVLYR = 0;
		}
		if (totalMarketSizeLastYr > 0) {
			totalMarketSizeVLYR = (int) CalculationUtil.calculateVLYR(totalMarketSizeYTD, totalMarketSizeLastYr);
		} else {
			totalMarketSizeVLYR = 0;
		}
		if (totalRevenueLastYr > 0) {
			totalRevVLYR = (int) CalculationUtil.calculateVLYR(totalRevenueYTD, totalRevenueLastYr);
		} else {
			totalRevVLYR = 0;
		}
		if (totalMarketSharelastyr > 0) {
			totalMarketShareVLYR = (int) CalculationUtil.calculateVLYR(totalMarketShareYTD, totalMarketSharelastyr);
		} else {
			totalMarketShareVLYR = 0;
		}
		MarketOutlookDetails lTotals = new MarketOutlookDetails();
		lTotals.setTotalPaxYTD(Integer.toString(totalPaxYTD));
		if (totalPaxLastYr > 0) {
			lTotals.setTotalPaxVLYR(Integer.toString(totalVLYR));
		} else {
			lTotals.setTotalPaxVLYR("NA");
		}
		lTotals.setTotalRevenueYTD(Float.toString(totalRevenueYTD));
		if (totalRevVLYR > 0) {
			lTotals.setTotalRevenueVLYR(Float.toString(totalRevVLYR));
		} else {
			lTotals.setTotalRevenueVLYR("NA");
		}
		lTotals.setTotalMarketSizeYTD(Integer.toString(totalMarketSizeYTD));
		if (totalMarketSizeVLYR > 0) {
			lTotals.setTotalMarketSizeVLYR(Float.toString(totalMarketSizeVLYR));
		} else {
			lTotals.setTotalMarketSizeVLYR("NA");
		}
		lTotals.setTotalMarketShareYTD(Float.toString(totalMarketShareYTD));
		if (totalMarketShareVLYR > 0) {
			lTotals.setTotalMarketShareVLYR(Float.toString(totalMarketShareVLYR));
		} else {
			lTotals.setTotalMarketShareVLYR("NA");
		}
		lTotalsList.add(lTotals);
		/* Computing totals ---End */
		responseMarketOutlookMap.put("DecliningMarketGridTotals", lTotalsList);
		responseMarketOutlookMap.put("DecliningMarketGrid", lDecliningMarketResponseList);
	}

	private void getMatureMarketList(Map<String, Object> responseMarketOutlookMap) {
		MarketOutlookModel lResponse = null;
		List<MarketOutlookGrowingResponse> lMatureMarketResponseList = new ArrayList<MarketOutlookGrowingResponse>();
		// Totals
		int totalPaxYTD = 0;
		int totalPaxLastYr = 0;
		int totalVLYR = 0;
		int totalMarketSizeYTD = 0;
		int totalMarketSizeLastYr = 0;
		float totalMarketSizeVLYR = 0;
		float totalRevenueYTD = 0;
		float totalRevenueLastYr = 0;
		float totalRevVLYR = 0;
		float totalMarketShareYTD = 0;
		float totalMarketSharelastyr = 0;
		float totalMarketShareVLYR = 0;
		Map<String, MarketOutlookModel> map2 = new HashMap<String, MarketOutlookModel>();
		for (MarketOutlookModel lMoObj2 : lMatureList) {
			if (!map2.containsKey(lMoObj2.getCombinationKey())) {
				lResponse = new MarketOutlookModel();
				lResponse.setCombinationKey(lMoObj2.getCombinationKey());
				lResponse.setRegion(lMoObj2.getRegion());
				lResponse.setCountry(lMoObj2.getCountry());
				lResponse.setPos(lMoObj2.getPos());
				lResponse.setCompartment(lMoObj2.getCompartment());
				lResponse.setTotalPax(lMoObj2.getTotalPax());
				lResponse.setTotalPax_lastyr(lMoObj2.getTotalPax_lastyr());
				lResponse.setTotalRevenue(lMoObj2.getTotalRevenue());
				lResponse.setTotalRevenue_lastyr(lMoObj2.getTotalRevenue_lastyr());
				lResponse.setTotalPaxFZ(lMoObj2.getTotalPaxFZ());
				lResponse.setTotalPax_1FZ(lMoObj2.getTotalPax_1FZ());
				map2.put(lMoObj2.getCombinationKey(), lResponse);
			} else {
				for (String lKey : map2.keySet()) {
					if (lMoObj2.getRegion().equals(lKey)) {
						lResponse = map2.get(lKey);
					}
					int totalPax = lMoObj2.getPax() + lResponse.getTotalPax();
					lResponse.setTotalPax(totalPax);
					int totalPax_lastyr = lMoObj2.getPax_1() + lResponse.getTotalPax_lastyr();
					lResponse.setTotalPax_lastyr(totalPax_lastyr);
					int totalFZPax = lMoObj2.getPaxFZ() + lResponse.getTotalPaxFZ();
					lResponse.setTotalPaxFZ(totalFZPax);
					int totalFZPaxlastyr = lMoObj2.getPax_1FZ() + lResponse.getTotalPax_1FZ();
					lResponse.setTotalPax_1FZ(totalFZPaxlastyr);
					float totalRevenue = lMoObj2.getRevenue() + lResponse.getTotalRevenue();
					lResponse.setTotalRevenue(totalRevenue);
					float totalRevenue_lastyr = lMoObj2.getRevenue_1() + lResponse.getTotalRevenue_lastyr();
					lResponse.setTotalRevenue_lastyr(totalRevenue_lastyr);
				}
			}
		}
		for (String key : map2.keySet()) {
			MarketOutlookGrowingResponse lMatureResponse = new MarketOutlookGrowingResponse();
			lMatureResponse.setRegion(map2.get(key).getRegion());
			lMatureResponse.setCountry(map2.get(key).getCountry());
			lMatureResponse.setPos(map2.get(key).getPos());
			lMatureResponse.setCompartment(map2.get(key).getCompartment());
			// Calculating FZ pax
			int totalPaxFZ = map2.get(key).getTotalPaxFZ();
			lMatureResponse.setPaxYTD(Integer.toString(totalPaxFZ));
			int totalPax_1FZ = map2.get(key).getTotalPax_1FZ();
			lMatureResponse.setTotalPax_lastyr(Integer.toString(totalPax_1FZ));
			int lpaxFZVLYR = 0;
			if (totalPax_1FZ > 0) {
				lpaxFZVLYR = (int) CalculationUtil.calculateVLYR(totalPaxFZ, totalPax_1FZ);
			} else {
				lpaxFZVLYR = 0;
			}
			if (lpaxFZVLYR > 0) {
				lMatureResponse.setPaxVLYR(Integer.toString(lpaxFZVLYR));
			} else {
				lMatureResponse.setPaxVLYR("NA");
			}
			// Calculating Market Size
			int totalMarketSize = map2.get(key).getTotalPax();
			int totalMarketSize_lastyr = map2.get(key).getTotalPax_lastyr();
			lMatureResponse.setMarketSizeYTD(Integer.toString(totalMarketSize));
			lMatureResponse.setTotalMarketSize_lastyr(Integer.toString(totalMarketSize_lastyr));
			int lMarketSizeVLYR = 0;
			if (totalMarketSize_lastyr > 0) {
				lMarketSizeVLYR = (int) CalculationUtil.calculateVLYR(totalMarketSize, totalMarketSize_lastyr);
			} else {
				lMarketSizeVLYR = 0;
			}
			if (lMarketSizeVLYR > 0) {
				lMatureResponse.setMarketSizeVLYR(Integer.toString(lMarketSizeVLYR));
			} else {
				lMatureResponse.setMarketSizeVLYR("NA");
			}
			// Calculating Revenue
			float totalSalesRevenue = map2.get(key).getTotalRevenue();
			float totalRevenue_lastyr = map2.get(key).getTotalRevenue_lastyr();
			lMatureResponse.setTotalRevenue_lastyr(Float.toString(totalRevenue_lastyr));
			float lRevenueVLYR = 0;
			if (totalRevenue_lastyr > 0) {
				lRevenueVLYR = CalculationUtil.calculateVLYR(totalSalesRevenue, totalRevenue_lastyr);
			} else {
				lRevenueVLYR = 0;
			}
			lMatureResponse.setRevenueYTD(Float.toString(totalSalesRevenue));
			if (lRevenueVLYR > 0) {
				lMatureResponse.setRevenueVLYR(Float.toString(lRevenueVLYR));
			} else {
				lMatureResponse.setRevenueVLYR("NA");
			}
			// Calculating Market Share
			float marketShareFZ = (float) totalPaxFZ / totalMarketSize;
			float marketShareFZ_lastyr = 0;
			if (totalPax_1FZ > 0 && totalMarketSize_lastyr > 0) {
				marketShareFZ_lastyr = (float) totalPax_1FZ / totalMarketSize_lastyr;
			} else {
				marketShareFZ_lastyr = 0;
			}
			float lMarketShareVLYR = 0;
			if (marketShareFZ_lastyr > 0) {
				lMarketShareVLYR = CalculationUtil.calculateVLYR(marketShareFZ, marketShareFZ_lastyr);
			} else {
				lMarketShareVLYR = 0;
			}
			if (lMarketShareVLYR > 0) {
				lMatureResponse.setMarketShareVLYR(Float.toString(lRevenueVLYR));
			} else {
				lMatureResponse.setMarketShareVLYR("NA");
			}
			lMatureResponse.setMarketShareYTD(Float.toString(marketShareFZ * 100));
			lMatureResponse.setTotalMarketShare_lastyr(Float.toString(marketShareFZ_lastyr * 100));
			lMatureResponse.setMarketShareVLYR(Float.toString(lMarketShareVLYR));
			lMatureMarketResponseList.add(lMatureResponse);
		}
		// Totals
		List<MarketOutlookDetails> lTotalsList = new ArrayList<MarketOutlookDetails>();
		/* Computing totals ---Start */
		for (MarketOutlookGrowingResponse lObj : lMatureMarketResponseList) {
			totalPaxYTD += Integer.parseInt(lObj.getPaxYTD());
			totalPaxLastYr += Integer.parseInt(lObj.getTotalPax_lastyr());
			totalRevenueYTD += Float.parseFloat(lObj.getRevenueYTD());
			totalRevenueLastYr += Float.parseFloat(lObj.getTotalRevenue_lastyr());
			totalMarketSizeYTD += Integer.parseInt(lObj.getMarketSizeYTD());
			totalMarketSizeLastYr += Integer.parseInt(lObj.getTotalMarketSize_lastyr());
		}
		float marketShare = (float) (totalPaxYTD) / (float) totalMarketSizeYTD;
		totalMarketShareYTD = CalculationUtil.round(marketShare, 4);
		totalMarketSharelastyr = (float) (totalPaxLastYr) / (float) totalMarketSizeLastYr;
		if (totalPaxLastYr > 0) {
			totalVLYR = (int) CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxLastYr);
		} else {
			totalVLYR = 0;
		}
		if (totalMarketSizeLastYr > 0) {
			totalMarketSizeVLYR = (int) CalculationUtil.calculateVLYR(totalMarketSizeYTD, totalMarketSizeLastYr);
		} else {
			totalMarketSizeVLYR = 0;
		}
		if (totalRevenueLastYr > 0) {
			totalRevVLYR = (int) CalculationUtil.calculateVLYR(totalRevenueYTD, totalRevenueLastYr);
		} else {
			totalRevVLYR = 0;
		}
		if (totalMarketSharelastyr > 0) {
			totalMarketShareVLYR = (int) CalculationUtil.calculateVLYR(totalMarketShareYTD, totalMarketSharelastyr);
		} else {
			totalMarketShareVLYR = 0;
		}
		MarketOutlookDetails lTotals = new MarketOutlookDetails();
		lTotals.setTotalPaxYTD(Integer.toString(totalPaxYTD));
		if (totalPaxLastYr > 0) {
			lTotals.setTotalPaxVLYR(Integer.toString(totalVLYR));
		} else {
			lTotals.setTotalPaxVLYR("NA");
		}
		lTotals.setTotalRevenueYTD(Float.toString(totalRevenueYTD));
		if (totalRevVLYR > 0) {
			lTotals.setTotalRevenueVLYR(Float.toString(totalRevVLYR));
		} else {
			lTotals.setTotalRevenueVLYR("NA");
		}
		lTotals.setTotalMarketSizeYTD(Integer.toString(totalMarketSizeYTD));
		if (totalMarketSizeVLYR > 0) {
			lTotals.setTotalMarketSizeVLYR(Float.toString(totalMarketSizeVLYR));
		} else {
			lTotals.setTotalMarketSizeVLYR("NA");
		}
		lTotals.setTotalMarketShareYTD(Float.toString(totalMarketShareYTD));
		if (totalMarketShareVLYR > 0) {
			lTotals.setTotalMarketShareVLYR(Float.toString(totalMarketShareVLYR));
		} else {
			lTotals.setTotalMarketShareVLYR("NA");
		}
		lTotalsList.add(lTotals);
		/* Computing totals ---End */
		responseMarketOutlookMap.put("MatureMarketGridTotals", lTotalsList);
		responseMarketOutlookMap.put("MatureMarketGrid", lMatureMarketResponseList);
	}

	@Override
	public BasicDBObject getOppurtunities(RequestModel requestModel) {
		// It returns object as a list
		BasicDBObject Oppurtunitieslist = marketDao.getOppurtunities(requestModel);
		return Oppurtunitieslist;
	}

	@Override
	public BasicDBObject getHostBooking(RequestModel requestModel) {
		BasicDBObject HostBookinglist = marketDao.getHostBooking(requestModel);
		return HostBookinglist;
	}

	@Override
	public List<BspOverview> getBSPOverviewDetails(RequestModel requestModel) {
		return null;
	}

	@Override
	public Map<String, Object> getMarketbarometerDetails(RequestModel mRequestModel) {
		Map<String, Object> marketBarometerMap = new HashMap<String, Object>();
		Map<String, MarketBarometerDetails> mbFriendMap = new HashMap<String, MarketBarometerDetails>();
		Map<String, MarketBarometerDetails> mbFoeMap = new HashMap<String, MarketBarometerDetails>();
		ArrayList<DBObject> marketData = marketDao.getMarketBarometer(mRequestModel);
		int totalAgentCounts = 0;
		int totalFriendsCounts = 0;
		int totalFoesCounts = 0;
		try {
			if (marketData != null) {
				JSONArray data = new JSONArray(marketData);
				if (data != null) {
					for (int i = 0; i < data.length(); i++) {
						JSONObject jsonObj = data.getJSONObject(i);
						String airline = "-";
						if (jsonObj.has("airline") && jsonObj.get("airline") != null
								&& !"null".equalsIgnoreCase(jsonObj.get("airline").toString()))
							airline = jsonObj.getString("airline");
						if (airline.equals("FZ")) {
							String region = "-";
							if (jsonObj.get("region") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("region").toString()))
								region = jsonObj.getString("region");
							String country = "-";
							if (jsonObj.get("country") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("country").toString()))
								country = jsonObj.getString("country");
							String pos = "-";
							if (jsonObj.get("pos") != null && !"null".equalsIgnoreCase(jsonObj.get("pos").toString()))
								pos = jsonObj.getString("pos");
							String od = "-";
							if (jsonObj.get("od") != null && !"null".equalsIgnoreCase(jsonObj.get("od").toString()))
								od = jsonObj.getString("od");
							String compartment = "-";
							if (jsonObj.has("compartment") && jsonObj.get("compartment") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("compartment").toString()))
								compartment = jsonObj.getString("compartment");
							String agentName = "-";
							if (jsonObj.has("agent") && jsonObj.get("agent") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("agent").toString()))
								agentName = jsonObj.getString("agent");
							String agentType = "-";
							if (jsonObj.get("friend_or_foe_rev") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("friend_or_foe_rev").toString()))
								agentType = jsonObj.getString("friend_or_foe_rev");
							Double revenue = 0D;
							if (jsonObj.get("rev_airline_agent") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("rev_airline_agent").toString()))
								revenue = Double.parseDouble(jsonObj.get("rev_airline_agent").toString());
							Double revenueLastYear = 0D;
							if (jsonObj.get("rev_airline_agent_lyr") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("rev_airline_agent_lyr").toString()))
								revenueLastYear = Double.parseDouble(jsonObj.get("rev_airline_agent_lyr").toString());
							Double marketShare = 0D;
							if (jsonObj.get("agent_to_airline_share") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("agent_to_airline_share").toString()))
								marketShare = jsonObj.getDouble("agent_to_airline_share");
							Double pi = 0D;
							if (jsonObj.get("pi_rev") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("pi_rev").toString()))
								pi = jsonObj.getDouble("pi_rev");
							Double revenueVLYR = 0D;
							if (revenueLastYear != 0) {
								revenueVLYR = ((revenue - revenueLastYear) / revenueLastYear) * 100;
							}
							String combinationKey = region + country + pos + od + compartment + agentName;
							String origin = "-";
							String destination = "-";
							if (!od.equals("-")) {
								origin = od.substring(0, 3);
								destination = od.substring(3);
							}
							if (agentType.equals("friend")) {
								MarketBarometerDetails mb = new MarketBarometerDetails();
								mb.setRegion(region);
								mb.setCountry(country);
								mb.setPos(pos);
								mb.setOrigin(origin);
								mb.setDestination(destination);
								mb.setCompartment(compartment);
								mb.setCombinationKey(combinationKey);
								mb.setAgentName(agentName);
								mb.setAirline(airline);
								mb.setRevenue(revenue);
								mb.setRevenueVLYR(revenueVLYR);
								mb.setMarketShare(marketShare);
								mb.setHostPI(pi);
								mbFriendMap.put(combinationKey, mb);
							} else {
								MarketBarometerDetails mb = new MarketBarometerDetails();
								mb.setRegion(region);
								mb.setCountry(country);
								mb.setPos(pos);
								mb.setOrigin(origin);
								mb.setDestination(destination);
								mb.setCompartment(compartment);
								mb.setCombinationKey(combinationKey);
								mb.setAgentName(agentName);
								mb.setAirline(airline);
								mb.setRevenue(revenue);
								mb.setRevenueVLYR(revenueVLYR);
								mb.setMarketShare(marketShare);
								mb.setHostPI(pi);
								mbFoeMap.put(combinationKey, mb);
							}
						}
					}
					for (int i = 0; i < data.length(); i++) {
						JSONObject jsonObj = data.getJSONObject(i);
						String airline = "-";
						if (jsonObj.has("airline") && jsonObj.get("airline") != null
								&& !"null".equalsIgnoreCase(jsonObj.get("airline").toString()))
							airline = jsonObj.getString("airline");
						if (airline.equals("EK") || airline.equals("QR") || airline.equals("LH")) {
							String region = "-";
							if (jsonObj.get("region") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("region").toString()))
								region = jsonObj.getString("region");
							String country = "-";
							if (jsonObj.get("country") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("country").toString()))
								country = jsonObj.getString("country");
							String pos = "-";
							if (jsonObj.get("pos") != null && !"null".equalsIgnoreCase(jsonObj.get("pos").toString()))
								pos = jsonObj.getString("pos");
							String od = "-";
							if (jsonObj.get("od") != null && !"null".equalsIgnoreCase(jsonObj.get("od").toString()))
								od = jsonObj.getString("od");
							String compartment = "-";
							if (jsonObj.has("compartment") && jsonObj.get("compartment") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("compartment").toString()))
								compartment = jsonObj.getString("compartment");
							String agentName = "-";
							if (jsonObj.has("agent") && jsonObj.get("agent") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("agent").toString()))
								agentName = jsonObj.getString("agent");

							Double pi = 0D;
							if (jsonObj.get("pi_rev") != null
									&& !"null".equalsIgnoreCase(jsonObj.get("pi_rev").toString()))
								pi = jsonObj.getDouble("pi_rev");
							String combinationKey = region + country + pos + od + compartment + agentName;
							if (mbFriendMap.containsKey(combinationKey)) {
								MarketBarometerDetails mb = mbFriendMap.get(combinationKey);
								if (airline.equals("EK"))
									mb.setComp1PI(pi);
								else if (airline.equals("QR"))
									mb.setComp2PI(pi);
								else if (airline.equals("LH"))
									mb.setComp3PI(pi);
							} else if (mbFoeMap.containsKey(combinationKey)) {
								MarketBarometerDetails mb = mbFoeMap.get(combinationKey);
								if (airline.equals("EK"))
									mb.setComp1PI(pi);
								else if (airline.equals("QR"))
									mb.setComp2PI(pi);
								else if (airline.equals("LH"))
									mb.setComp3PI(pi);
							}
						}
					}
				}
				List<MarketBarometerDetails> mbFriendsList = new ArrayList<MarketBarometerDetails>(
						mbFriendMap.values());
				List<MarketBarometerDetails> mbFoesList = new ArrayList<MarketBarometerDetails>(mbFoeMap.values());
				marketBarometerMap.put("marketBarometerFriendsMap", mbFriendsList);
				marketBarometerMap.put("marketBarometerFoesMap", mbFoesList);
				System.out.println("data processing completed in service method");
			}
		} catch (Exception e) {
			logger.error("getMarketbarometerDetails-Exception", e);
		}
		return marketBarometerMap;
	}

	@Override
	public Map<String, Object> getMarketOutlookReport(RequestModel requestModel) {
		// TODO Auto-generated method stub
		ArrayList<DBObject> resultObjList = marketDao.getMarketOutlookReport(requestModel);
		JSONArray data = null;
		if (resultObjList != null)
			data = new JSONArray(resultObjList);

		Map<String, Object> marketResponseMap = new HashMap<String, Object>();
		MarketOutlookReport marketOutlook = new MarketOutlookReport();

		int nicheMarketCount = 0;
		int growingMarketCount = 0;
		int decliningMarketCount = 0;
		int matureMarketCount = 0;

		List<Object> growingMarketList = new ArrayList<Object>();
		List<Object> decliningMarketList = new ArrayList<Object>();
		List<Object> matureMarketList = new ArrayList<Object>();
		List<Object> nicheMarketList = new ArrayList<Object>();
		if (data != null) {
			for (int i = 0; i < data.length(); i++) {

				JSONObject jsonObj = data.getJSONObject(i);
				marketOutlook = new MarketOutlookReport();

				JSONArray airlineDetails = new JSONArray(jsonObj.get("airline_details").toString());

				List<Object> airlineList = new ArrayList<Object>();

				for (int j = 0; j < airlineDetails.length(); j++) {

					JSONObject airlineDetailsObj = (JSONObject) airlineDetails.get(j);
					Map<String, Object> airlineMap = new HashMap<String, Object>();

					int airlinePax = 0;
					if (airlineDetailsObj.has("airline_pax")
							&& !airlineDetailsObj.get("airline_pax").toString().contains("zero")) {

						airlinePax = Math.round(Float.parseFloat(airlineDetailsObj.get("airline_pax").toString()));
						airlineMap.put("airline_pax", airlinePax);

					}

					int airlinePaxlastyr = 0;
					if (airlineDetailsObj.has("airline_pax_1")
							&& !airlineDetailsObj.get("airline_pax_1").toString().contains("zero")) {

						airlinePaxlastyr = Math
								.round(Float.parseFloat(airlineDetailsObj.get("airline_pax_1").toString()));
						airlineMap.put("airline_pax_1", airlinePaxlastyr);

					}

					if (airlineDetailsObj.has("market_share")
							&& !airlineDetailsObj.get("market_share").toString().contains("zero"))
						airlineMap.put("market_share",
								Math.round(Float.parseFloat(airlineDetailsObj.get("market_share").toString())));

					if (airlineDetailsObj.has("market_share_1")
							&& !airlineDetailsObj.get("market_share_1").toString().contains("zero"))
						airlineMap.put("market_share_1",
								Math.round(Float.parseFloat(airlineDetailsObj.get("market_share_1").toString())));

					if (airlineDetailsObj.has("market_share_vlyr")
							&& !airlineDetailsObj.get("market_share_vlyr").toString().contains("zero"))
						airlineMap.put("market_share_vlyr",
								Math.round(Float.parseFloat(airlineDetailsObj.get("market_share_vlyr").toString())));

					int airlinPaxVLYR = 0;
					if (airlinePaxlastyr != 0)
						airlinPaxVLYR = (airlinePax - airlinePaxlastyr) * 100 / airlinePaxlastyr;
					airlineMap.put("pax_vlyr", airlinPaxVLYR);

					if (airlineDetailsObj.has("airline"))
						airlineMap.put("airline", airlineDetailsObj.get("airline").toString());

					if (jsonObj.has("market_share")
							&& !airlineDetailsObj.get("market_share").toString().contains("zero"))
						airlineMap.put("market_share",
								Math.round(Float.parseFloat(airlineDetailsObj.get("market_share").toString())));

					if (jsonObj.has("market_share_1")
							&& !airlineDetailsObj.get("market_share_1").toString().contains("zero"))
						airlineMap.put("market_share_1",
								Math.round(Float.parseFloat(airlineDetailsObj.get("market_share_1").toString())));

					airlineList.add(airlineMap);

				}

				marketOutlook.setAirline(airlineList);

				if (jsonObj.has("region"))
					marketOutlook.setRegion(jsonObj.get("region").toString());

				if (jsonObj.has("country"))
					marketOutlook.setCountry(jsonObj.get("country").toString());

				if (jsonObj.has("market_size_vlyr")
						&& !jsonObj.get("market_size_vlyr").toString().contains("not available"))
					marketOutlook.setMarketSizeVLYR(
							Math.round(Float.parseFloat(jsonObj.get("market_size_vlyr").toString())));

				if (jsonObj.has("pos"))
					marketOutlook.setPos(jsonObj.get("pos").toString());

				if (jsonObj.has("od")) {
					String od = jsonObj.get("od").toString();
					marketOutlook.setOrigin(Utility.getOrignDestFromOD(od).getOrigin());
					marketOutlook.setDestination(Utility.getOrignDestFromOD(od).getDestination());
				}

				if (jsonObj.has("compartment"))
					marketOutlook.setCompartment(jsonObj.get("compartment").toString());

				if (jsonObj.has("market_size") && !jsonObj.get("market_size").toString().contains("not available"))
					marketOutlook.setMarketSize(Math.round(Float.parseFloat(jsonObj.get("market_size").toString())));

				if (jsonObj.has("compartment"))
					marketOutlook.setCompartment(jsonObj.get("compartment").toString());

				if (jsonObj.has("market_size_1") && !jsonObj.get("market_size_1").toString().contains("not available"))
					marketOutlook
							.setMarketSize_1(Math.round(Float.parseFloat(jsonObj.get("market_size_1").toString())));

				if (jsonObj.has("niche_market")) {
					if ("yes".equalsIgnoreCase(jsonObj.get("niche_market").toString())) {
						nicheMarketCount++;
						nicheMarketList.add(marketOutlook);

					}
				}
				if (jsonObj.has("market_condition")) {
					if (!jsonObj.get("market_condition").toString().contains("not available")) {
						if ("growing".equalsIgnoreCase(jsonObj.get("market_condition").toString())) {
							growingMarketCount++;
							growingMarketList.add(marketOutlook);
						} else if ("mature".equalsIgnoreCase(jsonObj.get("market_condition").toString())) {
							matureMarketCount++;
							matureMarketList.add(marketOutlook);
						} else if ("declining".equalsIgnoreCase(jsonObj.get("market_condition").toString())) {
							decliningMarketCount++;
							decliningMarketList.add(marketOutlook);
						}
					}
				}

			}
		}

		marketResponseMap.put("GrowingMarketTab", growingMarketList);
		marketResponseMap.put("DecliningMarketTab", decliningMarketList);
		marketResponseMap.put("MatureMarketTab", matureMarketList);
		marketResponseMap.put("NicheMarketTab", nicheMarketList);
		marketResponseMap.put("Tiles_NicheMarket", nicheMarketCount);
		marketResponseMap.put("Tiles_GrowingMarket", growingMarketCount);
		marketResponseMap.put("Tiles_DecliningMarket", decliningMarketCount);
		marketResponseMap.put("Tiles_MatureMarket", matureMarketCount);

		return marketResponseMap;
	}

	@Override
	public Object getAirPortPaxTraffic(RequestModel requestModel) {
		if (requestModel.getRegionArray() == null && requestModel.getPosArray() == null
				&& requestModel.getCountryArray() == null)
			// requestModel.setRegionArray(new String[] { "Other Regions" });
			requestModel.setRegionArray(new String[] {});
		else if (requestModel.getRegionArray() == null && requestModel.getPosArray() != null
				&& requestModel.getCountryArray() == null)
			requestModel.setCountryArray(
					mainDashboardDao.getCountryName(requestModel, "POS_CD", requestModel.getPosArray()));
		return marketDao.getAirPortPaxTraffic(requestModel);
	}

	@Override
	public List<Object> getOAGUnreservedRoutes(RequestModel requestModel) {
		return marketDao.getOAGUnreservedRoutes();
	}

	@Override
	public List<Object> getIATAGlobalOutlook(RequestModel requestModel) {
		// TODO Auto-generated method stub
		return marketDao.getIATAGlobalOutlook(requestModel);
	}

	@Override
	public List<Object> getAirlinePax(RequestModel requestModel) {
		// TODO Auto-generated method stub
		return marketDao.getAirlinePax(requestModel);
	}

	@Override
	public Map<String, Object> getMarketOutlookDescription(RequestModel requestModel) {
		// TODO Auto-generated method stub
		Map<String, Object> markeOutlookMap = new HashMap<String, Object>();
		List<Object> marketOtlookDescriptionList = marketDao.getMarketOutlookDescription(requestModel);
		markeOutlookMap.put("MarketOutlookGrid", marketOtlookDescriptionList);
		return markeOutlookMap;
	}

	@Override
	public Map<String, Object> getMarketOutlookDhbGrid(RequestModel requestModel) {

		ArrayList<DBObject> resultObjList = marketDao.getMarketOutlookReport(requestModel);
		JSONArray data = null;
		if (resultObjList != null)
			data = new JSONArray(resultObjList);

		Map<String, Object> marketResponseMap = new HashMap<String, Object>();
		List<Object> outlookList = new ArrayList<Object>();
		if (data != null) {
			for (int i = 0; i < data.length(); i++) {
				JSONObject jsonObj = data.getJSONObject(i);

				MarketOutlookDhb outlook = null;

				String region = null;
				String compartment = null;
				if (jsonObj.has("region"))
					region = jsonObj.get("region").toString();
				if (jsonObj.has("compartment"))
					compartment = jsonObj.get("compartment").toString();

				String key = region + compartment;

				if (marketResponseMap.containsKey(key)
						&& ((MarketOutlookDhb) marketResponseMap.get(key)).getKey().equalsIgnoreCase(key)) {

					outlook = (MarketOutlookDhb) marketResponseMap.get(key);

					int growingCount = 0;
					int decliningCount = 0;
					int matureCount = 0;
					int neechCount = 0;

					if (jsonObj.has("region"))
						outlook.setRegion(jsonObj.get("region").toString());

					if (jsonObj.has("compartment"))
						outlook.setCompartment(jsonObj.get("compartment").toString());

					outlook.setKey(key);

					if (jsonObj.has("market_condition")) {
						String marketCondition = jsonObj.get("market_condition").toString();

						if ("growing".equalsIgnoreCase(marketCondition))
							growingCount++;
						else if ("declining".equalsIgnoreCase(marketCondition))
							decliningCount++;
						else if ("mature".equalsIgnoreCase(marketCondition))
							matureCount++;

					}

					if (jsonObj.has("niche_market")) {
						if ("yes".equalsIgnoreCase(jsonObj.get("niche_market").toString()))
							neechCount++;
					}

					outlook.setGrowingCount(outlook.getGrowingCount() + growingCount);
					outlook.setDecliningCount(outlook.getDecliningCount() + decliningCount);
					outlook.setMatureCount(outlook.getMatureCount() + matureCount);
					outlook.setNicheCount(outlook.getNicheCount() + neechCount);

					marketResponseMap.put(key, outlook);

				} else {

					outlook = new MarketOutlookDhb();

					int growingCount = 0;
					int decliningCount = 0;
					int matureCount = 0;
					int neechCount = 0;

					if (jsonObj.has("region"))
						outlook.setRegion(jsonObj.get("region").toString());

					if (jsonObj.has("compartment"))
						outlook.setCompartment(jsonObj.get("compartment").toString());

					outlook.setKey(key);

					if (jsonObj.has("market_condition")) {
						String marketCondition = jsonObj.get("market_condition").toString();

						if ("growing".equalsIgnoreCase(marketCondition))
							growingCount++;
						else if ("declining".equalsIgnoreCase(marketCondition))
							decliningCount++;
						else if ("mature".equalsIgnoreCase(marketCondition))
							matureCount++;

					}

					if (jsonObj.has("niche_market")) {
						if ("yes".equalsIgnoreCase(jsonObj.get("niche_market").toString()))
							neechCount++;
					}

					outlook.setGrowingCount(growingCount);
					outlook.setDecliningCount(decliningCount);
					outlook.setMatureCount(matureCount);
					outlook.setNicheCount(neechCount);

					marketResponseMap.put(key, outlook);

				}

			}
		}

		Set<String> outlookSet = marketResponseMap.keySet();

		for (String s : outlookSet) {

			outlookList.add(marketResponseMap.get(s));

		}

		Map<String, Object> marketConditonMap = new HashMap<String, Object>();
		marketConditonMap.put("marketCondition", outlookList);

		return marketConditonMap;
	}
}