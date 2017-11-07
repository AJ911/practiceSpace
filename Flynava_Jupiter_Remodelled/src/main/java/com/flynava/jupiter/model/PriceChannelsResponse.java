/**
 * 
 */
package com.flynava.jupiter.model;

/**
 * @author Anu Merin
 *
 */
public class PriceChannelsResponse {

	private String region;
	private String country;
	private String pos;
	private String od;
	private String compartment;
	
	private String channel;
	
	private String priceElasticitySignal;
	
	private String revenuePercentage;
	private float revenueYTD;
	private float revenueVLYR;
	private float revenueVTGT;
	
	private String paxPercentage;
	private int paxYTD;
	private int paxVLYR;
	private int paxVTGT;
	
	
	

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

	
	public String getPriceElasticitySignal() {
		return priceElasticitySignal;
	}

	public void setPriceElasticitySignal(String priceElasticitySignal) {
		this.priceElasticitySignal = priceElasticitySignal;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRevenuePercentage() {
		return revenuePercentage;
	}

	public void setRevenuePercentage(String revenuePercentage) {
		this.revenuePercentage = revenuePercentage;
	}

	public String getPaxPercentage() {
		return paxPercentage;
	}

	public void setPaxPercentage(String paxPercentage) {
		this.paxPercentage = paxPercentage;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public int getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(int paxVTGT) {
		this.paxVTGT = paxVTGT;
	}

	

	
}
