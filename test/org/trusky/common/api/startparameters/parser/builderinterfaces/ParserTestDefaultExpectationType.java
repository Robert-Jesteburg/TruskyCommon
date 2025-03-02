/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.startparameters.parser.builderinterfaces;

import java.util.Optional;
import java.util.function.BiFunction;

public interface ParserTestDefaultExpectationType<DEFAULT_VAL_TYPE> {

	ParserTestTestExecutionType setDefaultValueNotExpected();

	ParserTestTestExecutionType setExpectedDefaultValue(Object defaultValue);

	/**
	 * Set an expected default value along with a lambda expression to check the actual value later on.
	 *
	 * @param defaultValue
	 * @param checkDefaultValueFunc
	 * @return ParserTestTestExecutionType interface
	 */
	public ParserTestTestExecutionType setExpectedDefaultValue(Object defaultValue,
															   BiFunction<Optional<DEFAULT_VAL_TYPE>, String, Boolean> checkDefaultValueFunc);
}
