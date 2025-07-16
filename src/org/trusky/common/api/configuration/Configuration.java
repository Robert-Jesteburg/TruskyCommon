package org.trusky.common.api.configuration;

/**
 * Tagging interface to allow the definition of a ConfigurationReader that returns an abstract Configuration.
 */
public interface Configuration {

	/**
	 * @return the port to listen for normall connectioons
	 */
	int getConnectionPort();

	/**
	 * @return TRUE if shutdown port has been specified and if it is different from the connection port.
	 */
	boolean hasSpecificShutdownPort();

	/**
	 * @return the shutdown port
	 */
	int getShutdownPort();

	String getKeystoreType();

	String getKeystorePath();

	String getKeystorePassword();

	String getKeymanagerType();
}
