package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.startparameters.CommandLine;
import org.trusky.common.api.startparameters.EditableStartParameterContainer;
import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;
import org.trusky.common.api.startparameters.parser.Log4JOptionParser;
import org.trusky.common.impl.startparameters.Log4JStartOption;

import java.util.List;

public class Log4JOptionParserImpl extends AbstractOptionParser<String> implements Log4JOptionParser {


	public Log4JOptionParserImpl() {
		this("--log4JConfigurationFile");
	}

	public Log4JOptionParserImpl(String optionName) {
		super(optionName);
	}


	@Override
	public void parse(CommandLine cmdLine, EditableStartParameterContainer parameterContainer)
	throws StartParameterException {

		// Should start with the option name
		if (!cmdLine.hasParameter()) {
			throw new StartParameterException("parse() was called without any parameter left.");
		}

		if (!getOptionName().equals(cmdLine.peekNext())) {
			throw new StartParameterException("Call to Log4J parser with invalid option start '" + cmdLine.peekNext() + "'.");
		}

		// Must not exist already
		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> log4JOptionList =
				parameterContainer.getOption(getOptionName());
		if (!log4JOptionList.isEmpty()) {
			throw new StartParameterException("Log4J option was specified twice.");
		}

		// Get file name
		cmdLine.next();
		if (!cmdLine.hasParameter()) {
			throw new StartParameterException("Missing argument for Log4J-Option.");
		}

		String fileName = cmdLine.next();
		if (fileName.matches(".*[\\\\/].*")) {
			throw new StartParameterException("File name for Log4J configuration file must not contain path " +
					"delimiters (specified: '" + fileName + "').");
		}


		// Create Startoption, put into container
		parameterContainer.addStartOption(new Log4JStartOption(getOptionName(), fileName));

		// Has no further sub parameters
		return;
	}

	@Override
	public boolean hasDefaultParameter() {
		return getDefaultParamValue().isPresent();
	}

	@Override
	public void applyDefaultParameterIfNecessary(EditableStartParameterContainer parameterContainer) {

		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> log4JOptionList =
				parameterContainer.getOption(getOptionName());

		if ((log4JOptionList == null || log4JOptionList.isEmpty()) && getDefaultParamValue().isPresent()) {
			Log4JStartOption startOption = new Log4JStartOption(getOptionName(), getDefaultParamValue().get());
			parameterContainer.addStartOption(startOption);
		}

	}
}
