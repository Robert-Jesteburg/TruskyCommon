package org.trusky.common.api.startparameters.exceptions;

public class InvalidOptionException extends StartParameterException {

	public InvalidOptionException(String message) {
		super(message);
	}

	public InvalidOptionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidOptionException(Throwable cause) {
		super(cause);
	}

	public InvalidOptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
