package org.trusky.common.impl.network.socket;

import org.trusky.common.api.network.socket.CommonKeyStore;

import java.security.KeyStore;

/**
 * This is simply a wrapper for a KeyStore, as KeyStore isn't an interface and it's not possible to derive from
 * because of final methods.
 */
public class CommonKeyStoreImpl implements CommonKeyStore {

	private final KeyStore keyStore;
	private final String keystorePassword;

	public CommonKeyStoreImpl(KeyStore keyStore, String keystorePassword) {
		this.keyStore = keyStore;
		this.keystorePassword = keystorePassword;

	}

	KeyStore getKeyStore() {
		return keyStore;
	}

	String getKeystorePassword() {
		return keystorePassword;
	}
}
