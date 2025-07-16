package org.trusky.common.impl.network.socket;

import org.trusky.common.api.network.socket.CommonSSLServerSocket;
import org.trusky.common.api.network.socket.CommonSSLSocket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.SocketException;

public class CommonSSLServerSocketImpl implements CommonSSLServerSocket {

	private final SSLServerSocket sslServerSocket;

	public CommonSSLServerSocketImpl(SSLServerSocket sslServerSocket) {
		this.sslServerSocket = sslServerSocket;
	}

	public CommonSSLSocket accept() throws IOException {

		SSLSocket accept = (SSLSocket) sslServerSocket.accept();
		return new CommonSSLSocketImpl(accept);
	}

	@Override
	public void setSoTimeout(int timeoutInMilliSeconds) throws SocketException {
		sslServerSocket.setSoTimeout(timeoutInMilliSeconds);
	}

	@Override
	public void close() throws Exception {
		sslServerSocket.close();
	}
}
