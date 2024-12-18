package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.startparameters.parser.CommandLine;
import org.trusky.common.api.startparameters.parser.EditableStartParameterContainer;
import org.trusky.common.api.startparameters.parser.StartOptionParser;

import java.util.Optional;

public abstract class AbstractOptionParser<DEFAULT_PARAM_TYPE> implements StartOptionParser {

	private final String optionName;
	private final boolean compareCaseSensitive;
	private final Optional<DEFAULT_PARAM_TYPE> defaultParamValueOpt;

	protected AbstractOptionParser(String optionName) {
		this(optionName, true);
	}

	protected AbstractOptionParser(String optionName, DEFAULT_PARAM_TYPE defaultOptionValue) {
		this(optionName, true, defaultOptionValue);
	}

	protected AbstractOptionParser(String optionName, boolean compareCaseSensitive) {

		this(optionName, compareCaseSensitive, null);
	}

	protected AbstractOptionParser(String optionName, boolean compareCaseSensitive,
								   DEFAULT_PARAM_TYPE defaultOptionValue) {

		if (optionName == null || optionName.isBlank()) { // isBlank also checks for empty string
			throw new IllegalArgumentException("Please specify a meaningful option name.");
		}

		checkDefaultOptionValueAllowed(defaultOptionValue);

		this.optionName = optionName;
		this.compareCaseSensitive = compareCaseSensitive;
		this.defaultParamValueOpt = (defaultOptionValue == null) ? Optional.empty() : Optional.of(defaultOptionValue);

	}

	/**
	 * Give implementation classes a chance to verify the optional value. Overloaed it, if (a) there is no parameter
	 * value (making the default value senseless) or (b) if any additional checks are neccessary. Throw the
	 * exception, if any condition is violated (or, in case of (a), if a parameter is != NULL is supplied).
	 *
	 * @param defaultOptionValue
	 * @throws IllegalArgumentException See explanation above
	 */
	protected void checkDefaultOptionValueAllowed(DEFAULT_PARAM_TYPE defaultOptionValue)
	throws IllegalArgumentException {
		// Does nothimg by default
	}

	protected String getOptionName() {
		return optionName;
	}

	protected Optional<DEFAULT_PARAM_TYPE> getDefaultParamValue() {
		return defaultParamValueOpt;
	}


	@Override
	public boolean canParse(CommandLine cmdLine) {
		return getOptionName().equals(cmdLine.peekNext());
	}

	@Override
	public boolean hasDefaultParameter() {
		return false;
	}

	@Override
	public void applyDefaultParameterIfNecessary(EditableStartParameterContainer parameterContainer) {
		// Does nothing by default
	}

}
