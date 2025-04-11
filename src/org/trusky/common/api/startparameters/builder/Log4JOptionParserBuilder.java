package org.trusky.common.api.startparameters.builder;

import org.trusky.common.api.startparameters.parser.Log4JOptionParser;

public interface Log4JOptionParserBuilder extends OptionParserBuilder {

	String DEFAULT_LOG4J_CONFIGURATION_NAME = "log4J-configuration.xml";

	Log4JOptionParserBuilder setOptionName(String optionName);

	Log4JOptionParserBuilder setOptionNameIsCaseSensitive(boolean isCaseSensitive);

	Log4JOptionParserBuilder setDefaultParameterValue(String defaultValue);

	Log4JOptionParser build();

}
