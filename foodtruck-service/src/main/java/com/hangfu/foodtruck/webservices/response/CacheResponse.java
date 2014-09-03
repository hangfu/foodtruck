package com.hangfu.foodtruck.webservices.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hangfu.foodtruck.webservices.dto.CacheDTO;
import com.hangfu.foodtruck.webservices.service.CacheService;

/**
 * This class represents the response of {@link CacheService} for cache related request
 * 
 * @author hangfu
 * 
 */
@XmlRootElement(name = "result")
public class CacheResponse extends BaseResponse {
	List<CacheDTO> data; // Collection of data object returned as response to the request

	// Getters and Setters
	@XmlElement(name = "cacheData")
	public List<CacheDTO> getData() {
		return data;
	}

	public void setData(List<CacheDTO> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return new StringBuffer().append("CacheResponse = [").append(" data : ").append(data).append(super.toString())
				.append("]").toString();
	}
}