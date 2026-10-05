package org.trusky.common.impl.network.socket;

import org.trusky.common.api.network.socket.CommonSSLServerSocket;
import org.trusky.common.api.network.socket.CommonSSLSocket;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Spy für CommonSSLServerSocket zu Testzwecken.
 * Liefert ein vorher hinterlegtes CommonSSLSocket und verfolgt setSoTimeout-Aufrufe.
 */
public class CommonSSLServerSocketSpy implements CommonSSLServerSocket {

	private final CommonSSLSocket sockeySSLSocket;
	private final List<SetSoTimeoutCall> setSoTimeoutCalls = new ArrayList<>();

	public CommonSSLServerSocketSpy(CommonSSLSocket providedSocket) {
		this.sockeySSLSocket = Objects.requireNonNull(providedSocket, "providedSocket darf nicht null sein");
	}

	@Override
	public CommonSSLSocket accept() throws IOException {
		return sockeySSLSocket;
	}

	@Override
	public void setSoTimeout(int timeoutInMilliSeconds) throws SocketException {
		setSoTimeoutCalls.add(new SetSoTimeoutCall(timeoutInMilliSeconds));
	}

	/**
	 * Gibt alle gespeicherten setSoTimeout-Aufrufe mit ihren Parametern zurück.
	 *
	 * @return Liste aller setSoTimeout-Aufrufe
	 */
	public List<SetSoTimeoutCall> getSetSoTimeoutCalls() {
		return new ArrayList<>(setSoTimeoutCalls);
	}

	/**
	 * Setzt die Liste der gespeicherten setSoTimeout-Aufrufe zurück.
	 */
	public void clearSetSoTimeoutCalls() {
		setSoTimeoutCalls.clear();
	}

	@Override
	public void close() throws IOException {
		// Kann bei Bedarf implementiert werden
	}

	/**
	 * Repräsentiert einen setSoTimeout-Aufruf mit seinem Parameter.
	 */
	public static class SetSoTimeoutCall {
		private final int timeoutInMilliSeconds;

		public SetSoTimeoutCall(int timeoutInMilliSeconds) {
			this.timeoutInMilliSeconds = timeoutInMilliSeconds;
		}

		public int getTimeoutInMilliSeconds() {
			return timeoutInMilliSeconds;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			SetSoTimeoutCall that = (SetSoTimeoutCall) o;
			return timeoutInMilliSeconds == that.timeoutInMilliSeconds;
		}

		@Override
		public int hashCode() {
			return Objects.hash(timeoutInMilliSeconds);
		}

		@Override
		public String toString() {
			return "SetSoTimeoutCall{" +
					"timeoutInMilliSeconds=" + timeoutInMilliSeconds +
					'}';
		}
	}
}

