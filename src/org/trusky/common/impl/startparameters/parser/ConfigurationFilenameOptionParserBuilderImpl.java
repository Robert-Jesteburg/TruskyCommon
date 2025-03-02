package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.startparameters.builder.AbstractOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.ConfigurationFilenameOptionParserBuilder;
import org.trusky.common.api.startparameters.parser.ConfigurationFilenameOptionParser;

import javax.inject.Inject;
import java.util.Optional;

public class ConfigurationFilenameOptionParserBuilderImpl extends
		AbstractOptionParserBuilder<ConfigurationFilenameOptionParserBuilder, String> implements
		ConfigurationFilenameOptionParserBuilder {

	private static final String DEFAULT_OPTION_NAME = "--configFileName";

	@Inject
	private ConfigurationFilenameOptionParserBuilderImpl() {
		this(DEFAULT_OPTION_NAME, true);
	}

	protected ConfigurationFilenameOptionParserBuilderImpl(String optionName, boolean isCaseSensitive) {
		super(optionName, isCaseSensitive);
	}

	protected ConfigurationFilenameOptionParserBuilderImpl(String optionName, boolean isCaseSensitive,
														   String defaultOptionValue) {
		super(optionName, isCaseSensitive, defaultOptionValue);
	}

	@Override
	public ConfigurationFilenameOptionParserBuilder setDefaultOptionValue(String defaultOptionValue) {
		return super.setDefaultParameterValue(defaultOptionValue);
	}

	@Override
	public ConfigurationFilenameOptionParser build() {

		Optional<String> defaultValue = getDefaultParameterValue();

		return (defaultValue.isPresent()) ? new ConfigurationFilenameOptionOptionParserImpl(getOptionName(),
				defaultValue.get()) : new ConfigurationFilenameOptionOptionParserImpl(getOptionName());
	}
}
