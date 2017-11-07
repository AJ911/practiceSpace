package com.flynava.jupiter.model;

public class MarketOutlookDhb {

	private String region;
	private int growingCount;
	private int decliningCount;
	private int nicheCount;
	private int matureCount;
	private String compartment;
	private String key;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getGrowingCount() {
		return growingCount;
	}

	public void setGrowingCount(int growingCount) {
		this.growingCount = growingCount;
	}

	public int getDecliningCount() {
		return decliningCount;
	}

	public void setDecliningCount(int decliningCount) {
		this.decliningCount = decliningCount;
	}

	public int getNicheCount() {
		return nicheCount;
	}

	public void setNicheCount(int nicheCount) {
		this.nicheCount = nicheCount;
	}

	public int getMatureCount() {
		return matureCount;
	}

	public void setMatureCount(int matureCount) {
		this.matureCount = matureCount;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

}
