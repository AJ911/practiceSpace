package com.flynava.jupiter.daoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.AnalystDashboardDao;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.util.Constants;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@Repository
public class AnalystDashboardDaoImpl implements AnalystDashboardDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	@Override
	public ArrayList<DBObject> getAnalystfromUserProfile(RequestModel pRequestModel) {
		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String user = null;
		if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty())
			user = pRequestModel.getUser().toString();

		DBObject query = new QueryBuilder().start().and(new QueryBuilder().start().put("full_name").is(user).get())
				.get();

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		DBCollection coll = db.getCollection("JUP_DB_User");
		BasicDBObject document = new BasicDBObject();
		document.put("$and", query);
		DBObject match = new BasicDBObject("$match", query);
		DBObject project = new BasicDBObject("$project", new BasicDBObject("list_of_pos", 1));

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

	@Override
	public List<DBObject> getAnalystDistributorCount(ArrayList<String> list) {
		List<DBObject> dbObjList = new ArrayList<DBObject>();
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBObject query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("pos").in(list).get()).get();
		DBCollection colle = db.getCollection("JUP_DB_Sales");

		BasicDBObject document1 = new BasicDBObject();
		document1.put("$and", query1);
		DBObject match1 = new BasicDBObject("$match", query1);

		// List<DBObject> results = colle.distinct("distributor", query1);

		AggregationOutput agout = colle.aggregate(match1, new BasicDBObject("$group",
				new BasicDBObject("_id", "$distributor").append("count", new BasicDBObject("$sum", 1))));
		Iterator<DBObject> results = agout.results().iterator();

		DBObject obj = null;
		while (results.hasNext()) {
			obj = results.next();
			dbObjList.add(obj);
			System.out.println(dbObjList);

		}

		return dbObjList;
	}

	@Override
	public List<List<DBObject>> getAnalystAgentsCount(ArrayList<String> list) {
		List<DBObject> dbObjList = new ArrayList<DBObject>();
		List<DBObject> dbList = new ArrayList<DBObject>();
		List<List<DBObject>> dbtotalList = new ArrayList<List<DBObject>>();
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBObject query1 = new QueryBuilder().start().and(new QueryBuilder().start().put("pos").in(list).get()).get();
		DBCollection colle = db.getCollection("JUP_DB_Sales");
		BasicDBObject document1 = new BasicDBObject();
		document1.put("$and", query1);
		DBObject match1 = new BasicDBObject("$match", query1);

		AggregationOutput agout = colle.aggregate(match1, new BasicDBObject("$group",
				new BasicDBObject("_id", "$agent").append("count", new BasicDBObject("$sum", 1))));
		Iterator<DBObject> results = agout.results().iterator();

		DBObject obj = null;
		while (results.hasNext()) {
			obj = results.next();
			dbObjList.add(obj);
		}
		AggregationOutput agout1 = colle.aggregate(match1, new BasicDBObject("$group",
				new BasicDBObject("_id", "$distributor").append("count", new BasicDBObject("$sum", 1))));
		Iterator<DBObject> results1 = agout1.results().iterator();
		DBObject obj1 = null;
		while (results1.hasNext()) {
			obj1 = results1.next();
			dbList.add(obj1);
		}
		dbtotalList.add(dbList);
		dbtotalList.add(dbObjList);

		return dbtotalList;
	}

}
