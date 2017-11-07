package com.flynava.jupiter.serviceInterface;

import java.util.Map;

import com.flynava.jupiter.model.RequestModel;
import com.mongodb.BasicDBObject;

/**
 * Service Interface for Customer Segment Dashboard
 * 
 * @author Anu Merin
 *
 */

public interface CustomerSegmentService {

	public Map<String, Object> getChannelSummary(RequestModel requestModel);

	public Map<String, Object> getFrequentFlyers(RequestModel mRequestModel);

	public Map<String, Object> getDistributors(RequestModel mRequestModel);

	public Map<String, Object> getDistributorDetails(RequestModel mRequestModel);

	public Map<String, Object> getChannelSummaryDirect(RequestModel mRequestModel);

	public Map<String, Object> getChannelSummaryIndirect(RequestModel mRequestModel);

	public Map<String, Object> getCustomerSegment(RequestModel requestModel);

	public Map<String, Object> getCustomerSegmentDetails(RequestModel requestModel);

	public BasicDBObject getPriceEffectiveness(RequestModel requestModel);

}
