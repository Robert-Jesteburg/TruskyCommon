package org.trusky.common.impl.network.socket;

import org.trusky.common.api.network.client.CommonSSLSocketFactory;
import org.trusky.common.api.network.server.CommonSSLServerSocketFactory;
import org.trusky.common.api.network.socket.CommonKeymanagerFactoryWrapper;
import org.trusky.common.api.network.socket.CommonSSLContext;
import org.trusky.common.impl.network.server.CommonSSLServerSocketFactoryImpl;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;

public class CommonSSLContextImpl implements CommonSSLContext {

	private final SSLContext sslContext;

	public CommonSSLContextImpl(SSLContext sslContext) {
		this.sslContext = sslContext;
	}

	@Override
	public void initForServer(CommonKeymanagerFactoryWrapper keyManagerFactory) throws KeyManagementException {

		if (keyManagerFactory == null) {
			throw new IllegalArgumentException("The keyManagerFCTORY MUST NOT BE NULL.");
		}

		if (!(keyManagerFactory instanceof CommonKeymanagerFactoryWrapperImpl)) {
			throw new IllegalArgumentException("KeyManagerFactory is of illegal type " + keyManagerFactory.getClass()
					.getCanonicalName() + ".");
		}

		CommonKeymanagerFactoryWrapperImpl ckmf = (CommonKeymanagerFactoryWrapperImpl) keyManagerFactory;
		sslContext.init(ckmf.getKeyManagers(), null, null);
	}

	@Override
	public CommonSSLSocketFactory getSocketFactory() {

		// FIXME Client code (später) implementieren, siehe Beispiel
		return null;

	}

	@Override
	public CommonSSLServerSocketFactory getServerSocketFactory() {
		return new CommonSSLServerSocketFactoryImpl(sslContext.getServerSocketFactory());
	}
}
