package org.trusky.common.mock.network.socket;

import org.trusky.common.api.network.socket.CommonSSLSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

/**
 * Einfaches Fake-Objekt für {@link CommonSSLSocket} im Test-Paket.
 * <p>
 * Rückgabewerte können über eine Fluent-API gesetzt werden. Es werden keine Aufrufe
 * gezählt oder Parameter geloggt — das Objekt ist bewusst minimal gehalten.
 */
public class CommonSSLSocketFake implements CommonSSLSocket {

	private InputStream inputStream;
	private OutputStream outputStream;
	private InetAddress inetAddress;
	private boolean closed = false;

	public CommonSSLSocketFake() {
	}

	public static CommonSSLSocketFake create() {
		return new CommonSSLSocketFake();
	}

	/**
	 * Fluent-API: setzt den InputStream, der von {@link #getInputStream()} zurückgegeben wird.
	 */
	public CommonSSLSocketFake withInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
		return this;
	}

	/**
	 * Fluent-API: setzt den OutputStream, der von {@link #getOutputStream()} zurückgegeben wird.
	 */
	public CommonSSLSocketFake withOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
		return this;
	}

	/**
	 * Fluent-API: setzt die InetAddress, die von {@link #getInetAddress()} zurückgegeben wird.
	 */
	public CommonSSLSocketFake withInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
		return this;
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

		// Streams are from the outside - so caller must close them!

	}

	@Override
	public InetAddress getInetAddress() throws IOException {
		if (closed) {
			throw new IOException("Socket ist geschlossen");
		}
		return inetAddress;
	}

	/**
	 * Hilfs-API: prüft, ob das Fake geschlossen wurde.
	 */
	public boolean isClosed() {
		return closed;
	}
}

