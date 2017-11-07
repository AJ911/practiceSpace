package com.flynava.jupiter.serviceInterface;

import java.util.ArrayList;
import java.util.Map;

import com.flynava.jupiter.model.RequestModel;

public interface MainDashboardService {

	public Map<String, Object> getBookings(RequestModel pRequestModel);

	public Map<String, Object> getEvents(RequestModel pRequestModel);

	public Map<String, Object> getSales(RequestModel pRequestModel);

	public Object getIndustryBenchmark(RequestModel pRequestModel);

	public ArrayList getKpiIndex(RequestModel pRequestModel);

}
