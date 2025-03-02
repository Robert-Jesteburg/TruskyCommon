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

public interface CommandLine {

	/**
	 * @return TRUE if #peekNext and #next return meaningful values.
	 */
	public boolean hasParameter();

	/**
	 * Get the actual parameter string without moving to the next parameter.
	 *
	 * @return The actual parameter.
	 */
	public String peekNext();

	/**
	 * Get actual parameter an advance to the next one.
	 *
	 * @return The actual parameter.
	 */
	public String next();
}
