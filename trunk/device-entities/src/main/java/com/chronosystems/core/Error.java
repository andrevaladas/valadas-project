/**
 * 
 */
package com.chronosystems.core;

import java.io.Serializable;

import javax.persistence.Transient;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author andrevaladas
 */
@Root
public class Error implements Serializable {

	private static final long serialVersionUID = 2524718676763740794L;

	@Transient
	@Element(required=false)
	private ErrorType type;

	@Transient
	@Element(required=false)
	private String message;

	public Error(final ErrorType type, final String message) {
		super();
		this.type = type;
		this.message = message == null || message.isEmpty() ? "This is a Very Critial ERROR! Run!!!" : message;
	}

	public ErrorType getType() {
		return type;
	}
	public void setType(final ErrorType type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(final String message) {
		this.message = message;
	}
}
