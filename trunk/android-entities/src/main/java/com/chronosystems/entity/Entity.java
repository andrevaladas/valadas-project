package com.chronosystems.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.chronosystems.core.FilterEntity;

/**
 * @author Andre Valadas
 */
@Root
public class Entity extends FilterEntity implements Serializable {

	private static final long serialVersionUID = -2259338148320677313L;

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
		if (devices == null) {
			devices = new ArrayList<Device>();
		}
		return devices;
	}
	public void setDevices(final List<Device> devices) {
		this.devices = devices;
	}

	public boolean hasDevices() {
		return !getDevices().isEmpty();
	}
	public void addDevice(final Device device) {
		if(devices == null) {
			devices = new ArrayList<Device>();
		}
		devices.add(device);
	}
}
