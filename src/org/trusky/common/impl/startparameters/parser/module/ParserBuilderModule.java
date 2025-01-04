/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.startparameters.parser.module;

import com.google.inject.AbstractModule;
import org.trusky.common.api.startparameters.builder.BaseDirOptionParserBuilder;
import org.trusky.common.impl.startparameters.parser.BaseDirOptionParserBuilderImpl;

public class ParserBuilderModule extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();

		bind(BaseDirOptionParserBuilder.class).to(BaseDirOptionParserBuilderImpl.class);

		// FIXME add the bindings for the other builders
		// FIXME add the module describing the bindings of the options (may be not necessary?)
	}
}
