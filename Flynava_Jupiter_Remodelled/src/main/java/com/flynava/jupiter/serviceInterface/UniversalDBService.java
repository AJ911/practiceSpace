package com.flynava.jupiter.serviceInterface;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.flynava.jupiter.model.QueryJupiter;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.SavedConfig;
import com.flynava.jupiter.util.Response;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public interface UniversalDBService {
	public List<BasicDBObject> getDBValues(RequestModel requestModel);

	public BasicDBObject getFunctionData(RequestModel mRequestModel);

	/* public int getDBValuesLength(RequestModel requestModel); */

	public Map<String, Object> getDBValuesLength(RequestModel requestModel);

	// public Response upsert(JSONObject jsonObject, String id);

	public Response upsert(JSONObject jsonObject, String id, String collection);

	public List<BasicDBObject> getQueryValues(RequestModel requestModel);

	public List<DBObject> getCompetitiorValues(RequestModel requestModel);

	public Map<String, Object> getSaveCompetitorConfig(SavedConfig savedConfig);

	public List<Object> executeUniversalQuery(QueryJupiter queryModel);

	public Response executeUniversalValidation(RequestModel requestModel);

}
