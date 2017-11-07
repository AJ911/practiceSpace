package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;
import java.util.List;

import com.flynava.jupiter.model.RequestModel;
import com.mongodb.DBObject;

public interface AnalystDao {
	ArrayList<DBObject> getAnalystEvents(RequestModel pRequestModel);

	ArrayList<DBObject> getAnalystGraph(RequestModel pRequestModel);

	List<DBObject> getAnalystChannelRevenue(ArrayList<String> list);

	List<DBObject> getAnalystDistributorRevenue(ArrayList<String> list);

	List<DBObject> getAnalystCustomerRevenue(ArrayList<String> list);

	List<DBObject> getAnalystFareMix(ArrayList<String> list);

	List<DBObject> getTriggerStatusCount(ArrayList<String> list);

	List<DBObject> getAnalystSql(ArrayList<String> list, RequestModel pRequestModel);


	List<DBObject> getAnalystFareType(ArrayList<String> list, RequestModel pRequestModel);

	List<DBObject> getAnalystPerformanceGrid(ArrayList<String> list, RequestModel pRequestModel);
}
