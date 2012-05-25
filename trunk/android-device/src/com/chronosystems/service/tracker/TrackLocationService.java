
package com.chronosystems.service.tracker;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.util.Log;

import com.chronosystems.entity.Device;
import com.chronosystems.gps.GpsLocationService;
import com.chronosystems.service.local.LocationFunctions;
import com.chronosystems.service.local.UserFunctions;
import com.chronosystems.service.remote.LocationService;
import com.chronosystems.wakeful.WakefulIntentService;

public class TrackLocationService extends WakefulIntentService {
	private float distance = -1f;
	private Location lastLocation;
	private boolean updateLocation = false;
	private static GpsLocationService locationService;

	public TrackLocationService() {
		super(TrackLocationService.class.getName());
	}

	@Override
	protected void doWakefulWork(final Intent intent) {
		if (TrackListener.mainActivity == null) {
			return;
		}

		Log.i(getClass().getSimpleName(), "background task - start");

		Location currentLocation = null;
		if (locationService == null) {
			locationService = new GpsLocationService((LocationManager) TrackListener.mainActivity.getSystemService(Context.LOCATION_SERVICE));
		}

		try {
			try {
				Looper.prepare();
			} catch (final Exception e) { }

			try {
				locationService.startService();
				currentLocation = locationService.getCurrentLocation();
			} finally {
				if (locationService != null) {
					locationService.stopService();
				}
			}
			if (currentLocation != null) {
				// validate the minimum distance in meters
				if (verifyMinimumDistance(currentLocation)) {
					// get current user and set current location
					final Device currentUser = UserFunctions.getCurrentUser(TrackListener.mainActivity);
					// remote location
					final com.chronosystems.entity.Location location = new com.chronosystems.entity.Location(
							currentLocation.getLatitude(),
							currentLocation.getLongitude());

					// send data to server
					final Device checkin = new Device();
					checkin.setId(currentUser.getId());
					checkin.addLocation(location);
					LocationService.checkinLocation(checkin);
				}
			}
		} catch (final Exception e) {
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			Log.e(getClass().getSimpleName(), sw.getBuffer().toString(), e);
		}

		Log.i(getClass().getSimpleName(), "background task - end");
		if (TrackListener.updateListener != null) {
			TrackListener.updateListener.update(currentLocation, distance, updateLocation);
		}
	}

	private boolean verifyMinimumDistance(final Location currentLocation) {
		updateLocation = false;
		if (lastLocation == null) {
			lastLocation = LocationFunctions.getLastLocation(TrackListener.mainActivity);//from local DB
		}

		// validate the approximate distance in meters
		if (lastLocation != null) {
			distance = lastLocation.distanceTo(currentLocation);
			updateLocation = distance >= TrackListener.minimumDistance;
		} else {
			// is first location
			updateLocation = true;
		}

		//store in local db
		if (updateLocation) {
			LocationFunctions.addLastLocation(currentLocation, TrackListener.mainActivity);
			lastLocation = currentLocation; //update location
		}
		return updateLocation;
	}
}
