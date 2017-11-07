package com.flynava.jupiter.model;

public class MonthlyLowFare {

	private int lowFare;
	private String carrier;
	private String od;
	private String depDate;

	public String getDepDate() {
		return depDate;
	}

	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	public int getLowFare() {
		return lowFare;
	}

	public void setLowFare(int lowFare) {
		this.lowFare = lowFare;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

}
