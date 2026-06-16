package org.trusky.common.api.network.server;

/**
 * This handler is responsible for handling one client connection on the server. It must be supplied by the
 * concrete server implementation and created using the CommonClientConnectionHandlerFactory
 */
public interface CommonClientSSLConnectionHandler {

	/**
	 * Handles the complete communication, from logon to any actions or requests until logoff. Will terminate if
	 * shutdownFlag is set to true, or if the client disconnects. The client socket will be closed when this method
	 * returns.
	 *
	 */
	void handleCommunication();
}
