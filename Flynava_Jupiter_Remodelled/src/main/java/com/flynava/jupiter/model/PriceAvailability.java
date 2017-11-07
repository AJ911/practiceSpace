package com.flynava.jupiter.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PriceAvailability {

	private String region;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String country;
	private String RBD;
	private String od;
	private String customerSegment;
	private String channel;
	private String fare_basis;
	private float base_fare;
	private float marketsize1;
	private String dep_date;

	private String currency;
	private float fare;
	private float count;
	private float marketsharepax;
	private float marketsharepaxlastyr;
	private float totalmarketsharepax;
	private float totalmarketsharepaxlastyr;
	private float marketsize;
	private float totalmarketsize;
	private String carrier;
	private float marketshare;
	private float marketshare_1;
	private float marketShareYTD;
	private float marketShareVLYR;
	private float marketShareVTGT;
	private float totalMarketshare;
	private float totalMarketshare_lastyr;
	private float priceAvailabilityIndex;

	private List lCompetitor = new ArrayList();
	private ArrayList<String> carrierList = new ArrayList<String>();
	private ArrayList<String> marketsharepaxList = new ArrayList<String>();
	private ArrayList<String> marketsharepaxList1 = new ArrayList<String>();
	private ArrayList<String> marketsizeList = new ArrayList<String>();

	private float targetmarketshare;

	private float fairmarketshare;
	private float validforperiod;

	private int totalfare;

	private float totalmarketsize1;

	private String combinationkey;

	private float totalpax;
	private float totalrevenue;

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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRBD() {
		return RBD;
	}

	public void setRBD(String rBD) {
		RBD = rBD;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getFare_basis() {
		return fare_basis;
	}

	public void setFare_basis(String fare_basis) {
		this.fare_basis = fare_basis;
	}

	public float getBase_fare() {
		return base_fare;
	}

	public void setBase_fare(float base_fare) {
		this.base_fare = base_fare;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public float getFare() {
		return fare;
	}

	public void setFare(float fare) {
		this.fare = fare;
	}

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}

	public int getTotalfare() {
		return totalfare;
	}

	public void setTotalfare(int totalfare) {
		this.totalfare = totalfare;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public float getCount() {
		return count;
	}

	public void setCount(float count) {
		this.count = count;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public float getMarketshare() {
		return marketshare;
	}

	public void setMarketshare(float marketshare) {
		this.marketshare = marketshare;
	}

	public float getMarketshare_1() {
		return marketshare_1;
	}

	public void setMarketshare_1(float marketshare_1) {
		this.marketshare_1 = marketshare_1;
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

	public float getTotalMarketshare() {
		return totalMarketshare;
	}

	public void setTotalMarketshare(float totalMarketshare) {
		this.totalMarketshare = totalMarketshare;
	}

	public float getTotalMarketshare_lastyr() {
		return totalMarketshare_lastyr;
	}

	public void setTotalMarketshare_lastyr(float totalMarketshare_lastyr) {
		this.totalMarketshare_lastyr = totalMarketshare_lastyr;
	}

	public List getlCompetitor() {
		return lCompetitor;
	}

	public void setlCompetitor(List lCompetitor) {
		this.lCompetitor = lCompetitor;
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

	public float getValidforperiod() {
		return validforperiod;
	}

	public void setValidforperiod(float validforperiod) {
		this.validforperiod = validforperiod;
	}

	public ArrayList<String> getCarrierList() {
		return carrierList;
	}

	public void setCarrierList(ArrayList<String> carrierList) {
		this.carrierList = carrierList;
	}

	public float getMarketsharepax() {
		return marketsharepax;
	}

	public void setMarketsharepax(float marketsharepax) {
		this.marketsharepax = marketsharepax;
	}

	public float getMarketsharepaxlastyr() {
		return marketsharepaxlastyr;
	}

	public void setMarketsharepaxlastyr(float marketsharepaxlastyr) {
		this.marketsharepaxlastyr = marketsharepaxlastyr;
	}

	public float getMarketsize() {
		return marketsize;
	}

	public void setMarketsize(float marketsize) {
		this.marketsize = marketsize;
	}

	public float getTotalmarketsharepax() {
		return totalmarketsharepax;
	}

	public void setTotalmarketsharepax(float totalmarketsharepax) {
		this.totalmarketsharepax = totalmarketsharepax;
	}

	public float getTotalmarketsharepaxlastyr() {
		return totalmarketsharepaxlastyr;
	}

	public void setTotalmarketsharepaxlastyr(float totalmarketsharepaxlastyr) {
		this.totalmarketsharepaxlastyr = totalmarketsharepaxlastyr;
	}

	public float getTotalmarketsize() {
		return totalmarketsize;
	}

	public void setTotalmarketsize(float totalmarketsize) {
		this.totalmarketsize = totalmarketsize;
	}

	public ArrayList<String> getMarketsharepaxList() {
		return marketsharepaxList;
	}

	public void setMarketsharepaxList(ArrayList<String> marketsharepaxList) {
		this.marketsharepaxList = marketsharepaxList;
	}

	public ArrayList<String> getMarketsharepaxList1() {
		return marketsharepaxList1;
	}

	public void setMarketsharepaxList1(ArrayList<String> marketsharepaxList1) {
		this.marketsharepaxList1 = marketsharepaxList1;
	}

	public ArrayList<String> getMarketsizeList() {
		return marketsizeList;
	}

	public void setMarketsizeList(ArrayList<String> marketsizeList) {
		this.marketsizeList = marketsizeList;
	}

	public float getMarketsize1() {
		return marketsize1;
	}

	public void setMarketsize1(float marketsize1) {
		this.marketsize1 = marketsize1;
	}

	public String getDep_date() {
		return dep_date;
	}

	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}

	public float getTotalmarketsize1() {
		return totalmarketsize1;
	}

	public void setTotalmarketsize1(float totalmarketsize1) {
		this.totalmarketsize1 = totalmarketsize1;
	}

	public float getTargetmarketshare() {
		return targetmarketshare;
	}

	public void setTargetmarketshare(float targetmarketshare) {
		this.targetmarketshare = targetmarketshare;
	}

	public float getMarketShareVTGT() {
		return marketShareVTGT;
	}

	public void setMarketShareVTGT(float marketShareVTGT) {
		this.marketShareVTGT = marketShareVTGT;
	}

	public float getPriceAvailabilityIndex() {
		return priceAvailabilityIndex;
	}

	public void setPriceAvailabilityIndex(float priceAvailabilityIndex) {
		this.priceAvailabilityIndex = priceAvailabilityIndex;
	}

	public void setFairmarketshare(float fairmarketshare) {
		this.fairmarketshare = fairmarketshare;
	}

	public float getFairmarketshare() {
		return fairmarketshare;
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

}
