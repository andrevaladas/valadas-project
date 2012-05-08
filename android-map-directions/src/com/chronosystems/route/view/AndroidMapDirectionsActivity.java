package com.chronosystems.route.view;

import java.util.List;

import android.os.Bundle;

import com.chronosystems.route.DrivingDirections.IDirectionsListener;
import com.chronosystems.route.DrivingDirections.Mode;
import com.chronosystems.route.Placemark;
import com.chronosystems.route.Route;
import com.chronosystems.route.impl.DrivingDirectionsGoogleKML;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;

public class AndroidMapDirectionsActivity extends MapActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final DrivingDirectionsGoogleKML googleKML = new DrivingDirectionsGoogleKML();

		final GeoPoint startPoint = new GeoPoint((int)(-29.7639*1E6), (int)(-51.1446*1E6));
		final GeoPoint endPoint = new GeoPoint((int)(-29.7700*1E6), (int)(-51.1407*1E6));//-29.7700, -51.1407
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

				System.out.println(">> Placemark <<");
				final List<Placemark> placemarks = route.getPlacemarks();
				for (final Placemark placemark : placemarks) {
					System.out.println("dintance: "+placemark.getDistance()+" instructions: "+placemark.getInstructions());
				}

			}
		});
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}