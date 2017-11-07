package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;
import java.util.List;

import com.flynava.jupiter.model.RequestModel;
import com.mongodb.DBObject;

public interface AnalystDashboardDao {

	ArrayList<DBObject> getAnalystfromUserProfile(RequestModel pRequestModel);

	List<DBObject> getAnalystDistributorCount(ArrayList<String> list);

	List<List<DBObject>> getAnalystAgentsCount(ArrayList<String> list);

}
