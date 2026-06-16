package org.trusky.common.api.configuration;

import java.util.function.Function;

/**
 * The implemmentation willl reside in the application code.
 *
 * @param <CONFIGTYPE> The type of the configuration.
 */
public interface ConfigurationSupplierFactory<CONFIGTYPE extends Configuration> {

	/**
	 * Creates a configuration supplier for the given configuration type.
	 *
	 * @param configFunction A function that takes the path to the configuration file and returns a
	 *                       ConfigurationSupplier.
	 * @param optionNames    The names of the command line options for the path. These parmeters are <b>ordered</b>,
	 *                       as the
	 *                       path will created by examine the values from left to right.
	 * @return a ConfigurationSupplier for the given configuration type.
	 */
	ConfigurationSupplier<CONFIGTYPE> createConfigurationSupplier(Function<String, ConfigurationSupplier<CONFIGTYPE>> configFunction, String... optionNames);
}
