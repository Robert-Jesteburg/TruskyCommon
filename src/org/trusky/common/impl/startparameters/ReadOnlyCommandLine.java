package org.trusky.common.impl.startparameters;

import org.trusky.common.api.startparameters.parser.CommandLine;
import org.trusky.common.api.startparameters.parser.StartOptionParser;

/**
 * Class for calling {@link StartOptionParser#canParse(CommandLine)}: Will
 * ensure method next() is not called against this CommandLine object.
 */
public class ReadOnlyCommandLine extends CommandLineImpl {

	public ReadOnlyCommandLine(String[] arguments) {
		super(arguments);
	}

	@Override
	public String next() {
		throw new UnsupportedOperationException("method next() must not be called.");
	}
}
