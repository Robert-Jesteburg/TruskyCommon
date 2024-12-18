package org.trusky.common.api.startparameters;

import org.trusky.common.api.startparameters.optionvalue.OptionValue;

import java.util.List;

/**
 * Container that holds all the start options constructed by the parsers.
 */
public interface StartParameterContainer {

	/**
	 * Get the Start option if present, else empty.
	 *
	 * @param optionName
	 * @return A list of Start options if present, else empty list.
	 */
	public List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> getOption(String optionName);
}