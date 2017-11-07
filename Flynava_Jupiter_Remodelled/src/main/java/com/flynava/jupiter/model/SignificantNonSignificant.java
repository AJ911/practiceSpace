package com.flynava.jupiter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SignificantNonSignificant {

	private float revenueYTD;
	private float revenueVLYR;
	private float revenueVTGT;
	private float flownpax;
	private float flownrevenue;
	private int months;
	private int days;
	private float totalforcastpax;
	private float totalforcastrevenue;
	private float totaltargetproratedpax;
	private float totaltargetproratedrevenue;

	private int paxYTD;
	private float paxVLYR;
	private float paxVTGT;
	private int hostRank;
	private float totalrevenuetarget;
	private float totalpaxtarget;

	private float marketShareYTD;
	private float marketShareVLYR;
	private float marketShareVTGT;

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String carrier;
	private float fms;

	private float totalRevenue;
	private float totalrevenue_lastyr;

	private int totalPax;
	private int totalPax_lastyr;

	private float totalMarketshare;
	private float totalMarketshare_lastyr;

	private int marketSharePax;
	private int marketSharePax_lastyr;

	private double totalMarketSize_lastyr;

	private int marketSize;
	private int marketSize_lastyr;
	private double totalMarketSize;

	private String departureDate;

	private String combinationkey;

	private float marketshare;
	private float marketshare_1;
	private ArrayList<String> lCarrier = new ArrayList<String>();

	private List lCompetitor = new ArrayList();
	private Map<String, Object> competitorsMap = new HashMap<String, Object>();
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
	private float totalFlownRevenue_lastyr;
	private float totalflownpaxlastyr;
	private float hostcapacity;
	private float hostcapacitylastyr;

	public float getFms() {
		return fms;
	}

	public void setFms(float fms) {
		this.fms = fms;
	}

	public List getlCompetitor() {
		return lCompetitor;
	}

	public void setlCompetitor(List lCompetitor) {
		this.lCompetitor = lCompetitor;
	}

	public float getMarketshare_1() {
		return marketshare_1;
	}

	public void setMarketshare_1(float marketshare_1) {
		this.marketshare_1 = marketshare_1;
	}

	public float getMarketshare() {
		return marketshare;
	}

	public void setMarketshare(float marketshare) {
		this.marketshare = marketshare;
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

	public float getMarketShareVTGT() {
		return marketShareVTGT;
	}

	public void setMarketShareVTGT(float marketShareVTGT) {
		this.marketShareVTGT = marketShareVTGT;
	}

	public float getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(float totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public float getTotalrevenue_lastyr() {
		return totalrevenue_lastyr;
	}

	public void setTotalrevenue_lastyr(float totalrevenue_lastyr) {
		this.totalrevenue_lastyr = totalrevenue_lastyr;
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

	public void setTotalMarketshare_lastyr(int totalMarketshare_lastyr) {
		this.totalMarketshare_lastyr = totalMarketshare_lastyr;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
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

	public float getPaxVLYR() {
		return paxVLYR;
	}

	public void setPaxVLYR(float paxVLYR) {
		this.paxVLYR = paxVLYR;
	}

	public float getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(float paxVTGT) {
		this.paxVTGT = paxVTGT;
	}

	public int getHostRank() {
		return hostRank;
	}

	public void setHostRank(int hostRank) {
		this.hostRank = hostRank;
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

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}

	public Map<String, Object> getCompetitorsMap() {
		return competitorsMap;
	}

	public void setCompetitorsMap(Map<String, Object> competitorsMap) {
		this.competitorsMap = competitorsMap;
	}

	public int getMarketSharePax() {
		return marketSharePax;
	}

	public void setMarketSharePax(int marketSharePax) {
		this.marketSharePax = marketSharePax;
	}

	public int getMarketSharePax_lastyr() {
		return marketSharePax_lastyr;
	}

	public void setMarketSharePax_lastyr(int marketSharePax_lastyr) {
		this.marketSharePax_lastyr = marketSharePax_lastyr;
	}

	public int getMarketSize() {
		return marketSize;
	}

	public void setMarketSize(int marketSize) {
		this.marketSize = marketSize;
	}

	public int getMarketSize_lastyr() {
		return marketSize_lastyr;
	}

	public void setMarketSize_lastyr(int marketSize_lastyr) {
		this.marketSize_lastyr = marketSize_lastyr;
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

	public double getTotalMarketSize_lastyr() {
		return totalMarketSize_lastyr;
	}

	public void setTotalMarketSize_lastyr(double totalMarketSize_lastyr) {
		this.totalMarketSize_lastyr = totalMarketSize_lastyr;
	}

	public ArrayList<String> getlCarrier() {
		return lCarrier;
	}

	public void setlCarrier(ArrayList<String> lCarrier) {
		this.lCarrier = lCarrier;
	}

	public float getTotalrevenuetarget() {
		return totalrevenuetarget;
	}

	public void setTotalrevenuetarget(float totalrevenuetarget) {
		this.totalrevenuetarget = totalrevenuetarget;
	}

	public float getTotalpaxtarget() {
		return totalpaxtarget;
	}

	public void setTotalpaxtarget(float totalpaxtarget) {
		this.totalpaxtarget = totalpaxtarget;
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

	public float getTotaltargetproratedrevenue() {
		return totaltargetproratedrevenue;
	}

	public void setTotaltargetproratedrevenue(float totaltargetproratedrevenue) {
		this.totaltargetproratedrevenue = totaltargetproratedrevenue;
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

	public float getTotalFlownRevenue_lastyr() {
		return totalFlownRevenue_lastyr;
	}

	public void setTotalFlownRevenue_lastyr(float totalFlownRevenue_lastyr) {
		this.totalFlownRevenue_lastyr = totalFlownRevenue_lastyr;
	}

	public float getTotalflownpaxlastyr() {
		return totalflownpaxlastyr;
	}

	public void setTotalflownpaxlastyr(float totalflownpaxlastyr) {
		this.totalflownpaxlastyr = totalflownpaxlastyr;
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

}
