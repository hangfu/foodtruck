/**
 * 
 */
package com.hangfu.foodtruck.webservices.enums;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The OperationResult data object represents the result of an operation.
 * 
 * @author hangfu
 * 
 */
@XmlRootElement
public enum OperationResult {
	Success, PartialSuccess, Failure
}
