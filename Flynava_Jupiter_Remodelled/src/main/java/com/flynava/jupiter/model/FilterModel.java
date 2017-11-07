package com.flynava.jupiter.model;

import java.util.ArrayList;

public class FilterModel {

	private String departureDate;
	private String region;
	private String country;
	private String pos;
	private String od;
	private String origin;
	private String destination;
	private String compartment;
	private String rbd;
	private String fromDate;
	private String toDate;
	private String fromDateEvent;
	private String toDateEvent;
	private String fromDateEvent_lastyr;
	private String toDateEvent_lastyr;
	private String currency;
	private float validForPeriod;
	private String revenueShareKey;
	private String fareBasis;
	private String channel;
	private String baseFare;
	private String customerSegment;
	private String odDistance;
	private float od_Distance;
	private String priceElasticitySignal;
	private String flightNumber;
	private String fare;
	private String pricePerformance;
	private String marketType;
	private String carrier;
	private float Fares;
	private float priceElasticity;
	private float effectiveness;
	private float basefares;
	private float price;
	// Pax
	private String hostPax;
	private String hostPaxForecast;
	private String hostPax_lastyr;
	private String hostPax_tgt;

	private String bookedPax;
	private String flownPax;
	private String salesPax;

	private String flownPax_lastyr;
	private String targetPax;

	private int pax;
	private int paxlastyr;
	private float targetpax;
	private float paxVLYR;
	private float paxVTGT;
	private String paxType;
	private float paxlastyear;

	// Revenue
	private String flownRevenue;
	private String flownRevenue_lastyr;
	private String revenueForecast;
	private String salesRevenue;
	private String salesRevenue_lastyr;
	private String targetRevenue;
	private float revenue;
	private float revenueVLYR;
	private float revenuelastyr;
	private float flown_revenue;
	private float flownrevenue_lastyr;

	// Market Size
	private String marketSize;
	private String marketSize_lastyr;

	// Capacity
	private String capacity;

	// Bookings
	private String hostBookings;
	private String hostBookings_lastyr;
	private String hostBookings_tgt;

	// Strategy
	private String strategy;

	// Price Characteristics
	private String agencyName;
	private String minPrice;
	private String maxPrice;
	private String proximityIndicator;

	// CombinationKey
	private String filterKey;

	// FareBrands
	private String fareBrand;
	private String fareRule;

	// MarketShare
	private String marketSharePax;
	private String marketSharePax_lastyr;

	// MarketSummary
	private String year;
	private String month;
	private String market_pax;
	private String market_pax_1;
	private String market_pax_2;
	private String market_pax_3;
	private String market_pax_4;

	private String agent;

	private String airCharge;

	private float capacityFZ;
	private float capacityLY_FZ;
	private float capacityComp1;
	private float capacityComp2;
	private float capacityComp3;
	private float capacityComp4;

	private float compRatingFZ;
	private float compRatingComp1;
	private float compRatingComp2;
	private float compRatingComp3;
	private float compRatingComp4;
	// New Product

	private String product;
	private String effectiveStartDate;
	private String effectiveEndDate;
	private String volume;

	// PIQ
	private float compFare1;
	private float compFare2;
	private float compFareBasis1;
	private float compFareBasis2;
	private float base_Fare;
	private String marketsize;
	private String marketsize1;
	private float targetmarketshare;
	private ArrayList<String> lCarrier = new ArrayList<String>();
	private float host_market_share;
	private float host_market_share_lastyr;
	// Price Stability
	private float nooffares;
	private float nooffarechanges;
	private String farebasis;

	private String atpcoFareBasis;
	private String ruleId;
	private String footNote;
	private String surCharge;
	private String yqCharge;
	private String taxes;
	private String totalFare;
	// revenue split kpi
	private float revenueForcast;
	private float flownpax;
	private float flownrevenue;
	private int Months;
	private int Days;
	private float paxForcast;
	private float flownpaxlastyr;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromDateEvent() {
		return fromDateEvent;
	}

	public void setFromDateEvent(String fromDateEvent) {
		this.fromDateEvent = fromDateEvent;
	}

	public String getToDateEvent() {
		return toDateEvent;
	}

	public void setToDateEvent(String toDateEvent) {
		this.toDateEvent = toDateEvent;
	}

	public String getFromDateEvent_lastyr() {
		return fromDateEvent_lastyr;
	}

	public void setFromDateEvent_lastyr(String fromDateEvent_lastyr) {
		this.fromDateEvent_lastyr = fromDateEvent_lastyr;
	}

	public String getToDateEvent_lastyr() {
		return toDateEvent_lastyr;
	}

	public void setToDateEvent_lastyr(String toDateEvent_lastyr) {
		this.toDateEvent_lastyr = toDateEvent_lastyr;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFlownRevenue() {
		return flownRevenue;
	}

	public void setFlownRevenue(String flownRevenue) {
		this.flownRevenue = flownRevenue;
	}

	public String getFlownRevenue_lastyr() {
		return flownRevenue_lastyr;
	}

	public void setFlownRevenue_lastyr(String flownRevenue_lastyr) {
		this.flownRevenue_lastyr = flownRevenue_lastyr;
	}

	public String getSalesRevenue() {
		return salesRevenue;
	}

	public void setSalesRevenue(String salesRevenue) {
		this.salesRevenue = salesRevenue;
	}

	public String getSalesRevenue_lastyr() {
		return salesRevenue_lastyr;
	}

	public void setSalesRevenue_lastyr(String salesRevenue_lastyr) {
		this.salesRevenue_lastyr = salesRevenue_lastyr;
	}

	public String getFilterKey() {
		return filterKey;
	}

	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}

	public String getFareBasis() {
		return fareBasis;
	}

	public void setFareBasis(String fareBasis) {
		this.fareBasis = fareBasis;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(String baseFare) {
		this.baseFare = baseFare;
	}

	public String getOdDistance() {
		return odDistance;
	}

	public void setOdDistance(String odDistance) {
		this.odDistance = odDistance;
	}

	public String getHostPax() {
		return hostPax;
	}

	public void setHostPax(String hostPax) {
		this.hostPax = hostPax;
	}

	public String getHostPax_lastyr() {
		return hostPax_lastyr;
	}

	public void setHostPax_lastyr(String hostPax_lastyr) {
		this.hostPax_lastyr = hostPax_lastyr;
	}

	public String getHostPax_tgt() {
		return hostPax_tgt;
	}

	public void setHostPax_tgt(String hostPax_tgt) {
		this.hostPax_tgt = hostPax_tgt;
	}

	public String getMarketSize_lastyr() {
		return marketSize_lastyr;
	}

	public void setMarketSize_lastyr(String marketSize_lastyr) {
		this.marketSize_lastyr = marketSize_lastyr;
	}

	public String getMarketSize() {
		return marketSize;
	}

	public void setMarketSize(String marketSize) {
		this.marketSize = marketSize;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

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

	public String getPriceElasticitySignal() {
		return priceElasticitySignal;
	}

	public void setPriceElasticitySignal(String priceElasticitySignal) {
		this.priceElasticitySignal = priceElasticitySignal;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFare() {
		return fare;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}

	public String getPricePerformance() {
		return pricePerformance;
	}

	public void setPricePerformance(String pricePerformance) {
		this.pricePerformance = pricePerformance;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getHostBookings() {
		return hostBookings;
	}

	public void setHostBookings(String hostBookings) {
		this.hostBookings = hostBookings;
	}

	public String getHostBookings_lastyr() {
		return hostBookings_lastyr;
	}

	public void setHostBookings_lastyr(String hostBookings_lastyr) {
		this.hostBookings_lastyr = hostBookings_lastyr;
	}

	public String getHostBookings_tgt() {
		return hostBookings_tgt;
	}

	public void setHostBookings_tgt(String hostBookings_tgt) {
		this.hostBookings_tgt = hostBookings_tgt;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getProximityIndicator() {
		return proximityIndicator;
	}

	public void setProximityIndicator(String proximityIndicator) {
		this.proximityIndicator = proximityIndicator;
	}

	public String getBookedPax() {
		return bookedPax;
	}

	public void setBookedPax(String bookedPax) {
		this.bookedPax = bookedPax;
	}

	public String getFlownPax() {
		return flownPax;
	}

	public void setFlownPax(String flownPax) {
		this.flownPax = flownPax;
	}

	public String getSalesPax() {
		return salesPax;
	}

	public void setSalesPax(String salesPax) {
		this.salesPax = salesPax;
	}

	public String getFlownPax_lastyr() {
		return flownPax_lastyr;
	}

	public void setFlownPax_lastyr(String flownPax_lastyr) {
		this.flownPax_lastyr = flownPax_lastyr;
	}

	public String getFareBrand() {
		return fareBrand;
	}

	public void setFareBrand(String fareBrand) {
		this.fareBrand = fareBrand;
	}

	public String getFareRule() {
		return fareRule;
	}

	public void setFareRule(String fareRule) {
		this.fareRule = fareRule;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getMarketSharePax() {
		return marketSharePax;
	}

	public void setMarketSharePax(String marketSharePax) {
		this.marketSharePax = marketSharePax;
	}

	public String getMarketSharePax_lastyr() {
		return marketSharePax_lastyr;
	}

	public void setMarketSharePax_lastyr(String marketSharePax_lastyr) {
		this.marketSharePax_lastyr = marketSharePax_lastyr;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMarket_pax() {
		return market_pax;
	}

	public void setMarket_pax(String market_pax) {
		this.market_pax = market_pax;
	}

	public String getMarket_pax_1() {
		return market_pax_1;
	}

	public void setMarket_pax_1(String market_pax_1) {
		this.market_pax_1 = market_pax_1;
	}

	public String getMarket_pax_2() {
		return market_pax_2;
	}

	public void setMarket_pax_2(String market_pax_2) {
		this.market_pax_2 = market_pax_2;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getTargetPax() {
		return targetPax;
	}

	public void setTargetPax(String targetPax) {
		this.targetPax = targetPax;
	}

	public String getTargetRevenue() {
		return targetRevenue;
	}

	public void setTargetRevenue(String targetRevenue) {
		this.targetRevenue = targetRevenue;
	}

	public String getMarket_pax_3() {
		return market_pax_3;
	}

	public void setMarket_pax_3(String market_pax_3) {
		this.market_pax_3 = market_pax_3;
	}

	public String getMarket_pax_4() {
		return market_pax_4;
	}

	public void setMarket_pax_4(String market_pax_4) {
		this.market_pax_4 = market_pax_4;
	}

	public String getAirCharge() {
		return airCharge;
	}

	public void setAirCharge(String airCharge) {
		this.airCharge = airCharge;
	}

	public int getPax() {
		return pax;
	}

	public void setPax(int pax) {
		this.pax = pax;
	}

	public int getPaxlastyr() {
		return paxlastyr;
	}

	public void setPaxlastyr(int paxlastyr) {
		this.paxlastyr = paxlastyr;
	}

	public float getRevenue() {
		return revenue;
	}

	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}

	public float getTargetpax() {
		return targetpax;
	}

	public void setTargetpax(float targetpax) {
		this.targetpax = targetpax;
	}

	public float getFares() {
		return Fares;
	}

	public void setFares(float fares) {
		Fares = fares;
	}

	public float getPriceElasticity() {
		return priceElasticity;
	}

	public void setPriceElasticity(float priceElasticity) {
		this.priceElasticity = priceElasticity;
	}

	public float getEffectiveness() {
		return effectiveness;
	}

	public void setEffectiveness(float effectiveness) {
		this.effectiveness = effectiveness;
	}

	public float getPaxVLYR() {
		return paxVLYR;
	}

	public void setPaxVLYR(float paxVLYR) {
		this.paxVLYR = paxVLYR;
	}

	public float getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(float paxVTGT) {
		this.paxVTGT = paxVTGT;
	}

	public float getRevenueVLYR() {
		return revenueVLYR;
	}

	public void setRevenueVLYR(float revenueVLYR) {
		this.revenueVLYR = revenueVLYR;
	}

	public float getRevenuelastyr() {
		return revenuelastyr;
	}

	public void setRevenuelastyr(float revenuelastyr) {
		this.revenuelastyr = revenuelastyr;
	}

	public float getBasefares() {
		return basefares;
	}

	public void setBasefares(float basefares) {
		this.basefares = basefares;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getCapacityFZ() {
		return capacityFZ;
	}

	public void setCapacityFZ(float capacityFZ) {
		this.capacityFZ = capacityFZ;
	}

	public float getCapacityComp1() {
		return capacityComp1;
	}

	public void setCapacityComp1(float capacityComp1) {
		this.capacityComp1 = capacityComp1;
	}

	public float getCapacityComp2() {
		return capacityComp2;
	}

	public void setCapacityComp2(float capacityComp2) {
		this.capacityComp2 = capacityComp2;
	}

	public float getCompRatingFZ() {
		return compRatingFZ;
	}

	public void setCompRatingFZ(float compRatingFZ) {
		this.compRatingFZ = compRatingFZ;
	}

	public float getCompRatingComp1() {
		return compRatingComp1;
	}

	public void setCompRatingComp1(float compRatingComp1) {
		this.compRatingComp1 = compRatingComp1;
	}

	public float getCompRatingComp2() {
		return compRatingComp2;
	}

	public void setCompRatingComp2(float compRatingComp2) {
		this.compRatingComp2 = compRatingComp2;
	}

	public float getOd_Distance() {
		return od_Distance;
	}

	public void setOd_Distance(float od_Distance) {
		this.od_Distance = od_Distance;
	}

	public String getPaxType() {
		return paxType;
	}

	public void setPaxType(String paxType) {
		this.paxType = paxType;
	}

	public float getFlown_revenue() {
		return flown_revenue;
	}

	public void setFlown_revenue(float flown_revenue) {
		this.flown_revenue = flown_revenue;
	}

	public float getFlownrevenue_lastyr() {
		return flownrevenue_lastyr;
	}

	public void setFlownrevenue_lastyr(float flownrevenue_lastyr) {
		this.flownrevenue_lastyr = flownrevenue_lastyr;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public String getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public float getValidForPeriod() {
		return validForPeriod;
	}

	public void setValidForPeriod(float validForPeriod) {
		this.validForPeriod = validForPeriod;
	}

	public float getCompFare1() {
		return compFare1;
	}

	public void setCompFare1(float compFare1) {
		this.compFare1 = compFare1;
	}

	public float getCompFare2() {
		return compFare2;
	}

	public void setCompFare2(float compFare2) {
		this.compFare2 = compFare2;
	}

	public float getCompFareBasis1() {
		return compFareBasis1;
	}

	public void setCompFareBasis1(float compFareBasis1) {
		this.compFareBasis1 = compFareBasis1;
	}

	public float getCompFareBasis2() {
		return compFareBasis2;
	}

	public void setCompFareBasis2(float compFareBasis2) {
		this.compFareBasis2 = compFareBasis2;
	}

	public float getBase_Fare() {
		return base_Fare;
	}

	public void setBase_Fare(float base_Fare) {
		this.base_Fare = base_Fare;
	}

	public String getMarketsize() {
		return marketsize;
	}

	public void setMarketsize(String marketsize) {
		this.marketsize = marketsize;
	}

	public String getMarketsize1() {
		return marketsize1;
	}

	public void setMarketsize1(String marketsize1) {
		this.marketsize1 = marketsize1;
	}

	public float getTargetmarketshare() {
		return targetmarketshare;
	}

	public void setTargetmarketshare(float targetmarketshare) {
		this.targetmarketshare = targetmarketshare;
	}

	public ArrayList<String> getlCarrier() {
		return lCarrier;
	}

	public void setlCarrier(ArrayList<String> lCarrier) {
		this.lCarrier = lCarrier;
	}

	public float getHost_market_share() {
		return host_market_share;
	}

	public void setHost_market_share(float host_market_share) {
		this.host_market_share = host_market_share;
	}

	public float getHost_market_share_lastyr() {
		return host_market_share_lastyr;
	}

	public void setHost_market_share_lastyr(float host_market_share_lastyr) {
		this.host_market_share_lastyr = host_market_share_lastyr;
	}

	public float getNooffares() {
		return nooffares;
	}

	public void setNooffares(float nooffares) {
		this.nooffares = nooffares;
	}

	public float getNooffarechanges() {
		return nooffarechanges;
	}

	public void setNooffarechanges(float nooffarechanges) {
		this.nooffarechanges = nooffarechanges;
	}

	public String getFarebasis() {
		return farebasis;
	}

	public void setFarebasis(String farebasis) {
		this.farebasis = farebasis;
	}

	public String getRevenueShareKey() {
		return revenueShareKey;
	}

	public void setRevenueShareKey(String revenueShareKey) {
		this.revenueShareKey = revenueShareKey;
	}

	public float getPaxlastyear() {
		return paxlastyear;
	}

	public void setPaxlastyear(float paxlastyear) {
		this.paxlastyear = paxlastyear;
	}

	public String getHostPaxForecast() {
		return hostPaxForecast;
	}

	public void setHostPaxForecast(String hostPaxForecast) {
		this.hostPaxForecast = hostPaxForecast;
	}

	public String getRevenueForecast() {
		return revenueForecast;
	}

	public void setRevenueForecast(String revenueForecast) {
		this.revenueForecast = revenueForecast;
	}

	public String getAtpcoFareBasis() {
		return atpcoFareBasis;
	}

	public void setAtpcoFareBasis(String atpcoFareBasis) {
		this.atpcoFareBasis = atpcoFareBasis;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getFootNote() {
		return footNote;
	}

	public void setFootNote(String footNote) {
		this.footNote = footNote;
	}

	public String getSurCharge() {
		return surCharge;
	}

	public void setSurCharge(String surCharge) {
		this.surCharge = surCharge;
	}

	public String getYqCharge() {
		return yqCharge;
	}

	public void setYqCharge(String yqCharge) {
		this.yqCharge = yqCharge;
	}

	public String getTaxes() {
		return taxes;
	}

	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}

	public String getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(String totalFare) {
		this.totalFare = totalFare;
	}

	public float getCompRatingComp3() {
		return compRatingComp3;
	}

	public void setCompRatingComp3(float compRatingComp3) {
		this.compRatingComp3 = compRatingComp3;
	}

	public float getCompRatingComp4() {
		return compRatingComp4;
	}

	public void setCompRatingComp4(float compRatingComp4) {
		this.compRatingComp4 = compRatingComp4;
	}

	public float getCapacityComp3() {
		return capacityComp3;
	}

	public void setCapacityComp3(float capacityComp3) {
		this.capacityComp3 = capacityComp3;
	}

	public float getCapacityComp4() {
		return capacityComp4;
	}

	public void setCapacityComp4(float capacityComp4) {
		this.capacityComp4 = capacityComp4;
	}

	public float getRevenueForcast() {
		return revenueForcast;
	}

	public void setRevenueForcast(float revenueForcast) {
		this.revenueForcast = revenueForcast;
	}

	public float getFlownpax() {
		return flownpax;
	}

	public void setFlownpax(float flownpax) {
		this.flownpax = flownpax;
	}

	public int getMonths() {
		return Months;
	}

	public void setMonths(int months) {
		Months = months;
	}

	public int getDays() {
		return Days;
	}

	public void setDays(int days) {
		Days = days;
	}

	public float getPaxForcast() {
		return paxForcast;
	}

	public void setPaxForcast(float paxForcast) {
		this.paxForcast = paxForcast;
	}

	public float getFlownrevenue() {
		return flownrevenue;
	}

	public void setFlownrevenue(float flownrevenue) {
		this.flownrevenue = flownrevenue;
	}

	public float getFlownpaxlastyr() {
		return flownpaxlastyr;
	}

	public void setFlownpaxlastyr(float flownpaxlastyr) {
		this.flownpaxlastyr = flownpaxlastyr;
	}

	public float getCapacityLY_FZ() {
		return capacityLY_FZ;
	}

	public void setCapacityLY_FZ(float capacityLY_FZ) {
		this.capacityLY_FZ = capacityLY_FZ;
	}

}
