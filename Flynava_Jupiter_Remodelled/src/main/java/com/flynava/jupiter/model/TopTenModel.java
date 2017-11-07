package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TopTenModel {

	private String year;
	private String month;
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String agent;
	private String od;
	private String marketingCarrier;

	private int pax;
	private int pax_lastyr;
	private int paxYTD;
	private String paxVLYR;
	private int paxVTGT;
	private int totalPax;
	private int totalPax_lastyr;
	private int targetPax;

	private float saleRevenue;
	private float saleRevenue_lastyr;
	private float flownRevenue;
	private float flownRevenue_lastyr;

	private float totalSalesRevenue;
	private float totalSalesRevenue_lastyr;
	private float totalFlownRevenue;
	private float totalFlownRevenue_lastyr;

	private float targetRevenue;
	private float flownRevenueYTD;
	private float salesRevenueYTD;
	private String revenueVLYR;
	private float revenueVTGT;

	private float marketSize;
	private float marketSize_lastyr;
	private float target_marketShare;

	private float capacity;
	private float capacity_LY;

	private String combinationKey;

	private float flownpax;
	private float flownpaxlastyr;
	private float hostcapacity;
	private float hostcapacitylastyr;
	private float totalflownpax;
	private float totalflownpaxlastyr;

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public int getPaxYTD() {
		return paxYTD;
	}

	public void setPaxYTD(int paxYTD) {
		this.paxYTD = paxYTD;
	}

	public int getPax() {
		return pax;
	}

	public void setPax(int pax) {
		this.pax = pax;
	}

	public int getTotalPax() {
		return totalPax;
	}

	public void setTotalPax(int totalPax) {
		this.totalPax = totalPax;
	}

	public String getCombinationKey() {
		return combinationKey;
	}

	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}

	public int getPax_lastyr() {
		return pax_lastyr;
	}

	public void setPax_lastyr(int pax_lastyr) {
		this.pax_lastyr = pax_lastyr;
	}

	public int getTotalPax_lastyr() {
		return totalPax_lastyr;
	}

	public void setTotalPax_lastyr(int totalPax_lastyr) {
		this.totalPax_lastyr = totalPax_lastyr;
	}

	public float getSaleRevenue() {
		return saleRevenue;
	}

	public void setSaleRevenue(float saleRevenue) {
		this.saleRevenue = saleRevenue;
	}

	public float getSaleRevenue_lastyr() {
		return saleRevenue_lastyr;
	}

	public void setSaleRevenue_lastyr(float saleRevenue_lastyr) {
		this.saleRevenue_lastyr = saleRevenue_lastyr;
	}

	public float getFlownRevenue() {
		return flownRevenue;
	}

	public void setFlownRevenue(float flownRevenue) {
		this.flownRevenue = flownRevenue;
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

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public int getTargetPax() {
		return targetPax;
	}

	public void setTargetPax(int targetPax) {
		this.targetPax = targetPax;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public float getTargetRevenue() {
		return targetRevenue;
	}

	public void setTargetRevenue(float targetRevenue) {
		this.targetRevenue = targetRevenue;
	}

	public float getTotalFlownRevenue() {
		return totalFlownRevenue;
	}

	public void setTotalFlownRevenue(float totalFlownRevenue) {
		this.totalFlownRevenue = totalFlownRevenue;
	}

	public float getRevenueVTGT() {
		return revenueVTGT;
	}

	public void setRevenueVTGT(float revenueVTGT) {
		this.revenueVTGT = revenueVTGT;
	}

	public String getMarketingCarrier() {
		return marketingCarrier;
	}

	public void setMarketingCarrier(String marketingCarrier) {
		this.marketingCarrier = marketingCarrier;
	}

	public float getMarketSize() {
		return marketSize;
	}

	public void setMarketSize(float marketSize) {
		this.marketSize = marketSize;
	}

	public float getMarketSize_lastyr() {
		return marketSize_lastyr;
	}

	public void setMarketSize_lastyr(float marketSize_lastyr) {
		this.marketSize_lastyr = marketSize_lastyr;
	}

	public float getTarget_marketShare() {
		return target_marketShare;
	}

	public void setTarget_marketShare(float target_marketShare) {
		this.target_marketShare = target_marketShare;
	}

	public String getPaxVLYR() {
		return paxVLYR;
	}

	public void setPaxVLYR(String paxVLYR) {
		this.paxVLYR = paxVLYR;
	}

	public String getRevenueVLYR() {
		return revenueVLYR;
	}

	public void setRevenueVLYR(String revenueVLYR) {
		this.revenueVLYR = revenueVLYR;
	}

	public float getFlownRevenue_lastyr() {
		return flownRevenue_lastyr;
	}

	public void setFlownRevenue_lastyr(float flownRevenue_lastyr) {
		this.flownRevenue_lastyr = flownRevenue_lastyr;
	}

	public float getTotalFlownRevenue_lastyr() {
		return totalFlownRevenue_lastyr;
	}

	public void setTotalFlownRevenue_lastyr(float totalFlownRevenue_lastyr) {
		this.totalFlownRevenue_lastyr = totalFlownRevenue_lastyr;
	}

	public float getFlownRevenueYTD() {
		return flownRevenueYTD;
	}

	public void setFlownRevenueYTD(float flownRevenueYTD) {
		this.flownRevenueYTD = flownRevenueYTD;
	}

	public float getSalesRevenueYTD() {
		return salesRevenueYTD;
	}

	public void setSalesRevenueYTD(float salesRevenueYTD) {
		this.salesRevenueYTD = salesRevenueYTD;
	}

	public int getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(int paxVTGT) {
		this.paxVTGT = paxVTGT;
	}

	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public float getCapacity_LY() {
		return capacity_LY;
	}

	public void setCapacity_LY(float capacity_LY) {
		this.capacity_LY = capacity_LY;
	}

	public float getFlownpax() {
		return flownpax;
	}

	public void setFlownpax(float flownpax) {
		this.flownpax = flownpax;
	}

	public float getFlownpaxlastyr() {
		return flownpaxlastyr;
	}

	public void setFlownpaxlastyr(float flownpaxlastyr) {
		this.flownpaxlastyr = flownpaxlastyr;
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

}
