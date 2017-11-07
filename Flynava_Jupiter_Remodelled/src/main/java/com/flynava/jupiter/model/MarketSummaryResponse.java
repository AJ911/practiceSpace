package com.flynava.jupiter.model;

public class MarketSummaryResponse {

	private String year;
	private String region;
	private String country;
	private String pos;
	private String compartment;
	private int marketSize;
	private String marketgrowth;
	private float paxPerDay;
	private float overallNetworkGrowth;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
	public String getCompartment() {
		return compartment;
	}
	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}
	public int getMarketSize() {
		return marketSize;
	}
	public void setMarketSize(int marketSize) {
		this.marketSize = marketSize;
	}
	
	public float getPaxPerDay() {
		return paxPerDay;
	}
	public void setPaxPerDay(float paxPerDay) {
		this.paxPerDay = paxPerDay;
	}
	public float getOverallNetworkGrowth() {
		return overallNetworkGrowth;
	}
	public void setOverallNetworkGrowth(float overallNetworkGrowth) {
		this.overallNetworkGrowth = overallNetworkGrowth;
	}
	public String getMarketgrowth() {
		return marketgrowth;
	}
	public void setMarketgrowth(String marketgrowth) {
		this.marketgrowth = marketgrowth;
	}
	

}
