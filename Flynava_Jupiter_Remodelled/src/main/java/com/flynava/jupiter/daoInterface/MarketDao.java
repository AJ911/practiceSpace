package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;
import java.util.List;

import com.flynava.jupiter.model.RequestModel;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public interface MarketDao {

	public ArrayList<DBObject> getMarketSummary(RequestModel requestModel);

	public ArrayList<DBObject> getMonthlyNetworkPassengerGrowth(RequestModel requestModel);

	public ArrayList<DBObject> getTopTenAgentByVolume(RequestModel requestModel);

	public ArrayList<DBObject> getTopTenOdSpikes(RequestModel requestModel);

	public ArrayList<DBObject> getTopTenCountryMarket(RequestModel requestModel);

	public ArrayList<DBObject> getCallCenterSalesGrowth(RequestModel requestModel);

	public BasicDBObject getBspsalesSummaryhost(RequestModel requestModel);

	public BasicDBObject getBspsalesSummaryAirline(RequestModel requestModel);

	public ArrayList<DBObject> getMarketoutlook(RequestModel requestModel);

	public BasicDBObject getCompetitorBooking(RequestModel requestModel);

	public ArrayList<DBObject> getMarketBarometer(RequestModel requestModel);

	public BasicDBObject getOppurtunities(RequestModel requestModel);

	public BasicDBObject getHostBooking(RequestModel requestModel);

	public BasicDBObject getBSPOverviewDetails(RequestModel requestModel);

	public BasicDBObject getMarketbarometerDetails(RequestModel requestModel);

	public ArrayList<DBObject> getMarketOutlookReport(RequestModel requestModel);

	public List<Object> getAirPortPaxTraffic(RequestModel requestModel);

	public List<Object> getOAGUnreservedRoutes();

	public List<Object> getIATAGlobalOutlook(RequestModel requestModel);

	public List<Object> getAirlinePax(RequestModel requestModel);

	public List<Object> getMarketOutlookDescription(RequestModel requestModel);

}
