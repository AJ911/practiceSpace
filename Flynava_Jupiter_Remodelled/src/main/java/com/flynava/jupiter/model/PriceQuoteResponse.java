
package com.flynava.jupiter.model;

public class PriceQuoteResponse {
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String od;
	private String channel;
	private String fareBasis;
	private String customerSegment;
	private String combinationKey;
	private String compartment;
	private String baseFare;

	private String HostPaxYTD;
	private String HostPaxVLYR;
	private float HostPaxVTGT;

	private String totalHostPax;
	private String totalHostPax_lastyr;

	private String marketShareYTD;
	private String marketShareVLYR;
	private String marketShareVTGT;

	private String avgFare;
	private String rbd;

	private String yield;
	private String yieldVLYR;

	private int month;
	private int days;

	// forcast
	private float totalforcastpax;
	private float totalforcastrevenue;
	private float totaltargetproratedpax;
	private float totaltargetproratedrevnue;

	private float flownpax;
	private float flownrevenue;

	private float revenuevtgt;
	private float fms;

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

	private String currency;
	private String totalFare;
	private String yqCharge;
	private String taxes;
	private String surCharge;
	private String footNote;
	private String ruleID;

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getFareBasis() {
		return fareBasis;
	}

	public void setFareBasis(String fareBasis) {
		this.fareBasis = fareBasis;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	public String getCombinationKey() {
		return combinationKey;
	}

	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}

	public String getTotalHostPax() {
		return totalHostPax;
	}

	public void setTotalHostPax(String totalHostPax) {
		this.totalHostPax = totalHostPax;
	}

	public String getTotalHostPax_lastyr() {
		return totalHostPax_lastyr;
	}

	public void setTotalHostPax_lastyr(String totalHostPax_lastyr) {
		this.totalHostPax_lastyr = totalHostPax_lastyr;
	}

	public String getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(String baseFare) {
		this.baseFare = baseFare;
	}

	public float getHostPaxVTGT() {
		return HostPaxVTGT;
	}

	public void setHostPaxVTGT(float hostPaxVTGT) {
		HostPaxVTGT = hostPaxVTGT;
	}

	public String getHostPaxYTD() {
		return HostPaxYTD;
	}

	public void setHostPaxYTD(String hostPaxYTD) {
		HostPaxYTD = hostPaxYTD;
	}

	public String getHostPaxVLYR() {
		return HostPaxVLYR;
	}

	public void setHostPaxVLYR(String hostPaxVLYR) {
		HostPaxVLYR = hostPaxVLYR;
	}

	public String getMarketShareYTD() {
		return marketShareYTD;
	}

	public void setMarketShareYTD(String marketShareYTD) {
		this.marketShareYTD = marketShareYTD;
	}

	public String getMarketShareVLYR() {
		return marketShareVLYR;
	}

	public void setMarketShareVLYR(String marketShareVLYR) {
		this.marketShareVLYR = marketShareVLYR;
	}

	public String getMarketShareVTGT() {
		return marketShareVTGT;
	}

	public void setMarketShareVTGT(String marketShareVTGT) {
		this.marketShareVTGT = marketShareVTGT;
	}

	public String getAvgFare() {
		return avgFare;
	}

	public void setAvgFare(String avgFare) {
		this.avgFare = avgFare;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public String getYieldVLYR() {
		return yieldVLYR;
	}

	public void setYieldVLYR(String yieldVLYR) {
		this.yieldVLYR = yieldVLYR;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
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

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
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

	public float getRevenuevtgt() {
		return revenuevtgt;
	}

	public void setRevenuevtgt(float revenuevtgt) {
		this.revenuevtgt = revenuevtgt;
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

	public float getFms() {
		return fms;
	}

	public void setFms(float fms) {
		this.fms = fms;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
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

}
