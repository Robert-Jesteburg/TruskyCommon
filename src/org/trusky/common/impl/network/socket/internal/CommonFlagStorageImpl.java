package org.trusky.common.impl.network.socket.internal;

public class CommonFlagStorageImpl {

	boolean flagValue = false;

	public synchronized void setFlag(boolean flagValue) {
		this.flagValue = flagValue;
	}

	public synchronized boolean getFlag() {
		return flagValue;
	}
}
