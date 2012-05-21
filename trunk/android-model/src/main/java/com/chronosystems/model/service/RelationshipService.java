package com.chronosystems.model.service;

import java.io.Serializable;

import com.chronosystems.entity.Relationship;
import com.chronosystems.model.dao.RelationshipDao;

/**
 * @author Andre Valadas
 */
public class RelationshipService extends RelationshipDao implements Serializable {

	private static final long serialVersionUID = 4338814577194676428L;

	@Override
	public void save(final Relationship entity) {
		super.save(entity);
	}
}
