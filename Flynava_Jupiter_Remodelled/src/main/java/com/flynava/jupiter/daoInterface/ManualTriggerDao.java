package com.flynava.jupiter.daoInterface;

import java.util.List;

import com.flynava.jupiter.model.ManualTrgrsReqModel;
import com.mongodb.DBObject;

public interface ManualTriggerDao {

	public List<DBObject> getManualTriggers(ManualTrgrsReqModel pRequestModel, String collName);

}
