package com.flynava.jupiter.daoInterface;

import java.util.List;

import org.json.JSONObject;

import com.flynava.jupiter.model.QueryJupiter;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.SavedConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public interface UniversalDBDao {
	public List<BasicDBObject> getDBValues(RequestModel requestModel);

	public BasicDBObject getFunctionData(RequestModel mRequestModel);

	int findcount(RequestModel requestModel);

	int upsert(JSONObject jsonObject, String id, String collection);

	public List<BasicDBObject> getQueryValues(RequestModel requestModel);

	List<DBObject> getCompetitors(String user, String user_level);

	public Boolean getSaveCompetitorConfig(SavedConfig savedConfig);

	public int universalUpdateQuery(QueryJupiter queryModel);

	public List<Object> universalFetchQuery(QueryJupiter queryModel);
	
	public Boolean universalValidateQuery(RequestModel requestModel);

}
