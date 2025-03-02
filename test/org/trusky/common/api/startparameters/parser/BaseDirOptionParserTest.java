/*
 * Copyright (c) 2024 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.startparameters.parser;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.trusky.common.api.injection.CommonGuiceModule;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.startparameters.builder.BaseDirOptionParserBuilder;
import org.trusky.common.api.util.CommonSystemSettings;

class BaseDirOptionParserTest extends StartOptionParserTestBase {

	private final String OPTION_NAME = "--baseDir";
	private final String EXAMPLE_BASE_DIR = "usr/local/share/example";

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

		CommonSystemSettings systemSettings = InjectorFactory.getInstance(CommonSystemSettings.class);
		String expectedBaseDir = getExpectedBaseDir(systemSettings.getHomeDir());

		BaseDirOptionParserBuilder parserBuilder = InjectorFactory.getInjector()
				.getInstance(BaseDirOptionParserBuilder.class);

		parserBuilder.setOptionName(OPTION_NAME);

		configureAndExecute().setOptionName(OPTION_NAME)
				.setParserBuilder(parserBuilder)
				.setExpectedDefaultValue(expectedBaseDir)
				.executionAsDefaultSettings()
				.executeTest();

	}

	@Test
	void testDefaultValue_ifDefaultValueWasSpecified() throws Exception {


		String expectedBaseDir = getExpectedBaseDir(EXAMPLE_BASE_DIR);

		BaseDirOptionParserBuilder parserBuilder = InjectorFactory.getInjector()
				.getInstance(BaseDirOptionParserBuilder.class);

		parserBuilder.setOptionName(OPTION_NAME);

		configureAndExecute().setOptionName(OPTION_NAME)
				.setParserBuilder(parserBuilder)
				.setExpectedDefaultValue(expectedBaseDir)
				.executionWithCommandLineParsing()
				.withArgument(OPTION_NAME)
				.withArgument(EXAMPLE_BASE_DIR)
				.withNoMoreArguments()
				.withDefaultValueFromArguments(EXAMPLE_BASE_DIR)
				.withNoSubValue()
				.executeTest();

	}

	private @NotNull String getExpectedBaseDir(String baseDir) {

		CommonSystemSettings systemSettings = InjectorFactory.getInstance(CommonSystemSettings.class);
		String expectedBaseDir = baseDir;
		if (!expectedBaseDir.endsWith(systemSettings.getPathSeparator())) {
			expectedBaseDir += systemSettings.getPathSeparator();
		}
		return expectedBaseDir;
	}

	@Test
	void testDefaultValue_ifDefaultValueWithTrailingSlashWasSpecified() throws Exception {

		String expectedBaseDir = getExpectedBaseDir(EXAMPLE_BASE_DIR);

		BaseDirOptionParserBuilder parserBuilder = InjectorFactory.getInjector()
				.getInstance(BaseDirOptionParserBuilder.class);

		parserBuilder.setOptionName(OPTION_NAME);

		configureAndExecute().setOptionName(OPTION_NAME)
				.setParserBuilder(parserBuilder)
				.setExpectedDefaultValue(expectedBaseDir)
				.executionWithCommandLineParsing()
				.withArgument(OPTION_NAME)
				.withArgument(expectedBaseDir)
				.withNoMoreArguments()
				.withDefaultValueFromArguments(expectedBaseDir)
				.withNoSubValue()
				.executeTest();

	}
}