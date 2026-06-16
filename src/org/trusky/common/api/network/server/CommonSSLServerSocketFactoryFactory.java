package org.trusky.common.api.network.server;

import org.trusky.common.api.configuration.KeystoreConfiguration;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Factory interface for creating CommonSSLServerSocketFactory instances
 * based on the provided KeystoreConfiguration. Hides all the technical steps required to create the factory.
 */
public interface CommonSSLServerSocketFactoryFactory {

	CommonSSLServerSocketFactory prepareSSLServerSocketFactory(KeystoreConfiguration config)
	throws KeyManagementException, IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException;
}
