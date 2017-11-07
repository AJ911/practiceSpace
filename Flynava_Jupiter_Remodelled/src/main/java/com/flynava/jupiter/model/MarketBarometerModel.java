package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MarketBarometerModel {
	
	private String region;
	private String country;
	private String pos;
	private String compartment;
	
	private String combinationKey;
	
	private int agentCounts;
	private int friendsCounts;
	private int foesCounts;
	private int corporateCounts;
	private int dealCounts;
	private int consolidatorCounts;
	
	
	public int getCorporateCounts() {
		return corporateCounts;
	}
	public void setCorporateCounts(int corporateCounts) {
		this.corporateCounts = corporateCounts;
	}
	public int getDealCounts() {
		return dealCounts;
	}
	public void setDealCounts(int dealCounts) {
		this.dealCounts = dealCounts;
	}
	public int getConsolidatorCounts() {
		return consolidatorCounts;
	}
	public void setConsolidatorCounts(int consolidatorCounts) {
		this.consolidatorCounts = consolidatorCounts;
	}
	public String getCompartment() {
		return compartment;
	}
	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}
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
	public String getCombinationKey() {
		return combinationKey;
	}
	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}
	public int getAgentCounts() {
		return agentCounts;
	}
	public void setAgentCounts(int agentCounts) {
		this.agentCounts = agentCounts;
	}
	public int getFriendsCounts() {
		return friendsCounts;
	}
	public void setFriendsCounts(int friendsCounts) {
		this.friendsCounts = friendsCounts;
	}
	public int getFoesCounts() {
		return foesCounts;
	}
	public void setFoesCounts(int foesCounts) {
		this.foesCounts = foesCounts;
	}
	
	
}
