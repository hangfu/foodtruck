/**
 * 
 */
package com.hangfu.foodtruck.webservices.cache;

import java.util.ArrayList;
import java.util.List;

import com.hangfu.foodtruck.webservices.dto.FoodTruckDTO;

/**
 * @author hangfu
 * 
 */
public class FoodTypeCache extends MapCache<String, List<FoodTruckDTO>> {

	/**
	 * add an entry
	 * 
	 * @param key
	 * @param dto
	 */
	public void add(String key, FoodTruckDTO dto) {
		if (!containsIndex(key)) {
			put(key, new ArrayList<FoodTruckDTO>());
		}
		get(key).add(dto);
	}
}
