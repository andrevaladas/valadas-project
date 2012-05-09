/**
 * 
 */
package com.chronosystems.model.dao;

import org.hibernate.Session;

import com.chronosystems.entity.Location;
import com.chronosystems.util.HibernateUtil;

/**
 * @author Andre Valadas
 */
public abstract class LocationDao {
	private Session session;

	public LocationDao() {
		super();
	}

	protected void save(final Location entity) {
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			if (entity.getId() == null) {
				session.save(entity);
			} else {
				session.update(entity);
			}
			session.getTransaction().commit();
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}

}
