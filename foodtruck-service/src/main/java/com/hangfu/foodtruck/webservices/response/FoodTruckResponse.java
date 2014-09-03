/**
 * 
 */
package com.hangfu.foodtruck.webservices.response;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.hangfu.foodtruck.webservices.dto.FoodTruckDTO;

/**
 * @author hangfu
 * 
 */
@XmlRootElement(name = "result")
public class FoodTruckResponse extends BaseResponse {

	private Set<FoodTruckDTO> data;

	public Set<FoodTruckDTO> getData() {
		return data;
	}

	public void setData(Set<FoodTruckDTO> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "FoodTruckResponse [data=" + data + "]";
	}

}
