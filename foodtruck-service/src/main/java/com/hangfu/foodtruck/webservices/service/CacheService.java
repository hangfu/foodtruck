package com.hangfu.foodtruck.webservices.service;

import com.hangfu.foodtruck.webservices.response.BaseResponse;

/**
 * @author hangfu
 * 
 */
public interface CacheService {

	/**
	 * Build the cache
	 * 
	 * @return
	 */
	public BaseResponse buildCache();

	/**
	 * Clear the cache
	 * 
	 * @return
	 */
	public BaseResponse clearCache();

	/**
	 * Simply logs the cache summary i.e. size of each map
	 * 
	 * @return
	 */
	public BaseResponse logCacheSummary();

}
