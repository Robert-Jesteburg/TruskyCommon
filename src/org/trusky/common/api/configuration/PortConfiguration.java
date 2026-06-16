package org.trusky.common.api.configuration;

public interface PortConfiguration {

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

}
