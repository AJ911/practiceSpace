package com.flynava.jupiter.model;

public class FlightAnalysis {

	private String airline;
	private String od;
	private String aircraft;
	private String flight_no;
	private String frequency;
	private String effective_from_date;
	private String effective_to_date;
	private String start_time;
	private String end_time;
	private int time_travel;
	private int flightCapacity;
	private int Total_capacity;

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public String getAircraft() {
		return aircraft;
	}

	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	public String getFlight_no() {
		return flight_no;
	}

	public void setFlight_no(String flight_no) {
		this.flight_no = flight_no;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getEffective_from_date() {
		return effective_from_date;
	}

	public void setEffective_from_date(String effective_from_date) {
		this.effective_from_date = effective_from_date;
	}

	public String getEffective_to_date() {
		return effective_to_date;
	}

	public void setEffective_to_date(String effective_to_date) {
		this.effective_to_date = effective_to_date;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getTime_travel() {
		return time_travel;
	}

	public void setTime_travel(int time_travel) {
		this.time_travel = time_travel;
	}

	public int getFlightCapacity() {
		return flightCapacity;
	}

	public void setFlightCapacity(int flightCapacity) {
		this.flightCapacity = flightCapacity;
	}

	public int getTotal_capacity() {
		return Total_capacity;
	}

	public void setTotal_capacity(int total_capacity) {
		Total_capacity = total_capacity;
	}

}
