package com.flynava.jupiter.model;

import org.json.JSONArray;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Revenue {

	private String revenueYTD;

	private String revenueVLYR;
	private String revenueVTGT;
	
	
	private String paxYTD;
	private String paxVLYR;
	private String paxVTGT;
	
	private String yieldYTD;
	private String yieldVLYR;
	private String yieldVTGT;
	
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private float revenue;
	private float revenue_lastyr;
	private int pax;
	private int pax_lastyr;
	private  float yield;
	private float yield_lastyr;
	private String pax_type;
	private float totaladultpaxYtd;
	private float totalchildpaxytd;
	private float totalinfpaxytd;
	private int totalPax;
	
	private float totalFlownRevenue;
	private float totalFlownRevenue_lastyr;
	
	private float totalSalesRevenue;
	private float totalSalesRevenue_lastyr;
	
	private float flownRevenue;
	private float flownRevenue_lastyr;
	
	JSONArray targetRevenueArray = null;
	JSONArray salesRevenueArray = null;
	JSONArray salesRevenue_LastYr_Array = null;
	
	private String combinationkey;
	
	private String combinationkey1;
	private float distance;
	
	
	
	
	
	
	
	public String getPaxYTD() {
		return paxYTD;
	}
	public void setPaxYTD(String paxYTD) {
		this.paxYTD = paxYTD;
	}
	public String getPaxVLYR() {
		return paxVLYR;
	}
	public void setPaxVLYR(String paxVLYR) {
		this.paxVLYR = paxVLYR;
	}
	public String getPaxVTGT() {
		return paxVTGT;
	}
	public void setPaxVTGT(String paxVTGT) {
		this.paxVTGT = paxVTGT;
	}
	public String getYieldYTD() {
		return yieldYTD;
	}
	public void setYieldYTD(String yieldYTD) {
		this.yieldYTD = yieldYTD;
	}
	public String getYieldVLYR() {
		return yieldVLYR;
	}
	public void setYieldVLYR(String yieldVLYR) {
		this.yieldVLYR = yieldVLYR;
	}
	public String getYieldVTGT() {
		return yieldVTGT;
	}
	public void setYieldVTGT(String yieldVTGT) {
		this.yieldVTGT = yieldVTGT;
	}
	public int getPax() {
		return pax;
	}
	public void setPax(int pax) {
		this.pax = pax;
	}
	public int getPax_lastyr() {
		return pax_lastyr;
	}
	public void setPax_lastyr(int pax_lastyr) {
		this.pax_lastyr = pax_lastyr;
	}
	public float getYield() {
		return yield;
	}
	public void setYield(float yield) {
		this.yield = yield;
	}
	public float getYield_lastyr() {
		return yield_lastyr;
	}
	public void setYield_lastyr(float yield_lastyr) {
		this.yield_lastyr = yield_lastyr;
	}
	public String getRevenueYTD() {
		return revenueYTD;
	}
	public void setRevenueYTD(String revenueYTD) {
		this.revenueYTD = revenueYTD;
	}
	public String getRevenueVLYR() {
		return revenueVLYR;
	}
	public void setRevenueVLYR(String revenueVLYR) {
		this.revenueVLYR = revenueVLYR;
	}
	public String getRevenueVTGT() {
		return revenueVTGT;
	}
	public void setRevenueVTGT(String revenueVTGT) {
		this.revenueVTGT = revenueVTGT;
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
	public String getCompartment() {
		return compartment;
	}
	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}
	
	public float getRevenue() {
		return revenue;
	}
	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}
	public float getRevenue_lastyr() {
		return revenue_lastyr;
	}
	public void setRevenue_lastyr(float revenue_lastyr) {
		this.revenue_lastyr = revenue_lastyr;
	}
	
	public String getCombinationkey() {
		return combinationkey;
	}
	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}
	
	public float getTotalFlownRevenue() {
		return totalFlownRevenue;
	}
	public void setTotalFlownRevenue(float totalFlownRevenue) {
		this.totalFlownRevenue = totalFlownRevenue;
	}
	public float getTotalFlownRevenue_lastyr() {
		return totalFlownRevenue_lastyr;
	}
	public void setTotalFlownRevenue_lastyr(float totalFlownRevenue_lastyr) {
		this.totalFlownRevenue_lastyr = totalFlownRevenue_lastyr;
	}
	public float getTotalSalesRevenue() {
		return totalSalesRevenue;
	}
	public void setTotalSalesRevenue(float totalSalesRevenue) {
		this.totalSalesRevenue = totalSalesRevenue;
	}
	public float getTotalSalesRevenue_lastyr() {
		return totalSalesRevenue_lastyr;
	}
	public void setTotalSalesRevenue_lastyr(float totalSalesRevenue_lastyr) {
		this.totalSalesRevenue_lastyr = totalSalesRevenue_lastyr;
	}
	public float getFlownRevenue() {
		return flownRevenue;
	}
	public void setFlownRevenue(float flownRevenue) {
		this.flownRevenue = flownRevenue;
	}
	public float getFlownRevenue_lastyr() {
		return flownRevenue_lastyr;
	}
	public void setFlownRevenue_lastyr(float flownRevenue_lastyr) {
		this.flownRevenue_lastyr = flownRevenue_lastyr;
	}
	public JSONArray getTargetRevenueArray() {
		return targetRevenueArray;
	}
	public void setTargetRevenueArray(JSONArray targetRevenueArray) {
		this.targetRevenueArray = targetRevenueArray;
	}
	public JSONArray getSalesRevenueArray() {
		return salesRevenueArray;
	}
	public void setSalesRevenueArray(JSONArray salesRevenueArray) {
		this.salesRevenueArray = salesRevenueArray;
	}
	public JSONArray getSalesRevenue_LastYr_Array() {
		return salesRevenue_LastYr_Array;
	}
	public void setSalesRevenue_LastYr_Array(JSONArray salesRevenue_LastYr_Array) {
		this.salesRevenue_LastYr_Array = salesRevenue_LastYr_Array;
	}
	public String getPax_type() {
		return pax_type;
	}
	public void setPax_type(String pax_type) {
		this.pax_type = pax_type;
	}
	public String getCombinationkey1() {
		return combinationkey1;
	}
	public void setCombinationkey1(String combinationkey1) {
		this.combinationkey1 = combinationkey1;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public float getTotaladultpaxYtd() {
		return totaladultpaxYtd;
	}
	public void setTotaladultpaxYtd(float totaladultpaxYtd) {
		this.totaladultpaxYtd = totaladultpaxYtd;
	}
	public float getTotalchildpaxytd() {
		return totalchildpaxytd;
	}
	public void setTotalchildpaxytd(float totalchildpaxytd) {
		this.totalchildpaxytd = totalchildpaxytd;
	}
	public float getTotalinfpaxytd() {
		return totalinfpaxytd;
	}
	public void setTotalinfpaxytd(float totalinfpaxytd) {
		this.totalinfpaxytd = totalinfpaxytd;
	}
	public int getTotalPax() {
		return totalPax;
	}
	public void setTotalPax(int totalPax) {
		this.totalPax = totalPax;
	}
	
	
}
