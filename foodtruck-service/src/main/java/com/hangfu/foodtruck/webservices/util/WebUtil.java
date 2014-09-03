package com.hangfu.foodtruck.webservices.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class to make web service calls
 * 
 * @author hangfu
 * 
 */
public class WebUtil {

	private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

	/**
	 * Makes call to URL with specified YBY cookie
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String callURL(String url) throws IOException {
		log.info("url : " + url);
		String cookie = null;
		HttpURLConnection connection = null;
		BufferedReader rd = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		URL serverAddress = null;
		try {
			serverAddress = new URL(url);
			Long now = System.currentTimeMillis();
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.addRequestProperty("Cookie", cookie);
			connection.setDoOutput(true);
			connection.setReadTimeout(60000);
			rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			log.info("Time taken to execute query : " + url + " was :" + (System.currentTimeMillis() - now));
			while ((line = rd.readLine()) != null) {
				sb.append(line + '\n');
			}
		} finally {
			if (rd != null) {
				rd.close();
			}
			if (connection != null) {
				if (connection.getInputStream() != null) {
					connection.getInputStream().close();
				}
				connection.disconnect();
			}
		}

		return sb.toString();
	}
}
