package org.trusky.common.api.network.server;

import org.trusky.common.api.network.socket.CommonSSLSocket;

public interface CommonServerTLSConnectionFactory {

	CommonSSLSocket createTLSSocket();
}
