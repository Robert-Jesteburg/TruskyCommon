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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.trusky.common.api.injection.CommonGuiceModule;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.startparameters.builder.ConfigurationFilenameOptionParserBuilder;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;

class ConfigurationFilenameOptionParserTest extends StartOptionParserTestBase<StringOptionValue> {

	private final String OPTION_NAME = "--configFile";
	private final String DEFAULT_FILE_NAME = "girlsboys.cnf";
	private final String CONFIG_FILE_NAME_FORM_COMMANDLINE = "server.cnf";

	@BeforeAll
	static void beforeAll() {
		InjectorFactory.setModule(new CommonGuiceModule());
	}

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testDefaultValue_IfNotSpecifiedOnCommandLine() throws Exception {

		ConfigurationFilenameOptionParserBuilder builder =
				InjectorFactory.getInstance(ConfigurationFilenameOptionParserBuilder.class);

		builder.setOptionName(OPTION_NAME);
		builder.setDefaultOptionValue(DEFAULT_FILE_NAME);

		configureAndExecute().setOptionName(OPTION_NAME)
				.setParserBuilder(builder)
				.setExpectedDefaultValue(DEFAULT_FILE_NAME)
				.executionAsDefaultSettings()
				.executeTest();
	}

	@Test
	void testDefaultValue_IfGivenOnCommandLine() throws Exception {

		ConfigurationFilenameOptionParserBuilder builder =
				InjectorFactory.getInstance(ConfigurationFilenameOptionParserBuilder.class);

		builder.setOptionName(OPTION_NAME);
		builder.setDefaultOptionValue(CONFIG_FILE_NAME_FORM_COMMANDLINE);

		configureAndExecute().setOptionName(OPTION_NAME)
				.setParserBuilder(builder)
				.setExpectedDefaultValue(CONFIG_FILE_NAME_FORM_COMMANDLINE)
				.executionWithCommandLineParsing()
				.withArgument(OPTION_NAME)
				.withArgument(CONFIG_FILE_NAME_FORM_COMMANDLINE)
				.withNoMoreArguments()
				.withDefaultValueFromArguments(CONFIG_FILE_NAME_FORM_COMMANDLINE)
				.withNoSubValue()
				.executeTest();

	}

}