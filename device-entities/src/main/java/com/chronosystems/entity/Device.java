package com.chronosystems.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.chronosystems.core.TimeEntity;

/**
 * @author Andre Valadas
 */
@Root
@Entity
public class Device extends TimeEntity implements Serializable {

	private static final long serialVersionUID = -1665393754488451531L;

	@Element(required=false)
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Element(required=false)
	private String name;

	@Element(required=false)
	private String email;

	@Element(required=false)
	private String password;

	private Date datecreated;

	@Lob
	@Element(required=false)
	private byte[] image;

	@ElementList(entry="locations", inline=true, required=false)
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="device")
	@OrderBy("timeline DESC")
	private List<Location> locations;

	@ManyToMany()
	@JoinTable(name = "RELATIONSHIP",
	joinColumns = { @JoinColumn(name = "ID_FOLLOWER") },
	inverseJoinColumns = { @JoinColumn(name = "ID_FOLLOWING") })
	private Set<Device> following = new HashSet<Device>(0);

	@ManyToMany()
	@JoinTable(name = "RELATIONSHIP",
	joinColumns = { @JoinColumn(name = "ID_FOLLOWING") },
	inverseJoinColumns = { @JoinColumn(name = "ID_FOLLOWER") })
	private Set<Device> followers = new HashSet<Device>(0);

	public Long getId() {
		return id;
	}
	public void setId(final Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}
	public Date getDatecreated() {
		if(datecreated == null) {
			if (super.getDateInTime() != null) {
				datecreated = new Date(super.getDateInTime());
			} else {
				datecreated = new Date();
			}
		}
		return datecreated;
	}
	public void setDatecreated(final Date datecreated) {
		this.datecreated = datecreated;
	}

	@Override
	public Long getDateInTime() {
		final Long dateInTime = super.getDateInTime();
		if (dateInTime == null) {
			super.setDateInTime(getDatecreated().getTime());
		}
		return super.getDateInTime();
	}

	public List<Location> getLocations() {
		if (locations ==  null) {
			locations = new ArrayList<Location>();
		}
		return locations;
	}
	public void setLocations(final List<Location> locations) {
		this.locations = locations;
	}
	public void addLocation(final Location location) {
		getLocations().add(0, location);
	}

	public byte[] getImage() {
		return image;
	}
	public void setImage(final byte[] image) {
		this.image = image;
	}

	public Set<Device> getFollowing() {
		return following;
	}
	public void setFollowing(final Set<Device> following) {
		this.following = following;
	}
	public Set<Device> getFollowers() {
		return followers;
	}
	public void setFollowers(final Set<Device> followers) {
		this.followers = followers;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", email=" + email
				+ ", password=" + password + ", datecreated=" + datecreated
				+ ", locations=" + locations + "]";
	}
}
