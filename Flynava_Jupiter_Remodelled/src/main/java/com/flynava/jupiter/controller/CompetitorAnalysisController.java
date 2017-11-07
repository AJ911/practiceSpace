
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.flynava.jupiter.model.FareAnalysis;
import com.flynava.jupiter.model.FlightAnalysis;
import com.flynava.jupiter.model.MarketIndicatorSummary;
import com.flynava.jupiter.model.MonthlyLowFare;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.CompetitorAnalysisService;

/**
 * @author Avani
 *
 */
@Controller
public class CompetitorAnalysisController {

	@Autowired
	CompetitorAnalysisService compAnalysisService;

	@RequestMapping(value = "/getProductIndicator", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<List<Object>> getProductIndicator(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<List<Object>>(compAnalysisService.getProductIndicator(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getMarketIndicator", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<List<MarketIndicatorSummary>> getMarketIndicator(
			@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<List<MarketIndicatorSummary>>(compAnalysisService.getMarketIndicator(pRequestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getFares", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getFares(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(compAnalysisService.getFares(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getFareAnalysis", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<List<FareAnalysis>> getFareRules(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<List<FareAnalysis>>(compAnalysisService.getFareAnalysis(pRequestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getMarketIndicatorTopOD", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getMarketIndicatorTopODs(
			@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(compAnalysisService.getMarketIndicatorTopODs(pRequestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getFlightAvailability", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<List<Object>> getFlightAvailability(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<List<Object>>(compAnalysisService.getFlightAvailability(pRequestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getFlightForecast", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getFlightForecast(
			@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(compAnalysisService.getFlightForecast(pRequestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getFareBrands", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Object> getFareBrands(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Object>(compAnalysisService.getFareBrands(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getScheduleInformation", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getScheduleInformation(
			@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(compAnalysisService.getScheduleInformation(pRequestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getScheduleNetwork", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Object> getScheduleNetwork(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Object>(compAnalysisService.getScheduleNetwork(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getScheduleInformationOd", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Object> getScheduleInformationOd(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Object>(compAnalysisService.getScheduleInformationOd(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getFareGridAllFares", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Object> getFareGridAllFares(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Object>(compAnalysisService.getFareGridAllFares(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getProductIndicatorReport", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getProductIndicatorReport(
			@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(compAnalysisService.getProductIndicatorReport(pRequestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getFlightAgentFriendsFoes", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getFlightAgentFriendsFoes(
			@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(compAnalysisService.getFlightAgentFriendsFoes(pRequestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getMarketEntrantsLeavers", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Map<String, Object>> getMarketEntrantsLeavers(
			@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(compAnalysisService.getMarketEntrantsLeavers(pRequestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getFlightAnalysis", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<List<FlightAnalysis>> getFlightAnalysis(
			@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<List<FlightAnalysis>>(compAnalysisService.getFlightAnalysis(pRequestModel),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/getLowFare", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<List<MonthlyLowFare>> getLowFare(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<List<MonthlyLowFare>>(compAnalysisService.getMonthlyLowFare(pRequestModel),
				HttpStatus.OK);

	}

}
