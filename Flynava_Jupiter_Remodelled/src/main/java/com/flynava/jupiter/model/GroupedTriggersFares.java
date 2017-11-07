package com.flynava.jupiter.model;

import java.util.List;
import java.util.Set;

public class GroupedTriggersFares {

	private Set<Trigger> triggersList;
	private List<TriggerFares> triggerFaresList;

	public Set<Trigger> getTriggersList() {
		return triggersList;
	}

	public void setTriggersList(Set<Trigger> triggersList) {
		this.triggersList = triggersList;
	}

	public List<TriggerFares> getTriggerFaresList() {
		return triggerFaresList;
	}

	public void setTriggerFaresList(List<TriggerFares> triggerFaresList) {
		this.triggerFaresList = triggerFaresList;
	}

}
