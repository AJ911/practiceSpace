package com.flynava.jupiter.model;

import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

public class ODTrigger {

	private String origin;
	private String destination;
	private int fltDurMinutes;
	private String pos;
	private String compartment;
	private String depDateFrom;
	private String depDateTo;
	private float hostLowestFiledFare; //
	private float hostFrequentAvailabeFare;
	private float hostMarketShare;
	private float hostFms;

	private List<DBObject> triggersList;

	private Map<String, Float> hostRevenueMap;
	private Map<String, Float> hostPaxMap;
	private Map<String, Float> hostAvgFareMap;
	private Map<String, Float> hostYieldMap;
	private Map<String, Float> seatFactorMap;
	private Map<String, Object> compMap;

	private List<Object> compList;
	private List<TriggerFares> faresList;

	private String key;

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

	public int getFltDurMinutes() {
		return fltDurMinutes;
	}

	public void setFltDurMinutes(int fltDurMinutes) {
		this.fltDurMinutes = fltDurMinutes;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	public String getDepDateFrom() {
		return depDateFrom;
	}

	public void setDepDateFrom(String depDateFrom) {
		this.depDateFrom = depDateFrom;
	}

	public String getDepDateTo() {
		return depDateTo;
	}

	public void setDepDateTo(String depDateTo) {
		this.depDateTo = depDateTo;
	}

	public List<DBObject> getTriggersList() {
		return triggersList;
	}

	public void setTriggersList(List<DBObject> triggersList) {
		this.triggersList = triggersList;
	}

	public Map<String, Object> getCompMap() {
		return compMap;
	}

	public void setCompMap(Map<String, Object> compMap) {
		this.compMap = compMap;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<TriggerFares> getFaresList() {
		return faresList;
	}

	public void setFaresList(List<TriggerFares> faresList) {
		this.faresList = faresList;
	}

	public List<Object> getCompList() {
		return compList;
	}

	public void setCompList(List<Object> compList) {
		this.compList = compList;
	}

	public float getHostLowestFiledFare() {
		return hostLowestFiledFare;
	}

	public void setHostLowestFiledFare(float hostLowestFiledFare) {
		this.hostLowestFiledFare = hostLowestFiledFare;
	}

	public float getHostFrequentAvailabeFare() {
		return hostFrequentAvailabeFare;
	}

	public void setHostFrequentAvailabeFare(float hostFrequentAvailabeFare) {
		this.hostFrequentAvailabeFare = hostFrequentAvailabeFare;
	}

	public float getHostMarketShare() {
		return hostMarketShare;
	}

	public void setHostMarketShare(float hostMarketShare) {
		this.hostMarketShare = hostMarketShare;
	}

	public float getHostFms() {
		return hostFms;
	}

	public void setHostFms(float hostFms) {
		this.hostFms = hostFms;
	}

	public Map<String, Float> getHostRevenueMap() {
		return hostRevenueMap;
	}

	public void setHostRevenueMap(Map<String, Float> hostRevenueMap) {
		this.hostRevenueMap = hostRevenueMap;
	}

	public Map<String, Float> getHostPaxMap() {
		return hostPaxMap;
	}

	public void setHostPaxMap(Map<String, Float> hostPaxMap) {
		this.hostPaxMap = hostPaxMap;
	}

	public Map<String, Float> getHostAvgFareMap() {
		return hostAvgFareMap;
	}

	public void setHostAvgFareMap(Map<String, Float> hostAvgFareMap) {
		this.hostAvgFareMap = hostAvgFareMap;
	}

	public Map<String, Float> getHostYieldMap() {
		return hostYieldMap;
	}

	public void setHostYieldMap(Map<String, Float> hostYieldMap) {
		this.hostYieldMap = hostYieldMap;
	}

	public Map<String, Float> getSeatFactorMap() {
		return seatFactorMap;
	}

	public void setSeatFactorMap(Map<String, Float> seatFactorMap) {
		this.seatFactorMap = seatFactorMap;
	}

}
