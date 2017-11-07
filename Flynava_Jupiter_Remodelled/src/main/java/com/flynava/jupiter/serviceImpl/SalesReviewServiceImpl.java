package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoImpl.CompetitorAnalysisDaoImpl;
import com.flynava.jupiter.daoInterface.SalesReviewDao;
import com.flynava.jupiter.model.FilterModel;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.SalesReviewTopTenOd;
import com.flynava.jupiter.model.SalesReviewTopTenOdTotalResponse;
import com.flynava.jupiter.serviceInterface.SalesReviewService;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Utility;
import com.mongodb.DBObject;

@Service
public class SalesReviewServiceImpl implements SalesReviewService {

	@Autowired
	SalesReviewDao mSalesReviewDao;
	
	private static final Logger logger = Logger.getLogger(SalesReviewServiceImpl.class);

	@Override
	public Map<String, Object> getTopTenOd(RequestModel pRequestModel) {

		// total
		float totalpaxVLYR = 0;
		float totalpaxVTGT = 0;
		float totalrevenueVLYR = 0;
		float totalrevenueVTGT = 0;
		float totalYieldVLYR = 0;
		float totalavgfare = 0;
		double distance = 0;
		JSONArray flownrevenueArray = null;
		JSONArray lTargetRevenueArray = null;
		JSONArray lPaxTargetArray = null;
		JSONArray oDDistanceArray = null;
		List<FilterModel> lTopTenList = new ArrayList<FilterModel>();
		List<SalesReviewTopTenOdTotalResponse> lTotalsList = new ArrayList<SalesReviewTopTenOdTotalResponse>();
		List<SalesReviewTopTenOd> lODList = new ArrayList<SalesReviewTopTenOd>();
		List<SalesReviewTopTenOd> lTopODList = new ArrayList<SalesReviewTopTenOd>();
		Map<String, Object> responseTopTenODMap = new HashMap<String, Object>();
		FilterModel lTopOd = new FilterModel();
		ArrayList<DBObject> lTopTenOdObj = mSalesReviewDao.getTopTenOd(pRequestModel);
		if (!lTopTenOdObj.isEmpty()) {
			JSONArray lTopTenOdData = new JSONArray(lTopTenOdObj);
			try {
				if (lTopTenOdData != null) {
					for (int i = 0; i < lTopTenOdData.length(); i++) {

						JSONObject lTopODJsonObj = lTopTenOdData.getJSONObject(i);
						lTopOd = new FilterModel();
						System.out.println("lPriceJSONObj:  " + lTopODJsonObj);
						if (lTopODJsonObj.has("od") && lTopODJsonObj.get("od") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("od").toString())) {
							lTopOd.setOd(lTopODJsonObj.get("od").toString());

						}
						if (lTopODJsonObj.has("RBD") && lTopODJsonObj.get("RBD") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("RBD").toString())) {
							lTopOd.setRbd(lTopODJsonObj.get("RBD").toString());

						}
						if (lTopODJsonObj.has("fare_basis") && lTopODJsonObj.get("fare_basis") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("fare_basis").toString())) {
							lTopOd.setFareBasis(lTopODJsonObj.get("fare_basis").toString());

						}
						if (lTopODJsonObj.has("pax") && lTopODJsonObj.get("pax") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pax").toString())) {
							lTopOd.setPax(Integer.parseInt(lTopODJsonObj.get("pax").toString()));

						}
						if (lTopODJsonObj.has("pax_1") && lTopODJsonObj.get("pax_1") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pax_1").toString())) {
							lTopOd.setPaxlastyear(Float.parseFloat(lTopODJsonObj.get("pax_1").toString()));

						}
						if (lTopODJsonObj.has("region") && lTopODJsonObj.get("region") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("region").toString())) {
							lTopOd.setRegion(lTopODJsonObj.get("region").toString());

						}
						if (lTopODJsonObj.has("country") && lTopODJsonObj.get("country") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("country").toString())) {
							lTopOd.setCountry(lTopODJsonObj.get("country").toString());

						}
						if (lTopODJsonObj.has("pos") && lTopODJsonObj.get("pos") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pos").toString())) {
							lTopOd.setPos(lTopODJsonObj.get("pos").toString());

						}
						if (lTopODJsonObj.has("compartment") && lTopODJsonObj.get("compartment") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("compartment").toString())) {
							lTopOd.setCompartment(lTopODJsonObj.get("compartment").toString());

						}
						if (lTopODJsonObj.has("currency") && lTopODJsonObj.get("currency") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("currency").toString())) {
							lTopOd.setCurrency(lTopODJsonObj.get("currency").toString());

						}
						if (lTopODJsonObj.has("fare") && lTopODJsonObj.get("fare") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("fare").toString())) {
							lTopOd.setFares(Float.parseFloat(lTopODJsonObj.get("fare").toString()));

						}
						if (lTopODJsonObj.has("revenue") && lTopODJsonObj.get("revenue") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("revenue").toString())) {
							lTopOd.setRevenue(Float.parseFloat(lTopODJsonObj.get("revenue").toString()));

						}
						if (lTopODJsonObj.has("revenue_1") && lTopODJsonObj.get("revenue_1") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("revenue_1").toString())) {
							lTopOd.setRevenuelastyr(Float.parseFloat(lTopODJsonObj.get("revenue_1").toString()));

						}
						double flownrevenue = 0;
						if (lTopODJsonObj.has("flown_revenue") && lTopODJsonObj.get("flown_revenue") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("flown_revenue").toString())) {
							flownrevenueArray = new JSONArray(lTopODJsonObj.get("flown_revenue").toString());
							if (flownrevenueArray != null) {
								if (flownrevenueArray.length() > 0) {
									flownrevenue = Utility.findSum(flownrevenueArray);
									lTopOd.setFlown_revenue((float) flownrevenue);
								}
							}

						}
						if (lTopODJsonObj.has("target_revenue") && lTopODJsonObj.get("target_revenue") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("target_revenue").toString())) {
							lTargetRevenueArray = new JSONArray(lTopODJsonObj.get("target_revenue").toString());
							Double targetRevenue = Utility.findSum(lTargetRevenueArray);
							lTopOd.setTargetRevenue(targetRevenue.toString());
						} else {
							lTopOd.setTargetRevenue("0");
						}
						if (lTopODJsonObj.has("pax_target") && lTopODJsonObj.get("pax_target") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pax_target").toString())) {
							lPaxTargetArray = new JSONArray(lTopODJsonObj.get("pax_target").toString());

							Double targetPax = Utility.findSum(lPaxTargetArray);
							lTopOd.setTargetPax(targetPax.toString());
						} else {
							lTopOd.setTargetPax("0");
						}
						if (lTopODJsonObj.has("od_distance") && lTopODJsonObj.get("od_distance") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("od_distance").toString())) {
							oDDistanceArray = new JSONArray(lTopODJsonObj.get("od_distance").toString());
							if (oDDistanceArray != null) {
								if (oDDistanceArray.length() > 0) {
									distance = Utility.findSum(oDDistanceArray);
									lTopOd.setOd_Distance((float) distance);
								}
							}
						}
						StringBuilder lStr = CalculationUtil.getFilters(pRequestModel, lTopOd);
						String keyBuilder = lStr.toString();
						if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
							keyBuilder = "null";
						}
						if ("Global Head".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(lTopOd.getOd());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getOd());
							}
						} else if ("Region Head".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(lTopOd.getOd() + lTopOd.getRegion());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getOd());
							}
						} else if ("Country Head".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(lTopOd.getOd() + lTopOd.getRegion() + lTopOd.getCountry());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getOd());
							}
						} else if ("Pricing Analyst".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(
										lTopOd.getOd() + lTopOd.getRegion() + lTopOd.getCountry() + lTopOd.getPos());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getOd());
							}
						}
						lTopOd.setFilterKey(lStr.toString());
						System.out.println("lMS " + lTopOd.getFilterKey());
						lTopTenList.add(lTopOd);
					}
					Map<String, SalesReviewTopTenOd> map = new HashMap<String, SalesReviewTopTenOd>();
					SalesReviewTopTenOd lTopTenOD = null;
					if (lTopTenList.size() > 0) {
						for (FilterModel lObj : lTopTenList) {
							if (!map.containsKey(lObj.getFilterKey())) {
								lTopTenOD = new SalesReviewTopTenOd();
								lTopTenOD.setCombinationKey(lObj.getFilterKey());
								lTopTenOD.setRegion(lObj.getRegion());
								lTopTenOD.setCountry(lObj.getCountry());
								lTopTenOD.setPos(lObj.getPos());
								lTopTenOD.setOd(lObj.getOd());
								lTopTenOD.setOrigin(lObj.getOd().substring(0, 3));
								lTopTenOD.setDestination(lObj.getOd().substring(3, 6));
								lTopTenOD.setCompartment(lObj.getCompartment());
								// total pax and last yr
								int totalPax = lObj.getPax();
								float totalPaxlastyr = lObj.getPaxlastyear();
								lTopTenOD.setTotalPax(totalPax);
								lTopTenOD.setTotalPaxlastyr(totalPaxlastyr);
								// targetpax
								float targetPax = Float.parseFloat(lObj.getTargetPax());
								lTopTenOD.setTargetpax(targetPax);
								// total revenue and last yr
								float totalRevenue = (lObj.getRevenue());
								float totalRevenuelastyr = lObj.getRevenuelastyr();
								float totalflownrevenue = lObj.getFlown_revenue();
								lTopTenOD.setTotalrevenue(totalRevenue);
								lTopTenOD.setTotalrevenuelastyr(totalRevenuelastyr);
								lTopTenOD.setTotalFlownRevenue(totalflownrevenue);
								// target revenue
								float targetRevenue = Float.parseFloat(lObj.getTargetRevenue());
								lTopTenOD.setTargetRevenue(targetRevenue);

								// total od distance
								float od_distance = lObj.getOd_Distance();
								lTopTenOD.setOdDistance(od_distance);
								map.put(lObj.getFilterKey(), lTopTenOD);
							} else {
								for (String lKey : map.keySet()) {
									if (lObj.getFilterKey().equals(lKey)) {
										lTopTenOD = map.get(lKey);
									}
								}
								// total pax and last yr
								float totalPax = lObj.getPax() + lTopTenOD.getTotalPax();
								float totalPaxlastyr = lObj.getPaxlastyear() + lTopTenOD.getTotalPaxlastyr();
								lTopTenOD.setTotalPax(totalPax);
								lTopTenOD.setTotalPaxlastyr(totalPaxlastyr);
								// total target pax
								float targetpax = Float.parseFloat(lObj.getTargetPax() + lTopTenOD.getTargetpax());
								lTopTenOD.setTargetpax(targetpax);
								// total revenue and last yr
								float totalRevenue = (lObj.getRevenue() + lTopTenOD.getTotalrevenue());
								float totalRevenuelastyr = lObj.getRevenuelastyr() + lTopTenOD.getTotalrevenuelastyr();
								float totalflownrevenue = lObj.getFlown_revenue() + lTopTenOD.getTotalFlownRevenue();
								lTopTenOD.setTotalrevenue(totalRevenue);
								lTopTenOD.setTotalrevenuelastyr(totalRevenuelastyr);
								lTopTenOD.setTotalFlownRevenue(totalflownrevenue);
								// total od distance
								float od_distance = lObj.getOd_Distance() + lTopTenOD.getOdDistance();
								lTopTenOD.setOdDistance(od_distance);
								// total target trevenue
								float targetrevenue = Float
										.parseFloat(lObj.getTargetRevenue() + lTopTenOD.getTargetRevenue());
								lTopTenOD.setTargetRevenue(targetrevenue);

							}
						}

					}
					for (String key : map.keySet()) {
						lTopTenOD = new SalesReviewTopTenOd();
						lTopTenOD.setCombinationKey(map.get(key).getCombinationKey());
						if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
							if (pRequestModel.getRegionArray()[0].toString() != null
									&& !pRequestModel.getRegionArray()[0].toString().equals("null")) {
								lTopTenOD.setRegion(map.get(key).getRegion());
							}
						}
						if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
							if (pRequestModel.getCountryArray()[0].toString() != null
									&& !pRequestModel.getCountryArray()[0].toString().equals("null")) {
								lTopTenOD.setCountry(map.get(key).getCountry());
							}
						}
						if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {
							if (pRequestModel.getPosArray()[0].toString() != null
									&& !pRequestModel.getPosArray()[0].toString().equals("null")) {
								lTopTenOD.setPos(map.get(key).getPos());
							}
						}
						if (pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getCompartmentArray().length > 0) {
							if (pRequestModel.getCompartmentArray()[0].toString() != null
									&& !pRequestModel.getCompartmentArray()[0].toString().equals("null")) {
								lTopTenOD.setCompartment(map.get(key).getCompartment());
							}
						}
						lTopTenOD.setOd(map.get(key).getOd());
						lTopTenOD.setOrigin(map.get(key).getOrigin());
						lTopTenOD.setDestination(map.get(key).getDestination());
						lTopTenOD.setCompartment(map.get(key).getCompartment());
						//
						// total pax and last yr
						float totalPax = map.get(key).getTotalPax();
						float totalPax_lastyr = map.get(key).getTotalPaxlastyr();
						lTopTenOD.setPax(totalPax);
						lTopTenOD.setTotalPaxlastyr(totalPax_lastyr);
						// target pax
						lTopTenOD.setTargetpax(map.get(key).getTargetpax());
						// pax vlyr
						float lpaxVLYR = 0;
						if (totalPax_lastyr > 0) {
							lpaxVLYR = (int) CalculationUtil.calculateVLYR(totalPax, totalPax_lastyr);
							lTopTenOD.setPaxVLYR(Float.toString(lpaxVLYR));
						} else {
							lTopTenOD.setPaxVLYR(("_"));
						}

						lTopTenOD.setPaxVTGT("_");
						// }

						// total revenue and last yr
						float totalrevenue = map.get(key).getTotalrevenue();
						float totalFlownRevenue = map.get(key).getTotalFlownRevenue();
						float totalrevenuelastyr = map.get(key).getTotalrevenuelastyr();
						lTopTenOD.setTotalrevenue(totalrevenue);
						lTopTenOD.setTotalFlownRevenue(totalFlownRevenue);
						lTopTenOD.setTotalrevenuelastyr(totalrevenuelastyr);
						// target revenue
						lTopTenOD.setTargetRevenue(map.get(key).getTargetRevenue());
						// revenue vlyr
						float lrevenueVLYR = 0;
						if (totalrevenuelastyr > 0) {
							lrevenueVLYR = CalculationUtil.calculateVLYR(totalrevenue, totalrevenuelastyr);
							lTopTenOD.setRevenueVLYR(Float.toString(lrevenueVLYR));
						} else {
							lTopTenOD.setRevenueVLYR("_");
						}
						// revenue VTGT
						// target are been adding as per maria

						float lrevenueVTGT = 0;
						if (map.get(key).getTargetRevenue() > 0) {
							lrevenueVTGT = CalculationUtil.calculateVTGT(totalrevenue, totalrevenuelastyr);

							lTopTenOD.setRevenueVTGT(Float.toString(lrevenueVTGT));
						} else {
							lTopTenOD.setRevenueVTGT("_");
						}
						// yield
						float od_distance = map.get(key).getOdDistance();
						float yield = 0;
						yield = (totalrevenue * 100) / (od_distance * totalPax);
						float yieldlastyr = 0;
						yieldlastyr = (totalrevenuelastyr * 100) / (od_distance * totalPax_lastyr);
						lTopTenOD.setTotalYield(yield);
						lTopTenOD.setYield(yield);
						lTopTenOD.setTotalYieldlastyr(yieldlastyr);
						// yield VLYR
						if (yieldlastyr > 0) {
							float yieldVLYr = CalculationUtil.calculateVLYR(yield, yieldlastyr);
							lTopTenOD.setYieldVLYR(Float.toString(yieldVLYr));
						}
						// avg fare
						float avgfare = CalculationUtil.calculateavgfare(totalrevenue, totalPax);
						lTopTenOD.setAvgfare(avgfare);
						lODList.add(lTopTenOD);

					}
				}

				Collections.sort(lODList, new lTopTenOd());
				if (lODList.size() > 10) {
					lTopODList.addAll(lODList.subList(0, 10));
				} else {
					lTopODList.addAll(lODList.subList(0, lODList.size()));
				}
				// totals
				float totalPaxYTD = 0;
				float totalPaxLastYr = 0;
				float totalPaxTarget = 0;
				float totalRevenueYTd = 0;
				float totalFlownRevenue = 0;
				float totalrevenuelastyr = 0;
				float totalrevenuetarget = 0;
				float totalyield = 0;
				float totalyieldlastyr = 0;

				for (SalesReviewTopTenOd lObj : lODList) {
					totalPaxYTD += lObj.getPax();
					totalPaxLastYr += lObj.getTotalPaxlastyr();
					totalPaxTarget += lObj.getTargetpax();
					totalRevenueYTd += lObj.getTotalrevenue();
					totalFlownRevenue += lObj.getFlownRevenue();
					totalrevenuelastyr += lObj.getTotalrevenuelastyr();
					totalrevenuetarget += lObj.getTargetRevenue();
					totalyield += lObj.getTotalYield();
					totalyieldlastyr += lObj.getTotalYieldlastyr();
				}
				SalesReviewTopTenOdTotalResponse lTotals = new SalesReviewTopTenOdTotalResponse();
				// total revenue
				if (totalPaxLastYr > 0) {
					totalpaxVLYR = (int) CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxLastYr);
					lTotals.setTotalPaxVLYR(Float.toString(totalpaxVLYR));
				} else {
					lTotals.setTotalPaxVLYR("_");
				}
				/*
				 * if (totalPaxTarget > 0) { totalpaxVTGT = (int)
				 * CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxTarget);
				 * lTotals.setTotalPaxVTGT(Float.toString(totalpaxVTGT)); } else
				 * {
				 */
				lTotals.setTotalPaxVTGT("_");
				// }

				lTotals.setTotalPaxYTD(Float.toString(totalPaxYTD));
				// total revenue
				if (totalrevenuelastyr > 0) {
					totalrevenueVLYR = (int) CalculationUtil.calculateVLYR(totalRevenueYTd, totalrevenuelastyr);
					lTotals.setTotalrevenueVLYR(Float.toString(totalrevenueVLYR));
				} else {
					lTotals.setTotalrevenueVLYR("_");
				}

				lTotals.setTotalrevenueVTGT("_");
				// }
				lTotals.setTotalrevenueYTD(Float.toString(totalRevenueYTd));
				lTotals.setTotalflownrevenueYtd(Float.toString(totalFlownRevenue));
				// total yield
				if (totalyieldlastyr > 0) {
					totalYieldVLYR = (int) CalculationUtil.calculateVLYR(totalyield, totalyieldlastyr);
					lTotals.setTotalyieldVLYR(Float.toString(totalYieldVLYR));
				} else {
					lTotals.setTotalyieldVLYR("_");
				}
				lTotals.setTotalyieldYTD(Float.toString(totalyield));
				// total avg fare
				if (totalPaxYTD > 0) {
					totalavgfare = CalculationUtil.calculateavgfare(totalRevenueYTd, totalPaxYTD);
					lTotals.setTotalavgfare(Float.toString(totalavgfare));
				} else {
					lTotals.setTotalavgfare("_");
				}

				lTotalsList.add(lTotals);

			} catch (Exception e) {
				logger.error("getTopTenOd-Exception", e);
			}
			responseTopTenODMap.put("TopTenODSpikesTotals", lTotalsList);
			responseTopTenODMap.put("TopTenOD", lODList);
		} else {
			responseTopTenODMap.put("TopTenODSpikesTotals", null);
			responseTopTenODMap.put("TopTenOD", null);
		}
		return responseTopTenODMap;
	}

	class lTopTenOd implements Comparator<SalesReviewTopTenOd> {
		@Override
		public int compare(SalesReviewTopTenOd o1, SalesReviewTopTenOd o2) {
			if (o1.getTotalrevenue() > 0) {
				if (o1.getTotalrevenue() < o2.getTotalrevenue()) {
					return 1;
				} else {
					return -1;
				}
			}
			return 0;
		}
	}

	@Override
	public Map<String, Object> getTopTenAgent(RequestModel pRequestModel) {

		// total
		float totalpaxVLYR = 0;
		float totalpaxVTGT = 0;
		float totalrevenueVLYR = 0;
		float totalrevenueVTGT = 0;
		float totalYieldVLYR = 0;
		float totalavgfare = 0;
		double distance = 0;
		JSONArray oDDistanceArray = null;
		JSONArray flownrevenueArray = null;
		JSONArray lTargetRevenueArray = null;
		JSONArray lPaxTargetArray = null;
		List<FilterModel> lTopTenList = new ArrayList<FilterModel>();
		List<SalesReviewTopTenOdTotalResponse> lTotalsList = new ArrayList<SalesReviewTopTenOdTotalResponse>();
		List<SalesReviewTopTenOd> lODList = new ArrayList<SalesReviewTopTenOd>();
		List<SalesReviewTopTenOd> lTopODList = new ArrayList<SalesReviewTopTenOd>();
		Map<String, Object> responseTopTenAgentMap = new HashMap<String, Object>();
		FilterModel lTopOd = new FilterModel();
		ArrayList<DBObject> lTopTenOdObj = mSalesReviewDao.getTopTenOd(pRequestModel);
		if (!lTopTenOdObj.isEmpty()) {
			JSONArray lTopTenOdData = new JSONArray(lTopTenOdObj);
			try {
				if (lTopTenOdData != null) {
					for (int i = 0; i < lTopTenOdData.length(); i++) {

						JSONObject lTopODJsonObj = lTopTenOdData.getJSONObject(i);
						lTopOd = new FilterModel();
						System.out.println("lPriceJSONObj:  " + lTopODJsonObj);
						if (lTopODJsonObj.has("agent") && lTopODJsonObj.get("agent") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("agent").toString())) {
							lTopOd.setAgent(lTopODJsonObj.get("agent").toString());

						}
						if (lTopODJsonObj.has("od") && lTopODJsonObj.get("od") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("od").toString())) {
							lTopOd.setOd(lTopODJsonObj.get("od").toString());

						}
						if (lTopODJsonObj.has("RBD") && lTopODJsonObj.get("RBD") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("RBD").toString())) {
							lTopOd.setRbd(lTopODJsonObj.get("RBD").toString());

						}
						if (lTopODJsonObj.has("fare_basis") && lTopODJsonObj.get("fare_basis") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("fare_basis").toString())) {
							lTopOd.setFareBasis(lTopODJsonObj.get("fare_basis").toString());

						}
						if (lTopODJsonObj.has("pax") && lTopODJsonObj.get("pax") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pax").toString())) {
							lTopOd.setPax(Integer.parseInt(lTopODJsonObj.get("pax").toString()));

						}
						if (lTopODJsonObj.has("pax_1") && lTopODJsonObj.get("pax_1") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pax_1").toString())) {
							lTopOd.setPaxlastyear(Float.parseFloat(lTopODJsonObj.get("pax_1").toString()));

						}
						if (lTopODJsonObj.has("region") && lTopODJsonObj.get("region") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("region").toString())) {
							lTopOd.setRegion(lTopODJsonObj.get("region").toString());

						}
						if (lTopODJsonObj.has("country") && lTopODJsonObj.get("country") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("country").toString())) {
							lTopOd.setCountry(lTopODJsonObj.get("country").toString());

						}
						if (lTopODJsonObj.has("pos") && lTopODJsonObj.get("pos") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pos").toString())) {
							lTopOd.setPos(lTopODJsonObj.get("pos").toString());

						}
						if (lTopODJsonObj.has("compartment") && lTopODJsonObj.get("compartment") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("compartment").toString())) {
							lTopOd.setCompartment(lTopODJsonObj.get("compartment").toString());

						}
						if (lTopODJsonObj.has("currency") && lTopODJsonObj.get("currency") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("currency").toString())) {
							lTopOd.setCurrency(lTopODJsonObj.get("currency").toString());

						}

						if (lTopODJsonObj.has("revenue") && lTopODJsonObj.get("revenue") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("revenue").toString())) {
							lTopOd.setRevenue(Float.parseFloat(lTopODJsonObj.get("revenue").toString()));

						}
						if (lTopODJsonObj.has("revenue_1") && lTopODJsonObj.get("revenue_1") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("revenue_1").toString())) {
							lTopOd.setRevenuelastyr(Float.parseFloat(lTopODJsonObj.get("revenue_1").toString()));

						}
						double flownrevenue = 0;
						if (lTopODJsonObj.has("flown_revenue") && lTopODJsonObj.get("flown_revenue") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("flown_revenue").toString())) {
							flownrevenueArray = new JSONArray(lTopODJsonObj.get("flown_revenue").toString());
							if (flownrevenueArray != null) {
								if (flownrevenueArray.length() > 0) {
									flownrevenue = Utility.findSum(flownrevenueArray);
									lTopOd.setFlown_revenue((float) flownrevenue);
								}
							}

						}
						if (lTopODJsonObj.has("target_revenue") && lTopODJsonObj.get("target_revenue") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("target_revenue").toString())) {
							lTargetRevenueArray = new JSONArray(lTopODJsonObj.get("target_revenue").toString());
							Double targetRevenue = Utility.findSum(lTargetRevenueArray);
							lTopOd.setTargetRevenue(targetRevenue.toString());
						} else {
							lTopOd.setTargetRevenue("_");
						}
						if (lTopODJsonObj.has("pax_target") && lTopODJsonObj.get("pax_target") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pax_target").toString())) {
							lPaxTargetArray = new JSONArray(lTopODJsonObj.get("pax_target").toString());

							Double targetPax = Utility.findSum(lPaxTargetArray);
							lTopOd.setTargetPax(targetPax.toString());
						} else {
							lTopOd.setTargetPax("_");
						}

						if (lTopODJsonObj.has("od_distance") && lTopODJsonObj.get("od_distance") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("od_distance").toString())) {
							oDDistanceArray = new JSONArray(lTopODJsonObj.get("od_distance").toString());
							if (oDDistanceArray != null) {
								if (oDDistanceArray.length() > 0) {
									distance = Utility.findSum(oDDistanceArray);
									lTopOd.setOd_Distance((float) distance);
								}
							}
						}
						StringBuilder lStr = CalculationUtil.getFilters(pRequestModel, lTopOd);
						String keyBuilder = lStr.toString();
						if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
							keyBuilder = "null";
						}
						if ("Global Head".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(
										lTopOd.getAgent() + lTopOd.getPos() + lTopOd.getOd() + lTopOd.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getAgent());
							}
						} else if ("Region Head".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(lTopOd.getOd() + lTopOd.getRegion());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getOd());
							}
						} else if ("Country Head".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(lTopOd.getOd() + lTopOd.getRegion() + lTopOd.getCountry());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getOd());
							}
						} else if ("Pricing Analyst".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(
										lTopOd.getOd() + lTopOd.getRegion() + lTopOd.getCountry() + lTopOd.getPos());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getOd());
							}
						}
						lTopOd.setFilterKey(lStr.toString());
						System.out.println("lMS " + lTopOd.getFilterKey());
						lTopTenList.add(lTopOd);
					}
					Map<String, SalesReviewTopTenOd> map = new HashMap<String, SalesReviewTopTenOd>();
					SalesReviewTopTenOd lTopTenOD = null;
					if (lTopTenList.size() > 0) {
						for (FilterModel lObj : lTopTenList) {
							if (!map.containsKey(lObj.getFilterKey())) {
								lTopTenOD = new SalesReviewTopTenOd();
								lTopTenOD.setCombinationKey(lObj.getFilterKey());
								lTopTenOD.setRegion(lObj.getRegion());
								lTopTenOD.setCountry(lObj.getCountry());
								lTopTenOD.setPos(lObj.getPos());
								lTopTenOD.setAgent(lObj.getAgent());
								lTopTenOD.setCompartment(lObj.getCompartment());
								lTopTenOD.setOd(lObj.getOd());
								lTopTenOD.setOrigin(lObj.getOd().substring(0, 3));
								lTopTenOD.setDestination(lObj.getOd().substring(3, 6));
								// total pax and last yr
								int totalPax = lObj.getPax();
								float totalPaxlastyr = lObj.getPaxlastyear();
								lTopTenOD.setTotalPax(totalPax);
								lTopTenOD.setTotalPaxlastyr(totalPaxlastyr);
								// targetpax
								lTopTenOD.setTargetpax(Float.parseFloat(lObj.getTargetPax()));
								// total revenue and last yr
								float totalRevenue = (lObj.getRevenue());
								float totalRevenuelastyr = lObj.getRevenuelastyr();
								float totalflownrevenue = lObj.getFlown_revenue();
								lTopTenOD.setTotalrevenue(totalRevenue);
								lTopTenOD.setTotalrevenuelastyr(totalRevenuelastyr);
								lTopTenOD.setTotalFlownRevenue(totalflownrevenue);
								// target revenue
								lTopTenOD.setTargetRevenue(Float.parseFloat(lObj.getTargetRevenue()));
								// od distance
								float od_distance = (lObj.getOd_Distance());
								lTopTenOD.setOdDistance(od_distance);

								map.put(lObj.getFilterKey(), lTopTenOD);
							} else {
								for (String lKey : map.keySet()) {
									if (lObj.getFilterKey().equals(lKey)) {
										lTopTenOD = map.get(lKey);
									}
								}
								// total pax and last yr
								float totalPax = lObj.getPax() + lTopTenOD.getTotalPax();
								float totalPaxlastyr = lObj.getPaxlastyear() + lTopTenOD.getTotalPaxlastyr();
								lTopTenOD.setTotalPax(totalPax);
								lTopTenOD.setTotalPaxlastyr(totalPaxlastyr);
								// total revenue and last yr
								float totalRevenue = (lObj.getRevenue() + lTopTenOD.getTotalrevenue());
								float totalRevenuelastyr = lObj.getRevenuelastyr() + lTopTenOD.getTotalrevenuelastyr();
								float totalflownrevenue = lObj.getFlown_revenue() + lTopTenOD.getTotalFlownRevenue();
								lTopTenOD.setTotalrevenue(totalRevenue);
								lTopTenOD.setTotalrevenuelastyr(totalRevenuelastyr);
								lTopTenOD.setTotalFlownRevenue(totalflownrevenue);
								// od distance
								float od_distance = lObj.getOd_Distance() + lTopTenOD.getOdDistance();
								lTopTenOD.setOdDistance(od_distance);

							}
						}

					}
					for (String key : map.keySet()) {
						lTopTenOD = new SalesReviewTopTenOd();
						lTopTenOD.setCombinationKey(map.get(key).getCombinationKey());
						if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
							if (pRequestModel.getRegionArray()[0].toString() != null
									&& !pRequestModel.getRegionArray()[0].toString().equals("null")) {
								lTopTenOD.setRegion(map.get(key).getRegion());
							}
						}
						if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
							if (pRequestModel.getCountryArray()[0].toString() != null
									&& !pRequestModel.getCountryArray()[0].toString().equals("null")) {
								lTopTenOD.setCountry(map.get(key).getCountry());
							}
						}
						if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {
							if (pRequestModel.getPosArray()[0].toString() != null
									&& !pRequestModel.getPosArray()[0].toString().equals("null")) {
								lTopTenOD.setPos(map.get(key).getPos());
							}
						}
						if (pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getCompartmentArray().length > 0) {
							if (pRequestModel.getCompartmentArray()[0].toString() != null
									&& !pRequestModel.getCompartmentArray()[0].toString().equals("null")) {
								lTopTenOD.setCompartment(map.get(key).getCompartment());
							}
						}
						lTopTenOD.setAgent(map.get(key).getAgent());
						lTopTenOD.setPos(map.get(key).getPos());
						lTopTenOD.setOd(map.get(key).getOd());
						lTopTenOD.setOrigin(map.get(key).getOrigin());
						lTopTenOD.setDestination(map.get(key).getDestination());
						lTopTenOD.setCompartment(map.get(key).getCompartment());
						// total pax and last yr
						float totalPax = map.get(key).getTotalPax();
						float totalPax_lastyr = map.get(key).getTotalPaxlastyr();
						lTopTenOD.setPax(totalPax);
						lTopTenOD.setTotalPaxlastyr(totalPax_lastyr);
						// target pax
						lTopTenOD.setTargetpax(map.get(key).getTargetpax());
						// pax vlyr
						float lpaxVLYR = 0;
						if (totalPax_lastyr > 0) {
							lpaxVLYR = (int) CalculationUtil.calculateVLYR(totalPax, totalPax_lastyr);
							lTopTenOD.setPaxVLYR(Float.toString(lpaxVLYR));
						} else {
							lTopTenOD.setPaxVLYR(("_"));
						}
						// pax vtgt
						float lPaxVTGT = 0;
						if (map.get(key).getTargetpax() > 0) {
							lPaxVTGT = CalculationUtil.calculateVTGT(totalPax, map.get(key).getTargetpax());
							lTopTenOD.setPaxVTGT(Float.toString(lPaxVTGT));
						} else {
							lTopTenOD.setPaxVTGT("_");
						}

						// total revenue and last yr
						float totalrevenue = map.get(key).getTotalrevenue();
						float totalFlownRevenue = map.get(key).getTotalFlownRevenue();
						float totalrevenuelastyr = map.get(key).getTotalrevenuelastyr();
						lTopTenOD.setTotalrevenue(totalrevenue);
						lTopTenOD.setTotalFlownRevenue(totalFlownRevenue);
						lTopTenOD.setTotalrevenuelastyr(totalrevenuelastyr);
						// target revenue
						lTopTenOD.setTargetRevenue(map.get(key).getTargetRevenue());
						// revenue vlyr
						float lrevenueVLYR = 0;
						if (totalrevenuelastyr > 0) {
							lrevenueVLYR = CalculationUtil.calculateVLYR(totalrevenue, totalrevenuelastyr);
							lTopTenOD.setRevenueVLYR(Float.toString(lrevenueVLYR));
						} else {
							lTopTenOD.setRevenueVLYR("_");
						}
						// revenue VTGT
						float lrevenueVTGT = 0;
						if (map.get(key).getTargetRevenue() > 0) {
							lrevenueVTGT = CalculationUtil.calculateVTGT(totalrevenue, map.get(key).getTargetRevenue());
							lTopTenOD.setRevenueVTGT(Float.toString(lrevenueVTGT));
						} else {
							lTopTenOD.setRevenueVTGT("_");
						}
						// yield
						float od_distance = map.get(key).getOdDistance();
						float yield = 0;
						yield = (totalrevenue * 100) / (od_distance * totalPax);
						float yieldlastyr = 0;
						if (od_distance > 0 && totalPax_lastyr > 0) {
							yieldlastyr = (totalrevenuelastyr * 100) / (od_distance * totalPax_lastyr);
						}
						if (yield > 0) {
							lTopTenOD.setTotalYield(yield);
						} else {
							lTopTenOD.setTotalYield(0);
						}
						if (yieldlastyr > 0) {
							lTopTenOD.setTotalYieldlastyr(yieldlastyr);
						} else {
							lTopTenOD.setTotalYieldlastyr(0);
						}
						// yield VLYR
						if (yieldlastyr > 0) {
							float yieldVLYr = CalculationUtil.calculateVLYR(yield, yieldlastyr);
							lTopTenOD.setYieldVLYR(Float.toString(yieldVLYr));
						}
						// avg fare
						float avgfare = CalculationUtil.calculateavgfare(totalrevenue, totalPax);
						lTopTenOD.setAvgfare(avgfare);
						// fare

						lODList.add(lTopTenOD);

					}
				}

				Collections.sort(lODList, new lTopTenOd());
				if (lODList.size() > 10) {
					lTopODList.addAll(lODList.subList(0, 10));
				} else {
					lTopODList.addAll(lODList.subList(0, lODList.size()));
				}
				// totals
				float totalPaxYTD = 0;
				float totalPaxLastYr = 0;
				float totalPaxTarget = 0;
				float totalRevenueYTd = 0;
				float totalFlownRevenue = 0;
				float totalrevenuelastyr = 0;
				float totalrevenuetarget = 0;
				float totalyield = 0;
				float totalyieldlastyr = 0;
				for (SalesReviewTopTenOd lObj : lTopODList) {
					totalPaxYTD += lObj.getPax();
					totalPaxLastYr += lObj.getTotalPaxlastyr();
					totalPaxTarget += lObj.getTargetpax();
					totalRevenueYTd += lObj.getTargetRevenue();
					totalFlownRevenue += lObj.getFlownRevenue();
					totalrevenuelastyr += lObj.getTotalrevenuelastyr();
					totalrevenuetarget += lObj.getTargetRevenue();
					totalyield += lObj.getTotalYield();
					totalyieldlastyr += lObj.getTotalYieldlastyr();

				}
				SalesReviewTopTenOdTotalResponse lTotals = new SalesReviewTopTenOdTotalResponse();
				// total pax
				if (totalPaxLastYr > 0) {
					totalpaxVLYR = (int) CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxLastYr);
					lTotals.setTotalPaxVLYR(Float.toString(totalpaxVLYR));
				} else {
					lTotals.setTotalPaxVLYR("_");
				}
				if (totalPaxTarget > 0) {
					totalpaxVTGT = (int) CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxTarget);
					lTotals.setTotalPaxVTGT(Float.toString(totalpaxVTGT));
				} else {
					lTotals.setTotalPaxVTGT("_");
				}

				lTotals.setTotalPaxYTD(Float.toString(totalPaxYTD));
				// total revenue
				if (totalrevenuelastyr > 0) {
					totalrevenueVLYR = (int) CalculationUtil.calculateVLYR(totalRevenueYTd, totalrevenuelastyr);
					lTotals.setTotalrevenueVLYR(Float.toString(totalrevenueVLYR));
				} else {
					lTotals.setTotalrevenueVLYR("_");
				}
				if (totalrevenuetarget > 0) {
					totalrevenueVTGT = (int) CalculationUtil.calculateVLYR(totalRevenueYTd, totalrevenuetarget);
					lTotals.setTotalrevenueVTGT(Float.toString(totalrevenueVTGT));
				} else {
					lTotals.setTotalrevenueVTGT("_");
				}
				lTotals.setTotalrevenueYTD(Float.toString(totalRevenueYTd));
				lTotals.setTotalflownrevenueYtd(Float.toString(totalFlownRevenue));
				// total yield
				if (totalyieldlastyr > 0) {
					totalYieldVLYR = (int) CalculationUtil.calculateVLYR(totalyield, totalyieldlastyr);
					lTotals.setTotalyieldVLYR(Float.toString(totalYieldVLYR));
				} else {
					lTotals.setTotalyieldVLYR("_");
				}
				lTotals.setTotalyieldYTD(Float.toString(totalyield));
				// total avg fare
				if (totalPaxYTD > 0) {
					totalavgfare = CalculationUtil.calculateavgfare(totalRevenueYTd, totalPaxYTD);
					lTotals.setTotalavgfare(Float.toString(totalavgfare));
				} else {
					lTotals.setTotalavgfare("_");
				}
				lTotalsList.add(lTotals);

			} catch (Exception e) {
				logger.error("getTopTenAgent-Exception", e);
			}
			responseTopTenAgentMap.put("TopTenODAgentTotals", lTotalsList);
			responseTopTenAgentMap.put("TopTenAgent", lODList);
		} else {
			responseTopTenAgentMap.put("TopTenODAgentTotals", null);
			responseTopTenAgentMap.put("TopTenAgent", null);
		}
		return responseTopTenAgentMap;
	}

	@Override
	public Map<String, Object> getTopTenFares(RequestModel pRequestModel) {

		// total
		float totalpaxVLYR = 0;
		float totalpaxVTGT = 0;
		float totalrevenueVLYR = 0;
		float totalrevenueVTGT = 0;
		float totalYieldVLYR = 0;
		float totalavgfare = 0;
		double distance = 0;
		JSONArray oDDistanceArray = null;
		JSONArray flownrevenueArray = null;
		JSONArray lTargetRevenueArray = null;
		JSONArray lPaxTargetArray = null;
		List<FilterModel> lTopTenList = new ArrayList<FilterModel>();
		List<SalesReviewTopTenOdTotalResponse> lTotalsList = new ArrayList<SalesReviewTopTenOdTotalResponse>();
		List<SalesReviewTopTenOd> lODList = new ArrayList<SalesReviewTopTenOd>();
		Map<String, Object> responseTopTenFaresMap = new HashMap<String, Object>();
		FilterModel lTopOd = new FilterModel();
		ArrayList<DBObject> lTopTenOdObj = mSalesReviewDao.getTopTenOd(pRequestModel);
		if (!lTopTenOdObj.isEmpty()) {
			JSONArray lTopTenOdData = new JSONArray(lTopTenOdObj);
			try {
				if (lTopTenOdData != null) {
					for (int i = 0; i < lTopTenOdData.length(); i++) {

						JSONObject lTopODJsonObj = lTopTenOdData.getJSONObject(i);
						lTopOd = new FilterModel();
						System.out.println("lPriceJSONObj:  " + lTopODJsonObj);
						if (lTopODJsonObj.has("fare") && lTopODJsonObj.get("fare") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("fare").toString())) {
							lTopOd.setFares(Float.parseFloat(lTopODJsonObj.get("fare").toString()));

						}
						if (lTopODJsonObj.has("RBD") && lTopODJsonObj.get("RBD") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("RBD").toString())) {
							lTopOd.setRbd(lTopODJsonObj.get("RBD").toString());

						}

						if (lTopODJsonObj.has("od") && lTopODJsonObj.get("od") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("od").toString())) {
							lTopOd.setOd(lTopODJsonObj.get("od").toString());

						}
						if (lTopODJsonObj.has("fare_basis") && lTopODJsonObj.get("fare_basis") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("fare_basis").toString())) {
							lTopOd.setFareBasis(lTopODJsonObj.get("fare_basis").toString());

						}
						if (lTopODJsonObj.has("pax") && lTopODJsonObj.get("pax") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pax").toString())) {
							lTopOd.setPax(Integer.parseInt(lTopODJsonObj.get("pax").toString()));

						}
						if (lTopODJsonObj.has("pax_1") && lTopODJsonObj.get("pax_1") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pax_1").toString())) {
							lTopOd.setPaxlastyear(Float.parseFloat(lTopODJsonObj.get("pax_1").toString()));

						}
						if (lTopODJsonObj.has("region") && lTopODJsonObj.get("region") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("region").toString())) {
							lTopOd.setRegion(lTopODJsonObj.get("region").toString());

						}
						if (lTopODJsonObj.has("country") && lTopODJsonObj.get("country") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("country").toString())) {
							lTopOd.setCountry(lTopODJsonObj.get("country").toString());

						}
						if (lTopODJsonObj.has("pos") && lTopODJsonObj.get("pos") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pos").toString())) {
							lTopOd.setPos(lTopODJsonObj.get("pos").toString());

						}
						if (lTopODJsonObj.has("compartment") && lTopODJsonObj.get("compartment") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("compartment").toString())) {
							lTopOd.setCompartment(lTopODJsonObj.get("compartment").toString());

						}
						if (lTopODJsonObj.has("currency") && lTopODJsonObj.get("currency") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("currency").toString())) {
							lTopOd.setCurrency(lTopODJsonObj.get("currency").toString());

						}
						if (lTopODJsonObj.has("revenue") && lTopODJsonObj.get("revenue") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("revenue").toString())) {
							lTopOd.setRevenue(Float.parseFloat(lTopODJsonObj.get("revenue").toString()));

						}
						if (lTopODJsonObj.has("revenue_1") && lTopODJsonObj.get("revenue_1") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("revenue_1").toString())) {
							lTopOd.setRevenuelastyr(Float.parseFloat(lTopODJsonObj.get("revenue_1").toString()));

						}
						double flownrevenue = 0;
						if (lTopODJsonObj.has("flown_revenue") && lTopODJsonObj.get("flown_revenue") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("flown_revenue").toString())) {
							flownrevenueArray = new JSONArray(lTopODJsonObj.get("flown_revenue").toString());
							if (flownrevenueArray != null) {
								if (flownrevenueArray.length() > 0) {
									flownrevenue = Utility.findSum(flownrevenueArray);
									lTopOd.setFlown_revenue((float) flownrevenue);
								}
							}

						}
						if (lTopODJsonObj.has("target_revenue") && lTopODJsonObj.get("target_revenue") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("target_revenue").toString())) {
							lTargetRevenueArray = new JSONArray(lTopODJsonObj.get("target_revenue").toString());
							Double targetRevenue = Utility.findSum(lTargetRevenueArray);
							lTopOd.setTargetRevenue(targetRevenue.toString());
						} else {
							lTopOd.setTargetRevenue("0");
						}
						if (lTopODJsonObj.has("pax_target") && lTopODJsonObj.get("pax_target") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("pax_target").toString())) {
							lPaxTargetArray = new JSONArray(lTopODJsonObj.get("pax_target").toString());

							Double targetPax = Utility.findSum(lPaxTargetArray);
							lTopOd.setTargetPax(targetPax.toString());
						} else {
							lTopOd.setTargetPax("0");
						}

						if (lTopODJsonObj.has("od_distance") && lTopODJsonObj.get("od_distance") != null
								&& !"null".equalsIgnoreCase(lTopODJsonObj.get("od_distance").toString())) {
							oDDistanceArray = new JSONArray(lTopODJsonObj.get("od_distance").toString());
							if (oDDistanceArray != null) {
								if (oDDistanceArray.length() > 0) {
									distance = Utility.findSum(oDDistanceArray);
									lTopOd.setOd_Distance((float) distance);
								}
							}
						}
						StringBuilder lStr = CalculationUtil.getFilters(pRequestModel, lTopOd);
						String keyBuilder = lStr.toString();
						if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnull".equalsIgnoreCase(keyBuilder)
								|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
							keyBuilder = "null";
						}
						if ("Global Head".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(lTopOd.getFares() + lTopOd.getFareBasis() + lTopOd.getOd()
										+ lTopOd.getCompartment());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getAgent());
							}
						} else if ("Region Head".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(lTopOd.getOd() + lTopOd.getRegion());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getOd());
							}
						} else if ("Country Head".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(lTopOd.getOd() + lTopOd.getRegion() + lTopOd.getCountry());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getOd());
							}
						} else if ("Pricing Analyst".equalsIgnoreCase(pRequestModel.getUser())) {
							if ("null".equalsIgnoreCase(keyBuilder)) {
								lTopOd.setFilterKey(
										lTopOd.getOd() + lTopOd.getRegion() + lTopOd.getCountry() + lTopOd.getPos());
								lStr = new StringBuilder();
								lStr.append(lTopOd.getFilterKey());
							} else {
								lStr.append(lTopOd.getOd());
							}
						}
						lTopOd.setFilterKey(lStr.toString());
						System.out.println("lMS " + lTopOd.getFilterKey());
						lTopTenList.add(lTopOd);
					}
					Map<String, SalesReviewTopTenOd> map = new HashMap<String, SalesReviewTopTenOd>();
					SalesReviewTopTenOd lTopTenOD = null;
					if (lTopTenList.size() > 0) {
						for (FilterModel lObj : lTopTenList) {
							if (!map.containsKey(lObj.getFilterKey())) {
								lTopTenOD = new SalesReviewTopTenOd();
								lTopTenOD.setCombinationKey(lObj.getFilterKey());
								lTopTenOD.setRegion(lObj.getRegion());
								lTopTenOD.setCountry(lObj.getCountry());
								lTopTenOD.setPos(lObj.getPos());
								lTopTenOD.setOd(lObj.getOd());
								lTopTenOD.setOrigin(lObj.getOd().substring(0, 3));
								lTopTenOD.setDestination(lObj.getOd().substring(3, 6));
								lTopTenOD.setCompartment(lObj.getCompartment());
								lTopTenOD.setFareBasis(lObj.getFareBasis());
								lTopTenOD.setRbd(lObj.getRbd());
								// total pax and last yr
								int totalPax = lObj.getPax();
								float totalPaxlastyr = lObj.getPaxlastyear();
								lTopTenOD.setTotalPax(totalPax);
								lTopTenOD.setTotalPaxlastyr(totalPaxlastyr);
								// targetpax
								lTopTenOD.setTargetpax(Float.parseFloat(lObj.getTargetPax()));
								// total revenue and last yr
								float totalRevenue = (lObj.getRevenue());
								float totalRevenuelastyr = lObj.getRevenuelastyr();
								float totalflownrevenue = lObj.getFlown_revenue();
								lTopTenOD.setTotalrevenue(totalRevenue);
								lTopTenOD.setTotalrevenuelastyr(totalRevenuelastyr);
								lTopTenOD.setTotalFlownRevenue(totalflownrevenue);
								// fare
								float fare = lObj.getFares();
								lTopTenOD.setFare(fare);
								map.put(lObj.getFilterKey(), lTopTenOD);
							} else {
								for (String lKey : map.keySet()) {
									if (lObj.getFilterKey().equals(lKey)) {
										lTopTenOD = map.get(lKey);
									}
								}
								// total pax and last yr
								float totalPax = lObj.getPax() + lTopTenOD.getTotalPax();
								float totalPaxlastyr = lObj.getPaxlastyear() + lTopTenOD.getTotalPaxlastyr();
								lTopTenOD.setTotalPax(totalPax);
								lTopTenOD.setTotalPaxlastyr(totalPaxlastyr);
								// total revenue and last yr
								float totalRevenue = (lObj.getRevenue() + lTopTenOD.getTotalrevenue());
								float totalRevenuelastyr = lObj.getRevenuelastyr() + lTopTenOD.getTotalrevenuelastyr();
								float totalflownrevenue = lObj.getFlown_revenue() + lTopTenOD.getTotalFlownRevenue();
								lTopTenOD.setTotalrevenue(totalRevenue);
								lTopTenOD.setTotalrevenuelastyr(totalRevenuelastyr);
								lTopTenOD.setTotalFlownRevenue(totalflownrevenue);
								lTopTenOD.setFare(lObj.getFares());

							}
						}

					}
					for (String key : map.keySet()) {
						lTopTenOD = new SalesReviewTopTenOd();
						lTopTenOD.setCombinationKey(map.get(key).getCombinationKey());
						if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
							if (pRequestModel.getRegionArray()[0].toString() != null
									&& !pRequestModel.getRegionArray()[0].toString().equals("null")) {
								lTopTenOD.setRegion(map.get(key).getRegion());
							}
						}
						if (pRequestModel.getCountryArray() != null && pRequestModel.getCountryArray().length > 0) {
							if (pRequestModel.getCountryArray()[0].toString() != null
									&& !pRequestModel.getCountryArray()[0].toString().equals("null")) {
								lTopTenOD.setCountry(map.get(key).getCountry());
							}
						}
						if (pRequestModel.getPosArray() != null && pRequestModel.getPosArray().length > 0) {
							if (pRequestModel.getPosArray()[0].toString() != null
									&& !pRequestModel.getPosArray()[0].toString().equals("null")) {
								lTopTenOD.setPos(map.get(key).getPos());
							}
						}
						if (pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getCompartmentArray().length > 0) {
							if (pRequestModel.getCompartmentArray()[0].toString() != null
									&& !pRequestModel.getCompartmentArray()[0].toString().equals("null")) {
								lTopTenOD.setCompartment(map.get(key).getCompartment());
							}
						}
						lTopTenOD.setFareBasis(map.get(key).getFareBasis());
						lTopTenOD.setOd(map.get(key).getOd());
						lTopTenOD.setOrigin(map.get(key).getOrigin());
						lTopTenOD.setDestination(map.get(key).getDestination());
						lTopTenOD.setCompartment(map.get(key).getCompartment());

						// total pax and last yr
						float totalPax = map.get(key).getTotalPax();
						float totalPax_lastyr = map.get(key).getTotalPaxlastyr();
						lTopTenOD.setPax(totalPax);
						lTopTenOD.setTotalPaxlastyr(totalPax_lastyr);
						// target pax
						lTopTenOD.setTargetpax(map.get(key).getTargetpax());
						// pax vlyr
						float lpaxVLYR = 0;
						if (totalPax_lastyr > 0) {
							lpaxVLYR = (int) CalculationUtil.calculateVLYR(totalPax, totalPax_lastyr);
							lTopTenOD.setPaxVLYR(Float.toString(lpaxVLYR));
						} else {
							lTopTenOD.setPaxVLYR(("_"));
						}
						// pax vtgt

						lTopTenOD.setPaxVTGT("_");
						// }

						// total revenue and last yr
						float totalrevenue = map.get(key).getTotalrevenue();
						float totalFlownRevenue = map.get(key).getTotalFlownRevenue();
						float totalrevenuelastyr = map.get(key).getTotalrevenuelastyr();
						lTopTenOD.setSalesRevenue(totalrevenue);
						lTopTenOD.setTotalrevenue(totalrevenue);
						lTopTenOD.setTotalFlownRevenue(totalFlownRevenue);
						lTopTenOD.setTotalrevenuelastyr(totalrevenuelastyr);
						// target revenue
						lTopTenOD.setTargetRevenue(map.get(key).getTargetRevenue());
						// revenue vlyr
						float lrevenueVLYR = 0;
						if (totalrevenuelastyr > 0) {
							lrevenueVLYR = CalculationUtil.calculateVLYR(totalrevenue, totalrevenuelastyr);
							lTopTenOD.setRevenueVLYR(Float.toString(lrevenueVLYR));
						} else {
							lTopTenOD.setRevenueVLYR("_");
						}
						// revenue VTGT

						lTopTenOD.setRevenueVTGT("_");
						// yield
						float od_distance = map.get(key).getOdDistance();
						float yield = 0;
						yield = (totalrevenue * 100) / (od_distance * totalPax);
						float yieldlastyr = 0;
						yieldlastyr = (totalrevenuelastyr * 100) / (od_distance * totalPax_lastyr);
						lTopTenOD.setTotalYield(yield);
						lTopTenOD.setTotalYieldlastyr(yieldlastyr);
						// yield VLYR
						if (yieldlastyr > 0) {
							float yieldVLYr = CalculationUtil.calculateVLYR(yield, yieldlastyr);
							lTopTenOD.setYieldVLYR(Float.toString(yieldVLYr));
						}
						// avg fare
						float avgfare = CalculationUtil.calculateavgfare(totalrevenue, totalPax);
						lTopTenOD.setAvgfare(avgfare);
						// fare
						float fare = map.get(key).getFare();
						lTopTenOD.setFare(fare);
						lODList.add(lTopTenOD);

					}
				}

				Collections.sort(lODList, new lTopTenOd());
				if (lODList.size() > 10) {
					lODList = (lODList.subList(0, 10));
				} else {
					lODList = (lODList.subList(0, lODList.size()));
				}
				// totals
				float totalPaxYTD = 0;
				float totalPaxLastYr = 0;
				float totalPaxTarget = 0;
				float totalRevenueYTd = 0;
				float totalFlownRevenue = 0;
				float totalrevenuelastyr = 0;
				float totalrevenuetarget = 0;
				float totalyield = 0;
				float totalyieldlastyr = 0;
				float totalfare = 0;
				for (SalesReviewTopTenOd lObj : lODList) {
					totalPaxYTD += lObj.getPax();
					totalPaxLastYr += lObj.getTotalPaxlastyr();
					totalPaxTarget += lObj.getTargetpax();
					totalRevenueYTd += lObj.getTotalrevenue();
					totalFlownRevenue += lObj.getFlownRevenue();
					totalrevenuelastyr += lObj.getTotalrevenuelastyr();
					totalrevenuetarget += lObj.getTargetRevenue();
					totalyield += lObj.getTotalYield();
					totalyieldlastyr += lObj.getTotalYieldlastyr();
					totalfare += lObj.getFare();
				}
				SalesReviewTopTenOdTotalResponse lTotals = new SalesReviewTopTenOdTotalResponse();
				// total revenue
				if (totalPaxLastYr > 0) {
					totalpaxVLYR = (int) CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxLastYr);
					lTotals.setTotalrevenueVLYR(Float.toString(totalpaxVLYR));
				} else {
					lTotals.setTotalPaxVLYR("_");
				}
				/*
				 * if (totalPaxTarget > 0) { totalpaxVTGT = (int)
				 * CalculationUtil.calculateVLYR(totalPaxYTD, totalPaxTarget);
				 * lTotals.setTotalrevenueVLYR(Float.toString(totalpaxVTGT)); }
				 * else {
				 */
				lTotals.setTotalPaxVTGT("_");
				// }

				lTotals.setTotalPaxYTD(Float.toString(totalPaxYTD));
				// total revenue
				if (totalrevenuelastyr > 0) {
					totalrevenueVLYR = (int) CalculationUtil.calculateVLYR(totalRevenueYTd, totalrevenuelastyr);
					lTotals.setTotalrevenueVLYR(Float.toString(totalrevenueVLYR));
				} else {
					lTotals.setTotalrevenueVLYR("_");
				}
				/*
				 * if (totalrevenuetarget > 0) { totalrevenueVTGT = (int)
				 * CalculationUtil.calculateVLYR(totalRevenueYTd,
				 * totalrevenuetarget);
				 * lTotals.setTotalrevenueVTGT(Float.toString(totalrevenueVTGT))
				 * ; } else {
				 */
				lTotals.setTotalrevenueVTGT("_");
				// }
				lTotals.setTotalrevenueYTD(Float.toString(totalRevenueYTd));
				lTotals.setTotalflownrevenueYtd(Float.toString(totalFlownRevenue));
				// total yield
				if (totalyieldlastyr > 0) {
					totalYieldVLYR = (int) CalculationUtil.calculateVLYR(totalyield, totalyieldlastyr);
					lTotals.setTotalyieldVLYR(Float.toString(totalYieldVLYR));
				} else {
					lTotals.setTotalyieldVLYR("_");
				}
				lTotals.setTotalyieldYTD(Float.toString(totalyield));
				// total avg fare
				if (totalPaxYTD > 0) {
					totalavgfare = CalculationUtil.calculateavgfare(totalRevenueYTd, totalPaxYTD);
					lTotals.setTotalavgfare(Float.toString(totalavgfare));
				} else {
					lTotals.setTotalavgfare("_");
				}
				// total fare
				lTotals.setTotalfare(Float.toString(totalfare));
				lTotalsList.add(lTotals);

			} catch (Exception e) {
				logger.error("getTopTenFares-Exception", e);
			}
			responseTopTenFaresMap.put("TopTenODFaresTotals", lTotalsList);
			responseTopTenFaresMap.put("TopTenFares", lODList);
		} else {
			responseTopTenFaresMap.put("TopTenODFaresTotals", null);
			responseTopTenFaresMap.put("TopTenFares", null);
		}
		return responseTopTenFaresMap;
	}

}
