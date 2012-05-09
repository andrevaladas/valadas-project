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

import com.chronosystems.core.TimeEntity;

/**
 * @author Andre Valadas
 */
@Root
@Entity
public class Location extends TimeEntity implements Serializable {

	private static final long serialVersionUID = -6509094806343240084L;

	@Element(required=false)
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Element
	private double latitude;

	@Element
	private double longitude;

	private Date timeline;

	@ManyToOne
	@JoinColumn(name = "iddevice")
	private Device device;

	public Location() {
		super();
	}

	public Location(final double latitude, final double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Long getId() {
		return id;
	}
	public void setId(final Long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}
	public void setDevice(final Device device) {
		this.device = device;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}
	public Date getTimeline() {
		if(timeline == null) {
			if (super.getDateInTime() != null) {
				timeline = new Date(super.getDateInTime());
			} else {
				timeline = new Date();
			}
		}
		return timeline;
	}
	public void setTimeline(final Date timeline) {
		this.timeline = timeline;
	}

	@Override
	public Long getDateInTime() {
		final Long dateInTime = super.getDateInTime();
		if (dateInTime == null) {
			super.setDateInTime(getTimeline().getTime());
		}
		return super.getDateInTime();
	}
}
