package com.flynava.jupiter.model;

import java.util.ArrayList;
import java.util.List;

public class PriceAvailabilityTotalResponse {

	private float totalmarketshareytd;
	private float totalmarketsharevlyr;
	private float totalmarketsharevtgt;
	private String totalfms;

	private String carrier;
	private float totalMarketShareYTD;
	private float totalMarketShareVLYR;
	private String totalMarketShareVTGT;
	private List lcompetitorTotals = new ArrayList();
	private float totalcompMarketShareYTD;
	private float totalcompMarketShareVLYR;
	private float totalcompMarketShareVTGT;
	private String combinationkey;
	private float marketshare1;
	private float totalmarketsharelastyr;
	private ArrayList<String> carrierList = new ArrayList<String>();

	public float getTotalmarketshareytd() {
		return totalmarketshareytd;
	}

	public void setTotalmarketshareytd(float totalmarketshareytd) {
		this.totalmarketshareytd = totalmarketshareytd;
	}

	public float getTotalmarketsharevlyr() {
		return totalmarketsharevlyr;
	}

	public void setTotalmarketsharevlyr(float totalmarketsharevlyr) {
		this.totalmarketsharevlyr = totalmarketsharevlyr;
	}

	public float getTotalmarketsharevtgt() {
		return totalmarketsharevtgt;
	}

	public void setTotalmarketsharevtgt(float totalmarketsharevtgt) {
		this.totalmarketsharevtgt = totalmarketsharevtgt;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public float getTotalMarketShareYTD() {
		return totalMarketShareYTD;
	}

	public void setTotalMarketShareYTD(float totalMarketShareYTD) {
		this.totalMarketShareYTD = totalMarketShareYTD;
	}

	public float getTotalMarketShareVLYR() {
		return totalMarketShareVLYR;
	}

	public void setTotalMarketShareVLYR(float totalMarketShareVLYR) {
		this.totalMarketShareVLYR = totalMarketShareVLYR;
	}

	public List getLcompetitorTotals() {
		return lcompetitorTotals;
	}

	public void setLcompetitorTotals(List lcompetitorTotals) {
		this.lcompetitorTotals = lcompetitorTotals;
	}

	public float getTotalcompMarketShareYTD() {
		return totalcompMarketShareYTD;
	}

	public void setTotalcompMarketShareYTD(float totalcompMarketShareYTD) {
		this.totalcompMarketShareYTD = totalcompMarketShareYTD;
	}

	public float getTotalcompMarketShareVLYR() {
		return totalcompMarketShareVLYR;
	}

	public void setTotalcompMarketShareVLYR(float totalcompMarketShareVLYR) {
		this.totalcompMarketShareVLYR = totalcompMarketShareVLYR;
	}

	public float getTotalcompMarketShareVTGT() {
		return totalcompMarketShareVTGT;
	}

	public void setTotalcompMarketShareVTGT(float totalcompMarketShareVTGT) {
		this.totalcompMarketShareVTGT = totalcompMarketShareVTGT;
	}

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}

	public float getMarketshare1() {
		return marketshare1;
	}

	public void setMarketshare1(float marketshare1) {
		this.marketshare1 = marketshare1;
	}

	public float getTotalmarketsharelastyr() {
		return totalmarketsharelastyr;
	}

	public void setTotalmarketsharelastyr(float totalmarketsharelastyr) {
		this.totalmarketsharelastyr = totalmarketsharelastyr;
	}

	public ArrayList<String> getCarrierList() {
		return carrierList;
	}

	public void setCarrierList(ArrayList<String> carrierList) {
		this.carrierList = carrierList;
	}

	public String getTotalMarketShareVTGT() {
		return totalMarketShareVTGT;
	}

	public void setTotalMarketShareVTGT(String totalMarketShareVTGT) {
		this.totalMarketShareVTGT = totalMarketShareVTGT;
	}

	public void setTotalfms(String totalfms) {
		this.totalfms = totalfms;
	}

	public String getTotalfms() {
		return totalfms;
	}

}
