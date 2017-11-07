package com.flynava.jupiter.util;

import com.flynava.jupiter.model.FilterModel;
import com.flynava.jupiter.model.RequestModel;

public class FilterUtil {

	public static String getFilter(RequestModel requestModel) {

		String filter = null;

		if (requestModel.getRegionArray() == null && requestModel.getCountryArray() == null
				&& requestModel.getPosArray() == null && requestModel.getOdArray() == null
				&& requestModel.getCompartmentArray() == null
				|| requestModel.getRegionArray() == null && requestModel.getCountryArray() == null
						&& requestModel.getPosArray() == null && requestModel.getOdArray() == null
						&& requestModel.getCompartmentArray() != null)
			filter = "region"; // key=region+compartment

		else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() == null
				&& requestModel.getPosArray() == null && requestModel.getOdArray() == null
				&& requestModel.getCompartmentArray() == null
				|| requestModel.getRegionArray() != null && requestModel.getCountryArray() == null
						&& requestModel.getPosArray() == null && requestModel.getOdArray() == null
						&& requestModel.getCompartmentArray() != null)
			filter = "regionFilter"; // key=region+country+compartment
		else if (requestModel.getRegionArray() == null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() == null && requestModel.getOdArray() == null
				&& requestModel.getCompartmentArray() == null
				|| requestModel.getRegionArray() == null && requestModel.getCountryArray() != null
						&& requestModel.getPosArray() == null && requestModel.getOdArray() == null
						&& requestModel.getCompartmentArray() != null)
			filter = "country";// key=country+pos+compartment

		else if (requestModel.getRegionArray() == null && requestModel.getCountryArray() == null
				&& requestModel.getPosArray() != null && requestModel.getOdArray() == null
				&& requestModel.getCompartmentArray() == null
				|| requestModel.getRegionArray() == null && requestModel.getCountryArray() == null
						&& requestModel.getPosArray() != null && requestModel.getOdArray() == null
						&& requestModel.getCompartmentArray() != null)
			filter = "pos"; // pos+compartment

		else if (requestModel.getRegionArray() == null && requestModel.getCountryArray() == null
				&& requestModel.getPosArray() == null && requestModel.getOdArray() != null
				&& requestModel.getCompartmentArray() == null
				|| requestModel.getRegionArray() == null && requestModel.getCountryArray() == null
						&& requestModel.getPosArray() == null && requestModel.getOdArray() != null
						&& requestModel.getCompartmentArray() != null)
			filter = "od";// key=origin+destination+compartment

		else if (requestModel.getRegionArray() == null && requestModel.getCountryArray() == null
				&& requestModel.getPosArray() == null && requestModel.getOdArray() != null
				&& requestModel.getCompartmentArray() != null)
			filter = "od+compartment";// key=origin+destination+compartment

		else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() == null && requestModel.getOdArray() == null
				&& requestModel.getCompartmentArray() == null
				|| requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
						&& requestModel.getPosArray() == null && requestModel.getOdArray() == null
						&& requestModel.getCompartmentArray() != null)
			filter = "region+country";// key=region+country+pos+compartment

		else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() != null && requestModel.getOdArray() == null
				&& requestModel.getCompartmentArray() == null
				|| requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
						&& requestModel.getPosArray() != null && requestModel.getOdArray() == null
						&& requestModel.getCompartmentArray() != null)
			filter = "region+country+POS"; // key=region+country+pos+compartment

		else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() != null && requestModel.getOdArray() != null
				&& requestModel.getCompartmentArray() == null
				|| requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
						&& requestModel.getPosArray() != null && requestModel.getOdArray() != null
						&& requestModel.getCompartmentArray() != null)
			filter = "region+country+POS+OD";// key=region+country+pos+origin+destination+compartment

		else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() == null
				&& requestModel.getPosArray() == null && requestModel.getOdArray() != null)
			filter = "region+OD";// key=region+origin+desination+compartment

		else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() == null && requestModel.getOdArray() != null)
			filter = "region+country+OD";// key=region+country+origin+destination+compartment

		else if (requestModel.getRegionArray() == null && requestModel.getCountryArray() == null
				&& requestModel.getPosArray() != null && requestModel.getOdArray() != null
				&& requestModel.getCompartmentArray() != null)
			filter = "POS+OD+compartment";// key=pos+origin+destination+compartment

		else if (requestModel.getRegionArray() == null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() != null && requestModel.getOdArray() != null
				&& requestModel.getCompartmentArray() != null)
			filter = "country+POS+OD+compartment";// key=country+pos+origin+destination+compartment

		else if (requestModel.getRegionArray() != null && requestModel.getCountryArray() != null
				&& requestModel.getPosArray() != null && requestModel.getOdArray() != null
				&& requestModel.getCompartmentArray() != null)
			filter = "region+country+POS+OD+compartment";// key=region+country+pos+origin+destination+compartment
		else
			filter = "country+POS+OD+compartment";

		return filter;

	}

	public static String getAggregationKey(FilterModel mFilterModel, String mKey) {
		if (mKey.equalsIgnoreCase("region")) {
			mKey = mFilterModel.getRegion() + mFilterModel.getProduct() + mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("regionFilter")) {
			mKey = mFilterModel.getRegion() + mFilterModel.getCountry() + mFilterModel.getProduct()
					+ mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("country")) {
			mKey = mFilterModel.getCountry() + mFilterModel.getPos() + mFilterModel.getProduct()
					+ mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("pos")) {
			mKey = mFilterModel.getPos() + mFilterModel.getRbd() + mFilterModel.getProduct()
					+ mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("od")) {
			mKey = mFilterModel.getOrigin() + mFilterModel.getDestination() + mFilterModel.getProduct()
					+ mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("compartment")) {
			mKey = mFilterModel.getRbd() + mFilterModel.getProduct() + mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("od+compartment")) {
			mKey = mFilterModel.getOrigin() + mFilterModel.getDestination() + mFilterModel.getProduct()
					+ mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("region+country")) {
			mKey = mFilterModel.getRegion() + mFilterModel.getCountry() + mFilterModel.getPos()
					+ mFilterModel.getProduct() + mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("region+country+pos")) {
			mKey = mFilterModel.getRegion() + mFilterModel.getCountry() + mFilterModel.getPos() + mFilterModel.getRbd()
					+ mFilterModel.getProduct() + mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("region+country+pos+od")) {
			mKey = mFilterModel.getRegion() + mFilterModel.getCountry() + mFilterModel.getOrigin()
					+ mFilterModel.getDestination() + mFilterModel.getPos() + mFilterModel.getRbd()
					+ mFilterModel.getProduct() + mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("region+od")) {
			mKey = mFilterModel.getRegion() + mFilterModel.getOrigin() + mFilterModel.getDestination()
					+ mFilterModel.getProduct() + mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("region+country+od")) {
			mKey = mFilterModel.getRegion() + mFilterModel.getCountry() + mFilterModel.getOrigin()
					+ mFilterModel.getDestination() + mFilterModel.getProduct() + mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("pos+od+compartment")) {
			mKey = mFilterModel.getPos() + mFilterModel.getOrigin() + mFilterModel.getDestination()
					+ mFilterModel.getRbd() + mFilterModel.getProduct() + mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("country+pos+od+compartment")) {
			mKey = mFilterModel.getCountry() + mFilterModel.getPos() + mFilterModel.getOrigin()
					+ mFilterModel.getDestination() + mFilterModel.getRbd() + mFilterModel.getProduct()
					+ mFilterModel.getCompartment();
		} else if (mKey.equalsIgnoreCase("region+country+pos+od+compartment")) {
			mKey = mFilterModel.getRegion() + mFilterModel.getCountry() + mFilterModel.getPos()
					+ mFilterModel.getOrigin() + mFilterModel.getDestination() + mFilterModel.getRbd()
					+ mFilterModel.getProduct() + mFilterModel.getCompartment();
		} else {
			mKey = mFilterModel.getCountry() + mFilterModel.getPos() + mFilterModel.getOrigin()
					+ mFilterModel.getDestination() + mFilterModel.getRbd() + mFilterModel.getProduct()
					+ mFilterModel.getCompartment();
		}
		return mKey;
	}

}
