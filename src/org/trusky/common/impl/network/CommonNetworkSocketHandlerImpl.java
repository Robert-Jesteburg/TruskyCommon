package org.trusky.common.impl.network;

import org.trusky.common.api.configuration.Configuration;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.logging.CommonLogger;
import org.trusky.common.api.logging.CommonLoggerFactory;
import org.trusky.common.api.network.implementators.CommonConnectionProcessor;
import org.trusky.common.api.network.implementators.CommonNetworkSocketHandler;
import org.trusky.common.api.network.implementators.CommonShutdownSocketListener;
import org.trusky.common.api.network.implementators.factory.CommonConnectionProcessorFactory;
import org.trusky.common.api.network.implementators.factory.CommonShutdownSocketFactory;
import org.trusky.common.api.network.server.CommonSSLServerSocketFactory;
import org.trusky.common.api.network.server.ServerExitCodes;
import org.trusky.common.api.network.socket.*;
import org.trusky.common.api.network.socket.factory.CommonKeyStoreFactory;
import org.trusky.common.api.network.socket.factory.CommonKeymanagerFactoryFactory;
import org.trusky.common.api.network.socket.factory.CommonKeystoreParametersFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class CommonNetworkSocketHandlerImpl implements CommonNetworkSocketHandler {

	private final CommonKeystoreParametersFactory keystoreParametersFactory;
	private final CommonKeyStoreFactory keystoreFactory;
	private final CommonKeymanagerFactoryFactory keymanagerFactoryFactory;
	private final CommonLoggerFactory clf;

	private final CommonLogger LOGGER;

	@Inject
	public CommonNetworkSocketHandlerImpl(CommonKeystoreParametersFactory keystoreParametersFactory,
										  CommonKeyStoreFactory keystoreFactory,
										  CommonKeymanagerFactoryFactory keymanagerFactoryFactory,
										  CommonLoggerFactory loggerFactory) {
		this.keystoreParametersFactory = keystoreParametersFactory;
		this.keystoreFactory = keystoreFactory;
		this.keymanagerFactoryFactory = keymanagerFactoryFactory;
		this.clf = loggerFactory;

		LOGGER = clf.getLogger(this.getClass());
	}

	@Override
	public void initForTLSServer(Configuration configuration,
								 CommonConnectionProcessorFactory connectionProcessorFactory,
								 CommonShutdownSocketFactory commonShutdownSocketFactory)
	throws Exception {

		CommonKeystoreParameters keystoreParameters = keystoreParametersFactory.create(configuration.getKeystorePath()
				, configuration.getKeystorePassword(), configuration.getKeystoreType());

		CommonKeyStore keyStore;
		try {
			keyStore = keystoreFactory.create(keystoreParameters);
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {

			LOGGER.error("Unable to obtain key store at " + configuration.getKeystorePath(), e);
			throw e;
		}

		CommonKeymanagerFactoryWrapper keymanagerFactoryWrapper;
		try {
			keymanagerFactoryWrapper = keymanagerFactoryFactory.create(configuration.getKeymanagerType(),
					keystoreParameters);

		} catch (NoSuchAlgorithmException e) {

			LOGGER.error("Unable to create key manager factory for type " + configuration.getKeymanagerType());
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

		CommonFlagContainer shutdownFlag = InjectorFactory.getInstance(CommonFlagContainer.class);
		shutdownFlag.setFlag(false);

		if (configuration.hasSpecificShutdownPort()) {
			new ShutdownConfigurator(clf).configureShutdown(configuration, serverSocketFactory,
					commonShutdownSocketFactory, shutdownFlag);
		}

		if (shutdownFlag.getFlag()) {
			LOGGER.error("Error configuring shutdown port. Terminating Server");
			System.exit(ServerExitCodes.SHUTDOWN_LISTENER_NOT_CREATABLE);
		}

		ConnectionLoop connectionLoop = new ConnectionLoop(clf);
		new Thread(() -> {
			try {
				connectionLoop.executeConnectionLoop(configuration, connectionProcessorFactory, serverSocketFactory,
						shutdownFlag);
			} catch (Exception e) {
				shutdownFlag.setFlag(true);
				LOGGER.error("Error creating connection loop. Terminating server.");
				System.exit(ServerExitCodes.CONNECTION_LOOP_NOT_CREATEABLE);
				throw new RuntimeException(e);
			}
		}).start();

	}


	private static class ConnectionLoop {

		private final CommonLogger LOGGER;

		@Inject
		public ConnectionLoop(CommonLoggerFactory clf) {
			LOGGER = clf.getLogger(this.getClass());
		}

		public void executeConnectionLoop(Configuration configuration,
										  CommonConnectionProcessorFactory connectionProcessorFactory,
										  CommonSSLServerSocketFactory serverSocketFactory,
										  CommonFlagContainer shutdownFlag)
		throws Exception {

			try (CommonSSLServerSocket serverSocket =
						 serverSocketFactory.createSSLServerSocket(configuration.getConnectionPort())) {

				serverSocket.setSoTimeout(2000);

				do {

					try {

						CommonSSLSocket accept = serverSocket.accept();

						// Neuen Thread zur Kommunikation starten
						CommonConnectionProcessor connectionProcessor = connectionProcessorFactory.create(accept,
								shutdownFlag);
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									connectionProcessor.start();
								} catch (IOException e) {
									LOGGER.error("Unable to start connection processor: " + e.getMessage());
									throw new RuntimeException(e);
								}
							}
						}).start();

					} catch (SocketTimeoutException timeoutException) {
						// Intentionally empty
					}

				} while (!shutdownFlag.getFlag());

				LOGGER.info("Terminating connection loop.");
			}
		}

	}

	private static class ShutdownConfigurator {

		private final CommonLogger LOGGER;

		@Inject
		public ShutdownConfigurator(CommonLoggerFactory clf) {
			LOGGER = clf.getLogger(this.getClass());
		}

		void configureShutdown(Configuration configuration, CommonSSLServerSocketFactory serverSocketFactory,
							   CommonShutdownSocketFactory commonShutdownSocketFactory,
							   CommonFlagContainer shutdownFlag)
		throws IOException {

			CommonSSLServerSocket shutdownSocket =
					serverSocketFactory.createSSLServerSocket(configuration.getShutdownPort());
			CommonShutdownSocketListener commonShutdownSocketListener =
					commonShutdownSocketFactory.create(shutdownFlag, shutdownSocket);

			try {
				try {
					// neuen Thread starten
					new Thread(() -> {
						try {
							commonShutdownSocketListener.start();
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}).start();

				} catch (RuntimeException e) {
					Throwable cause = e.getCause();
					if (cause instanceof IOException) {
						throw e;
					}

					throw new IOException(e);
				}

			} catch (IOException iox) {

				LOGGER.error("Unable to configure shutdown listener: {} ", iox.getMessage());
				shutdownFlag.setFlag(true);
			}
		}


	}

}
