package org.trusky.common.api.startparameters.optionvalue;

import java.util.Optional;

/**
 * An OptionValue is something that can be constructed from the command line (may be an object from a JSON string)
 * that will be bound to an option or a sub option.
 */
public interface OptionValue<BASE_TYPE> {

	public void setValue(BASE_TYPE val);

	public Optional<BASE_TYPE> getValue();

	public boolean isEmpty();
}
