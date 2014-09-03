package com.hangfu.foodtruck.webservices.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.hangfu.foodtruck.webservices.enums.FoodType;

/**
 * @author hangfu
 * 
 */
@XmlRootElement
public class FoodTruckDTO {

	private String name;
	private double longitude;
	private double latitude;
	private String address;
	private String locationDescription;
	private String schedule;
	private String foodItems;
	private List<FoodType> foodTypes;

	public FoodTruckDTO() {

	}

	public FoodTruckDTO(JSONObject obj) throws JSONException {
		this.name = obj.getString("applicant");
		if (obj.has("longitude")) {
			this.longitude = Double.valueOf(obj.getString("longitude"));
		}
		if (obj.has("latitude")) {
			this.latitude = Double.valueOf(obj.getString("latitude"));
		}
		if (obj.has("address")) {
			this.address = obj.getString("address");
		}
		if (obj.has("locationdescription")) {
			this.locationDescription = obj.getString("locationdescription");
		}
		if (obj.has("schedule")) {
			this.schedule = obj.getString("schedule");
		}
		if (obj.has("fooditems")) {
			this.foodItems = obj.getString("fooditems");
		}
		if (obj.has("fooditems")) {
			this.foodTypes = getFoodTypeTags(obj.getString("fooditems"));
		}
	}

	private List<FoodType> getFoodTypeTags(String str) {
		List<FoodType> types = new ArrayList<FoodType>();
		str = str.toUpperCase();
		for (FoodType type : FoodType.values()) {
			if (str.contains(type.name())) {
				types.add(type);
			}
		}
		return types;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(String foodItems) {
		this.foodItems = foodItems;
	}

	public List<FoodType> getFoodTypes() {
		return foodTypes;
	}

	public void setFoodTypes(List<FoodType> foodTypes) {
		this.foodTypes = foodTypes;
	}

	@Override
	public String toString() {
		return "FoodTruckDTO [name=" + name + ", longitude=" + longitude + ", latitude=" + latitude + ", address="
				+ address + ", locationDescription=" + locationDescription + ", schedule=" + schedule + ", foodItems="
				+ foodItems + ", foodTypes=" + foodTypes + "]";
	}

}
