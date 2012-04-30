/**
 * 
 */
package com.chronosystems.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author Andre Valadas
 *
 */
@Root
@Entity
public class Device implements Serializable {

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

	@Element(required=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datecreated;

	@ElementList(entry="locations", inline=true, required=false)
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="device")
	@OrderBy("timeline DESC")
	private List<Location> locations = new ArrayList<Location>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
}
