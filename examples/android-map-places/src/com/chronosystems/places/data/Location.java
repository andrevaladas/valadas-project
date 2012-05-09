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
@Root(name="location")
public class Location {

	@Element(required=false)
	private String lat;

	@Element(required=false)
	private String lng;

	public String getLat() {
		return lat;
	}
	public void setLat(final String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(final String lng) {
		this.lng = lng;
	}
}
