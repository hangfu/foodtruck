package com.hangfu.foodtruck.webservices.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hangfu
 * 
 */
@XmlRootElement
public class CacheDTO {
	private String name;
	private Map<Object, Object> map;

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Object, Object> getMap() {
		return map;
	}

	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("name : ").append(name).append(" map : ").append(map).toString();
	}
}
