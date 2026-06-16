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
import org.trusky.common.api.network.message.parser.CommonMessageParser;
import org.trusky.common.api.network.message.util.CommonIntSerializer;
import org.trusky.common.api.network.message.util.CommonMessageSerializer;
import org.trusky.common.impl.network.message.parser.CommonMessageParserImpl;
import org.trusky.common.impl.network.message.util.CommonIntSerializerImpl;
import org.trusky.common.impl.network.message.util.CommonMessageSerializerImpl;
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
		bind(CommonMessageParser.class).to(CommonMessageParserImpl.class);
		bind(CommonIntSerializer.class).to(CommonIntSerializerImpl.class);
		bind(CommonMessageSerializer.class).to(CommonMessageSerializerImpl.class);

		// FIXME hier die Serializer eintragen!
	}
}
