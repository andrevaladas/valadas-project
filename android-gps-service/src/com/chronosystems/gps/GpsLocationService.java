package com.chronosystems.gps;

import java.util.concurrent.atomic.AtomicInteger;

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

	AtomicInteger count;
	Location currentLocation;
	Location networkLocation;
	LocationManager locationManager;
	final LocationListenerGps locationListenerGps = new LocationListenerGps();
	final LocationListenerNetwork locationListenerNetwork = new LocationListenerNetwork();

	public GpsLocationService(final LocationManager locationManager) {
		this.locationManager = locationManager;
		startService();
	}

	public void startService() {
		//start service
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
	}

	public void stopService() {
		//remove service
		locationManager.removeUpdates(locationListenerNetwork);
		locationManager.removeUpdates(locationListenerGps);
	}

	public Location getCurrentLocation() {
		count = new AtomicInteger();
		while (currentLocation == null) {
			try {
				Thread.sleep(1000);
			} catch (final Exception e) {}

			//set networkLocation after x attempts
			if(count.getAndIncrement() > 10 && currentLocation == null && networkLocation != null) {
				currentLocation = networkLocation;
			}
		}
		return currentLocation;
	}

	class LocationListenerNetwork implements LocationListener {

		@Override
		public void onLocationChanged(final Location location) {
			networkLocation = location;
		}

		@Override
		public void onProviderDisabled(final String provider) {}
		@Override
		public void onProviderEnabled(final String provider) {}

		@Override
		public void onStatusChanged(final String provider, final int status, final Bundle extras) {
			switch (status) {
			case LocationProvider.OUT_OF_SERVICE:
				Log.v("Network", "Status Changed: Out of Service");
				stopService();
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Log.v("Network", "Status Changed: Temporarily Unavailable");
				stopService();
				break;
			case LocationProvider.AVAILABLE:
				Log.v("Network", "Status Changed: Available");
				startService();
				break;
			}
		}
	}

	class LocationListenerGps implements LocationListener {

		@Override
		public void onLocationChanged(final Location location) {
			System.out.println("LocationListenerGps Accuracy: "+location.getAccuracy());
			if (location.getAccuracy() < 50f) {
				currentLocation = location;
			}
		}

		@Override
		public void onProviderDisabled(final String provider) {
		}

		@Override
		public void onProviderEnabled(final String provider) {
		}

		@Override
		public void onStatusChanged(final String provider, final int status, final Bundle extras) {
			switch (status) {
			case LocationProvider.OUT_OF_SERVICE:
				Log.v("GPS", "Status Changed: Out of Service");
				stopService();
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Log.v("GPS", "Status Changed: Temporarily Unavailable");
				stopService();
				break;
			case LocationProvider.AVAILABLE:
				Log.v("GPS", "Status Changed: Available");
				startService();
				break;
			}
		}
	}
}
