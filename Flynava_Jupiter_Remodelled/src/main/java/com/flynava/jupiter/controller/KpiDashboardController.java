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
import com.flynava.jupiter.model.UserProfile;
import com.flynava.jupiter.serviceInterface.KpiService;
import com.flynava.jupiter.util.Response;

@Controller
public class KpiDashboardController {

	@Autowired
	KpiService mKpiService;

	@Autowired
	Response mResponse;

	/**
	 * @author-Afsheen
	 * @response Map<String, Object> significantOdList
	 * 
	 */

	/*
	 * This method provides the response for the BreakevenSeatFactor grid in Kpi
	 * Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getBreakevenSeatFactor", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getBreakevenSeatFactor(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getBreakevenSeatFactor(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the SignificantOd grid in Price
	 * Biometric Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getSignificantOd", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getSignificantOd(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getSignificantOd(pRequestModel), HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price Availability Index in
	 * Price Biometric Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceAvailabilityIndex", method = RequestMethod.POST)

	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceAvailabilityIndex(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getPriceAvailabilityIndex(pRequestModel),
				HttpStatus.OK);
	}

	/*
	 * This method provides the response for the Price Stability Index in kpi
	 * Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceStabilityIndex", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceStabilityIndex(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getPriceStabilityIndex(pRequestModel),
				HttpStatus.OK);

	}

	// TODO This can be deleted if it is not been ask to used by product team.
	@RequestMapping(value = "/getTotalEffective_IneffectiveFares", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)

	public ResponseEntity<Map<String, Object>> getTotalEffective_IneffectiveFares(
			@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getTotalEffective_IneffectiveFares(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getTotalEffectiveIneffectiveFares", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getTotalEffectiveIneffectiveFares(
			@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getTotalEffectiveIneffectiveFares(pRequestModel),
				HttpStatus.OK);
	}
	/*
	 * This method provides the response for the Revenue Split in kpi Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */

	@RequestMapping(value = "/getHostRevenueSplit", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getRevenueSplit(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getRevenueSplit(pRequestModel), HttpStatus.OK);

	}
	/*
	 * This method provides the response for the New Product in kpi Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */

	@RequestMapping(value = "/getNewProducts", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getNewProducts(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getNewProducts(pRequestModel), HttpStatus.OK);

	}

	/*
	 * This method provides the response for the Yield Rasm in kpi Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getYieldRASMSeatFactor", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getYield_RASM_Seat(@RequestBody RequestModel pRequestModel,
			UserProfile userProfile) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getYield_RASM_Seat(pRequestModel, userProfile),
				HttpStatus.OK);

	}

	/*
	 * This method provides the response for the Price Elasticity in kpi
	 * Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceElasticity", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceElasticity(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getPriceElasticity(pRequestModel), HttpStatus.OK);

	}

	/*
	 * This method provides the response for the Price Elasticity Range in kpi
	 * Dashboard.
	 * 
	 * @param pRequestModel
	 * 
	 * @return Response Entity<BasicDBObject>
	 */
	@RequestMapping(value = "/getPriceElasticityRange", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getPriceElasticityRange(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mKpiService.getPriceElasticityRange(pRequestModel),
				HttpStatus.OK);

	}

}