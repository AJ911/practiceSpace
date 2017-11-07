package com.flynava.jupiter.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flynava.jupiter.util.Utility;

/***
 * 
 * @author avani
 *
 */
@Controller
public class AuthController {

	@Autowired
	private ModelAndView model;

	@RequestMapping(value = { "/", "/AdminLogin" }, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		if (!Utility.getAuthenticationName().getName().equals("anonymousUser")
				&& !Utility.getAuthenticationType().toString().equals("[ROLE_ANONYMOUS]")) {
			model.setViewName("forward:AdminInside");
			return model;

		}
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		if (logout != null) {

			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("Login/index");
		return model;

	}

	@RequestMapping(value = "/AdminInside", method = RequestMethod.GET)
	public @ResponseBody ModelAndView dashboardLogin(HttpServletRequest request) {
		if (Utility.getAuthenticationType().toString().contains("ROLE_Network")) {
			model.addObject("role", "Role Network");
			model.addObject("userName", Utility.getAuthenticationName().getName());
			model.setViewName("Admin/index");
		} else if (Utility.getAuthenticationType().toString().contains("ROLE_Network")) {
			model.addObject("role", "Role Network");
			model.addObject("userName", Utility.getAuthenticationName().getName());
			model.setViewName("Users/index");
		}
		return model;

	}

}
