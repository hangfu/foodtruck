/**
 * 
 */
package com.hangfu.foodtruck.webservices.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hangfu.foodtruck.webservices.cache.FoodTruckDTOCache;
import com.hangfu.foodtruck.webservices.cache.FoodTypeCache;
import com.hangfu.foodtruck.webservices.dto.FoodTruckDTO;
import com.hangfu.foodtruck.webservices.enums.FoodType;
import com.hangfu.foodtruck.webservices.enums.OperationResult;
import com.hangfu.foodtruck.webservices.errors.ApiError;
import com.hangfu.foodtruck.webservices.response.FoodTruckResponse;
import com.hangfu.foodtruck.webservices.service.FoodTruckService;
import com.hangfu.foodtruck.webservices.util.CollectionUtil;

/**
 * @author hangfu
 * 
 */
@Service
@Path("/foodtruck")
public class FoodTruckServiceImpl implements FoodTruckService {

	private static final Logger log = LoggerFactory.getLogger(FoodTruckServiceImpl.class);

	@Autowired
	private FoodTruckDTOCache foodTruckDTOCache;

	@Autowired
	private FoodTypeCache foodTypeCache;

	@Override
	@GET
	@Produces("application/json")
	public FoodTruckResponse getFoodTrucks(@QueryParam("type") List<String> types) {

		log.info("types = " + types);

		FoodTruckResponse response = new FoodTruckResponse();
		Set<FoodTruckDTO> data = new HashSet<FoodTruckDTO>();

		// no type specified, query for all data
		if (CollectionUtil.isNullOrEmpty(types)) {
			if (foodTruckDTOCache.getCacheMap() == null || foodTruckDTOCache.size() == 0) {
				log.info("no data in cache");
				response.setOperationResult(OperationResult.Failure);
				response.addError(ApiError.NO_DATA_ERROR);
				return response;
			}

			data.addAll(foodTruckDTOCache.getCacheMap().values());
			log.info(foodTruckDTOCache.size() + " entries in foodTruckDTOCache");
			response.setData(data);
			return response;
		}

		for (String type : types) {
			type = type.toUpperCase();
			data.addAll(foodTypeCache.get(type));
		}
		response.setData(data);
		return response;
	}

	@GET
	@Path("foodtypes")
	@Produces("application/json")
	public List<String> getFoodTypes() {
		List<String> list = new ArrayList<String>();
		for (FoodType type : FoodType.values()) {
			list.add(type.name());
		}
		return list;
	}
}
