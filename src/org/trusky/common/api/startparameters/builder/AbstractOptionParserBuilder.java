package org.trusky.common.api.startparameters.builder;

import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.util.CommonStringUtilities;

import java.util.Optional;

/**
 * This abstract class is made public to support creating builders for parsers. It supports some basic checks on
 * option names.
 */
public abstract class AbstractOptionParserBuilder<BUILDER, DEFAULT_PARAM_TYPE> {

	private final CommonStringUtilities commonStringUtilities;
	private String optionName;
	private boolean isCaseSensitive;
	private Optional<DEFAULT_PARAM_TYPE> defaultPameterValueOpt = Optional.empty();

	protected AbstractOptionParserBuilder(String optionName, boolean isCaseSensitive) {
		this(optionName, isCaseSensitive, null);
	}

	protected AbstractOptionParserBuilder(String optionName, boolean isCaseSensitive,
										  DEFAULT_PARAM_TYPE defaultOptionValue) {
		commonStringUtilities = InjectorFactory.getInjector()
				.getInstance(CommonStringUtilities.class);

		checkOptionName(optionName);

		this.optionName = optionName;
		this.isCaseSensitive = isCaseSensitive;
		this.defaultPameterValueOpt = (defaultOptionValue == null) ? Optional.empty() :
				Optional.of(defaultOptionValue);
	}

	/**
	 * Checks general requirements for option names and throws an IllegalArgumentExceptions if cretierias aer not met.
	 *
	 * @param optionName The option name
	 * @throws IllegalArgumentException If the option name is illegal
	 */
	protected void checkOptionName(String optionName) throws IllegalArgumentException {

		if (commonStringUtilities.isNullOrEmpty(optionName)) {
			throw new IllegalArgumentException("The option name must not be NULL ode empty.");
		}

		if (commonStringUtilities.isWhitespaceOnly(optionName)) {
			throw new IllegalArgumentException("The option name must not consist of whitespaces.");
		}

		if (optionName.startsWith(" ") || optionName.startsWith("\t")) {
			throw new IllegalArgumentException("The option name must not start with a white space.");
		}
	}

	@SuppressWarnings("unchecked")
	public BUILDER setOptionName(String optionName) {

		checkOptionName(optionName);
		this.optionName = optionName;
		return (BUILDER) this;
	}

	@SuppressWarnings("unchecked")
	public BUILDER setOptionNameIsCaseSensitive(boolean isCaseSensitive) {
		this.isCaseSensitive = isCaseSensitive;
		return (BUILDER) this;
	}

	@SuppressWarnings("unchecked")
	protected BUILDER setDefaultParameterValue(DEFAULT_PARAM_TYPE defaultParameterValue) {
		this.defaultPameterValueOpt = (defaultParameterValue == null) ? Optional.empty() :
				Optional.of(defaultParameterValue);
		return (BUILDER) this;
	}

	public String getOptionName() {
		return optionName;
	}

	protected boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	protected Optional<DEFAULT_PARAM_TYPE> getDefaultParameterValue() {
		return defaultPameterValueOpt;
	}
}
