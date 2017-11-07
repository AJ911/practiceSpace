package com.flynava.jupiter.model;

import java.util.Set;

public class ModelPerformance {
	private String modelName;
	private Set<String> odSet;
	private Double odCount;
	private Double usage;
	private Double performance;
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Set<String> getOdSet() {
		return odSet;
	}
	public void setOdSet(Set<String> odSet) {
		this.odSet = odSet;
	}
	public Double getOdCount() {
		return odCount;
	}
	public void setOdCount(Double odCount) {
		this.odCount = odCount;
	}
	public Double getUsage() {
		return usage;
	}
	public void setUsage(Double usage) {
		this.usage = usage;
	}
	public Double getPerformance() {
		return performance;
	}
	public void setPerformance(Double performance) {
		this.performance = performance;
	}
}
