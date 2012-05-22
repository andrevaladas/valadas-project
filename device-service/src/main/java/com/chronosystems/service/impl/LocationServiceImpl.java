package com.chronosystems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chronosystems.dao.LocationDAO;
import com.chronosystems.entity.Location;
import com.chronosystems.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDAO locationDAO;

	@Override
	@Transactional
	public void save(final Location location) {
		locationDAO.save(location);
	}

	@Override
	@Transactional
	public List<Location> findLastLocations(final Long idDevice) {
		return locationDAO.findLastLocations(idDevice);
	}


}
