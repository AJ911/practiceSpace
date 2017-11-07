package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;
import java.util.List;

import com.flynava.jupiter.model.AggTrgrTypes;
import com.flynava.jupiter.model.Diffuser;
import com.flynava.jupiter.model.InfareData;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.Upsert;
import com.flynava.jupiter.model.WorkPackage;
import com.mongodb.DBObject;

public interface WorkFlowDao {

	List<DBObject> getTriggersRecommendation(RequestModel mRequestModel);

	public AggTrgrTypes getAllTriggerTypes(RequestModel mRequestModel);

	Double findTriggerLength(String str);

	ArrayList<DBObject> getForcastAvailability(RequestModel mRequestModel);

	ArrayList<DBObject> getWorkflowCompetitiorSummary(RequestModel mRequestModel);

	Boolean addUser(RequestModel mRequestModel);

	public int saveWorkPackage(WorkPackage pWorkPackage);

	public ArrayList<DBObject> getWorkPackage();

	public int saveAction(List<Diffuser> pDiffuserList);

	public ArrayList<DBObject> getSavedTriggerAction();

	public ArrayList<DBObject> getWorkFlowFareAnalysis(RequestModel pRequestModel);

	public ArrayList<DBObject> getRBDDiffuser();

	public ArrayList<DBObject> getFareBrandDiffuser();

	public ArrayList<DBObject> getChannelDiffuser();

	public int fileFare(String workPackageId);

	public int insert(Upsert upsert);

	public List<DBObject> getODTrgrData(RequestModel pRequestModel);

	public ArrayList<DBObject> getFlightAnalysis(RequestModel pRequestModel);

	public ArrayList<DBObject> getWorkflowInfareData(RequestModel pRequestModel);

	public int withdrawWorkPackage(Diffuser pDiffuser);

	public int getConfigdate(RequestModel pRequestModel);

	public ArrayList<DBObject> getConfiguredDates(RequestModel pRequestModel);

	public int getFlightDuration(String origin, String destination);

	List<DBObject> getRegion(RequestModel pRequestModel);

	int savemodel(WorkPackage pWorkPackage, InfareData infare);

	int savedublicate(List<Diffuser> pDiffuserList);

}
