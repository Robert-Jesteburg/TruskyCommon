package org.trusky.common.api.configuration;

public interface ServerPortConfiguration extends Configuration {

	/**
	 * @return the port to listen for normal connections
	 */
	int getConnectionPort();

	/**
	 * @return the unsecured port used to allow new clients to request the certificate
	 */
	int getUnsecuredConnectionPort();

	/**
	 * @return TRUE if a shutdown port has been specified
	 */
	boolean hasSpecificShutdownPort();

	/**
	 * @return the shutdown port
	 */
	int getShutdownPort();
}
