package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.startparameters.CommandLine;
import org.trusky.common.api.startparameters.EditableStartParameterContainer;
import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;
import org.trusky.common.api.startparameters.parser.ConfigurationFilenameOptionParser;
import org.trusky.common.impl.startparameters.ConfigurationFilenameOption;

import java.util.List;
import java.util.Optional;

public class ConfigurationFilenameOptionOptionParserImpl extends AbstractOptionParser<String> implements
		ConfigurationFilenameOptionParser {

	protected ConfigurationFilenameOptionOptionParserImpl(String optionName) {
		super(optionName);
	}

	protected ConfigurationFilenameOptionOptionParserImpl(String optionName, String defaultOptionValue) {
		super(optionName, defaultOptionValue);
	}

	@Override
	public void parse(CommandLine cmdLine, EditableStartParameterContainer parameterContainer)
	throws StartParameterException {

		// Should start with the option name
		if (!cmdLine.hasParameter()) {
			throw new StartParameterException("parse() was called without any parameter left.");
		}

		if (!getOptionName().equals(cmdLine.peekNext())) {
			throw new StartParameterException("Call to configuration filename parser with invalid option start '" + cmdLine.peekNext() + "'.");
		}

		// Must not exist already
		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> configFilenameOptionList =
				parameterContainer.getOption(getOptionName());
		if (!configFilenameOptionList.isEmpty()) {
			throw new StartParameterException("Configuration filename option was specified twice.");
		}

		// Get file name
		cmdLine.next();
		if (!cmdLine.hasParameter()) {
			throw new StartParameterException("Missing argument for configuration filename-Option.");
		}

		String configurationFileName = cmdLine.next();

		// Create Startoption, put into container
		parameterContainer.addStartOption(new ConfigurationFilenameOption(getOptionName(), configurationFileName));

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
				parameterContainer.addStartOption(new ConfigurationFilenameOption(getOptionName(),
						defaultParamValueOpt.get()));
			}

		}

	}

}
