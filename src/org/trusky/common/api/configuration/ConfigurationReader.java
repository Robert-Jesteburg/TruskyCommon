package org.trusky.common.api.configuration;

import java.io.IOException;

public interface ConfigurationReader<CONFIGTYPE extends Configuration> {

	// Some general property names
	public static final String STANDARD_PORT = "connection.port";
	public static final String SHUTDOWN_PORT = "connection.shutdown.port";
	public static final String KEYSTORE_TYPE = "keystore.type"; // "JKS"
	public static final String KEYSTORE_PATH = "keystore.path";
	public static final String KEYSTORE_PASSWORD = "keystore.password";
	public static final String KEYMANAGER_TYPE = "keymanager.type"; // "SunX509"
	public static final String SSL_TYPE = "ssl.type"; // "TLS"

	CONFIGTYPE readConfiguration(String fullPath) throws IOException;
}
