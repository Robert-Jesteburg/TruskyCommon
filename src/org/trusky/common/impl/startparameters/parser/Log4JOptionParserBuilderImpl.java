package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.startparameters.builder.AbstractOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.Log4JOptionParserBuilder;
import org.trusky.common.api.startparameters.parser.Log4JOptionParser;

import javax.inject.Inject;

public class Log4JOptionParserBuilderImpl extends
		AbstractOptionParserBuilder<Log4JOptionParserBuilder, String> implements Log4JOptionParserBuilder {

	private static final String DEFAULT_OPTION_NAME = "--log4J";
	private static final String DEFAULT_CONFIG_FILENAME = "log4J-template.xml";

	@Inject
	private Log4JOptionParserBuilderImpl() {
		this(DEFAULT_OPTION_NAME);
	}

	Log4JOptionParserBuilderImpl(String optionName) {
		super(optionName, true);
		setDefaultParameterValue(DEFAULT_CONFIG_FILENAME);
	}

	@Override
	public Log4JOptionParserBuilder setDefaultParameterValue(String defaultValue) {
		return super.setDefaultParameterValue(defaultValue);
	}

	@Override
	public Log4JOptionParser build() {

		return getDefaultParameterValue().isEmpty() ?  new Log4JOptionParserImpl(getOptionName()) :
				new Log4JOptionParserImpl(getOptionName(), getDefaultParameterValue().get());
	}
}
