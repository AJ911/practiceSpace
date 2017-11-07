package com.flynava.jupiter.serviceInterface;

import java.util.List;
import java.util.Map;

import com.flynava.jupiter.model.BspOverview;
import com.flynava.jupiter.model.RequestModel;
import com.mongodb.BasicDBObject;

public interface MarketService {

	// This is service class where all Market dashboard sub screen function
	// reside.

	public Map<String, Object> getMarketSummary(RequestModel requestModel);

	public Map<String, Object> getMonthlyNetworkPassengerGrowth(RequestModel requestModel);

	public Map<String, Object> getTopTenAgentByVolume(RequestModel requestModel);

	public Map<String, Object> getTopTenOdSpikes(RequestModel requestModel);

	public Map<String, Object> getTopTenCountryMarket(RequestModel requestModel);

	public Map<String, Object> getCallCenterSalesGrowth(RequestModel requestModel);

	public Map<String, Object> getBspsalesSummaryhost(RequestModel requestModel);

	public Map<String, Object> getBspsalesSummaryAirline(RequestModel mRequestModel);

	public Map<String, Object> getMarketBarometer(RequestModel requestModel);

	public Map<String, Object> getMarketoutlook(RequestModel requestModel);

	public Map<String, Object> getMarketOutlookDetails(RequestModel requestModel);

	public Map<String, Object> getMarketOutlookReport(RequestModel requestModel);

	public Map<String, Object> getMarketOutlookDhbGrid(RequestModel requestModel);

	public BasicDBObject getCompetitorBooking(RequestModel requestModel);

	public BasicDBObject getOppurtunities(RequestModel requestModel);

	public BasicDBObject getHostBooking(RequestModel requestModel);

	public List<BspOverview> getBSPOverviewDetails(RequestModel requestModel);

	public Map<String, Object> getMarketbarometerDetails(RequestModel requestModel);

	public Object getAirPortPaxTraffic(RequestModel requestModel);

	public List<Object> getOAGUnreservedRoutes(RequestModel requestModel);

	public List<Object> getIATAGlobalOutlook(RequestModel requestModel);

	public List<Object> getAirlinePax(RequestModel requestModel);

	public Map<String, Object> getMarketOutlookDescription(RequestModel requestModel);

}
