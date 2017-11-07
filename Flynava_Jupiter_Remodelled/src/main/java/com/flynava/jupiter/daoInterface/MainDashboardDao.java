package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;

import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.UserProfile;
import com.mongodb.DBObject;

public interface MainDashboardDao {

	public ArrayList<DBObject> getBookings(RequestModel pRequestModel);

	public Object getEvents(RequestModel pRequestModel);

	public ArrayList<DBObject> getSales(RequestModel pRequestModel);

	public Object getIndustryBenchmark(RequestModel pRequestModel);

	public ArrayList getKpiIndex(RequestModel pRequestModel);

	public UserProfile getUserProfile(RequestModel pRequestModel);

	public String[] getRegion(RequestModel pRequestModel, String pFilterParam, String[] pParamValArray);

	public String[] getCountry(RequestModel pRequestModel, String pFilterParam, String[] pParamValArray);

	public String[] getCountryName(RequestModel pRequestModel, String pFilterParam, String[] pParamValArray);

}
