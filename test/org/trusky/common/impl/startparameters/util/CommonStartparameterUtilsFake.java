/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.startparameters.util;

import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;
import org.trusky.common.api.util.CommonStartparametersUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * A fake object for tests of classes that need an instance of CommonStartparametersUtils.
 */
public class CommonStartparameterUtilsFake implements CommonStartparametersUtils {

	private Optional<String> generalReturnValue = Optional.empty();
	private boolean alwaysReturnNull = false;
	private Map<String, String> returnMap = new HashMap<>();
	private Map<String, String> exceptionMap = new HashMap<>();

	public void reset() {
		returnMap.clear();
		exceptionMap.clear();
		alwaysReturnNull = false;
	}

	public void alwaysReturn(String returnValue) {
		if (returnValue == null) {
			alwaysReturnNull = true;
		} else {
			generalReturnValue = Optional.of(returnValue);
		}
	}

	public void whenOptionnameThenReturn(String optionName, String returnValue) {
		returnMap.put(optionName, returnValue);
	}

	public void whenOptionnameThenException(String optionName, String exceptionMessage) {
		exceptionMap.put(optionName, exceptionMessage);
	}

	@Override
	public String getStringStartparameterWithDefault(String optionName,
													 Function<String, List<StringOptionValue>> parameterSupplier,
													 String defaultValue)
	throws IllegalArgumentException {


		if (exceptionMap.containsKey(optionName)) {
			throw new IllegalArgumentException(exceptionMap.get(optionName));
		}

		if (alwaysReturnNull) {
			return null;
		}

		if (generalReturnValue.isPresent()) {
			return generalReturnValue.get();
		}

		String returnValue = null;
		if (returnMap.containsKey(optionName)) {
			returnValue = returnMap.get(optionName);
		}

		return returnValue;
	}

	@Override
	public List<StringOptionValue> toStringOption(List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> list)
	throws IllegalArgumentException {

		// Use the real implementation
		CommonStartparametersUtils startparameterUtils = InjectorFactory.getInstance(CommonStartparametersUtils.class);
		return startparameterUtils.toStringOption(list);
	}


}
