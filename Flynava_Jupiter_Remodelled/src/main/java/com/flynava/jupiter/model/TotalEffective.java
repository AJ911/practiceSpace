package com.flynava.jupiter.model;

public class TotalEffective {

	private float paxYTD;
	private float paxVLYR;
	private float paxVTGT;

	private String pricePerformance;
	private String filterKey;

	private float priceElaticity;
	private String customerSegment;

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String fare_basis;
	private float fare;
	private String combinationkey;
	private String rbd;
	private String currency;

	private int Pax;
	private int Pax_lastyr;
	private float targetpax;
	private float revenue;
	private float revenuelastyr;
	private float revenuevlyr;
	private float effectiveness;

	public int getPax() {
		return Pax;
	}

	public void setPax(int pax) {
		Pax = pax;
	}

	public int getPax_lastyr() {
		return Pax_lastyr;
	}

	public void setPax_lastyr(int pax_lastyr) {
		Pax_lastyr = pax_lastyr;
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

	public String getFare_basis() {
		return fare_basis;
	}

	public void setFare_basis(String fare_basis) {
		this.fare_basis = fare_basis;
	}

	public float getFare() {
		return fare;
	}

	public void setFare(float fare) {
		this.fare = fare;
	}

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}

	public float getRevenue() {
		return revenue;
	}

	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}

	public float getTargetpax() {
		return targetpax;
	}

	public void setTargetpax(float targetpax) {
		this.targetpax = targetpax;
	}

	public float getPaxYTD() {
		return paxYTD;
	}

	public void setPaxYTD(float paxYTD) {
		this.paxYTD = paxYTD;
	}

	public float getPaxVLYR() {
		return paxVLYR;
	}

	public void setPaxVLYR(float paxVLYR) {
		this.paxVLYR = paxVLYR;
	}

	public float getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(float paxVTGT) {
		this.paxVTGT = paxVTGT;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

	public void setPricePerformance(String pricePerformance) {
		this.pricePerformance = pricePerformance;
	}

	public float getPriceElaticity() {
		return priceElaticity;
	}

	public void setPriceElaticity(float priceElaticity) {
		this.priceElaticity = priceElaticity;
	}

	public String getPricePerformance() {
		return pricePerformance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public float getRevenuevlyr() {
		return revenuevlyr;
	}

	public void setRevenuevlyr(float revenuevlyr) {
		this.revenuevlyr = revenuevlyr;
	}

	public float getEffectiveness() {
		return effectiveness;
	}

	public void setEffectiveness(float effectiveness) {
		this.effectiveness = effectiveness;
	}

	public float getRevenuelastyr() {
		return revenuelastyr;
	}

	public void setRevenuelastyr(float revenuelastyr) {
		this.revenuelastyr = revenuelastyr;
	}

	public String getFilterKey() {
		return filterKey;
	}

	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

}
