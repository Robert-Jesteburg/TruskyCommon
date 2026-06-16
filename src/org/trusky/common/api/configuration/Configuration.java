package org.trusky.common.api.configuration;

/**
 * Tagging interface to allow the definition of a ConfigurationReader that returns an abstract Configuration.
 */
public interface Configuration {


	int getConnectionPort();

	boolean hasSpecificShutdownPort();

	int getShutdownPort();

	String getKeystoreType();

	String getKeystorePath();

	String getKeystorePassword();

	String getKeymanagerType();
}
