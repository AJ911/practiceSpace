package com.flynava.jupiter.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flynava.jupiter.daoInterface.KpiDao;
import com.flynava.jupiter.model.BreakevenSeat;
import com.flynava.jupiter.model.FilterModel;
import com.flynava.jupiter.model.NewProduct;
import com.flynava.jupiter.model.Price;
import com.flynava.jupiter.model.PriceAvailability;
import com.flynava.jupiter.model.PriceAvailabilityTotalResponse;
import com.flynava.jupiter.model.PriceElasticity;
import com.flynava.jupiter.model.PriceElasticityModel;
import com.flynava.jupiter.model.PriceElasticityRange;
import com.flynava.jupiter.model.PriceElasticityRangeTotalResponse;
import com.flynava.jupiter.model.PriceElasticityTotalResponse;
import com.flynava.jupiter.model.PriceStability;
import com.flynava.jupiter.model.PriceStabilityTotalResponse;
import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.model.RevenueShare;
import com.flynava.jupiter.model.RevenueSplitModel;
import com.flynava.jupiter.model.RevenueSplitTotalResponse;
import com.flynava.jupiter.model.Significant;
import com.flynava.jupiter.model.SignificantNonSignificant;
import com.flynava.jupiter.model.SignificantTotalResponse;
import com.flynava.jupiter.model.TotalEffective;
import com.flynava.jupiter.model.TotalEffectiveIneffective;
import com.flynava.jupiter.model.TotaleffectiveIneffectiveTotalResponse;
import com.flynava.jupiter.model.UserProfile;
import com.flynava.jupiter.model.Yield;
import com.flynava.jupiter.model.Yieldrasm;
import com.flynava.jupiter.serviceInterface.KpiService;
import com.flynava.jupiter.util.CalculationUtil;
import com.flynava.jupiter.util.FilterUtil;
import com.flynava.jupiter.util.Utility;
import com.mongodb.DBObject;

/*
 * @author-Afsheen 
 * 
 * This Class kpiServiceImpl contains all the Service
 * level functions required in Kpi Dashboard
 */

@Service
public class KpiServiceImpl implements KpiService {

	@Autowired
	KpiDao mKpiDao;

	@Override

	public Map<String, Object> getBreakevenSeatFactor(RequestModel pRequestModel) {

		ArrayList<DBObject> lBreakevenSeatfactorObj = mKpiDao.getBreakevenSeatFactor(pRequestModel);

		JSONArray lBreakevenSeatfactorData = new JSONArray(lBreakevenSeatfactorObj);
		JSONArray lTargetYieldArray = new JSONArray();
		JSONArray lTargetSeatArray = new JSONArray();
		Map<String, Object> responseBreakevenseatfactorMap = new HashMap<String, Object>();
		List<BreakevenSeat> lBreakevenseatList = new ArrayList<BreakevenSeat>();

		try {
			if (lBreakevenSeatfactorData != null) {

				for (int i = 0; i < lBreakevenSeatfactorData.length(); i++) {
					JSONObject lYieldJSONObj = lBreakevenSeatfactorData.getJSONObject(i);
					BreakevenSeat lBreakevenSeat = new BreakevenSeat();
					System.out.println("lYieldJSONObj" + lYieldJSONObj);

					if (lYieldJSONObj.has("od") && lYieldJSONObj.get("od") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("od").toString())) {
						String od = lYieldJSONObj.get("od").toString();
						lBreakevenSeat.setOd(od);
						if (od != null) {
							String origin = od.substring(0, 3);
							lBreakevenSeat.setOrigin(origin);
							String destination = od.substring(3, 6);
							lBreakevenSeat.setDestination(destination);
						}
					}
					float revenue = 0;
					if (lYieldJSONObj.has("revenue") && lYieldJSONObj.get("revenue") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("revenue").toString())) {
						revenue = Float.parseFloat(lYieldJSONObj.get("revenue").toString());
						lBreakevenSeat.setRevenue(CalculationUtil.roundToTwoDecimal(revenue, 2));
					}
					float revenue1 = 0;
					if (lYieldJSONObj.has("revenue_1") && lYieldJSONObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("revenue_1").toString())) {
						revenue1 = Float.parseFloat(lYieldJSONObj.get("revenue_1").toString());
						lBreakevenSeat.setRevenue1(revenue1);
					}
					float pax = 0;
					if (lYieldJSONObj.has("pax") && lYieldJSONObj.get("pax") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("pax").toString())) {
						pax = Float.parseFloat(lYieldJSONObj.get("pax").toString());
						lBreakevenSeat.setPax(pax);
					}
					float pax1 = 0;
					if (lYieldJSONObj.has("pax_1") && lYieldJSONObj.get("pax_1") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("pax_1").toString())) {
						pax1 = Float.parseFloat(lYieldJSONObj.get("pax_1").toString());
						lBreakevenSeat.setPax1(pax1);
					}

					float seatfactor = 0;
					if (lYieldJSONObj.has("Seat_Factor") && lYieldJSONObj.get("Seat_Factor") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("Seat_Factor").toString())) {
						seatfactor = Float.parseFloat(lYieldJSONObj.get("Seat_Factor").toString());
						lBreakevenSeat.setSeat_Factor(seatfactor);
					}
					/*
					 * float yield = 0; if (lYieldJSONObj.has("Yield") &&
					 * lYieldJSONObj.get("Yield") != null &&
					 * !"null".equalsIgnoreCase(lYieldJSONObj.get("Yield").
					 * toString())) { yield =
					 * Float.parseFloat(lYieldJSONObj.get("Yield").toString());
					 * lBreakevenSeat.setYield(CalculationUtil.roundAFloat(
					 * yield, 1)); }
					 */
					float bsf = 0;
					if (lYieldJSONObj.has("bsf") && lYieldJSONObj.get("bsf") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("bsf").toString())) {
						bsf = Float.parseFloat(lYieldJSONObj.get("bsf").toString());
						lBreakevenSeat.setBsf(bsf);
					}

					float rpkm = 0;
					if (lYieldJSONObj.has("rpkm") && lYieldJSONObj.get("rpkm") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("rpkm").toString())) {
						rpkm = Float.parseFloat(lYieldJSONObj.get("rpkm").toString());
						lBreakevenSeat.setRpkm(rpkm);
					}
					float rpkm1 = 0;
					if (lYieldJSONObj.has("rpkm_1") && lYieldJSONObj.get("rpkm_1") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("rpkm_1").toString())) {
						rpkm1 = Float.parseFloat(lYieldJSONObj.get("rpkm_1").toString());
						lBreakevenSeat.setRpkm1(rpkm1);
					}
					/*
					 * float yield1 = 0; if (lYieldJSONObj.has("Yield_1") &&
					 * lYieldJSONObj.get("Yield_1") != null &&
					 * !"null".equalsIgnoreCase(lYieldJSONObj.get("Yield_1").
					 * toString())) { yield1 =
					 * Float.parseFloat(lYieldJSONObj.get("Yield_1").toString())
					 * ; lBreakevenSeat.setYield_1(yield1); }
					 */
					float seatfactor1 = 0;
					if (lYieldJSONObj.has("Seat_Factor_1") && lYieldJSONObj.get("Seat_Factor_1") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("Seat_Factor_1").toString())) {
						seatfactor1 = Float.parseFloat(lYieldJSONObj.get("Seat_Factor_1").toString());
						lBreakevenSeat.setSeat_factor_1(seatfactor1);
					}
					double targetyield = 0;
					if (lYieldJSONObj.has("yield_target") && lYieldJSONObj.get("yield_target") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("yield_target").toString())
							&& !"[]".equalsIgnoreCase(lYieldJSONObj.get("yield_target").toString())) {

						lTargetYieldArray = new JSONArray(lYieldJSONObj.get("yield_target").toString());
						if (lTargetYieldArray != null) {
							if (lTargetYieldArray.length() > 0) {

								for (int j = 0; j < lTargetYieldArray.length(); j++) {
									if (lTargetYieldArray != null
											&& !"null".equalsIgnoreCase(lTargetYieldArray.get(j).toString())) {

										JSONArray yieldarray = (new JSONArray(lTargetYieldArray.get(j).toString()));
										if (yieldarray.length() > 0) {
											if (yieldarray != null
													&& !"null".equalsIgnoreCase(yieldarray.get(0).toString())) {
												targetyield = Utility.findSum(yieldarray);
											}
										}

									}
								}

							}
						}
					}

					if (lYieldJSONObj.has("seat_factor_target") && lYieldJSONObj.get("seat_factor_target") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("seat_factor_target").toString())
							&& !"[]".equalsIgnoreCase(lYieldJSONObj.get("seat_factor_target").toString())) {
						lTargetSeatArray = new JSONArray(lYieldJSONObj.get("seat_factor_target").toString());

					}

					String compartment = "_";
					lBreakevenSeat.setCompartment(compartment);

					float avgfare = 0;
					avgfare = CalculationUtil.calculateavgfare(revenue, pax);
					lBreakevenSeat.setAvgfare((avgfare));

					float classyieldratio = 0;
					lBreakevenSeat.setClassyieldratio(CalculationUtil.roundAFloat(classyieldratio, 2));
					float yield = ((revenue * 100) / rpkm);
					if (yield > 0) {
						lBreakevenSeat.setYield(yield);
					} else {
						lBreakevenSeat.setYield(0);
					}
					float yield1 = ((revenue1 * 100) / rpkm1);
					if (yield1 > 0) {
						lBreakevenSeat.setYield_1(yield1);
					} else {
						lBreakevenSeat.setYield_1(0);
					}
					float yieldvlyr = CalculationUtil.calculateVLYR(yield, yield1);
					lBreakevenSeat.setYieldvlyr(CalculationUtil.roundAFloat(yieldvlyr, 2));

					float seatfactorvlyr = CalculationUtil.calculateVLYR(seatfactor, seatfactor1);
					lBreakevenSeat.setSeatfactor1vlyr(CalculationUtil.roundAFloat(seatfactorvlyr, 2));

					float delta = CalculationUtil.calculateVLYR(seatfactor, bsf);
					lBreakevenSeat.setDelta(CalculationUtil.roundAFloat(delta, 2));

					float bsfperc = bsf * 100;
					lBreakevenSeat.setBsfperc(CalculationUtil.roundAFloat(bsfperc, 2));

					float yieldvtgt = CalculationUtil.calculateVTGT(yield, (float) targetyield);
					lBreakevenSeat.setYieldvtgt(CalculationUtil.roundAFloat(yieldvtgt, 2));

					System.out.println("lBreakevenSeat" + lBreakevenSeat.getYield());
					lBreakevenseatList.add(lBreakevenSeat);

				}
				responseBreakevenseatfactorMap.put("breakevenseat", lBreakevenseatList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseBreakevenseatfactorMap;

	}

	@Override
	public Map<String, Object> getSignificantOd(RequestModel pRequestModel) {
		// It return object as a list
		JSONArray carrierArray = null;
		JSONArray lTargetMarketShareArray = null;
		JSONArray lTargetRevenueArray = null;
		JSONArray lTargetPaxArray = null;

		JSONArray market_share_paxArray = null;
		JSONArray market_share_pax_1Array = null;

		float lMarketshareYTD = 0;
		float lmarket_shareVLYR = 0;
		float lMarketshareVTGT = 0;
		float marketshare = 0;
		Map<String, Object> compMap = new HashMap<String, Object>();

		float lMarketshareVTGT1;
		float lMarketshareYTD1 = 0;
		double market_Size = 0;
		double market_Sizelastyr = 0;

		int lPaxYTD = 0;
		float lPaxVLYR = 0;

		float lPaxVTGT = 0;
		float lRevenueYTD = 0;
		float lRevenueVLYR = 0;
		float lRevenueVTGT = 0;
		float market_size = 0;
		int market_share_pax = 0;
		int market_share_paxlastyr = 0;
		int market_sizelastyr = 0;
		float totalrevenueYTD = 0;
		float totalrevenue_lastyr = 0;
		float totalRevenuetarget = 0;
		double marketsize = 0;
		double forcastpax = 0;

		float totalPaxYTD = 0;
		float totalMarketshareYTD = 0;
		float totalPaxlastyr = 0;
		float totalMarketsharelastyr = 0;
		float totalPaxtarget = 0;
		float totalmarketsharetarget = 0;
		float totalMarketshareYTD1 = 0;
		float totalMarketsharelastyr1 = 0;
		float totalmarketsharetarget1 = 0;
		int totalPax_lastyr = 0;
		int targetpax = 0;
		float lTotalforcastPax = 0;
		int targetrevenue = 0;
		float totalRevenue_lastyr = 0;
		double marketsize1 = 0;
		double forcastrevenue = 0;
		JSONArray paxarray = null;
		JSONArray revenuearray = null;
		JSONArray revenuelastyrarray = null;
		String Key = null;
		JSONArray paxlastyrarray = null;
		JSONArray revenuebasearray = null;
		JSONArray forcastrevenuearray = null;
		String carrier = null;
		float lTotalTargetRevenue = 0;
		float lTotalforcastRevenue = 0;
		float lTotaltargetPax = 0;
		JSONArray forcastpaxarray = null;
		JSONArray lRatingCarrierArray = null;
		JSONArray lCompetitiorRatingArray = null;
		JSONArray lCapacityCarrierArray = null;
		JSONArray lCapacityArray = null;
		JSONArray flownpaxlastyrarray = null;
		double flownpaxlastyr = 0;
		JSONArray flownrevenuelastyrarray = null;
		double flownrevenuelastyr = 0;
		JSONArray lCapacitylastyrArray = null;
		float lCapacitylastyr = 0;
		List<Significant> lSignificantList = new ArrayList<Significant>();
		List<SignificantNonSignificant> lSignificantNonSignificantList = new ArrayList<SignificantNonSignificant>();
		List<SignificantNonSignificant> lCompetitorDataList = new ArrayList<SignificantNonSignificant>();
		List<SignificantTotalResponse> lMTotalsList = new ArrayList<SignificantTotalResponse>();
		Map<String, Object> TotalMap1 = new HashMap<String, Object>();
		Map<String, Object> TotalMap2 = new HashMap<String, Object>();
		Map<String, Object> TotalMap3 = new HashMap<String, Object>();
		Map<String, Float> carrierPaxMap = new HashMap<String, Float>();
		Map<String, Float> capacitylastyrMap = new HashMap<String, Float>();
		Map<String, Object> responseSignificantNonSignificantMap = new HashMap<String, Object>();
		Significant lSignificant = new Significant();
		ArrayList<DBObject> lSignificantNonSignificantObj = mKpiDao.getSignificantOd(pRequestModel);
		JSONArray lSignificantNonSignificantData = new JSONArray(lSignificantNonSignificantObj);
		for (int i = 0; i < lSignificantNonSignificantData.length(); i++) {
			JSONObject lSignificantJSONObj = lSignificantNonSignificantData.getJSONObject(i);
			lSignificant = new Significant();
			System.out.println("lSignificantJSONObj" + lSignificantJSONObj);

			Key = lSignificantJSONObj.get("origin").toString() + lSignificantJSONObj.get("destination").toString();

			lSignificant.setCombinationkey(Key);
			if (lSignificantJSONObj.has("dep_date") && lSignificantJSONObj.get("dep_date") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("dep_date").toString())) {
				lSignificant.setDepartureDate(lSignificantJSONObj.get("dep_date").toString());

			}
			if (lSignificantJSONObj.has("region") && lSignificantJSONObj.get("region") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("region").toString())) {
				lSignificant.setRegion(lSignificantJSONObj.get("region").toString());

			}

			if (lSignificantJSONObj.has("country") && lSignificantJSONObj.get("country") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("country").toString())) {
				lSignificant.setCountry(lSignificantJSONObj.get("country").toString());
			}

			if (lSignificantJSONObj.has("pos") && lSignificantJSONObj.get("pos") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("pos").toString())) {
				lSignificant.setPos(lSignificantJSONObj.get("pos").toString());
			}

			if (lSignificantJSONObj.has("origin") && lSignificantJSONObj.get("origin") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("origin").toString())) {
				lSignificant.setOrigin(lSignificantJSONObj.get("origin").toString());
			}
			if (lSignificantJSONObj.has("destination") && lSignificantJSONObj.get("destination") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("destination").toString())) {
				lSignificant.setDestination(lSignificantJSONObj.get("destination").toString());
			}
			if (lSignificantJSONObj.has("compartment") && lSignificantJSONObj.get("compartment") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("compartment").toString())) {
				lSignificant.setCompartment(lSignificantJSONObj.get("compartment").toString());
			}
			if (lSignificantJSONObj.has("pax_type") && lSignificantJSONObj.get("pax_type") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("pax_type").toString())) {
				lSignificant.setPax_type(lSignificantJSONObj.get("pax_type").toString());

			}
			double pax = 0;
			if (lSignificantJSONObj.has("pax") && lSignificantJSONObj.get("pax") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("pax").toString())) {

				paxarray = new JSONArray(lSignificantJSONObj.get("pax").toString());
				if (paxarray != null) {
					if (paxarray.length() > 0) {
						pax = Utility.findSum(paxarray);
						lSignificant.setPax((int) pax);

					}
				}
			}
			JSONArray lFlownPaxArray = null;
			double flown = 0;
			if (lSignificantJSONObj.has("flown_pax") && lSignificantJSONObj.get("flown_pax") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("flown_pax").toString())
					&& !"[]".equalsIgnoreCase(lSignificantJSONObj.get("flown_pax").toString())) {
				lFlownPaxArray = new JSONArray(lSignificantJSONObj.get("flown_pax").toString());
				if (lFlownPaxArray != null) {
					if (lFlownPaxArray.length() > 0) {
						for (int j = 0; j < lFlownPaxArray.length(); j++) {
							if (lFlownPaxArray != null && !lFlownPaxArray.get(j).toString().equalsIgnoreCase("null")
									&& !lFlownPaxArray.get(j).toString().equalsIgnoreCase("[]")) {
								// JSONObject flownarray = new
								// JSONObject(lFlownPaxArray.getJSONObject(j).toString());
								flown = Utility.findSum(lFlownPaxArray);
								lSignificant.setFlownpax((float) flown);
							}
						}
					}
				}
			}

			JSONArray lFlownRevenueArray = null;
			double flownrevenue = 0;
			if (lSignificantJSONObj.has("flown_revenue") && lSignificantJSONObj.get("flown_revenue") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("flown_revenue").toString())
					&& !"[]".equalsIgnoreCase(lSignificantJSONObj.get("flown_revenue").toString())) {
				lFlownRevenueArray = new JSONArray(lSignificantJSONObj.get("flown_revenue").toString());
				if (lFlownRevenueArray != null) {
					if (lFlownRevenueArray.length() > 0) {
						for (int j = 0; j < lFlownRevenueArray.length(); j++) {
							if (lFlownRevenueArray != null
									&& !lFlownRevenueArray.get(j).toString().equalsIgnoreCase("null")
									&& !lFlownRevenueArray.get(j).toString().equalsIgnoreCase("[]")) {
								flownrevenue = Utility.findSum(lFlownRevenueArray);
								lSignificant.setFlownrevenue((float) flownrevenue);
							}
						}
					}
				}
			}
			if (lSignificantJSONObj.has("forecast_revenue") && lSignificantJSONObj.get("forecast_revenue") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("forecast_revenue").toString())) {

				forcastrevenuearray = new JSONArray(lSignificantJSONObj.get("forecast_revenue").toString());
				if (forcastrevenuearray != null) {
					if (forcastrevenuearray.length() > 0) {
						forcastrevenue = Utility.findSum(forcastrevenuearray);
						lSignificant.setForcastrevenue((float) (forcastrevenue));
					}
				}

			}
			if (forcastrevenuearray != null) {
				for (int m = 0; m < forcastrevenuearray.length(); m++) {
					if (!"null".equalsIgnoreCase(forcastrevenuearray.get(m).toString()))
						lTotalforcastRevenue += Float.parseFloat(forcastrevenuearray.get(m).toString());
				}
			}
			if (lSignificantJSONObj.has("forecast_pax") && lSignificantJSONObj.get("forecast_pax") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("forecast_pax").toString())) {

				forcastpaxarray = new JSONArray(lSignificantJSONObj.get("forecast_pax").toString());
				if (forcastpaxarray != null) {
					if (forcastpaxarray.length() > 0) {
						forcastpax = Utility.findSum(forcastpaxarray);
						lSignificant.setForcastpax((float) (forcastpax));
					}
				}

			}
			if (forcastpaxarray != null) {
				for (int m = 0; m < forcastpaxarray.length(); m++) {
					if (!"null".equalsIgnoreCase(forcastpaxarray.get(m).toString()))
						lTotalforcastPax += Float.parseFloat(forcastpaxarray.get(m).toString());
				}
			}
			double revenue = 0;
			if (lSignificantJSONObj.has("revenue") && lSignificantJSONObj.get("revenue") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("revenue").toString())) {

				revenuearray = new JSONArray(lSignificantJSONObj.get("revenue").toString());
				if (revenuearray != null) {
					if (revenuearray.length() > 0) {
						revenue = Utility.findSum(revenuearray);
						lSignificant.setRevenue((int) revenue);

					}
				}
			}

			double revenuebase = 0;
			if (lSignificantJSONObj.has("revenue_base") && lSignificantJSONObj.get("revenue_base") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("revenue_base").toString())) {

				revenuebasearray = new JSONArray(lSignificantJSONObj.get("revenue_base").toString());
				if (revenuebasearray != null) {
					if (revenuebasearray.length() > 0) {
						revenuebase = Utility.findSum(revenuebasearray);
						lSignificant.setRevenue_base((float) revenuebase);
					}
				}
			}

			if (lSignificantJSONObj.has("carrier") && lSignificantJSONObj.get("carrier") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("carrier").toString())) {

				carrierArray = new JSONArray(lSignificantJSONObj.get("carrier").toString());
				ArrayList<String> carrierList = new ArrayList<String>();
				for (int j = 0; j < carrierArray.length(); j++) {
					carrierList.add(carrierArray.get(j).toString());
					lSignificant.setlCarrier(carrierList);
				}
			}

			if (lSignificantJSONObj.has("market_share_pax") && lSignificantJSONObj.get("market_share_pax") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("market_share_pax").toString())
					&& !"[]".equalsIgnoreCase(lSignificantJSONObj.get("market_share_pax").toString())) {

				market_share_paxArray = new JSONArray(lSignificantJSONObj.get("market_share_pax").toString());

				if (market_share_paxArray != null && !"null".equalsIgnoreCase(market_share_paxArray.get(0).toString())
						&& !"[]".equalsIgnoreCase(market_share_paxArray.get(0).toString())) {

					lSignificant.setMarket_share_pax(Integer.parseInt(market_share_paxArray.get(0).toString()));

				} else {
					lSignificant.setMarket_share_pax(0);
				}
			}

			JSONArray market_sizeArray = null;
			if (lSignificantJSONObj.has("market_size") && lSignificantJSONObj.get("market_size") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("market_size").toString())
					&& !"[]".equalsIgnoreCase(lSignificantJSONObj.get("market_size").toString())) {

				market_sizeArray = new JSONArray(lSignificantJSONObj.get("market_size").toString());
				if (market_sizeArray != null && market_sizeArray.length() != 0) {

					lSignificant.setMarket_size(Integer.parseInt(market_sizeArray.get(0).toString()));
				} else {
					lSignificant.setMarket_size(0);
				}
			}
			if (lSignificantJSONObj.has("flown_pax_1") && lSignificantJSONObj.get("flown_pax_1") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("flown_pax_1").toString())) {
				flownpaxlastyrarray = new JSONArray(lSignificantJSONObj.get("flown_pax_1").toString());
				if (flownpaxlastyrarray != null) {
					if (flownpaxlastyrarray.length() > 0) {
						flownpaxlastyr = Utility.findSum(flownpaxlastyrarray);
						lSignificant.setFlownpaxlastyr((float) flownpaxlastyr);
					}
				}
			}
			if (lSignificantJSONObj.has("flown_revenue_1") && lSignificantJSONObj.get("flown_revenue_1") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("flown_revenue_1").toString())) {
				flownrevenuelastyrarray = new JSONArray(lSignificantJSONObj.get("flown_revenue_1").toString());
				if (flownrevenuelastyrarray != null) {
					if (flownrevenuelastyrarray.length() > 0) {
						flownrevenuelastyr = Utility.findSum(flownrevenuelastyrarray);
						lSignificant.setFlownrevenue_lastyr((float) flownrevenuelastyr);
					}
				}
			}

			double paxlastyr = 0;
			if (lSignificantJSONObj.has("pax_1") && lSignificantJSONObj.get("pax_1") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("pax_1").toString())) {
				paxlastyrarray = new JSONArray(lSignificantJSONObj.get("pax_1").toString());
				if (paxlastyrarray != null) {
					if (paxarray.length() > 0) {
						paxlastyr = Utility.findSum(paxlastyrarray);
						lSignificant.setPax_1((int) paxlastyr);

					}
				}
			}
			double revenuelastyr = 0;
			if (lSignificantJSONObj.has("revenue_1") && lSignificantJSONObj.get("revenue_1") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("revenue_1").toString())) {
				revenuelastyrarray = new JSONArray(lSignificantJSONObj.get("revenue_1").toString());
				if (revenuelastyrarray != null) {
					if (revenuelastyrarray.length() > 0) {
						revenuelastyr = Utility.findSum(revenuelastyrarray);
						lSignificant.setRevenue_1((int) revenuelastyr);

					}
				}
			}

			if (lSignificantJSONObj.has("market_share_pax_1") && lSignificantJSONObj.get("market_share_pax_1") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("market_share_pax_1").toString())
					&& !"[]".equalsIgnoreCase(lSignificantJSONObj.get("market_share_pax_1").toString())) {
				market_share_pax_1Array = new JSONArray(lSignificantJSONObj.get("market_share_pax_1").toString());
				if (market_share_pax_1Array != null
						&& !"null".equalsIgnoreCase(market_share_pax_1Array.get(0).toString())) {
					lSignificant.setMarket_share_pax_1(Integer.parseInt((market_share_pax_1Array.get(0).toString())));
				} else {
					lSignificant.setMarket_share_pax_1(0);
				}
			}

			JSONArray market_size_1Array = null;
			if (lSignificantJSONObj.has("market_size_1") && lSignificantJSONObj.get("market_size_1") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("market_size_1").toString())
					&& !"[]".equalsIgnoreCase(lSignificantJSONObj.get("market_size_1").toString())) {
				market_size_1Array = new JSONArray(lSignificantJSONObj.get("market_size_1").toString());
				if (market_size_1Array != null && !"null".equalsIgnoreCase(market_size_1Array.get(0).toString())) {
					lSignificant.setMarket_size_1(Integer.parseInt(market_size_1Array.get(0).toString()));
				} else {
					lSignificant.setMarket_size_1(0);
				}
			}
			if (lSignificantJSONObj.has("target_market_share") && lSignificantJSONObj.get("target_market_share") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("target_market_share").toString())
					&& !"[]".equalsIgnoreCase(lSignificantJSONObj.get("target_market_share").toString())) {
				lTargetMarketShareArray = new JSONArray(lSignificantJSONObj.get("target_market_share").toString());
			}

			if (lSignificantJSONObj.has("target_revenue") && lSignificantJSONObj.get("target_revenue") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("target_revenue").toString())
					&& !"[]".equalsIgnoreCase(lSignificantJSONObj.get("target_revenue").toString())) {
				lTargetRevenueArray = new JSONArray(lSignificantJSONObj.get("target_revenue").toString());
			}
			if (lTargetRevenueArray != null) {
				for (int m = 0; m < lTargetRevenueArray.length(); m++) {
					if (!"null".equalsIgnoreCase(lTargetRevenueArray.get(m).toString()))
						lTotalTargetRevenue += Float.parseFloat(lTargetRevenueArray.get(m).toString());
				}
			}
			if (lSignificantJSONObj.has("target_pax") && lSignificantJSONObj.get("target_pax") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("target_pax").toString())
					&& !"[]".equalsIgnoreCase(lSignificantJSONObj.get("target_pax").toString())) {
				lTargetPaxArray = new JSONArray(lSignificantJSONObj.get("target_pax").toString());
			}
			if (lTargetPaxArray != null) {
				for (int m = 0; m < lTargetPaxArray.length(); m++) {
					if (!"null".equalsIgnoreCase(lTargetPaxArray.get(m).toString()))
						lTotaltargetPax += Float.parseFloat(lTargetPaxArray.get(m).toString());
				}
			}
			// months and days
			int lMonth = 0;
			int lDays = 0;
			if (!"null".equalsIgnoreCase(lSignificantJSONObj.get("dep_date").toString())) {
				lMonth = Utility.findMonth(lSignificantJSONObj.get("dep_date").toString());
				lDays = Utility.findDay(lSignificantJSONObj.get("dep_date").toString());
				lSignificant.setMonths(lMonth);
				lSignificant.setDays(lDays);
			}
			// Rating Carrier
			if (lSignificantJSONObj.has("rating_carrier") && lSignificantJSONObj.get("rating_carrier") != null
					&& !lSignificantJSONObj.get("rating_carrier").toString().equalsIgnoreCase("null")
					&& !lSignificantJSONObj.get("rating_carrier").toString().equalsIgnoreCase("[]")) {
				lRatingCarrierArray = new JSONArray(lSignificantJSONObj.get("rating_carrier").toString());
			} else {
				lRatingCarrierArray = null;
			}

			if (lSignificantJSONObj.has("rating") && lSignificantJSONObj.get("rating") != null
					&& !lSignificantJSONObj.get("rating").toString().equalsIgnoreCase("null")
					&& !lSignificantJSONObj.get("rating").toString().equalsIgnoreCase("[]")) {
				lCompetitiorRatingArray = new JSONArray(lSignificantJSONObj.get("rating").toString());
			} else {
				lCompetitiorRatingArray = null;
			}
			// Capacity Carrier
			double lCapacityCarrier = 0;
			if (lSignificantJSONObj.has("capacity_airline") && lSignificantJSONObj.get("capacity_airline") != null
					&& !lSignificantJSONObj.get("capacity_airline").toString().equalsIgnoreCase("null")
					&& !lSignificantJSONObj.get("capacity_airline").toString().equalsIgnoreCase("[]")) {
				lCapacityCarrierArray = new JSONArray(lSignificantJSONObj.get("capacity_airline").toString());
			} else {
				lCapacityCarrierArray = null;
			}
			if (lSignificantJSONObj.has("capacity_1") && lSignificantJSONObj.get("capacity_1") != null
					&& !"null".equalsIgnoreCase(lSignificantJSONObj.get("capacity_1").toString())
					&& !"[]".equalsIgnoreCase(lSignificantJSONObj.get("capacity_1").toString())) {
				lCapacitylastyrArray = new JSONArray(lSignificantJSONObj.get("capacity_1").toString());
			}
			if (lCapacitylastyrArray != null) {
				for (int m = 0; m < lCapacitylastyrArray.length(); m++) {
					if (!"null".equalsIgnoreCase(lCapacitylastyrArray.get(m).toString()))
						lCapacitylastyr += Float.parseFloat(lCapacitylastyrArray.get(m).toString());
				}
			}

			// Capacity
			double lCapacity = 0;
			if (lSignificantJSONObj.has("capacity") && lSignificantJSONObj.get("capacity") != null
					&& !lSignificantJSONObj.get("capacity").toString().equalsIgnoreCase("null")
					&& !lSignificantJSONObj.get("capacity").toString().equalsIgnoreCase("[]")) {
				lCapacityArray = new JSONArray(lSignificantJSONObj.get("capacity").toString());
			} else {
				lCapacityArray = null;
			}
			if (lCapacityCarrierArray != null) {
				for (int k = 0; k < lCapacityCarrierArray.length(); k++) {
					carrierPaxMap.put(lCapacityCarrierArray.getString(k), (float) lCapacityArray.getDouble(k));
				}
				for (int k = 0; k < lCapacityCarrierArray.length(); k++) {
					capacitylastyrMap.put(lCapacityCarrierArray.getString(k),
							(float) lCapacitylastyrArray.getDouble(k));
				}
			}

			double capacityFZ = 0;
			double capacityComp1 = 0;
			double capacityComp2 = 0;
			double capacityComp3 = 0;
			double capacityComp4 = 0;

			if (lCapacityCarrierArray != null) {
				if (lCapacityCarrierArray.length() > 0) {
					for (int j = 0; j < lCapacityCarrierArray.length(); j++) {
						if (lCapacityCarrierArray.length() > 0) {
							if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("FZ")) {
								capacityFZ += Double.parseDouble(lCapacityArray.get(j).toString());
							} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("EK")) {
								capacityComp1 += Double.parseDouble(lCapacityArray.get(j).toString());
							} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("EY")) {
								capacityComp2 += Double.parseDouble(lCapacityArray.get(j).toString());
							} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("G9")) {
								capacityComp3 = Double.parseDouble(lCapacityArray.get(j).toString());
							} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("QR")) {
								capacityComp4 = Double.parseDouble(lCapacityArray.get(j).toString());
							}
						}
					}
					lSignificant.setCapacityFZ(Double.toString(capacityFZ));
					lSignificant.setCapacityComp1(Double.toString(capacityComp1));
					lSignificant.setCapacityComp2(Double.toString(capacityComp2));
					lSignificant.setCapacityComp3(Double.toString(capacityComp3));
					lSignificant.setCapacityComp4(Double.toString(capacityComp4));
				}
			} else {
				lSignificant.setCapacityFZ("-");
				lSignificant.setCapacityComp1("-");
				lSignificant.setCapacityComp2("-");
				lSignificant.setCapacityComp3("-");
				lSignificant.setCapacityComp4("-");
			}
			double compRatingFZ = 0;
			double compRatingComp1 = 0;
			double compRatingComp2 = 0;
			double compRatingComp3 = 0;
			double compRatingComp4 = 0;
			if (lCompetitiorRatingArray != null && lCompetitiorRatingArray.length() > 0) {
				for (int j = 0; j < lCompetitiorRatingArray.length(); j++) {
					if (lRatingCarrierArray.length() > 0) {
						if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("FZ")) {
							compRatingFZ = Double.parseDouble(lCompetitiorRatingArray.get(j).toString());
						} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("EK")) {
							compRatingComp1 = Double.parseDouble(lCompetitiorRatingArray.get(j).toString());
						} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("EY")) {
							compRatingComp2 = Double.parseDouble(lCompetitiorRatingArray.get(j).toString());
						} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("G9")) {
							compRatingComp3 = Double.parseDouble(lCompetitiorRatingArray.get(j).toString());
						} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("QR")) {
							compRatingComp4 = Double.parseDouble(lCompetitiorRatingArray.get(j).toString());
						}

					}
				}
				lSignificant.setCompRatingFZ(Double.toString(compRatingFZ));
				lSignificant.setCompRatingComp1(Double.toString(compRatingComp1));
				lSignificant.setCompRatingComp2(Double.toString(compRatingComp2));
				lSignificant.setCompRatingComp3(Double.toString(compRatingComp3));
				lSignificant.setCompRatingComp4(Double.toString(compRatingComp4));
			} else {
				lSignificant.setCompRatingFZ("-");
				lSignificant.setCompRatingComp1("-");
				lSignificant.setCompRatingComp2("-");
				lSignificant.setCompRatingComp3("-");
				lSignificant.setCompRatingComp4("-");
			}

			lSignificantList.add(lSignificant);
		}
		SignificantNonSignificant lSignificantNonSignificant = null;
		Map<String, SignificantNonSignificant> map = new HashMap<String, SignificantNonSignificant>();

		for (Significant lSignificantObj : lSignificantList) {
			if (!map.containsKey(lSignificantObj.getCombinationkey())) {

				lSignificantNonSignificant = new SignificantNonSignificant();
				lSignificantNonSignificant.setCombinationkey(lSignificantObj.getCombinationkey());
				lSignificantNonSignificant.setRegion(lSignificantObj.getRegion());
				lSignificantNonSignificant.setCountry(lSignificantObj.getCountry());
				lSignificantNonSignificant.setOrigin(lSignificantObj.getOrigin());
				lSignificantNonSignificant.setPos(lSignificantObj.getPos());
				lSignificantNonSignificant.setDestination(lSignificantObj.getDestination());
				lSignificantNonSignificant.setCompartment(lSignificantObj.getCompartment());
				lSignificantNonSignificant.setlCarrier(lSignificant.getlCarrier());
				lSignificantNonSignificant.setDepartureDate(lSignificantObj.getDepartureDate());
				lSignificantNonSignificant.setCapacityFZ(lSignificantObj.getCapacityFZ());
				lSignificantNonSignificant.setCapacityComp1(lSignificantObj.getCapacityComp1());
				lSignificantNonSignificant.setCapacityComp2(lSignificantObj.getCapacityComp2());
				lSignificantNonSignificant.setCapacityComp3(lSignificantObj.getCapacityComp3());
				lSignificantNonSignificant.setCapacityComp4(lSignificantObj.getCapacityComp4());

				lSignificantNonSignificant.setCompRatingFZ(lSignificantObj.getCompRatingFZ());
				lSignificantNonSignificant.setCompRatingComp1(lSignificantObj.getCompRatingComp1());
				lSignificantNonSignificant.setCompRatingComp2(lSignificantObj.getCompRatingComp2());
				lSignificantNonSignificant.setCompRatingComp3(lSignificantObj.getCompRatingComp3());
				lSignificantNonSignificant.setCompRatingComp4(lSignificantObj.getCompRatingComp4());
				// flown pax
				float flownpax = lSignificant.getFlownpax();
				lSignificantNonSignificant.setFlownpax(flownpax);
				float lflownpaxlastyr = lSignificantObj.getFlownpaxlastyr();
				lSignificantNonSignificant.setTotalflownpaxlastyr(lflownpaxlastyr);

				// flown Revenue
				float flownrevenue = lSignificant.getFlownrevenue();
				lSignificantNonSignificant.setFlownrevenue(flownrevenue);
				float lflownrevenuelastyr = lSignificantObj.getFlownrevenue_lastyr();
				lSignificantNonSignificant.setTotalFlownRevenue_lastyr(lflownrevenuelastyr);

				float totalHost_revenue = lSignificantObj.getRevenue();
				float totalHost_revenue_lastyr = lSignificantObj.getRevenue_1();
				lSignificantNonSignificant.setTotalRevenue(totalHost_revenue);
				lSignificantNonSignificant.setTotalrevenue_lastyr(totalHost_revenue_lastyr);

				int totalHost_pax = lSignificantObj.getPax();
				int totalHost_pax_lastyr = lSignificantObj.getPax_1();
				lSignificantNonSignificant.setTotalPax(totalHost_pax);
				lSignificantNonSignificant.setTotalPax_lastyr(totalHost_pax_lastyr);
				// months and days
				int lMonth = 0;
				int lDays = 0;
				if (!"null".equalsIgnoreCase(lSignificantObj.getDepartureDate().toString())) {
					lMonth = Utility.findMonth(lSignificantObj.getDepartureDate());
					lDays = Utility.findDay(lSignificantObj.getDepartureDate());
					lSignificantNonSignificant.setMonths(lMonth);
					lSignificantNonSignificant.setDays(lDays);
				}
				map.put(lSignificantObj.getCombinationkey(), lSignificantNonSignificant);
			} else {

				for (String lKey : map.keySet()) {
					if (lSignificantObj.getCombinationkey().equals(lKey)) {
						lSignificantNonSignificant = map.get(lKey);
					}
				}
				// flown Revenue
				float flownrevenue = lSignificantObj.getFlownrevenue() + lSignificantNonSignificant.getFlownrevenue();
				lSignificantNonSignificant.setFlownrevenue(flownrevenue);
				float lflownrevenuelastyr = lSignificantObj.getFlownrevenue_lastyr()
						+ lSignificantNonSignificant.getTotalFlownRevenue_lastyr();
				lSignificantNonSignificant.setTotalFlownRevenue_lastyr(lflownrevenuelastyr);
				// flown pax
				float flownpax = lSignificantObj.getFlownpax() + lSignificantNonSignificant.getFlownpax();
				lSignificantNonSignificant.setFlownpax(flownpax);
				float lflownpaxlastyr = lSignificantObj.getFlownpaxlastyr()
						+ lSignificantNonSignificant.getTotalflownpaxlastyr();
				lSignificantNonSignificant.setTotalflownpaxlastyr(lflownpaxlastyr);

				float totalHost_revenue = lSignificantObj.getRevenue() + lSignificantNonSignificant.getTotalRevenue();
				float totalHost_revenue_lastyr = lSignificantObj.getRevenue_1()
						+ lSignificantNonSignificant.getTotalrevenue_lastyr();
				lSignificantNonSignificant.setTotalRevenue(totalHost_revenue);
				lSignificantNonSignificant.setTotalrevenue_lastyr(totalHost_revenue_lastyr);

				int totalHost_pax = (Integer) lSignificantObj.getPax()
						+ (Integer) lSignificantNonSignificant.getTotalPax();
				int totalHost_pax_lastyr = lSignificantObj.getPax_1() + lSignificantNonSignificant.getTotalPax_lastyr();
				lSignificantNonSignificant.setTotalPax(totalHost_pax);
				lSignificantNonSignificant.setTotalPax_lastyr(totalHost_pax_lastyr);
				// months and days
				int lMonth = 0;
				int lDays = 0;
				if (!"null".equalsIgnoreCase(lSignificantObj.getDepartureDate().toString())) {
					lMonth = Utility.findMonth(lSignificantObj.getDepartureDate());
					lDays = Utility.findDay(lSignificantObj.getDepartureDate());
					lSignificantNonSignificant.setMonths(lMonth);
					lSignificantNonSignificant.setDays(lDays);
				}
			}

		}
		for (String key : map.keySet()) {
			lSignificantNonSignificant = new SignificantNonSignificant();
			lSignificantNonSignificant.setCombinationkey(map.get(key).getCombinationkey());
			lSignificantNonSignificant.setCountry(map.get(key).getCountry());
			lSignificantNonSignificant.setRegion(map.get(key).getRegion());
			lSignificantNonSignificant.setOrigin(map.get(key).getOrigin());
			lSignificantNonSignificant.setDestination(map.get(key).getDestination());
			lSignificantNonSignificant.setCompartment(map.get(key).getCompartment());
			lSignificantNonSignificant.setPos(map.get(key).getPos());
			lSignificantNonSignificant.setlCarrier(map.get(key).getlCarrier());
			// Capacity
			lSignificantNonSignificant.setCapacityFZ(map.get(key).getCapacityFZ());
			lSignificantNonSignificant.setCapacityComp1(map.get(key).getCapacityComp1());
			lSignificantNonSignificant.setCapacityComp2(map.get(key).getCapacityComp2());

			// CompRating
			lSignificantNonSignificant.setCompRatingFZ(map.get(key).getCompRatingFZ());
			lSignificantNonSignificant.setCompRatingComp1(map.get(key).getCompRatingComp1());
			lSignificantNonSignificant.setCompRatingComp2(map.get(key).getCompRatingComp2());

			ArrayList<Integer> lCapacityList = new ArrayList<Integer>();
			int lCapacityFZ = 1;
			if (!map.get(key).getCapacityFZ().equalsIgnoreCase("-")) {
				lCapacityFZ = (int) Float.parseFloat(map.get(key).getCapacityFZ());
				lCapacityList.add(lCapacityFZ);
			}

			if (!map.get(key).getCapacityComp1().equalsIgnoreCase("-")) {
				lCapacityList.add((int) Float.parseFloat(map.get(key).getCapacityComp1()));
			}
			if (!map.get(key).getCapacityComp2().equalsIgnoreCase("-")) {
				lCapacityList.add((int) Float.parseFloat(map.get(key).getCapacityComp2()));
			}
			if (!map.get(key).getCapacityComp3().equalsIgnoreCase("-")) {
				lCapacityList.add((int) Float.parseFloat(map.get(key).getCapacityComp3()));
			}
			if (!map.get(key).getCapacityComp4().equalsIgnoreCase("-")) {
				lCapacityList.add((int) Float.parseFloat(map.get(key).getCapacityComp4()));
			}

			ArrayList<Float> lCompRatingList = new ArrayList<Float>();
			if (!map.get(key).getCompRatingFZ().equalsIgnoreCase("-")) {
				lCompRatingList.add(Float.parseFloat(map.get(key).getCompRatingFZ()));
			} else {
				lCompRatingList.add((float) 1);
			}
			if (!map.get(key).getCompRatingComp1().equalsIgnoreCase("-")) {
				lCompRatingList.add(Float.parseFloat(map.get(key).getCompRatingComp1()));
			} else {
				lCompRatingList.add((float) 1);
			}
			if (!map.get(key).getCompRatingComp2().equalsIgnoreCase("-")) {
				lCompRatingList.add(Float.parseFloat(map.get(key).getCompRatingComp2()));
			} else {
				lCompRatingList.add((float) 1);
			}
			if (!map.get(key).getCompRatingComp3().equalsIgnoreCase("-")) {
				lCompRatingList.add(Float.parseFloat(map.get(key).getCompRatingComp3()));
			} else {
				lCompRatingList.add((float) 1);
			}
			if (!map.get(key).getCompRatingComp4().equalsIgnoreCase("-")) {
				lCompRatingList.add(Float.parseFloat(map.get(key).getCompRatingComp4()));
			} else {
				lCompRatingList.add((float) 1);
			}
			// FMS
			float lCompRatingFZ = 1;
			if (!map.get(key).getCompRatingFZ().equalsIgnoreCase("-")) {
				lCompRatingFZ = Float.parseFloat(map.get(key).getCompRatingFZ());
			}

			float lFMS = CalculationUtil.calculateFMS(lCapacityFZ, lCompRatingFZ, lCapacityList, lCompRatingList);
			if (lFMS != 0) {
				lSignificantNonSignificant.setFms(lFMS);
			} else {
				lSignificantNonSignificant.setFms(0);
			}
			float pForcastPax = 0;
			float pForcastRevenue = 0;
			int totaldaysFromdate = 0;
			Date date1 = null;
			Date date2 = null;
			if (!pRequestModel.getFromDate().isEmpty() && pRequestModel.getFromDate() != null
					&& !pRequestModel.getToDate().isEmpty() && pRequestModel.getToDate() != null) {
				date1 = Utility.convertStringToDateFromat(pRequestModel.getFromDate());
				date2 = Utility.convertStringToDateFromat(pRequestModel.getToDate());
			} else {
				date1 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
				date2 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
			}
			// int result = str2.compareTo(str1);
			int diff = (int) Utility.getDifferenceDays(date1, date2);
			int result = diff + 1;
			if (result == 0) {
				totaldaysFromdate = result + 1;
			}
			// no. of days
			int noOfDays = Utility.numberOfDaysInMonth(
					Utility.findMonth(map.get(key).getDepartureDate().toString()) - 1,
					Utility.findYear(map.get(key).getDepartureDate().toString()));
			float targetProratedPax = ((lTotaltargetPax / (float) noOfDays) * result);
			float proratedForcastPax = (((float) forcastpax / (float) noOfDays) * result);
			float targetProratedRevenue = ((lTotalTargetRevenue / noOfDays) * result);
			float proratedForcastRevenue = (((float) forcastrevenue / (float) noOfDays) * result);

			// months and days
			int lMonth = 0;
			int lDays = 0;
			if (!"null".equalsIgnoreCase(map.get(key).getDepartureDate().toString())) {
				lMonth = Utility.findMonth(map.get(key).getDepartureDate());
				lDays = Utility.findDay(map.get(key).getDepartureDate());
				lSignificantNonSignificant.setMonths(lMonth);
				lSignificantNonSignificant.setDays(lDays);
			}
			if (lSignificantNonSignificant.getMonths() == map.get(key).getMonths()) {
				float lTotaltargetProratedPax = targetProratedPax;
				float lTotalproratedForcastPax = proratedForcastPax;
				lSignificantNonSignificant.setTotalforcastpax(lTotalproratedForcastPax);
				lSignificantNonSignificant.setTotaltargetproratedpax(lTotaltargetProratedPax);
				float lTotaltargetProratedRevenue = targetProratedRevenue;
				float lTotalproratedForcastRevenue = proratedForcastRevenue;
				lSignificantNonSignificant.setTotalforcastrevenue(lTotalproratedForcastRevenue);
				lSignificantNonSignificant.setTotaltargetproratedrevenue(lTotaltargetProratedRevenue);

			} else {
				float lTotaltargetProratedPax = targetProratedPax + lTotaltargetPax;
				float lTotalproratedForcastPax = proratedForcastPax + lTotalforcastPax;
				lSignificantNonSignificant.setTotalforcastpax(lTotalproratedForcastPax);
				lSignificantNonSignificant.setTotaltargetproratedpax(lTotaltargetProratedPax);
				float lTotaltargetProratedRevenue = targetProratedRevenue + lTotalTargetRevenue;
				float lTotalproratedForcastRevenue = proratedForcastRevenue + lTotalforcastRevenue;
				lSignificantNonSignificant.setTotalforcastrevenue(lTotalproratedForcastRevenue);
				lSignificantNonSignificant.setTotaltargetproratedrevenue(lTotaltargetProratedRevenue);

			}
			// pax vtgt
			float paxvtgt = 0;

			if (diff == 0) {
				pForcastPax = 0;
				pForcastRevenue = 0;
			} else {
				pForcastPax = lSignificantNonSignificant.getTotalforcastpax();
				pForcastRevenue = lSignificantNonSignificant.getTotalforcastrevenue();
			}
			float pflownpax = map.get(key).getFlownpax();
			lSignificantNonSignificant.setFlownpax(pflownpax);
			if (targetProratedPax != 0) {
				paxvtgt = CalculationUtil.calculateVTGTRemodelled(pflownpax, pForcastPax, targetProratedPax);
			}
			lSignificantNonSignificant.setPaxVTGT((paxvtgt));
			// revenue vtgt
			float revenuevtgt = 0;
			float pflownrevenue = map.get(key).getFlownrevenue();
			lSignificantNonSignificant.setFlownrevenue(pflownrevenue);
			if (targetProratedRevenue != 0) {
				revenuevtgt = CalculationUtil.calculateVTGTRemodelled(pflownrevenue, pForcastRevenue,
						targetProratedRevenue);
				lSignificantNonSignificant.setRevenueVTGT((revenuevtgt));
			}

			int totalHost_pax = map.get(key).getTotalPax();
			lPaxYTD = totalHost_pax;
			totalPaxYTD += lPaxYTD;
			lSignificantNonSignificant.setTotalPax((map.get(key).getTotalPax()));
			lSignificantNonSignificant.setPaxYTD((lPaxYTD));
			String host = "FZ";
			float hostcapacity = 0;

			if (carrierPaxMap.containsKey(host)) {
				hostcapacity = (carrierPaxMap.get(host));
				lSignificantNonSignificant.setHostcapacity(hostcapacity);
			}
			float hostcapacitylastyr = 0;
			if (carrierPaxMap.containsKey(host)) {
				hostcapacitylastyr = (capacitylastyrMap.get(host));
				lSignificantNonSignificant.setHostcapacitylastyr(hostcapacitylastyr);
			}
			float lFlownpaxlastyr = map.get(key).getTotalflownpaxlastyr();
			lSignificantNonSignificant.setTotalflownpaxlastyr(lFlownpaxlastyr);
			float lflownrevenuelastyr = map.get(key).getTotalFlownRevenue_lastyr();
			lSignificantNonSignificant.setTotalFlownRevenue_lastyr(lflownrevenuelastyr);

			totalPax_lastyr = map.get(key).getTotalPax_lastyr();
			lSignificantNonSignificant.setTotalPax_lastyr(totalPax_lastyr);
			if (lFlownpaxlastyr != 0) {
				lPaxVLYR = CalculationUtil.calculateVLYR(pflownpax, lFlownpaxlastyr, hostcapacity, hostcapacitylastyr);
				lSignificantNonSignificant.setPaxVLYR((lPaxVLYR));

			} else {
				lSignificantNonSignificant.setPaxVLYR((lPaxVLYR));
			}
			totalPaxlastyr += totalPax_lastyr;
			float totalHost_revenue = map.get(key).getTotalRevenue();
			lRevenueYTD = totalHost_revenue;
			totalrevenueYTD += lRevenueYTD;
			lSignificantNonSignificant.setTotalRevenue((map.get(key).getTotalRevenue()));
			lSignificantNonSignificant.setRevenueYTD((lRevenueYTD));

			totalRevenue_lastyr = map.get(key).getTotalrevenue_lastyr();
			lSignificantNonSignificant.setTotalrevenue_lastyr(totalRevenue_lastyr);
			if (lflownrevenuelastyr != 0) {
				lRevenueVLYR = CalculationUtil.calculateVLYR(pflownrevenue, lflownrevenuelastyr, hostcapacity,
						hostcapacitylastyr);
				lSignificantNonSignificant.setRevenueVLYR((lRevenueVLYR));

			} else {
				lSignificantNonSignificant.setRevenueVLYR((lRevenueVLYR));
			}

			totalrevenue_lastyr += totalRevenue_lastyr;

			for (int c = 0; c < lSignificantNonSignificant.getlCarrier().size(); c++) {

				if (lSignificantNonSignificant.getlCarrier().get(c) != null
						&& !"null".equalsIgnoreCase(lSignificantNonSignificant.getlCarrier().get(c).toString())
						&& !lSignificantNonSignificant.getlCarrier().get(c).toString().isEmpty()) {

					if (lSignificantNonSignificant.getlCarrier().get(c).toString().equals("FZ")) {
						carrier = lSignificantNonSignificant.getlCarrier().get(c).toString();
						lSignificantNonSignificant.setCarrier(carrier);
						lSignificantNonSignificant
								.setCarrier(lSignificantNonSignificant.getlCarrier().get(c).toString());
						if (market_share_paxArray != null) {
							if (!"null".equalsIgnoreCase(market_share_paxArray.get(c).toString())
									&& !"[]".equalsIgnoreCase(market_share_paxArray.get(c).toString())) {

								if (market_share_paxArray != null) {
									if (market_share_paxArray.length() > 0) {
										market_Size = Utility.findSum(market_share_paxArray);
									}
								}
								float marketshare1 = 0;
								if (market_Size > 0) {
									marketshare1 = (float) ((lSignificant.getMarket_share_pax() * 100) / market_Size);
								} else {
									marketshare1 = 0;
								}

								if (marketshare1 > 0) {
									lSignificantNonSignificant.setMarketshare(marketshare1);
								} else {

									lSignificantNonSignificant.setMarketshare(0);
								}
								lSignificantNonSignificant.setMarketShareYTD((marketshare1));
								totalMarketshareYTD += lSignificantNonSignificant.getMarketShareYTD();
								if (market_share_pax_1Array != null) {
									if (market_share_pax_1Array.length() > 0) {
										market_Sizelastyr = Utility.findSum(market_share_pax_1Array);
									}
								}
								float marketshare_lastyr = 0;
								if (market_Sizelastyr > 0) {
									marketshare_lastyr = (float) ((lSignificant.getMarket_share_pax_1() * 100)
											/ market_Sizelastyr);
								} else {
									marketshare_lastyr = 0;
								}
								if (marketshare_lastyr > 0) {
									lSignificantNonSignificant.setMarketshare_1(marketshare_lastyr);
								} else {
									lSignificantNonSignificant.setMarketshare_1(0);
								}
								float totalHost_market_share1 = lSignificantNonSignificant.getMarketshare();

								float totalHost_market_share_lastyr1 = lSignificantNonSignificant.getMarketshare_1();
								lSignificantNonSignificant.setTotalMarketshare(totalHost_market_share1);
								lSignificantNonSignificant.setTotalMarketshare_lastyr(totalHost_market_share_lastyr1);

								if (totalHost_market_share_lastyr1 != 0) {
									lmarket_shareVLYR = CalculationUtil.calculateVLYR(totalHost_market_share1,
											totalHost_market_share_lastyr1);
									lSignificantNonSignificant.setMarketShareVLYR((lmarket_shareVLYR));

								} else {
									lSignificantNonSignificant.setMarketShareVLYR((lmarket_shareVLYR));
								}
								totalMarketsharelastyr += totalHost_market_share_lastyr1;
								int targetMarketShare1 = 0;
								if (lTargetMarketShareArray != null) {
									if (lTargetMarketShareArray.length() > 0) {
										for (int i = 0; i < lTargetMarketShareArray.length(); i++) {
											targetMarketShare1 += Integer
													.parseInt(lTargetMarketShareArray.get(i).toString());
										}
									}
								} else {
									lTargetMarketShareArray = null;
								}
								lMarketshareVTGT = ((marketshare1 - lSignificantNonSignificant.getFms())
										/ lSignificantNonSignificant.getFms()) * 100;
								totalmarketsharetarget += targetMarketShare1;
								lSignificantNonSignificant.setMarketShareVTGT((lMarketshareVTGT));

							}
						}

					} else {
						SignificantNonSignificant lCompetitorData = new SignificantNonSignificant();
						carrier = lSignificantNonSignificant.getlCarrier().get(c).toString();
						lCompetitorData.setCarrier(carrier);
						lCompetitorData.setCarrier(lSignificantNonSignificant.getlCarrier().get(c).toString());
						if (market_share_paxArray != null) {
							if (market_share_paxArray.length() > 0) {
								marketsize = Utility.findSum(market_share_paxArray);
							}
						}

						float marketshare1 = 0;
						if (marketsize > 0) {
							float lmarketShare = Float.parseFloat(market_share_paxArray.get(c).toString());
							marketshare1 = (float) ((lmarketShare * 100) / marketsize);
						} else {
							marketshare1 = 0;
						}

						if (marketshare1 > 0) {

							lCompetitorData.setMarketshare(marketshare1);
						} else {
							lCompetitorData.setMarketshare(0);

						}

						lCompetitorData.setMarketShareYTD((marketshare1));
						totalMarketshareYTD1 += lCompetitorData.getMarketShareYTD();
						if (market_share_pax_1Array != null) {
							if (market_share_pax_1Array.length() > 0) {
								marketsize1 = Utility.findSum(market_share_pax_1Array);

							}
						}

						float marketshare_lastyr = 0;
						if (marketsize1 > 0) {
							float lmarketShare1 = Float.parseFloat(market_share_pax_1Array.get(c).toString());
							marketshare_lastyr = (float) ((lmarketShare1 * 100) / marketsize1);
						} else {
							marketshare_lastyr = 0;
						}

						if (marketshare_lastyr > 0) {
							lCompetitorData.setMarketshare_1(marketshare_lastyr);
						} else {

							lCompetitorData.setMarketshare_1(0);
						}

						float totalHost_market_share1 = lCompetitorData.getMarketshare();
						float totalHost_market_share_lastyr1 = lCompetitorData.getMarketshare_1();
						lCompetitorData.setTotalMarketshare(totalHost_market_share1);
						if (totalHost_market_share_lastyr1 > 0) {
							lCompetitorData.setTotalMarketshare_lastyr(totalHost_market_share_lastyr1);
						} else {
							lCompetitorData.setTotalMarketshare_lastyr(0);
						}

						if (totalHost_market_share_lastyr1 > 0) {
							lmarket_shareVLYR = CalculationUtil.calculateVLYR(totalHost_market_share1,
									totalHost_market_share_lastyr1);
						}
						if (lmarket_shareVLYR < 0) {
							lCompetitorData.setMarketShareVLYR((lmarket_shareVLYR));
						} else {
							lCompetitorData.setMarketShareVLYR(0);
						}
						totalMarketsharelastyr1 += totalHost_market_share_lastyr1;
						int targetMarketShare1 = 0;
						if (lTargetMarketShareArray != null) {
							if (lTargetMarketShareArray.length() > 0) {
								for (int i = 0; i < lTargetMarketShareArray.length(); i++) {
									targetMarketShare1 += Integer.parseInt(lTargetMarketShareArray.get(i).toString());
								}
							}
						} else {
							lTargetMarketShareArray = null;
						}
						lMarketshareVTGT = ((marketshare1 - lSignificantNonSignificant.getFms())
								/ lSignificantNonSignificant.getFms()) * 100;
						totalmarketsharetarget1 += targetMarketShare1;
						lCompetitorData.setMarketShareVTGT((lMarketshareVTGT));
						lCompetitorDataList.add(lCompetitorData);
						for (int i = 0; i < lCompetitorDataList.size(); i++) {
							Map<String, Object> competitors = new HashMap<String, Object>();
							competitors.put("carrier", lCompetitorDataList.get(i).getCarrier());
							competitors.put("marketshareytd", lCompetitorDataList.get(i).getMarketShareYTD());
							competitors.put("marketssharevlyr", lCompetitorDataList.get(i).getMarketShareVLYR());
							competitors.put("marketsharevtgt", lCompetitorDataList.get(i).getMarketShareVTGT());

							compMap.put(carrier, competitors);
							compMap.put(lCompetitorDataList.get(i).getCarrier(), competitors);
						}
					}
					lSignificantNonSignificant.setCompetitorsMap(compMap);
				}

			}

			lSignificantNonSignificantList.add(lSignificantNonSignificant);
		}

		Collections.sort(lSignificantNonSignificantList, new Rating());
		int count = 1;
		for (SignificantNonSignificant lSiginificantObj : lSignificantNonSignificantList) {
			lSiginificantObj.setHostRank(count);
			count++;
		}
		lSignificantNonSignificant = map.get(Key);

		SignificantNonSignificant lCompetitorData = new SignificantNonSignificant();
		float totalpaxytdY = 0;
		float totalrevenueytdY = 0;
		float totalrevenuelastyrY = 0;
		float totalrevenuetargetY = 0;
		float totalpaxlastyrY = 0;
		float totalPaxtargetY = 0;
		float totalFmsofy = 0;
		float totalpaxytdJ = 0;
		float totalrevenueytdJ = 0;
		float totalrevenuelastyrJ = 0;
		float totalrevenuetargetJ = 0;
		float totalpaxlastyrJ = 0;
		float totalPaxtargetJ = 0;
		float totalpaxytdA = 0;
		float totalrevenueytdA = 0;
		float totalrevenuelastyrA = 0;
		float totalrevenuetargetA = 0;
		float totalpaxlastyrA = 0;
		float totalPaxtargetA = 0;
		float totalForcastPaxofy = 0;
		float totalTargetProratedPaxofY = 0;
		float totalFlownPaxofY = 0;
		float totalForcastRevenueofy = 0;
		float totalTargetProratedRevenueofY = 0;
		float totalflownrevenueofY = 0;
		float totalForcastPaxofJ = 0;
		float totalTargetProratedPaxofJ = 0;
		float totalFlownPaxofJ = 0;
		float totalForcastRevenueofJ = 0;
		float totalTargetProratedRevenueofJ = 0;
		float totalflownrevenueofJ = 0;
		float totalForcastPaxofA = 0;
		float totalTargetProratedPaxofA = 0;
		float totalFlownPaxofA = 0;
		float totalForcastRevenueofA = 0;
		float totalTargetProratedRevenueofA = 0;
		float totalflownrevenueofA = 0;
		float totalhostcapacityofY = 0;
		float totalhostcapacitylastyrofY = 0;
		float totalflownpaxlastyrofY = 0;
		float totalflownrevenuelastyrofY = 0;
		SignificantTotalResponse lModel = new SignificantTotalResponse();
		for (SignificantNonSignificant lObj : lSignificantNonSignificantList) {
			if (lObj.getCompartment().equals("Y")) {
				totalpaxytdY += lObj.getTotalPax();
				totalpaxlastyrY += lObj.getTotalPax_lastyr();
				totalPaxtargetY += lObj.getTotalpaxtarget();
				totalrevenueytdY += lObj.getTotalRevenue();
				totalrevenuelastyrY += lObj.getTotalrevenue_lastyr();
				totalrevenuetargetY += lObj.getTotalrevenuetarget();
				totalForcastPaxofy += lObj.getTotalforcastpax();
				totalTargetProratedPaxofY += lObj.getTotaltargetproratedpax();
				totalFlownPaxofY += lObj.getFlownpax();
				totalForcastRevenueofy += lObj.getTotalforcastrevenue();
				totalTargetProratedRevenueofY += lObj.getTotaltargetproratedrevenue();
				totalflownrevenueofY += lObj.getFlownrevenue();
				totalFmsofy += lObj.getFms();
				totalhostcapacityofY += lObj.getHostcapacity();
				totalhostcapacitylastyrofY += lObj.getHostcapacitylastyr();
				totalflownpaxlastyrofY += lObj.getTotalflownpaxlastyr();
				totalflownrevenuelastyrofY += lObj.getTotalFlownRevenue_lastyr();
				float lflownpaxlastyr = totalflownpaxlastyrofY;
				float lflownrevenuelastyr = totalflownrevenuelastyrofY;
				float hostcapacity = totalhostcapacityofY;
				float hostcapacitylastyr = totalhostcapacitylastyrofY;
				// total fms
				float lTotalFms = totalFmsofy;
				// total pax vtgt
				float pForcastPax = 0;
				int totaldaysFromdate = 0;
				float pForcastRevenue = 0;
				Date date1 = null;
				Date date2 = null;
				if (!pRequestModel.getFromDate().isEmpty() && pRequestModel.getFromDate() != null
						&& !pRequestModel.getToDate().isEmpty() && pRequestModel.getToDate() != null) {
					date1 = Utility.convertStringToDateFromat(pRequestModel.getFromDate());
					date2 = Utility.convertStringToDateFromat(pRequestModel.getToDate());
				} else {
					date1 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
					date2 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
				}
				int diff = (int) Utility.getDifferenceDays(date1, date2);
				int result = diff + 1;
				if (result == 0) {
					totaldaysFromdate = result + 1;
				}
				if (diff == 0) {
					pForcastPax = 0;
					pForcastRevenue = 0;
				} else {
					pForcastPax = totalForcastPaxofy;
					pForcastRevenue = totalForcastRevenueofy;
				}
				float lTotalPaxVTGTofY = CalculationUtil.calculateVTGTRemodelled(totalFlownPaxofY, pForcastPax,
						totalTargetProratedPaxofY);
				float lTotalrevenueVLYRY = 0;
				if (totalrevenuelastyrY != 0 && totalrevenuelastyrY > 0) {
					lTotalrevenueVLYRY = CalculationUtil.calculateVLYR(totalflownrevenueofY, lflownrevenuelastyr,
							hostcapacity, hostcapacitylastyr);
				} else {
					lTotalrevenueVLYRY = 0;
				}
				float lTotalRevenueVTGTY = 0;
				lTotalRevenueVTGTY = CalculationUtil.calculateVTGTRemodelled(totalflownrevenueofY, pForcastRevenue,
						totalTargetProratedRevenueofY);
				float lTotalpaxVLYRY = 0;
				if (totalpaxlastyrY != 0 && totalpaxlastyrY > 0) {
					lTotalpaxVLYRY = CalculationUtil.calculateVLYR(totalFlownPaxofY, lflownpaxlastyr, hostcapacity,
							hostcapacitylastyr);

				} else {
					lTotalpaxVLYRY = 0;
				}

				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("Total no of pax", (totalpaxytdY));
				map1.put("pax vlyr", (lTotalpaxVLYRY));
				map1.put("pax vtgt", (lTotalPaxVTGTofY));
				map1.put("Total revenue", (totalrevenueytdY));
				map1.put("Total revenue VLYR", (lTotalrevenueVLYRY));
				map1.put("Total revenue VTGT", (lTotalRevenueVTGTY));
				map1.put("Total Fms", lTotalFms);

				TotalMap1.put("Totals: " + lObj.getCompartment(), map1);
			}
			if (lObj.getCompartment().equals("J")) {
				totalpaxytdJ += lObj.getTotalPax();
				totalpaxlastyrJ += lObj.getTotalPax_lastyr();
				totalPaxtargetJ += lObj.getTotalpaxtarget();
				totalrevenueytdJ += lObj.getTotalRevenue();
				totalrevenuelastyrJ += lObj.getTotalrevenue_lastyr();
				totalrevenuetargetJ += lObj.getTotalrevenuetarget();
				totalForcastPaxofJ += lObj.getTotalforcastpax();
				totalTargetProratedPaxofJ += lObj.getTotaltargetproratedpax();
				totalFlownPaxofJ += lObj.getFlownpax();
				totalForcastRevenueofJ += lObj.getTotalforcastrevenue();
				totalTargetProratedRevenueofJ += lObj.getTotaltargetproratedrevenue();
				totalflownrevenueofJ += lObj.getFlownrevenue();
				float lTotalrevenueVLYRJ = 0;
				if (totalrevenuelastyrJ != 0 && totalrevenuelastyrJ > 0) {
					lTotalrevenueVLYRJ = CalculationUtil.calculateVLYR(totalrevenueytdJ, totalrevenuelastyrJ);
				} else {
					lTotalrevenueVLYRJ = 0;
				}
				float lTotalRevenueVTGTJ = 0;
				lTotalRevenueVTGTJ = CalculationUtil.calculateVTGTRemodelled(totalflownrevenueofJ,
						totalForcastRevenueofJ, totalTargetProratedRevenueofJ);
				float lTotalpaxVLYRJ = 0;
				if (totalpaxlastyrJ != 0 && totalpaxlastyrJ > 0) {
					lTotalpaxVLYRJ = CalculationUtil.calculateVLYR(totalpaxytdJ, totalpaxlastyrJ);

				} else {
					lTotalpaxVLYRJ = 0;
				}
				float lTotalpaxVTGTJ = 0;
				// total pax vtgt
				lTotalpaxVTGTJ = CalculationUtil.calculateVTGTRemodelled(totalFlownPaxofJ, totalForcastPaxofJ,
						totalTargetProratedPaxofJ);
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("Total no of pax", Math.round(totalpaxytdJ));
				map2.put("pax vlyr", Math.round(lTotalpaxVLYRJ));
				map2.put("pax vtgt", Math.round(lTotalpaxVTGTJ));
				map2.put("Total revenue", Math.round(totalrevenueytdJ));
				map2.put("Total revenue VLYR", Math.round(lTotalrevenueVLYRJ));
				map2.put("Total revenue VTGT", Math.round(lTotalRevenueVTGTJ));

				TotalMap2.put("Totals: " + lObj.getCompartment(), map2);
			}
			if ("A".equalsIgnoreCase(lObj.getCompartment())) {
				totalpaxytdA += lObj.getTotalPax();
				totalpaxlastyrA += lObj.getTotalPax_lastyr();
				totalPaxtargetA += lObj.getTotalpaxtarget();
				totalrevenueytdA += lObj.getTotalRevenue();
				totalrevenuelastyrA += lObj.getTotalrevenue_lastyr();
				totalrevenuetargetA += lObj.getTotalrevenuetarget();
				totalForcastPaxofA += lObj.getTotalforcastpax();
				totalTargetProratedPaxofA += lObj.getTotaltargetproratedpax();
				totalFlownPaxofA += lObj.getFlownpax();
				totalForcastRevenueofA += lObj.getTotalforcastrevenue();
				totalTargetProratedRevenueofA += lObj.getTotaltargetproratedrevenue();
				totalflownrevenueofA += lObj.getFlownrevenue();
				float lTotalrevenueVLYRA = 0;
				if (totalrevenuelastyrA != 0 && totalrevenuelastyrA > 0) {
					lTotalrevenueVLYRA = CalculationUtil.calculateVLYR(totalrevenueytdA, totalrevenuelastyrA);
				} else {
					lTotalrevenueVLYRA = 0;
				}
				float lTotalRevenueVTGTA = 0;
				lTotalRevenueVTGTA = CalculationUtil.calculateVTGTRemodelled(totalflownrevenueofA,
						totalForcastRevenueofA, totalTargetProratedRevenueofA);
				float lTotalpaxVLYRA = 0;
				if (totalpaxlastyrA != 0 && totalpaxlastyrA > 0) {
					lTotalpaxVLYRA = CalculationUtil.calculateVLYR(totalpaxytdA, totalpaxlastyrA);

				} else {
					lTotalpaxVLYRA = 0;
				}
				float lTotalpaxVTGTA = 0;
				// total pax vtgt
				lTotalpaxVTGTA = CalculationUtil.calculateVTGTRemodelled(totalFlownPaxofA, totalForcastPaxofA,
						totalTargetProratedPaxofA);
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3.put("Total no of pax", Math.round(totalpaxytdA));
				map3.put("pax vlyr", Math.round(lTotalpaxVLYRA));
				map3.put("pax vtgt", Math.round(lTotalpaxVTGTA));
				map3.put("Total revenue", Math.round(totalrevenueytdA));
				map3.put("Total revenue VLYR", Math.round(lTotalrevenueVLYRA));
				map3.put("Total revenue VTGT", Math.round(lTotalRevenueVTGTA));
				TotalMap3.put("Totals: " + lObj.getCompartment(), map3);
			}

		}

		lMTotalsList.add(lModel);
		if (TotalMap1 != null) {
			responseSignificantNonSignificantMap.putAll(TotalMap1);
		}
		if (TotalMap2 != null) {
			responseSignificantNonSignificantMap.putAll(TotalMap2);
		}
		if (TotalMap3 != null) {
			responseSignificantNonSignificantMap.putAll(TotalMap3);
		}
		responseSignificantNonSignificantMap.put("Significant and non-significant od", lSignificantNonSignificantList);
		return responseSignificantNonSignificantMap;

	}

	class Rating implements Comparator<SignificantNonSignificant> {

		@Override
		public int compare(SignificantNonSignificant o1, SignificantNonSignificant o2) {
			if (o1.getRevenueYTD() > 0) {
				if (o1.getRevenueYTD() < o2.getRevenueYTD()) {
					return 1;
				} else {
					return -1;
				}
			}

			return 0;
		}

	}

	@Override
	public Map<String, Object> getPriceAvailabilityIndex(RequestModel pRequestModel) {
		int totalfareYTD = 0;
		int fareYTD = 0;
		int lfareYTD = 0;
		String combinationKey = null;
		JSONArray market_share_paxArray = null;
		JSONArray lTargetMarketShareArray = null;
		JSONArray carrierArray = null;
		JSONArray market_sizeArray = null;
		List<FilterModel> lPriceList = new ArrayList<FilterModel>();
		float totalMarketshareYTD1 = 0;
		JSONArray lCapacityArray = null;
		float totalMarketshareYTD = 0;
		JSONArray market_share_pax_1Array = null;
		int market_sizelastyr = 0;
		JSONArray market_size_1Array = null;
		double market_Size = 0;
		double market_Sizelastyr = 0;
		float lMarketshareYTD = 0;
		float lmarket_shareVLYR = 0;
		float lMarketshareVTGT = 0;
		JSONArray lRatingCarrierArray = null;
		float lmarket_shareVLYR1 = 0;
		float lMarketshareVTGT1;
		float lMarketshareYTD1 = 0;
		float totalMarketsharelastyr = 0;
		float totalmarketsharetarget = 0;
		double marketsize = 0;
		double marketsize1 = 0;
		float totalMarketsharelastyr1 = 0;
		float totalmarketsharetarget1 = 0;
		double revenue = 0;
		double pax = 0;
		float marketShare_lastyr = 0;
		JSONArray lCompetitiorRatingArray = null;
		float marketShareYTD = 0;
		float marketShareVLYR = 0;
		float marketshareVTGT = 0;
		double ltotalmarketsharepaxlastyr = 0;
		double ltotalmarketsharepaxYTD = 0;
		double totalmarketsharepaxVLYR = 0;
		JSONArray lCapacityCarrierArray = null;
		List<PriceAvailability> lPriceAvailabilityList = new ArrayList<PriceAvailability>();
		List<PriceAvailabilityTotalResponse> lMTotalsList = new ArrayList<PriceAvailabilityTotalResponse>();
		Map<String, Object> TotalMap1 = new HashMap<String, Object>();
		Map<String, Object> TotalMap2 = new HashMap<String, Object>();
		Map<String, Object> TotalMap3 = new HashMap<String, Object>();
		Map<String, Object> responsePriceAvailabilityMap = new HashMap<String, Object>();
		FilterModel lPrice = new FilterModel();
		float avgFare = 0;

		ArrayList<DBObject> lPriceAvailabilityObj = mKpiDao.getPriceAvailabilityIndex(pRequestModel);
		JSONArray lPriceAvailabilityData = new JSONArray(lPriceAvailabilityObj);
		try {
			if (lPriceAvailabilityData != null) {
				for (int i = 0; i < lPriceAvailabilityData.length(); i++) {

					JSONObject lPriceJSONObj = lPriceAvailabilityData.getJSONObject(i);
					lPrice = new FilterModel();
					System.out.println("lPriceJSONObj:  " + lPriceJSONObj);

					if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
							&& pRequestModel.getUser().equals("Global Head")) {

						combinationKey = FilterUtil.getFilter(pRequestModel);

					}
					if (lPriceJSONObj.has("dep_date") && lPriceJSONObj.get("dep_date") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("dep_date").toString())) {
						lPrice.setDepartureDate(lPriceJSONObj.get("dep_date").toString());

					}
					if (lPriceJSONObj.has("region") && lPriceJSONObj.get("region") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("region").toString())) {
						lPrice.setRegion(lPriceJSONObj.get("region").toString());

					}
					if (lPriceJSONObj.has("fare_basis") && lPriceJSONObj.get("fare_basis") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("fare_basis").toString())) {

						lPrice.setFareBasis(lPriceJSONObj.get("fare_basis").toString());

					}
					if (lPriceJSONObj.has("RBD") && lPriceJSONObj.get("RBD") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("RBD").toString())) {

						lPrice.setRbd(lPriceJSONObj.get("RBD").toString());

					}

					if (lPriceJSONObj.has("country") && lPriceJSONObj.get("country") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("country").toString())) {
						lPrice.setCountry(lPriceJSONObj.get("country").toString());
					}

					if (lPriceJSONObj.has("pos") && lPriceJSONObj.get("pos") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("pos").toString())) {
						lPrice.setPos(lPriceJSONObj.get("pos").toString());

					}

					if (lPriceJSONObj.has("origin") && lPriceJSONObj.get("origin") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("origin").toString())) {
						lPrice.setOrigin(lPriceJSONObj.get("origin").toString());

					}

					if (lPriceJSONObj.has("destination") && lPriceJSONObj.get("destination") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("destination").toString())) {
						lPrice.setDestination(lPriceJSONObj.get("destination").toString());

					}
					lPrice.setOd(lPriceJSONObj.get("origin").toString() + lPriceJSONObj.get("destination").toString());

					if (lPriceJSONObj.has("compartment") && lPriceJSONObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("compartment").toString())) {
						lPrice.setCompartment(lPriceJSONObj.get("compartment").toString());

					}
					if (lPriceJSONObj.has("carrier") && lPriceJSONObj.get("carrier") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("carrier").toString())
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("carrier").toString())) {
						carrierArray = new JSONArray(lPriceJSONObj.get("carrier").toString());

					} else {
						carrierArray = null;
					}

					JSONArray revenueArray = null;
					if (lPriceJSONObj.has("revenue") && lPriceJSONObj.get("revenue") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("revenue").toString())) {
						revenueArray = new JSONArray(lPriceJSONObj.get("revenue").toString());
						if (revenueArray != null) {
							if (revenueArray.length() > 0) {
								revenue = Utility.findSum(revenueArray);
								lPrice.setRevenue((float) revenue);
							}
						}

					}

					JSONArray paxArray = null;
					if (lPriceJSONObj.has("pax") && lPriceJSONObj.get("pax") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("pax").toString())) {
						paxArray = new JSONArray(lPriceJSONObj.get("pax").toString());
						if (paxArray != null) {
							if (paxArray.length() > 0) {
								pax = Utility.findSum(paxArray);
								lPrice.setPax((int) pax);
							}
						}

					}

					// first letter of farebasis code is rbd

					if (lPriceJSONObj.has("currency") && lPriceJSONObj.get("currency") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("currency").toString())) {
						lPrice.setCurrency((lPriceJSONObj.get("currency").toString()));
					}

					if (lPriceJSONObj.has("Valid_for_Period") && lPriceJSONObj.get("Valid_for_Period") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("Valid_for_Period").toString())) {
						lPrice.setValidForPeriod(Float.parseFloat(lPriceJSONObj.get("Valid_for_Period").toString()));
					}
					if (lPriceJSONObj.has("channel") && lPriceJSONObj.get("channel") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("channel").toString())) {
						lPrice.setChannel((lPriceJSONObj.get("channel").toString()));
					} else {
						lPrice.setChannel("Data not available");
					}

					if (lPriceJSONObj.has("Competitor_1_Fare") && lPriceJSONObj.get("Competitor_1_Fare") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("Competitor_1_Fare").toString())) {
						lPrice.setCompFare1(Float.parseFloat(lPriceJSONObj.get("Competitor_1_Fare").toString()));
					}
					if (lPriceJSONObj.has("Competitor_2_Fare") && lPriceJSONObj.get("Competitor_2_Fare") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("Competitor_2_Fare").toString())) {
						lPrice.setCompFare2(Float.parseFloat(lPriceJSONObj.get("Competitor_2_Fare").toString()));
					}
					if (lPriceJSONObj.has("Competitor_1_Fare_Basis")
							&& lPriceJSONObj.get("Competitor_1_Fare_Basis") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("Competitor_1_Fare_Basis").toString())) {
						lPrice.setCompFareBasis1(
								Float.parseFloat(lPriceJSONObj.get("Competitor_1_Fare_Basis").toString()));
					}
					if (lPriceJSONObj.has("Competitor_2_Fare_Basis")
							&& lPriceJSONObj.get("Competitor_2_Fare_Basis") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("Competitor_2_Fare_Basis").toString())) {
						lPrice.setCompFareBasis2(
								Float.parseFloat(lPriceJSONObj.get("Competitor_2_Fare_Basis").toString()));
					}

					String segment = null;
					if (lPriceJSONObj.has("segment") && lPriceJSONObj.get("segment") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("segment").toString())) {
						segment = lPriceJSONObj.get("segment").toString();
						if (segment != null) {
							lPrice.setCustomerSegment(segment);
						} else {
							lPrice.setCustomerSegment("NA");
						}
					}
					// base fare is a fare
					if (lPriceJSONObj.has("Base_Fare") && lPriceJSONObj.get("Base_Fare") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("Base_Fare").toString())) {
						lPrice.setBase_Fare(Float.parseFloat(lPriceJSONObj.get("Base_Fare").toString()));
					} else {
						lPrice.setBase_Fare(0);
					}

					if (lPriceJSONObj.has("market_share_pax") && lPriceJSONObj.get("market_share_pax") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("market_share_pax").toString())
							&& !"[]".equalsIgnoreCase(lPriceJSONObj.get("market_share_pax").toString())) {

						market_share_paxArray = new JSONArray(lPriceJSONObj.get("market_share_pax").toString());
					} else {
						market_share_paxArray = null;
					}

					if (lPriceJSONObj.has("market_size") && lPriceJSONObj.get("market_size") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("market_size").toString())
							&& !"[]".equalsIgnoreCase(lPriceJSONObj.get("market_size").toString())) {

						market_sizeArray = new JSONArray(lPriceJSONObj.get("market_size").toString());

					}

					if (lPriceJSONObj.has("market_share_pax_1") && lPriceJSONObj.get("market_share_pax_1") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("market_share_pax_1").toString())
							&& !"[]".equalsIgnoreCase(lPriceJSONObj.get("market_share_pax_1").toString())) {

						market_share_pax_1Array = new JSONArray(lPriceJSONObj.get("market_share_pax_1").toString());
					}

					if (lPriceJSONObj.has("market_size_1") && lPriceJSONObj.get("market_size_1") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("market_size_1").toString())
							&& !"[]".equalsIgnoreCase(lPriceJSONObj.get("market_size_1").toString())) {

						market_size_1Array = new JSONArray(lPriceJSONObj.get("market_size_1").toString());
					}

					double marketSharePax = 0;
					double marketSharePax_lastYr = 0;
					double marketSize = 0;
					double marketSize_lastyr = 0;
					if (carrierArray != null) {
						if (carrierArray.length() > 0) {
							for (int j = 0; j < carrierArray.length(); j++) {
								if ("FZ".equalsIgnoreCase(carrierArray.get(j).toString())) {
									if (market_share_paxArray.length() > 0) {
										marketSharePax = Double.parseDouble(market_share_paxArray.get(j).toString());
										double marketsizecal = Utility.findSum(market_share_paxArray);
										float lmarketsize = (float) ((marketSharePax * 100) / marketsizecal);
										lPrice.setMarketSharePax(Double.toString(lmarketsize));
									}
									if (market_share_pax_1Array.length() > 0) {
										marketSharePax_lastYr = Double
												.parseDouble(market_share_pax_1Array.get(j).toString());
										double marketSize1cal = Utility.findSum(market_share_pax_1Array);
										float marketSizelastyr = (float) ((marketSharePax_lastYr * 100)
												/ marketSize1cal);
										lPrice.setMarketSharePax_lastyr(Double.toString(marketSizelastyr));
									}
									if (market_sizeArray != null) {
										if (market_sizeArray.length() > 0) {
											marketSize = Utility.findSum(market_sizeArray);
											lPrice.setMarketsize(Double.toString(marketSize));
										}
									}
									if (market_size_1Array != null) {
										if (market_size_1Array.length() > 0) {
											marketSize_lastyr = Utility.findSum(market_size_1Array);
											lPrice.setMarketsize1(Double.toString(marketSize_lastyr));
										}
									}

								}
							}
						}
					}

					if (lPriceJSONObj.has("target_market_share") && lPriceJSONObj.get("target_market_share") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("target_market_share").toString())
							&& !"[]".equalsIgnoreCase(lPriceJSONObj.get("target_market_share").toString())) {
						lTargetMarketShareArray = new JSONArray(lPriceJSONObj.get("target_market_share").toString());
						if (lTargetMarketShareArray != null) {
							if (lTargetMarketShareArray.length() > 0) {
								for (int k = 0; k < lTargetMarketShareArray.length(); k++) {
									float target = Float.parseFloat(lTargetMarketShareArray.get(k).toString());
									double markettarget = Utility.findSum(target);
									lPrice.setTargetmarketshare((float) markettarget);
								}
							}
						}

					}
					// Rating Carrier
					if (lPriceJSONObj.has("rating_carrier") && lPriceJSONObj.get("rating_carrier") != null
							&& !lPriceJSONObj.get("rating_carrier").toString().equalsIgnoreCase("null")
							&& !lPriceJSONObj.get("rating_carrier").toString().equalsIgnoreCase("[]")) {
						lRatingCarrierArray = new JSONArray(lPriceJSONObj.get("rating_carrier").toString());
					} else {
						lRatingCarrierArray = null;
					}

					if (lPriceJSONObj.has("rating") && lPriceJSONObj.get("rating") != null
							&& !lPriceJSONObj.get("rating").toString().equalsIgnoreCase("null")
							&& !lPriceJSONObj.get("rating").toString().equalsIgnoreCase("[]")) {
						lCompetitiorRatingArray = new JSONArray(lPriceJSONObj.get("rating").toString());
					} else {
						lCompetitiorRatingArray = null;
					}
					// Capacity Carrier
					double lCapacityCarrier = 0;
					if (lPriceJSONObj.has("capacity_airline") && lPriceJSONObj.get("capacity_airline") != null
							&& !lPriceJSONObj.get("capacity_airline").toString().equalsIgnoreCase("null")
							&& !lPriceJSONObj.get("capacity_airline").toString().equalsIgnoreCase("[]")) {
						lCapacityCarrierArray = new JSONArray(lPriceJSONObj.get("capacity_airline").toString());
					} else {
						lCapacityCarrierArray = null;
					}
					// Capacity
					double lCapacity = 0;
					if (lPriceJSONObj.has("capacity") && lPriceJSONObj.get("capacity") != null
							&& !lPriceJSONObj.get("capacity").toString().equalsIgnoreCase("null")
							&& !lPriceJSONObj.get("capacity").toString().equalsIgnoreCase("[]")) {
						lCapacityArray = new JSONArray(lPriceJSONObj.get("capacity").toString());
					} else {
						lCapacityArray = null;
					}
					double capacityFZ = 0;
					double capacityComp1 = 0;
					double capacityComp2 = 0;
					double capacityComp3 = 0;
					double capacityComp4 = 0;

					if (lCapacityCarrierArray != null) {
						if (lCapacityCarrierArray.length() > 0) {
							for (int j = 0; j < lCapacityCarrierArray.length(); j++) {
								if (lCapacityCarrierArray.length() > 0) {
									if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("FZ")) {
										capacityFZ += Double.parseDouble(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("EK")) {
										capacityComp1 += Double.parseDouble(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("EY")) {
										capacityComp2 += Double.parseDouble(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("G9")) {
										capacityComp3 = Double.parseDouble(lCapacityArray.get(j).toString());
									} else if (lCapacityCarrierArray.get(j).toString().equalsIgnoreCase("QR")) {
										capacityComp4 = Double.parseDouble(lCapacityArray.get(j).toString());
									}
								}
							}
							lPrice.setCapacityFZ((float) (capacityFZ));
							lPrice.setCapacityComp1((float) (capacityComp1));
							lPrice.setCapacityComp2((float) (capacityComp2));
							lPrice.setCapacityComp3((float) (capacityComp3));
							lPrice.setCapacityComp4((float) (capacityComp4));
						}
					} else {
						lPrice.setCapacityFZ(0);
						lPrice.setCapacityComp1(0);
						lPrice.setCapacityComp2(0);
						lPrice.setCapacityComp3(0);
						lPrice.setCapacityComp4(0);
					}
					double compRatingFZ = 0;
					double compRatingComp1 = 0;
					double compRatingComp2 = 0;
					double compRatingComp3 = 0;
					double compRatingComp4 = 0;
					if (lCompetitiorRatingArray != null && lCompetitiorRatingArray.length() > 0) {
						for (int j = 0; j < lCompetitiorRatingArray.length(); j++) {
							if (lRatingCarrierArray.length() > 0) {
								if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("FZ")) {
									compRatingFZ = Double.parseDouble(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("EK")) {
									compRatingComp1 = Double.parseDouble(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("EY")) {
									compRatingComp2 = Double.parseDouble(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("G9")) {
									compRatingComp3 = Double.parseDouble(lCompetitiorRatingArray.get(j).toString());
								} else if (lRatingCarrierArray.get(j).toString().equalsIgnoreCase("QR")) {
									compRatingComp4 = Double.parseDouble(lCompetitiorRatingArray.get(j).toString());
								}

							}
						}
						lPrice.setCompRatingFZ((float) (compRatingFZ));
						lPrice.setCompRatingComp1((float) (compRatingComp1));
						lPrice.setCompRatingComp2((float) (compRatingComp2));
						lPrice.setCompRatingComp3((float) (compRatingComp3));
						lPrice.setCompRatingComp4((float) (compRatingComp4));
					} else {
						lPrice.setCompRatingFZ(0);
						lPrice.setCompRatingComp1(0);
						lPrice.setCompRatingComp2(0);
						lPrice.setCompRatingComp3(0);
						lPrice.setCompRatingComp4(0);
					}

					System.out.println("lHost_Market_share " + lPrice.getPax() + "," + lPrice.getHost_market_share());
					// Key
					String lAggregationKey = FilterUtil.getAggregationKey(lPrice, combinationKey);
					lPrice.setFilterKey(lAggregationKey);
					lPriceList.add(lPrice);

				}
				for (int i = 0; i < lPriceList.size(); i++) {
					System.out.println("Market share for each year" + lPriceList.get(i).getHost_market_share());

				}

				Map<String, PriceAvailability> map = new HashMap<String, PriceAvailability>();

				PriceAvailability lPriceAvailability = null;

				for (FilterModel lPriceObj : lPriceList) {
					if (!map.containsKey(lPriceObj.getFilterKey())) {
						lPriceAvailability = new PriceAvailability();
						String rbd = null;
						lPriceAvailability.setCombinationkey(lPriceObj.getFilterKey());
						lPriceAvailability.setRegion(lPriceObj.getRegion());
						lPriceAvailability.setCountry(lPriceObj.getCountry());
						if (pRequestModel.getOdArray() != null) {
							lPriceAvailability.setOrigin(lPriceObj.getOrigin());

							lPriceAvailability.setDestination(lPriceObj.getDestination());
							lPriceAvailability.setOd(lPriceObj.getOrigin() + lPriceObj.getDestination());

						}
						lPriceAvailability.setFare_basis(lPriceObj.getFareBasis());
						lPriceAvailability.setRBD(lPriceObj.getRbd());
						lPriceAvailability.setPos(lPriceObj.getPos());
						lPriceAvailability.setCompartment(lPriceObj.getCompartment());
						lPriceAvailability.setDep_date(lPriceObj.getDepartureDate());
						lPriceAvailability.setBase_fare(lPriceObj.getBase_Fare());
						lPriceAvailability.setChannel(lPriceObj.getChannel());
						lPriceAvailability.setCapacityFZ(lPriceObj.getCapacityFZ());
						lPriceAvailability.setCapacityComp1(lPriceObj.getCapacityComp1());
						lPriceAvailability.setCapacityComp2(lPriceObj.getCapacityComp2());
						lPriceAvailability.setCapacityComp3(lPriceObj.getCapacityComp3());
						lPriceAvailability.setCapacityComp4(lPriceObj.getCapacityComp4());

						lPriceAvailability.setCompRatingFZ(lPriceObj.getCompRatingFZ());
						lPriceAvailability.setCompRatingComp1(lPriceObj.getCompRatingComp1());
						lPriceAvailability.setCompRatingComp2(lPriceObj.getCompRatingComp2());
						lPriceAvailability.setCompRatingComp3(lPriceObj.getCompRatingComp3());
						lPriceAvailability.setCompRatingComp4(lPriceObj.getCompRatingComp4());
						String segment = lPriceObj.getCustomerSegment();
						if (segment != null) {
							lPriceAvailability.setCustomerSegment(segment);
						} else {
							lPriceAvailability.setCustomerSegment("_");
						}
						String currency = lPriceObj.getCurrency();
						if (currency != null) {
							lPriceAvailability.setCurrency(currency);
						} else {
							lPriceAvailability.setCurrency("_");
						}
						float valid = (lPriceObj.getValidForPeriod());
						if (valid != 0.0) {
							lPriceAvailability.setValidforperiod(valid);
						} else {
							lPriceAvailability.setValidforperiod(0);
						}

						int totalPax = lPriceObj.getPax();

						lPriceAvailability.setTotalpax(totalPax);

						float totalRevenue = lPriceObj.getRevenue();
						lPriceAvailability.setTotalrevenue(CalculationUtil.roundToTwoDecimal(totalRevenue, 2));
						// host market share
						if (lPriceObj.getMarketSharePax() != null) {
							float marketShareHostPax = (Float.parseFloat(lPriceObj.getMarketSharePax()));
							lPriceAvailability.setMarketsharepax(marketShareHostPax);
						} else {
							lPriceAvailability.setMarketsharepax(0);
						}
						if (lPriceObj.getMarketSharePax_lastyr() != null) {
							float marketShareHostPax_lastyr = (Float.parseFloat(lPriceObj.getMarketSharePax_lastyr()));
							lPriceAvailability.setMarketsharepaxlastyr(marketShareHostPax_lastyr);
						} else {
							lPriceAvailability.setMarketsharepaxlastyr(0);
						}
						if (lPriceObj.getMarketsize() != null) {
							float marketSize = (Float.parseFloat(lPriceObj.getMarketsize()));
							lPriceAvailability.setMarketsize(marketSize);
						} else {
							lPriceAvailability.setMarketsize(0);
						}
						if (lPriceObj.getMarketsize1() != null) {
							float marketSize_lastyr = (Float.parseFloat(lPriceObj.getMarketsize1()));
							lPriceAvailability.setMarketsize1(marketSize_lastyr);
						} else {
							lPriceAvailability.setMarketsize1(0);
						}
						if (lPriceObj.getFareBasis() != null) {
							lPriceAvailability.setCount(lPriceAvailability.getCount() + 1);
						}

						map.put(lPriceObj.getFilterKey(), lPriceAvailability);
					} else {
						for (String lKey : map.keySet()) {
							if (lPriceObj.getFilterKey().equals(lKey)) {
								lPriceAvailability = map.get(lKey);
							}
						}
						float hostcapacity = (lPriceObj.getCapacityFZ() + lPriceAvailability.getCapacityFZ());
						lPriceAvailability.setCapacityFZ(hostcapacity);

						float comp1capacity = lPriceObj.getCapacityComp1() + lPriceAvailability.getCapacityComp1();
						lPriceAvailability.setCapacityComp1(comp1capacity);
						float comp2capacity = lPriceObj.getCapacityComp2() + lPriceAvailability.getCapacityComp2();
						lPriceAvailability.setCapacityComp2(comp2capacity);
						float comp3capacity = lPriceObj.getCapacityComp3() + lPriceAvailability.getCapacityComp3();
						lPriceAvailability.setCapacityComp3(comp3capacity);
						float comp4capacity = lPriceObj.getCapacityComp4() + lPriceAvailability.getCapacityComp4();
						lPriceAvailability.setCapacityComp4(comp4capacity);

						float hostrating = lPriceObj.getCompRatingFZ() + lPriceAvailability.getCompRatingFZ();
						lPriceAvailability.setCompRatingFZ(hostrating);
						float comp1rating = lPriceObj.getCompRatingComp1() + lPriceAvailability.getCompRatingComp1();
						lPriceAvailability.setCompRatingComp1(comp1rating);
						float comp2rating = lPriceObj.getCompRatingComp2() + lPriceAvailability.getCapacityComp2();
						lPriceAvailability.setCompRatingComp2(comp2rating);
						float comp3rating = lPriceObj.getCompRatingComp3() + lPriceAvailability.getCompRatingComp3();
						lPriceAvailability.setCompRatingComp3(comp3rating);
						float comp4rating = lPriceObj.getCompRatingComp4() + lPriceAvailability.getCompRatingComp4();
						lPriceAvailability.setCompRatingComp4(comp4rating);

						// totalpax
						int totalPax = (int) (lPriceObj.getPax() + lPriceAvailability.getTotalpax());

						lPriceAvailability.setTotalpax(CalculationUtil.roundToTwoDecimal(totalPax, 2));

						float totalRevenue = lPriceObj.getRevenue() + lPriceAvailability.getTotalrevenue();
						lPriceAvailability.setTotalrevenue(CalculationUtil.roundToTwoDecimal(totalRevenue, 2));
						if (lPriceAvailability.getFare_basis() != null) {
							if (lPriceObj.getFareBasis() != null) {

								if (!lPriceAvailability.getFare_basis().equals(lPriceObj.getFareBasis())) {
									lPriceAvailability.setCount(lPriceAvailability.getCount() + 1);
								}
							}
						}

						int m1 = Utility.findMonth(lPriceObj.getDepartureDate());
						int m2 = Utility.findMonth(lPriceAvailability.getDep_date());
						if (m1 != m2) {

							float marketShareHostPax = (Float.parseFloat(lPriceObj.getMarketSharePax())
									+ lPriceAvailability.getMarketsharepax());
							lPriceAvailability.setMarketsharepax(marketShareHostPax);

							float marketShareHostPax_lastyr = (Float.parseFloat(lPriceObj.getMarketSharePax_lastyr())
									+ lPriceAvailability.getMarketsharepaxlastyr());
							lPriceAvailability.setMarketsharepaxlastyr(marketShareHostPax_lastyr);

							float marketSize = (Float.parseFloat(lPriceObj.getMarketsize())
									+ lPriceAvailability.getMarketsize());
							lPriceAvailability.setMarketsize(marketSize);

							float marketSize_lastyr = (Float.parseFloat(lPriceObj.getMarketsize1())
									+ lPriceAvailability.getMarketsize1());
							lPriceAvailability.setMarketsize1(marketSize_lastyr);

						} else {
							if (lPriceObj.getMarketSharePax() != null) {
								float marketShareHostPax = Float.parseFloat(lPriceObj.getMarketSharePax());
								lPriceAvailability.setMarketsharepax(marketShareHostPax);
							} else {
								lPriceAvailability.setMarketsharepax(0);
							}
							if (lPriceObj.getMarketSharePax_lastyr() != null) {
								float marketShareHostPax_lastyr = Float
										.parseFloat(lPriceObj.getMarketSharePax_lastyr());
								lPriceAvailability.setMarketsharepaxlastyr(marketShareHostPax_lastyr);
							} else {
								lPriceAvailability.setMarketsharepaxlastyr(0);
							}
							if (lPriceObj.getMarketsize() != null) {
								float marketSize = Float.parseFloat(lPriceObj.getMarketsize());
								lPriceAvailability.setMarketsize(marketSize);
							} else {
								lPriceAvailability.setMarketsize(0);
							}
							if (lPriceObj.getMarketsize1() != null) {
								float marketSize_lastyr = Float.parseFloat(lPriceObj.getMarketsize1());
								lPriceAvailability.setMarketsize1(marketSize_lastyr);
							} else {
								lPriceAvailability.setMarketsize1(0);
							}
						}

						// Market share
						double totalMarketSharePax = Double
								.parseDouble(Float.toString(lPriceAvailability.getMarketsharepax()))
								+ lPriceAvailability.getTotalmarketsharepax();
						lPriceAvailability.setTotalmarketsharepax((float) totalMarketSharePax);

						// Market Share Last Year
						double totalMarketSharePax_lastYr = Double
								.parseDouble(Float.toString(lPriceAvailability.getMarketsharepaxlastyr()))
								+ lPriceAvailability.getTotalmarketsharepaxlastyr();
						lPriceAvailability.setTotalmarketsharepaxlastyr((float) totalMarketSharePax_lastYr);

						double totalmarketsize = lPriceAvailability.getMarketsize();
						lPriceAvailability.setTotalmarketsize((float) totalmarketsize);
						double totalmarketsize1 = lPriceAvailability.getMarketsize1();
						lPriceAvailability.setTotalmarketsize1((float) totalmarketsize1);

						lPriceAvailability.setTargetmarketshare(lPriceObj.getTargetmarketshare());
					}

				}
				for (String key : map.keySet()) {
					lPriceAvailability = map.get(key);
					lPriceAvailability.setCombinationkey(map.get(key).getCombinationkey());
					lPriceAvailability.setCountry(map.get(key).getCountry());
					lPriceAvailability.setRegion(map.get(key).getRegion());
					lPriceAvailability.setOrigin(map.get(key).getOrigin());
					lPriceAvailability.setDestination(map.get(key).getDestination());
					lPriceAvailability.setOd(map.get(key).getOd());
					lPriceAvailability.setCompartment(map.get(key).getCompartment());
					lPriceAvailability.setPos(map.get(key).getPos());
					lPriceAvailability.setRBD(map.get(key).getRBD());
					lPriceAvailability.setFare_basis(map.get(key).getFare_basis());
					lPriceAvailability.setBase_fare(map.get(key).getBase_fare());
					lPriceAvailability.setChannel(map.get(key).getChannel());
					lPriceAvailability.setCustomerSegment(map.get(key).getCustomerSegment());
					lPriceAvailability.setCurrency(map.get(key).getCurrency());
					lPriceAvailability.setFairmarketshare(map.get(key).getFairmarketshare());

					lPriceAvailability.setValidforperiod(map.get(key).getValidforperiod());
					// price availability index
					float priceAvailabilityIndex = (map.get(key).getCount()
							/ CalculationUtil.calculateTheoretcalMax(map.get(key).getCompartment()) * 100);
					if (priceAvailabilityIndex > 0) {
						lPriceAvailability.setPriceAvailabilityIndex((priceAvailabilityIndex));
					} else {
						lPriceAvailability.setPriceAvailabilityIndex(0);
					}
					// Capacity
					lPriceAvailability.setCapacityFZ(map.get(key).getCapacityFZ());
					lPriceAvailability.setCapacityComp1(map.get(key).getCapacityComp1());
					lPriceAvailability.setCapacityComp2(map.get(key).getCapacityComp2());

					// CompRating
					lPriceAvailability.setCompRatingFZ(map.get(key).getCompRatingFZ());
					lPriceAvailability.setCompRatingComp1(map.get(key).getCompRatingComp1());
					lPriceAvailability.setCompRatingComp2(map.get(key).getCompRatingComp2());

					ArrayList<Integer> lCapacityList = new ArrayList<Integer>();
					int lCapacityFZ = 1;

					lCapacityFZ = (int) (map.get(key).getCapacityFZ());
					lCapacityList.add(lCapacityFZ);

					lCapacityList.add((int) (map.get(key).getCapacityComp1()));

					lCapacityList.add((int) (map.get(key).getCapacityComp2()));

					lCapacityList.add((int) (map.get(key).getCapacityComp3()));

					lCapacityList.add((int) (map.get(key).getCapacityComp4()));

					ArrayList<Float> lCompRatingList = new ArrayList<Float>();

					lCompRatingList.add((map.get(key).getCompRatingFZ()));

					lCompRatingList.add((map.get(key).getCompRatingComp1()));

					lCompRatingList.add((map.get(key).getCompRatingComp2()));

					lCompRatingList.add((map.get(key).getCompRatingComp3()));

					lCompRatingList.add((map.get(key).getCompRatingComp4()));

					// FMS
					float lCompRatingFZ = 1;

					lCompRatingFZ = (map.get(key).getCompRatingFZ());

					float lFMS = CalculationUtil.calculateFMS(lCapacityFZ, lCompRatingFZ, lCapacityList,
							lCompRatingList);
					if (lFMS != 0) {
						lPriceAvailability.setFairmarketshare(((lFMS)));
					} else {
						lPriceAvailability.setFairmarketshare(0);
					}

					float paxytd = map.get(key).getTotalpax();
					float revenueytd = map.get(key).getTotalrevenue();

					avgFare = (float) (revenueytd / paxytd);
					lPriceAvailability.setFare((avgFare));

					// Market size
					lPriceAvailability.setTotalmarketsize(map.get(key).getTotalmarketsize());
					lPriceAvailability.setTotalmarketsize1(map.get(key).getTotalmarketsize1());

					// Setting MarketShare YTD
					if (map.get(key).getMarketsize() > 0) {
						marketShareYTD = ((map.get(key).getMarketsharepax()));
						lPriceAvailability.setMarketShareYTD((marketShareYTD));
					} else {
						lPriceAvailability.setMarketShareYTD(0);
					}

					marketShare_lastyr = ((map.get(key).getMarketsharepaxlastyr()));
					lPriceAvailability.setMarketsharepaxlastyr(marketShare_lastyr);

					// Market Share VLYR
					if (marketShare_lastyr > 0) {
						marketShareVLYR = CalculationUtil.calculateVLYR((float) marketShareYTD, marketShare_lastyr);
						lPriceAvailability.setMarketShareVLYR((marketShareVLYR));
					} else {
						lPriceAvailability.setMarketShareVLYR(0);
					}
					// Market share VTGT
					marketshareVTGT = (float) (((marketShareYTD - lFMS) / lFMS) * 100);
					lPriceAvailability.setMarketShareVTGT((marketshareVTGT));

					lPriceAvailabilityList.add(lPriceAvailability);

				}

				PriceAvailabilityTotalResponse lModel = new PriceAvailabilityTotalResponse();

				float totalRevenueofY = 0;
				float totalPaxofY = 0;
				float totalAvgFareofY = 0;
				float totalRevenueofJ = 0;
				float totalPaxofJ = 0;
				float totalAvgFareofJ = 0;
				float totalRevenueofA = 0;
				float totalPaxofA = 0;
				float totalAvgFareofA = 0;
				float totalcountofY = 0;
				float totalPriceAvailabilityIndex = 0;
				float fmsofY = 0;
				float fmsofJ = 0;
				float fmsofA = 0;
				float marketshareytdofY = 0;
				float marketsharelastyrofY = 0;
				float marketshareofY = 0;
				float marketsharevlyrofY = 0;
				float totalmarketsharevtgtofY = 0;
				for (PriceAvailability lObj : lPriceAvailabilityList) {
					if (lObj.getCompartment().equals("Y")) {
						totalRevenueofY += lObj.getTotalrevenue();
						totalPaxofY += lObj.getTotalpax();
						totalcountofY += lObj.getCount();
						fmsofY = lObj.getFairmarketshare();
						marketshareofY += lObj.getMarketShareYTD();
						marketsharelastyrofY += lObj.getMarketsharepaxlastyr();
						// total market share ytd
						marketshareytdofY = marketshareofY;
						// total market share vlyr
						marketsharevlyrofY = CalculationUtil.calculateVLYR(marketshareofY, marketsharelastyrofY);
						// total market share vtgt
						totalmarketsharevtgtofY = ((marketshareofY - fmsofY) / fmsofY) * 100;

						totalAvgFareofY = totalRevenueofY / totalPaxofY;
						totalPriceAvailabilityIndex = (totalcountofY
								/ CalculationUtil.calculateTheoretcalMax(lObj.getCompartment()) * 100);

						Map<String, Object> map1 = new HashMap<String, Object>();
						map1.put("FMs", fmsofY);
						map1.put("Total avg Fare", (totalAvgFareofY));
						map1.put("total Price availability index", (totalPriceAvailabilityIndex));
						map1.put("total market share ytd", marketshareytdofY);
						map1.put("total market share vlyr", marketsharevlyrofY);
						map1.put("total market share vtgt", totalmarketsharevtgtofY);
						TotalMap1.put("Totals: " + lObj.getCompartment(), map1);
					}
					if (lObj.getCompartment().equals("J")) {
						fmsofJ = lObj.getFairmarketshare();
						totalRevenueofJ += lObj.getTotalrevenue();
						totalPaxofJ += lObj.getTotalpax();
						totalAvgFareofJ = totalRevenueofJ / totalPaxofJ;
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("FMs", fmsofJ);
						map2.put("Total avg Fare", Math.round(totalAvgFareofJ));

						TotalMap2.put("Totals: " + lObj.getCompartment(), map2);
					}
					if ("A".equalsIgnoreCase(lObj.getCompartment())) {
						fmsofA = lObj.getFairmarketshare();
						totalRevenueofA += lObj.getTotalrevenue();
						totalPaxofA += lObj.getTotalpax();
						totalAvgFareofA = totalRevenueofA / totalPaxofA;
						Map<String, Object> map3 = new HashMap<String, Object>();
						map3.put("FMs", fmsofJ);
						map3.put("Total avg fare", Math.round(totalAvgFareofA));
						TotalMap3.put("Totals: " + lObj.getCompartment(), map3);
					}

				}

				lMTotalsList.add(lModel);
				if (TotalMap1 != null) {
					responsePriceAvailabilityMap.putAll(TotalMap1);
				}
				if (TotalMap2 != null) {
					responsePriceAvailabilityMap.putAll(TotalMap2);
				}
				if (TotalMap3 != null) {
					responsePriceAvailabilityMap.putAll(TotalMap3);
				}
				responsePriceAvailabilityMap.put("lPriceAvailability", lPriceAvailabilityList);

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return responsePriceAvailabilityMap;
	}

	@Override
	public Map<String, Object> getPriceStabilityIndex(RequestModel pRequestModel) {

		float lHost_market_shareYTD = 0;
		float lHost_market_shareVLYR = 0;
		float lMarketshareVTGT = 0;
		float totalMarketshareYTD = 0;
		float totalMarketsharelastyr = 0;
		float totalcapacityytd = 0;
		String compartment = null;
		JSONArray market_sizeArray = null;
		JSONArray lTargetMarketShareArray = null;
		JSONArray market_size1Array = null;
		double lTotalmarketsize = 0;
		double lTotalmarketsizelastyr = 0;
		String combinationKey = null;
		String carrier = null;
		float totalfareytd = 0;
		float fareytd = 0;
		float totalnooffarechangeYTD = 0;
		float lnooffarechangeYTD = 0;
		float lTotalpriceagilityindex = 0;
		float ltotalpriceagilityindex = 0;
		Map<String, Object> TotalMap1 = new HashMap<String, Object>();
		Map<String, Object> TotalMap2 = new HashMap<String, Object>();
		Map<String, Object> TotalMap3 = new HashMap<String, Object>();
		List<FilterModel> lPriceList = new ArrayList<FilterModel>();
		List<PriceStability> lPriceStabilityList = new ArrayList<PriceStability>();

		List<PriceStabilityTotalResponse> lMTotalsList = new ArrayList<PriceStabilityTotalResponse>();
		Map<String, Object> responsePriceStabilityMap = new HashMap<String, Object>();

		FilterModel lPrice = null;

		ArrayList<DBObject> lPriceStabilityObj = mKpiDao.getPriceStabilityIndex(pRequestModel);
		JSONArray lPriceStabilityData = new JSONArray(lPriceStabilityObj);

		try {
			if (lPriceStabilityData != null) {

				for (int i = 0; i < lPriceStabilityData.length(); i++) {
					JSONObject lPriceJSONObj = lPriceStabilityData.getJSONObject(i);
					lPrice = new FilterModel();
					System.out.println("lPriceJSONObj" + lPriceJSONObj);

					if (lPriceJSONObj.has("pos") && lPriceJSONObj.get("pos") != null) {
						if (lPriceJSONObj.has("compartment") && lPriceJSONObj.get("compartment") != null) {

							if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
									&& pRequestModel.getUser().equals("Global Head")) {
								combinationKey = FilterUtil.getFilter(pRequestModel);
							}
						}

					}

					if (lPriceJSONObj.has("region") && lPriceJSONObj.get("region") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("region").toString())) {
						lPrice.setRegion(lPriceJSONObj.get("region").toString());

					}

					if (lPriceJSONObj.has("country") && lPriceJSONObj.get("country") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("country").toString())) {
						lPrice.setCountry(lPriceJSONObj.get("country").toString());
					}

					if (lPriceJSONObj.has("pos") && lPriceJSONObj.get("pos") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("pos").toString())) {
						lPrice.setPos(lPriceJSONObj.get("pos").toString());
					}

					if (lPriceJSONObj.has("od") && lPriceJSONObj.get("od") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("od").toString())) {
						String od = lPriceJSONObj.get("od").toString();
						if (od != null) {
							String origin = od.substring(0, 3);
							lPrice.setOrigin(origin);
							String destination = od.substring(3, 6);
							lPrice.setDestination(destination);
						}

					}
					if (lPriceJSONObj.has("compartment") && lPriceJSONObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("compartment").toString())) {
						lPrice.setCompartment(lPriceJSONObj.get("compartment").toString());
					}
					if (lPriceJSONObj.has("No_of_fares") && lPriceJSONObj.get("No_of_fares") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("No_of_fares").toString())) {
						lPrice.setNooffares(Float.parseFloat(lPriceJSONObj.get("No_of_fares").toString()));

					}
					if (lPriceJSONObj.has("No_of_changes") && lPriceJSONObj.get("No_of_changes") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("No_of_changes").toString())) {
						lPrice.setNooffarechanges(Float.parseFloat(lPriceJSONObj.get("No_of_changes").toString()));
					}
					if (lPriceJSONObj.has("farebasis") && lPriceJSONObj.get("farebasis") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("farebasis").toString())) {
						String farebasis = lPriceJSONObj.get("farebasis").toString();
						lPrice.setFarebasis(farebasis);
						String rbd = null;
						if (farebasis != null) {
							rbd = farebasis.substring(0, 1);
							lPrice.setRbd(rbd);
						}
					}
					// Key
					String lAggregationKey = FilterUtil.getAggregationKey(lPrice, combinationKey);
					lPrice.setFilterKey(lAggregationKey);
					lPriceList.add(lPrice);

				}

				Map<String, PriceStability> map = new HashMap<String, PriceStability>();

				PriceStability lPriceStability = null;

				for (FilterModel lPriceObj : lPriceList) {
					if (!map.containsKey(lPriceObj.getFilterKey())) {
						lPriceStability = new PriceStability();
						lPriceStability.setCombinationkey(lPriceObj.getFilterKey());
						lPriceStability.setRegion(lPriceObj.getRegion());
						lPriceStability.setCountry(lPriceObj.getCountry());
						if (pRequestModel.getOdArray() != null) {
							lPriceStability.setOrigin(lPriceObj.getOrigin());
							lPriceStability.setDestination(lPriceObj.getDestination());
						}
						lPriceStability.setPos(lPriceObj.getPos());
						lPriceStability.setCompartment(lPriceObj.getCompartment());
						lPriceStability.setFarebasis(lPriceObj.getFarebasis());
						lPriceStability.setRbd(lPriceObj.getRbd());

						float totalnooffares = lPriceObj.getNooffares();
						if (totalnooffares > 0) {
							lPriceStability.setTotalnooffare((totalnooffares));
						} else {
							lPriceStability.setTotalnooffare(0);
						}
						float totalnooffarechanges = lPriceObj.getNooffarechanges();
						if (totalnooffarechanges > 0) {
							lPriceStability.setTotalnooffarechange((totalnooffarechanges));
						} else {
							lPriceStability.setTotalnooffarechange(0);
						}

						map.put(lPriceObj.getFilterKey(), lPriceStability);

					} else {
						for (String lKey : map.keySet()) {
							if (lPriceObj.getFilterKey().equals(lKey)) {
								lPriceStability = map.get(lKey);
							}
						}

						float totalnooffares = lPriceObj.getNooffares() + lPriceStability.getTotalnooffare();
						if (totalnooffares > 0) {
							lPriceStability.setTotalnooffare((totalnooffares));
						} else {
							lPriceStability.setTotalnooffare(0);
						}
						float totalnooffarechanges = lPriceObj.getNooffarechanges()
								+ lPriceStability.getTotalnooffarechange();
						if (totalnooffarechanges > 0) {
							lPriceStability.setTotalnooffarechange((totalnooffarechanges));
						} else {
							lPriceStability.setTotalnooffarechange(0);
						}
					}

				}
				for (String key : map.keySet()) {
					lPriceStability = map.get(key);
					lPriceStability.setCombinationkey(map.get(key).getCombinationkey());
					lPriceStability.setCountry(map.get(key).getCountry());
					lPriceStability.setRegion(map.get(key).getRegion());
					lPriceStability.setOrigin(map.get(key).getOrigin());
					lPriceStability.setDestination(map.get(key).getDestination());
					lPriceStability.setCompartment(map.get(key).getCompartment());
					lPriceStability.setPos(map.get(key).getPos());
					lPriceStability.setFarebasis(map.get(key).getFarebasis());
					lPriceStability.setRbd(map.get(key).getRbd());

					float totalnooffare = map.get(key).getTotalnooffare();
					fareytd = totalnooffare;
					totalfareytd += fareytd;
					lPriceStability.setTotalnooffare(Math.round(map.get(key).getTotalnooffare()));
					lPriceStability.setNooffareytd(Math.round(fareytd));

					float totalnooffarechange = map.get(key).getTotalnooffarechange();
					lnooffarechangeYTD = totalnooffarechange;
					totalnooffarechangeYTD += lnooffarechangeYTD;
					lPriceStability.setTotalnooffarechange(Math.round(map.get(key).getTotalnooffarechange()));
					lPriceStability.setNooffarechangeytd(Math.round(totalnooffarechangeYTD));

					float totalpriceagilityindex = totalnooffarechange / totalnooffare * 100;
					ltotalpriceagilityindex = totalpriceagilityindex;
					lTotalpriceagilityindex += ltotalpriceagilityindex;
					lPriceStability.setPriceagilityindex(Math.round(lTotalpriceagilityindex));
					lPriceStability.setTotalpriceagilityindex(Math.round(totalpriceagilityindex));

					lPriceStabilityList.add(lPriceStability);

				}
				float totalnooffareY = 0;
				float totalnooffarearechangeY = 0;
				float totalpriceagilityindexY = 0;
				float totalnooffareJ = 0;
				float totalnooffarearechangeJ = 0;
				float totalpriceagilityindexJ = 0;
				float totalnooffareA = 0;
				float totalnooffarearechangeA = 0;
				float totalpriceagilityindexA = 0;
				float totalnooffareNA = 0;
				float totalnooffarearechangeNA = 0;
				float totalpriceagilityindexNA = 0;
				PriceStabilityTotalResponse lModel = new PriceStabilityTotalResponse();
				for (PriceStability lObj : lPriceStabilityList) {
					if (lObj.getCompartment().equals("Y")) {
						totalnooffareY += lObj.getTotalnooffare();
						totalnooffarearechangeY += lObj.getTotalnooffarechange();
						totalpriceagilityindexY += lObj.getTotalpriceagilityindex();
						Map<String, Object> map1 = new HashMap<String, Object>();
						map1.put("Total no of fare", Math.round(totalnooffareY));
						map1.put("Total no of fare change", Math.round(totalnooffarearechangeY));
						map1.put("Total price agility index", Math.round(totalpriceagilityindexY));

						TotalMap1.put("Totals: " + lObj.getCompartment(), map1);
					}
					if (lObj.getCompartment().equals("J")) {
						totalnooffareJ += lObj.getTotalnooffare();
						totalnooffarearechangeJ += lObj.getTotalnooffarechange();
						totalpriceagilityindexJ += lObj.getTotalpriceagilityindex();
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("Total no of fare", Math.round(totalnooffareJ));
						map2.put("Total no of fare change", Math.round(totalnooffarearechangeJ));
						map2.put("Total price agility index", Math.round(totalpriceagilityindexJ));

						TotalMap2.put("Totals: " + lObj.getCompartment(), map2);
					}
					if ("A".equalsIgnoreCase(lObj.getCompartment())) {
						totalnooffareA += lObj.getTotalnooffare();
						totalnooffarearechangeA += lObj.getTotalnooffarechange();
						totalpriceagilityindexA += lObj.getTotalpriceagilityindex();
						Map<String, Object> map3 = new HashMap<String, Object>();
						map3.put("Total no of fare", Math.round(totalnooffareA));
						map3.put("Total no of fare change", Math.round(totalnooffarearechangeA));
						map3.put("Total price agility index", Math.round(totalpriceagilityindexA));

						TotalMap3.put("Totals: " + lObj.getCompartment(), map3);
					}

				}

				lMTotalsList.add(lModel);

				if (TotalMap1 != null) {
					responsePriceStabilityMap.putAll(TotalMap1);
				}
				if (TotalMap2 != null) {
					responsePriceStabilityMap.putAll(TotalMap2);
				}
				if (TotalMap3 != null) {
					responsePriceStabilityMap.putAll(TotalMap3);
				}
				responsePriceStabilityMap.put("price Stability", lPriceStabilityList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return responsePriceStabilityMap;

	}

	@Override
	public Map<String, Object> getTotalEffective_IneffectiveFares(RequestModel pRequestModel) {

		JSONArray lTargetPaxArray = null;
		JSONArray lTargetPricePerformanceArray = null;
		JSONArray lTargetPriceElasticityArray = null;

		float lPaxYTD = 0;
		float lPricePerformancceYTD = 0;
		float lPaxVLYR = 0;
		float lPaxVTGT = 0;
		float lPriceperformanceVLYR = 0;

		float lPriceperformanceVTGT = 0;
		float totalrevenueYTD = 0;
		float lrevenueYTD = 0;
		float lPriceElasticityVLYR = 0;

		float lPriceElasticityVTGT = 0;
		float totalPaxYTD = 0;
		float totalPaxVLYR = 0;
		float totalPax_target = 0;
		float totalPriceperformanceVLYR = 0;
		float totalPriceElasticityVLYR = 0;
		float avgfare = 0;
		float totalPiceperformance = 0;
		float totalPicerlasticity = 0;
		float totalPricePerformance = 0;
		float totalPiceperformancetarget = 0;
		float totalPriceElasticity = 0;
		JSONArray paxarray = null;
		JSONArray pax1Array = null;
		JSONArray revnuearray = null;

		List<TotalEffective> lTotalEffectiveList = new ArrayList<TotalEffective>();
		List<TotalEffectiveIneffective> lTotalEffectiveIneffectiveList = new ArrayList<TotalEffectiveIneffective>();

		List<TotaleffectiveIneffectiveTotalResponse> lMTotalsList = new ArrayList<TotaleffectiveIneffectiveTotalResponse>();
		Map<String, Object> responseTotalEffectiveIneffectiveFaresMap = new HashMap<String, Object>();

		TotalEffective lTotalEffective = new TotalEffective();
		String combinationKey = null;

		ArrayList<DBObject> lTotalEffectiveIneffectiveObj = mKpiDao.getTotalEffective_IneffectiveFares(pRequestModel);
		JSONArray lTotalEffectiveIneffectiveData = new JSONArray(lTotalEffectiveIneffectiveObj);
		try {

			if (lTotalEffectiveIneffectiveData != null) {
				for (int i = 0; i < lTotalEffectiveIneffectiveData.length(); i++) {

					JSONObject lTotalEffectiveJSONObj = lTotalEffectiveIneffectiveData.getJSONObject(i);
					lTotalEffective = new TotalEffective();
					System.out.println("lTotalEffectiveJSONObj" + lTotalEffectiveJSONObj);

					if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
							&& pRequestModel.getUser().equals("Global Head")) {

						if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("compartment").toString();

						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {

							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("country").toString();
						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() == null) {

							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("country").toString()
									+ lTotalEffectiveJSONObj.get("compartment").toString();
						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() == null) {

							combinationKey = lTotalEffectiveJSONObj.get("country").toString()
									+ lTotalEffectiveJSONObj.get("pos").toString()
									+ lTotalEffectiveJSONObj.get("compartment").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("country").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("country").toString()
									+ lTotalEffectiveJSONObj.get("pos").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("country").toString()
									+ lTotalEffectiveJSONObj.get("pos").toString()
									+ lTotalEffectiveJSONObj.get("compartment").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() != null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("country").toString()
									+ lTotalEffectiveJSONObj.get("pos").toString()
									+ lTotalEffectiveJSONObj.get("compartment").toString()
									+ lTotalEffectiveJSONObj.get("origin").toString()
									+ lTotalEffectiveJSONObj.get("destination").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() != null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("origin").toString()
									+ lTotalEffectiveJSONObj.get("destination").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() != null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("pos").toString()
									+ lTotalEffectiveJSONObj.get("origin").toString()
									+ lTotalEffectiveJSONObj.get("destination").toString();
						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("country").toString();

						}

						else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("pos").toString();

						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("compartment").toString();

						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() != null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("origin").toString()
									+ lTotalEffectiveJSONObj.get("destination").toString();

						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() != null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("country").toString()
									+ lTotalEffectiveJSONObj.get("pos").toString()
									+ lTotalEffectiveJSONObj.get("compartment").toString()
									+ lTotalEffectiveJSONObj.get("origin").toString()
									+ lTotalEffectiveJSONObj.get("destination").toString();

						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = lTotalEffectiveJSONObj.get("region").toString()
									+ lTotalEffectiveJSONObj.get("country").toString()
									+ lTotalEffectiveJSONObj.get("compartment").toString();

						}

					}

					if (lTotalEffectiveJSONObj.has("region") && lTotalEffectiveJSONObj.get("region") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("region").toString())) {
						lTotalEffective.setRegion(lTotalEffectiveJSONObj.get("region").toString());
					}
					if (lTotalEffectiveJSONObj.has("country") && lTotalEffectiveJSONObj.get("country") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("country").toString())) {
						lTotalEffective.setCountry(lTotalEffectiveJSONObj.get("country").toString());
					}
					lTotalEffective.setCombinationkey(combinationKey);

					if (lTotalEffectiveJSONObj.has("pos") && lTotalEffectiveJSONObj.get("pos") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("pos").toString())) {
						lTotalEffective.setPos(lTotalEffectiveJSONObj.get("pos").toString());
					}
					if (lTotalEffectiveJSONObj.has("origin") && lTotalEffectiveJSONObj.get("origin") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("origin").toString())) {
						lTotalEffective.setOrigin(lTotalEffectiveJSONObj.get("origin").toString());
					}
					if (lTotalEffectiveJSONObj.has("destination") && lTotalEffectiveJSONObj.get("destination") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("destination").toString())) {
						lTotalEffective.setDestination(lTotalEffectiveJSONObj.get("destination").toString());
					}
					if (lTotalEffectiveJSONObj.has("compartment") && lTotalEffectiveJSONObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("compartment").toString())) {
						lTotalEffective.setCompartment(lTotalEffectiveJSONObj.get("compartment").toString());
					}
					double revenueArray = 0;
					if (lTotalEffectiveJSONObj.has("revenue") && lTotalEffectiveJSONObj.get("revenue") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("revenue").toString())) {
						revnuearray = new JSONArray(lTotalEffectiveJSONObj.get("revenue").toString());
						if (revnuearray != null) {
							if (revnuearray.length() > 0) {
								revenueArray = Utility.findSum(revnuearray);
								lTotalEffective.setRevenue((float) revenueArray);
							}
						}
					}
					if (lTotalEffectiveJSONObj.has("fare_basis") && lTotalEffectiveJSONObj.get("fare_basis") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("fare_basis").toString())) {
						lTotalEffective.setFare_basis(lTotalEffectiveJSONObj.get("fare_basis").toString());
					}
					double paxArray = 0;
					if (lTotalEffectiveJSONObj.has("pax") && lTotalEffectiveJSONObj.get("region") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("pax").toString())) {
						paxarray = new JSONArray(lTotalEffectiveJSONObj.get("pax").toString());
						if (paxarray != null) {
							if (paxarray.length() > 0) {
								paxArray = Utility.findSum(paxarray);
								lTotalEffective.setPax((int) paxArray);
							}
						}
					}

					double pax1array = 0;
					if (lTotalEffectiveJSONObj.has("pax_1") && lTotalEffectiveJSONObj.get("pax_1") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("pax_1").toString())) {
						pax1Array = new JSONArray(lTotalEffectiveJSONObj.get("pax_1").toString());
						if (pax1Array != null) {
							if (pax1Array.length() > 0) {
								pax1array = Utility.findSum(pax1Array);
								lTotalEffective.setPax_lastyr((int) pax1array);
							}
						}
					}

					double targetpaxarray = 0;
					if (lTotalEffectiveJSONObj.has("target_pax") && lTotalEffectiveJSONObj.get("target_pax") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("target_pax").toString())
							&& !"[]".equalsIgnoreCase(lTotalEffectiveJSONObj.get("target_pax").toString())) {
						lTargetPaxArray = new JSONArray(lTotalEffectiveJSONObj.get("target_pax").toString());
						if (lTargetPaxArray != null) {
							if (lTargetPaxArray.length() > 0) {
								targetpaxarray = Utility.findSum(lTargetPaxArray);
								lTotalEffective.setTargetpax((float) targetpaxarray);
							}
						}
					}

					if (lTotalEffectiveJSONObj.has("Host_priceperformance_revenue")
							&& lTotalEffectiveJSONObj.get("Host_priceperformance_revenue") != null
							&& !"null".equalsIgnoreCase(
									lTotalEffectiveJSONObj.get("Host_priceperformance_revenue").toString())
							&& !"[]".equalsIgnoreCase(
									lTotalEffectiveJSONObj.get("Host_priceperformance_revenue").toString())) {
						lTargetPricePerformanceArray = new JSONArray(
								lTotalEffectiveJSONObj.get("Host_priceperformance_revenue").toString());
					}
					if (lTotalEffectiveJSONObj.has("Host_priceelasticity_revenue")
							&& lTotalEffectiveJSONObj.get("Host_priceelasticity_revenue") != null
							&& !"null".equalsIgnoreCase(
									lTotalEffectiveJSONObj.get("Host_priceelasticity_revenue").toString())
							&& !"[]".equalsIgnoreCase(
									lTotalEffectiveJSONObj.get("Host_priceelasticity_revenue").toString())) {
						lTargetPriceElasticityArray = new JSONArray(
								lTotalEffectiveJSONObj.get("Host_priceelasticity_revenue").toString());
					}

					System.out.println(
							"lTotalEffective" + lTotalEffective.getPax() + "," + lTotalEffective.getPricePerformance());
					lTotalEffectiveList.add(lTotalEffective);
				}

				for (int i = 0; i < lTotalEffectiveList.size(); i++) {
					System.out.println(
							"Total effective in effective fare for each screen" + lTotalEffectiveList.get(i).getPax());
				}

				Map<String, TotalEffectiveIneffective> map = new HashMap<String, TotalEffectiveIneffective>();

				TotalEffectiveIneffective lTotalEffectiveIneffective = null;
				for (TotalEffective lTotalEffectiveObj : lTotalEffectiveList) {
					if (!map.containsKey(lTotalEffectiveObj.getCombinationkey())) {
						lTotalEffectiveIneffective = new TotalEffectiveIneffective();
						lTotalEffectiveIneffective.setCombinationkey(lTotalEffectiveObj.getCombinationkey());
						lTotalEffectiveIneffective.setRegion(lTotalEffectiveObj.getRegion());
						lTotalEffectiveIneffective.setCountry(lTotalEffectiveObj.getCountry());
						lTotalEffectiveIneffective.setOrigin(lTotalEffectiveObj.getOrigin());
						lTotalEffectiveIneffective.setPos(lTotalEffectiveObj.getPos());
						lTotalEffectiveIneffective.setDestination(lTotalEffectiveObj.getDestination());
						lTotalEffectiveIneffective.setCompartment(lTotalEffectiveObj.getCompartment());
						lTotalEffectiveIneffective.setFare_basis(lTotalEffectiveObj.getFare_basis());
						lTotalEffectiveIneffective.setFare(lTotalEffectiveObj.getFare());
						int totalPax = lTotalEffectiveObj.getPax();
						float totalPax_lastyr = lTotalEffectiveObj.getPax_lastyr();
						lTotalEffectiveIneffective.setTotalPax((totalPax));

						lTotalEffectiveIneffective.setTotalPax_lastyr(Math.round(totalPax_lastyr));

						float totalRevenue = lTotalEffectiveObj.getRevenue();
						lTotalEffectiveIneffective.setTotalrevenue(Math.round(totalRevenue));

						float totaltargetpax = lTotalEffectiveObj.getTargetpax();
						lTotalEffectiveIneffective.setTotaltargetpax(Math.round(totaltargetpax));

						lTotalEffectiveIneffective.setTotalPricePerformance(Math.round(totalPricePerformance));

						lTotalEffectiveIneffective.setTotalPriceElasticity(Math.round(totalPriceElasticity));
						map.put(lTotalEffectiveObj.getCombinationkey(), lTotalEffectiveIneffective);
					} else {
						for (String lKey : map.keySet()) {
							if (lTotalEffectiveObj.getCombinationkey().equals(lKey)) {
								lTotalEffectiveIneffective = map.get(lKey);
							}
						}

						lTotalEffectiveIneffective.setOrigin(lTotalEffectiveObj.getOrigin());
						lTotalEffectiveIneffective.setCountry(lTotalEffectiveObj.getCountry());
						lTotalEffectiveIneffective.setRegion(lTotalEffectiveObj.getRegion());
						lTotalEffectiveIneffective.setDestination(lTotalEffectiveObj.getDestination());
						lTotalEffectiveIneffective.setCompartment(lTotalEffectiveObj.getCompartment());
						lTotalEffectiveIneffective.setPos(lTotalEffectiveObj.getPos());
						float totalPax = lTotalEffectiveObj.getPax() + lTotalEffectiveIneffective.getTotalPax();
						float totalPax_lastyr = lTotalEffectiveObj.getPax_lastyr()
								+ lTotalEffectiveIneffective.getTotalPax_lastyr();
						lTotalEffectiveIneffective.setTotalPax((totalPax));

						lTotalEffectiveIneffective.setTotalPax_lastyr((int) (totalPax_lastyr));

						float totaltargetpax = lTotalEffectiveObj.getTargetpax()
								+ lTotalEffectiveIneffective.getTotaltargetpax();
						lTotalEffectiveIneffective.setTotaltargetpax((totaltargetpax));

						float totalRevenue = lTotalEffectiveObj.getRevenue()
								+ lTotalEffectiveIneffective.getTotalrevenue();
						lTotalEffectiveIneffective.setTotalrevenue((totalRevenue));

						lTotalEffectiveIneffective.setTotalPricePerformance((totalPricePerformance));
						lTotalEffectiveIneffective.setTotalPriceElasticity((totalPriceElasticity));
					}

				}
				for (String key : map.keySet()) {
					lTotalEffectiveIneffective = map.get(key);
					lTotalEffectiveIneffective.setCombinationkey(map.get(key).getCombinationkey());
					lTotalEffectiveIneffective.setCountry(map.get(key).getCountry());
					lTotalEffectiveIneffective.setRegion(map.get(key).getRegion());
					lTotalEffectiveIneffective.setOrigin(map.get(key).getOrigin());
					lTotalEffectiveIneffective.setDestination(map.get(key).getDestination());
					lTotalEffectiveIneffective.setCompartment(map.get(key).getCompartment());
					lTotalEffectiveIneffective.setPos(map.get(key).getPos());
					lTotalEffectiveIneffective.setFare_basis(map.get(key).getFare_basis());
					lTotalEffectiveIneffective.setFare(map.get(key).getFare());
					float totalPax = map.get(key).getTotalPax();
					lPaxYTD = totalPax;
					totalPaxYTD += lPaxYTD;
					lTotalEffectiveIneffective.setTotalPax((map.get(key).getTotalPax()));
					lTotalEffectiveIneffective.setPaxYTD((lPaxYTD));

					float totalRevenue = map.get(key).getTotalrevenue();
					lrevenueYTD = totalRevenue;
					totalrevenueYTD += lrevenueYTD;
					lTotalEffectiveIneffective.setTotalrevenue(Math.round(map.get(key).getTotalrevenue()));

					avgfare = totalRevenue / totalPax;
					lTotalEffectiveIneffective.setAvgfare(Math.round(avgfare));

					float totalPriceperformance = map.get(key).getTotalPricePerformance();
					lPricePerformancceYTD = totalPriceperformance;
					lTotalEffectiveIneffective
							.setTotalPricePerformance(Math.round(map.get(key).getTotalPricePerformance()));
					lTotalEffectiveIneffective.setPricePerformanceYTD(lPricePerformancceYTD);
					int totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					if (totalPax_lastyr != 0) {
						lPaxVLYR = (((float) totalPax - (float) totalPax_lastyr) / totalPax_lastyr) * 100;
						lTotalEffectiveIneffective.setPaxVLYR(Math.round(lPaxVLYR));

					} else {
						lTotalEffectiveIneffective.setPaxVLYR(Math.round(lPaxVLYR));
					}

					totalPaxVLYR += totalPax_lastyr;
					double targetPax = 0;

					targetPax = map.get(key).getTotaltargetpax();

					if (targetPax > 0) {
						lPaxVTGT = (((float) totalPax - (float) targetPax) / (float) targetPax) * 100;
						totalPax_target += targetPax;

						lTotalEffectiveIneffective.setPaxVTGT(Math.round(lPaxVTGT));
					} else {
						lTotalEffectiveIneffective.setPaxVTGT(Math.round(lPaxVTGT));
					}
					totalPricePerformance = map.get(key).getTotalPricePerformance();
					float totalPricePerformance_lastyr = Math.round(map.get(key).getTotalPricePerformance_lastyr());
					if (totalPricePerformance_lastyr != 0) {
						lPriceperformanceVLYR = ((totalPricePerformance - totalPricePerformance_lastyr)
								/ totalPricePerformance_lastyr) * 100;
						totalPriceperformanceVLYR += totalPricePerformance_lastyr;
						lTotalEffectiveIneffective.setPricePerformanceVLYR(Math.round(lPriceperformanceVLYR));
					} else {
						lTotalEffectiveIneffective.setPricePerformanceVLYR(Math.round(lPriceperformanceVLYR));
					}
					float targetPriceperformance = 0.0f;
					if (lTargetPricePerformanceArray != null) {
						if (lTargetPricePerformanceArray.length() > 0) {
							for (int i = 0; i < lTargetPricePerformanceArray.length(); i++) {
								targetPriceperformance += Float
										.parseFloat(lTargetPricePerformanceArray.get(i).toString());
							}
						}
					} else {
						lTargetPricePerformanceArray = null;
					}
					if (targetPriceperformance > 0) {
						lPriceperformanceVTGT = ((totalPriceperformance - targetPriceperformance)
								/ targetPriceperformance) * 100;
						totalPiceperformancetarget += targetPriceperformance;
						lTotalEffectiveIneffective.setPricePerformanceVTGT(Math.round(lPriceperformanceVTGT));
					} else {
						lTotalEffectiveIneffective.setPricePerformanceVTGT(Math.round(lPriceperformanceVTGT));
					}
					totalPriceElasticity = map.get(key).getTotalPriceElasticity();
					float totalPriceElasticity_lastyr = map.get(key).getTotalPriceElasticity_lastyr();
					if (totalPriceElasticity_lastyr != 0) {
						lPriceElasticityVLYR = ((totalPriceElasticity - totalPriceElasticity_lastyr)
								/ totalPriceElasticity_lastyr) * 100;
						lTotalEffectiveIneffective.setPaxVLYR(Math.round(lPaxVLYR));
						totalPriceElasticityVLYR += totalPriceElasticity_lastyr;
					} else {
						lTotalEffectiveIneffective.setPaxVLYR(Math.round(lPaxVLYR));
					}
					float targetPriceElasticity = 0;
					if (lTargetPriceElasticityArray != null) {
						if (lTargetPriceElasticityArray.length() > 0) {
							for (int i = 0; i < lTargetPriceElasticityArray.length(); i++) {
								targetPriceElasticity += lTargetPriceElasticityArray.getInt(i);
							}
						}
					} else {
						lTargetPriceElasticityArray = null;
					}
					if (targetPriceElasticity > 0) {
						lPriceElasticityVTGT = ((totalPriceElasticity - targetPriceElasticity) / targetPriceElasticity)
								* 100;
						totalPicerlasticity += targetPriceElasticity;
						lTotalEffectiveIneffective.setPriceElasticityVTGT(Math.round(lPriceElasticityVTGT));

					} else {
						lTotalEffectiveIneffective.setPriceElasticityVTGT(Math.round(lPriceElasticityVTGT));
					}
					lTotalEffectiveIneffectiveList.add(lTotalEffectiveIneffective);

				}

				TotaleffectiveIneffectiveTotalResponse lModel = new TotaleffectiveIneffectiveTotalResponse();
				lModel.setTotalpaxytd(CalculationUtil.round(totalPaxYTD, 1));
				float lTotalPaxVLYR = 0;
				if (totalPaxVLYR != 0 && totalPaxVLYR > 0) {
					lTotalPaxVLYR = ((totalPaxYTD - totalPaxVLYR) / totalPaxVLYR) * 100;
					lModel.setTotalpaxvlyr(CalculationUtil.round(lTotalPaxVLYR, 1));
				} else {
					lModel.setTotalpaxvlyr(lTotalPaxVLYR);
				}
				float lTotalPaxVTGT = 0;
				if (totalPax_target != 0 && totalPax_target > 0) {
					lTotalPaxVTGT = ((totalPaxYTD - totalPax_target) / totalPax_target) * 100;
					lModel.setTotalpaxvtgt(CalculationUtil.round(lTotalPaxVTGT, 1));
				} else {
					lModel.setTotalpaxvtgt(lTotalPaxVTGT);
				}

				lModel.setTotalpriceperformanceytd(totalPricePerformance);
				float lTotalPriceperformanceVLYR = 0;
				if (totalPriceperformanceVLYR != 0 && totalPriceperformanceVLYR > 0) {
					lTotalPriceperformanceVLYR = ((totalPricePerformance - totalPriceperformanceVLYR)
							/ totalPriceperformanceVLYR) * 100;
					lModel.setTotalpriceperformancevlyr(CalculationUtil.round(lTotalPriceperformanceVLYR, 1));
				} else {
					lModel.setTotalpriceperformancevlyr(CalculationUtil.round(lTotalPriceperformanceVLYR, 1));
				}
				float lTotalpriceperformanceVTGT = 0;
				if (totalPiceperformancetarget != 0 && totalPiceperformancetarget > 0) {
					lTotalpriceperformanceVTGT = ((totalPricePerformance - totalPiceperformancetarget)
							/ totalPiceperformancetarget) * 100;
					lModel.setTotalpriceperformancevtgt(Math.round(lTotalpriceperformanceVTGT));
				} else {
					lModel.setTotalpriceperformancevtgt(Math.round(lTotalpriceperformanceVTGT));
				}

				lModel.setTotalpriceelasticityytd(totalPriceElasticity);
				float lTotalPriceelasticityVLYR = 0;
				if (totalPriceElasticityVLYR != 0 && totalPriceperformanceVLYR > 0) {
					lTotalPriceelasticityVLYR = ((totalPricePerformance - totalPriceElasticityVLYR)
							/ totalPriceElasticityVLYR) * 100;
					lModel.setTotalpriceelasticityvlyr(CalculationUtil.round(lTotalPriceelasticityVLYR, 1));
				} else {
					lModel.setTotalpriceelasticityvlyr(CalculationUtil.round(lTotalPriceelasticityVLYR, 1));
				}
				float lTotalpriceelasticityVTGT = 0;
				if (totalPicerlasticity != 0 && totalPicerlasticity > 0) {
					lTotalpriceelasticityVTGT = ((totalPicerlasticity - totalPiceperformancetarget)
							/ totalPiceperformancetarget) * 100;
					lModel.setTotalpriceelasticityvtgt(CalculationUtil.round(lTotalpriceelasticityVTGT, 1));
				} else {
					lModel.setTotalpriceelasticityvtgt(CalculationUtil.round(lTotalpriceelasticityVTGT, 1));
				}

				lMTotalsList.add(lModel);

				responseTotalEffectiveIneffectiveFaresMap.put("Total Effective_Ineffective Fares totals", lMTotalsList);
				responseTotalEffectiveIneffectiveFaresMap.put("Total Effective_Ineffective Fares",
						lTotalEffectiveIneffectiveList);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseTotalEffectiveIneffectiveFaresMap;
	}

	@Override
	public Map<String, Object> getRevenueSplit(RequestModel pRequestModel) {

		float lRevenueVLYR = 0;
		float lYieldVLYR = 0;
		float lYieldVTGT = 0;
		float lRevenueVTGT = 0;
		float lPaxVLYR = 0;
		float lPaxVTGT = 0;

		float totalSalesrevenueYTD = 0;

		float totalFlownrevenueYTD = 0;

		float totalSalesRevenue_LY = 0;
		float targetRevenue = 0;
		float totalrevenuetarget = 0;
		float totalPaxYTD = 0;
		float totalyieldtarget = 0;
		float totalPaxtarget = 0;
		int totalpaxpercent = 0;
		float lTotalspax1 = 0;
		float lTotalspax = 0;
		String Key = null;

		float lTotalsAdultpax = 0;
		float lTotalsChdpax = 0;
		float lTotalsInfpax = 0;
		float fare = 0;
		float fareYTD = 0;
		int totalpaxytd = 0;
		float totalflownrevenueytd = 0;
		float totaladultpaxperc = 0;
		float totalchdpaxperc = 0;
		float totalinfpaxperc = 0;
		float lTotalPax = 0;

		float yield = 0;
		float totalsalesrevenueytd = 0;

		float totalsalesrevenuelastyrytd = 0;
		float totalyieldytd = 0;
		float totalyieldlastyr = 0;

		float completeSalesRevenue = 0;
		int completePax = 0;
		int totalPaxLastYr = 0;
		float totalPaxs = 0;
		double flownrevenuelastyr = 0;
		float totalChdpax = 0;
		float totalInfpax = 0;
		double forcastpax = 0;
		JSONArray lTargetRevenueArray = null;
		JSONArray lTargetPaxArray = null;
		JSONArray lTargetYieldArray = null;
		JSONArray revenuearray = null;
		JSONArray forcastrevenuearray = null;
		JSONArray forcastpaxarray = null;
		JSONArray paxarray = null;
		JSONArray revenuelastyrarray = null;
		JSONArray paxlastyrarray = null;
		JSONArray distancearray = null;
		int adpax = 0;
		int chpax = 0;
		int inpax = 0;
		JSONArray flownrevenuelastyrarray = null;
		JSONArray carrierList = null;
		float lTotalTargetPax = 0;
		float lTotalforcastPax = 0;
		float lTotaltargetPax = 0;
		float lTotalTargetRevenue = 0;
		double forcastrevenue = 0;
		float lTotalforcastRevenue = 0;
		float lCapacitylastyr = 0;
		float lCapacity = 0;
		double revenuelastyr = 0;
		JSONArray lCapacityArray = null;
		JSONArray lCapacitylastyrArray = null;
		JSONArray flownpaxlastyrarray = null;
		double flownpaxlastyr = 0;
		Map<String, Object> TotalMap1 = new HashMap<String, Object>();
		Map<String, Object> TotalMap2 = new HashMap<String, Object>();
		Map<String, Object> TotalMap3 = new HashMap<String, Object>();
		List<FilterModel> lRevenueList = new ArrayList<FilterModel>();
		Map<String, Float> carrierPaxMap = new HashMap<String, Float>();
		Map<String, Float> capacitylastyrMap = new HashMap<String, Float>();
		List<RevenueSplitModel> lRevenueSplitModelList = new ArrayList<RevenueSplitModel>();
		List<RevenueSplitTotalResponse> lMTotalsList = new ArrayList<RevenueSplitTotalResponse>();
		Map<String, Object> responseRevenueSplitModelMap = new HashMap<String, Object>();
		FilterModel lRevenue = null;
		ArrayList<DBObject> lRevenueSplitModelObj = mKpiDao.getRevenueSplit(pRequestModel);
		JSONArray lRevenueSplitModelData = new JSONArray(lRevenueSplitModelObj);
		try {
			if (lRevenueSplitModelData != null) {

				for (int i = 0; i < lRevenueSplitModelData.length(); i++) {
					JSONObject lRevenueJSONObj = lRevenueSplitModelData.getJSONObject(i);
					lRevenue = new FilterModel();

					System.out.println("lRevenueJSONObj" + lRevenueJSONObj);

					if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
							&& pRequestModel.getUser().equals("Global Head")) {

						Key = FilterUtil.getFilter(pRequestModel);

					}
					if (lRevenueJSONObj.has("dep_date") && lRevenueJSONObj.get("dep_date") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("dep_date").toString())) {
						lRevenue.setDepartureDate(lRevenueJSONObj.get("dep_date").toString());

					}

					if (lRevenueJSONObj.has("region") && lRevenueJSONObj.get("region") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("region").toString())) {
						lRevenue.setRegion(lRevenueJSONObj.get("region").toString());

					}

					if (lRevenueJSONObj.has("country") && lRevenueJSONObj.get("country") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("country").toString())) {
						lRevenue.setCountry(lRevenueJSONObj.get("country").toString());
					}

					if (lRevenueJSONObj.has("pos") && lRevenueJSONObj.get("pos") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("pos").toString())) {
						lRevenue.setPos(lRevenueJSONObj.get("pos").toString());
					}

					if (lRevenueJSONObj.has("origin") && lRevenueJSONObj.get("origin") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("origin").toString())) {
						lRevenue.setOrigin(lRevenueJSONObj.get("origin").toString());
					}
					if (lRevenueJSONObj.has("destination") && lRevenueJSONObj.get("destination") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("destination").toString())) {
						lRevenue.setDestination(lRevenueJSONObj.get("destination").toString());
					}
					if (lRevenueJSONObj.has("compartment") && lRevenueJSONObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("compartment").toString())) {
						lRevenue.setCompartment(lRevenueJSONObj.get("compartment").toString());
					}
					JSONArray lFlownPaxArray = null;
					double flown = 0;
					if (lRevenueJSONObj.has("flown_pax") && lRevenueJSONObj.get("flown_pax") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("flown_pax").toString())
							&& !"[]".equalsIgnoreCase(lRevenueJSONObj.get("flown_pax").toString())) {
						lFlownPaxArray = new JSONArray(lRevenueJSONObj.get("flown_pax").toString());
						if (lFlownPaxArray != null) {
							if (lFlownPaxArray.length() > 0) {
								for (int j = 0; j < lFlownPaxArray.length(); j++) {
									if (lFlownPaxArray != null
											&& !lFlownPaxArray.get(j).toString().equalsIgnoreCase("null")
											&& !lFlownPaxArray.get(j).toString().equalsIgnoreCase("[]")) {
										flown = Utility.findSum(lFlownPaxArray);
										lRevenue.setFlownpax((float) flown);
									}
								}
							}
						}
					}
					JSONArray lFlownRevenueArray = null;
					double flownrevenue = 0;
					if (lRevenueJSONObj.has("flown_revenue") && lRevenueJSONObj.get("flown_revenue") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("flown_revenue").toString())
							&& !"[]".equalsIgnoreCase(lRevenueJSONObj.get("flown_revenue").toString())) {
						lFlownRevenueArray = new JSONArray(lRevenueJSONObj.get("flown_revenue").toString());
						if (lFlownRevenueArray != null) {
							if (lFlownRevenueArray.length() > 0) {
								for (int j = 0; j < lFlownRevenueArray.length(); j++) {
									if (lFlownRevenueArray != null
											&& !lFlownRevenueArray.get(j).toString().equalsIgnoreCase("null")
											&& !lFlownRevenueArray.get(j).toString().equalsIgnoreCase("[]")) {
										flownrevenue = Utility.findSum(lFlownRevenueArray);
										lRevenue.setFlownrevenue((float) flownrevenue);
									}
								}
							}
						}
					}

					if (lRevenueJSONObj.has("forecast_revenue") && lRevenueJSONObj.get("forecast_revenue") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("forecast_revenue").toString())) {

						forcastrevenuearray = new JSONArray(lRevenueJSONObj.get("forecast_revenue").toString());
						if (forcastrevenuearray != null) {
							if (forcastrevenuearray.length() > 0) {
								forcastrevenue = Utility.findSum(forcastrevenuearray);
								lRevenue.setRevenueForcast((float) (forcastrevenue));
							}
						}

					}
					if (forcastrevenuearray != null) {
						for (int m = 0; m < forcastrevenuearray.length(); m++) {
							if (!"null".equalsIgnoreCase(forcastrevenuearray.get(m).toString()))
								lTotalforcastRevenue += Float.parseFloat(forcastrevenuearray.get(m).toString());
						}
					}

					if (lRevenueJSONObj.has("forecast_pax") && lRevenueJSONObj.get("forecast_pax") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("forecast_pax").toString())) {

						forcastpaxarray = new JSONArray(lRevenueJSONObj.get("forecast_pax").toString());
						if (forcastpaxarray != null) {
							if (forcastpaxarray.length() > 0) {
								forcastpax = Utility.findSum(forcastpaxarray);
								lRevenue.setPaxForcast((float) (forcastpax));
							}
						}

					}
					if (forcastpaxarray != null) {
						for (int m = 0; m < forcastpaxarray.length(); m++) {
							if (!"null".equalsIgnoreCase(forcastpaxarray.get(m).toString()))
								lTotalforcastPax += Float.parseFloat(forcastpaxarray.get(m).toString());
						}
					}

					double revenue = 0;
					if (lRevenueJSONObj.has("revenue") && lRevenueJSONObj.get("revenue") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("revenue").toString())) {

						revenuearray = new JSONArray(lRevenueJSONObj.get("revenue").toString());
						if (revenuearray != null) {
							if (revenuearray.length() > 0) {
								revenue = Utility.findSum(revenuearray);
								lRevenue.setRevenue((float) (revenue));
							}
						}

					}
					completeSalesRevenue += lRevenue.getRevenue();
					double distance = 0;
					if (lRevenueJSONObj.has("distance") && lRevenueJSONObj.get("distance") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("distance").toString())) {
						distancearray = new JSONArray(lRevenueJSONObj.get("distance").toString());
						if (distancearray != null) {
							if (distancearray.length() > 0) {
								distance = Utility.findSum(distancearray);
								lRevenue.setOd_Distance((float) distance);
							}
						}

					}

					if (lRevenueJSONObj.has("flown_pax_1") && lRevenueJSONObj.get("flown_pax_1") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("flown_pax_1").toString())) {
						flownpaxlastyrarray = new JSONArray(lRevenueJSONObj.get("flown_pax_1").toString());
						if (flownpaxlastyrarray != null) {
							if (flownpaxlastyrarray.length() > 0) {
								flownpaxlastyr = Utility.findSum(flownpaxlastyrarray);
								lRevenue.setFlownpaxlastyr((float) flownpaxlastyr);
							}
						}
					}
					if (lRevenueJSONObj.has("flown_revenue_1") && lRevenueJSONObj.get("flown_revenue_1") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("flown_revenue_1").toString())) {
						flownrevenuelastyrarray = new JSONArray(lRevenueJSONObj.get("flown_revenue_1").toString());
						if (flownrevenuelastyrarray != null) {
							if (flownrevenuelastyrarray.length() > 0) {
								flownrevenuelastyr = Utility.findSum(flownrevenuelastyrarray);
								lRevenue.setFlownrevenue_lastyr((float) flownrevenuelastyr);
							}
						}
					}

					double pax = 0;
					if (lRevenueJSONObj.has("pax") && lRevenueJSONObj.get("pax") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("pax").toString())) {
						paxarray = new JSONArray(lRevenueJSONObj.get("pax").toString());
						if (paxarray != null) {
							if (paxarray.length() > 0) {
								pax = Utility.findSum(paxarray);
								lRevenue.setPax((int) pax);
							}
						}

					}

					completePax += lRevenue.getPax();
					if (lRevenueJSONObj.has("pax_type") && lRevenueJSONObj.get("pax_type") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("pax_type").toString())
							&& !"[]".equalsIgnoreCase(lRevenueJSONObj.get("pax_type").toString())) {
						lRevenue.setPaxType((lRevenueJSONObj.get("pax_type").toString().trim()));

					}

					if (lRevenueJSONObj.has("revenue_1") && lRevenueJSONObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("revenue_1").toString())) {
						revenuelastyrarray = new JSONArray(lRevenueJSONObj.get("revenue_1").toString());
						if (revenuelastyrarray != null) {
							if (revenuelastyrarray.length() > 0) {
								revenuelastyr = Utility.findSum(revenuelastyrarray);
								lRevenue.setRevenuelastyr((float) revenuelastyr);
							}
						}
					}
					double paxlastyr = 0;
					if (lRevenueJSONObj.has("pax_1") && lRevenueJSONObj.get("pax_1") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("pax_1").toString())) {
						paxlastyrarray = new JSONArray(lRevenueJSONObj.get("pax_1").toString());
						if (paxlastyrarray != null) {
							if (paxlastyrarray.length() > 0) {
								paxlastyr = Utility.findSum(paxlastyrarray);
								lRevenue.setPaxlastyr((int) paxlastyr);
							}
						}
					}

					if (lRevenueJSONObj.has("target_revenue") && lRevenueJSONObj.get("target_revenue") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("target_revenue").toString())
							&& !"[]".equalsIgnoreCase(lRevenueJSONObj.get("target_revenue").toString())) {
						lTargetRevenueArray = new JSONArray(lRevenueJSONObj.get("target_revenue").toString());
					}
					if (lTargetRevenueArray != null) {
						for (int m = 0; m < lTargetRevenueArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lTargetRevenueArray.get(m).toString()))
								lTotalTargetRevenue += Float.parseFloat(lTargetRevenueArray.get(m).toString());
						}
					}
					double targetpax = 0;
					if (lRevenueJSONObj.has("target_pax") && lRevenueJSONObj.get("target_pax") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("target_pax").toString())
							&& !"[]".equalsIgnoreCase(lRevenueJSONObj.get("target_pax").toString())) {
						lTargetPaxArray = new JSONArray(lRevenueJSONObj.get("target_pax").toString());
						targetpax = Utility.findSum(lTargetPaxArray);
						lRevenue.setTargetpax((float) targetpax);

					}
					if (lTargetPaxArray != null) {
						for (int m = 0; m < lTargetPaxArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lTargetPaxArray.get(m).toString()))
								lTotaltargetPax += Float.parseFloat(lTargetPaxArray.get(m).toString());
						}
					}

					if (lRevenueJSONObj.has("Host_yield_target") && lRevenueJSONObj.get("Host_yield_target") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("Host_yield_target").toString())
							&& !"[]".equalsIgnoreCase(lRevenueJSONObj.get("Host_yield_target").toString())) {
						lTargetYieldArray = new JSONArray(lRevenueJSONObj.get("Host_yield_target").toString());
					}
					if (lRevenueJSONObj.has("capacity_1") && lRevenueJSONObj.get("capacity_1") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("capacity_1").toString())
							&& !"[]".equalsIgnoreCase(lRevenueJSONObj.get("capacity_1").toString())) {
						lCapacitylastyrArray = new JSONArray(lRevenueJSONObj.get("capacity_1").toString());
					}
					if (lCapacitylastyrArray != null) {
						for (int m = 0; m < lCapacitylastyrArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lCapacitylastyrArray.get(m).toString()))
								lCapacitylastyr += Float.parseFloat(lCapacitylastyrArray.get(m).toString());
						}
					}
					if (lRevenueJSONObj.has("capacity") && lRevenueJSONObj.get("capacity") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("capacity").toString())
							&& !"[]".equalsIgnoreCase(lRevenueJSONObj.get("capacity").toString())) {
						lCapacityArray = new JSONArray(lRevenueJSONObj.get("capacity").toString());
					}
					if (lCapacityArray != null) {
						for (int m = 0; m < lCapacityArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lCapacityArray.get(m).toString()))
								lCapacity += Float.parseFloat(lCapacityArray.get(m).toString());
						}
					}
					if (lRevenueJSONObj.get("capacity_airline") != null
							&& !"null".equalsIgnoreCase(lRevenueJSONObj.get("capacity_airline").toString())
							&& !"[]".equalsIgnoreCase(lRevenueJSONObj.get("capacity_airline").toString())) {
						carrierList = new JSONArray(lRevenueJSONObj.get("capacity_airline").toString());
					}
					if (carrierList != null) {
						for (int k = 0; k < carrierList.length(); k++) {
							carrierPaxMap.put(carrierList.getString(k), (float) lCapacityArray.getDouble(k));
						}
						for (int k = 0; k < carrierList.length(); k++) {
							capacitylastyrMap.put(carrierList.getString(k), (float) lCapacitylastyrArray.getDouble(k));
						}
					}

					// months and days
					int lMonth = 0;
					int lDays = 0;
					if (!"null".equalsIgnoreCase(lRevenueJSONObj.get("dep_date").toString())) {
						lMonth = Utility.findMonth(lRevenueJSONObj.get("dep_date").toString());
						lDays = Utility.findDay(lRevenueJSONObj.get("dep_date").toString());
						lRevenue.setMonths(lMonth);
						lRevenue.setDays(lDays);
					}
					System.out.println("lRevenue and pax" + lRevenue.getRevenue() + "," + lRevenue.getPax());

					// Key
					String lAggregationKey = FilterUtil.getAggregationKey(lRevenue, Key);
					lRevenue.setFilterKey(lAggregationKey);
					String key2 = lRevenue.getFilterKey().substring(0, lRevenue.getFilterKey().length() - 1);
					lRevenue.setRevenueShareKey(key2);

					lRevenueList.add(lRevenue);

				}

				int paxy = 0;
				int Paxj = 0;
				int totalPaxy = 0;
				int totalPaxj = 0;

				int pax = 0;
				float ltotalspaxy = 0;
				float ltotalspaxj = 0;
				float lTotalspaxj = 0;
				RevenueShare lrevenueSplitModel = new RevenueShare();
				Map<String, RevenueShare> revenueShareMap = new HashMap<String, RevenueShare>();
				for (FilterModel revenueObj : lRevenueList) {
					if (!revenueShareMap.containsKey(revenueObj.getRevenueShareKey())) {
						lrevenueSplitModel = new RevenueShare();
						lrevenueSplitModel.setRevenueShareKey(revenueObj.getRevenueShareKey());
						lrevenueSplitModel.setRegion(revenueObj.getRegion());
						lrevenueSplitModel.setCountry(revenueObj.getCountry());
						lrevenueSplitModel.setPos(revenueObj.getPos());
						lrevenueSplitModel.setOrigin(revenueObj.getOrigin());
						lrevenueSplitModel.setDestination(revenueObj.getDestination());
						lrevenueSplitModel.setOd(revenueObj.getOrigin() + revenueObj.getDestination());
						lrevenueSplitModel.setCompartment(revenueObj.getCompartment());

						// Sales Revenue & Sales revenue last yr
						float totalsalesRevenue = revenueObj.getRevenue();
						float totalsalesRevenue_lastyr = revenueObj.getRevenuelastyr();
						lrevenueSplitModel.setTotalRevenue((totalsalesRevenue));
						lrevenueSplitModel.setTotalRevenue_lastyr((totalsalesRevenue_lastyr));

						// Pax
						int totalPax = revenueObj.getPax();
						lrevenueSplitModel.setTotalPax((totalPax));
						int totalPax_lastyr = revenueObj.getPaxlastyr();
						lrevenueSplitModel.setTotalPax_lastyr((totalPax_lastyr));

						revenueShareMap.put(revenueObj.getRevenueShareKey(), lrevenueSplitModel);
					} else {
						for (String lKey : revenueShareMap.keySet()) {
							if (revenueObj.getRevenueShareKey().equals(lKey)) {
								lrevenueSplitModel = revenueShareMap.get(lKey);
							}
						}
						lrevenueSplitModel.setRevenueShareKey(revenueObj.getRevenueShareKey());
						lrevenueSplitModel.setRegion(revenueObj.getRegion());
						lrevenueSplitModel.setCountry(revenueObj.getCountry());
						lrevenueSplitModel.setPos(revenueObj.getPos());
						lrevenueSplitModel.setOrigin(revenueObj.getOrigin());
						lrevenueSplitModel.setDestination(revenueObj.getDestination());
						lrevenueSplitModel.setOd(revenueObj.getOrigin() + revenueObj.getDestination());
						lrevenueSplitModel.setCompartment(revenueObj.getCompartment());
						// Sales Revenue
						float totalsalesRevenue = revenueObj.getRevenue() + lrevenueSplitModel.getTotalRevenue();
						float totalsalesRevenue_lastyr = revenueObj.getRevenuelastyr()
								+ lrevenueSplitModel.getTotalRevenue_lastyr();
						lrevenueSplitModel.setTotalRevenue((totalsalesRevenue));
						lrevenueSplitModel.setTotalRevenue_lastyr((totalsalesRevenue_lastyr));

						// Pax
						int totalPax = revenueObj.getPax() + lrevenueSplitModel.getTotalPax();
						lrevenueSplitModel.setTotalPax((totalPax));
						float totalPax_lastyr = revenueObj.getPaxlastyr() + lrevenueSplitModel.getTotalPax_lastyr();
						lrevenueSplitModel.setTotalPax_lastyr((totalPax_lastyr));

					}

				}

				Map<String, RevenueSplitModel> map = new HashMap<String, RevenueSplitModel>();

				RevenueSplitModel lRevenueSplitModel = new RevenueSplitModel();
				for (FilterModel lRevenueObj : lRevenueList) {
					if (!map.containsKey(lRevenueObj.getFilterKey())) {
						lRevenueSplitModel = new RevenueSplitModel();
						lRevenueSplitModel.setCombinationkey(lRevenueObj.getFilterKey());
						lRevenueSplitModel.setRegion(lRevenueObj.getRegion());
						lRevenueSplitModel.setCountry(lRevenueObj.getCountry());
						lRevenueSplitModel.setPos(lRevenueObj.getPos());
						lRevenueSplitModel.setOrigin(lRevenueObj.getOrigin());
						lRevenueSplitModel.setDestination(lRevenueObj.getDestination());
						lRevenueSplitModel.setDepartureDate(lRevenueObj.getDepartureDate());
						lRevenueSplitModel.setOd(lRevenueObj.getOrigin() + lRevenueObj.getDestination());
						lRevenueSplitModel.setCompartment(lRevenueObj.getCompartment());
						String paxtype = (lRevenueObj.getPaxType());
						lRevenueSplitModel.setPaxtype(paxtype);
						lRevenueSplitModel.setAvgfare(fare);
						// flown pax
						float flownpax = lRevenueObj.getFlownpax();
						lRevenueSplitModel.setFlownpax(flownpax);
						float lflownpaxlastyr = lRevenueObj.getFlownpaxlastyr();
						lRevenueSplitModel.setTotalflownpaxlastyr(lflownpaxlastyr);
						// flown Revenue
						float flownrevenue = lRevenueObj.getFlownrevenue();
						lRevenueSplitModel.setFlownrevenue(flownrevenue);
						float lflownrevenuelastyr = lRevenueObj.getFlownrevenue_lastyr();
						lRevenueSplitModel.setTotalFlownRevenue_lastyr(lflownrevenuelastyr);
						// Pax
						int totalPax = lRevenueObj.getPax();
						lRevenueSplitModel.setTotalPax((totalPax));
						int totalPax_lastyr = lRevenueObj.getPaxlastyr();
						lRevenueSplitModel.setTotalPax_lastyr((totalPax_lastyr));
						// Adult Pax
						if (lRevenueSplitModel.getPaxtype().equals("ADT")
								|| (lRevenueSplitModel.getPaxtype().equals("ADT ")
										&& (lRevenueSplitModel.getCompartment().equals("Y")
												|| lRevenueSplitModel.getCompartment().equals("J")
												|| lRevenueSplitModel.getCompartment().equals("F")))) {

							int Adultpax = lRevenueObj.getPax();
							lRevenueSplitModel.setTotaladultpaxYtd((Adultpax));
						}
						// Infant Pax
						if (lRevenueSplitModel.getPaxtype().equals("INF ")
								|| (lRevenueSplitModel.getPaxtype().equals("INF")
										&& (lRevenueSplitModel.getCompartment().equals("Y")
												|| lRevenueSplitModel.getCompartment().equals("J")
												|| lRevenueSplitModel.getCompartment().equals("F")))) {

							int Infpax = lRevenueObj.getPax();
							lRevenueSplitModel.setTotalinfpaxytd((Infpax));
						}
						// Child Pax
						if (lRevenueSplitModel.getPaxtype().equals("CHD")
								|| (lRevenueSplitModel.getPaxtype().equals("CHD ")
										&& (lRevenueSplitModel.getCompartment().equals("Y")
												|| lRevenueSplitModel.getCompartment().equals("J")
												|| lRevenueSplitModel.getCompartment().equals("F")))) {

							int Chdpax = lRevenueObj.getPax();
							lRevenueSplitModel.setTotalchildpaxytd((Chdpax));
						}
						// revenue for y comp
						float revenueOfY = 0;
						if (lRevenueSplitModel.getCompartment().equals("Y")) {
							revenueOfY = lRevenueObj.getRevenue();
							lRevenueSplitModel.setTotalrevenueofy(revenueOfY);
						}
						// revenue for j comp
						float revenueofJ = 0;
						if (lRevenueSplitModel.getCompartment().equals("J")) {
							revenueofJ = lRevenueObj.getRevenue();
							lRevenueSplitModel.setTotalrevenueofj(revenueofJ);

						}
						// revenue for f comp
						float revenueofF = 0;
						if (lRevenueSplitModel.getCompartment().equals("F")) {
							revenueofF = lRevenueObj.getRevenue();
							lRevenueSplitModel.setTotalrevenueoff(revenueofF);
						}

						// pax for y comp
						float paxOfY = 0;
						if (lRevenueSplitModel.getCompartment().equals("Y")) {
							paxOfY = lRevenueObj.getPax();
							lRevenueSplitModel.setTotalpaxofy(paxOfY);
						}
						// pax for jcomp
						float paxofJ = 0;
						if (lRevenueSplitModel.getCompartment().equals("J")) {
							paxofJ = lRevenueObj.getPax();
							lRevenueSplitModel.setTotalpaxofj(paxofJ);

						}
						// pax for f comp
						float paxofF = 0;
						if (lRevenueSplitModel.getCompartment().equals("F")) {
							paxofF = lRevenueObj.getPax();
							lRevenueSplitModel.setTotalpaxoff(paxofF);
						}

						// Flown Revenue & Flown Revenue last yr
						float totalFlownRevenue = (lRevenueObj.getFlown_revenue());
						float totalFlownRevenue_lastyr = (lRevenueObj.getFlownrevenue_lastyr());
						lRevenueSplitModel.setRevenueFlownYTD((totalFlownRevenue));
						lRevenueSplitModel.setTotalFlownRevenue_lastyr((totalFlownRevenue_lastyr));

						// Sales Revenue & Sales revenue last yr
						float totalsalesRevenue = lRevenueObj.getRevenue();
						float totalsalesRevenue_lastyr = lRevenueObj.getRevenuelastyr();
						lRevenueSplitModel.setTotalRevenue((totalsalesRevenue));
						lRevenueSplitModel.setTotalRevenue_lastyr((totalsalesRevenue_lastyr));

						// OD Distance
						float totaldistance = lRevenueObj.getOd_Distance();
						lRevenueSplitModel.setTotaldistance((totaldistance));
						map.put(lRevenueObj.getFilterKey(), lRevenueSplitModel);
						// months and days
						int lMonth = 0;
						int lDays = 0;
						if (!"null".equalsIgnoreCase(lRevenueObj.getDepartureDate().toString())) {
							lMonth = Utility.findMonth(lRevenueObj.getDepartureDate());
							lDays = Utility.findDay(lRevenueObj.getDepartureDate());
							lRevenueSplitModel.setMonth(lMonth);
							lRevenueSplitModel.setDays(lDays);
						}
					} else {
						for (String lKey : map.keySet()) {
							if (lRevenueObj.getFilterKey().equals(lKey)) {
								lRevenueSplitModel = map.get(lKey);
							}
						}
						// TODO Change average fare. Fare cannot be added
						fareYTD = lRevenueSplitModel.getAvgfare() + fare;
						lRevenueSplitModel.setAvgfare(fareYTD);

						// flown Revenue
						float flownrevenue = lRevenueObj.getFlown_revenue() + lRevenueSplitModel.getFlownrevenue();
						lRevenueSplitModel.setFlownrevenue(flownrevenue);
						float lflownrevenuelastyr = lRevenueObj.getFlownrevenue_lastyr()
								+ lRevenueSplitModel.getTotalFlownRevenue_lastyr();
						lRevenueSplitModel.setTotalFlownRevenue_lastyr(lflownrevenuelastyr);
						// flown pax
						float flownpax = lRevenueObj.getFlownpax() + lRevenueSplitModel.getFlownpax();
						lRevenueSplitModel.setFlownpax(flownpax);
						float lflownpaxlastyr = lRevenueObj.getFlownpaxlastyr()
								+ lRevenueSplitModel.getTotalflownpaxlastyr();
						lRevenueSplitModel.setTotalflownpaxlastyr(lflownpaxlastyr);
						// Pax
						int totalPax = lRevenueObj.getPax() + lRevenueSplitModel.getTotalPax();
						lRevenueSplitModel.setTotalPax((totalPax));
						float totalPax_lastyr = lRevenueObj.getPaxlastyr() + lRevenueSplitModel.getTotalPax_lastyr();
						lRevenueSplitModel.setTotalPax_lastyr((totalPax_lastyr));

						// pax type
						String paxtype = lRevenueObj.getPaxType();
						lRevenueSplitModel.setPaxtype(paxtype);

						// Adult Pax
						if (lRevenueSplitModel.getPaxtype().equals("ADT")
								|| (lRevenueSplitModel.getPaxtype().equals("ADT ")
										&& (lRevenueSplitModel.getCompartment().equals("Y")
												|| lRevenueSplitModel.getCompartment().equals("J")
												|| lRevenueSplitModel.getCompartment().equals("F")))) {

							int adultPax = (int) (lRevenueObj.getPax() + lRevenueSplitModel.getTotaladultpaxYtd());
							lRevenueSplitModel.setTotaladultpaxYtd((adultPax));
						}

						// Infant Pax
						if (lRevenueSplitModel.getPaxtype().equals("INF ")
								|| (lRevenueSplitModel.getPaxtype().equals("INF")
										&& (lRevenueSplitModel.getCompartment().equals("Y")
												|| lRevenueSplitModel.getCompartment().equals("J")
												|| lRevenueSplitModel.getCompartment().equals("F")))) {

							int infPax = (int) (lRevenueObj.getPax() + lRevenueSplitModel.getTotalinfpaxytd());
							lRevenueSplitModel.setTotalinfpaxytd((infPax));
						}

						// Child Pax
						if (lRevenueSplitModel.getPaxtype().equals("CHD")
								|| (lRevenueSplitModel.getPaxtype().equals("CHD ")
										&& (lRevenueSplitModel.getCompartment().equals("Y")
												|| lRevenueSplitModel.getCompartment().equals("J")
												|| lRevenueSplitModel.getCompartment().equals("F")))) {

							int chdPax = (int) (lRevenueObj.getPax() + lRevenueSplitModel.getTotalchildpaxytd());
							lRevenueSplitModel.setTotalchildpaxytd((chdPax));
						}
						float totalrevenuey = 0;
						float totalrevenuej = 0;
						float totalrevenuef = 0;
						float totalrevneuecomp = 0;
						// revenue for y comp
						float totalrevenueOfY = 0;
						if (lRevenueSplitModel.getCompartment().equals("Y")) {
							totalrevenueOfY = lRevenueObj.getRevenue() + lRevenueSplitModel.getTotalrevenueofy();
							totalrevenuey = totalrevenueOfY;
							lRevenueSplitModel.setTotalrevenueofy(totalrevenuey);
						}
						// revenue for j comp
						float totalrevenueofJ = 0;
						if (lRevenueSplitModel.getCompartment().equals("J")) {
							totalrevenueofJ = lRevenueObj.getRevenue() + lRevenueSplitModel.getTotalrevenueofj();
							totalrevenuej = totalrevenueofJ;
							lRevenueSplitModel.setTotalrevenueofj(totalrevenuej);

						}
						// revenue for f comp
						float totalrevenueofF = 0;
						if (lRevenueSplitModel.getCompartment().equals("F")) {
							totalrevenueofF = lRevenueObj.getRevenue() + lRevenueSplitModel.getTotalrevenueoff();
							totalrevenuef = totalrevenueofF;
							lRevenueSplitModel.setTotalrevenueoff(totalrevenuef);
						}

						// pax for y comp
						float totalpaxOfY = 0;
						if (lRevenueSplitModel.getCompartment().equals("Y")) {
							totalpaxOfY = lRevenueObj.getPax() + lRevenueSplitModel.getTotalpaxofy();
							lRevenueSplitModel.setTotalpaxofy(totalpaxOfY);
						}
						// paxfor j comp
						float totalpaxofJ = 0;
						if (lRevenueSplitModel.getCompartment().equals("J")) {
							totalpaxofJ = lRevenueObj.getPax() + lRevenueSplitModel.getTotalpaxofj();
							lRevenueSplitModel.setTotalpaxofj(totalpaxofJ);

						}
						// pax for f comp
						float totalpaxofF = 0;
						if (lRevenueSplitModel.getCompartment().equals("F")) {
							totalpaxofF = lRevenueObj.getPax() + lRevenueSplitModel.getTotalpaxoff();
							lRevenueSplitModel.setTotalpaxoff(totalpaxofF);
						}

						// Flown Revenue
						float totalFlownRevenue = (lRevenueObj.getFlown_revenue())
								+ lRevenueSplitModel.getRevenueFlownYTD();
						float totalFlownRevenue_lastyr = (lRevenueObj.getFlownrevenue_lastyr())
								+ lRevenueSplitModel.getTotalFlownRevenue_lastyr();
						lRevenueSplitModel.setRevenueFlownYTD((totalFlownRevenue));
						lRevenueSplitModel.setTotalFlownRevenue_lastyr((totalFlownRevenue_lastyr));

						// Sales Revenue
						float totalsalesRevenue = lRevenueObj.getRevenue() + lRevenueSplitModel.getTotalRevenue();
						float totalsalesRevenue_lastyr = lRevenueObj.getRevenuelastyr()
								+ lRevenueSplitModel.getTotalRevenue_lastyr();
						lRevenueSplitModel.setTotalRevenue((totalsalesRevenue));
						lRevenueSplitModel.setTotalRevenue_lastyr((totalsalesRevenue_lastyr));

						// OD distance
						float totaldistance = lRevenueObj.getOd_Distance() + lRevenueSplitModel.getTotaldistance();
						lRevenueSplitModel.setTotaldistance((totaldistance));
						// months and days
						int lMonth = 0;
						int lDays = 0;
						if (!"null".equalsIgnoreCase(lRevenueObj.getDepartureDate().toString())) {
							lMonth = Utility.findMonth(lRevenueObj.getDepartureDate());
							lDays = Utility.findDay(lRevenueObj.getDepartureDate());
							lRevenueSplitModel.setMonth(lMonth);
							lRevenueSplitModel.setDays(lDays);
						}

					}

				}

				for (String key : map.keySet()) {
					lRevenueSplitModel = new RevenueSplitModel();
					lRevenueSplitModel.setCombinationkey(map.get(key).getCombinationkey());
					lRevenueSplitModel.setCountry(map.get(key).getCountry());
					lRevenueSplitModel.setRegion(map.get(key).getRegion());
					lRevenueSplitModel.setOrigin(map.get(key).getOrigin());
					lRevenueSplitModel.setDestination(map.get(key).getDestination());
					lRevenueSplitModel.setCompartment(map.get(key).getCompartment());
					lRevenueSplitModel.setPos(map.get(key).getPos());

					float pForcastPax = 0;
					int totaldaysFromdate = 0;
					Date date1 = null;
					Date date2 = null;
					if (!pRequestModel.getFromDate().isEmpty() && pRequestModel.getFromDate() != null
							&& !pRequestModel.getToDate().isEmpty() && pRequestModel.getToDate() != null) {
						date1 = Utility.convertStringToDateFromat(pRequestModel.getFromDate());
						date2 = Utility.convertStringToDateFromat(pRequestModel.getToDate());
					} else {
						date1 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
						date2 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
					}
					// int result = str2.compareTo(str1);
					int diff = (int) Utility.getDifferenceDays(date1, date2);
					int result = diff + 1;
					if (result == 0) {
						totaldaysFromdate = result + 1;
					}
					// no. of days
					int noOfDays = Utility.numberOfDaysInMonth(
							Utility.findMonth(map.get(key).getDepartureDate().toString()) - 1,
							Utility.findYear(map.get(key).getDepartureDate().toString()));
					float targetProratedPax = ((lTotaltargetPax / (float) noOfDays) * result);
					float proratedForcastPax = (((float) forcastpax / (float) noOfDays) * result);
					float targetProratedRevenue = ((lTotalTargetRevenue / noOfDays) * result);
					float proratedForcastRevenue = (((float) forcastrevenue / (float) noOfDays) * result);

					// months and days
					int lMonth = 0;
					int lDays = 0;
					if (!"null".equalsIgnoreCase(map.get(key).getDepartureDate().toString())) {
						lMonth = Utility.findMonth(map.get(key).getDepartureDate());
						lDays = Utility.findDay(map.get(key).getDepartureDate());
						lRevenueSplitModel.setMonth(lMonth);
						lRevenueSplitModel.setDays(lDays);
					}
					if (lRevenueSplitModel.getMonth() == map.get(key).getMonth()) {
						float lTotaltargetProratedPax = targetProratedPax;
						float lTotalproratedForcastPax = proratedForcastPax;
						lRevenueSplitModel.setTotalforcastpax(lTotalproratedForcastPax);
						lRevenueSplitModel.setTotaltargetproratedpax(lTotaltargetProratedPax);
						float lTotaltargetProratedRevenue = targetProratedRevenue;
						float lTotalproratedForcastRevenue = proratedForcastRevenue;
						lRevenueSplitModel.setTotalforcastrevenue(lTotalproratedForcastRevenue);
						lRevenueSplitModel.setTotaltargetproratedrevnue(lTotaltargetProratedRevenue);

					} else {
						float lTotaltargetProratedPax = targetProratedPax + lTotaltargetPax;
						float lTotalproratedForcastPax = proratedForcastPax + lTotalforcastPax;
						lRevenueSplitModel.setTotalforcastpax(lTotalproratedForcastPax);
						lRevenueSplitModel.setTotaltargetproratedpax(lTotaltargetProratedPax);
						float lTotaltargetProratedRevenue = targetProratedRevenue + lTotalTargetRevenue;
						float lTotalproratedForcastRevenue = proratedForcastRevenue + lTotalforcastRevenue;
						lRevenueSplitModel.setTotalforcastrevenue(lTotalproratedForcastRevenue);
						lRevenueSplitModel.setTotaltargetproratedrevnue(lTotaltargetProratedRevenue);

					}
					// pax vtgt

					if (diff == 0) {
						pForcastPax = 0;
						proratedForcastRevenue = 0;
					} else {
						pForcastPax = lRevenueSplitModel.getTotalforcastpax();
						proratedForcastRevenue = lRevenueSplitModel.getTotalforcastrevenue();
					}
					float paxvtgt = 0;
					float pflownpax = map.get(key).getFlownpax();
					lRevenueSplitModel.setFlownpax(pflownpax);
					if (targetProratedPax != 0) {
						paxvtgt = CalculationUtil.calculateVTGTRemodelled(pflownpax, pForcastPax, targetProratedPax);
					}
					lRevenueSplitModel.setPaxVTGT((paxvtgt));
					// revenue vtgt
					float revenuevtgt = 0;
					float pflownrevenue = map.get(key).getFlownrevenue();
					lRevenueSplitModel.setFlownrevenue(pflownrevenue);
					lRevenueSplitModel.setRevenueFlownYTD(pflownrevenue);
					if (targetProratedRevenue != 0) {
						revenuevtgt = CalculationUtil.calculateVTGTRemodelled(pflownrevenue, proratedForcastRevenue,
								targetProratedRevenue);
						lRevenueSplitModel.setRevenueVTGT((revenuevtgt));
					}

					// Pax Type
					String paxtype = map.get(key).getPaxtype();
					lRevenueSplitModel.setPaxtype(paxtype);

					// Adult Pax
					float lTotalsadultPax = map.get(key).getTotaladultpaxYtd();
					lRevenueSplitModel.setTotaladultpaxperc(lTotalsadultPax);

					// Child Pax
					float lTotalschdpax = map.get(key).getTotalchildpaxytd();
					lRevenueSplitModel.setTotalchildpaxperc(lTotalschdpax);

					// Infant Pax
					float lTotalsinfpax = map.get(key).getTotalinfpaxytd();
					lRevenueSplitModel.setTotalinfpaxperc(lTotalsinfpax);

					// total pax YTD
					int lTotalsPax = (int) (lTotalsadultPax + lTotalschdpax + lTotalsinfpax);
					lRevenueSplitModel.setPaxYTD((lTotalsPax));
					// Adt pax YTd
					totalPaxs = lTotalsPax;
					if (lTotalsadultPax > 0) {
						String adultpax = map.get(key).getPaxtype();
						lRevenueSplitModel.setPaxtype(adultpax);
						float ltotalspax = (lTotalsadultPax / lTotalsPax) * 100;
						lRevenueSplitModel.setTotaladultpaxYtd(Math.round(ltotalspax));

					}
					// Chd Pax YTD
					if (lTotalschdpax > 0) {
						String chdpax = map.get(key).getPaxtype();
						lRevenueSplitModel.setPaxtype(chdpax);
						float ltotalspax1 = (lTotalschdpax / lTotalsPax) * 100;
						lRevenueSplitModel.setTotalchildpaxytd(Math.round(lTotalsChdpax));
					}
					// Inf Pax YTD
					if (lTotalsinfpax > 0) {
						String chdpax = map.get(key).getPaxtype();
						lRevenueSplitModel.setPaxtype(chdpax);
						float ltotalspax2 = (lTotalsinfpax / lTotalsPax) * 100;
						lRevenueSplitModel.setTotalinfpaxytd(Math.round(lTotalsInfpax));
					}
					float lTotalrevenue = 0;
					int lTotalpax = 0;
					for (String lKey : revenueShareMap.keySet()) {
						if (map.get(key).getCombinationkey().contains(revenueShareMap.get(lKey).getRevenueShareKey())) {

							lRevenueSplitModel.setRevenueShareKey(revenueShareMap.get(lKey).getRevenueShareKey());
							lTotalrevenue = revenueShareMap.get(lKey).getTotalRevenue();
							lRevenueSplitModel.setTotalRevenue(lTotalrevenue);
							lTotalpax = revenueShareMap.get(lKey).getTotalPax();
							lRevenueSplitModel.setTotalPax((int) lTotalPax);
						}
					}

					// Y Revenue
					float lTotalReveunueOfY = map.get(key).getTotalrevenueofy();
					lRevenueSplitModel.setTotalrevenueofy((lTotalReveunueOfY));

					// J Revenue
					float lTotalReveunueOfJ = map.get(key).getTotalrevenueofj();
					lRevenueSplitModel.setTotalrevenueofj((lTotalReveunueOfY));
					// F Revenue
					float lTotalReveunueOfF = map.get(key).getTotalrevenueoff();
					lRevenueSplitModel.setTotalrevenueoff((lTotalReveunueOfF));

					if (lTotalReveunueOfY > 0) {
						String compartment = map.get(key).getCompartment();
						float ltotalsrevenueofY = (lTotalReveunueOfY / lTotalrevenue) * 100;
						lRevenueSplitModel.setRevenueshareperc((ltotalsrevenueofY));
					}

					if (lTotalReveunueOfJ > 0) {
						String compartment = map.get(key).getCompartment();
						float ltotalsrevenueofJ = (lTotalReveunueOfJ / lTotalrevenue) * 100;
						lRevenueSplitModel.setRevenueshareperc((ltotalsrevenueofJ));

					}

					if (lTotalReveunueOfF > 0) {
						String compartment = map.get(key).getCompartment();
						float ltotalsrevenueofJ = (lTotalReveunueOfF / lTotalrevenue) * 100;
						lRevenueSplitModel.setRevenueshareperc((ltotalsrevenueofJ));

					}
					// y pax
					float paxofy = map.get(key).getTotalpaxofy();
					lRevenueSplitModel.setTotalpaxofy(paxofy);
					// j pax
					float paxofj = map.get(key).getTotalpaxofj();
					lRevenueSplitModel.setTotalpaxofj(paxofj);
					// f pax
					float paxoff = map.get(key).getTotalrevenueoff();
					lRevenueSplitModel.setTotalpaxoff(paxoff);
					// pax perc for y
					if (paxofy > 0) {
						float paxofY = paxofy / lTotalpax * 100;
						lRevenueSplitModel.setPaxpercentage(paxofY);
					}
					// pax perc for j
					if (paxofj > 0) {
						float paxofJ = paxofj / lTotalpax * 100;
						lRevenueSplitModel.setPaxpercentage(paxofJ);
					}
					// pax perc for f
					if (paxoff > 0) {
						float paxofF = paxoff / lTotalpax * 100;
						lRevenueSplitModel.setPaxpercentage(paxofF);
					}

					String host = "FZ";
					float hostcapacity = 0;

					if (carrierPaxMap.containsKey(host)) {
						hostcapacity = (carrierPaxMap.get(host));
						lRevenueSplitModel.setHostcapacity(hostcapacity);
					}
					float hostcapacitylastyr = 0;
					if (carrierPaxMap.containsKey(host)) {
						hostcapacitylastyr = (capacitylastyrMap.get(host));
						lRevenueSplitModel.setHostcapacitylastyr(hostcapacitylastyr);
					}
					float lFlownpaxlastyr = map.get(key).getTotalflownpaxlastyr();
					lRevenueSplitModel.setTotalflownpaxlastyr(lFlownpaxlastyr);
					float lflownrevenuelastyr = map.get(key).getTotalFlownRevenue_lastyr();
					lRevenueSplitModel.setTotalFlownRevenue_lastyr(lflownrevenuelastyr);
					// Pax VLYR
					float totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					lRevenueSplitModel.setTotalPax_lastyr((int) totalPax_lastyr);
					if (totalPax_lastyr != 0) {
						// TODO
						lPaxVLYR = CalculationUtil.calculateVLYR(pflownpax, lFlownpaxlastyr, hostcapacity,
								hostcapacitylastyr);
						lRevenueSplitModel.setPaxVLYR((lPaxVLYR));

					} else {
						lRevenueSplitModel.setPaxVLYR((lPaxVLYR));
					}
					// Total sales revenue
					float lTotalsalesRevenue = map.get(key).getTotalRevenue();

					lRevenueSplitModel.setRevenueSalesYTD((lTotalsalesRevenue));

					// totals of salesrevenueytd
					totalSalesrevenueYTD += lTotalsalesRevenue;

					/*
					 * float lTotalFlownrevenue =
					 * map.get(key).getRevenueFlownYTD();
					 * lRevenueSplitModel.setRevenueFlownYTD((lTotalFlownrevenue
					 * ));
					 */

					// totals of flownrevenueytd
					// totalFlownrevenueYTD += lTotalFlownrevenue;

					float lTotalsalesrevenue_lastyr = map.get(key).getTotalRevenue_lastyr();
					if (lTotalsalesrevenue_lastyr != 0) {
						lRevenueVLYR = CalculationUtil.calculateVLYR(pflownrevenue, lflownrevenuelastyr, hostcapacity,
								hostcapacitylastyr);
						lRevenueSplitModel.setRevenueVLYR((lRevenueVLYR));
					} else {
						lRevenueSplitModel.setRevenueVLYR(0);
					}

					// totals salesvlyr

					totalSalesRevenue_LY += lTotalsalesrevenue_lastyr;

					// Average Fare
					float avgfare = CalculationUtil.calculateavgfare(lRevenueSplitModel.getTotalRevenue(),
							lRevenueSplitModel.getPaxYTD());

					lRevenueSplitModel.setAvgfare((avgfare));

					// od distance
					float odDistance = map.get(key).getTotaldistance();
					lRevenueSplitModel.setTotaldistance(odDistance);

					// Yield
					float totalYield = CalculationUtil.calculateYield(lTotalsalesRevenue, lTotalsPax, odDistance);
					lRevenueSplitModel.setYieldYTD((totalYield));
					// yield last yr
					float totalYieldlastyr = CalculationUtil.calculateYield(lTotalsalesrevenue_lastyr, totalPax_lastyr,
							odDistance);
					if (totalYieldlastyr != 0) {

						lYieldVLYR = CalculationUtil.calculateVLYR(totalYield, totalYieldlastyr);
						lRevenueSplitModel.setYieldVLYR((lYieldVLYR));
					} else {
						lRevenueSplitModel.setYieldVLYR((lYieldVLYR));
					}
					// Target Yield
					float targetYield = 0.0f;
					if (lTargetYieldArray != null) {
						if (lTargetYieldArray.length() > 0) {
							for (int i = 0; i < lTargetYieldArray.length(); i++) {
								// TODO Utility findSum
								targetYield += Float.parseFloat(lTargetYieldArray.get(i).toString());
							}
						}
					} else {
						lTargetYieldArray = null;
					}
					lRevenueSplitModel.setTotalyieldtarget(targetYield);
					if (targetYield > 0) {
						lYieldVTGT = ((totalYield - targetYield) / targetYield) * 100;
						totalyieldtarget += targetYield;
						lRevenueSplitModel.setYieldVTGT((lYieldVTGT));
					} else {
						lRevenueSplitModel.setYieldVTGT((lYieldVTGT));
					}

					lRevenueSplitModelList.add(lRevenueSplitModel);
				}
				float totalpaxytdY = 0;
				float totalPaxLastYrY = 0;
				float totalPaxtargetY = 0;
				float totalsalesrevenuetarget = 0;
				float ltotalyieldtarget = 0;
				float totaladultpaxpercY = 0;
				float totalchdpaxpercY = 0;
				float totalinfpaxpercY = 0;
				float totalsalesrevenueytdY = 0;
				float totalsalesrevenuelastyrytdY = 0;
				float totalsalesrevenuetargetY = 0;
				float totalyieldytdY = 0;
				float totalyieldlastyrY = 0;
				float ltotalyieldtargetY = 0;
				float totalflownrevenueytdY = 0;

				float totalpaxytdJ = 0;
				float totalPaxLastYrJ = 0;
				float totalPaxtargetJ = 0;
				float totalsalesrevenuetargetJ = 0;
				float ltotalyieldtargetJ = 0;
				float totaladultpaxpercJ = 0;
				float totalchdpaxpercJ = 0;
				float totalinfpaxpercJ = 0;
				float totalsalesrevenueytdJ = 0;
				float totalsalesrevenuelastyrytdJ = 0;
				float ltotalsalesrevenuetargetJ = 0;
				float totalyieldytdJ = 0;
				float totalyieldlastyrJ = 0;
				float totalyieldtargetJ = 0;
				float totalflownrevenueytdJ = 0;

				float totalpaxytdA = 0;
				float totalPaxLastYrA = 0;
				float totalPaxtargetA = 0;
				float totalsalesrevenuetargetA = 0;
				float ltotalyieldtargetA = 0;
				float totaladultpaxpercA = 0;
				float totalchdpaxpercA = 0;
				float totalinfpaxpercA = 0;
				float totalsalesrevenueytdA = 0;
				float totalsalesrevenuelastyrytdA = 0;
				float ltotalsalesrevenuetargetA = 0;
				float totalyieldytdA = 0;
				float totalyieldlastyrA = 0;
				float totalyieldtargetA = 0;
				float totalflownrevenueytdA = 0;
				float totalForcastPaxofy = 0;
				float totalTargetProratedPaxofY = 0;
				float totalFlownPaxofY = 0;
				float totalForcastRevenueofy = 0;
				float totalTargetProratedRevenueofY = 0;
				float totalflownrevenueofY = 0;
				float totalhostcapacityofY = 0;
				float totalhostcapacitylastyrofY = 0;
				float totalflownpaxlastyrofY = 0;
				float totalflownrevenuelastyrofY = 0;
				RevenueSplitTotalResponse lModel = new RevenueSplitTotalResponse();
				for (RevenueSplitModel lObj : lRevenueSplitModelList) {
					lTotalPax += lObj.getPaxYTD();

					if (lObj.getCompartment().equals("Y")) {
						totalpaxytdY += lObj.getPaxYTD();
						totalPaxLastYrY += lObj.getTotalPax_lastyr();
						totalPaxtargetY += lObj.getTotalpaxtarget();
						totalsalesrevenueytdY += lObj.getRevenueSalesYTD();
						totalsalesrevenuelastyrytdY += lObj.getTotalRevenue_lastyr();
						totalsalesrevenuetargetY += lObj.getTotalrevenuetarget();
						totalyieldytdY += lObj.getYieldYTD();
						totalyieldlastyrY += lObj.getTotalYield_lastyr();
						ltotalyieldtargetY += lObj.getTotalyieldtarget();
						totalflownrevenueytdY += lObj.getRevenueFlownYTD();
						totaladultpaxpercY += lObj.getTotaladultpaxperc();
						totalchdpaxpercY += lObj.getTotalchildpaxperc();
						totalinfpaxpercY += lObj.getTotalinfpaxperc();
						totalForcastPaxofy += lObj.getTotalforcastpax();
						totalTargetProratedPaxofY += lObj.getTotaltargetproratedpax();
						totalFlownPaxofY += lObj.getFlownpax();
						totalForcastRevenueofy += lObj.getTotalforcastrevenue();
						totalTargetProratedRevenueofY += lObj.getTotaltargetproratedrevnue();
						totalflownrevenueofY += lObj.getFlownrevenue();
						totalhostcapacityofY += lObj.getHostcapacity();
						totalhostcapacitylastyrofY += lObj.getHostcapacitylastyr();
						totalflownpaxlastyrofY += lObj.getTotalflownpaxlastyr();
						totalflownrevenuelastyrofY += lObj.getTotalFlownRevenue_lastyr();
						float flownrevenue = totalflownrevenueofY;
						float flownpax = totalFlownPaxofY;
						float lflownpaxlastyr = totalflownpaxlastyrofY;
						float lflownrevenuelastyr = totalflownrevenuelastyrofY;
						float hostcapacity = totalhostcapacityofY;
						float hostcapacitylastyr = totalhostcapacitylastyrofY;
						// total pax vtgt
						float pForcastPax = 0;
						int totaldaysFromdate = 0;
						float pForcastRevenue = 0;
						Date date1 = null;
						Date date2 = null;
						if (!pRequestModel.getFromDate().isEmpty() && pRequestModel.getFromDate() != null
								&& !pRequestModel.getToDate().isEmpty() && pRequestModel.getToDate() != null) {
							date1 = Utility.convertStringToDateFromat(pRequestModel.getFromDate());
							date2 = Utility.convertStringToDateFromat(pRequestModel.getToDate());
						} else {
							date1 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
							date2 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
						}
						int diff = (int) Utility.getDifferenceDays(date1, date2);
						int result = diff + 1;
						if (result == 0) {
							totaldaysFromdate = result + 1;
						}
						if (diff == 0) {
							pForcastPax = 0;
							pForcastRevenue = 0;
						} else {
							pForcastPax = totalForcastPaxofy;
							pForcastRevenue = totalForcastRevenueofy;
						}
						float lTotalPaxVTGTofY = CalculationUtil.calculateVTGTRemodelled(totalFlownPaxofY, pForcastPax,
								totalTargetProratedPaxofY);
						float avgfareofY = CalculationUtil.calculateavgfare(totalsalesrevenueytdY, totalpaxytdY);
						float lTotalPaxVLYRofY = 0;
						if (lflownpaxlastyr > 0) {
							lTotalPaxVLYRofY = CalculationUtil.calculateVLYR(flownpax, lflownpaxlastyr, hostcapacity,
									hostcapacitylastyr);
						} else {
							lTotalPaxVLYRofY = 0;
						}
						float totaladpaxY = 0;
						if (lTotalPax > 0) {
							totaladpaxY = totaladultpaxpercY / lTotalPax * 100;
						} else {
							totaladpaxY = 0;
						}
						float totalchdpaxY = 0;
						if (lTotalPax > 0) {
							totalchdpaxY = totalchdpaxpercY / lTotalPax * 100;
						} else {
							totalchdpaxY = 0;
						}
						float totalinfpaxY = 0;
						if (lTotalPax > 0) {
							totalinfpaxY = totalinfpaxpercY / lTotalPax * 100;
						} else {
							totalinfpaxY = 0;
						}
						float lTotalrevenueVLYRY = 0;

						lTotalrevenueVLYRY = CalculationUtil.calculateVLYR(flownrevenue, lflownrevenuelastyr,
								hostcapacity, hostcapacitylastyr);

						// revenue VTGT
						float lTotalrevenueVTGTY = CalculationUtil.calculateVTGTRemodelled(totalflownrevenueofY,
								pForcastRevenue, totalTargetProratedRevenueofY);
						float lTotalyieldVLYRY = 0;

						lTotalyieldVLYRY = CalculationUtil.calculateVLYR(totalyieldytdY, totalyieldlastyrY);

						Map<String, Object> map1 = new HashMap<String, Object>();
						map1.put("Total Pax YTD", (totalpaxytdY));
						map1.put("Total Pax VLYR", (lTotalPaxVLYRofY));
						map1.put("Total pax VTGT", (lTotalPaxVTGTofY));
						map1.put("Total revenue YTD", (totalsalesrevenueytdY));
						map1.put("Total revenue VLYR", (lTotalrevenueVLYRY));
						map1.put("Total revenue VTGT", (lTotalrevenueVTGTY));
						map1.put("Total Adult pax YTD", (totaladpaxY));
						map1.put("Total Chd Pax YTD", (totalchdpaxY));
						map1.put("Total inf pax YTD", (totalinfpaxY));
						map1.put("Total Yield VLYR", (lTotalyieldVLYRY));
						map1.put("Total avg fares", (avgfareofY));
						map1.put("Total flown revenue", flownrevenue);
						map1.put("Total flown pax", flownpax);

						TotalMap1.put("Totals: " + lObj.getCompartment(), map1);
					}
					float totalForcastPaxofJ = 0;
					float totalTargetProratedPaxofJ = 0;
					float totalFlownPaxofJ = 0;
					float totalflownrevenueofJ = 0;
					float totalTargetProratedRevenueofJ = 0;
					float totalForcastRevenueofJ = 0;
					if (lObj.getCompartment().equals("J")) {
						totalpaxytdJ += lObj.getPaxYTD();
						totalPaxLastYrJ += lObj.getTotalPax_lastyr();
						totalPaxtargetJ += lObj.getTotalpaxtarget();
						totalsalesrevenueytdJ += lObj.getRevenueSalesYTD();
						totalsalesrevenuelastyrytdJ += lObj.getTotalRevenue_lastyr();
						ltotalsalesrevenuetargetJ += lObj.getTotalrevenuetarget();
						totalyieldytdJ += lObj.getYieldYTD();
						totalyieldlastyrJ += lObj.getTotalYield_lastyr();
						totalyieldtargetJ += lObj.getTotalyieldtarget();
						totalflownrevenueytdJ += lObj.getRevenueFlownYTD();
						totaladultpaxpercJ += lObj.getTotaladultpaxperc();
						totalchdpaxpercJ += lObj.getTotalchildpaxperc();
						totalinfpaxpercJ += lObj.getTotalinfpaxperc();
						totalForcastPaxofJ += lObj.getTotalforcastpax();
						totalTargetProratedPaxofJ += lObj.getTotaltargetproratedpax();
						totalFlownPaxofJ += lObj.getFlownpax();
						totalForcastRevenueofJ += lObj.getTotalforcastrevenue();
						totalTargetProratedRevenueofJ += lObj.getTotaltargetproratedrevnue();
						totalflownrevenueofJ += lObj.getFlownrevenue();
						float flownrevenue = totalflownrevenueofJ;
						float flownpax = totalFlownPaxofJ;
						// total pax vtgt
						float pForcastPax = 0;
						int totaldaysFromdate = 0;
						float pForcastRevenue = 0;
						String str1 = Utility.getCurrentDate();
						String str2 = pRequestModel.getFromDate();
						int result = str1.compareTo(str2);
						if (result == 0) {
							pForcastPax = 0;
							pForcastRevenue = 0;
						} else {
							pForcastPax = totalForcastPaxofJ;
							pForcastRevenue = totalForcastRevenueofJ;
						}
						float lTotalPaxVTGTofJ = CalculationUtil.calculateVTGTRemodelled(totalFlownPaxofJ, pForcastPax,
								totalTargetProratedPaxofJ);
						float avgfareofJ = CalculationUtil.calculateavgfare(totalsalesrevenueytdJ, totalpaxytdJ);
						float lTotalPaxVLYRofJ = 0;
						if (totalPaxLastYrJ > 0) {
							lTotalPaxVLYRofJ = CalculationUtil.calculateVLYR(totalpaxytdJ, totalPaxLastYrJ);
						} else {
							lTotalPaxVLYRofJ = 0;
						}
						float totaladpaxJ = 0;
						if (lTotalPax > 0) {
							totaladpaxJ = (totaladultpaxpercJ / lTotalPax) * 100;
						} else {
							totaladpaxJ = 0;
						}
						float totalchdpaxJ = 0;
						if (lTotalPax > 0) {
							totalchdpaxJ = (totalchdpaxpercJ / lTotalPax) * 100;
						} else {
							totalchdpaxJ = 0;
						}
						float totalinfpaxJ = 0;
						if (lTotalPax > 0) {
							totalinfpaxJ = (totalinfpaxpercJ / lTotalPax) * 100;
						} else {
							totalinfpaxJ = 0;
						}
						float lTotalrevenueVLYRJ = 0;

						lTotalrevenueVLYRJ = CalculationUtil.calculateVLYR(totalsalesrevenueytdJ,
								totalsalesrevenuelastyrytdJ);

						float lTotalrevenueVTGTJ = 0;

						// revenue VTGT
						lTotalrevenueVTGTJ = CalculationUtil.calculateVTGTRemodelled(totalflownrevenueofJ,
								pForcastRevenue, totalTargetProratedRevenueofJ);
						float lTotalyieldVLYRJ = 0;
						lTotalyieldVLYRJ = CalculationUtil.calculateVLYR(totalyieldytdJ, totalyieldlastyrJ);

						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("Total Pax YTD", (totalpaxytdJ));
						map2.put("Total Pax VLYR", (lTotalPaxVLYRofJ));
						map2.put("Total pax VTGT", (lTotalPaxVTGTofJ));
						map2.put("Total revenue YTD", (totalsalesrevenueytdJ));
						map2.put("Total revenue VLYR", (lTotalrevenueVLYRJ));
						map2.put("Total revenue VTGT", (lTotalrevenueVTGTJ));
						map2.put("Total Adult pax YTD", (totaladpaxJ));
						map2.put("Total Chd Pax YTD", (totalchdpaxJ));
						map2.put("Total inf pax YTD", (totalinfpaxJ));
						map2.put("Total Yield VLYR", (lTotalyieldVLYRJ));
						map2.put("Total average fares", (avgfareofJ));
						map2.put("Total flown revenue", flownrevenue);
						map2.put("Total flown Pax", flownpax);

						TotalMap2.put("Totals: " + lObj.getCompartment(), map2);
					}
					float totalForcastPaxofA = 0;
					float totalTargetProratedPaxofA = 0;
					float totalFlownPaxofA = 0;
					float totalflownrevenueofA = 0;
					float totalTargetProratedRevenueofA = 0;
					float totalForcastRevenueofA = 0;
					if ("A".equalsIgnoreCase(lObj.getCompartment())) {
						totalpaxytdA += lObj.getPaxYTD();
						totalPaxLastYrA += lObj.getTotalPax_lastyr();
						totalPaxtargetA += lObj.getTotalpaxtarget();
						totalsalesrevenueytdA += lObj.getRevenueSalesYTD();
						totalsalesrevenuelastyrytdA += lObj.getTotalRevenue_lastyr();
						totalsalesrevenuetargetA += lObj.getTotalrevenuetarget();
						totalyieldytdA += lObj.getYieldYTD();
						totalyieldlastyrA += lObj.getTotalYield_lastyr();
						ltotalyieldtargetA += lObj.getTotalyieldtarget();
						totalflownrevenueytdA += lObj.getRevenueFlownYTD();
						totaladultpaxpercA += lObj.getTotaladultpaxperc();
						totalchdpaxpercA += lObj.getTotalchildpaxperc();
						totalinfpaxpercA += lObj.getTotalinfpaxperc();
						totalForcastPaxofA += lObj.getTotalforcastpax();
						totalTargetProratedPaxofA += lObj.getTotaltargetproratedpax();
						totalFlownPaxofA += lObj.getFlownpax();
						totalForcastRevenueofA += lObj.getTotalforcastrevenue();
						totalTargetProratedRevenueofA += lObj.getTotaltargetproratedrevnue();
						totalflownrevenueofA += lObj.getFlownrevenue();
						float flownrevenue = totalflownrevenueofA;
						float flownpax = totalFlownPaxofA;
						// total pax vtgt
						float pForcastPax = 0;
						int totaldaysFromdate = 0;
						float pForcastRevenue = 0;
						String str1 = Utility.getCurrentDate();
						String str2 = pRequestModel.getFromDate();
						int result = str1.compareTo(str2);
						if (result == 0) {
							pForcastPax = 0;
							pForcastRevenue = 0;
						} else {
							pForcastPax = totalForcastPaxofA;
							pForcastRevenue = totalForcastRevenueofA;
						}
						float lTotalPaxVTGTofA = CalculationUtil.calculateVTGTRemodelled(totalFlownPaxofA, pForcastPax,
								totalTargetProratedPaxofA);
						float avgfareofJ = CalculationUtil.calculateavgfare(totalsalesrevenueytdA, totalpaxytdA);
						float lTotalPaxVLYRofA = 0;
						if (totalPaxLastYrA > 0) {
							lTotalPaxVLYRofA = CalculationUtil.calculateVLYR(totalpaxytdA, totalPaxLastYrA);
						} else {
							lTotalPaxVLYRofA = 0;
						}
						float totaladpaxA = 0;
						if (lTotalPax > 0) {
							totaladpaxA = totaladultpaxpercA / lTotalPax * 100;
						} else {
							totaladpaxA = 0;
						}
						float totalchdpaxA = 0;
						if (lTotalPax > 0) {
							totalchdpaxA = totalchdpaxpercA / lTotalPax * 100;
						} else {
							totalchdpaxA = 0;
						}
						float totalinfpaxA = 0;
						if (lTotalPax > 0) {
							totalinfpaxA = totalinfpaxpercA / lTotalPax * 100;
						} else {
							totalinfpaxA = 0;
						}
						float lTotalrevenueVLYRA = 0;

						lTotalrevenueVLYRA = CalculationUtil.calculateVLYR(totalsalesrevenueytdA,
								totalsalesrevenuelastyrytdA);

						float lTotalrevenueVTGTA = 0;
						lTotalrevenueVTGTA = CalculationUtil.calculateVTGTRemodelled(totalflownrevenueofA,
								pForcastRevenue, totalTargetProratedRevenueofA);

						float lTotalyieldVLYRA = 0;

						lTotalyieldVLYRA = CalculationUtil.calculateVLYR(totalyieldytdA, totalyieldlastyrA);

						Map<String, Object> map3 = new HashMap<String, Object>();
						map3.put("Total Pax YTD", (totalpaxytdA));
						map3.put("Total Pax VLYR", (lTotalPaxVLYRofA));
						map3.put("Total pax VTGT", (lTotalPaxVTGTofA));
						map3.put("Total revenue YTD", (totalsalesrevenueytdA));
						map3.put("Total revenue VLYR", (lTotalrevenueVLYRA));
						map3.put("Total revenue VTGT", (lTotalrevenueVTGTA));
						map3.put("Total Adult pax YTD", (totaladpaxA));
						map3.put("Total Chd Pax YTD", (totalchdpaxA));
						map3.put("Total inf pax YTD", (totalinfpaxA));
						map3.put("Total Yield VLYR", (lTotalyieldVLYRA));
						map3.put("Total avg fares", (avgfareofJ));
						map3.put("Total flown revneue", flownrevenue);
						map3.put("Total flown Pax", flownpax);

						TotalMap3.put("Totals: " + lObj.getCompartment(), map3);
					}

				}
				lMTotalsList.add(lModel);
				responseRevenueSplitModelMap.putAll(TotalMap1);
				responseRevenueSplitModelMap.putAll(TotalMap2);
				responseRevenueSplitModelMap.putAll(TotalMap3);
				responseRevenueSplitModelMap.put("Host_ Revenue_Split", lRevenueSplitModelList);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseRevenueSplitModelMap;

	}

	@Override
	public Map<String, Object> getNewProducts(RequestModel pRequestModel) {

		List<FilterModel> lNewPList = new ArrayList<FilterModel>();
		Map<String, NewProduct> map = new HashMap<String, NewProduct>();
		Map<String, Object> responseNewProductMap = new HashMap<String, Object>();
		List<NewProduct> lNewProductList = new ArrayList<NewProduct>();
		ArrayList<DBObject> lNewproductObj = mKpiDao.getNewProducts(pRequestModel);
		JSONArray lNewProductData = new JSONArray(lNewproductObj);
		JSONArray Volume_sales_pax = new JSONArray();
		String Key = null;
		FilterModel lNew = null;
		try {
			if (lNewProductData != null) {

				for (int i = 0; i < lNewProductData.length(); i++) {
					JSONObject lNewJSONObj = lNewProductData.getJSONObject(i);
					lNew = new FilterModel();

					if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
							&& pRequestModel.getUser().equals("Global Head")) {

						Key = FilterUtil.getFilter(pRequestModel);

					}

					String origin = "-";
					String destination = "_";
					System.out.println("lRevenueJSONObj" + lNewJSONObj);
					String region = "_";

					if (lNewJSONObj.has("region") && lNewJSONObj.get("region") != null
							&& !"null".equalsIgnoreCase(lNewJSONObj.get("region").toString())) {
						lNew.setRegion(lNewJSONObj.get("region").toString());

					}
					String country = "_";
					if (lNewJSONObj.has("country") && lNewJSONObj.get("country") != null
							&& !"null".equalsIgnoreCase(lNewJSONObj.get("country").toString())) {
						lNew.setCountry(lNewJSONObj.get("country").toString());
					}
					String compartment = "_";
					if (lNewJSONObj.has("compartment") && lNewJSONObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lNewJSONObj.get("compartment").toString())) {
						lNew.setCompartment(lNewJSONObj.get("compartment").toString());

					}
					String od = "_";
					if (lNewJSONObj.has("od") && lNewJSONObj.get("od") != null
							&& !"null".equalsIgnoreCase(lNewJSONObj.get("od").toString())) {
						lNew.setOd(lNewJSONObj.get("od").toString());
					}
					String Product = "_";
					if (lNewJSONObj.has("Product") && lNewJSONObj.get("Product") != null
							&& !"null".equalsIgnoreCase(lNewJSONObj.get("Product").toString())) {
						lNew.setProduct(lNewJSONObj.get("Product").toString());
					}

					String pos = "_";
					if (lNewJSONObj.has("pos") && lNewJSONObj.get("pos") != null
							&& !"null".equalsIgnoreCase(lNewJSONObj.get("pos").toString())) {
						lNew.setPos(lNewJSONObj.get("pos").toString());
					}

					String effectiveStartdate = "_";
					if (lNewJSONObj.has("Effective_start_date") && lNewJSONObj.get("Effective_start_date") != null
							&& !"null".equalsIgnoreCase(lNewJSONObj.get("Effective_start_date").toString())
							&& !"".equalsIgnoreCase(lNewJSONObj.get("Effective_start_date").toString())) {
						if (lNewJSONObj.get("Effective_start_date").toString() != null) {
							lNew.setEffectiveStartDate(lNewJSONObj.get("Effective_start_date").toString());
						} else {
							lNew.setEffectiveStartDate("_");
						}
					}

					String effectiveEnddate = "_";
					if (lNewJSONObj.has("Effective_end_date") && lNewJSONObj.get("Effective_end_date") != null
							&& !"null".equalsIgnoreCase(lNewJSONObj.get("Effective_end_date").toString())
							&& !"[]".equalsIgnoreCase(lNewJSONObj.get("Effective_end_date").toString())) {
						if (lNewJSONObj.get("Effective_end_date").toString() != null) {

							lNew.setEffectiveEndDate(lNewJSONObj.get("Effective_end_date").toString());
						} else {
							lNew.setEffectiveEndDate("_");
						}
					}
					String volume = "_";
					if (lNewJSONObj.has("Volume_sales_pax") && lNewJSONObj.get("Volume_sales_pax") != null
							&& !"null".equalsIgnoreCase(lNewJSONObj.get("Volume_sales_pax").toString())
							&& !"[]".equalsIgnoreCase(lNewJSONObj.get("Volume_sales_pax").toString())) {
						Volume_sales_pax = new JSONArray(lNewJSONObj.get("Volume_sales_pax").toString());

						if (Volume_sales_pax != null) {
							if (Volume_sales_pax.length() > 0) {

								for (int j = 0; j < Volume_sales_pax.length(); j++) {
									if (Volume_sales_pax != null
											&& !"null".equalsIgnoreCase(Volume_sales_pax.get(j).toString())) {

										JSONArray Volume_sales_paxarray = (new JSONArray(
												Volume_sales_pax.get(j).toString()));
										if (Volume_sales_paxarray.length() > 0) {
											if (Volume_sales_paxarray != null && !"null"
													.equalsIgnoreCase(Volume_sales_paxarray.get(0).toString())) {
												volume = Volume_sales_paxarray.get(0).toString();
											} else {
												volume = "_";
											}
										}

									}
								}

							}
						}
					}
					// Key
					String lAggregationKey = FilterUtil.getAggregationKey(lNew, Key);
					lNew.setFilterKey(lAggregationKey);
					lNewPList.add(lNew);
				}
				NewProduct lNewProduct = null;
				for (FilterModel lNewObj : lNewPList) {

					if (!map.containsKey(lNewObj.getFilterKey())) {
						lNewProduct = new NewProduct();

						lNewProduct.setCombinationkey(lNewObj.getFilterKey());
						if (lNewObj.getOd() != null) {
							String origin = lNewObj.getOd().substring(0, 3);
							String destination = lNewObj.getOd().substring(3, 6);
							lNewProduct.setOrigin(origin);
							lNewProduct.setDestination(destination);
						}

						lNewProduct.setCountry(lNewObj.getCountry());
						lNewProduct.setPos(lNewObj.getPos());
						lNewProduct.setRegion(lNewObj.getRegion());
						lNewProduct.setCompartment(lNewObj.getCompartment());
						lNewProduct.setProduct(lNewObj.getProduct());
						lNewProduct.setEffectivestartdate(lNewObj.getEffectiveStartDate());
						lNewProduct.setEffectiveenddate(lNewObj.getEffectiveEndDate());
						lNewProduct.setVolume(lNewObj.getVolume());
						map.put(lNewObj.getFilterKey(), lNewProduct);
					} else {

						for (String lKey : map.keySet()) {
							if (lNew.getFilterKey().equals(lKey)) {
								lNewProduct = map.get(lKey);
							}
						}
						lNewProduct.setCountry(lNewObj.getCountry());
						lNewProduct.setPos(lNewObj.getPos());
						lNewProduct.setRegion(lNewObj.getRegion());
						lNewProduct.setCompartment(lNewObj.getCompartment());
						lNewProduct.setProduct(lNewObj.getProduct());
						lNewProduct.setEffectivestartdate(lNewObj.getEffectiveStartDate());
						lNewProduct.setEffectiveenddate(lNewObj.getEffectiveEndDate());
						lNewProduct.setVolume(lNewObj.getVolume());

					}

				}

				for (String key : map.keySet()) {
					lNewProduct = map.get(key);
					lNewProduct.setCombinationkey(map.get(key).getCombinationkey());
					lNewProduct.setOrigin(map.get(key).getOrigin());
					lNewProduct.setDestination(map.get(key).getDestination());
					lNewProduct.setCountry(map.get(key).getCountry());
					lNewProduct.setCompartment(map.get(key).getCompartment());
					lNewProduct.setRegion(map.get(key).getRegion());
					lNewProduct.setPos(map.get(key).getPos());
					lNewProduct.setProduct(map.get(key).getProduct());
					lNewProduct.setEffectivestartdate(map.get(key).getEffectivestartdate());
					lNewProduct.setEffectiveenddate(map.get(key).getEffectiveenddate());
					lNewProduct.setVolume(map.get(key).getVolume());
					lNewProductList.add(lNewProduct);

				}
				responseNewProductMap.put("New Product", lNewProductList);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseNewProductMap;

	}

	@Override
	public Map<String, Object> getYield_RASM_Seat(RequestModel pRequestModel, UserProfile userProfile) {
		ArrayList<DBObject> lYieldObj = mKpiDao.getYield_RASM_Seat(pRequestModel, userProfile);
		JSONArray lYieldData = new JSONArray(lYieldObj);

		Map<String, Object> responseYieldMap = new HashMap<String, Object>();
		List<Yield> lYieldList = new ArrayList<Yield>();
		List<Yieldrasm> lYieldrasmList = new ArrayList<Yieldrasm>();
		Yield lYield = null;
		try {
			if (lYieldData != null) {

				for (int i = 0; i < lYieldData.length(); i++) {
					JSONObject lYieldJSONObj = lYieldData.getJSONObject(i);
					lYield = new Yield();
					System.out.println("lRevenueJSONObj" + lYieldJSONObj);

					if (lYieldJSONObj.has("od") && lYieldJSONObj.get("od") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("od").toString())) {
						String od = lYieldJSONObj.get("od").toString();
						lYield.setOd(od);
						if (od != null) {
							String origin = od.substring(0, 3);
							lYield.setOrigin(origin);
							String destination = od.substring(3, 6);
							lYield.setDestination(destination);

						}

					}
					if (lYieldJSONObj.has("dep_date") && lYieldJSONObj.get("dep_date") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("dep_date").toString())) {
						String dep_date = (lYieldJSONObj.get("dep_date").toString());
						lYield.setDep_date(dep_date);

					}

					if (lYieldJSONObj.has("ruleID") && lYieldJSONObj.get("ruleID") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("ruleID").toString())) {
						String ruleId = (lYieldJSONObj.get("ruleID").toString());
						lYield.setRuleid(ruleId);

					}
					if (lYieldJSONObj.has("footNote") && lYieldJSONObj.get("footNote") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("footNote").toString())) {
						String footnote = (lYieldJSONObj.get("footNote").toString());
						lYield.setFootnote(footnote);

					}
					float baseFare = 0;
					if (lYieldJSONObj.has("baseFare") && lYieldJSONObj.get("baseFare") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("baseFare").toString())) {
						baseFare = Float.parseFloat(lYieldJSONObj.get("baseFare").toString());
						lYield.setBasefare(baseFare);

					}
					float surCharge = 0;
					if (lYieldJSONObj.has("surCharge") && lYieldJSONObj.get("surCharge") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("surCharge").toString())) {
						surCharge = Float.parseFloat(lYieldJSONObj.get("surCharge").toString());
						lYield.setSurcharge(surCharge);

					}
					float yqCharge = 0;
					if (lYieldJSONObj.has("yqCharge") && lYieldJSONObj.get("yqCharge") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("yqCharge").toString())) {
						yqCharge = Float.parseFloat(lYieldJSONObj.get("yqCharge").toString());
						lYield.setYqcharge(yqCharge);

					}
					float taxes = 0;
					if (lYieldJSONObj.has("taxes") && lYieldJSONObj.get("taxes") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("taxes").toString())) {
						taxes = Float.parseFloat(lYieldJSONObj.get("taxes").toString());
						lYield.setYqcharge(taxes);

					}
					float totalFare = 0;
					if (lYieldJSONObj.has("totalFare") && lYieldJSONObj.get("totalFare") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("totalFare").toString())) {
						totalFare = Float.parseFloat(lYieldJSONObj.get("totalFare").toString());
						lYield.setYqcharge(totalFare);

					}
					int pax = 0;

					if (lYieldJSONObj.has("pax") && lYieldJSONObj.get("pax") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("pax").toString())) {
						pax = Integer.parseInt(lYieldJSONObj.get("pax").toString());
						lYield.setPax(pax);

					}
					float rpkm = 0;
					if (lYieldJSONObj.has("rpkm") && lYieldJSONObj.get("rpkm") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("rpkm").toString())) {
						rpkm = Float.parseFloat(lYieldJSONObj.get("rpkm").toString());
						lYield.setRpkm(rpkm);

					}
					float rasm = 0;
					if (lYieldJSONObj.has("rasm") && lYieldJSONObj.get("rasm") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("rasm").toString())) {
						rasm = Float.parseFloat(lYieldJSONObj.get("rasm").toString());
						lYield.setRasm(rasm);
					}

					float seatfactor = 0;
					if (lYieldJSONObj.has("Seat_Factor") && lYieldJSONObj.get("Seat_Factor") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("Seat_Factor").toString())) {
						seatfactor = Float.parseFloat(lYieldJSONObj.get("Seat_Factor").toString());
						lYield.setSeatfactor(seatfactor);

					}
					int pax1 = 0;
					if (lYieldJSONObj.has("pax_1") && lYieldJSONObj.get("pax_1") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("pax_1").toString())) {
						pax1 = Integer.parseInt(lYieldJSONObj.get("pax_1").toString());
						lYield.setPax1(pax1);

					}

					float revenue = 0;
					if (lYieldJSONObj.has("revenue") && lYieldJSONObj.get("revenue") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("revenue").toString())) {

						revenue = Float.parseFloat(lYieldJSONObj.get("revenue").toString());
						lYield.setRevenue(revenue);

					}
					float revenue1 = 0;
					if (lYieldJSONObj.has("revenue_1") && lYieldJSONObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("revenue_1").toString())) {
						revenue1 = Float.parseFloat(lYieldJSONObj.get("revenue_1").toString());
						lYield.setRevenue1(revenue1);

					}
					float rpkm1 = 0;
					if (lYieldJSONObj.has("rpkm_1") && lYieldJSONObj.get("rpkm_1") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("rpkm_1").toString())) {
						rpkm1 = Float.parseFloat(lYieldJSONObj.get("rpkm_1").toString());
						lYield.setRpkm1(rpkm1);

					}
					float yield = 0;
					if (lYieldJSONObj.has("Yield") && lYieldJSONObj.get("Yield") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("Yield").toString())) {
						yield = Float.parseFloat(lYieldJSONObj.get("Yield").toString());
						lYield.setYield(yield);

					}
					float yield1 = 0;
					if (lYieldJSONObj.has("Yield_1") && lYieldJSONObj.get("Yield_1") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("Yield_1").toString())) {
						yield1 = Float.parseFloat(lYieldJSONObj.get("Yield_1").toString());
						lYield.setYield1(yield1);

					}
					float rasm1 = 0;
					if (lYieldJSONObj.has("rasm_1") && lYieldJSONObj.get("rasm_1") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("rasm_1").toString())) {
						rasm1 = Float.parseFloat(lYieldJSONObj.get("rasm_1").toString());
						lYield.setRasm1(rasm1);

					}
					float seatfactor1 = 0;
					if (lYieldJSONObj.has("Seat_Factor_1") && lYieldJSONObj.get("Seat_Factor_1") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("Seat_Factor_1").toString())) {
						seatfactor1 = Float.parseFloat(lYieldJSONObj.get("Seat_Factor_1").toString());
						lYield.setSeatfactor1(seatfactor1);

					}

					float yieldtarget = 0;
					if (lYieldJSONObj.has("yield_target") && lYieldJSONObj.get("yield_target") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("yield_target").toString())) {
						yieldtarget = Float.parseFloat(lYieldJSONObj.get("yield_target").toString());
						lYield.setYieldtarget(yieldtarget);

					}

					float seatfactortarget = 0;
					if (lYieldJSONObj.has("seat_factor_target") && lYieldJSONObj.get("seat_factor_target") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("seat_factor_target").toString())) {
						seatfactortarget = Float.parseFloat(lYieldJSONObj.get("seat_factor_target").toString());
						lYield.setSeatfactortarget(seatfactortarget);

					}

					float paxtarget = 0;
					if (lYieldJSONObj.has("pax_targer") && lYieldJSONObj.get("pax_targer") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("pax_targer").toString())) {
						paxtarget = Float.parseFloat(lYieldJSONObj.get("pax_targer").toString());
						lYield.setPaxtarget(paxtarget);

					}
					float revenuetarget = 0;
					if (lYieldJSONObj.has("revenue_targer") && lYieldJSONObj.get("revenue_targer") != null
							&& !"null".equalsIgnoreCase(lYieldJSONObj.get("revenue_targer").toString())) {
						revenuetarget = Float.parseFloat(lYieldJSONObj.get("revenue_targer").toString());
						lYield.setRevenuetarget(revenuetarget);

					}

					String compartment = "_";
					lYield.setCompartment(compartment);

					lYieldList.add(lYield);

				}
				Yieldrasm lYieldrasm = null;
				for (Yield lObj : lYieldList) {
					lYieldrasm = new Yieldrasm();

					lYieldrasm.setOrigin(lObj.getOrigin());
					lYieldrasm.setDestination(lObj.getDestination());
					lYieldrasm.setRuleid(lObj.getRuleid());
					lYieldrasm.setFootnote(lObj.getFootnote());
					lYieldrasm.setTax(lObj.getTax());
					lYieldrasm.setBasefare(lObj.getBasefare());
					lYieldrasm.setSurcharge(lObj.getSurcharge());
					lYieldrasm.setYqcharge(lObj.getYqcharge());
					lYieldrasm.setTotalfare(lObj.getTotalfare());

					float revenue = lObj.getRevenue();
					lYieldrasm.setRevenueytd(revenue);
					float revenue1 = lObj.getRevenue1();
					lYieldrasm.setTotalrevenuelastyr(revenue1);
					float pax = lObj.getPax();
					lYieldrasm.setPaxytd(pax);
					float pax1 = lObj.getPax1();
					lYieldrasm.setTotalpaxlastyr(pax1);
					float rpkm = lObj.getRpkm();
					float yield = ((revenue * 100) / rpkm);
					lYieldrasm.setYieldytd(CalculationUtil.roundAFloat(yield, 1));
					float rpkm1 = lObj.getRpkm1();
					float yield1 = ((revenue1 * 100) / rpkm1);
					if (yield > 0) {
						lYieldrasm.setTotalyieldlastyr((yield1));
					} else {
						lYieldrasm.setTotalyieldlastyr((0));
					}
					float rasm = lObj.getRasm();
					lYieldrasm.setRasmytd(rasm);
					float rasm1 = lObj.getRasm1();
					lYieldrasm.setTotalrasmlastyr(rasm1);
					float seatfactor = lObj.getSeatfactor();
					lYieldrasm.setSeatfactorytd(CalculationUtil.roundAFloat(seatfactor, 2));
					float seatfactor1 = lObj.getSeatfactor1();
					lYieldrasm.setTotalseatfactorlastyr(seatfactor1);
					float revenuetarget = lObj.getRevenuetarget();
					lYieldrasm.setRevenuetarget(revenuetarget);
					float paxtarget = lObj.getPaxtarget();
					lYieldrasm.setPaxtarget(paxtarget);
					float yieldtarget = lObj.getYieldtarget();
					lYieldrasm.setYieldtarget(yieldtarget);
					lYieldrasm.setDep_date(lObj.getDep_date());
					float revenuevlyr = CalculationUtil.calculateVLYR(revenue, revenue1);
					lYieldrasm.setRevenuevlyr((int) (revenuevlyr));

					float paxvlyr = CalculationUtil.calculateVLYR(pax, pax1);
					lYieldrasm.setPaxvlyr(Math.round(paxvlyr));

					float yieldvlyr = CalculationUtil.calculateVLYR(yield, yield1);
					lYieldrasm.setYieldvlyr((yieldvlyr));

					float rasmvlyr = CalculationUtil.calculateVLYR(rasm, rasm1);
					lYieldrasm.setRasmvlyr(Math.round(rasmvlyr));

					float leg1vlyr = CalculationUtil.calculateVLYR(seatfactor, seatfactor1);
					lYieldrasm.setLeg1vlyr(Math.round(leg1vlyr));

					float revenuevtgt = CalculationUtil.calculateVTGT(revenue, revenuetarget);
					lYieldrasm.setRevenuevtgt(Math.round(revenuevtgt));

					float paxvtgt = CalculationUtil.calculateVTGT(pax, paxtarget);
					lYieldrasm.setPaxvtgt(Math.round(paxvtgt));

					float yieldvtgt = CalculationUtil.calculateVTGT(yield, yieldtarget);
					lYieldrasm.setYieldvtgt(Math.round(yieldvtgt));

					lYieldrasmList.add(lYieldrasm);
				}

				responseYieldMap.put("yield rasm", lYieldrasmList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseYieldMap;

	}

	@Override
	public Map<String, Object> getPriceElasticity(RequestModel pRequestModel) {

		JSONArray lTargetPaxArray = null;

		float lPaxYTD = 0;
		float lPaxVLYR = 0;
		float lPaxVTGT = 0;
		float totalPaxYTD = 0;
		float totalPaxVLYR = 0;
		float totalPax_target = 0;
		String combinationKey = null;
		String channel = "_";
		float basefare = 0;
		float lbasefareYTD = 0;
		float totalbasefareYTD = 0;
		float revenueytd = 0;
		float avgfare = 0;
		double forcastpax = 0;
		float lTotalforcastPax = 0;
		float lTotaltargetPax = 0;
		JSONArray forcastpaxarray = null;
		JSONArray paxlastyrArray = null;
		double paxlastyr = 0;
		float lCapacitylastyr = 0;
		JSONArray flownpaxlastyrarray = null;
		double flownpaxlastyr = 0;
		JSONArray lCapacityCarrierArray = null;
		JSONArray lCapacityArray = null;
		JSONArray lCapacitylastyrArray = null;
		Map<String, Object> TotalMap1 = new HashMap<String, Object>();
		Map<String, Object> TotalMap2 = new HashMap<String, Object>();
		Map<String, Object> TotalMap3 = new HashMap<String, Object>();
		List<FilterModel> lPriceList = new ArrayList<FilterModel>();
		Map<String, Float> carrierPaxMap = new HashMap<String, Float>();
		Map<String, Float> capacitylastyrMap = new HashMap<String, Float>();
		List<PriceElasticity> lPriceElasticityList = new ArrayList<PriceElasticity>();
		List<PriceElasticityModel> lAirchargePaxList = new ArrayList<PriceElasticityModel>();
		List<PriceElasticityTotalResponse> lMTotalsList = new ArrayList<PriceElasticityTotalResponse>();

		Map<String, Object> responsePriceElasticityMap = new HashMap<String, Object>();
		FilterModel lFilterModel = new FilterModel();
		Price lPrice = new Price();
		String customersegment = "_";

		ArrayList<DBObject> lPriceElasticityObj = mKpiDao.getPriceElasticity(pRequestModel);
		JSONArray lPriceElasticityData = new JSONArray(lPriceElasticityObj);
		try {
			if (lPriceElasticityData != null) {

				for (int i = 0; i < lPriceElasticityData.length(); i++) {
					JSONObject lPriceJSONObj = lPriceElasticityData.getJSONObject(i);
					lPrice = new Price();
					lFilterModel = new FilterModel();
					System.out.println("lRevenueJSONObj" + lPriceJSONObj);
					if (lPriceJSONObj.has("region") && lPriceJSONObj.get("region") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("region").toString())) {
						lFilterModel.setRegion(lPriceJSONObj.get("region").toString());

					}
					if (lPriceJSONObj.has("dep_date") && lPriceJSONObj.get("dep_date") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("dep_date").toString())) {
						lFilterModel.setDepartureDate(lPriceJSONObj.get("dep_date").toString());

					}
					lFilterModel.setFilterKey(combinationKey);

					if (lPriceJSONObj.has("country") && lPriceJSONObj.get("country") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("country").toString())) {
						lFilterModel.setCountry(lPriceJSONObj.get("country").toString());
					}

					if (lPriceJSONObj.has("pos") && lPriceJSONObj.get("pos") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("pos").toString())) {
						lFilterModel.setPos(lPriceJSONObj.get("pos").toString());
					}

					if (lPriceJSONObj.has("origin") && lPriceJSONObj.get("origin") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("origin").toString())) {
						lFilterModel.setOrigin(lPriceJSONObj.get("origin").toString());
					}
					if (lPriceJSONObj.has("destination") && lPriceJSONObj.get("destination") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("destination").toString())) {
						lFilterModel.setDestination(lPriceJSONObj.get("destination").toString());
					}
					if (lPriceJSONObj.has("compartment") && lPriceJSONObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("compartment").toString())) {
						lFilterModel.setCompartment(lPriceJSONObj.get("compartment").toString());
					}
					if (lPriceJSONObj.has("fare_basis") && lPriceJSONObj.get("fare_basis") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("fare_basis").toString())) {
						lFilterModel.setFareBasis(lPriceJSONObj.get("fare_basis").toString());

					}
					if (lPriceJSONObj.has("base_fare") && lPriceJSONObj.get("base_fare") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("base_fare").toString())) {
						if (lPriceJSONObj.get("base_fare") != null) {
							lFilterModel.setBasefares(Float.parseFloat(lPriceJSONObj.get("base_fare").toString()));
						} else {
							lFilterModel.setBasefares(0);
						}
					}
					if (lPriceJSONObj.has("AIR_CHARGE") && lPriceJSONObj.get("AIR_CHARGE") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("AIR_CHARGE").toString())) {
						lFilterModel.setAirCharge(lPriceJSONObj.get("AIR_CHARGE").toString());
					}
					if (lPriceJSONObj.has("channel") && lPriceJSONObj.get("channel") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("channel").toString())) {
						lFilterModel.setChannel(lPriceJSONObj.get("channel").toString());
					}

					if (lPriceJSONObj.has("segment") && lPriceJSONObj.get("segment") != null
							&& !"[]".equalsIgnoreCase(lPriceJSONObj.get("segment").toString())
							&& !lPriceJSONObj.get("segment").toString().equalsIgnoreCase(null)) {
						if (lPriceJSONObj.get("segment") == null) {
							lFilterModel.setCustomerSegment((lPriceJSONObj.get("segment").toString()));

						} else {
							lFilterModel.setCustomerSegment("_");
						}
					}

					if (lPriceJSONObj.has("price") && lPriceJSONObj.get("price") != null
							&& !"[]".equalsIgnoreCase(lPriceJSONObj.get("price").toString())
							&& !lPriceJSONObj.get("price").toString().equalsIgnoreCase(null)) {
						if (lPriceJSONObj.get("price") == null) {
							lFilterModel.setPrice(Float.parseFloat(lPriceJSONObj.get("price").toString()));

						} else {
							lFilterModel.setPrice(0);
						}
					}

					if (lPriceJSONObj.has("revenue") && lPriceJSONObj.get("revenue") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("revenue").toString())) {
						lFilterModel.setRevenue(Float.parseFloat(lPriceJSONObj.get("revenue").toString()));
					}

					if (lPriceJSONObj.has("pax") && lPriceJSONObj.get("pax") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("pax").toString())) {

						lFilterModel.setPax((int) Float.parseFloat(lPriceJSONObj.get("pax").toString()));
					}
					JSONArray lFlownPaxArray = null;
					double flown = 0;
					if (lPriceJSONObj.has("flown_pax") && lPriceJSONObj.get("flown_pax") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("flown_pax").toString())
							&& !"[]".equalsIgnoreCase(lPriceJSONObj.get("flown_pax").toString())) {
						lFlownPaxArray = new JSONArray(lPriceJSONObj.get("flown_pax").toString());
						if (lFlownPaxArray != null) {
							if (lFlownPaxArray.length() > 0) {
								for (int j = 0; j < lFlownPaxArray.length(); j++) {
									if (lFlownPaxArray != null
											&& !lFlownPaxArray.get(j).toString().equalsIgnoreCase("null")
											&& !lFlownPaxArray.get(j).toString().equalsIgnoreCase("[]")) {
										flown = Utility.findSum(lFlownPaxArray);
										lFilterModel.setFlownpax((float) flown);
									}
								}
							}
						}
					}
					if (lPriceJSONObj.has("flown_pax_1") && lPriceJSONObj.get("flown_pax_1") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("flown_pax_1").toString())) {
						flownpaxlastyrarray = new JSONArray(lPriceJSONObj.get("flown_pax_1").toString());
						if (flownpaxlastyrarray != null) {
							if (flownpaxlastyrarray.length() > 0) {
								flownpaxlastyr = Utility.findSum(flownpaxlastyrarray);
								lFilterModel.setFlownpaxlastyr((float) flownpaxlastyr);
							}
						}
					}
					if (lPriceJSONObj.has("forecast_pax") && lPriceJSONObj.get("forecast_pax") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("forecast_pax").toString())) {

						forcastpaxarray = new JSONArray(lPriceJSONObj.get("forecast_pax").toString());
						if (forcastpaxarray != null) {
							if (forcastpaxarray.length() > 0) {
								forcastpax = Utility.findSum(forcastpaxarray);
								lFilterModel.setPaxForcast((float) (forcastpax));
							}
						}

					}
					if (forcastpaxarray != null) {
						for (int m = 0; m < forcastpaxarray.length(); m++) {
							if (!"null".equalsIgnoreCase(forcastpaxarray.get(m).toString()))
								lTotalforcastPax += Float.parseFloat(forcastpaxarray.get(m).toString());
						}
					}

					if (lPriceJSONObj.has("pax_1") && lPriceJSONObj.get("pax_1") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("pax_1").toString())) {
						paxlastyrArray = new JSONArray(lPriceJSONObj.get("pax_1").toString());
						if (paxlastyrArray != null) {
							if (paxlastyrArray.length() > 0) {
								for (int j = 0; j < paxlastyrArray.length(); j++) {
									if (paxlastyrArray != null
											&& !paxlastyrArray.get(j).toString().equalsIgnoreCase("null")
											&& !paxlastyrArray.get(j).toString().equalsIgnoreCase("[]")) {
										paxlastyr = Utility.findSum(paxlastyrArray);
										lFilterModel.setPaxlastyear((float) paxlastyr);

									}
								}
							}
						}

					}

					if (lPriceJSONObj.has("target_pax") && lPriceJSONObj.get("target_pax") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("target_pax").toString())
							&& !"[]".equalsIgnoreCase(lPriceJSONObj.get("target_pax").toString())) {
						lTargetPaxArray = new JSONArray(lPriceJSONObj.get("target_pax").toString());

					}
					if (lTargetPaxArray != null) {
						for (int m = 0; m < lTargetPaxArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lTargetPaxArray.get(m).toString()))
								lTotaltargetPax += Float.parseFloat(lTargetPaxArray.get(m).toString());
						}
					}
					// Capacity Carrier
					double lCapacityCarrier = 0;
					if (lPriceJSONObj.has("capacity_airline") && lPriceJSONObj.get("capacity_airline") != null
							&& !lPriceJSONObj.get("capacity_airline").toString().equalsIgnoreCase("null")
							&& !lPriceJSONObj.get("capacity_airline").toString().equalsIgnoreCase("[]")) {
						lCapacityCarrierArray = new JSONArray(lPriceJSONObj.get("capacity_airline").toString());
					} else {
						lCapacityCarrierArray = null;
					}
					if (lPriceJSONObj.has("capacity_1") && lPriceJSONObj.get("capacity_1") != null
							&& !"null".equalsIgnoreCase(lPriceJSONObj.get("capacity_1").toString())
							&& !"[]".equalsIgnoreCase(lPriceJSONObj.get("capacity_1").toString())) {
						lCapacitylastyrArray = new JSONArray(lPriceJSONObj.get("capacity_1").toString());
					}
					if (lCapacitylastyrArray != null) {
						for (int m = 0; m < lCapacitylastyrArray.length(); m++) {
							if (!"null".equalsIgnoreCase(lCapacitylastyrArray.get(m).toString()))
								lCapacitylastyr += Float.parseFloat(lCapacitylastyrArray.get(m).toString());
						}
					}

					// Capacity
					double lCapacity = 0;
					if (lPriceJSONObj.has("capacity") && lPriceJSONObj.get("capacity") != null
							&& !lPriceJSONObj.get("capacity").toString().equalsIgnoreCase("null")
							&& !lPriceJSONObj.get("capacity").toString().equalsIgnoreCase("[]")) {
						lCapacityArray = new JSONArray(lPriceJSONObj.get("capacity").toString());
					} else {
						lCapacityArray = null;
					}
					if (lCapacityCarrierArray != null) {
						for (int k = 0; k < lCapacityCarrierArray.length(); k++) {
							carrierPaxMap.put(lCapacityCarrierArray.getString(k), (float) lCapacityArray.getDouble(k));
						}
						for (int k = 0; k < lCapacityCarrierArray.length(); k++) {
							capacitylastyrMap.put(lCapacityCarrierArray.getString(k),
									(float) lCapacitylastyrArray.getDouble(k));
						}
					}
					// months and days
					int lMonth = 0;
					int lDays = 0;
					if (!"null".equalsIgnoreCase(lPriceJSONObj.get("dep_date").toString())) {
						lMonth = Utility.findMonth(lPriceJSONObj.get("dep_date").toString());
						lDays = Utility.findDay(lPriceJSONObj.get("dep_date").toString());
						lFilterModel.setMonths(lMonth);
						lFilterModel.setDays(lDays);
					}

					StringBuilder lStr = CalculationUtil.getFilters(pRequestModel, lFilterModel);
					String keyBuilder = lStr.toString();
					if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
							|| "nullnullnull".equalsIgnoreCase(keyBuilder)
							|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
						keyBuilder = "null";
					}
					if ("Global Head".equalsIgnoreCase(pRequestModel.getUser())) {

						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {

						}

					} else if ("Region Head".equalsIgnoreCase(pRequestModel.getLevel())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getAgencyName() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}
					} else if ("Country Head".equalsIgnoreCase(pRequestModel.getLevel())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(
									lFilterModel.getRegion() + lFilterModel.getCountry() + lFilterModel.getPos()
											+ lFilterModel.getAgencyName() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}
					} else if ("Pricing Analyst".equalsIgnoreCase(pRequestModel.getLevel())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getPos() + lFilterModel.getOrigin() + lFilterModel.getDestination()
									+ lFilterModel.getAgencyName() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						}
					}

					lFilterModel.setFilterKey(lStr.toString());

					// Creating co-ordinates for price Elasticity slope

					if ((int) Float.parseFloat(lFilterModel.getAirCharge()) > 0 && (lFilterModel.getPax()) > 0) {
						PriceElasticityModel lPEModel = new PriceElasticityModel();
						lPEModel.setRegion(lFilterModel.getRegion());
						lPEModel.setCountry(lFilterModel.getCountry());
						lPEModel.setOrigin(lFilterModel.getOrigin());
						lPEModel.setDestination(lFilterModel.getDestination());
						lPEModel.setCompartment(lFilterModel.getCompartment());
						lPEModel.setCustomerSegment(lFilterModel.getCustomerSegment());
						double logAirCharge = CalculationUtil
								.logOfBase((int) Float.parseFloat(lFilterModel.getAirCharge()));
						lPEModel.setAirCharge(Double.toString(logAirCharge));
						double logPax = CalculationUtil.logOfBase((lFilterModel.getPax()));
						lPEModel.setHostPax(Double.toString(logPax));
						lPEModel.setFilterKey(lFilterModel.getFilterKey());
						lAirchargePaxList.add(lPEModel);
					}

					lPriceList.add(lFilterModel);

				}
				for (int i = 0; i < lPriceList.size(); i++) {
					System.out.println("Pax for each year" + lPriceList.get(i).getPax());

				}
				Map<String, PriceElasticity> map = new HashMap<String, PriceElasticity>();

				PriceElasticity lPriceElasticity = null;

				for (FilterModel lPriceObj : lPriceList) {
					if (!map.containsKey(lPriceObj.getFilterKey())) {
						lPriceElasticity = new PriceElasticity();
						lPriceElasticity.setCombinationkey(lPriceObj.getFilterKey());
						lPriceElasticity.setRegion(lPriceObj.getRegion());
						lPriceElasticity.setCountry(lPriceObj.getCountry());
						lPriceElasticity.setOrigin(lPriceObj.getOrigin());
						lPriceElasticity.setPos(lPriceObj.getPos());
						lPriceElasticity.setDestination(lPriceObj.getDestination());
						lPriceElasticity.setCompartment(lPriceObj.getCompartment());
						lPriceElasticity.setBase_fare(lPriceObj.getBasefares());
						lPriceElasticity.setChannel(lPriceObj.getChannel());
						lPriceElasticity.setFare_basis(lPriceObj.getFareBasis());
						lPriceElasticity.setCustomerSegment(lPriceObj.getCustomerSegment());
						lPriceElasticity.setPrice(lPriceObj.getPrice());
						lPriceElasticity.setPax(lPriceObj.getPax());
						lPriceElasticity.setPax_1(lPriceObj.getPaxlastyr());
						lPriceElasticity.setCurrency(lPriceObj.getCurrency());
						lPriceElasticity.setChannel(lPriceObj.getChannel());
						lPriceElasticity.setBase_fare(lPriceObj.getBasefares());
						lPriceElasticity.setPrice(lPriceObj.getPrice());
						lPriceElasticity.setDepdate(lPriceObj.getDepartureDate());

						// flown pax
						float flownpax = lPriceObj.getFlownpax();
						lPriceElasticity.setFlownpax(flownpax);
						float lflownpaxlastyr = lPriceObj.getFlownpaxlastyr();
						lPriceElasticity.setTotalflownpaxlastyr(lflownpaxlastyr);

						int totalPax = lPriceObj.getPax();
						int totalPax_lastyr = lPriceObj.getPaxlastyr();
						lPriceElasticity.setTotalPax((totalPax));
						lPriceElasticity.setTotalPax_lastyr((totalPax_lastyr));

						float totalrevenue = lPriceObj.getRevenue();
						lPriceElasticity.setTotalrevenue((totalrevenue));

						float totalbasefare = lPriceObj.getBasefares();
						lPriceElasticity.setTotalbasefare((totalbasefare));
						// months and days
						int lMonth = 0;
						int lDays = 0;
						if (!"null".equalsIgnoreCase(lPriceObj.getDepartureDate().toString())) {
							lMonth = Utility.findMonth(lPriceObj.getDepartureDate());
							lDays = Utility.findDay(lPriceObj.getDepartureDate());
							lPriceElasticity.setMonths(lMonth);
							lPriceElasticity.setDays(lDays);
						}
						map.put(lPriceObj.getFilterKey(), lPriceElasticity);
					} else {
						for (String lKey : map.keySet()) {
							if (lPriceObj.getFilterKey().equals(lKey)) {
								lPriceElasticity = map.get(lKey);
							}
						}
						lPriceElasticity.setOrigin(lPriceObj.getOrigin());
						lPriceElasticity.setCountry(lPriceObj.getCountry());
						lPriceElasticity.setRegion(lPriceObj.getRegion());
						lPriceElasticity.setDestination(lPriceObj.getDestination());
						lPriceElasticity.setCompartment(lPriceObj.getCompartment());
						lPriceElasticity.setPos(lPriceObj.getPos());
						lPriceElasticity.setBase_fare(lPriceObj.getBasefares());
						lPriceElasticity.setChannel(lPriceObj.getChannel());
						lPriceElasticity.setFare_basis(lPriceObj.getFareBasis());
						lPriceElasticity.setCustomerSegment(lPriceObj.getCustomerSegment());
						lPriceElasticity.setPrice(lPriceObj.getPrice());
						lPriceElasticity.setPax(lPriceObj.getPax());
						lPriceElasticity.setPax_1(lPriceObj.getPaxlastyr());
						lPriceElasticity.setCurrency(lPriceObj.getCurrency());
						lPriceElasticity.setChannel(lPriceObj.getChannel());
						lPriceElasticity.setPrice(lPriceObj.getPrice());
						lPriceElasticity.setDepdate(lPriceObj.getDepartureDate());
						// flown pax
						float flownpax = lPriceObj.getFlownpax() + lPriceElasticity.getFlownpax();
						lPriceElasticity.setFlownpax(flownpax);
						float lflownpaxlastyr = lPriceObj.getFlownpaxlastyr()
								+ lPriceElasticity.getTotalflownpaxlastyr();
						lPriceElasticity.setTotalflownpaxlastyr(lflownpaxlastyr);

						int totalPax = lPriceObj.getPax() + lPriceElasticity.getTotalPax();
						int totalPax_lastyr = lPriceObj.getPaxlastyr() + lPriceElasticity.getTotalPax_lastyr();
						lPriceElasticity.setTotalPax((totalPax));
						lPriceElasticity.setTotalPax_lastyr((totalPax_lastyr));
						float totalrevenue = lPriceObj.getRevenue() + lPriceElasticity.getTotalrevenue();
						lPriceElasticity.setTotalrevenue((totalrevenue));

						float totalbasefare = lPriceElasticity.getTotalbasefare() + lPriceObj.getBasefares();
						lPriceElasticity.setTotalbasefare((totalbasefare));
						// months and days
						int lMonth = 0;
						int lDays = 0;
						if (!"null".equalsIgnoreCase(lPriceObj.getDepartureDate().toString())) {
							lMonth = Utility.findMonth(lPriceObj.getDepartureDate());
							lDays = Utility.findDay(lPriceObj.getDepartureDate());
							lPriceElasticity.setMonths(lMonth);
							lPriceElasticity.setDays(lDays);
						}

					}

				}
				lPriceElasticity = new PriceElasticity();
				for (String key : map.keySet()) {

					lPriceElasticity = new PriceElasticity();
					if ("Global Head".equalsIgnoreCase(pRequestModel.getUser())) {
						lPriceElasticity.setRegion(map.get(key).getRegion());
						lPriceElasticity.setCompartment(map.get(key).getCompartment());
					} else if ("Region Head".equalsIgnoreCase(pRequestModel.getLevel())) {
						lPriceElasticity.setRegion(map.get(key).getRegion());
						lPriceElasticity.setCountry(map.get(key).getCountry());
						lPriceElasticity.setCompartment(map.get(key).getCompartment());
					} else if ("Country Head".equalsIgnoreCase(pRequestModel.getLevel())) {
						lPriceElasticity.setRegion(map.get(key).getRegion());
						lPriceElasticity.setCountry(map.get(key).getCountry());
						lPriceElasticity.setPos(map.get(key).getPos());
						lPriceElasticity.setCompartment(map.get(key).getCompartment());
					} else if ("Pricing Analyst".equalsIgnoreCase(pRequestModel.getLevel())) {
						lPriceElasticity.setRegion(map.get(key).getRegion());
						lPriceElasticity.setCountry(map.get(key).getCountry());
						lPriceElasticity.setPos(map.get(key).getPos());
						lPriceElasticity.setOrigin(map.get(key).getOrigin());
						lPriceElasticity.setDestination(map.get(key).getDestination());
						lPriceElasticity.setCompartment(map.get(key).getCompartment());
					}
					float lPE = (CalculationUtil.calculatePriceElasticity(lAirchargePaxList,
							map.get(key).getCombinationkey()));
					lPriceElasticity.setPriceElasticity(lPE);
					float pForcastPax = 0;
					int totaldaysFromdate = 0;
					Date date1 = null;
					Date date2 = null;
					if (!pRequestModel.getFromDate().isEmpty() && pRequestModel.getFromDate() != null
							&& !pRequestModel.getToDate().isEmpty() && pRequestModel.getToDate() != null) {
						date1 = Utility.convertStringToDateFromat(pRequestModel.getFromDate());
						date2 = Utility.convertStringToDateFromat(pRequestModel.getToDate());
					} else {
						date1 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
						date2 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
					}
					// int result = str2.compareTo(str1);
					int diff = (int) Utility.getDifferenceDays(date1, date2);
					int result = diff + 1;
					if (result == 0) {
						totaldaysFromdate = result + 1;
					}
					// no. of days
					int noOfDays = Utility.numberOfDaysInMonth(
							Utility.findMonth(map.get(key).getDepdate().toString()) - 1,
							Utility.findYear(map.get(key).getDepdate().toString()));
					float targetProratedPax = ((lTotaltargetPax / (float) noOfDays) * result);
					float proratedForcastPax = (((float) forcastpax / (float) noOfDays) * result);

					// months and days
					int lMonth = 0;
					int lDays = 0;
					if (!"null".equalsIgnoreCase(map.get(key).getDepdate().toString())) {
						lMonth = Utility.findMonth(map.get(key).getDepdate());
						lDays = Utility.findDay(map.get(key).getDepdate());
						lPriceElasticity.setMonths(lMonth);
						lPriceElasticity.setDays(lDays);
					}
					if (lPriceElasticity.getMonths() == map.get(key).getMonths()) {
						float lTotaltargetProratedPax = targetProratedPax;
						float lTotalproratedForcastPax = proratedForcastPax;
						lPriceElasticity.setTotalforcastpax(lTotalproratedForcastPax);
						lPriceElasticity.setTotaltargetproratedpax(lTotaltargetProratedPax);

					} else {
						float lTotaltargetProratedPax = targetProratedPax + lTotaltargetPax;
						float lTotalproratedForcastPax = proratedForcastPax + lTotalforcastPax;
						lPriceElasticity.setTotalforcastpax(lTotalproratedForcastPax);
						lPriceElasticity.setTotaltargetproratedpax(lTotaltargetProratedPax);

					}
					// pax vtgt
					if (diff == 0) {
						pForcastPax = 0;

					} else {
						pForcastPax = lPriceElasticity.getTotalforcastpax();

					}
					float paxvtgt = 0;
					float pflownpax = map.get(key).getFlownpax();
					lPriceElasticity.setFlownpax(pflownpax);
					if (targetProratedPax != 0) {
						paxvtgt = CalculationUtil.calculateVTGTRemodelled(pflownpax, pForcastPax, targetProratedPax);
					}
					lPriceElasticity.setPaxVTGT(paxvtgt);
					float totalbasefare = map.get(key).getTotalbasefare();
					lbasefareYTD = totalbasefare;
					totalbasefareYTD += lbasefareYTD;
					lPriceElasticity.setTotalbasefare((map.get(key).getTotalbasefare()));
					float totalPax = map.get(key).getTotalPax();
					lPaxYTD = totalPax;
					totalPaxYTD += lPaxYTD;
					lPriceElasticity.setTotalPax((map.get(key).getTotalPax()));
					lPriceElasticity.setPaxYTD((lPaxYTD));

					float totalrevenue = map.get(key).getTotalrevenue();
					revenueytd = totalrevenue;

					avgfare = revenueytd / lPaxYTD;
					lPriceElasticity.setPrice((avgfare));
					String host = "FZ";
					float hostcapacity = 0;

					if (carrierPaxMap.containsKey(host)) {
						hostcapacity = (carrierPaxMap.get(host));
						lPriceElasticity.setHostcapacity(hostcapacity);
					}
					float hostcapacitylastyr = 0;
					if (carrierPaxMap.containsKey(host)) {
						hostcapacitylastyr = (capacitylastyrMap.get(host));
						lPriceElasticity.setHostcapacitylastyr(hostcapacitylastyr);
					}
					float lFlownpaxlastyr = map.get(key).getTotalflownpaxlastyr();
					lPriceElasticity.setTotalflownpaxlastyr(lFlownpaxlastyr);

					float totalPax_lastyr = map.get(key).getTotalPax_lastyr();
					lPriceElasticity.setTotalPax_lastyr((int) totalPax_lastyr);
					if (lFlownpaxlastyr != 0) {
						lPaxVLYR = CalculationUtil.calculateVLYR(pflownpax, lFlownpaxlastyr, hostcapacity,
								hostcapacitylastyr);
						lPriceElasticity.setPaxVLYR((lPaxVLYR));

					} else {
						lPriceElasticity.setPaxVLYR((lPaxVLYR));
					}
					totalPaxVLYR += totalPax_lastyr;

					lPriceElasticityList.add(lPriceElasticity);

				}
				float totalPaxY = 0;
				float totalPaxlastyrY = 0;
				float totalPaxtargetY = 0;
				float totalPaxJ = 0;
				float totalPaxlastyrJ = 0;
				float totalPaxtargetJ = 0;
				float totalPaxA = 0;
				float totalPaxlastyrA = 0;
				float totalPaxtargetA = 0;
				float totalForcastPaxofY = 0;
				float totalTargetProratedPaxofY = 0;
				float totalFlownPaxofY = 0;
				float totalhostcapacityofY = 0;
				float totalhostcapacitylastyrofY = 0;
				float totalflownpaxlastyrofY = 0;
				PriceElasticityTotalResponse lModel = new PriceElasticityTotalResponse();
				for (PriceElasticity lObj : lPriceElasticityList) {
					if (lObj.getCompartment().equals("Y")) {
						totalPaxY += lObj.getTotalPax();
						totalPaxlastyrY += lObj.getTotalPax_lastyr();
						totalPaxtargetY += lObj.getTotalpaxtarget();
						totalForcastPaxofY += lObj.getTotalforcastpax();
						totalTargetProratedPaxofY += lObj.getTotaltargetproratedpax();
						totalFlownPaxofY += lObj.getFlownpax();
						totalhostcapacityofY += lObj.getHostcapacity();
						totalhostcapacitylastyrofY += lObj.getHostcapacitylastyr();
						totalflownpaxlastyrofY += lObj.getTotalflownpaxlastyr();
						float lflownpaxlastyr = totalflownpaxlastyrofY;

						float hostcapacity = totalhostcapacityofY;
						float hostcapacitylastyr = totalhostcapacitylastyrofY;
						float pForcastPax = 0;
						int totaldaysFromdate = 0;
						Date date1 = null;
						Date date2 = null;
						if (!pRequestModel.getFromDate().isEmpty() && pRequestModel.getFromDate() != null
								&& !pRequestModel.getToDate().isEmpty() && pRequestModel.getToDate() != null) {
							date1 = Utility.convertStringToDateFromat(pRequestModel.getFromDate());
							date2 = Utility.convertStringToDateFromat(pRequestModel.getToDate());
						} else {
							date1 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
							date2 = Utility.convertStringToDateFromat(Utility.getCurrentDate());
						}
						int diff = (int) Utility.getDifferenceDays(date1, date2);
						int result = diff + 1;
						if (result == 0) {
							totaldaysFromdate = result + 1;
						}
						if (diff == 0) {
							pForcastPax = 0;

						} else {
							pForcastPax = totalForcastPaxofY;

						}
						float lTotalPaxVLYRofY = 0;
						if (lflownpaxlastyr > 0) {
							lTotalPaxVLYRofY = CalculationUtil.calculateVLYR(totalFlownPaxofY, lflownpaxlastyr,
									hostcapacity, hostcapacitylastyr);

						} else {
							lTotalPaxVLYRofY = 0;
						}
						float lTotalPaxVTGTofY = 0;
						if (totalTargetProratedPaxofY != 0 && totalTargetProratedPaxofY > 0) {
							lTotalPaxVTGTofY = CalculationUtil.calculateVTGTRemodelled(totalFlownPaxofY, pForcastPax,
									totalTargetProratedPaxofY);

						} else {
							lTotalPaxVTGTofY = 0;
						}
						Map<String, Object> map1 = new HashMap<String, Object>();
						map1.put("Total Pax YTD", (totalPaxY));
						map1.put("Total Pax VLYR", (lTotalPaxVLYRofY));
						map1.put("Total pax VTGT", (lTotalPaxVTGTofY));

						TotalMap1.put("Totals: " + lObj.getCompartment(), map1);
					}
					if (lObj.getCompartment().equals("J")) {
						totalPaxJ += lObj.getTotalPax();
						totalPaxlastyrJ += lObj.getTotalPax_lastyr();
						totalPaxtargetJ += lObj.getTotalpaxtarget();
						float lTotalPaxVLYRofJ = 0;
						if (totalPaxlastyrJ > 0) {
							lTotalPaxVLYRofJ = CalculationUtil.calculateVLYR(totalPaxJ, totalPaxlastyrJ);

						} else {
							lTotalPaxVLYRofJ = 0;
						}
						float lTotalPaxVTGTofJ = 0;
						if (totalPaxtargetJ != 0 && totalPaxtargetJ > 0) {
							lTotalPaxVTGTofJ = CalculationUtil.calculateVTGT(totalPaxJ, totalPaxtargetJ);

						} else {
							lTotalPaxVTGTofJ = 0;
						}
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("Total Pax YTD", Math.round(totalPaxJ));
						map2.put("Total Pax VLYR", Math.round(lTotalPaxVLYRofJ));
						map2.put("Total Pax VTGT", Math.round(lTotalPaxVTGTofJ));

						TotalMap2.put("Totals: " + lObj.getCompartment(), map2);
					}
					if ("A".equalsIgnoreCase(lObj.getCompartment())) {
						totalPaxA += lObj.getTotalPax();
						totalPaxlastyrA += lObj.getTotalPax_lastyr();
						totalPaxtargetA += lObj.getTotalpaxtarget();
						float lTotalPaxVLYRofA = 0;
						if (totalPaxlastyrA > 0) {
							lTotalPaxVLYRofA = CalculationUtil.calculateVLYR(totalPaxA, totalPaxlastyrA);

						} else {
							lTotalPaxVLYRofA = 0;
						}
						float lTotalPaxVTGTofA = 0;
						if (totalPaxtargetA != 0 && totalPaxtargetA > 0) {
							lTotalPaxVTGTofA = CalculationUtil.calculateVTGT(totalPaxA, totalPaxtargetA);

						} else {
							lTotalPaxVTGTofA = 0;
						}
						Map<String, Object> map3 = new HashMap<String, Object>();
						map3.put("Total Pax YTD", Math.round(totalPaxA));
						map3.put("Total Pax VLYR", Math.round(lTotalPaxVLYRofA));
						map3.put("Total Pax VTGT", Math.round(lTotalPaxVTGTofA));

						TotalMap3.put("Totals: " + lObj.getCompartment(), map3);
					}
				}

				lMTotalsList.add(lModel);
				responsePriceElasticityMap.putAll(TotalMap1);
				responsePriceElasticityMap.putAll(TotalMap2);
				responsePriceElasticityMap.putAll(TotalMap3);

				responsePriceElasticityMap.put("Price Elasticity", lPriceElasticityList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responsePriceElasticityMap;

	}

	@Override
	public Map<String, Object> getPriceElasticityRange(RequestModel pRequestModel) {
		Map<String, Object> PriceElasticityMap = new HashMap<String, Object>();
		Map<String, PriceElasticityRange> csMap = new HashMap<String, PriceElasticityRange>();

		Double totalelasticityrange = 0D;
		Double totalnumoffares = 0D;
		Double totalpercsuccessseatfactor = 0D;
		String combinationKey = null;
		List<PriceElasticityRange> csList = new ArrayList<PriceElasticityRange>(csMap.values());
		List<PriceElasticityRangeTotalResponse> tmList = new ArrayList<PriceElasticityRangeTotalResponse>();
		ArrayList<DBObject> lPriceElasticity = mKpiDao.getPriceElasticityRange(pRequestModel);
		JSONArray data = new JSONArray(csList);
		try {
			if (data != null) {

				for (int i = 0; i < data.length(); i++) {
					JSONObject jsonObj = data.getJSONObject(i);

					if (pRequestModel.getUser() != null && !pRequestModel.getUser().isEmpty()
							&& pRequestModel.getUser().equals("Global Head")) {

						if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = jsonObj.get("region").toString();

						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {

							combinationKey = jsonObj.get("region").toString();
						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() == null) {

							combinationKey = jsonObj.get("region").toString() + jsonObj.get("country").toString()
									+ jsonObj.get("compartment").toString();
						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() == null) {

							combinationKey = jsonObj.get("country").toString() + jsonObj.get("pos").toString()
									+ jsonObj.get("compartment").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("country").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("country").toString()
									+ jsonObj.get("pos").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("country").toString()
									+ jsonObj.get("pos").toString() + jsonObj.get("compartment").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() != null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("country").toString()
									+ jsonObj.get("pos").toString() + jsonObj.get("compartment").toString()
									+ jsonObj.get("origin").toString() + jsonObj.get("destination").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() != null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("origin").toString()
									+ jsonObj.get("destination").toString();
						} else if (pRequestModel.getRegionArray() != null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() != null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("pos").toString()
									+ jsonObj.get("origin").toString() + jsonObj.get("destination").toString();
						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() != null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("country").toString();

						}

						else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("pos").toString();

						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() == null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("compartment").toString();

						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() == null && pRequestModel.getCompartmentArray() == null
								&& pRequestModel.getOdArray() != null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("origin").toString()
									+ jsonObj.get("destination").toString();

						} else if (pRequestModel.getRegionArray() == null && pRequestModel.getCountryArray() == null
								&& pRequestModel.getPosArray() != null && pRequestModel.getCompartmentArray() != null
								&& pRequestModel.getOdArray() != null) {
							combinationKey = jsonObj.get("region").toString() + jsonObj.get("country").toString()
									+ jsonObj.get("pos").toString() + jsonObj.get("compartment").toString()
									+ jsonObj.get("origin").toString() + jsonObj.get("destination").toString();

						}

					}

					String region = "-";
					if (jsonObj.get("region") != null && !"null".equalsIgnoreCase(jsonObj.get("region").toString()))
						region = jsonObj.getString("region");
					String country = "-";
					if (jsonObj.get("country") != null && !"null".equalsIgnoreCase(jsonObj.get("country").toString()))
						country = jsonObj.getString("country");
					String pos = "-";
					if (jsonObj.get("pos") != null && !"null".equalsIgnoreCase(jsonObj.get("pos").toString()))
						pos = jsonObj.getString("pos");
					String compartment = "-";
					if (jsonObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("compartment").toString()))
						compartment = jsonObj.getString("compartment");
					String origin = "-";
					if (jsonObj.get("origin") != null && !"null".equalsIgnoreCase(jsonObj.get("origin").toString()))
						origin = jsonObj.getString("origin");
					String destination = "-";
					if (jsonObj.get("destination") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("destination").toString()))
						destination = jsonObj.getString("destination");
					Double Elasticity_rangeYTD = 0D;
					if (jsonObj.get("Elasticity_range") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("Elasticity_range").toString()))
						Elasticity_rangeYTD = jsonObj.getDouble("Elasticity_range");
					Double Number_of_FaresYTD = 0D;
					if (jsonObj.get("Number_of_Fares") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("Number_of_Fares").toString()))
						Number_of_FaresYTD = jsonObj.getDouble("Number_of_Fares");
					Double percentage_success_seat_factorYTD = 0D;
					if (jsonObj.get("percentage_success_seat_factor") != null
							&& !"null".equalsIgnoreCase(jsonObj.get("percentage_success_seat_factor").toString()))
						percentage_success_seat_factorYTD = jsonObj.getDouble("percentage_success_seat_factor");
					if (!csMap.containsKey(combinationKey)) {
						PriceElasticityRange cs = new PriceElasticityRange();
						cs.setRegion(region);
						cs.setCountry(country);
						cs.setPos(pos);
						cs.setCompartment(compartment);
						cs.setCombinationkey(combinationKey);
						cs.setOrigin(origin);
						cs.setDestination(destination);
						cs.setElasticityrangeytd(Elasticity_rangeYTD);
						cs.setNumberoffaresytd(Number_of_FaresYTD);
						cs.setPercsuccessfactorytd(percentage_success_seat_factorYTD);
						totalelasticityrange += Elasticity_rangeYTD;
						totalnumoffares += Number_of_FaresYTD;
						totalpercsuccessseatfactor += percentage_success_seat_factorYTD;

						csMap.put(combinationKey, cs);

					} else {
						PriceElasticityRange cs = csMap.get(combinationKey);
						cs.setElasticityrangeytd(cs.getElasticityrangeytd() + Elasticity_rangeYTD);
						cs.setNumberoffaresytd(cs.getNumberoffaresytd() + Number_of_FaresYTD);
						cs.setPercsuccessfactorytd(cs.getPercsuccessfactorytd() + percentage_success_seat_factorYTD);

						totalelasticityrange += Elasticity_rangeYTD;
						totalnumoffares += Number_of_FaresYTD;
						totalpercsuccessseatfactor += totalpercsuccessseatfactor;
					}

				}

				for (String key : csMap.keySet()) {
					PriceElasticityRange cs = csMap.get(key);

					cs.setElastricityrange(totalelasticityrange);
					cs.setNumberoffares(totalnumoffares);
					cs.setPercsuccsearfactor(totalpercsuccessseatfactor);

				}

				PriceElasticityRangeTotalResponse tm = new PriceElasticityRangeTotalResponse();
				tm.setTotalnumberoffares(totalnumoffares);
				tm.setTotalpercseatfactor(totalpercsuccessseatfactor);

				tmList.add(tm);
				PriceElasticityMap.put("pricelasticityRangeMap", csList);
				PriceElasticityMap.put("priceElasticityRangeTotalsMap", tmList);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PriceElasticityMap;

	}

	@Override
	public Map<String, Object> getTotalEffectiveIneffectiveFares(RequestModel pRequestModel) {
		ArrayList<DBObject> lTotalEffectiveIneffectiveObj = mKpiDao.getTotalEffectiveIneffectiveFares(pRequestModel);
		JSONArray lTotalEffectiveIneffectiveData = new JSONArray(lTotalEffectiveIneffectiveObj);

		JSONArray lTargetPaxArray = null;
		JSONArray lTargetPricePerformanceArray = null;
		JSONArray lTargetPriceElasticityArray = null;
		JSONArray paxarray = null;
		JSONArray pax1Array = null;
		JSONArray revnuearray = null;
		JSONArray revenue1Array = null;
		List<FilterModel> lCustomerSegmentDataList = new ArrayList<FilterModel>();
		List<PriceElasticityModel> lAirchargePaxList = new ArrayList<PriceElasticityModel>();
		FilterModel lFilterModel = new FilterModel();
		Map<String, Object> responseTotalEffectiveIneffectiveFaresMap = new HashMap<String, Object>();

		try {

			if (lTotalEffectiveIneffectiveData != null) {
				for (int i = 0; i < lTotalEffectiveIneffectiveData.length(); i++) {
					lFilterModel = new FilterModel();
					JSONObject lTotalEffectiveJSONObj = lTotalEffectiveIneffectiveData.getJSONObject(i);
					TotalEffective lTotalEffective = new TotalEffective();
					System.out.println("lTotalEffectiveJSONObj" + lTotalEffectiveJSONObj);

					if (lTotalEffectiveJSONObj.has("region") && lTotalEffectiveJSONObj.get("region") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("region").toString())) {
						if (lTotalEffectiveJSONObj.get("region").equals("na")) {
							lFilterModel.setRegion("-");
						} else {
							lFilterModel.setRegion(lTotalEffectiveJSONObj.get("region").toString());
						}

					}
					if (lTotalEffectiveJSONObj.has("country") && lTotalEffectiveJSONObj.get("country") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("country").toString())) {
						if (lTotalEffectiveJSONObj.get("country").equals("na")) {
							lFilterModel.setCountry("-");
						} else {
							lFilterModel.setCountry(lTotalEffectiveJSONObj.get("country").toString());
						}
					}
					if (lTotalEffectiveJSONObj.has("ruleID") && lTotalEffectiveJSONObj.get("ruleID") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("ruleID").toString())) {
						String ruleId = (lTotalEffectiveJSONObj.get("ruleID").toString());
						lFilterModel.setRuleId(ruleId);

					}
					String footnote = "_";
					if (lTotalEffectiveJSONObj.has("footNote") && lTotalEffectiveJSONObj.get("footNote") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("footNote").toString())) {
						footnote = (lTotalEffectiveJSONObj.get("footNote").toString());
						if (!footnote.isEmpty()) {
							lFilterModel.setFootNote(footnote);
						} else {
							lFilterModel.setFootNote("NA");
						}

					}
					float baseFare = 0;
					if (lTotalEffectiveJSONObj.has("baseFare") && lTotalEffectiveJSONObj.get("baseFare") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("baseFare").toString())) {
						baseFare = Float.parseFloat(lTotalEffectiveJSONObj.get("baseFare").toString());
						lFilterModel.setBasefares(baseFare);

					}
					float surCharge = 0;
					if (lTotalEffectiveJSONObj.has("surCharge") && lTotalEffectiveJSONObj.get("surCharge") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("surCharge").toString())) {
						surCharge = Float.parseFloat(lTotalEffectiveJSONObj.get("surCharge").toString());
						lFilterModel.setSurCharge(Float.toString(surCharge));

					}
					float yqCharge = 0;
					if (lTotalEffectiveJSONObj.has("yqCharge") && lTotalEffectiveJSONObj.get("yqCharge") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("yqCharge").toString())) {
						yqCharge = Float.parseFloat(lTotalEffectiveJSONObj.get("yqCharge").toString());
						lFilterModel.setYqCharge(Float.toString(yqCharge));

					}
					float taxes = 0;
					if (lTotalEffectiveJSONObj.has("taxes") && lTotalEffectiveJSONObj.get("taxes") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("taxes").toString())) {
						taxes = Float.parseFloat(lTotalEffectiveJSONObj.get("taxes").toString());
						lFilterModel.setTaxes(Float.toString(taxes));

					}
					float totalFare = 0;
					if (lTotalEffectiveJSONObj.has("totalFare") && lTotalEffectiveJSONObj.get("totalFare") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("totalFare").toString())) {
						totalFare = Float.parseFloat(lTotalEffectiveJSONObj.get("totalFare").toString());
						lFilterModel.setTotalFare(Float.toString(totalFare));

					}
					if (lTotalEffectiveJSONObj.has("pos") && lTotalEffectiveJSONObj.get("pos") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("pos").toString())) {
						lFilterModel.setPos(lTotalEffectiveJSONObj.get("pos").toString());
					}
					if (lTotalEffectiveJSONObj.has("origin") && lTotalEffectiveJSONObj.get("origin") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("origin").toString())) {
						lFilterModel.setOrigin(lTotalEffectiveJSONObj.get("origin").toString());
					}
					if (lTotalEffectiveJSONObj.has("destination") && lTotalEffectiveJSONObj.get("destination") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("destination").toString())) {
						lFilterModel.setDestination(lTotalEffectiveJSONObj.get("destination").toString());
					}
					if (lTotalEffectiveJSONObj.has("compartment") && lTotalEffectiveJSONObj.get("compartment") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("compartment").toString())) {
						lFilterModel.setCompartment(lTotalEffectiveJSONObj.get("compartment").toString());
					}
					if (lTotalEffectiveJSONObj.has("fare") && lTotalEffectiveJSONObj.get("fare") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("fare").toString())) {
						lFilterModel.setFares(Float.parseFloat(lTotalEffectiveJSONObj.get("fare").toString()));
					}
					if (lTotalEffectiveJSONObj.has("currency") && lTotalEffectiveJSONObj.get("currency") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("currency").toString())) {
						lFilterModel.setCurrency((lTotalEffectiveJSONObj.get("currency").toString()));
					}
					double revenueArray = 0;
					float revenue = 0;
					if (lTotalEffectiveJSONObj.has("revenue") && lTotalEffectiveJSONObj.get("revenue") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("revenue").toString())) {
						revnuearray = new JSONArray(lTotalEffectiveJSONObj.get("revenue").toString());
						if (revnuearray != null) {
							if (revnuearray.length() > 0) {
								revenueArray = Utility.findSum(revnuearray);
								revenue = (float) revenueArray;
								lFilterModel.setRevenue((float) revenueArray);
							}
						}
					}

					int targetrevenue = 0;
					if (lTotalEffectiveJSONObj.has("targetrevenue")
							&& lTotalEffectiveJSONObj.get("targetrevenue") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("targetrevenue").toString())) {
						targetrevenue = Integer.parseInt(lTotalEffectiveJSONObj.get("targetrevenue").toString());

					} else {
						targetrevenue = 17386;
					}

					if (lTotalEffectiveJSONObj.has("fare_basis") && lTotalEffectiveJSONObj.get("fare_basis") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("fare_basis").toString())) {

						lFilterModel.setFareBasis(lTotalEffectiveJSONObj.get("fare_basis").toString());
					}
					if (lTotalEffectiveJSONObj.has("RBD") && lTotalEffectiveJSONObj.get("RBD") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("RBD").toString())) {

						lFilterModel.setRbd(lTotalEffectiveJSONObj.get("RBD").toString());
					}

					double paxArray = 0;
					float pax = 0;
					if (lTotalEffectiveJSONObj.has("pax") && lTotalEffectiveJSONObj.get("pax") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("pax").toString())) {
						paxarray = new JSONArray(lTotalEffectiveJSONObj.get("pax").toString());
						if (paxarray != null) {
							if (paxarray.length() > 0) {
								paxArray = Utility.findSum(paxarray);
								pax = (float) paxArray;
								lFilterModel.setPax((int) pax);
							}
						}
					}

					double pax1 = 0D;
					float Pax1 = 0;
					if (lTotalEffectiveJSONObj.has("pax_1") && lTotalEffectiveJSONObj.get("pax_1") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("pax_1").toString())) {
						pax1Array = new JSONArray(lTotalEffectiveJSONObj.get("pax_1").toString());
						if (pax1Array != null) {
							if (pax1Array.length() > 0) {
								pax1 = Utility.findSum(pax1Array);
								Pax1 = (float) pax1;
								lFilterModel.setPaxlastyr((int) pax1);
							}
						}
					}
					double revenue1 = 0D;
					float Revenue1 = 0;
					if (lTotalEffectiveJSONObj.has("revenue_1") && lTotalEffectiveJSONObj.get("revenue_1") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("revenue_1").toString())) {
						revenue1Array = new JSONArray(lTotalEffectiveJSONObj.get("revenue_1").toString());
						if (revenue1Array != null) {
							if (revenue1Array.length() > 0) {
								revenue1 = Utility.findSum(revenue1Array);
								Revenue1 = (float) revenue1;
								lFilterModel.setRevenuelastyr((int) Revenue1);
							}
						}
					}
					double targetpaxarray = 0;
					float paxtarget = 0;
					if (lTotalEffectiveJSONObj.has("target_pax") && lTotalEffectiveJSONObj.get("target_pax") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("target_pax").toString())
							&& !"[]".equalsIgnoreCase(lTotalEffectiveJSONObj.get("target_pax").toString())) {
						lTargetPaxArray = new JSONArray(lTotalEffectiveJSONObj.get("target_pax").toString());
						if (lTargetPaxArray != null) {
							if (lTargetPaxArray.length() > 0) {
								targetpaxarray = Utility.findSum(lTargetPaxArray);
								paxtarget = (float) targetpaxarray;
								lFilterModel.setTargetpax((float) targetpaxarray);
							}
						}
					}
					JSONArray airchargeArray = new JSONArray();
					if (lTotalEffectiveJSONObj.has("air_charge") && lTotalEffectiveJSONObj.get("air_charge") != null
							&& !"null".equalsIgnoreCase(lTotalEffectiveJSONObj.get("air_charge").toString())
							&& !"[]".equalsIgnoreCase(lTotalEffectiveJSONObj.get("air_charge").toString())) {
						airchargeArray = new JSONArray(lTotalEffectiveJSONObj.get("air_charge").toString());

					}
					if (lTotalEffectiveJSONObj.has("Host_priceperformance_revenue")
							&& lTotalEffectiveJSONObj.get("Host_priceperformance_revenue") != null
							&& !"null".equalsIgnoreCase(
									lTotalEffectiveJSONObj.get("Host_priceperformance_revenue").toString())
							&& !"[]".equalsIgnoreCase(
									lTotalEffectiveJSONObj.get("Host_priceperformance_revenue").toString())) {
						lTargetPricePerformanceArray = new JSONArray(
								lTotalEffectiveJSONObj.get("Host_priceperformance_revenue").toString());
					}
					if (lTotalEffectiveJSONObj.has("Host_priceelasticity_revenue")
							&& lTotalEffectiveJSONObj.get("Host_priceelasticity_revenue") != null
							&& !"null".equalsIgnoreCase(
									lTotalEffectiveJSONObj.get("Host_priceelasticity_revenue").toString())
							&& !"[]".equalsIgnoreCase(
									lTotalEffectiveJSONObj.get("Host_priceelasticity_revenue").toString())) {
						lTargetPriceElasticityArray = new JSONArray(
								lTotalEffectiveJSONObj.get("Host_priceelasticity_revenue").toString());
					}
					StringBuilder lStr = CalculationUtil.getFilters(pRequestModel, lFilterModel);
					String keyBuilder = lStr.toString();
					if ("null".equalsIgnoreCase(keyBuilder) || "nullnull".equalsIgnoreCase(keyBuilder)
							|| "nullnullnull".equalsIgnoreCase(keyBuilder)
							|| "nullnullnullnull".equalsIgnoreCase(keyBuilder)) {
						keyBuilder = "null";
					}
					if ("Global Head".equalsIgnoreCase(pRequestModel.getUser())) {

						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCustomerSegment());
						}

					} else if ("Region Head".equalsIgnoreCase(pRequestModel.getUser())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCustomerSegment());
						}
					} else if ("Country Head".equalsIgnoreCase(pRequestModel.getUser())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCustomerSegment());
						}
					} else if ("Pricing Analyst".equalsIgnoreCase(pRequestModel.getUser())) {
						if ("null".equalsIgnoreCase(keyBuilder)) {
							lFilterModel.setFilterKey(lFilterModel.getRegion() + lFilterModel.getCountry()
									+ lFilterModel.getPos() + lFilterModel.getCompartment());
							lStr = new StringBuilder();
							lStr.append(lFilterModel.getFilterKey());
						} else {
							lStr.append(lFilterModel.getCustomerSegment());
						}
					}

					lFilterModel.setFilterKey(lStr.toString());
					PriceElasticityModel lPEModel = new PriceElasticityModel();
					if (airchargeArray != null) {
						for (int c = 0; c < airchargeArray.length(); c++) {
							if (!"null".equalsIgnoreCase(paxarray.get(c).toString())
									&& !"[]".equalsIgnoreCase(paxarray.get(c).toString())) {
								if ((airchargeArray.get(c)) != null && (paxarray.get(c) != null)) {
									if ((int) Float.parseFloat(airchargeArray.get(c).toString()) > 0
											&& (int) Float.parseFloat(paxarray.get(c).toString()) > 0) {
										lPEModel.setRegion(lFilterModel.getRegion());
										lPEModel.setCountry(lFilterModel.getCountry());
										lPEModel.setOrigin(lFilterModel.getOrigin());
										lPEModel.setDestination(lFilterModel.getDestination());
										lPEModel.setCompartment(lFilterModel.getCompartment());
										lPEModel.setRbd(lFilterModel.getRbd());
										lPEModel.setFarebasis(lFilterModel.getFareBasis());

										double logAirCharge = CalculationUtil
												.logOfBase((int) Float.parseFloat(airchargeArray.get(c).toString()));
										lPEModel.setAirCharge(Double.toString(logAirCharge));
										double logPax = CalculationUtil
												.logOfBase((int) Float.parseFloat(paxarray.get(c).toString()));
										lPEModel.setHostPax(Double.toString(logPax));
										lPEModel.setFilterKey(lFilterModel.getFilterKey());
										lAirchargePaxList.add(lPEModel);
									}
								}
							}
						}
					}
					float lPE = CalculationUtil.roundToTwoDecimal(
							CalculationUtil.calculatePriceElasticity(lAirchargePaxList, lPEModel.getFilterKey()), 2);
					lFilterModel.setPriceElasticity((lPE));
					// revenue vlyr calculation
					float revenueVLYR = CalculationUtil.calculateVLYR(revenue, (float) Revenue1);
					lFilterModel.setRevenueVLYR(Math.round(revenueVLYR));

					// pax vlyr calculation
					float paxVLYR = CalculationUtil.calculateVLYR(pax, (float) pax1);
					lFilterModel.setPaxVLYR(Math.round(paxVLYR));

					// pax vtgt calculation
					float paxvtgt = CalculationUtil.calculateVTGT(pax, paxtarget);
					lFilterModel.setPaxVTGT(Math.round(paxvtgt));

					// effectiveness calculation
					float effectiveness = CalculationUtil.calculateEffectiveness(revenueVLYR, paxVLYR);
					lFilterModel.setEffectiveness(Math.round(effectiveness));

					// Price Performance Calculation
					// TODO for now once it coming from db it will be calculated
					float lMarketShareVTGT = 0;
					float lFMS = 0;
					float lRevenueVTGT = 5;
					float lPPScore = CalculationUtil.calculatePricePerformance(lRevenueVTGT, lMarketShareVTGT);
					lFilterModel.setPricePerformance(Float.toString(lPPScore));

					System.out.println(
							"lTotalEffective" + lTotalEffective.getPax() + "," + lTotalEffective.getPricePerformance());
					lCustomerSegmentDataList.add(lFilterModel);
				}
				responseTotalEffectiveIneffectiveFaresMap.put("totaleffective", lCustomerSegmentDataList);
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return responseTotalEffectiveIneffectiveFaresMap;
	}

}