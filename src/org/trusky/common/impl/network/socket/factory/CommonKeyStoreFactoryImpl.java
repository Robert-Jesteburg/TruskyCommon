package org.trusky.common.impl.network.socket.factory;

import org.trusky.common.api.network.socket.CommonKeyStore;
import org.trusky.common.api.network.socket.CommonKeystoreParameters;
import org.trusky.common.api.network.socket.factory.CommonKeyStoreFactory;
import org.trusky.common.impl.network.socket.CommonKeystoreParametersImpl;
import org.trusky.common.impl.network.socket.factory.internal.CommonKeystoreCreator;

import javax.inject.Inject;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class CommonKeyStoreFactoryImpl implements CommonKeyStoreFactory {

	private final CommonKeystoreCreator keystoreCreator;

	@Inject
	public CommonKeyStoreFactoryImpl(CommonKeystoreCreator keystoreCreator) {
		this.keystoreCreator = keystoreCreator;
	}

	@Override
	public CommonKeyStore create(CommonKeystoreParameters parameters)
	throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

		if (parameters == null) {
			throw new IllegalArgumentException("Parameters must not be NULL.");
		}

		if (!(parameters instanceof CommonKeystoreParametersImpl)) {
			throw new IllegalArgumentException("Invalid type of parameters: " + parameters.getClass()
					.getCanonicalName());
		}

		CommonKeystoreParametersImpl params = (CommonKeystoreParametersImpl) parameters;
		return keystoreCreator.createKeystore(params);
	}
}
