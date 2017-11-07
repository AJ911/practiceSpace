package com.flynava.jupiter.model;

import java.util.Map;

public class FaresSellup {

	private String carrier;
	private String od;
	private String origin;
	private String destination;
	private int lowestFrae;
	private Map<String, Object> sellUp;

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

	public int getLowestFrae() {
		return lowestFrae;
	}

	public void setLowestFrae(int lowestFrae) {
		this.lowestFrae = lowestFrae;
	}

	public Map<String, Object> getSellUp() {
		return sellUp;
	}

	public void setSellUp(Map<String, Object> sellUp) {
		this.sellUp = sellUp;
	}

}
