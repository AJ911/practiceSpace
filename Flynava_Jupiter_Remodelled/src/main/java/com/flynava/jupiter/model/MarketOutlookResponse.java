package com.flynava.jupiter.model;

public class MarketOutlookResponse {

	
	private String region;
	private String country;
	private String pos;
	private String compartment;
	
	private String declineCounts;
	private String growingCounts;
	private String matureCounts;
	private String nicheCounts;
	
	private String combinationKey;
	
	
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getCompartment() {
		return compartment;
	}
	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}
	
	public String getDeclineCounts() {
		return declineCounts;
	}
	public void setDeclineCounts(String declineCounts) {
		this.declineCounts = declineCounts;
	}
	public String getGrowingCounts() {
		return growingCounts;
	}
	public void setGrowingCounts(String growingCounts) {
		this.growingCounts = growingCounts;
	}
	public String getMatureCounts() {
		return matureCounts;
	}
	public void setMatureCounts(String matureCounts) {
		this.matureCounts = matureCounts;
	}
	public String getNicheCounts() {
		return nicheCounts;
	}
	public void setNicheCounts(String nicheCounts) {
		this.nicheCounts = nicheCounts;
	}
	public String getCombinationKey() {
		return combinationKey;
	}
	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}
	
	

}
