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
import com.flynava.jupiter.serviceInterface.MainDashboardService;

/**
 * @author Avanindra.Ratan
 *
 */
@Controller
public class MainDashboardController {

	@Autowired
	MainDashboardService mainDashboardService;

	@RequestMapping(value = "/getBookings", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getBookings(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(mainDashboardService.getBookings(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getEvents", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getEvents(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(mainDashboardService.getEvents(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getKpiindex", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity getKpiIndex(@RequestBody RequestModel pRequestModel) {
		/**
		 * This method is used to get the Market Summary from Market dashboard.
		 * 
		 * It return responses as a BasicDBObject.
		 * 
		 * @param
		 */

		return new ResponseEntity(mainDashboardService.getKpiIndex(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getSales", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getSales(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(mainDashboardService.getSales(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getIndustryBenchmark", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Object> getIndustryBenchmarck(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Object>(mainDashboardService.getIndustryBenchmark(pRequestModel), HttpStatus.OK);
	}

}
