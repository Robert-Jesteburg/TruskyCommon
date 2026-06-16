/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.application;

/* If JavaFX is not present on the module path during compilation in some
 * environments, avoid a hard dependency here to keep the module compilable.
 * The class intentionally does not extend javafx.application.Application
 * to allow compiling without JavaFX. If JavaFX is required at runtime,
 * consider adding a small adapter class that extends Application in a
 * separate module which requires javafx.graphics.
 */
public abstract class AbstractFxApplication {

	public static void main(String[] args) {

	}

}
