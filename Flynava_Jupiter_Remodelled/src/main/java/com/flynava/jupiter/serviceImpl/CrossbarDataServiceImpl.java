package com.flynava.jupiter.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.CrossbarDataDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.serviceInterface.CrossbarDataService;

@Service
public class CrossbarDataServiceImpl implements CrossbarDataService{
	@Autowired
	CrossbarDataDao crossbarDataDao;

	@Override
	public Map<String, Object> getCrossbarData(RequestModel requestModel) {
		Map<String,Object> crossbarData= (Map<String, Object>) crossbarDataDao.getCrossbarData(requestModel);
		return crossbarData;
	}

}
