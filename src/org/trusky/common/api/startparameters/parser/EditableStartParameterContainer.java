package org.trusky.common.api.startparameters.parser;

import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.StartParameterContainer;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;

/**
 * Each parser gets an instance of this interface to put the created StartOption into the container.
 */
public interface EditableStartParameterContainer extends StartParameterContainer {

	public void addStartOption(StartOption<? extends OptionValue<?>, ? extends OptionValue<?>> startOption);
}
