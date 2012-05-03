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
import javax.persistence.Transient;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Andre Valadas
 */
@Root
@Entity
public class Location implements Serializable {

	private static final long serialVersionUID = -6509094806343240084L;

	@Element(required=false)
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Element
	private double latitude;

	@Element
	private double longitude;

	private Date timeline;

	@Transient
	@Element(required=false)
	private Long timelineInTime;

	@ManyToOne
	@JoinColumn(name = "iddevice")
	private Device device;

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
		if (timeline == null) {
			if (timelineInTime != null) {
				timeline = new Date(timelineInTime);
			} else {
				timeline = new Date();
			}
		}
		return timeline;
	}
	public void setTimeline(final Date timeline) {
		this.timeline = timeline;
		this.timelineInTime = getTimeline().getTime();
	}
	public void setTimelineInTime(final Long timelineInTime) {
		this.timelineInTime = timelineInTime;
	}
	public Long getTimelineInTime() {
		if(timelineInTime == null) {
			timelineInTime = getTimeline().getTime();
		}
		return timelineInTime;
	}
}
