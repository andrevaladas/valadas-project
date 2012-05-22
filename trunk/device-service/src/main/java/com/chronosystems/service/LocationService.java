package com.chronosystems.service;

import java.util.List;

import com.chronosystems.entity.Location;

public interface LocationService {

	public void save(final Location location);
	public List<Location> findLastLocations(final Long idDevice);
}
