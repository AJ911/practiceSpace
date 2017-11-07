package com.flynava.jupiter.daoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.FaresDashboardDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.util.Constants;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@Repository
public class FaresDashboardDaoImpl implements FaresDashboardDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	@Override
	public List<DBObject> getZones(RequestModel pRequestModel) {

		ArrayList<String> zoneList = null;
		if (pRequestModel.getZoneArray() != null && pRequestModel.getZoneArray().length > 0) {
			zoneList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getZoneArray().length; i++) {
				zoneList.add(pRequestModel.getZoneArray()[i]);
			}
		}

		DBObject query = null;
		if (zoneList != null) {
			query = new QueryBuilder().start().or(new QueryBuilder().start().put("zone_code").in(zoneList).get()).get();
		}

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection coll = db.getCollection("JUP_DB_ATPCO_Zone_Master");
		BasicDBObject document = new BasicDBObject();
		document.put("$and", query);
		DBObject match = new BasicDBObject("$match", query);
		DBObject project = new BasicDBObject("$project", new BasicDBObject("location", 1));
		List<DBObject> pipeline = Arrays.asList(match, project);
		AggregationOutput output = (coll).aggregate(match, project);

		coll.aggregate(pipeline);

		ArrayList<DBObject> resultList = new ArrayList<DBObject>();
		for (DBObject result : output.results()) {
			resultList.add(result);
		}

		return resultList;

	}

	@Override
	public List<DBObject> getFares(RequestModel pRequestModel) {
		ArrayList<String> carrierList = null;
		if (pRequestModel.getCarrierArray() != null && pRequestModel.getCarrierArray().length > 0) {
			carrierList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getCarrierArray().length; i++) {
				carrierList.add(pRequestModel.getCarrierArray()[i]);
			}
		}

		ArrayList<String> tarrifList = null;
		if (pRequestModel.getTarrifArray() != null && pRequestModel.getTarrifArray().length > 0) {
			tarrifList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getTarrifArray().length; i++) {
				tarrifList.add(pRequestModel.getTarrifArray()[i]);
			}
		}

		// String tariff = pRequestModel.getTarrif();
		ArrayList<String> ruleIDList = null;
		if (pRequestModel.getRuleIDArray() != null && pRequestModel.getRuleIDArray().length > 0) {
			ruleIDList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getRuleIDArray().length; i++) {
				ruleIDList.add(pRequestModel.getRuleIDArray()[i]);
			}
		}
		ArrayList<String> odList = null;
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {
			odList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				odList.add(pRequestModel.getOdArray()[i]);
			}
		}
		// TODO to be added in query once it come in db
		ArrayList<String> clusterList = null;
		if (pRequestModel.getClusterArray() != null && pRequestModel.getClusterArray().length > 0) {
			clusterList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getClusterArray().length; i++) {
				clusterList.add(pRequestModel.getClusterArray()[i]);
			}
		}
		ArrayList<String> zoneList = new ArrayList<String>();
		if (pRequestModel.getZoneArray() != null) {
			List<DBObject> zoneObj = getZones(pRequestModel);

			JSONArray data = new JSONArray(zoneObj);

			for (int i = 0; i < data.length(); i++) {
				JSONObject jsonObj = data.getJSONObject(i);
				JSONArray zone = null;
				if (jsonObj.has("location") && jsonObj.get("location") != null
						&& !"null".equalsIgnoreCase(jsonObj.get("location").toString())) {
					zone = new JSONArray(jsonObj.get("location").toString());

				}

				for (int j = 0; j < zone.length(); j++) {
					zoneList.add(zone.getString(j));
				}
			}
		}
		ArrayList<String> compartmentList = null;
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			compartmentList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		}
		ArrayList<String> rbdList = null;
		if (pRequestModel.getRbdArray() != null && pRequestModel.getRbdArray().length > 0) {
			rbdList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getRbdArray().length; i++) {
				rbdList.add(pRequestModel.getRbdArray()[i]);
			}
		}

		ArrayList<Integer> onewayreturnList = null;
		if (pRequestModel.getOneway_returnArray() != null && pRequestModel.getOneway_returnArray().length > 0) {
			onewayreturnList = new ArrayList<Integer>();
			for (int i = 0; i < pRequestModel.getOneway_returnArray().length; i++) {
				onewayreturnList.add(pRequestModel.getOneway_returnArray()[i]);
			}
		}

		// int oneway_return = pRequestModel.getOneway_returnArray();
		ArrayList<String> filingCurrencyList = null;
		if (pRequestModel.getFilingcurrencyArray() != null && pRequestModel.getFilingcurrencyArray().length > 0) {
			filingCurrencyList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getFilingcurrencyArray().length; i++) {
				filingCurrencyList.add(pRequestModel.getFilingcurrencyArray()[i]);
			}
		}
		ArrayList<String> amountRangeList = null;
		if (pRequestModel.getAmountrangeArray() != null && pRequestModel.getAmountrangeArray().length > 0) {
			amountRangeList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getAmountrangeArray().length; i++) {
				amountRangeList.add(pRequestModel.getAmountrangeArray()[i]);
			}
		}
		ArrayList<String> channelList = null;
		if (pRequestModel.getChannelArray() != null && pRequestModel.getChannelArray().length > 0) {
			channelList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getChannelArray().length; i++) {
				channelList.add(pRequestModel.getChannelArray()[i]);
			}
		}
		// boolean publicprivate = pRequestModel.getFare_flag();

		ArrayList<Boolean> publicprivateList = null;
		if (pRequestModel.getFare_flag() != null && pRequestModel.getFare_flag().length > 0) {
			publicprivateList = new ArrayList<Boolean>();
			for (int i = 0; i < pRequestModel.getFare_flag().length; i++) {
				publicprivateList.add(pRequestModel.getFare_flag()[i]);
			}
		}

		ArrayList<String> corporateList = null;
		if (pRequestModel.getCorporateArray() != null && pRequestModel.getCorporateArray().length > 0) {
			corporateList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getCorporateArray().length; i++) {
				corporateList.add(pRequestModel.getCorporateArray()[i]);
			}
		}
		ArrayList<String> fareBrandList = null;
		if (pRequestModel.getFarebrandArray() != null && pRequestModel.getFarebrandArray().length > 0) {
			fareBrandList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getFarebrandArray().length; i++) {
				fareBrandList.add(pRequestModel.getFarebrandArray()[i]);
			}
		}
		ArrayList<String> fareDesignatorList = null;
		if (pRequestModel.getFareDesignatorArray() != null && pRequestModel.getFareDesignatorArray().length > 0) {
			fareDesignatorList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getFareDesignatorArray().length; i++) {
				fareDesignatorList.add(pRequestModel.getFareDesignatorArray()[i]);
			}
		}
		ArrayList<String> footnoteList = null;
		if (pRequestModel.getFootnoteArray() != null && pRequestModel.getFootnoteArray().length > 0) {
			footnoteList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getFootnoteArray().length; i++) {
				footnoteList.add(pRequestModel.getFootnoteArray()[i]);
			}
		}
		ArrayList<Integer> routeList = null;
		if (pRequestModel.getRouteArray() != null && pRequestModel.getRouteArray().length > 0) {
			routeList = new ArrayList<Integer>();
			for (int i = 0; i < pRequestModel.getRouteArray().length; i++) {
				routeList.add(pRequestModel.getRouteArray()[i]);
			}
		}
		ArrayList<String> wpnameList = null;
		if (pRequestModel.getWpnameArray() != null && pRequestModel.getWpnameArray().length > 0) {
			wpnameList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getWpnameArray().length; i++) {
				wpnameList.add(pRequestModel.getWpnameArray()[i]);
			}
		}
		/*
		 * ArrayList<String> gfsList = null; if (pRequestModel.getGfsArray() !=
		 * null && pRequestModel.getGfsArray().length > 0) { gfsList = new
		 * ArrayList<String>(); for (int i = 0; i <
		 * pRequestModel.getGfsArray().length; i++) {
		 * gfsList.add(pRequestModel.getGfsArray()[i]); } }
		 */

		String gfs = pRequestModel.getGfs();
		String effectiveFrom = null;
		String effectiveTo = null;
		effectiveFrom = pRequestModel.getEffectivefrom();
		effectiveTo = pRequestModel.getEffectiveto();
		String salesfrom = null;
		String salesTo = null;
		salesfrom = pRequestModel.getSalesfrom();
		salesTo = pRequestModel.getSalesto();
		String travelfrom = null;
		String travelTo = null;
		travelfrom = pRequestModel.getTravelfrom();
		travelTo = pRequestModel.getTravelto();
		String travelCompleteList = pRequestModel.getTravelComplete();
		String season = null;
		String[] season1 = null;
		String seasonfrom = null;
		String seasonTo = null;
		if (pRequestModel.getSeasonfirst_last() != null && pRequestModel.getSeasonfirst_last().length() > 0) {
			season = pRequestModel.getSeasonfirst_last().toLowerCase();
			season1 = season.split(",");
			seasonfrom = season1[0];
			seasonTo = season1[1];

		}
		ArrayList<String> paxtypeList = null;
		if (pRequestModel.getPaxtypeArray() != null && pRequestModel.getPaxtypeArray().length > 0) {
			paxtypeList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getPaxtypeArray().length; i++) {
				paxtypeList.add(pRequestModel.getPaxtypeArray()[i]);
			}
		}
		ArrayList<String> advancePurchaseList = null;
		if (pRequestModel.getAdvancepurchaseArray() != null && pRequestModel.getAdvancepurchaseArray().length > 0) {
			advancePurchaseList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getAdvancepurchaseArray().length; i++) {
				advancePurchaseList.add(pRequestModel.getAdvancepurchaseArray()[i]);
			}
		}
		ArrayList<String> minStayList = null;
		if (pRequestModel.getMinStayArray() != null && pRequestModel.getMinStayArray().length > 0) {
			minStayList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getMinStayArray().length; i++) {
				minStayList.add(pRequestModel.getMinStayArray()[i]);
			}
		}
		ArrayList<String> maxStayList = null;
		if (pRequestModel.getMaxStayArray() != null && pRequestModel.getMaxStayArray().length > 0) {
			maxStayList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getMaxStayArray().length; i++) {
				maxStayList.add(pRequestModel.getMaxStayArray()[i]);
			}
		}
		String dow = null;
		String[] dow1 = null;
		String dowin = null;
		String dowout = null;
		if (pRequestModel.getDowin_out() != null && pRequestModel.getDowin_out().length() > 0) {
			dow = pRequestModel.getDowin_out().toString();
			dow1 = season.split(",");
			dowin = season1[0];
			dowout = season1[1];

		}

		int maxfareList = 0;
		if (pRequestModel.getMaxPriceArray() != null && pRequestModel.getMaxPriceArray().length > 0) {
			for (int i = 0; i < pRequestModel.getMaxPriceArray().length; i++) {
				maxfareList = Integer.parseInt(pRequestModel.getMaxPriceArray()[i].trim());
			}
		}
		int minPriceList = 0;
		if (pRequestModel.getMinPriceArray() != null && pRequestModel.getMinPriceArray().length > 0) {

			for (int i = 0; i < pRequestModel.getMinPriceArray().length; i++) {
				minPriceList = Integer.parseInt(pRequestModel.getMinPriceArray()[i].trim());
			}
		}

		DBObject query = null;
		if (travelTo != null) {
			query = new QueryBuilder().start()
					.and(new QueryBuilder().start().put("dep_date_to").lessThanEquals(travelTo).get()).get();
		} else {
			query = new QueryBuilder().start().and(new QueryBuilder().start().get()).get();
		}
		DBObject query1 = null;

		if (carrierList != null) {

			query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("carrier").in(carrierList).get())
					.get();
		} else {
			query1 = new QueryBuilder().start().and(query, new QueryBuilder().start().get()).get();
		}
		DBObject query2 = null;
		if (tarrifList != null) {
			query2 = new QueryBuilder().start()
					.and(query1, new QueryBuilder().start().put("tariff_code").in(tarrifList).get()).get();
		} else {
			query2 = new QueryBuilder().start().and(query1, new QueryBuilder().start().get()).get();
		}
		DBObject query3 = null;
		if (ruleIDList != null) {
			query3 = new QueryBuilder().start()
					.and(query2, new QueryBuilder().start().put("Rule_id").in(ruleIDList).get()).get();
		} else {
			query3 = new QueryBuilder().start().and(query2, new QueryBuilder().start().get()).get();
		}
		DBObject query4 = null;
		if (odList != null) {
			query4 = new QueryBuilder().start().and(query3, new QueryBuilder().start().put("OD").in(odList).get())
					.get();
		} else {
			query4 = new QueryBuilder().start().and(query3, new QueryBuilder().start().get()).get();
		}
		DBObject query5 = null;
		if (compartmentList != null) {
			query5 = new QueryBuilder().start()
					.and(query4, new QueryBuilder().start().put("compartment").in(compartmentList).get()).get();
		} else {
			query5 = new QueryBuilder().start().and(query4, new QueryBuilder().start().get()).get();
		}
		DBObject query6 = null;
		if (rbdList != null) {
			query6 = new QueryBuilder().start().and(query5, new QueryBuilder().start().put("RBD").in(rbdList).get())
					.get();
		} else {
			query6 = new QueryBuilder().start().and(query5, new QueryBuilder().start().get()).get();
		}
		DBObject query7 = null;
		if (onewayreturnList != null) {
			query7 = new QueryBuilder().start()
					.and(query6, new QueryBuilder().start().put("oneway_return").in(onewayreturnList).get()).get();
		} else {
			query7 = new QueryBuilder().start().and(query6, new QueryBuilder().start().get()).get();
		}
		DBObject query8 = null;
		if (filingCurrencyList != null) {
			query8 = new QueryBuilder().start()
					.and(query7, new QueryBuilder().start().put("currency").in(filingCurrencyList).get()).get();
		} else {
			query8 = new QueryBuilder().start().and(query7, new QueryBuilder().start().get()).get();
		}
		DBObject query9 = null;
		if (channelList != null) {
			query9 = new QueryBuilder().start()
					.and(query8, new QueryBuilder().start().put("channel").in(channelList).get()).get();
		} else {
			query9 = new QueryBuilder().start().and(query8, new QueryBuilder().start().get()).get();
		}
		DBObject query10 = null;
		if (fareBrandList != null) {
			query10 = new QueryBuilder().start()
					.and(query9, new QueryBuilder().start().put("fare_brand").in(fareBrandList).get()).get();
		} else {
			query10 = new QueryBuilder().start().and(query9, new QueryBuilder().start().get()).get();
		}
		DBObject query11 = null;
		if (footnoteList != null) {
			query11 = new QueryBuilder().start()
					.and(query10, new QueryBuilder().start().put("footnote").in(footnoteList).get()).get();
		} else {
			query11 = new QueryBuilder().start().and(query10, new QueryBuilder().start().get()).get();
		}
		DBObject query12 = null;
		if (routeList != null) {
			query12 = new QueryBuilder().start().and(query11, new QueryBuilder().start().put("rtg").in(routeList).get())
					.get();
		} else {
			query12 = new QueryBuilder().start().and(query11, new QueryBuilder().start().get()).get();
		}
		DBObject query13 = null;
		if (gfs != null) {
			query13 = new QueryBuilder().start().and(query12, new QueryBuilder().start().put("gfs").is(gfs).get())
					.get();
		} else {
			query13 = new QueryBuilder().start().and(query12, new QueryBuilder().start().get()).get();
		}
		DBObject query14 = null;
		if (travelCompleteList != null) {
			query14 = new QueryBuilder().start()
					.and(query13, new QueryBuilder().start().put("travel_complete").in(travelCompleteList).get()).get();
		} else {
			query14 = new QueryBuilder().start().and(query13, new QueryBuilder().start().get()).get();
		}
		DBObject query15 = null;
		if (effectiveFrom != null) {
			query15 = new QueryBuilder().start()
					.and(query14,
							new QueryBuilder().start().put("effective_from").greaterThanEquals(effectiveFrom).get())
					.get();
		} else {
			query15 = new QueryBuilder().start().and(query14, new QueryBuilder().start().get()).get();
		}
		DBObject query16 = null;
		if (effectiveTo != null) {
			query16 = new QueryBuilder().start()
					.and(query15, new QueryBuilder().start().put("effective_to").lessThanEquals(effectiveTo).get())
					.get();
		} else {
			query16 = new QueryBuilder().start().and(query15, new QueryBuilder().start().get()).get();
		}
		DBObject query17 = null;
		if (salesfrom != null) {
			query17 = new QueryBuilder().start()
					.and(query16, new QueryBuilder().start().put("sale_date_from").greaterThanEquals(salesfrom).get())
					.get();
		} else {
			query17 = new QueryBuilder().start().and(query16, new QueryBuilder().start().get()).get();
		}
		DBObject query18 = null;
		if (salesTo != null) {
			query18 = new QueryBuilder().start()
					.and(query17, new QueryBuilder().start().put("sale_date_to").lessThanEquals(salesTo).get()).get();
		} else {
			query18 = new QueryBuilder().start().and(query17, new QueryBuilder().start().get()).get();
		}
		DBObject query19 = null;
		if (travelfrom != null) {
			query19 = new QueryBuilder().start()
					.and(query18, new QueryBuilder().start().put("dep_date_from").greaterThanEquals(travelfrom).get())
					.get();
		} else {
			query19 = new QueryBuilder().start().and(query18, new QueryBuilder().start().get()).get();
		}
		DBObject query20 = null;
		if (maxfareList > -1 && minPriceList > -1) {
			query20 = new QueryBuilder().start().and(query19, new QueryBuilder().start().put("fare")
					.greaterThanEquals(minPriceList).lessThanEquals(maxfareList).get()).get();
		} else {
			query20 = new QueryBuilder().start().and(query19, new QueryBuilder().start().get()).get();
		}
		DBObject query21 = null;
		if (clusterList != null) {
			query21 = new QueryBuilder().start()
					.and(query20, new QueryBuilder().start().put("Cluster").in(clusterList).get()).get();
		} else {
			query21 = new QueryBuilder().start().and(query20, new QueryBuilder().start().get()).get();
		}
		DBObject query22 = null;
		// if (publicprivate) {
		query22 = new QueryBuilder().start()
				.and(query21, new QueryBuilder().start().put("private_fare").in(publicprivateList).get()).get();
		DBObject query23 = null;
		if (pRequestModel.getZoneArray() != null) {
			query23 = new QueryBuilder().start()
					.and(query20,
							new QueryBuilder().start().put("origin").in(zoneList).put("destination").in(zoneList).get())
					.get();
		} else {
			query23 = new QueryBuilder().start().and(query20, new QueryBuilder().start().get()).get();
		}

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection coll = db.getCollection("JUP_DB_ATPCO_Fares");
		BasicDBObject document = new BasicDBObject();
		document.put("$and", query23);
		DBObject match = new BasicDBObject("$match", query23);
		DBObject project = new BasicDBObject("$project",
				new BasicDBObject("carrier", 1).append("tariff_code", 1).append("origin", 1).append("destination", 1)
						.append("Rule_id", 1).append("fare_basis", 1).append("RBD", 1).append("oneway_return", 1)
						.append("rtg", 1).append("footnote", 1).append("currency", 1).append("fare", 1).append("YQ", 1)
						.append("YR", 1).append("taxes", 1).append("surcharge", 1).append("total_fare", 1)
						.append("effective_from", 1).append("effective_to", 1).append("dep_date_from", 1)
						.append("dep_date_to", 1).append("TravelComplete", 1).append("sale_date_from", 1)
						.append("sale_date_to", 1).append("revenue", 1).append("pax", 1));
		List<DBObject> pipeline = Arrays.asList(match, project);
		AggregationOutput output = (coll).aggregate(match, project);

		coll.aggregate(pipeline);

		ArrayList<DBObject> resultList = new ArrayList<DBObject>();
		for (DBObject result : output.results()) {
			resultList.add(result);
		}

		return resultList;

	}

	@Override
	public List<DBObject> getMarketshare(RequestModel pRequestModel) {
		String fromdate = pRequestModel.getFromDate();
		String todate = pRequestModel.getToDate();

		ArrayList<String> odList = null;
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {
			odList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				odList.add(pRequestModel.getOdArray()[i]);
			}
		}

		List<DBObject> dbObjList = new ArrayList<DBObject>();
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBObject query = new QueryBuilder().start()
				.and(new QueryBuilder().start().put("pos").in(getAnalystPos(pRequestModel)).get()).get();

		DBObject query1 = null;

		if (odList != null) {

			query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("od").greaterThanEquals(fromdate)
					.put("").lessThanEquals(todate).get()).get();
		} else {
			query1 = new QueryBuilder().start().and(query, new QueryBuilder().start().get()).get();
		}
		DBObject query2 = null;

		if (odList != null) {

			query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("od").in(odList).get()).get();
		} else {
			query1 = new QueryBuilder().start().and(query, new QueryBuilder().start().get()).get();
		}

		DBCollection colle = db.getCollection("JUP_DB_Market_Share");
		DBObject match = new BasicDBObject("$match", query1);

		DBObject project = new BasicDBObject("$project",
				new BasicDBObject("MarketingCarrier1", 1).append("market_size", 1).append("share", 1).append("pax", 1));
		List<DBObject> pipeline = Arrays.asList(match, project);
		AggregationOutput agout = (colle).aggregate(match, project);

		Iterator<DBObject> results = agout.results().iterator();

		DBObject obj = null;
		while (results.hasNext()) {
			obj = results.next();
			dbObjList.add(obj);
			System.out.println(dbObjList);

		}
		return dbObjList;

	}

	@Override
	public List<DBObject> getDocument(RequestModel pRequestModel, String collName) {

		String startdate = pRequestModel.getEffectivefrom();
		String enddate = pRequestModel.getEffectiveto();

		ArrayList<String> odList = null;
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {
			odList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				odList.add(pRequestModel.getOdArray()[i]);
			}
		}

		ArrayList<String> compartmentList = null;
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			compartmentList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		}

		ArrayList<String> rbdList = null;
		if (pRequestModel.getRbdArray() != null && pRequestModel.getRbdArray().length > 0) {
			rbdList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getRbdArray().length; i++) {
				rbdList.add(pRequestModel.getRbdArray()[i]);
			}
		}

		ArrayList<String> filingCurrencyList = null;
		if (pRequestModel.getFilingcurrencyArray() != null && pRequestModel.getFilingcurrencyArray().length > 0) {
			filingCurrencyList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getFilingcurrencyArray().length; i++) {
				filingCurrencyList.add(pRequestModel.getFilingcurrencyArray()[i]);
			}
		}
		DBObject query = null;
		if (odList != null) {
			query = new QueryBuilder().start().and(new QueryBuilder().start().put("od").in(odList).get()).get();
		} else {
			query = new QueryBuilder().start().and(new QueryBuilder().start().get()).get();
		}

		DBObject query1 = null;
		if (compartmentList != null) {
			query1 = new QueryBuilder().start()
					.and(query, new QueryBuilder().start().put("compartment").in(compartmentList).get()).get();
		} else {
			query1 = new QueryBuilder().start().and(query, new QueryBuilder().start().get()).get();
		}
		DBObject query2 = null;
		if (rbdList != null) {
			query2 = new QueryBuilder().start().and(query1, new QueryBuilder().start().put("RBD").in(rbdList).get())
					.get();
		} else {
			query2 = new QueryBuilder().start().and(query1, new QueryBuilder().start().get()).get();
		}
		DBObject query3 = null;
		if (filingCurrencyList != null) {
			query3 = new QueryBuilder().start()
					.and(query2, new QueryBuilder().start().put("currency").in(filingCurrencyList).get()).get();
		} else {
			query3 = new QueryBuilder().start().and(query2, new QueryBuilder().start().get()).get();
		}
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection coll = db.getCollection(collName);

		DBCursor findvalue = null;
		List<DBObject> dbObjList = new ArrayList<DBObject>();
		DBCursor cursor = null;
		if (coll != null)
			cursor = coll.find(query3);

		if (cursor != null) {
			while (cursor.hasNext()) {

				dbObjList.add(cursor.next());

			}
		}

		return dbObjList;
	}

	@Override
	public ArrayList<DBObject> getAnalystPos(RequestModel pRequestModel) {

		String user = null;
		if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty())
			user = pRequestModel.getUser().toString();

		DBObject query = new QueryBuilder().start().and(new QueryBuilder().start().put("full_name").is(user).get())
				.get();

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection coll = db.getCollection("JUP_DB_User");
		BasicDBObject document = new BasicDBObject();
		document.put("$and", query);
		DBObject match = new BasicDBObject("$match", query);
		DBObject project = new BasicDBObject("$project", new BasicDBObject("list_of_pos", 1));

		List<DBObject> pipeline = Arrays.asList(match, project);
		AggregationOutput output = (coll).aggregate(match, project);

		coll.aggregate(pipeline);

		ArrayList<DBObject> resultList = new ArrayList<DBObject>();
		for (DBObject result : output.results()) {
			System.out.println(result);

			resultList.add(result);
		}
		return resultList;
	}
}
