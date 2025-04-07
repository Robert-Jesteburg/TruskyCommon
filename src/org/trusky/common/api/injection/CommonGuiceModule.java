/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.injection;

import com.google.inject.AbstractModule;
import org.trusky.common.api.startparameters.builder.module.CommonParserBuilderModule;
import org.trusky.common.api.startparameters.module.CommonStartparametersModule;
import org.trusky.common.api.util.CommonUtilModule;
import org.trusky.common.impl.application.CommonApplicationModule;

/**
 * Specify this to build the injector with all the dependencies for the common library. You can specify additional
 * (that is, modules describing your own project's injection rules) while creating the injector.
 */
public class CommonGuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();

		install(new CommonStartparametersModule());
		install(new CommonParserBuilderModule());
		install(new CommonUtilModule());
		install(new CommonApplicationModule());


		//bind(CommandLine.class).to(CommandLineImpl.class);


	}
}
