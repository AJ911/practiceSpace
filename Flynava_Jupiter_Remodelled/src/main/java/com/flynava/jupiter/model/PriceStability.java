package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PriceStability {

	private float Host_market_shareYTD;
	private float Host_market_shareVLYR;
	private float Host_market_shareVTGT;

	private String region;
	private String country;
	private String pos;

	private String compartment;
	private String marketingCarrier1;
	private float host_market_share;
	private float host_market_share_lastyr;
	private float market_size;
	private int market_size_1;
	private float capacity;
	private float rating;
	private float totalcapacity;
	private float totalmarketsize;
	private float totalmarketsize_1;
	private float fms;
	private String od;
	private String farebasis;
	private String rbd;

	private String origin;
	private String destination;
	private float nooffares;
	private float nooffarechanges;
	private float priceagilityindex;
	private float totalpriceagilityindex;

	private int totalMarketshare;
	private int totalMarketshare_lastyr;

	private String combinationkey;

	private float totalnooffare;
	private float totalnooffarechange;
	private float nooffareytd;
	private float nooffarechangeytd;

	public float getMarket_size() {
		return market_size;
	}

	public void setMarket_size(float market_size) {
		this.market_size = market_size;
	}

	public float getTotalmarketsize_1() {
		return totalmarketsize_1;
	}

	public void setTotalmarketsize_1(float totalmarketsize_1) {
		this.totalmarketsize_1 = totalmarketsize_1;
	}

	public float getFms() {
		return fms;
	}

	public void setFms(float fms) {
		this.fms = fms;
	}

	public float getTotalmarketsize() {
		return totalmarketsize;
	}

	public void setTotalmarketsize(float totalmarketsize) {
		this.totalmarketsize = totalmarketsize;
	}

	public float getTotalcapacity() {
		return totalcapacity;
	}

	public void setTotalcapacity(float totalcapacity) {
		this.totalcapacity = totalcapacity;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getTotalMarketshare() {
		return totalMarketshare;
	}

	public void setTotalMarketshare(int totalMarketshare) {
		this.totalMarketshare = totalMarketshare;
	}

	public int getTotalMarketshare_lastyr() {
		return totalMarketshare_lastyr;
	}

	public void setTotalMarketshare_lastyr(int totalMarketshare_lastyr) {
		this.totalMarketshare_lastyr = totalMarketshare_lastyr;
	}

	public float getHost_market_shareYTD() {
		return Host_market_shareYTD;
	}

	public void setHost_market_shareYTD(float host_market_shareYTD) {
		Host_market_shareYTD = host_market_shareYTD;
	}

	public float getHost_market_shareVLYR() {
		return Host_market_shareVLYR;
	}

	public void setHost_market_shareVLYR(float host_market_shareVLYR) {
		Host_market_shareVLYR = host_market_shareVLYR;
	}

	public float getHost_market_shareVTGT() {
		return Host_market_shareVTGT;
	}

	public void setHost_market_shareVTGT(float host_market_shareVTGT) {
		Host_market_shareVTGT = host_market_shareVTGT;
	}

	public String getMarketingCarrier1() {
		return marketingCarrier1;
	}

	public void setMarketingCarrier1(String marketingCarrier1) {
		this.marketingCarrier1 = marketingCarrier1;
	}

	private float totalHost_market_share;
	private float totalHost_market_share_lastyr;

	public void setHost_market_shareVTGT(int host_market_shareVTGT) {
		Host_market_shareVTGT = host_market_shareVTGT;
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

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
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

	public int getMarket_size_1() {
		return market_size_1;
	}

	public void setMarket_size_1(int market_size_1) {
		this.market_size_1 = market_size_1;
	}

	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public String getCombinationkey() {
		return combinationkey;
	}

	public void setCombinationkey(String combinationkey) {
		this.combinationkey = combinationkey;
	}

	public float getTotalHost_market_share() {
		return totalHost_market_share;
	}

	public void setTotalHost_market_share(float totalHost_market_share) {
		this.totalHost_market_share = totalHost_market_share;
	}

	public float getTotalHost_market_share_lastyr() {
		return totalHost_market_share_lastyr;
	}

	public void setTotalHost_market_share_lastyr(float totalHost_market_share_lastyr) {
		this.totalHost_market_share_lastyr = totalHost_market_share_lastyr;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
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

	public float getTotalnooffare() {
		return totalnooffare;
	}

	public void setTotalnooffare(float totalnooffare) {
		this.totalnooffare = totalnooffare;
	}

	public float getTotalnooffarechange() {
		return totalnooffarechange;
	}

	public void setTotalnooffarechange(float totalnooffarechange) {
		this.totalnooffarechange = totalnooffarechange;
	}

	public float getNooffareytd() {
		return nooffareytd;
	}

	public void setNooffareytd(float nooffareytd) {
		this.nooffareytd = nooffareytd;
	}

	public float getNooffarechangeytd() {
		return nooffarechangeytd;
	}

	public void setNooffarechangeytd(float nooffarechangeytd) {
		this.nooffarechangeytd = nooffarechangeytd;
	}

	public float getPriceagilityindex() {
		return priceagilityindex;
	}

	public void setPriceagilityindex(float priceagilityindex) {
		this.priceagilityindex = priceagilityindex;
	}

	public float getTotalpriceagilityindex() {
		return totalpriceagilityindex;
	}

	public void setTotalpriceagilityindex(float totalpriceagilityindex) {
		this.totalpriceagilityindex = totalpriceagilityindex;
	}

	public String getFarebasis() {
		return farebasis;
	}

	public void setFarebasis(String farebasis) {
		this.farebasis = farebasis;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

}
