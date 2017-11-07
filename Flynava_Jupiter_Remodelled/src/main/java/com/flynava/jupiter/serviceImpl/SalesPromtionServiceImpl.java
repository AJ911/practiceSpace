package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.SalesPromotionDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.SalesPromotion;
import com.flynava.jupiter.serviceInterface.SalesPromotionService;
import com.mongodb.DBObject;

@Service
public class SalesPromtionServiceImpl implements SalesPromotionService {

	@Autowired
	SalesPromotionDao mSalesPromotionDao;

	@Override
	public Map<String, Object> getSalesPromotionBaseFare(RequestModel pRequestModel) {

		List<SalesPromotion> lbasefareList = new ArrayList<SalesPromotion>();
		ArrayList<DBObject> basefare = mSalesPromotionDao.getSalesPromotionBaseFare(pRequestModel);
		JSONArray salesData = new JSONArray(basefare);

		SalesPromotion lsales = null;
		for (int i = 0; i < salesData.length(); i++) {
			JSONObject lJSONObj = salesData.getJSONObject(i);
			lsales = new SalesPromotion();
			System.out.println("lPriceJSONObj" + lJSONObj);

			float fare = Float.parseFloat(lJSONObj.get("fare").toString());
			lsales.setBasefare(Float.toString(fare));

			String lFlag = "true";
			if (pRequestModel.getFlag() != null && !pRequestModel.getFlag().isEmpty()) {
				lFlag = pRequestModel.getFlag();
			}

			float discount = 0;
			if (pRequestModel.getDiscount() > 0) {
				discount = pRequestModel.getDiscount();

			}

			if (lFlag.equals("true")) {
				float afterDiscount = (fare - (fare * discount / 100));
				lsales.setFareAfterDiscount(Float.toString(afterDiscount));

			} else {
				float afterDiscount = fare - discount;
				lsales.setFareAfterDiscount(Float.toString(afterDiscount));
			}

			lbasefareList.add(lsales);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SalesPromotion", lbasefareList);
		return map;
	}

}
