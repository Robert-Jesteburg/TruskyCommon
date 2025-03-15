package org.trusky.common.api.startparameters.exceptions;

public class InvalidOptionException extends StartParameterException {

	private final String invalidOption;

	public InvalidOptionException(String message, String invalidOption) {
		super(message);
		this.invalidOption = invalidOption;
	}

	@SuppressWarnings("unused")
	public InvalidOptionException(String message, Throwable cause, String invalidOption) {
		super(message, cause);
		this.invalidOption = invalidOption;
	}

	@SuppressWarnings("unused")
	public InvalidOptionException(Throwable cause, String invalidOption) {
		super(cause);
		this.invalidOption = invalidOption;
	}

	@SuppressWarnings("unused")
	public InvalidOptionException(String message, Throwable cause, String invalidOption, boolean enableSuppression,
								  boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.invalidOption = invalidOption;
	}

	public String getInvalidOptionName() {
		return invalidOption;
	}
}
