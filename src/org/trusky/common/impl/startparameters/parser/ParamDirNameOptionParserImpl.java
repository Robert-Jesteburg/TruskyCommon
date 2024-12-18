package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;
import org.trusky.common.api.startparameters.parser.CommandLine;
import org.trusky.common.api.startparameters.parser.EditableStartParameterContainer;
import org.trusky.common.api.startparameters.parser.ParamDirNameOptionParser;
import org.trusky.common.impl.startparameters.ParamDirNameOption;

import java.util.List;
import java.util.Optional;

public class ParamDirNameOptionParserImpl extends AbstractOptionParser<String> implements ParamDirNameOptionParser {

	protected ParamDirNameOptionParserImpl(String optionName) {
		this(optionName, true);
	}

	protected ParamDirNameOptionParserImpl(String optionName, boolean compareCaseSensitive) {
		super(optionName, compareCaseSensitive);
	}

	protected ParamDirNameOptionParserImpl(String optionName, boolean compareCaseSensitive,
										   String defaultParameterValue) {
		super(optionName, compareCaseSensitive, defaultParameterValue);
	}

	@Override
	public void parse(CommandLine cmdLine, EditableStartParameterContainer parameterContainer)
	throws StartParameterException {

		// Should start with the option name
		if (!cmdLine.hasParameter()) {
			throw new StartParameterException("parse() was called without any parameter left.");
		}

		if (!getOptionName().equals(cmdLine.peekNext())) {
			throw new StartParameterException("Call to ParamDirParser parser with invalid option start '" + cmdLine.peekNext() + "'.");
		}

		// Must not exist already
		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> paramDirNameOptionList =
				parameterContainer.getOption(getOptionName());
		if (!paramDirNameOptionList.isEmpty()) {
			throw new StartParameterException("ParamDirParser option was specified twice.");
		}

		// Get file name
		cmdLine.next();
		if (!cmdLine.hasParameter()) {
			throw new StartParameterException("Missing argument for ParamDirParser-Option.");
		}

		String dirName = cmdLine.next();

		// Create Startoption, put into container
		parameterContainer.addStartOption(new ParamDirNameOption(getOptionName(), dirName));

		// Has no further sub parameters
		return;

	}

	@Override
	public boolean hasDefaultParameter() {
		return getDefaultParamValue().isPresent();
	}

	@Override
	public void applyDefaultParameterIfNecessary(EditableStartParameterContainer parameterContainer) {

		if (parameterContainer.getOption(getOptionName())
				.isEmpty()) {

			// This option requires a value, so set it if a default value was given
			Optional<String> defaultParamValueOpt = getDefaultParamValue();
			if (defaultParamValueOpt.isPresent()) {
				parameterContainer.addStartOption(new ParamDirNameOption(getOptionName(), defaultParamValueOpt.get()));
			}

		}

	}
}
