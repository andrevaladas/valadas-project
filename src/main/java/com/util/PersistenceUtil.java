/**
 * 
 */
package com.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * @author André Valadas
 *
 */
public class PersistenceUtil {
 
    private EntityManagerFactory emf;
    private EntityManager em;
 
    public void startConnection(){
        emf = Persistence.createEntityManagerFactory("HsqldbWithTDD");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
 
    public void closeConnection(){
        em.getTransaction().commit();
        emf.close();
    }
 
    public void save(Object entity){
        em.persist(entity);
    }
 
    public Query createQuery(final String hql) {
    	return em.createQuery(hql);
    }
}