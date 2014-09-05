package com.hangfu.foodtruck.webservices.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author hangfu
 * 
 */
public class CollectionUtil {

	/**
	 * Utility method to check if a collection is null or empty
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNullOrEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty())
			return true;
		return false;
	}

	/**
	 * Utility method to check if a map is null or empty
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isNullOrEmpty(Map<?, ?> map) {
		if (map == null || map.isEmpty())
			return true;
		return false;
	}
}
