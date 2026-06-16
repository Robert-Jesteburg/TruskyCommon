package org.trusky.common.impl.network.server;

import org.trusky.common.api.network.server.CommonClientConnectionHandlerFactory;
import org.trusky.common.api.network.server.CommonClientSSLConnectionHandler;
import org.trusky.common.api.network.server.CommonSSLServerAcceptLoop;
import org.trusky.common.api.network.server.RunnableExecutor;
import org.trusky.common.api.network.socket.CommonFlagContainer;
import org.trusky.common.api.network.socket.CommonSSLServerSocket;
import org.trusky.common.api.network.socket.CommonSSLSocket;

import java.net.SocketException;

// FIXME write test
public class CommonSSLServerAcceptLoopImpl implements CommonSSLServerAcceptLoop {

	private boolean isRunning = false;

	private final CommonSSLServerSocket sslServerSocket;
	private final CommonClientConnectionHandlerFactory connectionHandlerFactory;
	private final CommonFlagContainer shutdownFlag;
	private final RunnableExecutor runnableExecutor;

	CommonSSLServerAcceptLoopImpl(CommonSSLServerSocket sslServerSocket,
								  CommonClientConnectionHandlerFactory connectionHandlerFactory,
								  CommonFlagContainer shutdownFlag,
								  RunnableExecutor runnableExecutor) {
		this.sslServerSocket = sslServerSocket;
		this.connectionHandlerFactory = connectionHandlerFactory;
		this.shutdownFlag = shutdownFlag;
		this.runnableExecutor = runnableExecutor;
	}

	@Override
	public void startLoop() throws SocketException {

		sslServerSocket.setSoTimeout(1000);

		isRunning = true;
		while (!shutdownFlag.getFlag()) {
			try {
				CommonSSLSocket clientSocket = sslServerSocket.accept();
				CommonClientSSLConnectionHandler handler =
						connectionHandlerFactory.createClientConnectionHandler(clientSocket, shutdownFlag);
				// Thread creation is now delegated to the injected RunnableExecutor
				// This allows for better testability (tests can inject a synchronous executor)
				runnableExecutor.execute(() -> handler.handleCommunication());
			} catch (Exception e) {

				// FIXME log error, then continue
				e.printStackTrace();
			}
		}
		isRunning = false;

	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}
}
