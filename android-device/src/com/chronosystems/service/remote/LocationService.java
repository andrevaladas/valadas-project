package com.chronosystems.service.remote;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;

public class LocationService {

	private static String checkinURL = "/location/checkin";

	/**
	 * function checkin Location Request
	 * */
	public static Entity checkinLocation(final Device device){
		return WebServiceManager.executeRequest(checkinURL, device);
	}
}
