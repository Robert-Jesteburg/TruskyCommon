package org.trusky.common.api.startparameters.parser;

public interface CommandLine {

	/**
	 * @return TRUE if #peekNext and #next return meaningful values.
	 */
	public boolean hasParameter();

	/**
	 * Get the actual parameter string without moving to the next parameter.
	 *
	 * @return The actual parameter.
	 */
	public String peekNext();

	/**
	 * Get actual parameter an advance to the next one.
	 *
	 * @return The actual parameter.
	 */
	public String next();
}
