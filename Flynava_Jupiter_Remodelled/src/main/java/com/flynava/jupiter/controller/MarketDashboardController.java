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

import com.flynava.jupiter.model.BspOverview;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.MarketService;
import com.mongodb.BasicDBObject;

/**
 * The MarketDashboardController class accepts calls from the Market Dashboard
 * (UI), based on the Url call, the respective method is being called,for each
 * grid.
 *
 * @author Anu Merin
 * 
 */

@Controller
public class MarketDashboardController {

	@Autowired
	MarketService mMarketService;

	/**
	 * This method is used to get the Market Summary.
	 * 
	 * It return responses as a Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getMarketSummary", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getMarketSummary(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(mMarketService.getMarketSummary(pRequestModel), HttpStatus.OK);

	}

	/**
	 * This method is used to get the Monthly Network Passenger Growth.
	 * 
	 * It return responses as a Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getMonthlyNetworkPassengerGrowth", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getMonthlyNetworkPassengerGrowth(
			@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mMarketService.getMonthlyNetworkPassengerGrowth(pRequestModel),
				HttpStatus.OK);
	}

	/**
	 * This method is used to get the Top Ten Agent By volume.
	 * 
	 * It return responses as a ResponseEntity<Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getTopTenAgentByVolume", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getTopTenAgentByVolume(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mMarketService.getTopTenAgentByVolume(pRequestModel),
				HttpStatus.OK);

	}

	/**
	 * This method is used to get the Top ten OD Spikes.
	 * 
	 * It return responses as a Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getTopTenOdSpikes", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getTopTenOdSpikes(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mMarketService.getTopTenOdSpikes(pRequestModel), HttpStatus.OK);

	}

	/**
	 * This method is used to get the Top Ten Country Market.
	 * 
	 * It return responses as a Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getTopTenCountryMarkets", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getTopTenCountryMarket(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mMarketService.getTopTenCountryMarket(pRequestModel),
				HttpStatus.OK);

	}

	/**
	 * This method is used to get the Call Center Sales Growth.
	 * 
	 * It return responses as a Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getCallCenterSalesGrowth", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getCallCenterSalesGrowth(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mMarketService.getCallCenterSalesGrowth(pRequestModel),
				HttpStatus.OK);

	}

	/**
	 * This method is used to get the Bsp sales summary host.
	 * 
	 * It return responses as a Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getBspSalesSummaryHost", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getBspsalesSummaryhost(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mMarketService.getBspsalesSummaryhost(pRequestModel),
				HttpStatus.OK);
	}

	/**
	 * This method is used to get the Bsp sales summary for all airlines.
	 * 
	 * It return responses as a Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getBspSalesSummaryAirlines", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getBspSalesSummaryAirlines(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mMarketService.getBspsalesSummaryAirline(pRequestModel),
				HttpStatus.OK);
	}

	/**
	 * This method is used to get the Market Barometer Dashboard Grid .
	 * 
	 * It return responses as a Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getMarketBarometer", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getMarketBarometer(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mMarketService.getMarketBarometer(pRequestModel), HttpStatus.OK);
	}

	/**
	 * This method is used to get the Competitor Booking.
	 * 
	 * It return responses as a BasicDBObject.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getCompetitorBooking", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<BasicDBObject> getCompetitorBooking(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<BasicDBObject>(mMarketService.getCompetitorBooking(pRequestModel), HttpStatus.OK);

	}

	/**
	 * This method is used to get the Market Opportunities.
	 * 
	 * It return responses as a BasicDBObject.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getOpportunities", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<BasicDBObject> getOpprtunities(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<BasicDBObject>(mMarketService.getOppurtunities(pRequestModel), HttpStatus.OK);

	}

	/**
	 * This method is used to get the Host booking.
	 * 
	 * It return responses as a BasicDBObject.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getHostBooking", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<BasicDBObject> getHostBooking(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<BasicDBObject>(mMarketService.getHostBooking(pRequestModel), HttpStatus.OK);
	}

	/**
	 * This method is used to get the Bsp overview details.
	 * 
	 * It return responses as a BasicDBObject.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getBSPOverviewDetails", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<BspOverview>> getBSPOverviewDetails(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<List<BspOverview>>(mMarketService.getBSPOverviewDetails(pRequestModel),
				HttpStatus.OK);
	}

	/**
	 * This method is used to get the Market barometer.
	 * 
	 * It return responses as a BasicDBObject.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getMarketBarometerDetails", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getMarketbarometerDetails(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mMarketService.getMarketbarometerDetails(pRequestModel),
				HttpStatus.OK);
	}

	/**
	 * This method is used to get the Market Outlook Detail Screen.
	 * 
	 * It return responses as a Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getMarketOutlookReport", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getMarketOutlookReport(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<Map<String, Object>>(mMarketService.getMarketOutlookReport(pRequestModel),
				HttpStatus.OK);

	}

	/**
	 * This method is used to get the Market Outlook Dashboard Grid.
	 * 
	 * It return responses as a Map<String, Object>.
	 * 
	 * @param
	 */
	@RequestMapping(value = "/getMarketOutlookDhbGrid", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getMarketOutlookDhbGrid(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mMarketService.getMarketOutlookDhbGrid(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getAirPortPaxTraffic", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Object> getAirPortPaxTraffic(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Object>(mMarketService.getAirPortPaxTraffic(pRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/getOAGUnreservedRoutes", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Object>> getOAGUnreservedRoutes(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<List<Object>>(mMarketService.getOAGUnreservedRoutes(pRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/getIATAGlobalOutlook", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Object>> getIATAGlobalOutlook(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<List<Object>>(mMarketService.getIATAGlobalOutlook(pRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/getAirlinePax", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Object>> getAirlinePax(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<List<Object>>(mMarketService.getAirlinePax(pRequestModel), HttpStatus.OK);
	}

}
