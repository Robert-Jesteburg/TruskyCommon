package org.trusky.common.impl.network.server;

import org.trusky.common.api.configuration.KeystoreConfiguration;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.logging.CommonLogger;
import org.trusky.common.api.logging.CommonLoggerFactory;
import org.trusky.common.api.network.server.CommonSSLServerSocketFactory;
import org.trusky.common.api.network.server.CommonSSLServerSocketFactoryFactory;
import org.trusky.common.api.network.socket.CommonKeyStore;
import org.trusky.common.api.network.socket.CommonKeymanagerFactoryWrapper;
import org.trusky.common.api.network.socket.CommonKeystoreParameters;
import org.trusky.common.api.network.socket.CommonSSLContext;
import org.trusky.common.api.network.socket.factory.CommonKeyStoreFactory;
import org.trusky.common.api.network.socket.factory.CommonKeymanagerFactoryFactory;
import org.trusky.common.api.network.socket.factory.CommonKeystoreParametersFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class CommonSSLServerSocketFactoryFactoryImpl implements CommonSSLServerSocketFactoryFactory {

	private final CommonLogger LOGGER;

	private final CommonKeystoreParametersFactory keystoreParametersFactory;
	private final CommonKeyStoreFactory keystoreFactory;
	private final CommonKeymanagerFactoryFactory keymanagerFactoryFactory;

	public CommonSSLServerSocketFactoryFactoryImpl(CommonKeystoreParametersFactory keystoreParametersFactory,
												   CommonKeyStoreFactory keystoreFactory,
												   CommonKeymanagerFactoryFactory keymanagerFactoryFactory) {

		CommonLoggerFactory clf = InjectorFactory.getInstance(CommonLoggerFactory.class);
		LOGGER = clf.getLogger(CommonSSLServerSocketFactoryFactoryImpl.class);

		this.keystoreParametersFactory = keystoreParametersFactory;
		this.keystoreFactory = keystoreFactory;
		this.keymanagerFactoryFactory = keymanagerFactoryFactory;
	}

	@Override
	public CommonSSLServerSocketFactory prepareSSLServerSocketFactory(KeystoreConfiguration config)
	throws KeyManagementException, IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {

		CommonKeystoreParameters keystoreParameters = keystoreParametersFactory.create(config.getKeystorePath(),
				config.getKeystorePassword(), config.getKeystoreType());

		CommonKeyStore keyStore;
		try {
			keyStore = keystoreFactory.create(keystoreParameters);
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException e) {

			LOGGER.error("Unable to obtain key store at " + config.getKeystorePath(), e);
			throw e;
		}

		CommonKeymanagerFactoryWrapper keymanagerFactoryWrapper;
		try {
			keymanagerFactoryWrapper = keymanagerFactoryFactory.create(config.getKeymanagerType(), keystoreParameters);

		} catch (NoSuchAlgorithmException e) {

			LOGGER.error("Unable to create key manager factory for type " + config.getKeymanagerType());
			throw e;
		}

		CommonSSLContext sslContext = InjectorFactory.getInstance(CommonSSLContext.class);
		try {
			sslContext.initForServer(keymanagerFactoryWrapper);
		} catch (KeyManagementException e) {
			LOGGER.error("Unable to init the SSL Cotext.", e);
			throw e;
		}

		CommonSSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
		return serverSocketFactory;
	}
}
