package com.flynava.jupiter.model;

public class MarketBarometerDetails {
	
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String agentName;
	private String combinationKey;
	private Double marketShare;
	private Double revenue;
	private Double revenueVLYR;
	private Double hostPI;
	private Double comp1PI;
	private Double comp2PI;
	private Double comp3PI;
	private String airline;
	
	
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getCompartment() {
		return compartment;
	}
	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}
	public String getCombinationKey() {
		return combinationKey;
	}
	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
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
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public Double getMarketShare() {
		return marketShare;
	}
	public void setMarketShare(Double marketShare) {
		this.marketShare = marketShare;
	}
	public Double getRevenue() {
		return revenue;
	}
	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}
	public Double getRevenueVLYR() {
		return revenueVLYR;
	}
	public void setRevenueVLYR(Double revenueVLYR) {
		this.revenueVLYR = revenueVLYR;
	}
	public Double getHostPI() {
		return hostPI;
	}
	public void setHostPI(Double hostPI) {
		this.hostPI = hostPI;
	}
	public Double getComp1PI() {
		return comp1PI;
	}
	public void setComp1PI(Double comp1pi) {
		comp1PI = comp1pi;
	}
	public Double getComp2PI() {
		return comp2PI;
	}
	public void setComp2PI(Double comp2pi) {
		comp2PI = comp2pi;
	}
	public Double getComp3PI() {
		return comp3PI;
	}
	public void setComp3PI(Double comp3pi) {
		comp3PI = comp3pi;
	}
	
	
	

}
