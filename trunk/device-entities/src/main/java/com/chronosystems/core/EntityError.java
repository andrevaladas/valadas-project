package com.chronosystems.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class EntityError implements Serializable {

	private static final long serialVersionUID = -7789051059298620661L;

	@ElementList(entry="errors", inline=true, required=false)
	private List<Error> errors;

	public List<Error> getErrors() {
		if (errors == null) {
			errors = new ArrayList<Error>();
		}
		return errors;
	}
	public void setErrors(final List<Error> errors) {
		this.errors = errors;
	}

	public boolean hasErrors() {
		return !getErrors().isEmpty();
	}
	public void addError(final String error) {
		getErrors().add(new Error(ErrorType.ERROR, error));
	}
	public void addWarning(final String warning) {
		getErrors().add(new Error(ErrorType.WARNING, warning));
	}
	public void addInfo(final String info) {
		getErrors().add(new Error(ErrorType.INFO, info));
	}
	public void addAlert(final String alert) {
		getErrors().add(new Error(ErrorType.ALERT, alert));
	}
	public void addSuccessMessage(final String success) {
		getErrors().add(new Error(ErrorType.SUCCESS, success));
	}
	public String getFullErrors() {
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < getErrors().size(); i++) {
			final Error error = getErrors().get(i);
			if (!result.toString().isEmpty()) {
				result.append("\n");
			}
			result.append(error.getMessage());
		}
		return result.toString();
	}
}
