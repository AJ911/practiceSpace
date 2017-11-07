package com.flynava.jupiter.model;

import java.io.Serializable;

public class ManualTrigger implements Serializable {

	private String pos;
	private String destination;
	private String origin;
	private String compartment;

	private int depMonth;
	private String depDate;

	private float forecastPax;
	private float forecastRevenue;
	private float avgFareForecast;
	private float salesRevenue;
	private float flownRevenue;
	private float salesPax;
	private float flownPax;
	private float targetPax;
	private float targetRevenue;
	private float targetAvgFare;
	private float marketSize;
	private float marketSharePax;
	private float capacity;
	private float capacityLYR;
	private float marketShareVTGT;
	private float marketShareVLYR;
	private float paxVTGT;
	private float revenueVTGT;
	private float paxVLYR;
	private float revenueVLYR;
	private float yield;
	private float yieldForecast;
	private float yieldLYR;
	private float yieldTarget;
	private float yieldVTGT;
	private float avgFareVTGT;

	private float distance;

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	public float getForecastPax() {
		return forecastPax;
	}

	public void setForecastPax(float forecastPax) {
		this.forecastPax = forecastPax;
	}

	public float getForecastRevenue() {
		return forecastRevenue;
	}

	public void setForecastRevenue(float forecastRevenue) {
		this.forecastRevenue = forecastRevenue;
	}

	public float getAvgFareForecast() {
		return avgFareForecast;
	}

	public void setAvgFareForecast(float avgFareForecast) {
		this.avgFareForecast = avgFareForecast;
	}

	public float getSalesRevenue() {
		return salesRevenue;
	}

	public void setSalesRevenue(float salesRevenue) {
		this.salesRevenue = salesRevenue;
	}

	public float getFlownRevenue() {
		return flownRevenue;
	}

	public void setFlownRevenue(float flownRevenue) {
		this.flownRevenue = flownRevenue;
	}

	public float getSalesPax() {
		return salesPax;
	}

	public void setSalesPax(float salesPax) {
		this.salesPax = salesPax;
	}

	public float getFlownPax() {
		return flownPax;
	}

	public void setFlownPax(float flownPax) {
		this.flownPax = flownPax;
	}

	public float getTargetPax() {
		return targetPax;
	}

	public void setTargetPax(float targetPax) {
		this.targetPax = targetPax;
	}

	public float getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(float paxVTGT) {
		this.paxVTGT = paxVTGT;
	}

	public float getRevenueVTGT() {
		return revenueVTGT;
	}

	public void setRevenueVTGT(float revenueVTGT) {
		this.revenueVTGT = revenueVTGT;
	}

	public float getPaxVLYR() {
		return paxVLYR;
	}

	public void setPaxVLYR(float paxVLYR) {
		this.paxVLYR = paxVLYR;
	}

	public float getRevenueVLYR() {
		return revenueVLYR;
	}

	public void setRevenueVLYR(float revenueVLYR) {
		this.revenueVLYR = revenueVLYR;
	}

	public float getTargetRevenue() {
		return targetRevenue;
	}

	public void setTargetRevenue(float targetRevenue) {
		this.targetRevenue = targetRevenue;
	}

	public float getTargetAvgFare() {
		return targetAvgFare;
	}

	public void setTargetAvgFare(float targetAvgFare) {
		this.targetAvgFare = targetAvgFare;
	}

	public float getMarketSize() {
		return marketSize;
	}

	public void setMarketSize(float marketSize) {
		this.marketSize = marketSize;
	}

	public float getMarketSharePax() {
		return marketSharePax;
	}

	public void setMarketSharePax(float marketSharePax) {
		this.marketSharePax = marketSharePax;
	}

	public float getMarketShareVTGT() {
		return marketShareVTGT;
	}

	public void setMarketShareVTGT(float marketShareVTGT) {
		this.marketShareVTGT = marketShareVTGT;
	}

	public float getMarketShareVLYR() {
		return marketShareVLYR;
	}

	public void setMarketShareVLYR(float marketShareVLYR) {
		this.marketShareVLYR = marketShareVLYR;
	}

	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public float getCapacityLYR() {
		return capacityLYR;
	}

	public void setCapacityLYR(float capacityLYR) {
		this.capacityLYR = capacityLYR;
	}

	public int getDepMonth() {
		return depMonth;
	}

	public void setDepMonth(int depMonth) {
		this.depMonth = depMonth;
	}

	public String getDepDate() {
		return depDate;
	}

	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getYield() {
		return yield;
	}

	public void setYield(float yield) {
		this.yield = yield;
	}

	public float getYieldForecast() {
		return yieldForecast;
	}

	public void setYieldForecast(float yieldForecast) {
		this.yieldForecast = yieldForecast;
	}

	public float getYieldLYR() {
		return yieldLYR;
	}

	public void setYieldLYR(float yieldLYR) {
		this.yieldLYR = yieldLYR;
	}

	public float getYieldTarget() {
		return yieldTarget;
	}

	public void setYieldTarget(float yieldTarget) {
		this.yieldTarget = yieldTarget;
	}

	public float getYieldVTGT() {
		return yieldVTGT;
	}

	public void setYieldVTGT(float yieldVTGT) {
		this.yieldVTGT = yieldVTGT;
	}

	public float getAvgFareVTGT() {
		return avgFareVTGT;
	}

	public void setAvgFareVTGT(float avgFareVTGT) {
		this.avgFareVTGT = avgFareVTGT;
	}

}
