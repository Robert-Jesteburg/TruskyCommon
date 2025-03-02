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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.trusky.common.api.injection.CommonGuiceModule;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.startparameters.builder.ParamDirNameOptionParserBuilder;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;

public class ParamDirNameOptionParserTest extends StartOptionParserTestBase<StringOptionValue> {

	private final String OPTION_NAME = "--paramDir";
	private final String DEFAULT_VALUE = ".girlsboys";

	@BeforeAll
	static void beforeAll() {
		InjectorFactory.setModule(new CommonGuiceModule());
	}

	@Test
	void testDefaultValue_IfNotGivenOnCommandLine() throws Exception {

		ParamDirNameOptionParserBuilder builder = InjectorFactory.getInstance(ParamDirNameOptionParserBuilder.class);

		builder.setOptionName(OPTION_NAME);

		configureAndExecute().setOptionName(OPTION_NAME)
				.setParserBuilder(builder)
				.setDefaultValueNotExpected()
				.executionAsDefaultSettings()
				.executeTest();

	}

	@Test
	void testDefaultValue_IfReadFromCommandLine() throws Exception {

		ParamDirNameOptionParserBuilder builder = InjectorFactory.getInstance(ParamDirNameOptionParserBuilder.class);

		builder.setOptionName(OPTION_NAME);

		configureAndExecute().setOptionName(OPTION_NAME)
				.setParserBuilder(builder)
				.setDefaultValueNotExpected()
				.executionWithCommandLineParsing()
				.withArgument(OPTION_NAME)
				.withArgument(DEFAULT_VALUE)
				.withNoMoreArguments()
				.withDefaultValueFromArguments(DEFAULT_VALUE)
				.withNoSubValue()
				.executeTest();
	}
}
