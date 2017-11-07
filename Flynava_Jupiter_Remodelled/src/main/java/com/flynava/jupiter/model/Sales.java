package com.flynava.jupiter.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sales {

	private String dep_date;
	private String Region;
	private String Country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private long pax;
	private long pax_1;
	private float targetPax;
	private long flownPax;
	private float forecastPax;
	private float revenueForecast;
	private long revenue;
	private long revenue_1;
	private long flownRevenue;
	private long flownRevenue_1;
	private float targetRevenue;
	private long marketSize;
	private long marketSize_1;
	private long hostRevenueSales;
	private long hostRevenueFlown;
	private long hostRevenueVLYR;
	private long HostRevenueVTGT;
	private long hostPaxYTD;
	private long hostPaxVLYR;
	private long hostPaxVTGT;
	private int month;
	private int year;
	private int day;
	private float carrierCapacity;
	private float carrierCapacity_1;
	private String key;

	private Map<String, Object> competitors = new HashMap<String, Object>();

	public long getFlownPax() {
		return flownPax;
	}

	public void setFlownPax(long flownPax) {
		this.flownPax = flownPax;
	}

	public String getDep_date() {
		return dep_date;
	}

	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
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

	public long getPax() {
		return pax;
	}

	public void setPax(long pax) {
		this.pax = pax;
	}

	public long getPax_1() {
		return pax_1;
	}

	public void setPax_1(long pax_1) {
		this.pax_1 = pax_1;
	}

	public long getRevenue() {
		return revenue;
	}

	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}

	public long getRevenue_1() {
		return revenue_1;
	}

	public void setRevenue_1(long revenue_1) {
		this.revenue_1 = revenue_1;
	}

	public long getFlownRevenue() {
		return flownRevenue;
	}

	public void setFlownRevenue(long flownRevenue) {
		this.flownRevenue = flownRevenue;
	}

	public long getFlownRevenue_1() {
		return flownRevenue_1;
	}

	public void setFlownRevenue_1(long flownRevenue_1) {
		this.flownRevenue_1 = flownRevenue_1;
	}

	public long getMarketSize() {
		return marketSize;
	}

	public void setMarketSize(long marketSize) {
		this.marketSize = marketSize;
	}

	public long getMarketSize_1() {
		return marketSize_1;
	}

	public void setMarketSize_1(long marketSize_1) {
		this.marketSize_1 = marketSize_1;
	}

	public long getHostRevenueSales() {
		return hostRevenueSales;
	}

	public void setHostRevenueSales(long hostRevenueSales) {
		this.hostRevenueSales = hostRevenueSales;
	}

	public long getHostRevenueFlown() {
		return hostRevenueFlown;
	}

	public void setHostRevenueFlown(long hostRevenueFlown) {
		this.hostRevenueFlown = hostRevenueFlown;
	}

	public long getHostRevenueVLYR() {
		return hostRevenueVLYR;
	}

	public void setHostRevenueVLYR(long hostRevenueVLYR) {
		this.hostRevenueVLYR = hostRevenueVLYR;
	}

	public long getHostRevenueVTGT() {
		return HostRevenueVTGT;
	}

	public void setHostRevenueVTGT(long hostRevenueVTGT) {
		HostRevenueVTGT = hostRevenueVTGT;
	}

	public long getHostPaxYTD() {
		return hostPaxYTD;
	}

	public void setHostPaxYTD(long hostPaxYTD) {
		this.hostPaxYTD = hostPaxYTD;
	}

	public long getHostPaxVLYR() {
		return hostPaxVLYR;
	}

	public void setHostPaxVLYR(long hostPaxVLYR) {
		this.hostPaxVLYR = hostPaxVLYR;
	}

	public long getHostPaxVTGT() {
		return hostPaxVTGT;
	}

	public void setHostPaxVTGT(long hostPaxVTGT) {
		this.hostPaxVTGT = hostPaxVTGT;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public float getCarrierCapacity() {
		return carrierCapacity;
	}

	public void setCarrierCapacity(float carrierCapacity) {
		this.carrierCapacity = carrierCapacity;
	}

	public float getCarrierCapacity_1() {
		return carrierCapacity_1;
	}

	public void setCarrierCapacity_1(float carrierCapacity_1) {
		this.carrierCapacity_1 = carrierCapacity_1;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, Object> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(Map<String, Object> competitors) {
		this.competitors = competitors;
	}

	public float getTargetPax() {
		return targetPax;
	}

	public void setTargetPax(float targetPax) {
		this.targetPax = targetPax;
	}

	public float getForecastPax() {
		return forecastPax;
	}

	public void setForecastPax(float forecastPax) {
		this.forecastPax = forecastPax;
	}

	public float getRevenueForecast() {
		return revenueForecast;
	}

	public void setRevenueForecast(float revenueForecast) {
		this.revenueForecast = revenueForecast;
	}

	public float getTargetRevenue() {
		return targetRevenue;
	}

	public void setTargetRevenue(float targetRevenue) {
		this.targetRevenue = targetRevenue;
	}

}
