package com.flynava.jupiter.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.flynava.jupiter.model.FilterModel;
import com.flynava.jupiter.model.MarketOutlookModel;
import com.flynava.jupiter.model.PriceElasticityModel;
import com.flynava.jupiter.model.RequestModel;

public class CalculationUtil {

	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STRING_LENGTH = 10;

	/**
	 * This method calculates the VLYR for any element.
	 * 
	 * @return float
	 * @author Anu Merin
	 */
	public static final float calculateVLYR(float total_year, float total_lastYear) {
		float lVLYR = 0;
		if (total_lastYear > 0) {
			lVLYR = ((total_year - total_lastYear) / total_lastYear) * 100;
		} else {
			lVLYR = 0;
		}
		return lVLYR;

	}

	/**
	 * This method calculates the VLYR for any element.
	 * 
	 * @return float
	 * @author Anu Merin
	 */
	public static final float calculateVLYR(float total_year, float total_lastYear, float capacity_year,
			float capacity_lastYear) {
		float lVLYR = 0;
		if (total_lastYear > 0) {
			lVLYR = (((total_year * (capacity_lastYear / capacity_year)) - total_lastYear) / total_lastYear) * 100;
		} else {
			lVLYR = 0;
		}
		return lVLYR;

	}

	public static final float calculateavgfare(float totalrevenue, float totalpax) {
		float avgfare = 0;
		if (totalpax > 0) {
			avgfare = (totalrevenue / totalpax);
		} else {
			avgfare = 0;
		}
		return avgfare;

	}

	/**
	 * This method calculates the VTGT for any element.
	 * 
	 * @return float
	 * @author Anu Merin
	 */

	public static final float calculateVTGTRemodelled(float pPax, float pForecastPax, float pTargetPax) {

		if (pTargetPax != 0)

			return (pPax + pForecastPax - pTargetPax) * 100 / pTargetPax;

		else

			return 0;

	}

	public static final float calculateVTGT(float total_year, float target) {
		float lVTGT = 0;
		if (target > 0) {
			lVTGT = ((total_year - target) / target) * 100;
		} else {
			lVTGT = 0;
		}

		return Math.round(lVTGT);

	}

	/**
	 * This method calculates the yield for any element.
	 * 
	 * @return float
	 * @author Afsheen
	 */
	public static final float calculateYield(float totalrevenue, float totalpax, float od_distance) {
		float lYield = 0;
		if (totalpax > 0 && od_distance > 0) {
			lYield = (totalrevenue * 100) / (totalpax * od_distance);
		} else {
			lYield = 0;
		}

		return (lYield);

	}

	public static final float calculateYieldForecast(float revenueForecast, float paxForecast, float od_distance) {
		float lYield = 0;
		if (paxForecast > 0 && od_distance > 0) {
			lYield = (revenueForecast * 100) / (paxForecast * od_distance);
		} else {
			lYield = 0;
		}

		return (lYield);

	}

	public static final StringBuilder getFilters(RequestModel mRequestModel, FilterModel mFilterModel) {
		StringBuilder lFilterKeyBuilder = new StringBuilder();

		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if (!"all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					lFilterKeyBuilder.append(mFilterModel.getRegion());
				} else {
					// Do Nothing
				}

			}

		}

		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				if (!"all".equalsIgnoreCase(mRequestModel.getCountryArray()[i])) {
					lFilterKeyBuilder.append(mFilterModel.getCountry());
				} else {
					// Do Nothing
				}
			}
		}

		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				if (!"all".equalsIgnoreCase(mRequestModel.getPosArray()[i])) {
					lFilterKeyBuilder.append(mFilterModel.getPos());
				} else {
					// Do Nothing
				}
			}
		}

		if (mRequestModel.getOdArray() != null && mRequestModel.getOdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOdArray().length; i++) {
				if (!"all".equalsIgnoreCase(mRequestModel.getOdArray()[i])) {
					lFilterKeyBuilder.append(mFilterModel.getOd());
				} else {
					// Do Nothing
				}
			}
		}

		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				if (!"all".equalsIgnoreCase(mRequestModel.getCompartmentArray()[i])) {
					lFilterKeyBuilder.append(mFilterModel.getCompartment());
				} else {
					// Do Nothing
				}
			}
		} else {
			// lFilterKeyBuilder.append(mFilterModel.getCompartment());
		}

		if (mRequestModel.getRbdArray() != null && mRequestModel.getRbdArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRbdArray().length; i++) {
				if (!mRequestModel.getRbdArray()[i].equalsIgnoreCase("all")) {
					lFilterKeyBuilder.append(mFilterModel.getRbd());
				} else {
					// Do Nothing
				}
			}
		}

		if (mRequestModel.getCurrency() != null && !mRequestModel.getCurrency().equalsIgnoreCase("")) {
			lFilterKeyBuilder.append(mFilterModel.getCurrency());
		}
		if (!(lFilterKeyBuilder.length() > 0)) {
			lFilterKeyBuilder.append("null");
		}

		return lFilterKeyBuilder;
	}

	/**
	 * This method generates random string
	 * 
	 * @return
	 */
	public static final String generateRandomString() {

		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	/**
	 * This method generates random numbers
	 * 
	 * @return int
	 */
	private static final int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

	/**
	 * This generic method sorts map based on value
	 * 
	 * @return Map
	 * @author surya
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				return -(e1.getValue()).compareTo(e2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	/**
	 * This generic method sorts map based on value
	 * 
	 * @return Map
	 * @author surya
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> getTopNValuesOfMap(Map<K, V> map, int n) {
		List<Map.Entry<K, V>> list = new LinkedList<Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				return -(e1.getValue()).compareTo(e2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (int i = 0; i < n; i++) {
			result.put(list.get(i).getKey(), list.get(i).getValue());
		}

		return result;
	}

	/**
	 * Round to certain number of decimals
	 * 
	 * @param number
	 * @param scale
	 * @return
	 * @author Anu Merin
	 */
	public static float round(float number, int scale) {
		int pow = 10;
		for (int i = 1; i < scale; i++)
			pow *= 10;
		float tmp = number * pow;
		float value = (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
		return Math.round(value);
	}

	public static float roundToTwoDecimal(float number, int scale) {
		int pow = 10;
		for (int i = 1; i < scale; i++)
			pow *= 10;
		float tmp = number * pow;
		float value = (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
		return (value);
	}

	public static final StringBuilder getFilterKeyForMarketOutlook(RequestModel mRequestModel,
			MarketOutlookModel mFilterModel) {
		StringBuilder lFilterKeyBuilder = new StringBuilder();

		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				if (!"all".equalsIgnoreCase(mRequestModel.getRegionArray()[i])) {
					lFilterKeyBuilder.append(mFilterModel.getRegion());
				} else {
					// Do Nothing
				}

			}

		}

		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				if (!"all".equalsIgnoreCase(mRequestModel.getCountryArray()[i])) {
					lFilterKeyBuilder.append(mFilterModel.getCountry());
				} else {
					// Do Nothing
				}
			}
		}

		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				if (!"all".equalsIgnoreCase(mRequestModel.getPosArray()[i])) {
					lFilterKeyBuilder.append(mFilterModel.getPos());
				} else {
					// Do Nothing
				}
			}
		}

		/*
		 * if (mRequestModel.getOdArray() != null &&
		 * mRequestModel.getOdArray().length > 0) { for (int i = 0; i <
		 * mRequestModel.getOdArray().length; i++) { if
		 * (!"all".equalsIgnoreCase(mRequestModel.getOdArray()[i])) {
		 * lFilterKeyBuilder.append(mFilterModel.getOd()); } else { //Do Nothing
		 * } } }
		 */

		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				if (!"all".equalsIgnoreCase(mRequestModel.getCompartmentArray()[i])) {
					lFilterKeyBuilder.append(mFilterModel.getCompartment());
				} else {
					// Do Nothing
				}
			}
		} else {
			// lFilterKeyBuilder.append(mFilterModel.getCompartment());
		}

		/*
		 * if (mRequestModel.getRbdArray() != null &&
		 * mRequestModel.getRbdArray().length > 0) { for (int i = 0; i <
		 * mRequestModel.getRbdArray().length; i++) { if
		 * (!mRequestModel.getRbdArray()[i].equalsIgnoreCase("all")) {
		 * lFilterKeyBuilder.append(mFilterModel.getRbd()); } else { // Do
		 * Nothing } } }
		 */

		/*
		 * if (mRequestModel.getCurrency() != null &&
		 * !mRequestModel.getCurrency().equalsIgnoreCase("")) {
		 * lFilterKeyBuilder.append(mFilterModel.getCurrency()); }
		 */
		if (!(lFilterKeyBuilder.length() > 0)) {
			lFilterKeyBuilder.append("null");
		}

		return lFilterKeyBuilder;
	}

	public static double logOfBase(int num) {
		return Math.log(num);
	}

	public static float calculatePriceElasticity(List<PriceElasticityModel> mList, String filterKey) {
		double priceElasticity = 0;
		// Formula for Price Elasticity
		// x=Pax, y=aircharge
		// P.E= c-d/a-b
		// where a=n*(x1y1+x2y2.....+xnyn),b=(x1+x2....+xn)*(y1+y2....+yn)
		// c=n*(sq(x1)+sq(x2)+....+sq(xn)), d= sq(x1+x2+....xn)

		List<String> airChargesList = new ArrayList<String>();

		List<String> paxList = new ArrayList<String>();

		if (mList.size() > 0) {
			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i).getFilterKey().equalsIgnoreCase(filterKey)) {
					airChargesList.add(mList.get(i).getAirCharge());
					paxList.add(mList.get(i).getHostPax());
				}
			}
		}

		int airChargesListSize = airChargesList.size();
		int paxListSize = paxList.size();

		if (airChargesListSize == paxListSize && (airChargesListSize > 1 && paxListSize > 1)) {
			int n = airChargesListSize;
			float sumXY = 0;
			float sumX = 0;
			float sumY = 0;
			float sumXsquare = 0;
			float a = 0;
			float b = 0;
			float c = 0;
			float d = 0;
			for (int j = 0; j < paxList.size(); j++) {
				sumXY += (Float.parseFloat(paxList.get(j)) * (Float.parseFloat(airChargesList.get(j))));
				sumX += Float.parseFloat(paxList.get(j));
				sumY += Float.parseFloat(airChargesList.get(j));
				sumXsquare += Math.pow(Float.parseFloat(paxList.get(j)), 2);
			}
			a = n * sumXY;
			b = sumX * sumY;
			c = n * sumXsquare;
			d = (float) Math.pow(sumX, 2);
			priceElasticity = (c - d) / (a - b);
		}

		return (float) priceElasticity;
	}

	public static float calculatePricePerformance(float mRevenueVTGT, float mMarketShareVTGT) {
		float lWeightagelastyr = 60;// TODO from config screen
		float lWeightagesecondlastyr = 40; // TODO from config screen

		float marketShareVTGT = 0;

		float lRevenue_weightage = lWeightagelastyr / (lWeightagelastyr + lWeightagesecondlastyr); // weightage
		// given
		// for
		// Revenue
		float lMarketShare_weightage = lWeightagesecondlastyr / (lWeightagelastyr + lWeightagesecondlastyr);// weightage
		// given
		// for
		// Market
		// Share

		if (mMarketShareVTGT != 0) {
			marketShareVTGT = mMarketShareVTGT;
		} else {
			marketShareVTGT = 15;
		}

		float lPPScore = lRevenue_weightage * mRevenueVTGT + lMarketShare_weightage * marketShareVTGT;
		return lPPScore;
	}

	public static float calculateEffectiveness(float revenuevlyr, float paxvlyr) {
		float lWeightage_1 = 60;// TODO from config screen
		float lWeightage_2 = 40; // TODO from config screen

		float lRevenue_weightage = lWeightage_1 / (lWeightage_1 + lWeightage_2);
		// weightage given for Revenue
		float lPax_weightage = lWeightage_2 / (lWeightage_1 + lWeightage_2);
		// weightage given for Market Share

		float effectiveness = lRevenue_weightage * revenuevlyr + lPax_weightage * paxvlyr;
		return effectiveness;
	}

	public static int calculateTheoretcalMax(String mCompartment) {
		int theoreticalMax = 0;
		if (mCompartment.equalsIgnoreCase("Y")) {
			theoreticalMax = Constants.TYPE_OF_RBD_FOR_Y * Constants.TYPE_OF_TRIPS * Constants.TYPE_OF_FARES
					* Constants.TYPE_OF_FARE_BRANDS_FOR_Y * Constants.TYPE_OF_CHANNELS;
		} else if (mCompartment.equalsIgnoreCase("J")) {
			theoreticalMax = Constants.TYPE_OF_RBD_FOR_J * Constants.TYPE_OF_TRIPS * Constants.TYPE_OF_FARES
					* Constants.TYPE_OF_FARE_BRANDS_FOR_J * Constants.TYPE_OF_CHANNELS;
		} else if (mCompartment.equalsIgnoreCase("F") || mCompartment.equalsIgnoreCase("A")) {
			theoreticalMax = Constants.TYPE_OF_RBD_FOR_F_A * Constants.TYPE_OF_TRIPS * Constants.TYPE_OF_FARES
					* Constants.TYPE_OF_FARE_BRANDS_FOR_F_A * Constants.TYPE_OF_CHANNELS;
		} else if (mCompartment.equalsIgnoreCase("NA") || mCompartment.equalsIgnoreCase("NA")) {
			theoreticalMax = Constants.TYPE_OF_RBD_FOR_NA * Constants.TYPE_OF_TRIPS * Constants.TYPE_OF_FARES
					* Constants.TYPE_OF_FARE_BRANDS_FOR_NA * Constants.TYPE_OF_CHANNELS;
		}
		return theoreticalMax;

	}

	public static String getTempCollection() {

		String lTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String lRandomString = CalculationUtil.generateRandomString();

		return "JUP_DB_" + lTime + "_" + lRandomString;
	}

	public static ArrayList<String> getAllCompartments() {
		ArrayList<String> compartments = new ArrayList<String>();
		compartments.add("Y");
		compartments.add("J");
		compartments.add("F");

		return compartments;

	}

	public static float calculateFMS(float mHostCapacity, float mHostProductRating, List<Integer> mCapacityList,
			List<Float> mProductRatingList) {
		float lFMS = 0;
		float sum = 0;
		if (mCapacityList.size() == mProductRatingList.size()) {
			for (int i = 0; i < mCapacityList.size(); i++) {
				sum += mCapacityList.get(i) * mProductRatingList.get(i);
			}
			lFMS = ((mHostCapacity * mHostProductRating) / sum) * 100;
		}

		return lFMS;
	}

	public static float calculateFMS(float lAirlineCapcity, float lRating, float lCarriersCapacity) {

		float lCarrierFMS = 0;
		if (lCarriersCapacity != 0)
			lCarrierFMS = (lAirlineCapcity * lRating) * 100 / lCarriersCapacity;

		return lCarrierFMS;
	}

	public static float doDivision(float valueOne, float valueTwo) {

		if (valueTwo != 0)
			return valueOne / valueTwo;
		else
			return 0;

	}

	/**
	 * Round to certain number of decimals
	 * 
	 * @param d
	 * @param decimalPlace
	 * @return
	 */
	public static float roundAFloat(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

}
