package org.trusky.common.impl.network.socket;

import org.trusky.common.api.network.socket.CommonKeyStore;
import org.trusky.common.api.network.socket.CommonKeymanagerFactoryWrapper;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class CommonKeymanagerFactoryWrapperImpl implements CommonKeymanagerFactoryWrapper {

	private final KeyManagerFactory kmf;

	public CommonKeymanagerFactoryWrapperImpl(KeyManagerFactory kmf) {
		this.kmf = kmf;
	}

	@Override
	public void init(CommonKeyStore keyStore, String keyStorePassword)
	throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {

		if (keyStore == null) {
			throw new IllegalArgumentException("Key store mustn't be NULL.");
		}

		if (!(keyStore instanceof CommonKeyStoreImpl)) {
			throw new IllegalArgumentException("Key store is not of the expecetd type (but " + keyStore.getClass()
					.getCanonicalName() + ").");
		}

		CommonKeyStoreImpl internalKeyStore = (CommonKeyStoreImpl) keyStore;
		kmf.init(internalKeyStore.getKeyStore(), keyStorePassword.toCharArray());

	}

	@Override
	public KeyManager[] getKeyManagers() {
		return kmf.getKeyManagers();
	}
}
