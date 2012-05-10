/**
 * 
 */
package com.chronosystems.places.data;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author Andre Valadas
 *
 */
@Root(name="result")
public class Place {

	@Element(required=false)
	private String name;
	@Element(required=false)
	private String vicinity;

	@ElementList(entry="type", inline=true, required=false)
	private List<String> type;

	@Element(required=false)
	private Geometry geometry;
	@Element(required=false)
	private String rating;
	@Element(required=false)
	private String icon;
	@Element(required=false)
	private String reference;
	@Element(required=false)
	private String id;

	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getVicinity() {
		return vicinity;
	}
	public void setVicinity(final String vicinity) {
		this.vicinity = vicinity;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(final List<String> type) {
		this.type = type;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(final Geometry geometry) {
		this.geometry = geometry;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(final String rating) {
		this.rating = rating;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(final String icon) {
		this.icon = icon;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(final String reference) {
		this.reference = reference;
	}
	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}
}
