package com.chronosystems.service.local;

import android.content.Context;
import android.location.Location;

import com.chronosystems.entity.Device;

public class LocationFunctions {

	/**
	 * Getting lastLocation data from database
	 * */
	public static Location getLastLocation(final Context context){
		return new DatabaseHandler(context).getLastLocation();
	}

	/**
	 * Add last location
	 * */
	public static void addLastLocation(final Location location, final Context context){
		final DatabaseHandler db = new DatabaseHandler(context);
		final Device currentUser = db.getCurrentUser();
		db.addLastLocation(currentUser.getId(), location.getLatitude(), location.getLongitude());
	}

}
