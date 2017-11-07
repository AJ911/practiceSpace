package com.flynava.jupiter.model;

import java.util.ArrayList;
import java.util.List;

public class MarketIndicatorSummary {

	private List<Integer> POS_vs_TotalMarket = new ArrayList<Integer>();
	private List<Integer> overall_POS_Growth = new ArrayList<Integer>();
	private List<Integer> passenger_Per_Day = new ArrayList<Integer>();
	private List<Integer> market_Growth = new ArrayList<Integer>();
	private List<Integer> MarketSize = new ArrayList<Integer>();

	public List<Integer> getPOS_vs_TotalMarket() {
		return POS_vs_TotalMarket;
	}

	public void setPOS_vs_TotalMarket(List<Integer> pOS_vs_TotalMarket) {
		POS_vs_TotalMarket = pOS_vs_TotalMarket;
	}

	public List<Integer> getOverall_POS_Growth() {
		return overall_POS_Growth;
	}

	public void setOverall_POS_Growth(List<Integer> overall_POS_Growth) {
		this.overall_POS_Growth = overall_POS_Growth;
	}

	public List<Integer> getPassenger_Per_Day() {
		return passenger_Per_Day;
	}

	public void setPassenger_Per_Day(List<Integer> passenger_Per_Day) {
		this.passenger_Per_Day = passenger_Per_Day;
	}

	public List<Integer> getMarket_Growth() {
		return market_Growth;
	}

	public void setMarket_Growth(List<Integer> market_Growth) {
		this.market_Growth = market_Growth;
	}

	public List<Integer> getMarketSize() {
		return MarketSize;
	}

	public void setMarketSize(List<Integer> marketSize) {
		MarketSize = marketSize;
	}

}
