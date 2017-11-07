package com.flynava.jupiter.model;

import java.util.List;
import java.util.Map;

public class TriggerFares implements Comparable {

	private String ruleId;// fa
	private String status;
	private String farebasis;// fa
	private float bookings;// fa
	private String recomdCateg;
	private String currency;// fa
	private float currentFare;// fa
	private float recmndFare;// fa
	private String owrt;// fa
	private String footnote;// fa
	private String saleFrom;// fa
	private String saleTo;// fa
	private String travelFrom;// fa
	private String travelTo;// fa
	private List<Map<String, Object>> flightNumber;// @sai
	private float currentYield;// fa
	private float recmndYield;// fa
	private float lastFiled;// @sai

	private String marketCondition;
	private String typeOfMarket;
	private String pricingStrategy;
	private String nonPricingStrategy;
	private float currentBaseFare;
	private float currentYQ;// fa
	private float currentSurcharges;// fa
	private float currentTax;// fa
	private float currentTotalFare;// fa
	private float recoBaseFare;// fa
	private float recoTax;// fa
	private float recoSurcharges;// fa
	private float recoTotalFare;// fa
	private float yield_VLYR;
	private float yield_VTGT;
	private float reco_fare_yield;// fa
	private float seat_factor_leg1;
	private float seat_factor_leg2;
	private float hostFareRating;
	private float hostMarketRating;
	private float hostProductRating;
	private float host_web_frequently_available_total_fare;
	private float host_web_frequently_available_base_fare;
	private float host_web_frequently_available_fare_frequency;
	private float host_web_frequently_available_fare_tax;
	private float host_lowest_filed_fare_base_fare;
	private float host_lowest_filed_fare_tax;
	private float host_lowest_filed_fare_yq;
	private float host_lowest_filed_fare_surcharges;

	private List<Map<String, Object>> compList;

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFarebasis() {
		return farebasis;
	}

	public void setFarebasis(String farebasis) {
		this.farebasis = farebasis;
	}

	public float getBookings() {
		return bookings;
	}

	public void setBookings(float bookings) {
		this.bookings = bookings;
	}

	public String getRecomdCateg() {
		return recomdCateg;
	}

	public void setRecomdCateg(String recomdCateg) {
		this.recomdCateg = recomdCateg;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public float getCurrentFare() {
		return currentFare;
	}

	public void setCurrentFare(float currentFare) {
		this.currentFare = currentFare;
	}

	public float getRecmndFare() {
		return recmndFare;
	}

	public void setRecmndFare(float recmndFare) {
		this.recmndFare = recmndFare;
	}

	public String getOwrt() {
		return owrt;
	}

	public void setOwrt(String owrt) {
		this.owrt = owrt;
	}

	public String getFootnote() {
		return footnote;
	}

	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}

	public String getSaleFrom() {
		return saleFrom;
	}

	public void setSaleFrom(String saleFrom) {
		this.saleFrom = saleFrom;
	}

	public String getSaleTo() {
		return saleTo;
	}

	public void setSaleTo(String saleTo) {
		this.saleTo = saleTo;
	}

	public String getTravelFrom() {
		return travelFrom;
	}

	public void setTravelFrom(String travelFrom) {
		this.travelFrom = travelFrom;
	}

	public String getTravelTo() {
		return travelTo;
	}

	public void setTravelTo(String travelTo) {
		this.travelTo = travelTo;
	}

	public List<Map<String, Object>> getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(List<Map<String, Object>> flightNumber) {
		this.flightNumber = flightNumber;
	}

	public float getCurrentYield() {
		return currentYield;
	}

	public void setCurrentYield(float currentYield) {
		this.currentYield = currentYield;
	}

	public float getRecmndYield() {
		return recmndYield;
	}

	public void setRecmndYield(float recmndYield) {
		this.recmndYield = recmndYield;
	}

	public float getLastFiled() {
		return lastFiled;
	}

	public void setLastFiled(float lastFiled) {
		this.lastFiled = lastFiled;
	}

	public String getMarketCondition() {
		return marketCondition;
	}

	public void setMarketCondition(String marketCondition) {
		this.marketCondition = marketCondition;
	}

	public String getTypeOfMarket() {
		return typeOfMarket;
	}

	public void setTypeOfMarket(String typeOfMarket) {
		this.typeOfMarket = typeOfMarket;
	}

	public String getPricingStrategy() {
		return pricingStrategy;
	}

	public void setPricingStrategy(String pricingStrategy) {
		this.pricingStrategy = pricingStrategy;
	}

	public String getNonPricingStrategy() {
		return nonPricingStrategy;
	}

	public void setNonPricingStrategy(String nonPricingStrategy) {
		this.nonPricingStrategy = nonPricingStrategy;
	}

	public float getCurrentBaseFare() {
		return currentBaseFare;
	}

	public void setCurrentBaseFare(float currentBaseFare) {
		this.currentBaseFare = currentBaseFare;
	}

	public float getCurrentYQ() {
		return currentYQ;
	}

	public void setCurrentYQ(float currentYQ) {
		this.currentYQ = currentYQ;
	}

	public float getCurrentSurcharges() {
		return currentSurcharges;
	}

	public void setCurrentSurcharges(float currentSurcharges) {
		this.currentSurcharges = currentSurcharges;
	}

	public float getCurrentTax() {
		return currentTax;
	}

	public void setCurrentTax(float currentTax) {
		this.currentTax = currentTax;
	}

	public float getCurrentTotalFare() {
		return currentTotalFare;
	}

	public void setCurrentTotalFare(float currentTotalFare) {
		this.currentTotalFare = currentTotalFare;
	}

	public float getRecoBaseFare() {
		return recoBaseFare;
	}

	public void setRecoBaseFare(float recoBaseFare) {
		this.recoBaseFare = recoBaseFare;
	}

	public float getRecoTax() {
		return recoTax;
	}

	public void setRecoTax(float recoTax) {
		this.recoTax = recoTax;
	}

	public float getRecoSurcharges() {
		return recoSurcharges;
	}

	public void setRecoSurcharges(float recoSurcharges) {
		this.recoSurcharges = recoSurcharges;
	}

	public float getRecoTotalFare() {
		return recoTotalFare;
	}

	public void setRecoTotalFare(float recoTotalFare) {
		this.recoTotalFare = recoTotalFare;
	}

	public float getYield_VLYR() {
		return yield_VLYR;
	}

	public void setYield_VLYR(float yield_VLYR) {
		this.yield_VLYR = yield_VLYR;
	}

	public float getYield_VTGT() {
		return yield_VTGT;
	}

	public void setYield_VTGT(float yield_VTGT) {
		this.yield_VTGT = yield_VTGT;
	}

	public float getReco_fare_yield() {
		return reco_fare_yield;
	}

	public void setReco_fare_yield(float reco_fare_yield) {
		this.reco_fare_yield = reco_fare_yield;
	}

	public float getSeat_factor_leg1() {
		return seat_factor_leg1;
	}

	public void setSeat_factor_leg1(float seat_factor_leg1) {
		this.seat_factor_leg1 = seat_factor_leg1;
	}

	public float getSeat_factor_leg2() {
		return seat_factor_leg2;
	}

	public void setSeat_factor_leg2(float seat_factor_leg2) {
		this.seat_factor_leg2 = seat_factor_leg2;
	}

	public float getHostFareRating() {
		return hostFareRating;
	}

	public void setHostFareRating(float hostFareRating) {
		this.hostFareRating = hostFareRating;
	}

	public float getHostMarketRating() {
		return hostMarketRating;
	}

	public void setHostMarketRating(float hostMarketRating) {
		this.hostMarketRating = hostMarketRating;
	}

	public float getHostProductRating() {
		return hostProductRating;
	}

	public void setHostProductRating(float hostProductRating) {
		this.hostProductRating = hostProductRating;
	}

	public float getHost_web_frequently_available_total_fare() {
		return host_web_frequently_available_total_fare;
	}

	public void setHost_web_frequently_available_total_fare(float host_web_frequently_available_total_fare) {
		this.host_web_frequently_available_total_fare = host_web_frequently_available_total_fare;
	}

	public float getHost_web_frequently_available_base_fare() {
		return host_web_frequently_available_base_fare;
	}

	public void setHost_web_frequently_available_base_fare(float host_web_frequently_available_base_fare) {
		this.host_web_frequently_available_base_fare = host_web_frequently_available_base_fare;
	}

	public float getHost_web_frequently_available_fare_frequency() {
		return host_web_frequently_available_fare_frequency;
	}

	public void setHost_web_frequently_available_fare_frequency(float host_web_frequently_available_fare_frequency) {
		this.host_web_frequently_available_fare_frequency = host_web_frequently_available_fare_frequency;
	}

	public float getHost_web_frequently_available_fare_tax() {
		return host_web_frequently_available_fare_tax;
	}

	public void setHost_web_frequently_available_fare_tax(float host_web_frequently_available_fare_tax) {
		this.host_web_frequently_available_fare_tax = host_web_frequently_available_fare_tax;
	}

	public float getHost_lowest_filed_fare_base_fare() {
		return host_lowest_filed_fare_base_fare;
	}

	public void setHost_lowest_filed_fare_base_fare(float host_lowest_filed_fare_base_fare) {
		this.host_lowest_filed_fare_base_fare = host_lowest_filed_fare_base_fare;
	}

	public float getHost_lowest_filed_fare_tax() {
		return host_lowest_filed_fare_tax;
	}

	public void setHost_lowest_filed_fare_tax(float host_lowest_filed_fare_tax) {
		this.host_lowest_filed_fare_tax = host_lowest_filed_fare_tax;
	}

	public float getHost_lowest_filed_fare_yq() {
		return host_lowest_filed_fare_yq;
	}

	public void setHost_lowest_filed_fare_yq(float host_lowest_filed_fare_yq) {
		this.host_lowest_filed_fare_yq = host_lowest_filed_fare_yq;
	}

	public float getHost_lowest_filed_fare_surcharges() {
		return host_lowest_filed_fare_surcharges;
	}

	public void setHost_lowest_filed_fare_surcharges(float host_lowest_filed_fare_surcharges) {
		this.host_lowest_filed_fare_surcharges = host_lowest_filed_fare_surcharges;
	}

	public List<Map<String, Object>> getCompList() {
		return compList;
	}

	public void setCompList(List<Map<String, Object>> compList) {
		this.compList = compList;
	}

	@Override
	public int compareTo(Object str) {

		if (this.getFarebasis().equalsIgnoreCase(str.toString()))
			return 0;
		else

			return -1;
	}

}
