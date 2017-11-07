package com.flynava.jupiter.serviceImpl;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.FareUpdateDao;
import com.flynava.jupiter.model.ManualTrgrsReqModel;
import com.flynava.jupiter.serviceInterface.FareUpdateService;
import com.mongodb.DBObject;

@Service
public class FareUpdateServiceImpl implements FareUpdateService {

	@Autowired
	FareUpdateDao mFareUpdateDao;

	@Override
	public List<DBObject> getFareUpdate(ManualTrgrsReqModel pRequestModel) {
		List<DBObject> dbObjList = mFareUpdateDao.getFareUpdate(pRequestModel);

		if (dbObjList != null) {
			for (int i = 0; i < dbObjList.size(); i++) {
				DBObject dbObj = dbObjList.get(i);

				try {

					JSONObject forcstObj = null;
					if (dbObj.containsKey("forecast") && dbObj.get("forecast") != null)
						forcstObj = new JSONObject(dbObj.get("forecast").toString());

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
		}

		return dbObjList;
	}

}
