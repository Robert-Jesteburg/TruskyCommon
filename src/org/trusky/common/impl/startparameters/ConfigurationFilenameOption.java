package org.trusky.common.impl.startparameters;

import org.trusky.common.api.startparameters.AbstractStartOption;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;

public class ConfigurationFilenameOption extends AbstractStartOption<StringOptionValue, StringOptionValue> {

	public ConfigurationFilenameOption(String optionName) {
		super(optionName);
	}

	public ConfigurationFilenameOption(String optionName, String defaultValue) {
		super(optionName, new StringOptionValue(defaultValue));
	}
}
