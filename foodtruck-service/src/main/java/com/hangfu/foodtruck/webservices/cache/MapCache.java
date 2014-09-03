package com.hangfu.foodtruck.webservices.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author hangfu
 * 
 */
public abstract class MapCache<E, V> implements Cache<E, V> {
	Map<E, V> cacheMap;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.cache.Cache#get(java.lang.Object)
	 */
	@Override
	public V get(E idx) {
		return cacheMap.get(idx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(E idx, V obj) {
		cacheMap.put(idx, obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.cache.Cache#containsIndex(java.lang.Object)
	 */
	@Override
	public boolean containsIndex(E idx) {
		return cacheMap.containsKey(idx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.cache.Cache#clear()
	 */
	@Override
	public void clear() {
		cacheMap.clear();
	}

	/**
	 * Clear the null entries from cache
	 */
	public void clearNull() {
		List<Object> keysToBeRemoved = new ArrayList<Object>();
		for (Entry<E, V> entry : cacheMap.entrySet()) {
			Object value = entry.getValue();
			if (value == null) {
				keysToBeRemoved.add(entry.getKey());
			}
		}
		for (Object key : keysToBeRemoved) {
			cacheMap.remove(key);
		}
	}

	/**
	 * Get map of null entries
	 * 
	 * @return
	 */
	public Map<E, V> getNulls() {
		Map<E, V> map = new HashMap<E, V>();
		for (Entry<E, V> entry : cacheMap.entrySet()) {
			V value = entry.getValue();
			if (value == null) {
				map.put(entry.getKey(), value);
			}
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.cache.Cache#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(E idx) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.cache.Cache#size()
	 */
	@Override
	public int size() {
		return cacheMap.size();
	}

	// Getters and Setters
	public Map<E, V> getCacheMap() {
		return cacheMap;
	}

	public void setCacheMap(Map<E, V> cacheMap) {
		this.cacheMap = Collections.synchronizedMap(cacheMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.cache.Cache#load()
	 */
	@Override
	public void load() {
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.cache.Cache#save()
	 */
	@Override
	public void save() {
		return;
	}
}
