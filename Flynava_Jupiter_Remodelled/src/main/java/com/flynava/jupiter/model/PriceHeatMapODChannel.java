package com.flynava.jupiter.model;

public class PriceHeatMapODChannel {
	private String origin;
	private String destination;
	private String channel;
	private String segment;
	private Double revenue;
	private Double revenueLastYear;
	private Double revenueTarget;
	private Double marketShare;
	private Double revenueVLYR;
	private Double revenueVTGT;
	private Double FMS;
	private String combinationKeyOd;
	private String combinationKeyChannel;
	private Double ppi; // Price Performance Index
	private int hostEffectiveFares;
	private int hostIneffectiveFares;
	private Double hostMarketShare;

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public Double getHostMarketShare() {
		return hostMarketShare;
	}

	public void setHostMarketShare(Double hostMarketShare) {
		this.hostMarketShare = hostMarketShare;
	}

	public String getCombinationKeyChannel() {
		return combinationKeyChannel;
	}

	public void setCombinationKeyChannel(String combinationKeyChannel) {
		this.combinationKeyChannel = combinationKeyChannel;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

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

	public String getCombinationKey() {
		return combinationKeyOd;
	}

	public void setCombinationKey(String combinationKey) {
		this.combinationKeyOd = combinationKey;
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
}
