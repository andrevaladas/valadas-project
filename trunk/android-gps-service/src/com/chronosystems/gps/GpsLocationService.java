package com.chronosystems.gps;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Andre Valadas
 */
public class GpsLocationService implements LocationListener {

	public Location checkin(final LocationManager locationManager) {
		Log.d("AsyncCheckinService", "doInBackground");
		if (locationManager == null) {
			return null;
		}

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

			// final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			final Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			criteria.setAltitudeRequired(false);
			criteria.setSpeedRequired(false);

			//start service
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			//get current location
			final Location currentLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
			//remove service
			locationManager.removeUpdates(this);

			return currentLocation;
		}
		return null;
	}

	@Override
	public void onLocationChanged(final Location location) {
	}

	@Override
	public void onProviderDisabled(final String provider) {
	}

	@Override
	public void onProviderEnabled(final String provider) {
	}

	@Override
	public void onStatusChanged(final String provider, final int status,
			final Bundle extras) {
		/* This is called when the GPS status alters */
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
			Log.v("GPS", "Status Changed: Out of Service");
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			Log.v("GPS", "Status Changed: Temporarily Unavailable");
			break;
		case LocationProvider.AVAILABLE:
			Log.v("GPS", "Status Changed: Available");
			break;
		}
	}
}
