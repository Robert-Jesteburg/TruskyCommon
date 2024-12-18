package org.trusky.common.impl.startparameters;

import org.trusky.common.api.startparameters.AbstractStartOption;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;

public class ParamDirNameOption extends AbstractStartOption<StringOptionValue, StringOptionValue> {

	public ParamDirNameOption(String optionName, String defaultValue) {
		super(optionName, new StringOptionValue(defaultValue));
	}
}
