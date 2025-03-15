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

import org.trusky.common.api.startparameters.CommandLineProcessor;
import org.trusky.common.api.startparameters.EditableStartparameterParserContainer;
import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.StartParameterContainer;
import org.trusky.common.api.startparameters.builder.OptionParserBuilder;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;

import javax.inject.Inject;
import java.util.List;

public class StartparameterManagerImpl implements StartparameterManager {

	private final EditableStartparameterParserContainer parserContainer;
	private final CommandLineProcessor commandLineProcessor;
	private StartParameterContainer parameterContainer;

	private boolean parsersHaveBeenSet = false;

	@Inject
	StartparameterManagerImpl(EditableStartparameterParserContainer parserContainer,
							  CommandLineProcessor commandLineProcessor) {

		this.parameterContainer = null;
		this.parserContainer = parserContainer;
		this.commandLineProcessor = commandLineProcessor;

	}

	@Override
	public void prepareParameters(List<OptionParserBuilder> parameterBuilderList) {

		if (parameterBuilderList == null) {
			throw new IllegalArgumentException("Builder list must not be empty.");
		}

		if (parsersHaveBeenSet) {
			throw new IllegalStateException("prepareParameters must not be called twice.");
		}

		parameterBuilderList.stream()
				.map(OptionParserBuilder::build)
				.forEach(parserContainer::addParser);

		parsersHaveBeenSet = true;

	}

	@Override
	public void parseCommandLine(String[] args) throws StartParameterException {

		if (args == null) {
			throw new IllegalArgumentException("Argument array must nor be NULL.");
		}

		if (!parsersHaveBeenSet) {
			throw new IllegalStateException("Please call prepareParameters() first.");
		}

		if (parameterContainer != null) {
			throw new IllegalStateException("parseCommandLine must not be called twice.");
		}

		parameterContainer = commandLineProcessor.parseCommandLine(args, parserContainer);

	}

	@Override
	public List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> getOption(String optionName) {

		if (parameterContainer == null) {
			throw new IllegalStateException("Please call parseCommandLine() before calling this method.");
		}

		return parameterContainer.getOption(optionName);
	}

	@Override
	public boolean hasAnyStartOption() {

		if (parameterContainer == null) {
			return false;
		}

		return !parameterContainer.isEmpty();
	}
}
