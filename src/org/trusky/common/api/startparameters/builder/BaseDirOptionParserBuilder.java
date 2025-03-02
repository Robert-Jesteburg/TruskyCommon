package org.trusky.common.api.startparameters.builder;

import org.trusky.common.api.startparameters.parser.BaseDirOptionParser;

public interface BaseDirOptionParserBuilder extends OptionParserBuilder {

	public BaseDirOptionParserBuilder setOptionName(String optionName);


	public BaseDirOptionParserBuilder setOptionNameIsCaseSensitive(boolean isCaseSensitive);

	public BaseDirOptionParserBuilder setDefaultOptionValue(String defaultOptionValue);


	public BaseDirOptionParser build();
}
