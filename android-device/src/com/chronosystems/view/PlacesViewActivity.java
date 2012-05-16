package com.chronosystems.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.chronosystems.crop.image.ImageHelper;
import com.chronosystems.library.maps.CustomItemizedOverlay;
import com.chronosystems.library.maps.CustomOverlayItem;
import com.chronosystems.library.utils.GpsUtils;
import com.chronosystems.maps.core.OnSingleTapListener;
import com.chronosystems.maps.core.TapControlledMapView;
import com.chronosystems.places.AsyncGooglePlacesService;
import com.chronosystems.places.data.Location;
import com.chronosystems.places.data.Place;
import com.chronosystems.places.data.PlaceFilter;
import com.chronosystems.places.data.PlaceSearch;
import com.chronosystems.places.view.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class PlacesViewActivity extends MapActivity implements OnSingleTapListener, LocationListener {

	private TapControlledMapView placesView;
	private MyLocationOverlay myLocationOverlay;
	private LocationManager locationManager;
	private CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	private GeoPoint currentPoint;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_view);
		placesView = (TapControlledMapView) findViewById(R.id.places_view);
		placesView.setBuiltInZoomControls(true);
		placesView.getController().setZoom(15);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);

		myLocationOverlay = new MyLocationOverlay(this, placesView);
		placesView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				placesView.getController().animateTo(myLocationOverlay.getMyLocation());
			}
		});

		final Drawable drawable = getResources().getDrawable(R.drawable.places_pin);
		itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, placesView);
		itemizedOverlay.setShowClose(false);
		itemizedOverlay.setShowDisclosure(true);
		itemizedOverlay.setSnapToCenter(true);

		// dismiss balloon upon single tap of MapView (iOS behavior)
		placesView.setOnSingleTapListener(this);
	}

	private void executePlaceSearch(final PlaceFilter placeFilter) {
		new AsyncGooglePlacesService(this) {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				removeOverlays();
			};

			@Override
			protected void onPostExecute(final PlaceSearch result) {
				super.onPostExecute(result);
				if (result == null) {
					return;
				}
				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						final List<Place> placeResult = result.getResult();
						Log.d("PlacesViewActivity", "placesService.onPostExecute: " + placeResult.size());
						for (final Place place : placeResult) {

							final Location location = place.getGeometry().getLocation();
							final GeoPoint placePoint = new GeoPoint((int)(Double.valueOf(location.getLat())*1E6),(int)(Double.valueOf(location.getLng())*1E6));

							// define data point
							final CustomOverlayItem overlayItem = new CustomOverlayItem(
									placePoint,
									place.getName(),
									place.getVicinity(),
									ImageHelper.getBitmapFromUrl(place.getIcon()));

							//add item on overlay
							itemizedOverlay.addOverlay(overlayItem);
							Log.d("YourApp", "onPostExecute : result = " + place.getName());
						}
						placesView.getOverlays().add(itemizedOverlay);
						Log.d("PlacesViewActivity", "placesService.onPostExecute");
					}
				});
			}
		}.execute(placeFilter);
	}

	private void removeOverlays() {
		if (placesView.getOverlays().size() > 0) {
			final List<Overlay> overlaysToRemove = new ArrayList<Overlay>();
			for (final Overlay overlay : placesView.getOverlays()) {
				if (overlay instanceof CustomItemizedOverlay<?>) {
					overlaysToRemove.add(overlay);
				}
			}
			placesView.getOverlays().removeAll(overlaysToRemove);
		}
	}

	public void onLocationChanged(final android.location.Location location) {
		final int lat = (int) (location.getLatitude() * 1E6);
		final int lng = (int) (location.getLongitude() * 1E6);
		currentPoint = new GeoPoint(lat, lng);
	}

	public void onProviderDisabled(final String provider) {}
	public void onProviderEnabled(final String provider) {}
	public void onStatusChanged(final String provider, final int status, final Bundle extras) {}

	@Override
	protected void onResume() {
		super.onResume();
		if (myLocationOverlay != null) {
			if (GpsUtils.isEnabled(this)) {
				myLocationOverlay.enableMyLocation();
				myLocationOverlay.enableCompass();
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (myLocationOverlay != null) {
			myLocationOverlay.disableMyLocation();
			myLocationOverlay.disableCompass();
			locationManager.removeUpdates(this);
		}
	}

	@Override
	protected void onStop() {
		super.onPause();
		if (myLocationOverlay != null) {
			myLocationOverlay.disableMyLocation();
			myLocationOverlay.disableCompass();
			locationManager.removeUpdates(this);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public boolean onSingleTap(final MotionEvent e) {
		if (placesView.getOverlays().size() > 0) {
			for (final Overlay overlay : placesView.getOverlays()) {
				if (overlay instanceof CustomItemizedOverlay<?>) {
					@SuppressWarnings("unchecked")
					final CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = (CustomItemizedOverlay<CustomOverlayItem>) overlay;
					itemizedOverlay.hideAllBalloons();
					break;
				}
			}
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu_places, menu);
		if (placesView.isSatellite()) {
			final MenuItem item = menu.findItem(R.id.map);
			item.setTitle(getString(R.string.road));
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_home:
			startActivity(new Intent(getApplicationContext(), TabDashboardActivity.class));
			return true;
		case R.id.search_places:
			if (GpsUtils.isEnabled(this)) {
				if (currentPoint == null) {
					android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					if (lastKnownLocation == null) {
						lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					}
					currentPoint = new GeoPoint((int)(lastKnownLocation.getLatitude()*1E6),(int)(lastKnownLocation.getLongitude()*1E6));;
				}

				final PlaceFilter placeFilter = new PlaceFilter();
				placeFilter.setLocation(currentPoint);
				placeFilter.setLanguage("pt-BR");
				placeFilter.setRadius(500);
				placeFilter.setSensor(true);
				executePlaceSearch(placeFilter);
			}
			return true;
		case R.id.map:
			final String satellite = getString(R.string.satellite);
			if (item.getTitle().equals(satellite)) {
				placesView.setSatellite(true);
				item.setTitle(getString(R.string.road));
				item.setChecked(true);
			} else {
				placesView.setSatellite(false);
				item.setTitle(satellite);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}