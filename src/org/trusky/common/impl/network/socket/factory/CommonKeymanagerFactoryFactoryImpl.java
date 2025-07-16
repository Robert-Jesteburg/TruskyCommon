package org.trusky.common.impl.network.socket.factory;

import org.trusky.common.api.network.socket.CommonKeymanagerFactoryWrapper;
import org.trusky.common.api.network.socket.CommonKeystoreParameters;
import org.trusky.common.api.network.socket.factory.CommonKeymanagerFactoryFactory;
import org.trusky.common.impl.network.socket.CommonKeymanagerFactoryWrapperImpl;

import javax.net.ssl.KeyManagerFactory;
import java.security.NoSuchAlgorithmException;

public class CommonKeymanagerFactoryFactoryImpl implements CommonKeymanagerFactoryFactory {

	@Override
	public CommonKeymanagerFactoryWrapper create(String instance, CommonKeystoreParameters keystoreParameters)
	throws NoSuchAlgorithmException {

		KeyManagerFactory kmf = KeyManagerFactory.getInstance(instance);

		return new CommonKeymanagerFactoryWrapperImpl(kmf);
	}
}
