package org.trusky.common.api.startparameters;

import org.trusky.common.api.startparameters.optionvalue.OptionValue;

import java.util.List;
import java.util.Optional;

/**
 * Implementations of StartOption hold the actual values the user specified on the command line im a structured way
 * .<br/>
 * Each start option has a name (like --configDir) that denotes the beginning and can be used to find the correct
 * Option class; there may or may not a value following
 * (in the case of --configDir there will be one: The directory base name).
 *
 * @param <DEFAULT_VAL_TYPE> The type of the default value (mostly Sting)
 * @param <SUB_VAL_TYPE>     The type of sub value types (mostly String)
 */
public interface StartOption<DEFAULT_VAL_TYPE extends OptionValue<?>, SUB_VAL_TYPE extends OptionValue<?>> {

	String getOptionName();

	/**
	 * Options itself can act as switches (like --use-strict-syntax) in which case they do not have a value.
	 *
	 * @return see above
	 */
	boolean isDefaultValuePresent();

	/**
	 * Get the default value or empty, if not specified on the command line.
	 *
	 * @return s.a.
	 */
	Optional<DEFAULT_VAL_TYPE> getDefaultValue();

	/**
	 * Builds an array with all the sub option names that were supplied on the actual command line. Those names can
	 * then be used to access the value.
	 *
	 * @return The default value
	 */
	String[] getSubOptionNames();

	/**
	 * An option may be present, but with no value following it (may needs none, if actiong like a switch for example)
	 *
	 * @return s.a.
	 */
	boolean isSubOptionPresent(String subOptionName);

	/**
	 * If the option is present this method tells if there is a value for present.
	 *
	 * @param subOptionName Name of the sub option
	 * @return s.a.
	 */
	boolean hasValue(String subOptionName);

	/**
	 * Get the list of values from the sub option or empty if either the sub option wasn't specified at all or if
	 * no value was
	 * supplied on the command line.
	 *
	 * @param subOptionName Name of the sub option
	 * @return s.a.
	 */
	List<SUB_VAL_TYPE> getValue(String subOptionName);
}
