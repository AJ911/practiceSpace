package com.flynava.jupiter.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document
public class UserDetails {

	private String level;
	private String user;
	private JSONArray region=null;
	private JSONArray country=null;
	private JSONArray od=null;
	private JSONArray pos=null;
	private JSONArray compartment=null;
	private JSONArray competitors=null;
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
	public JSONArray getRegion() {
		return region;
	}
	public void setRegion(JSONArray region) {
		this.region = region;
	}
	public JSONArray getCountry() {
		return country;
	}
	public void setCountry(JSONArray country) {
		this.country = country;
	}
	public JSONArray getOd() {
		return od;
	}
	public void setOd(JSONArray od) {
		this.od = od;
	}
	public JSONArray getPos() {
		return pos;
	}
	public void setPos(JSONArray pos) {
		this.pos = pos;
	}
	public JSONArray getCompartment() {
		return compartment;
	}
	public void setCompartment(JSONArray compartment) {
		this.compartment = compartment;
	}
	public JSONArray getCompetitors() {
		return competitors;
	}
	public void setCompetitors(JSONArray competitors) {
		this.competitors = competitors;
	}
	public String getSig_od() {
		return sig_od;
	}
	public void setSig_od(String sig_od) {
		this.sig_od = sig_od;
	}
	
	
	
	
	
}
