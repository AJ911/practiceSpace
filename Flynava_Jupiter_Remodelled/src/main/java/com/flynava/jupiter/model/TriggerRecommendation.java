package com.flynava.jupiter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriggerRecommendation {
	private String id;
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String fareBasis;
	private Double hostBookings;
	private Double hostBookingsVLYR;
	private Double hostBookingsVTGT;
	private Double currentFare;
	private Double recommendedFare;
	private String travelDateTo;
	private String travelDateFrom;
	private String effectiveDateTo;
	private String effectiveDateFrom;
	private String saleDateFrom;
	private String saleDateTo;
	private String ruleId;
	private Double hostMarketShare;
	private Double hostMarketShareVLYR;
	private Double hostMarketShareVTGT;
	private Double hostFms;

	private String compartment;
	private String currency;
	private String tripType;
	private String rbdType;
	private String footNote;
	private String triggerId;
	private String triggerStartDate;
	private String triggerEndDate;
	private String recomndCateg;
	private String lastTicketedDate;
	private String desc;
	private String strategyPricing;
	private String strategyNonPricing;
	private String compRating;
	private String hostRating;
	private String objective;
	private String marketType;
	private String marketCondition;
	private Double owrt;

	private String taxes;
	private String surcharge;
	private String yqCharge;

	private String baseFare;

	private String strategy;
	private String type;
	private String condition;
	private String forecast;
	private String expiry;
	private String significance;
	private String events;
	private String changedValue;

	private String availability;
	private String triggerAge;

	private String currentyq;
	private String currentsurcharge;
	private String currentTax;
	private String currentTotalfare;
	private String recoBasefare;
	private String recotax;
	private String recoyq;
	private String recosurcharge;
	private String recototalfare;
	private String lastfiledate;
	private String salespax;
	private String salespaxvlyr;
	private String salespaxvtgt;
	private String salesrevenue;
	private String salesrevenuevlyr;
	private String salesrevenuevtgt;
	private String yield;
	private String yieldvlyr;
	private String yieldvtgt;
	private String recofareyield;
	private String avgfare;
	private String avgfarevlyr;
	private String avgfarevtgt;
	private String seatfactorleg1;
	private String seatfactorleg2;
	private String hostdistributorrating;
	private String hostfarerating;
	private String hostmarketrating;
	private String hostproductrating;
	private String hostwebfrequentavailabletotalfare;
	private String hostwebfrequentavailablebasefare;
	private String hostwebfrequentavailablefarefrequency;
	private String hostwebfrequentavailablefaretax;
	private String hostlowestfiledfaretotalfare;
	private String hostlowestfiledfarebasefare;
	private String hostlowesrfiledfaretax;
	private String hostlowestfiledfareyq;
	private String hostlowestfiledfaresurcharges;
	private String compmarketshare;
	private String compmarketsharevlyr;
	private String compfms;
	private String compmarketsharevtgt;
	private String compcapacityrating;
	private String compdistributorrating;
	private String compfarerating;
	private String compmarketrating;
	private String compproductrating;
	private String compwebfrequentlyavailabletotalfare;
	private String compwebfrequentlyavailablebasefare;
	private String compwebfrequentlyavailablefarefrequency;
	private String compwebfrequentlyavailablefaretax;
	private String complowestfiledfaretotalfare;
	private String complowestfiledfarebasefare;
	private String complowestfiledfaretax;
	private String complowestfiledfareyq;
	private String complowestfiledfaresurcharges;

	List<String> flightnumberoutbound = new ArrayList<String>();

	List<String> flightnumberinbound = new ArrayList<String>();

	List<String> daysofweekoutbound = new ArrayList<String>();

	List<String> daysofweekinbound = new ArrayList<String>();

	Map<String, String> triggerIDdetails = new HashMap<String, String>();
	Map<String, String> faredetails = new HashMap<String, String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getFareBasis() {
		return fareBasis;
	}

	public void setFareBasis(String fareBasis) {
		this.fareBasis = fareBasis;
	}

	public Double getHostBookings() {
		return hostBookings;
	}

	public void setHostBookings(Double hostBookings) {
		this.hostBookings = hostBookings;
	}

	public Double getHostBookingsVLYR() {
		return hostBookingsVLYR;
	}

	public void setHostBookingsVLYR(Double hostBookingsVLYR) {
		this.hostBookingsVLYR = hostBookingsVLYR;
	}

	public Double getHostBookingsVTGT() {
		return hostBookingsVTGT;
	}

	public void setHostBookingsVTGT(Double hostBookingsVTGT) {
		this.hostBookingsVTGT = hostBookingsVTGT;
	}

	public Double getCurrentFare() {
		return currentFare;
	}

	public void setCurrentFare(Double currentFare) {
		this.currentFare = currentFare;
	}

	public Double getRecommendedFare() {
		return recommendedFare;
	}

	public void setRecommendedFare(Double recommendedFare) {
		this.recommendedFare = recommendedFare;
	}

	public String getTravelDateTo() {
		return travelDateTo;
	}

	public void setTravelDateTo(String travelDateTo) {
		this.travelDateTo = travelDateTo;
	}

	public String getTravelDateFrom() {
		return travelDateFrom;
	}

	public void setTravelDateFrom(String travelDateFrom) {
		this.travelDateFrom = travelDateFrom;
	}

	public String getEffectiveDateTo() {
		return effectiveDateTo;
	}

	public void setEffectiveDateTo(String effectiveDateTo) {
		this.effectiveDateTo = effectiveDateTo;
	}

	public String getEffectiveDateFrom() {
		return effectiveDateFrom;
	}

	public void setEffectiveDateFrom(String effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}

	public String getSaleDateFrom() {
		return saleDateFrom;
	}

	public void setSaleDateFrom(String saleDateFrom) {
		this.saleDateFrom = saleDateFrom;
	}

	public String getSaleDateTo() {
		return saleDateTo;
	}

	public void setSaleDateTo(String saleDateTo) {
		this.saleDateTo = saleDateTo;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Double getHostMarketShare() {
		return hostMarketShare;
	}

	public void setHostMarketShare(Double hostMarketShare) {
		this.hostMarketShare = hostMarketShare;
	}

	public Double getHostMarketShareVLYR() {
		return hostMarketShareVLYR;
	}

	public void setHostMarketShareVLYR(Double hostMarketShareVLYR) {
		this.hostMarketShareVLYR = hostMarketShareVLYR;
	}

	public Double getHostMarketShareVTGT() {
		return hostMarketShareVTGT;
	}

	public void setHostMarketShareVTGT(Double hostMarketShareVTGT) {
		this.hostMarketShareVTGT = hostMarketShareVTGT;
	}

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getRbdType() {
		return rbdType;
	}

	public void setRbdType(String rbdType) {
		this.rbdType = rbdType;
	}

	public String getFootNote() {
		return footNote;
	}

	public void setFootNote(String footNote) {
		this.footNote = footNote;
	}

	public String getLastTicketedDate() {
		return lastTicketedDate;
	}

	public void setLastTicketedDate(String lastTicketedDate) {
		this.lastTicketedDate = lastTicketedDate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStrategyPricing() {
		return strategyPricing;
	}

	public void setStrategyPricing(String strategyPricing) {
		this.strategyPricing = strategyPricing;
	}

	public String getStrategyNonPricing() {
		return strategyNonPricing;
	}

	public void setStrategyNonPricing(String strategyNonPricing) {
		this.strategyNonPricing = strategyNonPricing;
	}

	public String getCompRating() {
		return compRating;
	}

	public void setCompRating(String compRating) {
		this.compRating = compRating;
	}

	public String getHostRating() {
		return hostRating;
	}

	public void setHostRating(String hostRating) {
		this.hostRating = hostRating;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getMarketCondition() {
		return marketCondition;
	}

	public void setMarketCondition(String marketCondition) {
		this.marketCondition = marketCondition;
	}

	public String getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}

	public String getRecomndCateg() {
		return recomndCateg;
	}

	public void setRecomndCateg(String recomndCateg) {
		this.recomndCateg = recomndCateg;
	}

	public String getTaxes() {
		return taxes;
	}

	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}

	public String getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}

	public String getYqCharge() {
		return yqCharge;
	}

	public void setYqCharge(String yqCharge) {
		this.yqCharge = yqCharge;
	}

	public String getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(String baseFare) {
		this.baseFare = baseFare;
	}

	public String getTriggerStartDate() {
		return triggerStartDate;
	}

	public void setTriggerStartDate(String triggerStartDate) {
		this.triggerStartDate = triggerStartDate;
	}

	public String getTriggerEndDate() {
		return triggerEndDate;
	}

	public void setTriggerEndDate(String triggerEndDate) {
		this.triggerEndDate = triggerEndDate;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getForecast() {
		return forecast;
	}

	public void setForecast(String forecast) {
		this.forecast = forecast;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getSignificance() {
		return significance;
	}

	public void setSignificance(String significance) {
		this.significance = significance;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getChangedValue() {
		return changedValue;
	}

	public void setChangedValue(String changedValue) {
		this.changedValue = changedValue;
	}

	public Double getHostFms() {
		return hostFms;
	}

	public void setHostFms(Double hostFms) {
		this.hostFms = hostFms;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getTriggerAge() {
		return triggerAge;
	}

	public void setTriggerAge(String triggerAge) {
		this.triggerAge = triggerAge;
	}

	public String getCurrentyq() {
		return currentyq;
	}

	public void setCurrentyq(String currentyq) {
		this.currentyq = currentyq;
	}

	public String getCurrentsurcharge() {
		return currentsurcharge;
	}

	public void setCurrentsurcharge(String currentsurcharge) {
		this.currentsurcharge = currentsurcharge;
	}

	public String getCurrentTax() {
		return currentTax;
	}

	public void setCurrentTax(String currentTax) {
		this.currentTax = currentTax;
	}

	public String getCurrentTotalfare() {
		return currentTotalfare;
	}

	public void setCurrentTotalfare(String currentTotalfare) {
		this.currentTotalfare = currentTotalfare;
	}

	public String getRecoBasefare() {
		return recoBasefare;
	}

	public void setRecoBasefare(String recoBasefare) {
		this.recoBasefare = recoBasefare;
	}

	public String getRecotax() {
		return recotax;
	}

	public void setRecotax(String recotax) {
		this.recotax = recotax;
	}

	public String getRecoyq() {
		return recoyq;
	}

	public void setRecoyq(String recoyq) {
		this.recoyq = recoyq;
	}

	public String getRecosurcharge() {
		return recosurcharge;
	}

	public void setRecosurcharge(String recosurcharge) {
		this.recosurcharge = recosurcharge;
	}

	public String getRecototalfare() {
		return recototalfare;
	}

	public void setRecototalfare(String recototalfare) {
		this.recototalfare = recototalfare;
	}

	public String getLastfiledate() {
		return lastfiledate;
	}

	public void setLastfiledate(String lastfiledate) {
		this.lastfiledate = lastfiledate;
	}

	public String getSalespax() {
		return salespax;
	}

	public void setSalespax(String salespax) {
		this.salespax = salespax;
	}

	public String getSalespaxvlyr() {
		return salespaxvlyr;
	}

	public void setSalespaxvlyr(String salespaxvlyr) {
		this.salespaxvlyr = salespaxvlyr;
	}

	public String getSalespaxvtgt() {
		return salespaxvtgt;
	}

	public void setSalespaxvtgt(String salespaxvtgt) {
		this.salespaxvtgt = salespaxvtgt;
	}

	public String getSalesrevenue() {
		return salesrevenue;
	}

	public void setSalesrevenue(String salesrevenue) {
		this.salesrevenue = salesrevenue;
	}

	public String getSalesrevenuevlyr() {
		return salesrevenuevlyr;
	}

	public void setSalesrevenuevlyr(String salesrevenuevlyr) {
		this.salesrevenuevlyr = salesrevenuevlyr;
	}

	public String getSalesrevenuevtgt() {
		return salesrevenuevtgt;
	}

	public void setSalesrevenuevtgt(String salesrevenuevtgt) {
		this.salesrevenuevtgt = salesrevenuevtgt;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public String getYieldvlyr() {
		return yieldvlyr;
	}

	public void setYieldvlyr(String yieldvlyr) {
		this.yieldvlyr = yieldvlyr;
	}

	public String getYieldvtgt() {
		return yieldvtgt;
	}

	public void setYieldvtgt(String yieldvtgt) {
		this.yieldvtgt = yieldvtgt;
	}

	public String getRecofareyield() {
		return recofareyield;
	}

	public void setRecofareyield(String recofareyield) {
		this.recofareyield = recofareyield;
	}

	public String getAvgfare() {
		return avgfare;
	}

	public void setAvgfare(String avgfare) {
		this.avgfare = avgfare;
	}

	public String getAvgfarevlyr() {
		return avgfarevlyr;
	}

	public void setAvgfarevlyr(String avgfarevlyr) {
		this.avgfarevlyr = avgfarevlyr;
	}

	public String getAvgfarevtgt() {
		return avgfarevtgt;
	}

	public void setAvgfarevtgt(String avgfarevtgt) {
		this.avgfarevtgt = avgfarevtgt;
	}

	public String getSeatfactorleg1() {
		return seatfactorleg1;
	}

	public void setSeatfactorleg1(String seatfactorleg1) {
		this.seatfactorleg1 = seatfactorleg1;
	}

	public String getSeatfactorleg2() {
		return seatfactorleg2;
	}

	public void setSeatfactorleg2(String seatfactorleg2) {
		this.seatfactorleg2 = seatfactorleg2;
	}

	public String getHostdistributorrating() {
		return hostdistributorrating;
	}

	public void setHostdistributorrating(String hostdistributorrating) {
		this.hostdistributorrating = hostdistributorrating;
	}

	public String getHostfarerating() {
		return hostfarerating;
	}

	public void setHostfarerating(String hostfarerating) {
		this.hostfarerating = hostfarerating;
	}

	public String getHostmarketrating() {
		return hostmarketrating;
	}

	public void setHostmarketrating(String hostmarketrating) {
		this.hostmarketrating = hostmarketrating;
	}

	public String getHostproductrating() {
		return hostproductrating;
	}

	public void setHostproductrating(String hostproductrating) {
		this.hostproductrating = hostproductrating;
	}

	public String getHostwebfrequentavailabletotalfare() {
		return hostwebfrequentavailabletotalfare;
	}

	public void setHostwebfrequentavailabletotalfare(String hostwebfrequentavailabletotalfare) {
		this.hostwebfrequentavailabletotalfare = hostwebfrequentavailabletotalfare;
	}

	public String getHostwebfrequentavailablebasefare() {
		return hostwebfrequentavailablebasefare;
	}

	public void setHostwebfrequentavailablebasefare(String hostwebfrequentavailablebasefare) {
		this.hostwebfrequentavailablebasefare = hostwebfrequentavailablebasefare;
	}

	public String getHostwebfrequentavailablefarefrequency() {
		return hostwebfrequentavailablefarefrequency;
	}

	public void setHostwebfrequentavailablefarefrequency(String hostwebfrequentavailablefarefrequency) {
		this.hostwebfrequentavailablefarefrequency = hostwebfrequentavailablefarefrequency;
	}

	public String getHostwebfrequentavailablefaretax() {
		return hostwebfrequentavailablefaretax;
	}

	public void setHostwebfrequentavailablefaretax(String hostwebfrequentavailablefaretax) {
		this.hostwebfrequentavailablefaretax = hostwebfrequentavailablefaretax;
	}

	public String getHostlowestfiledfaretotalfare() {
		return hostlowestfiledfaretotalfare;
	}

	public void setHostlowestfiledfaretotalfare(String hostlowestfiledfaretotalfare) {
		this.hostlowestfiledfaretotalfare = hostlowestfiledfaretotalfare;
	}

	public String getHostlowestfiledfarebasefare() {
		return hostlowestfiledfarebasefare;
	}

	public void setHostlowestfiledfarebasefare(String hostlowestfiledfarebasefare) {
		this.hostlowestfiledfarebasefare = hostlowestfiledfarebasefare;
	}

	public String getHostlowesrfiledfaretax() {
		return hostlowesrfiledfaretax;
	}

	public void setHostlowesrfiledfaretax(String hostlowesrfiledfaretax) {
		this.hostlowesrfiledfaretax = hostlowesrfiledfaretax;
	}

	public String getHostlowestfiledfareyq() {
		return hostlowestfiledfareyq;
	}

	public void setHostlowestfiledfareyq(String hostlowestfiledfareyq) {
		this.hostlowestfiledfareyq = hostlowestfiledfareyq;
	}

	public String getHostlowestfiledfaresurcharges() {
		return hostlowestfiledfaresurcharges;
	}

	public void setHostlowestfiledfaresurcharges(String hostlowestfiledfaresurcharges) {
		this.hostlowestfiledfaresurcharges = hostlowestfiledfaresurcharges;
	}

	public String getCompmarketshare() {
		return compmarketshare;
	}

	public void setCompmarketshare(String compmarketshare) {
		this.compmarketshare = compmarketshare;
	}

	public String getCompmarketsharevlyr() {
		return compmarketsharevlyr;
	}

	public void setCompmarketsharevlyr(String compmarketsharevlyr) {
		this.compmarketsharevlyr = compmarketsharevlyr;
	}

	public String getCompfms() {
		return compfms;
	}

	public void setCompfms(String compfms) {
		this.compfms = compfms;
	}

	public String getCompmarketsharevtgt() {
		return compmarketsharevtgt;
	}

	public void setCompmarketsharevtgt(String compmarketsharevtgt) {
		this.compmarketsharevtgt = compmarketsharevtgt;
	}

	public String getCompcapacityrating() {
		return compcapacityrating;
	}

	public void setCompcapacityrating(String compcapacityrating) {
		this.compcapacityrating = compcapacityrating;
	}

	public String getCompdistributorrating() {
		return compdistributorrating;
	}

	public void setCompdistributorrating(String compdistributorrating) {
		this.compdistributorrating = compdistributorrating;
	}

	public String getCompfarerating() {
		return compfarerating;
	}

	public void setCompfarerating(String compfarerating) {
		this.compfarerating = compfarerating;
	}

	public String getCompmarketrating() {
		return compmarketrating;
	}

	public void setCompmarketrating(String compmarketrating) {
		this.compmarketrating = compmarketrating;
	}

	public String getCompproductrating() {
		return compproductrating;
	}

	public void setCompproductrating(String compproductrating) {
		this.compproductrating = compproductrating;
	}

	public String getCompwebfrequentlyavailabletotalfare() {
		return compwebfrequentlyavailabletotalfare;
	}

	public void setCompwebfrequentlyavailabletotalfare(String compwebfrequentlyavailabletotalfare) {
		this.compwebfrequentlyavailabletotalfare = compwebfrequentlyavailabletotalfare;
	}

	public String getCompwebfrequentlyavailablebasefare() {
		return compwebfrequentlyavailablebasefare;
	}

	public void setCompwebfrequentlyavailablebasefare(String compwebfrequentlyavailablebasefare) {
		this.compwebfrequentlyavailablebasefare = compwebfrequentlyavailablebasefare;
	}

	public String getCompwebfrequentlyavailablefarefrequency() {
		return compwebfrequentlyavailablefarefrequency;
	}

	public void setCompwebfrequentlyavailablefarefrequency(String compwebfrequentlyavailablefarefrequency) {
		this.compwebfrequentlyavailablefarefrequency = compwebfrequentlyavailablefarefrequency;
	}

	public String getCompwebfrequentlyavailablefaretax() {
		return compwebfrequentlyavailablefaretax;
	}

	public void setCompwebfrequentlyavailablefaretax(String compwebfrequentlyavailablefaretax) {
		this.compwebfrequentlyavailablefaretax = compwebfrequentlyavailablefaretax;
	}

	public String getComplowestfiledfaretotalfare() {
		return complowestfiledfaretotalfare;
	}

	public void setComplowestfiledfaretotalfare(String complowestfiledfaretotalfare) {
		this.complowestfiledfaretotalfare = complowestfiledfaretotalfare;
	}

	public String getComplowestfiledfarebasefare() {
		return complowestfiledfarebasefare;
	}

	public void setComplowestfiledfarebasefare(String complowestfiledfarebasefare) {
		this.complowestfiledfarebasefare = complowestfiledfarebasefare;
	}

	public String getComplowestfiledfaretax() {
		return complowestfiledfaretax;
	}

	public void setComplowestfiledfaretax(String complowestfiledfaretax) {
		this.complowestfiledfaretax = complowestfiledfaretax;
	}

	public String getComplowestfiledfareyq() {
		return complowestfiledfareyq;
	}

	public void setComplowestfiledfareyq(String complowestfiledfareyq) {
		this.complowestfiledfareyq = complowestfiledfareyq;
	}

	public String getComplowestfiledfaresurcharges() {
		return complowestfiledfaresurcharges;
	}

	public void setComplowestfiledfaresurcharges(String complowestfiledfaresurcharges) {
		this.complowestfiledfaresurcharges = complowestfiledfaresurcharges;
	}

	public Double getOwrt() {
		return owrt;
	}

	public void setOwrt(Double owrt) {
		this.owrt = owrt;
	}

	public List<String> getDaysofweekoutbound() {
		return daysofweekoutbound;
	}

	public void setDaysofweekoutbound(List<String> daysofweekoutbound) {
		this.daysofweekoutbound = daysofweekoutbound;
	}

	public List<String> getDaysofweekinbound() {
		return daysofweekinbound;
	}

	public void setDaysofweekinbound(List<String> daysofweekinbound) {
		this.daysofweekinbound = daysofweekinbound;
	}

	public Map<String, String> getTriggerIDdetails() {
		return triggerIDdetails;
	}

	public void setTriggerIDdetails(Map<String, String> triggerIDdetails) {
		this.triggerIDdetails = triggerIDdetails;
	}

	public Map<String, String> getFaredetails() {
		return faredetails;
	}

	public void setFaredetails(Map<String, String> faredetails) {
		this.faredetails = faredetails;
	}

	public List<String> getFlightnumberoutbound() {
		return flightnumberoutbound;
	}

	public void setFlightnumberoutbound(List<String> flightnumberoutbound) {
		this.flightnumberoutbound = flightnumberoutbound;
	}

	public List<String> getFlightnumberinbound() {
		return flightnumberinbound;
	}

	public void setFlightnumberinbound(List<String> flightnumberinbound) {
		this.flightnumberinbound = flightnumberinbound;
	}

}
