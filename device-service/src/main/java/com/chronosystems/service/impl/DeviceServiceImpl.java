package com.chronosystems.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chronosystems.dao.DeviceDAO;
import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;
import com.chronosystems.service.DeviceService;
import com.chronosystems.service.LocationService;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceDAO deviceDAO;

	@Autowired
	private LocationService locationService;

	@Override
	@Transactional
	public void save(final Device device) {
		deviceDAO.save(device);
	}

	@Override
	@Transactional
	public Device find(final Long id) {
		return deviceDAO.find(id);
	}

	@Override
	@Transactional
	public Device find(final String email) {
		return deviceDAO.find(email);
	}

	@Override
	@Transactional
	public Device find(final String email, final String password) {
		return deviceDAO.find(email, password);
	}

	@Override
	@Transactional
	public Long rowCount(final String email) {
		return deviceDAO.rowCount(email);
	}

	@Override
	@Transactional
	public Entity search(final Entity entity) {
		return deviceDAO.search(entity);
	}

	@Override
	@Transactional
	public Entity findFollowing(final Entity entity) {
		final Entity entityResult = deviceDAO.findFollowing(entity);
		loadLastLocations(entityResult);
		return entityResult;
	}

	@Override
	@Transactional
	public Entity findFollowers(final Entity entity) {
		return deviceDAO.findFollowers(entity);
	}

	private void loadLastLocations(final Entity entityResult) {
		// return only last device location
		final List<Device> devices = entityResult.getDevices();
		for (final Device device : devices) {
			device.setDateInTime(device.getDatecreated().getTime());
			final List<Location> locations = locationService.findLastLocations(device.getId());
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
