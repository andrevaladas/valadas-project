package com.chronosystems.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chronosystems.dao.DeviceDAO;
import com.chronosystems.dao.session.CustomHibernateDaoSupport;
import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;

@Repository
public class DeviceDAOImpl extends CustomHibernateDaoSupport implements DeviceDAO {

	@Override
	public void save(final Device device) {
		if (device.getId() == null) {
			getHibernateTemplate().save(device);
		} else {
			getHibernateTemplate().update(device);
		}
	}

	@Override
	public Device find(final Long id) {
		return (Device) getSessionFactory().getCurrentSession().createQuery("from Device where id = :id ").setLong("id", id).uniqueResult();
	}

	@Override
	public Device find(final String email) {
		return (Device) getSessionFactory().getCurrentSession().createQuery("from Device where email = :email ").setString("email", email).uniqueResult();
	}

	@Override
	public Device find(final String email, final String password) {
		final Query query = getSessionFactory().getCurrentSession().createQuery("from Device where email = :email and password = :password");
		query.setString("email", email);
		query.setString("password", password);
		return (Device) query.uniqueResult();
	}

	@Override
	public Long rowCount(final String email) {
		return (Long) getSessionFactory().getCurrentSession().createQuery("select count(*) from Device where email = :email ").setString("email", email).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Entity search(final Entity entity) {
		final StringBuilder hql = new StringBuilder();
		hql.append(" select dev ");
		hql.append(" from Device dev ");
		hql.append(" where dev.id <> :id");
		hql.append("   and dev.locations.size > 0");
		hql.append("   and not exists (from Relationship rel");
		hql.append("   				   where rel.follower.id = :id");
		hql.append("   				     and rel.following.id = dev.id)");
		hql.append(" order by dev.name");

		final Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", entity.getDevices().get(0).getId());

		// paginacao
		query.setFirstResult(entity.getMaxRecords() * ((entity.getCurrentPage()-1)+1));
		query.setMaxResults(entity.getMaxRecords()+1);

		// execute query
		final List<Device> result = query.list();

		// has more rows?
		entity.setHasNext(result.size());
		if(Boolean.TRUE.equals(entity.getHasNext())) {
			result.remove(result.size()-1);//remove last record
		}
		/** set device result list */
		entity.setDevices(result);
		return entity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Entity findFollowing(final Entity entity) {
		final StringBuilder hql = new StringBuilder();
		hql.append(" select dev.following ");
		hql.append(" from Device dev ");
		hql.append(" where dev.id = :id");

		final Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", entity.getDevices().get(0).getId());

		// paginacao
		query.setFirstResult(entity.getMaxRecords() * ((entity.getCurrentPage()-1)+1));
		query.setMaxResults(entity.getMaxRecords()+1);

		// execute query
		final List<Device> result = query.list();

		// has more rows?
		entity.setHasNext(result.size());
		if(Boolean.TRUE.equals(entity.getHasNext())) {
			result.remove(result.size()-1);//remove last record
		}
		/** set device result list */
		entity.setDevices(result);
		return entity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Entity findFollowers(final Entity entity) {
		final StringBuilder hql = new StringBuilder();
		hql.append(" select dev.follower ");
		hql.append(" from Device dev ");
		hql.append(" where dev.id = :id");
		hql.append("   and dev.follower.locations.size > 0");

		final Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong("id", entity.getDevices().get(0).getId());

		// paginacao
		query.setFirstResult(entity.getMaxRecords() * ((entity.getCurrentPage()-1)+1));
		query.setMaxResults(entity.getMaxRecords()+1);

		// execute query
		final List<Device> result = query.list();

		// has more rows?
		entity.setHasNext(result.size());
		if(Boolean.TRUE.equals(entity.getHasNext())) {
			result.remove(result.size()-1);//remove last record
		}
		/** set device result list */
		entity.setDevices(result);
		return entity;
	}
}
