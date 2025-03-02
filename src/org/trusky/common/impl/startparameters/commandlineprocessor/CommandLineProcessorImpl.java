package org.trusky.common.impl.startparameters.commandlineprocessor;

import org.trusky.common.api.startparameters.CommandLine;
import org.trusky.common.api.startparameters.CommandLineProcessor;
import org.trusky.common.api.startparameters.StartParameterContainer;
import org.trusky.common.api.startparameters.StartparameterParserContainer;
import org.trusky.common.api.startparameters.exceptions.InvalidOptionException;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;
import org.trusky.common.api.startparameters.parser.StartOptionParser;
import org.trusky.common.impl.startparameters.CommandLineImpl;
import org.trusky.common.impl.startparameters.StartParameterContainerImpl;

import java.util.Optional;

public class CommandLineProcessorImpl implements CommandLineProcessor {


	public StartParameterContainer parseCommandLine(String[] commandLine,
													StartparameterParserContainer parserContainer)
	throws StartParameterException {

		StartParameterContainerImpl parameter = new StartParameterContainerImpl();

		CommandLine cmdLine = new CommandLineImpl(commandLine);

		// Parse command line options
		while (cmdLine.hasParameter()) {

			Optional<StartOptionParser> parserOpt = parserContainer.findParserForOption(cmdLine);
			if (parserOpt.isEmpty()) {
				throw new InvalidOptionException( //
						"Can't obtain parser to parse \"" + //
								cmdLine.peekNext() + //
								"\" option."  //
				);
			}

			parserOpt.get()
					.parse(cmdLine, parameter);

		}

		// Create default parameters
		for (int i = 0; i < parserContainer.getParserCount(); i++) {

			StartOptionParser parser = parserContainer.getParser(i);
			if (parser.hasDefaultParameter()) {
				parser.applyDefaultParameterIfNecessary(parameter);
			}
		}

		return parameter;

	}


}
