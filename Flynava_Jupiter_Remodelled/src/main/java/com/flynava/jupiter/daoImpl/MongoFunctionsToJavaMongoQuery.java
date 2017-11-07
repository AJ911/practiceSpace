package com.flynava.jupiter.daoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.model.RequestModel;
import com.flynava.jupiter.util.Constants;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@Repository
public class MongoFunctionsToJavaMongoQuery {

	@Autowired
	MongoTemplate mongoTemplateDemoDB;

	/**
	 * This method is a conversion of the mongo function
	 * JUP_FN_workflow_Flight_Forecast
	 * 
	 * @param mRequestModel
	 *            with parameters "user":"Global Head", "level":"network",
	 *            "singleOD":"DXBDOH", "fromDate":"2017-02-01",
	 *            "toDate":"2017-02-28"
	 * @return ArrayList<DBObject>
	 * 
	 */

	public ArrayList<DBObject> getForecastThroughJavaMongoQuery(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		String startDate = null;
		if (mRequestModel.getFromDate() != null && !mRequestModel.getFromDate().isEmpty())
			startDate = mRequestModel.getFromDate().toString();
		else {
			startDate = "2017-02-14";
		}
		String endDate = null;
		if (mRequestModel.getToDate() != null && !mRequestModel.getToDate().isEmpty())
			endDate = mRequestModel.getToDate().toString();
		else {
			endDate = "2017-02-14";
		}
		String od = null;
		if (mRequestModel.getSingleOD() != null && !mRequestModel.getSingleOD().isEmpty())
			od = mRequestModel.getSingleOD().toString();

		ArrayList<String> odList = new ArrayList<String>();
		if (!od.contains("DXB")) {
			String leg1 = od.substring(0, 3) + "DXB";
			String leg2 = "DXB" + od.substring(3, 6);
			odList.add(leg1);
			odList.add(leg2);
		} else {
			odList.add(od);
		}

		// Conversion of mongo function to Java mongo queries

		DBObject query = new QueryBuilder().start()
				.and(new QueryBuilder().start().put("Depart_Date").greaterThanEquals(startDate).get(),
						new QueryBuilder().start().put("Depart_Date").lessThanEquals(endDate).get())
				.get();

		DBObject query1 = new QueryBuilder().start().and(query, new QueryBuilder().start().put("OD").in(odList).get())
				.get();

		/*
		 * DBObject queryOr= new QueryBuilder().start().or(new
		 * BasicDBObject("origin.City", 1).append("origin.City", 1).append("OD",
		 * 1))
		 */

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection coll = db.getCollection("JUP_DB_Forecast_Leg");

		DBObject match = new BasicDBObject("$match", query1);
		DBObject project = new BasicDBObject("$project",
				new BasicDBObject("Flgt_Numb", 1).append("Depart_Date", 1).append("OD", 1).append("Org", 1)
						.append("Des", 1).append("J_Fcst_in_percent", 1).append("Y_Fcst_in_percent", 1)
						.append("TL_Fcst_in_percent", 1).append("forecast_TL_1", 1).append("J_Cab_Bk", 1)
						.append("Y_Cab_Bk", 1)
						.append("Cabin_J_Availability",
								new BasicDBObject("$subtract", Arrays.asList("$Cabin_J_Capacity", "$J_Cab_Bk")))
						.append("Cabin_Y_Availability",
								new BasicDBObject("$subtract", Arrays.asList("$Cabin_Y_Capacity", "$Y_Cab_Bk"))));

		List<DBObject> pipeline = Arrays.asList(match, project);

		AggregationOutput output = coll.aggregate(pipeline);

		for (DBObject result : output.results()) {
			System.out.println(result);
		}

		return lDataList;
	}

	/**
	 * This method is a conversion of the mongo function
	 * JUP_FN_Triggers_Recommendation_test
	 * 
	 * @param mRequestModel
	 *            with parameters "user":"Global Head", "level":"network",
	 *            "regionArray":["MiddleEast"], "countryArray":["AE"],
	 *            "posArray":["DXB"], "originArray":["DXB"],
	 *            "destinationArray":["DOH"], "compartmentArray":["Y","J"],
	 *            "triggerArray":["booking_changes_VTGT"],
	 *            "statusArray":["accepted"]
	 * @return ArrayList<DBObject>
	 * 
	 */
	public ArrayList<DBObject> getTriggersRecommendationThroughJavaMongoQuery(RequestModel mRequestModel) {

		ArrayList<DBObject> lDataList = new ArrayList<DBObject>();

		ArrayList<String> countryList = new ArrayList<String>();
		if (mRequestModel.getCountryArray() != null && mRequestModel.getCountryArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCountryArray().length; i++) {
				countryList.add(mRequestModel.getCountryArray()[i]);
			}
		}

		ArrayList<String> regionList = new ArrayList<String>();
		if (mRequestModel.getRegionArray() != null && mRequestModel.getRegionArray().length > 0) {
			for (int i = 0; i < mRequestModel.getRegionArray().length; i++) {
				regionList.add(mRequestModel.getRegionArray()[i]);
			}
		}

		ArrayList<String> posList = new ArrayList<String>();
		if (mRequestModel.getPosArray() != null && mRequestModel.getPosArray().length > 0) {
			for (int i = 0; i < mRequestModel.getPosArray().length; i++) {
				posList.add(mRequestModel.getPosArray()[i]);
			}
		}

		ArrayList<String> originList = new ArrayList<String>();
		if (mRequestModel.getOriginArray() != null && mRequestModel.getOriginArray().length > 0) {
			for (int i = 0; i < mRequestModel.getOriginArray().length; i++) {
				originList.add(mRequestModel.getOriginArray()[i]);
			}
		}

		ArrayList<String> destinationList = new ArrayList<String>();
		if (mRequestModel.getDestinationArray() != null && mRequestModel.getDestinationArray().length > 0) {
			for (int i = 0; i < mRequestModel.getDestinationArray().length; i++) {
				destinationList.add(mRequestModel.getDestinationArray()[i]);
			}
		}

		ArrayList<String> compartmentList = new ArrayList<String>();
		if (mRequestModel.getCompartmentArray() != null && mRequestModel.getCompartmentArray().length > 0) {
			for (int i = 0; i < mRequestModel.getCompartmentArray().length; i++) {
				compartmentList.add(mRequestModel.getCompartmentArray()[i]);
			}
		}

		ArrayList<String> triggerList = new ArrayList<String>();
		if (mRequestModel.getTriggerArray() != null && mRequestModel.getTriggerArray().length > 0) {
			for (int i = 0; i < mRequestModel.getTriggerArray().length; i++) {
				triggerList.add(mRequestModel.getTriggerArray()[i]);
			}
		}

		ArrayList<String> statusList = new ArrayList<String>();
		if (mRequestModel.getStatusArray() != null && mRequestModel.getStatusArray().length > 0) {
			for (int i = 0; i < mRequestModel.getStatusArray().length; i++) {
				statusList.add(mRequestModel.getStatusArray()[i]);
			}
		}

		DB db = mongoTemplateDemoDB.getDb().getMongo().getDB(Constants.DB_NAME);
		DBCollection coll = db.getCollection("JUP_DB_Workflow_1");
		DBObject query = new QueryBuilder().start().put("region").in(regionList).put("country").in(countryList)
				.put("pos").in(posList).put("origin").in(originList).put("destination").in(destinationList)
				.put("compartment").in(compartmentList).put("trigger_type").in(triggerList).put("status").in(statusList)
				.get();

		DBObject match = new BasicDBObject("$match", query);
		DBObject project = new BasicDBObject("$project", new BasicDBObject("triggerID", "$trigger_id")
				.append("triggerStartdate", "$triggering_data.dep_date_start")
				.append("triggerEnddate", "$triggering_data.dep_date_end")
				.append("recommendationCategory", "$recommendation_category").append("desc", 1).append("region", 1)
				.append("country", 1).append("pos", 1).append("origin", 1).append("destination", 1)
				.append("compartment", 1).append("fareBasis", "$farebasis")
				.append("travelDateFrom", "$host_pricing_data.dep_date_from")
				.append("travelDateTo", "$host_pricing_data.dep_date_to")
				.append("effectiveDateFrom", "$host_pricing_data.effective_from")
				.append("effectiveDateTo", "$host_pricing_data.effective_to")
				.append("saleDateFrom", "$host_pricing_data.sale_date_from")
				.append("saleDateTo", "$host_pricing_data.sale_date_to").append("ruleID", "$host_pricing_data.Rule_id")
				.append("OW/RT", "$host_pricing_data.oneway_return").append("footNote", "$host_pricing_data.footnote")
				.append("lastTicketed", "$host_pricing_data.last_ticketed_date")
				.append("currency", "$host_pricing_data.currency").append("currentBaseFare", null)
				.append("currentYQ", null).append("currentSurcharges", null).append("currentTax", null)
				.append("currentTotalFare", null).append("recoBaseFare", null).append("recoTax", null)
				.append("recoYQ", null).append("recoSurcharges", null).append("recoTotalFare", null)
				.append("lastFiledDate", null).append("hostBookings", "$bookings_data.bookings")
				.append("hostBookings_VLYR", "$bookings_data.vlyr").append("hostBookings_VTGT", "$bookings_data.vtgt")
				.append("sales_pax", "$pax_data.pax").append("sales_pax_VLYR", "$pax_data.vlyr")
				.append("sales_pax_VTGT", "$pax_data.vtgt").append("sales_revenue", "$revenue_data.revenue")
				.append("sales_revenue_VLYR", "$revenue_data.vlyr").append("sales_revenue_VTGT", "$revenue_data.vtgt")
				.append("yield", "$yield_date.yield").append("yield_VLYR", "$yield_data.vlyr")
				.append("yield_VTGT", "$yield_data.vtgt").append("reco_fare_yield", "$reco_yield")
				.append("avg_fare", "$avg_fare_data.avg_fare").append("avg_fare_vlyr", "$avg_fare.vlyr")
				.append("avg_fare_vtgt", "$avg_fare.vtgt").append("seat_factor_leg1", "$seat_factor.leg1")
				.append("seat_factor_leg2", "$seat_factor.leg2").append("fms", "$mrkt_data.host.fms")
				.append("marketShare_VTGT", "$mrkt_data.host.market_share_vtgt")
				.append("hostRating", "$mrkt_data.host.rating")
				.append("hostCapacityRating", "$mrkt_data.host.capacity_rating")
				.append("hostDistributorRating", "$mrkt_data.host.distributor_rating")
				.append("hostFareRating", "$mrkt_data.host.fare_rating")
				.append("hostMarketRating", "$mrkt_data.host.market_rating")
				.append("hostProductRating", "$mrkt_data.host.product_rating")
				.append("host_web_frequently_available_total_fare", "$mrkt_data.host.most_available_fare.total_fare")
				.append("host_web_frequently_available_base_fare", "$mrkt_data.host.most_available_fare.base_fare")
				.append("host_web_frequently_available_fare_tax", "$mrkt_data.host.most_available_fare.tax")
				.append("host_lowest_filed_fare_total_fare", "$mrkt_data.host.lowest_filed_fare.total_fare")
				.append("host_lowest_filed_fare_base_fare", "$mrkt_data.host.lowest_filed_fare.base_fare")
				.append("host_lowest_filed_fare_tax", "$mrkt_data.host.lowest_filed_fare.tax")
				.append("host_lowest_filed_fare_yq", "$mrkt_data.host.lowest_filed_fare.yq")
				.append("host_lowest_filed_fare_surcharges", "$mrkt_data.host.lowest_filed_fare.surcharge")
				.append("compCarrier", "$mrkt_data.comp.airline")
				.append("comp_marketShare", "$mrkt_data.comp.market_share")
				.append("comp_marketShare_VLYR", "$mrkt_data.comp.market_share_vlyr")
				.append("comp_fms", "$mrkt_data.comp.fms")
				.append("comp_marketShare_VTGT", "$mrkt_data.comp.market_share_vtgt")
				.append("competitorRating", "$mrkt_data.comp.rating")
				.append("compCapacityRating", "$mrkt_data.comp.capacity_rating")
				.append("compDistributorRating", "$mrkt_data.comp.distributor_rating")
				.append("compFareRating", "$mrkt_data.comp.fare_rating")
				.append("compMarketRating", "$mrkt_data.comp.market_rating")
				.append("compProductRating", "$mrkt_data.comp.product_rating")
				.append("comp_web_frequently_available_total_fare", "$mrkt_data.comp.most_available_fare.total_fare")
				.append("comp_web_frequently_available_base_fare", "$mrkt_data.comp.most_available_fare.base_fare")
				.append("comp_web_frequently_available_fare_frequency", "$mrkt_data.comp.most_available_fare.frequency")
				.append("comp_web_frequently_available_fare_tax", "$mrkt_data.comp.most_available_fare.tax")
				.append("comp_lowest_filed_fare_total_fare", "$mrkt_data.comp.lowest_filed_fare.total_fare")
				.append("comp_lowest_filed_fare_base_fare", "$mrkt_data.comp.lowest_filed_fare.base_fare")
				.append("comp_lowest_filed_fare_tax", "$mrkt_data.comp.lowest_filed_fare.tax")
				.append("comp_lowest_filed_fare_yq", "$mrkt_data.comp.lowest_filed_fare.yq")
				.append("comp_lowest_filed_fare_surcharges", "$mrkt_data.comp.lowest_filed_fare.surcharge")
				.append("flightNumberleg1_outbound", "$flight_data.leg1.outbound")
				.append("flightNumberleg2_outbound", "$flight_data.leg2.outbound")
				.append("flightNumberleg1_inbound", "$flight_data.leg1.inbound")
				.append("flightNumberleg2_inbound", "$flight_data.leg2.inbound"));
		List<DBObject> pipeline = Arrays.asList(match, project);

		AggregationOutput output = coll.aggregate(pipeline);

		for (DBObject result : output.results()) {
			System.out.println(result);
		}

		return lDataList;
	}

}
