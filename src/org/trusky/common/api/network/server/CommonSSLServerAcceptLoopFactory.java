package org.trusky.common.api.network.server;

import org.trusky.common.api.network.socket.CommonFlagContainer;
import org.trusky.common.api.network.socket.CommonSSLServerSocket;

/**
 * Creates the CommonSSLServerAcceptLoop.
 */
public interface CommonSSLServerAcceptLoopFactory {

	CommonSSLServerAcceptLoop createSSLServerAcceptLoop(CommonSSLServerSocket sslServerSocket,
														CommonClientConnectionHandlerFactory connectionHandlerFactory,
														CommonFlagContainer shutdownFlag);

	CommonSSLServerAcceptLoop createSSLServerAcceptLoop(CommonSSLServerSocket sslServerSocket,
														CommonClientConnectionHandlerFactory connectionHandlerFactory,
														CommonFlagContainer shutdownFlag,
														RunnableExecutor runnableExecutor);
}
