/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.trusky.common.api.injection.CommonGuiceModule;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.impl.util.CommonLog4JConfigurationUtilsImpl;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

class CommonLog4JConfigurationUtilsTest {

	// Values for baseDir
	private final static String HOME_DIR_OHNE_SLASH = "/home/miri";
	private final static String HOME_DIR_MIT_SLASH = "/home/miri/";
	private final static String HOME_DIR_KURZSCHREIBSEISE = "~";

	// Values for addDir
	private final static String APP_DIR_OHNE_SLASH = "girlsboys";
	private final static String APP_DIR_MIT_SLASH = APP_DIR_OHNE_SLASH + "/";

	// Values for log4J configuration file name
	private final static String CONFIG_FILE_NAME = "girlsboys-log.xml";

	// Option names
	private final static String OPT_BASEDIR_NAME = "--baseDir";
	private final static String OPT_APPDIR_NAME = "--appDir";
	private final static String OPT_CONFIG_NAME = "--log4J";

	private final String EXPECTED_DIR = HOME_DIR_MIT_SLASH + APP_DIR_OHNE_SLASH;
	private final String EXPECTED_FILENAME_WITH_PATH = HOME_DIR_MIT_SLASH + APP_DIR_MIT_SLASH + CONFIG_FILE_NAME;

	@Mock
	private CommonSystemSettings commonSystemSettings;

	@Mock
	private CommonStartparametersUtils startparametersUtils;

	private static Stream<Arguments> provideParameterContents() {

		return Stream.of( //
				Arguments.of(HOME_DIR_OHNE_SLASH, APP_DIR_OHNE_SLASH, CONFIG_FILE_NAME), //
				Arguments.of(HOME_DIR_OHNE_SLASH, APP_DIR_MIT_SLASH, CONFIG_FILE_NAME),
				//
				Arguments.of(HOME_DIR_MIT_SLASH, APP_DIR_OHNE_SLASH, CONFIG_FILE_NAME), //
				Arguments.of(HOME_DIR_MIT_SLASH, APP_DIR_MIT_SLASH, CONFIG_FILE_NAME),
				//
				Arguments.of(HOME_DIR_KURZSCHREIBSEISE, APP_DIR_OHNE_SLASH, CONFIG_FILE_NAME),
				Arguments.of(HOME_DIR_KURZSCHREIBSEISE, APP_DIR_OHNE_SLASH, CONFIG_FILE_NAME)

				//


						);
	}

	@BeforeEach
	void setUp() {
		InjectorFactory.setModule(new CommonGuiceModule());

		MockitoAnnotations.initMocks(this);

		when(commonSystemSettings.getPathSeparator()).thenReturn("/");
		when(commonSystemSettings.getHomeDir()).thenReturn(System.getProperty("user.home"));
	}


	@AfterEach
	void tearDown() {
	}

	@ParameterizedTest
	@MethodSource("provideParameterContents")
	void testInput(String homeDir, String appDir, String configFile) {

		// GIVEN
		when(startparametersUtils.getStringStartparameterWithDefault(eq(OPT_BASEDIR_NAME), any(), any())).thenReturn(homeDir);
		when(startparametersUtils.getStringStartparameterWithDefault(eq(OPT_APPDIR_NAME), any(), any())).thenReturn(appDir);
		when(startparametersUtils.getStringStartparameterWithDefault(eq(OPT_CONFIG_NAME), any(), any())).thenReturn(configFile);

		CommonLog4JConfigurationUtils util = //
				new CommonLog4JConfigurationUtilsImpl(startparametersUtils, commonSystemSettings);

		// WHEN
		String baseDir = util.getLog4JBasePath(OPT_BASEDIR_NAME, OPT_APPDIR_NAME, null);
		String configFileName = util.getLog4JConfigurationFileName(OPT_CONFIG_NAME, null);
		String full = //
				util.getFullConfigurationFileNameWithPath(OPT_BASEDIR_NAME, OPT_APPDIR_NAME, OPT_CONFIG_NAME, null);

		// THEN
		assertAll( //
				() -> assertThat(baseDir).isEqualTo(EXPECTED_DIR), //
				() -> assertThat(configFileName).isEqualTo(CONFIG_FILE_NAME), //
				() -> assertThat(full).isEqualTo(EXPECTED_FILENAME_WITH_PATH));

	}

}