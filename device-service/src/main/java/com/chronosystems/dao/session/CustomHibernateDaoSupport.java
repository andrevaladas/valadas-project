package com.chronosystems.dao.session;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class CustomHibernateDaoSupport extends HibernateDaoSupport {
	@Autowired
	public void injectSessionFactory(final SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
}