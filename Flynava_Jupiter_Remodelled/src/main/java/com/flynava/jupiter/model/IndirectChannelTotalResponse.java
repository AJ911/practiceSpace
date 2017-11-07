package com.flynava.jupiter.model;

import java.util.HashMap;
import java.util.Map;

public class IndirectChannelTotalResponse {

	private float totalRevenueYTD;
	private float totalFlownYTD;
	private float totalRevenueVLYR;
	private float totalRevenueVTGT;

	private float totalYeild;
	private float totalYeildVLYR;

	private float totalPaxYTD;
	private float totalPaxVLYR;
	private float totalPaxVTGT;
	private float totalMarketShareYTD;
	private float totalComp1ShareYTD;
	private float totalComp2ShareYTD;
	private float totalComp3ShareYTD;
	private float totalComp4ShareYTD;
	private float totalComp5ShareYTD;
	private float totalAverageFare;
	Map<String, Float> totalcarrierPaxMap = new HashMap<String, Float>();

	public float getTotalRevenueYTD() {
		return totalRevenueYTD;
	}

	public void setTotalRevenueYTD(float totalRevenueYTD) {
		this.totalRevenueYTD = totalRevenueYTD;
	}

	public float getTotalRevenueVLYR() {
		return totalRevenueVLYR;
	}

	public void setTotalRevenueVLYR(float totalRevenueVLYR) {
		this.totalRevenueVLYR = totalRevenueVLYR;
	}

	public float getTotalRevenueVTGT() {
		return totalRevenueVTGT;
	}

	public void setTotalRevenueVTGT(float totalRevenueVTGT) {
		this.totalRevenueVTGT = totalRevenueVTGT;
	}

	public float getTotalYeild() {
		return totalYeild;
	}

	public void setTotalYeild(float totalYeild) {
		this.totalYeild = totalYeild;
	}

	public float getTotalYeildVLYR() {
		return totalYeildVLYR;
	}

	public void setTotalYeildVLYR(float totalYeildVLYR) {
		this.totalYeildVLYR = totalYeildVLYR;
	}

	public float getTotalPaxYTD() {
		return totalPaxYTD;
	}

	public void setTotalPaxYTD(float totalPaxYTD) {
		this.totalPaxYTD = totalPaxYTD;
	}

	public float getTotalPaxVLYR() {
		return totalPaxVLYR;
	}

	public void setTotalPaxVLYR(float totalPaxVLYR) {
		this.totalPaxVLYR = totalPaxVLYR;
	}

	public float getTotalPaxVTGT() {
		return totalPaxVTGT;
	}

	public void setTotalPaxVTGT(float totalPaxVTGT) {
		this.totalPaxVTGT = totalPaxVTGT;
	}

	public float getTotalMarketShareYTD() {
		return totalMarketShareYTD;
	}

	public void setTotalMarketShareYTD(float totalMarketShareYTD) {
		this.totalMarketShareYTD = totalMarketShareYTD;
	}

	public float getTotalComp1ShareYTD() {
		return totalComp1ShareYTD;
	}

	public void setTotalComp1ShareYTD(float totalComp1ShareYTD) {
		this.totalComp1ShareYTD = totalComp1ShareYTD;
	}

	public float getTotalComp2ShareYTD() {
		return totalComp2ShareYTD;
	}

	public void setTotalComp2ShareYTD(float totalComp2ShareYTD) {
		this.totalComp2ShareYTD = totalComp2ShareYTD;
	}

	public float getTotalComp3ShareYTD() {
		return totalComp3ShareYTD;
	}

	public void setTotalComp3ShareYTD(float totalComp3ShareYTD) {
		this.totalComp3ShareYTD = totalComp3ShareYTD;
	}

	public float getTotalAverageFare() {
		return totalAverageFare;
	}

	public void setTotalAverageFare(float totalAverageFare) {
		this.totalAverageFare = totalAverageFare;
	}

	public float getTotalComp4ShareYTD() {
		return totalComp4ShareYTD;
	}

	public void setTotalComp4ShareYTD(float totalComp4ShareYTD) {
		this.totalComp4ShareYTD = totalComp4ShareYTD;
	}

	public float getTotalFlownYTD() {
		return totalFlownYTD;
	}

	public void setTotalFlownYTD(float totalFlownYTD) {
		this.totalFlownYTD = totalFlownYTD;
	}

	public float getTotalComp5ShareYTD() {
		return totalComp5ShareYTD;
	}

	public void setTotalComp5ShareYTD(float totalComp5ShareYTD) {
		this.totalComp5ShareYTD = totalComp5ShareYTD;
	}

	public Map<String, Float> getTotalcarrierPaxMap() {
		return totalcarrierPaxMap;
	}

	public void setTotalcarrierPaxMap(Map<String, Float> totalcarrierPaxMap) {
		this.totalcarrierPaxMap = totalcarrierPaxMap;
	}

}
