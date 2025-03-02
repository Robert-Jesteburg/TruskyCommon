package org.trusky.common.api.startparameters.parser.builderinterfaces;


public interface ParserTestArgumentEvaluator {

	ParserTestFirstSubArgumentEvaluator withDefaultValueFromArguments(String defaultParameter);

	/**
	 * If the parameters on the command line are not meant for this parser, call this method.
	 *
	 * <p>If the default value is missing, there can't be any sub value! </p>
	 *
	 * @return The test executor to execute the test.
	 */
	ParserTestExecuter withNoMatchingParameters();
}
