package com.flynava.jupiter.controller;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flynava.jupiter.model.ManualTrgrsReqModel;
import com.flynava.jupiter.serviceInterface.FareUpdateService;
import com.flynava.jupiter.util.Response;
import com.mongodb.DBObject;

@Controller
public class FareUpdateController {

	@Autowired
	Response response;
	@Autowired
	FareUpdateService mFareUpdateService;

	@RequestMapping(value = "/getFareUpdate", method = RequestMethod.POST)
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<DBObject>> getFareUpdate(@RequestBody ManualTrgrsReqModel pRequestModel) {
		return new ResponseEntity<List<DBObject>>(mFareUpdateService.getFareUpdate(pRequestModel), HttpStatus.OK);
	}

}
