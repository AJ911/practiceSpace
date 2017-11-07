package com.flynava.jupiter.controller;

import java.util.List;
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

import com.flynava.jupiter.model.AnalystPerformanceGrid;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.AnalystService;
import com.flynava.jupiter.util.Response;

@Controller
public class AnalystController {

	@Autowired
	Response response;
	@Autowired
	AnalystService mAnalystService;

	@RequestMapping(value = "/getAnalystEvents", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystEvents(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystService.getAnalystEvents(pRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystChannelRevenuePie", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystpie(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystService.getAnalystChannelRevenue(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystChannelFarePie", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystChannelPie(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystService.getAnalystChannelFare(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystDistributorRevenuePie", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystDistributorRevenuePie(
			@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystService.getAnalystDistributorRevenuePie(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystDistributorFarePie", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystDistributorFarePie(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystService.getAnalystDistributorFarePie(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystCustomerRevenuePie", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystCustomerRevenuePie(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystService.getAnalystCustomerRevenuePie(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystCustomerFarePie", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystCustomerFarePie(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystService.getAnalystCustomerFarePie(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystFareType", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystFareType(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystService.getAnalystFareType(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystFareMixtypePie", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystFareMixtypePie(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystService.getAnalystFareMixtypePie(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystSqlTime", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystSqlTime(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystService.getAnalystSqlTime(pRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystPerformanceGrid", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<AnalystPerformanceGrid>> getAnalystPerformanceGrid(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<List<AnalystPerformanceGrid>>(mAnalystService.getAnalystPerformanceGrid(pRequestModel),
				HttpStatus.OK);
	}

}
