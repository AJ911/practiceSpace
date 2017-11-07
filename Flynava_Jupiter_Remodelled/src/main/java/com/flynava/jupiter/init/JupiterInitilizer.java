package com.flynava.jupiter.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoImpl.ManualTriggerDaoImpl;
import com.flynava.jupiter.daoInterface.ManualTriggerDao;
import com.mongodb.DBObject;

@Service
public class JupiterInitilizer {

	static ManualTriggerDao manualTriggerDao = new ManualTriggerDaoImpl();

	private static volatile JupiterInitilizer initObj = new JupiterInitilizer();;

	private JupiterInitilizer() {

	}

	public static JupiterInitilizer getInitInstance() {

		return initObj;
	}

	public static Map<String, List<DBObject>> getManualTrigger() {

		List<DBObject> dbObjList = manualTriggerDao.getManualTriggers(null, null);

		Map<String, List<DBObject>> manualTriggerMap = new HashMap<String, List<DBObject>>();

		manualTriggerMap.put("manualTriggerMaster", dbObjList);

		return manualTriggerMap;

	}

}
