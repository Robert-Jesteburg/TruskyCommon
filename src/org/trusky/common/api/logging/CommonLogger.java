/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.logging;

public interface CommonLogger {

	void info(String message);

	void info(String format, Object... args);

	void debug(String message);

	void debug(String format, Object... args);

	void debug(String message, Exception e);

	void trace(String message);

	void trace(String format, Object... args);

	void trace(String message, Exception e);

	void warn(String message);

	void warn(String format, Object... args);

	void warn(String message, Exception e);

	void error(String message);

	void error(String format, Object... args);

	void error(String message, Exception e);

}
