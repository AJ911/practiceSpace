package com.flynava.jupiter.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.UserDao;
import com.flynava.jupiter.model.User;
import com.flynava.jupiter.model.UserProfile;
import com.flynava.jupiter.util.Constants;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	MongoTemplate mongoTemplate;

	public User getUser(String userName, String userNameValue) {

		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put(userName, userNameValue);

		DBObject resultObj = (mongoTemplate.getDb().getMongo().getDB(Constants.DB_NAME).getCollection("JUP_DB_User")
				.findOne(whereQuery));

		User user = new User();
		user.setUsername(resultObj.get("username").toString());
		user.setFirstname(resultObj.get("first_name").toString());
		user.setLastname(resultObj.get("last_name").toString());
		user.setEmail(resultObj.get("email").toString());
		user.setRole(resultObj.get("role").toString());
		user.setPassword(resultObj.get("password").toString());

		// findOne(query, User.class, "JUP_DB_User");

		return user;
	}

	@Override
	public List<UserProfile> getUserProfile(String profileField, String profileFieldValue) {

		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put(profileField, profileFieldValue);

		DBCursor cursor = (DBCursor) mongoTemplate.getDb().getMongo().getDB(Constants.DB_NAME)
				.getCollection("JUP_DB_User_Profile").findOne(whereQuery);

		List<UserProfile> userProfileList = new ArrayList<UserProfile>();
		while (cursor.hasNext()) {

			UserProfile userProfile = (UserProfile) cursor.next();
			userProfileList.add(userProfile);

		}

		return userProfileList;
	}

}
