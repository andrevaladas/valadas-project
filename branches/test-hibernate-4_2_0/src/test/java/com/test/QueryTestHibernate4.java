/**
 * 
 */
package com.test;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.model.A;
import com.model.B;
import com.util.HibernateUtil;

/**
 * @author André Valadas
 *
 */
public class QueryTestHibernate4 {

	private SessionFactory sessionFactory;
	private Session session;

	@Before
	public void setUp(){
		sessionFactory = HibernateUtil.buildSessionFactory();
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		// create data
        createData();
	}

	@After
	public void tearDown() {
		 sessionFactory.close();
	}

	@Test
    public void testQueryForHibernate4_newQueryTranslator(){

        final StringBuilder hql = new StringBuilder();
    	hql.append("from A a ");
    	hql.append("	join fetch a.b b_alias ");
    	hql.append("where a.b.name = 'entity_B'"); //FIXME THIS POINT
    	//hql.append("where b_alias.name = 'entity_B'"); //TODO THIS IS THE CORRECT SINTAX...?

		session.createQuery(hql.toString()).list();
		Assert.fail("Look the SQL output with TWO joins to the same entity: B");
    }

	@Test
    public void testQueryForHibernate4_correctToOldVersion(){

		final StringBuilder hql = new StringBuilder();
    	hql.append("from A a ");
    	hql.append("	join fetch a.b b_alias ");
    	//hql.append("where a.b.name = 'entity_B'"); //FIXME THIS POINT
    	hql.append("where b_alias.name = 'entity_B'"); //TODO THIS IS THE CORRECT SINTAX...?

		session.createQuery(hql.toString()).list();
		System.out.println(">>> This is the correct HQL sintax... compatible with hibernate 3 ?");
    }

    private void createData(){
        A a = new A("entity_A");
        B b = new B("entity_B");
        a.setB(b);

        session.save(a);
        session.flush();
    }
}
