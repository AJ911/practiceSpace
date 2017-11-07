package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.CustSegDashboardDao;
import com.flynava.jupiter.model.ChannelSummary;
import com.flynava.jupiter.model.ChannelSummaryTotalResponse;
import com.flynava.jupiter.model.CustomerSegment;
import com.flynava.jupiter.model.CustomerSegmentDetails;
import com.flynava.jupiter.model.CustomerSegmentDetailsTotals;
import com.flynava.jupiter.model.CustomerSegmentTotals;
import com.flynava.jupiter.model.DirectChannelTotalResponse;
import com.flynava.jupiter.model.DirectIndirectChannelSummary;
import com.flynava.jupiter.model.DistributionDetailsTotalResponse;
import com.flynava.jupiter.model.DistributorSummary;
import com.flynava.jupiter.model.DistributorSummaryDetails;
import com.flynava.jupiter.model.DistributorTotalResponse;
import com.flynava.jupiter.model.FrequentFlyer;
import com.flynava.jupiter.model.IndirectChannelTotalResponse;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.TotalsModel;
import com.flynava.jupiter.serviceInterface.CustomerSegmentService;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Utility;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Surya This Class CustSegDashboardServiceImpl contains all the Service
 *         level functions required in Distribution Customer Dashboard
 */
@Service
public class CustSegDashboardServiceImpl implements CustomerSegmentService {

	@Autowired
	CustSegDashboardDao mCustSegDashboardDao;

	private static final Logger logger = Logger.getLogger(CustSegDashboardServiceImpl.class);

	@Override
	public Map<String, Object> getChannelSummary(RequestModel pRequestModel) {
		ArrayList<DBObject> lChannelSummaryList = mCustSegDashboardDao.getChannelSummary(pRequestModel);
		JSONArray lData = new JSONArray(lChannelSummaryList);

		Map<String, Object> lChannelSummaryMap = new HashMap<String, Object>();
		Map<String, ChannelSummary> lCsMap = new HashMap<String, ChannelSummary>();

		Double lTotalRevenue = 0D;
		Double lTotalTarget = 0D;
		Double lTotalFlownRevenueYTD = 0D;
		Double lTotalRevenueVLYR = 0D;
		Double lTotalRevenueVTGT = 0D;
		Double lTotalDedicatedFare = 0D;
		Double lTotalRevenueLastYear = 0D;
		Double lTotalPax = 0D;
		JSONArray reveneulastyrArray = null;
		double revenuelastyr = 0;

		try {
			if (lData != null) {

				for (int i = 0; i < lData.length(); i++) {
					JSONObject lJsonObj = lData.getJSONObject(i);

					String lCombinationKey = getCombinationKeyDashboardScreen(pRequestModel, lJsonObj);
					String lRegion = "-";
					if (lJsonObj.get("region") != null && !"null".equalsIgnoreCase(lJsonObj.get("region").toString()))
						lRegion = lJsonObj.getString("region");
					String lCountry = "-";
					if (lJsonObj.get("country") != null && !"null".equalsIgnoreCase(lJsonObj.get("country").toString()))
						lCountry = lJsonObj.getString("country");
					String lPos = "-";
					if (lJsonObj.get("pos") != null && !"null".equalsIgnoreCase(lJsonObj.get("pos").toString()))
						lPos = lJsonObj.getString("pos");
					String lCompartment = "-";
					if (lJsonObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("compartment").toString()))
						lCompartment = lJsonObj.getString("compartment");
					String lChannel = "-";
					if (lJsonObj.get("channel") != null && !"null".equalsIgnoreCase(lJsonObj.get("channel").toString()))
						lChannel = lJsonObj.getJSONArray("channel").getString(0);
					Double lRevenue = 0D;
					if (lJsonObj.get("revenue") != null && !"null".equalsIgnoreCase(lJsonObj.get("revenue").toString()))
						lRevenue = Utility.findSum(lJsonObj.get("revenue"));

					if (lJsonObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("revenue_1").toString())) {
						if (reveneulastyrArray != null) {
							if (reveneulastyrArray.length() > 0) {
								for (int j = 0; j < reveneulastyrArray.length(); j++) {
									if (reveneulastyrArray != null
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("null")
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
										revenuelastyr = Utility.findSum(reveneulastyrArray);

									}
								}
							}
						}
					}

					Double lTarget = 0D;
					if (lJsonObj.get("target_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("target_revenue").toString()))
						lTarget = Utility.findSum(lJsonObj.get("target_revenue"));
					Double lFlown = 0D;
					if (lJsonObj.get("flown_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("flown_revenue").toString()))
						lFlown = Utility.findSum(lJsonObj.get("flown_revenue"));
					Double lPax = 0D;
					if (lJsonObj.get("pax") != null && !"null".equalsIgnoreCase(lJsonObj.get("pax").toString()))
						lPax = Utility.findSum(lJsonObj.get("pax"));

					lCombinationKey += lChannel;
					if (!lCsMap.containsKey(lCombinationKey)) {
						ChannelSummary lCsModel = new ChannelSummary();
						lCsModel.setRegion(lRegion);
						lCsModel.setCountry(lCountry);
						lCsModel.setPos(lPos);
						lCsModel.setChannel(lChannel);
						lCsModel.setCombinationKey(lCombinationKey);
						lCsModel.setRevenueYTD(lRevenue);
						lCsModel.setRevenueLastYear(revenuelastyr);
						lCsModel.setTargetYTD(lTarget);
						lCsModel.setFlownYTD((double) lFlown);
						lCsModel.setCompartment(lCompartment);

						// untill value coming from DB i am setting the value to
						// zero
						lCsModel.setDedicatedFare(lPax);

						lTotalRevenue += lRevenue;
						lTotalTarget += lTarget;
						lTotalFlownRevenueYTD += lFlown;
						lTotalRevenueLastYear += revenuelastyr;
						lTotalPax += lPax;
						lCsMap.put(lCombinationKey, lCsModel);

					} else {
						ChannelSummary lCsModel = lCsMap.get(lCombinationKey);
						lCsModel.setRevenueYTD(lCsModel.getRevenueYTD() + lRevenue);
						lCsModel.setRevenueLastYear(lCsModel.getRevenueLastYear() + revenuelastyr);
						lCsModel.setTargetYTD(lCsModel.getTargetYTD() + lTarget);
						lCsModel.setFlownYTD(lCsModel.getFlownYTD() + lFlown);
						lCsModel.setDedicatedFare(lCsModel.getDedicatedFare() + lPax);

						lTotalRevenue += lRevenue;
						lTotalTarget += lTarget;
						lTotalFlownRevenueYTD += lFlown;
						lTotalPax += lPax;
						lTotalRevenueLastYear += revenuelastyr;
					}

				}

				for (String key : lCsMap.keySet()) {
					ChannelSummary lCsModel = lCsMap.get(key);
					Double lChannelRevenuePerc = 0D;

					Double lChannelTargetPerc = 0D;
					Double lChannelVariancePerc = 0D;

					Double lRevenueVLYR = 0D;
					Double lRevenueVTGT = 0D;
					Double lDedicatedFarePerc = 0D;

					if (lTotalRevenue != 0) {
						lChannelRevenuePerc = (lCsModel.getRevenueYTD() / lTotalRevenue) * 100;
					}
					if (lTotalTarget != 0) {
						lChannelTargetPerc = (lCsModel.getTargetYTD() / lTotalTarget) * 100;
					}
					if (lChannelTargetPerc != 0) {
						lChannelVariancePerc = ((lChannelRevenuePerc - lChannelTargetPerc));
					}
					if (lCsModel.getRevenueLastYear() != 0) {
						lRevenueVLYR = ((lCsModel.getRevenueYTD() - lCsModel.getRevenueLastYear())
								/ lCsModel.getRevenueLastYear()) * 100;
					}
					if (lCsModel.getTargetYTD() != 0) {
						lRevenueVTGT = ((lCsModel.getRevenueYTD() - lCsModel.getTargetYTD()) / lCsModel.getTargetYTD())
								* 100;
					}
					if (lTotalPax != 0) {
						lDedicatedFarePerc = lCsModel.getDedicatedFare() / lTotalPax * 100;
					}
					lCsModel.setDedicatedFarePerc(lDedicatedFarePerc);
					lCsModel.setChannelRevenuePerc(lChannelRevenuePerc);
					lCsModel.setChannelTargetPerc(lChannelTargetPerc);
					lCsModel.setChannelVariancePerc(lChannelVariancePerc);
					lCsModel.setRevenueVLYR(lRevenueVLYR);
					lCsModel.setRevenueVTGT(lRevenueVTGT);

				}

				ChannelSummaryTotalResponse lcstModel = new ChannelSummaryTotalResponse();
				lcstModel.setTotalRevenueYTD(lTotalRevenue.floatValue());
				lcstModel.setTotalFlownRevenueYTd(lTotalFlownRevenueYTD.floatValue());
				lcstModel.setTotaldedicatedfare(lTotalDedicatedFare.floatValue());
				lcstModel.setTotaldedicatedfare(lTotalPax.floatValue());
				if (lTotalRevenueLastYear != 0)
					lTotalRevenueVLYR = ((lTotalRevenue - lTotalRevenueLastYear) / lTotalRevenueLastYear) * 100;
				if (lTotalTarget != 0)
					lTotalRevenueVTGT = ((lTotalRevenue - lTotalTarget) / lTotalTarget) * 100;
				lcstModel.setTotalrevenuevlyr(lTotalRevenueVLYR.floatValue());
				lcstModel.setTotalrevenuevtgt(lTotalRevenueVTGT.floatValue());

				List<ChannelSummary> lCsList = new ArrayList<ChannelSummary>(lCsMap.values());
				List<ChannelSummaryTotalResponse> lCstList = new ArrayList<ChannelSummaryTotalResponse>();
				lCstList.add(lcstModel);
				lChannelSummaryMap.put("channelSummaryMap", lCsList);
				lChannelSummaryMap.put("channelSummaryTotalsMap", lCstList);

			}
		} catch (Exception e) {
			logger.error("getChannelSummary-Exception", e);
		}
		return lChannelSummaryMap;
	}

	@Override
	public Map<String, Object> getFrequentFlyers(RequestModel pRequestModel) {

		BasicDBObject lFrequentFlyer = (BasicDBObject) mCustSegDashboardDao.getFrequentFlyers(pRequestModel);
		JSONArray lData = null;
		if (lFrequentFlyer.containsKey("_batch")) {
			lData = new JSONArray(lFrequentFlyer.get("_batch").toString());
		}

		Map<String, Object> frequentFlyerMap = new HashMap<String, Object>();
		Map<String, FrequentFlyer> ffMap = new HashMap<String, FrequentFlyer>();

		Double totalRevenue = 0D;
		Double totalTarget = 0D;
		Double totalFlownRevenueYTD = 0D;
		Double totalRevenueLastYear = 0D;
		Double totalRevenueVLYR = 0D;
		Double totalRevenueVTGT = 0D;

		try {
			if (lData != null) {

				for (int i = 0; i < lData.length(); i++) {
					JSONObject lJsonObj = lData.getJSONObject(i);
					String region = "-";
					if (lJsonObj.get("region") != null && !"null".equalsIgnoreCase(lJsonObj.get("region").toString()))
						region = lJsonObj.getString("region");
					String country = "-";
					if (lJsonObj.get("country") != null && !"null".equalsIgnoreCase(lJsonObj.get("country").toString()))
						country = lJsonObj.getString("country");
					String pos = "-";
					if (lJsonObj.get("pos") != null && !"null".equalsIgnoreCase(lJsonObj.get("pos").toString()))
						pos = lJsonObj.getString("pos");
					String compartment = "-";
					if (lJsonObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("compartment").toString()))
						compartment = lJsonObj.getString("compartment");
					Double revenueYTD = 0D;
					if (lJsonObj.get("revenue") != null && !"null".equalsIgnoreCase(lJsonObj.get("revenue").toString()))
						revenueYTD = lJsonObj.getDouble("revenue");
					Double revenueLastYear = 0D;
					if (lJsonObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("revenue_1").toString()))
						revenueLastYear = lJsonObj.getDouble("revenue_1");
					Double targetYTD = 0D;
					if (lJsonObj.get("target_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("target_revenue").toString()))
						targetYTD = Utility.findSum(lJsonObj.get("target_revenue"));
					Double flownYTD = 0D;

					String combinationKey = region + country + pos + compartment;
					if (!ffMap.containsKey(combinationKey)) {
						FrequentFlyer ff = new FrequentFlyer();
						ff.setRegion(region);
						ff.setCountry(country);
						ff.setPos(pos);
						ff.setCombinationkey(combinationKey);
						ff.setRevenueYtd(revenueYTD);
						ff.setRevenuelastyr(revenueLastYear);
						ff.setTargetytd(targetYTD);
						ff.setFlownYTd(flownYTD);
						ff.setCompartment(compartment);
						ffMap.put(combinationKey, ff);

						totalRevenue += revenueYTD;
						totalTarget += targetYTD;
						totalFlownRevenueYTD += flownYTD;
						totalRevenueLastYear += revenueLastYear;

					} else {
						FrequentFlyer ff = ffMap.get(combinationKey);

						ff.setRevenueYtd(ff.getRevenueYtd() + revenueYTD);
						ff.setRevenuelastyr(ff.getRevenuelastyr() + revenueLastYear);
						ff.setTargetytd(ff.getTargetytd() + targetYTD);
						ff.setFlownYTd(ff.getFlownYTd() + flownYTD);

						totalRevenue += revenueYTD;
						totalTarget += targetYTD;
						totalFlownRevenueYTD += flownYTD;

						totalRevenueLastYear += revenueLastYear;
					}
				}
				for (String key : ffMap.keySet()) {

					FrequentFlyer ff = ffMap.get(key);
					Double revenueVLYR = 0D;
					Double revenueVTGT = 0D;
					Double frequentflyerRevenuePerc = 0D;

					if (totalRevenue != 0) {
						frequentflyerRevenuePerc = (ff.getRevenueYtd() / totalRevenue) * 100;
					}

					if (ff.getRevenuelastyr() != 0) {
						revenueVLYR = ((ff.getRevenueYtd() - ff.getRevenuelastyr()) / ff.getRevenuelastyr()) * 100;
					}
					if (ff.getTargetytd() != 0) {
						revenueVTGT = (ff.getRevenueYtd() - ff.getTargetytd() / ff.getTargetytd()) * 100;
					}

					ff.setFrequentrevenueperc(frequentflyerRevenuePerc);
					ff.setFrequenttargetperc((double) 0);
					ff.setFrequentvarienceperc((double) 0);
					ff.setRevenueVLYR(revenueVLYR);
					ff.setRevenueVtgt(revenueVTGT);

				}
				TotalsModel tm = new TotalsModel();
				tm.setTotalRevenueYTD(totalRevenue.floatValue());
				tm.setTotalFlownRevenueYTD(totalFlownRevenueYTD.floatValue());

				if (totalRevenueLastYear != 0)
					totalRevenueVLYR = ((totalRevenue - totalRevenueLastYear) / totalRevenueLastYear) * 100;
				if (totalTarget != 0)
					totalRevenueVTGT = ((totalRevenue - totalTarget) / totalTarget) * 100;
				tm.setTotalRevenueVLYR(totalRevenueVLYR.floatValue());
				tm.setTotalRevenueVTGT(totalRevenueVTGT.floatValue());

				List<FrequentFlyer> ffList = new ArrayList<FrequentFlyer>(ffMap.values());
				List<TotalsModel> tmList = new ArrayList<TotalsModel>();
				tmList.add(tm);

				frequentFlyerMap.put("frequentflyerSummaryMap", ffList);
				frequentFlyerMap.put("frequentflyerTotalsMap", tmList);
			}
		} catch (Exception e) {
			logger.error("getFrequentFlyers-Exception", e);
		}
		return frequentFlyerMap;

	}

	@Override
	public Map<String, Object> getDistributors(RequestModel pRequestModel) {

		ArrayList<DBObject> lDistributorSummary = mCustSegDashboardDao.getDistributors(pRequestModel);
		JSONArray lData = new JSONArray(lDistributorSummary);

		Map<String, Object> lDistributorsSummaryMap = new HashMap<String, Object>();
		Map<String, DistributorSummary> lDsMap = new HashMap<String, DistributorSummary>();

		Double lTotalRevenue = 0D;
		Double lTotalTarget = 0D;
		Double lTotalFlownRevenueYTD = 0D;
		Double lTotalRevenueLastYear = 0D;
		Double lTotalRevenueVLYR = 0D;
		Double lTotalRevenueVTGT = 0D;
		JSONArray reveneulastyrArray = null;
		double revenuelastyr = 0;

		try {
			if (lData != null) {

				for (int i = 0; i < lData.length(); i++) {
					JSONObject lJsonObj = lData.getJSONObject(i);
					String lCombinationKey = getCombinationKeyDashboardScreen(pRequestModel, lJsonObj);

					String lRegion = "-";
					if (lJsonObj.get("region") != null && !"null".equalsIgnoreCase(lJsonObj.get("region").toString()))
						lRegion = lJsonObj.getString("region");
					String lCountry = "-";
					if (lJsonObj.get("country") != null && !"null".equalsIgnoreCase(lJsonObj.get("country").toString()))
						lCountry = lJsonObj.getString("country");
					String lPos = "-";
					if (lJsonObj.get("pos") != null && !"null".equalsIgnoreCase(lJsonObj.get("pos").toString()))
						lPos = lJsonObj.getString("pos");
					String lCompartment = "-";
					if (lJsonObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("compartment").toString()))
						lCompartment = lJsonObj.getString("compartment");
					float lRevenueYTD = 0;
					if (lJsonObj.get("revenue") != null && !"null".equalsIgnoreCase(lJsonObj.get("revenue").toString()))
						lRevenueYTD = Float.parseFloat(lJsonObj.get("revenue").toString());

					if (lJsonObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("revenue_1").toString())) {
						reveneulastyrArray = new JSONArray(lJsonObj.get("revenue_1").toString());
						if (reveneulastyrArray != null) {
							if (reveneulastyrArray.length() > 0) {
								for (int j = 0; j < reveneulastyrArray.length(); j++) {
									if (reveneulastyrArray != null
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("null")
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
										revenuelastyr = Utility.findSum(reveneulastyrArray);

									}
								}
							}
						}
					}
					String lDistributor = "-";
					if (lJsonObj.get("Distributor") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("Distributor").toString()))
						lDistributor = lJsonObj.getString("Distributor");

					Double lFlownYTD = 0D;
					if (lJsonObj.get("flown_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("flown_revenue").toString()))
						lFlownYTD = Utility.findSum(lJsonObj.get("flown_revenue"));

					Double lTargetYTD = 0D;
					if (lJsonObj.get("target_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("target_revenue").toString()))
						lTargetYTD = Utility.findSum(lJsonObj.get("target_revenue"));

					lCombinationKey += lDistributor;

					if (!lDsMap.containsKey(lCombinationKey)) {
						DistributorSummary lDsModel = new DistributorSummary();
						lDsModel.setRegion(lRegion);
						lDsModel.setCountry(lCountry);
						lDsModel.setPos(lPos);
						lDsModel.setCombinationkey(lCombinationKey);
						lDsModel.setRevenueYtd(lRevenueYTD);
						lDsModel.setRevenuelastyr((float) revenuelastyr);
						lDsModel.setTargetytd(lTargetYTD);
						lDsModel.setFlownYTd(lFlownYTD);
						lDsModel.setCompartment(lCompartment);
						lDsModel.setDistributor(lDistributor);
						lDsMap.put(lCombinationKey, lDsModel);

						lTotalRevenue += lRevenueYTD;
						lTotalTarget += lTargetYTD;
						lTotalFlownRevenueYTD += lFlownYTD;
						lTotalRevenueLastYear += revenuelastyr;

					} else {
						DistributorSummary lDsModel = lDsMap.get(lCombinationKey);

						lDsModel.setRevenueYtd(lDsModel.getRevenueYtd() + lRevenueYTD);
						lDsModel.setRevenuelastyr((float) (lDsModel.getRevenuelastyr() + revenuelastyr));
						lDsModel.setTargetytd(lDsModel.getTargetytd() + lTargetYTD);
						lDsModel.setFlownYTd(lDsModel.getFlownYTd() + lFlownYTD);
						lTotalRevenue += lRevenueYTD;
						lTotalTarget += lTargetYTD;
						lTotalFlownRevenueYTD += lFlownYTD;

						lTotalRevenueLastYear += revenuelastyr;
					}
				}
				for (String key : lDsMap.keySet()) {

					DistributorSummary lDsModel = lDsMap.get(key);
					float lRevenueVLYR = 0;
					float lRevenueVTGT = 0;
					Double lDistributorRevenuePerc = 0D;
					Double lDistributorTargetPerc = 0D;
					Double lDistributorVariancePerc = 0D;

					if (lTotalRevenue != 0) {
						lDistributorRevenuePerc = (lDsModel.getRevenueYtd() / lTotalRevenue) * 100;
					}
					if (lTotalTarget != 0) {
						lDistributorTargetPerc = (lDsModel.getTargetytd() / lTotalTarget) * 100;
					}
					if (lDistributorTargetPerc != 0) {
						lDistributorVariancePerc = ((lDistributorRevenuePerc - lDistributorTargetPerc));
					}

					if (lDsModel.getRevenuelastyr() != 0) {

						lRevenueVLYR = CalculationUtil.calculateVLYR(lDsModel.getRevenueYtd(),
								lDsModel.getRevenuelastyr());
					}
					if (lDsModel.getTargetytd() != 0) {

						lRevenueVTGT = CalculationUtil.calculateVTGT(lDsModel.getRevenueYtd(),
								(float) lDsModel.getTargetytd());
					}

					lDsModel.setDistributorrevenueperc(lDistributorRevenuePerc);
					lDsModel.setDistributortargetperc(lDistributorTargetPerc);
					lDsModel.setDistributorvarianceperc(lDistributorVariancePerc);
					lDsModel.setRevenueVLYR((double) lRevenueVLYR);
					lDsModel.setRevenueVtgt((double) lRevenueVTGT);

				}

				DistributorTotalResponse lDtModel = new DistributorTotalResponse();
				lDtModel.setTotalrevenueYtd(lTotalRevenue.floatValue());
				lDtModel.setTotalflownytd(lTotalFlownRevenueYTD.floatValue());

				if (lTotalRevenueLastYear != 0)
					lTotalRevenueVLYR = ((lTotalRevenue - lTotalRevenueLastYear) / lTotalRevenueLastYear) * 100;
				if (lTotalTarget != 0)
					lTotalRevenueVTGT = ((lTotalRevenue - lTotalTarget) / lTotalTarget) * 100;
				lDtModel.setTotalrevenuevlyr(lTotalRevenueVLYR.floatValue());
				lDtModel.setTotalrevenuevtgt(lTotalRevenueVTGT.floatValue());

				List<DistributorSummary> lDsList = new ArrayList<DistributorSummary>(lDsMap.values());
				List<DistributorTotalResponse> lDtList = new ArrayList<DistributorTotalResponse>();
				lDtList.add(lDtModel);

				lDistributorsSummaryMap.put("distributorsummaryMap", lDsList);
				lDistributorsSummaryMap.put("distributorsummaryTotalsMap", lDtList);

			}
		} catch (Exception e) {
			logger.error("getDistributors-Exception", e);
		}
		return lDistributorsSummaryMap;

	}

	@Override
	public Map<String, Object> getDistributorDetails(RequestModel pRequestModel) {

		ArrayList<DBObject> lDistributorData = mCustSegDashboardDao.getDistributorDetails(pRequestModel);
		JSONArray lData = new JSONArray(lDistributorData);
		Map<String, Object> lDistributorSummaryDetailsMap = new HashMap<String, Object>();
		Map<String, DistributorSummaryDetails> lDsMap = new HashMap<String, DistributorSummaryDetails>();

		Double lRevenueTotal = 0D;
		Double lRevenueFlownTotal = 0D;
		Double lRevenueLastYearTotal = 0D;
		Double lRevenueTargetTotal = 0D;
		Double lPaxTotal = 0D;
		Double lPaxLastYearTotal = 0D;
		Double lPaxTargetTotal = 0D;
		Double lPaxDistanceTotal = 0D;
		Double lHostPaxTotal = 0D;
		Double lComp1paxTotal = 0D;
		Double lComp2paxTotal = 0D;
		Double lComp3paxTotal = 0D;
		Double lComp4paxTotal = 0D;
		Double lComp5paxTotal = 0D;
		Double lMarketPaxCompleteTotal = 0D;
		Double lMarketPaxLastYearCompleteTotal = 0D;
		Double lHostPaxLastYearTotal = 0D;
		Double lPaxDistanceLastYearTotal = 0D;
		JSONArray reveneulastyrArray = null;
		double revenuelastyr = 0;
		JSONArray paxlastyrArray = null;
		double paxlastyr = 0;
		try {
			if (lData != null) {

				for (int i = 0; i < lData.length(); i++) {
					JSONObject lJsonObj = lData.getJSONObject(i);
					String lCombinationKey = getCombinationKeyDetailScreen(pRequestModel, lJsonObj);

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

					String lCompartment = "-";
					if (checkIfValueExist("compartment", lJsonObj)) {
						lCompartment = lJsonObj.getString("compartment");
					}

					String lChannel = "-";
					if (checkIfValueExist("channel", lJsonObj)) {
						lChannel = lJsonObj.getString("channel");
					}
					String lDistributor = "-";
					if (checkIfValueExist("distributor", lJsonObj)) {
						lDistributor = lJsonObj.getString("distributor");
					}

					Double lRevenue = 0D;
					if (checkIfValueExist("revenue", lJsonObj)) {
						lRevenue = lJsonObj.getDouble("revenue");
					}

					if (checkIfValueExist("revenue_1", lJsonObj)) {

						reveneulastyrArray = new JSONArray(lJsonObj.get("revenue_1").toString());
						if (reveneulastyrArray != null) {
							if (reveneulastyrArray.length() > 0) {
								for (int j = 0; j < reveneulastyrArray.length(); j++) {
									if (reveneulastyrArray != null
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("null")
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
										revenuelastyr = Utility.findSum(reveneulastyrArray);

									}
								}
							}
						}
					}

					Double lPax = 0D;
					if (checkIfValueExist("pax", lJsonObj)) {
						lPax = lJsonObj.getDouble("pax");
					}

					if (checkIfValueExist("pax_1", lJsonObj)) {
						paxlastyrArray = new JSONArray(lJsonObj.get("pax_1").toString());
						if (paxlastyrArray != null) {
							if (paxlastyrArray.length() > 0) {
								for (int j = 0; j < paxlastyrArray.length(); j++) {
									if (paxlastyrArray != null
											&& !paxlastyrArray.get(j).toString().equalsIgnoreCase("null")
											&& !paxlastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
										paxlastyr = Utility.findSum(paxlastyrArray);

									}
								}
							}
						}

					}

					Double lFlownRevenue = 0D;
					if (checkIfValueExist("flown_revenue", lJsonObj)) {
						lFlownRevenue = Utility.findSum(lJsonObj.get("flown_revenue"));
					}

					Double lPaxTarget = 0D;
					if (checkIfValueExist("target_pax", lJsonObj)) {
						lPaxTarget = Utility.findSum(lJsonObj.get("target_pax"));
					}

					Double lRevenueTarget = 0D;
					if (checkIfValueExist("target_revenue", lJsonObj)) {
						lRevenueTarget = Utility.findSum(lJsonObj.get("target_revenue"));
					}

					Double lDistance = 0D;
					if (checkIfValueExist("distance", lJsonObj)) {
						lDistance = Utility.findSum(lJsonObj.get("distance"));
					}

					JSONArray lCarrierArray = new JSONArray();
					if (checkIfValueExist("carrier", lJsonObj)) {
						lCarrierArray = lJsonObj.getJSONArray("carrier");
					}

					Double lMarketPaxComplete = 0D;
					JSONArray lMarketPaxArray = new JSONArray();
					if (checkIfValueExist("market_share_pax", lJsonObj)) {
						lMarketPaxArray = lJsonObj.getJSONArray("market_share_pax");
						if (lMarketPaxArray.length() > 0) {
							lMarketPaxComplete = Utility.findSum(lJsonObj.get("market_share_pax"));
						}
					}

					Double lMarketPaxLastYearComplete = 0D;
					JSONArray lMarketPaxLastYearArray = new JSONArray();
					if (checkIfValueExist("market_share_pax_1", lJsonObj)) {
						lMarketPaxLastYearArray = lJsonObj.getJSONArray("market_share_pax_1");
						if (lMarketPaxArray.length() > 0) {
							lMarketPaxLastYearComplete = Utility.findSum(lJsonObj.get("market_share_pax_1"));
						}
					}

					Map<String, Double> lCarrierPaxMap = new HashMap<String, Double>();
					Map<String, Double> lCarrierPaxMapLastYear = new HashMap<String, Double>();
					for (int k = 0; k < lCarrierArray.length(); k++) {
						lCarrierPaxMap.put(lCarrierArray.getString(k), lMarketPaxArray.getDouble(k));
						lCarrierPaxMapLastYear.put(lCarrierArray.getString(k), lMarketPaxLastYearArray.getDouble(k));
					}

					String lHost = "FZ";
					String lComp1 = "EK";
					String lComp2 = "QR";
					String lComp3 = "EY";
					String lComp4 = "G9";
					String lComp5 = "ET";
					Double lHostPax = 0D;
					Double lHostPaxLastYear = 0D;
					Double lComp1pax = 0D;
					Double lComp2pax = 0D;
					Double lComp3pax = 0D;
					Double lComp4pax = 0D;
					Double lComp5pax = 0D;
					if (lCarrierPaxMap.containsKey(lHost)) {
						lHostPax = lCarrierPaxMap.get(lHost);
					}
					if (lCarrierPaxMapLastYear.containsKey(lHost)) {
						lHostPaxLastYear = lCarrierPaxMapLastYear.get(lHost);
					}
					if (lCarrierPaxMap.containsKey(lComp1)) {
						lComp1pax = lCarrierPaxMap.get(lComp1);
					}
					if (lCarrierPaxMap.containsKey(lComp2)) {
						lComp2pax = lCarrierPaxMap.get(lComp2);
					}
					if (lCarrierPaxMap.containsKey(lComp3)) {
						lComp3pax = lCarrierPaxMap.get(lComp3);
					}
					if (lCarrierPaxMap.containsKey(lComp4)) {
						lComp4pax = lCarrierPaxMap.get(lComp4);
					}
					if (lCarrierPaxMap.containsKey(lComp5)) {
						lComp5pax = lCarrierPaxMap.get(lComp5);
					}

					lCombinationKey += lDistributor;
					if (!lDsMap.containsKey(lCombinationKey)) {
						DistributorSummaryDetails lDsModel = new DistributorSummaryDetails();
						lDsModel.setRegion(lRegion);
						lDsModel.setCountry(lCountry);
						lDsModel.setPos(lPos);
						lDsModel.setOrigin(lOrigin);
						lDsModel.setDestination(lDestination);
						lDsModel.setCompartment(lCompartment);
						lDsModel.setDistributor(lDistributor);
						lDsModel.setChannel(lChannel);
						lDsModel.setRevenue(lRevenue);
						lDsModel.setRevenueLastYear(revenuelastyr);
						lDsModel.setRevenueTarget(lRevenueTarget);
						lDsModel.setRevenueFlown(lFlownRevenue);
						lDsModel.setPax(lPax);
						lDsModel.setPaxLastYear(paxlastyr);
						lDsModel.setPaxTarget(lPaxTarget);
						lDsModel.setDistance(lDistance);
						lDsModel.setPaxDistance(lPax * lDistance);
						lDsModel.setPaxDistanceLastYear(paxlastyr * lDistance);
						lDsModel.setHostPax(lHostPax);
						lDsModel.setHostPaxLastYear(lHostPaxLastYear);
						lDsModel.setComp1pax(lComp1pax);
						lDsModel.setComp2pax(lComp2pax);
						lDsModel.setComp3pax(lComp3pax);
						lDsModel.setComp4pax(lComp4pax);
						lDsModel.setComp5pax(lComp5pax);
						lDsModel.setMarketPaxComplete(lMarketPaxComplete);
						lDsModel.setMarketPaxLastYearComplete(lMarketPaxLastYearComplete);
						lDsMap.put(lCombinationKey, lDsModel);

						lRevenueTotal += lRevenue;
						lRevenueFlownTotal += lFlownRevenue;
						lRevenueLastYearTotal += revenuelastyr;
						lRevenueTargetTotal += lRevenueTarget;
						lPaxTotal += lPax;
						lPaxLastYearTotal += paxlastyr;
						lPaxTargetTotal += lPaxTarget;
						lPaxDistanceTotal += (lPax * lDistance);
						lHostPaxTotal += lHostPax;
						lComp1paxTotal += lComp1pax;
						lComp2paxTotal += lComp2pax;
						lComp3paxTotal += lComp3pax;
						lComp4paxTotal += lComp4pax;
						lMarketPaxCompleteTotal += lMarketPaxComplete;
						lMarketPaxLastYearCompleteTotal += lMarketPaxLastYearComplete;
						lHostPaxLastYearTotal += lHostPaxLastYear;
						lPaxDistanceLastYearTotal += (paxlastyr * lDistance);

					} else {
						DistributorSummaryDetails ds = lDsMap.get(lCombinationKey);
						ds.setRevenue(ds.getRevenue() + lRevenue);
						ds.setRevenueLastYear(ds.getRevenueLastYear() + revenuelastyr);
						ds.setRevenueTarget(ds.getRevenueTarget() + lRevenueTarget);
						ds.setRevenueFlown(ds.getRevenueFlown() + lFlownRevenue);
						ds.setPax(ds.getPax() + lPax);
						ds.setPaxLastYear(ds.getPaxLastYear() + paxlastyr);
						ds.setPaxTarget(ds.getPaxTarget() + lPaxTarget);
						ds.setDistance(ds.getDistance() + lDistance);
						ds.setPaxDistance(ds.getPaxDistance() + (ds.getPax() * ds.getDistance()));
						ds.setPaxDistanceLastYear(
								ds.getPaxDistanceLastYear() + (ds.getPaxLastYear() * ds.getDistance()));
						ds.setHostPax(ds.getHostPax() + lHostPax);
						ds.setHostPaxLastYear(ds.getHostPaxLastYear() + lHostPaxLastYear);
						ds.setComp1pax(ds.getComp1pax() + lComp1pax);
						ds.setComp2pax(ds.getComp2pax() + lComp2pax);
						ds.setComp3pax(ds.getComp3pax() + lComp3pax);
						ds.setComp4pax(ds.getComp4pax() + lComp4pax);
						ds.setComp5pax(ds.getComp5pax() + lComp5pax);
						ds.setMarketPaxComplete(ds.getMarketPaxComplete() + lMarketPaxComplete);
						ds.setMarketPaxLastYearComplete(ds.getMarketPaxLastYearComplete() + lMarketPaxLastYearComplete);

						lRevenueTotal += lRevenue;
						lRevenueFlownTotal += lFlownRevenue;
						lRevenueLastYearTotal += revenuelastyr;
						lRevenueTargetTotal += lRevenueTarget;
						lPaxTotal += lPax;
						lPaxLastYearTotal += paxlastyr;
						lPaxTargetTotal += lPaxTarget;
						lPaxDistanceTotal += (lPax * lDistance);
						lHostPaxTotal += lHostPax;
						lComp1paxTotal += lComp1pax;
						lComp2paxTotal += lComp2pax;
						lComp3paxTotal += lComp3pax;
						lComp4paxTotal += lComp4pax;
						lComp5paxTotal += lComp5pax;
						lMarketPaxCompleteTotal += lMarketPaxComplete;
						lMarketPaxLastYearCompleteTotal += lMarketPaxLastYearComplete;
						lHostPaxLastYearTotal += lHostPaxLastYear;
						lPaxDistanceLastYearTotal += (paxlastyr * lDistance);

					}
				}
				for (String lKey : lDsMap.keySet()) {
					DistributorSummaryDetails lDsModel = lDsMap.get(lKey);
					Double lRevenueVLYR = 0D;
					Double lRevenueVTGT = 0D;
					Double lPaxVTYR = 0D;
					Double lPaxVTGT = 0D;
					Double lAverageFare = 0D;
					Double lYeild = 0D;
					Double lYeildLastYear = 0D;
					Double lYeildVLYR = 0D;
					Double lHostMarketShare = 0D;
					Double lHostMarketShareLastYear = 0D;
					Double lHostMarketShareVLYR = 0D;
					Double lComp1MarketShare = 0D;
					Double lComp2MarketShare = 0D;
					Double lComp3MarketShare = 0D;
					Double lComp4MarketShare = 0D;
					Double lComp5MarketShare = 0D;
					Double lRevenuePerc = 0D;
					Double lPaxPerc = 0D;

					if (lDsModel.getRevenueLastYear() != 0) {
						lRevenueVLYR = ((lDsModel.getRevenue() - lDsModel.getRevenueLastYear())
								/ lDsModel.getRevenueLastYear()) * 100;
					}
					if (lDsModel.getRevenueTarget() != 0) {
						lRevenueVTGT = ((lDsModel.getRevenue() - lDsModel.getRevenueTarget())
								/ lDsModel.getRevenueTarget()) * 100;
					}
					if (lDsModel.getPaxLastYear() != 0) {
						lPaxVTYR = ((lDsModel.getPax() - lDsModel.getPaxLastYear()) / lDsModel.getPaxLastYear()) * 100;
					}
					if (lDsModel.getPaxTarget() != 0) {
						lPaxVTGT = ((lDsModel.getPax() - lDsModel.getPaxTarget()) / lDsModel.getPaxTarget()) * 100;
					}
					if (lDsModel.getPax() != 0) {
						lAverageFare = lDsModel.getRevenue() / lDsModel.getPax();
					}
					if (lDsModel.getPaxDistance() != 0) {
						lYeild = lDsModel.getRevenue() / lDsModel.getPaxDistance();
					}
					if (lDsModel.getPaxDistanceLastYear() != 0) {
						lYeildLastYear = lDsModel.getRevenue() / lDsModel.getPaxDistanceLastYear();
					}
					if (lYeildLastYear != 0) {
						lYeildVLYR = ((lYeild - lYeildLastYear) / lYeildLastYear) * 100;
					}
					if (lDsModel.getMarketPaxComplete() != 0) {
						lHostMarketShare = (lDsModel.getHostPax() / lDsModel.getMarketPaxComplete()) * 100;
					}
					if (lDsModel.getMarketPaxLastYearComplete() != 0) {
						lHostMarketShareLastYear = (lDsModel.getHostPaxLastYear()
								/ lDsModel.getMarketPaxLastYearComplete()) * 100;
					}
					if (lHostMarketShareLastYear != 0) {
						lHostMarketShareVLYR = ((lHostMarketShare - lHostMarketShareLastYear)
								/ lHostMarketShareLastYear) * 100;
					}
					if (lDsModel.getMarketPaxComplete() != 0) {
						lComp1MarketShare = lDsModel.getComp1pax() / lDsModel.getMarketPaxComplete() * 100;
					}
					if (lDsModel.getMarketPaxComplete() != 0) {
						lComp2MarketShare = lDsModel.getComp2pax() / lDsModel.getMarketPaxComplete() * 100;
					}
					if (lDsModel.getMarketPaxComplete() != 0) {
						lComp3MarketShare = lDsModel.getComp3pax() / lDsModel.getMarketPaxComplete() * 100;
					}
					if (lDsModel.getMarketPaxComplete() != 0) {
						lComp4MarketShare = lDsModel.getComp4pax() / lDsModel.getMarketPaxComplete() * 100;
					}
					if (lDsModel.getMarketPaxComplete() != 0) {
						lComp5MarketShare = lDsModel.getComp5pax() / lDsModel.getMarketPaxComplete() * 100;
					}
					if (lRevenueTotal != 0) {
						lRevenuePerc = (lDsModel.getRevenue() / lRevenueTotal) * 100;
					}
					if (lPaxTotal != 0) {
						lPaxPerc = (lDsModel.getPax() / lPaxTotal) * 100;
					}
					Map<String, Double> carrierPaxMap = new HashMap<String, Double>();
					carrierPaxMap.put("FZ", lHostMarketShare);
					carrierPaxMap.put("FZ VLYR", lHostMarketShareVLYR);
					carrierPaxMap.put("EK", lComp1MarketShare);
					carrierPaxMap.put("QR", lComp2MarketShare);
					carrierPaxMap.put("EY", lComp3MarketShare);
					carrierPaxMap.put("G9", lComp4MarketShare);
					carrierPaxMap.put("ET", lComp5MarketShare);
					lDsModel.setCarrierPaxMap(carrierPaxMap);
					lDsModel.setRevenueVLYR(lRevenueVLYR);
					lDsModel.setRevenueVTGT(lRevenueVTGT);
					lDsModel.setPaxVLYR(lPaxVTYR);
					lDsModel.setPaxVTGT(lPaxVTGT);
					lDsModel.setYeild(lYeild);
					lDsModel.setYeildVLYR(lYeildVLYR);
					lDsModel.setAverageFare(lAverageFare);
					lDsModel.setHostMarketShare(lHostMarketShare);
					lDsModel.setHostMarketShareVLYR(lHostMarketShareVLYR);
					lDsModel.setComp1MarketShare(lComp1MarketShare);
					lDsModel.setComp2MarketShare(lComp2MarketShare);
					lDsModel.setComp3MarketShare(lComp3MarketShare);
					lDsModel.setComp4MarketShare(lComp4MarketShare);
					lDsModel.setComp5MarketShare(lComp5MarketShare);
					lDsModel.setPaxPerc(lPaxPerc);
					lDsModel.setRevenuePerc(lRevenuePerc);
					// setting them to zero as lData is not available
					lDsModel.setFairMarketShare(0D);
					lDsModel.setHostMarketShareVTGT(0D);

				}

				List<DistributorSummaryDetails> lDsdList = new ArrayList<DistributorSummaryDetails>(lDsMap.values());
				DistributionDetailsTotalResponse lDdtResponse = new DistributionDetailsTotalResponse();
				Double lRevenueVLYRTotal = 0D;
				Double lRevenueVTGTTotal = 0D;
				Double lPaxVLYRTotal = 0D;
				Double lPaxVTGTTotal = 0D;
				Double lAverageFareTotal = 0D;
				Double lYeildTotal = 0D;
				Double lYeildLastYearTotal = 0D;
				Double lYeildVLYRTotal = 0D;
				Double lHostMarketShareTotal = 0D;
				Double lHostMarketShareLastYearTotal = 0D;
				Double lHostMarketShareVLYRTotal = 0D;
				Double lComp1MarketShareTotal = 0D;
				Double lComp2MarketShareTotal = 0D;
				Double lComp3MarketShareTotal = 0D;
				Double lComp4MarketShareTotal = 0D;
				Double lComp5MarketShareTotal = 0D;

				if (lRevenueLastYearTotal != 0) {
					lRevenueVLYRTotal = ((lRevenueTotal - lRevenueLastYearTotal) / lRevenueLastYearTotal) * 100;
				}
				if (lRevenueTargetTotal != 0) {
					lRevenueVTGTTotal = ((lRevenueTotal - lRevenueTargetTotal) / lRevenueTargetTotal) * 100;
				}
				if (lPaxLastYearTotal != 0) {
					lPaxVLYRTotal = ((lPaxTotal - lPaxLastYearTotal) / lPaxLastYearTotal) * 100;
				}
				if (lPaxTargetTotal != 0) {
					lPaxVTGTTotal = ((lPaxTotal - lPaxTargetTotal) / lPaxTargetTotal) * 100;
				}
				if (lPaxTotal != 0) {
					lAverageFareTotal = lRevenueTotal / lPaxTotal;
				}
				if (lPaxDistanceTotal != 0) {
					lYeildTotal = lRevenueTotal / lPaxDistanceTotal;
				}
				if (lPaxDistanceLastYearTotal != 0) {
					lYeildLastYearTotal = lRevenueLastYearTotal / lPaxDistanceLastYearTotal;
				}
				if (lYeildLastYearTotal != 0) {
					lYeildVLYRTotal = ((lYeildTotal - lYeildLastYearTotal) / lYeildLastYearTotal) * 100;
				}
				if (lMarketPaxCompleteTotal != 0) {
					lHostMarketShareTotal = (lHostPaxTotal / lMarketPaxCompleteTotal) * 100;
				}
				if (lMarketPaxLastYearCompleteTotal != 0) {
					lHostMarketShareLastYearTotal = (lHostPaxLastYearTotal / lMarketPaxLastYearCompleteTotal) * 100;
				}
				if (lHostMarketShareLastYearTotal != 0) {
					lHostMarketShareVLYRTotal = ((lHostMarketShareTotal - lHostMarketShareLastYearTotal)
							/ lHostMarketShareLastYearTotal) * 100;
				}
				if (lMarketPaxCompleteTotal != 0) {
					lComp1MarketShareTotal = (lComp1paxTotal / lMarketPaxCompleteTotal) * 100;
				}
				if (lMarketPaxCompleteTotal != 0) {
					lComp2MarketShareTotal = (lComp2paxTotal / lMarketPaxCompleteTotal) * 100;
				}
				if (lMarketPaxCompleteTotal != 0) {
					lComp3MarketShareTotal = (lComp3paxTotal / lMarketPaxCompleteTotal) * 100;
				}
				if (lMarketPaxCompleteTotal != 0) {
					lComp4MarketShareTotal = (lComp4paxTotal / lMarketPaxCompleteTotal) * 100;
				}
				if (lMarketPaxCompleteTotal != 0) {
					lComp5MarketShareTotal = (lComp5paxTotal / lMarketPaxCompleteTotal) * 100;
				}
				Map<String, Double> totalcarrierPaxMap = new HashMap<String, Double>();
				totalcarrierPaxMap.put("FZ", lHostMarketShareTotal);
				totalcarrierPaxMap.put("FZ VLYR", lHostMarketShareVLYRTotal);
				totalcarrierPaxMap.put("EK", lComp1MarketShareTotal);
				totalcarrierPaxMap.put("QR", lComp2MarketShareTotal);
				totalcarrierPaxMap.put("EY", lComp3MarketShareTotal);
				totalcarrierPaxMap.put("G9", lComp4MarketShareTotal);
				totalcarrierPaxMap.put("ET", lComp5MarketShareTotal);
				lDdtResponse.setTotalcarrierPaxMap(totalcarrierPaxMap);

				lDdtResponse.setRevenueTotal(lRevenueTotal);
				lDdtResponse.setRevenueFlownTotal(lRevenueFlownTotal);
				lDdtResponse.setPaxTotal(lPaxTotal);
				lDdtResponse.setRevenueVLYRTotal(lRevenueVLYRTotal);
				lDdtResponse.setRevenueVTGTTotal(lRevenueVTGTTotal);
				lDdtResponse.setPaxVLYRTotal(lPaxVLYRTotal);
				lDdtResponse.setPaxVTGTTotal(lPaxVTGTTotal);
				lDdtResponse.setYeildTotal(lYeildTotal);
				lDdtResponse.setYeildVLYRTotal(lYeildVLYRTotal);
				lDdtResponse.setAverageFareTotal(lAverageFareTotal);
				lDdtResponse.setHostMarketShareTotal(lHostMarketShareTotal);
				lDdtResponse.setHostMarketShareVLYRTotal(lHostMarketShareVLYRTotal);
				lDdtResponse.setComp1MarketShareTotal(lComp1MarketShareTotal);
				lDdtResponse.setComp2MarketShareTotal(lComp2MarketShareTotal);
				lDdtResponse.setComp3MarketShareTotal(lComp3MarketShareTotal);
				lDdtResponse.setComp4MarketShareTotal(lComp4MarketShareTotal);
				// setting these values as lData is not available
				lDdtResponse.setHostMarkerShareVTGT(0D);
				lDdtResponse.setFairMarketShareTotal(0D);

				lDistributorSummaryDetailsMap.put("DistributorSummaryDetailsTotals", lDdtResponse);
				lDistributorSummaryDetailsMap.put("DistributorSummaryDetails", lDsdList);

			}
		} catch (Exception e) {
			logger.error("getDistributorDetails-Exception", e);
		}

		return lDistributorSummaryDetailsMap;

	}

	@Override
	public Map<String, Object> getChannelSummaryDirect(RequestModel pRequestModel) {

		ArrayList<DBObject> lChannelSummaryList = mCustSegDashboardDao.getChannelSummaryDirect(pRequestModel);
		JSONArray lData = new JSONArray(lChannelSummaryList);
		Map<String, Object> lDirectChannelSummaryMap = new HashMap<String, Object>();
		Map<String, DirectIndirectChannelSummary> lDcMap = new HashMap<String, DirectIndirectChannelSummary>();
		float lTotalRevenue = 0;
		float lTotalrevenueTarget = 0;
		float lTotalRevenueLastYear = 0;
		float lTotalPax = 0;
		float lTotalPaxLastYear = 0;
		float lTotalPaxTarget = 0;
		float lTotalRevenueVLYR = 0;
		float lTotalRevenueVTGT = 0;
		float lTotalPaxVLYR = 0;
		float lTotalPaxVTGT = 0;
		Double lTotalDistance = 0D;
		float lDirectChannelSummaryRevenuePerc = 0;
		float lTotalYeild = 0;
		float lTotalYeildLastYear = 0;
		float lTotalYeildVLYR = 0;
		float lPaxPerc = 0;
		float lTotalAverageFare = 0;
		JSONArray paxlastyrArray = null;
		JSONArray reveneulastyrArray = null;

		String lCombinationKey = null;
		try {
			if (lData != null) {

				for (int i = 0; i < lData.length(); i++) {
					JSONObject lJsonObj = lData.getJSONObject(i);

					// getting combination key according to user profile and
					// filter values
					lCombinationKey = getCombinationKeyDetailScreen(pRequestModel, lJsonObj);

					String lRegion = "-";
					if (lJsonObj.get("region") != null && !"null".equalsIgnoreCase(lJsonObj.get("region").toString()))
						lRegion = lJsonObj.getString("region");

					String lCountry = "-";
					if (lJsonObj.get("country") != null && !"null".equalsIgnoreCase(lJsonObj.get("country").toString()))
						lCountry = lJsonObj.getString("country");

					String lPos = "-";
					if (lJsonObj.get("pos") != null && !"null".equalsIgnoreCase(lJsonObj.get("pos").toString()))
						lPos = lJsonObj.getString("pos");

					String lOd = "-";
					if (lJsonObj.get("od") != null && !"null".equalsIgnoreCase(lJsonObj.get("od").toString()))
						lOd = lJsonObj.getString("od");

					String lCompartment = "-";
					if (lJsonObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("compartment").toString()))
						lCompartment = lJsonObj.getString("compartment");

					float lPaxYTD = 0;
					if (lJsonObj.get("pax") != null && !"null".equalsIgnoreCase(lJsonObj.get("pax").toString()))
						lPaxYTD = Float.parseFloat(lJsonObj.get("pax").toString());
					double lPaxLastYear = 0;
					if (lJsonObj.get("pax_1") != null && !"null".equalsIgnoreCase(lJsonObj.get("pax_1").toString())
							&& !"[]".equalsIgnoreCase(lJsonObj.get("pax_1").toString())) {
						paxlastyrArray = new JSONArray(lJsonObj.get("pax_1").toString());
						if (paxlastyrArray != null) {
							if (paxlastyrArray.length() > 0) {
								for (int j = 0; j < paxlastyrArray.length(); j++) {
									if (paxlastyrArray != null
											&& !paxlastyrArray.get(j).toString().equalsIgnoreCase("null")
											&& !paxlastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
										lPaxLastYear = Utility.findSum(paxlastyrArray);

									}
								}
							}
						}
					}

					Double lTargetPaxYTD = 0D;
					if (lJsonObj.get("target_pax") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("target_pax").toString()))
						lTargetPaxYTD = Utility.findSum(lJsonObj.get("target_pax"));
					float lRevenueYTD = 0;
					if (lJsonObj.get("revenue") != null && !"null".equalsIgnoreCase(lJsonObj.get("revenue").toString()))
						lRevenueYTD = Float.parseFloat(lJsonObj.get("revenue").toString());
					double lRevenueLastYear = 0;
					if (lJsonObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("revenue_1").toString())) {
						reveneulastyrArray = new JSONArray(lJsonObj.get("revenue_1").toString());
						if (reveneulastyrArray != null) {
							if (reveneulastyrArray.length() > 0) {
								for (int j = 0; j < reveneulastyrArray.length(); j++) {
									if (reveneulastyrArray != null
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("null")
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
										lRevenueLastYear = Utility.findSum(reveneulastyrArray);

									}
								}
							}
						}
					}
					Double lTargetRevenueYTD = 0D;
					if (lJsonObj.get("target_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("target_revenue").toString()))
						lTargetRevenueYTD = Utility.findSum(lJsonObj.get("target_revenue"));
					Double lFlownRevenue = 0D;
					if (lJsonObj.get("flown_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("flown_revenue").toString()))
						lFlownRevenue = Utility.findSum(lJsonObj.get("flown_revenue"));
					double lDistance = 0;
					if (lJsonObj.get("distance") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("distance").toString()))
						lDistance = Utility.findSum(lJsonObj.get("distance").toString());

					String lChannel = "-";
					if (lJsonObj.get("channel") != null && !"null".equalsIgnoreCase(lJsonObj.get("channel").toString()))
						lChannel = lJsonObj.getString("channel");

					String lOrigin = "-";
					String lDestination = "-";
					if (!lOd.equals("-")) {
						lOrigin = lOd.substring(0, 3);
						lDestination = lOd.substring(3);
					}

					lCombinationKey += lChannel;
					if (!lDcMap.containsKey(lCombinationKey)) {
						DirectIndirectChannelSummary lDcModel = new DirectIndirectChannelSummary();
						lDcModel.setRegion(lRegion);
						lDcModel.setCountry(lCountry);
						lDcModel.setPos(lPos);
						lDcModel.setOrigin(lOrigin);
						lDcModel.setDestination(lDestination);
						lDcModel.setCompartment(lCompartment);
						lDcModel.setChannel(lChannel);
						lDcModel.setCombinationkey(lCombinationKey);
						lDcModel.setRevenueytd(lRevenueYTD);
						lDcModel.setRevenueLastYear((float) lRevenueLastYear);
						lDcModel.setTargetrevenueytd(lTargetRevenueYTD);
						lDcModel.setPaxytd(lPaxYTD);
						lDcModel.setPaxlastyear((float) lPaxLastYear);
						lDcModel.setTargetpaxytd(lTargetPaxYTD);
						lDcModel.setDistance((float) lDistance);
						lDcModel.setFlownRevenue(lFlownRevenue);
						lDcModel.setDistancePax(lDcModel.getPaxytd() * lDcModel.getDistance());
						lTotalRevenue += lRevenueYTD;
						lTotalrevenueTarget += lTargetRevenueYTD;

						lTotalPax += lPaxYTD;
						lTotalPaxTarget += lTargetPaxYTD;

						lTotalRevenueLastYear += lRevenueLastYear;
						lTotalPaxLastYear += lPaxLastYear;
						lTotalDistance += lDistance;

						lDcMap.put(lCombinationKey, lDcModel);

					} else {
						DirectIndirectChannelSummary lDcModel = lDcMap.get(lCombinationKey);
						lDcModel.setRevenueytd(lDcModel.getRevenueytd() + lRevenueYTD);
						lDcModel.setRevenueLastYear((float) (lDcModel.getRevenueLastYear() + lRevenueLastYear));
						lDcModel.setTargetrevenueytd(lDcModel.getTargetrevenueytd() + lTargetRevenueYTD);
						lDcModel.setFlownRevenue(lDcModel.getFlownRevenue() + lFlownRevenue);

						lDcModel.setPaxytd(lDcModel.getPaxytd() + lPaxYTD);
						lDcModel.setPaxlastyear((float) (lDcModel.getPaxlastyear() + lPaxLastYear));
						lDcModel.setTargetpaxytd(lDcModel.getTargetpaxytd() + lTargetPaxYTD);
						lDcModel.setDistance((float) (lDcModel.getDistance() + lDistance));
						lDcModel.setDistancePax(
								lDcModel.getDistancePax() + lDcModel.getPaxytd() * lDcModel.getDistance());

						lTotalRevenue += lRevenueYTD;
						lTotalrevenueTarget += lTargetRevenueYTD;
						lTotalRevenueLastYear += lRevenueLastYear;
						lTotalPaxTarget += lTargetPaxYTD;

						lTotalPax += lPaxYTD;
						lTotalPaxLastYear += lPaxLastYear;
						lTotalDistance += lDistance;
					}

				}
				for (String key : lDcMap.keySet()) {
					DirectIndirectChannelSummary dc = lDcMap.get(key);

					float revenueVLYR = 0;
					float revenueVTGT = 0;
					float paxVLYR = 0;
					float paxVTGT = 0;
					float yeild = 0;
					float yeildLastYear = 0;
					float yeildVLYR = 0;
					float averageFare = 0;

					if (lTotalRevenue != 0) {
						lDirectChannelSummaryRevenuePerc = (dc.getRevenueytd() / lTotalRevenue) * 100;
					}

					if (dc.getRevenueLastYear() != 0) {
						revenueVLYR = CalculationUtil.calculateVLYR(dc.getRevenueytd(), dc.getRevenueLastYear());

					}
					if (dc.getTargetrevenueytd() != 0) {
						revenueVTGT = CalculationUtil.calculateVTGT(dc.getRevenueytd(),
								(float) dc.getTargetrevenueytd());

					}

					if (lTotalPax != 0) {
						lPaxPerc = (dc.getPaxytd() / lTotalPax) * 100;
					}
					if (dc.getPaxlastyear() != 0 || dc.getPaxlastyear() > 0) {
						paxVLYR = CalculationUtil.calculateVLYR(dc.getPaxytd(), dc.getPaxlastyear());
					} else {
						paxVLYR = 0;
					}
					if (dc.getTargetpaxytd() != 0) {
						paxVTGT = CalculationUtil.calculateVTGT(dc.getPaxytd(), (float) dc.getTargetpaxytd());
					}
					if (dc.getDistancePax() != 0) {
						yeild = CalculationUtil.calculateYield(dc.getRevenueytd(), dc.getPaxytd(), dc.getDistance());
					}
					if (dc.getDistance() * dc.getPaxlastyear() != 0) {
						yeildLastYear = CalculationUtil.calculateYield(dc.getRevenueLastYear(), dc.getPaxlastyear(),
								dc.getDistance());
					}
					if (yeildLastYear != 0) {
						yeildVLYR = CalculationUtil.calculateVLYR(yeild, yeildLastYear);
					}
					if (dc.getPaxytd() != 0) {
						averageFare = dc.getRevenueytd() / dc.getPaxytd();

					}
					lTotalYeild += yeild;
					lTotalYeildLastYear += yeildLastYear;

					dc.setFlownRevenue((dc.getRevenueytd()));

					dc.setRevenuevlyr((revenueVLYR));
					dc.setRevenuevtgt((revenueVTGT));
					dc.setPaxvlyr((paxVLYR));
					dc.setRevenueshareperc((lDirectChannelSummaryRevenuePerc));
					dc.setPaxvtgt((paxVTGT));
					dc.setYieldytd(CalculationUtil.roundAFloat(yeild, 1));
					dc.setYiedvlyr(CalculationUtil.roundAFloat(yeildVLYR, 2));
					dc.setAverageFare((averageFare));
					dc.setPaxperc((lPaxPerc));

					// rounding all the values
					dc.setRevenueytd((dc.getRevenueytd()));
					dc.setPaxytd(dc.getPaxytd());

				}

				DirectChannelTotalResponse tm = new DirectChannelTotalResponse();
				tm.setTotalRevenueYTD(lTotalRevenue);
				tm.setTotalPaxYTD(lTotalPax);
				if (lTotalRevenueLastYear != 0)
					lTotalRevenueVLYR = CalculationUtil.calculateVLYR(lTotalRevenue, lTotalRevenueLastYear);
				if (lTotalrevenueTarget != 0)
					lTotalRevenueVTGT = CalculationUtil.calculateVTGT(lTotalRevenue, lTotalrevenueTarget);
				if (lTotalPaxLastYear != 0 || lTotalPaxLastYear > 0)
					lTotalPaxVLYR = CalculationUtil.calculateVLYR(lTotalPax, lTotalPaxLastYear);
				if (lTotalPaxTarget != 0)
					lTotalPaxVTGT = CalculationUtil.calculateVTGT(lTotalPax, lTotalPaxTarget);
				if (lTotalYeildLastYear != 0) {
					lTotalYeildVLYR = CalculationUtil.calculateVLYR(lTotalYeild, lTotalYeildLastYear);
				}
				if (lTotalPax != 100) {
					lTotalAverageFare = lTotalRevenue / lTotalPax;
				}
				tm.setTotalAverageFare((lTotalAverageFare));
				tm.setTotalYeild(CalculationUtil.roundAFloat(lTotalYeild, 1));
				tm.setTotalYeildVLYR(CalculationUtil.roundAFloat(lTotalYeildVLYR, 2));
				tm.setTotalRevenueVLYR((lTotalRevenueVLYR));
				tm.setTotalRevenueVTGT((lTotalRevenueVTGT));
				tm.setTotalPaxVLYR((lTotalPaxVLYR));
				tm.setTotalPaxVTGT((lTotalPaxVTGT));

				List<DirectIndirectChannelSummary> dcList = new ArrayList<DirectIndirectChannelSummary>(
						lDcMap.values());
				List<DirectChannelTotalResponse> tmList = new ArrayList<DirectChannelTotalResponse>();
				tmList.add(tm);
				lDirectChannelSummaryMap.put("DirectchannelSummaryMap", dcList);
				lDirectChannelSummaryMap.put("DirectchannelSummaryTotalsMap", tmList);

			}
		} catch (Exception e) {
			logger.error("getChannelSummaryDirect-Exception", e);
		}

		return lDirectChannelSummaryMap;

	}

	@Override
	public Map<String, Object> getChannelSummaryIndirect(RequestModel pRequestModel) {

		ArrayList<DBObject> channeldata = mCustSegDashboardDao.getChannelSummaryIndirect(pRequestModel);
		JSONArray lData = new JSONArray(channeldata);
		Map<String, Object> inDirectChannelSummaryMap = new HashMap<String, Object>();
		Map<String, DirectIndirectChannelSummary> icMap = new HashMap<String, DirectIndirectChannelSummary>();
		float totalRevenue = 0;
		float totalrevenueTarget = 0;
		float totalRevenueLastYear = 0;
		float totalpax = 0;
		Double totalpaxlastyear = 0D;
		float totalpaxLastyear = 0;
		float totalpaxTarget = 0;
		float totalRevenueVLYR = 0;
		float totalRevenueVTGT = 0;
		float totalPaxVLYR = 0;
		float totalPaxVTGT = 0;
		Double totalDistance = 0D;
		float totalAverageFare = 0;
		float totalYeild = 0;
		float totalYeildLastYear = 0;
		float totalYeildVLYR = 0;
		float directchannelsummaryrRevenuePerc = 0;
		float paxperc = 0;
		float totalMarketShare = 0;
		float totalComp1Share = 0;
		float totalComp2Share = 0;
		float totalComp3Share = 0;
		float totalComp4Share = 0;
		float totalComp5Share = 0;

		float completeMarketPax = 0;
		float totalHostPax = 0;
		float totalComp1Pax = 0;
		float totalComp2Pax = 0;
		float totalComp3Pax = 0;
		float totalComp4Pax = 0;
		float totalComp5Pax = 0;
		float totalFlownRevenue = 0;
		String combinationKey = null;
		JSONArray paxlastyrArray = null;
		JSONArray reveneulastyrArray = null;
		double revenuelastyr = 0;
		double paxlastyr = 0;
		try {
			if (lData != null) {

				for (int i = 0; i < lData.length(); i++) {
					JSONObject lJsonObj = lData.getJSONObject(i);

					// getting combination key according to user profile and
					// filter values
					combinationKey = getCombinationKeyDetailScreen(pRequestModel, lJsonObj);

					String region = "-";
					if (lJsonObj.get("region") != null && !"null".equalsIgnoreCase(lJsonObj.get("region").toString()))
						region = lJsonObj.getString("region");

					String country = "-";
					if (lJsonObj.get("country") != null && !"null".equalsIgnoreCase(lJsonObj.get("country").toString()))
						country = lJsonObj.getString("country");

					String pos = "-";
					if (lJsonObj.get("pos") != null && !"null".equalsIgnoreCase(lJsonObj.get("pos").toString()))
						pos = lJsonObj.getString("pos");

					String od = "-";
					if (lJsonObj.get("od") != null && !"null".equalsIgnoreCase(lJsonObj.get("od").toString()))
						od = lJsonObj.getString("od");

					String compartment = "-";
					if (lJsonObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("compartment").toString()))
						compartment = lJsonObj.getString("compartment");

					float paxytd = 0;
					if (lJsonObj.get("pax") != null && !"null".equalsIgnoreCase(lJsonObj.get("pax").toString()))
						paxytd = Float.parseFloat(lJsonObj.get("pax").toString());

					if (lJsonObj.get("pax_1") != null && !"null".equalsIgnoreCase(lJsonObj.get("pax_1").toString())) {

						paxlastyrArray = new JSONArray(lJsonObj.get("pax_1").toString());
						if (paxlastyrArray != null) {
							if (paxlastyrArray.length() > 0) {
								for (int j = 0; j < paxlastyrArray.length(); j++) {
									if (paxlastyrArray != null
											&& !paxlastyrArray.get(j).toString().equalsIgnoreCase("null")
											&& !paxlastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
										paxlastyr = Utility.findSum(paxlastyrArray);

									}
								}
							}
						}
					}

					Double targetpaxYTD = 0D;
					if (lJsonObj.get("target_pax") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("target_pax").toString()))
						targetpaxYTD = Utility.findSum(lJsonObj.get("target_pax"));
					float revenueYTD = 0;
					if (lJsonObj.get("revenue") != null && !"null".equalsIgnoreCase(lJsonObj.get("revenue").toString()))
						revenueYTD = Float.parseFloat(lJsonObj.get("revenue").toString());

					if (lJsonObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("revenue_1").toString())) {
						reveneulastyrArray = new JSONArray(lJsonObj.get("revenue_1").toString());
						if (reveneulastyrArray != null) {
							if (reveneulastyrArray.length() > 0) {
								for (int j = 0; j < reveneulastyrArray.length(); j++) {
									if (reveneulastyrArray != null
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("null")
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
										revenuelastyr = Utility.findSum(reveneulastyrArray);

									}
								}
							}
						}
					}
					Double targetrevenueYTD = 0D;
					if (lJsonObj.get("target_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("target_revenue").toString()))
						targetrevenueYTD = Utility.findSum(lJsonObj.get("target_revenue"));
					Double flownRevenue = 0D;
					if (lJsonObj.get("flown_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("flown_revenue").toString()))
						flownRevenue = Utility.findSum(lJsonObj.get("flown_revenue"));
					double distance = 0;
					if (lJsonObj.get("distance") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("distance").toString()))
						distance = Utility.findSum(lJsonObj.get("distance").toString());

					JSONArray carrierList = new JSONArray();
					if (lJsonObj.get("carrier") != null && !"null".equalsIgnoreCase(lJsonObj.get("carrier").toString())
							&& !"[]".equalsIgnoreCase(lJsonObj.get("carrier").toString())) {
						carrierList = new JSONArray(lJsonObj.get("carrier").toString());
					}

					JSONArray marketPaxList = new JSONArray();
					if (lJsonObj.get("market_share_pax") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("market_share_pax").toString())
							&& !"[]".equalsIgnoreCase(lJsonObj.get("market_share_pax").toString())) {
						marketPaxList = new JSONArray(lJsonObj.get("market_share_pax").toString());

					}

					Double totalMarketPax = Utility.findSum(marketPaxList);
					Map<String, Double> carrierPaxMap = new HashMap<String, Double>();

					for (int k = 0; k < carrierList.length(); k++) {
						carrierPaxMap.put(carrierList.getString(k), marketPaxList.getDouble(k));
					}
					// CalculationUtil.getTopNValuesOfMap(carrierPaxMap, 3);

					// getting host and market values
					String host = "FZ";
					String comp1 = "EK";
					String comp2 = "QR";
					String comp3 = "EY";
					String comp4 = "G9";
					String comp5 = "ET";
					Double hostpax = 0D;
					Double comp1pax = 0D;
					Double comp2pax = 0D;
					Double comp3pax = 0D;
					Double comp4pax = 0D;
					Double comp5pax = 0D;

					if (carrierPaxMap.containsKey(host)) {
						hostpax = carrierPaxMap.get(host);
					}
					if (carrierPaxMap.containsKey(comp1)) {
						comp1pax = carrierPaxMap.get(comp1);
					}
					if (carrierPaxMap.containsKey(comp2)) {
						comp2pax = carrierPaxMap.get(comp2);
					}
					if (carrierPaxMap.containsKey(comp3)) {
						comp3pax = carrierPaxMap.get(comp3);
					}
					if (carrierPaxMap.containsKey(comp4)) {
						comp4pax = carrierPaxMap.get(comp4);
					}
					if (carrierPaxMap.containsKey(comp5)) {

						comp5pax = carrierPaxMap.get(comp5);
					}

					String channel = "-";
					if (lJsonObj.get("channel") != null && !"null".equalsIgnoreCase(lJsonObj.get("channel").toString()))
						channel = lJsonObj.getString("channel");

					String origin = "-";
					String destination = "-";
					if (!od.equals("-")) {
						origin = od.substring(0, 3);
						destination = od.substring(3);
					}

					combinationKey += channel;
					if (!icMap.containsKey(combinationKey)) {
						DirectIndirectChannelSummary dc = new DirectIndirectChannelSummary();
						dc.setRegion(region);
						dc.setCountry(country);
						dc.setPos(pos);
						dc.setOrigin(origin);
						dc.setDestination(destination);
						dc.setCompartment(compartment);
						dc.setChannel(channel);
						dc.setCombinationkey(combinationKey);
						dc.setRevenueytd(revenueYTD);
						dc.setRevenueLastYear((float) revenuelastyr);
						dc.setTargetrevenueytd(targetrevenueYTD);
						dc.setPaxytd(paxytd);
						dc.setPaxlastyear((float) paxlastyr);
						dc.setTargetpaxytd(targetpaxYTD);
						dc.setDistance((float) distance);
						dc.setFlownRevenue(flownRevenue);
						dc.setDistancePax(dc.getPaxytd() * dc.getDistance());
						dc.setTotalMarketPax(totalMarketPax);
						dc.setHostpax(hostpax);
						dc.setComp1pax(comp1pax);
						dc.setComp2pax(comp2pax);
						dc.setComp3pax(comp3pax);
						dc.setComp4pax(comp4pax);
						dc.setComp5pax(comp5pax);
						dc.setTotalMarketPax(totalMarketPax);

						totalRevenue += revenueYTD;
						totalFlownRevenue += flownRevenue;
						totalrevenueTarget += targetrevenueYTD;

						totalpax += paxytd;
						totalpaxTarget += targetpaxYTD;

						totalRevenueLastYear += revenuelastyr;
						totalpaxlastyear += paxlastyr;
						totalDistance += distance;
						completeMarketPax += totalMarketPax;
						totalHostPax += hostpax;
						totalComp1Pax += comp1pax;
						totalComp2Pax += comp2pax;
						totalComp3Pax += comp3pax;
						totalComp4Pax += comp4pax;
						totalComp5Pax += comp5pax;

						icMap.put(combinationKey, dc);

					} else {
						DirectIndirectChannelSummary dc = icMap.get(combinationKey);
						dc.setRevenueytd(dc.getRevenueytd() + revenueYTD);
						dc.setRevenueLastYear((float) (dc.getRevenueLastYear() + revenuelastyr));
						dc.setTargetrevenueytd(dc.getTargetrevenueytd() + targetrevenueYTD);
						dc.setFlownRevenue(dc.getFlownRevenue() + flownRevenue);

						dc.setPaxytd(dc.getPaxytd() + paxytd);
						dc.setPaxlastyear((float) (dc.getPaxlastyear() + paxlastyr));
						dc.setTargetpaxytd(dc.getTargetpaxytd() + targetpaxYTD);
						dc.setDistance((float) (dc.getDistance() + distance));
						dc.setDistancePax(dc.getDistancePax() + dc.getPaxytd() * dc.getDistance());
						dc.setTotalMarketPax(dc.getTotalMarketPax() + totalMarketPax);

						dc.setHostpax(dc.getHostpax() + hostpax);
						dc.setComp1pax(dc.getComp1pax() + comp1pax);
						dc.setComp2pax(dc.getComp2pax() + comp2pax);
						dc.setComp3pax(dc.getComp3pax() + comp3pax);
						dc.setComp4pax(dc.getComp4pax() + comp4pax);
						dc.setComp5pax(dc.getComp5pax() + comp5pax);

						totalRevenue += revenueYTD;
						totalFlownRevenue += flownRevenue;
						totalrevenueTarget += targetrevenueYTD;
						totalRevenueLastYear += revenuelastyr;
						totalpaxTarget += targetpaxYTD;

						totalpax += paxytd;
						totalpaxlastyear += paxlastyr;
						totalDistance += distance;

						completeMarketPax += totalMarketPax;
						totalHostPax += hostpax;
						totalComp1Pax += comp1pax;
						totalComp2Pax += comp2pax;
						totalComp3Pax += comp3pax;
						totalComp4Pax += comp4pax;
						totalComp5Pax += comp5pax;
					}

				}
				for (String key : icMap.keySet()) {
					DirectIndirectChannelSummary dc = icMap.get(key);

					float revenueVLYR = 0;
					float revenueVTGT = 0;
					float paxVLYR = 0;
					float paxVTGT = 0;
					float yeild = 0;
					float yeildLastYear = 0;
					float yeildVLYR = 0;
					float averageFare = 0;
					Double hostMarketShare = 0D;
					Double comp1MarketShare = 0D;
					Double comp2MarketShare = 0D;
					Double comp3MarketShare = 0D;
					Double comp4MarketShare = 0D;
					Double comp5MarketShare = 0D;

					if (totalRevenue != 0) {
						directchannelsummaryrRevenuePerc = (dc.getRevenueytd() / totalRevenue) * 100;
					}

					if (dc.getRevenueLastYear() != 0) {
						revenueVLYR = CalculationUtil.calculateVLYR(dc.getRevenueytd(), dc.getRevenueLastYear());
					}
					if (dc.getTargetrevenueytd() != 0) {
						revenueVTGT = CalculationUtil.calculateVTGT(dc.getRevenueytd(),
								(float) dc.getTargetrevenueytd());
					}

					if (totalpax != 0) {
						paxperc = (dc.getPaxytd() / totalpax) * 100;
					}
					if (dc.getPaxlastyear() != 0 || dc.getPaxlastyear() > 0) {
						paxVLYR = CalculationUtil.calculateVLYR(dc.getPaxytd(), dc.getPaxlastyear());
					} else {
						paxVLYR = 0;
					}
					if (dc.getTargetpaxytd() != 0) {
						paxVTGT = CalculationUtil.calculateVTGT(dc.getPaxytd(), (float) dc.getTargetpaxytd());
					}
					if (dc.getDistancePax() != 0) {
						yeild = CalculationUtil.calculateYield(dc.getRevenueytd(), dc.getPaxytd(), dc.getDistance());
					}
					if (dc.getDistance() * dc.getPaxlastyear() != 0) {
						yeildLastYear = CalculationUtil.calculateYield(dc.getRevenueLastYear(), dc.getPaxlastyear(),
								dc.getDistance());
					}
					if (yeildLastYear != 0) {
						yeildVLYR = CalculationUtil.calculateVLYR(yeild, yeildLastYear);
					}
					if (dc.getPaxytd() != 0) {
						averageFare = dc.getRevenueytd() / dc.getPaxytd();

					}
					if (dc.getTotalMarketPax() != 0) {
						hostMarketShare = ((dc.getHostpax() * 100) / dc.getTotalMarketPax());
					}
					if (dc.getTotalMarketPax() != 0) {
						comp1MarketShare = ((dc.getComp1pax() * 100) / dc.getTotalMarketPax());
					}
					if (dc.getTotalMarketPax() != 0) {
						comp2MarketShare = ((dc.getComp2pax() * 100) / dc.getTotalMarketPax());
					}
					if (dc.getTotalMarketPax() != 0) {
						comp3MarketShare = ((dc.getComp3pax() * 100) / dc.getTotalMarketPax());
					}
					if (dc.getTotalMarketPax() != 0) {
						comp4MarketShare = ((dc.getComp4pax() * 100) / dc.getTotalMarketPax());
					}
					if (dc.getTotalMarketPax() != 0) {
						comp5MarketShare = ((dc.getComp5pax() * 100) / dc.getTotalMarketPax());
					}
					ArrayList<Double> marketsharepax = new ArrayList<Double>();
					marketsharepax.add(hostMarketShare);
					marketsharepax.add(comp1MarketShare);
					marketsharepax.add(comp2MarketShare);
					marketsharepax.add(comp3MarketShare);
					marketsharepax.add(comp4MarketShare);
					marketsharepax.add(comp5MarketShare);

					Map<String, Double> carrierPaxMap = new HashMap<String, Double>();
					carrierPaxMap.put("FZ", hostMarketShare);
					carrierPaxMap.put("EK", comp1MarketShare);
					carrierPaxMap.put("QR", comp2MarketShare);
					carrierPaxMap.put("EY", comp3MarketShare);
					carrierPaxMap.put("G9", comp4MarketShare);
					carrierPaxMap.put("ET", comp5MarketShare);
					dc.setCarrierPaxMap(carrierPaxMap);
					totalYeild += yeild;
					totalYeildLastYear += yeildLastYear;

					dc.setFlownRevenue((dc.getFlownRevenue()));
					dc.setRevenueytd(dc.getRevenueytd());
					dc.setRevenuevlyr((revenueVLYR));
					dc.setRevenuevtgt(revenueVTGT);
					dc.setPaxvlyr((paxVLYR));
					dc.setRevenueshareperc((directchannelsummaryrRevenuePerc));
					dc.setPaxvtgt(paxVTGT);
					dc.setYieldytd((yeild));
					dc.setYiedvlyr((yeildVLYR));
					dc.setAverageFare((averageFare));
					dc.setPaxperc((paxperc));
					dc.setHostMarketShare((hostMarketShare));
					dc.setComp1MarketShare((comp1MarketShare));
					dc.setComp2MarketShare((comp2MarketShare));
					dc.setComp3MarketShare((comp3MarketShare));
					dc.setComp4MarketShare(comp4MarketShare);
					dc.setComp5MarketShare(comp5MarketShare);

					// rounding all the values
					dc.setRevenueytd((dc.getRevenueytd()));

				}

				IndirectChannelTotalResponse tm = new IndirectChannelTotalResponse();
				tm.setTotalRevenueYTD(totalRevenue);
				tm.setTotalFlownYTD(totalFlownRevenue);
				tm.setTotalPaxYTD(totalpax);
				if (totalRevenueLastYear != 0)
					totalRevenueVLYR = CalculationUtil.calculateVLYR(totalRevenue, totalRevenueLastYear);
				if (totalrevenueTarget != 0)
					totalRevenueVTGT = CalculationUtil.calculateVTGT(totalRevenue, totalrevenueTarget);
				if (totalpaxLastyear != 0 || totalpaxLastyear > 0)
					totalPaxVLYR = CalculationUtil.calculateVLYR(totalpax, totalpaxLastyear);
				if (totalpaxTarget != 0)
					totalPaxVTGT = CalculationUtil.calculateVTGT(totalpax, totalpaxTarget);
				if (totalpax != 0) {
					totalAverageFare = totalRevenue / totalpax;
				}
				if (totalYeildLastYear != 0)
					totalYeildVLYR = CalculationUtil.calculateVLYR(totalYeild, totalYeildLastYear);
				if (completeMarketPax != 0)
					totalMarketShare = ((totalHostPax * 100) / completeMarketPax);
				if (completeMarketPax != 0)
					totalComp1Share = ((totalComp1Pax * 100) / completeMarketPax);
				if (completeMarketPax != 0)
					totalComp2Share = ((totalComp2Pax * 100) / completeMarketPax);
				if (completeMarketPax != 0)
					totalComp3Share = ((totalComp3Pax * 100) / completeMarketPax);
				if (completeMarketPax != 0)
					totalComp4Share = ((totalComp4Pax * 100) / completeMarketPax);
				if (completeMarketPax != 0)
					totalComp5Share = ((totalComp5Pax * 100) / completeMarketPax);

				Map<String, Float> totalcarrierPaxMap = new HashMap<String, Float>();
				totalcarrierPaxMap.put("FZ", totalMarketShare);
				totalcarrierPaxMap.put("EK", totalComp1Share);
				totalcarrierPaxMap.put("QR", totalComp2Share);
				totalcarrierPaxMap.put("EY", totalComp3Share);
				totalcarrierPaxMap.put("G9", totalComp4Share);
				totalcarrierPaxMap.put("ET", totalComp5Share);

				tm.setTotalcarrierPaxMap(totalcarrierPaxMap);
				tm.setTotalMarketShareYTD((totalMarketShare));
				tm.setTotalComp1ShareYTD((totalComp1Share));
				tm.setTotalComp2ShareYTD((totalComp2Share));
				tm.setTotalComp3ShareYTD((totalComp3Share));
				tm.setTotalComp4ShareYTD((totalComp4Share));
				tm.setTotalComp5ShareYTD((totalComp5Share));

				tm.setTotalYeild(CalculationUtil.roundAFloat(totalYeild, 1));
				tm.setTotalYeildVLYR(CalculationUtil.roundAFloat(totalYeildVLYR, 1));
				tm.setTotalAverageFare((totalAverageFare));
				tm.setTotalRevenueVLYR((totalRevenueVLYR));
				tm.setTotalRevenueVTGT((totalRevenueVTGT));
				tm.setTotalPaxVLYR((totalPaxVLYR));
				tm.setTotalPaxVTGT((totalPaxVTGT));

				List<DirectIndirectChannelSummary> dcList = new ArrayList<DirectIndirectChannelSummary>(icMap.values());
				List<IndirectChannelTotalResponse> tmList = new ArrayList<IndirectChannelTotalResponse>();
				tmList.add(tm);
				inDirectChannelSummaryMap.put("inDirectchannelSummaryMap", dcList);
				inDirectChannelSummaryMap.put("inDirectchannelSummaryTotalsMap", tmList);

			}
		} catch (Exception e) {
			logger.error("getChannelSummaryIndirect-Exception", e);
		}

		return inDirectChannelSummaryMap;

	}

	@Override
	public Map<String, Object> getCustomerSegment(RequestModel pRequestModel) {
		ArrayList<DBObject> channeldata = mCustSegDashboardDao.getCustomerSegment(pRequestModel);
		JSONArray lData = new JSONArray(channeldata);

		Map<String, Object> customerSegmentMap = new HashMap<String, Object>();
		Map<String, CustomerSegment> csMap = new HashMap<String, CustomerSegment>();

		Double totalRevenue = 0D;
		Double totalTarget = 0D;
		Double totalFlownRevenueYTD = 0D;
		Double totalRevenueVLYR = 0D;
		Double totalRevenueVTGT = 0D;
		Double totalDedicatedFare = 0D;
		Double totalRevenueLastYear = 0D;
		Double totalPax = 0D;
		JSONArray reveneulastyrArray = null;
		double revenuelastyr = 0;
		JSONArray flownrevenueArray = null;
		double flownrevenue = 0;
		try {
			if (lData != null) {

				for (int i = 0; i < lData.length(); i++) {
					JSONObject lJsonObj = lData.getJSONObject(i);

					String combinationKey = getCombinationKeyDashboardScreen(pRequestModel, lJsonObj);

					String region = "-";
					if (lJsonObj.get("region") != null && !"null".equalsIgnoreCase(lJsonObj.get("region").toString()))
						region = lJsonObj.getString("region");
					String country = "-";
					if (lJsonObj.get("country") != null && !"null".equalsIgnoreCase(lJsonObj.get("country").toString()))
						country = lJsonObj.getString("country");
					String pos = "-";
					if (lJsonObj.get("pos") != null && !"null".equalsIgnoreCase(lJsonObj.get("pos").toString()))
						pos = lJsonObj.getString("pos");
					String compartment = "-";
					if (lJsonObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("compartment").toString()))
						compartment = lJsonObj.getString("compartment");
					String segment = "-";
					if (lJsonObj.has("segment"))
						segment = lJsonObj.getString("segment");
					Double revenue = 0D;
					if (lJsonObj.get("revenue") != null && !"null".equalsIgnoreCase(lJsonObj.get("revenue").toString()))
						revenue = lJsonObj.getDouble("revenue");

					if (lJsonObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("revenue_1").toString())) {
						reveneulastyrArray = new JSONArray(lJsonObj.get("revenue_1").toString());
						if (reveneulastyrArray != null) {
							if (reveneulastyrArray.length() > 0) {
								for (int j = 0; j < reveneulastyrArray.length(); j++) {
									if (reveneulastyrArray != null
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("null")
											&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
										revenuelastyr = Utility.findSum(reveneulastyrArray);

									}
								}
							}
						}
					}
					Double target = 0D;
					if (lJsonObj.get("target_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("target_revenue").toString()))
						target = Utility.findSum(lJsonObj.get("target_revenue"));

					if (lJsonObj.get("flown_revenue") != null
							&& !"null".equalsIgnoreCase(lJsonObj.get("flown_revenue").toString())) {
						flownrevenueArray = new JSONArray(lJsonObj.get("revenue_1").toString());
						if (flownrevenueArray != null) {
							if (flownrevenueArray.length() > 0) {
								for (int j = 0; j < flownrevenueArray.length(); j++) {
									if (flownrevenueArray != null
											&& !flownrevenueArray.get(j).toString().equalsIgnoreCase("null")
											&& !flownrevenueArray.get(j).toString().equalsIgnoreCase("[]")) {
										flownrevenue = Utility.findSum(flownrevenueArray);

									}
								}
							}
						}
					}

					combinationKey += segment;

					if (!csMap.containsKey(combinationKey)) {
						CustomerSegment cs = new CustomerSegment();
						cs.setRegion(region);
						cs.setCountry(country);
						cs.setPos(pos);
						cs.setSegment(segment);
						cs.setCombinationKey(combinationKey);
						cs.setRevenueYTD(revenue);
						cs.setRevenueLastYear(revenuelastyr);
						cs.setTargetYTD(target);
						cs.setFlownYTD((double) flownrevenue);
						cs.setCompartment(compartment);

						totalRevenue += revenue;
						totalTarget += target;
						totalFlownRevenueYTD += flownrevenue;
						totalRevenueLastYear += revenuelastyr;
						csMap.put(combinationKey, cs);

					} else {
						CustomerSegment cs = csMap.get(combinationKey);
						cs.setRevenueYTD(cs.getRevenueYTD() + revenue);
						cs.setRevenueLastYear(cs.getRevenueLastYear() + revenuelastyr);
						cs.setTargetYTD(cs.getTargetYTD() + target);
						cs.setFlownYTD(cs.getFlownYTD() + flownrevenue);

						totalRevenue += revenue;
						totalTarget += target;
						totalFlownRevenueYTD += flownrevenue;
						totalRevenueLastYear += revenuelastyr;
					}

				}

				for (String key : csMap.keySet()) {
					CustomerSegment cs = csMap.get(key);
					Double channelRevenuePerc = 0D;

					Double channelTargetPerc = 0D;
					Double channelVariancePerc = 0D;

					Double revenueVLYR = 0D;
					Double revenueVTGT = 0D;
					Double dedicatedFarePerc = 0D;
					if (totalRevenue != 0) {
						channelRevenuePerc = (cs.getRevenueYTD() / totalRevenue) * 100;
					}

					// commenting for now once prity give me channel wise target
					// it will be updated as said by prity,arvind and anu.
					if (totalTarget != 0) {
						channelTargetPerc = (cs.getTargetYTD() / totalTarget) * 100;
					}
					if (channelTargetPerc != 0) {
						channelVariancePerc = ((channelRevenuePerc - channelTargetPerc));
					}
					if (cs.getRevenueLastYear() != 0) {
						revenueVLYR = ((cs.getRevenueYTD() - cs.getRevenueLastYear()) / cs.getRevenueLastYear()) * 100;
					}
					if (cs.getTargetYTD() != 0) {
						revenueVTGT = ((cs.getRevenueYTD() - cs.getTargetYTD()) / cs.getTargetYTD()) * 100;
					}

					cs.setChannelRevenuePerc(channelRevenuePerc);
					cs.setChannelTargetPerc(channelTargetPerc);
					cs.setChannelVariancePerc(channelVariancePerc);
					cs.setRevenueVLYR(revenueVLYR);
					cs.setRevenueVTGT(revenueVTGT);

				}
				CustomerSegmentTotals tm = new CustomerSegmentTotals();
				tm.setTotalRevenueYTD(totalRevenue);
				tm.setTotalFlownRevenueYTD(totalFlownRevenueYTD);
				if (totalRevenueLastYear != 0)
					totalRevenueVLYR = ((totalRevenue - totalRevenueLastYear) / totalRevenueLastYear) * 100;
				if (totalTarget != 0)
					totalRevenueVTGT = ((totalRevenue - totalTarget) / totalTarget) * 100;
				tm.setTotalRevenueVLYR(totalRevenueVLYR);
				tm.setTotalRevenueVTGT(totalRevenueVTGT);

				List<CustomerSegment> csList = new ArrayList<CustomerSegment>(csMap.values());
				List<CustomerSegmentTotals> tmList = new ArrayList<CustomerSegmentTotals>();
				tmList.add(tm);
				customerSegmentMap.put("customerSegmentMap", csList);
				customerSegmentMap.put("customerSegmentTotalsMap", tmList);

			}
		} catch (Exception e) {
			logger.error("getCustomerSegment-Exception", e);
		}

		return customerSegmentMap;
	}

	@Override
	public Map<String, Object> getCustomerSegmentDetails(RequestModel pRequestModel) {
		ArrayList<DBObject> customersegmentdetails = mCustSegDashboardDao.getCustomerSegmentDetails(pRequestModel);
		JSONArray lData = new JSONArray(customersegmentdetails);

		Map<String, Object> customerSegmentDetailsMap = new HashMap<String, Object>();
		Map<String, CustomerSegmentDetails> csdMap = new HashMap<String, CustomerSegmentDetails>();

		Double totalRevenue = 0D;
		Double totalRevenueLastYear = 0D;
		Double totalPax = 0D;
		Double totalPaxLastYear = 0D;
		Double totalRevenueFlown = 0D;
		Double totalRevenueVLYR = 0D;
		Double totalpaxVLYR = 0D;
		Double totalAverageFare = 0D;
		Double totalPaxDistance = 0D;
		Double totalPaxDistanceLastYear = 0D;
		Double totalYeild = 0D;
		Double totalYeildLastYear = 0D;
		Double totalYeildVLYR = 0D;
		double revenuelastyr = 0;
		JSONArray flownrevenueArray = null;
		JSONArray reveneulastyrArray = null;
		JSONArray paxlastyrArray = null;
		double flownrevenue = 0;
		double paxlastyr = 0;
		for (int i = 0; i < lData.length(); i++) {
			JSONObject lJsonObj = lData.getJSONObject(i);

			String combinationKey = getCombinationKeyDetailScreen(pRequestModel, lJsonObj);
			String region = "-";
			if (lJsonObj.get("region") != null && !"null".equalsIgnoreCase(lJsonObj.get("region").toString()))
				region = lJsonObj.getString("region");
			String country = "-";
			if (lJsonObj.get("country") != null && !"null".equalsIgnoreCase(lJsonObj.get("country").toString()))
				country = lJsonObj.getString("country");
			String pos = "-";
			if (lJsonObj.get("pos") != null && !"null".equalsIgnoreCase(lJsonObj.get("pos").toString()))
				pos = lJsonObj.getString("pos");
			String origin = "-";
			if (lJsonObj.get("origin") != null && !"null".equalsIgnoreCase(lJsonObj.get("origin").toString()))
				origin = lJsonObj.getString("origin");
			String destination = "-";
			if (lJsonObj.get("destination") != null && !"null".equalsIgnoreCase(lJsonObj.get("destination").toString()))
				destination = lJsonObj.getString("destination");
			String compartment = "-";
			if (lJsonObj.get("compartment") != null && !"null".equalsIgnoreCase(lJsonObj.get("compartment").toString()))
				compartment = lJsonObj.getString("compartment");
			String segment = "-";
			if (lJsonObj.has("segment"))
				segment = lJsonObj.getString("segment");
			Double revenue = 0D;
			if (lJsonObj.get("revenue") != null && !"null".equalsIgnoreCase(lJsonObj.get("revenue").toString()))
				revenue = lJsonObj.getDouble("revenue");

			if (lJsonObj.get("revenue_1") != null && !"null".equalsIgnoreCase(lJsonObj.get("revenue_1").toString())) {
				reveneulastyrArray = new JSONArray(lJsonObj.get("revenue_1").toString());
				if (reveneulastyrArray != null) {
					if (reveneulastyrArray.length() > 0) {
						for (int j = 0; j < reveneulastyrArray.length(); j++) {
							if (reveneulastyrArray != null
									&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("null")
									&& !reveneulastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
								revenuelastyr = Utility.findSum(reveneulastyrArray);

							}
						}
					}
				}
			}

			Double distance = 0D;
			if (lJsonObj.get("distance") != null && !"null".equalsIgnoreCase(lJsonObj.get("distance").toString()))
				distance = Utility.findSum(lJsonObj.get("distance"));

			if (lJsonObj.get("flown_revenue") != null
					&& !"null".equalsIgnoreCase(lJsonObj.get("flown_revenue").toString())) {
				flownrevenueArray = new JSONArray(lJsonObj.get("flown_revenue").toString());
				if (flownrevenueArray != null) {
					if (flownrevenueArray.length() > 0) {
						for (int j = 0; j < flownrevenueArray.length(); j++) {
							if (flownrevenueArray != null
									&& !flownrevenueArray.get(j).toString().equalsIgnoreCase("null")
									&& !flownrevenueArray.get(j).toString().equalsIgnoreCase("[]")) {
								flownrevenue = Utility.findSum(flownrevenueArray);

							}
						}
					}
				}
			}

			Double pax = 0D;
			if (lJsonObj.get("pax") != null && !"null".equalsIgnoreCase(lJsonObj.get("pax").toString()))
				pax = lJsonObj.getDouble("pax");

			if (lJsonObj.get("pax_1") != null && !"null".equalsIgnoreCase(lJsonObj.get("pax_1").toString())) {
				paxlastyrArray = new JSONArray(lJsonObj.get("pax_1").toString());
				if (paxlastyrArray != null) {
					if (paxlastyrArray.length() > 0) {
						for (int j = 0; j < paxlastyrArray.length(); j++) {
							if (paxlastyrArray != null && !paxlastyrArray.get(j).toString().equalsIgnoreCase("null")
									&& !paxlastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
								paxlastyr = Utility.findSum(paxlastyrArray);

							}
						}
					}
				}
			}

			combinationKey += segment;
			if (!csdMap.containsKey(combinationKey)) {
				CustomerSegmentDetails csd = new CustomerSegmentDetails();
				csd.setRegion(region);
				csd.setCountry(country);
				csd.setOrigin(origin);
				csd.setPos(pos);
				csd.setDestination(destination);
				csd.setCompartment(compartment);
				csd.setSegment(segment);
				csd.setRevenue(revenue);
				csd.setRevenueLastYear(revenuelastyr);
				csd.setPax(pax);
				csd.setPaxLastYear(paxlastyr);
				csd.setRevenueFlown(flownrevenue);
				csd.setPaxDistance(pax * distance);
				csd.setPaxDistanceLastYear(paxlastyr * distance);
				csdMap.put(combinationKey, csd);

				totalRevenue += revenue;
				totalRevenueLastYear += revenuelastyr;
				totalPax += pax;
				totalPaxLastYear += paxlastyr;
				totalRevenueFlown += flownrevenue;
				totalPaxDistance += pax * distance;
				totalPaxDistanceLastYear = paxlastyr * distance;
			} else {
				CustomerSegmentDetails csd = csdMap.get(combinationKey);
				csd.setRevenue(csd.getRevenue() + revenue);
				csd.setRevenueLastYear(csd.getRevenueLastYear() + revenuelastyr);
				csd.setPax(csd.getPax() + pax);
				csd.setPaxLastYear(csd.getPaxLastYear() + paxlastyr);
				csd.setRevenueFlown(csd.getRevenueFlown() + flownrevenue);
				csd.setPaxDistance(csd.getPaxDistance() + pax * distance);
				csd.setPaxDistanceLastYear(csd.getPaxDistanceLastYear() + paxlastyr * distance);
				totalRevenue += revenue;
				totalRevenueLastYear += revenuelastyr;
				totalPax += pax;
				totalPaxLastYear += paxlastyr;
				totalRevenueFlown += flownrevenue;
				totalPaxDistance += pax * distance;
				totalPaxDistanceLastYear = paxlastyr * distance;
			}

			for (String key : csdMap.keySet()) {
				CustomerSegmentDetails csd = csdMap.get(key);
				Double revenueVLYR = 0D;
				Double paxVLYR = 0D;
				Double averageFare = 0D;
				Double yeild = 0D;
				Double yeildLastYear = 0D;
				Double yeildVLYR = 0D;
				Double revenuePerc = 0D;
				Double paxPerc = 0D;
				averageFare = csd.getRevenue() / csd.getPax();
				if (csd.getRevenueLastYear() != 0) {
					revenueVLYR = ((csd.getRevenue() - csd.getRevenueLastYear()) / csd.getRevenueLastYear()) * 100;
				}
				if (csd.getPaxLastYear() != 0) {
					paxVLYR = ((csd.getPax() - csd.getPaxLastYear()) / csd.getPaxLastYear()) * 100;
				}
				if (csd.getPaxDistance() != 0) {
					yeild = csd.getRevenue() / (csd.getPaxDistance());
				}
				if (csd.getPaxDistanceLastYear() != 0) {
					yeildLastYear = csd.getRevenueLastYear() / csd.getPaxDistanceLastYear();
				}
				if (yeildLastYear != 0) {
					yeildVLYR = ((yeild - yeildLastYear) / yeildLastYear) * 100;
				}
				if (totalRevenue != 0) {
					revenuePerc = (csd.getRevenue() / totalRevenue) * 100;
				}
				if (totalPax != 0) {
					paxPerc = (csd.getPax() / totalPax) * 100;
				}
				csd.setAverageFare(averageFare);
				csd.setRevenueVLYR(revenueVLYR);
				csd.setPaxVLYR(paxVLYR);
				csd.setYeild(yeild);
				csd.setYeildLastYear(yeildLastYear);
				csd.setYeildVLYR(yeildVLYR);
				csd.setRevenueSharePerc(revenuePerc);
				csd.setPaxPerc(paxPerc);
			}

		}
		List<CustomerSegmentDetails> csdList = new ArrayList<CustomerSegmentDetails>(csdMap.values());
		List<CustomerSegmentDetailsTotals> tmList = new ArrayList<CustomerSegmentDetailsTotals>();
		CustomerSegmentDetailsTotals csdt = new CustomerSegmentDetailsTotals();
		if (totalRevenueLastYear != 0)
			totalRevenueVLYR = ((totalRevenue - totalRevenueLastYear) / totalRevenueLastYear) * 100;
		if (totalPaxLastYear != 0)
			totalpaxVLYR = ((totalPax - totalPaxLastYear) / totalPaxLastYear) * 100;
		if (totalPaxDistance != 0)
			totalYeild = totalRevenue / totalPaxDistance;
		if (totalPaxDistanceLastYear != 0)
			totalYeildLastYear = totalRevenueLastYear / totalPaxDistanceLastYear;
		if (totalYeildLastYear != 0)
			totalYeildVLYR = ((totalYeild - totalYeildLastYear) / totalYeildLastYear) * 100;
		if (totalPax != 0)
			totalAverageFare = totalRevenue / totalPax;
		csdt.setTotalRevenue(totalRevenue);
		csdt.setTotalPax(totalPax);
		csdt.setTotalRevenueFlown(totalRevenueFlown);
		csdt.setTotalRevenueVLYR(totalRevenueVLYR);
		csdt.setTotalpaxVLYR(totalpaxVLYR);
		csdt.setTotalYeild(totalYeild);
		csdt.setTotalYeildVLYR(totalYeildVLYR);
		csdt.setTotalAverageFare(totalAverageFare);
		tmList.add(csdt);
		customerSegmentDetailsMap.put("customerSegmentDetailsMap", csdList);
		customerSegmentDetailsMap.put("customerSegmentDetailsTotalsMap", tmList);
		return customerSegmentDetailsMap;
	}

	@Override
	public BasicDBObject getPriceEffectiveness(RequestModel requestModel) {

		BasicDBObject getpriceeffectivenessResponse = mCustSegDashboardDao.getPriceEffectiveness(requestModel);
		return getpriceeffectivenessResponse;
	}

	// TODO create a common method for creating combination keys rather than
	// having multiple methods for different grids
	private String getCombinationKeyDetailScreen(RequestModel pRequestModel, JSONObject lJsonObj) {
		// TODO Auto-generated method stub
		String key = lJsonObj.getString("compartment");
		boolean flag = false;
		if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
				&& pRequestModel.getPosArray() == null && pRequestModel.getOdArray() == null)
			flag = true;
		if (flag) {
			if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
					&& pRequestModel.getUser().equals("Global Head")) {
				key += lJsonObj.getString("region");
			} else if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
					&& pRequestModel.getUser().equals("Region Head")) {
				key += lJsonObj.getString("region");
			} else if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
					&& pRequestModel.getUser().equals("Country Head")) {
				key += lJsonObj.getString("country");
			} else {
				key += lJsonObj.getString("pos");
			}
		} else {
			if (pRequestModel.getRegionArray() != null) {
				key = key + lJsonObj.getString("region") + lJsonObj.getString("country");
			}
			if (pRequestModel.getCountryArray() != null) {
				key = key + lJsonObj.getString("country") + lJsonObj.getString("pos");
			}
			if (pRequestModel.getPosArray() != null) {
				key = key + lJsonObj.getString("pos");
			}
			if (pRequestModel.getOdArray() != null) {
				key = key + lJsonObj.getString("od");
			}
		}
		return key;

	}

	private String getCombinationKeyDashboardScreen(RequestModel pRequestModel, JSONObject lJsonObj) {
		String key = lJsonObj.getString("compartment");
		boolean flag = false;
		if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
				&& pRequestModel.getPosArray() == null)
			flag = true;
		if (flag) {
			if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
					&& pRequestModel.getUser().equals("Global Head")) {
				key += lJsonObj.getString("region");
			} else if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
					&& pRequestModel.getUser().equals("Region Head")) {
				key += lJsonObj.getString("region");
			} else if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
					&& pRequestModel.getUser().equals("Country Head")) {
				key += lJsonObj.getString("country");
			} else {
				key += lJsonObj.getString("pos");
			}
		} else {
			if (pRequestModel.getRegionArray() != null) {
				key = key + lJsonObj.getString("region") + lJsonObj.getString("country");
			}
			if (pRequestModel.getCountryArray() != null) {
				key = key + lJsonObj.getString("country") + lJsonObj.getString("pos");
			}
			if (pRequestModel.getPosArray() != null) {
				key = key + lJsonObj.getString("pos");
			}
		}

		return key;

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
