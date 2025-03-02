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

import org.trusky.common.api.startparameters.optionvalue.OptionValue;

/**
 * Each parser gets an instance of this interface to put the created StartOption into the container.
 */
public interface EditableStartParameterContainer extends StartParameterContainer {

	public void addStartOption(StartOption<? extends OptionValue<?>, ? extends OptionValue<?>> startOption);
}
