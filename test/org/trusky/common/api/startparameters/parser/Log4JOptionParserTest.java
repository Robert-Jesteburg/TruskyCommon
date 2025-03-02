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
import org.trusky.common.api.startparameters.builder.Log4JOptionParserBuilder;

public class Log4JOptionParserTest extends StartOptionParserTestBase {

	private final static String OPTION_NAME = "--log4J";
	private final static String CONFIGURATION_FILENAME = "log4j.xml";

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
	void testDefaultValue_ifNotSpecifiedOnTheCommandLine() throws Exception {


		//  Has no default value
		Log4JOptionParserBuilder parserBuilder = InjectorFactory.getInjector()
				.getInstance(Log4JOptionParserBuilder.class);

		parserBuilder.setOptionName(OPTION_NAME);

		configureAndExecute().setOptionName(OPTION_NAME)
				.setParserBuilder(parserBuilder)
				.setDefaultValueNotExpected()
				.executionAsDefaultSettings()
				.executeTest();

	}

	@Test
	void testDefaultValue_ifDefaultValueWasSpecified() throws Exception {

		Log4JOptionParserBuilder parserBuilder = InjectorFactory.getInjector()
				.getInstance(Log4JOptionParserBuilder.class);

		parserBuilder.setOptionName(OPTION_NAME);

		configureAndExecute().setOptionName(OPTION_NAME)
				.setParserBuilder(parserBuilder)
				.setDefaultValueNotExpected()
				.executionWithCommandLineParsing()
				.withArgument(OPTION_NAME)
				.withArgument(CONFIGURATION_FILENAME)
				.withNoMoreArguments()
				.withDefaultValueFromArguments(CONFIGURATION_FILENAME)
				.withNoSubValue()
				.executeTest();
	}


}