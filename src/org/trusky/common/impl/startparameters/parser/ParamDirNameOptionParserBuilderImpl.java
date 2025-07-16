package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.startparameters.builder.AbstractOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.ParamDirNameOptionParserBuilder;
import org.trusky.common.api.startparameters.parser.ParamDirNameOptionParser;

import javax.inject.Inject;

public class ParamDirNameOptionParserBuilderImpl extends
		AbstractOptionParserBuilder<ParamDirNameOptionParserBuilder, String> implements
		ParamDirNameOptionParserBuilder {

	private static final String DEFAULT_OPTION_NAME = "--paramDir";

	@Inject
	private ParamDirNameOptionParserBuilderImpl() {
		this(DEFAULT_OPTION_NAME, true);
	}

	protected ParamDirNameOptionParserBuilderImpl(String optionName, boolean isCaseSensitive) {
		super(optionName, true);
	}


	@Override
	public ParamDirNameOptionParserBuilder setDefaultValue(String defaultValue) {
		super.setDefaultParameterValue(defaultValue);
		return this;
	}

	@Override
	public ParamDirNameOptionParser build() {
		return getDefaultParameterValue().isPresent() ? new ParamDirNameOptionParserImpl(getOptionName(), true,
				getDefaultParameterValue().get()) : new ParamDirNameOptionParserImpl(getOptionName());
	}

}
