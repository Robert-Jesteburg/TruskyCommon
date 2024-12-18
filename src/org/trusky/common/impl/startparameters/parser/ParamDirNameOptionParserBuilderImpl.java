package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.startparameters.builder.AbstractOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.ParamDirNameOptionParserBuilder;
import org.trusky.common.api.startparameters.parser.ParamDirNameOptionParser;

public class ParamDirNameOptionParserBuilderImpl extends
		AbstractOptionParserBuilder<ParamDirNameOptionParserBuilder, String> implements
		ParamDirNameOptionParserBuilder {

	protected ParamDirNameOptionParserBuilderImpl(String optionName, boolean isCaseSensitive) {
		super(optionName, true);
	}


	@Override
	public ParamDirNameOptionParser build() {
		return getDefaultParameterValue().isPresent() ? new ParamDirNameOptionParserImpl(getOptionName(), true,
				getDefaultParameterValue().get()) : new ParamDirNameOptionParserImpl(getOptionName());
	}

}
