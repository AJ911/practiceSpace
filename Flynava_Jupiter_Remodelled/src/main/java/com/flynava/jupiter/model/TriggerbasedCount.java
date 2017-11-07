package com.flynava.jupiter.model;

import java.util.HashMap;
import java.util.Map;

public class TriggerbasedCount {
	private String pos;
	private String od;
	private String compartment;
	private String key;
	private int Count;
	private String status;
	private String actiontime;
	private String actiondate;
	private String triggereventTime;
	private String triggereventDate;

	private long diffSeconds;
	private long diffMinutes;
	private long diffHours;
	private long diffDays;

	Map<String, Object> countMap = new HashMap<String, Object>();

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	public Map<String, Object> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<String, Object> countMap) {
		this.countMap = countMap;
	}

	public String getActiontime() {
		return actiontime;
	}

	public void setActiontime(String actiontime) {
		this.actiontime = actiontime;
	}

	public String getTriggereventTime() {
		return triggereventTime;
	}

	public void setTriggereventTime(String triggereventTime) {
		this.triggereventTime = triggereventTime;
	}

	public long getDiffSeconds() {
		return diffSeconds;
	}

	public void setDiffSeconds(long diffSeconds) {
		this.diffSeconds = diffSeconds;
	}

	public long getDiffMinutes() {
		return diffMinutes;
	}

	public void setDiffMinutes(long diffMinutes) {
		this.diffMinutes = diffMinutes;
	}

	public long getDiffHours() {
		return diffHours;
	}

	public void setDiffHours(long diffHours) {
		this.diffHours = diffHours;
	}

	public long getDiffDays() {
		return diffDays;
	}

	public void setDiffDays(long diffDays) {
		this.diffDays = diffDays;
	}

	public String getActiondate() {
		return actiondate;
	}

	public void setActiondate(String actiondate) {
		this.actiondate = actiondate;
	}

	public String getTriggereventDate() {
		return triggereventDate;
	}

	public void setTriggereventDate(String triggereventDate) {
		this.triggereventDate = triggereventDate;
	}

}
