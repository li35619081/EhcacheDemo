package com.test.ehcache.util;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhcacheUtil {

	private static final Logger LOG = LoggerFactory.getLogger(EhcacheUtil.class);


	public static CacheManager manager = CacheManager.create();
	
	/**
	 * Gets an element from the cache. Updates Element Statistics
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, Object key) {
		Ehcache ehcache = getEhcache(cacheName);
		if (ehcache != null) {
			Element element = ehcache.get(key);
			if (element != null) {
				return element.getObjectValue();
			}
		}
		return null;
	}

	/**
	 * Gets an element from the cache. Updates Element Statistics
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static List<?> getKeys(String cacheName) {
		Ehcache ehcache = getEhcache(cacheName);
		if (ehcache != null) {
			return ehcache.getKeys();
		}
		return null;
	}

	/**
	 * Put an element in the cache.
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, Object key, Object value) {
		Ehcache ehcache = getEhcache(cacheName);
		if (ehcache != null) {
			ehcache.put(new Element(key, value));
		}
	}

	/**
	 * Removes an Element from the Cache
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static boolean remove(String cacheName, Object key) {
		Ehcache ehcache = getEhcache(cacheName);
		if (ehcache != null) {
			return ehcache.remove(key);
		}
		return false;
	}

	/**
	 * Removes all cached items.
	 * 
	 * @param cacheName
	 */
	public static void removeAll(String cacheName) {
		Ehcache ehcache = getEhcache(cacheName);
		if (ehcache != null) {
			LOG.info("Removes ehcache all cached items.");
			ehcache.removeAll();
		}
	}
	
	/**
	 * Get ehcache object instance
	 * @param cacheName
	 * @return
	 */
	private static Ehcache getEhcache(String cacheName){
		return manager.getEhcache(cacheName);
	}
}
