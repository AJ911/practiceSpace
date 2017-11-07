package com.flynava.jupiter.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.AnalystDao;
import com.flynava.jupiter.model.AnalystChannelCountPerc;
import com.flynava.jupiter.model.AnalystChannelPerc;
import com.flynava.jupiter.model.AnalystCustomerSegment;
import com.flynava.jupiter.model.AnalystDistributor;
import com.flynava.jupiter.model.AnalystDistributorFare;
import com.flynava.jupiter.model.AnalystDistributorPerc;
import com.flynava.jupiter.model.AnalystPerformanceGrid;
import com.flynava.jupiter.model.AnalystPieChart;
import com.flynava.jupiter.model.AnalystPieChartCount;
import com.flynava.jupiter.model.AnalystSqlGraph;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.SqlGraph;
import com.flynava.jupiter.model.TriggerCount;
import com.flynava.jupiter.model.TriggerbasedCount;
import com.flynava.jupiter.serviceInterface.AnalystService;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.Utility;
import com.mongodb.DBObject;

@Service
public class AnalystServiceImpl implements AnalystService {

	@Autowired
	AnalystDao mAnalystDao;

	@Override
	public Map<String, Object> getAnalystEvents(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDao.getAnalystEvents(pRequestModel);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FaresData", FaresObj);
		return map;
	}

	@Override
	public Map<String, Object> getAnalystChannelRevenue(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDao.getAnalystGraph(pRequestModel);
		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);
			JSONArray pos = null;
			if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
				pos = new JSONArray(jsonObj.get("list_of_pos").toString());

			}

			for (int j = 0; j < pos.length(); j++) {
				list.add(pos.getString(j));
			}
		}
		List<DBObject> SalesObj = mAnalystDao.getAnalystChannelRevenue(list);
		JSONArray data1 = new JSONArray(SalesObj);
		List<AnalystPieChart> pielist = new ArrayList<AnalystPieChart>();
		List<AnalystChannelPerc> perclist = new ArrayList<AnalystChannelPerc>();
		AnalystPieChart lpiechart = null;
		for (int i = 0; i < data1.length(); i++) {
			JSONObject jsonObj = data1.getJSONObject(i);
			lpiechart = new AnalystPieChart();

			lpiechart.setChannel(jsonObj.get("_id").toString());
			lpiechart.setChannelValue(Float.parseFloat(jsonObj.get("value").toString()));

			pielist.add(lpiechart);
		}
		float totalchannelvalue = 0;
		for (AnalystPieChart p : pielist) {

			float channelvalue = p.getChannelValue();
			totalchannelvalue += channelvalue;

		}
		AnalystChannelPerc lperc = null;
		for (AnalystPieChart p : pielist) {
			lperc = new AnalystChannelPerc();
			String channel = p.getChannel();
			float channelvalue = p.getChannelValue();
			float channelPerc = (channelvalue / totalchannelvalue) * 100;
			lperc.setChannel(channel);
			lperc.setChannelValue(channelvalue);
			lperc.setChannelPerc(channelPerc);
			perclist.add(lperc);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Channel_Revenue_Spread", perclist);
		return map;
	}

	@Override
	public Map<String, Object> getAnalystChannelFare(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDao.getAnalystGraph(pRequestModel);
		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);
			JSONArray pos = null;
			if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
				pos = new JSONArray(jsonObj.get("list_of_pos").toString());

			}

			for (int j = 0; j < pos.length(); j++) {
				list.add(pos.getString(j));
			}
		}
		List<DBObject> SalesObj = mAnalystDao.getAnalystChannelRevenue(list);
		JSONArray data1 = new JSONArray(SalesObj);
		List<AnalystPieChartCount> pielist = new ArrayList<AnalystPieChartCount>();
		List<AnalystChannelCountPerc> perclist = new ArrayList<AnalystChannelCountPerc>();
		AnalystPieChartCount lpiechart = null;
		for (int i = 0; i < data1.length(); i++) {
			JSONObject jsonObj = data1.getJSONObject(i);
			lpiechart = new AnalystPieChartCount();

			lpiechart.setChannel(jsonObj.get("_id").toString());
			lpiechart.setChannelCount(Float.parseFloat(jsonObj.get("count").toString()));

			pielist.add(lpiechart);
		}
		float totalchannelvalue = 0;
		for (AnalystPieChartCount p : pielist) {

			float channelvalue = p.getChannelCount();
			totalchannelvalue += channelvalue;

		}
		AnalystChannelCountPerc lperc = null;
		for (AnalystPieChartCount p : pielist) {
			lperc = new AnalystChannelCountPerc();
			String channel = p.getChannel();
			float channelvalue = p.getChannelCount();
			float channelPerc = (channelvalue / totalchannelvalue) * 100;
			lperc.setChannel(channel);
			lperc.setChannelcount(channelvalue);
			lperc.setChannelCountPerc(channelPerc);
			perclist.add(lperc);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Channel_Fare_Spread", perclist);
		return map;
	}

	@Override
	public Map<String, Object> getAnalystDistributorRevenuePie(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDao.getAnalystGraph(pRequestModel);

		// List<DBObject> functionData = resultList;

		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);
			JSONArray pos = null;
			if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
				pos = new JSONArray(jsonObj.get("list_of_pos").toString());

			}

			for (int j = 0; j < pos.length(); j++) {
				list.add(pos.getString(j));
			}
		}
		List<DBObject> SalesObj = mAnalystDao.getAnalystDistributorRevenue(list);
		JSONArray data1 = new JSONArray(SalesObj);
		List<AnalystDistributor> pielist = new ArrayList<AnalystDistributor>();
		List<AnalystDistributorPerc> perclist = new ArrayList<AnalystDistributorPerc>();
		List<AnalystDistributorPerc> percSortlist = new ArrayList<AnalystDistributorPerc>();
		List<AnalystDistributorPerc> percotherSortlist = new ArrayList<AnalystDistributorPerc>();
		AnalystDistributor lpiechart = null;
		for (int i = 0; i < data1.length(); i++) {
			JSONObject jsonObj = data1.getJSONObject(i);
			lpiechart = new AnalystDistributor();

			lpiechart.setDistributor(jsonObj.get("_id").toString());
			lpiechart.setDistributorValue(Float.parseFloat(jsonObj.get("value").toString()));

			pielist.add(lpiechart);
		}
		float totaldistributorvalue = 0;
		for (AnalystDistributor p : pielist) {

			float distributorvalue = p.getDistributorValue();
			totaldistributorvalue += distributorvalue;

		}
		AnalystDistributorPerc lperc = null;
		for (AnalystDistributor p : pielist) {
			lperc = new AnalystDistributorPerc();
			String distributor = p.getDistributor();
			float distributorValue = p.getDistributorValue();
			float distributorPerc = (distributorValue / totaldistributorvalue) * 100;
			lperc.setDistributor(distributor);
			lperc.setDistributorValue(distributorValue);
			lperc.setDistributorperc(distributorPerc);
			perclist.add(lperc);

		}
		Collections.sort(perclist, new lperc());
		if (perclist.size() > 10) {
			percSortlist.addAll(perclist.subList(0, 4));
			percotherSortlist.addAll(perclist.subList(5, perclist.size()));

		} else {
			percSortlist.addAll(perclist.subList(0, perclist.size()));
		}
		float otherdistributorvalue = 0;
		for (AnalystDistributorPerc sorting : percotherSortlist) {

			otherdistributorvalue += sorting.getDistributorValue();

		}
		float totaldistributorperc = otherdistributorvalue / totaldistributorvalue * 100;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Distributor_Revenue_Spread", percSortlist);
		map.put("others_Revenue_Spread1", totaldistributorperc);
		// map.put("other", percotherSortlist);

		return map;
	}

	class lperc implements Comparator<AnalystDistributorPerc> {
		@Override
		public int compare(AnalystDistributorPerc o1, AnalystDistributorPerc o2) {
			if (o1.getDistributorperc() > 0) {
				if (o1.getDistributorperc() < o2.getDistributorperc()) {
					return 1;
				} else {
					return -1;
				}
			}
			return 0;
		}
	}

	@Override
	public Map<String, Object> getAnalystDistributorFarePie(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDao.getAnalystGraph(pRequestModel);
		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);
			JSONArray pos = null;
			if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
				pos = new JSONArray(jsonObj.get("list_of_pos").toString());

			}

			for (int j = 0; j < pos.length(); j++) {
				list.add(pos.getString(j));
			}
		}
		List<DBObject> SalesObj = mAnalystDao.getAnalystDistributorRevenue(list);
		JSONArray data1 = new JSONArray(SalesObj);
		List<AnalystDistributorFare> pielist = new ArrayList<AnalystDistributorFare>();
		List<AnalystDistributorPerc> perclist = new ArrayList<AnalystDistributorPerc>();
		List<AnalystDistributorPerc> percSortlist = new ArrayList<AnalystDistributorPerc>();
		List<AnalystDistributorPerc> percotherSortlist = new ArrayList<AnalystDistributorPerc>();
		AnalystDistributorFare lpiechart = null;
		for (int i = 0; i < data1.length(); i++) {
			JSONObject jsonObj = data1.getJSONObject(i);
			lpiechart = new AnalystDistributorFare();

			lpiechart.setDistributor(jsonObj.get("_id").toString());
			lpiechart.setDistributorCount(Float.parseFloat(jsonObj.get("count").toString()));

			pielist.add(lpiechart);
		}
		float totalchannelvalue = 0;
		for (AnalystDistributorFare p : pielist) {

			float channelvalue = p.getDistributorCount();
			totalchannelvalue += channelvalue;

		}
		AnalystDistributorPerc lperc = null;
		for (AnalystDistributorFare p : pielist) {
			lperc = new AnalystDistributorPerc();
			String channel = p.getDistributor();
			float channelvalue = p.getDistributorCount();
			float channelPerc = (channelvalue / totalchannelvalue) * 100;
			lperc.setDistributor(channel);
			lperc.setDistributorValue(channelvalue);
			lperc.setDistributorperc(channelPerc);
			perclist.add(lperc);

		}

		Collections.sort(perclist, new lperc1());
		if (perclist.size() > 10) {
			percSortlist.addAll(perclist.subList(0, 4));
			percotherSortlist.addAll(perclist.subList(5, perclist.size()));
		} else {
			percSortlist.addAll(perclist.subList(0, perclist.size()));
		}
		float otherdistributorvalue = 0;
		for (AnalystDistributorPerc sorting : percotherSortlist) {

			otherdistributorvalue += sorting.getDistributorValue();

		}
		float totaldistributorperc = otherdistributorvalue / totalchannelvalue * 100;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Distributor_Fare_Spread", percSortlist);
		map.put("other_distributor_fare", totaldistributorperc);
		return map;
	}

	int count = 0;

	class lperc1 implements Comparator<AnalystDistributorPerc> {
		@Override
		public int compare(AnalystDistributorPerc o1, AnalystDistributorPerc o2) {
			try {
				// if (o1.getDistributorperc() > 0) {
				if (o1.getDistributorperc() < 0) {
					count++;
					System.out.println(o1.getDistributorperc());
					System.out.println(o2.getDistributorperc());
					if (o1.getDistributorperc() < o2.getDistributorperc()) {

						return 1;
					} else {
						return -1;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
	}

	@Override
	public Map<String, Object> getAnalystCustomerRevenuePie(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDao.getAnalystGraph(pRequestModel);

		// List<DBObject> functionData = resultList;

		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);
			JSONArray pos = null;
			if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
				pos = new JSONArray(jsonObj.get("list_of_pos").toString());

			}

			for (int j = 0; j < pos.length(); j++) {
				list.add(pos.getString(j));
			}
		}
		List<DBObject> SalesObj = mAnalystDao.getAnalystCustomerRevenue(list);
		JSONArray data1 = new JSONArray(SalesObj);
		List<AnalystDistributor> pielist = new ArrayList<AnalystDistributor>();
		List<AnalystCustomerSegment> perclist = new ArrayList<AnalystCustomerSegment>();
		AnalystDistributor lpiechart = null;
		for (int i = 0; i < data1.length(); i++) {
			JSONObject jsonObj = data1.getJSONObject(i);
			lpiechart = new AnalystDistributor();
			if (jsonObj.has("_id") && jsonObj.get("_id").toString() != null) {

				lpiechart.setDistributor(jsonObj.get("_id").toString());
				lpiechart.setDistributorValue(Float.parseFloat(jsonObj.get("value").toString()));
				pielist.add(lpiechart);
			}
		}
		float totaldistributorvalue = 0;
		for (AnalystDistributor p : pielist) {

			float distributorvalue = p.getDistributorValue();
			totaldistributorvalue += distributorvalue;

		}
		AnalystCustomerSegment lperc = null;
		for (AnalystDistributor p : pielist) {
			lperc = new AnalystCustomerSegment();
			String distributor = p.getDistributor();
			float distributorValue = p.getDistributorValue();
			float distributorPerc = (distributorValue / totaldistributorvalue) * 100;
			lperc.setCustomerSegment(distributor);
			lperc.setCustomerSegmentperc(distributorPerc);
			perclist.add(lperc);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Customer_Revenue_Spread", perclist);
		return map;
	}

	@Override
	public Map<String, Object> getAnalystCustomerFarePie(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDao.getAnalystGraph(pRequestModel);
		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);
			JSONArray pos = null;
			if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
				pos = new JSONArray(jsonObj.get("list_of_pos").toString());

			}

			for (int j = 0; j < pos.length(); j++) {
				list.add(pos.getString(j));
			}
		}
		List<DBObject> SalesObj = mAnalystDao.getAnalystCustomerRevenue(list);
		JSONArray data1 = new JSONArray(SalesObj);
		List<AnalystPieChartCount> pielist = new ArrayList<AnalystPieChartCount>();
		List<AnalystChannelCountPerc> perclist = new ArrayList<AnalystChannelCountPerc>();
		AnalystPieChartCount lpiechart = null;
		for (int i = 0; i < data1.length(); i++) {
			JSONObject jsonObj = data1.getJSONObject(i);
			lpiechart = new AnalystPieChartCount();
			if (jsonObj.has("_id") && jsonObj.get("_id") != null) {
				lpiechart.setChannel(jsonObj.get("_id").toString());
				lpiechart.setChannelCount(Float.parseFloat(jsonObj.get("count").toString()));

				pielist.add(lpiechart);
			}
		}
		float totalchannelvalue = 0;
		for (AnalystPieChartCount p : pielist) {

			float channelvalue = p.getChannelCount();
			totalchannelvalue += channelvalue;

		}
		AnalystChannelCountPerc lperc = null;
		for (AnalystPieChartCount p : pielist) {
			lperc = new AnalystChannelCountPerc();
			String channel = p.getChannel();
			float channelvalue = p.getChannelCount();
			float channelPerc = (channelvalue / totalchannelvalue) * 100;
			lperc.setChannel(channel);
			lperc.setChannelcount(channelvalue);
			lperc.setChannelCountPerc(channelPerc);
			perclist.add(lperc);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Customer_Segment_Fare_Spread", perclist);
		return map;
	}

	@Override
	public List<AnalystPerformanceGrid> getAnalystPerformanceGrid(RequestModel pRequestModel) {
		List<DBObject> FaresObj = mAnalystDao.getAnalystGraph(pRequestModel);

		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);
			JSONArray pos = null;
			if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
				pos = new JSONArray(jsonObj.get("list_of_pos").toString());

			}

			for (int j = 0; j < pos.length(); j++) {
				list.add(pos.getString(j));
			}
		}

		List<DBObject> FareTypeObj = mAnalystDao.getAnalystPerformanceGrid(list, pRequestModel);

		pRequestModel.setFromDate(Utility.getPrevYrDate(pRequestModel.getFromDate()));
		pRequestModel.setToDate(Utility.getPrevYrDate(pRequestModel.getToDate()));

		List<DBObject> FareTypeLYObj = mAnalystDao.getAnalystPerformanceGrid(list, pRequestModel);

		JSONArray data1 = new JSONArray(FareTypeObj);

		Map<String, AnalystPerformanceGrid> PerfGridCurYrMap = getPerfGridMap(FareTypeObj);
		Map<String, AnalystPerformanceGrid> PerfGridLastYrMap = getPerfGridMap(FareTypeLYObj);

		List<AnalystPerformanceGrid> perfGridList = new ArrayList<AnalystPerformanceGrid>();

		for (Map.Entry<String, AnalystPerformanceGrid> mapEle : PerfGridCurYrMap.entrySet()) {

			AnalystPerformanceGrid perfGridCY = mapEle.getValue();

			float flownPaxLYR = 0;
			float salesPaxLYR = 0;
			float flownPax = 0;
			float salesPax = 0;
			float targetPax = 0;
			float forecastPax = 0;

			float salesRevenueLYR = 0;
			float flownRevenueLYR = 0;
			float salesRevenue = 0;
			float flownRevenue = 0;
			float targetRevenue = 0;
			float forecastRevenue = 0;

			float avgFare = 0;
			float avgForecastFare = 0;

			flownPax = perfGridCY.getFlownPax();
			salesPax = perfGridCY.getSalesPax();
			targetPax = perfGridCY.getTargetPax();
			forecastPax = perfGridCY.getForecastPax();

			salesRevenue = perfGridCY.getSalesRevenue();
			flownRevenue = perfGridCY.getFlownRevenue();
			targetRevenue = perfGridCY.getTargetRevenue();
			forecastRevenue = perfGridCY.getForecastRevenue();

			float paxVTGT = 0;
			if (targetPax != 0)
				paxVTGT = CalculationUtil.calculateVTGTRemodelled(flownPax, forecastPax, targetPax);
			perfGridCY.setPaxVTGT(paxVTGT);

			float revenueVTGT = 0;
			if (targetRevenue != 0)
				revenueVTGT = CalculationUtil.calculateVTGTRemodelled(flownRevenue, forecastRevenue, targetRevenue);
			perfGridCY.setRevenueVTGT(revenueVTGT);

			if (PerfGridLastYrMap.containsKey(mapEle.getKey())) {

				AnalystPerformanceGrid apGridLYR = PerfGridLastYrMap.get(mapEle.getKey());

				flownPaxLYR = apGridLYR.getFlownPax();
				salesPaxLYR = apGridLYR.getSalesPax();

				salesRevenueLYR = apGridLYR.getSalesRevenue();
				flownRevenueLYR = apGridLYR.getFlownRevenue();

			}

			perfGridList.add(perfGridCY);

		}

		return perfGridList;
	}

	public Map<String, AnalystPerformanceGrid> getPerfGridMap(List<DBObject> perfListCurntYr) {

		Map<String, AnalystPerformanceGrid> apGridMap = new HashMap<String, AnalystPerformanceGrid>();
		String key = null;
		ManualTriggerServiceImpl mTrigImpl = null;

		if (perfListCurntYr != null) {
			for (int i = 0; i < perfListCurntYr.size(); i++) {

				DBObject dbObj = perfListCurntYr.get(i);

				AnalystPerformanceGrid apGrid = null;

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

					if (apGridMap.containsKey(key)) {

						apGrid = apGridMap.get(key);

						/*
						 * JSONObject forcstObj = null; if
						 * (dbObj.containsKey("forecast") &&
						 * dbObj.get("forecast") != null) forcstObj = new
						 * JSONObject(dbObj.get("forecast").toString());
						 */

						float proratedForecastPax = 0;

						int depMonth = 0;
						if (mTrigImpl.valueExists(dbObj, "dep_month"))
							depMonth = Integer.parseInt(dbObj.get("dep_month").toString());

						int depYear = 0;
						if (mTrigImpl.valueExists(dbObj, "dep_year"))
							depYear = Integer.parseInt(dbObj.get("dep_year").toString());

						int noOfDays = Utility.numberOfDaysInMonth(depMonth, depYear);

						/*
						 * if (mTrigImpl.valueExists(forcstObj, "pax") &&
						 * !Utility.datePassed(dbObj.get("dep_date").toString())
						 * && noOfDays != 0) {
						 * 
						 * proratedForecastPax =
						 * Float.parseFloat(forcstObj.get("pax").toString()) /
						 * noOfDays; }
						 */
						apGrid.setForecastPax(proratedForecastPax + apGrid.getForecastPax());

						float proratedForecastRevenue = 0;

						/*
						 * if (mTrigImpl.valueExists(forcstObj, "revenue") &&
						 * !Utility.datePassed(dbObj.get("dep_date").toString())
						 * && noOfDays != 0) {
						 * 
						 * proratedForecastRevenue =
						 * Float.parseFloat(forcstObj.get("revenue").toString())
						 * / noOfDays; }
						 */
						apGrid.setForecastRevenue(proratedForecastRevenue + apGrid.getForecastRevenue());

						JSONObject salesRevObj = null;
						if (dbObj.containsKey("sale_revenue") && dbObj.get("sale_revenue") != null)
							salesRevObj = new JSONObject(dbObj.get("sale_revenue").toString());

						float salesRevenue = 0;
						if (mTrigImpl.valueExists(salesRevObj, "value"))
							salesRevenue = Float.parseFloat(salesRevObj.get("value").toString());
						apGrid.setSalesRevenue(salesRevenue + apGrid.getSalesRevenue());

						JSONObject flwnRevObj = null;
						if (dbObj.containsKey("flown_revenue") && dbObj.get("flown_revenue") != null)
							flwnRevObj = new JSONObject(dbObj.get("flown_revenue").toString());

						JSONObject targetObj = null;
						if (dbObj.containsKey("target") && dbObj.get("target") != null)
							targetObj = new JSONObject(dbObj.get("target").toString());

						float proratedTargetPax = 0;
						if (mTrigImpl.valueExists(targetObj, "pax") && noOfDays != 0)
							proratedTargetPax = Float.parseFloat(targetObj.get("pax").toString()) / noOfDays;
						apGrid.setTargetPax(proratedTargetPax + apGrid.getTargetPax());

						float proratedTargetRevenue = 0;
						if (mTrigImpl.valueExists(targetObj, "revenue") && noOfDays != 0)
							proratedTargetRevenue = Float.parseFloat(targetObj.get("revenue").toString()) / noOfDays;
						apGrid.setTargetRevenue(proratedTargetRevenue + apGrid.getTargetRevenue());

						float flownRevenue = 0;
						if (mTrigImpl.valueExists(flwnRevObj, "value"))
							flownRevenue = Float.parseFloat(flwnRevObj.get("value").toString());
						apGrid.setFlownRevenue(flownRevenue + apGrid.getFlownRevenue());

						JSONObject salesPaxObj = null;
						if (dbObj.containsKey("sale_pax") && dbObj.get("sale_pax") != null)
							salesPaxObj = new JSONObject(dbObj.get("sale_pax").toString());

						float salesPax = 0;
						if (mTrigImpl.valueExists(salesPaxObj, "value"))
							salesPax = Float.parseFloat(salesPaxObj.get("value").toString());
						apGrid.setSalesPax(salesPax + apGrid.getSalesPax());

						JSONObject flwnPaxObj = null;
						if (dbObj.containsKey("flown_pax") && dbObj.get("flown_pax") != null)
							flwnPaxObj = new JSONObject(dbObj.get("flown_pax").toString());

						float flownPax = 0;
						if (mTrigImpl.valueExists(flwnPaxObj, "value"))
							flownPax = Float.parseFloat(salesPaxObj.get("value").toString());
						apGrid.setSalesPax(flownPax + apGrid.getFlownPax());

						apGridMap.put(key, apGrid);

					} else {

						apGrid = new AnalystPerformanceGrid();

						apGrid.setPos(posObj);

						apGrid.setOrigin(originObj);

						apGrid.setDestination(destinationObj);

						apGrid.setCompartment(compartmentObj);

						/*
						 * JSONObject forcstObj = null; if
						 * (dbObj.containsKey("forecast") &&
						 * dbObj.get("forecast") != null) forcstObj = new
						 * JSONObject(dbObj.get("forecast").toString());
						 */
						float proratedForecastPax = 0;

						int depMonth = 0;
						if (mTrigImpl.valueExists(dbObj, "dep_month"))
							depMonth = Integer.parseInt(dbObj.get("dep_month").toString());

						int depYear = 0;
						if (mTrigImpl.valueExists(dbObj, "dep_year"))
							depYear = Integer.parseInt(dbObj.get("dep_year").toString());

						int noOfDays = Utility.numberOfDaysInMonth(depMonth, depYear);

						/*
						 * if (mTrigImpl.valueExists(forcstObj, "pax") &&
						 * !Utility.datePassed(dbObj.get("dep_date").toString())
						 * ) {
						 * 
						 * if (noOfDays != 0) proratedForecastPax =
						 * Float.parseFloat(forcstObj.get("pax").toString()) /
						 * noOfDays; }
						 */ apGrid.setForecastPax(proratedForecastPax);

						float proratedForecastRevenue = 0;

						/*
						 * if (mTrigImpl.valueExists(forcstObj, "revenue") &&
						 * !Utility.datePassed(dbObj.get("dep_date").toString())
						 * ) {
						 * 
						 * if (noOfDays != 0) proratedForecastRevenue =
						 * Float.parseFloat(forcstObj.get("revenue").toString())
						 * / noOfDays; }
						 */ apGrid.setForecastRevenue(proratedForecastRevenue);

						JSONObject salesRevObj = null;
						if (dbObj.containsKey("sale_revenue") && dbObj.get("sale_revenue") != null)
							salesRevObj = new JSONObject(dbObj.get("sale_revenue").toString());

						float salesRevenue = 0;
						if (mTrigImpl.valueExists(salesRevObj, "value"))
							salesRevenue = Float.parseFloat(salesRevObj.get("value").toString());
						apGrid.setSalesRevenue(salesRevenue);

						JSONObject flwnRevObj = null;
						if (dbObj.containsKey("flown_revenue") && dbObj.get("flown_revenue") != null)
							flwnRevObj = new JSONObject(dbObj.get("flown_revenue").toString());

						float flownRevenue = 0;
						if (mTrigImpl.valueExists(flwnRevObj, "value"))
							flownRevenue = Float.parseFloat(flwnRevObj.get("value").toString());
						apGrid.setFlownRevenue(flownRevenue);

						JSONObject targetObj = null;
						if (dbObj.containsKey("target") && dbObj.get("target") != null)
							targetObj = new JSONObject(dbObj.get("target").toString());

						float proratedTargetPax = 0;
						if (mTrigImpl.valueExists(targetObj, "pax") && noOfDays != 0)
							proratedTargetPax = Float.parseFloat(targetObj.get("pax").toString()) / noOfDays;
						apGrid.setTargetPax(proratedTargetPax);

						float proratedTargetRevenue = 0;
						if (mTrigImpl.valueExists(targetObj, "revenue") && noOfDays != 0)
							proratedTargetRevenue = Float.parseFloat(targetObj.get("revenue").toString()) / noOfDays;
						apGrid.setTargetRevenue(proratedTargetRevenue);

						JSONObject salesPaxObj = null;
						if (dbObj.containsKey("sale_pax") && dbObj.get("sale_pax") != null)
							salesPaxObj = new JSONObject(dbObj.get("sale_pax").toString());

						float salesPax = 0;
						if (mTrigImpl.valueExists(salesPaxObj, "value"))
							salesPax = Float.parseFloat(salesPaxObj.get("value").toString());
						apGrid.setSalesPax(salesPax);

						JSONObject flwnPaxObj = null;
						if (dbObj.containsKey("flown_pax") && dbObj.get("flown_pax") != null)
							flwnPaxObj = new JSONObject(dbObj.get("flown_pax").toString());

						float flownPax = 0;
						if (mTrigImpl.valueExists(flwnPaxObj, "value"))
							flownPax = Float.parseFloat(salesPaxObj.get("value").toString());
						apGrid.setSalesPax(flownPax);

						apGridMap.put(key, apGrid);

					}

					/*
					 * List<PerformanceGrid> pGridList = new
					 * ArrayList<PerformanceGrid>();
					 * List<AnalystPerformanceGrid> apGridList = new
					 * ArrayList<AnalystPerformanceGrid>(); String key = null;
					 * ManualTriggerServiceImpl mTrigImpl = null;
					 * PerformanceGrid pGridModel=null; AnalystPerformanceGrid
					 * apGridModel = null; try{ if (perfListCurntYr != null) {
					 * for (int i = 0; i < perfListCurntYr.size(); i++) {
					 * System.out.println("\n entries" +i); pGridModel=new
					 * PerformanceGrid();
					 * 
					 * DBObject dbObj = perfListCurntYr.get(i);
					 * 
					 * if (dbObj.containsKey("pos") && dbObj.get("pos") != null)
					 * pGridModel.setPos((dbObj.get("pos").toString()));
					 * 
					 * if (dbObj.containsKey("origin") && dbObj.get("origin") !=
					 * null)
					 * pGridModel.setOrigin((dbObj.get("origin").toString()));
					 * 
					 * if (dbObj.containsKey("destination") &&
					 * dbObj.get("destination") != null)
					 * pGridModel.setDestination((dbObj.get("destination").
					 * toString()));
					 * 
					 * if (dbObj.containsKey("compartment") &&
					 * dbObj.get("compartment") != null)
					 * pGridModel.setCompartment((dbObj.get("compartment").
					 * toString()));
					 */
					/*
					 * String salesRevenue = null; if
					 * (dbObj.containsKey("revenue") && dbObj.get("revenue") !=
					 * null) salesRevenue = (dbObj.get("revenue").toString());
					 * pGridModel.setSalesRevenue(Float.parseFloat(salesRevenue)
					 * );
					 * 
					 * 
					 * String salesPax = null; if (dbObj.containsKey("pax") &&
					 * dbObj.get("pax") != null)
					 * salesPax=(dbObj.get("pax").toString());
					 * pGridModel.setSalesPax(Float.parseFloat(salesPax));
					 * 
					 * 
					 * 
					 * key = pGridModel.getPos() + pGridModel.getOrigin() +
					 * pGridModel.getDestination() +
					 * pGridModel.getCompartment();
					 * 
					 * pGridModel.setKey(key); pGridList.add(pGridModel);
					 * 
					 * // if (pGridList.size() > 0) {
					 * 
					 * // for (PerformanceGrid lObj : pGridList) {
					 * 
					 * if (apGridMap.containsKey(key)) {
					 * 
					 * if (dbObj.containsKey("sale_pax") &&
					 * dbObj.get("sale_pax") != null) salesPaxObj = new
					 * JSONObject(dbObj.get("sale_pax").toString());
					 * 
					 * float salesPax = 0; if (valueExists(salesPaxObj,
					 * "value")) salesPax =
					 * Float.parseFloat(salesPaxObj.get("value").toString());
					 * manualTrgr.setSalesPax(salesPax +
					 * manualTrgr.getSalesPax());
					 * 
					 * JSONObject flwnPaxObj = null; if
					 * (dbObj.containsKey("flown_pax") && dbObj.get("flown_pax")
					 * != null) flwnPaxObj = new
					 * JSONObject(dbObj.get("flown_pax").toString());
					 * 
					 * float flownPax = 0; if (valueExists(flwnPaxObj, "value"))
					 * flownPax =
					 * Float.parseFloat(salesPaxObj.get("value").toString());
					 * manualTrgr.setSalesPax(flownPax +
					 * manualTrgr.getFlownPax());
					 * 
					 * manualtrgrgMap.put(key, manualTrgr); }
					 * 
					 * 
					 * if (!apGridMap.containsKey(lObj.getKey())) {
					 * apGridModel=new AnalystPerformanceGrid();
					 * apGridModel.setOrigin(lObj.getOrigin());
					 * apGridModel.setDestination(lObj.getDestination());
					 * apGridModel.setCompartment(lObj.getCompartment());
					 * apGridModel.setSalesPax(lObj.getSalesPax());
					 * apGridModel.setSalesRevenue(lObj.getSalesRevenue());
					 * apGridModel.setPos(lObj.getPos());
					 * apGridModel.setKey(lObj.getKey());
					 * 
					 * // apGridList.add(apGridModel);
					 * apGridMap.put(lObj.getKey(),apGridModel); } else { for
					 * (String lKey : apGridMap.keySet()) { if
					 * (lObj.getKey().equals(lKey)) {
					 * apGridModel=apGridMap.get(lKey); } }
					 * 
					 * float totalRevenue = lObj.getSalesRevenue() +
					 * apGridModel.getSalesRevenue(); float totalPax =
					 * lObj.getSalesPax() + apGridModel.getSalesPax();
					 * 
					 * apGridModel.setTotalSalesRevenue(totalRevenue);
					 * apGridModel.setTotalSalesPax(totalPax);
					 * 
					 * // apGridList.add(apGridModel);
					 * apGridMap.put(lObj.getKey(), apGridModel); }
					 * 
					 * }
					 * 
					 * } } }
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return apGridMap;
	}

	@Override
	public Map<String, Object> getAnalystFareMixtypePie(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDao.getAnalystGraph(pRequestModel);
		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);
			JSONArray pos = null;
			if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
				pos = new JSONArray(jsonObj.get("list_of_pos").toString());

			}

			for (int j = 0; j < pos.length(); j++) {
				list.add(pos.getString(j));
			}
		}
		List<DBObject> SalesObj = mAnalystDao.getAnalystFareMix(list);
		JSONArray data1 = new JSONArray(SalesObj);
		List<AnalystPieChartCount> pielist = new ArrayList<AnalystPieChartCount>();
		List<AnalystChannelCountPerc> perclist = new ArrayList<AnalystChannelCountPerc>();
		AnalystPieChartCount lpiechart = null;
		for (int i = 0; i < data1.length(); i++) {
			JSONObject jsonObj = data1.getJSONObject(i);
			lpiechart = new AnalystPieChartCount();
			if (jsonObj.has("_id") && jsonObj.get("_id") != null) {
				lpiechart.setChannel(jsonObj.get("_id").toString());
				lpiechart.setChannelCount(Float.parseFloat(jsonObj.get("count").toString()));

				pielist.add(lpiechart);
			}
		}
		float totalchannelvalue = 0;
		for (AnalystPieChartCount p : pielist) {

			float channelvalue = p.getChannelCount();
			totalchannelvalue += channelvalue;

		}
		AnalystChannelCountPerc lperc = null;
		for (AnalystPieChartCount p : pielist) {
			lperc = new AnalystChannelCountPerc();
			String channel = p.getChannel();
			float channelvalue = p.getChannelCount();
			float channelPerc = (channelvalue / totalchannelvalue) * 100;
			lperc.setChannel(channel);
			lperc.setChannelcount(channelvalue);
			lperc.setChannelCountPerc(channelPerc);
			perclist.add(lperc);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Customer_Segment_Fare_Spread", perclist);
		return map;
	}

	@Override
	public Map<String, Object> getAnalystSqlTime(RequestModel pRequestModel) {

		List<TriggerbasedCount> responseList = new ArrayList<TriggerbasedCount>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<AnalystSqlGraph> sqllist = new ArrayList<AnalystSqlGraph>();
		ArrayList<DBObject> FaresObj = mAnalystDao.getAnalystGraph(pRequestModel);
		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		try {
			if (data != null) {
				for (int i = 0; i < data.length(); i++) {
					JSONObject jsonObj = data.getJSONObject(i);
					JSONArray pos = null;
					if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
						pos = new JSONArray(jsonObj.get("list_of_pos").toString());

					}

					for (int j = 0; j < pos.length(); j++) {
						list.add(pos.getString(j));
					}
				}
			}
			List<DBObject> SalesObj = mAnalystDao.getAnalystSql(list, pRequestModel);
			List<DBObject> TriggerCountObj = mAnalystDao.getTriggerStatusCount(list);
			JSONArray data1 = new JSONArray(TriggerCountObj);
			List<TriggerCount> pielist = new ArrayList<TriggerCount>();

			List<SqlGraph> countList = new ArrayList<SqlGraph>();
			if (SalesObj != null) {
				JSONArray triggerdata = new JSONArray(SalesObj);
				AnalystSqlGraph lsql = null;

				for (int i = 0; i < triggerdata.length(); i++) {
					JSONObject jsonObj = triggerdata.getJSONObject(i);
					lsql = new AnalystSqlGraph();

					String pos = "-";
					String key = "-";
					if (jsonObj.has("pos") && jsonObj.get("pos") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("pos").toString())) {
						pos = jsonObj.get("pos").toString();
						lsql.setPos(pos);
					}
					String compartment = "-";
					if (jsonObj.has("compartment") && jsonObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("compartment").toString())) {
						compartment = jsonObj.get("compartment").toString();
						lsql.setCompartment(compartment);
					}
					String od = "-";
					if (jsonObj.has("od") && jsonObj.get("od") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("od").toString())) {
						od = jsonObj.get("od").toString();
						lsql.setOd(od);
					}
					String status = "-";
					if (jsonObj.has("status") && jsonObj.get("status") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("status").toString())) {
						status = jsonObj.get("status").toString();
						lsql.setStatus(status);
					}
					key = pos + od + compartment;
					System.out.println(key);
					lsql.setKey(key);
					if (jsonObj.has("triggering_event_time") && jsonObj.get("triggering_event_time") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("triggering_event_time").toString())) {
						String startTime = (jsonObj.get("triggering_event_time").toString());
						lsql.setTriggereventTime(startTime);

					}
					if (jsonObj.has("triggering_event_date") && jsonObj.get("triggering_event_date") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("triggering_event_date").toString())) {
						String startDate = (jsonObj.get("triggering_event_date").toString());
						lsql.setTriggereventDate(startDate);

					}
					if (jsonObj.has("action_time") && jsonObj.get("action_time") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("action_time").toString())) {
						String endTime = (jsonObj.get("action_time").toString());
						lsql.setActiontime(endTime);
					}

					if (jsonObj.has("action_date") && jsonObj.get("action_date") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("action_date").toString())) {
						String endDate = (jsonObj.get("action_date").toString());
						lsql.setActionDate(endDate);
					}
					sqllist.add(lsql);

					// System.out.println(sqllist);
				}
			}
			Map<String, TriggerbasedCount> lTempMap = new HashMap<String, TriggerbasedCount>();
			TriggerbasedCount lc = null;
			int count = 1;
			for (AnalystSqlGraph lModel : sqllist) {
				if (!lTempMap.containsKey(lModel.getKey())) {
					lc = new TriggerbasedCount();
					lc.setKey(lModel.getKey());
					lc.setOd(lModel.getOd());
					lc.setPos(lModel.getPos());
					lc.setCompartment(lModel.getCompartment());
					lc.setTriggereventTime(lModel.getTriggereventTime());
					lc.setTriggereventDate(lModel.getTriggereventDate());
					lc.setActiondate(lModel.getActionDate());
					lc.setActiontime(lModel.getActiontime());
					int counter = 0;
					String status = (lModel.getStatus());
					lc.setStatus(status);
					if (status != null) {
						if (!status.equalsIgnoreCase("Pending")) {
							counter = +counter + 1;
							lc.setCount(counter);
						}
					}
					String date = null;
					if (lModel.getTriggereventTime() != null) {
						String startTime = lModel.getTriggereventTime();
						String startDate = lModel.getTriggereventDate();
						String pattern = startDate + " " + startTime + ":" + 0 + 0;
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						date = simpleDateFormat.format(new Date());
						System.out.println(date);
					}
					String enddate = null;
					if (lModel.getActiontime() != null && lModel.getActionDate() != null) {
						String endTime = lModel.getActiontime();
						String endDate = lModel.getActionDate();
						String pattern = endDate + " " + endTime + ":" + 0 + 0;
						SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern);
						enddate = simpleDateFormat1.format(new Date());
						System.out.println(enddate);
					}
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					Date d1 = null;
					Date d2 = null;

					try {
						d1 = format.parse(date);
						if (enddate != null) {
							d2 = format.parse(enddate);
						}
						long diff = 0;
						// in milliseconds

						if (d2 != null && d2.getTime() > 0) {
							diff = Math.abs(d2.getTime() - d1.getTime());

							long diffSeconds = diff / 1000;
							long diffMinutes = (diff / 1000) / 60;
							long diffHours = diff / (60 * 60 * 1000);
							long diffDays = diff / (24 * 60 * 60 * 1000);
							lc.setDiffSeconds(diffSeconds);
							lc.setDiffMinutes(diffMinutes);
							lc.setDiffHours(diffHours);
							lc.setDiffDays(diffDays);
							System.out.print(diffDays + " days, ");
							System.out.print(diffHours + " hours, ");
							System.out.print(diffMinutes + " minutes, ");
							System.out.print(diffSeconds + " seconds.");
						}

					} catch (ParseException e) {
						e.printStackTrace();
					}

					lTempMap.put(lModel.getKey(), lc);
				} else {

					for (String lKey : lTempMap.keySet()) {
						if (lModel.getKey().equals(lKey)) {
							lc = lTempMap.get(lKey);
						}
					}
					String status = (lModel.getStatus());
					lc.setStatus(status);
					if (status != null) {
						if (!status.equalsIgnoreCase("Pending")) {
							count = lc.getCount() + 1;
							lc.setCount(count);
						}
					}
					String date = null;
					if (lModel.getTriggereventTime() != null) {

						String startTime = lModel.getTriggereventTime();
						String startDate = lModel.getTriggereventDate();
						String pattern = startDate + " " + startTime + ":" + 0 + 0;
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						date = simpleDateFormat.format(new Date());
						System.out.println(date);

					}
					String enddate = null;
					if (lModel.getActiontime() != null && lModel.getActionDate() != null) {
						String endTime = lModel.getActiontime();

						String endDate = lModel.getActionDate();
						String pattern = endDate + " " + endTime + ":" + 0 + 0;

						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						enddate = simpleDateFormat.format(new Date());
						System.out.println(enddate);
					}
					SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

					Date d1 = null;
					Date d2 = null;

					try {
						d1 = format.parse(date);
						if (enddate != null) {
							d2 = format.parse(enddate);
						}
						// in milliseconds
						if (d2 != null && d2.getTime() > 0) {
							long diff = Math.abs(d2.getTime() - d1.getTime());

							long diffSeconds = diff / (1000);
							long totaldiffseconds = diffSeconds + lc.getDiffSeconds();
							long diffMinutes = (diff / 1000) / 60;
							long totaldiffMinutes = diffMinutes + lc.getDiffMinutes();
							long diffHours = diff / (60 * 60 * 1000);
							long totaldiffHours = diffHours + lc.getDiffHours();
							long diffDays = diff / (24 * 60 * 60 * 1000);
							long totaldiffDays = diffDays + lc.getDiffDays();
							lc.setDiffDays(totaldiffDays);
							lc.setDiffHours(totaldiffHours);
							lc.setDiffMinutes(totaldiffMinutes);
							lc.setDiffSeconds(totaldiffseconds);

							System.out.print(diffDays + " days, ");
							System.out.print(diffHours + " hours, ");
							System.out.print(diffMinutes + " minutes, ");
							System.out.print(diffSeconds + " seconds.");
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}

					lTempMap.put(lModel.getKey(), lc);
				}
			}
			for (Entry<String, TriggerbasedCount> mapElem : lTempMap.entrySet()) {
				responseList.add(mapElem.getValue());
			}

			TriggerCount lcount = null;
			for (int i = 0; i < data1.length(); i++) {
				JSONObject jsonObj = data1.getJSONObject(i);
				lcount = new TriggerCount();
				if (jsonObj.has("_id") && jsonObj.get("_id") != null) {
					lcount.setStatus(jsonObj.get("_id").toString());
					lcount.setCount(Float.parseFloat(jsonObj.get("count").toString()));

					pielist.add(lcount);
				}
			}
			float totalcount = 0;
			for (TriggerCount p : pielist) {

				float channelvalue = p.getCount();
				totalcount += channelvalue;

			}
			SqlGraph lperc = null;
			for (TriggerbasedCount trgrBasedCount : responseList) {
				lperc = new SqlGraph();
				long totalhour = lc.getDiffHours();
				float timetaken = (totalhour / totalcount);
				lperc.setTimetaken(timetaken);
				int indiviualCount = trgrBasedCount.getCount();
				float workcompletionperc = (indiviualCount / totalcount) * 100;
				lperc.setWorkcompletionperc(workcompletionperc);
				countList.add(lperc);
			}

			map.put("sql", countList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> getAnalystFareType(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDao.getAnalystGraph(pRequestModel);
		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		if (data != null) {
			for (int i = 0; i < data.length(); i++) {
				JSONObject jsonObj = data.getJSONObject(i);
				JSONArray pos = null;
				if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
					pos = new JSONArray(jsonObj.get("list_of_pos").toString());

				}

				for (int j = 0; j < pos.length(); j++) {
					list.add(pos.getString(j));
				}
			}
		}

		List<DBObject> analyst = mAnalystDao.getAnalystFareType(list, pRequestModel);
		String lastYrFromDate = pRequestModel.getFromDate();
		String lastYrToDate = pRequestModel.getToDate();

		pRequestModel.setFromDate(lastYrFromDate);
		pRequestModel.setToDate(lastYrToDate);
		List<DBObject> analyst_1 = mAnalystDao.getAnalystFareType(list, pRequestModel);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FareType", analyst);
		return null;
	}

}
