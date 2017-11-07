package com.flynava.jupiter.serviceInterface;

import java.util.Map;

import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.UserProfile;

/*@author Afsheen
 *  
 * */
//This is service class of where all KPI dashboard sub screen function
// reside.
public interface KpiService {

	Map<String, Object> getBreakevenSeatFactor(RequestModel pRequestModel);

	Map<String, Object> getSignificantOd(RequestModel pRequestModel);

	Map<String, Object> getPriceAvailabilityIndex(RequestModel pRequestModel);

	Map<String, Object> getPriceStabilityIndex(RequestModel pRequestModel);

	Map<String, Object> getTotalEffective_IneffectiveFares(RequestModel pRequestModel);

	Map<String, Object> getRevenueSplit(RequestModel pRequestModel);

	Map<String, Object> getNewProducts(RequestModel pRequestModel);

	Map<String, Object> getYield_RASM_Seat(RequestModel pRequestModel, UserProfile userProfile);

	Map<String, Object> getPriceElasticity(RequestModel pRequestModel);

	public Map<String, Object> getPriceElasticityRange(RequestModel pRequestModel);

	Map<String, Object> getTotalEffectiveIneffectiveFares(RequestModel pRequestModel);

}