package com.flynava.jupiter.model;

public class Trigger {

	private String triggerId;

	private String triggerDesc;

	private String recomndCateg;

	private String triggerTypes;

	private String triggerSubTypes;

	public String getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}

	public String getTriggerDesc() {
		return triggerDesc;
	}

	public void setTriggerDesc(String triggerDesc) {
		this.triggerDesc = triggerDesc;
	}

	public String getRecomndCateg() {
		return recomndCateg;
	}

	public void setRecomndCateg(String recomndCateg) {
		this.recomndCateg = recomndCateg;
	}

	public String getTriggerTypes() {
		return triggerTypes;
	}

	public void setTriggerTypes(String triggerTypes) {
		this.triggerTypes = triggerTypes;
	}

	public String getTriggerSubTypes() {
		return triggerSubTypes;
	}

	public void setTriggerSubTypes(String triggerSubTypes) {
		this.triggerSubTypes = triggerSubTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((triggerId == null) ? 0 : triggerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this)
			return true;
		if (!(obj instanceof Trigger)) {
			return false;
		}

		Trigger trgr = (Trigger) obj;

		return trgr.triggerId.equalsIgnoreCase(triggerId);

	}

}
