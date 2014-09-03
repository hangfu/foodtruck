package com.hangfu.foodtruck.webservices.errors;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The data object represents the error code and corresponding error message that are returned as part of a response
 * object if an operation fails.
 * 
 * @author hangfu
 * 
 */
@XmlRootElement
public class ApiError {
	// Bad input params

	// Data source issues
	public static final ApiError QUERY_ERROR = new ApiError("QUERY_ERROR", "Error while querying data from data source");

	public static final ApiError NO_DATA_ERROR = new ApiError("NO_DATA_ERROR", "No data available in the cache");

	private String code; // The error code
	private String message; // The error message

	public ApiError() {
	}

	public ApiError(String code, String message) {
		this.code = code;
		this.message = message;
	}

	// Getters and Setters
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return new StringBuffer().append("code : ").append(code).append(" message : ").append(message).toString();
	}
}
