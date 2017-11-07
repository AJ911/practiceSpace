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
import com.flynava.jupiter.serviceInterface.PriceBiometricService;
import com.mongodb.BasicDBObject;

/**
 * The PriceBiometricController class accepts calls from the Price Biometric
 * Dashboard (UI), based on the Url call, the respective method is being
 * called,for each grid.
 *
 * @author Anu Merin
 * 
 */

@Controller
public class PriceBiometricController {

	@Autowired
	PriceBiometricService mPriceBiometricService;

	/*
	 * This method provides the response for the Revenue Split grid in Price
	 * Biometric Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getRevenueSplit", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getRevenueSplit(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getRevenueSplit(pRequestModel),
				HttpStatus.OK);
	}

	/**
	 * SQL Graph is moved to Performance Dashboard as per confluence on
	 * 08-12-2016
	 */
	// TODO can be removed if this link is not being used by UI.
	@RequestMapping(value = "/getSQLGraph", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<BasicDBObject> getSQLGraph(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<BasicDBObject>(mPriceBiometricService.getSQLGraph(pRequestModel), HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Strategy Performance grid in
	 * Price Biometric Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getStrategyPerformance", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getStrategyPerformance(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getStrategyPerformance(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Model Performance grid in Price
	 * Biometric Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getModelPerformance", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getModelPerformance(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getModelPerformance(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price Curve grid in Price
	 * Biometric Dashboard-> Price Curve button->Price Curve Page.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceCurve", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceCurve(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getPriceCurve(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price Performance grid in Price
	 * Biometric Dashboard-> Price Performance button->Price Performance Page.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPricePerformance", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPricePerformance(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getPricePerformance(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price Characteristics grid in
	 * Price Biometric Dashboard-> Price Char. button->Price Characteristics
	 * Major Agencies Grid.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceCharac_MajorAgencies", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceCharac_MajorAgencies(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(
				mPriceBiometricService.getPriceCharac_MajorAgencies(pRequestModel), HttpStatus.OK);
	}

	/*
	 * 
	 * This method provides the response for the Price Characteristics grid in
	 * Price Biometric Dashboard-> Price Char. button->Price Characteristics
	 * Price History Grid.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceCharac_PriceHistory", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceCharac_PriceHistory(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(
				mPriceBiometricService.getPriceCharac_PriceHistory(pRequestModel), HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price Characteristics grid in
	 * Price Biometric Dashboard-> Price Char. button->Price Characteristics
	 * Customer Segment Grid.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceCharac_CustomerSegment", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceCharac_CustomerSegment(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(
				mPriceBiometricService.getPriceCharac_CustomerSegment(pRequestModel), HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price Characteristics grid in
	 * Price Biometric Dashboard-> Price Char. button->Price Characteristics
	 * Fare Brand Grid.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceCharac_FareBrand", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceCharac_FareBrand(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getPriceCharac_FareBrand(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price Characteristics grid in
	 * Price Biometric Dashboard. Price Biometric Dashboard-> Price Char.
	 * button->Price Characteristics Channels Grid.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceCharac_Channels", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceCharac_Channels(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getPriceCharac_Channels(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price Quote in Price Biometric
	 * Dashboard-> Price Quote button->Price Quote Grid.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceQuote", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceQuote(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getPriceQuote(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price HeatMap in Price
	 * Biometric Dashboard-> Price Heat map button->Price Heat Map OD Grid.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceHeatMapODChannel", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceHeatMapODChannel(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getPriceHeatMapODChannel(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price HeatMap in Price
	 * Biometric Dashboard-> Price Heat map button->Price Heat Map customer
	 * segment Grid.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceHeatMapSegment", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceHeatMapSegment(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(
				mPriceBiometricService.getPriceHeatMap_CustomerSegment(pRequestModel), HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price HeatMap in Price
	 * Biometric Dashboard-> Price Heat map button->Price Heat Map channels
	 * Grid.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceHeatMapChannels", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceHeatMapChannels(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getPriceHeatMapChannels(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price HeatMap in Price
	 * Biometric Dashboard-> Price Heat map button->Price Heat Map Fares Grid.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceHeatMapFares", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<BasicDBObject> getPriceHeatMapFares(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<BasicDBObject>(mPriceBiometricService.getPriceHeatMapFares(pRequestModel),
				HttpStatus.OK);
	}

	// Price Elasticity same as the Price Elasticity in KPI dashboard
	
	@RequestMapping(value = "/getVLYRPoc", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getVLYRPoc(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mPriceBiometricService.getVLYRPoc(pRequestModel),
				HttpStatus.OK);
	}
	
	

}
