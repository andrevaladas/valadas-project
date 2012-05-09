/**
 * 
 */
package com.chronosystems.model.service;

import java.io.Serializable;

import com.chronosystems.entity.Location;
import com.chronosystems.model.dao.LocationDao;

/**
 * @author Andre Valadas
 */
public class LocationService extends LocationDao implements Serializable {

	private static final long serialVersionUID = 4338814577194676428L;

	@Override
	public void save(final Location entity) {
		super.save(entity);
	}
}
