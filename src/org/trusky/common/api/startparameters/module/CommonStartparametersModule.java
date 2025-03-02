/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.startparameters.module;

import com.google.inject.AbstractModule;
import org.trusky.common.api.startparameters.StartParameterContainer;
import org.trusky.common.impl.startparameters.StartParameterContainerImpl;

public class CommonStartparametersModule extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();

		/*
		 * The CommandLine is an internal structure that is constructed in the base class of the (server-)application.
		 * It will never be constructed by the client code, so the injector doesn't need to know about.
		 */
		// bind(CommandLine.class).to(CommandLineImpl.class);

		bind(StartParameterContainer.class).to(StartParameterContainerImpl.class);

		// Das hier sollte nie über das Interface erzeugt werden.
		// bind(EditableStartparameterParserContainer.class).to(StartParameterContainerImpl.class);

	}
}
