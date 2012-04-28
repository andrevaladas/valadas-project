/**
 * 
 */
package com.chronosystems.model.dao;

import java.util.List;

import org.hibernate.Session;

import com.chronosystems.entity.Device;
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

	protected void save(Device entity) {
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

	protected Device find(String login) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			final Device result = (Device) session.createQuery("from Device where login = :login ").setString("login", login).uniqueResult();
			session.getTransaction().commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected List<Device> findAll() {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			final List<Device> list = session.createQuery("from Device").list();
			session.getTransaction().commit();
			return list;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
}
