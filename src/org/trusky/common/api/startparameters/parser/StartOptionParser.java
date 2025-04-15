package org.trusky.common.api.startparameters.parser;

import org.trusky.common.api.startparameters.CommandLine;
import org.trusky.common.api.startparameters.EditableStartParameterContainer;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;

import java.util.Optional;

public interface StartOptionParser {

	String getOptionName();

	Optional getDefaultParamValue();

	/**
	 * @param cmdLine This method must not call next() against cmdLine
	 * @return TRUE if this parser is responsible to parse.
	 */
	public boolean canParse(CommandLine cmdLine);

	/**
	 * Parse one ore more parameter strings and put the resulting start parameter option into the parameterContainer.
	 *
	 * @param cmdLine
	 * @param parameterContainer
	 * @throws StartParameterException
	 */
	public void parse(CommandLine cmdLine, EditableStartParameterContainer parameterContainer)
	throws StartParameterException;

	default boolean hasDefaultParameter() {
		return false;
	}

	/**
	 * If a default value exists AND the option in question was not defined on the command line (can be determined by
	 * examining the parameterContainer for the option the parser is responsible for) the parser should create a
	 * StartOption with those default values.
	 *
	 * @param parameterContainer
	 */
	public void applyDefaultParameterIfNecessary(EditableStartParameterContainer parameterContainer);
}