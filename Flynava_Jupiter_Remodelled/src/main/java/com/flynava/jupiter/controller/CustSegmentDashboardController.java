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
import com.flynava.jupiter.serviceInterface.CustomerSegmentService;
import com.flynava.jupiter.util.Response;
import com.mongodb.BasicDBObject;

/**
 * @author surya This Controller CustSegmentDashboardController contains all the
 *         functions required in Distribution Customer Dashboard
 * 
 *
 */

@Controller
public class CustSegmentDashboardController {

	@Autowired
	CustomerSegmentService mCustomerSegmentService;

	@Autowired
	Response response;

	/**
	 * This method is used to get the Channel Summary in the Distribution
	 * Customer Dashboard.
	 * 
	 */
	@RequestMapping(value = "/getChannelSummary", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getChannelSummary(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mCustomerSegmentService.getChannelSummary(pRequestModel),
				HttpStatus.OK);

	}

	/**
	 * This method is used to get the Channel Summary direct section in the
	 * Distribution Customer Dashboard.
	 * 
	 */
	@RequestMapping(value = "/getChannelSummaryDirect", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getChannelSummaryDirect(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mCustomerSegmentService.getChannelSummaryDirect(pRequestModel),
				HttpStatus.OK);

	}

	/**
	 * This method is used to get the Channel Summary indirect section in the
	 * Distribution Customer Dashboard.
	 * 
	 */
	@RequestMapping(value = "/getChannelSummaryIndirect", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getChannelSummaryIndirect(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mCustomerSegmentService.getChannelSummaryIndirect(pRequestModel),
				HttpStatus.OK);
	}

	/**
	 * This method is used to get the customer segment section in the
	 * Distribution Customer Dashboard.
	 * 
	 */
	@RequestMapping(value = "/getCustomerSegment", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getCustomerSegment(@RequestBody RequestModel requestModel) {
		return new ResponseEntity<Map<String, Object>>(mCustomerSegmentService.getCustomerSegment(requestModel),
				HttpStatus.OK);
	}

	/**
	 * This method is used to get the customer segment details section in the
	 * Distribution Customer Dashboard.
	 * 
	 */
	@RequestMapping(value = "/getCustomerSegmentDetails", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getCustomerSegmentDetails(@RequestBody RequestModel requestModel) {
		return new ResponseEntity<Map<String, Object>>(mCustomerSegmentService.getCustomerSegmentDetails(requestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getPriceEffectiveness", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<BasicDBObject> getPriceEffectiveness(@RequestBody RequestModel requestModel) {
		return new ResponseEntity<BasicDBObject>(mCustomerSegmentService.getPriceEffectiveness(requestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getFrequentFlyers", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getFrequentFlyers(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mCustomerSegmentService.getFrequentFlyers(pRequestModel),
				HttpStatus.OK);

	}

	/**
	 * This method is used to get the distributors section in the Distribution
	 * Customer Dashboard.
	 * 
	 */
	@RequestMapping(value = "/getDistributors", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getDistributors(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mCustomerSegmentService.getDistributors(pRequestModel),
				HttpStatus.OK);

	}

	/**
	 * This method is used to get the distributors details section in the
	 * Distribution Customer Dashboard.
	 * 
	 */
	@RequestMapping(value = "/getDistributorDetails", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getDistributorDetails(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mCustomerSegmentService.getDistributorDetails(pRequestModel),
				HttpStatus.OK);

	}

}
