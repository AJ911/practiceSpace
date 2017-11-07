package com.flynava.jupiter.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Price {

	private float host_market_shareYTD;
	private float host_market_shareVLYR;
	private float host_market_shareVTGT;

	private int paxYTD;
	private int paxVLYR;
	private float paxVTGT;
	private ArrayList<String> lCarrier = new ArrayList<String>();
	private float elasticity_floor;
	private float elasticity_ceiling;

	private String marketSharePax;
	private String marketSharePax_lastyr;
	private String capacity;

	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String od;
	private String compartment;
	private String fare_basis;
	private String currency;
	private int pax;
	private int pax_1;
	private float base_fare;
	private float price;
	private String channel;
	private String customerSegment;
	private int pax_target;
	private String marketingCarrier1;
	private float host_market_share;
	private float host_market_share_lastyr;
	private String customer_segment;
	private int targetpax;

	private String season;
	private String carrier;
	private float nooffares;
	private float nooffarechanges;

	private float valid_for_period;
	private float validforperiod;
	private String farebasis;
	private String rbd;
	private float fare;
	private float market_share_pax;
	private float market_share_pax_1;
	private float target_market_size;
	private float market_size;
	private float market_size_1;

	private float totalMarketshare;
	private float totalMarketshare_lastyr;

	private int totalPax;
	private int totalPax_lastyr;

	private int pax_lastyr;
	private float compfare1;
	private float compfare2;
	private float compfarebasis1;
	private float compfarebasis2;

	private String dep_date;
	private String marketsize;
	private String marketsize1;

	private float targetmarketshare;
	private float revenue;
	private ArrayList<String> carrierList = new ArrayList<String>();

	private ArrayList<String> marketsharepaxList = new ArrayList<String>();
	private ArrayList<String> marketsharepaxList1 = new ArrayList<String>();
	private ArrayList<String> marketsizeList = new ArrayList<String>();

	private String combinationkey;

	JSONArray market_sizeArray = null;
	JSONArray market_size_1Array = null;
	private JSONArray carrierArray = new JSONArray();

	public JSONArray getMarket_sizeArray() {
		return market_sizeArray;
	}

	public void setMarket_sizeArray(JSONArray market_sizeArray) {
		this.market_sizeArray = market_sizeArray;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public float getFare() {
		return fare;
	}

	public void setFare(float fare) {
		this.fare = fare;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getMarketingCarrier1() {
		return marketingCarrier1;
	}

	public void setMarketingCarrier1(String marketingCarrier1) {
		this.marketingCarrier1 = marketingCarrier1;
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

	public JSONArray getMarket_size_1Array() {
		return market_size_1Array;
	}

	public void setMarket_size_1Array(JSONArray market_size_1Array) {
		this.market_size_1Array = market_size_1Array;
	}

	public int getTargetpax() {
		return targetpax;
	}

	public void setTargetpax(int targetpax) {
		this.targetpax = targetpax;
	}

	public int getPax_lastyr() {
		return pax_lastyr;
	}

	public void setPax_lastyr(int pax_lastyr) {
		this.pax_lastyr = pax_lastyr;
	}

	public float getElasticity_floor() {
		return elasticity_floor;
	}

	public void setElasticity_floor(float elasticity_floor) {
		this.elasticity_floor = elasticity_floor;
	}

	public float getElasticity_ceiling() {
		return elasticity_ceiling;
	}

	public void setElasticity_ceiling(float elasticity_ceiling) {
		this.elasticity_ceiling = elasticity_ceiling;
	}

	public int getTotalPax() {
		return totalPax;
	}

	public void setTotalPax(int totalPax) {
		this.totalPax = totalPax;
	}

	public int getTotalPax_lastyr() {
		return totalPax_lastyr;
	}

	public void setTotalPax_lastyr(int totalPax_lastyr) {
		this.totalPax_lastyr = totalPax_lastyr;
	}

	public float getHost_market_shareYTD() {
		return host_market_shareYTD;
	}

	public void setHost_market_shareYTD(float host_market_shareYTD) {
		this.host_market_shareYTD = host_market_shareYTD;
	}

	public float getHost_market_shareVLYR() {
		return host_market_shareVLYR;
	}

	public void setHost_market_shareVLYR(float host_market_shareVLYR) {
		this.host_market_shareVLYR = host_market_shareVLYR;
	}

	public float getHost_market_shareVTGT() {
		return host_market_shareVTGT;
	}

	public void setHost_market_shareVTGT(float host_market_shareVTGT) {
		this.host_market_shareVTGT = host_market_shareVTGT;
	}

	public int getPaxYTD() {
		return paxYTD;
	}

	public void setPaxYTD(int paxYTD) {
		this.paxYTD = paxYTD;
	}

	public int getPaxVLYR() {
		return paxVLYR;
	}

	public void setPaxVLYR(int paxVLYR) {
		this.paxVLYR = paxVLYR;
	}

	public float getPaxVTGT() {
		return paxVTGT;
	}

	public void setPaxVTGT(float paxVTGT) {
		this.paxVTGT = paxVTGT;
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

	public String getFare_basis() {
		return fare_basis;
	}

	public void setFare_basis(String fare_basis) {
		this.fare_basis = fare_basis;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getPax() {
		return pax;
	}

	public void setPax(int pax) {
		this.pax = pax;
	}

	public int getPax_1() {
		return pax_1;
	}

	public void setPax_1(int pax_1) {
		this.pax_1 = pax_1;
	}

	public float getBase_fare() {
		return base_fare;
	}

	public void setBase_fare(float base_fare) {
		this.base_fare = base_fare;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getPax_target() {
		return pax_target;
	}

	public void setPax_target(int pax_target) {
		this.pax_target = pax_target;
	}

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}

	public String getCustomer_segment() {
		return customer_segment;
	}

	public void setCustomer_segment(String customer_segment) {
		this.customer_segment = customer_segment;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	public float getValidforperiod() {
		return validforperiod;
	}

	public void setValidforperiod(float validforperiod) {
		this.validforperiod = validforperiod;
	}

	public float getCompfare1() {
		return compfare1;
	}

	public void setCompfare1(float compfare1) {
		this.compfare1 = compfare1;
	}

	public float getCompfare2() {
		return compfare2;
	}

	public void setCompfare2(float compfare2) {
		this.compfare2 = compfare2;
	}

	public float getCompfarebasis1() {
		return compfarebasis1;
	}

	public void setCompfarebasis1(float compfarebasis1) {
		this.compfarebasis1 = compfarebasis1;
	}

	public float getCompfarebasis2() {
		return compfarebasis2;
	}

	public void setCompfarebasis2(float compfarebasis2) {
		this.compfarebasis2 = compfarebasis2;
	}

	public float getMarket_share_pax() {
		return market_share_pax;
	}

	public void setMarket_share_pax(float market_share_pax) {
		this.market_share_pax = market_share_pax;
	}

	public float getMarket_share_pax_1() {
		return market_share_pax_1;
	}

	public void setMarket_share_pax_1(float market_share_pax_1) {
		this.market_share_pax_1 = market_share_pax_1;
	}

	public float getTarget_market_size() {
		return target_market_size;
	}

	public void setTarget_market_size(float target_market_size) {
		this.target_market_size = target_market_size;
	}

	public float getMarket_size() {
		return market_size;
	}

	public void setMarket_size(float market_size) {
		this.market_size = market_size;
	}

	public float getMarket_size_1() {
		return market_size_1;
	}

	public void setMarket_size_1(float market_size_1) {
		this.market_size_1 = market_size_1;
	}

	public float getTotalMarketshare() {
		return totalMarketshare;
	}

	public void setTotalMarketshare(float totalMarketshare) {
		this.totalMarketshare = totalMarketshare;
	}

	public float getTotalMarketshare_lastyr() {
		return totalMarketshare_lastyr;
	}

	public void setTotalMarketshare_lastyr(float totalMarketshare_lastyr) {
		this.totalMarketshare_lastyr = totalMarketshare_lastyr;
	}

	public float getRevenue() {
		return revenue;
	}

	public void setRevenue(float revenue) {
		this.revenue = revenue;
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

	public float getValid_for_period() {
		return valid_for_period;
	}

	public void setValid_for_period(float valid_for_period) {
		this.valid_for_period = valid_for_period;
	}

	public JSONArray getCarrierArray() {
		return carrierArray;
	}

	public void setCarrierArray(JSONArray carrierArray) {
		this.carrierArray = carrierArray;
	}

	public ArrayList<String> getCarrierList() {
		return carrierList;
	}

	public void setCarrierList(ArrayList<String> carrierList) {
		this.carrierList = carrierList;
	}

	public ArrayList<String> getMarketsharepaxList() {
		return marketsharepaxList;
	}

	public void setMarketsharepaxList(ArrayList<String> marketsharepaxList) {
		this.marketsharepaxList = marketsharepaxList;
	}

	public ArrayList<String> getMarketsharepaxList1() {
		return marketsharepaxList1;
	}

	public void setMarketsharepaxList1(ArrayList<String> marketsharepaxList1) {
		this.marketsharepaxList1 = marketsharepaxList1;
	}

	public ArrayList<String> getMarketsizeList() {
		return marketsizeList;
	}

	public void setMarketsizeList(ArrayList<String> marketsizeList) {
		this.marketsizeList = marketsizeList;
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

	public String getDep_date() {
		return dep_date;
	}

	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
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

	public String getFarebasis() {
		return farebasis;
	}

	public void setFarebasis(String farebasis) {
		this.farebasis = farebasis;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public ArrayList<String> getlCarrier() {
		return lCarrier;
	}

	public void setlCarrier(ArrayList<String> lCarrier) {
		this.lCarrier = lCarrier;
	}

}
