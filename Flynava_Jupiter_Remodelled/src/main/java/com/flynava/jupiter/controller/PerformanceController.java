package com.flynava.jupiter.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flynava.jupiter.model.AnalystPerformance;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.PerformanceService;
import com.mongodb.DBObject;

@Controller
public class PerformanceController {

	@Autowired
	PerformanceService performanceService;

	@RequestMapping(value = "/getChannelSegmentRevenueSpread", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Float>> getChannelSegmentRevenueSpread(
			@RequestBody RequestModel requestModel) {

		return new ResponseEntity<Map<String, Float>>(performanceService.getChannelSegmentRevenueSpread(requestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getDistributorSegmentRevenueSpread", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Float>> getDistributorSegmentRevenueSpread(
			@RequestBody RequestModel requestModel) {

		return new ResponseEntity<Map<String, Float>>(
				performanceService.getDistributorSegmentRevenueSpread(requestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getCustomerSegmentRevenueSpread", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Float>> getCustomerSegmentRevenueSpread(
			@RequestBody RequestModel requestModel) {

		return new ResponseEntity<Map<String, Float>>(performanceService.getCustomerSegmentRevenueSpread(requestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getAnalystPerformance", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<ArrayList<AnalystPerformance>> getAnalystPerformance(
			@RequestBody RequestModel requestModel) {

		return new ResponseEntity<ArrayList<AnalystPerformance>>(performanceService.getAnalystPerformance(),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getAnalystPerformanceSQL", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<ArrayList<DBObject>> getAnalystPerformanceSQL() {

		return new ResponseEntity<ArrayList<DBObject>>(performanceService.getAnalystPerformanceSQL(), HttpStatus.OK);

	}

	@RequestMapping(value = "/saveSimulation", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity saveSimulation() {

		return new ResponseEntity(performanceService.saveSimulation(), HttpStatus.OK);

	}

	@RequestMapping(value = "/getJupiterPerformance", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<ArrayList<DBObject>> getJupiterPerformance() {

		return new ResponseEntity<ArrayList<DBObject>>(performanceService.getJupiterPerformance(), HttpStatus.OK);

	}

}
