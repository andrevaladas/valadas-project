/**
 * 
 */
package com.chronosystems.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Andre Valadas
 *
 */
@Root
@Entity
public class Location implements Serializable {

	private static final long serialVersionUID = -6509094806343240084L;

	@Element
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Element
	private double latitude;

	@Element
	private double longitude;

	@Element
	private Date timeline;

	@ManyToOne
	@JoinColumn(name = "iddevice")
	private Device device;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Date getTimeline() {
		return timeline;
	}
	public void setTimeline(Date timeline) {
		this.timeline = timeline;
	}
}
