/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.startparameters;

import org.trusky.common.api.startparameters.parser.StartOptionParser;

import java.util.Optional;

/**
 * Container to hold all the defined parsers.
 */
public interface StartparameterParserContainer {

	public int getParserCount();

	public StartOptionParser getParser(int index);

	Optional<StartOptionParser> findParserForOption(CommandLine nextOption);
}
