/**
 * 
 */
package com.chronosystems.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Andre Valadas
 *
 */
@Entity
@XmlRootElement
public class Device implements Serializable {

	private static final long serialVersionUID = -1665393754488451531L;
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String login;
	private String name;

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="device")
	@OrderBy("timeline ASC")
	private List<Location> locations = new ArrayList<Location>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
}
