package com.chronosystems.route.overlay;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class AddItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private final ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();

	private Context context;

	public AddItemizedOverlay(final Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public AddItemizedOverlay(final Drawable defaultMarker, final Context context) {
		this(defaultMarker);
		this.context = context;
	}

	@Override
	protected OverlayItem createItem(final int i) {
		return mapOverlays.get(i);
	}

	@Override
	public int size() {
		return mapOverlays.size();
	}

	@Override
	public boolean onTap(final GeoPoint p, final MapView mapView) {
		System.out.println("GEO POINT");
		return super.onTap(p, mapView);
	}

	@Override
	protected boolean onTap(final int index) {
		final OverlayItem overlayItem = mapOverlays.get(index);
		Toast.makeText(context, overlayItem.getTitle() + " - " + overlayItem.getSnippet(), Toast.LENGTH_SHORT).show();
		Log.e("Tap", "Tap Performed");
		return true;
	}

	public void addOverlay(final OverlayItem overlay) {
		mapOverlays.add(overlay);
		this.populate();
	}

	//	/**
	//	 * Getting Latitude and Longitude on Touch event
	//	 * **/
	//	@Override
	//	public boolean onTouchEvent(final MotionEvent event, final MapView mapView)
	//	{
	//
	//		if (event.getAction() == 1) {
	//			final GeoPoint geopoint = mapView.getProjection().fromPixels(
	//					(int) event.getX(),
	//					(int) event.getY());
	//			// latitude
	//			final double lat = geopoint.getLatitudeE6() / 1E6;
	//			// longitude
	//			final double lon = geopoint.getLongitudeE6() / 1E6;
	//
	//			Toast.makeText(context, "Lat: " + lat + ", Lon: "+lon, Toast.LENGTH_SHORT).show();
	//			return true;
	//		}
	//		return false;
	//	}

}