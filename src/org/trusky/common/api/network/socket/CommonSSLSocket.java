package org.trusky.common.api.network.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public interface CommonSSLSocket {

	InputStream getInputStream() throws IOException;

	OutputStream getOutputStream() throws IOException;

	void close() throws IOException;

	InetAddress getInetAddress() throws IOException;
}
