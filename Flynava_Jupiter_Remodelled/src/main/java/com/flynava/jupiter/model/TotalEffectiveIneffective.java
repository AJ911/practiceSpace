package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TotalEffectiveIneffective {

	private float paxYTD;
	private float paxVLYR;
	private float paxVTGT;

	private float pricePerformanceYTD;
	private float pricePerformanceVLYR;
	private float pricePerformanceVTGT;

	private float priceElasticityYTD;
	private float priceElasticityVLYR;
	private float priceElasticityVTGT;

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String fare_basis;
	private float fare;
	private String combinationkey;

	private float totalPax;
	private int totalPax_lastyr;

	private float totalPricePerformance;
	private float totalPricePerformance_lastyr;
	private float targetpax;

	private float totalPriceElasticity;
	private float totalPriceElasticity_lastyr;

	private float totalrevenue;
	private float revenueytd;
	private String rbd;

	private float avgfare;
	private float totaltargetpax;

	public float getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(float paxVTGT) {
		this.paxVTGT = paxVTGT;
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

	public float getPricePerformanceYTD() {
		return pricePerformanceYTD;
	}

	public void setPricePerformanceYTD(float pricePerformanceYTD) {
		this.pricePerformanceYTD = pricePerformanceYTD;
	}

	public float getPricePerformanceVLYR() {
		return pricePerformanceVLYR;
	}

	public void setPricePerformanceVLYR(float pricePerformanceVLYR) {
		this.pricePerformanceVLYR = pricePerformanceVLYR;
	}

	public float getPricePerformanceVTGT() {
		return pricePerformanceVTGT;
	}

	public void setPricePerformanceVTGT(float pricePerformanceVTGT) {
		this.pricePerformanceVTGT = pricePerformanceVTGT;
	}

	public float getPriceElasticityYTD() {
		return priceElasticityYTD;
	}

	public void setPriceElasticityYTD(float priceElasticityYTD) {
		this.priceElasticityYTD = priceElasticityYTD;
	}

	public float getPriceElasticityVLYR() {
		return priceElasticityVLYR;
	}

	public void setPriceElasticityVLYR(float priceElasticityVLYR) {
		this.priceElasticityVLYR = priceElasticityVLYR;
	}

	public float getPriceElasticityVTGT() {
		return priceElasticityVTGT;
	}

	public void setPriceElasticityVTGT(float priceElasticityVTGT) {
		this.priceElasticityVTGT = priceElasticityVTGT;
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

	public int getTotalPax_lastyr() {
		return totalPax_lastyr;
	}

	public void setTotalPax_lastyr(int totalPax_lastyr) {
		this.totalPax_lastyr = totalPax_lastyr;
	}

	public float getTotalPricePerformance() {
		return totalPricePerformance;
	}

	public void setTotalPricePerformance(float totalPricePerformance) {
		this.totalPricePerformance = totalPricePerformance;
	}

	public float getTotalPricePerformance_lastyr() {
		return totalPricePerformance_lastyr;
	}

	public void setTotalPricePerformance_lastyr(float totalPricePerformance_lastyr) {
		this.totalPricePerformance_lastyr = totalPricePerformance_lastyr;
	}

	public float getTotalPriceElasticity() {
		return totalPriceElasticity;
	}

	public void setTotalPriceElasticity(float totalPriceElasticity) {
		this.totalPriceElasticity = totalPriceElasticity;
	}

	public float getTotalPriceElasticity_lastyr() {
		return totalPriceElasticity_lastyr;
	}

	public void setTotalPriceElasticity_lastyr(float totalPriceElasticity_lastyr) {
		this.totalPriceElasticity_lastyr = totalPriceElasticity_lastyr;
	}

	public float getTotalrevenue() {
		return totalrevenue;
	}

	public void setTotalrevenue(float totalrevenue) {
		this.totalrevenue = totalrevenue;
	}

	public float getRevenueytd() {
		return revenueytd;
	}

	public void setRevenueytd(float revenueytd) {
		this.revenueytd = revenueytd;
	}

	public float getAvgfare() {
		return avgfare;
	}

	public void setAvgfare(float avgfare) {
		this.avgfare = avgfare;
	}

	public float getTargetpax() {
		return targetpax;
	}

	public void setTargetpax(float targetpax) {
		this.targetpax = targetpax;
	}

	public float getTotaltargetpax() {
		return totaltargetpax;
	}

	public void setTotaltargetpax(float totaltargetpax) {
		this.totaltargetpax = totaltargetpax;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

	public float getTotalPax() {
		return totalPax;
	}

	public void setTotalPax(float totalPax) {
		this.totalPax = totalPax;
	}

}
