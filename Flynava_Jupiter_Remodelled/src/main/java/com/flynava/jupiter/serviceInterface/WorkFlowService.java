package com.flynava.jupiter.serviceInterface;

import java.util.List;
import java.util.Map;

import com.flynava.jupiter.model.AggTrgrTypes;
import com.flynava.jupiter.model.Diffuser;
import com.flynava.jupiter.model.FlightAnalysisWorkflow;
import com.flynava.jupiter.model.InfareData;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.Upsert;
import com.flynava.jupiter.model.WorkPackage;
import com.flynava.jupiter.util.Response;
import com.mongodb.DBObject;

//service
public interface WorkFlowService {

	public Map<String, Object> getForcastAvailability(RequestModel mRequestModel);

	public List<DBObject> getWorkflowCompetitiorSummary(RequestModel mRequestModel);

	public Map<String, Object> addUser(RequestModel mRequestModel);

	public Response saveWorkPackage(WorkPackage pWorkPackage);

	public List<DBObject> getSavedTriggerAction();

	public List<DBObject> getWorkPackage();

	public List<DBObject> getWorkFlowFareAnalysis(RequestModel pRequestModel);

	public Response saveAction(List<Diffuser> pDiffuserList);

	public List<DBObject> getRBDDiffuser();

	public List<DBObject> getFareBrandDiffuser();

	public List<DBObject> getChannelDiffuser();

	public Response fileFare(String workPackageId);

	public Response checkRcmdFareChange(Diffuser pDiffuser);

	public Response insert(Upsert upsert);

	public List<Object> getTriggerRecmnd(RequestModel pRequestModel);

	public List<List<DBObject>> getTriggerRecords(RequestModel pRequestModel);

	public List<Map<String, List<FlightAnalysisWorkflow>>> getFlightAnalysis(RequestModel pRequestModel);

	public Map<String, List<DBObject>> getWorkflowInfareData(RequestModel pRequestModel);

	public Response withdrawWorkPackage(Diffuser pDiffuser);

	public Response getConfigDate(RequestModel pRequestModel);

	public Map<String, Object> getConfiguredDates(RequestModel pRequestModel);

	public AggTrgrTypes getAllTriggerTypes(RequestModel pRequestModel);

	Response savemodel(WorkPackage pWorkPackage, InfareData infare);

}
