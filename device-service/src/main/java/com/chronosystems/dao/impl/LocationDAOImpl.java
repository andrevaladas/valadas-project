package com.chronosystems.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chronosystems.dao.LocationDAO;
import com.chronosystems.dao.session.CustomHibernateDaoSupport;
import com.chronosystems.entity.Location;

@Repository
public class LocationDAOImpl extends CustomHibernateDaoSupport implements LocationDAO {

	@Override
	public void save(final Location location) {
		if (location.getId() == null) {
			getHibernateTemplate().save(location);
		} else {
			getHibernateTemplate().update(location);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Location> findLastLocations(final Long idDevice) {
		final StringBuilder hql = new StringBuilder();
		hql.append(" select loc from Location loc ");
		hql.append(" where loc.device.id = :id ");
		hql.append(" order by loc.timeline desc ");

		final Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", idDevice);

		// limit
		query.setFirstResult(0);
		query.setMaxResults(20);

		// execute query
		return query.list();
	}
}
