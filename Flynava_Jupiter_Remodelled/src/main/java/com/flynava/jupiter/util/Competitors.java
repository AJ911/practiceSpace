package com.flynava.jupiter.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Competitors {
	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	public List<BasicDBObject> getCompetitors(String user, String user_level) {
		String dbName = null;
		dbName = Constants.DB_NAME;
		String collection = "JUP_DB_Competitor_Config";
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(dbName);
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("user", user);
		whereQuery.put("userlevel", user_level);

		BasicDBObject document = new BasicDBObject();
		document.put("$and", whereQuery);
		DBObject match = new BasicDBObject("$match", document);
		DBObject project = new BasicDBObject("$project", new BasicDBObject("competitors", 1));

		AggregationOutput output = db.getCollection(collection).aggregate(match, project);

		for (DBObject result : output.results()) {
			System.out.println(result);

		}

		DBCursor cursor = null;
		cursor = db.getCollection(collection).find(document);

		List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
		if (cursor != null) {
			while (cursor.hasNext()) {
				resultList.add((BasicDBObject) cursor.next());
			}
		}
		return resultList;

	}

}
