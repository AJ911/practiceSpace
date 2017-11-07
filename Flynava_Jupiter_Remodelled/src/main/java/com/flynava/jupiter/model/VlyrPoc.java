package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class VlyrPoc {

	private String departureDate;

	private String region;
	private String country;
	private String pos;
	private String od;
	private String compartment;
	private String filterKey;
	
	private float capacityFZ;
	private float capacityFZ_LY;
	
	private float hostBookings;
	private float hostBookings_lastyr;
	private float hostBookings_VLYR;
	
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
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
	public String getFilterKey() {
		return filterKey;
	}
	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}
	public float getCapacityFZ() {
		return capacityFZ;
	}
	public void setCapacityFZ(float capacityFZ) {
		this.capacityFZ = capacityFZ;
	}
	public float getCapacityFZ_LY() {
		return capacityFZ_LY;
	}
	public void setCapacityFZ_LY(float capacityFZ_LY) {
		this.capacityFZ_LY = capacityFZ_LY;
	}
	public float getHostBookings() {
		return hostBookings;
	}
	public void setHostBookings(float hostBookings) {
		this.hostBookings = hostBookings;
	}
	public float getHostBookings_lastyr() {
		return hostBookings_lastyr;
	}
	public void setHostBookings_lastyr(float hostBookings_lastyr) {
		this.hostBookings_lastyr = hostBookings_lastyr;
	}
	public float getHostBookings_VLYR() {
		return hostBookings_VLYR;
	}
	public void setHostBookings_VLYR(float hostBookings_VLYR) {
		this.hostBookings_VLYR = hostBookings_VLYR;
	}

}
