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

import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;

import java.util.List;
import java.util.function.Function;

public interface CommonStartparametersUtils {

	/**
	 * Get the (default) value of an StringOption.
	 *
	 * <p>
	 * It is assumed that the option is present zero or one time.
	 * </p>
	 *
	 * @param optionName        if the list contains two or more entries an IllegalArgumentException will be thrown.
	 * @param parameterSupplier Function that returns the list of start options for the given option name. The list
	 *                          is expected to contain one or zero elements;
	 * @param defaultValue      The default value that the methoh should return in cas the option in question was not
	 *                          given or if there was no/an empty default value specified
	 * @return the computed String
	 */
	String getStringStartparameterWithDefault(String optionName,
											  Function<String, List<StringOptionValue>> parameterSupplier,
											  String defaultValue)
	throws IllegalArgumentException;

	/**
	 * Converts the list to a list of StringOptionValues. If the list contains any other StartOption type than
	 * StringOptionValue an IllegalArgumentException will be thrown.
	 *
	 * @param list Must not be null, and must not contain any element that isn't a StringOptionType.
	 * @return A list of StringOptionTyoe elements
	 * @throws IllegalArgumentException see above.
	 */
	List<StringOptionValue> toStringOptionList( //
												List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> list //
											  ) throws IllegalArgumentException;
}
