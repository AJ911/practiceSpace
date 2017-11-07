package com.flynava.jupiter.serviceInterface;

import java.util.Map;

import com.flynava.jupiter.model.RequestModel;

/**
 * This is service class of Sales Review.
 * 
 * @author Afsheen
 * 
 */

public interface SalesReviewService {

	public Map<String, Object> getTopTenOd(RequestModel pRequestModel);

	public Map<String, Object> getTopTenAgent(RequestModel pRequestModel);

	public Map<String, Object> getTopTenFares(RequestModel pRequestModel);

}
