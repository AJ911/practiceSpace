package com.flynava.jupiter.model;

public class Events {

	private String name;
	private String value;
	private String origin;
	private String destination;
	private String year;
	private String start_date;
	private String end_date;
	private String season_flag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getSeason_flag() {
		return season_flag;
	}

	public void setSeason_flag(String season_flag) {
		this.season_flag = season_flag;
	}

	@Override
	public String toString() {
		return "Events [name=" + name + ", value=" + value + ", origin=" + origin + ", destination=" + destination
				+ ", year=" + year + ", start_date=" + start_date + ", end_date=" + end_date + ", season_flag="
				+ season_flag + "]";
	}

}
