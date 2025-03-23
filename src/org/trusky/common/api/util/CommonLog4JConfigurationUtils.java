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

import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;

import java.util.List;
import java.util.function.Function;

public interface CommonLog4JConfigurationUtils {

	/**
	 * Returns the base path as absolute path. The path does not end with the path separator.
	 *
	 * @param baseDirOptionName Option name for the base directory
	 * @param appDirOptionName  Option name for the application name option
	 * @param parameterSupplier The parameter supplier to get the command line option the user entered.
	 * @return see above.
	 */
	String getLog4JBasePath(String baseDirOptionName, String appDirOptionName, Function<String,
			List<StringOptionValue>> parameterSupplier);

	/**
	 * Get the name of the configuration file name.
	 *
	 * @param log4JFileNameOptionName Name of the option for the configuration file name
	 * @param parameterSupplier       The parameter supplier to get the command line option the user entered.
	 * @return see above.
	 */
	String getLog4JConfigurationFileName(String log4JFileNameOptionName,
										 Function<String, List<StringOptionValue>> parameterSupplier);

	/**
	 * Convenient method to get the absolute file name (including path) of the log4J configuration file.
	 *
	 * @param baseDirOptionName       Option name for the base directory
	 * @param appDirOptionName        Option name for the application name option
	 * @param log4JFileNameOptionName Name of the option for the configuration file name
	 * @param parameterSupplier       The parameter supplier to get the command line option the user entered.
	 * @return see above.
	 */
	String getFullConfigurationFileNameWithPath(String baseDirOptionName, String appDirOptionName,
												String log4JFileNameOptionName, Function<String,
					List<StringOptionValue>> parameterSupplier);

	void configureLog4J(String pathAndLocationOfConfigurationFile);
}
