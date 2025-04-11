/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.util;

import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;
import org.trusky.common.api.util.CommonStartparametersUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CommonStartParametersUtilImpl implements CommonStartparametersUtils {

	@Override
	public String getStringStartparameterWithDefault(String optionName,
													 Function<String, List<StringOptionValue>> parameterSupplier,
													 String defaultValue)
	throws IllegalArgumentException {

		if (optionName == null || optionName.isBlank()) {
			throw new IllegalArgumentException("Option name must be given,");
		}

		if (parameterSupplier == null) {
			throw new IllegalArgumentException("Parameter supplier must be supplied for option \"" + optionName +
					"\"");
		}

		List<StringOptionValue> settings = parameterSupplier.apply(optionName);

		if (!settings.isEmpty()) {

			if (settings.size() > 1) {
				throw new IllegalArgumentException("More than one entry for option \"" + optionName + "\" given.");
			}

			StringOptionValue optionValue = settings.get(0);
			Optional<String> optValue = optionValue.getValue();
			return optValue.orElse(defaultValue);

		} else {
			return defaultValue;
		}

	}

	@Override
	public List<StringOptionValue> toStringOptionList(List<StartOption<? extends OptionValue<?>, ?
			extends OptionValue<?>>> list)
	throws IllegalArgumentException {


		if (list == null) {
			throw new IllegalArgumentException("The list must not be empty.");
		}

		List<StringOptionValue> returnList = new ArrayList<>();


		for (StartOption<? extends OptionValue<?>, ? extends OptionValue<?>> startOption : list) {

			Optional<? extends OptionValue<?>> defaultValue = startOption.getDefaultValue();
			if (defaultValue.isEmpty()) {
				returnList.add(new StringOptionValue());

			} else {
				if (!(defaultValue.get() instanceof StringOptionValue)) {
					throw new IllegalArgumentException("The element in the list is not of type StringOptionValue.");
				}

				returnList.add((StringOptionValue) defaultValue.get());
			}
		}

		return returnList;

	}

}
