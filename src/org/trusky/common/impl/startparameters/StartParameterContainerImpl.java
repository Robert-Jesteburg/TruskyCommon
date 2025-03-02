package org.trusky.common.impl.startparameters;

import org.trusky.common.api.startparameters.EditableStartParameterContainer;
import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This is an internal helper class that is instantiated in the common library and therefore mustn't be created by
 * injection from outside this library.
 */
public class StartParameterContainerImpl implements EditableStartParameterContainer {

	private final Map<String, List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>>> optionMap =
			new HashMap<>();

	@Override
	public void addStartOption(StartOption<? extends OptionValue<?>, ? extends OptionValue<?>> startOption) {

		if (startOption == null) {
			throw new IllegalArgumentException("The start option must not be NULL.");
		}

		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> actualList;
		if (optionMap.containsKey(startOption.getOptionName())) {
			actualList = optionMap.get(startOption.getOptionName());

		} else {
			actualList = new LinkedList<>();
			optionMap.put(startOption.getOptionName(), actualList);
		}

		actualList.add(startOption);

	}

	@Override
	public List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> getOption(String optionName) {

		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> actualList;
		if (optionMap.containsKey(optionName)) {

			List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> storedList =
					optionMap.get(optionName);
			actualList = new LinkedList<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>>(storedList);

		} else {
			actualList = new LinkedList<>();
		}

		return actualList;
	}
}
