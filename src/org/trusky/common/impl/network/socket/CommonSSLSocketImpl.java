package org.trusky.common.impl.network.socket;

import org.jetbrains.annotations.NotNull;
import org.trusky.common.api.network.socket.CommonSSLSocket;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public class CommonSSLSocketImpl implements CommonSSLSocket {


	private final SSLSocket sslSocket;
	private boolean isSocketOpen;

	public CommonSSLSocketImpl(@NotNull SSLSocket sslSocket) {
		this.sslSocket = sslSocket;
		isSocketOpen = !sslSocket.isClosed();
	}

	@Override
	public InputStream getInputStream() throws IOException {

		if (!isSocketOpen) {
			throw new IOException("Socket is (already) closed.");
		}

		return sslSocket.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {

		if (!isSocketOpen) {
			throw new IOException("Socket is (already) closed.");
		}

		return sslSocket.getOutputStream();
	}

	@Override
	public void close() throws IOException {
		if (isSocketOpen) {
			sslSocket.close();
			isSocketOpen = false;
		}
	}

	@Override
	public InetAddress getInetAddress() throws IOException {

		if (!isSocketOpen) {
			throw new IOException("Socket is (already) closed.");
		}

		return sslSocket.getInetAddress();
	}
}
