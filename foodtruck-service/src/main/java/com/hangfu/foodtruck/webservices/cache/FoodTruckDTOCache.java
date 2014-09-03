/**
 * 
 */
package com.hangfu.foodtruck.webservices.cache;

import com.hangfu.foodtruck.webservices.dto.FoodTruckDTO;

/**
 * @author hangfu
 * 
 */
public class FoodTruckDTOCache extends MapCache<String, FoodTruckDTO> {

	/**
	 * Build cache index using name and address
	 * 
	 * @param name
	 * @param address
	 * @return
	 */
	public String getIndex(String name, String address) {
		if (name == null || address == null) {
			return null;
		}

		return new StringBuffer().append(name).append("|").append(address).toString();
	}
}
