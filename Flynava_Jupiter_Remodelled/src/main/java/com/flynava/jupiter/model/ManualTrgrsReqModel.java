package com.flynava.jupiter.model;

import java.util.List;
import java.util.Map;

public class ManualTrgrsReqModel {

	private String fromDate;
	private String toDate;
	private String biDirectional;

	private Map<String, List<String>> posMap;
	private Map<String, List<String>> originMap;
	private Map<String, List<String>> destMap;
	private Map<String, List<String>> compMap;

	private Map<String, List<String>> exclPosMap;
	private Map<String, List<String>> exclOriginMap;
	private Map<String, List<String>> exclDestMap;
	private Map<String, List<String>> exclCompMap;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getBiDirectional() {
		return biDirectional;
	}

	public void setBiDirectional(String biDirectional) {
		this.biDirectional = biDirectional;
	}

	public Map<String, List<String>> getPosMap() {
		return posMap;
	}

	public void setPosMap(Map<String, List<String>> posMap) {
		this.posMap = posMap;
	}

	public Map<String, List<String>> getOriginMap() {
		return originMap;
	}

	public void setOriginMap(Map<String, List<String>> originMap) {
		this.originMap = originMap;
	}

	public Map<String, List<String>> getDestMap() {
		return destMap;
	}

	public void setDestMap(Map<String, List<String>> destMap) {
		this.destMap = destMap;
	}

	public Map<String, List<String>> getCompMap() {
		return compMap;
	}

	public void setCompMap(Map<String, List<String>> compMap) {
		this.compMap = compMap;
	}

	public Map<String, List<String>> getExclPosMap() {
		return exclPosMap;
	}

	public void setExclPosMap(Map<String, List<String>> exclPosMap) {
		this.exclPosMap = exclPosMap;
	}

	public Map<String, List<String>> getExclOriginMap() {
		return exclOriginMap;
	}

	public void setExclOriginMap(Map<String, List<String>> exclOriginMap) {
		this.exclOriginMap = exclOriginMap;
	}

	public Map<String, List<String>> getExclDestMap() {
		return exclDestMap;
	}

	public void setExclDestMap(Map<String, List<String>> exclDestMap) {
		this.exclDestMap = exclDestMap;
	}

	public Map<String, List<String>> getExclCompMap() {
		return exclCompMap;
	}

	public void setExclCompMap(Map<String, List<String>> exclCompMap) {
		this.exclCompMap = exclCompMap;
	}

}
