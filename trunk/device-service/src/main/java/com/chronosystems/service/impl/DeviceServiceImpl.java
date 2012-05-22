package com.chronosystems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chronosystems.dao.DeviceDAO;
import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceDAO deviceDAO;

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
		return deviceDAO.findFollowing(entity);
	}

	@Override
	@Transactional
	public Entity findFollowers(final Entity entity) {
		return deviceDAO.findFollowers(entity);
	}
}
