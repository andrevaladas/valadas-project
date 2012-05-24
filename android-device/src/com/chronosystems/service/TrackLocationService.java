package com.chronosystems.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.gps.GpsLocationService;
import com.chronosystems.service.local.LocationFunctions;
import com.chronosystems.service.local.UserFunctions;
import com.chronosystems.service.remote.LocationService;

/**
 * @author andrevaladas
 */
public class TrackLocationService extends Service {

	// static data/shared references, etc.
	public static ServiceUpdateListener UPDATE_LISTENER;
	private static Activity MAIN_ACTIVITY;

	// data
	private float distance = -1f;
	private Location lastLocation;
	private static GpsLocationService locationService;
	private static final Timer timer = new Timer();
	private static final long UPDATE_INTERVAL = 3 * 60 * 1000;
	private static final float UPDATE_DISTANCE = 200f; //the approximate distance in meters

	@Override
	public void onStart(final Intent intent, final int startId) {
		super.onStart(intent, startId);
		final Notification notification = new Notification();
		startForeground(1, notification);
	}

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
			Toast.makeText(MAIN_ACTIVITY, "TrackLocationService started", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		//_shutdownService();
		//if (MAIN_ACTIVITY != null) {
		//	Toast.makeText(MAIN_ACTIVITY, "TrackLocationService stopped", Toast.LENGTH_SHORT).show();
		//}
	}

	// service business logic
	private void _startService() {
		if (locationService == null) {
			locationService = new GpsLocationService((LocationManager) MAIN_ACTIVITY.getSystemService(Context.LOCATION_SERVICE));
			timer.scheduleAtFixedRate(
					new TimerTask() {
						@Override
						public void run() {
							_runUpdateLocation();
						}
					},
					3000,
					UPDATE_INTERVAL);
			Log.i(getClass().getSimpleName(), "Timer started!!!");
		}
	}

	/** dont forget to fire update to the ui listener */
	private void _runUpdateLocation() {
		// http post to the service
		Log.i(getClass().getSimpleName(), "background task - start");
		String serviceMessage = "#LocationService can't find location";

		try {
			try {
				Looper.prepare();
			} catch (final Exception e) { }

			locationService.startService();
			final Location currentLocation = locationService.getCurrentLocation();
			if (currentLocation != null) {
				// validate the minimum distance in meters
				if (verifyAccuracyDistance(currentLocation)) {
					// get current user and set current location
					final Device currentUser = UserFunctions.getCurrentUser(MAIN_ACTIVITY.getApplicationContext());
					// remote location
					final com.chronosystems.entity.Location location = new com.chronosystems.entity.Location(
							currentLocation.getLatitude(),
							currentLocation.getLongitude());

					// send data to server
					final Device checkin = new Device();
					checkin.setId(currentUser.getId());
					checkin.addLocation(location);
					final Entity checkinEntity = LocationService.checkinLocation(checkin);
					if (checkinEntity != null && !checkinEntity.hasErrors()) {
						serviceMessage = "#LocationService updated: "+distance +" speed: "+(int)(currentLocation.getSpeed()*3.6);
					}
				} else {
					serviceMessage = "#LocationService distance is not enough: "+distance +" speed: "+(int)(currentLocation.getSpeed()*3.6);
				}
			}
		} catch (final Exception e) {
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			Log.e(getClass().getSimpleName(), sw.getBuffer().toString(), e);
		} finally {
			if (locationService != null) {
				locationService.stopService();
			}
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

	private boolean verifyAccuracyDistance(final Location currentLocation) {
		boolean update = false;
		if (lastLocation == null) {
			lastLocation = LocationFunctions.getLastLocation(MAIN_ACTIVITY);//from local DB
			update = (lastLocation == null); //update my first location
		}
		// validate the approximate distance in meters
		if (!update) {
			distance = lastLocation.distanceTo(currentLocation);
			update = distance >= UPDATE_DISTANCE;
		}
		lastLocation = currentLocation; //update location
		LocationFunctions.addLastLocation(lastLocation, MAIN_ACTIVITY);
		return update;
	}
}