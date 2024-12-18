package org.trusky.common.api.startparameters.builder;

import org.trusky.common.api.startparameters.parser.ConfigurationFilenameOptionParser;

public interface ConfigurationFilenameOptionParserBuilder {

	public ConfigurationFilenameOptionParserBuilder setOptionName(String optionName);


	public ConfigurationFilenameOptionParserBuilder setOptionNameIsCaseSensitive(boolean isCaseSensitive);

	public ConfigurationFilenameOptionParserBuilder setDefaultOptionValue(String defaultOptionValue);


	public ConfigurationFilenameOptionParser build();

}
