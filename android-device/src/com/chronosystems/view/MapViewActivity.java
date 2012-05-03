package com.chronosystems.view;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MotionEvent;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Location;
import com.chronosystems.library.maps.CustomItemizedOverlay;
import com.chronosystems.library.maps.CustomOverlayItem;
import com.chronosystems.maps.core.OnSingleTapListener;
import com.chronosystems.maps.core.TapControlledMapView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;

public class MapViewActivity extends MapActivity {

	TapControlledMapView mapView; // use the custom TapControlledMapView
	List<Overlay> mapOverlays;
	Drawable drawable;
	Drawable drawable2;
	Drawable drawable3;
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay2;
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay3;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);

		mapView = (TapControlledMapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		// dismiss balloon upon single tap of MapView (iOS behavior)
		mapView.setOnSingleTapListener(new OnSingleTapListener() {
			public boolean onSingleTap(final MotionEvent e) {
				itemizedOverlay.hideAllBalloons();
				return true;
			}
		});

		// first overlay
		drawable = getResources().getDrawable(R.drawable.pushpin);
		itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);
		// set iOS behavior attributes for overlay
		itemizedOverlay.setShowClose(false);
		itemizedOverlay.setShowDisclosure(true);
		itemizedOverlay.setSnapToCenter(true);

		/** Receive Params */
		//final Entity entity = (Entity) getIntent().getSerializableExtra("entity");
		final Device device = (Device) getIntent().getSerializableExtra("device");
		final Location location = device.getLocations().get(0);
		final GeoPoint pointCenter = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));
		final Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		String addressValue = "";
		try {
			final List<Address> addresses = geoCoder.getFromLocation(pointCenter.getLatitudeE6() / 1E6, pointCenter.getLongitudeE6() / 1E6, 1);
			if (addresses.size() > 0) {
				final Address address = addresses.get(0);
				for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
					addressValue += address.getAddressLine(i) + " ";
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if(addressValue.isEmpty()) {
				addressValue = "Não foi possível localizar o endereço de referência.";
			}
		}
		final CustomOverlayItem overlayItemCenter = new CustomOverlayItem(pointCenter,
				device.getName(),
				addressValue,
				"http://ia.media-imdb.com/images/M/MV5BMjAyNjk5Njk0MV5BMl5BanBnXkFtZTcwOTA4MjIyMQ@@._V1._SX40_CR0,0,40,54_.jpg");
		itemizedOverlay.addOverlay(overlayItemCenter);



		// second overlay
		drawable2 = getResources().getDrawable(R.drawable.marker);
		itemizedOverlay2 = new CustomItemizedOverlay<CustomOverlayItem>(drawable2, mapView);
		itemizedOverlay2.setShowClose(false);
		itemizedOverlay2.setShowDisclosure(true);
		itemizedOverlay2.setSnapToCenter(true);

		final GeoPoint point = new GeoPoint((int)(51.5174723*1E6),(int)(-0.0899537*1E6));
		final CustomOverlayItem overlayItem = new CustomOverlayItem(point, "Tomorrow Never Dies (1997)",
				"(M gives Bond his mission in Daimler car)",
				"http://ia.media-imdb.com/images/M/MV5BMTM1MTk2ODQxNV5BMl5BanBnXkFtZTcwOTY5MDg0NA@@._V1._SX40_CR0,0,40,54_.jpg");
		itemizedOverlay2.addOverlay(overlayItem);

		final GeoPoint point2 = new GeoPoint((int)(51.515259*1E6),(int)(-0.086623*1E6));
		final CustomOverlayItem overlayItem2 = new CustomOverlayItem(point2, "GoldenEye (1995)",
				"(Interiors Russian defence ministry council chambers in St Petersburg)",
				"http://ia.media-imdb.com/images/M/MV5BMzk2OTg4MTk1NF5BMl5BanBnXkFtZTcwNjExNTgzNA@@._V1._SX40_CR0,0,40,54_.jpg");
		itemizedOverlay2.addOverlay(overlayItem2);



		// third overlay
		drawable3 = getResources().getDrawable(R.drawable.marker2);
		itemizedOverlay3 = new CustomItemizedOverlay<CustomOverlayItem>(drawable3, mapView);
		itemizedOverlay3.setShowClose(false);
		itemizedOverlay3.setShowDisclosure(true);
		itemizedOverlay3.setSnapToCenter(true);

		final GeoPoint point3 = new GeoPoint((int)(51.513329*1E6),(int)(-0.08896*1E6));
		final CustomOverlayItem overlayItem3 = new CustomOverlayItem(point3, "Sliding Doors (1998)",
				"(interiors)", null);
		itemizedOverlay3.addOverlay(overlayItem3);

		final GeoPoint point4 = new GeoPoint((int)(51.51738*1E6),(int)(-0.08186*1E6));
		final CustomOverlayItem overlayItem4 = new CustomOverlayItem(point4, "Mission: Impossible (1996)",
				"(Ethan & Jim cafe meeting)",
				"http://ia.media-imdb.com/images/M/MV5BMjAyNjk5Njk0MV5BMl5BanBnXkFtZTcwOTA4MjIyMQ@@._V1._SX40_CR0,0,40,54_.jpg");
		itemizedOverlay3.addOverlay(overlayItem4);


		//Add all layers
		mapOverlays = mapView.getOverlays();
		mapOverlays.add(itemizedOverlay);
		mapOverlays.add(itemizedOverlay2);
		mapOverlays.add(itemizedOverlay3);


		//Show on Map
		if (savedInstanceState == null) {
			final MapController mc = mapView.getController();
			mc.animateTo(pointCenter);
			mc.setZoom(16);
		} else {
			// restoring focused state of overlays
			int focused;
			focused = savedInstanceState.getInt("focused_1", -1);
			if (focused >= 0) {
				itemizedOverlay.setFocus(itemizedOverlay.getItem(focused));
			}
			focused = savedInstanceState.getInt("focused_2", -1);
			if (focused >= 0) {
				itemizedOverlay2.setFocus(itemizedOverlay2.getItem(focused));
			}
			focused = savedInstanceState.getInt("focused_3", -1);
			if (focused >= 0) {
				itemizedOverlay3.setFocus(itemizedOverlay3.getItem(focused));
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
		if (itemizedOverlay.getFocus() != null) {
			outState.putInt("focused_1", itemizedOverlay.getLastFocusedIndex());
		}
		if (itemizedOverlay2.getFocus() != null) {
			outState.putInt("focused_2", itemizedOverlay2.getLastFocusedIndex());
		}
		if (itemizedOverlay3.getFocus() != null) {
			outState.putInt("focused_3", itemizedOverlay3.getLastFocusedIndex());
		}
		super.onSaveInstanceState(outState);
	}
}