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

import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;
import org.trusky.common.api.util.CommonStartparametersUtils;

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
			if (optValue.isPresent()) {
				return optValue.get();
			} else {
				return defaultValue;
			}


		} else {
			return defaultValue;
		}

	}

}
