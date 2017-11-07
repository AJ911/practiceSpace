package com.flynava.jupiter.model;

public class EntrantsLeavers {

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String od;
	private String compartment;
	private Double entrants;
	private Double leavers;
	private String combinationKey;

	public String getCombinationKey() {
		return combinationKey;
	}

	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
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

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	public Double getEntrants() {
		return entrants;
	}

	public void setEntrants(Double entrants) {
		this.entrants = entrants;
	}

	public Double getLeavers() {
		return leavers;
	}

	public void setLeavers(Double leavers) {
		this.leavers = leavers;
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

}
