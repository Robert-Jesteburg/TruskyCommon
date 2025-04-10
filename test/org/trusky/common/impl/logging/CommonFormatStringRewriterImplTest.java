/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.logging;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CommonFormatStringRewriterImplTest {

	private static Stream<Arguments> supplyTestStrings() {

		return Stream.of( //
				Arguments.of("{} changed", "%s changed"), //
				Arguments.of("The value {} was unexpected.", "The value %s was unexpected."), //
				Arguments.of("For {}, parameter {} is required.", "For %s, parameter %s is required."), //
				Arguments.of("Last value read was: {}", "Last value read was: %s"), //
				Arguments.of("Nothing to replace at all.", "Nothing to replace at all."),
				//
				Arguments.of("Don't replace \\{}, as it's escaped", "Don't replace \\{}, as it's escaped")

						);
	}

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	private CommonFormatStringRewriter getSut() {
		return new CommonFormatStringRewriterImpl();
	}

	@ParameterizedTest
	@MethodSource("supplyTestStrings")
	void testStringReplacement(String log4JFmtString, String expectedResult) {

		String result = getSut().fromLog4JToNormalString(log4JFmtString);
		assertThat(result).isEqualTo(expectedResult);

	}
}