package com.chronosystems.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.util.HibernateUtil;

/**
 * @author Andre Valadas
 *
 */
public abstract class DeviceDao {
	private Session session;

	public DeviceDao() {
		super();
	}

	protected void save(final Device entity) {
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

	public Device find(final Long id) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			return (Device) session.createQuery("from Device where id = :id ").setLong("id", id).uniqueResult();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
		return null;
	}

	protected Device find(final String email) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			return (Device) session.createQuery("from Device where email = :email ").setString("email", email).uniqueResult();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
		return null;
	}

	protected Device find(final String email, final String password) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			final Query query = session.createQuery("from Device where email = :email and password = :password");
			query.setString("email", email);
			query.setString("password", password);
			return (Device) query.uniqueResult();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
		return null;
	}

	protected Long rowCount(final String email) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			return (Long) session.createQuery("select count(*) from Device where email = :email ").setString("email", email).uniqueResult();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
		return 0L;
	}

	@SuppressWarnings("unchecked")
	protected Entity search(final Entity entity) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			final StringBuilder hql = new StringBuilder();
			hql.append(" select dev ");
			hql.append(" from Device dev ");
			hql.append(" where dev.id <> :id");
			hql.append("   and dev.locations.size > 0");
			hql.append("   and not exists (from Relationship rel");
			hql.append("   				   where rel.follower.id = :id");
			hql.append("   				     and rel.following.id = dev.id)");
			hql.append(" order by dev.name");

			final Query query = session.createQuery(hql.toString());
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
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected Entity findFollowing(final Entity entity) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			final StringBuilder hql = new StringBuilder();
			hql.append(" select dev.following ");
			hql.append(" from Device dev ");
			hql.append(" where dev.id = :id");

			final Query query = session.createQuery(hql.toString());
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
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected Entity findFollowers(final Entity entity) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			final StringBuilder hql = new StringBuilder();
			hql.append(" select dev.follower ");
			hql.append(" from Device dev ");
			hql.append(" where dev.id = :id");
			hql.append("   and dev.follower.locations.size > 0");

			final Query query = session.createQuery(hql.toString());
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
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
}
