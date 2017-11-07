package com.flynava.jupiter.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.UniversalDBDao;
import com.flynava.jupiter.model.QueryJupiter;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.SavedConfig;
import com.flynava.jupiter.util.Constants;
import com.google.gson.reflect.TypeToken;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author Surya This Class UniversalDBDaoImpl contains all the dao level
 *         functions which are generic and required by UI
 */
@Repository
public class UniversalDBDaoImpl implements UniversalDBDao {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	private static final Logger logger = Logger.getLogger(UniversalDBDaoImpl.class);

	@Override
	public List<BasicDBObject> getDBValues(RequestModel requestModel) {

		String dbName = null;
		if (requestModel.getDbName() != null && !requestModel.getDbName().isEmpty())
			dbName = requestModel.getDbName().toString();
		else
			dbName = Constants.DB_NAME;
		String collection = null;
		if (requestModel.getCollection() != null && !requestModel.getCollection().isEmpty())
			collection = requestModel.getCollection().toString();
		else
			collection = "JUP_DB_Airline_Competitor";
		String query = null;
		if (requestModel.getQuery() != null && !requestModel.getQuery().isEmpty())
			query = requestModel.getQuery().toString();
		else
			query = "";

		DBCursor resultCursor = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(dbName);
		try {
			if (query.isEmpty()) {
				resultCursor = db.getCollection(collection).find();
			} else {

				DBObject dbObject = (DBObject) JSON.parse(query);
				System.out.println("query dbobj" + dbObject.toString());
				resultCursor = db.getCollection(collection).find(dbObject);

			}
			System.out.println("resultCursor size: " + resultCursor.size());

		} catch (Exception e) {
			logger.error("getDBValues-Exception", e);
		}
		List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
		if (resultCursor != null) {
			while (resultCursor.hasNext()) {
				resultList.add((BasicDBObject) resultCursor.next());
			}
		}
		return resultList;
	}

	@Override
	public BasicDBObject getFunctionData(RequestModel requestModel) {

		BasicDBObject functionData = null;

		String dbName = null;
		if (requestModel.getDbName() != null && !requestModel.getDbName().isEmpty())
			dbName = requestModel.getDbName().toString();
		else
			dbName = Constants.DB_NAME;
		String functionName = null;
		if (requestModel.getFunctionName() != null && !requestModel.getFunctionName().isEmpty())
			functionName = requestModel.getFunctionName().toString();
		else
			functionName = "JUP_FN_Trigger_Types";

		String query = functionName + "()";
		Object resultObj = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(dbName);
		try {

			resultObj = db.eval(query);
			System.out.println("resultObj: " + ((BasicDBObject) resultObj).size());

		} catch (Exception e) {
			logger.error("getFunctionData-Exception", e);
		}
		functionData = (BasicDBObject) resultObj;

		return (BasicDBObject) resultObj;
	}

	@Override
	public int findcount(RequestModel requestModel) {

		String collectionName = "JUP_DB_OD_Master";
		int length = 0;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			length = (int) db.getCollection(collectionName).count();

		} catch (Exception e) {
			logger.error("findcount-Exception", e);
		}
		return length;
	}

	@Override
	public int upsert(JSONObject jsonObject, String id, String collection) {

		DBCollection lCollection = null;
		DB lDb = null;
		int lRow = 1;
		BasicDBObject basicdbObject = new BasicDBObject();

		try {

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			if (lDb != null) {
				lCollection = lDb.getCollection(collection);

			}

			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.append(Constants.COLUMN_NAME, new ObjectId(id));

			Map<String, Object> retMap = new com.google.gson.Gson().fromJson(jsonObject.toString(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			for (String s : retMap.keySet()) {
				if (!s.equals("flag")) {
					basicdbObject.put(s, retMap.get(s));
				}

			}
			if (retMap.containsValue("false")) {

				BasicDBObject newDoc = new BasicDBObject();
				newDoc.append("$set", basicdbObject);

				lCollection.update(searchQuery, newDoc);
			} else {
				BasicDBObject lnewDoc = new BasicDBObject();
				lnewDoc.append("$set", basicdbObject);

				lCollection.insert(lnewDoc);
			}
			lRow = 1;

		} catch (Exception e) {
			lRow = 0;
			e.printStackTrace();
		}

		return lRow;
	}

	@Override
	public List<BasicDBObject> getQueryValues(RequestModel requestModel) {

		String dbName = null;
		if (requestModel.getDbName() != null && !requestModel.getDbName().isEmpty())
			dbName = requestModel.getDbName().toString();
		else
			dbName = Constants.DB_NAME;
		String collection = null;
		if (requestModel.getCollection() != null && !requestModel.getCollection().isEmpty())
			collection = requestModel.getCollection().toString();
		else
			collection = "JUP_DB_Competitor_Config";
		String query[] = null;
		String query1 = null;
		String query2 = null;
		if (requestModel.getQuery() != null && !requestModel.getQuery().isEmpty()) {
			query = requestModel.getQuery().toString().split(":");
			query2 = query[0];
			query1 = query[1];
		} else {
			query = null;
		}
		DBCursor cursor = null;
		// DBCursor resultCursor = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(dbName);
		try {
			if (query == null) {
				cursor = db.getCollection(collection).find();
			} else {
				BasicDBObject document = new BasicDBObject();
				document.put(query2, query1);

				cursor = db.getCollection(collection).find(document);
				// DBObject dbObject = (DBObject) JSON.parse(query);
				System.out.println("cursor" + cursor);
				// resultCursor = db.getCollection(collection).find(dbObject);

			}
			System.out.println("cursor size: " + cursor.size());

		} catch (Exception e) {
			logger.error("getDBValues-Exception", e);
		}
		List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
		if (cursor != null) {
			while (cursor.hasNext()) {
				resultList.add((BasicDBObject) cursor.next());
			}
		}
		return resultList;
	}

	@Override
	public List<DBObject> getCompetitors(String user, String user_level) {
		String dbName = null;
		dbName = Constants.DB_NAME;
		String collection = "JUP_DB_Competitor_Config";
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(dbName);
		List<BasicDBObject> whereQuery = new ArrayList<BasicDBObject>();

		whereQuery.add(new BasicDBObject("user", user).append("user_level", user_level));

		BasicDBObject document = new BasicDBObject();
		document.put("$and", whereQuery);
		DBObject match = new BasicDBObject("$match", document);
		DBObject project = new BasicDBObject("$project", new BasicDBObject("competitors", 1));

		AggregationOutput output = db.getCollection(collection).aggregate(match, project);
		List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
		List<DBObject> resultList1 = new ArrayList<DBObject>();
		for (DBObject result : output.results()) {
			System.out.println(result);

			resultList1.add(result);

		}
		System.out.println("List" + resultList1);
		return resultList1;

	}

	@Override
	public Boolean getSaveCompetitorConfig(SavedConfig savedConfig) {
		String collection = null;
		if (savedConfig.getCollection() != null && !savedConfig.getCollection().isEmpty())
			collection = savedConfig.getCollection();
		else {
			collection = "JUP_DB_Competitor_Config";
		}
		String user = null;
		if (savedConfig.getDetails() != null && !savedConfig.getDetails().isEmpty())
			user = savedConfig.getDetails();
		else {

		}
		DBObject posDetails = (DBObject) JSON.parse(user);

		DBCollection lCollection = null;
		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

		try {
			if (db.collectionExists(collection)) {
				lCollection = db.getCollection(collection);
			}
			if (lCollection != null && db.collectionExists(collection))
				lCollection.insert(posDetails);
			String functionName = null;
			functionName = "JUP_FN_Trigger_Types";
			String query = functionName + "()";
			Object resultObj = null;

			resultObj = db.eval(query);
			System.out.println("resultObj: " + ((BasicDBObject) resultObj).size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	@Override
	public int universalUpdateQuery(QueryJupiter queryModel) {

		int lRow = 1;
		try {

			BasicDBObject searchQuery = new BasicDBObject();
			BasicDBObject updateField = new BasicDBObject();

			DB lDb = null;

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			DBCollection lUniversalCollection = null;
			if (lDb != null)
				lUniversalCollection = lDb.getCollection(queryModel.getCollName());

			if (queryModel != null && lUniversalCollection != null) {

				Map<String, String> queryMap;
				if (queryModel.getQueryFilds() != null) {

					queryMap = queryModel.getQueryFilds();

					for (Map.Entry<String, String> query : queryMap.entrySet()) {

						searchQuery.put(query.getKey(), query.getValue());

					}

				}

				Map<String, String> updateMap;
				if (queryModel.getQueryFilds() != null) {

					updateMap = queryModel.getUpdateFilds();

					for (Map.Entry<String, String> query : updateMap.entrySet()) {

						updateField.put(query.getKey(), query.getValue());

					}

				}

				if (queryModel.getOperation().contains("update"))
					lUniversalCollection.update(searchQuery, new BasicDBObject().append("$set", updateField), false,
							true);

				else if (queryModel.getOperation().contains("insert"))
					lUniversalCollection.update(searchQuery, new BasicDBObject().append("$set", updateField), true,
							true);

			}

		} catch (Exception e) {

			logger.error("UniversalUpdateQuery-function", e);

			lRow = 0;

		}

		return lRow;
	}

	@Override
	public List<Object> universalFetchQuery(QueryJupiter queryModel) {

		List<Object> dbObjList = new ArrayList<Object>();
		try {

			BasicDBObject searchQuery = new BasicDBObject();
			DBCursor cursor = null;

			DB lDb = null;

			if (mongoTemplateDemoDB != null)
				lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);

			DBCollection lUniversalCollection = null;
			if (lDb != null)
				lUniversalCollection = lDb.getCollection(queryModel.getCollName());

			if (queryModel != null && lUniversalCollection != null) {

				Map<String, String> queryMap;
				if (queryModel.getQueryFilds() != null) {

					queryMap = queryModel.getQueryFilds();

					for (Map.Entry<String, String> query : queryMap.entrySet()) {

						searchQuery.put(query.getKey(), query.getValue());

					}

				}

				if (queryModel.getOperation().contains("fetch"))
					cursor = lUniversalCollection.find(searchQuery);

				if (cursor != null) {
					while (cursor.hasNext()) {
						DBObject dbObj = cursor.next();

						dbObjList.add(dbObj);
					}
				}

			}

		} catch (Exception e) {

			logger.error("UniversalFetchQuery-function", e);

		}

		return dbObjList;
	}

	public Boolean universalValidateQuery(RequestModel requestModel){
		
		Boolean valResult=false;
		try {

			DB lDb = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
			DBCollection lCollection = lDb.getCollection("JUP_DB_WorkPackage");
			
			BasicDBObject whereQuery = new BasicDBObject();
			
			whereQuery.append("WorkPackageName", requestModel.getWorkpackagename());
			whereQuery.append("User", requestModel.getWorkpackageuser());
			
			DBCursor dbCursor = lCollection.find(whereQuery);

			while (dbCursor.hasNext()) {
				
				valResult=true;
				dbCursor.next();
			}
		
		} catch(Exception e){
		
			logger.error("UniversalValidateQuery-function", e);
		}
		return valResult;
	}
}
