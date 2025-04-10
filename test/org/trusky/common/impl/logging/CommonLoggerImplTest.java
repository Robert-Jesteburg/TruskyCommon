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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.trusky.common.api.injection.CommonGuiceModule;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.logging.CommonLogger;

class CommonLoggerImplTest {

	@BeforeAll
	static void beforeAll() {
		InjectorFactory.setModule(new CommonGuiceModule());
	}

	@Test
	void testDebugMitException() {

		try {
			throwExampleException();
		} catch (IllegalArgumentException ia) {

			CommonLoggerFactoryImpl factory = new CommonLoggerFactoryImpl();
			CommonLogger logger = factory.getLogger(this.getClass());
			logger.debug("Test-Exception: ", ia);
		}

	}

	private void throwExampleException() {
		throw new IllegalArgumentException("A simple test exception.");
	}

}