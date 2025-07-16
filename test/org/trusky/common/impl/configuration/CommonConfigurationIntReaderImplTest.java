package org.trusky.common.impl.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.trusky.common.api.configuration.CommonConfigurationIntReader;

import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CommonConfigurationIntReaderImplTest {


	private CommonConfigurationIntReader getSut() {
		return new CommonConfigurationIntReaderImpl();
	}

	@ParameterizedTest
	@MethodSource("provideMandatoryIntValue")
	<T extends Throwable> void readMandatoryIntValue(String key, boolean keyExists, String value, int expectedValue,
													 Class<T> exceptionClass, String expectedExceptionMessage) {

		// GIVEN
		Properties props = Mockito.mock(Properties.class);

		when(props.containsKey(key)).thenReturn(keyExists);
		if (keyExists) {
			when(props.getProperty(key)).thenReturn(value);
		}

		// WHEN & THEN
		if (exceptionClass == null) {
			assertDoesNotThrow(() -> getSut().readMandatoryIntValue(props, key)).equals(expectedValue);

		} else {
			T ex = assertThrows(exceptionClass, () -> getSut().readMandatoryIntValue(props, key));
			assertThat(ex.getMessage()).isEqualTo(expectedExceptionMessage);
		}

	}

	public static Stream<Arguments> provideMandatoryIntValue() {

		final String key = "Beispiel.key";
		final String keyNotPresentText = "No integer value found for key '" + key + "'.";
		final String invalidValueTypeText = "Value 'Wert' under key '" + key + //
				"' is not a valid integer value.";

		final boolean VALUE_EXISTS = true;
		final boolean VALUE_MISSING = false;


		return Stream.of( //

				// No entry present
				Arguments.of(key, VALUE_MISSING, "", 0, IllegalStateException.class, keyNotPresentText), //

				// A String instead of an integer
				Arguments.of(key, VALUE_EXISTS, "Wert", 0, NumberFormatException.class, invalidValueTypeText),

				// Normal integer
				Arguments.of(key, VALUE_EXISTS, "7", 7, null, ""),

				// Integer with +
				Arguments.of(key, VALUE_EXISTS, "+14", 14, null, ""),

				// Integer with + and space
				Arguments.of(key, VALUE_EXISTS, "+ 24", 24, null, ""),

				// Negative integer
				Arguments.of(key, VALUE_EXISTS, "-3", -3, null, ""),

				// Negative integer with space
				Arguments.of(key, VALUE_EXISTS, "- 16", -16, null, "")

						);
	}

	@ParameterizedTest
	@MethodSource("provideMadatoryPositveIntValueWithZero")
	void readMandatoryPositiveIntValueWithZero( //
												String key, //
												int valueRead, //
												boolean isKeyPresent, //
												Class<? extends Exception> exceptionClass, //
												String exceptionMessage //
											  ) {

		// GIVEN
		Properties props = Mockito.mock(Properties.class);

		when(props.containsKey(key)).thenReturn(isKeyPresent);
		if (isKeyPresent) {
			when(props.getProperty(key)).thenReturn("" + valueRead);
		}

		// WHEN & THEN
		if (exceptionClass == null) {
			assertDoesNotThrow(() -> getSut().readManadtoryPositiveIntValue(props, key, true)).equals(valueRead);

		} else {
			Exception ex = assertThrows(exceptionClass, () -> getSut().readManadtoryPositiveIntValue(props, key,
					true));
			assertThat(ex.getMessage()).isEqualTo(exceptionMessage);
		}
	}

	public static Stream<Arguments> provideMadatoryPositveIntValueWithZero() {

		final String key = "Beispiel.key";
		final boolean KEY_IS_PRESENT = true;
		final boolean KEY_NOT_PRESENT = false;

		return Stream.of( //
				// Key not present
				Arguments.of(key, //
						-1, //
						KEY_NOT_PRESENT, //
						IllegalStateException.class, //
						"No integer value found for key '" + key + "'."), //

				// Negativer Wert
				Arguments.of(key, //
						-1, //
						KEY_IS_PRESENT, //
						IllegalArgumentException.class, //
						"Integer value for '" + key + "' (actual value -1) is not expected to be negative."), //


				// Zero
				Arguments.of(key, //
						0, //
						KEY_IS_PRESENT, //
						null, //
						""), //


				// psitive value
				Arguments.of(key, //
						17, //
						KEY_IS_PRESENT, //
						null, //
						"") //
						);
	}

	@ParameterizedTest
	@MethodSource("provideMadatoryPositveIntValueWithoutZero")
	void testReadMandatoryPositiveIntValueWithoutZero( //
													   String key, //
													   int valueRead, //
													   boolean isKeyPresent, //
													   Class<? extends Exception> exceptionClass, //
													   String exceptionMessage //
													 ) {

		// GIVEN
		Properties props = Mockito.mock(Properties.class);

		when(props.containsKey(key)).thenReturn(isKeyPresent);
		if (isKeyPresent) {
			when(props.getProperty(key)).thenReturn("" + valueRead);
		}

		// WHEN & THEN
		if (exceptionClass == null) {
			assertDoesNotThrow(() -> getSut().readManadtoryPositiveIntValue(props, key, false)).equals(valueRead);

		} else {
			Exception ex = assertThrows(exceptionClass, () -> getSut().readManadtoryPositiveIntValue(props, key,
					false));
			assertThat(ex.getMessage()).isEqualTo(exceptionMessage);
		}

	}

	public static Stream<Arguments> provideMadatoryPositveIntValueWithoutZero() {

		final String key = "Beispiel.key";
		final boolean KEY_IS_PRESENT = true;
		final boolean KEY_NOT_PRESENT = false;

		return Stream.of( //
				// Key not present
				Arguments.of(key, //
						-1, //
						KEY_NOT_PRESENT, //
						IllegalStateException.class, //
						"No integer value found for key '" + key + "'."), //

				// Negativer Wert
				Arguments.of(key, //
						-1, //
						KEY_IS_PRESENT, //
						IllegalArgumentException.class, //
						"Integer value for '" + key + "' (actual value -1) is not expected to be negative."), //


				// Zero
				Arguments.of(key, //
						0, //
						KEY_IS_PRESENT, //
						IllegalArgumentException.class, //
						"Integer value for '" + key + "' (actual value 0) is not expected to become zero."), //


				// psitive value
				Arguments.of(key, //
						17, //
						KEY_IS_PRESENT, //
						null, //
						"") //
						);
	}


	@ParameterizedTest
	@MethodSource("provideNegativeIntParameterValues")
	void testMandatoryNegativeIntValue(//
									   String key, //
									   int valueRead, //
									   boolean isKeyPresent, //
									   boolean allowZero, //
									   Class<? extends Exception> exceptionClass, //
									   String exceptionMessage //
									  ) {
		// GIVEN
		Properties props = Mockito.mock(Properties.class);

		when(props.containsKey(key)).thenReturn(isKeyPresent);
		if (isKeyPresent) {
			when(props.getProperty(key)).thenReturn("" + valueRead);
		}

		// WHEN & THEN
		if (exceptionClass == null) {
			assertDoesNotThrow(() -> getSut().readManadtoryNegativeIntValue(props, key, allowZero)).equals(valueRead);

		} else {
			Exception ex = assertThrows(exceptionClass, () -> getSut().readManadtoryNegativeIntValue(props, key));
			assertThat(ex.getMessage()).isEqualTo(exceptionMessage);
		}

	}

	public static Stream<Arguments> provideNegativeIntParameterValues() {


		final String key = "Beispiel.key";
		final boolean KEY_IS_PRESENT = true;
		final boolean KEY_NOT_PRESENT = false;
		final boolean ALLOW_ZERO = true;
		final boolean NO_ZERO_ALLOWED = false;

		return Stream.of( //
				// Key not present
				Arguments.of(key, //
						-1, //
						KEY_NOT_PRESENT, //
						ALLOW_ZERO, // Doesn't matter
						IllegalStateException.class, //
						"No integer value found for key '" + key + "'."), //

				// Positive value
				Arguments.of(key, //
						1, //
						KEY_IS_PRESENT, //
						ALLOW_ZERO, // Doesn't matter
						IllegalArgumentException.class, //
						"Integer value for '" + key + "' (actual value 1) is not expected to be positive."), //


				// Zero - denied
				Arguments.of(key, //
						0, //
						KEY_IS_PRESENT, //
						NO_ZERO_ALLOWED, //
						IllegalArgumentException.class, //
						"Integer value for '" + key + "' (actual value 0) is not expected to become zero."), //

				// Zero - allowed
				Arguments.of(key, //
						0, //
						KEY_IS_PRESENT, //
						ALLOW_ZERO, //
						null, //
						"") //

						);
	}


	@ParameterizedTest
	@MethodSource("provideArgsForOptionalRead")
	void readOptionalIntValue(String key, //
							  int valueRead, //
							  boolean isKeyPresent) {

		// GIVEN
		Properties props = Mockito.mock(Properties.class);

		when(props.containsKey(key)).thenReturn(isKeyPresent);
		if (isKeyPresent) {
			when(props.getProperty(key)).thenReturn("" + valueRead);
		}


		// WHEN
		Optional<Integer> optResult = getSut().readOptionalIntValue(props, key);

		// THEN
		assertThat(optResult.isPresent()).isEqualTo(isKeyPresent);
		if (isKeyPresent) {
			assertThat(optResult.get()).isEqualTo(valueRead);
		}
	}

	public static Stream<Arguments> provideArgsForOptionalRead() {

		final String key = "Beispiel.key";
		final boolean KEY_IS_PRESENT = true;
		final boolean KEY_NOT_PRESENT = false;

		return Stream.of( //
				Arguments.of(key, 0, KEY_NOT_PRESENT), //
				Arguments.of(key, 12, KEY_IS_PRESENT) //
						);
	}

	@Test
	void readReadOptionalIntValueWithDefaullt() {

		final String key = "Beispiel.key";
		final boolean KEY_IS_PRESENT = true;

		Properties props = Mockito.mock(Properties.class);

		when(props.containsKey(key)).thenReturn(false);


		// WHEN
		int result = getSut().readOptionalIntValue(props, key, 18);

		// THEN
		assertThat(result).isEqualTo(18);


	}

	@ParameterizedTest
	@MethodSource("provideForOptionalPositiveIntValueWithoutZero")
	void readOptionalPositveIntValueWithoutZero(String key, boolean isKeyPresent, String propertyValue, int value,
												Class<Exception> expectedException) {

		// GIVEN
		Properties props = Mockito.mock(Properties.class);
		when(props.containsKey(key)).thenReturn(isKeyPresent);
		if (isKeyPresent) {
			when(props.getProperty(key)).thenReturn(propertyValue);
		}

		// WHEN & THEN
		CommonConfigurationIntReader sut = getSut();
		if (expectedException == null) {
			Optional<Integer> result = sut.readOptionalPositveIntValueWithoutZero(props, key);

			assertThat(result.isPresent()).isEqualTo(isKeyPresent);
			if (isKeyPresent) {
				assertThat(result.get()).isEqualTo(value);
			}

		} else {
			assertThrows(expectedException, () -> sut.readOptionalPositveIntValueWithoutZero(props, key));
		}

	}

	private static Stream<Arguments> provideForOptionalPositiveIntValueWithoutZero() {

		final String key = "Beispiel.key";
		final boolean KEY_IS_PRESENT = true;
		final boolean KEY_NOT_PRESENT = false;
		final int VALUE_DOESNT_MATTERS = 0;

		return Stream.of( //
				Arguments.of(key, KEY_NOT_PRESENT, "", VALUE_DOESNT_MATTERS, null), //
				Arguments.of(key, KEY_IS_PRESENT, "1", 1, null), //
				Arguments.of(key, KEY_IS_PRESENT, "0", 0, IllegalArgumentException.class), //
				Arguments.of(key, KEY_IS_PRESENT, "-1", -1, IllegalArgumentException.class), //
				Arguments.of(key, KEY_IS_PRESENT, "nonsense", VALUE_DOESNT_MATTERS, NumberFormatException.class)

						);
	}
}