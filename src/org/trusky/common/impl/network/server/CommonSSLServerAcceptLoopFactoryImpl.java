package org.trusky.common.impl.network.server;

import org.trusky.common.api.network.server.CommonClientConnectionHandlerFactory;
import org.trusky.common.api.network.server.CommonSSLServerAcceptLoop;
import org.trusky.common.api.network.server.CommonSSLServerAcceptLoopFactory;
import org.trusky.common.api.network.server.RunnableExecutor;
import org.trusky.common.api.network.socket.CommonFlagContainer;
import org.trusky.common.api.network.socket.CommonSSLServerSocket;

public class CommonSSLServerAcceptLoopFactoryImpl implements CommonSSLServerAcceptLoopFactory {


	@Override
	public CommonSSLServerAcceptLoop createSSLServerAcceptLoop(CommonSSLServerSocket sslServerSocket,
															   CommonClientConnectionHandlerFactory connectionHandlerFactory, //
															   CommonFlagContainer shutdownFlag) {

		return createSSLServerAcceptLoop( //
				sslServerSocket, //
				connectionHandlerFactory, //
				shutdownFlag, //
				new ThreadRunnableExecutor() //
										);

	}

	@Override
	public CommonSSLServerAcceptLoop createSSLServerAcceptLoop(CommonSSLServerSocket sslServerSocket,
															   CommonClientConnectionHandlerFactory connectionHandlerFactory, //
															   CommonFlagContainer shutdownFlag, //
															   RunnableExecutor runnableExecutor) {

		return new CommonSSLServerAcceptLoopImpl(sslServerSocket, connectionHandlerFactory, shutdownFlag,
				runnableExecutor);
	}
}
