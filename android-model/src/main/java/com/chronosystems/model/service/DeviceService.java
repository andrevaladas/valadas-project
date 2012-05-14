/**
 * 
 */
package com.chronosystems.model.service;

import java.io.Serializable;
import java.util.List;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;
import com.chronosystems.model.dao.DeviceDao;

/**
 * @author Andre Valadas
 *
 */
public class DeviceService extends DeviceDao implements Serializable {

	private static final long serialVersionUID = -1626723954167785038L;

	@Override
	public Device find(final String email) {
		return super.find(email);
	}

	@Override
	public Device find(final Long id) {
		return super.find(id);
	}

	@Override
	public Device find(final String email, final String password) {
		return super.find(email, password);
	}

	@Override
	public void save(final Device entity) {
		super.save(entity);
	}

	@Override
	public Long rowCount(final String email) {
		return super.rowCount(email);
	}

	@Override
	public Entity search(final Entity entity) {
		final Entity resultEntity = super.search(entity);

		// return only last device location
		final List<Device> devices = resultEntity.getDevices();
		for (final Device device : devices) {
			device.setDateInTime(device.getDatecreated().getTime());

			final List<Location> locations = device.getLocations();
			for (final Location location : locations) {
				location.updateTimeline();
			}
		}
		return resultEntity;
	}
}
