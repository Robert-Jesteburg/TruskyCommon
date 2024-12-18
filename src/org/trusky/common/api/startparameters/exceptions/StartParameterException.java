package org.trusky.common.api.startparameters.exceptions;

public class StartParameterException extends Exception {

	public StartParameterException(String message) {
		super(message);
	}

	public StartParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public StartParameterException(Throwable cause) {
		super(cause);
	}

	public StartParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
