package com.flynava.jupiter.model;

import java.util.HashMap;
import java.util.Map;

public class DistributorSummaryDetails {

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String distributor;
	private String channel;
	private Double pax;
	private Double paxLastYear;
	private Double paxTarget;
	private Double paxPerc;
	private Double paxVLYR;
	private Double paxVTGT;
	private Double revenue;
	private Double revenueLastYear;
	private Double revenueTarget;
	private Double revenueVLYR;
	private Double revenueVTGT;
	private Double revenueFlown;
	private Double revenuePerc;
	private Double yeild;
	private Double yeildVLYR;
	private Double averageFare;
	private Double fairMarketShare;
	private Double hostMarketShare;
	private Double hostMarketShareVLYR;
	private Double hostMarketShareVTGT;
	private Double comp1MarketShare;
	private Double comp2MarketShare;
	private Double comp3MarketShare;
	private Double comp4MarketShare;
	private Double comp5MarketShare;
	private Double distance;
	private Double paxDistance;
	private Double paxDistanceLastYear;
	private Double hostPax;
	private Double hostPaxLastYear;
	private Double comp1pax;
	private Double comp2pax;
	private Double comp3pax;
	private Double comp4pax;
	private Double comp5pax;
	private Double marketPaxComplete;
	private Double marketPaxLastYearComplete;
	Map<String, Double> carrierPaxMap = new HashMap<String, Double>();

	public Double getRevenuePerc() {
		return revenuePerc;
	}

	public void setRevenuePerc(Double revenuePerc) {
		this.revenuePerc = revenuePerc;
	}

	public Double getHostPax() {
		return hostPax;
	}

	public void setHostPax(Double hostPax) {
		this.hostPax = hostPax;
	}

	public Double getHostPaxLastYear() {
		return hostPaxLastYear;
	}

	public void setHostPaxLastYear(Double hostPaxLastYear) {
		this.hostPaxLastYear = hostPaxLastYear;
	}

	public Double getComp1pax() {
		return comp1pax;
	}

	public void setComp1pax(Double comp1pax) {
		this.comp1pax = comp1pax;
	}

	public Double getComp2pax() {
		return comp2pax;
	}

	public void setComp2pax(Double comp2pax) {
		this.comp2pax = comp2pax;
	}

	public Double getComp3pax() {
		return comp3pax;
	}

	public void setComp3pax(Double comp3pax) {
		this.comp3pax = comp3pax;
	}

	public Double getMarketPaxComplete() {
		return marketPaxComplete;
	}

	public void setMarketPaxComplete(Double marketPaxComplete) {
		this.marketPaxComplete = marketPaxComplete;
	}

	public Double getMarketPaxLastYearComplete() {
		return marketPaxLastYearComplete;
	}

	public void setMarketPaxLastYearComplete(Double marketPaxLastYearComplete) {
		this.marketPaxLastYearComplete = marketPaxLastYearComplete;
	}

	public Double getPaxDistanceLastYear() {
		return paxDistanceLastYear;
	}

	public void setPaxDistanceLastYear(Double paxDistanceLastYear) {
		this.paxDistanceLastYear = paxDistanceLastYear;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getPaxDistance() {
		return paxDistance;
	}

	public void setPaxDistance(Double paxDistance) {
		this.paxDistance = paxDistance;
	}

	public Double getRevenueFlown() {
		return revenueFlown;
	}

	public void setRevenueFlown(Double revenueFlown) {
		this.revenueFlown = revenueFlown;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Double getPax() {
		return pax;
	}

	public void setPax(Double pax) {
		this.pax = pax;
	}

	public Double getPaxLastYear() {
		return paxLastYear;
	}

	public void setPaxLastYear(Double paxLastYear) {
		this.paxLastYear = paxLastYear;
	}

	public Double getPaxTarget() {
		return paxTarget;
	}

	public void setPaxTarget(Double paxTarget) {
		this.paxTarget = paxTarget;
	}

	public Double getPaxPerc() {
		return paxPerc;
	}

	public void setPaxPerc(Double paxPerc) {
		this.paxPerc = paxPerc;
	}

	public Double getPaxVLYR() {
		return paxVLYR;
	}

	public void setPaxVLYR(Double paxVLYR) {
		this.paxVLYR = paxVLYR;
	}

	public Double getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(Double paxVTGT) {
		this.paxVTGT = paxVTGT;
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

	public Double getYeild() {
		return yeild;
	}

	public void setYeild(Double yeild) {
		this.yeild = yeild;
	}

	public Double getYeildVLYR() {
		return yeildVLYR;
	}

	public void setYeildVLYR(Double yeildVLYR) {
		this.yeildVLYR = yeildVLYR;
	}

	public Double getAverageFare() {
		return averageFare;
	}

	public void setAverageFare(Double averageFare) {
		this.averageFare = averageFare;
	}

	public Double getFairMarketShare() {
		return fairMarketShare;
	}

	public void setFairMarketShare(Double fairMarketShare) {
		this.fairMarketShare = fairMarketShare;
	}

	public Double getHostMarketShare() {
		return hostMarketShare;
	}

	public void setHostMarketShare(Double hostMarketShare) {
		this.hostMarketShare = hostMarketShare;
	}

	public Double getHostMarketShareVLYR() {
		return hostMarketShareVLYR;
	}

	public void setHostMarketShareVLYR(Double hostMarketShareVLYR) {
		this.hostMarketShareVLYR = hostMarketShareVLYR;
	}

	public Double getHostMarketShareVTGT() {
		return hostMarketShareVTGT;
	}

	public void setHostMarketShareVTGT(Double hostMarketShareVTGT) {
		this.hostMarketShareVTGT = hostMarketShareVTGT;
	}

	public Double getComp1MarketShare() {
		return comp1MarketShare;
	}

	public void setComp1MarketShare(Double comp1MarketShare) {
		this.comp1MarketShare = comp1MarketShare;
	}

	public Double getComp2MarketShare() {
		return comp2MarketShare;
	}

	public void setComp2MarketShare(Double comp2MarketShare) {
		this.comp2MarketShare = comp2MarketShare;
	}

	public Double getComp3MarketShare() {
		return comp3MarketShare;
	}

	public void setComp3MarketShare(Double comp3MarketShare) {
		this.comp3MarketShare = comp3MarketShare;
	}

	public Double getComp4MarketShare() {
		return comp4MarketShare;
	}

	public void setComp4MarketShare(Double comp4MarketShare) {
		this.comp4MarketShare = comp4MarketShare;
	}

	public Double getComp4pax() {
		return comp4pax;
	}

	public void setComp4pax(Double comp4pax) {
		this.comp4pax = comp4pax;
	}

	public Double getComp5pax() {
		return comp5pax;
	}

	public void setComp5pax(Double comp5pax) {
		this.comp5pax = comp5pax;
	}

	public Double getComp5MarketShare() {
		return comp5MarketShare;
	}

	public void setComp5MarketShare(Double comp5MarketShare) {
		this.comp5MarketShare = comp5MarketShare;
	}

	public Map<String, Double> getCarrierPaxMap() {
		return carrierPaxMap;
	}

	public void setCarrierPaxMap(Map<String, Double> carrierPaxMap) {
		this.carrierPaxMap = carrierPaxMap;
	}

}
