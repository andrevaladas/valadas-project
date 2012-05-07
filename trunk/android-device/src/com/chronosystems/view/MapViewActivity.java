package com.chronosystems.view;

import java.util.List;

import android.os.Bundle;
import android.view.MotionEvent;

import com.chronosystems.crop.image.ImageHelper;
import com.chronosystems.entity.Device;
import com.chronosystems.entity.Location;
import com.chronosystems.library.maps.CustomItemizedOverlay;
import com.chronosystems.library.maps.CustomOverlayItem;
import com.chronosystems.library.utils.LocationUtils;
import com.chronosystems.maps.core.OnSingleTapListener;
import com.chronosystems.maps.core.TapControlledMapView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;

public class MapViewActivity extends MapActivity {

	TapControlledMapView mapView; // use the custom TapControlledMapView
	List<Overlay> mapOverlays;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);

		mapView = (TapControlledMapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapOverlays = mapView.getOverlays();

		// dismiss balloon upon single tap of MapView (iOS behavior)
		mapView.setOnSingleTapListener(new OnSingleTapListener() {
			public boolean onSingleTap(final MotionEvent e) {
				@SuppressWarnings("unchecked")
				final CustomItemizedOverlay<CustomOverlayItem> overlay = (CustomItemizedOverlay<CustomOverlayItem>)mapOverlays.get(0);
				overlay.hideAllBalloons();
				return true;
			}
		});

		/** Receive Params */
		//final Entity entity = (Entity) getIntent().getSerializableExtra("entity");
		final Device device = (Device) getIntent().getSerializableExtra("device");

		/** create all point locations on map */
		final List<Location> locations = device.getLocations();
		for (final Location location : locations) {
			// create a point
			final GeoPoint point = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));

			// define data point
			final CustomOverlayItem overlayItem = new CustomOverlayItem(
					point,
					device.getName(),
					LocationUtils.getDateTimeDescrition(location).concat("\n").concat(LocationUtils.getAddressFromGeocoder(location, getBaseContext())),
					ImageHelper.getRoundedBitmap(device.getImage()));

			// define overlay/marker by location timeline
			location.setDevice(device);
			final CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = LocationUtils.getItemizedOverlay(location, mapView, getResources());

			//add item on overlay
			itemizedOverlay.addOverlay(overlayItem);
			//add overlay to map
			mapOverlays.add(itemizedOverlay);
		}

		//Show on Map
		if (savedInstanceState == null) {
			final MapController mc = mapView.getController();
			final Location lastLocation = device.getLocations().get(0);
			final GeoPoint lastPoint = new GeoPoint((int)(lastLocation.getLatitude()*1E6),(int)(lastLocation.getLongitude()*1E6));
			mc.animateTo(lastPoint);
			mc.setZoom(15);
		} else {
			// restoring focused state of overlays
			final int focused = savedInstanceState.getInt("focused", -1);
			if (focused >= 0) {
				@SuppressWarnings("unchecked")
				final CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = (CustomItemizedOverlay<CustomOverlayItem>) mapOverlays.get(focused);
				itemizedOverlay.setFocus(itemizedOverlay.getItem(focused));
			}
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		// saving focused state of overlays
		for (final Overlay overlay : mapOverlays) {
			@SuppressWarnings("unchecked")
			final CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = (CustomItemizedOverlay<CustomOverlayItem>) overlay;
			if (itemizedOverlay.getFocus() != null) {
				outState.putInt("focused", itemizedOverlay.getLastFocusedIndex());
				break;
			}
		}
		super.onSaveInstanceState(outState);
	}
}