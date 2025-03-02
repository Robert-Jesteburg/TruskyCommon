package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.startparameters.CommandLine;
import org.trusky.common.api.startparameters.EditableStartParameterContainer;
import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;
import org.trusky.common.api.startparameters.parser.BaseDirOptionParser;
import org.trusky.common.api.util.CommonSystemSettings;
import org.trusky.common.impl.startparameters.BaseDirOption;

import java.util.List;
import java.util.Optional;

public class BaseDirOptionParserImpl extends AbstractOptionParser<String> implements BaseDirOptionParser {

	protected BaseDirOptionParserImpl(String optionName) {
		this(optionName, true);
	}

	protected BaseDirOptionParserImpl(String optionName, boolean compareCaseSensitive) {
		this(optionName, compareCaseSensitive, null);
	}

	protected BaseDirOptionParserImpl(String optionName, boolean compareCaseSensitive, String defaultParameterValue) {
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
			throw new StartParameterException("Call to BaseDir parser with invalid option start '" + cmdLine.peekNext() + "'.");
		}

		// Must not exist already
		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> log4JOptionList =
				parameterContainer.getOption(getOptionName());
		if (!log4JOptionList.isEmpty()) {
			throw new StartParameterException("BaseDir option was specified twice.");
		}

		// Get file name
		cmdLine.next();
		if (!cmdLine.hasParameter()) {
			throw new StartParameterException("Missing argument for BaseDir-Option.");
		}

		String baseDirName = cmdLine.next();

		CommonSystemSettings commonSystemSettings = InjectorFactory.getInstance(CommonSystemSettings.class);
		if (!baseDirName.endsWith(commonSystemSettings.getPathSeparator())) {
			baseDirName = baseDirName + commonSystemSettings.getPathSeparator();
		}

		// Create Startoption, put into container
		parameterContainer.addStartOption(new BaseDirOption(getOptionName(), baseDirName));

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

				String defaultParmOption = defaultParamValueOpt.get();
				CommonSystemSettings commonSystemSettings = InjectorFactory.getInstance(CommonSystemSettings.class);
				if (!defaultParmOption.endsWith(commonSystemSettings.getPathSeparator())) {
					defaultParmOption = defaultParmOption + commonSystemSettings.getPathSeparator();
				}
				parameterContainer.addStartOption(new BaseDirOption(getOptionName(), defaultParmOption));
			}

		}

	}
}
