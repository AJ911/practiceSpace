package com.flynava.jupiter.serviceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.flynava.jupiter.model.FareAnalysis;
import com.flynava.jupiter.model.FlightAnalysis;
import com.flynava.jupiter.model.MarketIndicatorSummary;
import com.flynava.jupiter.model.MonthlyLowFare;
import com.flynava.jupiter.model.RequestModel;
import com.mongodb.DBObject;

public interface CompetitorAnalysisService {

	public List<Object> getProductIndicator(RequestModel pRequestModel);

	public Map<String, Object> getProductIndicatorReport(RequestModel pRequestModel);

	public Map<String, Object> getFares(RequestModel pRequestModel);

	public List<FareAnalysis> getFareAnalysis(RequestModel pRequestModel);

	public Map<String, Object> getMarketIndicatorTopODs(RequestModel pRequestModel);

	public List<MarketIndicatorSummary> getMarketIndicator(RequestModel pRequestModel);

	public List<Object> getFlightAvailability(RequestModel pRequestModel);

	public Map<String, Object> getFlightForecast(RequestModel pRequestModel);

	public Map<String, Object> getFlightAgentFriendsFoes(RequestModel pRequestModel);

	public Object getFareBrands(RequestModel pRequestModel);

	public Object getFareGridAllFares(RequestModel pRequestModel);

	public Map<String, Object> getScheduleInformation(RequestModel pRequestModel);

	public Map<String, Object> getMarketEntrantsLeavers(RequestModel pRequestModel);

	public List<FlightAnalysis> getFlightAnalysis(RequestModel pRequestModel);

	public ArrayList<DBObject> getScheduleNetwork(RequestModel pRequestModel);

	public ArrayList<DBObject> getScheduleInformationOd(RequestModel pRequestModel);

	public List<MonthlyLowFare> getMonthlyLowFare(RequestModel pRequestModel);

}
