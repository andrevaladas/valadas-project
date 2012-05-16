package com.chronosystems.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;
import com.chronosystems.library.list.ImageLoader;
import com.chronosystems.library.maps.CustomItemizedOverlay;
import com.chronosystems.library.maps.CustomOverlayItem;
import com.chronosystems.library.utils.GpsUtils;
import com.chronosystems.library.utils.LocationUtils;
import com.chronosystems.maps.core.OnSingleTapListener;
import com.chronosystems.maps.core.TapControlledMapView;
import com.chronosystems.service.local.AsyncService;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class MapViewActivity extends MapActivity implements OnSingleTapListener {

	List<CustomItemizedOverlay<CustomOverlayItem>> itemizedOverlayList;
	TapControlledMapView mapView;
	List<Overlay> mapOverlays;
	MyLocationOverlay myLocationOverlay;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** all point locations to map */
		final List<Location> locations = new ArrayList<Location>();

		final Entity allFriends = (Entity) getIntent().getSerializableExtra("allFriends");
		if (allFriends != null) {
			/** Put only last location from all devices */
			final List<Device> devices = allFriends.getDevices();
			for (final Device device : devices) {
				final List<Location> allLocations = device.getLocations();
				for (final Location location : allLocations) {
					location.setDevice(device);
					locations.add(location);
					break;
				}
			}
		} else {
			/** Put all location from one device */
			final Device device = (Device) getIntent().getSerializableExtra("device");
			/** all point locations to map */
			locations.addAll(device.getLocations());
			for (final Location location : locations) {
				location.setDevice(device);
			}
		}

		/** Execute map layers generation */
		new AsyncService(this) {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				setContentView(R.layout.map_view);
			}

			@Override
			protected Entity doInBackground(final String... args) {
				mapView = (TapControlledMapView) findViewById(R.id.mapview);
				itemizedOverlayList = new ArrayList<CustomItemizedOverlay<CustomOverlayItem>>();

				for (final Location location : locations) {
					// create a point
					final GeoPoint point = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));

					// define data point
					final CustomOverlayItem overlayItem = new CustomOverlayItem(
							point,
							location.getDevice().getName(),
							LocationUtils.getDateTimeDescrition(location).concat("\n").concat(LocationUtils.getGeocoderAddress(location, getBaseContext())),
							ImageLoader.getBitmapCached(location.getDevice()));

					// define overlay/marker by location timeline
					final CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = LocationUtils.getItemizedOverlay(location, itemizedOverlayList.isEmpty(), mapView, getResources());

					//add item on overlay
					itemizedOverlay.addOverlay(overlayItem);
					//add overlay to map
					itemizedOverlayList.add(itemizedOverlay);
				}

				return null;
			}

			@Override
			protected void onPostExecute(final Entity result) {
				super.onPostExecute(result);

				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						mapView.setBuiltInZoomControls(true);
						mapOverlays = mapView.getOverlays();
						myLocationOverlay = new MyLocationOverlay(MapViewActivity.this, mapView);
						mapOverlays.addAll(itemizedOverlayList);
						mapOverlays.add(myLocationOverlay);

						if (GpsUtils.isEnabled(MapViewActivity.this)) {
							myLocationOverlay.enableMyLocation();
							myLocationOverlay.enableCompass();
						}

						// dismiss balloon upon single tap of MapView (iOS behavior)
						mapView.setOnSingleTapListener(MapViewActivity.this);

						//Show on Map
						if (savedInstanceState == null) {
							final MapController mc = mapView.getController();
							final Location lastLocation = locations.get(0);
							final GeoPoint lastPoint = new GeoPoint((int)(lastLocation.getLatitude()*1E6),(int)(lastLocation.getLongitude()*1E6));
							mc.animateTo(lastPoint);
							mc.setZoom(15);
						} else {
							// restoring focused state of overlays
							final int focused = savedInstanceState.getInt("focused", -1);
							if (focused >= 0) {
								final Overlay overlay = mapOverlays.get(focused);
								if (overlay instanceof CustomItemizedOverlay<?>) {
									@SuppressWarnings("unchecked")
									final CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = (CustomItemizedOverlay<CustomOverlayItem>) overlay;
									itemizedOverlay.setFocus(itemizedOverlay.getItem(focused));
								}
							}
						}
					}
				});
			}
		}.execute();
	}


	@Override
	protected void onResume() {
		super.onResume();
		if (myLocationOverlay != null) {
			if (GpsUtils.isEnabled(MapViewActivity.this)) {
				myLocationOverlay.enableMyLocation();
				myLocationOverlay.enableCompass();
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (myLocationOverlay != null) {
			myLocationOverlay.disableMyLocation();
			myLocationOverlay.disableCompass();
		}
	}

	@Override
	protected void onStop() {
		super.onPause();
		if (myLocationOverlay != null) {
			myLocationOverlay.disableMyLocation();
			myLocationOverlay.disableCompass();
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		// saving focused state of overlays
		for (final Overlay overlay : mapOverlays) {
			if (overlay instanceof CustomItemizedOverlay<?>) {
				final CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = (CustomItemizedOverlay<CustomOverlayItem>) overlay;
				if (itemizedOverlay.getFocus() != null) {
					outState.putInt("focused", itemizedOverlay.getLastFocusedIndex());
					break;
				}
			}
		}
		super.onSaveInstanceState(outState);
	}

	public boolean onSingleTap(final MotionEvent e) {
		if (mapOverlays.size() > 0) {
			for (final Overlay overlay : mapOverlays) {
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
		menuInflater.inflate(R.layout.menu_map, menu);
		if (mapView.isSatellite()) {
			final MenuItem item = menu.findItem(R.id.map);
			item.setTitle(getString(R.string.map));
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_home:
			startActivity(new Intent(getApplicationContext(), TabDashboardActivity.class));
			return true;
		case R.id.menu_me:
			if (myLocationOverlay != null) {
				final GeoPoint myLocation = myLocationOverlay.getMyLocation();
				if (myLocation != null) {
					mapView.getController().animateTo(myLocation);
				} else {
					GpsUtils.checkConfiguration(this);
				}
			}
			return true;
		case R.id.map:
			final String satellite = getString(R.string.satellite);
			if (item.getTitle().equals(satellite)) {
				mapView.setSatellite(true);
				item.setTitle(getString(R.string.map));
				item.setChecked(true);
			} else {
				mapView.setSatellite(false);
				item.setTitle(satellite);
			}
			return true;
		case R.id.menu_chance_account:
			startActivity(new Intent(getApplicationContext(), LoginActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}