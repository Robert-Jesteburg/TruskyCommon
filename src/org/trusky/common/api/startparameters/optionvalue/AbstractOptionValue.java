package org.trusky.common.api.startparameters.optionvalue;

import java.util.Optional;

public abstract class AbstractOptionValue<BASE_TYPE> implements OptionValue<BASE_TYPE> {

	private Optional<BASE_TYPE> val = Optional.empty();

	@Override
	public void setValue(BASE_TYPE val) {
		this.val = Optional.of(val);
	}

	@Override
	public Optional<BASE_TYPE> getValue() {
		return val;
	}


}
