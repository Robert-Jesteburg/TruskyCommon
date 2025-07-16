package org.trusky.common.impl.network.socket;

import org.trusky.common.api.network.socket.CommonFlagContainer;
import org.trusky.common.impl.network.socket.internal.CommonFlagStorageImpl;

public class CommonFlagContainerImpl implements CommonFlagContainer {

	private static CommonFlagStorageImpl flagStorage = null;

	@Override
	public void setFlag(boolean flagValue) {

		if (flagStorage == null) {
			createFlagStorage();
		}

		flagStorage.setFlag(flagValue);
	}

	@Override
	public boolean getFlag() {

		if (flagStorage == null) {
			createFlagStorage();
		}

		return flagStorage.getFlag();
	}

	private synchronized void createFlagStorage() {

		if (flagStorage == null) {
			flagStorage = new CommonFlagStorageImpl();
		}

	}
}
