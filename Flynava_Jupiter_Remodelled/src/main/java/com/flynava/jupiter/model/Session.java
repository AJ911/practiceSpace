package com.flynava.jupiter.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class Session {
	
	private String sessionTemp;
	
	public Session() {
	
	}

	public String getSessionTemp() {
		return sessionTemp;
	}

	public void setSessionTemp(String sessionTemp) {
		this.sessionTemp = sessionTemp;
	}

}
