package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;
import java.util.List;

import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.UserProfile;
import com.mongodb.DBObject;

public interface CompetitorAnalysisDao {

	public List<Object> getProductIndicator(UserProfile pUserProfile);

	public ArrayList<DBObject> getProductIndicatorReport(UserProfile pUserProfile);

	public ArrayList<DBObject> getFares(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getFareAnalysis(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getMarketIndicator(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getMarketIndicatorTopODs(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getFlightAvailability(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getFlightAgentFriends(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getFlightForecast(RequestModel pRequestModel, UserProfile pUserProfile);

	public Object getFareBrands(RequestModel pRequestModel);

	public ArrayList<DBObject> getScheduleDirect(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getScheduleIndirect(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getScheduleNetwork();

	public ArrayList<DBObject> getScheduleInformationOd(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getFareGridAllFares(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getMarketEntrantsLeavers(RequestModel requestmodel, UserProfile pUserProfile);

	public ArrayList<DBObject> getFlightAnalysis(RequestModel pRequestModel, UserProfile pUserProfile);

	public ArrayList<DBObject> getMonthlyLowFare(RequestModel pRequestModel, UserProfile pUserProfile);

}
