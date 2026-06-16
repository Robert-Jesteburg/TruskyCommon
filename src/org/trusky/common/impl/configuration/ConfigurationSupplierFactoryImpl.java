package org.trusky.common.impl.configuration;

import org.trusky.common.api.configuration.Configuration;
import org.trusky.common.api.configuration.ConfigurationSupplier;
import org.trusky.common.api.configuration.ConfigurationSupplierFactory;
import org.trusky.common.api.util.CommonPathBuilder;
import org.trusky.common.api.util.CommonStartparametersUtils;
import org.trusky.common.impl.application.StartparameterManager;

import java.util.function.Function;

public class ConfigurationSupplierFactoryImpl<CONFIGTYPE extends Configuration> implements
		ConfigurationSupplierFactory<CONFIGTYPE> {

	private final CommonStartparametersUtils commonStartparametersUtils;
	private final StartparameterManager startparameterManager;
	private final CommonPathBuilder pathBuilder;

	public ConfigurationSupplierFactoryImpl(CommonStartparametersUtils commonStartparametersUtils,
											StartparameterManager startparameterManager,
											CommonPathBuilder pathBuilder) {
		this.commonStartparametersUtils = commonStartparametersUtils;
		this.startparameterManager = startparameterManager;
		this.pathBuilder = pathBuilder;
	}

	@Override
	public ConfigurationSupplier<CONFIGTYPE> createConfigurationSupplier(Function<String,
			ConfigurationSupplier<CONFIGTYPE>> configFunction, String... optionNames) {

		String fullPathToConfigFile = pathBuilder.createPathNameAsAbsolutePath(getOptionFromNameFunction(), //
				optionNames);

		// Der übergebene Function liefert bereits einen ConfigurationSupplier; wir wenden ihn auf den Pfad an.
		return configFunction.apply(fullPathToConfigFile);

	}

	private Function<String, String> getOptionFromNameFunction() {
		return o -> commonStartparametersUtils.getStringStartparameterWithDefault( //
				o, //
				name -> commonStartparametersUtils.toStringOptionList(startparameterManager.getOption(name)), //
				"");
	}
}
