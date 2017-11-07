package com.flynava.jupiter.model;

import java.util.HashMap;
import java.util.Map;

public class MarketIndicatorTopODs implements Comparable<MarketIndicatorTopODs> {

	private String od;
	private String origin;
	private String destination;
	private String setFactor;
	private int marketSize;
	private int saleRevenue;
	private int hostRevenueFlown;
	private int hostRevenueflown_1;
	private int hostRevenueTarget;
	private int salePax;
	private int salePax_1;
	private int flownPax;
	private int flownPax_1;
	private int hostTargetPax;
	private int marketSize_1;
	private int capacity;
	private float revenueYTD;
	private float revenueVLYR;
	private float revenueVTGT;
	private long paxYTD;
	private float paxVLYR;
	private float paxVTGT;
	private float marketShareYTD;
	private float marketShareVLYR;
	private float marketShareVTGT;
	private float fms;
	private int month;
	private int day;
	private String key;

	private Map<String, Object> competitorsMap = new HashMap<String, Object>();

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
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

	public String getSetFactor() {
		return setFactor;
	}

	public void setSetFactor(String setFactor) {
		this.setFactor = setFactor;
	}

	public int getSaleRevenue() {
		return saleRevenue;
	}

	public void setSaleRevenue(int saleRevenue) {
		this.saleRevenue = saleRevenue;
	}

	public int getHostRevenueFlown() {
		return hostRevenueFlown;
	}

	public void setHostRevenueFlown(int hostRevenueFlown) {
		this.hostRevenueFlown = hostRevenueFlown;
	}

	public int getHostRevenueTarget() {
		return hostRevenueTarget;
	}

	public void setHostRevenueTarget(int hostRevenueTarget) {
		this.hostRevenueTarget = hostRevenueTarget;
	}

	public int getSalePax() {
		return salePax;
	}

	public void setSalePax(int salePax) {
		this.salePax = salePax;
	}

	public int getSalePax_1() {
		return salePax_1;
	}

	public void setSalePax_1(int salePax_1) {
		this.salePax_1 = salePax_1;
	}

	public int getFlownPax() {
		return flownPax;
	}

	public void setFlownPax(int flownPax) {
		this.flownPax = flownPax;
	}

	public int getFlownPax_1() {
		return flownPax_1;
	}

	public void setFlownPax_1(int flownPax_1) {
		this.flownPax_1 = flownPax_1;
	}

	public int getHostTargetPax() {
		return hostTargetPax;
	}

	public void setHostTargetPax(int hostTargetPax) {
		this.hostTargetPax = hostTargetPax;
	}

	public int getMarketSize_1() {
		return marketSize_1;
	}

	public void setMarketSize_1(int marketSize_1) {
		this.marketSize_1 = marketSize_1;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
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

	public long getPaxYTD() {
		return paxYTD;
	}

	public void setPaxYTD(long paxYTD) {
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

	public float getMarketShareYTD() {
		return marketShareYTD;
	}

	public void setMarketShareYTD(float marketShareYTD) {
		this.marketShareYTD = marketShareYTD;
	}

	public float getMarketShareVLYR() {
		return marketShareVLYR;
	}

	public void setMarketShareVLYR(float marketShareVLYR) {
		this.marketShareVLYR = marketShareVLYR;
	}

	public float getMarketShareVTGT() {
		return marketShareVTGT;
	}

	public void setMarketShareVTGT(float marketShareVTGT) {
		this.marketShareVTGT = marketShareVTGT;
	}

	public float getFms() {
		return fms;
	}

	public void setFms(float fms) {
		this.fms = fms;
	}

	public int getMarketSize() {
		return marketSize;
	}

	public void setMarketSize(int marketSize) {
		this.marketSize = marketSize;
	}

	public Map<String, Object> getCompetitorsMap() {
		return competitorsMap;
	}

	public void setCompetitorsMap(Map<String, Object> competitorsMap) {
		this.competitorsMap = competitorsMap;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getHostRevenueflown_1() {
		return hostRevenueflown_1;
	}

	public void setHostRevenueflown_1(int hostRevenueflown_1) {
		this.hostRevenueflown_1 = hostRevenueflown_1;
	}

	@Override
	public int compareTo(MarketIndicatorTopODs o) {
		// TODO Auto-generated method stub

		int salesRevenue = ((MarketIndicatorTopODs) o).getSaleRevenue();

		return this.saleRevenue - salesRevenue;
	}

}
