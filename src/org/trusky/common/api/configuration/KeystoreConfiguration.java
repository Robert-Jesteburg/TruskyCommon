package org.trusky.common.api.configuration;

public interface KeystoreConfiguration {

	String getKeystoreType();

	String getKeystorePath();

	String getKeystorePassword();

	String getKeymanagerType();
}
