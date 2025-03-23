package org.trusky.common.impl.startparameters;

import org.trusky.common.api.startparameters.AbstractStartOption;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;

/**
 * Get the name (without the path) for the configuration file for log4J.
 */
public class Log4JStartOption extends AbstractStartOption<StringOptionValue, StringOptionValue> {

	private final static String DEFAULT_OPTION_NAME = "--log4JConfigurationFile";

	public Log4JStartOption() {
		this(DEFAULT_OPTION_NAME);
	}

	private Log4JStartOption(String optionName) {
		super(optionName);
	}

	public Log4JStartOption(String optionName, String defaultValue) {
		super(optionName, new StringOptionValue(defaultValue));
	}

}
