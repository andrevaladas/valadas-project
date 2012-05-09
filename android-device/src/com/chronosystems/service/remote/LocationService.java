package com.chronosystems.service.remote;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;

public class LocationService {

	private static String checkinURL = "/location/checkin";

	/**
	 * function checkin Location Request
	 * */
	public static Entity checkinLocation(final Device device, final android.location.Location location){
		//create a location checkin
		final Location checkin = new Location(
				location.getLatitude(),
				location.getLongitude());
		device.addLocation(checkin);
		return WebServiceManager.executeRequest(checkinURL, device);
	}
}
