package com.flynava.jupiter.model;

public class CurrencyTable {

	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private Double fare;
	private String currency;
	private Double revenue;
	private Double revenueBase;
	private Double avgRateRealization;
	private Double tax;
	private String YQ;
	private Double surcharge;
	private Double basefare;
	private String ruleId;
	private String footnote;
	private String fareBasis;

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAvgRateRealization() {
		return avgRateRealization;
	}

	public void setAvgRateRealization(Double avgRateRealization) {
		this.avgRateRealization = avgRateRealization;
	}

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
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

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public String getCurrency() {
		return currency;
	}

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

	public Double getRevenueBase() {
		return revenueBase;
	}

	public void setRevenueBase(Double revenueBase) {
		this.revenueBase = revenueBase;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(Double surcharge) {
		this.surcharge = surcharge;
	}

	public Double getBasefare() {
		return basefare;
	}

	public void setBasefare(Double basefare) {
		this.basefare = basefare;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getFootnote() {
		return footnote;
	}

	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}

	public String getFareBasis() {
		return fareBasis;
	}

	public void setFareBasis(String fareBasis) {
		this.fareBasis = fareBasis;
	}

	public String getYQ() {
		return YQ;
	}

	public void setYQ(String yQ) {
		YQ = yQ;
	}

}
