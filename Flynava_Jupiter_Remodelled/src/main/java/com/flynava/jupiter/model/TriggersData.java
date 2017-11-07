package com.flynava.jupiter.model;

import java.util.List;
import java.util.Map;

public class TriggersData {

	private String origin;
	private String destination;
	private String pos;
	private String compartment;
	private String type;
	private String triggerSubType;
	private String depDateFrom;
	private String depDateTo;
	private String hostLowestFiledFare;
	private String hostFrequentAvailabeFare;
	private String hostMarketShare;
	private String hostFms;

	private String triggerId;
	private String triggersDesc;

	private Map<String, String> hostRevenueMap;
	private Map<String, String> hostPaxMap;
	private Map<String, String> hostAvgFareMap;
	private Map<String, String> hostYieldMap;
	private Map<String, String> seatFactorMap;
	private List<Object> compList;

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

	public String getHostLowestFiledFare() {
		return hostLowestFiledFare;
	}

	public void setHostLowestFiledFare(String hostLowestFiledFare) {
		this.hostLowestFiledFare = hostLowestFiledFare;
	}

	public String getHostFrequentAvailabeFare() {
		return hostFrequentAvailabeFare;
	}

	public void setHostFrequentAvailabeFare(String hostFrequentAvailabeFare) {
		this.hostFrequentAvailabeFare = hostFrequentAvailabeFare;
	}

	public String getHostMarketShare() {
		return hostMarketShare;
	}

	public void setHostMarketShare(String hostMarketShare) {
		this.hostMarketShare = hostMarketShare;
	}

	public String getHostFms() {
		return hostFms;
	}

	public void setHostFms(String hostFms) {
		this.hostFms = hostFms;
	}

	public String getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}

	public Map<String, String> getHostRevenueMap() {
		return hostRevenueMap;
	}

	public void setHostRevenueMap(Map<String, String> hostRevenueMap) {
		this.hostRevenueMap = hostRevenueMap;
	}

	public Map<String, String> getHostPaxMap() {
		return hostPaxMap;
	}

	public void setHostPaxMap(Map<String, String> hostPaxMap) {
		this.hostPaxMap = hostPaxMap;
	}

	public Map<String, String> getHostAvgFareMap() {
		return hostAvgFareMap;
	}

	public void setHostAvgFareMap(Map<String, String> hostAvgFareMap) {
		this.hostAvgFareMap = hostAvgFareMap;
	}

	public Map<String, String> getHostYieldMap() {
		return hostYieldMap;
	}

	public void setHostYieldMap(Map<String, String> hostYieldMap) {
		this.hostYieldMap = hostYieldMap;
	}

	public Map<String, String> getSeatFactorMap() {
		return seatFactorMap;
	}

	public void setSeatFactorMap(Map<String, String> seatFactorMap) {
		this.seatFactorMap = seatFactorMap;
	}

	public String getTriggersDesc() {
		return triggersDesc;
	}

	public void setTriggersDesc(String triggersDesc) {
		this.triggersDesc = triggersDesc;
	}

	public List<Object> getCompList() {
		return compList;
	}

	public void setCompList(List<Object> compList) {
		this.compList = compList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTriggerSubType() {
		return triggerSubType;
	}

	public void setTriggerSubType(String triggerSubType) {
		this.triggerSubType = triggerSubType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((triggerId == null) ? 0 : triggerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TriggersData other = (TriggersData) obj;
		if (triggerId == null) {
			if (other.triggerId != null)
				return false;
		} else if (!triggerId.equals(other.triggerId))
			return false;
		return true;
	}

}
