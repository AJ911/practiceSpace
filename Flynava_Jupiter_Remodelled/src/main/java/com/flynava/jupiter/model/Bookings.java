package com.flynava.jupiter.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Bookings {

	private String id = UUID.randomUUID().toString();

	private String dep_date;
	private String book_date;
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private long pax;
	private long pax_1;
	private float forecastPax;
	private long flownPax;
	private long ticket;
	private long netBooking_IndirectChannel;
	private long netBooking_DirectChannel;
	private float MarketSize;
	private float MarketSize_1;
	private float targetPax;
	private float hostBookingsYTD;
	private float hostBookingsVLYR;
	private float hostBookingsVTGT;
	private float hostTicketedPercentage;
	private int day;
	private int month;
	private int year;
	private float hostCapacity;
	private float hostCapacity_1;
	private long carrierCapacity;
	private long carrierCapacity_1;
	private String Key;

	private Map<String, Object> competitorsMap = new HashMap<String, Object>();

	public float getHostCapacity() {
		return hostCapacity;
	}

	public void setHostCapacity(float hostCapacity) {
		this.hostCapacity = hostCapacity;
	}

	public float getHostCapacity_1() {
		return hostCapacity_1;
	}

	public void setHostCapacity_1(float hostCapacity_1) {
		this.hostCapacity_1 = hostCapacity_1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDep_date() {
		return dep_date;
	}

	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}

	public String getBook_date() {
		return book_date;
	}

	public void setBook_date(String book_date) {
		this.book_date = book_date;
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

	public long getPax() {
		return pax;
	}

	public void setPax(long pax) {
		this.pax = pax;
	}

	public long getFlownPax() {
		return flownPax;
	}

	public void setFlownPax(long flownPax) {
		this.flownPax = flownPax;
	}

	public long getNetBooking_IndirectChannel() {
		return netBooking_IndirectChannel;
	}

	public void setNetBooking_IndirectChannel(long netBooking_IndirectChannel) {
		this.netBooking_IndirectChannel = netBooking_IndirectChannel;
	}

	public long getNetBooking_DirectChannel() {
		return netBooking_DirectChannel;
	}

	public void setNetBooking_DirectChannel(long netBooking_DirectChannel) {
		this.netBooking_DirectChannel = netBooking_DirectChannel;
	}

	public float getMarketSize() {
		return MarketSize;
	}

	public void setMarketSize(float marketSize) {
		MarketSize = marketSize;
	}

	public float getMarketSize_1() {
		return MarketSize_1;
	}

	public void setMarketSize_1(float marketSize_1) {
		MarketSize_1 = marketSize_1;
	}

	public float getHostBookingsYTD() {
		return hostBookingsYTD;
	}

	public void setHostBookingsYTD(float hostBookingsYTD) {
		this.hostBookingsYTD = hostBookingsYTD;
	}

	public float getHostBookingsVLYR() {
		return hostBookingsVLYR;
	}

	public void setHostBookingsVLYR(float hostBookingsVLYR) {
		this.hostBookingsVLYR = hostBookingsVLYR;
	}

	public float getHostBookingsVTGT() {
		return hostBookingsVTGT;
	}

	public void setHostBookingsVTGT(float hostBookingsVTGT) {
		this.hostBookingsVTGT = hostBookingsVTGT;
	}

	public float getHostTicketedPercentage() {
		return hostTicketedPercentage;
	}

	public void setHostTicketedPercentage(float hostTicketedPercentage) {
		this.hostTicketedPercentage = hostTicketedPercentage;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public long getTicket() {
		return ticket;
	}

	public void setTicket(long ticket) {
		this.ticket = ticket;
	}

	public long getPax_1() {
		return pax_1;
	}

	public void setPax_1(long pax_1) {
		this.pax_1 = pax_1;
	}

	public Map<String, Object> getCompetitorsMap() {
		return competitorsMap;
	}

	public void setCompetitorsMap(Map<String, Object> competitorsMap) {
		this.competitorsMap = competitorsMap;
	}

	public long getCarrierCapacity() {
		return carrierCapacity;
	}

	public void setCarrierCapacity(long carrierCapacity) {
		this.carrierCapacity = carrierCapacity;
	}

	public long getCarrierCapacity_1() {
		return carrierCapacity_1;
	}

	public void setCarrierCapacity_1(long carrierCapacity_1) {
		this.carrierCapacity_1 = carrierCapacity_1;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public float getForecastPax() {
		return forecastPax;
	}

	public void setForecastPax(float forecastPax) {
		this.forecastPax = forecastPax;
	}

	public float getTargetPax() {
		return targetPax;
	}

	public void setTargetPax(float targetPax) {
		this.targetPax = targetPax;
	}

}
