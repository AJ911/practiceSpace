/**
 * 
 */
package com.flynava.jupiter.model;

/**
 * @author Anu Merin
 *
 */
public class PriceHistoryModel {

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String od;
	private String compartment;
	private float revenue;
	private float revenue_lastyr;
	
	private String priceElasticitySignal;
	
	private float hostRevenue;
	private float hostRevenue_lastyr;
	
	private int hostPax;
	private int hostPax_lastyr;
	
	private int bookedPax;
	private int flownPax;
	private int salesPax;
	
	private String proximityIndicator;
	private String combinationKey;
	
	private float totalRevenue;
	private float totalRevenue_lastyr;
	private int totalPax;
	private int totalPax_lastyr;
	private int totalBookedPax;
	private int totalSalesPax;
	private int totalFlownPax;
	
	private float revenueYTD;
	private float revenueVLYR;
	private float revenueVTGT;
	
	private int paxYTD;
	private int paxVLYR;
	
	private int ticketed;
	private int flown;
	
	private String ticketsOrFlown;
	
	private int totalFlown;
	private int totalTickets;
	
	private int yieldYTD;
	private float yieldVLYR;
	private float yieldVTGT;
	
	private float avgFare;
	private float odDistance;
	

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

	public String getPriceElasticitySignal() {
		return priceElasticitySignal;
	}

	public void setPriceElasticitySignal(String priceElasticitySignal) {
		this.priceElasticitySignal = priceElasticitySignal;
	}

	public float getHostRevenue() {
		return hostRevenue;
	}

	public void setHostRevenue(float hostRevenue) {
		this.hostRevenue = hostRevenue;
	}

	public float getHostRevenue_lastyr() {
		return hostRevenue_lastyr;
	}

	public void setHostRevenue_lastyr(float hostRevenue_lastyr) {
		this.hostRevenue_lastyr = hostRevenue_lastyr;
	}

	public int getHostPax() {
		return hostPax;
	}

	public void setHostPax(int hostPax) {
		this.hostPax = hostPax;
	}

	public String getProximityIndicator() {
		return proximityIndicator;
	}

	public void setProximityIndicator(String proximityIndicator) {
		this.proximityIndicator = proximityIndicator;
	}

	public String getCombinationKey() {
		return combinationKey;
	}

	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}

	public float getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(float totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getHostPax_lastyr() {
		return hostPax_lastyr;
	}

	public void setHostPax_lastyr(int hostPax_lastyr) {
		this.hostPax_lastyr = hostPax_lastyr;
	}

	public float getTotalRevenue_lastyr() {
		return totalRevenue_lastyr;
	}

	public void setTotalRevenue_lastyr(float totalRevenue_lastyr) {
		this.totalRevenue_lastyr = totalRevenue_lastyr;
	}

	public int getTotalPax() {
		return totalPax;
	}

	public void setTotalPax(int totalPax) {
		this.totalPax = totalPax;
	}

	public int getTotalPax_lastyr() {
		return totalPax_lastyr;
	}

	public void setTotalPax_lastyr(int totalPax_lastyr) {
		this.totalPax_lastyr = totalPax_lastyr;
	}

	public float getRevenueYTD() {
		return revenueYTD;
	}

	public void setRevenueYTD(float revenueYTD) {
		this.revenueYTD = revenueYTD;
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

	public int getTicketed() {
		return ticketed;
	}

	public void setTicketed(int ticketed) {
		this.ticketed = ticketed;
	}

	public int getFlown() {
		return flown;
	}

	public void setFlown(int flown) {
		this.flown = flown;
	}

	public String getTicketsOrFlown() {
		return ticketsOrFlown;
	}

	public void setTicketsOrFlown(String ticketsOrFlown) {
		this.ticketsOrFlown = ticketsOrFlown;
	}

	public int getTotalFlown() {
		return totalFlown;
	}

	public void setTotalFlown(int totalFlown) {
		this.totalFlown = totalFlown;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}

	public int getYieldYTD() {
		return yieldYTD;
	}

	public void setYieldYTD(int yieldYTD) {
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

	public float getAvgFare() {
		return avgFare;
	}

	public void setAvgFare(float avgFare) {
		this.avgFare = avgFare;
	}

	public int getBookedPax() {
		return bookedPax;
	}

	public void setBookedPax(int bookedPax) {
		this.bookedPax = bookedPax;
	}

	public int getFlownPax() {
		return flownPax;
	}

	public void setFlownPax(int flownPax) {
		this.flownPax = flownPax;
	}

	public int getSalesPax() {
		return salesPax;
	}

	public void setSalesPax(int salesPax) {
		this.salesPax = salesPax;
	}

	public int getTotalBookedPax() {
		return totalBookedPax;
	}

	public void setTotalBookedPax(int totalBookedPax) {
		this.totalBookedPax = totalBookedPax;
	}

	public int getTotalSalesPax() {
		return totalSalesPax;
	}

	public void setTotalSalesPax(int totalSalesPax) {
		this.totalSalesPax = totalSalesPax;
	}

	public int getTotalFlownPax() {
		return totalFlownPax;
	}

	public void setTotalFlownPax(int totalFlownPax) {
		this.totalFlownPax = totalFlownPax;
	}

	public float getOdDistance() {
		return odDistance;
	}

	public void setOdDistance(float odDistance) {
		this.odDistance = odDistance;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	
}
