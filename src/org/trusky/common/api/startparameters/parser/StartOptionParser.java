package org.trusky.common.api.startparameters.parser;

import org.trusky.common.api.startparameters.CommandLine;
import org.trusky.common.api.startparameters.EditableStartParameterContainer;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;

public interface StartOptionParser {

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

	/**
	 * If no values were given on the command line there may be a reasonable default. This method returns TRUE if
	 * such a default exists; in that case {@link #applyDefaultParameterIfNecessary(EditableStartParameterContainer)}
	 * must be called after processing all command line parameters.
	 *
	 * @return TRUE/FALSE, see description above.
	 */
	public boolean hasDefaultParameter();

	/**
	 * If a default value exists AND the option in question was not defined on the command line (can be determined by
	 * examining the parameterContainer for the option the parser is responsible for) the parser should create a
	 * StartOption with those default values.
	 *
	 * @param parameterContainer
	 */
	public void applyDefaultParameterIfNecessary(EditableStartParameterContainer parameterContainer);
}