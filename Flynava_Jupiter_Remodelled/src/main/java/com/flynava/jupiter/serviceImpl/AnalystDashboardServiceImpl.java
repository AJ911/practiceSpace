package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.AnalystDashboardDao;
import com.flynava.jupiter.model.AnalystDistributor;
import com.flynava.jupiter.model.AnalystLanding;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.AnalystDashboardService;
import com.mongodb.DBObject;

@Service
public class AnalystDashboardServiceImpl implements AnalystDashboardService {

	@Autowired
	AnalystDashboardDao mAnalystDashboardDao;

	@Override
	public Map<String, Object> getAnalystTotalDistributor(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDashboardDao.getAnalystfromUserProfile(pRequestModel);
		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);
			JSONArray pos = null;
			if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
				pos = new JSONArray(jsonObj.get("list_of_pos").toString());

			}

			for (int j = 0; j < pos.length(); j++) {
				list.add(pos.getString(j));
			}
		}
		List<DBObject> SalesObj = mAnalystDashboardDao.getAnalystDistributorCount(list);
		JSONArray data1 = new JSONArray(SalesObj);
		List<AnalystDistributor> pielist = new ArrayList<AnalystDistributor>();
		List<AnalystLanding> perclist = new ArrayList<AnalystLanding>();
		AnalystDistributor lpiechart = null;
		List<Integer> perc = new ArrayList<Integer>();
		perc.add(data1.length());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total_distributor_count", perc);
		return map;
	}

	@Override
	public Map<String, Object> getAnalystTotalAgents(RequestModel pRequestModel) {
		ArrayList<DBObject> FaresObj = mAnalystDashboardDao.getAnalystfromUserProfile(pRequestModel);
		JSONArray data = new JSONArray(FaresObj);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject jsonObj = data.getJSONObject(i);

			JSONArray pos = null;
			if (jsonObj.has("list_of_pos") && jsonObj.get("list_of_pos") != null
					&& !"null".equalsIgnoreCase(jsonObj.get("list_of_pos").toString())) {
				pos = new JSONArray(jsonObj.get("list_of_pos").toString());

			}

			for (int j = 0; j < pos.length(); j++) {
				list.add(pos.getString(j));
			}
		}
		List<List<DBObject>> SalesObj = mAnalystDashboardDao.getAnalystAgentsCount(list);
		JSONArray data1 = new JSONArray(SalesObj);
		List<Integer> perc = new ArrayList<Integer>();
		List<Integer> perc1 = new ArrayList<Integer>();

		List<AnalystDistributor> pielist = new ArrayList<AnalystDistributor>();
		List<AnalystLanding> perclist = new ArrayList<AnalystLanding>();
		AnalystDistributor lpiechart = null;
		JSONArray jsonObj = data1.getJSONArray(0);
		JSONArray jsonObj1 = data1.getJSONArray(1);
		perc.add(jsonObj.length());
		perc1.add(jsonObj1.length());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total_distributor_count", perc);
		map.put("total_agent_count", perc1);
		return map;
	}

}
