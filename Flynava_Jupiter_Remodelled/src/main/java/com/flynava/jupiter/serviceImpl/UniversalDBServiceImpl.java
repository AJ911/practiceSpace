package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.UniversalDBDao;
import com.flynava.jupiter.model.QueryJupiter;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.SavedConfig;
import com.flynava.jupiter.serviceInterface.UniversalDBService;
import com.flynava.jupiter.util.Constants;
import com.flynava.jupiter.util.Response;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Surya This Class UniversalDBServiceImpl contains all the service
 *         level functions which are generic and required by UI
 */
@Service
public class UniversalDBServiceImpl implements UniversalDBService {
	@Autowired
	UniversalDBDao universalDBDao;

	@Override
	public List<BasicDBObject> getDBValues(RequestModel requestModel) {
		List<BasicDBObject> dbValues = universalDBDao.getDBValues(requestModel);
		System.out.println("service level dbvalues" + dbValues.toString());
		return dbValues;
	}

	@Override
	public BasicDBObject getFunctionData(RequestModel mRequestModel) {
		BasicDBObject functionData = universalDBDao.getFunctionData(mRequestModel);
		return functionData;
	}

	@Override
	public Map<String, Object> getDBValuesLength(RequestModel requestModel) {

		int length = universalDBDao.findcount(requestModel);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", length);
		return map;

	}

	@Override
	public Response upsert(JSONObject jsonObject, String id, String collection) {
		int row = 0;
		if (universalDBDao != null)
			row = universalDBDao.upsert(jsonObject, id, collection);

		Response response = new Response();

		if (row > 0) {
			response.setStatus(Constants.INSERT_SUCCESS_STATUS);
			response.setMessage(Constants.INSERT_SUCCESS_MESSAGE);
		} else {
			response.setStatus(Constants.INSERT_FAILED_STATUS);
			response.setMessage(Constants.INSERT_FAILED_MESSAGE);
		}

		return response;
	}

	@Override
	public List<BasicDBObject> getQueryValues(RequestModel requestModel) {

		List<BasicDBObject> dbValues = universalDBDao.getQueryValues(requestModel);
		System.out.println("service level Queryvalues" + dbValues.toString());
		return dbValues;
	}

	@Override
	public List<DBObject> getCompetitiorValues(RequestModel requestModel) {

		List<DBObject> dbValues = universalDBDao.getCompetitors("service.user", "network");
		System.out.println("service level Queryvalues" + dbValues.toString());
		return dbValues;
	}

	@Override
	public Map<String, Object> getSaveCompetitorConfig(SavedConfig savedConfig) {
		Boolean functionData = universalDBDao.getSaveCompetitorConfig(savedConfig);
		Map<String, Object> functionMap = new HashMap<String, Object>();
		if (functionData) {
			functionMap.put("userInserted", functionData);
		} else {
			functionMap.put("userInserted", functionData);
		}
		return functionMap;
	}

	@Override
	public List<Object> executeUniversalQuery(QueryJupiter queryModel) {

		int lRow = 1;
		Response response = new Response();
		List<Object> responseList = null;

		if (universalDBDao != null && queryModel != null && queryModel.getOperation() != null) {
			if (queryModel.getOperation().contains("insert") || queryModel.getOperation().contains("update")) {

				lRow = universalDBDao.universalUpdateQuery(queryModel);
				responseList = new ArrayList<Object>();
				if (lRow > 0) {

					response.setStatus(Constants.INSERT_SUCCESS_STATUS);
					response.setMessage(Constants.INSERT_SUCCESS_MESSAGE);
				} else {

					response.setStatus(Constants.INSERT_FAILED_STATUS);
					response.setMessage(Constants.INSERT_FAILED_MESSAGE);

				}

				responseList.add(response);

			} else if (queryModel.getOperation().contains("fetch")) {

				responseList = universalDBDao.universalFetchQuery(queryModel);

			}
		}

		return responseList;
	}
	
	public Response executeUniversalValidation(RequestModel requestModel){
		
		Boolean validationData = universalDBDao.universalValidateQuery(requestModel);
				
		Response response = new Response();

		if (validationData) {
			response.setStatus(Constants.INSERT_FAILED_STATUS);
			response.setMessage(Constants.INSERT_FAILED_MESSAGE);
		} else {
			response.setStatus(Constants.INSERT_SUCCESS_STATUS);
			response.setMessage(Constants.INSERT_SUCCESS_MESSAGE);
		}

		return response;
	}

}
