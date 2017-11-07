package com.flynava.jupiter.model;

import java.util.List;

public class MarketOutlookReport {

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private int marketSize;
	private int marketSize_1;
	private int marketSizeVLYR;

	private List<Object> airline;

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

	public List<Object> getAirline() {
		return airline;
	}

	public void setAirline(List<Object> airline) {
		this.airline = airline;
	}

	public int getMarketSize() {
		return marketSize;
	}

	public void setMarketSize(int marketSize) {
		this.marketSize = marketSize;
	}

	public int getMarketSize_1() {
		return marketSize_1;
	}

	public void setMarketSize_1(int marketSize_1) {
		this.marketSize_1 = marketSize_1;
	}

	public int getMarketSizeVLYR() {
		return marketSizeVLYR;
	}

	public void setMarketSizeVLYR(int marketSizeVLYR) {
		this.marketSizeVLYR = marketSizeVLYR;
	}

}