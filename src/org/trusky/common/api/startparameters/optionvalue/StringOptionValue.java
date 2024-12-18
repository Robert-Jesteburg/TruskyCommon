package org.trusky.common.api.startparameters.optionvalue;

public class StringOptionValue extends AbstractOptionValue<String> {

	public StringOptionValue() {
		setValue(null);
	}

	public StringOptionValue(String value) {
		setValue(value);
	}

	@Override
	public boolean isEmpty() {
		return getValue() == null || getValue().isEmpty();
	}
}
