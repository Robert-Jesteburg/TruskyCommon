package org.trusky.common.api.configuration;

/**
 * Server configuration, combining settings required by the server with its keystore and port configuration.
 */
public interface ServerConfiguration extends Configuration, KeystoreConfiguration, ServerPortConfiguration {
}
