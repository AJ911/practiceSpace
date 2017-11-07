package com.flynava.jupiter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

public class RequestModel {

	private JSONArray requestJson;
	private String fromDate;
	private String toDate;
	private String getDateFrom;
	private String genDateTo;
	private String lastYrFromDate;
	private String lastYrToDate;
	private String fromSnapDate;
	private String toSnapDate;
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private String year;
	private String flag;
	private String eventName;
	private String type;
	private String carriers;
	private String channel;
	private String features;
	private String currency;
	private String farebasis;
	private String[] regionArray;
	private String[] countryArray;
	private String[] posArray;
	private String[] originArray;
	private String[] destinationArray;
	private String[] compartmentArray;
	private String sigod;
	private String[] odArray;
	private String[] statusArray;
	private String[] monthArray;
	private String[] yearArray;
	private String month;
	private String dbName;
	private String collection;
	private String query;
	private String user;
	private String analystName;
	public String level;
	private JSONArray Pos1 = null;
	private JSONArray od = null;
	private JSONArray Compartment1 = null;
	private JSONArray Country1 = null;
	private JSONArray Region1 = null;
	private String role;
	public ArrayList<String> UserDetails;
	private String functionName;
	private String[] triggerArray;
	private String cond;
	private String[] rbdArray;
	private String singleOD;
	private String[] farebasisArray;
	private String[] currencyArray;
	private String name;
	private String[] competitorArray;
	private String[] dashboardArray;
	private String sigOD;
	private float discount;
	private String workPackageId;
	private String[] configdateArray;

	private String[] carrierArray;
	private String[] tarrifArray;
	private String[] ruleIDArray;
	private String[] clusterArray;
	private String[] zoneArray;
	private int[] oneway_returnArray;
	private String[] filingcurrencyArray;
	private String[] amountrangeArray;
	private String[] channelArray;
	private String[] fareTypeArray;
	private String[] agentArray;
	private String[] corporateArray;
	private String[] farebrandArray;
	private String[] fareDesignatorArray;
	private String[] footnoteArray;
	private Integer[] routeArray;
	private String[] wpnameArray;
	private String gfs;
	private String effectivefrom;
	private String effectiveto;
	private String salesfrom;
	private String salesto;
	private String travelfrom;
	private String travelto;
	private String travelComplete;
	private String seasonfirst_last;
	private String[] paxtypeArray;
	private String[] advancepurchaseArray;
	private String[] minStayArray;
	private String[] maxStayArray;
	private String dowin_out;
	private String[] holidaynameArray;
	private String[] marketArray;
	private String workpackagename;
	private String[] maxPriceArray;
	private String[] minPriceArray;
	private Boolean[] fare_flag;

	private String workpackageuser;

	private Map<String, List<String>> posMap;
	private Map<String, List<String>> originMap;
	private Map<String, List<String>> destMap;
	private Map<String, List<String>> compMap;

	private Map<String, List<String>> exclPosMap;
	private Map<String, List<String>> exclOriginMap;
	private Map<String, List<String>> exclDestMap;
	private Map<String, List<String>> exclCompMap;

	public Map<String, List<String>> getPosMap() {
		return posMap;
	}

	public void setPosMap(Map<String, List<String>> posMap) {
		this.posMap = posMap;
	}

	public Map<String, List<String>> getOriginMap() {
		return originMap;
	}

	public void setOriginMap(Map<String, List<String>> originMap) {
		this.originMap = originMap;
	}

	public Map<String, List<String>> getDestMap() {
		return destMap;
	}

	public void setDestMap(Map<String, List<String>> destMap) {
		this.destMap = destMap;
	}

	public Map<String, List<String>> getCompMap() {
		return compMap;
	}

	public void setCompMap(Map<String, List<String>> compMap) {
		this.compMap = compMap;
	}

	public Map<String, List<String>> getExclPosMap() {
		return exclPosMap;
	}

	public void setExclPosMap(Map<String, List<String>> exclPosMap) {
		this.exclPosMap = exclPosMap;
	}

	public Map<String, List<String>> getExclOriginMap() {
		return exclOriginMap;
	}

	public void setExclOriginMap(Map<String, List<String>> exclOriginMap) {
		this.exclOriginMap = exclOriginMap;
	}

	public Map<String, List<String>> getExclDestMap() {
		return exclDestMap;
	}

	public void setExclDestMap(Map<String, List<String>> exclDestMap) {
		this.exclDestMap = exclDestMap;
	}

	public Map<String, List<String>> getExclCompMap() {
		return exclCompMap;
	}

	public void setExclCompMap(Map<String, List<String>> exclCompMap) {
		this.exclCompMap = exclCompMap;
	}

	public String getWorkpackagename() {
		return workpackagename;
	}

	public void setWorkpackagename(String workpackagename) {
		this.workpackagename = workpackagename;
	}

	public String[] getCompetitorArray() {
		return competitorArray;
	}

	public void setCompetitorArray(String[] competitorArray) {
		this.competitorArray = competitorArray;
	}

	public String[] getDashboardArray() {
		return dashboardArray;
	}

	public void setDashboardArray(String[] dashboardArray) {
		this.dashboardArray = dashboardArray;
	}

	public String getSigOD() {
		return sigOD;
	}

	public void setSigOD(String sigOD) {
		this.sigOD = sigOD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSingleOD(String singleOD) {
		this.singleOD = singleOD;
	}

	public String[] getCurrencyArray() {
		return currencyArray;
	}

	public void setCurrencyArray(String[] currencyArray) {
		this.currencyArray = currencyArray;
	}

	public String getCond() {
		return cond;
	}

	public void setCond(String cond) {
		this.cond = cond;
	}

	public String[] getMonthArray() {
		return monthArray;
	}

	public void setMonthArray(String[] monthArray) {
		this.monthArray = monthArray;
	}

	public String[] getYearArray() {
		return yearArray;
	}

	public void setYearArray(String[] yearArray) {
		this.yearArray = yearArray;
	}

	public String[] getStatusArray() {
		return statusArray;
	}

	public void setStatusArray(String[] statusArray) {
		this.statusArray = statusArray;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String[] getTriggerArray() {
		return triggerArray;
	}

	public void setTriggerArray(String[] triggerArray) {
		this.triggerArray = triggerArray;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getCarriers() {
		return carriers;
	}

	public void setCarriers(String carriers) {
		this.carriers = carriers;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String[] getPosArray() {
		return posArray;
	}

	public void setPosArray(String[] posArray) {
		this.posArray = posArray;
	}

	public String[] getOriginArray() {
		return originArray;
	}

	public void setOriginArray(String[] originArray) {
		this.originArray = originArray;
	}

	public String[] getDestinationArray() {
		return destinationArray;
	}

	public void setDestinationArray(String[] destinationArray) {
		this.destinationArray = destinationArray;
	}

	public String[] getCompartmentArray() {
		return compartmentArray;
	}

	public void setCompartmentArray(String[] compartmentArray) {
		this.compartmentArray = compartmentArray;
	}

	public String[] getRegionArray() {
		return regionArray;
	}

	public void setRegionArray(String[] regionArray) {
		this.regionArray = regionArray;
	}

	public String[] getCountryArray() {
		return countryArray;
	}

	public void setCountryArray(String[] countryArray) {
		this.countryArray = countryArray;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFarebasis() {
		return farebasis;
	}

	public void setFarebasis(String farebasis) {
		this.farebasis = farebasis;
	}

	public String[] getOdArray() {
		return odArray;
	}

	public void setOdArray(String[] odArray) {
		this.odArray = odArray;
	}

	public String getLastYrFromDate() {
		return lastYrFromDate;
	}

	public void setLastYrFromDate(String lastYrFromDate) {
		this.lastYrFromDate = lastYrFromDate;
	}

	public String getLastYrToDate() {
		return lastYrToDate;
	}

	public void setLastYrToDate(String lastYrToDate) {
		this.lastYrToDate = lastYrToDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ArrayList<String> getUserDetails() {
		return UserDetails;
	}

	public void setUserDetails(ArrayList<String> userDetails) {
		UserDetails = userDetails;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public JSONArray getOd() {
		return od;
	}

	public void setOd(JSONArray od) {
		this.od = od;
	}

	public JSONArray getPos1() {
		return Pos1;
	}

	public void setPos1(JSONArray pos1) {
		Pos1 = pos1;
	}

	public JSONArray getCompartment1() {
		return Compartment1;
	}

	public void setCompartment1(JSONArray compartment1) {
		Compartment1 = compartment1;
	}

	public JSONArray getCountry1() {
		return Country1;
	}

	public void setCountry1(JSONArray country1) {
		Country1 = country1;
	}

	public JSONArray getRegion1() {
		return Region1;
	}

	public void setRegion1(JSONArray region1) {
		Region1 = region1;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String[] getRbdArray() {
		return rbdArray;
	}

	public void setRbdArray(String[] rbdArray) {
		this.rbdArray = rbdArray;
	}

	public String getSingleOD() {
		return singleOD;
	}

	public void setOD(String singleOD) {
		this.singleOD = singleOD;
	}

	public String[] getFarebasisArray() {
		return farebasisArray;
	}

	public void setFarebasisArray(String[] farebasisArray) {
		this.farebasisArray = farebasisArray;
	}

	public String getSigod() {
		return sigod;
	}

	public void setSigod(String sigod) {
		this.sigod = sigod;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getAnalystName() {
		return analystName;
	}

	public void setAnalystName(String analystName) {
		this.analystName = analystName;
	}

	public JSONArray getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(JSONArray requestJson) {
		this.requestJson = requestJson;
	}

	public String getWorkPackageId() {
		return workPackageId;
	}

	public void setWorkPackageId(String workPackageId) {
		this.workPackageId = workPackageId;
	}

	public String getFromSnapDate() {
		return fromSnapDate;
	}

	public void setFromSnapDate(String fromSnapDate) {
		this.fromSnapDate = fromSnapDate;
	}

	public String getToSnapDate() {
		return toSnapDate;
	}

	public void setToSnapDate(String toSnapDate) {
		this.toSnapDate = toSnapDate;
	}

	public String[] getConfigdateArray() {
		return configdateArray;
	}

	public void setConfigdateArray(String[] configdateArray) {
		this.configdateArray = configdateArray;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public String[] getCarrierArray() {
		return carrierArray;
	}

	public void setCarrierArray(String[] carrierArray) {
		this.carrierArray = carrierArray;
	}

	public String[] getRuleIDArray() {
		return ruleIDArray;
	}

	public void setRuleIDArray(String[] ruleIDArray) {
		this.ruleIDArray = ruleIDArray;
	}

	public String[] getClusterArray() {
		return clusterArray;
	}

	public void setClusterArray(String[] clusterArray) {
		this.clusterArray = clusterArray;
	}

	public String[] getZoneArray() {
		return zoneArray;
	}

	public void setZoneArray(String[] zoneArray) {
		this.zoneArray = zoneArray;
	}

	public String[] getFilingcurrencyArray() {
		return filingcurrencyArray;
	}

	public void setFilingcurrencyArray(String[] filingcurrencyArray) {
		this.filingcurrencyArray = filingcurrencyArray;
	}

	public String[] getAmountrangeArray() {
		return amountrangeArray;
	}

	public void setAmountrangeArray(String[] amountrangeArray) {
		this.amountrangeArray = amountrangeArray;
	}

	public String[] getChannelArray() {
		return channelArray;
	}

	public void setChannelArray(String[] channelArray) {
		this.channelArray = channelArray;
	}

	public String[] getAgentArray() {
		return agentArray;
	}

	public void setAgentArray(String[] agentArray) {
		this.agentArray = agentArray;
	}

	public String[] getCorporateArray() {
		return corporateArray;
	}

	public void setCorporateArray(String[] corporateArray) {
		this.corporateArray = corporateArray;
	}

	public String[] getFarebrandArray() {
		return farebrandArray;
	}

	public void setFarebrandArray(String[] farebrandArray) {
		this.farebrandArray = farebrandArray;
	}

	public String[] getFareDesignatorArray() {
		return fareDesignatorArray;
	}

	public void setFareDesignatorArray(String[] fareDesignatorArray) {
		this.fareDesignatorArray = fareDesignatorArray;
	}

	public String[] getFootnoteArray() {
		return footnoteArray;
	}

	public void setFootnoteArray(String[] footnoteArray) {
		this.footnoteArray = footnoteArray;
	}

	public String[] getWpnameArray() {
		return wpnameArray;
	}

	public void setWpnameArray(String[] wpnameArray) {
		this.wpnameArray = wpnameArray;
	}

	public String[] getPaxtypeArray() {
		return paxtypeArray;
	}

	public void setPaxtypeArray(String[] paxtypeArray) {
		this.paxtypeArray = paxtypeArray;
	}

	public String[] getAdvancepurchaseArray() {
		return advancepurchaseArray;
	}

	public void setAdvancepurchaseArray(String[] advancepurchaseArray) {
		this.advancepurchaseArray = advancepurchaseArray;
	}

	public String[] getMinStayArray() {
		return minStayArray;
	}

	public void setMinStayArray(String[] minStayArray) {
		this.minStayArray = minStayArray;
	}

	public String[] getMaxStayArray() {
		return maxStayArray;
	}

	public void setMaxStayArray(String[] maxStayArray) {
		this.maxStayArray = maxStayArray;
	}

	public String getSeasonfirst_last() {
		return seasonfirst_last;
	}

	public void setSeasonfirst_last(String seasonfirst_last) {
		this.seasonfirst_last = seasonfirst_last;
	}

	public String getDowin_out() {
		return dowin_out;
	}

	public void setDowin_out(String dowin_out) {
		this.dowin_out = dowin_out;
	}

	public void setRouteArray(Integer[] routeArray) {
		this.routeArray = routeArray;
	}

	public Integer[] getRouteArray() {
		return routeArray;
	}

	public String[] getHolidaynameArray() {
		return holidaynameArray;
	}

	public void setHolidaynameArray(String[] holidaynameArray) {
		this.holidaynameArray = holidaynameArray;
	}

	public String[] getMarketArray() {
		return marketArray;
	}

	public void setMarketArray(String[] marketArray) {
		this.marketArray = marketArray;
	}

	public String getEffectivefrom() {
		return effectivefrom;
	}

	public void setEffectivefrom(String effectivefrom) {
		this.effectivefrom = effectivefrom;
	}

	public String getEffectiveto() {
		return effectiveto;
	}

	public void setEffectiveto(String effectiveto) {
		this.effectiveto = effectiveto;
	}

	public String getSalesfrom() {
		return salesfrom;
	}

	public void setSalesfrom(String salesfrom) {
		this.salesfrom = salesfrom;
	}

	public String getSalesto() {
		return salesto;
	}

	public void setSalesto(String salesto) {
		this.salesto = salesto;
	}

	public String getTravelfrom() {
		return travelfrom;
	}

	public void setTravelfrom(String travelfrom) {
		this.travelfrom = travelfrom;
	}

	public String getTravelto() {
		return travelto;
	}

	public void setTravelto(String travelto) {
		this.travelto = travelto;
	}

	public String getTravelComplete() {
		return travelComplete;
	}

	public void setTravelComplete(String travelComplete) {
		this.travelComplete = travelComplete;
	}

	public String getWorkpackageuser() {
		return workpackageuser;
	}

	public void setWorkpackageuser(String workpackageuser) {
		this.workpackageuser = workpackageuser;
	}

	public String[] getFareTypeArray() {
		return fareTypeArray;
	}

	public void setFareTypeArray(String[] fareTypeArray) {
		this.fareTypeArray = fareTypeArray;
	}

	public int[] getOneway_returnArray() {
		return oneway_returnArray;
	}

	public void setOneway_returnArray(int[] oneway_returnArray) {
		this.oneway_returnArray = oneway_returnArray;
	}

	public String getGfs() {
		return gfs;
	}

	public void setGfs(String gfs) {
		this.gfs = gfs;
	}

	public String[] getTarrifArray() {
		return tarrifArray;
	}

	public void setTarrifArray(String[] tarrifArray) {
		this.tarrifArray = tarrifArray;
	}

	public String[] getMaxPriceArray() {
		return maxPriceArray;
	}

	public void setMaxPriceArray(String[] maxPriceArray) {
		this.maxPriceArray = maxPriceArray;
	}

	public String[] getMinPriceArray() {
		return minPriceArray;
	}

	public void setMinPriceArray(String[] minPriceArray) {
		this.minPriceArray = minPriceArray;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Boolean[] getFare_flag() {
		return fare_flag;
	}

	public void setFare_flag(Boolean[] fare_flag) {
		this.fare_flag = fare_flag;
	}

	public String getGetDateFrom() {
		return getDateFrom;
	}

	public void setGetDateFrom(String getDateFrom) {
		this.getDateFrom = getDateFrom;
	}

	public String getGenDateTo() {
		return genDateTo;
	}

	public void setGenDateTo(String genDateTo) {
		this.genDateTo = genDateTo;
	}

}
