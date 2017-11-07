package com.flynava.jupiter.serviceInterface;

import java.util.Map;

import com.flynava.jupiter.model.RequestModel;

public interface SalesPromotionService {

	Map<String, Object> getSalesPromotionBaseFare(RequestModel pRequestModel);

}
