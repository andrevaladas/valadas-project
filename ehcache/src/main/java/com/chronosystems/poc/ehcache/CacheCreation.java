/**
 * 
 */
package com.chronosystems.poc.ehcache;

import java.util.List;

/**
 * @author User
 *
 */
public abstract class CacheCreation<T> {
	public abstract List<T> getAll();	
}
