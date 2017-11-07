package com.flynava.jupiter.model;

import java.util.Map;

public class QueryJupiter {

	private String operation;
	private Map<String, String> queryFilds;
	private Map<String, String> updateFilds;
	private String collName;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Map<String, String> getQueryFilds() {
		return queryFilds;
	}

	public void setQueryFilds(Map<String, String> queryFilds) {
		this.queryFilds = queryFilds;
	}

	public String getCollName() {
		return collName;
	}

	public void setCollName(String collName) {
		this.collName = collName;
	}

	public Map<String, String> getUpdateFilds() {
		return updateFilds;
	}

	public void setUpdateFilds(Map<String, String> updateFilds) {
		this.updateFilds = updateFilds;
	}

}
