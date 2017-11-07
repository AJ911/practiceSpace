package com.flynava.jupiter.serviceInterface;

import java.util.Map;

import com.flynava.jupiter.model.RequestModel;

public interface FaresDashboardService {

	Map<String, Object> getFares(RequestModel pRequestModel);

	Map<String, Object> getFaresCompetitorSummary(RequestModel pRequestModel);

}
