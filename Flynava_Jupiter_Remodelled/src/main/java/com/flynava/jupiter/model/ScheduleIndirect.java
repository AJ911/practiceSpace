package com.flynava.jupiter.model;

public class ScheduleIndirect {

	private String aircraft;
	private int daily_capacity;
	private int route_capacity;
	private String[] days_of_Week;
	private String airline;
	private int No_of_flight;
	private int average_trip_time;
	private int No_of_aircrafts;
	private int flights_per_day;
	private String[] type_of_aircraft;
	private float flight_nos_peak_time;
	private float off_peak_time;
	private float OTP;
	private float frequency_shift;
	private float rating;
	private float transit_time;
	private float stops;

	public String getAircraft() {
		return aircraft;
	}

	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	public int getDaily_capacity() {
		return daily_capacity;
	}

	public void setDaily_capacity(int daily_capacity) {
		this.daily_capacity = daily_capacity;
	}

	public int getRoute_capacity() {
		return route_capacity;
	}

	public void setRoute_capacity(int route_capacity) {
		this.route_capacity = route_capacity;
	}

	public String[] getDays_of_Week() {
		return days_of_Week;
	}

	public void setDays_of_Week(String[] days_of_Week) {
		this.days_of_Week = days_of_Week;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public int getNo_of_flight() {
		return No_of_flight;
	}

	public void setNo_of_flight(int no_of_flight) {
		No_of_flight = no_of_flight;
	}

	public int getAverage_trip_time() {
		return average_trip_time;
	}

	public void setAverage_trip_time(int average_trip_time) {
		this.average_trip_time = average_trip_time;
	}

	public int getNo_of_aircrafts() {
		return No_of_aircrafts;
	}

	public void setNo_of_aircrafts(int no_of_aircrafts) {
		No_of_aircrafts = no_of_aircrafts;
	}

	public int getFlights_per_day() {
		return flights_per_day;
	}

	public void setFlights_per_day(int flights_per_day) {
		this.flights_per_day = flights_per_day;
	}

	public String[] getType_of_aircraft() {
		return type_of_aircraft;
	}

	public void setType_of_aircraft(String[] type_of_aircraft) {
		this.type_of_aircraft = type_of_aircraft;
	}

	public float getFlight_nos_peak_time() {
		return flight_nos_peak_time;
	}

	public void setFlight_nos_peak_time(float flight_nos_peak_time) {
		this.flight_nos_peak_time = flight_nos_peak_time;
	}

	public float getOff_peak_time() {
		return off_peak_time;
	}

	public void setOff_peak_time(float off_peak_time) {
		this.off_peak_time = off_peak_time;
	}

	public float getOTP() {
		return OTP;
	}

	public void setOTP(float oTP) {
		OTP = oTP;
	}

	public float getFrequency_shift() {
		return frequency_shift;
	}

	public void setFrequency_shift(float frequency_shift) {
		this.frequency_shift = frequency_shift;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public float getTransit_time() {
		return transit_time;
	}

	public void setTransit_time(float transit_time) {
		this.transit_time = transit_time;
	}

	public float getStops() {
		return stops;
	}

	public void setStops(float stops) {
		this.stops = stops;
	}

}
