package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PriceBiometricRevenueSplitModel {

	private float revenueSalesYTD;
	private float revenueFlownYTD;
	private String revenueVLYR;
	private String revenueVTGT;

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String combinationkey;
	private float totalflownrevenue;
	private float totalflownrevenuelastyr;
	private float hostcapacity;
	private float hostcapacitylastyr;

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

	public float getRevenueSalesYTD() {
		return revenueSalesYTD;
	}

	public void setRevenueSalesYTD(float revenueSalesYTD) {
		this.revenueSalesYTD = revenueSalesYTD;
	}

	public float getRevenueFlownYTD() {
		return revenueFlownYTD;
	}

	public void setRevenueFlownYTD(float revenueFlownYTD) {
		this.revenueFlownYTD = revenueFlownYTD;
	}

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
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

	public float getHostcapacity() {
		return hostcapacity;
	}

	public void setHostcapacity(float hostcapacity) {
		this.hostcapacity = hostcapacity;
	}

	public float getHostcapacitylastyr() {
		return hostcapacitylastyr;
	}

	public void setHostcapacitylastyr(float hostcapacitylastyr) {
		this.hostcapacitylastyr = hostcapacitylastyr;
	}

	public float getTotalflownrevenue() {
		return totalflownrevenue;
	}

	public void setTotalflownrevenue(float totalflownrevenue) {
		this.totalflownrevenue = totalflownrevenue;
	}

	public float getTotalflownrevenuelastyr() {
		return totalflownrevenuelastyr;
	}

	public void setTotalflownrevenuelastyr(float totalflownrevenuelastyr) {
		this.totalflownrevenuelastyr = totalflownrevenuelastyr;
	}

}
