
package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PricePerformanceResponse {

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String rbd;
	private String fareBasisCode;
	private String footNote;
	private String ruleID;
	private String currency;
	private String totalFare;
	private float baseFare;
	private String yqCharge;
	private String taxes;
	private String surCharge;
	private String priceElasticitySignal;
	private String channel;
	private String pricePerformanceScore;
	private float salesRevenueYTD;
	private float revenueVLYR;
	private float revenueVTGT;
	private float yieldYTD;
	private float yieldVLYR;
	private String yieldVTGT;
	private String marketType;
	private double marketSize;
	private int paxYTD;
	private int paxVLYR;
	private float paxVTGT;
	private float marketShareYTD;
	private float marketShareVLYR;
	private String marketShareVTGT;
	private String fms;
	private String deltaFMS;

	private int bookings;
	private int bookingsVLYR;
	private int bookingsVTGT;

	private String availability;

	private String capacityFZ;
	private String capacityComp1;
	private String capacityComp2;
	private String capacityComp3;
	private String capacityComp4;

	private String compRatingFZ;
	private String compRatingComp1;
	private String compRatingComp2;
	private String compRatingComp3;
	private String compRatingComp4;

	private int months;
	private int days;

	// forcast
	private float totalforcastpax;
	private float totalforcastrevenue;
	private float totaltargetproratedpax;
	private float totaltargetproratedrevnue;
	private float flownpax;
	private float flownrevenue;
	private float hostcapacity;
	private float hostcapacitylastyr;
	private float totalflownrevenue;
	private float totalflownpax;
	private float totalflownpaxlastyr;
	private float totalflownrevenuelastyr;

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

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

	public String getFareBasisCode() {
		return fareBasisCode;
	}

	public void setFareBasisCode(String fareBasisCode) {
		this.fareBasisCode = fareBasisCode;
	}

	public String getFootNote() {
		return footNote;
	}

	public void setFootNote(String footNote) {
		this.footNote = footNote;
	}

	public String getRuleID() {
		return ruleID;
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(String totalFare) {
		this.totalFare = totalFare;
	}

	public float getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(float baseFare) {
		this.baseFare = baseFare;
	}

	public String getYqCharge() {
		return yqCharge;
	}

	public void setYqCharge(String yqCharge) {
		this.yqCharge = yqCharge;
	}

	public String getTaxes() {
		return taxes;
	}

	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}

	public String getSurCharge() {
		return surCharge;
	}

	public void setSurCharge(String surCharge) {
		this.surCharge = surCharge;
	}

	public String getPriceElasticitySignal() {
		return priceElasticitySignal;
	}

	public void setPriceElasticitySignal(String priceElasticitySignal) {
		this.priceElasticitySignal = priceElasticitySignal;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPricePerformanceScore() {
		return pricePerformanceScore;
	}

	public void setPricePerformanceScore(String pricePerformanceScore) {
		this.pricePerformanceScore = pricePerformanceScore;
	}

	public float getSalesRevenueYTD() {
		return salesRevenueYTD;
	}

	public void setSalesRevenueYTD(float salesRevenueYTD) {
		this.salesRevenueYTD = salesRevenueYTD;
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

	public float getYieldYTD() {
		return yieldYTD;
	}

	public void setYieldYTD(float yieldYTD) {
		this.yieldYTD = yieldYTD;
	}

	public float getYieldVLYR() {
		return yieldVLYR;
	}

	public void setYieldVLYR(float yieldVLYR) {
		this.yieldVLYR = yieldVLYR;
	}

	public String getYieldVTGT() {
		return yieldVTGT;
	}

	public void setYieldVTGT(String yieldVTGT) {
		this.yieldVTGT = yieldVTGT;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public double getMarketSize() {
		return marketSize;
	}

	public void setMarketSize(double marketSize) {
		this.marketSize = marketSize;
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

	public String getMarketShareVTGT() {
		return marketShareVTGT;
	}

	public void setMarketShareVTGT(String marketShareVTGT) {
		this.marketShareVTGT = marketShareVTGT;
	}

	public String getFms() {
		return fms;
	}

	public void setFms(String fms) {
		this.fms = fms;
	}

	public String getDeltaFMS() {
		return deltaFMS;
	}

	public void setDeltaFMS(String deltaFMS) {
		this.deltaFMS = deltaFMS;
	}

	public int getBookings() {
		return bookings;
	}

	public void setBookings(int bookings) {
		this.bookings = bookings;
	}

	public int getBookingsVLYR() {
		return bookingsVLYR;
	}

	public void setBookingsVLYR(int bookingsVLYR) {
		this.bookingsVLYR = bookingsVLYR;
	}

	public int getBookingsVTGT() {
		return bookingsVTGT;
	}

	public void setBookingsVTGT(int bookingsVTGT) {
		this.bookingsVTGT = bookingsVTGT;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getCapacityFZ() {
		return capacityFZ;
	}

	public void setCapacityFZ(String capacityFZ) {
		this.capacityFZ = capacityFZ;
	}

	public String getCapacityComp1() {
		return capacityComp1;
	}

	public void setCapacityComp1(String capacityComp1) {
		this.capacityComp1 = capacityComp1;
	}

	public String getCapacityComp2() {
		return capacityComp2;
	}

	public void setCapacityComp2(String capacityComp2) {
		this.capacityComp2 = capacityComp2;
	}

	public String getCompRatingFZ() {
		return compRatingFZ;
	}

	public void setCompRatingFZ(String compRatingFZ) {
		this.compRatingFZ = compRatingFZ;
	}

	public String getCompRatingComp1() {
		return compRatingComp1;
	}

	public void setCompRatingComp1(String compRatingComp1) {
		this.compRatingComp1 = compRatingComp1;
	}

	public String getCompRatingComp2() {
		return compRatingComp2;
	}

	public void setCompRatingComp2(String compRatingComp2) {
		this.compRatingComp2 = compRatingComp2;
	}

	public String getCapacityComp3() {
		return capacityComp3;
	}

	public void setCapacityComp3(String capacityComp3) {
		this.capacityComp3 = capacityComp3;
	}

	public String getCapacityComp4() {
		return capacityComp4;
	}

	public void setCapacityComp4(String capacityComp4) {
		this.capacityComp4 = capacityComp4;
	}

	public String getCompRatingComp3() {
		return compRatingComp3;
	}

	public void setCompRatingComp3(String compRatingComp3) {
		this.compRatingComp3 = compRatingComp3;
	}

	public String getCompRatingComp4() {
		return compRatingComp4;
	}

	public void setCompRatingComp4(String compRatingComp4) {
		this.compRatingComp4 = compRatingComp4;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public float getTotalforcastpax() {
		return totalforcastpax;
	}

	public void setTotalforcastpax(float totalforcastpax) {
		this.totalforcastpax = totalforcastpax;
	}

	public float getTotalforcastrevenue() {
		return totalforcastrevenue;
	}

	public void setTotalforcastrevenue(float totalforcastrevenue) {
		this.totalforcastrevenue = totalforcastrevenue;
	}

	public float getTotaltargetproratedpax() {
		return totaltargetproratedpax;
	}

	public void setTotaltargetproratedpax(float totaltargetproratedpax) {
		this.totaltargetproratedpax = totaltargetproratedpax;
	}

	public float getTotaltargetproratedrevnue() {
		return totaltargetproratedrevnue;
	}

	public void setTotaltargetproratedrevnue(float totaltargetproratedrevnue) {
		this.totaltargetproratedrevnue = totaltargetproratedrevnue;
	}

	public float getFlownpax() {
		return flownpax;
	}

	public void setFlownpax(float flownpax) {
		this.flownpax = flownpax;
	}

	public float getFlownrevenue() {
		return flownrevenue;
	}

	public void setFlownrevenue(float flownrevenue) {
		this.flownrevenue = flownrevenue;
	}

	public float getHostcapacity() {
		return hostcapacity;
	}

	public void setHostcapacity(float hostcapacity) {
		this.hostcapacity = hostcapacity;
	}

	public float getHostcapacitylastyr() {
		return hostcapacitylastyr;
	}

	public void setHostcapacitylastyr(float hostcapacitylastyr) {
		this.hostcapacitylastyr = hostcapacitylastyr;
	}

	public float getTotalflownrevenue() {
		return totalflownrevenue;
	}

	public void setTotalflownrevenue(float totalflownrevenue) {
		this.totalflownrevenue = totalflownrevenue;
	}

	public float getTotalflownpax() {
		return totalflownpax;
	}

	public void setTotalflownpax(float totalflownpax) {
		this.totalflownpax = totalflownpax;
	}

	public float getTotalflownpaxlastyr() {
		return totalflownpaxlastyr;
	}

	public void setTotalflownpaxlastyr(float totalflownpaxlastyr) {
		this.totalflownpaxlastyr = totalflownpaxlastyr;
	}

	public float getTotalflownrevenuelastyr() {
		return totalflownrevenuelastyr;
	}

	public void setTotalflownrevenuelastyr(float totalflownrevenuelastyr) {
		this.totalflownrevenuelastyr = totalflownrevenuelastyr;
	}

}
