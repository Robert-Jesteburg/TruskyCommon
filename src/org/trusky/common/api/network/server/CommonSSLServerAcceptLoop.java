package org.trusky.common.api.network.server;

import java.net.SocketException;

/**
 * Implements the accept loop of the server. Will use the CommonClientConnectionHandlerFactory to get a handler for
 * each new client connection. The client connection will be executed on a new thread.
 * The shutdown flag will be checked periodically, terminating the loop if set to true.
 */
public interface CommonSSLServerAcceptLoop {

	void startLoop() throws SocketException;

	boolean isRunning();
}
