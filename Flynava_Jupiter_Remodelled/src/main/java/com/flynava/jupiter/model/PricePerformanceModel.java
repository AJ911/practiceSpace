package com.flynava.jupiter.model;

import org.json.JSONArray;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PricePerformanceModel {

	private float salesRevenueYTD;
	private float revenueVLYR;
	private float revenueVTGT;

	private int paxYTD;
	private int paxVLYR;
	private float paxVTGT;

	private float yieldVLYR;
	private float yieldVTGT;

	private float marketShareYTD;
	private float marketShareVLYR;
	private float flownpaxlastyr;
	private float flownrevenuelastyr;

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String od;
	private String compartment;
	private String combinationKey;

	private String priceElasticitySignal;
	private String currency;
	private String channel;
	private String fareBasis;
	private String customerSegment;
	private String flightNumber;
	private String marketType;
	private float baseFare;
	private String rbd;
	private String footNote;
	private String ruleID;
	private String totalFare;

	private float hostRevenue;
	private float revenueForecast;
	private float hostRevenue_lastyr;
	private float hostRevenue_tgt;

	private float hostPax;
	private float flownPax;
	private float hostPaxForecast;
	private float hostPax_lastyr;
	private float hostPax_tgt;

	private float marketSharePax;
	private float marketSharePax_lastyr;

	private float totalSalesRevenue;
	private float flownRevenue;
	private float totalSalesRevenue_lastyr;

	private double totalMarketSharePax;
	private double totalMarketSharePax_lastyr;

	private float marketSize;
	private float marketSize_lastyr;
	private double totalMarketSize;

	private String departureDate;

	private float totalPax;
	private float totalPax_lastyr;

	private float totalYield;
	private float totalYield_lastyr;

	private float hostBookings;
	private float hostBookings_lastyr;
	private float hostbookings_target;

	private float totalHostBookings;
	private float totalHostBookings_lastyr;

	JSONArray hostBookingsArray = null;
	JSONArray hostBookingsLastYearArray = null;
	JSONArray hostBookingsTargetArray = null;

	private String fare;
	private String pricePerformance;
	private String capacity;
	private String odDistance;

	private float capacityFZ;
	private float capacityComp1;
	private float capacityComp2;
	private float capacityComp3;
	private float capacityComp4;

	private float compRatingFZ;
	private float compRatingComp1;
	private float compRatingComp2;
	private float compRatingComp3;
	private float compRatingComp4;

	private float yqCharge;
	private float taxes;
	private float surCharge;

	private int months;
	private int days;

	public float getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(float paxVTGT) {
		this.paxVTGT = paxVTGT;
	}

	public String getCombinationKey() {
		return combinationKey;
	}

	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}

	public float getTotalPax() {
		return totalPax;
	}

	public void setTotalPax(float totalPax) {
		this.totalPax = totalPax;
	}

	public float getTotalPax_lastyr() {
		return totalPax_lastyr;
	}

	public void setTotalPax_lastyr(int totalPax_lastyr) {
		this.totalPax_lastyr = totalPax_lastyr;
	}

	public float getTotalYield() {
		return totalYield;
	}

	public void setTotalYield(float totalYield) {
		this.totalYield = totalYield;
	}

	public float getTotalYield_lastyr() {
		return totalYield_lastyr;
	}

	public void setTotalYield_lastyr(float totalYield_lastyr) {
		this.totalYield_lastyr = totalYield_lastyr;
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

	public float getSalesRevenueYTD() {
		return salesRevenueYTD;
	}

	public void setSalesRevenueYTD(float revenueYTD) {
		this.salesRevenueYTD = revenueYTD;
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

	public float getTotalSalesRevenue() {
		return totalSalesRevenue;
	}

	public void setTotalSalesRevenue(float totalRevenue) {
		this.totalSalesRevenue = totalRevenue;
	}

	public float getTotalSalesRevenue_lastyr() {
		return totalSalesRevenue_lastyr;
	}

	public void setTotalSalesRevenue_lastyr(float totalRevenue_lastyr) {
		this.totalSalesRevenue_lastyr = totalRevenue_lastyr;
	}

	public String getPriceElasticitySignal() {
		return priceElasticitySignal;
	}

	public void setPriceElasticitySignal(String priceElasticitySignal) {
		this.priceElasticitySignal = priceElasticitySignal;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFareBasis() {
		return fareBasis;
	}

	public void setFareBasis(String fareBasis) {
		this.fareBasis = fareBasis;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
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

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public float getHostPax() {
		return hostPax;
	}

	public void setHostPax(float hostPax) {
		this.hostPax = hostPax;
	}

	public float getHostPax_lastyr() {
		return hostPax_lastyr;
	}

	public void setHostPax_lastyr(float hostPax_lastyr) {
		this.hostPax_lastyr = hostPax_lastyr;
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

	public double getTotalMarketSharePax() {
		return totalMarketSharePax;
	}

	public void setTotalMarketSharePax(double totalMarketSharePax) {
		this.totalMarketSharePax = totalMarketSharePax;
	}

	public double getTotalMarketSharePax_lastyr() {
		return totalMarketSharePax_lastyr;
	}

	public void setTotalMarketSharePax_lastyr(double totalMarketSharePax_lastyr) {
		this.totalMarketSharePax_lastyr = totalMarketSharePax_lastyr;
	}

	public double getTotalMarketSize() {
		return totalMarketSize;
	}

	public void setTotalMarketSize(double totalMarketSize) {
		this.totalMarketSize = totalMarketSize;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public float getHostBookings() {
		return hostBookings;
	}

	public void setHostBookings(float hostBookings) {
		this.hostBookings = hostBookings;
	}

	public float getHostBookings_lastyr() {
		return hostBookings_lastyr;
	}

	public void setHostBookings_lastyr(float hostBookings_lastyr) {
		this.hostBookings_lastyr = hostBookings_lastyr;
	}

	public float getHostbookings_target() {
		return hostbookings_target;
	}

	public void setHostbookings_target(float hostbookings_target) {
		this.hostbookings_target = hostbookings_target;
	}

	public float getTotalHostBookings() {
		return totalHostBookings;
	}

	public void setTotalHostBookings(float totalHostBookings) {
		this.totalHostBookings = totalHostBookings;
	}

	public float getTotalHostBookings_lastyr() {
		return totalHostBookings_lastyr;
	}

	public void setTotalHostBookings_lastyr(float totalHostBookings_lastyr) {
		this.totalHostBookings_lastyr = totalHostBookings_lastyr;
	}

	public JSONArray getHostBookingsArray() {
		return hostBookingsArray;
	}

	public void setHostBookingsArray(JSONArray hostBookingsArray) {
		this.hostBookingsArray = hostBookingsArray;
	}

	public JSONArray getHostBookingsLastYearArray() {
		return hostBookingsLastYearArray;
	}

	public void setHostBookingsLastYearArray(JSONArray hostBookingsLastYearArray) {
		this.hostBookingsLastYearArray = hostBookingsLastYearArray;
	}

	public JSONArray getHostBookingsTargetArray() {
		return hostBookingsTargetArray;
	}

	public void setHostBookingsTargetArray(JSONArray hostBookingsTargetArray) {
		this.hostBookingsTargetArray = hostBookingsTargetArray;
	}

	public String getFare() {
		return fare;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}

	public String getPricePerformance() {
		return pricePerformance;
	}

	public void setPricePerformance(String pricePerformance) {
		this.pricePerformance = pricePerformance;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getOdDistance() {
		return odDistance;
	}

	public void setOdDistance(String odDistance) {
		this.odDistance = odDistance;
	}

	public float getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(float baseFare) {
		this.baseFare = baseFare;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public float getMarketSharePax() {
		return marketSharePax;
	}

	public void setMarketSharePax(float marketSharePax) {
		this.marketSharePax = marketSharePax;
	}

	public float getMarketSharePax_lastyr() {
		return marketSharePax_lastyr;
	}

	public void setMarketSharePax_lastyr(float marketSharePax_lastyr) {
		this.marketSharePax_lastyr = marketSharePax_lastyr;
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

	public float getHostRevenue_tgt() {
		return hostRevenue_tgt;
	}

	public void setHostRevenue_tgt(float hostRevenue_tgt) {
		this.hostRevenue_tgt = hostRevenue_tgt;
	}

	public float getHostPax_tgt() {
		return hostPax_tgt;
	}

	public void setHostPax_tgt(float hostPax_tgt) {
		this.hostPax_tgt = hostPax_tgt;
	}

	public float getCapacityFZ() {
		return capacityFZ;
	}

	public void setCapacityFZ(float capacityFZ) {
		this.capacityFZ = capacityFZ;
	}

	public float getCapacityComp1() {
		return capacityComp1;
	}

	public void setCapacityComp1(float capacityComp1) {
		this.capacityComp1 = capacityComp1;
	}

	public float getCapacityComp2() {
		return capacityComp2;
	}

	public void setCapacityComp2(float capacityComp2) {
		this.capacityComp2 = capacityComp2;
	}

	public float getCompRatingFZ() {
		return compRatingFZ;
	}

	public void setCompRatingFZ(float compRatingFZ) {
		this.compRatingFZ = compRatingFZ;
	}

	public float getCompRatingComp1() {
		return compRatingComp1;
	}

	public void setCompRatingComp1(float compRatingComp1) {
		this.compRatingComp1 = compRatingComp1;
	}

	public float getCompRatingComp2() {
		return compRatingComp2;
	}

	public void setCompRatingComp2(float compRatingComp2) {
		this.compRatingComp2 = compRatingComp2;
	}

	public float getRevenueForecast() {
		return revenueForecast;
	}

	public void setRevenueForecast(float revenueForecast) {
		this.revenueForecast = revenueForecast;
	}

	public void setRevenueForecast(int revenueForecast) {
		this.revenueForecast = revenueForecast;
	}

	public float getFlownPax() {
		return flownPax;
	}

	public void setFlownPax(float flownPax) {
		this.flownPax = flownPax;
	}

	public float getHostPaxForecast() {
		return hostPaxForecast;
	}

	public void setHostPaxForecast(int hostPaxForecast) {
		this.hostPaxForecast = hostPaxForecast;
	}

	public float getFlownRevenue() {
		return flownRevenue;
	}

	public void setFlownRevenue(float flownRevenue) {
		this.flownRevenue = flownRevenue;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
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

	public String getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(String totalFare) {
		this.totalFare = totalFare;
	}

	public float getYqCharge() {
		return yqCharge;
	}

	public void setYqCharge(float yqCharge) {
		this.yqCharge = yqCharge;
	}

	public float getTaxes() {
		return taxes;
	}

	public void setTaxes(float taxes) {
		this.taxes = taxes;
	}

	public float getSurCharge() {
		return surCharge;
	}

	public void setSurCharge(float surCharge) {
		this.surCharge = surCharge;
	}

	public float getCapacityComp3() {
		return capacityComp3;
	}

	public void setCapacityComp3(float capacityComp3) {
		this.capacityComp3 = capacityComp3;
	}

	public float getCapacityComp4() {
		return capacityComp4;
	}

	public void setCapacityComp4(float capacityComp4) {
		this.capacityComp4 = capacityComp4;
	}

	public float getCompRatingComp3() {
		return compRatingComp3;
	}

	public void setCompRatingComp3(float compRatingComp3) {
		this.compRatingComp3 = compRatingComp3;
	}

	public float getCompRatingComp4() {
		return compRatingComp4;
	}

	public void setCompRatingComp4(float compRatingComp4) {
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

	public float getFlownpaxlastyr() {
		return flownpaxlastyr;
	}

	public void setFlownpaxlastyr(float flownpaxlastyr) {
		this.flownpaxlastyr = flownpaxlastyr;
	}

	public float getFlownrevenuelastyr() {
		return flownrevenuelastyr;
	}

	public void setFlownrevenuelastyr(float flownrevenuelastyr) {
		this.flownrevenuelastyr = flownrevenuelastyr;
	}

}
