package com.flynava.jupiter.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.FaresDashboardDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.FaresDashboardService;
import com.mongodb.DBObject;

@Service
public class FaresDashboardServiceImpl implements FaresDashboardService {

	@Autowired
	FaresDashboardDao mFaresDashboardDao;

	@Override
	public Map<String, Object> getFares(RequestModel pRequestModel) {
		List<DBObject> FaresObj = mFaresDashboardDao.getFares(pRequestModel);
		// List<DBObject> FaresotherfieldObj =
		// mFaresDashboardDao.getDocument(pRequestModel, "JUP_DB_Sales");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fares", FaresObj);

		return map;
	}

	public boolean valueExists(Object obj, String key) {

		boolean valExists = false;

		if (obj != null) {

			if (obj instanceof DBObject) {

				DBObject dbObj = (DBObject) obj;

				if (dbObj.containsKey(key) && dbObj.get(key) != null
						&& !dbObj.get(key).toString().equalsIgnoreCase("null")
						&& !dbObj.get(key).toString().equalsIgnoreCase("NA")) {

					valExists = true;

				}

			} else if (obj instanceof JSONObject) {

				JSONObject jsonObj = (JSONObject) obj;

				try {
					if (jsonObj.has(key) && jsonObj.get(key) != null
							&& !jsonObj.get(key).toString().equalsIgnoreCase("null")
							&& !jsonObj.get(key).toString().equalsIgnoreCase("NA")) {

						valExists = true;

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		return valExists;

	}

	@Override
	public Map<String, Object> getFaresCompetitorSummary(RequestModel pRequestModel) {
		return null;
	}

}
