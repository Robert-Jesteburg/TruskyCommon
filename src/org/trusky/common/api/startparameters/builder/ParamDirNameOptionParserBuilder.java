package org.trusky.common.api.startparameters.builder;

import org.trusky.common.api.startparameters.parser.ParamDirNameOptionParser;

public interface ParamDirNameOptionParserBuilder extends OptionParserBuilder {


	public ParamDirNameOptionParserBuilder setOptionName(String optionName);

	public ParamDirNameOptionParserBuilder setOptionNameIsCaseSensitive(boolean isCaseSensitive);

	public ParamDirNameOptionParser build();
}
