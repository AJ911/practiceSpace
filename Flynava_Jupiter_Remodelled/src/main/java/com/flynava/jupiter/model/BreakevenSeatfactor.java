package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BreakevenSeatfactor {

	
	private float yieldYTD;
	private float yieldVLYR;
	private float yieldVTGT;
	
	private float seatfactorYTD;
	private float seatfactorVLYR;
	private float seatfactorVTGT;
	
	private String classYield;
	private float bsf;
	private float Delta;
	
	
	
	private String od;

	private String compartment;
	
	private float revenue;
	private float pax;
	
	private float totalyield;
	private float totalyield_lastyr;
	
	private float totalseat_factor;
	private float totalseat_factor_lastyr;
	private float avgfare;
	
	private float totalpax;
	private float totalrevenue;
	private float  paxytd;
	private float revenueytd;
	
	private String combinationKey;
	
	
	
	
	
	
	public float getDelta() {
		return Delta;
	}
	public void setDelta(float delta) {
		Delta = delta;
	}
	public String getCombinationKey() {
		return combinationKey;
	}
	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}
	public float getTotalseat_factor() {
		return totalseat_factor;
	}
	public void setTotalseat_factor(float totalseat_factor) {
		this.totalseat_factor = totalseat_factor;
	}
	public float getTotalseat_factor_lastyr() {
		return totalseat_factor_lastyr;
	}
	public void setTotalseat_factor_lastyr(float totalseat_factor_lastyr) {
		this.totalseat_factor_lastyr = totalseat_factor_lastyr;
	}
	
	public String getCompartment() {
		return compartment;
	}
	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}
	
	
	public float getSeatfactorYTD() {
		return seatfactorYTD;
	}
	public void setSeatfactorYTD(float seatfactorYTD) {
		this.seatfactorYTD = seatfactorYTD;
	}
	public float getSeatfactorVLYR() {
		return seatfactorVLYR;
	}
	public void setSeatfactorVLYR(float seatfactorVLYR) {
		this.seatfactorVLYR = seatfactorVLYR;
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
	public float getYieldVTGT() {
		return yieldVTGT;
	}
	public void setYieldVTGT(float yieldVTGT) {
		this.yieldVTGT = yieldVTGT;
	}
	
	
	public float getBsf() {
		return bsf;
	}
	public void setBsf(float bsf) {
		this.bsf = bsf;
	}
	
	
	public float getTotalyield() {
		return totalyield;
	}
	public void setTotalyield(float totalyield) {
		this.totalyield = totalyield;
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
	public float getTotalpax() {
		return totalpax;
	}
	public void setTotalpax(float totalpax) {
		this.totalpax = totalpax;
	}
	public float getTotalrevenue() {
		return totalrevenue;
	}
	public void setTotalrevenue(float totalrevenue) {
		this.totalrevenue = totalrevenue;
	}
	public float getPaxytd() {
		return paxytd;
	}
	public void setPaxytd(float paxytd) {
		this.paxytd = paxytd;
	}
	public float getRevenueytd() {
		return revenueytd;
	}
	public void setRevenueytd(float revenueytd) {
		this.revenueytd = revenueytd;
	}
	public float getAvgfare() {
		return avgfare;
	}
	public void setAvgfare(float avgfare) {
		this.avgfare = avgfare;
	}
	public String getClassYield() {
		return classYield;
	}
	public void setClassYield(String classYield) {
		this.classYield = classYield;
	}
	public float getSeatfactorVTGT() {
		return seatfactorVTGT;
	}
	public void setSeatfactorVTGT(float seatfactorVTGT) {
		this.seatfactorVTGT = seatfactorVTGT;
	}
	
	
}
