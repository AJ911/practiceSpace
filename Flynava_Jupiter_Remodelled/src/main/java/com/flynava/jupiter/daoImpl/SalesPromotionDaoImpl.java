package com.flynava.jupiter.daoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.SalesPromotionDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.util.Constants;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@Repository
public class SalesPromotionDaoImpl implements SalesPromotionDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	@Override
	public ArrayList<DBObject> getSalesPromotionBaseFare(RequestModel pRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String startDate = null;
		if (pRequestModel.getFromDate() != null && !pRequestModel.getFromDate().isEmpty())
			startDate = pRequestModel.getFromDate().toString();
		else {

			startDate = "2017-02-14";
		}
		String endDate = null;
		if (pRequestModel.getToDate() != null && !pRequestModel.getToDate().isEmpty())
			endDate = pRequestModel.getToDate().toString();
		else {

			endDate = "2017-02-14";
		}

		ArrayList<String> odList = null;
		if (pRequestModel.getOdArray() != null && pRequestModel.getOdArray().length > 0) {
			odList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getOdArray().length; i++) {
				odList.add(pRequestModel.getOdArray()[i]);
			}
		}

		ArrayList<String> compartmentList = null;
		if (pRequestModel.getCompartmentArray() != null && pRequestModel.getCompartmentArray().length > 0) {
			compartmentList = new ArrayList<String>();
			for (int i = 0; i < pRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(pRequestModel.getCompartmentArray()[i]);
			}
		}

		DBObject query = new QueryBuilder().start()
				.and(new QueryBuilder().start().put("OD").in(odList).put("compartment").in(compartmentList).get())
				.get();

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection coll = db.getCollection("JUP_DB_ATPCO_Fares");
		BasicDBObject document = new BasicDBObject();
		document.put("$and", query);
		DBObject match = new BasicDBObject("$match", query);
		DBObject project = new BasicDBObject("$project", new BasicDBObject("pos", 1).append("origin", 1)
				.append("destination", 1).append("compartment", 1).append("fare", 1));
		List<DBObject> pipeline = Arrays.asList(match, project);
		AggregationOutput output = (coll).aggregate(match, project);

		coll.aggregate(pipeline);

		ArrayList<DBObject> resultList = new ArrayList<DBObject>();
		for (DBObject result : output.results()) {
			System.out.println(result);

			resultList.add(result);
			//
		}

		return resultList;

	}

}
