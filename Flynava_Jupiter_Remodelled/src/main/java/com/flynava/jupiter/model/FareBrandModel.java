/**
 * 
 */
package com.flynava.jupiter.model;

/**
 * @author Anu Merin
 *
 */
public class FareBrandModel {

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private float revenue;
	private float revenue_lastyr;
	private float targetRevenue;
	
	private int targetPax;
	
	private String priceElasticitySignal;
	
	private float hostRevenue;
	private float hostRevenue_lastyr;
	
	private int hostPax;
	private int hostPax_lastyr;
	
	
	private String proximityIndicator;
	private String combinationKey;
	
	private float totalRevenue;
	private float totalRevenue_lastyr;
	private int totalPax;
	private int totalPax_lastyr;
	
	private float revenueYTD;
	private float revenueVLYR;
	private float revenueVTGT;
	
	private int paxYTD;
	private int paxVLYR;
	private float paxVTGT;
	
	private int yieldYTD;
	private float yieldVLYR;
	private float yieldVTGT;
	private float avgFare;
	
	
	private String fareBrand;
	private String fareRule;
	private float percentageRevenue;
	private float percentagePax;
	

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

	public float getRevenue() {
		return revenue;
	}

	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}

	public float getRevenue_lastyr() {
		return revenue_lastyr;
	}

	public void setRevenue_lastyr(float revenue_lastyr) {
		this.revenue_lastyr = revenue_lastyr;
	}

	public String getPriceElasticitySignal() {
		return priceElasticitySignal;
	}

	public void setPriceElasticitySignal(String priceElasticitySignal) {
		this.priceElasticitySignal = priceElasticitySignal;
	}

	public float getHostRevenue() {
		return hostRevenue;
	}

	public void setHostRevenue(float hostRevenue) {
		this.hostRevenue = hostRevenue;
	}

	public float getHostRevenue_lastyr() {
		return hostRevenue_lastyr;
	}

	public void setHostRevenue_lastyr(float hostRevenue_lastyr) {
		this.hostRevenue_lastyr = hostRevenue_lastyr;
	}

	public int getHostPax() {
		return hostPax;
	}

	public void setHostPax(int hostPax) {
		this.hostPax = hostPax;
	}

	public String getProximityIndicator() {
		return proximityIndicator;
	}

	public void setProximityIndicator(String proximityIndicator) {
		this.proximityIndicator = proximityIndicator;
	}

	public String getCombinationKey() {
		return combinationKey;
	}

	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}

	public float getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(float totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getHostPax_lastyr() {
		return hostPax_lastyr;
	}

	public void setHostPax_lastyr(int hostPax_lastyr) {
		this.hostPax_lastyr = hostPax_lastyr;
	}

	public float getTotalRevenue_lastyr() {
		return totalRevenue_lastyr;
	}

	public void setTotalRevenue_lastyr(float totalRevenue_lastyr) {
		this.totalRevenue_lastyr = totalRevenue_lastyr;
	}

	public int getTotalPax() {
		return totalPax;
	}

	public void setTotalPax(int totalPax) {
		this.totalPax = totalPax;
	}

	public int getTotalPax_lastyr() {
		return totalPax_lastyr;
	}

	public void setTotalPax_lastyr(int totalPax_lastyr) {
		this.totalPax_lastyr = totalPax_lastyr;
	}

	public float getRevenueYTD() {
		return revenueYTD;
	}

	public void setRevenueYTD(float revenueYTD) {
		this.revenueYTD = revenueYTD;
	}

	public float getRevenueVLYR() {
		return revenueVLYR;
	}

	public void setRevenueVLYR(float revenueVLYR) {
		this.revenueVLYR = revenueVLYR;
	}

	public float getRevenueVTGT() {
		return revenueVTGT;
	}

	public void setRevenueVTGT(float revenueVTGT) {
		this.revenueVTGT = revenueVTGT;
	}

	public int getPaxYTD() {
		return paxYTD;
	}

	public void setPaxYTD(int paxYTD) {
		this.paxYTD = paxYTD;
	}

	public int getPaxVLYR() {
		return paxVLYR;
	}

	public void setPaxVLYR(int paxVLYR) {
		this.paxVLYR = paxVLYR;
	}

	public int getYieldYTD() {
		return yieldYTD;
	}

	public void setYieldYTD(int yieldYTD) {
		this.yieldYTD = yieldYTD;
	}

	public float getYieldVLYR() {
		return yieldVLYR;
	}

	public void setYieldVLYR(float yieldVLYR) {
		this.yieldVLYR = yieldVLYR;
	}

	public float getYieldVTGT() {
		return yieldVTGT;
	}

	public void setYieldVTGT(float yieldVTGT) {
		this.yieldVTGT = yieldVTGT;
	}

	public float getAvgFare() {
		return avgFare;
	}

	public void setAvgFare(float avgFare) {
		this.avgFare = avgFare;
	}

	public float getTargetRevenue() {
		return targetRevenue;
	}

	public void setTargetRevenue(float targetRevenue) {
		this.targetRevenue = targetRevenue;
	}

	public int getTargetPax() {
		return targetPax;
	}

	public void setTargetPax(int targetPax) {
		this.targetPax = targetPax;
	}

	public float getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(float paxVTGT) {
		this.paxVTGT = paxVTGT;
	}

	public String getFareBrand() {
		return fareBrand;
	}

	public void setFareBrand(String fareBrand) {
		this.fareBrand = fareBrand;
	}

	public String getFareRule() {
		return fareRule;
	}

	public void setFareRule(String fareRule) {
		this.fareRule = fareRule;
	}

	public float getPercentageRevenue() {
		return percentageRevenue;
	}

	public void setPercentageRevenue(float percentageRevenue) {
		this.percentageRevenue = percentageRevenue;
	}

	public float getPercentagePax() {
		return percentagePax;
	}

	public void setPercentagePax(float percentagePax) {
		this.percentagePax = percentagePax;
	}

	
}
