/**
 * 
 */
package com.chronosystems.places.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Andre Valadas
 *
 */
@Root(name="geometry")
public class Geometry {

	@Element(required=false)
	private Location location;

	@Element(required=false)
	private Viewport viewport;

	public Location getLocation() {
		return location;
	}
	public void setLocation(final Location location) {
		this.location = location;
	}
	public Viewport getViewport() {
		return viewport;
	}
	public void setViewport(final Viewport viewport) {
		this.viewport = viewport;
	}
}
