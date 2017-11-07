package com.flynava.jupiter.model;

public class PriceHeatMapChannel {
	private String channel;
	private Double revenue;
	private Double revenueLastYear;
	private Double revenueTarget;
	private Double marketShare;
	private Double revenueVLYR;
	private Double revenueVTGT;
	private Double FMS;
	private String combinationKey;
	private Double ppi; // Price Performance Index
	private int hostEffectiveFares;
	private int hostIneffectiveFares;
	
	
	public int getHostEffectiveFares() {
		return hostEffectiveFares;
	}
	public void setHostEffectiveFares(int hostEffectiveFares) {
		this.hostEffectiveFares = hostEffectiveFares;
	}
	public int getHostIneffectiveFares() {
		return hostIneffectiveFares;
	}
	public void setHostIneffectiveFares(int hostIneffectiveFares) {
		this.hostIneffectiveFares = hostIneffectiveFares;
	}
	public Double getPpi() {
		return ppi;
	}
	public void setPpi(Double ppi) {
		this.ppi = ppi;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Double getRevenue() {
		return revenue;
	}
	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}
	public Double getRevenueLastYear() {
		return revenueLastYear;
	}
	public void setRevenueLastYear(Double revenueLastYear) {
		this.revenueLastYear = revenueLastYear;
	}
	public Double getRevenueTarget() {
		return revenueTarget;
	}
	public void setRevenueTarget(Double revenueTarget) {
		this.revenueTarget = revenueTarget;
	}
	public Double getMarketShare() {
		return marketShare;
	}
	public void setMarketShare(Double marketShare) {
		this.marketShare = marketShare;
	}
	public Double getRevenueVLYR() {
		return revenueVLYR;
	}
	public void setRevenueVLYR(Double revenueVLYR) {
		this.revenueVLYR = revenueVLYR;
	}
	public Double getRevenueVTGT() {
		return revenueVTGT;
	}
	public void setRevenueVTGT(Double revenueVTGT) {
		this.revenueVTGT = revenueVTGT;
	}
	public Double getFMS() {
		return FMS;
	}
	public void setFMS(Double fMS) {
		FMS = fMS;
	}
	public String getCombinationKey() {
		return combinationKey;
	}
	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}
	
	

}
