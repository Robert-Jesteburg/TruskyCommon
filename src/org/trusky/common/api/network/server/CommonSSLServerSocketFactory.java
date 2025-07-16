package org.trusky.common.api.network.server;

import org.trusky.common.api.network.socket.CommonSSLServerSocket;

import java.io.IOException;

public interface CommonSSLServerSocketFactory {

	CommonSSLServerSocket createSSLServerSocket(int port) throws IOException;
}
