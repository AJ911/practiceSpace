package com.flynava.jupiter.controller;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.flynava.jupiter.model.QueryJupiter;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.SavedConfig;
import com.flynava.jupiter.serviceInterface.UniversalDBService;
import com.flynava.jupiter.util.Response;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Surya This Controller UniversalDBController contains all the
 *         functions which are generic and required by UI
 */

@Controller
public class UniversalDBController {
	@Autowired
	Response response;
	@Autowired
	UniversalDBService universalDBService;

	/*
	 * This method is for displaying Database collection records taking
	 * parameters dbName,collection and query
	 */
	@RequestMapping(value = "/getDBValues", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<BasicDBObject>> getDBValues(@RequestBody RequestModel requestModel) {
		return new ResponseEntity<List<BasicDBObject>>(universalDBService.getDBValues(requestModel), HttpStatus.OK);
	}

	/*
	 * This method is for displaying Database function records with out any
	 * parameters
	 */
	@RequestMapping(value = "/getFunctionData", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<BasicDBObject> getFunctionData(@RequestBody RequestModel mRequestModel) {
		return new ResponseEntity<BasicDBObject>(universalDBService.getFunctionData(mRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/getDBValuesLength", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getDBValuesLength(@RequestBody RequestModel requestModel) {
		return new ResponseEntity<Map<String, Object>>(universalDBService.getDBValuesLength(requestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getUpsert", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> upsert(@RequestBody String requestModel, @RequestParam("id") String id,
			@RequestParam("collection") String collection) {

		return new ResponseEntity<Response>(universalDBService.upsert(new JSONObject(requestModel), id, collection),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getQueryValues", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<BasicDBObject>> getQueryValues(@RequestBody RequestModel requestModel) {
		return new ResponseEntity<List<BasicDBObject>>(universalDBService.getQueryValues(requestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/SavedCompetitorConfig", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getSaveCompetitorConfig(@RequestBody SavedConfig savedConfig) {
		return new ResponseEntity<Map<String, Object>>(universalDBService.getSaveCompetitorConfig(savedConfig),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getcompetitorsValues", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<DBObject>> getcompetitorsValues(@RequestBody RequestModel requestModel) {
		return new ResponseEntity<List<DBObject>>(universalDBService.getCompetitiorValues(requestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/executeUniversalQuery", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Object>> executeUniversalQuery(@RequestBody QueryJupiter queryModel) {
		return new ResponseEntity<List<Object>>(universalDBService.executeUniversalQuery(queryModel), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/executeUniversalValidation", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> executeUniversalValidation(@RequestBody RequestModel requestModel) {
		return new ResponseEntity<Response>(universalDBService.executeUniversalValidation(requestModel), HttpStatus.OK);
	}

}
