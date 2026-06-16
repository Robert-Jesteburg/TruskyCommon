package org.trusky.common.api.network.server;

import org.trusky.common.api.network.socket.CommonFlagContainer;
import org.trusky.common.api.network.socket.CommonSSLSocket;

/**
 * This factory creates the approbiate handler for one client connection on the server. It must be supplied by the
 * concrete server implementation (that is, your application will create the implementation class and the injection
 * binding).
 */
public interface CommonClientConnectionHandlerFactory {

	CommonClientSSLConnectionHandler createClientConnectionHandler(CommonSSLSocket sslSocket,
																   CommonFlagContainer shutdownFlag);
}
