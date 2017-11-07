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
import org.springframework.web.bind.annotation.ResponseBody;

import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.SalesReviewService;

@Controller
public class SalesReviewController {

	@Autowired
	SalesReviewService mSalesReviewService;

	@RequestMapping(value = "/getTopTenOdSales", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getTopTenOd(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mSalesReviewService.getTopTenOd(pRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/getTopTenAgentSales", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getTopTenAgent(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mSalesReviewService.getTopTenAgent(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getTopTenFaresSales", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getTopTenFares(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mSalesReviewService.getTopTenFares(pRequestModel),
				HttpStatus.OK);
	}

}
