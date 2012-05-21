package com.chronosystems.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.gps.GpsLocationService;
import com.chronosystems.service.local.UserFunctions;
import com.chronosystems.service.remote.LocationService;

/**
 * @author andrevaladas
 */
public class MyLocationService extends Service {

	// constants
	public static final String ServletUri = "http://localhost:8080/Ping";

	// static data/shared references, etc.
	public static ServiceUpdateListener UPDATE_LISTENER;
	private static Activity MAIN_ACTIVITY;

	// data
	private GpsLocationService locationService;
	private Location lastLocation;
	private final Timer timer = new Timer();
	private static final long UPDATE_INTERVAL = 5 * 1000;
	private static final float UPDATE_DISTANCE = 100f; //the approximate distance in meters

	// hooks into other activities
	public static void setMainActivity(final Activity activity) {
		MAIN_ACTIVITY = activity;
	}

	public static void setUpdateListener(final ServiceUpdateListener listener) {
		UPDATE_LISTENER = listener;
	}

	@Override
	public IBinder onBind(final Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// init the service here
		_startService();
		if (MAIN_ACTIVITY != null) {
			Toast.makeText(MAIN_ACTIVITY, "MyLocationService started", Toast.LENGTH_SHORT);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		_shutdownService();
		if (MAIN_ACTIVITY != null) {
			Toast.makeText(MAIN_ACTIVITY, "MyLocationService stopped", Toast.LENGTH_SHORT);
		}
	}

	// service business logic
	private void _startService() {
		locationService = new GpsLocationService((LocationManager) MAIN_ACTIVITY.getSystemService(Context.LOCATION_SERVICE));
		timer.scheduleAtFixedRate(
				new TimerTask() {
					@Override
					public void run() {
						_runUpdateLocation();
					}
				},
				0,
				UPDATE_INTERVAL);
		Log.i(getClass().getSimpleName(), "Timer started!!!");
	}

	/** dont forget to fire update to the ui listener */
	private void _runUpdateLocation() {
		// http post to the service
		Log.i(getClass().getSimpleName(), "background task - start");
		String serviceMessage = "MyLocationService error";

		try {
			final Location currentLocation = locationService.getCurrentLocation();
			if (currentLocation != null) {

				// validate the minimum distance in meters
				if (checkAccuracyUpdate(currentLocation)) {
					// get current user and set current location
					final Device currentUser = UserFunctions.getCurrentUser(MAIN_ACTIVITY.getApplicationContext());
					// remote location
					final com.chronosystems.entity.Location location = new com.chronosystems.entity.Location(
							currentLocation.getLatitude(),
							currentLocation.getLongitude());
					currentUser.addLocation(location);

					// send data to server
					final Entity checkinEntity = LocationService.checkinLocation(currentUser);
					if (checkinEntity != null && !checkinEntity.hasErrors()) {
						serviceMessage = "MyLocationService updated";
					}
				} else {
					serviceMessage = "MyLocationService minimum distance is not enough";
				}
			}
		} catch (final Exception e) {
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			Log.e(getClass().getSimpleName(), sw.getBuffer().toString(), e);
		}

		Log.i(getClass().getSimpleName(), "background task - end");

		if (UPDATE_LISTENER != null) {
			UPDATE_LISTENER.update(serviceMessage);
		}
	}

	private void _shutdownService() {
		if (timer != null) {
			timer.cancel();
		}
		if (locationService != null) {
			locationService.stopService();
		}
		Log.i(getClass().getSimpleName(), "Timer stopped!!!");
	}

	private boolean checkAccuracyUpdate(final Location currentLocation) {
		if (lastLocation == null) {
			lastLocation = currentLocation; //update location
			return true;
		}
		// validate the approximate distance in meters
		final boolean update = lastLocation.distanceTo(currentLocation) >= UPDATE_DISTANCE;
		lastLocation = currentLocation; //update location
		return update;
	}
}
