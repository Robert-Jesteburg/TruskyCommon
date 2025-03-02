package org.trusky.common.api.startparameters.parser.builderinterfaces;

public interface ParserTestArgumentSupplier {

	ParserTestArgumentSupplier withArgument(String argument);

	ParserTestArgumentEvaluator withNoMoreArguments();
}
