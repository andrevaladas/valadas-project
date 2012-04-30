package com.chronosystems.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author Andre Valadas
 *
 */
@Root
public class Entity implements Serializable {

	private static final long serialVersionUID = 6246777207650306617L;

	@ElementList(entry="devices", inline=true, required=false)
	private List<Device> devices;

	public Entity() {
		super();
	}
	
	public Entity(final Device device) {
		super();
		addDevice(device);
	}

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
