package com.flynava.jupiter.daoInterface;

import java.util.ArrayList;

import com.flynava.jupiter.model.RequestModel;
import com.mongodb.DBObject;

public interface SalesReviewDao {

	ArrayList<DBObject> getTopTenOd(RequestModel pRequestModel);

}
