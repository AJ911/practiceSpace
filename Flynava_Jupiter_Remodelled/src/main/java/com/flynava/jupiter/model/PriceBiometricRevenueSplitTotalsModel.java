package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PriceBiometricRevenueSplitTotalsModel {

	private float totalSalesRevenueYTD;
	private float totalFlownRevenueYTD;

	private String totalRevenueVLYR;
	private float totalRevenueVTGT;

	public float getTotalSalesRevenueYTD() {
		return totalSalesRevenueYTD;
	}

	public void setTotalSalesRevenueYTD(float totalSalesRevenueYTD) {
		this.totalSalesRevenueYTD = totalSalesRevenueYTD;
	}

	public float getTotalFlownRevenueYTD() {
		return totalFlownRevenueYTD;
	}

	public void setTotalFlownRevenueYTD(float totalFlownRevenueYTD) {
		this.totalFlownRevenueYTD = totalFlownRevenueYTD;
	}

	public String getTotalRevenueVLYR() {
		return totalRevenueVLYR;
	}

	public void setTotalRevenueVLYR(String totalRevenueVLYR) {
		this.totalRevenueVLYR = totalRevenueVLYR;
	}

	public float getTotalRevenueVTGT() {
		return totalRevenueVTGT;
	}

	public void setTotalRevenueVTGT(float totalRevenueVTGT) {
		this.totalRevenueVTGT = totalRevenueVTGT;
	}

}
