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
	public Device find(String email) {
		return super.find(email);
	}
	
	@Override
	public Device find(String email, String password) {
		return super.find(email, password);
	}
	
	@Override
	public void save(Device entity) {
		super.save(entity);
	}
	
	@Override
	public Long rowCount(String email) {
		return super.rowCount(email);
	}
	
	@Override
	public List<Device> findAll() {
		return super.findAll();
	}
}
