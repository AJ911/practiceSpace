package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;
import java.util.List;

import com.flynava.jupiter.model.RequestModel;
import com.mongodb.DBObject;

public interface FaresDashboardDao {

	List<DBObject> getFares(RequestModel pRequestModel);

	List<DBObject> getMarketshare(RequestModel pRequestModel);

	ArrayList<DBObject> getAnalystPos(RequestModel pRequestModel);

	List<DBObject> getDocument(RequestModel pRequestModel, String collName);

	List<DBObject> getZones(RequestModel pRequestModel);
}
