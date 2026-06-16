package org.trusky.common.api.configuration;

/**
 * A configuration supplier is responsible to provide the initialized configuration object. That is, it is also
 * responsible for reading any configuration files, parsing command line arguments, etc.
 *
 * <p>While being defined in the common library the implementation will reside in the concrete product.</p>
 *
 * @param <CONFIGTYPE> Configurations may vary significantly depending on the concrete application. For example,
 *                     while a server will have access to a database, an android application may not. Thus, the server
 *                     configuration will have methods for connecting the database that the android configuration
 *                     will not.
 *                     The CONFIGTYPE template parameter allows for strong typing of the configuration object.
 */
public interface ConfigurationSupplier<CONFIGTYPE extends Configuration> {

	/**
	 *
	 * @return the initialized configuration object of the correct type.
	 */
	CONFIGTYPE getConfiguration();
}
