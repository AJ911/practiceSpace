/**
 * 
 */
package com.flynava.jupiter.model;

/**
 * @author Anu Merin
 *
 */
public class PriceFareBrandResponse {

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	
	private String fareBrand;
	private String fareRules;
	
	private String priceElasticity;
	
	private String revenueYTD;
	private String revenueVLYR;
	private String paxYTD;
	private String paxVLYR;
	
	private String paxPercentage;
	private String revenuePercentage;
	
	
	
	
	
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
	
	public String getFareBrand() {
		return fareBrand;
	}
	public void setFareBrand(String fareBrand) {
		this.fareBrand = fareBrand;
	}
	public String getFareRules() {
		return fareRules;
	}
	public void setFareRules(String fareRules) {
		this.fareRules = fareRules;
	}
	public String getPaxPercentage() {
		return paxPercentage;
	}
	public void setPaxPercentage(String paxPercentage) {
		this.paxPercentage = paxPercentage;
	}
	public String getRevenuePercentage() {
		return revenuePercentage;
	}
	public void setRevenuePercentage(String revenuePercentage) {
		this.revenuePercentage = revenuePercentage;
	}
	

	
	
}
