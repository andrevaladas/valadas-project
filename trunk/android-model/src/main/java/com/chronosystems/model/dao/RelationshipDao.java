package com.chronosystems.model.dao;

import org.hibernate.Session;

import com.chronosystems.entity.Relationship;
import com.chronosystems.util.HibernateUtil;

/**
 * @author Andre Valadas
 */
public abstract class RelationshipDao {
	private Session session;

	public RelationshipDao() {
		super();
	}

	protected void save(final Relationship entity) {
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
}
