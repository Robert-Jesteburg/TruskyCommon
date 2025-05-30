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

import com.google.inject.AbstractModule;
import org.trusky.common.api.logging.CommonLoggerFactory;

public class CommonLog4JModule extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();

		bind(CommonLoggerFactory.class).to(CommonLoggerFactoryImpl.class);
		bind(CommonFormatStringRewriter.class).to(CommonFormatStringRewriterImpl.class);
	}
}
