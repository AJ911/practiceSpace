package com.flynava.jupiter.serviceInterface;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.flynava.jupiter.model.User;
import com.flynava.jupiter.model.UserProfile;


public interface UserService {
	
	public boolean verifyUser(User user, HttpSession session);
	public List<UserProfile> getUserProfile(String profileField, String profileFieldValue);

}
