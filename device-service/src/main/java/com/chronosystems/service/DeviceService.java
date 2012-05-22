package com.chronosystems.service;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;

public interface DeviceService {

	public void save(final Device device);

	public Device find(final Long id);
	public Device find(final String email);
	public Device find(final String email, final String password);

	public Long rowCount(final String email);

	public Entity search(final Entity entity);
	public Entity findFollowing(final Entity entity);
	public Entity findFollowers(final Entity entity);
}
