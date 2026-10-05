package org.trusky.common.impl.network.socket;

import org.trusky.common.api.network.socket.CommonSSLSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

/**
 * Fake für CommonSSLSocket zu Testzwecken.
 * Eine einfache, unabhängige Implementierung für Tests.
 */
public class CommonSSLSocketFake implements CommonSSLSocket {

	private InputStream inputStream;
	private OutputStream outputStream;
	private InetAddress inetAddress;
	private boolean closed = false;

	public CommonSSLSocketFake() {
	}

	public CommonSSLSocketFake(InputStream inputStream, OutputStream outputStream) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (closed) {
			throw new IOException("Socket ist geschlossen");
		}
		return inputStream;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		if (closed) {
			throw new IOException("Socket ist geschlossen");
		}
		return outputStream;
	}

	@Override
	public void close() throws IOException {
		closed = true;
	}

	@Override
	public InetAddress getInetAddress() throws IOException {
		if (closed) {
			throw new IOException("Socket ist geschlossen");
		}
		return inetAddress;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	public boolean isClosed() {
		return closed;
	}
}

