package com.flynava.jupiter.model;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Significant {

	private String revenueYTD;
	private String revenueVLYR;
	private String revenueVTGT;
	private float flownrevenue;
	private float flownpax;
	private float forcastrevenue;
	private float forcastpax;
	private int months;
	private int days;
	private float flownpaxlastyr;
	private float flownrevenue_lastyr;

	private String marketSharePax;
	private String marketSharePax_lastyr;

	private String ekmarketSharePax;
	private String ekmarketSharePax_lastyr;

	private String qrmarketSharePax;
	private String qrmarketSharePax_lastyr;

	private String wmarketSharePax;
	private String wmarketSharePax_lastyr;

	private String departureDate;

	private String marketSize;
	private String marketSize_lastyr;

	private String paxYTD;
	private String paxVLYR;
	private String paxVTGT;
	private String rank;

	private String marketShareYTD;
	private String marketShareVLYR;
	private String marketShareVTGT;

	private String wmarketShareYTD;
	private String wmarketShareVLYR;
	private String wmarketShareVTGT;

	private String QRmarketShareYTD;
	private String QRmarketShareVLYR;
	private String QRmarketShareVTGT;

	private String ekmarketShareYTD;
	private String ekmarketShareVLYR;
	private String ekmarketShareVTGT;

	private int fms;

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String pax_type;
	private int pax;
	private int pax_1;
	private float revenue;
	private float revenue_1;
	private float revenue_base;
	private String carrier;

	private int targetpax;
	private float target_revenue;
	private int market_share_pax;
	private int market_share_pax_1;
	private float target_market_size;
	private int market_size;
	private int market_size_1;
	private int capacity;
	private int totalRevenue;
	private int totalrevenue_lastyr;

	private int totalPax;
	private int totalPax_lastyr;
	private ArrayList<String> lCarrier = new ArrayList<String>();

	private int totalMarketshare;
	private int totalMarketshare_lastyr;

	private String combinationkey;

	private float marketshare;
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

	public float getMarketshare() {
		return marketshare;
	}

	public void setMarketshare(float marketshare) {
		this.marketshare = marketshare;
	}

	public int getMarket_share_pax() {
		return market_share_pax;
	}

	public void setMarket_share_pax(int market_share_pax) {
		this.market_share_pax = market_share_pax;
	}

	public String getRank() {
		return rank;
	}

	public int getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(int totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getTotalrevenue_lastyr() {
		return totalrevenue_lastyr;
	}

	public void setTotalrevenue_lastyr(int totalrevenue_lastyr) {
		this.totalrevenue_lastyr = totalrevenue_lastyr;
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

	public int getTotalMarketshare() {
		return totalMarketshare;
	}

	public void setTotalMarketshare(int totalMarketshare) {
		this.totalMarketshare = totalMarketshare;
	}

	public int getTotalMarketshare_lastyr() {
		return totalMarketshare_lastyr;
	}

	public void setTotalMarketshare_lastyr(int totalMarketshare_lastyr) {
		this.totalMarketshare_lastyr = totalMarketshare_lastyr;
	}

	public void setRank(String rank) {
		this.rank = rank;
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

	public String getQRmarketShareYTD() {
		return QRmarketShareYTD;
	}

	public void setQRmarketShareYTD(String qRmarketShareYTD) {
		QRmarketShareYTD = qRmarketShareYTD;
	}

	public String getQRmarketShareVLYR() {
		return QRmarketShareVLYR;
	}

	public void setQRmarketShareVLYR(String qRmarketShareVLYR) {
		QRmarketShareVLYR = qRmarketShareVLYR;
	}

	public String getQRmarketShareVTGT() {
		return QRmarketShareVTGT;
	}

	public void setQRmarketShareVTGT(String qRmarketShareVTGT) {
		QRmarketShareVTGT = qRmarketShareVTGT;
	}

	public int getFms() {
		return fms;
	}

	public void setFms(int fms) {
		this.fms = fms;
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

	public String getPax_type() {
		return pax_type;
	}

	public void setPax_type(String pax_type) {
		this.pax_type = pax_type;
	}

	public int getPax() {
		return pax;
	}

	public void setPax(int pax) {
		this.pax = pax;
	}

	public int getPax_1() {
		return pax_1;
	}

	public void setPax_1(int pax_1) {
		this.pax_1 = pax_1;
	}

	public float getRevenue() {
		return revenue;
	}

	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}

	public float getRevenue_1() {
		return revenue_1;
	}

	public void setRevenue_1(float revenue_1) {
		this.revenue_1 = revenue_1;
	}

	public float getRevenue_base() {
		return revenue_base;
	}

	public void setRevenue_base(float revenue_base) {
		this.revenue_base = revenue_base;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public int getTargetpax() {
		return targetpax;
	}

	public void setTargetpax(int targetpax) {
		this.targetpax = targetpax;
	}

	public float getTarget_revenue() {
		return target_revenue;
	}

	public void setTarget_revenue(float target_revenue) {
		this.target_revenue = target_revenue;
	}

	public int getMarket_share_pax_1() {
		return market_share_pax_1;
	}

	public void setMarket_share_pax_1(int market_share_pax_1) {
		this.market_share_pax_1 = market_share_pax_1;
	}

	public float getTarget_market_size() {
		return target_market_size;
	}

	public void setTarget_market_size(float target_market_size) {
		this.target_market_size = target_market_size;
	}

	public int getMarket_size() {
		return market_size;
	}

	public void setMarket_size(int market_size) {
		this.market_size = market_size;
	}

	public int getMarket_size_1() {
		return market_size_1;
	}

	public void setMarket_size_1(int market_size_1) {
		this.market_size_1 = market_size_1;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}

	public String getMarketSharePax() {
		return marketSharePax;
	}

	public void setMarketSharePax(String marketSharePax) {
		this.marketSharePax = marketSharePax;
	}

	public String getMarketSharePax_lastyr() {
		return marketSharePax_lastyr;
	}

	public void setMarketSharePax_lastyr(String marketSharePax_lastyr) {
		this.marketSharePax_lastyr = marketSharePax_lastyr;
	}

	public String getMarketSize() {
		return marketSize;
	}

	public void setMarketSize(String marketSize) {
		this.marketSize = marketSize;
	}

	public String getMarketSize_lastyr() {
		return marketSize_lastyr;
	}

	public void setMarketSize_lastyr(String marketSize_lastyr) {
		this.marketSize_lastyr = marketSize_lastyr;
	}

	public String getEkmarketShareYTD() {
		return ekmarketShareYTD;
	}

	public void setEkmarketShareYTD(String ekmarketShareYTD) {
		this.ekmarketShareYTD = ekmarketShareYTD;
	}

	public String getEkmarketShareVLYR() {
		return ekmarketShareVLYR;
	}

	public void setEkmarketShareVLYR(String ekmarketShareVLYR) {
		this.ekmarketShareVLYR = ekmarketShareVLYR;
	}

	public String getEkmarketShareVTGT() {
		return ekmarketShareVTGT;
	}

	public void setEkmarketShareVTGT(String ekmarketShareVTGT) {
		this.ekmarketShareVTGT = ekmarketShareVTGT;
	}

	public String getWmarketShareYTD() {
		return wmarketShareYTD;
	}

	public void setWmarketShareYTD(String wmarketShareYTD) {
		this.wmarketShareYTD = wmarketShareYTD;
	}

	public String getWmarketShareVLYR() {
		return wmarketShareVLYR;
	}

	public void setWmarketShareVLYR(String wmarketShareVLYR) {
		this.wmarketShareVLYR = wmarketShareVLYR;
	}

	public String getWmarketShareVTGT() {
		return wmarketShareVTGT;
	}

	public void setWmarketShareVTGT(String wmarketShareVTGT) {
		this.wmarketShareVTGT = wmarketShareVTGT;
	}

	public String getEkmarketSharePax() {
		return ekmarketSharePax;
	}

	public void setEkmarketSharePax(String ekmarketSharePax) {
		this.ekmarketSharePax = ekmarketSharePax;
	}

	public String getEkmarketSharePax_lastyr() {
		return ekmarketSharePax_lastyr;
	}

	public void setEkmarketSharePax_lastyr(String ekmarketSharePax_lastyr) {
		this.ekmarketSharePax_lastyr = ekmarketSharePax_lastyr;
	}

	public String getQrmarketSharePax() {
		return qrmarketSharePax;
	}

	public void setQrmarketSharePax(String qrmarketSharePax) {
		this.qrmarketSharePax = qrmarketSharePax;
	}

	public String getQrmarketSharePax_lastyr() {
		return qrmarketSharePax_lastyr;
	}

	public void setQrmarketSharePax_lastyr(String qrmarketSharePax_lastyr) {
		this.qrmarketSharePax_lastyr = qrmarketSharePax_lastyr;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getWmarketSharePax() {
		return wmarketSharePax;
	}

	public void setWmarketSharePax(String wmarketSharePax) {
		this.wmarketSharePax = wmarketSharePax;
	}

	public String getWmarketSharePax_lastyr() {
		return wmarketSharePax_lastyr;
	}

	public void setWmarketSharePax_lastyr(String wmarketSharePax_lastyr) {
		this.wmarketSharePax_lastyr = wmarketSharePax_lastyr;
	}

	public ArrayList<String> getlCarrier() {
		return lCarrier;
	}

	public void setlCarrier(ArrayList<String> lCarrier) {
		this.lCarrier = lCarrier;
	}

	public float getFlownrevenue() {
		return flownrevenue;
	}

	public void setFlownrevenue(float flownrevenue) {
		this.flownrevenue = flownrevenue;
	}

	public float getFlownpax() {
		return flownpax;
	}

	public void setFlownpax(float flownpax) {
		this.flownpax = flownpax;
	}

	public float getForcastrevenue() {
		return forcastrevenue;
	}

	public void setForcastrevenue(float forcastrevenue) {
		this.forcastrevenue = forcastrevenue;
	}

	public float getForcastpax() {
		return forcastpax;
	}

	public void setForcastpax(float forcastpax) {
		this.forcastpax = forcastpax;
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

	public float getFlownpaxlastyr() {
		return flownpaxlastyr;
	}

	public void setFlownpaxlastyr(float flownpaxlastyr) {
		this.flownpaxlastyr = flownpaxlastyr;
	}

	public float getFlownrevenue_lastyr() {
		return flownrevenue_lastyr;
	}

	public void setFlownrevenue_lastyr(float flownrevenue_lastyr) {
		this.flownrevenue_lastyr = flownrevenue_lastyr;
	}

}
