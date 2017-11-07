package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;
import java.util.List;

import com.flynava.jupiter.model.RequestModel;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public interface PriceBiometricDao {

	ArrayList<DBObject> getRevenueSplit(RequestModel mRequestModel);

	BasicDBObject getSQLGraph(RequestModel mRequestModel);

	ArrayList<DBObject> getStrategyPerformance(RequestModel mRequestModel);

	ArrayList<DBObject> getModelPerformance(RequestModel mRequestModel);

	ArrayList<DBObject> getPriceCurve(RequestModel mRequestModel);

	ArrayList<DBObject> getPricePerformance(RequestModel requestModel);

	List<DBObject> getPriceCharac_MajorAgencies(RequestModel mRequestModel);

	ArrayList<DBObject> getPriceQuote(RequestModel requestModel);

	ArrayList<DBObject> getPriceCharac_Channels(RequestModel mRequestModel);

	ArrayList<DBObject> getPriceCharac_PriceHistory(RequestModel mRequestModel);

	ArrayList<DBObject> getPriceCharac_CustomerSegment(RequestModel mRequestModel);

	ArrayList<DBObject> getPriceCharac_FareBrand(RequestModel mRequestModel);

	ArrayList<DBObject> getPriceHeatMapODChannel(RequestModel mRequestModel);

	BasicDBObject getPriceHeatMap_CustomerSegment(RequestModel mRequestModel);

	BasicDBObject getPriceHeatMapChannels(RequestModel mRequestModel);

	BasicDBObject getPriceHeatMapFares(RequestModel mRequestModel);

	BasicDBObject getAnalystPerformance(RequestModel mRequestModel);
	
	ArrayList<DBObject> getVLYRPoc(RequestModel mRequestModel);
	
	

}
