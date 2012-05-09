package com.chronosystems.route.view;

import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.chronosystems.route.DrivingDirections.IDirectionsListener;
import com.chronosystems.route.DrivingDirections.Mode;
import com.chronosystems.route.Placemark;
import com.chronosystems.route.Route;
import com.chronosystems.route.impl.DrivingDirectionsGoogleKML;
import com.chronosystems.route.overlay.AddItemizedOverlay;
import com.chronosystems.route.overlay.RouteOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class AndroidMapDirectionsActivity extends MapActivity {

	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	AddItemizedOverlay itemizedOverlay
	;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Displaying Zooming controls
		final MapView mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);

		/**
		 * Changing Map Type
		 * */
		// mapView.setSatellite(true); // Satellite View
		// mapView.setStreetView(true); // Street View
		// mapView.setTraffic(true); // Traffic view

		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.mark_red);
		itemizedOverlay = new AddItemizedOverlay(drawable, this);

		final DrivingDirectionsGoogleKML googleKML = new DrivingDirectionsGoogleKML();
		final GeoPoint startPoint = new GeoPoint((int)(-29.7639*1E6), (int)(-51.1446*1E6));
		final GeoPoint endPoint = new GeoPoint((int)(-29.7700*1E6), (int)(-51.1407*1E6));//-29.7700, -51.1407

		/**
		 * Placing Start and End Marker
		 * */
		itemizedOverlay.addOverlay(new OverlayItem(startPoint, "Start point", "router"));
		mapOverlays.add(itemizedOverlay);

		itemizedOverlay.addOverlay(new OverlayItem(endPoint, "End point", "router"));
		mapOverlays.add(itemizedOverlay);


		/**
		 * showing location by Latitude and Longitude
		 * */
		final MapController mc = mapView.getController();
		mc.animateTo(startPoint);
		mc.setZoom(15);
		mapView.invalidate();

		googleKML.driveTo(startPoint, endPoint, Mode.DRIVING, new IDirectionsListener() {
			@Override
			public void onDirectionsNotAvailable() {
				System.out.println("DirectionsNotAvailable");
			}
			@Override
			public void onDirectionsAvailable(final Route route, final Mode mode) {
				System.err.println(route.getTotalDistance());

				System.out.println(">> GEOPOINTS <<");
				final List<GeoPoint> geopoints = route.getGeoPoints();
				for (final GeoPoint geoPoint : geopoints) {
					System.out.println("lat.: "+geoPoint.getLatitudeE6()+" long.: "+geoPoint.getLongitudeE6());
				}

				/**
				 * Placing Route
				 * */
				drawPath(geopoints, Color.BLUE);

				System.out.println(">> Placemark <<");
				final List<Placemark> placemarks = route.getPlacemarks();
				for (final Placemark placemark : placemarks) {
					/**
					 * Placing Marker
					 * */
					//					final GeoPoint location = placemark.getLocation();
					//					if (location != null) {
					//						final OverlayItem overlayitem = new OverlayItem(location, placemark.getDistance(), placemark.getInstructions());
					//						itemizedOverlay.addOverlay(overlayitem);
					//						mapOverlays.add(itemizedOverlay);
					//					}

					System.out.println("dintance: "+placemark.getDistance()+" instructions: "+placemark.getInstructions());
				}
			}
		});
	}

	private void drawPath(final List<GeoPoint> geoPoints, final int color) {
		for (int i = 1; i < geoPoints.size(); i++) {
			mapOverlays.add(new RouteOverlay(geoPoints.get(i - 1), geoPoints.get(i), color));
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}