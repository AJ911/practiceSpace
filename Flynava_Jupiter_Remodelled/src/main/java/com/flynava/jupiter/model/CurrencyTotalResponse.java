package com.flynava.jupiter.model;

public class CurrencyTotalResponse {

	private Double TotalrevenueYTD;
	private Double totalAvgRateRealization;

	
	
	public Double getTotalAvgRateRealization() {
		return totalAvgRateRealization;
	}

	public void setTotalAvgRateRealization(Double totalAvgRateRealization) {
		this.totalAvgRateRealization = totalAvgRateRealization;
	}

	public Double getTotalrevenueYTD() {
		return TotalrevenueYTD;
	}

	public void setTotalrevenueYTD(Double totalrevenueYTD) {
		TotalrevenueYTD = totalrevenueYTD;
	}

}
