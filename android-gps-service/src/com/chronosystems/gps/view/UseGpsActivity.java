package com.chronosystems.gps.view;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class UseGpsActivity extends Activity implements LocationListener {
	private static final int GPS_FREQUENCY_MILLIS = 10 * 1000;
	private static final int GPS_DISTANCE_METERS = 10;

	TextView appText;
	Location lastLocation;
	LocationManager locationManager;

	StringBuilder sb;
	int noOfFixes = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		appText = (TextView) findViewById(R.id.appText);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// test checkin location
		//		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
		//			final GpsLocationService locationService = new GpsLocationService();
		//			final Location checkin = locationService.checkin(locationManager);
		//			if (checkin != null) {
		//				System.out.println(checkin.getLatitude());
		//				showNewLocation(checkin);
		//			}
		//		} else {
		//			// I open here the preferences to force the user start the GPS
		//			showAlertMessageNoGps();
		//		}
		startMonitoring();
	}

	private void startMonitoring() {
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			registerForUpdates();
		} else {
			// I open here the preferences to force the user start the GPS
			showAlertMessageNoGps();
		}
	}

	@Override
	protected void onResume() {
		/*
		 * onResume is is always called after onStart, even if the app hasn't been
		 * paused
		 */
		registerForUpdates();
		super.onResume();
	}

	@Override
	protected void onPause() {
		/* GPS, as it turns out, consumes battery like crazy */
		deregisterForUpdates();
		super.onPause();
	}

	@Override
	protected void onStop() {
		/* may as well just finish since saving the state is not important for this toy app */
		finish();
		super.onStop();
	}

	/** Launches the SecuritySettings activity */
	private void launchGPSOptions() {
		/* bring up the GPS settings */
		startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
	}

	@Override
	public void onLocationChanged(final Location location) {
		showNewLocation(location);
	}

	@Override
	public void onProviderDisabled(final String provider) {
		deregisterForUpdates();
	}

	@Override
	public void onProviderEnabled(final String provider) {
		registerForUpdates();
	}

	@Override
	public void onStatusChanged(final String provider, final int status, final Bundle extras) {
		/* This is called when the GPS status alters */
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
			Log.v("GPS", "Status Changed: Out of Service");
			Toast.makeText(this, "Status Changed: Out of Service",Toast.LENGTH_SHORT).show();
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			Log.v("GPS", "Status Changed: Temporarily Unavailable");
			Toast.makeText(this, "Status Changed: Temporarily Unavailable",Toast.LENGTH_SHORT).show();
			break;
		case LocationProvider.AVAILABLE:
			Log.v("GPS", "Status Changed: Available");
			Toast.makeText(this, "Status Changed: Available",Toast.LENGTH_SHORT).show();
			break;
		}
	}

	/** Register the listener with the Location Manager to receive location updates */
	private void registerForUpdates() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_FREQUENCY_MILLIS, GPS_DISTANCE_METERS, this);
	}

	/** Register the listener with the Location Manager to receive location updates */
	private void deregisterForUpdates() {
		locationManager.removeUpdates(this);
	}

	/** Builds an alert message to allow the user the option of enabling GPS */
	private void showAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder
		.setMessage("Your GPS seems to be disabled, do you want to enable it?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int id) {
				launchGPSOptions();
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int id) {
				dialog.cancel();
				finish();
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	/** Updates the location field with coordinates */
	private void showNewLocation(final Location location) {
		if(lastLocation == null) {
			lastLocation = location;
		}

		sb = new StringBuilder(512);
		noOfFixes++;

		/* display some of the data in the TextView */

		sb.append("No. of Fixes: ");
		sb.append(noOfFixes);
		sb.append('\n');
		sb.append('\n');

		sb.append("Londitude: ");
		sb.append(location.getLongitude());
		sb.append('\n');

		sb.append("Latitude: ");
		sb.append(location.getLatitude());
		sb.append('\n');

		sb.append("Altitiude: ");
		sb.append(location.getAltitude());
		sb.append('\n');

		sb.append("Accuracy: ");
		sb.append(location.getAccuracy());
		sb.append('\n');

		sb.append("Time: ");
		sb.append(TimeUnit.MILLISECONDS.toSeconds(location.getTime()-lastLocation.getTime()));
		sb.append("seconds \n");

		sb.append("Speed: ");
		sb.append(location.getSpeed()*3.6);
		sb.append('\n');

		sb.append("Distance: ");
		sb.append(lastLocation.distanceTo(location));
		sb.append('\n');

		// display the update text
		appText.setText(sb.toString());
		lastLocation = location;
	}
}