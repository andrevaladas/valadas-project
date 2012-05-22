package com.chronosystems.dao.impl;

import org.springframework.stereotype.Repository;

import com.chronosystems.dao.RelationshipDAO;
import com.chronosystems.dao.session.CustomHibernateDaoSupport;
import com.chronosystems.entity.Relationship;

@Repository
public class RelationshipDAOImpl extends CustomHibernateDaoSupport implements RelationshipDAO {

	@Override
	public void save(final Relationship relationship) {
		if (relationship.getId() == null) {
			getHibernateTemplate().save(relationship);
		} else {
			getHibernateTemplate().update(relationship);
		}
	}
}
