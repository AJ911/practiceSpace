package com.flynava.jupiter.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.UserDao;
import com.flynava.jupiter.model.Session;
import com.flynava.jupiter.model.User;
import com.flynava.jupiter.model.UserProfile;
import com.flynava.jupiter.serviceInterface.UserService;
import com.flynava.jupiter.util.Utility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	Session customSession;

	public boolean verifyUser(User user, HttpSession session) {

		boolean result = false;
		String userName = user.getUsername();
		String passwordUI = user.getPassword();
		System.out.println("password UI: " + passwordUI);

		User userDB = userDao.getUser("username", userName);
		if(userDB != null)
			session.setAttribute("role", userDB.getRole());

		if (userDB != null) {
			String passwordDB = userDB.getPassword();
			byte[] salt;
			salt = userDB.getSalt().getBytes();

			result = Utility.verifyHashedPassword(passwordDB, passwordUI, salt);
			if (result) {
				customSession.setSessionTemp(Utility.getToken(userName, passwordUI));
			}

		}

		return result;

	}

	@Override
	public List<UserProfile> getUserProfile(String profileField, String profileFieldValue) {
		List<UserProfile> userProfiles = userDao.getUserProfile(profileField, profileFieldValue);
		return userProfiles;
	}
}
