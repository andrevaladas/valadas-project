package com.chronosystems.model.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
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
		return super.search(entity);
	}

	@Override
	public Entity findFollowing(final Entity entity) {
		final Entity resultEntity = super.findFollowing(entity);
		loadLastLocations(resultEntity);
		return resultEntity;
	}

	@Override
	public Entity findFollowers(final Entity entity) {
		final Entity resultEntity = super.findFollowers(entity);
		loadLastLocations(resultEntity);
		return resultEntity;
	}

	private void loadLastLocations(final Entity resultEntity) {
		// return only last device location
		final List<Device> devices = resultEntity.getDevices();
		for (final Device device : devices) {
			device.setDateInTime(device.getDatecreated().getTime());
			final List<Location> locations = new LocationService().findLastLocations(device.getId());
			device.setLocations(locations);
		}

		//order by lastLocation
		Collections.sort(devices, new Comparator<Device>() {
			@Override
			public int compare(final Device o1, final Device o2) {
				return o2.getLocations().get(0).getTimeline().compareTo(o1.getLocations().get(0).getTimeline());
			};
		});
	}
}
