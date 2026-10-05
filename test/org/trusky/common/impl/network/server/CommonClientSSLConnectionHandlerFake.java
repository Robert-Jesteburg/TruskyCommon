package org.trusky.common.impl.network.server;

import org.trusky.common.api.network.server.CommonClientSSLConnectionHandler;

/**
 * Fake für CommonClientSSLConnectionHandler zu Testzwecken.
 * Eine einfache, unabhängige Implementierung für Tests.
 */
public class CommonClientSSLConnectionHandlerFake implements CommonClientSSLConnectionHandler {

	private boolean communicationHandled = false;
	private Exception exceptionToThrow = null;

	private Runnable callback = () -> {
	};

	@Override
	public void handleCommunication() {

		callback.run();

		if (exceptionToThrow != null) {
			throw new RuntimeException(exceptionToThrow);
		}
		communicationHandled = true;
	}

	public boolean isCommunicationHandled() {
		return communicationHandled;
	}

	public void setExceptionToThrow(Exception exception) {
		this.exceptionToThrow = exception;
	}

	public void reset() {
		communicationHandled = false;
		exceptionToThrow = null;
		callback = () -> {
		};
	}

	public void setCallback(Runnable callback) {
		this.callback = callback;
	}
}

