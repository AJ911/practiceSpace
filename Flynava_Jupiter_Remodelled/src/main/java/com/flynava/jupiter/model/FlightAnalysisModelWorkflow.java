package com.flynava.jupiter.model;

import java.util.HashMap;
import java.util.Map;

public class FlightAnalysisModelWorkflow {

	private Map<String, Object> flightsDetailsMap = new HashMap<String, Object>();
	private String od;
	private Integer flightNo;
	private String travelDate;
	private float availabilityY;
	private float availabilityJ;
	private float availability_TL;
	private String origin;
	private String destination;
	private String key;

	private float booking_J;
	private float booking_Y;
	private float booking_TL;

	private float salesrevenueY;
	private float salespaxY;
	private float salesrevenueJ;
	private float salespaxJ;
	private float forecastpaxY;
	private float forecastpaxJ;

	private float forecastRevenueY;
	private float forecastRevenueJ;
	private float flownPaxY;
	private float flownrevenueY;
	private float flownPaxJ;
	private float flownrevenueJ;
	private float legDistance;

	private float forecastseatfactorY;
	private float forecastseatfactorJ;
	private float flownseatfactorY;
	private float flownseatfactorJ;
	private float capacity_J_CY;
	private float capacity_TL_CY;
	private float capacity_Y_CY;
	private float yieldJ;
	private float yield_TL;
	private float yieldlastyr_TL;
	private float yieldvlyr_TL;
	private Double seatfactor_TL;
	private float forecastseatfactor_TL;
	private float flownseatfactorTL;
	private float flownseatfactorvlyr_TL;
	private float seatfactorvlyrTL;
	private float averagefare;

	public Map<String, Object> getFlightsDetailsMap() {
		return flightsDetailsMap;
	}

	public void setFlightsDetailsMap(Map<String, Object> flightsDetailsMap) {
		this.flightsDetailsMap = flightsDetailsMap;
	}

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

	public float getAvailabilityY() {
		return availabilityY;
	}

	public void setAvailabilityY(float availabilityY) {
		this.availabilityY = availabilityY;
	}

	public float getAvailabilityJ() {
		return availabilityJ;
	}

	public void setAvailabilityJ(float availabilityJ) {
		this.availabilityJ = availabilityJ;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public float getBooking_J() {
		return booking_J;
	}

	public void setBooking_J(float booking_J) {
		this.booking_J = booking_J;
	}

	public float getBooking_Y() {
		return booking_Y;
	}

	public void setBooking_Y(float booking_Y) {
		this.booking_Y = booking_Y;
	}

	public float getBooking_TL() {
		return booking_TL;
	}

	public void setBooking_TL(float booking_TL) {
		this.booking_TL = booking_TL;
	}

	public float getSalesrevenueY() {
		return salesrevenueY;
	}

	public void setSalesrevenueY(float salesrevenueY) {
		this.salesrevenueY = salesrevenueY;
	}

	public float getSalespaxY() {
		return salespaxY;
	}

	public void setSalespaxY(float salespaxY) {
		this.salespaxY = salespaxY;
	}

	public float getSalesrevenueJ() {
		return salesrevenueJ;
	}

	public void setSalesrevenueJ(float salesrevenueJ) {
		this.salesrevenueJ = salesrevenueJ;
	}

	public float getSalespaxJ() {
		return salespaxJ;
	}

	public void setSalespaxJ(float salespaxJ) {
		this.salespaxJ = salespaxJ;
	}

	public float getForecastpaxY() {
		return forecastpaxY;
	}

	public void setForecastpaxY(float forecastpaxY) {
		this.forecastpaxY = forecastpaxY;
	}

	public float getForecastpaxJ() {
		return forecastpaxJ;
	}

	public void setForecastpaxJ(float forecastpaxJ) {
		this.forecastpaxJ = forecastpaxJ;
	}

	public float getForecastRevenueY() {
		return forecastRevenueY;
	}

	public void setForecastRevenueY(float forecastRevenueY) {
		this.forecastRevenueY = forecastRevenueY;
	}

	public float getForecastRevenueJ() {
		return forecastRevenueJ;
	}

	public void setForecastRevenueJ(float forecastRevenueJ) {
		this.forecastRevenueJ = forecastRevenueJ;
	}

	public float getFlownPaxY() {
		return flownPaxY;
	}

	public void setFlownPaxY(float flownPaxY) {
		this.flownPaxY = flownPaxY;
	}

	public float getFlownrevenueY() {
		return flownrevenueY;
	}

	public void setFlownrevenueY(float flownrevenueY) {
		this.flownrevenueY = flownrevenueY;
	}

	public float getFlownPaxJ() {
		return flownPaxJ;
	}

	public void setFlownPaxJ(float flownPaxJ) {
		this.flownPaxJ = flownPaxJ;
	}

	public float getFlownrevenueJ() {
		return flownrevenueJ;
	}

	public void setFlownrevenueJ(float flownrevenueJ) {
		this.flownrevenueJ = flownrevenueJ;
	}

	public float getLegDistance() {
		return legDistance;
	}

	public void setLegDistance(float legDistance) {
		this.legDistance = legDistance;
	}

	public float getForecastseatfactorY() {
		return forecastseatfactorY;
	}

	public void setForecastseatfactorY(float forecastseatfactorY) {
		this.forecastseatfactorY = forecastseatfactorY;
	}

	public float getForecastseatfactorJ() {
		return forecastseatfactorJ;
	}

	public void setForecastseatfactorJ(float forecastseatfactorJ) {
		this.forecastseatfactorJ = forecastseatfactorJ;
	}

	public float getFlownseatfactorY() {
		return flownseatfactorY;
	}

	public void setFlownseatfactorY(float flownseatfactorY) {
		this.flownseatfactorY = flownseatfactorY;
	}

	public float getFlownseatfactorJ() {
		return flownseatfactorJ;
	}

	public void setFlownseatfactorJ(float flownseatfactorJ) {
		this.flownseatfactorJ = flownseatfactorJ;
	}

	public float getCapacity_J_CY() {
		return capacity_J_CY;
	}

	public void setCapacity_J_CY(float capacity_J_CY) {
		this.capacity_J_CY = capacity_J_CY;
	}

	public float getCapacity_Y_CY() {
		return capacity_Y_CY;
	}

	public void setCapacity_Y_CY(float capacity_Y_CY) {
		this.capacity_Y_CY = capacity_Y_CY;
	}

	public float getCapacity_TL_CY() {
		return capacity_TL_CY;
	}

	public void setCapacity_TL_CY(float capacity_TL_CY) {
		this.capacity_TL_CY = capacity_TL_CY;
	}

	public float getAvailability_TL() {
		return availability_TL;
	}

	public void setAvailability_TL(float availability_TL) {
		this.availability_TL = availability_TL;
	}

	public float getYieldJ() {
		return yieldJ;
	}

	public void setYieldJ(float yieldJ) {
		this.yieldJ = yieldJ;
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

	public Double getSeatfactor_TL() {
		return seatfactor_TL;
	}

	public void setSeatfactor_TL(Double seatfactor_TL) {
		this.seatfactor_TL = seatfactor_TL;
	}

	public float getAveragefare() {
		return averagefare;
	}

	public void setAveragefare(float averagefare) {
		this.averagefare = averagefare;
	}

}