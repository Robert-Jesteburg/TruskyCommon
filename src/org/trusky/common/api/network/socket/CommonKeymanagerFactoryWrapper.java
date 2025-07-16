package org.trusky.common.api.network.socket;

import javax.net.ssl.KeyManager;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * It is a generalization of a SDK class named KeyManagerFactory. It is used internally
 * for the creation of an SSL socket.
 *
 * <p>There is a factory to create this wrapper called
 * {@link org.trusky.common.api.network.socket.factory.CommonKeymanagerFactoryFactory}</p>
 */
public interface CommonKeymanagerFactoryWrapper {

	void init(CommonKeyStore keyStore, String keyStorePassword)
	throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException;

	KeyManager[] getKeyManagers();
}
