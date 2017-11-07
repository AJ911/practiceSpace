package com.flynava.jupiter.serviceInterface;

import java.util.List;

import com.flynava.jupiter.model.ManualTrgrsReqModel;
import com.flynava.jupiter.model.ManualTrigger;

public interface ManualTriggerService {

	public List<ManualTrigger> getManualTriggers(ManualTrgrsReqModel pRequestModel);

}
