package com.flynava.jupiter.serviceInterface;

import java.util.Map;

import com.flynava.jupiter.model.RequestModel;
import com.mongodb.BasicDBObject;

public interface PriceBiometricService {

	Map<String, Object> getRevenueSplit(RequestModel mRequestModel);

	BasicDBObject getSQLGraph(RequestModel mRequestModel);

	Map<String, Object> getStrategyPerformance(RequestModel mRequestModel);

	Map<String, Object> getModelPerformance(RequestModel mRequestModel);

	Map<String, Object> getPriceCurve(RequestModel mRequestModel);

	Map<String, Object> getPricePerformance(RequestModel requestModel);

	Map<String, Object> getPriceQuote(RequestModel requestModel);

	Map<String, Object> getPriceCharac_MajorAgencies(RequestModel mRequestModel);

	Map<String, Object> getPriceCharac_Channels(RequestModel mRequestModel);

	Map<String, Object> getPriceCharac_PriceHistory(RequestModel mRequestModel);

	Map<String, Object> getPriceCharac_CustomerSegment(RequestModel mRequestModel);

	Map<String, Object> getPriceCharac_FareBrand(RequestModel mRequestModel);

	Map<String, Object> getPriceHeatMapODChannel(RequestModel mRequestModel);

	Map<String, Object> getPriceHeatMap_CustomerSegment(RequestModel mRequestModel);

	Map<String, Object> getPriceHeatMapChannels(RequestModel mRequestModel);

	BasicDBObject getPriceHeatMapFares(RequestModel mRequestModel);
	
	Map<String, Object> getVLYRPoc(RequestModel mRequestModel);
	
	

}
