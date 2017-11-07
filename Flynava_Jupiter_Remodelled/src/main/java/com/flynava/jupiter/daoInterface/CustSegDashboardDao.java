package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;

import com.flynava.jupiter.model.RequestModel;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Anu Merin
 *
 */
public interface CustSegDashboardDao {

	public ArrayList<DBObject> getChannelSummary(RequestModel requestModel);

	public Object getFrequentFlyers(RequestModel mRequestModel);

	public ArrayList<DBObject> getDistributors(RequestModel mRequestModel);

	public ArrayList<DBObject> getDistributorDetails(RequestModel mRequestModel);

	public ArrayList<DBObject> getChannelSummaryDirect(RequestModel mRequestModel);

	public ArrayList<DBObject> getChannelSummaryIndirect(RequestModel mRequestModel);

	public ArrayList<DBObject> getCustomerSegment(RequestModel requestModel);

	public ArrayList<DBObject> getCustomerSegmentDetails(RequestModel requestModel);

	public BasicDBObject getPriceEffectiveness(RequestModel requestModel);

}
