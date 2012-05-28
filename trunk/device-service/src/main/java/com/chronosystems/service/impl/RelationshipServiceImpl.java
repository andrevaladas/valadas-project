package com.chronosystems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chronosystems.dao.RelationshipDAO;
import com.chronosystems.entity.Relationship;
import com.chronosystems.service.RelationshipService;

@Service
@Transactional
public class RelationshipServiceImpl implements RelationshipService {

	@Autowired
	private RelationshipDAO relationshipDAO;

	@Override
	public void save(final Relationship relationship) {
		relationshipDAO.save(relationship);
	}
}
