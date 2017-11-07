package com.flynava.jupiter.controller;

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

import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.FaresDashboardService;
import com.flynava.jupiter.util.Response;

@Controller
public class FaresDashboardController {

	@Autowired
	Response response;
	@Autowired
	FaresDashboardService mFaresDashboardService;

	@RequestMapping(value = "/getFaresDashboard", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getTriggerTypes(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mFaresDashboardService.getFares(pRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/getFaresCompetitorSummary", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getFaresCompetitorSummary(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mFaresDashboardService.getFaresCompetitorSummary(pRequestModel),
				HttpStatus.OK);
	}

}
