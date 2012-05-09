package com.chronosystems.route.impl;

import com.chronosystems.route.Placemark;
import com.google.android.maps.GeoPoint;

public class PlacemarkImpl implements Placemark {
	private GeoPoint location;
	private String instructions;
	private String distance;

	public void setLocation(final GeoPoint location) {
		this.location = location;
	}

	@Override
	public GeoPoint getLocation() {
		return location;
	}

	public void setInstructions(final String instructions) {
		this.instructions = instructions;
	}

	@Override
	public String getInstructions() {
		return instructions;
	}

	public void setDistance(final String distance) {
		this.distance = distance;
	}
	@Override
	public String getDistance() {
		return distance;
	}
}
