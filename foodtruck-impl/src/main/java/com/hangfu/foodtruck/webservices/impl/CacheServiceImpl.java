package com.hangfu.foodtruck.webservices.impl;

import java.io.IOException;
import java.net.CacheResponse;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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

	@Override
	@GET
	@Path("/build_cache")
	@Produces("application/json")
	public BaseResponse buildCache(@HeaderParam("X-YBY-Info") String ybyCookieInfo,
			@QueryParam("period") Integer cachePeriod, @QueryParam("toDate") String toDate) {

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
		return response;
	}

	private void cacheData(String dataSFResponse) throws JSONException {

		clearCache(null, null, null, null, null, null, null);

		JSONArray array = new JSONArray(dataSFResponse);
		log.info(array.length() + " food truck records from DataSF");
		int count = 0;
		for (int i = 0; i < array.length(); i++) {
			JSONObject element = array.getJSONObject(i);
			// filter out invalid records
			if (!"APPROVED".equals(element.getString("status"))) {
				continue;
			}
			FoodTruckDTO dto = new FoodTruckDTO(element);
			// filter out invalid geo location
			if (dto.getLatitude() == null || dto.getLongitude() == 0 || dto.getLongitude() == null
					|| dto.getLongitude() == 0) {
				count++;
				continue;
			}
			String idx = foodTruckDTOCache.getIndex(dto.getName(), dto.getAddress());
			// add data into foodTruckDTOCache
			foodTruckDTOCache.put(idx, dto);

			// add data into foodTypeCache
			for (FoodType type : dto.getFoodTypes()) {
				foodTypeCache.add(type.name(), dto);
			}
		}
		log.info("count = " + count);
		log.info(foodTruckDTOCache.size() + " approved foodtrucks cached in total");
	}

	@Override
	public BaseResponse clearCache(String ybyHeader, String api, String service, String dto, String metadata,
			String type, Long propertyId) {

		foodTruckDTOCache.getCacheMap().clear();
		log.info("clear cache. cache size is now " + foodTruckDTOCache.getCacheMap().size());

		return null;
	}

	// @Override
	// @GET
	// @Path("/list_cache")
	// @Produces("application/json")
	// public CacheResponse listCache(@HeaderParam("X-YBY-Info") String ybyHeader, @QueryParam("api") String inApi,
	// @QueryParam("service") String inService, @QueryParam("dto") String inDto,
	// @QueryParam("metadata") String inMetadata, @QueryParam("type") String inType) {
	// log.info("api : " + inApi + " service : " + inService + " dto : " + inDto + " type : " + inType);
	// CacheResponse response = new CacheResponse();
	// List<CacheDTO> data = new ArrayList<CacheDTO>();
	// CacheDTO cacheDTO = new CacheDTO();
	// cacheDTO.setName("foodTruckDTOCache");
	// cacheDTO.setMap((Map<Object, Object>) foodTruckDTOCache.getCacheMap());
	// data.add(cacheDTO);
	//
	// response.setData(data);
	// return response;
	// }

	@Override
	public BaseResponse logCacheSummary(String ybyHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CacheResponse listCache(String ybyHeader, String api, String service, String dto, String metadata,
			String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFoodTruckDTOCache(FoodTruckDTOCache foodTruckDTOCache) {
		this.foodTruckDTOCache = foodTruckDTOCache;
	}

	public void setFoodTypeCache(FoodTypeCache foodTypeCache) {
		this.foodTypeCache = foodTypeCache;
	}

}