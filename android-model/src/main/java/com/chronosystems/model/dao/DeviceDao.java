/**
 * 
 */
package com.chronosystems.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Location;
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
	protected List<Device> findAll() {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			final List<Device> list = session.createQuery("from Device order by name").list();
			final List<Device> result = new ArrayList<Device>();
			for (final Device device : list) {
				final List<Location> locations = device.getLocations();
				if(!locations.isEmpty()) {
					final Device data = new Device();
					data.setName(device.getName());
					data.setEmail(device.getEmail());
					data.setDatecreatedInTime(device.getDatecreated().getTime());

					final Location location = locations.get(0);
					location.setTimelineInTime(location.getTimeline().getTime());
					data.addLocation(location);
					result.add(data);
				}
			}
			return result;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
}