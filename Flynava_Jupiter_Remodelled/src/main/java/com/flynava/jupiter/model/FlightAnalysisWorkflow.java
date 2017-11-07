package com.flynava.jupiter.model;

import java.util.Map;

public class FlightAnalysisWorkflow {

	private String od;
	private Integer flightNo;
	private String travelDate;

	private float availability_TL;
	private String origin;
	private String destination;
	private String key;

	private float averagefare;

	private float capacity_TL_CY;

	private float booking_TL;

	private float salesrevenue;
	private float salespax;
	private float forecastpax;

	private float forecastRevenue;
	private float flownPax;
	private float flownrevenue;
	private float legDistance;

	private float yield_TL;

	private float yieldlastyr_TL;

	private float yieldvlyr_TL;

	private float seatfactor_TL;

	private float forecastseatfactor_TL;

	private float flownseatfactorTL;

	private float flownseatfactorvlyr_TL;

	private float seatfactorvlyrTL;

	private Map<String, Object> flightsDetailsMap;

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public Integer getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(Integer flightNo) {
		this.flightNo = flightNo;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
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

	public float getCapacity_TL_CY() {
		return capacity_TL_CY;
	}

	public void setCapacity_TL_CY(float capacity_TL_CY) {
		this.capacity_TL_CY = capacity_TL_CY;
	}

	public float getBooking_TL() {
		return booking_TL;
	}

	public void setBooking_TL(float booking_TL) {
		this.booking_TL = booking_TL;
	}

	public float getLegDistance() {
		return legDistance;
	}

	public void setLegDistance(float legDistance) {
		this.legDistance = legDistance;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public float getYield_TL() {
		return yield_TL;
	}

	public void setYield_TL(float yield_TL) {
		this.yield_TL = yield_TL;
	}

	public float getYieldlastyr_TL() {
		return yieldlastyr_TL;
	}

	public void setYieldlastyr_TL(float yieldlastyr_TL) {
		this.yieldlastyr_TL = yieldlastyr_TL;
	}

	public float getYieldvlyr_TL() {
		return yieldvlyr_TL;
	}

	public void setYieldvlyr_TL(float yieldvlyr_TL) {
		this.yieldvlyr_TL = yieldvlyr_TL;
	}

	public float getSeatfactor_TL() {
		return seatfactor_TL;
	}

	public void setSeatfactor_TL(float seatfactor_TL) {
		this.seatfactor_TL = seatfactor_TL;
	}

	public float getForecastseatfactor_TL() {
		return forecastseatfactor_TL;
	}

	public void setForecastseatfactor_TL(float forecastseatfactor_TL) {
		this.forecastseatfactor_TL = forecastseatfactor_TL;
	}

	public float getFlownseatfactorTL() {
		return flownseatfactorTL;
	}

	public void setFlownseatfactorTL(float flownseatfactorTL) {
		this.flownseatfactorTL = flownseatfactorTL;
	}

	public float getFlownseatfactorvlyr_TL() {
		return flownseatfactorvlyr_TL;
	}

	public void setFlownseatfactorvlyr_TL(float flownseatfactorvlyr_TL) {
		this.flownseatfactorvlyr_TL = flownseatfactorvlyr_TL;
	}

	public float getSeatfactorvlyrTL() {
		return seatfactorvlyrTL;
	}

	public void setSeatfactorvlyrTL(float seatfactorvlyrTL) {
		this.seatfactorvlyrTL = seatfactorvlyrTL;
	}

	public float getAvailability_TL() {
		return availability_TL;
	}

	public void setAvailability_TL(float availability_TL) {
		this.availability_TL = availability_TL;
	}

	public Map<String, Object> getFlightsDetailsMap() {
		return flightsDetailsMap;
	}

	public void setFlightsDetailsMap(Map<String, Object> flightsDetailsMap) {
		this.flightsDetailsMap = flightsDetailsMap;
	}

	public float getSalesrevenue() {
		return salesrevenue;
	}

	public void setSalesrevenue(float salesrevenue) {
		this.salesrevenue = salesrevenue;
	}

	public float getSalespax() {
		return salespax;
	}

	public void setSalespax(float salespax) {
		this.salespax = salespax;
	}

	public float getForecastpax() {
		return forecastpax;
	}

	public void setForecastpax(float forecastpax) {
		this.forecastpax = forecastpax;
	}

	public float getForecastRevenue() {
		return forecastRevenue;
	}

	public void setForecastRevenue(float forecastRevenue) {
		this.forecastRevenue = forecastRevenue;
	}

	public float getFlownPax() {
		return flownPax;
	}

	public void setFlownPax(float flownPax) {
		this.flownPax = flownPax;
	}

	public float getFlownrevenue() {
		return flownrevenue;
	}

	public void setFlownrevenue(float flownrevenue) {
		this.flownrevenue = flownrevenue;
	}

	public float getAveragefare() {
		return averagefare;
	}

	public void setAveragefare(float averagefare) {
		this.averagefare = averagefare;
	}

}
