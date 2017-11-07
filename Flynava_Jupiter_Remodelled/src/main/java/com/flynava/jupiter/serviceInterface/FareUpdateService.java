package com.flynava.jupiter.serviceInterface;

import java.util.List;

import com.flynava.jupiter.model.ManualTrgrsReqModel;
import com.mongodb.DBObject;

public interface FareUpdateService {

	List<DBObject> getFareUpdate(ManualTrgrsReqModel pRequestModel);

}
