package com.flynava.jupiter.model;

import java.util.HashMap;
import java.util.Map;

public class DirectIndirectChannelSummary {

	private String region;
	private String country;
	private String origin;
	private String destination;
	private String pos;
	private String compartment;
	private String channel;
	private float paxlastyear;
	private double targetpaxytd;
	private float revenueLastYear;
	private double targetrevenueytd;
	private double revenueshareperc;
	private float revenueytd;
	private double revenuevlyr;
	private double revenuevtgt;
	private double paxperc;
	private float paxytd;
	private double paxvlyr;
	private double paxvtgt;
	private float yieldytd;
	private float yiedvlyr;
	private String combinationkey;
	private float distance;
	private double averageFare;
	private double flownRevenue;
	private float distancePax;
	private double hostMarketShare;
	private double comp1MarketShare;
	private double comp2MarketShare;
	private double comp3MarketShare;
	private double comp4MarketShare;
	private double comp5MarketShare;
	private double totalMarketPax;
	private double comp1pax;
	private double comp2pax;
	private double comp3pax;
	private double comp4pax;
	private double comp5pax;
	private double hostpax;
	Map<String, Double> carrierPaxMap = new HashMap<String, Double>();

	public double getHostpax() {
		return hostpax;
	}

	public void setHostpax(double hostpax) {
		this.hostpax = hostpax;
	}

	public double getTotalMarketPax() {
		return totalMarketPax;
	}

	public void setTotalMarketPax(double totalMarketPax) {
		this.totalMarketPax = totalMarketPax;
	}

	public double getComp1pax() {
		return comp1pax;
	}

	public void setComp1pax(double comp1pax) {
		this.comp1pax = comp1pax;
	}

	public double getComp2pax() {
		return comp2pax;
	}

	public void setComp2pax(double comp2pax) {
		this.comp2pax = comp2pax;
	}

	public double getComp3pax() {
		return comp3pax;
	}

	public void setComp3pax(double comp3pax) {
		this.comp3pax = comp3pax;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public float getPaxlastyear() {
		return paxlastyear;
	}

	public void setPaxlastyear(float paxlastyear) {
		this.paxlastyear = paxlastyear;
	}

	public double getTargetpaxytd() {
		return targetpaxytd;
	}

	public void setTargetpaxytd(double targetpaxytd) {
		this.targetpaxytd = targetpaxytd;
	}

	public float getRevenueLastYear() {
		return revenueLastYear;
	}

	public void setRevenueLastYear(float revenueLastYear) {
		this.revenueLastYear = revenueLastYear;
	}

	public double getTargetrevenueytd() {
		return targetrevenueytd;
	}

	public void setTargetrevenueytd(double targetrevenueytd) {
		this.targetrevenueytd = targetrevenueytd;
	}

	public double getRevenueshareperc() {
		return revenueshareperc;
	}

	public void setRevenueshareperc(double revenueshareperc) {
		this.revenueshareperc = revenueshareperc;
	}

	public float getRevenueytd() {
		return revenueytd;
	}

	public void setRevenueytd(float revenueytd) {
		this.revenueytd = revenueytd;
	}

	public double getRevenuevlyr() {
		return revenuevlyr;
	}

	public void setRevenuevlyr(double revenuevlyr) {
		this.revenuevlyr = revenuevlyr;
	}

	public double getRevenuevtgt() {
		return revenuevtgt;
	}

	public void setRevenuevtgt(double revenuevtgt) {
		this.revenuevtgt = revenuevtgt;
	}

	public double getPaxperc() {
		return paxperc;
	}

	public void setPaxperc(double paxperc) {
		this.paxperc = paxperc;
	}

	public float getPaxytd() {
		return paxytd;
	}

	public void setPaxytd(float paxytd) {
		this.paxytd = paxytd;
	}

	public double getPaxvlyr() {
		return paxvlyr;
	}

	public void setPaxvlyr(double paxvlyr) {
		this.paxvlyr = paxvlyr;
	}

	public double getPaxvtgt() {
		return paxvtgt;
	}

	public void setPaxvtgt(double paxvtgt) {
		this.paxvtgt = paxvtgt;
	}

	public float getYieldytd() {
		return yieldytd;
	}

	public void setYieldytd(float yieldytd) {
		this.yieldytd = yieldytd;
	}

	public float getYiedvlyr() {
		return yiedvlyr;
	}

	public void setYiedvlyr(float yiedvlyr) {
		this.yiedvlyr = yiedvlyr;
	}

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public double getAverageFare() {
		return averageFare;
	}

	public void setAverageFare(double averageFare) {
		this.averageFare = averageFare;
	}

	public double getFlownRevenue() {
		return flownRevenue;
	}

	public void setFlownRevenue(double flownRevenue) {
		this.flownRevenue = flownRevenue;
	}

	public float getDistancePax() {
		return distancePax;
	}

	public void setDistancePax(float distancePax) {
		this.distancePax = distancePax;
	}

	public double getHostMarketShare() {
		return hostMarketShare;
	}

	public void setHostMarketShare(double hostMarketShare) {
		this.hostMarketShare = hostMarketShare;
	}

	public double getComp1MarketShare() {
		return comp1MarketShare;
	}

	public void setComp1MarketShare(double comp1MarketShare) {
		this.comp1MarketShare = comp1MarketShare;
	}

	public double getComp2MarketShare() {
		return comp2MarketShare;
	}

	public void setComp2MarketShare(double comp2MarketShare) {
		this.comp2MarketShare = comp2MarketShare;
	}

	public double getComp3MarketShare() {
		return comp3MarketShare;
	}

	public void setComp3MarketShare(double comp3MarketShare) {
		this.comp3MarketShare = comp3MarketShare;
	}

	public double getComp4MarketShare() {
		return comp4MarketShare;
	}

	public void setComp4MarketShare(double comp4MarketShare) {
		this.comp4MarketShare = comp4MarketShare;
	}

	public double getComp4pax() {
		return comp4pax;
	}

	public void setComp4pax(double comp4pax) {
		this.comp4pax = comp4pax;
	}

	public double getComp5MarketShare() {
		return comp5MarketShare;
	}

	public void setComp5MarketShare(double comp5MarketShare) {
		this.comp5MarketShare = comp5MarketShare;
	}

	public double getComp5pax() {
		return comp5pax;
	}

	public void setComp5pax(double comp5pax) {
		this.comp5pax = comp5pax;
	}

	public Map<String, Double> getCarrierPaxMap() {
		return carrierPaxMap;
	}

	public void setCarrierPaxMap(Map<String, Double> carrierPaxMap) {
		this.carrierPaxMap = carrierPaxMap;
	}

}
