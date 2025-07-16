package org.trusky.common.api.network.socket.factory;

import org.trusky.common.api.network.socket.CommonKeymanagerFactoryWrapper;
import org.trusky.common.api.network.socket.CommonKeystoreParameters;

import java.security.NoSuchAlgorithmException;

public interface CommonKeymanagerFactoryFactory {

	CommonKeymanagerFactoryWrapper create(String instance, CommonKeystoreParameters keystoreParameters)
	throws NoSuchAlgorithmException;
}
