/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.application;

import com.google.common.base.Supplier;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;

public interface CommonLog4JConfigFileCreatorOrchestrator {

	String getLogPath( //
					   String baseDirOptionName, //
					   String appDirOptionName, //
					   Function<String, List<StringOptionValue>> parameterSupplier);

	void createFile( //
					 String baseDirOptionName, //
					 String appDirOptionName, //
					 String log4JFileNameOptionName, //
					 Function<String, List<StringOptionValue>> parameterSupplier, //
					 Supplier<InputStream> logTemplateSupplier) throws IOException;
}
