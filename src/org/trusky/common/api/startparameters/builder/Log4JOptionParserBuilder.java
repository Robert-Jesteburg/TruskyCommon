package org.trusky.common.api.startparameters.builder;

import org.trusky.common.api.startparameters.parser.Log4JOptionParser;

public interface Log4JOptionParserBuilder {

	public Log4JOptionParserBuilder setOptionName(String optionName);

	public Log4JOptionParserBuilder setOptionNameIsCaseSensitive(boolean isCaseSensitive);

	public Log4JOptionParserBuilder setDefaultParameterValue(String defaultValue);

	public Log4JOptionParser build();

}
