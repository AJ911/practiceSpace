package com.flynava.jupiter.serviceInterface;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.flynava.jupiter.model.CurrencyTable;
import com.flynava.jupiter.model.RequestModel;
import com.mongodb.BasicDBObject;

public interface ExchangeRateService {
	public Map<String, Object> getCurrencyTable(RequestModel requestModel);
	
	public Map<String, Object> getYQTable(RequestModel requestModel);

	
}
