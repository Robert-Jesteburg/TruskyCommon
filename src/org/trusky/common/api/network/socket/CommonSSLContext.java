package org.trusky.common.api.network.socket;

import org.trusky.common.api.network.client.CommonSSLSocketFactory;
import org.trusky.common.api.network.server.CommonSSLServerSocketFactory;

import java.security.KeyManagementException;

public interface CommonSSLContext {

	void initForServer(CommonKeymanagerFactoryWrapper keyManagerFactory) throws KeyManagementException;

	// FIXME eine Init-Methode für ein CommonTrustManagerFactory fehlt noch (Client code)

	CommonSSLSocketFactory getSocketFactory();

	CommonSSLServerSocketFactory getServerSocketFactory();
}
