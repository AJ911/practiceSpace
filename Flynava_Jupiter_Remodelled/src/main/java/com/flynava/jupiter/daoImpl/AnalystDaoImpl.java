package com.flynava.jupiter.daoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.AnalystDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Utility;
import com.mongodb.AggregationOptions;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@Repository
public class AnalystDaoImpl implements AnalystDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	@Override
	public ArrayList<DBObject> getAnalystEvents(RequestModel pRequestModel) {

		String user = null;
		if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty())
			user = pRequestModel.getUser();

		String startDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			startDate = pRequestModel.getFromDate();

		String endDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			endDate = pRequestModel.getToDate();

		ArrayList<String> holidayList = null;
		if (pRequestModel.getHolidaynameArray() != null && pRequestModel.getHolidaynameArray().length > 0) {
			holidayList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getHolidaynameArray().length; i++) {
				holidayList.add(pRequestModel.getHolidaynameArray()[i]);
			}
		}
		ArrayList<String> marketList = null;
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {
			marketList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				marketList.add(pRequestModel.getOdArray()[i]);
			}
		}
		ArrayList<String> regionList = null;
		if (pRequestModel.getRegionArray() != null && pRequestModel.getRegionArray().length > 0) {
			regionList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getRegionArray().length; i++) {
				regionList.add(pRequestModel.getRegionArray()[i]);
			}
		}
		ArrayList<String> clusterList = null;
		if (pRequestModel.getClusterArray() != null && pRequestModel.getClusterArray().length > 0) {
			clusterList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getClusterArray().length; i++) {
				clusterList.add(pRequestModel.getClusterArray()[i]);
			}
		}

		DBObject query = new QueryBuilder().start().and(new QueryBuilder().start().put("Analyst").is(user).get()).get();
		DBObject query1 = null;
		if (startDate != null && endDate != null) {

			query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("2017_Start_Date")
					.greaterThanEquals(startDate).put("2017_End_Date").lessThanEquals(endDate).get()).get();

		} else {
			query1 = new QueryBuilder().start().and(query, new QueryBuilder().start().get()).get();

		}
		DBObject query2 = null;
		if (holidayList != null) {

			query2 = new QueryBuilder().start()
					.and(query1, new QueryBuilder().start().put("Holiday_Name").in(holidayList).get()).get();

		} else {
			query2 = new QueryBuilder().start().and(query1, new QueryBuilder().start().get()).get();

		}
		DBObject query3 = null;
		if (marketList != null) {

			query3 = new QueryBuilder().start()
					.and(query2, new QueryBuilder().start().put("Market").in(marketList).get()).get();

		} else {
			query3 = new QueryBuilder().start().and(query2, new QueryBuilder().start().get()).get();

		}
		DBObject query4 = null;
		if (marketList != null) {

			query4 = new QueryBuilder().start()
					.and(query3, new QueryBuilder().start().put("Region").in(regionList).get()).get();

		} else {
			query4 = new QueryBuilder().start().and(query3, new QueryBuilder().start().get()).get();

		}
		DBObject query5 = null;
		if (marketList != null) {

			query5 = new QueryBuilder().start()
					.and(query4, new QueryBuilder().start().put("Cluster").in(clusterList).get()).get();

		} else {
			query5 = new QueryBuilder().start().and(query4, new QueryBuilder().start().get()).get();

		}

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection coll = db.getCollection("JUP_DB_Pricing_Calendar");
		BasicDBObject document = new BasicDBObject();
		document.put("$and", query5);
		DBObject match = new BasicDBObject("$match", query5);
		DBObject project = new BasicDBObject("$project", new BasicDBObject("Analyst", 1).append("Holiday_Name", 1)
				.append("Market", 1).append("compartment", 1).append("2017_Start_Date", 1).append("2017_End_Date", 1));
		List<DBObject> pipeline = Arrays.asList(match, project);
		AggregationOutput output = (coll).aggregate(match, project);

		coll.aggregate(pipeline);

		ArrayList<DBObject> resultList = new ArrayList<DBObject>();
		for (DBObject result : output.results()) {
			System.out.println(result);

			resultList.add(result);
			//
		}

		return resultList;
	}

	@Override
	public ArrayList<DBObject> getAnalystGraph(RequestModel pRequestModel) {

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

	@Override
	public List<DBObject> getAnalystChannelRevenue(ArrayList<String> list) {

		List<DBObject> dbObjList = new ArrayList<DBObject>();
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBObject query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("pos").in(list).get()).get();
		DBCollection colle = db.getCollection("JUP_DB_Sales");
		BasicDBObject document1 = new BasicDBObject();
		document1.put("$and", query1);
		DBObject match1 = new BasicDBObject("$match", query1);

		AggregationOutput agout = colle.aggregate(match1,
				new BasicDBObject("$group",
						new BasicDBObject("_id", "$channel").append("value", new BasicDBObject("$sum", "$revenue"))
								.append("count", new BasicDBObject("$sum", 1))));
		Iterator<DBObject> results = agout.results().iterator();

		DBObject obj = null;
		while (results.hasNext()) {
			obj = results.next();
			dbObjList.add(obj);
			System.out.println(obj.get("_id") + " " + obj.get("value"));

		}
		return dbObjList;
	}

	@Override
	public List<DBObject> getAnalystDistributorRevenue(ArrayList<String> list) {
		List<DBObject> dbObjList = new ArrayList<DBObject>();
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBObject query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("pos").in(list).get()).get();
		DBCollection colle = db.getCollection("JUP_DB_Sales");
		BasicDBObject document1 = new BasicDBObject();
		document1.put("$and", query1);
		DBObject match1 = new BasicDBObject("$match", query1);

		AggregationOutput agout = colle.aggregate(match1,
				new BasicDBObject("$group",
						new BasicDBObject("_id", "$distributor").append("value", new BasicDBObject("$sum", "$revenue"))
								.append("count", new BasicDBObject("$sum", 1))));
		Iterator<DBObject> results = agout.results().iterator();

		DBObject obj = null;
		while (results.hasNext()) {
			obj = results.next();
			dbObjList.add(obj);

		}
		return dbObjList;
	}

	@Override
	public List<DBObject> getAnalystCustomerRevenue(ArrayList<String> list) {
		List<DBObject> dbObjList = new ArrayList<DBObject>();
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBObject query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("pos").in(list).get()).get();
		DBCollection colle = db.getCollection("JUP_DB_Sales");
		BasicDBObject document1 = new BasicDBObject();
		document1.put("$and", query1);
		DBObject match1 = new BasicDBObject("$match", query1);

		AggregationOutput agout = colle.aggregate(match1,
				new BasicDBObject("$group",
						new BasicDBObject("_id", "$segment").append("value", new BasicDBObject("$sum", "$revenue"))
								.append("count", new BasicDBObject("$sum", 1))));
		Iterator<DBObject> results = agout.results().iterator();

		DBObject obj = null;
		while (results.hasNext()) {
			obj = results.next();
			dbObjList.add(obj);
		}
		return dbObjList;
	}

	@Override
	public List<DBObject> getAnalystFareMix(ArrayList<String> list) {
		List<DBObject> dbObjList = new ArrayList<DBObject>();
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBObject query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("origin").in(list).get()).get();
		DBCollection colle = db.getCollection("JUP_DB_ATPCO_Fares");
		BasicDBObject document1 = new BasicDBObject();
		document1.put("$and", query1);
		DBObject match1 = new BasicDBObject("$match", query1);
		AggregationOutput agout = colle.aggregate(match1,
				new BasicDBObject("$group",
						new BasicDBObject("_id", "$RBD_type").append("value", new BasicDBObject("$sum", "$fare"))
								.append("count", new BasicDBObject("$sum", 1))));
		Iterator<DBObject> results = agout.results().iterator();

		DBObject obj = null;
		while (results.hasNext()) {
			obj = results.next();
			dbObjList.add(obj);
		}
		return dbObjList;
	}

	@Override
	public List<DBObject> getAnalystSql(ArrayList<String> list, RequestModel pRequestModel) {

		String startDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			startDate = pRequestModel.getFromDate().toString();

		String endDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			endDate = pRequestModel.getToDate().toString();

		ArrayList<String> statusList = new ArrayList<String>();
		statusList.add("accepted");
		statusList.add("Autoaccepted");
		statusList.add("watched");
		statusList.add("Autowatched");
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBObject query = new QueryBuilder().start().and(new QueryBuilder().start().put("pos").in(list).put("status")
				.in(statusList).put("triggering_event_date").greaterThanEquals(startDate).lessThanEquals(endDate).get())
				.get();
		DBCollection coll = db.getCollection("JUP_DB_Workflow_1_dummy");
		DBObject match = new BasicDBObject("$match", query);
		DBObject project = new BasicDBObject("$project",
				new BasicDBObject("action_time", 1).append("triggering_event_date", 1)
						.append("triggering_event_time", 1).append("pos", 1).append("od", 1).append("compartment", 1)
						.append("status", 1).append("action_date", 1));
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
	public List<DBObject> getTriggerStatusCount(ArrayList<String> list) {
		List<DBObject> dbObjList = new ArrayList<DBObject>();
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBObject query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("pos").in(list).get()).get();
		DBCollection colle = db.getCollection("JUP_DB_Workflow_1_dummy");

		BasicDBObject document1 = new BasicDBObject();
		document1.put("$and", query1);
		DBObject match1 = new BasicDBObject("$match", query1);

		// List<DBObject> results = colle.distinct("distributor", query1);

		AggregationOutput agout = colle.aggregate(match1, new BasicDBObject("$group",
				new BasicDBObject("_id", "$status").append("count", new BasicDBObject("$sum", 1))));
		Iterator<DBObject> results = agout.results().iterator();

		DBObject obj = null;
		while (results.hasNext()) {
			obj = results.next();
			dbObjList.add(obj);
		}

		return dbObjList;
	}

	@Override
	public List<DBObject> getAnalystPerformanceGrid(ArrayList<String> list,RequestModel pRequestModel) {
		
		List<DBObject> dbObjList = new ArrayList<DBObject>();
		
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			
		DBCollection lCollection = db.getCollection("JUP_DB_Manual_Triggers_Module");
		BasicDBObject searchQuery = new BasicDBObject();

		try {

			String region = "regionArray";
			String country = "countryArray";
			String city = "cityArray";
			String network = "networkArray";
			String cluster = "clusterArray";
			String origin = "originArray";
			String destination = "destinationArray";
			String allComptmt = "all";
			String comptmt = "compartmentArray";

			if (pRequestModel.getFromDate() != null)
				searchQuery.put("dep_date", new BasicDBObject("$gte", pRequestModel.getFromDate()));

			if (pRequestModel.getToDate() != null)
				searchQuery.put("dep_date", new BasicDBObject("$lte", pRequestModel.getToDate()));
			
			if (pRequestModel.getOdArray() != null)
				searchQuery.put("od", new BasicDBObject("$in", pRequestModel.getOdArray()));

			if (pRequestModel.getPosMap() != null && pRequestModel.getPosMap().containsKey(region)) {

				List<String> posRegionList = pRequestModel.getPosMap().get(region);
				searchQuery.put("pos.Region", new BasicDBObject("$in", posRegionList));
			}

			if (pRequestModel.getExclPosMap() != null && pRequestModel.getExclPosMap().containsKey(region)) {

				List<String> posRegionList = pRequestModel.getExclPosMap().get(region);
				searchQuery.put("pos.Region", new BasicDBObject("$nin", posRegionList));
			}

			if (pRequestModel.getPosMap() != null && pRequestModel.getPosMap().containsKey(country)) {

				List<String> posCountryList = pRequestModel.getPosMap().get(country);
				searchQuery.put("pos.Country", new BasicDBObject("$in", posCountryList));

			}

			if (pRequestModel.getExclPosMap() != null && pRequestModel.getExclPosMap().containsKey(country)) {

				List<String> posCountryList = pRequestModel.getExclPosMap().get(country);
				searchQuery.put("pos.Country", new BasicDBObject("$nin", posCountryList));
			}

			if (pRequestModel.getPosMap() != null && pRequestModel.getPosMap().containsKey(city)) {

				List<String> posCityList = pRequestModel.getPosMap().get(city);
				searchQuery.put("pos.City", new BasicDBObject("$in", posCityList));

			}

			if (pRequestModel.getExclPosMap() != null && pRequestModel.getExclPosMap().containsKey(city)) {

				List<String> posCityList = pRequestModel.getExclPosMap().get(city);
				searchQuery.put("pos.City", new BasicDBObject("$nin", posCityList));
			}

			if (pRequestModel.getPosMap() != null && pRequestModel.getPosMap().containsKey(network)) {

				List<String> posNetworkList = pRequestModel.getPosMap().get(network);
				searchQuery.put("pos.Network", new BasicDBObject("$in", posNetworkList));

			}

			if (pRequestModel.getExclPosMap() != null && pRequestModel.getExclPosMap().containsKey(network)) {

				List<String> posNetworkList = pRequestModel.getExclPosMap().get(network);
				searchQuery.put("pos.Network", new BasicDBObject("$nin", posNetworkList));
			}

			if (pRequestModel.getPosMap() != null && pRequestModel.getPosMap().containsKey(cluster)) {

				List<String> posClusterList = pRequestModel.getPosMap().get(cluster);
				searchQuery.put("pos.Cluster", new BasicDBObject("$in", posClusterList));

			}

			if (pRequestModel.getExclPosMap() != null && pRequestModel.getExclPosMap().containsKey(cluster)) {

				List<String> posClusterList = pRequestModel.getExclPosMap().get(cluster);
				searchQuery.put("pos.Cluster", new BasicDBObject("$nin", posClusterList));
			}

			if (pRequestModel.getOriginMap() != null && pRequestModel.getOriginMap().containsKey(region)) {

				List<String> originRegionList = pRequestModel.getOriginMap().get(region);
				searchQuery.put("origin.Region", new BasicDBObject("$in", originRegionList));
			}

			if (pRequestModel.getExclOriginMap() != null && pRequestModel.getExclOriginMap().containsKey(region)) {

				List<String> originRegionList = pRequestModel.getExclOriginMap().get(region);
				searchQuery.put("origin.Region", new BasicDBObject("$nin", originRegionList));
			}

			if (pRequestModel.getOriginMap() != null && pRequestModel.getOriginMap().containsKey(country)) {

				List<String> originCountryList = pRequestModel.getOriginMap().get(country);
				searchQuery.put("origin.Country", new BasicDBObject("$in", originCountryList));

			}

			if (pRequestModel.getExclOriginMap() != null && pRequestModel.getExclOriginMap().containsKey(country)) {

				List<String> originCountryList = pRequestModel.getExclOriginMap().get(country);
				searchQuery.put("origin.Country", new BasicDBObject("$nin", originCountryList));
			}

			if (pRequestModel.getOriginMap() != null && pRequestModel.getOriginMap().containsKey(city)) {

				List<String> originCityList = pRequestModel.getOriginMap().get(city);
				searchQuery.put("origin.City", new BasicDBObject("$in", originCityList));

			}

			if (pRequestModel.getExclOriginMap() != null && pRequestModel.getExclOriginMap().containsKey(city)) {

				List<String> originCityList = pRequestModel.getExclOriginMap().get(city);
				searchQuery.put("origin.City", new BasicDBObject("$nin", originCityList));
			}

			if (pRequestModel.getOriginMap() != null && pRequestModel.getOriginMap().containsKey(network)) {

				List<String> originNetworkList = pRequestModel.getOriginMap().get(network);
				searchQuery.put("origin.Network", new BasicDBObject("$in", originNetworkList));

			}

			if (pRequestModel.getExclOriginMap() != null && pRequestModel.getExclOriginMap().containsKey(network)) {

				List<String> originNetworkList = pRequestModel.getExclOriginMap().get(network);
				searchQuery.put("origin.Network", new BasicDBObject("$nin", originNetworkList));
			}

			if (pRequestModel.getOriginMap() != null && pRequestModel.getOriginMap().containsKey(cluster)) {

				List<String> originClusterList = pRequestModel.getOriginMap().get(cluster);
				searchQuery.put("origin.Cluster", new BasicDBObject("$in", originClusterList));

			}

			if (pRequestModel.getExclOriginMap() != null && pRequestModel.getExclOriginMap().containsKey(cluster)) {

				List<String> originClusterList = pRequestModel.getExclOriginMap().get(cluster);
				searchQuery.put("origin.Cluster", new BasicDBObject("$nin", originClusterList));
			}

			if (pRequestModel.getOriginMap() != null && pRequestModel.getOriginMap().containsKey(origin)) {

				List<String> orgOriginList = pRequestModel.getDestMap().get(origin);
				searchQuery.put("origin.origin", new BasicDBObject("$in", orgOriginList));

			}

			if (pRequestModel.getExclOriginMap() != null && pRequestModel.getExclOriginMap().containsKey(origin)) {

				List<String> orgOriginList = pRequestModel.getExclOriginMap().get(origin);
				searchQuery.put("origin.origin", new BasicDBObject("$nin", orgOriginList));
			}

			if (pRequestModel.getDestMap() != null && pRequestModel.getDestMap().containsKey(region)) {

				List<String> destRegionList = pRequestModel.getDestMap().get(region);
				searchQuery.put("destination.Region", new BasicDBObject("$in", destRegionList));
			}

			if (pRequestModel.getExclDestMap() != null && pRequestModel.getExclDestMap().containsKey(region)) {

				List<String> destRegionList = pRequestModel.getExclDestMap().get(region);
				searchQuery.put("destination.Region", new BasicDBObject("$nin", destRegionList));
			}

			if (pRequestModel.getDestMap() != null && pRequestModel.getDestMap().containsKey(country)) {

				List<String> destCountryList = pRequestModel.getDestMap().get(country);
				searchQuery.put("destination.Country", new BasicDBObject("$in", destCountryList));

			}

			if (pRequestModel.getExclDestMap() != null && pRequestModel.getExclDestMap().containsKey(country)) {

				List<String> destCountryList = pRequestModel.getExclDestMap().get(country);
				searchQuery.put("destination.Country", new BasicDBObject("$nin", destCountryList));
			}

			if (pRequestModel.getDestMap() != null && pRequestModel.getDestMap().containsKey(city)) {

				List<String> destCityList = pRequestModel.getDestMap().get(city);
				searchQuery.put("destination.City", new BasicDBObject("$in", destCityList));

			}

			if (pRequestModel.getExclDestMap() != null && pRequestModel.getExclDestMap().containsKey(city)) {

				List<String> destCityList = pRequestModel.getExclDestMap().get(city);
				searchQuery.put("destination.City", new BasicDBObject("$nin", destCityList));
			}

			if (pRequestModel.getDestMap() != null && pRequestModel.getDestMap().containsKey(network)) {

				List<String> destNetworkList = pRequestModel.getDestMap().get(network);
				searchQuery.put("destination.Network", new BasicDBObject("$in", destNetworkList));

			}

			if (pRequestModel.getExclDestMap() != null && pRequestModel.getExclDestMap().containsKey(network)) {

				List<String> destNetworkList = pRequestModel.getExclDestMap().get(network);
				searchQuery.put("destination.Network", new BasicDBObject("$nin", destNetworkList));
			}

			if (pRequestModel.getDestMap() != null && pRequestModel.getDestMap().containsKey(cluster)) {

				List<String> destClusterList = pRequestModel.getDestMap().get(country);
				searchQuery.put("destination.Cluster", new BasicDBObject("$in", destClusterList));

			}

			if (pRequestModel.getExclDestMap() != null && pRequestModel.getExclDestMap().containsKey(cluster)) {

				List<String> destClusterList = pRequestModel.getExclDestMap().get(cluster);
				searchQuery.put("destination.Cluster", new BasicDBObject("$nin", destClusterList));
			}

			if (pRequestModel.getDestMap() != null && pRequestModel.getDestMap().containsKey(destination)) {

				List<String> destDestinationList = pRequestModel.getDestMap().get(destination);
				searchQuery.put("destination.destination", new BasicDBObject("$in", destDestinationList));

			}

			if (pRequestModel.getExclDestMap() != null && pRequestModel.getExclDestMap().containsKey(destination)) {

				List<String> destDestinationList = pRequestModel.getExclDestMap().get(destination);
				searchQuery.put("destination.destination", new BasicDBObject("$nin", destDestinationList));
			}

			if (pRequestModel.getCompMap() != null && pRequestModel.getCompMap().containsKey(allComptmt)) {

				List<String> compAllList = pRequestModel.getCompMap().get(allComptmt);
				searchQuery.put("compartment.all", new BasicDBObject("$in", compAllList));

			}

			ArrayList<String> compartmentList = new ArrayList<String>();
			if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
				for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
					compartmentList.add(pRequestModel.getCompartmentArray()[i]);

				}
				searchQuery.put("compartment.compartment", new BasicDBObject("$in", compartmentList));
			}
			if (pRequestModel.getCompMap() != null && pRequestModel.getCompMap().containsKey(comptmt)) {

				List<String> compList = pRequestModel.getCompMap().get(comptmt);
				searchQuery.put("compartment.compartment", new BasicDBObject("$in", compList));

			}

			DBCursor cursor = null;
			if (lCollection != null)
				cursor = lCollection.find(searchQuery);

			if (cursor != null) {
				while (cursor.hasNext()) {

					dbObjList.add(cursor.next());

				}
			}

			System.out.println(dbObjList);

		
		
		} catch (Exception e) {

			e.printStackTrace();

		}

		return dbObjList;
	}
		
	@Override
	public List<DBObject> getAnalystFareType(ArrayList<String> list, RequestModel pRequestModel) {

		String fromDate = pRequestModel.getFromDate();
		String toDate = pRequestModel.getToDate();
		String fromSnapdate = null;
		String fromPrevYrDate = null;
		String toPrevYrDate = null;
		DBCollection temp_collection = null;
		if (pRequestModel.getFromSnapDate() != null) {
			fromSnapdate = Utility.getCurrentDate();
		} else {
			fromSnapdate = "2017-04-27";
			fromPrevYrDate = Utility.getPrevYrDate(fromSnapdate);
		}
		String toSnapDate = null;
		if (pRequestModel.getToSnapDate() != null) {
			toSnapDate = Utility.getCurrentDate();
		} else {
			toSnapDate = "2017-05-30";
			toPrevYrDate = Utility.getPrevYrDate(toSnapDate);
		}
		try {
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBObject query = new QueryBuilder().start()
				.and(new QueryBuilder().start().put("pos").in(list).put("dep_date").greaterThanEquals(fromDate)
						.lessThanEquals(toDate).put("snap_date").greaterThanEquals(fromSnapdate)
						.lessThanEquals(toSnapDate).get())
				.get();
		DBCollection colle = db.getCollection("JUP_DB_Sales");
		//DBObject out = new BasicDBObject("$out", "datasetTemp"); 
		AggregationOutput temp_out= colle.aggregate(Arrays.asList((DBObject)new BasicDBObject("$out", "temp_sales")));
		temp_collection=db.getCollection("temp_sales");
		
		BasicDBObject document1 = new BasicDBObject();
		document1.put("$and", query);
		DBObject Lookup_LocalField = new BasicDBObject("from", "JUP_DB_Host_OD_Capacity").append("localField", "od")
				.append("foreignField", "od").append("as", "Capacity");
		DBObject lookup = new BasicDBObject("$lookup", Lookup_LocalField);
		DBObject match = new BasicDBObject("$match", query);
		DBObject unwindPath = new BasicDBObject("path", "$Capacity");

		DBObject unwindCombine = new BasicDBObject("$unwind", unwindPath);

		DBObject project = new BasicDBObject("$project",
				new BasicDBObject("pos", 1).append("od", 1).append("compartment", 1).append("origin", 1)
						.append("destination", 1).append("revenue", 1).append("od_capacity", "$Capacity.revenue"));
		AggregationOptions aggregationOptions = AggregationOptions.builder()
                .batchSize(100)
                .outputMode(AggregationOptions.OutputMode.CURSOR)
                .allowDiskUse(true)
                .build();
		List<DBObject> aggregateList = Arrays.asList(match, lookup, unwindCombine, project);
		//AggregationOutput output = temp_collection.aggregate(aggregateList);
		Cursor cursor = temp_collection.aggregate(aggregateList,aggregationOptions);
		//Iterator<DBObject> results = output.results().iterator();
		List<DBObject> dbObjList = new ArrayList<DBObject>();
		DBObject obj = null;
		
		while (cursor.hasNext()) {
			obj = cursor.next();
			System.out.println(cursor.next());
			dbObjList.add(obj);

		}

		return dbObjList;
		}
		finally{
		temp_collection.drop();
		}
	}

}
