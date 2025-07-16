package org.trusky.common.api.network.socket;

import java.io.IOException;
import java.net.SocketException;

public interface CommonSSLServerSocket extends AutoCloseable {

	CommonSSLSocket accept() throws IOException;

	void setSoTimeout(int timeoutInMilliSeconds) throws SocketException;
}
