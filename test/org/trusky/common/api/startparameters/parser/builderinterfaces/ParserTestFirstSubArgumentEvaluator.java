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

import org.trusky.common.api.startparameters.optionvalue.OptionValue;

public interface ParserTestFirstSubArgumentEvaluator {

	/**
	 * Use this method for sub values the start option under test is expected to deliver (depends on the arguments
	 * given in preceding steps).
	 *
	 * @param subOptionName The name of the parameter, normally starting with a single dash, must not be NULL
	 * @param subValue      The value the start option is expected to deliver under the subOptionName
	 * @return next interface
	 */
	ParserTestFurtherSubArgumentEvaluator withFirstSubValue(String subOptionName, OptionValue<?> subValue);

	/**
	 * The option under test does not have any sub value.
	 *
	 * @return TestExecuter
	 */
	ParserTestExecuter withNoSubValue();
}
