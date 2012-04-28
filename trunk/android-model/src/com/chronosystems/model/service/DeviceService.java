/**
 * 
 */
package com.chronosystems.model.service;

import java.io.Serializable;
import java.util.List;

import com.chronosystems.entity.Device;
import com.chronosystems.model.dao.DeviceDao;

/**
 * @author Andre Valadas
 *
 */
public class DeviceService extends DeviceDao implements Serializable {

	private static final long serialVersionUID = -1626723954167785038L;

	@Override
	public Device find(String login) {
		return super.find(login);
	}
	
	@Override
	public void save(Device entity) {
		super.save(entity);
	}
	
	@Override
	public List<Device> findAll() {
		return super.findAll();
	}
}
