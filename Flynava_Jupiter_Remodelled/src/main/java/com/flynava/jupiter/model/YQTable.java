package com.flynava.jupiter.model;

public class YQTable {

	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String fareBasis;
	private Double fare;
	private Double YQ;
	private Double YQVLYR;
	private Double tax;
	private Double surcharge;
	private Double basefare;
	private String ruleId;
	private String footnote;
	private String currency;
	private Double totalfare;

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

	public String getFareBasis() {
		return fareBasis;
	}

	public void setFareBasis(String fareBasis) {
		this.fareBasis = fareBasis;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public Double getYQ() {
		return YQ;
	}

	public void setYQ(Double yQ) {
		YQ = yQ;
	}

	public Double getYQVLYR() {
		return YQVLYR;
	}

	public void setYQVLYR(Double yQVLYR) {
		YQVLYR = yQVLYR;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getTotalfare() {
		return totalfare;
	}

	public void setTotalfare(Double totalfare) {
		this.totalfare = totalfare;
	}

}
