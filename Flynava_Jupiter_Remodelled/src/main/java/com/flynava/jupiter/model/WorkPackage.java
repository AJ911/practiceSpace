package com.flynava.jupiter.model;

public class WorkPackage {

	private String workPackageId;
	private String[] triggerId;
	private String packageName;
	private String priorityNumber;
	private String businessRegion;
	private String productType;
	private String subType;
	private String ruleOrFareChange;
	private String lastBillingDate;
	private String createdBy;
	private String reviewLevel;
	private String targetFilingDate;
	private String filingStatus;
	private String QAStatus;
	private String creationDate;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getReviewLevel() {
		return reviewLevel;
	}

	public void setReviewLevel(String reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	public String getTargetFilingDate() {
		return targetFilingDate;
	}

	public void setTargetFilingDate(String targetFilingDate) {
		this.targetFilingDate = targetFilingDate;
	}

	public String getFilingStatus() {
		return filingStatus;
	}

	public void setFilingStatus(String filingStatus) {
		this.filingStatus = filingStatus;
	}

	public String getWorkPackageId() {
		return workPackageId;
	}

	public void setWorkPackageId(String workPackageId) {
		this.workPackageId = workPackageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPriorityNumber() {
		return priorityNumber;
	}

	public void setPriorityNumber(String priorityNumber) {
		this.priorityNumber = priorityNumber;
	}

	public String getBusinessRegion() {
		return businessRegion;
	}

	public void setBusinessRegion(String businessRegion) {
		this.businessRegion = businessRegion;
	}

	public String getRuleOrFareChange() {
		return ruleOrFareChange;
	}

	public void setRuleOrFareChange(String ruleOrFareChange) {
		this.ruleOrFareChange = ruleOrFareChange;
	}

	public String getLastBillingDate() {
		return lastBillingDate;
	}

	public void setLastBillingDate(String lastBillingDate) {
		this.lastBillingDate = lastBillingDate;
	}

	public String[] getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(String[] triggerId) {
		this.triggerId = triggerId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getQAStatus() {
		return QAStatus;
	}

	public void setQAStatus(String qAStatus) {
		QAStatus = qAStatus;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

}
