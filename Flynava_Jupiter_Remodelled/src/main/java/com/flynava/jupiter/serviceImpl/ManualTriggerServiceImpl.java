package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.ManualTriggerDao;
import com.flynava.jupiter.model.ManualTrgrsReqModel;
import com.flynava.jupiter.model.ManualTrigger;
import com.flynava.jupiter.serviceInterface.ManualTriggerService;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Utility;
import com.mongodb.DBObject;

@Service
public class ManualTriggerServiceImpl implements ManualTriggerService {

	@Autowired
	ManualTriggerDao manualTrgrDao;

	@Override
	public List<ManualTrigger> getManualTriggers(ManualTrgrsReqModel pRequestModel) {

		List<DBObject> dbObjListCurntYr = manualTrgrDao.getManualTriggers(pRequestModel,
				"JUP_DB_Manual_Triggers_Module");

		/*
		 * setting last year date in the requestModel to query collection for
		 * last year data
		 */

		Date curntYrFromDate = null;
		if (pRequestModel.getFromDate() != null) {
			curntYrFromDate = Utility.convertStringToDateFromat(pRequestModel.getFromDate());
		}

		Date curntYrToDate = null;
		if (pRequestModel.getToDate() != null) {
			curntYrToDate = Utility.convertStringToDateFromat(pRequestModel.getToDate());
		}

		Date lastYrFromDate = new Date();
		if (curntYrFromDate != null) {
			lastYrFromDate.setDate(curntYrFromDate.getDate());
			lastYrFromDate.setMonth(curntYrFromDate.getMonth());
			lastYrFromDate.setYear(curntYrFromDate.getYear() - 1);
		}
		pRequestModel.setFromDate(Utility.convertDateToStringFromat(lastYrFromDate));

		long datePeriod = Utility.getDifferenceDays(curntYrFromDate, curntYrToDate);

		Date lastYrToDate = new Date();
		if (curntYrToDate != null) {
			lastYrToDate.setDate(curntYrToDate.getDate());
			lastYrToDate.setMonth(curntYrToDate.getMonth());
			lastYrToDate.setYear(curntYrToDate.getYear() - 1);
		}
		pRequestModel.setToDate(Utility.convertDateToStringFromat(lastYrToDate));

		List<DBObject> dbObjListLastYr = manualTrgrDao.getManualTriggers(pRequestModel,
				"JUP_DB_Manual_Triggers_Module");

		Map<String, ManualTrigger> manualTrgrCurYrMap = getManualTriggerMap(dbObjListCurntYr);
		Map<String, ManualTrigger> manualTrgrLastYrMap = getManualTriggerMap(dbObjListLastYr);

		List<ManualTrigger> manualTrgrList = new ArrayList<ManualTrigger>();

		for (Map.Entry<String, ManualTrigger> mapEle : manualTrgrCurYrMap.entrySet()) {

			ManualTrigger manualTrgrCYR = mapEle.getValue();

			float flownPaxLYR = 0;
			float salesPaxLYR = 0;
			float flownPax = 0;
			float salesPax = 0;
			float targetPax = 0;
			float forecastPax = 0;

			float distance = 0;

			float salesRevenueLYR = 0;
			float flownRevenueLYR = 0;
			float salesRevenue = 0;
			float flownRevenue = 0;
			float targetRevenue = 0;
			float forecastRevenue = 0;

			float avgFare = 0;
			float avgForecastFare = 0;

			float mrktSize = 0;

			flownPax = manualTrgrCYR.getFlownPax();
			salesPax = manualTrgrCYR.getSalesPax();
			targetPax = manualTrgrCYR.getTargetPax();
			forecastPax = manualTrgrCYR.getForecastPax();

			distance = manualTrgrCYR.getDistance();

			salesRevenue = manualTrgrCYR.getSalesRevenue();
			flownRevenue = manualTrgrCYR.getFlownRevenue();
			targetRevenue = manualTrgrCYR.getTargetRevenue();
			forecastRevenue = manualTrgrCYR.getForecastRevenue();

			mrktSize = manualTrgrCYR.getMarketSize();

			float paxVTGT = 0;
			if (targetPax != 0)
				paxVTGT = CalculationUtil.calculateVTGTRemodelled(salesPax, forecastPax * datePeriod,
						targetPax * datePeriod);
			manualTrgrCYR.setPaxVTGT(paxVTGT);

			float revenueVTGT = 0;
			if (targetRevenue != 0)
				revenueVTGT = CalculationUtil.calculateVTGTRemodelled(salesRevenue, forecastRevenue * datePeriod,
						targetRevenue * datePeriod);
			manualTrgrCYR.setRevenueVTGT(revenueVTGT);

			float mrktSharePax = 0;
			if (mrktSize != 0)
				mrktSharePax = salesPax / mrktSize;

			manualTrgrCYR.setMarketSharePax(mrktSharePax);

			float yield = CalculationUtil.calculateYield(salesRevenue, salesPax, distance);
			manualTrgrCYR.setYield(yield);

			float yieldForecast = CalculationUtil.calculateYieldForecast(forecastRevenue, forecastPax, distance);
			manualTrgrCYR.setYieldForecast(yieldForecast);

			float marketShare = CalculationUtil.doDivision(salesPax, mrktSize) * 100;
			manualTrgrCYR.setMarketSharePax(marketShare);

			if (manualTrgrLastYrMap.containsKey(mapEle.getKey())) {

				ManualTrigger manualTrgrLYR = manualTrgrLastYrMap.get(mapEle.getKey());

				flownPaxLYR = manualTrgrLYR.getFlownPax();
				salesPaxLYR = manualTrgrLYR.getSalesPax();

				salesRevenueLYR = manualTrgrLYR.getSalesRevenue();
				flownRevenueLYR = manualTrgrLYR.getFlownRevenue();

			}

			manualTrgrList.add(manualTrgrCYR);

		}

		return manualTrgrList;
	}

	public Map<String, ManualTrigger> getManualTriggerMap(List<DBObject> dbObjListCurntYr) {

		Map<String, ManualTrigger> manualtrgrgMap = new HashMap<String, ManualTrigger>();
		String key = null;

		if (dbObjListCurntYr != null) {
			for (int i = 0; i < dbObjListCurntYr.size(); i++) {

				DBObject dbObj = dbObjListCurntYr.get(i);

				ManualTrigger manualTrgr = null;

				try {

					JSONObject posObj = null;
					if (dbObj.containsKey("pos") && dbObj.get("pos") != null)
						posObj = new JSONObject(dbObj.get("pos").toString());

					JSONObject originObj = null;
					if (dbObj.containsKey("origin") && dbObj.get("origin") != null)
						originObj = new JSONObject(dbObj.get("origin").toString());

					JSONObject destinationObj = null;
					if (dbObj.containsKey("destination") && dbObj.get("destination") != null)
						destinationObj = new JSONObject(dbObj.get("destination").toString());

					JSONObject compartmentObj = null;
					if (dbObj.containsKey("compartment") && dbObj.get("compartment") != null)
						compartmentObj = new JSONObject(dbObj.get("compartment").toString());

					key = posObj.toString() + originObj.toString() + destinationObj.toString()
							+ compartmentObj.toString();

					if (manualtrgrgMap.containsKey(key)) {

						manualTrgr = manualtrgrgMap.get(key);

						JSONObject forcstObj = null;
						if (dbObj.containsKey("forecast") && dbObj.get("forecast") != null)
							forcstObj = new JSONObject(dbObj.get("forecast").toString());

						float proratedForecastPax = 0;

						int depMonth = 0;
						if (valueExists(dbObj, "dep_month"))
							depMonth = Math.round(Float.parseFloat((dbObj.get("dep_month").toString())));

						int depYear = 0;
						if (valueExists(dbObj, "dep_year"))
							depYear = Math.round(Float.parseFloat((dbObj.get("dep_year").toString())));

						int noOfDays = Utility.numberOfDaysInMonth(depMonth, depYear);

						if (valueExists(forcstObj, "pax") && !Utility.datePassed(dbObj.get("dep_date").toString())
								&& noOfDays != 0) {

							proratedForecastPax = Float.parseFloat(forcstObj.get("pax").toString()) / noOfDays;
						}
						manualTrgr.setForecastPax(proratedForecastPax + manualTrgr.getForecastPax());

						float proratedForecastRevenue = 0;

						if (valueExists(forcstObj, "revenue") && !Utility.datePassed(dbObj.get("dep_date").toString())
								&& noOfDays != 0) {

							proratedForecastRevenue = Float.parseFloat(forcstObj.get("revenue").toString()) / noOfDays;
						}
						manualTrgr.setForecastRevenue(proratedForecastRevenue + manualTrgr.getForecastRevenue());

						JSONObject salesRevObj = null;
						if (dbObj.containsKey("sale_revenue") && dbObj.get("sale_revenue") != null)
							salesRevObj = new JSONObject(dbObj.get("sale_revenue").toString());

						float salesRevenue = 0;
						if (valueExists(salesRevObj, "value"))
							salesRevenue = Float.parseFloat(salesRevObj.get("value").toString());
						manualTrgr.setSalesRevenue(salesRevenue + manualTrgr.getSalesRevenue());

						JSONObject flwnRevObj = null;
						if (dbObj.containsKey("flown_revenue") && dbObj.get("flown_revenue") != null)
							flwnRevObj = new JSONObject(dbObj.get("flown_revenue").toString());

						String depDate = null;
						if (dbObj.containsKey("dep_date") && dbObj.get("dep_date") != null)
							depDate = dbObj.get("dep_date").toString();

						JSONObject targetObj = null;
						if (dbObj.containsKey("target") && dbObj.get("target") != null)
							targetObj = new JSONObject(dbObj.get("target").toString());

						float proratedTargetPax = 0;
						if (valueExists(targetObj, "pax") && noOfDays != 0)
							proratedTargetPax = Float.parseFloat(targetObj.get("pax").toString()) / noOfDays;

						if (depDate != null && !depDate.equalsIgnoreCase(manualTrgr.getDepDate()))
							manualTrgr.setTargetPax(proratedTargetPax + manualTrgr.getTargetPax());
						else
							manualTrgr.setTargetPax(proratedTargetPax);

						float proratedTargetRevenue = 0;
						if (valueExists(targetObj, "revenue") && noOfDays != 0)
							proratedTargetRevenue = Float.parseFloat(targetObj.get("revenue").toString()) / noOfDays;
						if (depDate != null && !depDate.equalsIgnoreCase(manualTrgr.getDepDate()))
							manualTrgr.setTargetRevenue(proratedTargetRevenue + manualTrgr.getTargetRevenue());
						else

							manualTrgr.setTargetRevenue(proratedTargetRevenue);

						float proratedAvgFare = 0;
						if (valueExists(targetObj, "avgFare") && noOfDays != 0)
							proratedAvgFare = Float.parseFloat(targetObj.get("avgFare").toString()) / noOfDays;

						if (depDate != null && !depDate.equalsIgnoreCase(manualTrgr.getDepDate()))
							manualTrgr.setTargetAvgFare(proratedAvgFare + manualTrgr.getTargetAvgFare());
						else
							manualTrgr.setTargetAvgFare(proratedAvgFare);

						float flownRevenue = 0;
						if (valueExists(flwnRevObj, "value"))
							flownRevenue = Float.parseFloat(flwnRevObj.get("value").toString());
						manualTrgr.setFlownRevenue(flownRevenue + manualTrgr.getFlownRevenue());

						JSONObject salesPaxObj = null;
						if (dbObj.containsKey("sale_pax") && dbObj.get("sale_pax") != null) {
							salesPaxObj = new JSONObject(dbObj.get("sale_pax").toString());
						}

						float salesPax = 0;
						if (valueExists(salesPaxObj, "value"))
							salesPax = Float.parseFloat(salesPaxObj.get("value").toString());
						manualTrgr.setSalesPax(salesPax + manualTrgr.getSalesPax());

						JSONObject flwnPaxObj = null;
						if (dbObj.containsKey("flown_pax") && dbObj.get("flown_pax") != null)
							flwnPaxObj = new JSONObject(dbObj.get("flown_pax").toString());

						float flownPax = 0;
						if (valueExists(flwnPaxObj, "value"))
							flownPax = Float.parseFloat(salesPaxObj.get("value").toString());
						manualTrgr.setSalesPax(flownPax + manualTrgr.getFlownPax());

						JSONObject MSPaxObj = null;
						if (dbObj.containsKey("MS_pax") && dbObj.get("MS_pax") != null)
							MSPaxObj = new JSONObject(dbObj.get("MS_pax").toString());

						float mrktSize = 0;
						if (valueExists(MSPaxObj, "market_size"))
							mrktSize = Float.parseFloat(MSPaxObj.get("market_size").toString());

						if (manualTrgr.getDepMonth() != depMonth) {
							mrktSize += manualTrgr.getMarketSize();
						}

						manualTrgr.setMarketSize(mrktSize);

						manualTrgr.setDepDate(depDate);

						if (valueExists(dbObj, "distance"))
							manualTrgr.setDistance(Float.parseFloat(dbObj.get("distance").toString()));

						manualtrgrgMap.put(key, manualTrgr);

					} else {

						manualTrgr = new ManualTrigger();

						manualTrgr.setPos(posObj.toString());

						manualTrgr.setOrigin(originObj.toString());

						manualTrgr.setDestination(destinationObj.toString());

						manualTrgr.setCompartment(compartmentObj.toString());

						String depDate = null;
						if (dbObj.containsKey("dep_date") && dbObj.get("dep_date") != null)
							depDate = dbObj.get("dep_date").toString();
						manualTrgr.setDepDate(depDate);

						JSONObject forcstObj = null;
						if (dbObj.containsKey("forecast") && dbObj.get("forecast") != null)
							forcstObj = new JSONObject(dbObj.get("forecast").toString());

						float proratedForecastPax = 0;

						int depMonth = 0;
						if (valueExists(dbObj, "dep_month"))
							depMonth = Math.round(Float.parseFloat((dbObj.get("dep_month").toString())));

						manualTrgr.setDepMonth(depMonth);

						int depYear = 0;
						if (valueExists(dbObj, "dep_year"))
							depYear = Math.round(Float.parseFloat((dbObj.get("dep_year").toString())));

						int noOfDays = Utility.numberOfDaysInMonth(depMonth, depYear);

						if (valueExists(forcstObj, "pax") && !Utility.datePassed(dbObj.get("dep_date").toString())) {

							if (noOfDays != 0)
								proratedForecastPax = Float.parseFloat(forcstObj.get("pax").toString()) / noOfDays;
						}
						manualTrgr.setForecastPax(proratedForecastPax);

						float proratedForecastRevenue = 0;

						if (valueExists(forcstObj, "revenue")
								&& !Utility.datePassed(dbObj.get("dep_date").toString())) {

							if (noOfDays != 0)
								proratedForecastRevenue = Float.parseFloat(forcstObj.get("revenue").toString())
										/ noOfDays;
						}
						manualTrgr.setForecastRevenue(proratedForecastRevenue);

						JSONObject salesRevObj = null;
						if (dbObj.containsKey("sale_revenue") && dbObj.get("sale_revenue") != null)
							salesRevObj = new JSONObject(dbObj.get("sale_revenue").toString());

						float salesRevenue = 0;
						if (valueExists(salesRevObj, "value"))
							salesRevenue = Float.parseFloat(salesRevObj.get("value").toString());
						manualTrgr.setSalesRevenue(salesRevenue);

						JSONObject flwnRevObj = null;
						if (dbObj.containsKey("flown_revenue") && dbObj.get("flown_revenue") != null)
							flwnRevObj = new JSONObject(dbObj.get("flown_revenue").toString());

						float flownRevenue = 0;
						if (valueExists(flwnRevObj, "value"))
							flownRevenue = Float.parseFloat(flwnRevObj.get("value").toString());
						manualTrgr.setFlownRevenue(flownRevenue);

						JSONObject targetObj = null;
						if (dbObj.containsKey("target") && dbObj.get("target") != null)
							targetObj = new JSONObject(dbObj.get("target").toString());

						float proratedTargetPax = 0;
						if (valueExists(targetObj, "pax") && noOfDays != 0)
							proratedTargetPax = Float.parseFloat(targetObj.get("pax").toString()) / noOfDays;
						manualTrgr.setTargetPax(proratedTargetPax);

						float proratedTargetRevenue = 0;
						if (valueExists(targetObj, "revenue") && noOfDays != 0)
							proratedTargetRevenue = Float.parseFloat(targetObj.get("revenue").toString()) / noOfDays;
						manualTrgr.setTargetRevenue(proratedTargetRevenue);

						float proratedAvgFare = 0;
						if (valueExists(targetObj, "avgFare") && noOfDays != 0)
							proratedAvgFare = Float.parseFloat(targetObj.get("avgFare").toString()) / noOfDays;
						manualTrgr.setTargetAvgFare(proratedAvgFare);

						JSONObject salesPaxObj = null;
						if (dbObj.containsKey("sale_pax") && dbObj.get("sale_pax") != null)
							salesPaxObj = new JSONObject(dbObj.get("sale_pax").toString());

						float salesPax = 0;
						if (valueExists(salesPaxObj, "value"))
							salesPax = Float.parseFloat(salesPaxObj.get("value").toString());
						manualTrgr.setSalesPax(salesPax);

						JSONObject flwnPaxObj = null;
						if (dbObj.containsKey("flown_pax") && dbObj.get("flown_pax") != null)
							flwnPaxObj = new JSONObject(dbObj.get("flown_pax").toString());

						float flownPax = 0;
						if (valueExists(flwnPaxObj, "value"))
							flownPax = Float.parseFloat(salesPaxObj.get("value").toString());
						manualTrgr.setSalesPax(flownPax);

						JSONObject MSPaxObj = null;
						if (dbObj.containsKey("MS_pax") && dbObj.get("MS_pax") != null)
							MSPaxObj = new JSONObject(dbObj.get("MS_pax").toString());

						float mrktSize = 0;
						if (valueExists(MSPaxObj, "market_size"))
							mrktSize = Float.parseFloat(MSPaxObj.get("market_size").toString());
						manualTrgr.setMarketSize(mrktSize);

						if (valueExists(dbObj, "distance"))
							manualTrgr.setDistance(Float.parseFloat(dbObj.get("distance").toString()));

						manualtrgrgMap.put(key, manualTrgr);

					}

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}

		}

		return manualtrgrgMap;
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

				try {
					if (jsonObj.has(key) && jsonObj.get(key) != null
							&& !jsonObj.get(key).toString().equalsIgnoreCase("null")
							&& !jsonObj.get(key).toString().equalsIgnoreCase("NA")) {

						valExists = true;

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		return valExists;

	}

}
