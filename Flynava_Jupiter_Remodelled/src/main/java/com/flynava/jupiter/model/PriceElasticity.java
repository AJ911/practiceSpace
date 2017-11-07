package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PriceElasticity {

	private String depdate;
	private float paxYTD;
	private float paxVLYR;
	private float paxVTGT;
	private float flownpax;
	private int months;
	private int days;
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String fare_basis;
	private String currency;
	private int pax;
	private int pax_1;
	private float price;
	private float base_fare;
	private String channel;
	private String customerSegment;
	private int pax_target;
	private int totalpaxtarget;
	private float totalforcastpax;
	private float totaltargetproratedpax;

	private float totalbasefare;
	private float totalrevenue;

	private String Combinationkey;
	private int totalPax;
	private int totalPax_lastyr;
	private float priceElasticity;
	private float totalflownpaxlastyr;
	private float hostcapacity;
	private float hostcapacitylastyr;

	public float getPaxYTD() {
		return paxYTD;
	}

	public void setPaxYTD(float paxYTD) {
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

	public String getFare_basis() {
		return fare_basis;
	}

	public void setFare_basis(String fare_basis) {
		this.fare_basis = fare_basis;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getBase_fare() {
		return base_fare;
	}

	public void setBase_fare(float base_fare) {
		this.base_fare = base_fare;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getPax_target() {
		return pax_target;
	}

	public void setPax_target(int pax_target) {
		this.pax_target = pax_target;
	}

	public String getCombinationkey() {
		return Combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		Combinationkey = combinationkey;
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

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	public float getTotalbasefare() {
		return totalbasefare;
	}

	public void setTotalbasefare(float totalbasefare) {
		this.totalbasefare = totalbasefare;
	}

	public float getTotalrevenue() {
		return totalrevenue;
	}

	public void setTotalrevenue(float totalrevenue) {
		this.totalrevenue = totalrevenue;
	}

	public int getTotalpaxtarget() {
		return totalpaxtarget;
	}

	public void setTotalpaxtarget(int totalpaxtarget) {
		this.totalpaxtarget = totalpaxtarget;
	}

	public float getPriceElasticity() {
		return priceElasticity;
	}

	public void setPriceElasticity(float priceElasticity) {
		this.priceElasticity = priceElasticity;
	}

	public float getFlownpax() {
		return flownpax;
	}

	public void setFlownpax(float flownpax) {
		this.flownpax = flownpax;
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

	public String getDepdate() {
		return depdate;
	}

	public void setDepdate(String depdate) {
		this.depdate = depdate;
	}

	public float getTotalforcastpax() {
		return totalforcastpax;
	}

	public void setTotalforcastpax(float totalforcastpax) {
		this.totalforcastpax = totalforcastpax;
	}

	public float getTotaltargetproratedpax() {
		return totaltargetproratedpax;
	}

	public void setTotaltargetproratedpax(float totaltargetproratedpax) {
		this.totaltargetproratedpax = totaltargetproratedpax;
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
