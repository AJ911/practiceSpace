package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BreakevenSeat {

	private float bsf;

	private String od;
	private String origin;
	private String destination;
	private float revenue;
	private float revenue1;
	private float pax;
	private float pax1;
	private float rpkm;
	private float rpkm1;
	private float seat_Factor;
	private float yield;

	private String Compartment;
	private float delta;
	private float bsfperc;
	private float targetyield;
	private float classyieldratio;

	private float seat_factor_1;

	private float yield_1;

	private float totalyield;
	private float totalyield_lastyr;
	private float avgfare;

	private float totalseatfactor;
	private float totalseat_factor_lastyr;
	private float seatfactor1vlyr;

	private float revenuevlyr;
	private float paxvlyr;
	private float yieldvlyr;
	private float revenuevtgt;
	private float paxvtgt;
	private float yieldvtgt;

	private String combinationkey;

	public float getBsf() {
		return bsf;
	}

	public void setBsf(float bsf) {
		this.bsf = bsf;
	}

	public float getTotalseat_factor_lastyr() {
		return totalseat_factor_lastyr;
	}

	public void setTotalseat_factor_lastyr(float totalseat_factor_lastyr) {
		this.totalseat_factor_lastyr = totalseat_factor_lastyr;
	}

	public float getSeat_factor_1() {
		return seat_factor_1;
	}

	public void setSeat_factor_1(float seat_factor_1) {
		this.seat_factor_1 = seat_factor_1;
	}

	public float getSeat_Factor() {
		return seat_Factor;
	}

	public void setSeat_Factor(float seat_Factor) {
		this.seat_Factor = seat_Factor;
	}

	public String getCompartment() {
		return Compartment;
	}

	public void setCompartment(String compartment) {
		Compartment = compartment;
	}

	public float getRevenue() {
		return revenue;
	}

	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}

	public float getPax() {
		return pax;
	}

	public void setPax(float pax) {
		this.pax = pax;
	}

	public float getYield() {
		return yield;
	}

	public void setYield(float yield) {
		this.yield = yield;
	}

	public float getYield_1() {
		return yield_1;
	}

	public void setYield_1(float yield_1) {
		this.yield_1 = yield_1;
	}

	public float getTotalyield() {
		return totalyield;
	}

	public void setTotalyield(float totalyield) {
		this.totalyield = totalyield;
	}

	public float getTotalseatfactor() {
		return totalseatfactor;
	}

	public void setTotalseatfactor(float totalseatfactor) {
		this.totalseatfactor = totalseatfactor;
	}

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}

	public float getTotalyield_lastyr() {
		return totalyield_lastyr;
	}

	public void setTotalyield_lastyr(float totalyield_lastyr) {
		this.totalyield_lastyr = totalyield_lastyr;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public float getSeatfactor1vlyr() {
		return seatfactor1vlyr;
	}

	public void setSeatfactor1vlyr(float seatfactor1vlyr) {
		this.seatfactor1vlyr = seatfactor1vlyr;
	}

	public float getRevenuevlyr() {
		return revenuevlyr;
	}

	public void setRevenuevlyr(float revenuevlyr) {
		this.revenuevlyr = revenuevlyr;
	}

	public float getPaxvlyr() {
		return paxvlyr;
	}

	public void setPaxvlyr(float paxvlyr) {
		this.paxvlyr = paxvlyr;
	}

	public float getYieldvlyr() {
		return yieldvlyr;
	}

	public void setYieldvlyr(float yieldvlyr) {
		this.yieldvlyr = yieldvlyr;
	}

	public float getRevenuevtgt() {
		return revenuevtgt;
	}

	public void setRevenuevtgt(float revenuevtgt) {
		this.revenuevtgt = revenuevtgt;
	}

	public float getPaxvtgt() {
		return paxvtgt;
	}

	public void setPaxvtgt(float paxvtgt) {
		this.paxvtgt = paxvtgt;
	}

	public float getYieldvtgt() {
		return yieldvtgt;
	}

	public void setYieldvtgt(float yieldvtgt) {
		this.yieldvtgt = yieldvtgt;
	}

	public float getRevenue1() {
		return revenue1;
	}

	public void setRevenue1(float revenue1) {
		this.revenue1 = revenue1;
	}

	public float getPax1() {
		return pax1;
	}

	public void setPax1(float pax1) {
		this.pax1 = pax1;
	}

	public float getDelta() {
		return delta;
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}

	public float getBsfperc() {
		return bsfperc;
	}

	public void setBsfperc(float bsfperc) {
		this.bsfperc = bsfperc;
	}

	public float getTargetyield() {
		return targetyield;
	}

	public void setTargetyield(float targetyield) {
		this.targetyield = targetyield;
	}

	public float getClassyieldratio() {
		return classyieldratio;
	}

	public void setClassyieldratio(float classyieldratio) {
		this.classyieldratio = classyieldratio;
	}

	public float getAvgfare() {
		return avgfare;
	}

	public void setAvgfare(float avgfare) {
		this.avgfare = avgfare;
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

	public float getRpkm() {
		return rpkm;
	}

	public void setRpkm(float rpkm) {
		this.rpkm = rpkm;
	}

	public float getRpkm1() {
		return rpkm1;
	}

	public void setRpkm1(float rpkm1) {
		this.rpkm1 = rpkm1;
	}

}
