package org.trusky.common.api.startparameters.builder;

import org.trusky.common.api.startparameters.parser.Log4JOptionParser;

public interface Log4JOptionParserBuilder extends OptionParserBuilder {

	Log4JOptionParserBuilder setOptionName(String optionName);

	Log4JOptionParserBuilder setOptionNameIsCaseSensitive(boolean isCaseSensitive);

	Log4JOptionParserBuilder setDefaultParameterValue(String defaultValue);

	Log4JOptionParser build();

}
