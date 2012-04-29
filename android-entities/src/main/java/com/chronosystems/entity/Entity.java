package com.chronosystems.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Andre Valadas
 *
 */
@XmlRootElement
public class Entity implements Serializable {
	
	private static final long serialVersionUID = 6246777207650306617L;
	
	private List<Device> devices;
	
	public Entity() {
		super();
	}
	
	public Entity(final Device device) {
		super();
		addDevice(device);
	}

	@XmlElement(name="result")
	public List<Device> getDevices() {
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public void addDevice(final Device device) {
		if(devices == null) {
			devices = new ArrayList<Device>();
		}
		devices.add(device);
	}
}
