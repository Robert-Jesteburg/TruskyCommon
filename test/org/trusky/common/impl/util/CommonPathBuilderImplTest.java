package org.trusky.common.impl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.trusky.common.api.logging.CommonLogger;
import org.trusky.common.api.logging.CommonLoggerFactory;
import org.trusky.common.api.util.CommonSystemSettings;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;


class CommonPathBuilderImplTest {

	private final String BASE_PATH_OPTION = "-basePath";
	private final String APP_DIR_OPTION = "-appDir";
	private final String CONFIG_FILE_OPTION = "-configFile";

	@Mock
	private CommonSystemSettings systemSettings;

	@Mock
	private CommonLoggerFactory loggerFactory;

	@Mock
	private CommonLogger logger;

	@BeforeEach
	void setUp() {

		MockitoAnnotations.initMocks(this);

		when(loggerFactory.getLogger(any())).thenReturn(logger);

		when(systemSettings.getPathSeparator()).thenReturn("/");
		when(systemSettings.getHomeDir()).thenReturn("/home/jessi");

	}

	@ParameterizedTest
	@MethodSource("providePaths")
	void testPathBuilder(String basePath, String appDir, String configFile, int expectedLogWarnings,
						 String expectedResult) {

		// GIVEN
		TestOptionSupplier tos = new TestOptionSupplier();
		tos.putOption(BASE_PATH_OPTION, basePath);
		tos.putOption(APP_DIR_OPTION, appDir);
		tos.putOption(CONFIG_FILE_OPTION, configFile);

		// WHEN
		CommonPathBuilderImpl pathBuillder = new CommonPathBuilderImpl(systemSettings, loggerFactory);
		String fullPathName = pathBuillder.createPathNameAsAbsolutePath(on -> tos.getOption(on), BASE_PATH_OPTION,
				APP_DIR_OPTION, CONFIG_FILE_OPTION);

		// THEN
		assertAll( //
				() -> assertThat(fullPathName).isEqualTo(expectedResult), //
				() -> verify(logger, times(expectedLogWarnings)).warn(anyString()) //
				 );

	}

	public static Stream<Arguments> providePaths() {

		return Stream.of( //
				// 01 No path separator at all
				Arguments.of("/home/someuser", //
						"girlsboys", //
						"config.properties", //
						0, //
						"/home/someuser/girlsboys/config.properties"), //

				// 02 Path separator in basePath
				Arguments.of("/home/someuser/", //
						"girlsboys", //
						"config.properties", //
						0, //
						"/home/someuser/girlsboys/config.properties"), //

				// 03 Path separator in appDir
				Arguments.of("/home/someuser", //
						"girlsboys/", //
						"config.properties", //
						0, //
						"/home/someuser/girlsboys/config.properties"), //

				// 04 Path separator in basePath + appDir
				Arguments.of("/home/someuser/", //
						"girlsboys/", //
						"config.properties", //
						0, //
						"/home/someuser/girlsboys/config.properties" //
							), //

				// 05 Empty first parameter
				Arguments.of("", //
						"girlsboys/", //
						"config.properties", //
						1, //
						"/girlsboys/config.properties"),

				// 06 Empty middle parameter
				Arguments.of("/home/someuser/", //
						"", //
						"config.properties", //
						1, //
						"/home/someuser/config" + ".properties" //
							), //

				// 07 Empty last parameter
				Arguments.of("/home/someuser/", //
						"girlsboys", //
						"", //
						1, //
						"/home/someuser/girlsboys" //
							), //


				// 08 All empty arguments
				Arguments.of("", //
						"", //
						"", //
						3, //
						"/" //
							)

						);


	}


	private class TestOptionSupplier {

		private final Map<String, String> map = new HashMap<>();

		void putOption(String optionName, String value) {
			map.put(optionName, value);
		}

		public String getOption(String optionName) {
			return map.get(optionName);
		}

	}
}