
package com.flynava.jupiter.model;

public class PriceQuote {
	private String departureDate;
	private String region;
	private String country;
	private String pos;
	private String origin;
	private String destination;
	private String od;
	private String channel;
	private String fareBasis;
	private String customerSegment;
	private Double yeild;
	private Double yeildLastYear;
	private Double yeildTarget;
	private Double hostPax;
	private float hostFlownPax;
	private Double hostPaxForecast;
	private Double hostPaxLastYear;
	private Double hostPaxTarget;
	private Double marketSharePax;
	private Double marketSharePaxLastYear;
	private String combinationKey;
	private Double count;
	private Double yeildVLYR;
	private Double yeildVTGT;
	private String compartment;
	private String baseFare;
	private String rbd;

	private String totalHostPax;
	private String totalHostPax_lastyr;

	private float marketSize;
	private float marketSize_lastyr;
	private float totalMarketSize;
	private float totalMarketSize_lastyr;

	private float totalFlownRevenue;
	private float totalFlownRevenue_lastyr;

	private float totalRevenue;
	private float totalRevenue_lastyr;

	private float odDistance;
	private int months;
	private int days;

	private String capacityFZ;
	private String capacityComp1;
	private String capacityComp2;
	private String capacityComp3;
	private String capacityComp4;

	private String compRatingFZ;
	private String compRatingComp1;
	private String compRatingComp2;
	private String compRatingComp3;
	private String compRatingComp4;

	private String footNote;
	private String ruleID;
	private String totalFare;
	private float yqCharge;
	private float taxes;
	private float surCharge;
	private String currency;

	public String getCompartment() {
		return compartment;
	}

	public void setCompartment(String compartment) {
		this.compartment = compartment;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getFareBasis() {
		return fareBasis;
	}

	public void setFareBasis(String fareBasis) {
		this.fareBasis = fareBasis;
	}

	public String getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	public Double getYeild() {
		return yeild;
	}

	public void setYeild(Double yeild) {
		this.yeild = yeild;
	}

	public Double getYeildLastYear() {
		return yeildLastYear;
	}

	public void setYeildLastYear(Double yeildLastYear) {
		this.yeildLastYear = yeildLastYear;
	}

	public Double getYeildTarget() {
		return yeildTarget;
	}

	public void setYeildTarget(Double yeildTarget) {
		this.yeildTarget = yeildTarget;
	}

	public Double getHostPax() {
		return hostPax;
	}

	public void setHostPax(Double hostPax) {
		this.hostPax = hostPax;
	}

	public Double getHostPaxLastYear() {
		return hostPaxLastYear;
	}

	public void setHostPaxLastYear(Double hostPaxLastYear) {
		this.hostPaxLastYear = hostPaxLastYear;
	}

	public Double getHostPaxTarget() {
		return hostPaxTarget;
	}

	public void setHostPaxTarget(Double hostPaxTarget) {
		this.hostPaxTarget = hostPaxTarget;
	}

	public Double getMarketSharePax() {
		return marketSharePax;
	}

	public void setMarketSharePax(Double marketSharePax) {
		this.marketSharePax = marketSharePax;
	}

	public Double getMarketSharePaxLastYear() {
		return marketSharePaxLastYear;
	}

	public void setMarketSharePaxLastYear(Double marketSharePaxLastYear) {
		this.marketSharePaxLastYear = marketSharePaxLastYear;
	}

	public String getCombinationKey() {
		return combinationKey;
	}

	public void setCombinationKey(String combinationKey) {
		this.combinationKey = combinationKey;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public Double getYeildVLYR() {
		return yeildVLYR;
	}

	public void setYeildVLYR(Double yeildVLYR) {
		this.yeildVLYR = yeildVLYR;
	}

	public Double getYeildVTGT() {
		return yeildVTGT;
	}

	public void setYeildVTGT(Double yeildVTGT) {
		this.yeildVTGT = yeildVTGT;
	}

	public Double getHostPaxVLYR() {
		return hostPaxVLYR;
	}

	public void setHostPaxVLYR(Double hostPaxVLYR) {
		this.hostPaxVLYR = hostPaxVLYR;
	}

	public Double getHostPaxVTGT() {
		return hostPaxVTGT;
	}

	public void setHostPaxVTGT(Double hostPaxVTGT) {
		this.hostPaxVTGT = hostPaxVTGT;
	}

	public Double getMarketShareVLYR() {
		return marketShareVLYR;
	}

	public void setMarketShareVLYR(Double marketShareVLYR) {
		this.marketShareVLYR = marketShareVLYR;
	}

	public Double getMarketShareVTGT() {
		return marketShareVTGT;
	}

	public void setMarketShareVTGT(Double marketShareVTGT) {
		this.marketShareVTGT = marketShareVTGT;
	}

	private Double hostPaxVLYR;
	private Double hostPaxVTGT;
	private Double marketShareVLYR;
	private Double marketShareVTGT;

	public String getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(String baseFare) {
		this.baseFare = baseFare;
	}

	public String getTotalHostPax() {
		return totalHostPax;
	}

	public void setTotalHostPax(String totalHostPax) {
		this.totalHostPax = totalHostPax;
	}

	public String getTotalHostPax_lastyr() {
		return totalHostPax_lastyr;
	}

	public void setTotalHostPax_lastyr(String totalHostPax_lastyr) {
		this.totalHostPax_lastyr = totalHostPax_lastyr;
	}

	public float getMarketSize() {
		return marketSize;
	}

	public void setMarketSize(float marketSize) {
		this.marketSize = marketSize;
	}

	public float getMarketSize_lastyr() {
		return marketSize_lastyr;
	}

	public void setMarketSize_lastyr(float marketSize_lastyr) {
		this.marketSize_lastyr = marketSize_lastyr;
	}

	public float getTotalMarketSize() {
		return totalMarketSize;
	}

	public void setTotalMarketSize(float totalMarketSize) {
		this.totalMarketSize = totalMarketSize;
	}

	public float getTotalMarketSize_lastyr() {
		return totalMarketSize_lastyr;
	}

	public void setTotalMarketSize_lastyr(float totalMarketSize_lastyr) {
		this.totalMarketSize_lastyr = totalMarketSize_lastyr;
	}

	public float getTotalFlownRevenue() {
		return totalFlownRevenue;
	}

	public void setTotalFlownRevenue(float totalFlownRevenue) {
		this.totalFlownRevenue = totalFlownRevenue;
	}

	public float getTotalFlownRevenue_lastyr() {
		return totalFlownRevenue_lastyr;
	}

	public void setTotalFlownRevenue_lastyr(float totalFlownRevenue_lastyr) {
		this.totalFlownRevenue_lastyr = totalFlownRevenue_lastyr;
	}

	public float getOdDistance() {
		return odDistance;
	}

	public void setOdDistance(float odDistance) {
		this.odDistance = odDistance;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public float getHostFlownPax() {
		return hostFlownPax;
	}

	public void setHostFlownPax(float hostFlownPax) {
		this.hostFlownPax = hostFlownPax;
	}

	public Double getHostPaxForecast() {
		return hostPaxForecast;
	}

	public void setHostPaxForecast(Double hostPaxForecast) {
		this.hostPaxForecast = hostPaxForecast;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getCapacityFZ() {
		return capacityFZ;
	}

	public void setCapacityFZ(String capacityFZ) {
		this.capacityFZ = capacityFZ;
	}

	public String getCapacityComp1() {
		return capacityComp1;
	}

	public void setCapacityComp1(String capacityComp1) {
		this.capacityComp1 = capacityComp1;
	}

	public String getCapacityComp2() {
		return capacityComp2;
	}

	public void setCapacityComp2(String capacityComp2) {
		this.capacityComp2 = capacityComp2;
	}

	public String getCapacityComp3() {
		return capacityComp3;
	}

	public void setCapacityComp3(String capacityComp3) {
		this.capacityComp3 = capacityComp3;
	}

	public String getCapacityComp4() {
		return capacityComp4;
	}

	public void setCapacityComp4(String capacityComp4) {
		this.capacityComp4 = capacityComp4;
	}

	public String getCompRatingFZ() {
		return compRatingFZ;
	}

	public void setCompRatingFZ(String compRatingFZ) {
		this.compRatingFZ = compRatingFZ;
	}

	public String getCompRatingComp1() {
		return compRatingComp1;
	}

	public void setCompRatingComp1(String compRatingComp1) {
		this.compRatingComp1 = compRatingComp1;
	}

	public String getCompRatingComp2() {
		return compRatingComp2;
	}

	public void setCompRatingComp2(String compRatingComp2) {
		this.compRatingComp2 = compRatingComp2;
	}

	public String getCompRatingComp3() {
		return compRatingComp3;
	}

	public void setCompRatingComp3(String compRatingComp3) {
		this.compRatingComp3 = compRatingComp3;
	}

	public String getCompRatingComp4() {
		return compRatingComp4;
	}

	public void setCompRatingComp4(String compRatingComp4) {
		this.compRatingComp4 = compRatingComp4;
	}

	public float getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(float totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public float getTotalRevenue_lastyr() {
		return totalRevenue_lastyr;
	}

	public void setTotalRevenue_lastyr(float totalRevenue_lastyr) {
		this.totalRevenue_lastyr = totalRevenue_lastyr;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

	public String getFootNote() {
		return footNote;
	}

	public void setFootNote(String footNote) {
		this.footNote = footNote;
	}

	public String getRuleID() {
		return ruleID;
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}

	public String getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(String totalFare) {
		this.totalFare = totalFare;
	}

	public float getYqCharge() {
		return yqCharge;
	}

	public void setYqCharge(float yqCharge) {
		this.yqCharge = yqCharge;
	}

	public float getTaxes() {
		return taxes;
	}

	public void setTaxes(float taxes) {
		this.taxes = taxes;
	}

	public float getSurCharge() {
		return surCharge;
	}

	public void setSurCharge(float surCharge) {
		this.surCharge = surCharge;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
