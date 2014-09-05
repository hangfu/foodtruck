package com.hangfu.foodtruck.webservices.pool;

import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hangfu.foodtruck.webservices.service.CacheService;

/**
 * This class is responsible for caching the data during bootstrapping. This slows down the webapp init
 * 
 * @author hangfu
 * 
 */
public class DataCachingThread extends TimerTask {
	private static final Logger log = LoggerFactory.getLogger(DataCachingThread.class);

	@Autowired
	private CacheService cacheService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		log.info("DataCachingThread.run() invoked at " + new Date());

		cacheService.buildCache();
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
}
