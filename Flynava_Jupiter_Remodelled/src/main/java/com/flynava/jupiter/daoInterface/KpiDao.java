package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;

import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.UserProfile;
import com.mongodb.DBObject;

public interface KpiDao {

	ArrayList<DBObject> getBreakevenSeatFactor(RequestModel pRequestModel);

	ArrayList<DBObject> getSignificantOd(RequestModel pRequestModel);

	ArrayList<DBObject> getPriceAvailabilityIndex(RequestModel pRequestModel);

	ArrayList<DBObject> getPriceStabilityIndex(RequestModel pRequestModel);

	ArrayList<DBObject> getTotalEffective_IneffectiveFares(RequestModel pRequestModel);

	ArrayList<DBObject> getRevenueSplit(RequestModel pRequestModel);

	ArrayList<DBObject> getNewProducts(RequestModel pRequestModel);

	ArrayList<DBObject> getYield_RASM_Seat(RequestModel pRequestModel, UserProfile userProfile);

	ArrayList<DBObject> getPriceElasticity(RequestModel pRequestModel);

	public ArrayList<DBObject> getPriceElasticityRange(RequestModel pRequestModel);

	ArrayList<DBObject> getTotalEffectiveIneffectiveFares(RequestModel pRequestModel);

}
