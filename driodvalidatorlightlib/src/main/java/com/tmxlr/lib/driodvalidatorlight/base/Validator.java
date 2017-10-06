package com.tmxlr.lib.driodvalidatorlight.base;

public abstract class Validator {

	final String errorMessage;

	public Validator(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public abstract boolean isValid(String value) throws ValidatorException;

	public String getMessage() {
		return errorMessage;
	}

}
