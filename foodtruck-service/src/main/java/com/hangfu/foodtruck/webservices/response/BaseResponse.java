package com.hangfu.foodtruck.webservices.response;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.hangfu.foodtruck.webservices.enums.OperationResult;
import com.hangfu.foodtruck.webservices.errors.ApiError;
import com.hangfu.foodtruck.webservices.util.CollectionUtil;

/**
 * This class represents the web service' base response. The APIs responds with their respective derivation of this base
 * class
 * 
 * @author hangfu
 * 
 */
@XmlRootElement(name = "result")
public class BaseResponse {
	private Set<ApiError> errors; // List of errors on operation failure, empty list on success
	private OperationResult operationResult; // The operation result signifying the response status

	public BaseResponse() {
		operationResult = OperationResult.Success;
	}

	// Getters and setters
	public OperationResult getOperationResult() {
		return operationResult;
	}

	public void setOperationResult(OperationResult operationResult) {
		this.operationResult = operationResult;
	}

	public Set<ApiError> getErrors() {
		return errors;
	}

	public void setErrors(Set<ApiError> errors) {
		this.errors = errors;
	}

	public void setErrors(ApiError[] errors) {
		if (this.errors == null) {
			this.errors = new LinkedHashSet<ApiError>();
		}
		this.errors.clear();
		this.errors.addAll(Arrays.asList(errors));
	}

	public void addError(ApiError error) {
		if (this.errors == null) {
			this.errors = new LinkedHashSet<ApiError>();
		}
		if (error != null) {
			this.errors.add(error);
		}
	}

	public void addErrors(Set<ApiError> errors) {
		if (CollectionUtil.isNullOrEmpty(errors)) {
			return;
		}
		if (this.errors == null) {
			this.errors = new LinkedHashSet<ApiError>();
		}
		if (errors != null) {
			this.errors.addAll(errors);
		}
	}

	@Override
	public String toString() {
		return new StringBuffer().append(" errors : ").append(errors).append(" operationResult : ")
				.append(operationResult).toString();
	}
}
