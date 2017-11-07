package com.flynava.jupiter.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.FareUpdateDao;
import com.flynava.jupiter.model.ManualTrgrsReqModel;
import com.flynava.jupiter.util.Constants;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class FareUpdateDaoImpl implements FareUpdateDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	private static final Logger logger = Logger.getLogger(ManualTriggerDaoImpl.class);

	@Override
	public List<DBObject> getFareUpdate(ManualTrgrsReqModel pRequestModel) {
		List<DBObject> dbObjList = new ArrayList<DBObject>();

		DB lDb = null;
		if (mongoTemplateDemoDB != null)
			lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection lCollection = null;
		if (lDb != null)
			lCollection = lDb.getCollection("JUP_DB_Manual_Triggers_Module");

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
				searchQuery.put("departure_date", new BasicDBObject("$gt", pRequestModel.getFromDate()));

			if (pRequestModel.getToDate() != null)
				searchQuery.put("departure_date", new BasicDBObject("$lt", pRequestModel.getToDate()));

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

		} catch (Exception e) {

			logger.error("ManualTriggerDaoImpl-Exception", e);

		}

		return dbObjList;
	}

}
