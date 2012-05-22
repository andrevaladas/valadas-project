package com.chronosystems.dao;

import java.util.List;

import com.chronosystems.entity.Location;

public interface LocationDAO {

	public void save(final Location location);
	public List<Location> findLastLocations(final Long idDevice);
}
