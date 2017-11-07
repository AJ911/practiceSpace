package com.flynava.jupiter.model;

import java.util.ArrayList;

public class Fares {

	private String carrier;
	private int lowestFare;
	private int NoOfFares;
	private int highestFare;
	private int avarageSpellUp;

	private ArrayList<Integer> fares;
	private ArrayList<String> rbd;

	public ArrayList<String> getRbd() {
		return rbd;
	}

	public void setRbd(ArrayList<String> rbd) {
		this.rbd = rbd;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public int getLowestFare() {
		return lowestFare;
	}

	public void setLowestFare(int lowestFare) {
		this.lowestFare = lowestFare;
	}

	public int getNoOfFares() {
		return NoOfFares;
	}

	public void setNoOfFares(int noOfFares) {
		NoOfFares = noOfFares;
	}

	public int getHighestFare() {
		return highestFare;
	}

	public void setHighestFare(int highestFare) {
		this.highestFare = highestFare;
	}

	public int getAvarageSpellUp() {
		return avarageSpellUp;
	}

	public void setAvarageSpellUp(int avarageSpellUp) {
		this.avarageSpellUp = avarageSpellUp;
	}

	public ArrayList<Integer> getFares() {
		return fares;
	}

	public void setFares(ArrayList<Integer> fares) {
		this.fares = fares;
	}

}
