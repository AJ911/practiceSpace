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
import com.flynava.jupiter.serviceInterface.AnalystDashboardService;
import com.flynava.jupiter.util.Response;

@Controller
public class AnalystDashboardController {

	@Autowired
	Response response;
	@Autowired
	AnalystDashboardService mAnalystDashboardService;

	@RequestMapping(value = "/getAnalystTotalDistributor", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystTotalDistributor(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(
				mAnalystDashboardService.getAnalystTotalDistributor(pRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnalystTotalAgents", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getAnalystTotalAgents(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mAnalystDashboardService.getAnalystTotalAgents(pRequestModel),
				HttpStatus.OK);
	}

}
