package com.flynava.jupiter.serviceInterface;

import java.util.ArrayList;
import java.util.Map;

import com.flynava.jupiter.model.AnalystPerformance;
import com.flynava.jupiter.model.RequestModel;
import com.mongodb.DBObject;

public interface PerformanceService {

	public Map<String, Float> getChannelSegmentRevenueSpread(RequestModel requestModel);

	public Map<String, Float> getDistributorSegmentRevenueSpread(RequestModel requestModel);

	public Map<String, Float> getCustomerSegmentRevenueSpread(RequestModel requestModel);

	public ArrayList<AnalystPerformance> getAnalystPerformance();

	public ArrayList<DBObject> getAnalystPerformanceSQL();

	public int saveSimulation();

	public ArrayList<DBObject> getJupiterPerformance();

}
