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
import com.flynava.jupiter.serviceInterface.ExchangeRateService;
import com.flynava.jupiter.util.Response;

/**
 * @author surya This Controller ExchangeRateDashBoardController contains all
 *         the functions required in Exchange Rate Dashboard
 * 
 *
 */

@Controller
public class ExchangeRateDashBoardController {
	@Autowired
	Response response;
	@Autowired
	ExchangeRateService mExchangeRateService;

	/**
	 * This method is used to get the Currency Table in the Exchange Rate
	 * Dashboard.
	 * 
	 */

	@RequestMapping(value = "/getCurrencyTable", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getCurrencyTable(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mExchangeRateService.getCurrencyTable(pRequestModel),
				HttpStatus.OK);
	}

	/**
	 * This method is used to get the YQ Table in the Exchange Rate Dashboard.
	 * 
	 */
	@RequestMapping(value = "/getYQTable", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getYQTable(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mExchangeRateService.getYQTable(pRequestModel), HttpStatus.OK);
	}

}
