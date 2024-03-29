/**
 * 
 */
package com.chronosystems.places.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import com.google.android.maps.GeoPoint;

/**
 * @author Andre Valadas
 */
public class PlaceFilter {

	private static final String codeKey = "AIzaSyC8EYE1c4P6p-JIQOlu6asdL7vjHc-6kCI";

	private GeoPoint location;
	private String name;
	private List<String> type = Arrays.asList("establishment");//food|restaurant|establishment|store|grocery_or_supermarket|bakery
	private String language = "pt-BR"; //en
	private int radius = 500;
	private boolean sensor;

	public GeoPoint getLocation() {
		return location;
	}
	public void setLocation(final GeoPoint location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(final List<String> type) {
		this.type = type;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(final int radius) {
		this.radius = radius;
	}
	public boolean isSensor() {
		return sensor;
	}
	public void setSensor(final boolean sensor) {
		this.sensor = sensor;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(final String language) {
		this.language = language;
	}

	public String getFilterUrl(final String urlPlaces) {
		StringBuilder url = new StringBuilder(urlPlaces+"?language="+language);
		if(location != null) {
			url.append("&location=");
			url.append(String.valueOf(location.getLatitudeE6()/1E6));
			url.append(",");
			url.append(String.valueOf(location.getLongitudeE6()/1E6));
		}

		if(name != null) {
			try {
				url.append("&name="+URLEncoder.encode(name, "UTF-8"));
			} catch (final UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if(type != null && !getType().isEmpty()) {
			String pipeEncoded = "|";
			try {
				pipeEncoded = URLEncoder.encode(pipeEncoded, "UTF-8");
			} catch (final UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			url.append("&types=");
			for (final String type : getType()) {
				url.append(type+pipeEncoded);
			}
			final int lastIndexOf = url.lastIndexOf(pipeEncoded);
			url = url.delete(lastIndexOf, url.toString().length());
		}

		url.append("&radius="+radius);
		url.append("&sensor="+sensor);
		url.append("&key="+codeKey);
		return url.toString();
	}
}
