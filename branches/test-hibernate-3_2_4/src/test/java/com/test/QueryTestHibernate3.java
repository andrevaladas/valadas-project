/**
 * 
 */
package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.model.A;
import com.model.B;
import com.util.PersistenceUtil;

/**
 * @author André Valadas
 *
 */
public class QueryTestHibernate3 {

	private PersistenceUtil session;

	@Before
	public void setUp(){
		session = new PersistenceUtil();
        session.startConnection();
		// create data
        createData();
	}

	@After
	public void tearDown() {
		 session.closeConnection();
	}

	@Test
    public void testQuery(){
        final StringBuilder hql = new StringBuilder();
    	hql.append("from A a ");
    	hql.append("	join fetch a.b b ");
    	hql.append("where a.b.name = 'entity_B'");
		session.createQuery(hql.toString()).getResultList();
    }
 
    private void createData(){
    	final A a = new A("entity_A");
    	final B b = new B("entity_B");
        a.setB(b);

        session.save(a);
    }
}
