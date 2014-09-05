package com.hangfu.foodtruck.webservices.impl;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hangfu.foodtruck.webservices.cache.FoodTruckDTOCache;
import com.hangfu.foodtruck.webservices.cache.FoodTypeCache;
import com.hangfu.foodtruck.webservices.cache.MapCache;
import com.hangfu.foodtruck.webservices.dto.FoodTruckDTO;
import com.hangfu.foodtruck.webservices.enums.FoodType;
import com.hangfu.foodtruck.webservices.enums.OperationResult;
import com.hangfu.foodtruck.webservices.errors.ApiError;
import com.hangfu.foodtruck.webservices.response.BaseResponse;
import com.hangfu.foodtruck.webservices.service.CacheService;
import com.hangfu.foodtruck.webservices.util.WebUtil;

/**
 * The web service API exposes non-functional methods to end user like cache management.
 * 
 * @author hangfu
 * 
 */
@Service
@Path("/cache")
public class CacheServiceImpl implements CacheService {

	private static final Logger log = LoggerFactory.getLogger(CacheServiceImpl.class);

	private static String SF_FOOD_DATA_URL = "http://data.sfgov.org/resource/rqzj-sfat.json";

	@Autowired
	private FoodTruckDTOCache foodTruckDTOCache;

	@Autowired
	private FoodTypeCache foodTypeCache;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.service.CacheService#buildCache()
	 */
	@Override
	@GET
	@Path("/build_cache")
	@Produces("application/json")
	public BaseResponse buildCache() {

		BaseResponse response = new BaseResponse();
		response.setOperationResult(OperationResult.Success);

		// get data from DataSF
		String dataSFResponse = null;
		try {
			dataSFResponse = WebUtil.callURL(SF_FOOD_DATA_URL);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			response.addError(ApiError.QUERY_ERROR);
			response.setOperationResult(OperationResult.Failure);
			return response;
		}

		if (dataSFResponse == null) {
			log.error("no response data from DataSF");
			response.addError(ApiError.QUERY_ERROR);
			response.setOperationResult(OperationResult.Failure);
			return response;
		}

		try {
			cacheData(dataSFResponse);
		} catch (JSONException e) {
			log.error(e.getMessage(), e);
			response.addError(ApiError.QUERY_ERROR);
			response.setOperationResult(OperationResult.Failure);
			return response;
		}

		log.info("Completed cache build");
		logCacheSummary();

		return response;
	}

	/**
	 * cache data into foodTruckDTOCache and foodTypeCache
	 * 
	 * @param dataSFResponse
	 * @throws JSONException
	 */
	private void cacheData(String dataSFResponse) throws JSONException {

		clearCache();

		JSONArray array = new JSONArray(dataSFResponse);
		log.info(array.length() + " food truck records from DataSF");
		for (int i = 0; i < array.length(); i++) {
			JSONObject element = array.getJSONObject(i);
			// filter out invalid records
			if (!"APPROVED".equals(element.getString("status"))) {
				continue;
			}
			FoodTruckDTO dto = new FoodTruckDTO(element);
			// filter out record with invalid geo location
			if (dto.getLatitude() == null || dto.getLongitude() == 0 || dto.getLongitude() == null
					|| dto.getLongitude() == 0) {
				continue;
			}
			// add data into foodTruckDTOCache
			String idx = foodTruckDTOCache.getIndex(dto.getName(), dto.getAddress());
			foodTruckDTOCache.put(idx, dto);

			// add data into foodTypeCache
			for (FoodType type : dto.getFoodTypes()) {
				foodTypeCache.add(type.name(), dto);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.service.CacheService#clearCache()
	 */
	@Override
	@GET
	@Path("/clear_cache")
	@Produces("application/json")
	public BaseResponse clearCache() {

		BaseResponse response = new BaseResponse();

		foodTruckDTOCache.clear();
		log.info("clear foodTruckDTOCache. cache size is now " + foodTruckDTOCache.size());

		foodTypeCache.clear();
		log.info("clear foodTypeCache. cache size is now " + foodTypeCache.size());

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hangfu.foodtruck.webservices.service.CacheService#logCacheSummary()
	 */
	@Override
	@GET
	@Path("/log_cache_summary")
	@Produces("application/json")
	public BaseResponse logCacheSummary() {
		BaseResponse response = new BaseResponse();

		// Just output the length of each for output purpose
		logCache("foodTruckDTOCache", foodTruckDTOCache);
		logCache("foodTypeCache", foodTypeCache);

		return response;
	}

	/**
	 * Log the size of cache
	 * 
	 * @param name
	 * @param cache
	 */
	private void logCache(String name, MapCache<?, ?> cache) {
		log.info(name + " cache has " + (cache != null && cache.getCacheMap() != null ? cache.size() : null)
				+ " entries");
	}

	public void setFoodTruckDTOCache(FoodTruckDTOCache foodTruckDTOCache) {
		this.foodTruckDTOCache = foodTruckDTOCache;
	}

	public void setFoodTypeCache(FoodTypeCache foodTypeCache) {
		this.foodTypeCache = foodTypeCache;
	}

}