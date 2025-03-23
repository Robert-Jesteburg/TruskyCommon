/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.trusky.common.api.injection.CommonGuiceModule;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;

class CommonStartParametersUtilImplTest {

	@BeforeEach
	void setUp() {
		InjectorFactory.setModule(new CommonGuiceModule());
	}

	@AfterEach
	void tearDown() {
	}

	private CommonStartParametersUtilImpl getSut() {
		return new CommonStartParametersUtilImpl();
	}

	/**
	 * On missing option the default value must be delivered.
	 */
	@Test
	void whenOptionNotPresentDefaultIsDelivered() {

		final String defaultString = "Default value";
		String result = getSut().getStringStartparameterWithDefault("example", s -> new ArrayList<>(), defaultString);

		// This MUST be exactly the same object
		assertThat(result == defaultString).isTrue();

	}

	/**
	 * Even if the default value specified is NULL it must be returned as that if there is no option value for the
	 * given option name.
	 */
	@Test
	void whenOptionNotPresentAndDefaultIsNull_NullIsDelivered() {

		final String defaultString = null;
		String result = getSut().getStringStartparameterWithDefault("example", s -> new ArrayList<>(), defaultString);

		// This MUST be exactly the same object
		assertThat(result).as("Even a NULL default value has to be returned unchanged.")
				.isNull();

	}

	/**
	 * In the standard situation (exactly one option value for the given option) the string inside the
	 * {@link StringOptionValue} has to be returned.
	 */
	@Test
	void whenExactlyOneStringValueIsGiven_thatStringIsDelivered() {

		final String content = "A simple string.";
		final StringOptionValue optionValue = new StringOptionValue(content);
		final String defaultvalue = "defaultValue";

		String result = getSut().getStringStartparameterWithDefault("example", s -> List.of(optionValue),
				defaultvalue);

		assertAll( //
				() -> assertThat(result).isEqualTo(content), //
				() -> assertThat(result).isNotEqualTo(defaultvalue) //
				 );

	}

	/**
	 * On a StringOptionValue with no contents the default value has to be returned.
	 */
	@Test
	void whenStringOptionValueIsEmpty_defaultValueIsReturned() {

		final String content = "A simple string.";
		final StringOptionValue optionValue = new StringOptionValue();
		final String defaultvalue = "defaultValue";

		String result = getSut().getStringStartparameterWithDefault("example", s -> List.of(optionValue),
				defaultvalue);

		assertAll( //
				() -> assertThat(result).isEqualTo(defaultvalue), //
				() -> assertThat(result).isNotEqualTo(content) //
				 );

	}

	/**
	 * If there are multiple StringValues delivered an exception must be thrown.
	 */
	@Test
	void whenMoreThanOneStringVaule_anExceptionIsThrown() {

		final StringOptionValue val1 = new StringOptionValue("Content 1");
		final StringOptionValue val2 = new StringOptionValue("Content 2");
		final List<StringOptionValue> valueList = List.of(val1, val2);

		try {
			getSut().getStringStartparameterWithDefault( //
					"example", //
					s -> {
						return valueList;
					}, "any vallue");

			fail("An IlegalArgumentException was expected to be thrown.");

		} catch (IllegalArgumentException ia) {

			// This is the desired exception
		}


	}

	/**
	 * A NULL option name must lead to an exception.
	 */
	@Test
	void whenOptionNameIsNull_exceptionIsThrown() {

		final StringOptionValue val1 = new StringOptionValue("Content 1");
		final List<StringOptionValue> valueList = List.of(val1);

		try {
			getSut().getStringStartparameterWithDefault(null, s -> valueList, "any");
			fail("An IllegalArgumentException was expected because of the NULL option name.");

		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
	}

	/**
	 * Empty option names of different flavors must all result in an exception.
	 *
	 * @param optionName The option names from the ValueSource.
	 */
	@ParameterizedTest()
	@ValueSource(strings = {"", " ", "     ", "\t\t", " \t", "\t "})
	void testEmptyOptionNames(String optionName) {

		final StringOptionValue val1 = new StringOptionValue("Content 1");
		final List<StringOptionValue> valueList = List.of(val1);

		try {
			getSut().getStringStartparameterWithDefault(optionName, s -> valueList, "any");
			fail("An IllegalArgumentException is expected for option name \"" + optionName + "\"");

		} catch (IllegalArgumentException e) {
			// Expected behavior
		}

	}

	/**
	 * If a NULL supplier is specified an Exception must be thrown.
	 */
	@Test
	void whenParameterSupplierIsNull_exceptionIsThrown() {

		try {
			getSut().getStringStartparameterWithDefault("example", null, "any");
			fail("An IllegalArgumentException is expected on NULL parameter supplier.");

		} catch (IllegalArgumentException e) {
			// Expected behavior
		}

	}

}