package com.flynava.jupiter.serviceInterface;

import java.util.List;
import java.util.Map;

import com.flynava.jupiter.model.AnalystPerformanceGrid;
import com.flynava.jupiter.model.RequestModel;

public interface AnalystService {

	public Map<String, Object> getAnalystEvents(RequestModel pRequestModel);

	public Map<String, Object> getAnalystChannelRevenue(RequestModel pRequestModel);

	public Map<String, Object> getAnalystChannelFare(RequestModel pRequestModel);

	public Map<String, Object> getAnalystDistributorRevenuePie(RequestModel pRequestModel);

	public Map<String, Object> getAnalystDistributorFarePie(RequestModel pRequestModel);

	public Map<String, Object> getAnalystCustomerRevenuePie(RequestModel pRequestModel);

	public Map<String, Object> getAnalystCustomerFarePie(RequestModel pRequestModel);

	public Map<String, Object> getAnalystFareMixtypePie(RequestModel pRequestModel);

	public Map<String, Object> getAnalystSqlTime(RequestModel pRequestModel);

	List<AnalystPerformanceGrid> getAnalystPerformanceGrid(RequestModel pRequestModel);

	public Map<String, Object> getAnalystFareType(RequestModel pRequestModel);

}
