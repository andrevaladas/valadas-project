package com.chronosystems.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chronosystems.dao.DeviceDAO;
import com.chronosystems.dao.session.CustomHibernateDaoSupport;
import com.chronosystems.form.Contact;

@Repository
public class DeviceDAOImpl extends CustomHibernateDaoSupport implements DeviceDAO {

	@Override
	public void addContact(final Contact contact) {
		getHibernateTemplate().save(contact);
	}

	@Override
	public List<Contact> listContact() {
		return getSessionFactory().getCurrentSession().createQuery("from Contact").list();
	}

	@Override
	public void removeContact(final Integer id) {
		final Contact contact = (Contact) getSessionFactory().getCurrentSession().load(
				Contact.class, id);
		if (null != contact) {
			getSessionFactory().getCurrentSession().delete(contact);
		}

	}
}
