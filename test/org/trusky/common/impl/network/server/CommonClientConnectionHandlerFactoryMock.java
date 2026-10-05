package org.trusky.common.impl.network.server;

import org.trusky.common.api.network.server.CommonClientConnectionHandlerFactory;
import org.trusky.common.api.network.server.CommonClientSSLConnectionHandler;
import org.trusky.common.api.network.socket.CommonFlagContainer;
import org.trusky.common.api.network.socket.CommonSSLSocket;

import java.util.Objects;

/**
 * Mock für CommonClientConnectionHandlerFactory zu Testzwecken.
 * Liefert eine zuvor hinterlegte Instanz von CommonClientSSLConnectionHandler.
 */
public class CommonClientConnectionHandlerFactoryMock implements CommonClientConnectionHandlerFactory {

	private CommonClientSSLConnectionHandler providedHandler;

	public CommonClientConnectionHandlerFactoryMock() {
		this(() -> {
		}); // Do nothing on callback
	}

	public CommonClientConnectionHandlerFactoryMock(CommonClientSSLConnectionHandler providedHandler) {
		this.providedHandler = Objects.requireNonNull(providedHandler, "providedHandler darf nicht null sein");
	}

	@Override
	public CommonClientSSLConnectionHandler createClientConnectionHandler(CommonSSLSocket sslSocket,
																		  CommonFlagContainer shutdownFlag) {
		// Ignoriert die Parameter und liefert immer die hinterlegte Instanz
		return providedHandler;
	}

	public void setProvidedHandler(CommonClientSSLConnectionHandler providedHandler) {
		this.providedHandler = Objects.requireNonNull(providedHandler, "providedHandler darf nicht null sein");
	}
}

