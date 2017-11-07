package com.flynava.jupiter.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document
public class UserProfile {

	private String level;
	private String user;
	private String[] region;
	private String[] country;
	private String[] pos;
	private String[] od;
	private String[] compartment;
	private String[] competitors;
	private String sig_od;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String[] getRegion() {
		return region;
	}

	public void setRegion(String[] region) {
		this.region = region;
	}

	public String[] getCountry() {
		return country;
	}

	public void setCountry(String[] country) {
		this.country = country;
	}

	public String[] getPos() {
		return pos;
	}

	public void setPos(String[] pos) {
		this.pos = pos;
	}

	public String[] getOd() {
		return od;
	}

	public void setOd(String[] od) {
		this.od = od;
	}

	public String[] getCompartment() {
		return compartment;
	}

	public void setCompartment(String[] compartment) {
		this.compartment = compartment;
	}

	public String[] getCompetitors() {
		return competitors;
	}

	public void setCompetitors(String[] competitors) {
		this.competitors = competitors;
	}

	public String getSig_od() {
		return sig_od;
	}

	public void setSig_od(String sig_od) {
		this.sig_od = sig_od;
	}

}
