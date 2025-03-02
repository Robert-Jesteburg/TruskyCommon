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
 */
public interface StartOption<DEFAULT_VAL_TYPE extends OptionValue<?>, SUB_VAL_TYPE extends OptionValue<?>> {

	public String getOptionName();

	/**
	 * Options itself can act as switches (like --use-strict-syntax) in which case they do not have a value.
	 *
	 * @return see above
	 */
	public boolean isDefaultValuePresent();

	/**
	 * Get the default value or empty, if not specified on the command line.
	 *
	 * @return s.a.
	 */
	public Optional<DEFAULT_VAL_TYPE> getDefaultValue();

	/**
	 * Builds an array with all the sub option names that were supplied on the actual command line. Those names can
	 * then be used to access the value.
	 *
	 * @return
	 */
	public String[] getSubOptionNames();

	/**
	 * An option may be present, but with no value following it (may needs none, if actiong like a switch for example)
	 *
	 * @return s.a.
	 */
	public boolean isSubOptionPresent(String subOptionName);

	/**
	 * If the option is present this method tells if there is a value for present.
	 *
	 * @param subOptionName
	 * @return s.a.
	 */
	public boolean hasValue(String subOptionName);

	/**
	 * Get the list of values from the sub option or empty if either the sub option wasn't specified at all or if
	 * no value was
	 * supplied on the command line.
	 *
	 * @param subOptionName
	 * @return s.a.
	 */
	public List<SUB_VAL_TYPE> getValue(String subOptionName);
}
