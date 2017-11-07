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

import com.flynava.jupiter.daoInterface.ExchangeRateDao;
import com.flynava.jupiter.model.CurrencyTable;
import com.flynava.jupiter.model.CurrencyTotalResponse;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.YQTable;
import com.flynava.jupiter.model.YqTotalResponse;
import com.flynava.jupiter.serviceInterface.ExchangeRateService;
import com.flynava.jupiter.util.Utility;
import com.mongodb.DBObject;

/**
 * @author Surya This Class ExchangeRateServiceImpl contains all the Service
 *         level functions required in Exchange Rate Dashboard
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
	@Autowired
	ExchangeRateDao mExchangeRateDao;

	private static final Logger logger = Logger.getLogger(ExchangeRateServiceImpl.class);

	@Override
	public Map<String, Object> getCurrencyTable(RequestModel pRequestModel) {
		ArrayList<DBObject> currencyTable = mExchangeRateDao.getCurrencyTable(pRequestModel);
		JSONArray currencyTableData = new JSONArray(currencyTable);
		System.out.println("size" + currencyTable.size());

		Map<String, Object> currencyMap = new HashMap<String, Object>();
		List<CurrencyTable> cList = new ArrayList<CurrencyTable>();
		List<CurrencyTotalResponse> totalsList = new ArrayList<CurrencyTotalResponse>();

		Double totalRevenueYTD = 0D;
		Double totalRevenueBase = 0D;
		Double totalAvgRateRealization = 0D;
		try {
			if (currencyTableData != null) {

				for (int i = 0; i < currencyTableData.length(); i++) {

					JSONObject lJsonObj = currencyTableData.getJSONObject(i);

					String pos = "-";
					if (checkIfValueExist("pos", lJsonObj)) {
						pos = lJsonObj.get("pos").toString();
					}

					String od = "-";
					if (checkIfValueExist("od", lJsonObj)) {
						od = lJsonObj.get("od").toString();
					}

					String origin = "-";
					String destination = "-";
					if (!"-".equals(od)) {
						origin = od.substring(0, 3);
						destination = od.substring(3);
					}

					String compartment = "-";
					if (checkIfValueExist("compartment", lJsonObj)) {
						compartment = lJsonObj.get("compartment").toString();
					}

					String lCurrency = "-";
					if (checkIfValueExist("currency", lJsonObj)) {
						lCurrency = lJsonObj.get("currency").toString();
					}

					Double revenue = 0D;
					if (checkIfValueExist("revenue", lJsonObj)) {
						revenue = lJsonObj.getDouble("revenue");
					}

					Double fare = 0D;
					if (checkIfValueExist("fare", lJsonObj)) {
						fare = lJsonObj.getDouble("fare");
					}

					Double revenuebase = 0D;
					if (checkIfValueExist("revenue_base", lJsonObj)) {
						revenuebase = lJsonObj.getDouble("revenue_base");
					}
					String fareBasis = "-";
					if (checkIfValueExist("fare_basis", lJsonObj)) {
						fareBasis = lJsonObj.get("fare_basis").toString();
					}

					String lYQ = "_";
					JSONArray lYQArray = null;
					if (checkIfValueExist("YQ", lJsonObj)) {
						if (lJsonObj.get("YQ") != "null" && lJsonObj.get("YQ") != "[]") {
							lYQArray = new JSONArray(lJsonObj.get("YQ").toString());
							if (lYQArray.length() > 0) {
								lYQ = Double.toString(Utility.findSum(lYQArray));
							} else {
								lYQ = "NA";
							}
						}
					}

					/*
					 * Double lYQlastyr = 0D; if(checkIfValueExist("YQ_1",
					 * lJsonObj)){ lYQlastyr = lJsonObj.getDouble("YQ_1"); }
					 */
					String footnote = "_";
					JSONArray footnoteArray = null;
					if (checkIfValueExist("footnote", lJsonObj)) {
						if (lJsonObj.get("footnote") != "null" && lJsonObj.get("footnote") != "[]") {
							footnoteArray = new JSONArray(lJsonObj.get("footnote").toString());
							if (footnoteArray.length() > 0) {
								footnote = Double.toString(Utility.findSum(footnoteArray));
							} else {
								footnote = "NA";
							}
						}
					}
					String ruleId = "_";
					JSONArray ruleIdArray = null;
					if (checkIfValueExist("Rule_id", lJsonObj)) {
						if (lJsonObj.get("footnote") != "null" && lJsonObj.get("footnote") != "[]") {
							ruleIdArray = new JSONArray(lJsonObj.get("footnote").toString());
							if (ruleIdArray.length() > 0) {
								ruleId = Double.toString(Utility.findSum(ruleIdArray));
							} else {
								ruleId = "NA";
							}
						}
					}

					Double surcharge = 0D;
					if (checkIfValueExist("SURCHARGE_1_1", lJsonObj)) {
						surcharge = lJsonObj.getDouble("SURCHARGE_1_1");
					}
					Double basefare = 0D;
					if (checkIfValueExist("baseFare", lJsonObj)) {
						basefare = lJsonObj.getDouble("baseFare");
					}
					Double tax = 0D;
					if (checkIfValueExist("TAX_CHARGE", lJsonObj)) {
						tax = lJsonObj.getDouble("TAX_CHARGE");
					}

					CurrencyTable currency = new CurrencyTable();

					currency.setPos(pos);
					currency.setOrigin(origin);
					currency.setDestination(destination);
					currency.setCompartment(compartment);
					currency.setCurrency(lCurrency);
					currency.setFare(fare);
					currency.setRevenue(revenue);
					currency.setRevenueBase(revenuebase);
					currency.setFareBasis(fareBasis);
					currency.setBasefare(basefare);
					currency.setTax(tax);
					currency.setSurcharge(surcharge);
					currency.setYQ(lYQ);
					currency.setRuleId(ruleId);
					currency.setFootnote(footnote);
					Double avgRateRealization = 0D;
					if (revenue != 0)
						avgRateRealization = revenuebase / revenue;
					currency.setAvgRateRealization(avgRateRealization);

					cList.add(currency);
					totalRevenueYTD += revenue;
					totalRevenueBase += revenuebase;

				}

				CurrencyTotalResponse tm = new CurrencyTotalResponse();
				tm.setTotalrevenueYTD(totalRevenueYTD);
				if (totalRevenueYTD != 0)
					totalAvgRateRealization = totalRevenueBase / totalRevenueYTD;
				tm.setTotalAvgRateRealization(totalAvgRateRealization);
				totalsList.add(tm);
				currencyMap.put("currencyTotals", totalsList);
				currencyMap.put("currencyMap", cList);

			}

		} catch (Exception e) {
			logger.error("getCurrencyTable-Exception", e);
		}
		return currencyMap;
	}

	@Override
	public Map<String, Object> getYQTable(RequestModel pRequestModel) {
		ArrayList<DBObject> currencyTable = mExchangeRateDao.getYQTable(pRequestModel);
		JSONArray yqTableData = new JSONArray(currencyTable);
		System.out.println("size" + currencyTable.size());

		Map<String, Object> yqMap = new HashMap<String, Object>();
		List<YQTable> yqList = new ArrayList<YQTable>();
		List<YqTotalResponse> totalsList = new ArrayList<YqTotalResponse>();

		Double totalYQ = 0D;
		Double totalYQlastyr = 0D;
		Double totalYQVLYR = 0D;

		try {
			if (yqTableData != null) {

				for (int i = 0; i < yqTableData.length(); i++) {

					JSONObject lJsonObj = yqTableData.getJSONObject(i);

					String pos = "-";
					if (checkIfValueExist("pos", lJsonObj)) {
						pos = lJsonObj.get("pos").toString();
					}

					String od = "-";
					if (checkIfValueExist("OD", lJsonObj)) {
						od = lJsonObj.get("OD").toString();
					}

					String origin = "-";
					String destination = "-";
					if (!od.equals("-")) {
						origin = od.substring(0, 3);
						destination = od.substring(3);
					}

					String compartment = "-";
					if (checkIfValueExist("compartment", lJsonObj)) {
						compartment = lJsonObj.get("compartment").toString();
					}

					String fareBasis = "-";
					if (checkIfValueExist("fare_basis", lJsonObj)) {
						fareBasis = lJsonObj.get("fare_basis").toString();
					}
					String currency = "_";
					if (checkIfValueExist("currency", lJsonObj)) {
						currency = lJsonObj.get("currency").toString();
					}

					Double lYQ = 0D;
					if (checkIfValueExist("yqCharge", lJsonObj)) {
						lYQ = lJsonObj.getDouble("yqCharge");
					}

					Double lYQlastyr = 0D;
					if (checkIfValueExist("YQ_1", lJsonObj)) {
						lYQlastyr = lJsonObj.getDouble("YQ_1");
					}

					String footnote = "_";
					if (checkIfValueExist("footnote", lJsonObj)) {
						footnote = lJsonObj.get("footnote").toString();
					}
					String ruleId = "_";
					if (checkIfValueExist("Rule_id", lJsonObj)) {
						ruleId = lJsonObj.get("Rule_id").toString();
					}

					Double surcharge = 0D;
					if (checkIfValueExist("surCharge", lJsonObj)) {
						surcharge = lJsonObj.getDouble("surCharge");
					}
					Double totalfare = 0D;
					if (checkIfValueExist("totalFare", lJsonObj)) {
						totalfare = lJsonObj.getDouble("totalFare");
					}
					Double basefare = 0D;
					if (checkIfValueExist("baseFare", lJsonObj)) {
						basefare = lJsonObj.getDouble("baseFare");
					}
					Double tax = 0D;
					if (checkIfValueExist("taxes", lJsonObj)) {
						tax = lJsonObj.getDouble("taxes");
					}

					YQTable yq = new YQTable();

					yq.setPos(pos);
					yq.setOrigin(origin);
					yq.setDestination(destination);
					yq.setCompartment(compartment);
					yq.setFareBasis(fareBasis);
					yq.setBasefare(basefare);
					yq.setYQ(lYQ);
					yq.setRuleId(ruleId);
					yq.setFootnote(footnote);
					yq.setTax(tax);
					yq.setSurcharge(surcharge);
					yq.setCurrency(currency);
					yq.setTotalfare(totalfare);

					double YQVLYR = 0;
					if (lYQlastyr != null) {
						if (lYQlastyr != 0) {
							YQVLYR = ((lYQ - lYQlastyr) / lYQlastyr) * 100;
							yq.setYQVLYR(YQVLYR);
						} else {
							yq.setYQVLYR((double) 0);
						}
					}

					totalYQ += lYQ;

					totalYQlastyr += lYQlastyr;

					yqList.add(yq);
				}

				YqTotalResponse tm = new YqTotalResponse();
				tm.setTotalYQ(totalYQ);
				if (totalYQlastyr != 0)
					totalYQVLYR = ((totalYQ - totalYQlastyr) / totalYQlastyr) * 100;
				tm.setTotalYQVLYR(totalYQVLYR);
				totalsList.add(tm);
				yqMap.put("yqTotals", totalsList);
				yqMap.put("yqMap", yqList);

			}

		} catch (Exception e) {
			logger.error("getYQTable-Exception", e);
		}

		return yqMap;
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