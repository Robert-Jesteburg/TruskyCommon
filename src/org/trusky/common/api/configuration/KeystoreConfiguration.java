package org.trusky.common.api.configuration;

public interface KeystoreConfiguration extends Configuration {

	String getKeystoreType();

	String getKeystorePath();

	String getKeystorePassword();

	String getKeymanagerType();
}
