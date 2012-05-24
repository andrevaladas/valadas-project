package com.chronosystems.gps;

import java.util.concurrent.TimeUnit;
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

	private AtomicInteger verifyCount;
	private AtomicInteger locationCount;
	private Location currentLocation;
	private final LocationManager locationManager;
	private final LocationListenerGps locationListenerGps = new LocationListenerGps();
	private final LocationListenerNetwork locationListenerNetwork = new LocationListenerNetwork();

	public GpsLocationService(final LocationManager locationManager) {
		this.locationManager = locationManager;
		startService();
	}

	public void startService() {
		//start service
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
		}
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
		}
	}

	public void stopService() {
		//remove service
		locationManager.removeUpdates(locationListenerNetwork);
		locationManager.removeUpdates(locationListenerGps);
	}

	private Location getLastKnownLocation() {
		Location lastKnownLocation = null;
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		if (lastKnownLocation == null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		return lastKnownLocation;
	}

	/**
	 * Return the last known location
	 * @return
	 */
	public Location getCurrentLocation() {
		verifyCount = new AtomicInteger();
		locationCount = new AtomicInteger();
		currentLocation = getLastKnownLocation();

		Log.v("GPS_PROVIDER", "START");
		//provider enabled
		if (currentLocation != null) {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (final Exception e) {}

				final Location verifyLocation = getLastKnownLocation();
				Log.v("GPS_PROVIDER", verifyCount.get()+"#"+locationCount.get()+": "+TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - verifyLocation.getTime())+"s | "+verifyLocation.getLatitude()+" | "+verifyLocation.getLongitude()+" | "+verifyLocation.getAccuracy());
				if (currentLocation.getTime() != verifyLocation.getTime() && locationCount.getAndIncrement() >= 3) {
					if (verifyLocation.getAccuracy() < 20f) {
						Log.v("GPS_PROVIDER", "FOUND");
						return verifyLocation;
					}
				}
				currentLocation = verifyLocation;
				if (verifyCount.getAndIncrement() >= 30) {
					break;
				}
			}
		}
		Log.v("GPS_PROVIDER", "DEFAULT");
		return currentLocation;
	}

	class LocationListenerNetwork implements LocationListener {

		@Override
		public void onLocationChanged(final Location location) {
			System.out.println("LocationListenerNetwork Accuracy: "+location.getAccuracy());
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
