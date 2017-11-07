package com.flynava.jupiter.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Market {

	private String dep_date;
	private String book_date;
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String compartment;
	private long pax;
	private long ticket;
	private long pax_1;
	private long hostYTD;
	private float hostVLYR;
	private float hostVTGT;
	private long target_pax;
	private long target_market_share;
	private long market_size;
	private long market_size_1;
	private float ticketedPercentage;
	
	
	private Map<String, Object> hostMarketShareMap;
	private Map<String, Object> competitorsMap;

	Map<String, Object> directChannelMap = new HashMap<String, Object>();
	Map<String, Object> indrectChannelMap = new HashMap<String, Object>();
	
	
	public Map<String, Object> getHostMarketShareMap() {
		return hostMarketShareMap;
	}
	public void setHostMarketShareMap(Map<String, Object> hostMarketShareMap) {
		this.hostMarketShareMap = hostMarketShareMap;
	}
	public Map<String, Object> getCompetitorsMap() {
		return competitorsMap;
	}
	public void setCompetitorsMap(Map<String, Object> competitorsMap) {
		this.competitorsMap = competitorsMap;
	}
	public Map<String, Object> getDirectChannelMap() {
		return directChannelMap;
	}
	public void setDirectChannelMap(Map<String, Object> directChannelMap) {
		this.directChannelMap = directChannelMap;
	}
	public Map<String, Object> getIndrectChannelMap() {
		return indrectChannelMap;
	}
	public void setIndrectChannelMap(Map<String, Object> indrectChannelMap) {
		this.indrectChannelMap = indrectChannelMap;
	}
	public String getDep_date() {
		return dep_date;
	}
	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}
	public String getBook_date() {
		return book_date;
	}
	public void setBook_date(String book_date) {
		this.book_date = book_date;
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
	public long getPax() {
		return pax;
	}
	public void setPax(long pax) {
		this.pax = pax;
	}
	public long getTicket() {
		return ticket;
	}
	public void setTicket(long ticket) {
		this.ticket = ticket;
	}
	public long getPax_1() {
		return pax_1;
	}
	public void setPax_1(long pax_1) {
		this.pax_1 = pax_1;
	}
	public long getHostYTD() {
		return hostYTD;
	}
	public void setHostYTD(long hostYTD) {
		this.hostYTD = hostYTD;
	}
	public float getHostVLYR() {
		return hostVLYR;
	}
	public void setHostVLYR(float hostVLYR) {
		this.hostVLYR = hostVLYR;
	}
	public float getHostVTGT() {
		return hostVTGT;
	}
	public void setHostVTGT(float hostVTGT) {
		this.hostVTGT = hostVTGT;
	}
	public long getTarget_pax() {
		return target_pax;
	}
	public void setTarget_pax(long target_pax) {
		this.target_pax = target_pax;
	}
	public long getTarget_market_share() {
		return target_market_share;
	}
	public void setTarget_market_share(long target_market_share) {
		this.target_market_share = target_market_share;
	}
	public long getMarket_size() {
		return market_size;
	}
	public void setMarket_size(long market_size) {
		this.market_size = market_size;
	}
	public long getMarket_size_1() {
		return market_size_1;
	}
	public void setMarket_size_1(long market_size_1) {
		this.market_size_1 = market_size_1;
	}
	public float getTicketedPercentage() {
		return ticketedPercentage;
	}
	public void setTicketedPercentage(float ticketedPercentage) {
		this.ticketedPercentage = ticketedPercentage;
	}

	
}
