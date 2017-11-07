package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RevenueSplitModel {

	private String departureDate;
	private int month;
	private int days;
	private float revenueSalesYTD;
	private float revenueFlownYTD;
	private float revenueVLYR;
	private float revenueVTGT;
	private float RevenueYTD;

	private int paxYTD;
	private float paxVLYR;
	private float paxVTGT;
	private float paxpercentage;
	private float flownpax;
	private float flownrevenue;

	private float yieldVLYR;
	private float yieldVTGT;
	private float yieldYTD;
	private int count;
	private float totalpaxtarget;
	private float totalrevenuetarget;
	private float totalyieldtarget;

	private float revenueofy;
	private float revenueofj;
	private float revenueoff;

	private float revenuecompytd;
	private float paxofy;
	private float paxofj;
	private float paxoff;

	private float totalrevenueofy;
	private float totalrevenueofj;
	private float totalrevenueoff;

	private float totalpaxofy;
	private float totalpaxofj;
	private float totalpaxoff;
	private float totalpaxofcomp;
	private float paxofcomp;

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String paxtype;
	private String combinationkey;
	private String revenueShareKey;
	private float avgfare;
	private String od;

	private float totalRevenue;
	private float totalRevenue_lastyr;

	private int totalPax;
	private float totalPax_lastyr;
	private float totalYield;
	private float totalYield_lastyr;
	private float totaladultpaxYtd;
	private float totalchildpaxytd;
	private float totalinfpaxytd;
	private float revenueshareperc;
	private float totaladultpaxperc;
	private float totalchildpaxperc;
	private float totalinfpaxperc;

	private float totalFlownRevenue;
	private float totalFlownRevenue_lastyr;

	private float totalSalesRevenue;
	private float totalSalesRevenue_lastyr;

	private float totaldistance;
	// forcast
	private float totalforcastpax;
	private float totalforcastrevenue;
	private float totaltargetproratedpax;
	private float totaltargetproratedrevnue;
	private float totalflownpaxlastyr;
	private float hostcapacity;
	private float hostcapacitylastyr;

	public float getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(float paxVTGT) {
		this.paxVTGT = paxVTGT;
	}

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}

	public int getTotalPax() {
		return totalPax;
	}

	public void setTotalPax(int totalPax) {
		this.totalPax = totalPax;
	}

	public float getTotalPax_lastyr() {
		return totalPax_lastyr;
	}

	public void setTotalPax_lastyr(float totalPax_lastyr) {
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

	public float getPaxVLYR() {
		return paxVLYR;
	}

	public void setPaxVLYR(float paxVLYR) {
		this.paxVLYR = paxVLYR;
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

	public float getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(float totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public float getTotalRevenue_lastyr() {
		return totalRevenue_lastyr;
	}

	public void setTotalRevenue_lastyr(float totalRevenue_lastyr) {
		this.totalRevenue_lastyr = totalRevenue_lastyr;
	}

	public float getTotalFlownRevenue_lastyr() {
		return totalFlownRevenue_lastyr;
	}

	public void setTotalFlownRevenue_lastyr(float totalFlownRevenue_lastyr) {
		this.totalFlownRevenue_lastyr = totalFlownRevenue_lastyr;
	}

	public float getTotalSalesRevenue_lastyr() {
		return totalSalesRevenue_lastyr;
	}

	public void setTotalSalesRevenue_lastyr(float totalSalesRevenue_lastyr) {
		this.totalSalesRevenue_lastyr = totalSalesRevenue_lastyr;
	}

	public float getRevenueSalesYTD() {
		return revenueSalesYTD;
	}

	public void setRevenueSalesYTD(float revenueSalesYTD) {
		this.revenueSalesYTD = revenueSalesYTD;
	}

	public float getRevenueFlownYTD() {
		return revenueFlownYTD;
	}

	public void setRevenueFlownYTD(float revenueFlownYTD) {
		this.revenueFlownYTD = revenueFlownYTD;
	}

	public String getPaxtype() {
		return paxtype;
	}

	public void setPaxtype(String paxtype) {
		this.paxtype = paxtype;
	}

	public float getYieldYTD() {
		return yieldYTD;
	}

	public void setYieldYTD(float yieldYTD) {
		this.yieldYTD = yieldYTD;
	}

	public float getTotaladultpaxYtd() {
		return totaladultpaxYtd;
	}

	public void setTotaladultpaxYtd(float totaladultpaxYtd) {
		this.totaladultpaxYtd = totaladultpaxYtd;
	}

	public float getTotalinfpaxytd() {
		return totalinfpaxytd;
	}

	public void setTotalinfpaxytd(float totalinfpaxytd) {
		this.totalinfpaxytd = totalinfpaxytd;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getTotalchildpaxytd() {
		return totalchildpaxytd;
	}

	public void setTotalchildpaxytd(float totalchildpaxytd) {
		this.totalchildpaxytd = totalchildpaxytd;
	}

	public float getRevenueshareperc() {
		return revenueshareperc;
	}

	public void setRevenueshareperc(float revenueshareperc) {
		this.revenueshareperc = revenueshareperc;
	}

	public float getTotalFlownRevenue() {
		return totalFlownRevenue;
	}

	public void setTotalFlownRevenue(float totalFlownRevenue) {
		this.totalFlownRevenue = totalFlownRevenue;
	}

	public float getTotalSalesRevenue() {
		return totalSalesRevenue;
	}

	public void setTotalSalesRevenue(float totalSalesRevenue) {
		this.totalSalesRevenue = totalSalesRevenue;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public float getTotaldistance() {
		return totaldistance;
	}

	public void setTotaldistance(float totaldistance) {
		this.totaldistance = totaldistance;
	}

	public float getAvgfare() {
		return avgfare;
	}

	public void setAvgfare(float avgfare) {
		this.avgfare = avgfare;
	}

	public float getRevenueofy() {
		return revenueofy;
	}

	public void setRevenueofy(float revenueofy) {
		this.revenueofy = revenueofy;
	}

	public float getRevenueofj() {
		return revenueofj;
	}

	public void setRevenueofj(float revenueofj) {
		this.revenueofj = revenueofj;
	}

	public float getRevenueoff() {
		return revenueoff;
	}

	public void setRevenueoff(float revenueoff) {
		this.revenueoff = revenueoff;
	}

	public float getTotalrevenueofy() {
		return totalrevenueofy;
	}

	public void setTotalrevenueofy(float totalrevenueofy) {
		this.totalrevenueofy = totalrevenueofy;
	}

	public float getTotalrevenueofj() {
		return totalrevenueofj;
	}

	public void setTotalrevenueofj(float totalrevenueofj) {
		this.totalrevenueofj = totalrevenueofj;
	}

	public float getTotalrevenueoff() {
		return totalrevenueoff;
	}

	public void setTotalrevenueoff(float totalrevenueoff) {
		this.totalrevenueoff = totalrevenueoff;
	}

	public float getRevenuecompytd() {
		return revenuecompytd;
	}

	public void setRevenuecompytd(float revenuecompytd) {
		this.revenuecompytd = revenuecompytd;
	}

	public float getTotaladultpaxperc() {
		return totaladultpaxperc;
	}

	public void setTotaladultpaxperc(float totaladultpaxperc) {
		this.totaladultpaxperc = totaladultpaxperc;
	}

	public float getTotalchildpaxperc() {
		return totalchildpaxperc;
	}

	public void setTotalchildpaxperc(float totalchildpaxperc) {
		this.totalchildpaxperc = totalchildpaxperc;
	}

	public float getTotalinfpaxperc() {
		return totalinfpaxperc;
	}

	public void setTotalinfpaxperc(float totalinfpaxperc) {
		this.totalinfpaxperc = totalinfpaxperc;
	}

	public float getPaxofy() {
		return paxofy;
	}

	public void setPaxofy(float paxofy) {
		this.paxofy = paxofy;
	}

	public float getPaxofj() {
		return paxofj;
	}

	public void setPaxofj(float paxofj) {
		this.paxofj = paxofj;
	}

	public float getPaxoff() {
		return paxoff;
	}

	public void setPaxoff(float paxoff) {
		this.paxoff = paxoff;
	}

	public float getTotalpaxofy() {
		return totalpaxofy;
	}

	public void setTotalpaxofy(float totalpaxofy) {
		this.totalpaxofy = totalpaxofy;
	}

	public float getTotalpaxofj() {
		return totalpaxofj;
	}

	public void setTotalpaxofj(float totalpaxofj) {
		this.totalpaxofj = totalpaxofj;
	}

	public float getTotalpaxoff() {
		return totalpaxoff;
	}

	public void setTotalpaxoff(float totalpaxoff) {
		this.totalpaxoff = totalpaxoff;
	}

	public float getTotalpaxofcomp() {
		return totalpaxofcomp;
	}

	public void setTotalpaxofcomp(float totalpaxofcomp) {
		this.totalpaxofcomp = totalpaxofcomp;
	}

	public float getPaxofcomp() {
		return paxofcomp;
	}

	public void setPaxofcomp(float paxofcomp) {
		this.paxofcomp = paxofcomp;
	}

	public float getTotalpaxtarget() {
		return totalpaxtarget;
	}

	public void setTotalpaxtarget(float totalpaxtarget) {
		this.totalpaxtarget = totalpaxtarget;
	}

	public float getTotalrevenuetarget() {
		return totalrevenuetarget;
	}

	public void setTotalrevenuetarget(float totalrevenuetarget) {
		this.totalrevenuetarget = totalrevenuetarget;
	}

	public float getTotalyieldtarget() {
		return totalyieldtarget;
	}

	public void setTotalyieldtarget(float totalyieldtarget) {
		this.totalyieldtarget = totalyieldtarget;
	}

	public float getRevenueYTD() {
		return RevenueYTD;
	}

	public void setRevenueYTD(float revenueYTD) {
		RevenueYTD = revenueYTD;
	}

	public String getRevenueShareKey() {
		return revenueShareKey;
	}

	public void setRevenueShareKey(String revenueShareKey) {
		this.revenueShareKey = revenueShareKey;
	}

	public float getPaxpercentage() {
		return paxpercentage;
	}

	public void setPaxpercentage(float paxpercentage) {
		this.paxpercentage = paxpercentage;
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

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
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
