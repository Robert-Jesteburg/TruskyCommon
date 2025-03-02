package org.trusky.common.api.startparameters;

import org.trusky.common.api.startparameters.exceptions.StartParameterException;

public interface CommandLineProcessor {

	/**
	 * @param commandLine     The command line that was given
	 * @param parserContainer Initialized container with all parsers present.
	 * @return Container with the start options.
	 */
	StartParameterContainer parseCommandLine(String[] commandLine, StartparameterParserContainer parserContainer)
	throws StartParameterException;
}
