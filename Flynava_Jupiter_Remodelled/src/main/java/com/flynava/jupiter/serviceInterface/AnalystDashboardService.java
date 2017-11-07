package com.flynava.jupiter.serviceInterface;

import java.util.Map;

import com.flynava.jupiter.model.RequestModel;

public interface AnalystDashboardService {

	Map<String, Object> getAnalystTotalDistributor(RequestModel pRequestModel);

	Map<String, Object> getAnalystTotalAgents(RequestModel pRequestModel);

}