package org.trusky.common.impl.network.server;

import org.trusky.common.api.network.server.CommonSSLServerSocketFactory;
import org.trusky.common.api.network.socket.CommonSSLServerSocket;
import org.trusky.common.impl.network.socket.CommonSSLServerSocketImpl;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;

public class CommonSSLServerSocketFactoryImpl implements CommonSSLServerSocketFactory {


	private final SSLServerSocketFactory serverSocketFactory;

	public CommonSSLServerSocketFactoryImpl(SSLServerSocketFactory serverSocketFactory) {
		this.serverSocketFactory = serverSocketFactory;
	}

	@Override
	public CommonSSLServerSocket createSSLServerSocket(int port) throws IOException {

		SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(port);
		return new CommonSSLServerSocketImpl(sslServerSocket);
	}
}
