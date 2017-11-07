package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;

import com.flynava.jupiter.model.RequestModel;
import com.mongodb.DBObject;

public interface PerformanceDao {

	public ArrayList<DBObject> getChannelSegmentRevenueSpread(RequestModel requestModel);

	public ArrayList<DBObject> getDistributorSegmentRevenueSpread(RequestModel requestModel);

	public ArrayList<DBObject> getCustomerSegmentRevenueSpread(RequestModel requestModel);

	public ArrayList<DBObject> getAnalystPerformance();

	public ArrayList<DBObject> getAnalystPerformanceSQL();

	public int saveSimulation();

	public ArrayList<DBObject> getJupiterPerformance();

}
