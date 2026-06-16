/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.util;

import com.google.inject.AbstractModule;
import org.trusky.common.impl.util.*;

public class CommonUtilModule extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();

		bind(CommonStringUtilities.class).to(CommonStringUtilitiesImpl.class);
		bind(CommonSystemSettings.class).to(CommonSystemSettingsImpl.class);
		bind(CommonStartparametersUtils.class).to(CommonStartParametersUtilImpl.class);
		bind(CommonLog4JConfigurationUtils.class).to(CommonLog4JConfigurationUtilsImpl.class);
		bind(CommonFileUtilities.class).to(CommonFileUtilitiesImpl.class);
		bind(CommonPathBuilder.class).to(CommonPathBuilderImpl.class);
	}
}
