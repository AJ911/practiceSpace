package com.flynava.jupiter.model;

/**
 * @author Anu Merin
 *
 */
public class PriceHistoryResponse {

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	
	private String priceElasticity;
	private String avgFare;
	
	private String revenueYTD;
	private String revenueVLYR;
	private String paxYTD;
	private String paxVLYR;
	
	private String yieldYTD;
	private String yieldVLYR;
	
	private String B_T_F;
	
	
	
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
	public String getPriceElasticity() {
		return priceElasticity;
	}
	public void setPriceElasticity(String priceElasticity) {
		this.priceElasticity = priceElasticity;
	}
	
	public String getRevenueYTD() {
		return revenueYTD;
	}
	public void setRevenueYTD(String revenueYTD) {
		this.revenueYTD = revenueYTD;
	}
	public String getRevenueVLYR() {
		return revenueVLYR;
	}
	public void setRevenueVLYR(String revenueVLYR) {
		this.revenueVLYR = revenueVLYR;
	}
	public String getPaxYTD() {
		return paxYTD;
	}
	public void setPaxYTD(String paxYTD) {
		this.paxYTD = paxYTD;
	}
	public String getPaxVLYR() {
		return paxVLYR;
	}
	public void setPaxVLYR(String paxVLYR) {
		this.paxVLYR = paxVLYR;
	}
	
	public String getAvgFare() {
		return avgFare;
	}
	public void setAvgFare(String avgFare) {
		this.avgFare = avgFare;
	}
	public String getYieldYTD() {
		return yieldYTD;
	}
	public void setYieldYTD(String yieldYTD) {
		this.yieldYTD = yieldYTD;
	}
	public String getYieldVLYR() {
		return yieldVLYR;
	}
	public void setYieldVLYR(String yieldVLYR) {
		this.yieldVLYR = yieldVLYR;
	}
	public String getB_T_F() {
		return B_T_F;
	}
	public void setB_T_F(String b_T_F) {
		B_T_F = b_T_F;
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
