package org.trusky.common.impl.startparameters;

import org.trusky.common.api.startparameters.CommandLine;

public class CommandLineImpl implements CommandLine {

	private final String[] arguments;
	private int actualParametrIndex;

	public CommandLineImpl(String[] arguments) {
		this.arguments = arguments;
		actualParametrIndex = 0;
	}


	@Override
	public boolean hasParameter() {
		return (arguments != null) && (actualParametrIndex < arguments.length);
	}

	@Override
	public String peekNext() {

		if (arguments == null || actualParametrIndex >= arguments.length) {
			throw new IllegalArgumentException("Access behind the string parameter array. Use hasNext() before " +
					"calling this method!");
		}

		return arguments[actualParametrIndex];
	}

	@Override
	public String next() {

		String parameterString = peekNext();
		actualParametrIndex++;
		return parameterString;
	}
}
