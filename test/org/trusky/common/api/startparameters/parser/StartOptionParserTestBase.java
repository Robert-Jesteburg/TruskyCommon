/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.startparameters.parser;

import org.trusky.common.api.startparameters.CommandLine;
import org.trusky.common.api.startparameters.EditableStartParameterContainer;
import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.builder.OptionParserBuilder;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;
import org.trusky.common.api.startparameters.parser.builderinterfaces.*;
import org.trusky.common.impl.startparameters.CommandLineImpl;
import org.trusky.common.impl.startparameters.StartParameterContainerImpl;

import java.util.*;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class StartOptionParserTestBase<DEFAULT_VAL_TYPE extends OptionValue<?>> {

	protected ParserTestOptionNameSupplier configureAndExecute() {
		return new TestExecuter();
	}

	private Boolean checkDefaultValue(Optional<DEFAULT_VAL_TYPE> valueFromStartOption, String expectedStringValue) {

		boolean isCheckSuccessfull = true;

		// Base implementation just knows about strings

		if (valueFromStartOption.isPresent()) {

			DEFAULT_VAL_TYPE optionValue = valueFromStartOption.get();
			if (optionValue instanceof StringOptionValue) {

				StringOptionValue stringOptionValue = (StringOptionValue) optionValue;
				if (stringOptionValue.getValue()
						.isPresent()) {

					isCheckSuccessfull = stringOptionValue.getValue()
							.get()
							.equals(expectedStringValue);

				} else {

					/*
					 * This method will be called if a default value is expected. So, if the start option holds no
					 * default value this is an error.
					 */
					isCheckSuccessfull = false;

				}
			}

		}

		return isCheckSuccessfull;
	}

	private class TestExecuter implements ParserTestOptionNameSupplier, ParserTestParserBuilderSupplier,
			ParserTestDefaultExpectationType<DEFAULT_VAL_TYPE>, ParserTestTestExecutionType,
			ParserTestArgumentSupplier,
			ParserTestFirstSubArgumentEvaluator, ParserTestArgumentEvaluator, ParserTestFurtherSubArgumentEvaluator,
			ParserTestExecuter {

		String optionName;
		OptionParserBuilder parserBuilder;
		boolean isDefaultValueExpected;
		Object defaultValue;
		boolean doTestWithCommandLine;
		List<String> cmdLineParameter = new LinkedList<>();

		Map<String, List<OptionValue<?>>> suboptionMap = new HashMap<>();
		String defaultParameterFromCommandLine;
		boolean subOptionsAreExpected;

		// Parameter on a call to this function: The object stored in the startOption, the String set as the default
		// value
		private BiFunction<Optional<DEFAULT_VAL_TYPE>, String, Boolean> checkDefaultValueFunc;

		// -------------- ParserTestOptionNameSupplier
		@Override
		public ParserTestParserBuilderSupplier setOptionName(String optionName) {
			assertThat(optionName).isNotNull();
			this.optionName = optionName;
			return this;
		}

		// -------------- ParserTestParserBuilderSupplier

		/**
		 * @param parserBuilder The builder must have been configured to use the same option name that was specified
		 *                      in {@link #setOptionName(String)}!
		 * @return ParserTestDefaultExpectationType
		 */
		@Override
		public ParserTestDefaultExpectationType setParserBuilder(OptionParserBuilder parserBuilder) {
			assertThat(parserBuilder).isNotNull();
			this.parserBuilder = parserBuilder;
			return this;
		}

		// -------------- ParserTestDefaultExpectationType

		@Override
		public ParserTestTestExecutionType setDefaultValueNotExpected() {

			isDefaultValueExpected = false;
			defaultValue = null;
			return this;
		}

		@Override
		public ParserTestTestExecutionType setExpectedDefaultValue(Object defaultValue) {

			isDefaultValueExpected = true;
			this.defaultValue = defaultValue;
			checkDefaultValueFunc = null;
			return this;
		}

		@Override
		public ParserTestTestExecutionType setExpectedDefaultValue(Object defaultValue,
																   BiFunction<Optional<DEFAULT_VAL_TYPE>, String,
																		   Boolean> checkDefaultValueFunc) {

			isDefaultValueExpected = true;
			this.defaultValue = defaultValue;
			this.checkDefaultValueFunc = checkDefaultValueFunc;
			return this;
		}


		// -------------- ParserTestTestExecutionType

		@Override
		public ParserTestExecuter executionAsDefaultSettings() {

			doTestWithCommandLine = false;
			return this;
		}

		@Override
		public ParserTestArgumentSupplier executionWithCommandLineParsing() {
			doTestWithCommandLine = true;
			return this;
		}

		// -------------- ParserTestArgumentSupplier

		@Override
		public ParserTestArgumentSupplier withArgument(String argument) {

			cmdLineParameter.add(argument);
			return this;
		}

		@Override
		public ParserTestArgumentEvaluator withNoMoreArguments() {
			return this;
		}

		// -------------- ParserTestArgumentEvaluator

		@Override
		public ParserTestFirstSubArgumentEvaluator withDefaultValueFromArguments(String defaultParameter) {

			defaultParameterFromCommandLine = defaultParameter;
			return this;
		}

		@Override
		public ParserTestExecuter withNoMatchingParameters() {
			return this;
		}

		// -------------- ParserTestFirstSubArgumentEvaluator

		@Override
		public ParserTestFurtherSubArgumentEvaluator withFirstSubValue(String subOptionName, OptionValue<?> value) {

			List<OptionValue<?>> valueList = new LinkedList<>();
			valueList.add(value);

			suboptionMap.put(subOptionName, valueList);
			subOptionsAreExpected = true;
			return this;
		}

		@Override
		public ParserTestExecuter withNoSubValue() {

			subOptionsAreExpected = false; // must be tested to support no sub value at all
			return this;
		}

		// -------------- ParserTestFurtherSubArgumentEvaluator

		@Override
		public ParserTestFurtherSubArgumentEvaluator withSubValue(String subOptionName, OptionValue<?> subValue) {

			List<OptionValue<?>> valueList = suboptionMap.get(subOptionName);
			assertThat(valueList).isNotNull();
			valueList.add(subValue);

			return this;

		}

		@Override
		public ParserTestExecuter withLastSubValue(String subOptionName, OptionValue<?> subValue) {

			List<OptionValue<?>> valueList = suboptionMap.get(subOptionName);
			assertThat(valueList).isNotNull();
			valueList.add(subValue);

			return this;
		}

		// -------------- ParserTestExecuter

		@Override
		public void executeTest() throws Exception {

			StartOptionParser parser = parserBuilder.build();

			assertThat(parser.hasDefaultParameter()).isEqualTo(isDefaultValueExpected);

			EditableStartParameterContainer editableStartParameterContainer = new StartParameterContainerImpl();

			if (doTestWithCommandLine) {

				// Create command line by prepending optionName to the parameters supplied
				int paramCount = cmdLineParameter.size();
				String[] paramArray = new String[paramCount];
				int i = 0;
				for (String additionalParameter : cmdLineParameter) {
					paramArray[i] = additionalParameter;
					i++;
				}

				CommandLine cmdLine = new CommandLineImpl(paramArray);
				parser.parse(cmdLine, editableStartParameterContainer);

			} else {
				parser.applyDefaultParameterIfNecessary(editableStartParameterContainer);
			}

			/*
			 * If there is no command line to be parsed AND the parser defines no default value, then there is no
			 * need for it to be put into the parameter container.
			 *
			 * And if there is no object present, there's nothing to be checked further.
			 */
			if (!doTestWithCommandLine && defaultValue == null) {

				//  If the command line had no matching parameter and there's no default value the start option
				//  shouldn't be present either! Needs another method to tell the test class about it.

				assertThat(editableStartParameterContainer.getOption(optionName)).as("The start option under test " +
								"mustn't be present in the parameter container.")
						.isEmpty();

				return; // No further tests to be executed!
			}

			// Should be exactly one option inside the container
			List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> startOptionList =
					editableStartParameterContainer.getOption(optionName);

			assertThat(startOptionList.size()).as("Exactly one start option of this kind should be present.")
					.isEqualTo(1);

			StartOption<? extends OptionValue<?>, ? extends OptionValue<?>> startOption = startOptionList.get(0);
			if (isDefaultValueExpected) { // No default value means a standalone option like --verbose

				Optional<? extends OptionValue<?>> optDefaultBaseDirValue = startOption.getDefaultValue();
				assertThat(optDefaultBaseDirValue.isPresent()).as("The default value should be present")
						.isTrue();

				OptionValue<?> optionValue = optDefaultBaseDirValue.get();
				assertThat(optionValue).as("The option value itself should not be NULL. even if the default value is " + "NULL.")
						.isNotNull();

				assertThat(optionValue.isEmpty()).as("The inner value of the option value should have been set.")
						.isFalse();

				Optional<?> optInnerValue = optionValue.getValue();
				assertThat(optInnerValue).as("Inner value shouldn't be empty.")
						.isPresent();

				Object innerDefaultValue = optInnerValue.get();
				if (defaultValue == null) {
					assertThat(innerDefaultValue).as("The default value is not NULL, as set to be expected.")
							.isNull();

				} else {
					assertThat(defaultValue).as("The actual default value is not equal to the expected value given.")
							.isEqualTo(defaultValue);
				}


			}
			if (doTestWithCommandLine) {

				// default value
				if (defaultParameterFromCommandLine != null) {
					assertThat(startOption.getDefaultValue()).as("The default value '" + defaultParameterFromCommandLine + "' was expected.")
							.isPresent();

					/*
					 * The content can be testet if it is a string value (which is the usual case). Otherwise, the
					 * test does not know about
					 */
					if (checkDefaultValueFunc == null) {
						assertThat(checkDefaultValue((Optional<DEFAULT_VAL_TYPE>) startOption.getDefaultValue(),
								defaultParameterFromCommandLine)).as("The default value differs from the expectation")
								.isTrue();

					} else {
						assertThat(checkDefaultValueFunc.apply((Optional<DEFAULT_VAL_TYPE>) startOption.getDefaultValue(), defaultParameterFromCommandLine)).isTrue();
					}

				}


				// Sub values
				if (!subOptionsAreExpected) {

					assertThat(startOption.getSubOptionNames().length).as("There mustn't be any sub value!")
							.isEqualTo(0);

				} else {

					//  Sub values in our map should be present at the names given
					for (String subOptionName : suboptionMap.keySet()) {

						assertThat(startOption.hasValue(subOptionName)).as("Sub option '" + subOptionName + "' " + "is"
										+ " missing.")
								.isTrue();


						List<? extends OptionValue<?>> valueList = startOption.getValue(subOptionName);

						assertThat(valueList).isNotNull();

						// valueList weist gleiche Werte auf (in gleicher Reihenfolge!). NULL berücksichtigen!
						List<? extends OptionValue<?>> expectedValues = suboptionMap.get(subOptionName);

						assertThat(expectedValues.size()).as("Entry count differs.")
								.isEqualTo(valueList.size());

						int j = 0;
						for (OptionValue<?> val : valueList) {

							OptionValue<?> expectedValue = expectedValues.get(j);

							if (val.isEmpty()) {
								assertThat(expectedValue.isEmpty()).as("List at index " + j + " is expected to hold " + "values.")
										.isTrue();

							} else {

								Optional<?> actualValue = val.getValue();
								Optional<?> expected = expectedValue.getValue();

								// Entweder beide empty
								if (actualValue.isEmpty()) {
									assertThat(expected.isEmpty()).as("Actual value at index " + j + " is not " +
													"expected" + " " + "to be empty.")
											.isTrue();

								} else {

									// Oder Inhalt ist gleich
									assertThat(actualValue).as("actual value at index " + j + " differs from expected "
													+ "value.")
											.isEqualTo(expected);
								}

							}

							j++;
						}

					}

				}
			}

		}


	}


}
