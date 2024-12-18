package org.trusky.common.api.startparameters.builder;

import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.util.StringUtilities;

import java.util.Optional;

/**
 * This abstract class is made public to support creating builders for parsers. It supports some basic checks on
 * option names.
 */
public abstract class AbstractOptionParserBuilder<BUILDER, DEFAULT_PARAM_TYPE> {

	private String optionName;
	private boolean isCaseSensitive;
	private Optional<DEFAULT_PARAM_TYPE> defaultPameterValueOpt = Optional.empty();

	private final StringUtilities stringUtilities;

	protected AbstractOptionParserBuilder(String optionName, boolean isCaseSensitive) {
		this(optionName, isCaseSensitive, null);
	}

	protected AbstractOptionParserBuilder(String optionName, boolean isCaseSensitive,
										  DEFAULT_PARAM_TYPE defaultOptionValue) {
		stringUtilities = InjectorFactory.getInjector()
				.getInstance(StringUtilities.class);

		checkOptionName(optionName);

		this.optionName = optionName;
		this.isCaseSensitive = isCaseSensitive;
		this.defaultPameterValueOpt = (defaultOptionValue == null) ? Optional.empty() :
				Optional.of(defaultOptionValue);
	}

	/**
	 * Checks general requirements for option names and throws an IllegalArgumentExceptions if cretierias aer not met.
	 *
	 * @param optionName
	 * @throws IllegalArgumentException
	 */
	protected void checkOptionName(String optionName) throws IllegalArgumentException {

		if (stringUtilities.isNullOrEmpty(optionName)) {
			throw new IllegalArgumentException("The option name must not be NULL ode empty.");
		}

		if (stringUtilities.isWhitespaceOnly(optionName)) {
			throw new IllegalArgumentException("The option name must not consist of whitespaces.");
		}

		if (optionName.startsWith(" ") || optionName.startsWith("\t")) {
			throw new IllegalArgumentException("The option name must not start with a white space.");
		}
	}

	public BUILDER setOptionName(String optionName) {

		checkOptionName(optionName);
		this.optionName = optionName;
		return (BUILDER) this;
	}

	public BUILDER setOptionNameIsCaseSensitive(boolean isCaseSensitive) {
		this.isCaseSensitive = isCaseSensitive;
		return (BUILDER) this;
	}

	protected BUILDER setDefaultParameterValue(DEFAULT_PARAM_TYPE defaultParameterValue) {
		this.defaultPameterValueOpt = (defaultParameterValue == null) ? Optional.empty() :
				Optional.of(defaultParameterValue);
		return (BUILDER) this;
	}

	protected String getOptionName() {
		return optionName;
	}

	protected boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	protected Optional<DEFAULT_PARAM_TYPE> getDefaultParameterValue() {
		return defaultPameterValueOpt;
	}
}
