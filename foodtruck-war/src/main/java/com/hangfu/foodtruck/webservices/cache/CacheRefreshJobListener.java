package com.hangfu.foodtruck.webservices.cache;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * This listener will destroy quartz job when tomcat stops. otherwise, quartz jobs won't shut down and may cause memory
 * leak
 * 
 * @author hangfu
 * 
 */
public class CacheRefreshJobListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(CacheRefreshJobListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("Shutting down CacheRefreshJob at " + new Date());
		try {
			// Get a reference to the Scheduler and shut it down
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			Scheduler scheduler = (Scheduler) context.getBean("quartzSchedulerFactory");
			scheduler.shutdown(true);

			// Sleep for a bit so that we don't get any errors
			Thread.sleep(1000);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("CacheRefreshJob finished at " + new Date());

	}

	public void contextInitialized(ServletContextEvent arg0) {
	}
}

