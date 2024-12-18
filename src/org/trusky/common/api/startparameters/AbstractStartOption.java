package org.trusky.common.api.startparameters;

import org.trusky.common.api.startparameters.optionvalue.OptionValue;

import java.util.*;

/**
 * Class that can be used to easily create a start option without to have to create the same methods over and over
 * again.
 */
public abstract class AbstractStartOption<DEFAULT_VAL_TYPE extends OptionValue, SUB_VAL_TYPE extends OptionValue> implements
		StartOption<DEFAULT_VAL_TYPE, SUB_VAL_TYPE> {

	private final String optionName;

	private Optional<DEFAULT_VAL_TYPE> defaultValue;
	private final Map<String, List<SUB_VAL_TYPE>> subOptionMap = new HashMap<>();

	public AbstractStartOption(String optionName) {
		this.optionName = optionName;
		defaultValue = Optional.empty();
	}

	public AbstractStartOption(String optionName, DEFAULT_VAL_TYPE defaultValue) {
		this.optionName = optionName;
		this.defaultValue = Optional.of(defaultValue);
	}

	@Override
	public String getOptionName() {
		return optionName;
	}

	@Override
	public boolean isDefaultValuePresent() {
		return defaultValue.isPresent() && !defaultValue.get()
				.isEmpty();
	}

	@Override
	public Optional<DEFAULT_VAL_TYPE> getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String[] getSubOptionNames() {

		String[] presentOptionNames = new String[subOptionMap.keySet()
				.size()];
		int i = 0;
		for (String optionName : subOptionMap.keySet()) {
			presentOptionNames[i] = optionName;
			i++;
		}

		return presentOptionNames;
	}

	@Override
	public boolean isSubOptionPresent(String subOptionName) {
		return subOptionMap.containsKey(subOptionName);
	}

	@Override
	public boolean hasValue(String subOptionName) {

		boolean isValuePresent = false;
		if (isSubOptionPresent(subOptionName)) {

			List<SUB_VAL_TYPE> subValue = subOptionMap.get(subOptionName);
			isValuePresent = (subValue != null) && !subValue.isEmpty();
		}

		return isValuePresent;

	}

	@Override
	public List<SUB_VAL_TYPE> getValue(String subOptionName) {

		List<SUB_VAL_TYPE> valueList;
		if (isSubOptionPresent(subOptionName)) {

			List<SUB_VAL_TYPE> subValueList = subOptionMap.get(subOptionName);
			if ((subValueList != null) && !subValueList.isEmpty()) {
				return new LinkedList<>();
			}

			valueList = new LinkedList<>(subValueList);

		} else {
			valueList = new LinkedList<>();
		}

		return valueList;

	}

	/**
	 * Set the default value. Passing in NULL will result in the default value being empty.
	 *
	 * @param value
	 */
	protected void setDefaultValue(DEFAULT_VAL_TYPE value) {
		this.defaultValue = (value == null) ? Optional.empty() : Optional.of(value);
	}

	/**
	 * Add a switch as sub option, No checks are made to see if there is already such a sub option.
	 *
	 * @param subOptionName
	 */
	protected void addSubOption(String subOptionName) {
		subOptionMap.put(subOptionName, null);
	}

	/**
	 * Add a sub option with a value, No checks are made to see if there is already such a sub option.
	 *
	 * @param subOptionName
	 * @param value
	 */
	protected void addSubOption(String subOptionName, SUB_VAL_TYPE value) {

		if (value == null) {
			throw new IllegalArgumentException("Value must not be NULL.");
		}

		List<SUB_VAL_TYPE> valueList;
		if (subOptionMap.containsKey(subOptionName)) {
			valueList = subOptionMap.get(subOptionName);

		} else {
			valueList = new LinkedList<>();
			subOptionMap.put(subOptionName, valueList);
		}

		valueList.add(value);
	}
}
