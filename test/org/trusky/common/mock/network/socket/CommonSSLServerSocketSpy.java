package org.trusky.common.mock.network.socket;

import org.trusky.common.api.network.socket.CommonSSLServerSocket;
import org.trusky.common.api.network.socket.CommonSSLSocket;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Test-Spy für `CommonSSLServerSocket`.
 * <p>
 * Features:
 * - Fluent-API: Rückgabewerte für `accept()` können vorab gesetzt werden.
 * - Zählt Aufrufe von `accept()` und `setSoTimeout(int)`.
 * - Protokolliert alle Aufrufe in der Reihenfolge ihres Eintreffens, inkl. Parametern
 * und zurückgegebener Werte, so dass Tests diese auswerten können.
 */
public final class CommonSSLServerSocketSpy implements CommonSSLServerSocket {

	private final List<Call> callLog = new ArrayList<>();
	private final List<CommonSSLSocket> acceptReturnQueue = new ArrayList<>();

	public CommonSSLServerSocketSpy() {
	}

	/**
	 * Factory-Method (konvenient für Tests)
	 */
	public static CommonSSLServerSocketSpy create() {
		return new CommonSSLServerSocketSpy();
	}

	/**
	 * Setzt den Rückgabewert für den nächsten Aufruf von {@link #accept()}.
	 */
	public CommonSSLServerSocketSpy withAcceptReturn(CommonSSLSocket socket) {
		acceptReturnQueue.clear();
		acceptReturnQueue.add(Objects.requireNonNull(socket, "socket darf nicht null sein"));
		return this;
	}

	/**
	 * Setzt eine Folge von Rückgabewerten für aufeinanderfolgende {@link #accept()}-Aufrufe.
	 * Die Werte werden in der gleichen Reihenfolge wie übergeben zurückgegeben.
	 */
	public CommonSSLServerSocketSpy withAcceptReturns(CommonSSLSocket... sockets) {
		acceptReturnQueue.clear();
		if (sockets != null) {
			for (CommonSSLSocket s : sockets) {
				acceptReturnQueue.add(Objects.requireNonNull(s, "socket darf nicht null sein"));
			}
		}
		return this;
	}

	/**
	 * Fügt einen weiteren Rückgabewert ans Ende der Warteschlange hinzu.
	 */
	public CommonSSLServerSocketSpy enqueueAcceptReturn(CommonSSLSocket socket) {
		acceptReturnQueue.add(Objects.requireNonNull(socket, "socket darf nicht null sein"));
		return this;
	}

	@Override
	public CommonSSLSocket accept() throws IOException {
		CommonSSLSocket returned = null;
		if (!acceptReturnQueue.isEmpty()) {
			returned = acceptReturnQueue.remove(0);
		}
		callLog.add(new Call(Call.Type.ACCEPT, null, returned));
		return returned;
	}

	@Override
	public void setSoTimeout(int timeoutInMilliSeconds) throws SocketException {
		callLog.add(new Call(Call.Type.SET_SO_TIMEOUT, timeoutInMilliSeconds, null));
	}

	@Override
	public void close() throws IOException {
		callLog.add(new Call(Call.Type.CLOSE, null, null));
	}

	/**
	 * Liefert eine unveränderliche Kopie des Aufrufprotokolls in chronologischer Reihenfolge.
	 */
	public List<Call> getCallLog() {
		return Collections.unmodifiableList(new ArrayList<>(callLog));
	}

	public int getCallCount() {
		return callLog.size();
	}

	public int getAcceptCallCount() {
		return (int) callLog.stream()
				.filter(c -> c.getType() == Call.Type.ACCEPT)
				.count();
	}

	public int getSetSoTimeoutCallCount() {
		return (int) callLog.stream()
				.filter(c -> c.getType() == Call.Type.SET_SO_TIMEOUT)
				.count();
	}

	/**
	 * Liefert die Parameter (timeouts) aller {@code setSoTimeout}-Aufrufe in der Reihenfolge der Aufrufe.
	 */
	public List<Integer> getSetSoTimeoutParameters() {
		return callLog.stream()
				.filter(c -> c.getType() == Call.Type.SET_SO_TIMEOUT)
				.map(Call::getTimeoutInMilliSeconds)
				.toList();
	}

	/**
	 * Liefert die zurückgegebenen Sockets aller {@code accept}-Aufrufe in Aufrufreihenfolge.
	 */
	public List<CommonSSLSocket> getAcceptReturnValues() {
		return callLog.stream()
				.filter(c -> c.getType() == Call.Type.ACCEPT)
				.map(Call::getReturnedSocket)
				.toList();
	}

	/**
	 * Leert das interne Aufrufprotokoll (nützlich zwischen Testfällen).
	 */
	public void clearCallLog() {
		callLog.clear();
	}

	/**
	 * Entfernt alle konfigurierten Rückgabewerte für {@code accept()}.
	 */
	public void clearAcceptReturns() {
		acceptReturnQueue.clear();
	}

	/**
	 * Repräsentiert einen einzelnen Aufruf (ein Eintrag im Protokoll).
	 */
	public static final class Call {
		public enum Type {ACCEPT, SET_SO_TIMEOUT, CLOSE}

		private final Type type;
		private final Integer timeoutInMilliSeconds; // nur für SET_SO_TIMEOUT
		private final CommonSSLSocket returnedSocket; // nur für ACCEPT

		public Call(Type type, Integer timeoutInMilliSeconds, CommonSSLSocket returnedSocket) {
			this.type = Objects.requireNonNull(type);
			this.timeoutInMilliSeconds = timeoutInMilliSeconds;
			this.returnedSocket = returnedSocket;
		}

		public Type getType() {
			return type;
		}

		public Integer getTimeoutInMilliSeconds() {
			return timeoutInMilliSeconds;
		}

		public CommonSSLSocket getReturnedSocket() {
			return returnedSocket;
		}

		@Override
		public String toString() {
			return "Call{" + "type=" + type + ", timeoutInMilliSeconds=" + timeoutInMilliSeconds + ", returnedSocket=" + returnedSocket + '}';
		}
	}
}

