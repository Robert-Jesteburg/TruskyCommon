/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.startparameters.builder.module;

import com.google.inject.AbstractModule;
import org.trusky.common.api.startparameters.builder.BaseDirOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.ConfigurationFilenameOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.Log4JOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.ParamDirNameOptionParserBuilder;
import org.trusky.common.impl.startparameters.parser.BaseDirOptionParserBuilderImpl;
import org.trusky.common.impl.startparameters.parser.ConfigurationFilenameOptionParserBuilderImpl;
import org.trusky.common.impl.startparameters.parser.Log4JOptionParserBuilderImpl;
import org.trusky.common.impl.startparameters.parser.ParamDirNameOptionParserBuilderImpl;

public class CommonParserBuilderModule extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();

		bind(BaseDirOptionParserBuilder.class).to(BaseDirOptionParserBuilderImpl.class);
		bind(ConfigurationFilenameOptionParserBuilder.class).to(ConfigurationFilenameOptionParserBuilderImpl.class);
		bind(Log4JOptionParserBuilder.class).to(Log4JOptionParserBuilderImpl.class);
		bind(ParamDirNameOptionParserBuilder.class).to(ParamDirNameOptionParserBuilderImpl.class);

	}
}
