package com.hangfu.foodtruck.webservices.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author hangfu
 * 
 */
public class CollectionUtil {
	/**
	 * Builds a csv string from an array of elements
	 * 
	 * @param <E>
	 * @param array
	 * @return
	 */
	public static <E> String arraytoCsv(E[] array) {
		if (array == null || array.length == 0) {
			return null;
		}

		// Build the csv string
		StringBuffer sb = new StringBuffer();
		for (E element : array) {
			sb.append(element);
			sb.append(",");
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	/**
	 * Builds a csv string from a list of elements
	 * 
	 * @param <E>
	 * @param set
	 * @return
	 */
	public static <E> String setToCsv(Set<E> set) {
		if (set == null || set.size() == 0) {
			return null;
		}

		// Build the csv string
		StringBuffer sb = new StringBuffer();
		for (E element : set) {
			sb.append(element);
			sb.append(",");
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

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
