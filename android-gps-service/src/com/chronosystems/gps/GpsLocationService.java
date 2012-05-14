package com.chronosystems.gps;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Andre Valadas
 */
public class GpsLocationService {

	Location currentLocation = null;
	LocationManager locationManager;
	final LocationListenerGps locationListenerGps = new LocationListenerGps();

	public GpsLocationService(final LocationManager locationManager) {
		this.locationManager = locationManager;
	}

	class LocationListenerGps implements LocationListener {

		@Override
		public void onLocationChanged(final Location location) {
			System.out.println("LocationListenerGps");
			currentLocation = location;
		}

		@Override
		public void onProviderDisabled(final String provider) {
		}

		@Override
		public void onProviderEnabled(final String provider) {
		}

		@Override
		public void onStatusChanged(final String provider, final int status, final Bundle extras) {
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

	public void startService() {
		//start service
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
	}

	public void stopService() {
		//remove service
		locationManager.removeUpdates(locationListenerGps);
	}

	public Location getCurrentLocation() {
		while (currentLocation == null) {
			try {
				Thread.sleep(1000);
			} catch (final Exception e) {}
		}
		return currentLocation;
	}
}
