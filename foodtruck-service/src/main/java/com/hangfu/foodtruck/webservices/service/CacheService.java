package com.hangfu.foodtruck.webservices.service;

import java.net.CacheResponse;

import com.hangfu.foodtruck.webservices.response.BaseResponse;

/**
 * @author hangfu
 * 
 */
public interface CacheService {

	/**
	 * Build the cache
	 * 
	 * @param ybyHeader
	 * @param period
	 * @param toDate
	 * 
	 * @return
	 */
	public BaseResponse buildCache(String ybyHeader, Integer period, String toDate);

	/**
	 * Clear the cache
	 * 
	 * @param ybyHeader
	 * @param api
	 * @param service
	 * @param dto
	 * @param metadata
	 * @param type
	 * @param propertyId
	 * 
	 * @return
	 */
	public BaseResponse clearCache(String ybyHeader, String api, String service, String dto, String metadata,
			String type, Long propertyId);

	/**
	 * Lists the cache content
	 * 
	 * @param ybyHeader
	 * @param api
	 * @param service
	 * @param dto
	 * @param metadata
	 * @param type
	 * 
	 * @return
	 */
	public CacheResponse listCache(String ybyHeader, String api, String service, String dto, String metadata,
			String type);

	/**
	 * Simply logs the cache summary i.e. size of each map
	 * 
	 * @param ybyHeader
	 * 
	 * @return
	 */
	public BaseResponse logCacheSummary(String ybyHeader);

}
