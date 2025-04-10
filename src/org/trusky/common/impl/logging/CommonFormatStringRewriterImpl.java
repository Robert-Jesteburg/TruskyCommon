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

public class CommonFormatStringRewriterImpl implements CommonFormatStringRewriter {

	@Override
	public String fromLog4JToNormalString(String log4JString) {

		String replacements = replaceAtTheBeginning(log4JString);
		replacements = replacements.replaceAll("([^\\\\])\\{\\}", "$1%s");
		return replacements;
	}

	private String replaceAtTheBeginning(String log4JString) {

		if (log4JString.startsWith("{}")) {
			return "%s" + log4JString.substring(2);

		}

		return log4JString;

	}
}
