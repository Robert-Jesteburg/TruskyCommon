/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.util;

import org.trusky.common.api.util.CommonSystemSettings;

public class CommonSystemSettingsImpl implements CommonSystemSettings {

	@Override
	public String getHomeDir() {
		return System.getProperty("user.home");
	}

	@Override
	public String getPathSeparator() {
		return java.io.File.separator;
	}


}
