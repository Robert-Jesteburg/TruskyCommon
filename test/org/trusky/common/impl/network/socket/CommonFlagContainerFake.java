package org.trusky.common.impl.network.socket;

import org.trusky.common.api.network.socket.CommonFlagContainer;

/**
 * Fake für CommonFlagContainer zu Testzwecken.
 * Eine einfache, unabhängige Implementierung ohne Abhängigkeiten zur echten Implementierung.
 * Kann für verschiedene Tests wiederverwendet werden.
 */
public class CommonFlagContainerFake implements CommonFlagContainer {

	private boolean flag = false;

	@Override
	public void setFlag(boolean flagValue) {
		this.flag = flagValue;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}
}

