package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.PerformanceDao;
import com.flynava.jupiter.model.AnalystPerformance;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.PerformanceService;
import com.flynava.jupiter.util.CalculationUtil;
import com.mongodb.DBObject;

@Service
public class PerformanceServiceImpl implements PerformanceService {

	@Autowired
	PerformanceDao performanceDao;

	@Override
	public Map<String, Float> getChannelSegmentRevenueSpread(RequestModel requestModel) {
		// TODO Auto-generated method stub

		ArrayList<DBObject> lDataList = performanceDao.getChannelSegmentRevenueSpread(requestModel);

		Map<String, Integer> revenueSpreadMap = new HashMap<String, Integer>();
		Map<String, Float> percentageChannelRevenueMap = new HashMap<String, Float>();
		JSONArray data = null;
		if (lDataList != null) {
			data = new JSONArray(lDataList);

			for (int i = 0; i < data.length(); i++) {
				JSONObject jsonObj = data.getJSONObject(i);

				JSONArray channelArray = null;
				if (jsonObj.has("channel"))
					channelArray = new JSONArray(jsonObj.get("channel").toString());

				JSONArray revenueArray = null;
				if (jsonObj.has("revenue"))
					revenueArray = new JSONArray(jsonObj.get("revenue").toString());

				if (channelArray != null && revenueArray != null && channelArray.length() == revenueArray.length()) {
					for (int c = 0; c < channelArray.length(); c++) {

						if (revenueSpreadMap.containsKey(channelArray.get(c).toString())) {
							revenueSpreadMap.put(channelArray.get(c).toString(),
									Math.round(revenueSpreadMap.get(channelArray.get(c).toString())
											+ Float.parseFloat(revenueArray.get(c).toString())));
						} else {

							revenueSpreadMap.put(channelArray.get(c).toString(),
									Math.round(Float.parseFloat(revenueArray.get(c).toString())));

						}

					}
				}

			}

			Set<String> channelSet = new HashSet<String>();
			channelSet = revenueSpreadMap.keySet();

			int totalRevenue = 0;

			for (String s : channelSet) {
				totalRevenue += revenueSpreadMap.get(s);
			}

			for (String s : channelSet) {

				float channelRevenue = 0.0f;
				if (totalRevenue != 0) {
					channelRevenue = (float) revenueSpreadMap.get(s) * 100 / (float) totalRevenue;
					percentageChannelRevenueMap.put(s, CalculationUtil.round(channelRevenue, 2));
				} else
					percentageChannelRevenueMap.put(s, (float) 0);

			}

		}

		return percentageChannelRevenueMap;
	}

	@Override
	public Map<String, Float> getDistributorSegmentRevenueSpread(RequestModel requestModel) {
		// TODO Auto-generated method stub

		ArrayList<DBObject> lDataList = performanceDao.getDistributorSegmentRevenueSpread(requestModel);

		Map<String, Integer> revenueSpreadMap = new HashMap<String, Integer>();
		Map<String, Float> distributorRevenuePercentageMap = new HashMap<String, Float>();
		Map<String, Float> resultMap = new HashMap<String, Float>();
		JSONArray data = null;
		if (lDataList != null) {
			data = new JSONArray(lDataList);

			for (int i = 0; i < data.length(); i++) {
				JSONObject jsonObj = data.getJSONObject(i);

				String distributor = null;
				if (jsonObj.has("Distributor"))
					distributor = jsonObj.get("Distributor").toString();

				float revenue = 0;
				if (jsonObj.has("revenue"))
					revenue = Float.parseFloat(jsonObj.get("revenue").toString());

				if (revenueSpreadMap.containsKey(distributor))
					revenueSpreadMap.put(distributor, Math.round(revenueSpreadMap.get(distributor) + revenue));
				else
					revenueSpreadMap.put(distributor, Math.round(revenue));

			}

			Set<String> distributorSet = new HashSet<String>();
			distributorSet = revenueSpreadMap.keySet();

			int totalRevenue = 0;
			for (String s : distributorSet) {

				totalRevenue += revenueSpreadMap.get(s);

			}

			for (String s : distributorSet) {

				float distributorRevenuePercentage = 0;

				if (totalRevenue != 0) {
					distributorRevenuePercentage = (float) revenueSpreadMap.get(s) * 100 / (float) totalRevenue;
				} else
					distributorRevenuePercentage = 0.0f;

				distributorRevenuePercentageMap.put(s, CalculationUtil.round(distributorRevenuePercentage, 2));

			}

			distributorRevenuePercentageMap = CalculationUtil.sortMapByValue(distributorRevenuePercentageMap);

			int i = 0;
			int distributorPercentage = 0;
			for (String s : distributorRevenuePercentageMap.keySet()) {

				if (i <= 4) {
					resultMap.put(s, distributorRevenuePercentageMap.get(s));
					distributorPercentage += distributorRevenuePercentageMap.get(s);
					i++;
				} else {
					resultMap.put("others", (float) (100 - distributorPercentage));
					break;

				}

			}

		}
		return resultMap;
	}

	@Override
	public Map<String, Float> getCustomerSegmentRevenueSpread(RequestModel requestModel) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public ArrayList<AnalystPerformance> getAnalystPerformance() {

		ArrayList<DBObject> analystDBObjectList = null;
		ArrayList<AnalystPerformance> analystPerformanceList = new ArrayList<AnalystPerformance>();
		if (performanceDao != null)
			analystDBObjectList = performanceDao.getAnalystPerformance();

		for (DBObject analystDBObject : analystDBObjectList) {

			AnalystPerformance analyst = new AnalystPerformance();

			if (analystDBObject.containsKey("analyst_name"))
				analyst.setAnalystName(analystDBObject.get("analyst_name").toString());

			if (analystDBObject.containsKey("simulation_run"))
				analyst.setSimulationRun(analystDBObject.get("simulation_run").toString());

			if (analystDBObject.containsKey("unique_strategies"))
				analyst.setUniqueStrategies(analystDBObject.get("unique_strategies").toString());

			if (analystDBObject.containsKey("lead_to_price_war"))
				analyst.setLeadToPriceWar(analystDBObject.get("lead_to_price_war").toString());

			if (analystDBObject.containsKey("no_fares"))
				analyst.setNoFares(analystDBObject.get("no_fares").toString());

			if (analystDBObject.containsKey("time_action"))
				analyst.setTimeAction(analystDBObject.get("time_action").toString());

			if (analystDBObject.containsKey("no_sales_requests"))
				analyst.setNoSalesRequest(analystDBObject.get("no_sales_requests").toString());

			if (analystDBObject.containsKey("time_respond"))
				analyst.setTimeRespond(analystDBObject.get("time_respond").toString());

			if (analystDBObject.containsKey("no_fares_filed"))
				analyst.setNoFaresFiled(analystDBObject.get("no_fares_filed").toString());

			if (analystDBObject.containsKey("time_file"))
				analyst.setTimeFile(analystDBObject.get("time_file").toString());

			if (analystDBObject.containsKey("time_spent_system"))
				analyst.setTimeSpentSystem(analystDBObject.get("time_spent_system").toString());

			if (analystDBObject.containsKey("time_spent_Jupiter"))
				analyst.setTimeSpentJupiter(analystDBObject.get("time_spent_Jupiter").toString());

			if (analystDBObject.containsKey("triggers_accepted"))
				analyst.setTriggersAccepted(analystDBObject.get("triggers_accepted").toString());

			if (analystDBObject.containsKey("triggers_rejected"))
				analyst.setTriggersRejected(analystDBObject.get("triggers_rejected").toString());

			if (analystDBObject.containsKey("triggers_watch"))
				analyst.setTriggersWatch(analystDBObject.get("triggers_watch").toString());

			if (analystDBObject.containsKey("triggers_pending"))
				analyst.setTriggersPending(analystDBObject.get("triggers_pending").toString());

			if (analystDBObject.containsKey("sales_review"))
				analyst.setSalesReview(analystDBObject.get("sales_review").toString());

			if (analystDBObject.containsKey("sales_request"))
				analyst.setSalesRequest(analystDBObject.get("sales_request").toString());

			if (analystDBObject.containsKey("last_update_date"))
				analyst.setLastUpdateDate(analystDBObject.get("last_update_date").toString());

			if (analystDBObject.containsKey("last_update_time"))
				analyst.setLastUpdateTime(analystDBObject.get("last_update_time").toString());

			analystPerformanceList.add(analyst);

		}

		return analystPerformanceList;
	}

	@Override
	public ArrayList<DBObject> getAnalystPerformanceSQL() {

		ArrayList<DBObject> analystDBObjectList = null;
		if (performanceDao != null)
			analystDBObjectList = performanceDao.getAnalystPerformanceSQL();

		return analystDBObjectList;
	}

	@Override
	public int saveSimulation() {

		int i = performanceDao.saveSimulation();

		return 0;

	}

	public ArrayList<DBObject> getJupiterPerformance() {

		ArrayList<DBObject> jupPerfDBObjectList = null;
		if (performanceDao != null)
			jupPerfDBObjectList = performanceDao.getJupiterPerformance();

		return jupPerfDBObjectList;

	}

}
