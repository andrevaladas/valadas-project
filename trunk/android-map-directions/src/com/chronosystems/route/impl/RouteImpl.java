package com.chronosystems.route.impl;

import java.util.ArrayList;
import java.util.List;

import com.chronosystems.route.Placemark;
import com.chronosystems.route.Route;
import com.google.android.maps.GeoPoint;

public class RouteImpl implements Route {
	private String totalDistance;
	private List<GeoPoint> geoPoints;
	private List<Placemark> placemarks;

	public void setTotalDistance(final String totalDistance) {
		this.totalDistance = totalDistance;
	}

	@Override
	public String getTotalDistance() {
		return totalDistance;
	}

	public void setGeoPoints(final List<GeoPoint> geoPoints) {
		this.geoPoints = geoPoints;
	}

	@Override
	public List<GeoPoint> getGeoPoints() {
		return geoPoints;
	}

	public void addGeoPoint (final GeoPoint point)
	{
		if (geoPoints == null) {
			geoPoints = new ArrayList<GeoPoint>();
		}
		geoPoints.add(point);
	}

	public void setPlacemarks(final List<Placemark> placemarks) {
		this.placemarks = placemarks;
	}

	@Override
	public List<Placemark> getPlacemarks() {
		return placemarks;
	}

	public void addPlacemark (final Placemark mark)
	{
		if (placemarks == null) {
			placemarks = new ArrayList<Placemark>();
		}
		placemarks.add(mark);
	}
}
