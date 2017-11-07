package com.flynava.jupiter.controller;

import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.SalesPromotionService;
import com.flynava.jupiter.util.Response;

@Controller
public class SalesPromotionController {

	@Autowired
	Response response;
	@Autowired
	SalesPromotionService mSalesPromotionService;

	@RequestMapping(value = "/getSalesPromotionBaseFare", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, Object>> getSalesPromotionBaseFare(@RequestBody RequestModel pRequestModel) {
		return new ResponseEntity<Map<String, Object>>(mSalesPromotionService.getSalesPromotionBaseFare(pRequestModel),
				HttpStatus.OK);
	}

}
