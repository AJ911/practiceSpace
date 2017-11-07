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
import com.flynava.jupiter.serviceInterface.CrossbarDataService;
import com.flynava.jupiter.util.Response;

/**
 * @author Surya
 *
 */

@Controller
public class CrossbarDataController {
	@Autowired
	Response response;
	@Autowired
	CrossbarDataService crossbarDataService;

	@RequestMapping(value = "/getCrossbarData", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getCrossbarData(@RequestBody RequestModel requestModel) {
		/*
		 * This method is for displaying Crossbar Data taking parameter
		 * "procedure name"
		 */

		return new ResponseEntity<Map<String, Object>>(crossbarDataService.getCrossbarData(requestModel),
				HttpStatus.OK);
	}

}
