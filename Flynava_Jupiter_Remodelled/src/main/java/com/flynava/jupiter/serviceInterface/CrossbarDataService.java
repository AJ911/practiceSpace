package com.flynava.jupiter.serviceInterface;

import java.util.Map;

import com.flynava.jupiter.model.RequestModel;

public interface CrossbarDataService {

	Map<String, Object> getCrossbarData(RequestModel requestModel);

}
