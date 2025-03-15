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

import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.builder.OptionParserBuilder;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;

import java.util.List;

public interface StartparameterManager {

	/**
	 * Configure the expected start parameters by supplying the list of parameter builders.
	 *
	 * @param parameterBuilderList List of builders, must not be NULL.
	 */
	void prepareParameters(List<OptionParserBuilder> parameterBuilderList);

	/**
	 * Do the actual parsing.
	 *
	 * @param args The arguments passed into the main method. Mjst not be NULL.
	 */
	void parseCommandLine(String[] args) throws StartParameterException;

	/**
	 * Obtain the settings for the option given by optionName.
	 *
	 * @param optionName option name, must not be NULL.
	 * @return A list of options for the given name.
	 */
	List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> getOption(String optionName);

	/**
	 * @return TRUE if parsing resulted in any start option being found (this includes options not present on the
	 * command line but generated from their default values).
	 */
	boolean hasAnyStartOption();
}
