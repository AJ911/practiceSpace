package com.flynava.jupiter.daoInterface;

import java.util.List;

import com.flynava.jupiter.model.User;
import com.flynava.jupiter.model.UserProfile;

public interface UserDao {

	public User getUser(String field, String fieldValue);

	public List<UserProfile> getUserProfile(String profileField, String profileFieldValue);

}
