/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.trusky.common.api.injection.CommonGuiceModule;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.builder.BaseDirOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.OptionParserBuilder;
import org.trusky.common.api.startparameters.builder.ParamDirNameOptionParserBuilder;
import org.trusky.common.api.startparameters.exceptions.InvalidOptionException;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;

class StartparameterManagerImplTest {

	@BeforeAll
	static void beforeAll() {
		InjectorFactory.setModule(new CommonGuiceModule());
	}

	private static BaseDirOptionParserBuilder getOptionParserBuilder() {
		return InjectorFactory.getInstance(BaseDirOptionParserBuilder.class);
	}

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	private StartparameterManager getSut() {
		return InjectorFactory.getInstance(StartparameterManager.class);
	}

	/**
	 * GIVEN: A NULL parameter list
	 * WHEN: Put into the prepareParameters() method
	 * THEN: It fails with an IllegalArgumentException
	 */
	@Test
	void parameterInitialisationWithNUllFails() {

		try {
			getSut().prepareParameters(null);
			fail("Method call with NULL argument was expected to fail.");

		} catch (IllegalArgumentException ia) {
			// This is the expected exception, so it's correct to reach this block
		}
	}

	/**
	 * GIVEN: A valid parser builder list
	 * WHEN: prepareParameters() method is called twice
	 * THEN: An illegalStateException is being thrown
	 */
	@Test
	void secondInitOfParserBuildersLeadTooException() {

		// Any builder
		OptionParserBuilder builder = getOptionParserBuilder();

		StartparameterManager sut = getSut();

		// First initialisation is expected to succeed
		sut.prepareParameters(List.of(builder.build()));

		// Second call has to fail
		try {
			sut.prepareParameters(List.of(builder.build()));
			fail("Method call was expected to fail with an exception");

		} catch (IllegalStateException e) {
			// All correct, that was expected
		}

	}

	/**
	 * GIVEN: prepareParameters() has not been called
	 * WHEN: parseCommandLine() is called
	 * THEN: An IllegalStateException will be thrown
	 *
	 * @throws StartParameterException This should not be thrown, but comes from the call to parseCommandLine
	 */
	@Test
	void commandParsingWithoutParserInitialisationFails() throws StartParameterException {

		try {
			getSut().parseCommandLine(new String[]{"-v"});
			fail("Parsing without setting parsers is expected to fail.");

		} catch (IllegalStateException e) {
			// Expected exception
		}

	}

	/**
	 * GIVEN: The prepareParameters() method has been called, but there was no call to parseCommandLine()
	 * WHEN: getOption() is beeing called
	 * THEN: An IllegalSTateException is being called
	 */
	@Test
	void acccessingOptionValuesBeforeParsingFails() {

		// Any builder
		OptionParserBuilder builder = getOptionParserBuilder();

		StartparameterManager sut = getSut();

		sut.prepareParameters(List.of(builder.build()));

		try {
			List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> startOptionList = sut.getOption(
					"anyValue");

			fail("Method ist expected to fail - but returned a list with " + startOptionList.size() + " entries.");

		} catch (IllegalStateException e) {
			// This is the expected behavior
		}

	}

	/**
	 * GIVEN: prepareParameters() has been called with a valid list
	 * WHEN: parseCommandLine() is called with a NULL argument
	 * THEN: An IllegalArgumentException is being thrown
	 *
	 * @throws StartParameterException This should not be thrown, but comes from the call to parseCommandLine
	 */
	@Test
	void nullArgumentForParsingFails() throws StartParameterException {

		// Any builder
		OptionParserBuilder builder = getOptionParserBuilder();

		StartparameterManager sut = getSut();

		// First initialisation is expected to succeed
		sut.prepareParameters(List.of(builder.build()));

		try {
			sut.parseCommandLine(null);
			fail("Call with NULL argument is expected to fail.");

		} catch (IllegalArgumentException ia) {
			// Expected behavior
		}

	}

	/**
	 * GIVEN: A parser has been supplied for an option that is really present on the command line <br/>
	 * WHEN: The option is accessed after parsing...<br/>
	 * THEN: ... it' accessible (and there is exactly one entry present)
	 *
	 * @throws StartParameterException This should not be thrown, but comes from the call to parseCommandLine
	 */
	@Test
	void expectedOptionThatIsPresentCanBeAccessed() throws StartParameterException {

		OptionParserBuilder builder = getOptionParserBuilder();

		StartparameterManager sut = getSut();

		sut.prepareParameters(List.of(builder.build()));

		String[] args = new String[]{builder.getOptionName(), //
				"~/"};

		sut.parseCommandLine(args);

		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> startOptionList =
				sut.getOption(builder.getOptionName());

		assertAll( //
				() -> assertThat(startOptionList).isNotNull(), //
				() -> assertThat(startOptionList.size()).as("Must be " + "exactly one entry")
						.isEqualTo(1)

				 );


	}


	/**
	 * GIVEN: A parser for a value not given on the command line has been supplied that has no default value<br/>
	 * WHEN: The option is accessed after parsing...<br/>
	 * THEN: ...there is no value (that is, an empty list is returned)
	 *
	 * @throws StartParameterException This should not be thrown, but comes from the call to parseCommandLine
	 */
	@Test
	void optionWithoutDefaultIsNotPresentIfNoDefaultvaluePresent() throws StartParameterException {

		ParamDirNameOptionParserBuilder builder = InjectorFactory.getInstance(ParamDirNameOptionParserBuilder.class);

		StartparameterManager sut = getSut();
		sut.prepareParameters(List.of(builder.build()));
		sut.parseCommandLine(new String[]{});
		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> startOptionList =
				sut.getOption(builder.getOptionName());

		assertAll( //
				() -> assertThat(startOptionList.isEmpty()).as("The start options schould not be present, as it isn't "
								+ "present " + "on the command line and there is nor default value given.")
						.isTrue());
	}

	/**
	 * GIVEN: A parser for a value not specified on the command line that has a default value<br/>
	 * WHEN: The option is accessed after parsing...<br/>
	 * THEN: ...the single value is present and holds the expected default value.
	 *
	 * @throws StartParameterException This should not be thrown, but comes from the call to parseCommandLine
	 */
	@Test
	void expectedOptionWithDefaultvalueThatIsAbsentCanBeAccessed() throws StartParameterException {

		final String DEFAULT_VALUE = "~/";

		BaseDirOptionParserBuilder builder = getOptionParserBuilder();
		builder.setDefaultOptionValue(DEFAULT_VALUE);

		StartparameterManager sut = getSut();
		sut.prepareParameters(List.of(builder.build()));
		sut.parseCommandLine(new String[]{});
		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> startOptionList =
				sut.getOption(builder.getOptionName());

		/*
		 * The warnings below for using get() without checking presence with isPresent can be ignored for the test:
		 * If the value is not present, this is an error and the test fails with reasonable
		 */
		assertAll( //
				() -> assertThat(startOptionList).isNotNull(), //
				() -> assertThat(startOptionList.size()) //
						.as("There is " + "exactly one entry expected (providing " + //
								"the default value)")
						.isEqualTo(1), //
				() -> assertThat(startOptionList.get(0)
						.getDefaultValue()).isPresent(), //
				() -> assertThat(startOptionList.get(0)
						.getDefaultValue()
						.get()
						.isEmpty()).isFalse(), //
				() -> assertThat(startOptionList.get(0)
						.getDefaultValue()
						.get()
						.getValue()
						.get()
						.toString()).isEqualTo(DEFAULT_VALUE));

	}

	/**
	 * GIVEN: An option is given on the command line that is not supported by any given parser<br/>
	 * WHEN: Doing the parsing...<br/>
	 * THEN: ...it will result in an InvalidOptionException that holds the unknown option
	 *
	 * @throws StartParameterException This should not be thrown, but comes from the call to parseCommandLine
	 */
	@Test
	void unexpectedOptionLeadsToException() throws StartParameterException {

		final String UNEXPECTED_OPTION = "-f";

		StartparameterManager sut = getSut();
		sut.prepareParameters(new LinkedList<>());

		String[] args = new String[]{UNEXPECTED_OPTION};

		try {
			sut.parseCommandLine(args);
			fail("Expected InvalidOptionException was not thrown.");

		} catch (InvalidOptionException e) {

			// Option name should be given in the exception
			assertThat(e.getInvalidOptionName()).isEqualTo(UNEXPECTED_OPTION);
		}

	}

	/**
	 * If no option is expected  (so no option parsers are given) and no options are given on the command line,
	 * parsing should be completed successfully.
	 *
	 * @throws StartParameterException This should not be thrown, but comes from the call to parseCommandLine
	 */
	@Test
	void noOptionGivenWhileNoParsersDefinedSucceeds() throws StartParameterException {

		StartparameterManager sut = getSut();

		sut.prepareParameters(new ArrayList<>());
		sut.parseCommandLine(new String[]{}); // Must not crash or throw any exception

		assertAll( //
				() -> assertThat(sut.hasAnyStartOption()).isFalse() //
				 );

	}

}