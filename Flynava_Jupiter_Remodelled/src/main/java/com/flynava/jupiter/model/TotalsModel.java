/**
 * 
 */
package com.flynava.jupiter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anu Merin
 *
 */
public class TotalsModel {

	private String carrier;

	private float totalRevenueYTD;
	private float totalRevenueVLYR;
	private float totalRevenueVTGT;

	private float totalYieldYTD;
	private float totalYieldVTGT;
	private float totalYieldVLYR;

	private float totalPaxYTD;
	private float totalPaxVLYR;
	private float totalPaxVTGT;

	private float totalMarketShareYTD;
	private float totalMarketShareVLYR;
	private float totalMarketShareVTGT;

	private float totalcompMarketShareYTD;
	private float totalcompMarketShareVLYR;
	private float totalcompMarketShareVTGT;

	private float totalSalesYTD;
	private float totalFlownYTD;

	private float totalseatfactorYTD;
	private float totalseatfactorVLYR;
	private float totalseatfactorVTGT;

	private float fare;

	private float totalpriceperformanceYTD;
	private float totalpriceperformanceVLYR;
	private float totalpriceperformanceVTGT;

	private float totalpriceelasticityYTD;
	private float totalpriceelasticityVLYR;
	private float totalpriceelasticityVTGT;

	private float totalYQ;
	private float totalYQVLYR;
	private float totalAvgRateRealization;

	private float totalSalesRevenueYTD;
	private float totalFlownRevenueYTD;
	private float totalDedicatedFare;
	private int totalpaxpercent;

	private int totalfare;
	private int totalfms;

	private int totalelasticityceiling;
	private int totalelasticityfloor;

	private float totalrasmytd;
	private float totalrasmvlyr;

	private float totalbasefare;
	private float totalYeild;
	private float totalYeildVLYR;
	private float totalAverageFare;
	private float totalComp1ShareYTD;
	private float totalComp2ShareYTD;
	private float totalComp3ShareYTD;

	public float getTotalComp1ShareYTD() {
		return totalComp1ShareYTD;
	}

	public void setTotalComp1ShareYTD(float totalComp1ShareYTD) {
		this.totalComp1ShareYTD = totalComp1ShareYTD;
	}

	public float getTotalComp2ShareYTD() {
		return totalComp2ShareYTD;
	}

	public void setTotalComp2ShareYTD(float totalComp2ShareYTD) {
		this.totalComp2ShareYTD = totalComp2ShareYTD;
	}

	public float getTotalComp3ShareYTD() {
		return totalComp3ShareYTD;
	}

	public void setTotalComp3ShareYTD(float totalComp3ShareYTD) {
		this.totalComp3ShareYTD = totalComp3ShareYTD;
	}

	public float getTotalAverageFare() {
		return totalAverageFare;
	}

	public void setTotalAverageFare(float totalAverageFare) {
		this.totalAverageFare = totalAverageFare;
	}

	public float getTotalYeild() {
		return totalYeild;
	}

	public void setTotalYeild(float totalYeild) {
		this.totalYeild = totalYeild;
	}

	public float getTotalYeildVLYR() {
		return totalYeildVLYR;
	}

	public void setTotalYeildVLYR(float totalYeildVLYR) {
		this.totalYeildVLYR = totalYeildVLYR;
	}

	private List lcompetitorTotals = new ArrayList();

	private int totalekfair;
	private int totalqrfare;
	private int totallhfare;
	private int totalekfairbasis;
	private int totalqrfarebasis;
	private int totallhfarebasis;

	private int totaladultpax;
	private int totalchildpax;
	private int totalinfpax;

	private int totalbsf;
	private int totaldelta;
	private int totalclassyield;

	private float totalcustomersegment;

	private Double totalAvgRateRealisation;

	public Double getTotalAvgRateRealisation() {
		return totalAvgRateRealisation;
	}

	public void setTotalAvgRateRealisation(Double totalAvgRateRealisation) {
		this.totalAvgRateRealisation = totalAvgRateRealisation;
	}

	public float getTotalseatfactorYTD() {
		return totalseatfactorYTD;
	}

	public void setTotalseatfactorYTD(float totalseatfactorYTD) {
		this.totalseatfactorYTD = totalseatfactorYTD;
	}

	public float getTotalseatfactorVLYR() {
		return totalseatfactorVLYR;
	}

	public void setTotalseatfactorVLYR(float totalseatfactorVLYR) {
		this.totalseatfactorVLYR = totalseatfactorVLYR;
	}

	public float getTotalseatfactorVTGT() {
		return totalseatfactorVTGT;
	}

	public void setTotalseatfactorVTGT(float totalseatfactorVTGT) {
		this.totalseatfactorVTGT = totalseatfactorVTGT;
	}

	public float getTotalSalesYTD() {
		return totalSalesYTD;
	}

	public void setTotalSalesYTD(float totalSalesYTD) {
		this.totalSalesYTD = totalSalesYTD;
	}

	public float getTotalFlownYTD() {
		return totalFlownYTD;
	}

	public void setTotalFlownYTD(float totalFlownYTD) {
		this.totalFlownYTD = totalFlownYTD;
	}

	public float getTotalYieldYTD() {
		return totalYieldYTD;
	}

	public void setTotalYieldYTD(float totalYieldYTD) {
		this.totalYieldYTD = totalYieldYTD;
	}

	public float getTotalYieldVTGT() {
		return totalYieldVTGT;
	}

	public void setTotalYieldVTGT(float totalYieldVTGT) {
		this.totalYieldVTGT = totalYieldVTGT;
	}

	public float getTotalYieldVLYR() {
		return totalYieldVLYR;
	}

	public void setTotalYieldVLYR(float totalYieldVLYR) {
		this.totalYieldVLYR = totalYieldVLYR;
	}

	public float getTotalFlownRevenueYTD() {
		return totalFlownRevenueYTD;
	}

	public void setTotalFlownRevenueYTD(float totalFlownRevenueYTD) {
		this.totalFlownRevenueYTD = totalFlownRevenueYTD;
	}

	public float getTotalDedicatedFare() {
		return totalDedicatedFare;
	}

	public void setTotalDedicatedFare(float totalDedicatedFare) {
		this.totalDedicatedFare = totalDedicatedFare;
	}

	public List getLcompetitorTotals() {
		return lcompetitorTotals;
	}

	public void setLcompetitorTotals(List lcompetitorTotals) {
		this.lcompetitorTotals = lcompetitorTotals;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public float getTotalcompMarketShareYTD() {
		return totalcompMarketShareYTD;
	}

	public void setTotalcompMarketShareYTD(float totalcompMarketShareYTD) {
		this.totalcompMarketShareYTD = totalcompMarketShareYTD;
	}

	public float getTotalcompMarketShareVLYR() {
		return totalcompMarketShareVLYR;
	}

	public void setTotalcompMarketShareVLYR(float totalcompMarketShareVLYR) {
		this.totalcompMarketShareVLYR = totalcompMarketShareVLYR;
	}

	public float getTotalcompMarketShareVTGT() {
		return totalcompMarketShareVTGT;
	}

	public void setTotalcompMarketShareVTGT(float totalcompMarketShareVTGT) {
		this.totalcompMarketShareVTGT = totalcompMarketShareVTGT;
	}

	public float getTotalYQ() {
		return totalYQ;
	}

	public void setTotalYQ(float totalYQ) {
		this.totalYQ = totalYQ;
	}

	public float getTotalYQVLYR() {
		return totalYQVLYR;
	}

	public void setTotalYQVLYR(float totalYQVLYR) {
		this.totalYQVLYR = totalYQVLYR;
	}

	public float getTotalAvgRateRealization() {
		return totalAvgRateRealization;
	}

	public void setTotalAvgRateRealization(float totalAvgRateRealization) {
		this.totalAvgRateRealization = totalAvgRateRealization;
	}

	public float getTotalRevenueYTD() {
		return totalRevenueYTD;
	}

	public void setTotalRevenueYTD(float totalRevenueYTD) {
		this.totalRevenueYTD = totalRevenueYTD;
	}

	public float getTotalRevenueVLYR() {
		return totalRevenueVLYR;
	}

	public void setTotalRevenueVLYR(float totalRevenueVLYR) {
		this.totalRevenueVLYR = totalRevenueVLYR;
	}

	public float getTotalRevenueVTGT() {
		return totalRevenueVTGT;
	}

	public void setTotalRevenueVTGT(float totalRevenueVTGT) {
		this.totalRevenueVTGT = totalRevenueVTGT;
	}

	public float getTotalPaxYTD() {
		return totalPaxYTD;
	}

	public void setTotalPaxYTD(float totalPaxYTD) {
		this.totalPaxYTD = totalPaxYTD;
	}

	public float getTotalPaxVLYR() {
		return totalPaxVLYR;
	}

	public void setTotalPaxVLYR(float totalPaxVLYR) {
		this.totalPaxVLYR = totalPaxVLYR;
	}

	public float getTotalPaxVTGT() {
		return totalPaxVTGT;
	}

	public void setTotalPaxVTGT(float totalPaxVTGT) {
		this.totalPaxVTGT = totalPaxVTGT;
	}

	public float getTotalMarketShareYTD() {
		return totalMarketShareYTD;
	}

	public void setTotalMarketShareYTD(float totalMarketShareYTD) {
		this.totalMarketShareYTD = totalMarketShareYTD;
	}

	public float getTotalMarketShareVLYR() {
		return totalMarketShareVLYR;
	}

	public void setTotalMarketShareVLYR(float totalMarketShareVLYR) {
		this.totalMarketShareVLYR = totalMarketShareVLYR;
	}

	public float getTotalMarketShareVTGT() {
		return totalMarketShareVTGT;
	}

	public void setTotalMarketShareVTGT(float totalMarketShareVTGT) {
		this.totalMarketShareVTGT = totalMarketShareVTGT;
	}

	public float getTotalSalesRevenueYTD() {
		return totalSalesRevenueYTD;
	}

	public void setTotalSalesRevenueYTD(float totalSalesRevenueYTD) {
		this.totalSalesRevenueYTD = totalSalesRevenueYTD;
	}

	public int getTotalfare() {
		return totalfare;
	}

	public void setTotalfare(int totalfare) {
		this.totalfare = totalfare;
	}

	public int getTotalpaxpercent() {
		return totalpaxpercent;
	}

	public void setTotalpaxpercent(int totalpaxpercent) {
		this.totalpaxpercent = totalpaxpercent;
	}

	public int getTotalfms() {
		return totalfms;
	}

	public void setTotalfms(int totalfms) {
		this.totalfms = totalfms;
	}

	public int getTotalekfair() {
		return totalekfair;
	}

	public void setTotalekfair(int totalekfair) {
		this.totalekfair = totalekfair;
	}

	public int getTotalqrfare() {
		return totalqrfare;
	}

	public void setTotalqrfare(int totalqrfare) {
		this.totalqrfare = totalqrfare;
	}

	public int getTotallhfare() {
		return totallhfare;
	}

	public void setTotallhfare(int totallhfare) {
		this.totallhfare = totallhfare;
	}

	public int getTotalekfairbasis() {
		return totalekfairbasis;
	}

	public void setTotalekfairbasis(int totalekfairbasis) {
		this.totalekfairbasis = totalekfairbasis;
	}

	public int getTotalqrfarebasis() {
		return totalqrfarebasis;
	}

	public void setTotalqrfarebasis(int totalqrfarebasis) {
		this.totalqrfarebasis = totalqrfarebasis;
	}

	public int getTotallhfarebasis() {
		return totallhfarebasis;
	}

	public void setTotallhfarebasis(int totallhfarebasis) {
		this.totallhfarebasis = totallhfarebasis;
	}

	public int getTotaladultpax() {
		return totaladultpax;
	}

	public void setTotaladultpax(int totaladultpax) {
		this.totaladultpax = totaladultpax;
	}

	public int getTotalchildpax() {
		return totalchildpax;
	}

	public void setTotalchildpax(int totalchildpax) {
		this.totalchildpax = totalchildpax;
	}

	public int getTotalinfpax() {
		return totalinfpax;
	}

	public void setTotalinfpax(int totalinfpax) {
		this.totalinfpax = totalinfpax;
	}

	public int getTotalbsf() {
		return totalbsf;
	}

	public void setTotalbsf(int totalbsf) {
		this.totalbsf = totalbsf;
	}

	public int getTotaldelta() {
		return totaldelta;
	}

	public void setTotaldelta(int totaldelta) {
		this.totaldelta = totaldelta;
	}

	public int getTotalclassyield() {
		return totalclassyield;
	}

	public void setTotalclassyield(int totalclassyield) {
		this.totalclassyield = totalclassyield;
	}

	public float getFare() {
		return fare;
	}

	public void setFare(float fare) {
		this.fare = fare;
	}

	public float getTotalcustomersegment() {
		return totalcustomersegment;
	}

	public void setTotalcustomersegment(float totalcustomersegment) {
		this.totalcustomersegment = totalcustomersegment;
	}

	public int getTotalelasticityceiling() {
		return totalelasticityceiling;
	}

	public void setTotalelasticityceiling(int totalelasticityceiling) {
		this.totalelasticityceiling = totalelasticityceiling;
	}

	public int getTotalelasticityfloor() {
		return totalelasticityfloor;
	}

	public void setTotalelasticityfloor(int totalelasticityfloor) {
		this.totalelasticityfloor = totalelasticityfloor;
	}

	public float getTotalpriceperformanceYTD() {
		return totalpriceperformanceYTD;
	}

	public void setTotalpriceperformanceYTD(float totalpriceperformanceYTD) {
		this.totalpriceperformanceYTD = totalpriceperformanceYTD;
	}

	public float getTotalpriceperformanceVLYR() {
		return totalpriceperformanceVLYR;
	}

	public void setTotalpriceperformanceVLYR(float totalpriceperformanceVLYR) {
		this.totalpriceperformanceVLYR = totalpriceperformanceVLYR;
	}

	public float getTotalpriceperformanceVTGT() {
		return totalpriceperformanceVTGT;
	}

	public void setTotalpriceperformanceVTGT(float totalpriceperformanceVTGT) {
		this.totalpriceperformanceVTGT = totalpriceperformanceVTGT;
	}

	public float getTotalpriceelasticityYTD() {
		return totalpriceelasticityYTD;
	}

	public void setTotalpriceelasticityYTD(float totalpriceelasticityYTD) {
		this.totalpriceelasticityYTD = totalpriceelasticityYTD;
	}

	public float getTotalpriceelasticityVLYR() {
		return totalpriceelasticityVLYR;
	}

	public void setTotalpriceelasticityVLYR(float totalpriceelasticityVLYR) {
		this.totalpriceelasticityVLYR = totalpriceelasticityVLYR;
	}

	public float getTotalpriceelasticityVTGT() {
		return totalpriceelasticityVTGT;
	}

	public void setTotalpriceelasticityVTGT(float totalpriceelasticityVTGT) {
		this.totalpriceelasticityVTGT = totalpriceelasticityVTGT;
	}

	public float getTotalrasmytd() {
		return totalrasmytd;
	}

	public void setTotalrasmytd(float totalrasmytd) {
		this.totalrasmytd = totalrasmytd;
	}

	public float getTotalrasmvlyr() {
		return totalrasmvlyr;
	}

	public void setTotalrasmvlyr(float totalrasmvlyr) {
		this.totalrasmvlyr = totalrasmvlyr;
	}

	public float getTotalbasefare() {
		return totalbasefare;
	}

	public void setTotalbasefare(float totalbasefare) {
		this.totalbasefare = totalbasefare;
	}

}
