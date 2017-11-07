package com.flynava.jupiter.model;

import org.json.JSONObject;

public class AnalystPerformanceGrid {
	
	private float salesRevenue;
	private float salesPax;
	private float salesRevenueLY;
	private float salesPaxLY;
	private float flownRevenue;
	private float targetRevenue;
	private float flownPax;
	private float forecastRevenue;
	private float forecastPax;
	private JSONObject origin;
	private JSONObject destination;
	private JSONObject pos;
	private JSONObject compartment;
	private String key;
	private float totalSalesRevenue;
	private float totalSalesPax;
	private float marketSize;
	private float targetPax;
	private float revenueVTGT;
	private float paxVTGT;
	
	
	
	public float getRevenueVTGT() {
		return revenueVTGT;
	}
	public void setRevenueVTGT(float revenueVTGT) {
		this.revenueVTGT = revenueVTGT;
	}
	public float getPaxVTGT() {
		return paxVTGT;
	}
	public void setPaxVTGT(float paxVTGT) {
		this.paxVTGT = paxVTGT;
	}
	public float getForecastPax() {
		return forecastPax;
	}
	public void setForecastPax(float forecastPax) {
		this.forecastPax = forecastPax;
	}
	public float getTargetPax() {
		return targetPax;
	}
	public void setTargetPax(float targetPax) {
		this.targetPax = targetPax;
	}
	public float getFlownPax() {
		return flownPax;
	}
	public void setFlownPax(float flownPax) {
		this.flownPax = flownPax;
	}
	public float getForecastRevenue() {
		return forecastRevenue;
	}
	public void setForecastRevenue(float forecastRevenue) {
		this.forecastRevenue = forecastRevenue;
	}
		
	public float getSalesRevenue() {
		return salesRevenue;
	}
	public void setSalesRevenue(float salesRevenue) {
		this.salesRevenue = salesRevenue;
	}
	public float getSalesPax() {
		return salesPax;
	}
	public void setSalesPax(float salesPax) {
		this.salesPax = salesPax;
	}
	public float getSalesRevenueLY() {
		return salesRevenueLY;
	}
	public void setSalesRevenueLY(float salesRevenueLY) {
		this.salesRevenueLY = salesRevenueLY;
	}
	public float getSalesPaxLY() {
		return salesPaxLY;
	}
	public void setSalesPaxLY(float salesPaxLY) {
		this.salesPaxLY = salesPaxLY;
	}
	public float getFlownRevenue() {
		return flownRevenue;
	}
	public void setFlownRevenue(float flownRevenue) {
		this.flownRevenue = flownRevenue;
	}
	public float getTargetRevenue() {
		return targetRevenue;
	}
	public void setTargetRevenue(float targetRevenue) {
		this.targetRevenue = targetRevenue;
	}
	public JSONObject getOrigin() {
		return origin;
	}
	public void setOrigin(JSONObject origin) {
		this.origin = origin;
	}
	public JSONObject getDestination() {
		return destination;
	}
	public void setDestination(JSONObject destination) {
		this.destination = destination;
	}
	public JSONObject getPos() {
		return pos;
	}
	public void setPos(JSONObject posObj) {
		this.pos = posObj;
	}
	public JSONObject getCompartment() {
		return compartment;
	}
	public void setCompartment(JSONObject compartment) {
		this.compartment = compartment;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public float getTotalSalesRevenue() {
		return totalSalesRevenue;
	}
	public void setTotalSalesRevenue(float totalSalesRevenue) {
		this.totalSalesRevenue = totalSalesRevenue;
	}
	public float getTotalSalesPax() {
		return totalSalesPax;
	}
	public void setTotalSalesPax(float totalSalesPax) {
		this.totalSalesPax = totalSalesPax;
	}
	public float getMarketSize() {
		return marketSize;
	}
	public void setMarketSize(float marketSize) {
		this.marketSize = marketSize;
	}
}
