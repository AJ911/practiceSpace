package com.flynava.jupiter.model;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignificantTotalResponse {
	
	
	private float totalRevenueYTD;
	private float totalPaxYTD;
	private float totalRevenueVLYR;
	private float totalRevenueVTGT;
	private float totalPaxVLYR;
	private float totalPaxVTGT;
	
	private String carrier;
	
	private float totalMarketShareYTD;
	private float totalMarketShareVLYR;
	private float totalMarketShareVTGT;
	
	private List lcompetitorTotals=new ArrayList();
	private Map<String, Object> competitorsMap = new HashMap<String, Object>();
	
	private float totalcompMarketShareYTD;
	private float totalcompMarketShareVLYR;
	private float totalcompMarketShareVTGT;
	public float getTotalRevenueYTD() {
		return totalRevenueYTD;
	}
	public void setTotalRevenueYTD(float totalRevenueYTD) {
		this.totalRevenueYTD = totalRevenueYTD;
	}
	public float getTotalPaxYTD() {
		return totalPaxYTD;
	}
	public void setTotalPaxYTD(float totalPaxYTD) {
		this.totalPaxYTD = totalPaxYTD;
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
	public float getTotalMarketShareVTGT() {
		return totalMarketShareVTGT;
	}
	public void setTotalMarketShareVTGT(float totalMarketShareVTGT) {
		this.totalMarketShareVTGT = totalMarketShareVTGT;
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
	public Map<String, Object> getCompetitorsMap() {
		return competitorsMap;
	}
	public void setCompetitorsMap(Map<String, Object> competitorsMap) {
		this.competitorsMap = competitorsMap;
	}
	
	
	
	
	
	
	
	
	
	
	

}