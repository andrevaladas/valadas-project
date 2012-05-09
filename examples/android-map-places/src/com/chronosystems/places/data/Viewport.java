package com.chronosystems.places.data;

import org.simpleframework.xml.Element;

public class Viewport {

	@Element(required=false)
	private Location southwest;
	@Element(required=false)
	private Location northeast;

	public Location getSouthwest() {
		return southwest;
	}
	public void setSouthwest(final Location southwest) {
		this.southwest = southwest;
	}
	public Location getNortheast() {
		return northeast;
	}
	public void setNortheast(final Location northeast) {
		this.northeast = northeast;
	}
}
