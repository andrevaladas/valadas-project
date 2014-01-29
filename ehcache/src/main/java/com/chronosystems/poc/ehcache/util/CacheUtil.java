/**
 * 
 */
package com.chronosystems.poc.ehcache.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.chronosystems.poc.ehcache.CacheCreation;

/**
 * @author User
 *
 */
public class CacheUtil {
	public static CacheManager cacheMgr = null;

	private static Ehcache getCache(String cacheName){
		if(cacheMgr == null){
			// We could use an environment or a VM variable
			cacheMgr = CacheManager.newInstance("src/main/resources/ehcache.xml");
			//cacheMgr = CacheManager.create("ehcache.xml");
		}
			
		Ehcache cache = null;
		if(cacheMgr!=null){
			//cache = cacheMgr.addCacheIfAbsent(name);
			cache = cacheMgr.getEhcache(cacheName);
		}
		
		return cache;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getListFromCache(String threadName, String cacheName, String key, CacheCreation<T> cacheCreation){
		List<T> all = new ArrayList<T>();
			
		Ehcache cache = getCache(cacheName);
		Element element = null;
		if(cache!=null){
			element = cache.get(key);
		}
			
		if(element==null){
			System.out.println(threadName+" : CacheUtil.getListFromCache() : the element '"+key+"' has not been found in the cache ---> get the original data.");

			all = cacheCreation.getAll();
			cache.put(new Element(key, all));
			System.out.println(threadName+" : CacheUtil.getListFromCache() : the original data for the element '"+key+"' has been added in the cache.");


		}else{
			System.out.println(threadName+" : CacheUtil.getListFromCache() : the element '"+key+"' has been found in the cache.");

			//all = (List<T>) element.getValue();
			all = (List<T>) element.getObjectValue();
		}
		return all;

	}
}
