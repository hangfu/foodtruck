package com.hangfu.foodtruck.webservices.cache;

/**
 * @author hangfu
 * 
 */
public interface Cache<E, V> {
	/**
	 * Get cache object at given index
	 * 
	 * @param idx
	 * @return
	 */
	public Object get(E idx);

	/**
	 * Put cache object at given index
	 * 
	 * @param idx
	 * @param obj
	 */
	public void put(E idx, V obj);

	/**
	 * Determines if cache contains the said index
	 * 
	 * @return
	 */
	public boolean containsIndex(E idx);

	/**
	 * Clear the whole cache
	 */
	public void clear();

	/**
	 * Validate the object at given index
	 * 
	 * @param idx
	 * @return
	 */
	public boolean isValid(E idx);

	public int size();

	/**
	 * Deserialize the cache
	 */
	public void load();

	/**
	 * Serialize the cache
	 */
	public void save();

}
