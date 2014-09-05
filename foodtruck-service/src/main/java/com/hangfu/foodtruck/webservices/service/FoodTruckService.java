package com.hangfu.foodtruck.webservices.service;

import java.util.List;

import com.hangfu.foodtruck.webservices.response.FoodTruckResponse;

/**
 * @author hangfu
 * 
 */
public interface FoodTruckService {

	/**
	 * given a list of food type, return food trucks belong to that types 
	 * if no type specified, return all food trucks
	 * 
	 * @param types
	 * @return
	 */
	public FoodTruckResponse getFoodTrucks(List<String> types);

	/**
	 * return all food types defined
	 * 
	 * @return
	 */
	public List<String> getFoodTypes();
}
