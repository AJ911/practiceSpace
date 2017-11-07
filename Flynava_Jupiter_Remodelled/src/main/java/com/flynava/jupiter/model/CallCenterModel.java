package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CallCenterModel {

	private String month;
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	
	
	private String saleRevenue;
	private String saleRevenue_lastyr;
	
	private String flownRevenue;
	private String flownRevenue_lastyr;
	
	private float totalRevenue;
	private float totalRevenue_lastyr;
	
	private float revenueYTD;
	private String revenueVLYR;
	private String revenueVTGT;
	
	private float targetRevenue;
	
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
	
	
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public float getTargetRevenue() {
		return targetRevenue;
	}
	public void setTargetRevenue(float targetRevenue) {
		this.targetRevenue = targetRevenue;
	}
	
	public float getRevenueYTD() {
		return revenueYTD;
	}
	public void setRevenueYTD(float revenueYTD) {
		this.revenueYTD = revenueYTD;
	}
	public String getRevenueVLYR() {
		return revenueVLYR;
	}
	public void setRevenueVLYR(String revenueVLYR) {
		this.revenueVLYR = revenueVLYR;
	}
	public String getRevenueVTGT() {
		return revenueVTGT;
	}
	public void setRevenueVTGT(String revenueVTGT) {
		this.revenueVTGT = revenueVTGT;
	}
	public float getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(float totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public float getTotalRevenue_lastyr() {
		return totalRevenue_lastyr;
	}
	public void setTotalRevenue_lastyr(float totalRevenue_lastyr) {
		this.totalRevenue_lastyr = totalRevenue_lastyr;
	}
	public String getSaleRevenue() {
		return saleRevenue;
	}
	public void setSaleRevenue(String saleRevenue) {
		this.saleRevenue = saleRevenue;
	}
	public String getSaleRevenue_lastyr() {
		return saleRevenue_lastyr;
	}
	public void setSaleRevenue_lastyr(String saleRevenue_lastyr) {
		this.saleRevenue_lastyr = saleRevenue_lastyr;
	}
	public String getFlownRevenue() {
		return flownRevenue;
	}
	public void setFlownRevenue(String flownRevenue) {
		this.flownRevenue = flownRevenue;
	}
	public String getFlownRevenue_lastyr() {
		return flownRevenue_lastyr;
	}
	public void setFlownRevenue_lastyr(String flownRevenue_lastyr) {
		this.flownRevenue_lastyr = flownRevenue_lastyr;
	}
	
	
}
