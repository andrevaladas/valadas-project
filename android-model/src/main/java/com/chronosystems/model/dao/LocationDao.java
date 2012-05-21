package com.chronosystems.model.dao;

import java.util.List;

import org.hibernate.Query;
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

	@SuppressWarnings("unchecked")
	protected List<Location> findLastLocations(final Long idDevice) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			final StringBuilder hql = new StringBuilder();
			hql.append(" select loc from Location loc ");
			hql.append(" where loc.device.id = :id ");
			hql.append(" order by loc.timeline desc ");

			final Query query = session.createQuery(hql.toString());
			query.setLong("id", idDevice);

			// limit
			query.setFirstResult(0);
			query.setMaxResults(10);

			// execute query
			return query.list();
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
}
