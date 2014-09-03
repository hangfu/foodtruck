package com.hangfu.foodtruck.webservices.service;

import java.util.List;

import com.hangfu.foodtruck.webservices.response.FoodTruckResponse;

/**
 * @author hangfu
 * 
 */
public interface FoodTruckService {
	public FoodTruckResponse getFoodTrucks(List<String> type);
}
