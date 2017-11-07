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

import com.flynava.jupiter.model.AggTrgrTypes;
import com.flynava.jupiter.model.Diffuser;
import com.flynava.jupiter.model.FlightAnalysisWorkflow;
import com.flynava.jupiter.model.InfareData;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.Upsert;
import com.flynava.jupiter.model.WorkPackage;
import com.flynava.jupiter.serviceInterface.WorkFlowService;
import com.flynava.jupiter.util.Response;
import com.mongodb.DBObject;

/**
 * @author Surya This Controller WorkFlowController contains all the functions
 *         required in Workflow Dashboard
 */

@Controller
public class WorkFlowController {
	@Autowired
	Response response;
	@Autowired
	WorkFlowService mWorkFlowService;

	/**
	 * This method is used to get the Forcast Availability in the Workflow
	 * Dashboard.
	 * 
	 */

	@RequestMapping(value = "/getForcastAvailability", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getForcastAvailability(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mWorkFlowService.getForcastAvailability(pRequestModel),
				HttpStatus.OK);
	}

	/**
	 * This method is used to get the Competitor Summary in the Workflow
	 * Dashboard.
	 * 
	 */
	@RequestMapping(value = "/workflowCompetitorSummary", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<DBObject>> getWorkflowCompetitorSummary(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<List<DBObject>>(mWorkFlowService.getWorkflowCompetitiorSummary(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> addUser(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mWorkFlowService.addUser(pRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/saveWorkPackage", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> saveWorkPackage(@RequestBody WorkPackage pDiffuser) {
		return new ResponseEntity<Response>(mWorkFlowService.saveWorkPackage(pDiffuser), HttpStatus.OK);
	}

	@RequestMapping(value = "/savemodel", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> savemodel(@RequestBody WorkPackage pDiffuser, InfareData infare) {
		return new ResponseEntity<Response>(mWorkFlowService.savemodel(pDiffuser, infare), HttpStatus.OK);
	}

	@RequestMapping(value = "/getSavedTriggerAction", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<DBObject>> getSavedTriggerAction() {
		return new ResponseEntity<List<DBObject>>(mWorkFlowService.getSavedTriggerAction(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getWorkPackage", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<DBObject>> getWorkPackage() {
		return new ResponseEntity<List<DBObject>>(mWorkFlowService.getWorkPackage(), HttpStatus.OK);
	}

	@RequestMapping(value = "/saveAction", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> acceptAction(@RequestBody List<Diffuser> pDiffuserList) {
		return new ResponseEntity<Response>(mWorkFlowService.saveAction(pDiffuserList), HttpStatus.OK);
	}

	@RequestMapping(value = "/getWorkFlowFareAnalysis", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<DBObject>> getWorkFlowFareAnalysis(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<List<DBObject>>(mWorkFlowService.getWorkFlowFareAnalysis(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getRBDDiffuser", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<DBObject>> getRDBDiffuser() {
		return new ResponseEntity<List<DBObject>>(mWorkFlowService.getRBDDiffuser(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getFareBrandDiffuser", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<DBObject>> getFareBrandDiffuser() {
		return new ResponseEntity<List<DBObject>>(mWorkFlowService.getFareBrandDiffuser(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getChannelDiffuser", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<DBObject>> getChannelDiffuser() {
		return new ResponseEntity<List<DBObject>>(mWorkFlowService.getChannelDiffuser(), HttpStatus.OK);
	}

	@RequestMapping(value = "/filingFare", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> fileFare(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Response>(mWorkFlowService.fileFare(pRequestModel.getWorkPackageId()), HttpStatus.OK);
	}

	@RequestMapping(value = "/checkRcmdFareChange", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> checkRcmdFareChange(@RequestBody Diffuser pDiffuser) {
		return new ResponseEntity<Response>(mWorkFlowService.checkRcmdFareChange(pDiffuser), HttpStatus.OK);
	}

	@RequestMapping(value = "/getInsertFlightNumberDOW", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> Insert(@RequestBody Upsert upsert) {

		return new ResponseEntity<Response>(mWorkFlowService.insert(upsert), HttpStatus.OK);

	}

	@RequestMapping(value = "/getTriggerRecmnd", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Object>> getTriggerRecmnd(@RequestBody RequestModel pRequestModel) {

		return new ResponseEntity<List<Object>>(mWorkFlowService.getTriggerRecmnd(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getTriggerRecords", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<List<DBObject>>> getTriggerRecords(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<List<List<DBObject>>>(mWorkFlowService.getTriggerRecords(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getFlightAnalysisWorkflow", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Map<String, List<FlightAnalysisWorkflow>>>> getFlightAnalysis(
			@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<List<Map<String, List<FlightAnalysisWorkflow>>>>(
				mWorkFlowService.getFlightAnalysis(pRequestModel), HttpStatus.OK);
	}

	@RequestMapping(value = "/getWorkflowInfareData", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, List<DBObject>>> getWorkflowInfareData(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, List<DBObject>>>(mWorkFlowService.getWorkflowInfareData(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/withdrawWorkPackage", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> withdrawWorkPackage(@RequestBody Diffuser pDiffuser) {
		return new ResponseEntity<Response>(mWorkFlowService.withdrawWorkPackage(pDiffuser), HttpStatus.OK);
	}

	@RequestMapping(value = "/getConfigDate", method = RequestMethod.POST)

	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Response> getConfigDate(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Response>(mWorkFlowService.getConfigDate(pRequestModel), HttpStatus.OK);

	}

	@RequestMapping(value = "/getConfiguredDates", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)

	public ResponseEntity<Map<String, Object>> getConfiguredDates(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mWorkFlowService.getConfiguredDates(pRequestModel),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getTriggerTypes", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<AggTrgrTypes> getAllTriggerTypes(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<AggTrgrTypes>(mWorkFlowService.getAllTriggerTypes(pRequestModel), HttpStatus.OK);
	}

}
