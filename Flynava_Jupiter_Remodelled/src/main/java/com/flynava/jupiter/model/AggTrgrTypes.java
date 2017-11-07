package com.flynava.jupiter.model;

import java.util.List;
import java.util.Map;

public class AggTrgrTypes {

	private List<Map.Entry<String, Integer>> dshbTrgrList;
	private List<Map.Entry<String, Integer>> eventTrgrList;
	private List<Map.Entry<String, Integer>> eventTypeTrgrList;
	private List<Map.Entry<String, Integer>> trgrTypeList;

	public List<Map.Entry<String, Integer>> getDshbTrgrList() {
		return dshbTrgrList;
	}

	public void setDshbTrgrList(List<Map.Entry<String, Integer>> dshbTrgrList) {
		this.dshbTrgrList = dshbTrgrList;
	}

	public List<Map.Entry<String, Integer>> getEventTrgrList() {
		return eventTrgrList;
	}

	public void setEventTrgrList(List<Map.Entry<String, Integer>> eventTrgrList) {
		this.eventTrgrList = eventTrgrList;
	}

	public List<Map.Entry<String, Integer>> getEventTypeTrgrList() {
		return eventTypeTrgrList;
	}

	public void setEventTypeTrgrList(List<Map.Entry<String, Integer>> eventTypeTrgrList) {
		this.eventTypeTrgrList = eventTypeTrgrList;
	}

	public List<Map.Entry<String, Integer>> getTrgrTypeList() {
		return trgrTypeList;
	}

	public void setTrgrTypeList(List<Map.Entry<String, Integer>> trgrTypeList) {
		this.trgrTypeList = trgrTypeList;
	}

}
