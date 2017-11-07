package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;

import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.UserDetails;
import com.flynava.jupiter.model.UserProfile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public interface ExchangeRateDao {

	public ArrayList<DBObject> getCurrencyTable(RequestModel requestModel);
	public ArrayList<DBObject> getYQTable(RequestModel requestModel);
	
	

	public UserDetails getUserDetails(RequestModel requestModel);

}
